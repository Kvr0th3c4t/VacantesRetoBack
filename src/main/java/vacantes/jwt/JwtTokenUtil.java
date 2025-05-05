package vacantes.jwt;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;


@Component // Marca esta clase como un componente de Spring para inyección automática
public class JwtTokenUtil {
	
	
	@Value("${jwt.secret}") // Inyecta la clave secreta desde application.properties
	private String SECRET_KEY;
	
    // Tiempo de validez del token de acceso (5 minutos en milisegundos ) 5 * 60 * 1000; 
    private static final long ACCESS_TOKEN_VALIDITY = 7 * 24 * 60 * 60 * 1000;  
    
    // Tiempo de validez del token de refresh (7 días en milisegundos)
    private static final long REFRESH_TOKEN_VALIDITY = 7 * 24 * 60 * 60 * 1000; 
 
    // Genera un token de acceso con username y rol
    public String generateAccessToken(String username, String rol) {
        // Convierte la clave secreta en un objeto Key utilizando codificación UTF-8
        Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
        
        // Crea el token JWT
        return Jwts.builder()
                .setSubject(username) // Establece el "subject" como el username
                .claim("rol", rol) // Agrega un claim personalizado con el rol
                .setIssuedAt(new Date()) // Fecha de emisión del token
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_VALIDITY)) // Fecha de expiración
                .signWith(key, SignatureAlgorithm.HS512) // Firma el token con la clave y algoritmo HS512
                .compact(); // Devuelve el token como String
    }
 
    // Genera un token de refresh con mayor duración 
    public String generateRefreshToken(String username, String rol) {
        Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
        
        return Jwts.builder()
                .setSubject(username)
                .claim("rol", rol)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_VALIDITY))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }
 
    // Valida un token recibido
    public boolean validateToken(String token) {
        try {
            // Crea la clave para verificar la firma
            Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
            
            // Intenta parsear el token con la clave
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true; // Si no lanza excepción, el token es válido
        } catch (JwtException ex) {
            return false; // Si hay alguna excepción (firma inválida, expirado, etc.)
        }
    }

    // Extrae el username (subject) del token
    public String getUsernameFromToken(String token) {
        Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
        
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject(); // Retorna el subject (username)
    }
    
    // Extrae el rol del token desde los claims personalizados
    public String getRoleFromToken(String token) {
        Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
        
        Claims claims = Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .getBody();

        return claims.get("rol", String.class); // Obtiene el claim "rol" como String
    }

}
