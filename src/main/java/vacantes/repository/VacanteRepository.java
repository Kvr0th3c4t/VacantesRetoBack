package vacantes.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import vacantes.modelo.entities.Vacante;

@Repository
public interface VacanteRepository extends JpaRepository<Vacante, Integer>{


	@Query("from Vacante v where v.estatus = 'CREADA' and v.empresa.idEmpresa = ?1")
	List<Vacante> findByCreadaAndEmpresa(int idEmpresa);
	
	@Query("from Vacante v where v.estatus = 'CREADA' and v.categoria.idCategoria = ?1")
	List<Vacante> findByCreadaAndCategoria(int idCategoria);
	
	@Query("select v from Vacante v where v.estatus = 'CREADA'")
	List<Vacante> findByCreada();
	
	@Query("select v from Vacante v where v.idVacante = ?1")
	public Vacante buscarPorId (int idVacante);
	
	@Query("select v from Vacante v where v.empresa.idEmpresa = ?1")
	public List<Vacante> findVacanteByIdEmpresa(int idEmpresa);
	
	@Query("select v from Vacante v where v.categoria.idCategoria = ?1")
	public List<Vacante> findVacanteByIdCategoria(int idCategoria);
	
}
