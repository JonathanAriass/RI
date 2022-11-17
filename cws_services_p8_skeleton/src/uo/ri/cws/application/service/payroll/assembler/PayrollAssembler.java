package uo.ri.cws.application.service.payroll.assembler;

import java.util.ArrayList;
import java.util.List;

import uo.ri.cws.application.service.payroll.PayrollService.PayrollBLDto;
import uo.ri.cws.application.service.payroll.PayrollService.PayrollSummaryBLDto;
import uo.ri.cws.domain.Payroll;
import uo.ri.util.math.Round;

public class PayrollAssembler {

	public static PayrollBLDto toBLDto(Payroll arg) {
		PayrollBLDto result = null;

		if (arg != null) {
			result = new PayrollBLDto();
			result.id = arg.getId();
			result.version = arg.getVersion();
			result.bonus = arg.getBonus();
			result.date = arg.getDate();
			result.incomeTax = arg.getIncomeTax();
			result.monthlyWage = arg.getMonthlyWage();
			result.nic = arg.getNIC();
			result.productivityBonus = arg.getProductivityBonus();
			result.trienniumPayment = arg.getTrienniumPayment();
			double earnings = result.monthlyWage + result.bonus
					+ result.productivityBonus + result.trienniumPayment;
			double deductions = result.incomeTax + result.nic;
			double netWage = Round.twoCents(earnings)
					- Round.twoCents(deductions);
			result.netWage = Round.twoCents(netWage);
			result.contractId = arg.getContract().getId();
		}

		return result;
	}

	public static List<PayrollBLDto> toDtoList(List<Payroll> arg) {
		List<PayrollBLDto> result = new ArrayList<PayrollBLDto>();
		for (Payroll p : arg)
			result.add(toBLDto(p));
		return result;
	}

	public static List<PayrollSummaryBLDto> toBLDtoSummaryList(
			List<PayrollBLDto> payrolls) {
		List<PayrollSummaryBLDto> aux = new ArrayList<PayrollSummaryBLDto>();

		for (PayrollBLDto p : payrolls) {
			aux.add(toBLDtoSummary(p));
		}

		return aux;
	}

	public static PayrollSummaryBLDto toBLDtoSummary(PayrollBLDto p) {
		PayrollSummaryBLDto result = new PayrollSummaryBLDto();

		result.id = p.id;
		result.version = p.version;

		result.date = p.date;

		double earnings = p.monthlyWage + p.bonus + p.productivityBonus
				+ p.trienniumPayment;
		double deductions = p.incomeTax + p.nic;
		double netWage = Math.round(earnings * 100.0) / 100.0
				- Math.round(deductions * 100.0) / 100.0;
		result.netWage = netWage;

		return result;
	}

}
