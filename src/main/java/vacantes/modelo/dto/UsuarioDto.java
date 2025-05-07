package vacantes.modelo.dto;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import vacantes.modelo.entities.Usuario;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of="email")
public class UsuarioDto {
	private String nombre;
    private String apellidos;
    private String email;
    private String rol;
    private String password;
    
   
    public UsuarioDto convertToUsuario(Usuario usuario) {
    	ModelMapper modelMapper = new ModelMapper();
    	TypeMap<Usuario, UsuarioDto> typeMapU = modelMapper.createTypeMap(Usuario.class, UsuarioDto.class);
        typeMapU.addMappings(mapper -> {
            mapper.map(Usuario::getEmail, UsuarioDto::setEmail);
            mapper.map(Usuario::getNombre, UsuarioDto::setNombre);
            mapper.map(Usuario::getApellidos, UsuarioDto::setApellidos);
            mapper.map(Usuario::getPassword,UsuarioDto::setPassword);
        });
        return modelMapper.map(usuario, UsuarioDto.class);
    }
}
