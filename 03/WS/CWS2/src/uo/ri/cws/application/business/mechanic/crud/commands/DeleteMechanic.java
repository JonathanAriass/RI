package uo.ri.cws.application.business.mechanic.crud.commands;

import assertion.Argument;
import uo.ri.cws.application.business.BusinessException;
import uo.ri.cws.application.business.util.command.Command;
import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.PersistenceFactory;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway;
import uo.ri.cws.application.persistence.workorder.WorkOrderGateway;

public class DeleteMechanic implements Command<Void> {
	

	private MechanicGateway mg = PersistenceFactory.forMechanic();
	private WorkOrderGateway wg = PersistenceFactory.forWorkOrder();
	private String id = "";
	
	public DeleteMechanic(String id) {
		validate(id);
		this.id = id;
	}

	public Void execute() throws BusinessException {
				// Check if mechanic exist with this id
				if (existMechanic(id)) {
					throw new BusinessException("Repeated mechanic");
				}
				if (!notWorkorders(id)) {
					throw new BusinessException("Repeated mechanic");
				}
				mg.remove(id);
				return null;
	}

	private boolean existMechanic(String id) throws PersistenceException {
		if (mg.findById(id).isPresent()) {
			return true;
		} else {
			return false;
		}
	}
	
	private boolean notWorkorders(String id) throws PersistenceException {
		if (wg.findByMechanic(id).isEmpty()) {
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
