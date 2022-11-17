package uo.ri.cws.application.service.payroll.crud.command;

import java.util.ArrayList;
import java.util.List;

import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.PayrollRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.payroll.PayrollService.PayrollBLDto;
import uo.ri.cws.application.service.payroll.PayrollService.PayrollSummaryBLDto;
import uo.ri.cws.application.service.payroll.assembler.PayrollAssembler;
import uo.ri.cws.application.util.command.Command;

public class FindAllPayrolls implements Command<List<PayrollSummaryBLDto>> {

	private static PayrollRepository repoPayrolls = Factory.repository.forPayroll();

	@Override
	public List<PayrollSummaryBLDto> execute() throws BusinessException {
		List<PayrollSummaryBLDto> result = summaryPayrolls();

		return result;
	}

	private List<PayrollSummaryBLDto> summaryPayrolls() {
		List<PayrollSummaryBLDto> aux = new ArrayList<PayrollSummaryBLDto>();

		List<PayrollBLDto> payrolls = PayrollAssembler.toDtoList(
				repoPayrolls.findAll());

		for (PayrollBLDto p : payrolls) {
			aux.add(PayrollAssembler.toBLDtoSummary(p));
		}

		return aux;
	}

}
