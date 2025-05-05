package vacantes.modelo.service;

import java.util.List;
import vacantes.modelo.dto.VacanteListasDto;
import vacantes.modelo.entities.Vacante;
import vacantes.repository.IGenericoCRUD;

public interface VacanteService extends IGenericoCRUD<Vacante, Integer>{
	Vacante insertVacante(Vacante vacante);
	VacanteListasDto verVacante(int idVacante);
	VacanteListasDto updateVacante(VacanteListasDto vacanteDto);
	Vacante cancelVacante(int idVacante);
	
	List<Vacante> buscarPorCreada();
	List<Vacante> buscarVacantePorEmpresa(int idEmpresa);
	List<Vacante> buscarPorCreadaYEmpresa(int idEmpresa);
	List<Vacante> buscarPorCreadaYCategoria(int idCategoria);
	List<Vacante> buscarVacantePorCategoria(int iCategoria);
	List<VacanteListasDto> todas();
}
