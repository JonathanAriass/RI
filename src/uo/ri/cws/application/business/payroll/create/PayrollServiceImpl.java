package uo.ri.cws.application.business.payroll.create;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.business.BusinessException;
import uo.ri.cws.application.business.payroll.PayrollService;
import uo.ri.cws.application.business.payroll.create.commands.DeleteAllPayrolls;
import uo.ri.cws.application.business.payroll.create.commands.DeleteLastPayrollFor;
import uo.ri.cws.application.business.payroll.create.commands.FindAllPayrolls;
import uo.ri.cws.application.business.payroll.create.commands.FindPayrollDetails;
import uo.ri.cws.application.business.payroll.create.commands.GeneratePayrolls;
import uo.ri.cws.application.business.util.command.CommandExecutor;

public class PayrollServiceImpl implements PayrollService {

	private CommandExecutor executor = new CommandExecutor();
	
	@Override
	public void generatePayrolls() throws BusinessException {
		executor.execute(new GeneratePayrolls());
	}

	@Override
	public void generatePayrolls(LocalDate present) throws BusinessException {
		executor.execute(new GeneratePayrolls(present));
	}

	@Override
	public void deleteLastPayrollFor(String mechanicId) throws BusinessException {
		executor.execute(new DeleteLastPayrollFor(mechanicId));
	}

	@Override
	public void deleteLastPayrolls() throws BusinessException {
		executor.execute(new DeleteAllPayrolls());
	}

	@Override
	public Optional<PayrollBLDto> getPayrollDetails(String id) throws BusinessException {
		return executor.execute(new FindPayrollDetails(id));
	}

	@Override
	public List<PayrollSummaryBLDto> getAllPayrolls() throws BusinessException {
		return executor.execute(new FindAllPayrolls());
	}

	@Override
	public List<PayrollSummaryBLDto> getAllPayrollsForMechanic(String id) throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PayrollSummaryBLDto> getAllPayrollsForProfessionalGroup(String name) throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

}
