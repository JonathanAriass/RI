package uo.ri.cws.application.service.payroll.crud.command;

import java.time.LocalDate;

import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.ContractRepository;
import uo.ri.cws.application.repository.PayrollRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Contract;
import uo.ri.cws.domain.Contract.ContractState;
import uo.ri.cws.domain.Payroll;

public class GeneratePayrolls implements Command<Void> {

	private LocalDate fechaPresente;

	private static PayrollRepository repoPayrolls = Factory.repository.forPayroll();
	private static ContractRepository repoContracts = Factory.repository.forContract();

	public GeneratePayrolls() {
		this.fechaPresente = LocalDate.now();
	}

	public GeneratePayrolls(LocalDate date) {
//		ArgumentChecks.isNotNull(date);

		fechaPresente = date;
	}

	@Override
	public Void execute() throws BusinessException {
		if (fechaPresente == null) {
			fechaPresente = LocalDate.now();
		}

		System.out.println(repoContracts.findAll());
//		repoContracts.findAll().stream().forEach(contract -> {
//			if (contract.getState() == ContractState.IN_FORCE
//					|| (contract.getState() == ContractState.TERMINATED
//							&& contract.getEndDate().isPresent()
//							&& fechaPresente != null
//							&& contract	.getEndDate()
//										.get()
//										.getMonthValue() >= fechaPresente.getMonthValue()
//							&& contract	.getEndDate()
//										.get()
//										.getYear() == fechaPresente.getYear())) {
//				// Comprobar que tiene payroll
//				if (!hasPayroll(contract)) {
//					Payroll p = new Payroll(contract);
//					repoPayrolls.add(p);
//				}
//			}
//		});

		for (Contract contract : repoContracts.findAll()) {
			if (contract.getState() == ContractState.IN_FORCE
					|| (contract.getState() == ContractState.TERMINATED
							&& contract.getEndDate().isPresent()
							&& fechaPresente != null
							&& contract	.getEndDate()
										.get()
										.getMonthValue() >= fechaPresente.getMonthValue()
							&& contract	.getEndDate()
										.get()
										.getYear() == fechaPresente.getYear())) {
				// Comprobar que tiene payroll
				if (!hasPayroll(contract)) {
					Payroll p = new Payroll(contract, fechaPresente);
					System.out.println("ENTRA");
					repoPayrolls.add(p);
					System.out.println(contract.getPayrolls());
				}
			}
		}

		// TODO comprobar que funciona con los test
//		repoContracts	.findAllForPayrolls(fechaPresente)
//						.stream()
//						.forEach(contract -> {
//							if (contract.getMechanic().isPresent()) {
//								if (!contract	.getMechanic()
//												.get()
//												.getAssigned()
//												.isEmpty()) {
//									Payroll p = new Payroll(contract);
//									repoPayrolls.add(p);
//
//								}
//							}
//						});

		return null;
	}

	private boolean hasPayroll(Contract contract) {
		boolean aux = false;

//		for (Payroll payroll : repoPayrolls.findAll()) {
//
//			if (payroll.getContract().equals(contract)
//					&& payroll	.getDate()
//								.getMonthValue() == fechaPresente.getMonthValue()
//					&& payroll.getDate().getYear() == fechaPresente.getYear()) {
//				aux = true;
//			}
//		}

		System.out.println(contract.getPayrolls().toString());
		if (!contract.getPayrolls().isEmpty()) {
			aux = true;
		}

		return aux;
	}

}
