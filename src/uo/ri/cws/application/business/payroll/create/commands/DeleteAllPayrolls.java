package uo.ri.cws.application.business.payroll.create.commands;

import java.time.LocalDate;
import java.util.List;

import uo.ri.cws.application.business.BusinessException;
import uo.ri.cws.application.business.payroll.PayrollService.PayrollBLDto;
import uo.ri.cws.application.business.payroll.assembler.PayrollAssembler;
import uo.ri.cws.application.business.util.command.Command;
import uo.ri.cws.application.persistence.PersistenceFactory;
import uo.ri.cws.application.persistence.payroll.PayrollGateway;

public class DeleteAllPayrolls implements Command<Void> {

	private PayrollGateway pg = PersistenceFactory.forPayroll();

	
	@Override
	public Void execute() throws BusinessException {
		List<PayrollBLDto> payrolls = PayrollAssembler.toDtoList(pg.findAll());
		
		for (PayrollBLDto p : payrolls) {
			if (p.date.getMonth().equals(LocalDate.now().getMonth())
					&& p.date.getYear() == LocalDate.now().getYear()) {
				pg.remove(p.id);
			}
		}
		
		return null;
	}

}
