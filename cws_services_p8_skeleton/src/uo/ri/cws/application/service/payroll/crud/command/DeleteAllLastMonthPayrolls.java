package uo.ri.cws.application.service.payroll.crud.command;

import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.PayrollRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Payroll;

public class DeleteAllLastMonthPayrolls implements Command<Void> {

	private static PayrollRepository repoPayrolls = Factory.repository.forPayroll();

	@Override
	public Void execute() throws BusinessException {

		for (Payroll p : repoPayrolls.findCurrentMonthPayrolls()) {
			repoPayrolls.remove(p);
		}

		return null;
	}

}
