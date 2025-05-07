package vacantes.restcontroller;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import vacantes.modelo.dto.SolicitudAltaDto;
import vacantes.modelo.dto.SolicitudListasDto;
import vacantes.modelo.dto.UsuarioAltaDto;
import vacantes.modelo.dto.VacanteListasDto;
import vacantes.modelo.entities.Rol;
import vacantes.modelo.entities.Solicitud;
import vacantes.modelo.entities.Usuario;
import vacantes.modelo.entities.Vacante;
import vacantes.modelo.service.SolicitudService;
import vacantes.modelo.service.UsuarioService;
import vacantes.modelo.service.VacanteService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/usuario")
public class UsuarioRestController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private VacanteService vacanteService;

    @Autowired
    private SolicitudService solicitudService;

    @PostMapping("/altaUsuario")
    public ResponseEntity<?> altaUsuario(@RequestBody UsuarioAltaDto altaDto, HttpServletRequest request) {

        Usuario usuarioNuevo = new Usuario();

        usuarioNuevo.setEmail(altaDto.getEmail());
        usuarioNuevo.setNombre(altaDto.getNombre());
        usuarioNuevo.setApellidos(altaDto.getApellidos());
        usuarioNuevo.setPassword(altaDto.getPassword());
        usuarioNuevo.setEnabled(1);
        usuarioNuevo.setFechaRegistro(new Date());
        usuarioNuevo.setRol(Rol.CLIENTE);

        if (usuarioService.insertUno(usuarioNuevo) == true);
        return new ResponseEntity<>("El usuario se creó correctamente", HttpStatus.CREATED);
    }

    @GetMapping("/verVacanteCreada")
    public ResponseEntity<?> verCreadas(HttpServletRequest request) {

        List<Vacante> listaux = vacanteService.buscarPorCreada();

        if (!listaux.isEmpty()) {
            List<VacanteListasDto> vacanteDto = listaux.stream()
                .map(vacante -> {
                    VacanteListasDto dto = new VacanteListasDto();
                    return dto.convertToVacanteListasDto(vacante);
                })
                .collect(Collectors.toList());

            return ResponseEntity.ok(vacanteDto);
        }

        // Mensaje como JSON
        Map<String, String> response = Map.of("mensaje", "No se han encontrado vacantes asociadas");
        return ResponseEntity.ok(response);
    }


    @GetMapping("/vacantes/{id}")
    public ResponseEntity<VacanteListasDto> verDetallesDeVacante(@PathVariable int id) {
        VacanteListasDto vacante = vacanteService.verVacante(id);
        if (vacante == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(vacante);
    }

    @GetMapping("/verCreadaEmpresa/{idEmpresa}")
    public ResponseEntity<?> verCreadasEmpresa(@PathVariable int idEmpresa, HttpServletRequest request) {

        List<Vacante> listaux = vacanteService.buscarPorCreadaYEmpresa(idEmpresa);

        if (!listaux.isEmpty()) {
            List<VacanteListasDto> vacanteDto = listaux.stream()
                .map(vacante -> {
                    VacanteListasDto dto = new VacanteListasDto();
                    return dto.convertToVacanteListasDto(vacante);
                })
                .collect(Collectors.toList());

            return new ResponseEntity<>(vacanteDto, HttpStatus.OK);
        }
        return new ResponseEntity<>("No se encuentran vacantes disponibles", HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/verCreadaCategoria/{idCategoria}")
    public ResponseEntity<?> verCreadasCategoria(@PathVariable int idCategoria, HttpServletRequest request) {

        List<Vacante> listaux = vacanteService.buscarPorCreadaYCategoria(idCategoria);

        if (!listaux.isEmpty()) {
            List<VacanteListasDto> vacanteDto = listaux.stream()
                .map(vacante -> {
                    VacanteListasDto dto = new VacanteListasDto();
                    return dto.convertToVacanteListasDto(vacante);
                })
                .collect(Collectors.toList());

            return new ResponseEntity<>(vacanteDto, HttpStatus.OK);
        }
        return new ResponseEntity<>("No se encuentran vacantes disponibles", HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/verSolicitudes")
    public ResponseEntity<?> verSolicitudes(HttpServletRequest request) {

        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        List<Solicitud> listaux = solicitudService.buscarSolicitudPorIdUsuario(email);

        if (!listaux.isEmpty()) {
            List<SolicitudListasDto> solicitudDto = listaux.stream()
                .map(solicitud -> {
                    SolicitudListasDto dto = new SolicitudListasDto();
                    return dto.convertToSolicitudDto(solicitud);
                })
                .collect(Collectors.toList());

            return ResponseEntity.ok(solicitudDto);
        }

        //Esto ahora devuelve un JSON en vez de texto plano
        Map<String, String> response = Map.of("mensaje", "No se encuentran vacantes disponibles");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    @PostMapping("/postularVacante/{idVacante}")
    public ResponseEntity<?> postularVacante(@PathVariable int idVacante, @RequestBody SolicitudAltaDto altaDto, HttpServletRequest request) {

        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        Usuario usuarioPostulante = usuarioService.buscarPorEmailEntidad(email);
        Vacante vacanteSolicitud = vacanteService.buscarUno(idVacante);

        if (vacanteSolicitud == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("mensaje", "La vacante no existe"));
        }
        //Validación: comprobar si ya existe una solicitud para esta vacante por el mismo usuario
        Solicitud existente = solicitudService.buscarSolicitudPorIdVacanteYEmail(idVacante, email);
        if (existente != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("mensaje", "Ya has solicitado esta vacante anteriormente"));
        }
        Solicitud solicitudPostulante = new Solicitud();
        solicitudPostulante.setUsuario(usuarioPostulante);
        solicitudPostulante.setVacante(vacanteSolicitud);
        solicitudPostulante.setFecha(new Date());
        solicitudPostulante.setArchivo(altaDto.getArchivo());
        solicitudPostulante.setComentarios(altaDto.getComentarios());
        solicitudPostulante.setEstado(false);
        solicitudPostulante.setCurriculum(altaDto.getCurriculum());

        solicitudService.insertUno(solicitudPostulante);

        return ResponseEntity.ok(Map.of("mensaje", "Solicitud creada con éxito"));
    }

    
    @DeleteMapping("/solicitud/eliminar/{id}")
    public ResponseEntity<?> eliminarSolicitud(@PathVariable int id) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        if (!solicitudService.esDueñoDeSolicitud(email, id)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("mensaje", "No tienes permiso para eliminar esta solicitud."));
        }

        int eliminado = solicitudService.eliminarUno(id);

        if (eliminado > 0) {
            return ResponseEntity.ok(Map.of("mensaje", "Solicitud eliminada correctamente."));
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("mensaje", "No se pudo eliminar la solicitud."));
        }
    }

}
