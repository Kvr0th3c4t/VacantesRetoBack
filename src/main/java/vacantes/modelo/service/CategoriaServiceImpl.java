package vacantes.modelo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vacantes.modelo.entities.Categoria;
import vacantes.repository.CategoriaRepository;

@Service
public class CategoriaServiceImpl implements CategoriaService{
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@Override
	public List<Categoria> buscarTodo() {
		return categoriaRepository.findAll();
	}

	@Override
	public Categoria buscarUno(Integer indx) {
		return categoriaRepository.findById(indx).orElse(null);
	}

	@Override
	public boolean insertUno(Categoria ele) {
		try {
			categoriaRepository.save(ele);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}return false;
	}

	@Override
	public int eliminarUno(Integer indx) {
		try {
			if(categoriaRepository.existsById(indx)) {
				categoriaRepository.deleteById(indx);
			}return 1;
		} catch (Exception e) {
			e.printStackTrace();
		}return 0;
	}

	@Override
	public int modificarUno(Categoria ele) {
		try {
			categoriaRepository.save(ele);
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
		}return 0;
	}

}
