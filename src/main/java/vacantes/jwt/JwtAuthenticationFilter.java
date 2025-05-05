package vacantes.jwt;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@Component // Marca esta clase como un componente de Spring (para que pueda ser inyectado)
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenUtil jwtTokenUtil; // Clase utilitaria para trabajar con JWT

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        // Obtiene el valor del header "Authorization"
        String authHeader = request.getHeader("Authorization");

        // Verifica si el header no es nulo y empieza con "Bearer "
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            // Extrae el token eliminando el prefijo "Bearer "
            String token = authHeader.substring(7);

            // Verifica si el token es válido
            if (jwtTokenUtil.validateToken(token)) {
                // Extrae el nombre de usuario del token
                String username = jwtTokenUtil.getUsernameFromToken(token);
                // Extrae el rol del token
                String rol = jwtTokenUtil.getRoleFromToken(token); 

                // Verifica que el usuario no esté ya autenticado en el contexto de seguridad
                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    // Crea la autoridad (rol) del usuario, con el prefijo "ROLE_"
                    List<GrantedAuthority> authorities = List.of(() -> "ROLE_" + rol.toUpperCase());

                    // Crea el objeto de autenticación sin contraseña (ya que se usa JWT)
                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(username, null, authorities);

                    // Asocia detalles adicionales de la request al objeto de autenticación
                    authentication.setDetails(
                            new WebAuthenticationDetailsSource().buildDetails(request));

                    // Establece el usuario autenticado en el contexto de seguridad
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }

        // Continúa con el siguiente filtro en la cadena
        filterChain.doFilter(request, response);
    }
}
