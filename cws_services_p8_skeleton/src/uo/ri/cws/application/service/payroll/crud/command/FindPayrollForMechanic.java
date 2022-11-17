package uo.ri.cws.application.service.payroll.crud.command;

import java.util.List;
import java.util.Optional;

import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.MechanicRepository;
import uo.ri.cws.application.repository.PayrollRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.payroll.PayrollService.PayrollSummaryBLDto;
import uo.ri.cws.application.service.payroll.assembler.PayrollAssembler;
import uo.ri.cws.application.util.BusinessChecks;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Mechanic;
import uo.ri.util.assertion.ArgumentChecks;

public class FindPayrollForMechanic
		implements Command<List<PayrollSummaryBLDto>> {

	private String id = "";
	private static PayrollRepository repoPayrolls = Factory.repository.forPayroll();
	private static MechanicRepository repoMechanics = Factory.repository.forMechanic();

	public FindPayrollForMechanic(String id) {
		ArgumentChecks.isNotBlank(id);
		ArgumentChecks.isNotBlank(id);
		ArgumentChecks.isNotNull(id);

		this.id = id;
	}

	@Override
	public List<PayrollSummaryBLDto> execute() throws BusinessException {
		Optional<Mechanic> m = repoMechanics.findById(id);
		check(m);
		return PayrollAssembler.toBLDtoSummaryList(PayrollAssembler.toDtoList(
				repoPayrolls.findPayrollsForMechanicId(id)));
	}

	private void check(Optional<Mechanic> m) throws BusinessException {
		BusinessChecks.isNotNull(m, "Mechanic does not exist");
		BusinessChecks.isTrue(m.isPresent(), "Mechanic is not present");
	}

}
