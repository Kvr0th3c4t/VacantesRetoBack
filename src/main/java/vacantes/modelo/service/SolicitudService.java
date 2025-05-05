package vacantes.modelo.service;

import java.util.List;
import vacantes.modelo.entities.Solicitud;
import vacantes.repository.IGenericoCRUD;


public interface SolicitudService extends IGenericoCRUD<Solicitud, Integer>{
	
	Solicitud asignarSolicitud(int idSolicitud);
	List<Solicitud> buscarSolicitudPorIdUsuario(String email);
	List<Solicitud> buscarSolicitudPorIdVacante(int idVacante);
	public List<Solicitud> buscarSolicitudPorEmpresa(int idEmpresa);
	Solicitud buscarSolicitudPorIdVacanteYEmail(int idVacante, String email);
	boolean esDueñoDeSolicitud(String email, int idSolicitud); 
}
