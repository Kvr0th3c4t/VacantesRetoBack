package vacantes.modelo.dto;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vacantes.modelo.entities.Usuario;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UsuarioAltaDto {
	
	private String email;
	private String nombre;
	private String apellidos;
	private String password;
	
	public UsuarioAltaDto convertToUsuarioAltaDto(Usuario usuario) {
		ModelMapper modelMapper = new ModelMapper();
		
		 TypeMap<Usuario, UsuarioAltaDto> typeMapE = modelMapper.createTypeMap(Usuario.class, UsuarioAltaDto.class);
	        typeMapE.addMappings(mapper -> {
	            mapper.map(Usuario::getEmail, UsuarioAltaDto::setEmail);
	            mapper.map(Usuario::getNombre, UsuarioAltaDto::setNombre);
	            mapper.map(Usuario::getApellidos, UsuarioAltaDto::setApellidos);
	            mapper.map(Usuario::getPassword, UsuarioAltaDto::setPassword);
	        });

	        return modelMapper.map(usuario, UsuarioAltaDto.class);
	    }
}
