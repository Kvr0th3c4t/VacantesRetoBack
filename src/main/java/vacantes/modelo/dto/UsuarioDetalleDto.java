package vacantes.modelo.dto;

import java.util.Date;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vacantes.modelo.entities.Rol;
import vacantes.modelo.entities.Usuario;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder

public class UsuarioDetalleDto {
	private String email;
	private String nombre;
	private String apellidos;
	private Rol rol;
	private Date fechaRegistro; 
	
	public UsuarioDetalleDto convertToUsuarioDetalleDto(Usuario usuario) {
		ModelMapper modelMapper = new ModelMapper();
		
		TypeMap<Usuario, UsuarioDetalleDto> typeMapE = modelMapper.createTypeMap(Usuario.class, UsuarioDetalleDto.class);
	        typeMapE.addMappings(mapper -> {
	            mapper.map(Usuario::getEmail, UsuarioDetalleDto::setEmail);
	            mapper.map(Usuario::getNombre, UsuarioDetalleDto::setNombre);
	            mapper.map(Usuario::getApellidos, UsuarioDetalleDto::setApellidos);
	        });

	        return modelMapper.map(usuario, UsuarioDetalleDto.class);
	    }
}
