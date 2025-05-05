package vacantes.modelo.dto;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vacantes.modelo.entities.Estatus;
import vacantes.modelo.entities.Vacante;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class VacanteAltaDto {

	private String nombre;
	private String descripcion;
	private double salario;
	private Estatus estatus;
	private String imagen;
	private String detalles;
	private int idCategoria;
	
	public VacanteAltaDto convertToVacanteAltaDto(Vacante vacante) {
		ModelMapper modelMapper = new ModelMapper();
		
		 TypeMap<Vacante, VacanteAltaDto> typeMapE = modelMapper.createTypeMap(Vacante.class, VacanteAltaDto.class);
	        typeMapE.addMappings(mapper -> {
	            mapper.map(Vacante::getNombre, VacanteAltaDto::setNombre);
	            mapper.map(Vacante::getDescripcion, VacanteAltaDto::setDescripcion);
	            mapper.map(Vacante::getSalario, VacanteAltaDto::setSalario);
	            mapper.map(Vacante::getEstatus, VacanteAltaDto::setEstatus);
	            mapper.map(Vacante::getImagen, VacanteAltaDto::setImagen);
	            mapper.map(Vacante::getDetalles, VacanteAltaDto::setDetalles);
	            mapper.map(src ->
	            		src.getCategoria().getIdCategoria(), VacanteAltaDto::setIdCategoria);
	        });

	        return modelMapper.map(vacante, VacanteAltaDto.class);
	    }
}
