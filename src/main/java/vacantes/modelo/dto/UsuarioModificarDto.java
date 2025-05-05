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
public class UsuarioModificarDto {
	private String nombre;
	private String apellidos;
	private String password;
	
	public UsuarioModificarDto convertToUsuarioModificarDto(Usuario usuario) {
		ModelMapper modelMapper = new ModelMapper();
		
		 TypeMap<Usuario, UsuarioModificarDto> typeMapE = modelMapper.createTypeMap(Usuario.class, UsuarioModificarDto.class);
	        typeMapE.addMappings(mapper -> {
	            mapper.map(Usuario::getNombre, UsuarioModificarDto::setNombre);
	            mapper.map(Usuario::getApellidos, UsuarioModificarDto::setApellidos);
	            mapper.map(Usuario::getPassword, UsuarioModificarDto::setPassword);
	        });

	        return modelMapper.map(usuario, UsuarioModificarDto.class);
	    }
}
