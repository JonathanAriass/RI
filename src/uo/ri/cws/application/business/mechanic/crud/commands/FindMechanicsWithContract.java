package uo.ri.cws.application.business.mechanic.crud.commands;

import java.util.ArrayList;
import java.util.List;

import uo.ri.cws.application.business.BusinessException;
import uo.ri.cws.application.business.mechanic.MechanicService.MechanicBLDto;
import uo.ri.cws.application.business.mechanic.assembler.MechanicAssembler;
import uo.ri.cws.application.business.util.command.Command;
import uo.ri.cws.application.persistence.PersistenceFactory;
import uo.ri.cws.application.persistence.contract.ContractGateway;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway;

public class FindMechanicsWithContract implements Command<List<MechanicBLDto>> {
		
	private MechanicGateway mg = PersistenceFactory.forMechanic();
	private ContractGateway cg = PersistenceFactory.forContract();
	
	@Override
	public List<MechanicBLDto> execute() throws BusinessException {
		List<MechanicBLDto> result = new ArrayList<MechanicBLDto>();
		
		// Recorrer la lista de contratos en busqueda de los ids de los mecanicos
		List<String> mechanic_ids = getMechanicsId();
		
		// Obtenemos los mechanicos a traves de los ids
		result = getMechanics(mechanic_ids); 
		
		return result;
	}

	private List<String> getMechanicsId() {
		List<String> result = new ArrayList<>();
		
		result = cg.findMechanicsIdWithContract();
		
		return result;
	}
	
	private List<MechanicBLDto> getMechanics(List<String> ids) {
		List<MechanicBLDto> result = new ArrayList<MechanicBLDto>();
		
		for (String id : ids) {
			result.add(MechanicAssembler.toBLDto(mg.findById(id)).get());
		}
		
		return result;
	}
	
}
