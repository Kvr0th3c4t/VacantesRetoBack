package vacantes.modelo.dto;

import java.io.Serializable;

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
public class VacanteListasDto implements Serializable{

	private static final long serialVersionUID = 1L;
	private String idVacante; 
	private String salario; 
	private String imagen; 
	private String nombre;
	private Estatus estatus;
	private String descripcion;
	private String nombreCategoria;
	private String nombreEmpresa;
	
	public VacanteListasDto convertToVacanteListasDto(Vacante vacante) {
		ModelMapper modelMapper = new ModelMapper();
		
		TypeMap<Vacante, VacanteListasDto> typeMapV = 
                modelMapper.createTypeMap(Vacante.class, VacanteListasDto.class);
        		typeMapV.addMappings(maper -> {
                    maper.map(vac -> vac.getCategoria().getNombre(), VacanteListasDto::setNombreCategoria);
                    maper.map(vac -> vac.getEmpresa().getNombreEmpresa(), VacanteListasDto::setNombreEmpresa);
        });
        return modelMapper.map(vacante, VacanteListasDto.class);
   }

}
