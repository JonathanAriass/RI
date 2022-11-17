package uo.ri.cws.application.service.payroll.crud.command;

import java.time.LocalDate;
import java.util.Optional;

import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.ContractRepository;
import uo.ri.cws.application.repository.PayrollRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Contract;
import uo.ri.cws.domain.Payroll;

public class GeneratePayrolls implements Command<Void> {

	private LocalDate fechaPresente;

	private static PayrollRepository repoPayrolls = Factory.repository.forPayroll();
	private static ContractRepository repoContracts = Factory.repository.forContract();

	public GeneratePayrolls() {
		this.fechaPresente = LocalDate.now();
	}

	public GeneratePayrolls(LocalDate date) {
		fechaPresente = date;
	}

	@Override
	public Void execute() throws BusinessException {
		if (fechaPresente == null) {
			fechaPresente = LocalDate.now();
		}

		for (Contract contract : repoContracts.findAllForPayrolls(
				fechaPresente)) {
			Optional<Payroll> payroll = repoPayrolls.findCurrentMonthByContractId(
					contract.getId());
			if (!payroll.isPresent()) {
				Payroll p = new Payroll(contract, fechaPresente);
				repoPayrolls.add(p);
			}
		}

		return null;
	}

}
