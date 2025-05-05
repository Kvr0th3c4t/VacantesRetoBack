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
public class VacanteDetalleDto {
	
	private int idVacante;
	private String nombre;
	private String descripcion;
	private double salario;
	private String imagen;
	private Estatus estatus;
	private String detalles;
	private boolean destacado;
	private String nombreEmpresa;
	private String pais;
	private String nombreCategoria;
	private String descripcionCategoria;
	
	public VacanteDetalleDto convertToVacanteDetalleDto(Vacante vacante) {
		ModelMapper modelMapper = new ModelMapper();
		
		 TypeMap<Vacante, VacanteDetalleDto> typeMapE = modelMapper.createTypeMap(Vacante.class, VacanteDetalleDto.class);
	        typeMapE.addMappings(mapper -> {
	            mapper.map(Vacante::getNombre, VacanteDetalleDto::setNombre);
	            mapper.map(Vacante::getDescripcion, VacanteDetalleDto::setDescripcion);
	            mapper.map(Vacante::getSalario, VacanteDetalleDto::setSalario);
	            mapper.map(Vacante::getImagen, VacanteDetalleDto::setImagen);
	            mapper.map(Vacante::getDetalles, VacanteDetalleDto::setDetalles);
	            mapper.map(src ->
	            		src.getCategoria().getNombre(), VacanteDetalleDto::setNombreCategoria);
	            mapper.map(src ->
        				src.getCategoria().getDescripcion(), VacanteDetalleDto::setDescripcionCategoria);
	            mapper.map(src ->
        				src.getEmpresa().getNombreEmpresa(), VacanteDetalleDto::setNombreEmpresa);
	            mapper.map(src ->
						src.getEmpresa().getPais(), VacanteDetalleDto::setPais);
        
	        });

	        return modelMapper.map(vacante, VacanteDetalleDto.class);
	    }
}
