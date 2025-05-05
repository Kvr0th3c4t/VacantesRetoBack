package vacantes.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import vacantes.modelo.entities.Solicitud;

@Repository
public interface SolicitudRepository extends JpaRepository<Solicitud, Integer>{
	
	@Query("from Solicitud s where s.usuario.email = ?1")
	List<Solicitud> findSolicitudByEmail(String email);
	
	@Query("select s from Solicitud s where s.vacante.idVacante = ?1")
	public List<Solicitud> findSolicitudByIdVacante(int idVacante);
	
	@Query("select s from Solicitud s where s.vacante.idVacante =?1 and s.usuario.email =?2")
	public Solicitud findSolicitudByIdVacanteAndEmail(int idVacante, String email);
	
	@Query ("select s from Solicitud s where s.vacante.empresa.idEmpresa =?1")
	public List<Solicitud> findSolicitudByIdEmpresa(int idEmpresa);
	
	@Query("SELECT COUNT(s) > 0 FROM Solicitud s WHERE s.usuario.email = ?1 AND s.idSolicitud = ?2")
	boolean comprobarDueñoSolicitud(String email, int idSolicitud); 
}
