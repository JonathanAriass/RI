package uo.ri.cws.application.service.payroll.crud.command;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.MechanicRepository;
import uo.ri.cws.application.repository.PayrollRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.payroll.PayrollService.PayrollBLDto;
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
		return buildSummaryPayroll(PayrollAssembler.toDtoList(
				repoPayrolls.findPayrollsForMechanicId(id)));
	}

	private List<PayrollSummaryBLDto> buildSummaryPayroll(
			List<PayrollBLDto> payrolls) {
		List<PayrollSummaryBLDto> aux = new ArrayList<PayrollSummaryBLDto>();

		for (PayrollBLDto p : payrolls) {
			aux.add(PayrollAssembler.toBLDtoSummary(p));
		}

		return aux;
	}

//	private PayrollSummaryBLDto buildSummaryPayroll(PayrollBLDto p) {
//		PayrollSummaryBLDto result = new PayrollSummaryBLDto();
//
//		result.id = p.id;
//		result.version = p.version;
//
//		result.date = p.date;
//
//		double earnings = p.monthlyWage + p.bonus + p.productivityBonus
//				+ p.trienniumPayment;
//		double deductions = p.incomeTax + p.nic;
//		double netWage = Math.round(earnings * 100.0) / 100.0
//				- Math.round(deductions * 100.0) / 100.0;
//		result.netWage = netWage;
//
//		return result;
//	}

	private void check(Optional<Mechanic> m) throws BusinessException {
		BusinessChecks.isNotNull(m, "Mechanic does not exist");
		BusinessChecks.isTrue(m.isPresent(), "Mechanic is not present");
//		BusinessChecks.isTrue(!m.get()._getContract().getPayrolls().isEmpty(),
//				"Mechanic has no payrolls");

	}

}
