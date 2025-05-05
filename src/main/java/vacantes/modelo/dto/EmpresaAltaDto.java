package vacantes.modelo.dto;

import java.io.Serializable;
import java.util.Date;

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
public class EmpresaAltaDto implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String nombreEmpresa;
	private String cif;
	private String pais;
	private String direccionFiscal;
	
	private String email;
	private Date fechaResgistro;
    private String nombreUsuario;
    private String apellidos;
	
	public EmpresaAltaDto convertToEmpresaDto(Empresa empresa) {
		ModelMapper modelMapper = new ModelMapper();
		
		 TypeMap<Empresa, EmpresaAltaDto> typeMapE = modelMapper.createTypeMap(Empresa.class, EmpresaAltaDto.class);
	        typeMapE.addMappings(mapper -> {
	            mapper.map(Empresa::getCif, EmpresaAltaDto::setCif);
	            mapper.map(Empresa::getPais, EmpresaAltaDto::setPais);
	            mapper.map(Empresa::getDireccionFiscal, EmpresaAltaDto::setDireccionFiscal);
	            mapper.map(src ->
	            			src.getUsuario().getNombre(), EmpresaAltaDto::setNombreUsuario);
	            mapper.map(src ->
	            			src.getUsuario().getApellidos(), EmpresaAltaDto::setApellidos);
	            mapper.map(src -> src.getUsuario().getEmail(), EmpresaAltaDto::setEmail);
	        });

	        return modelMapper.map(empresa, EmpresaAltaDto.class);
	    }
}
