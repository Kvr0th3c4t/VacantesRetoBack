package vacantes.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public interface IGenericoCRUD <Elemento, Indice>{
	List<Elemento> buscarTodo ();
	Elemento buscarUno(Indice indx);
	boolean insertUno(Elemento ele);
	int eliminarUno(Indice indx);
	int modificarUno (Elemento ele);
}