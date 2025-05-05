package vacantes.modelo.dto;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vacantes.modelo.entities.Solicitud;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class SolicitudAltaDto {

	private String archivo;
	private String comentarios;
	private String curriculum;
	
	public SolicitudAltaDto convertToEmpresaDto(Solicitud solicitud) {
		ModelMapper modelMapper = new ModelMapper();
		
		 TypeMap<Solicitud, SolicitudAltaDto> typeMapE = modelMapper.createTypeMap(Solicitud.class, SolicitudAltaDto.class);
	        typeMapE.addMappings(mapper -> {
	            mapper.map(Solicitud::getArchivo, SolicitudAltaDto::setArchivo);
	            mapper.map(Solicitud::getComentarios, SolicitudAltaDto::setComentarios);
	            mapper.map(Solicitud::getCurriculum, SolicitudAltaDto::setCurriculum);
	           
	        });

	        return modelMapper.map(solicitud, SolicitudAltaDto.class);
	    }
	
}
