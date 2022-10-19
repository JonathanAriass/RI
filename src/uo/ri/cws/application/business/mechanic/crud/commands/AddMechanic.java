package uo.ri.cws.application.business.mechanic.crud.commands;

import java.util.UUID;

import assertion.Argument;
import uo.ri.cws.application.business.BusinessException;
import uo.ri.cws.application.business.mechanic.MechanicService.MechanicBLDto;
import uo.ri.cws.application.business.mechanic.assembler.MechanicAssembler;
import uo.ri.cws.application.business.util.command.Command;
import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.PersistenceFactory;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway.MechanicDALDto;


public class AddMechanic implements Command<MechanicBLDto> {

	// Codigo Business
	private MechanicBLDto mechanic = null;
	private MechanicGateway mg = PersistenceFactory.forMechanic();
	
	public AddMechanic(MechanicBLDto arg) {
		validate(arg);
		mechanic = arg;
	}
	

	public MechanicBLDto execute() throws BusinessException {
				// Check UNIQUE DNI
				if (existMechanic(mechanic.dni)) {
					throw new BusinessException("Repeated mechanic");
				}
				insertMechanic();
				
				return mechanic;
	}

	private void insertMechanic() throws PersistenceException {
		// DTO completed
		mechanic.id = UUID.randomUUID().toString();
		mechanic.version = 1L;
		
		MechanicDALDto  m = MechanicAssembler.toDALDto(mechanic);
		mg.add(m);
	}

	// Throw business exception if there is a mechanic with this DNI in DDBB
	private boolean existMechanic(String dni) throws PersistenceException {
		if (mg.findByDni(dni).isPresent()) {
			return true;
		} else {
			return false;
		}
	}

	private void validate(MechanicBLDto arg) {
		// usar clase del proyecto util Argumen
		Argument.isNotNull(arg, "Null mechanic");
		Argument.isNotEmpty(arg.dni, "Null or empty dni");
		Argument.isNotEmpty(arg.name, "Null or empty name");
		Argument.isNotEmpty(arg.surname, "Null or empty surname");
		
	}
	
}
