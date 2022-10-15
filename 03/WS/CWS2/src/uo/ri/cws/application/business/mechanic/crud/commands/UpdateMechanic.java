package uo.ri.cws.application.business.mechanic.crud.commands;

import assertion.Argument;
import uo.ri.cws.application.business.BusinessException;
import uo.ri.cws.application.business.mechanic.MechanicService.MechanicBLDto;
import uo.ri.cws.application.business.mechanic.assembler.MechanicAssembler;
import uo.ri.cws.application.business.util.command.Command;
import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.PersistenceFactory;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway;

public class UpdateMechanic implements Command<Void> {

		private MechanicGateway mg = PersistenceFactory.forMechanic();
		
		private MechanicBLDto mechanic = null;
		
		public UpdateMechanic(MechanicBLDto arg) {
			validate(arg);
			this.mechanic = arg;
		}
		
		public Void execute() throws BusinessException {
			if (!existMechanic(mechanic.id)) {
				throw new BusinessException("Repeated mechanic");
			}
			mg.update(MechanicAssembler.toDALDto(mechanic));

			return null;
		}
		
		private boolean existMechanic(String id) throws PersistenceException {
			if (mg.findById(id).isPresent()) {
				return true;
			} else {
				return false;
			}
		}

		private void validate(MechanicBLDto arg) {
			// usar clase del proyecto util Argument
			Argument.isNotNull(arg, "Null mechanic");
			Argument.isNotEmpty(arg.id, "Null or empty id");
			Argument.isNotEmpty(arg.name, "Null or empty name");
			Argument.isNotEmpty(arg.surname, "Null or empty surname");
			
		}
	
}
