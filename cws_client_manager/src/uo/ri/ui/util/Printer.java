package uo.ri.ui.util;

import java.util.List;

import uo.ri.cws.application.service.invoice.InvoicingService.InvoiceDto;
import uo.ri.cws.application.service.invoice.InvoicingService.PaymentMeanDto;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.cws.application.service.payroll.PayrollService.PayrollBLDto;
import uo.ri.cws.application.service.payroll.PayrollService.PayrollSummaryBLDto;
import uo.ri.cws.application.service.professionalgroup.ProfessionalGroupService.ProfessionalGroupBLDto;
import uo.ri.cws.application.service.vehicletype.VehicleTypeCrudService.VehicleTypeDto;
import uo.ri.cws.application.service.workorder.WorkOrderCrudService.WorkOrderDto;
import uo.ri.util.console.Console;

public class Printer {

	public static void printMechanic(MechanicDto m) {
		Console.printf("\t%-36.36s %-9s %-10.10s %-25.25s %-10.2s\n", m.id,
				m.dni, m.name, m.surname, m.version);
	}

	public static void printMechanics(List<MechanicDto> list) {

		Console.printf("\t%-36s %-9s %-10s %-25s %-10s\n",
				"Mechanic identifier", "DNI", "Name", "Surname", "Version");
		for (MechanicDto m : list)
			printMechanic(m);
	}

	public static void printInvoice(InvoiceDto invoice) {

		double importeConIVa = invoice.total;
		double iva = invoice.vat;
		double importeSinIva = importeConIVa / (1 + iva / 100);

		Console.printf("Invoice #: %d\n", invoice.number);
		Console.printf("\tDate: %1$td/%1$tm/%1$tY\n", invoice.date);
		Console.printf("\tTotal: %.2f €\n", importeSinIva);
		Console.printf("\tTax: %.1f %% \n", invoice.vat);
		Console.printf("\tTotal, tax inc.: %.2f €\n", invoice.total);
		Console.printf("\tStatus: %s\n", invoice.state);
	}

	public static void printPaymentMeans(List<PaymentMeanDto> medios) {
		Console.println();
		Console.println("Available payment means");

		Console.printf("\t%s \t%-8.8s \t%s \n", "Id", "Type", "Acummulated");
		for (PaymentMeanDto medio : medios) {
			printPaymentMean(medio);
		}
	}

	public static void printProfessionalGroup(ProfessionalGroupBLDto pg) {
		Console.printf("\t%-36.36s %-9s %-17.2f %-16.2f %-10.2s\n", pg.id,
				pg.name, pg.productivityRate, pg.trieniumSalary, pg.version);
	}

	public static void printProfessionalGroups(
			List<ProfessionalGroupBLDto> list) {

		Console.printf("\t%-36s %-9s %-10s %-10s %-10s\n", "Group identifier",
				"Name", "ProductivityBonus", "TrienniumPayment", "Version");
		for (ProfessionalGroupBLDto g : list)
			printProfessionalGroup(g);
	}

	public static void printPayroll(PayrollBLDto p) {
		double earnings = p.monthlyWage + p.bonus + p.productivityBonus
				+ p.trienniumPayment;
		double deductions = p.incomeTax + p.nic;
		double netWage = Math.round(earnings * 100.0) / 100.0
				- Math.round(deductions * 100.0) / 100.0;

		Console.printf(
				"\t%-36s %-9s %-9s %-10s %-10s %-10s %-10s %-10s %-10s %-10s %-10s %-10s %-10s\n",
				"PayrollId", "Bonus", "Date", "IncomeTax", "MonthlyWage", "NIC",
				"ProductivityBonus", "TrienniumPayment", "Version",
				"ContractId", "Earnings", "Deductions", "NetWage");
		Console.printf(
				"\t%-36.36s %-9.2f %-9.9s %-10.2f %-10.2f %-10.2f %-10.2f %-10.2f"
						+ " %-10.2s %-10.2s %-10.2f %-10.2f %-10.2f\n",
				p.id, p.bonus, p.date, p.incomeTax, p.monthlyWage, p.nic,
				p.productivityBonus, p.trienniumPayment, p.version,
				p.contractId, earnings, deductions, netWage);
	}

	public static void printPayrolls(List<PayrollBLDto> list) {
		Console.printf(
				"\t%-36s %-9s %-9s %-10s %-10s %-10s %-10s %-10s %-10s %-10s\n",
				"PayrollId", "Bonus", "Date", "IncomeTax", "MonthlyWage", "NIC",
				"ProductivityBonus", "TrienniumPayment", "Version",
				"ContractId");
		for (PayrollBLDto g : list)
			printPayroll(g);
	}

	public static void printPayrollSummary(PayrollSummaryBLDto p) {
		Console.printf("\t%-36s %-9s %-10s %-10s\n", "PayrollId", "Date",
				"NetWage", "Version");
		Console.printf("\t%-36.36s %-9.9s %-10.2f %-10.2s\n", p.id, p.date,
				p.netWage, p.version);
	}

	public static void printPayrollsSummary(List<PayrollSummaryBLDto> list) {
		Console.printf("\t%-36s %-9s %-10s %-10s\n", "PayrollId", "Date",
				"NetWage", "Version");
		for (PayrollSummaryBLDto g : list)
			printPayrollSummary(g);
	}

	private static void printPaymentMean(PaymentMeanDto medio) {
		Console.printf("\t%s \t%-8.8s \t%s \n", medio.id,
				medio.getClass().getName() // not the best...
				, medio.accumulated);
	}

	public static void printWorkOrder(WorkOrderDto rep) {

		Console.printf("\t%s \t%-40.40s \t%td/%<tm/%<tY \t%-12.12s \t%.2f\n",
				rep.id, rep.description, rep.date, rep.state, rep.total);
	}

	public static void printVehicleType(VehicleTypeDto vt) {

		Console.printf("\t%s %-10.10s %5.2f %d\n", vt.id, vt.name,
				vt.pricePerHour, vt.minTrainigHours);
	}

}
