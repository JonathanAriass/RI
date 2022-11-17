package uo.ri.cws.application.service.payroll.crud;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import uo.ri.conf.Factory;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.payroll.PayrollService;
import uo.ri.cws.application.service.payroll.crud.command.DeleteAllLastMonthPayrolls;
import uo.ri.cws.application.service.payroll.crud.command.DeletePayrollsForMechanic;
import uo.ri.cws.application.service.payroll.crud.command.FindAllPayrolls;
import uo.ri.cws.application.service.payroll.crud.command.FindPayrollDetails;
import uo.ri.cws.application.service.payroll.crud.command.FindPayrollForMechanic;
import uo.ri.cws.application.service.payroll.crud.command.FindPayrollsByProfessionalGroup;
import uo.ri.cws.application.service.payroll.crud.command.GeneratePayrolls;
import uo.ri.cws.application.util.command.CommandExecutor;

public class PayrollServiceImpl implements PayrollService {

	private CommandExecutor executor = Factory.executor.forExecutor();

	@Override
	public void generatePayrolls() throws BusinessException {
		executor.execute(new GeneratePayrolls());
	}

	@Override
	public void generatePayrolls(LocalDate present) throws BusinessException {
		executor.execute(new GeneratePayrolls(present));
	}

	@Override
	public void deleteLastPayrollFor(String mechanicId)
			throws BusinessException {
		executor.execute(new DeletePayrollsForMechanic(mechanicId));
	}

	@Override
	public void deleteLastPayrolls() throws BusinessException {
		executor.execute(new DeleteAllLastMonthPayrolls());
	}

	@Override
	public Optional<PayrollBLDto> getPayrollDetails(String id)
			throws BusinessException {
		return executor.execute(new FindPayrollDetails(id));
	}

	@Override
	public List<PayrollSummaryBLDto> getAllPayrolls() throws BusinessException {
		return executor.execute(new FindAllPayrolls());
	}

	@Override
	public List<PayrollSummaryBLDto> getAllPayrollsForMechanic(String id)
			throws BusinessException {
		return executor.execute(new FindPayrollForMechanic(id));
	}

	@Override
	public List<PayrollSummaryBLDto> getAllPayrollsForProfessionalGroup(
			String name) throws BusinessException {
		return executor.execute(new FindPayrollsByProfessionalGroup(name));
	}

}
