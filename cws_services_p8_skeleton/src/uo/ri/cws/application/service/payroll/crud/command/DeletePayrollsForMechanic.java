package uo.ri.cws.application.service.payroll.crud.command;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.MechanicRepository;
import uo.ri.cws.application.repository.PayrollRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.util.BusinessChecks;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Mechanic;
import uo.ri.cws.domain.Payroll;
import uo.ri.util.assertion.ArgumentChecks;

public class DeletePayrollsForMechanic implements Command<Void> {

	private String mechanicId = "";
	private static PayrollRepository repoPayrolls = Factory.repository.forPayroll();
	private static MechanicRepository repoMechanics = Factory.repository.forMechanic();

	public DeletePayrollsForMechanic(String mechanicId) {
		ArgumentChecks.isNotBlank(mechanicId);
		ArgumentChecks.isNotBlank(mechanicId);
		ArgumentChecks.isNotNull(mechanicId);

		this.mechanicId = mechanicId;
	}

	@Override
	public Void execute() throws BusinessException {

		Optional<Mechanic> m = repoMechanics.findById(mechanicId);

		check(m);

		List<Payroll> payrolls = repoPayrolls.findPayrollsForMechanicId(
				mechanicId);

		for (Payroll p : payrolls) {
			if (p.getDate().getMonthValue() == (LocalDate.now().getMonthValue())
					&& p.getDate().getYear() == LocalDate.now().getYear()) {
				System.out.println("ENTRA");
				repoPayrolls.remove(p);
			}
		}

		return null;
	}

	private void check(Optional<Mechanic> m) throws BusinessException {
		BusinessChecks.isNotNull(m, "Mechanic does not exist");
		BusinessChecks.isTrue(m.isPresent(), "Mechanic is not present");
//		BusinessChecks.isTrue(!m.get()._getContract().getPayrolls().isEmpty(),
//				"Mechanic has no payrolls");

	}

}
