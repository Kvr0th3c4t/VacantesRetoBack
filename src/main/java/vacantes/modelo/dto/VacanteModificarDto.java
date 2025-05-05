package vacantes.modelo.dto;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vacantes.modelo.entities.Estatus;
import vacantes.modelo.entities.Vacante;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class VacanteModificarDto {
	
	private int idVacante;
	private String nombre;
	private String descripcion;
	private double salario;
	private Estatus estatus;
	private String imagen;
	private String detalles;
	
	public VacanteModificarDto convertToVacanteModificarDto(Vacante vacante) {
		ModelMapper modelMapper = new ModelMapper();
		
		 TypeMap<Vacante, VacanteModificarDto> typeMapE = modelMapper.createTypeMap(Vacante.class, VacanteModificarDto.class);
	        typeMapE.addMappings(mapper -> {
	            mapper.map(Vacante::getNombre, VacanteModificarDto::setNombre);
	            mapper.map(Vacante::getDescripcion, VacanteModificarDto::setDescripcion);
	            mapper.map(Vacante::getSalario, VacanteModificarDto::setSalario);
	            mapper.map(Vacante::getEstatus, VacanteModificarDto::setEstatus);
	            mapper.map(Vacante::getImagen, VacanteModificarDto::setImagen);
	            mapper.map(Vacante::getDetalles, VacanteModificarDto::setDetalles);
	        });

	        return modelMapper.map(vacante, VacanteModificarDto.class);
	    }
}
