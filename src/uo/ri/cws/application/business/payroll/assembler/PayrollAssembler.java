package uo.ri.cws.application.business.payroll.assembler;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import math.Round;
import uo.ri.cws.application.business.payroll.PayrollService.PayrollBLDto;
import uo.ri.cws.application.persistence.payroll.PayrollGateway.PayrollDALDto;

public class PayrollAssembler {

	public static Optional<PayrollBLDto> toBLDto(Optional<PayrollDALDto> arg) {
		Optional<PayrollBLDto> result = arg.isEmpty() ? Optional.ofNullable(null)
				: Optional.ofNullable(toPayrollDto(arg.get()));
		return result;
	}

	public static List<PayrollBLDto> toDtoList(List<PayrollDALDto> arg) {
		List<PayrollBLDto> result = new ArrayList<PayrollBLDto>();
		for (PayrollDALDto mr : arg)
			result.add(toPayrollDto(mr));
		return result;
	}

	public static PayrollDALDto toDALDto(PayrollBLDto arg) {
		PayrollDALDto result = new PayrollDALDto();
		result.id = arg.id;
		result.version = arg.version;
		result.bonus = arg.bonus;
		result.date = arg.date;
		result.incometax = arg.incomeTax;
		result.monthlywage = arg.monthlyWage;
		result.nic = arg.nic;
		result.productivitybonus = arg.productivityBonus;
		result.trienniumpayment = arg.trienniumPayment;
		result.contractid = arg.contractId;
		return result;
	}

	private static PayrollBLDto toPayrollDto(PayrollDALDto arg) {
		PayrollBLDto result = new PayrollBLDto();
		result.id = arg.id;
		result.version = arg.version;
		result.bonus = arg.bonus;
		result.date = arg.date;
		result.incomeTax = arg.incometax;
		result.monthlyWage = arg.monthlywage;
		result.nic = arg.nic;
		result.productivityBonus = arg.productivitybonus;
		result.trienniumPayment = arg.trienniumpayment;
		result.contractId = arg.contractid;
		
		double earnings = result.monthlyWage + result.bonus + result.productivityBonus + result.trienniumPayment;
		double deductions = result.incomeTax + result.nic;
		double netWage = Round.twoCents(earnings) - Round.twoCents(deductions);
		result.netWage = Round.twoCents(netWage);
		
		return result;
	}
	
}
