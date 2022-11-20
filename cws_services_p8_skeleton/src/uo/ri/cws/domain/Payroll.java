package uo.ri.cws.domain;

import java.time.LocalDate;
import java.util.Objects;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import uo.ri.cws.domain.Contract.ContractState;
import uo.ri.cws.domain.WorkOrder.WorkOrderState;
import uo.ri.cws.domain.base.BaseEntity;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.math.Round;

@Entity
@Table(name = "tpayrolls", uniqueConstraints = @UniqueConstraint(columnNames = {
		"contract_id", "date" }))
public class Payroll extends BaseEntity {

	@Basic(optional = false)
	private LocalDate date;

	private double bonus;
	private double incomeTax;
	private double monthlyWage;
	private double nic;
	private double productivityBonus;
	private double trienniumPayment;

	@ManyToOne
	private Contract contract;

	Payroll() {
	}

	public Payroll(Contract c) {
		this(c, LocalDate.now());
	}

	public Payroll(Contract c, LocalDate d) {
		ArgumentChecks.isNotNull(d, "PAYROLL: invalid date.");
		ArgumentChecks.isNotNull(c, "PAYROLL: invalid contract.");
		date = d;
		contract = c;

		generatePayroll();
		Associations.Run.link(this, c);
	}

	public Payroll(Contract contract2, LocalDate d, double monthlyWage2,
			double extra, double productivity, double trienniums, double tax,
			double nic2) {
		ArgumentChecks.isNotNull(d, "PAYROLL: invalid date.");
		ArgumentChecks.isNotNull(contract2, "PAYROLL: invalid contract.");

		this.bonus = extra;
		this.incomeTax = tax;
		this.monthlyWage = monthlyWage2;
		this.nic = nic2;
		this.productivityBonus = productivity;
		this.trienniumPayment = trienniums;
		this.date = d;

		Associations.Run.link(this, contract2);
	}

	private void generatePayroll() {
		// PARA EL MONTHLYWAGE
		this.monthlyWage = contract.getAnnualBaseWage() / 14;

		// PARA EL BONUS
		if (date.getMonth().getValue() == 12
				|| date.getMonth().getValue() == 6) {
			this.bonus = monthlyWage;
		}

		// PARA EL PRODUCTIVITYWAGE
		double auxWorkorders = 0;

		if (contract.getMechanic().isPresent()) {
			for (WorkOrder w : contract.getMechanic().get().getAssigned()) {
				if (w.getDate().getMonth().equals(date.getMonth())
						&& w.getDate().getYear() == date.getYear()
						&& w.getState() == WorkOrderState.INVOICED) {
					auxWorkorders += w.getAmount();
				}
			}
		} else if (contract.getState() == ContractState.TERMINATED
				&& contract.getFiredMechanic().isPresent()) {
			for (WorkOrder w : contract	.getFiredMechanic()
										.get()
										.getAssigned()) {
				if (w.getDate().getMonth().equals(date.getMonth())
						&& w.getDate().getYear() == date.getYear()
						&& w.getState() == WorkOrderState.INVOICED) {
					auxWorkorders += w.getAmount();
				}
			}
		}

		double productivityBonus2 = contract.getProfessionalGroup()
											.getProductivityBonusPercentage();
		double res = (productivityBonus2 / 100) * auxWorkorders;
		this.productivityBonus = res;

		// PARA EL TRIENNIUM
		int trienniumAcumulatted = 0;

		trienniumAcumulatted = date.getYear()
				- contract.getStartDate().getYear();

		double triennium_payment = contract	.getProfessionalGroup()
											.getTrienniumPayment();

		this.trienniumPayment = Round.twoCents(
				(trienniumAcumulatted / 3) * triennium_payment);

		// PARA EL NIC
		this.nic = (monthlyWage * 14 * 0.05) / 12;

		// PARA EL INCOMETAX
		double annualBaseWage = monthlyWage * 14;
		double aux = 0.0;
		if (annualBaseWage > 0 && annualBaseWage <= 12450) {
			aux = 0.19;
		} else if (annualBaseWage >= 12450 && annualBaseWage <= 20200) {
			aux = 0.24;
		} else if (annualBaseWage >= 20200 && annualBaseWage <= 35200) {
			aux = 0.30;
		} else if (annualBaseWage >= 35200 && annualBaseWage <= 60000) {
			aux = 0.37;
		} else if (annualBaseWage >= 60000 && annualBaseWage <= 300000) {
			aux = 0.45;
		} else {
			aux = 0.47;
		}
		double earnings = monthlyWage + bonus + productivityBonus
				+ trienniumPayment;

		this.incomeTax = Round.twoCents(aux * earnings);
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
		return this.productivityBonus;
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
		return Objects.equals(contract, other.contract)
				&& Objects.equals(date, other.date);
	}

	@Override
	public String toString() {
		return "Payroll [date=" + date + ", bonus=" + bonus + ", incomeTax="
				+ incomeTax + ", monthlyWage=" + monthlyWage + ", nic=" + nic
				+ ", productivityBonus=" + productivityBonus
				+ ", trienniumPayment=" + trienniumPayment + ", contract="
				+ contract + "]";
	}

	void _setContract(Contract contract2) {
		this.contract = contract2;
	}

}
