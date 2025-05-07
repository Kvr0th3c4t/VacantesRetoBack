package vacantes.modelo.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import vacantes.modelo.dto.UsuarioDto;
import vacantes.modelo.entities.Usuario;
import vacantes.repository.UsuarioRepository;

@Service
public class UsuarioServiceImpl implements UsuarioService{

	@Autowired
	private UsuarioRepository usuarioRepository;
	
    @Autowired
    private ModelMapper modelMapper;
    
	@Override
	public List<Usuario> buscarTodo() {
		return usuarioRepository.findAll();
	}

	@Override
	public Usuario buscarUno(String email) {
		return usuarioRepository.findById(email).orElse(null);
	}
	
	@Override
	public boolean insertUno(Usuario usuario) {
	    try {
	        
	        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
	            
	            Usuario existente = usuarioRepository.findByEmail(usuario.getEmail());
	            
	            existente.setNombre(usuario.getNombre());
	            existente.setApellidos(usuario.getApellidos());
	            
	            usuarioRepository.save(existente);
	        } else {
	            usuarioRepository.save(usuario);
	        }
	        return true;
	    } catch (Exception e) {
	        e.printStackTrace();
	        return false;
	    }
	}
	
	@Override
	public int eliminarUno(String iUsuario) {
		try {
			if(usuarioRepository.existsById(iUsuario)) {
				usuarioRepository.deleteById(iUsuario);
			}
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
		}return 0;
	}

	@Override
	public int modificarUno(Usuario usuario) {
		try {
			usuarioRepository.save(usuario);
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
		}return 0;
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Usuario usuario = usuarioRepository.findByEmail(email);
	    if (usuario == null) {
	        throw new UsernameNotFoundException("Usuario no encontrado");
	    }
	    return usuario; // Funciona por que esta clase Ya implementa UserDetails
	}

	@Override
	public UsuarioDto findByEmail(String email) {
        Usuario usuario = usuarioRepository.findByEmail(email);
        UsuarioDto dto = modelMapper.map(usuario, UsuarioDto.class);
        
        if (usuario.getRol().name() != null) {
            dto.setRol(usuario.getRol().name());
        }
        return dto;
	}

	@Override
	public boolean existsByEmail(String email) {
		
		return usuarioRepository.existsByEmail(email);
	}

	@Override
	public Usuario buscarPorEmailEntidad(String Email) {
		
		return usuarioRepository.findByEmail(Email);
	}
}
