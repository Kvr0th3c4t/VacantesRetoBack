package vacantes.modelo.dto;

import java.io.Serializable;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import vacantes.modelo.entities.Empresa;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@EqualsAndHashCode(of="idEmpresa")
public class EmpresaListasDto implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private int idEmpresa;
    private String nombre;
    private String pais;
    private String email;
	
	public EmpresaListasDto convertToEmpresaListasDto(Empresa empresa) {
		ModelMapper modelMapper = new ModelMapper();
		
		 TypeMap<Empresa, EmpresaListasDto> typeMapE = modelMapper.createTypeMap(Empresa.class, EmpresaListasDto.class);
	        typeMapE.addMappings(mapper -> {
	            mapper.map(Empresa::getNombreEmpresa, EmpresaListasDto::setNombre);
	            mapper.map(Empresa::getPais, EmpresaListasDto::setPais);
	            mapper.map(src -> src.getUsuario().getEmail(), EmpresaListasDto::setEmail);
	        });

	        return modelMapper.map(empresa, EmpresaListasDto.class);
	    }
}
