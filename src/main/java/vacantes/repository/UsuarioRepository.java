package vacantes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vacantes.modelo.entities.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, String>{

	Usuario findByEmail(String email);
	boolean existsByEmail(String email);
}
