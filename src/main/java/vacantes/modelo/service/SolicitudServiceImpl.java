package vacantes.modelo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vacantes.modelo.entities.Solicitud;
import vacantes.repository.SolicitudRepository;

@Service
public class SolicitudServiceImpl implements SolicitudService{
	
	
	@Autowired
	private SolicitudRepository solicitudRepository;
	
	@Override
	public Solicitud asignarSolicitud(int idSolicitud) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Solicitud> buscarTodo() {
		return solicitudRepository.findAll();
	}

	@Override
	public Solicitud buscarUno(Integer idSolicitud) {
		return solicitudRepository.findById(idSolicitud).orElse(null);
	}

	@Override
	public boolean insertUno(Solicitud solicitud) {
		try {
			solicitudRepository.save(solicitud);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}return false;
	}

	@Override
	public int eliminarUno(Integer idSolicitud) {
		try {
			if(solicitudRepository.existsById(idSolicitud)) {
				solicitudRepository.deleteById(idSolicitud);
			}
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
		}return 0;
	}

	@Override
	public int modificarUno(Solicitud solicitud) {
		try {
			solicitudRepository.save(solicitud);
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
		}return 0;
	}

	@Override
	public List<Solicitud> buscarSolicitudPorIdUsuario(String email) {
	    return solicitudRepository.findSolicitudByEmail(email);
	}

	@Override
	public List<Solicitud> buscarSolicitudPorIdVacante(int idVacante) {
	    return solicitudRepository.findSolicitudByIdVacante(idVacante);
	}

	@Override
	public Solicitud buscarSolicitudPorIdVacanteYEmail(int idVacante, String email) {
	    return solicitudRepository.findSolicitudByIdVacanteAndEmail(idVacante, email);
	}

	@Override
	public List<Solicitud> buscarSolicitudPorEmpresa(int idEmpresa) {
	    return solicitudRepository.findSolicitudByIdEmpresa(idEmpresa);
	}

	@Override
	public boolean esDueñoDeSolicitud(String email, int idSolicitud) {
		
		return solicitudRepository.comprobarDueñoSolicitud(email, idSolicitud);
	}

}
