package pe.gob.mpfn.cfms.mesadeturno.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.MappedJwtClaimSetConverter;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import pe.gob.mpfn.cfms.mesadeturno.bean.JwtToken;
import pe.gob.mpfn.cfms.mesadeturno.bean.Usuario;
import pe.gob.mpfn.cfms.mesadeturno.common.handler.RestAuthExceptionHandler;
import pe.gob.mpfn.cfms.mesadeturno.common.jwt.CustomJWSVerifierFactory;
import pe.gob.mpfn.cfms.mesadeturno.common.util.JwtTokenUtility;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.List;
import java.util.Map;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig {
    @Value("${jwt.token.key}")
    private String jwtTokenKey;

    private final ObjectMapper mapper;

    WebSecurityConfig(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Autowired
    private RestAuthExceptionHandler restAuthExceptionHandler;

    private static final String[] AUTH_WHITELIST = {
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**",
            "/v3/api-docs/**",
            "/api-docs/**",
            "/api/public/**",
            "/api/public/authenticate",
            "/actuator/*",
            "/swagger-ui/**"
    };

    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.csrf(AbstractHttpConfigurer::disable);
        http.cors(withDefaults());
        http.authorizeHttpRequests(authz -> authz
                .requestMatchers(AUTH_WHITELIST).permitAll()
                .requestMatchers(HttpMethod.OPTIONS, "/swagger-ui.html#/**").permitAll()
                .anyRequest().authenticated()); //Los endpoints empiezan con "e/ordenNotificacion"
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.exceptionHandling(exception -> exception.authenticationEntryPoint(restAuthExceptionHandler));
        http.exceptionHandling(exception -> exception.accessDeniedHandler(restAuthExceptionHandler));
        http.oauth2ResourceServer((oauth2) -> oauth2
                .jwt((jwtCust) -> jwtCust.decoder(jwtDecoder()))
        );
        http.httpBasic(withDefaults());
        return http.build();
    }

    @Bean
    JwtDecoder jwtDecoder() {
        String keyString = jwtTokenKey;
        SecretKey key = new SecretKeySpec(keyString.getBytes(), "DES");
        NimbusJwtDecoder decoder = NimbusJwtDecoder.withSecretKey(key).macAlgorithm(MacAlgorithm.HS512)
                .jwtProcessorCustomizer(t -> t.setJWSVerifierFactory(new CustomJWSVerifierFactory())).build();
        decoder.setClaimSetConverter(MappedJwtClaimSetConverter.withDefaults(
                Map.of(Usuario.JWT_CLAIM_NAME, source -> mapper.convertValue(source, Usuario.class))));

        return decoder;
    }

    @Bean
    @RequestScope
    JwtTokenUtility jwtTokenUtility() {
        JwtAuthenticationToken currentUser = (JwtAuthenticationToken) SecurityContextHolder.getContext()
                .getAuthentication();
        Jwt jwt = currentUser.getToken();
        JwtToken payload = new JwtToken(jwt.getSubject(), jwt.getIssuer().toString(), jwt.getClaimAsString("ip"),
                jwt.getClaim(Usuario.JWT_CLAIM_NAME));
        return new JwtTokenUtility(jwt.getTokenValue(), payload);
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("*"));
        configuration.setAllowedMethods(List.of("POST", "PUT", "GET", "OPTIONS", "DELETE", "PATCH")); // or simply "*"
        configuration.setAllowedHeaders(List.of("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}

