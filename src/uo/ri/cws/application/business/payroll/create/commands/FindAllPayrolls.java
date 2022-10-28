package uo.ri.cws.application.business.payroll.create.commands;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import uo.ri.cws.application.business.BusinessException;
import uo.ri.cws.application.business.payroll.PayrollService.PayrollBLDto;
import uo.ri.cws.application.business.payroll.PayrollService.PayrollSummaryBLDto;
import uo.ri.cws.application.business.payroll.assembler.PayrollAssembler;
import uo.ri.cws.application.business.util.command.Command;
import uo.ri.cws.application.persistence.PersistenceFactory;
import uo.ri.cws.application.persistence.payroll.PayrollGateway;

public class FindAllPayrolls implements Command<List<PayrollSummaryBLDto>> {

	private PayrollGateway pg = PersistenceFactory.forPayroll();
	
	@Override
	public List<PayrollSummaryBLDto> execute() throws BusinessException {
		List<PayrollSummaryBLDto> result = summaryPayrolls();
		
		return result;
	}

	private List<PayrollSummaryBLDto> summaryPayrolls() {
		List<PayrollSummaryBLDto> aux = new ArrayList<PayrollSummaryBLDto>();
		
		List<PayrollBLDto> payrolls = PayrollAssembler.toDtoList(pg.findAll());
		
		for (PayrollBLDto p : payrolls) {
			aux.add(buildSummaryPayroll(p));
		}
		
		return null;
	}

	private PayrollSummaryBLDto buildSummaryPayroll(PayrollBLDto p) {
		PayrollSummaryBLDto result = new PayrollSummaryBLDto();
		
		result.id = p.id;
		result.version = p.version;
		
		result.date = p.date;
		result.netWage = p.netWage;
		
		return result;
	}

}
