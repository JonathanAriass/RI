package uo.ri.cws.application.business.mechanic.crud.commands;

import java.util.Optional;

import assertion.Argument;
import uo.ri.cws.application.business.BusinessException;
import uo.ri.cws.application.business.mechanic.MechanicService.MechanicBLDto;
import uo.ri.cws.application.business.mechanic.assembler.MechanicAssembler;
import uo.ri.cws.application.business.util.command.Command;
import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.PersistenceFactory;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway;

public class FindMechanicByDni implements Command<Optional<MechanicBLDto>> {
	
	private String dni = "";
	private MechanicGateway mg = PersistenceFactory.forMechanic();
	
	public FindMechanicByDni(String arg) {
		validate(arg);
		this.dni = arg;
	}
	
	public Optional<MechanicBLDto> execute() throws BusinessException {
		Optional<MechanicBLDto> result = null;

		if (!existMechanic(dni)) {
			throw new BusinessException("Mechanic does not exist");
		}
		result = findMechanicByDni(dni);
		return result ;

	}
	
	
	private Optional<MechanicBLDto> findMechanicByDni(String dni) throws PersistenceException {
		return MechanicAssembler.toBLDto(mg.findByDni(dni)) ;
	}

	// Throw business exception if there is a mechanic with this DNI in DDBB
	private boolean existMechanic(String dni) throws PersistenceException {
		if (mg.findByDni(dni).isPresent()) {
			return true;
		} else {
			return false;
		}
	}
		
	private void validate(String arg) {
		// usar clase del proyecto util Argumen
		Argument.isNotEmpty(arg, "Null or empty dni");
	}
}
