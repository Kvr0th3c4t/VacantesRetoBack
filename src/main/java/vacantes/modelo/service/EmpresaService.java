package vacantes.modelo.service;

import vacantes.modelo.entities.Empresa;
import vacantes.modelo.entities.Usuario;
import vacantes.repository.IGenericoCRUD;


public interface EmpresaService extends IGenericoCRUD<Empresa, Integer>{
	Empresa buscarEmpresaPorEmail(String email);
	Empresa buscarEmpresaPorId(int idEmpresa);
	Empresa buscarEmpresaPorUsuario(Usuario usuario);
	}
