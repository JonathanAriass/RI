package uo.ri.cws.application.service.payroll.crud.command;

import java.util.List;

import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.PayrollRepository;
import uo.ri.cws.application.repository.ProfessionalGroupRepository;
import uo.ri.cws.application.service.BusinessException;
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

		return PayrollAssembler.toBLDtoSummaryList(
				PayrollAssembler.toDtoList(payrolls));
	}

	private boolean existsProfessionalGroup() {
		if (repoGroups.findByName(name).isPresent()) {
			return true;
		}
		return false;
	}

}
