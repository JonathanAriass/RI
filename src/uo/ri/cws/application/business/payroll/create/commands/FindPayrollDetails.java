package uo.ri.cws.application.business.payroll.create.commands;

import java.util.Optional;

import assertion.Argument;
import uo.ri.cws.application.business.BusinessException;
import uo.ri.cws.application.business.payroll.PayrollService.PayrollBLDto;
import uo.ri.cws.application.business.payroll.assembler.PayrollAssembler;
import uo.ri.cws.application.business.util.command.Command;
import uo.ri.cws.application.persistence.PersistenceFactory;
import uo.ri.cws.application.persistence.payroll.PayrollGateway;

public class FindPayrollDetails implements Command<Optional<PayrollBLDto>> {

	private String payrollId = "";
	private PayrollGateway pg = PersistenceFactory.forPayroll();
	
	public FindPayrollDetails(String arg) {
		validate(arg);
		this.payrollId = arg;
	}
	
	@Override
	public Optional<PayrollBLDto> execute() throws BusinessException {
		
		return PayrollAssembler.toBLDto(pg.findById(payrollId));
	}

	private void validate(String arg) {
		// usar clase del proyecto util Argumen
		Argument.isNotEmpty(arg, "Null or empty id");
	}
	
}
