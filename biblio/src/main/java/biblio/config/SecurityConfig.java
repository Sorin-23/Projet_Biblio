package biblio.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
@EnableMethodSecurity(prePostEnabled = true) // Activer les annotations @PreAuthorize / @PostAuthorize
public class SecurityConfig {
    // Le SecurityFilterChain va nous permettre de configurer les accès, éventuellement le CSRF, politiques CORS générales, etc.
    @Bean // On bypass la config auto-configuration
    SecurityFilterChain filterChain(HttpSecurity http, JwtHeaderFilter jwtFilter) throws Exception {
        // Configurer ici les accès généraux
        http.authorizeHttpRequests(auth -> {
            // On autorise les ressources externes (JSP, CSS, JS, IMG)
            auth.requestMatchers("/WEB-INF/**", "/*.css", "/assets/**").permitAll();

            // On autorise tout le monde sur connexion
            auth.requestMatchers(HttpMethod.POST, "/api/auth").permitAll();

            // Sinon, accès restreint aux utilisateurs authentifiés
            auth.requestMatchers("/**").authenticated();
        });

        // Activer le formulaire de connexion
        http.formLogin(form -> {
            form.loginPage("/login"); // Page de login, GetMapping à gérer nous-même
            form.loginProcessingUrl("/process_login"); // URL de process du login par Spring Security, PostMapping créé et géré par Spring Security
            form.defaultSuccessUrl("/home", true); // Redirection vers /home après login OK
            form.permitAll(); // On autorise la page de login
        });

        // Activer la page de déconnexion
        http.logout(logout -> {
            logout.logoutUrl("/logout") // URL de déconnexion, Mapping créé et géré par Spring Security
                .logoutRequestMatcher(request -> "GET".equals(request.getMethod()) && request.getRequestURI().equals("/logout")); // Cette ligne est pour autoriser le GET sur /logout, car par défaut c'est du POST que Spring Security crée

            logout.logoutSuccessUrl("/login"); // Redirection vers /login après logout OK
        });

        // Désactiver la protection CSRF
        http.csrf(csrf -> csrf.ignoringRequestMatchers("/api/**"));

        // Configuration de la politique CORS
        http.cors(cors -> {
            CorsConfigurationSource source = request -> {
                CorsConfiguration config = new CorsConfiguration();

                // On autorise toutes les en-têtes HTTP, toutes les méthodes HTTP de tous les domaines
                config.setAllowedHeaders(List.of("*"));
                config.setAllowedMethods(List.of("*"));
                config.setAllowedOrigins(List.of("*"));

                return config;
            };

            cors.configurationSource(source);
        });

        // Positionner le filter JwtHeaderFilter AVANT AuthenticationFilter
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Permet d'injecter dans le contexte de Spring l'AuthenticationManager actuellement utilisé par Spring Security
    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
