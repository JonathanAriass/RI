package uo.ri.cws.application.business.mechanic.crud.commands;


import java.util.Optional;

import assertion.Argument;
import uo.ri.cws.application.business.mechanic.MechanicService.MechanicBLDto;
import uo.ri.cws.application.business.mechanic.assembler.MechanicAssembler;
import uo.ri.cws.application.business.util.command.Command;
import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.PersistenceFactory;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway;

public class FindMechanicById implements Command<Optional<MechanicBLDto>> {
	
	private String id = "";
	private MechanicGateway mg = PersistenceFactory.forMechanic();
	
	public FindMechanicById(String arg) {
		validate(arg);
		this.id = arg;
	}
	
	public Optional<MechanicBLDto> execute() {
		Optional<MechanicBLDto> result = null;

		if (!existMechanic(id)) {
			return Optional.empty();
		}
		result = findMechanicById(id);
		return result ;

	}
	
	
	private Optional<MechanicBLDto> findMechanicById(String id) throws PersistenceException {
		return MechanicAssembler.toBLDto(mg.findById(id)) ;
	}

	// Throw business exception if there is a mechanic with this DNI in DDBB
	private boolean existMechanic(String id) throws PersistenceException {
		if (mg.findById(id).isPresent()) {
			return true;
		} else {
			return false;
		}
	}
		
	private void validate(String arg) {
		// usar clase del proyecto util Argumen
		Argument.isNotEmpty(arg, "Null or empty id");
	}
	
}
