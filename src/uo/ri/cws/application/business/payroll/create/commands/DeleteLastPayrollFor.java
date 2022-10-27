package uo.ri.cws.application.business.payroll.create.commands;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import assertion.Argument;
import uo.ri.cws.application.business.BusinessException;
import uo.ri.cws.application.business.contract.ContractService.ContractBLDto;
import uo.ri.cws.application.business.contract.assembler.ContractAssembler;
import uo.ri.cws.application.business.mechanic.MechanicService.MechanicBLDto;
import uo.ri.cws.application.business.mechanic.assembler.MechanicAssembler;
import uo.ri.cws.application.business.payroll.PayrollService.PayrollBLDto;
import uo.ri.cws.application.business.payroll.assembler.PayrollAssembler;
import uo.ri.cws.application.business.util.command.Command;
import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.PersistenceFactory;
import uo.ri.cws.application.persistence.contract.ContractGateway;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway;
import uo.ri.cws.application.persistence.payroll.PayrollGateway;

public class DeleteLastPayrollFor implements Command<Void> {

	private String mechanicDni = "";
	private PayrollBLDto payroll = null;
	private MechanicGateway mg = PersistenceFactory.forMechanic();
	private PayrollGateway pg = PersistenceFactory.forPayroll();
	private ContractGateway cg = PersistenceFactory.forContract();
	
	public DeleteLastPayrollFor(String dni) {
		validate(dni);
		this.mechanicDni = dni;
	}
	
	@Override
	public Void execute() throws BusinessException {
		// Check if mechanic exist with this id
		if (!existMechanic(mechanicDni)) {
			throw new BusinessException("Mechanic doesn't exist");
		}
		
		if (!hasPayroll(mechanicDni)) {
			throw new BusinessException("Mechanic has no payrolls");
		}
		
		Optional<MechanicBLDto> mechanic = MechanicAssembler.toBLDto(mg.findByDni(mechanicDni));
		Optional<ContractBLDto> contrato = ContractAssembler.toBLDto(cg.findByMechanic(mechanic.get().id));
		
//		Optional<PayrollBLDto> payroll = PayrollAssembler.toBLDto(pg.findPayrollByContractId(contrato.get().id));
		
		pg.remove(payroll.id);
		
		return null;
	}

	private boolean existMechanic(String id) throws PersistenceException {
		if (mg.findByDni(id).isPresent()) {
			return true;
		} else {
			return false;
		}
	}
	
	private boolean hasPayroll(String mechanicDni2) throws PersistenceException {
		Optional<MechanicBLDto> mechanic = MechanicAssembler.toBLDto(mg.findByDni(mechanicDni2));
		Optional<ContractBLDto> contrato = ContractAssembler.toBLDto(cg.findByMechanic(mechanic.get().id));

		if (contrato.isPresent()) {
			List<PayrollBLDto> payrolls = PayrollAssembler.toDtoList(pg.findPayrollByContractId(contrato.get().id));
			for (PayrollBLDto p : payrolls) {
				if (p.date.getMonthValue() == (LocalDate.now().getMonthValue())) {
					payroll = p;
					return true;				
				}
			}
			
			
//			if (!payroll.isEmpty()) {
//				System.out.println((payroll.get().date.getMonthValue()));
//				if (payroll.get().date.getMonthValue() == (LocalDate.now().getMonthValue())) {
//					return true;					
//				}
//			} else {
//				return false;
//			}
		}
		
		return false;
	}
	
	private void validate(String arg) {
		// usar clase del proyecto util Argumen
		Argument.isNotEmpty(arg, "Null or empty id");
	}
	
}
