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
public class EmpresaDetalleDto implements Serializable{
private static final long serialVersionUID = 1L;
	
	private int idEmpresa;
    private String nombre;
    private String pais;
    private String email;
    private String cif;
    private String direccionFiscal;
	
	public EmpresaDetalleDto convertToEmpresaDetalleDto(Empresa empresa) {
		ModelMapper modelMapper = new ModelMapper();
		
		TypeMap<Empresa, EmpresaDetalleDto> typeMapE = modelMapper.createTypeMap(Empresa.class, EmpresaDetalleDto.class);
	        typeMapE.addMappings(mapper -> {
	            mapper.map(Empresa::getNombreEmpresa, EmpresaDetalleDto::setNombre);
	            mapper.map(Empresa::getPais, EmpresaDetalleDto::setPais);
	            mapper.map(src -> src.getUsuario().getEmail(), EmpresaDetalleDto::setEmail);
	        });

	        return modelMapper.map(empresa, EmpresaDetalleDto.class);
	    }
}

