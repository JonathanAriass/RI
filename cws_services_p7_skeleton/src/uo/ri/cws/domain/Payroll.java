package uo.ri.cws.domain;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

public class Payroll {

	private LocalDate date;
	
	private double bonus;
	private double incomeTax;
	private double monthlyWage;
	private double nic;
	private double productivityBonus;
	private double trienniumPayment;
	
	private Contract contract;
	
	Payroll() {}

	public Payroll(Contract c) {
		this(c, LocalDate.now());
	}
	
	public Payroll(Contract c, LocalDate d) {
		date = d;
		contract = c;
	}

	public Payroll(Contract contract2, LocalDate d, double monthlyWage2, double extra, double productivity,
			double trienniums, double tax, double nic2) {
		// TODO validaciones
		
		
		this.bonus = extra;
		this.incomeTax = tax;
		this.monthlyWage = monthlyWage2;
		this.nic = nic2;
		this.productivityBonus = productivity;
		this.trienniumPayment = trienniums;
		this.date = d;
		this.contract = contract2;
		
		Associations.Run.link(this, contract2);
	}

	public Contract getContract() {
		return this.contract;
	}

	public LocalDate getDate() {
		return date;
	}

	public double getBonus() {
		return bonus;
	}

	public double getIncomeTax() {
		return incomeTax;
	}

	public double getMonthlyWage() {
		return monthlyWage;
	}

	public double getNIC() {
		return nic;
	}

	public double getProductivityBonus() {
		return productivityBonus;
	}

	public double getTrienniumPayment() {
		return trienniumPayment;
	}

	@Override
	public int hashCode() {
		return Objects.hash(contract, date);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Payroll other = (Payroll) obj;
		return Objects.equals(contract, other.contract) && Objects.equals(date, other.date);
	}

	@Override
	public String toString() {
		return "Payroll [date=" + date + ", bonus=" + bonus + ", incomeTax=" + incomeTax + ", monthlyWage="
				+ monthlyWage + ", nic=" + nic + ", productivityBonus=" + productivityBonus + ", trienniumPayment="
				+ trienniumPayment + ", contract=" + contract + "]";
	}

	void _setContract(Contract contract2) {
		this.contract = contract2;
	}
		
	
}
