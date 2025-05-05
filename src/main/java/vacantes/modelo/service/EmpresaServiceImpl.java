package vacantes.modelo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vacantes.modelo.entities.Empresa;
import vacantes.modelo.entities.Usuario;
import vacantes.repository.EmpresaRepository;

@Service
public class EmpresaServiceImpl implements EmpresaService{

	@Autowired
	private EmpresaRepository empresaRepository;

	@Override
	public List<Empresa> buscarTodo() {
		return empresaRepository.findAll();
	}

	@Override
	public Empresa buscarUno(Integer indx) {
		return empresaRepository.findById(indx).orElse(null);
	}

	@Override
	public boolean insertUno(Empresa ele) {
		try {
			empresaRepository.save(ele);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}return false;
	}

	@Override
	public int eliminarUno(Integer indx) {
		try {
			if(empresaRepository.existsById(indx)) {
				empresaRepository.deleteById(indx);
			}
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
		}return 0;
	}

	@Override
	public int modificarUno(Empresa ele) {
		try {
			empresaRepository.save(ele);
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
		}return 0;
	}

	@Override
	public Empresa buscarEmpresaPorEmail(String email) {
		Empresa empresaBuscada = empresaRepository.findEmpresaByEmail(email);
		return empresaBuscada !=null ? empresaBuscada:null;
	}

	@Override
	public Empresa buscarEmpresaPorId(int idEmpresa) {
		return empresaRepository.findEmpresaByIdEmpresa(idEmpresa);
	}

	@Override
	public Empresa buscarEmpresaPorUsuario(Usuario usuario) {
		return empresaRepository.findByUsuario(usuario);
	}

}
