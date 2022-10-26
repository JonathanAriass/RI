package uo.ri.cws.application.business.mechanic.crud.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import assertion.Argument;
import uo.ri.cws.application.business.BusinessException;
import uo.ri.cws.application.business.mechanic.MechanicService.MechanicBLDto;
import uo.ri.cws.application.business.mechanic.assembler.MechanicAssembler;
import uo.ri.cws.application.business.professionalgroup.ProfessionalGroupService.ProfessionalGroupBLDto;
import uo.ri.cws.application.business.professionalgroup.assembler.ProfessionalGroupAssembler;
import uo.ri.cws.application.business.util.command.Command;
import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.PersistenceFactory;
import uo.ri.cws.application.persistence.contract.ContractGateway;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway;
import uo.ri.cws.application.persistence.professionalgroup.ProfessionalGroupGateway;

public class FindMechanicsByProfessionalGroup implements Command<List<MechanicBLDto>> {

	private MechanicGateway mg = PersistenceFactory.forMechanic();
	private ContractGateway cg = PersistenceFactory.forContract();
	private ProfessionalGroupGateway pg = PersistenceFactory.forProfessionalGroup();
	
	private String name = "";
	
	public FindMechanicsByProfessionalGroup(String name) {
		validate(name);
		this.name = name;
	}
	
	@Override
	public List<MechanicBLDto> execute() throws BusinessException {
		List<MechanicBLDto> result = new ArrayList<MechanicBLDto>();
		
		// Obtenemos el id del professional group por nombre
		String groupId;
		Optional<ProfessionalGroupBLDto> group = ProfessionalGroupAssembler.toBLDto(pg.findByName(name));
		if (group.isPresent()) {
			groupId = group.get().id;
		} else {
			return result;
		}
		
		// Obtener lista de mechanicos a traves de los ids y el nombre del pg
		
		
		List<String> mechanic_ids = getMechanicsId(groupId);
		
		// Obtenemos los mechanicos a traves de los ids
		result = getMechanics(mechanic_ids); 
				
		return result;
	}
	
	private List<String> getMechanicsId(String id) throws PersistenceException {
		List<String> result = new ArrayList<>();
		
		result = cg.findMechanicsForProfessionalGroupsName(id);
		
		return result;
	}
	
	private List<MechanicBLDto> getMechanics(List<String> ids) throws PersistenceException {
		List<MechanicBLDto> result = new ArrayList<MechanicBLDto>();
		
		for (String id : ids) {
			result.add(MechanicAssembler.toBLDto(mg.findById(id)).get());
		}
		
		return result;
	}
	
	private void validate(String arg) {
		// usar clase del proyecto util Argument
		Argument.isNotEmpty(arg, "Null or empty name");

	}
	
}
