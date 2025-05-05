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
public class UsuarioListasDto {
	private String nombre;
	private String apellidos;
	
	public UsuarioListasDto convertToUsuarioListasDto(Usuario usuario) {
		ModelMapper modelMapper = new ModelMapper();
		
		 TypeMap<Usuario, UsuarioListasDto> typeMapE = modelMapper.createTypeMap(Usuario.class, UsuarioListasDto.class);
	        typeMapE.addMappings(mapper -> {
	            mapper.map(Usuario::getNombre, UsuarioListasDto::setNombre);
	            mapper.map(Usuario::getApellidos, UsuarioListasDto::setApellidos);
	        });

	        return modelMapper.map(usuario, UsuarioListasDto.class);
	    }
}
