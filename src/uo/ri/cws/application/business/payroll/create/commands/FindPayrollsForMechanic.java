package uo.ri.cws.application.business.payroll.create.commands;

import java.util.List;

import assertion.Argument;
import uo.ri.cws.application.business.BusinessException;
import uo.ri.cws.application.business.payroll.PayrollService.PayrollSummaryBLDto;
import uo.ri.cws.application.business.util.command.Command;
import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.PersistenceFactory;
import uo.ri.cws.application.persistence.contract.ContractGateway;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway;
import uo.ri.cws.application.persistence.payroll.PayrollGateway;

public class FindPayrollsForMechanic implements Command<List<PayrollSummaryBLDto>> {

	
	private String mechanicId = "";
	private PayrollGateway pg = PersistenceFactory.forPayroll();
	private ContractGateway cg = PersistenceFactory.forContract();
	private MechanicGateway mg = PersistenceFactory.forMechanic();
	
	public FindPayrollsForMechanic(String arg) {
		validate(arg);
		this.mechanicId = arg;
	}
	
	@Override
	public List<PayrollSummaryBLDto> execute() throws BusinessException {
		if (!existMechanic(mechanicId)) {
			throw new BusinessException("Mechanic doesn't exist");
		}
		
		// sacar id del contrato para el mecanico y hacer busqueda de payrolls
		String contractId = "";
		if (cg.findByMechanic(mechanicId).isPresent()) {
			contractId = cg.findByMechanic(mechanicId).get().id;			
		}
		
		return null;
	}
	
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
