package vacantes.modelo.dto;

import java.io.Serializable;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vacantes.modelo.entities.Estatus;
import vacantes.modelo.entities.Solicitud;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class SolicitudListasDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private int idVacante;
    private int idSolicitud;
    private String nombrePostulante;
    private String apellidosPostulante;
    private String imagen;
    private String salario;
    private boolean estado;
    private String archivo;
    private String comentarios;
    private String curriculum;
    private String nombreVacante;
    private Estatus estatus;

    public SolicitudListasDto convertToSolicitudDto(Solicitud solicitud) {
        ModelMapper modelMapper = new ModelMapper();

        TypeMap<Solicitud, SolicitudListasDto> typeMapS =
            modelMapper.createTypeMap(Solicitud.class, SolicitudListasDto.class);

        typeMapS.addMappings(mapper -> {
            mapper.map(Solicitud::getArchivo, SolicitudListasDto::setArchivo);
            mapper.map(src -> src.getUsuario().getNombre(), SolicitudListasDto::setNombrePostulante);
            mapper.map(src -> src.getUsuario().getApellidos(), SolicitudListasDto::setApellidosPostulante);
            mapper.map(Solicitud::getComentarios, SolicitudListasDto::setComentarios);
            mapper.map(Solicitud::getCurriculum, SolicitudListasDto::setCurriculum);
            mapper.map(src -> src.getVacante().getNombre(), SolicitudListasDto::setNombreVacante);
            mapper.map(src -> src.getVacante().getEstatus(), SolicitudListasDto::setEstatus);
            mapper.map(src -> src.getVacante().getImagen(), SolicitudListasDto::setImagen);
            mapper.map(src -> src.getVacante().getSalario(), SolicitudListasDto::setSalario);
            mapper.map(Solicitud::isEstado, SolicitudListasDto::setEstado);
            mapper.map(src -> src.getVacante().getIdVacante(), SolicitudListasDto::setIdVacante);
            mapper.map(Solicitud::getIdSolicitud, SolicitudListasDto::setIdSolicitud);
        });

        return modelMapper.map(solicitud, SolicitudListasDto.class);
    }
}
