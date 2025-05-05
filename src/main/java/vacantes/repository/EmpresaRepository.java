package vacantes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import vacantes.modelo.entities.Empresa;
import vacantes.modelo.entities.Usuario;

@Repository
public interface EmpresaRepository extends JpaRepository<Empresa, Integer>{
	
	@Query("select e from Empresa e where e.usuario.email =?1")
	Empresa findEmpresaByEmail(String email);
	
	@Query("select e from Empresa e where e.usuario.email =?1")
	Empresa findEmpresaByIdEmpresa(int idEmpresa);
	
	Empresa findByUsuario(Usuario usuario);	
}
