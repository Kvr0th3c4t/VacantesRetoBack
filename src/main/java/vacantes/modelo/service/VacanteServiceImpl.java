
package vacantes.modelo.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vacantes.modelo.dto.VacanteListasDto;
import vacantes.modelo.entities.Estatus;
import vacantes.modelo.entities.Vacante;
import vacantes.repository.VacanteRepository;

@Service
public class VacanteServiceImpl implements VacanteService{
	
	@Autowired
	private VacanteRepository vacanteRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public Vacante insertVacante(Vacante vacante) {
		try {
			if (vacanteRepository.existsById(vacante.getIdVacante()))
				return null;
			else {
				return vacanteRepository.save(vacante);
			}
		} catch (Exception e) {
			return null;
		}
	}
	@Override
	public VacanteListasDto verVacante(int idVancante) {
		
	    Vacante vacante = vacanteRepository.buscarPorId(idVancante);
	    

	    if (vacante != null) {
	        return modelMapper.map(vacante, VacanteListasDto.class);
	    }
	    
	    return null; 
	}
	
	
	@Override
	public VacanteListasDto updateVacante(VacanteListasDto vacanteDto) {
		 try {
		        Vacante vacante = modelMapper.map(vacanteDto, Vacante.class); 

		        if (vacanteRepository.existsById(vacante.getIdVacante())) {
		            
		            Vacante updatedVacante = vacanteRepository.save(vacante);

		            
		            return modelMapper.map(updatedVacante, VacanteListasDto.class);
		        }
		    } catch (Exception e) {
		        e.printStackTrace();
		    }
		    return null; 
	}

	@Override
	public Vacante cancelVacante(int idVacante) {

		Vacante vacante = vacanteRepository.buscarPorId(idVacante);
		
		if (vacante != null) {
		vacante.setEstatus(Estatus.CANCELADA);
		
		return vacanteRepository.save(vacante);
		
		}else {
			return null;
		}
	}

	@Override
	public List<VacanteListasDto> todas() {
		List<Vacante> vacantes = vacanteRepository.findAll();

	   
	    return vacantes.stream()
	            .map(vacante -> modelMapper.map(vacante, VacanteListasDto.class))
	            .collect(Collectors.toList());
	}
	
	//Métodos CRUD con IGenericoCRUD
	@Override
	public List<Vacante> buscarTodo() {
		return vacanteRepository.findAll();
	}
	@Override
	public Vacante buscarUno(Integer idVacante) {
		return vacanteRepository.findById(idVacante).orElse(null);
	}
	@Override
	public boolean insertUno(Vacante vacante) {
		try {
			vacanteRepository.save(vacante);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}return false;
	}
	@Override
	public int eliminarUno(Integer idVacante) {
		try {
			if(vacanteRepository.existsById(idVacante)) {
				vacanteRepository.deleteById(idVacante);
			}
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
		}return 0;
	}
	@Override
	public int modificarUno(Vacante vacante) {
		try {
			vacanteRepository.save(vacante);
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
		}return 0;
	}
	@Override
	public List<Vacante> buscarPorCreadaYEmpresa(int idEmpresa) {
	    return vacanteRepository.findByCreadaAndEmpresa(idEmpresa);
	}

	@Override
	public List<Vacante> buscarPorCreadaYCategoria(int idCategoria) {
	    return vacanteRepository.findByCreadaAndCategoria(idCategoria);
	}

	@Override
	public List<Vacante> buscarVacantePorEmpresa(int idEmpresa) {
	    return vacanteRepository.findVacanteByIdEmpresa(idEmpresa);
	}

	@Override
	public List<Vacante> buscarVacantePorCategoria(int idCategoria) {
	    return vacanteRepository.findVacanteByIdCategoria(idCategoria);
	}
	
	@Override
	public List<Vacante> buscarPorCreada() {
		return vacanteRepository.findByCreada();
	}
}
	
	
	
