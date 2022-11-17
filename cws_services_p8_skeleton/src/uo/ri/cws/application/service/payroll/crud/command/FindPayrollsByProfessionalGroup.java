package uo.ri.cws.application.service.payroll.crud.command;

import java.util.ArrayList;
import java.util.List;

import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.PayrollRepository;
import uo.ri.cws.application.repository.ProfessionalGroupRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.payroll.PayrollService.PayrollBLDto;
import uo.ri.cws.application.service.payroll.PayrollService.PayrollSummaryBLDto;
import uo.ri.cws.application.service.payroll.assembler.PayrollAssembler;
import uo.ri.cws.application.util.BusinessChecks;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Payroll;
import uo.ri.util.assertion.ArgumentChecks;

public class FindPayrollsByProfessionalGroup
		implements Command<List<PayrollSummaryBLDto>> {

	private String name = "";
	private static PayrollRepository repoPayrolls = Factory.repository.forPayroll();
	private static ProfessionalGroupRepository repoGroups = Factory.repository.forProfessionalGroup();

	public FindPayrollsByProfessionalGroup(String name) {
		ArgumentChecks.isNotBlank(name);
		ArgumentChecks.isNotBlank(name);
		ArgumentChecks.isNotNull(name);

		this.name = name;
	}

	@Override
	public List<PayrollSummaryBLDto> execute() throws BusinessException {
		BusinessChecks.isTrue(existsProfessionalGroup(),
				"Professional group does not exist");

		List<Payroll> payrolls = repoPayrolls.findPayrollsForProfessionalGroup(
				name);

//		List<PayrollBLDto> payrolls = new ArrayList<PayrollBLDto>();
//		for (ContractBLDto c : contractsId) {
//			payrolls.addAll(PayrollAssembler.toDtoList(
//					pg.findPayrollByContractId(c.id)));
//		}

		return buildSummaryPayroll(PayrollAssembler.toDtoList(payrolls));
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

	private boolean existsProfessionalGroup() {
		if (repoGroups.findByName(name).isPresent()) {
			return true;
		}
		return false;
	}

}
