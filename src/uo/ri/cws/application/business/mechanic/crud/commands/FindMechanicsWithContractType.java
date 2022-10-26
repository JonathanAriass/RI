package uo.ri.cws.application.business.mechanic.crud.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import assertion.Argument;
import uo.ri.cws.application.business.BusinessException;
import uo.ri.cws.application.business.contracttype.ContractTypeService.ContractTypeBLDto;
import uo.ri.cws.application.business.contracttype.assembler.ContractTypeAssembler;
import uo.ri.cws.application.business.mechanic.MechanicService.MechanicBLDto;
import uo.ri.cws.application.business.mechanic.assembler.MechanicAssembler;
import uo.ri.cws.application.business.professionalgroup.ProfessionalGroupService.ProfessionalGroupBLDto;
import uo.ri.cws.application.business.professionalgroup.assembler.ProfessionalGroupAssembler;
import uo.ri.cws.application.business.util.command.Command;
import uo.ri.cws.application.persistence.PersistenceFactory;
import uo.ri.cws.application.persistence.contract.ContractGateway;
import uo.ri.cws.application.persistence.contracttype.ContractTypeGateway;

import uo.ri.cws.application.persistence.mechanic.MechanicGateway;

public class FindMechanicsWithContractType implements Command<List<MechanicBLDto>> {

	private MechanicGateway mg = PersistenceFactory.forMechanic();
	private ContractGateway cg = PersistenceFactory.forContract();
	private ContractTypeGateway ctg = PersistenceFactory.forContractType();
	
	private String name = "";
	
	public FindMechanicsWithContractType(String name) {
		validate(name);
		this.name = name;
	}
	
	@Override
	public List<MechanicBLDto> execute() throws BusinessException {
		List<MechanicBLDto> result = new ArrayList<MechanicBLDto>();
		
		/// Obtener id del contract type
		String contractTypeId;
		Optional<ContractTypeBLDto> typeId = ContractTypeAssembler.toBLDto(ctg.findContractTypeIdByName(name));
		if (typeId.isPresent()) {
			contractTypeId = typeId.get().id;
		} else {
			return result;
		}
		
		// Recorrer la lista de contratos en busqueda de los ids de los mecanicos
		List<String> mechanic_ids = getMechanicsId(contractTypeId);
		
		// Obtenemos los mechanicos a traves de los ids
		result = getMechanics(mechanic_ids); 
		
		return result;
		
		// Sacar id del contract type a traves del nombre
		
		// Sacar los contratos in force
		
		// Sacar los mecanicos
	}
	
	private List<String> getMechanicsId(String id) {
		List<String> result = new ArrayList<>();
		
		result = cg.findMechanicsIdWithContractType(id);
		
		return result;
	}
	
	private List<MechanicBLDto> getMechanics(List<String> ids) {
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
