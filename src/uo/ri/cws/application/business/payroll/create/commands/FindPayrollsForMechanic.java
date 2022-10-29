package uo.ri.cws.application.business.payroll.create.commands;

import java.util.ArrayList;
import java.util.List;

import assertion.Argument;
import uo.ri.cws.application.business.BusinessException;
import uo.ri.cws.application.business.mechanic.assembler.MechanicAssembler;
import uo.ri.cws.application.business.payroll.PayrollService.PayrollBLDto;
import uo.ri.cws.application.business.payroll.PayrollService.PayrollSummaryBLDto;
import uo.ri.cws.application.business.payroll.assembler.PayrollAssembler;
import uo.ri.cws.application.business.util.command.Command;
import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.PersistenceFactory;
import uo.ri.cws.application.persistence.contract.ContractGateway;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway;
import uo.ri.cws.application.persistence.payroll.PayrollGateway;

public class FindPayrollsForMechanic implements Command<List<PayrollSummaryBLDto>> {

	
	private String mechanicDni = "";
	private PayrollGateway pg = PersistenceFactory.forPayroll();
	private ContractGateway cg = PersistenceFactory.forContract();
	private MechanicGateway mg = PersistenceFactory.forMechanic();
	
	public FindPayrollsForMechanic(String arg) {
		validate(arg);
		this.mechanicDni = arg;
	}
	
	@Override
	public List<PayrollSummaryBLDto> execute() throws BusinessException {
		if (!existMechanic(mechanicDni)) {
			throw new BusinessException("Mechanic doesn't exist");
		}
		
		String mechanicId = MechanicAssembler.toBLDto(mg.findByDni(mechanicDni)).get().id;
		
		// sacar id del contrato para el mecanico y hacer busqueda de payrolls
		String contractId = "";
		if (cg.findByMechanic(mechanicId).isPresent()) {
			contractId = cg.findByMechanic(mechanicId).get().id;			
		}
		
		List<PayrollBLDto> payrolls = PayrollAssembler.toDtoList(pg.findPayrollByContractId(contractId));
		
		return buildSummaryPayroll(payrolls);
	}
	
	private boolean existMechanic(String id) throws PersistenceException {
		if (mg.findByDni(mechanicDni).isPresent()) {
			return true;
		} else {
			return false;
		}
	}
	
	private List<PayrollSummaryBLDto> buildSummaryPayroll(List<PayrollBLDto> payrolls) {
		List<PayrollSummaryBLDto> aux = new ArrayList<PayrollSummaryBLDto>();
		
		for (PayrollBLDto p : payrolls) {
			aux.add(buildSummaryPayroll(p));
		}
		
		return aux;
	}
	
	private PayrollSummaryBLDto buildSummaryPayroll(PayrollBLDto p) {
PayrollSummaryBLDto result = new PayrollSummaryBLDto();
		
		result.id = p.id;
		result.version = p.version;
		
		result.date = p.date;
		
		double earnings = p.monthlyWage + p.bonus + p.productivityBonus + p.trienniumPayment;
		double deductions = p.incomeTax + p.nic;
		double netWage = Math.round(earnings*100.0)/100.0 - Math.round(deductions*100.0)/100.0;
		result.netWage = netWage;
		
		return result;
	}
	
//	private PayrollSummaryBLDto buildSummaryPayroll(PayrollBLDto p) {
//		PayrollSummaryBLDto result = new PayrollSummaryBLDto();
//		
//		result.id = p.id;
//		result.version = p.version;
//		
//		result.date = p.date;
//		result.netWage = p.netWage;
//		
//		return result;
//	}

	private void validate(String arg) {
		// usar clase del proyecto util Argumen
		Argument.isNotEmpty(arg, "Null or empty id");
	}
	
}
