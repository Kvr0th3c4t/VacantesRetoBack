package vacantes.restcontroller;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import vacantes.modelo.dto.EmpresaModificarDto;
import vacantes.modelo.dto.SolicitudListasDto;
import vacantes.modelo.dto.VacanteAltaDto;
import vacantes.modelo.dto.VacanteDetalleDto;
import vacantes.modelo.dto.VacanteListasDto;
import vacantes.modelo.dto.VacanteModificarDto;
import vacantes.modelo.entities.Categoria;
import vacantes.modelo.entities.Empresa;
import vacantes.modelo.entities.Estatus;
import vacantes.modelo.entities.Solicitud;
import vacantes.modelo.entities.Vacante;
import vacantes.modelo.service.CategoriaService;
import vacantes.modelo.service.EmpresaService;
import vacantes.modelo.service.SolicitudService;
import vacantes.modelo.service.VacanteService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/empresa")
public class EmpresaRestController {

	@Autowired
	private EmpresaService empresaService;

	@Autowired
	private VacanteService vacanteService;

	@Autowired
	private SolicitudService solicitudService;
	
	@Autowired
	private CategoriaService categoriaService;
	
    @GetMapping("/verTodasVacantes")
    public ResponseEntity<?> verTodasVacantes() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Empresa empresa = empresaService.buscarEmpresaPorEmail(email);

        List<Vacante> vacantes = vacanteService.buscarVacantePorEmpresa(empresa.getIdEmpresa());

        List<VacanteListasDto> vacantesDto = vacantes.stream()
            .map(vacante -> {
                VacanteListasDto dto = new VacanteListasDto();
                return dto.convertToVacanteListasDto(vacante);
            })
            .collect(Collectors.toList());

        return new ResponseEntity<>(vacantesDto, HttpStatus.OK);
    }
	
    @PutMapping("/modificarEmpresa")
    public ResponseEntity<?> modificarEmpresa(@RequestBody EmpresaModificarDto empresaDto) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Empresa empresaActual = empresaService.buscarEmpresaPorEmail(email);

        empresaActual.setNombreEmpresa(empresaDto.getNombre());
        empresaActual.setDireccionFiscal(empresaDto.getDireccionFiscal());
        empresaActual.setPais(empresaDto.getPais());

        if (empresaService.modificarUno(empresaActual) == 1) {
            Map<String, String> response = Map.of("mensaje", "La empresa se modificó correctamente");
            return ResponseEntity.ok(response);
        } else {
            Map<String, String> response = Map.of("mensaje", "La empresa no se ha podido modificar");
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).body(response);
        }
    }

    @PostMapping("/altaVacante")
    public ResponseEntity<?> altaVacante(@RequestBody VacanteAltaDto altaDto) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Empresa empresaB = empresaService.buscarEmpresaPorEmail(email);
        Categoria categoriaBuscada = categoriaService.buscarUno(altaDto.getIdCategoria());

        if (categoriaBuscada == null) {
            return new ResponseEntity<>("La categoría no existe", HttpStatus.INTERNAL_SERVER_ERROR);
        } else {
            Vacante nuevaVacante = new Vacante();
            nuevaVacante.setEmpresa(empresaB);
            nuevaVacante.setCategoria(categoriaBuscada);
            nuevaVacante.setNombre(altaDto.getNombre());
            nuevaVacante.setDescripcion(altaDto.getDescripcion());
            nuevaVacante.setFecha(new Date());
            nuevaVacante.setEstatus(Estatus.CREADA);
            nuevaVacante.setSalario(altaDto.getSalario());
            nuevaVacante.setDetalles(altaDto.getDetalles());
            nuevaVacante.setImagen(altaDto.getImagen());

            vacanteService.insertUno(nuevaVacante);
            return new ResponseEntity<>(nuevaVacante, HttpStatus.CREATED);
        }
    }
    
	@PutMapping("/eliminarVacante/{idVacante}")
	public ResponseEntity<?> eliminar(@PathVariable int idVacante) {

		vacanteService.cancelVacante(idVacante);
		return ResponseEntity.ok(Map.of("mensaje", "Vacante eliminada correctamente."));
	}

	@PutMapping("/modificarVacante/{idVacante}")
	public ResponseEntity<?> modificarVacante(@PathVariable int idVacante, @RequestBody VacanteModificarDto modificarDto) {
		Vacante vacanteMod = vacanteService.buscarUno(idVacante);

		//vacanteMod.setIdVacante(idVacante);
		vacanteMod.setNombre(modificarDto.getNombre());
		vacanteMod.setDescripcion(modificarDto.getDescripcion());
		vacanteMod.setSalario(modificarDto.getSalario());
		vacanteMod.setEstatus(modificarDto.getEstatus());
		vacanteMod.setImagen(modificarDto.getImagen());
		vacanteMod.setDetalles(modificarDto.getDetalles());

		if (vacanteService.modificarUno(vacanteMod) == 1) {
			return new ResponseEntity<>(vacanteMod, HttpStatus.OK);
		} else {
			return new ResponseEntity<>("La vacante no se ha podido modificar", HttpStatus.NOT_MODIFIED);
		}
	}

	@GetMapping("/detalleVacante/{idVacante}")
	public ResponseEntity<?> verDetalle(@PathVariable int idVacante) {

		Vacante vacanteBuscada = vacanteService.buscarUno(idVacante);

		if (vacanteBuscada != null) {
			VacanteDetalleDto vacanteDetalle = new VacanteDetalleDto();
			vacanteDetalle.convertToVacanteDetalleDto(vacanteBuscada);
			return new ResponseEntity<>(vacanteDetalle.convertToVacanteDetalleDto(vacanteBuscada), HttpStatus.OK);
		}

		return new ResponseEntity<>("La vacante no se ha podido encontrar", HttpStatus.FOUND);
	}
	
	@PutMapping("/asignarVacante/{idVacante}/{email}")
	public ResponseEntity<?> asignarCandidato(@PathVariable int idVacante, @PathVariable String email) {

		Vacante vacanteBuscada = vacanteService.buscarUno(idVacante);
		vacanteBuscada.setEstatus(Estatus.CUBIERTA);
		vacanteService.modificarUno(vacanteBuscada);

		Solicitud solicitudBuscada = solicitudService.buscarSolicitudPorIdVacanteYEmail(idVacante, email);
		solicitudBuscada.setEstado(true);
		solicitudService.modificarUno(solicitudBuscada);
		return new ResponseEntity<>("Solicitud asignada", HttpStatus.OK);
	}

	@GetMapping("/verTodasSolicitudes")
	public ResponseEntity<?> verTodasSolicitudes() {

		String email = SecurityContextHolder.getContext().getAuthentication().getName();

		Empresa empresa = empresaService.buscarEmpresaPorEmail(email);

		if (empresa != null) {
			List<Solicitud> solicitudes = solicitudService.buscarSolicitudPorEmpresa(empresa.getIdEmpresa());

			List<SolicitudListasDto> solicitudDto = solicitudes.stream()
				.map(solicitud -> {
					SolicitudListasDto dto = new SolicitudListasDto();
					return dto.convertToSolicitudDto(solicitud);
				})
				.collect(Collectors.toList());

			return new ResponseEntity<>(solicitudDto, HttpStatus.OK);
		}
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No autorizado");
	}

	@GetMapping("/verCV/{idSolicitud}")
	public ResponseEntity<?> verCV(@PathVariable int idSolicitud) {

		Solicitud solicitudBuscada = solicitudService.buscarUno(idSolicitud);
		return new ResponseEntity<>(solicitudBuscada.getCurriculum(), HttpStatus.OK);
	}

	@PutMapping("/aceptarSolicitud/{idSolicitud}")
	public ResponseEntity<?> aceptarSolicitud(@PathVariable int idSolicitud, HttpServletRequest request) {

		Solicitud solicitudBuscada = solicitudService.buscarUno(idSolicitud);
		if (solicitudBuscada != null) {
			solicitudBuscada.setEstado(true);
			solicitudService.modificarUno(solicitudBuscada);
			return ResponseEntity.ok(Collections.singletonMap("mensaje", "La solicitud se aceptó"));
		}
		return ResponseEntity.badRequest()
			.body(Collections.singletonMap("mensaje", "La solicitud no pudo aceptarse"));
	}

	@PutMapping("/denegarSolicitud/{idSolicitud}")
	public ResponseEntity<?> denegarSolicitud(@PathVariable int idSolicitud) {

		Solicitud solicitudBuscada = solicitudService.buscarUno(idSolicitud);
		if (solicitudBuscada != null) {
			solicitudBuscada.setEstado(false);
			solicitudService.modificarUno(solicitudBuscada);
			return ResponseEntity.ok(Collections.singletonMap("mensaje", "La solicitud se denegó"));
		}
		return ResponseEntity.badRequest()
			.body(Collections.singletonMap("mensaje", "La solicitud no pudo ser denegada"));
	}
	
	@PutMapping("/escribirComentario/{idSolicitud}")
	public ResponseEntity<?> escribirComentario(@RequestBody String comentarios, @PathVariable int idSolicitud) {

		Solicitud solicitudBuscada = solicitudService.buscarUno(idSolicitud);
		if (solicitudBuscada != null) {
			solicitudBuscada.setComentarios(comentarios);
			solicitudService.modificarUno(solicitudBuscada);
			return new ResponseEntity<>("Los comentarios se publicaron con éxito", HttpStatus.OK);
		}
		return new ResponseEntity<>("Los comentarios no se publicaron", HttpStatus.BAD_REQUEST);
	}
	
	
	@GetMapping("/solicitudesPorVacante/{idVacante}")
	public ResponseEntity<?> verSolicitudesPorVacante(@PathVariable int idVacante) {

		List<Solicitud> solicitudes = solicitudService.buscarSolicitudPorIdVacante(idVacante);

		List<SolicitudListasDto> solicitudesDto = solicitudes.stream()
			.map(solicitud -> {
				SolicitudListasDto dto = new SolicitudListasDto();
				return dto.convertToSolicitudDto(solicitud);
			})
			.collect(Collectors.toList());

		return new ResponseEntity<>(solicitudesDto, HttpStatus.OK);
	}
	
	@GetMapping("/getVacante/{idVacante}")
	public ResponseEntity<?> getVacanteById(@PathVariable int idVacante) {

		Vacante vacante = vacanteService.buscarUno(idVacante);
		if (vacante != null) {
			return new ResponseEntity<>(vacante, HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Vacante no encontrada", HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/verCategorias")
	public ResponseEntity<?> verCategorias() {
		List<Categoria> listCat = categoriaService.buscarTodo();
		if (!listCat.isEmpty()) {
			return new ResponseEntity<>(listCat, HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Error al ver categorías", HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/perfilEmpresa")
	public ResponseEntity<?> obtenerPerfilEmpresa() {

		String email = SecurityContextHolder.getContext().getAuthentication().getName();

		Empresa empresa = empresaService.buscarEmpresaPorEmail(email);

		if (empresa != null) {
			EmpresaModificarDto dto = new EmpresaModificarDto();
			dto.setNombre(empresa.getNombreEmpresa());
			dto.setDireccionFiscal(empresa.getDireccionFiscal());
			dto.setPais(empresa.getPais());

			return ResponseEntity.ok(dto);
		} else {
			Map<String, String> response = Map.of("mensaje", "Empresa no encontrada");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		}
	}

}
