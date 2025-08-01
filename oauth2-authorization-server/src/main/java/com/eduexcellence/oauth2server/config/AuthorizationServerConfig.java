package com.eduexcellence.oauth2server.config;

import com.nimbusds.jose.KeySourceException;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSelector;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.List;
import java.util.UUID;
import java.time.Duration;

@Configuration
public class AuthorizationServerConfig {

    // non-human user
    @Bean
    public RegisteredClientRepository repository() {
        RegisteredClient client = RegisteredClient.withId(UUID.randomUUID().toString())
                .clientId("client")
                .clientSecret("secret")
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                .redirectUri("http://localhost:8080/users")
                .scope(OidcScopes.OPENID)
                .scope("Read")
                .tokenSettings(TokenSettings.builder()
                        .refreshTokenTimeToLive(Duration.ofDays(1))
                        .accessTokenTimeToLive(Duration.ofMinutes(120))
                        .build())
                .clientSettings(ClientSettings.builder().requireProofKey(false).build())
                .build();

        return new InMemoryRegisteredClientRepository(client);
    }

    // human user
    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails john = User.withUsername("john")
                .password("123")
                .roles("read", "write")
                .build();

        return new InMemoryUserDetailsManager(john);
    }

    // Allow plain-text password
    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    // Key to sign/validate JWT
    @Bean
    public JWKSource<SecurityContext> jwkSource() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);

        KeyPair keyPair = keyPairGenerator.generateKeyPair(); // Asymmetric Key
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();

        RSAKey rsaKey = new RSAKey.Builder(publicKey)
                .privateKey(privateKey)
                .keyID(UUID.randomUUID().toString())
                .build();

        return (new JWKSource<SecurityContext>() {
            @Override
            public List<JWK> get(JWKSelector jwkSelector, SecurityContext securityContext) throws KeySourceException {
                return jwkSelector.select(new JWKSet(rsaKey));
            }
        });

    }

    // hook Authorization Server JWT to spring context
    @Bean
    public AuthorizationServerSettings authorizationServerSettings() {
        return AuthorizationServerSettings.builder().build();
    }

    // Filter-Chain 1
    @Bean
    @Order(1)
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf().disable();
        OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(httpSecurity);
        httpSecurity.getConfigurer(OAuth2AuthorizationServerConfigurer.class)
                .oidc(Customizer.withDefaults());
        httpSecurity.exceptionHandling(
                e -> e.authenticationEntryPoint(
                        new LoginUrlAuthenticationEntryPoint("/login")));

        return httpSecurity.build();
    }

    // Filter-Chain 2
    @Bean
    @Order(2)
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers(
                    "/swagger-ui/**",
                    "/swagger-ui.html",
                    "/v3/api-docs",
                    "/v3/api-docs/**",
                    "/webjars/**",
                    "/login",
                    "/error"
                ).permitAll()
                .anyRequest().authenticated()
            )
            .formLogin(Customizer.withDefaults())
            .oauth2ResourceServer(oauth2 -> oauth2.jwt());
        return http.build();
    }
}
