 package vacantes.configuracion;

 
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import vacantes.jwt.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity // Necesario para @PreAuthorize
public class DataUserConfiguration {
	

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
        	.cors(cors -> cors.configurationSource(corsConfigurationSource())) 
            .authorizeHttpRequests(auth -> auth
            		.requestMatchers(
            			    "/swagger-ui/**",
            			    "/swagger-ui.html",
            			    "/v3/api-docs/**",
            			    "/v3/api-docs",
            			    "/swagger-resources/**",
            			    "/webjars/**"
            			).permitAll() //Para permitir swagger
            		.requestMatchers("/auth/**").permitAll() // login, registro, refresh
            	    .requestMatchers("/public/**").permitAll() // contenido para invitados
            	    .requestMatchers("/api/admin/**").hasRole("ADMON") //Contenido para rol admin
            	    .requestMatchers("/api/empresa/**").hasRole("EMPRESA")//Contenido para rol empresa
            	    .requestMatchers("/api/usuario/**").hasRole("CLIENTE")//Contenido para rol user
            	    .anyRequest().authenticated()
            )
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
    
    
    @Bean
    ModelMapper modelMapper() {
        return new ModelMapper();
    }
	@Bean
	PasswordEncoder passwordEncoder() {
	    //return new BCryptPasswordEncoder(); //Descomentar linea para contraseñas encriptadas
		 return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}
	
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("http://localhost:4200"); // Tu frontend
        configuration.addAllowedMethod("*");
        configuration.addAllowedHeader("*");
        configuration.setAllowCredentials(true); // Si usas cookies, opcional

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
