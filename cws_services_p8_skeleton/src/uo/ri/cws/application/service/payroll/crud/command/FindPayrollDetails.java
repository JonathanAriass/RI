package uo.ri.cws.application.service.payroll.crud.command;

import java.util.Optional;

import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.PayrollRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.payroll.PayrollService.PayrollBLDto;
import uo.ri.cws.application.service.payroll.assembler.PayrollAssembler;
import uo.ri.cws.application.util.command.Command;
import uo.ri.util.assertion.ArgumentChecks;

public class FindPayrollDetails implements Command<Optional<PayrollBLDto>> {

	private String id = "";
	private static PayrollRepository repoPayrolls = Factory.repository.forPayroll();

	public FindPayrollDetails(String id) {
		ArgumentChecks.isNotBlank(id);
		ArgumentChecks.isNotBlank(id);
		ArgumentChecks.isNotNull(id);

		this.id = id;
	}

	@Override
	public Optional<PayrollBLDto> execute() throws BusinessException {
		if (repoPayrolls.findById(id).isEmpty()) {
			return Optional.empty();
		}

		return Optional.of(
				PayrollAssembler.toBLDto(repoPayrolls.findById(id).get()));
	}

//	private void check(Optional<Payroll> p) throws BusinessException {
//		BusinessChecks.isNotNull(p, "Payroll does not exist");
//		BusinessChecks.isTrue(p.isPresent(), "Payroll is not present");
//	}

}
