package uo.ri.cws.application.business.professionalgroup.crud.commands;

import assertion.Argument;
import uo.ri.cws.application.business.BusinessException;
import uo.ri.cws.application.business.professionalgroup.ProfessionalGroupService.ProfessionalGroupBLDto;
import uo.ri.cws.application.business.professionalgroup.assembler.ProfessionalGroupAssembler;
import uo.ri.cws.application.business.util.command.Command;
import uo.ri.cws.application.persistence.PersistenceFactory;
import uo.ri.cws.application.persistence.contract.ContractGateway;
import uo.ri.cws.application.persistence.professionalgroup.ProfessionalGroupGateway;

public class DeleteProfessionalGroup implements Command<Void> {

	private String name= "";
	private ProfessionalGroupGateway pg = PersistenceFactory.forProfessionalGroup();
	private ContractGateway cg = PersistenceFactory.forContract();
	
	public DeleteProfessionalGroup(String name) {
		validate(name);
		this.name = name;
	}
	
	@Override
	public Void execute() throws BusinessException {
		
		// check if professional group exists
		if (!existProfessionalGroup(this.name)) {
			throw new BusinessException("Professional group doesn't exist");
		}
		
		ProfessionalGroupBLDto prgr = ProfessionalGroupAssembler.toBLDto(pg.findByName(this.name)).get();

		
		if (hasContractAssigned(prgr.id)) {
			throw new BusinessException("Professional group has contract assigned");
		}
		
		pg.remove(prgr.id);
		
		return null;
	}

	private boolean existProfessionalGroup(String name) {
		if (pg.findByName(name).isPresent()) {
			return true;
		}
		return false;
	}
	
	private boolean hasContractAssigned(String arg) {
		if (cg.findByProfessionalGroupId(arg).isPresent()) {
			return true;
		}
		
		return false;
	}
	
	private void validate(String arg) {
		// usar clase del proyecto util Argument
		Argument.isNotEmpty(arg, "Null or empty name");

	}

}
