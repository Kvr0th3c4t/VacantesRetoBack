package vacantes.modelo.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import vacantes.modelo.dto.UsuarioDto;
import vacantes.modelo.entities.Usuario;
import vacantes.repository.IGenericoCRUD;


public interface UsuarioService extends IGenericoCRUD<Usuario, String>, UserDetailsService{
	UsuarioDto findByEmail(String email);
	boolean existsByEmail(String email);
	Usuario buscarPorEmailEntidad(String Email); 
}
