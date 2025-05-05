package vacantes.modelo.dto;

import java.io.Serializable;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vacantes.modelo.entities.Empresa;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class EmpresaModificarDto implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private int idEmpresa;
	private String nombre;
    private String direccionFiscal;
    private String pais;
    
    public EmpresaModificarDto convertToEmpresaModificarDto(Empresa empresa) {
		ModelMapper modelMapper = new ModelMapper();
		
		 TypeMap<Empresa, EmpresaModificarDto> typeMapE = modelMapper.createTypeMap(Empresa.class, EmpresaModificarDto.class);
	        typeMapE.addMappings(mapper -> {
	            mapper.map(Empresa::getNombreEmpresa, EmpresaModificarDto::setNombre);
	        });

	        return modelMapper.map(empresa, EmpresaModificarDto.class);
	    }
}
