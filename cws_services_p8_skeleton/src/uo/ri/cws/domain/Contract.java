package uo.ri.cws.domain;

import java.time.LocalDate;

import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import uo.ri.cws.domain.base.BaseEntity;
import uo.ri.util.assertion.ArgumentChecks;

@Entity
@Table(name="tcontracts",
uniqueConstraints=@UniqueConstraint(columnNames = {"mechanic_id", 
		"professionalgroup_id", "contracttype_id"}))
public class Contract extends BaseEntity {

	// Atributos naturales
	private double annualBaseWage;
	private LocalDate endDate;
	private double settlement;
	@Basic(optional=false) private LocalDate startDate;
	private ContractState state;
	
	// Atributos accidentales
	@OneToOne private Mechanic mechanic;
	@ManyToOne private ContractType contracttype;
	@ManyToOne private ProfessionalGroup professionalGroup;
	@OneToMany(mappedBy="") private Set<Payroll> payrolls = new HashSet<>();
	@ManyToOne private Mechanic firedMechanic;
	
	public enum ContractState {
		TERMINATED,
		IN_FORCE
	}
	
	Contract() {}
	
	public Contract(Mechanic mechanic, ContractType type, ProfessionalGroup group, double wage) {
		// Validar
		this(mechanic, type, group, LocalDate.now().plusMonths(1) ,wage);
		
		
	}

	public Contract(Mechanic mechanic, ContractType type, ProfessionalGroup group,
			LocalDate endDate, double wage) {
		ArgumentChecks.isNotNull(mechanic, "CONTRACT: invalid mechanic");
		ArgumentChecks.isNotNull(group, "CONTRACT: invalid professiona group");
		ArgumentChecks.isNotNull(type, "CONTRACT: invalid contract type");
		ArgumentChecks.isTrue(wage > 0, "CONTRACT: invalid wage");
		ArgumentChecks.isNotNull(endDate, "CONTRACT: invalid date");
		
		this.endDate = endDate;
		this.startDate = LocalDate.now();
		this.annualBaseWage = wage;
		this.state = ContractState.IN_FORCE;
		Associations.Hire.link(this, mechanic, type, group);
	}

	
	public double getAnnualBaseWage() {
		return annualBaseWage;
	}

	public Optional<LocalDate> getEndDate() {
		return Optional.of(endDate);
	}

	public double getSettlement() {
		return settlement;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public ContractState getState() {
		return state;
	}

	public Optional<Mechanic> getMechanic() {
		return Optional.of(mechanic);
	}

	public ProfessionalGroup getProfessionalGroup() {
		return professionalGroup;
	}

	public void terminate() {
		this.state = ContractState.TERMINATED;
		int workedYears = endDate.getYear() - startDate.getYear();

		if (workedYears > 0) {
			double total = payrolls.stream().sorted((date1, date2) -> 
			date1.getDate().compareTo(date2.getDate())).limit(12)
					.mapToDouble(
							payroll -> payroll.getMonthlyWage() + 
							payroll.getBonus() + payroll.getProductivityBonus() 
							+ payroll.getTrienniumPayment())
					.sum();
			
			double daySalary = total / 365;
			this.settlement = daySalary * workedYears * contracttype.getCompensationDays();
		}
		Associations.Fire.link(this);
	}

	public Optional<Mechanic> getFiredMechanic() {
		if (firedMechanic != null) {
			return Optional.of(firedMechanic);
		} else {
			return Optional.empty();
		}
	}

	public ContractType getContractType() {
		return contracttype;
	}

	public void _setMechanic(Mechanic m) {
		this.mechanic = m;
	}

	public void _setProfessionalGroup(ProfessionalGroup group) {
		this.professionalGroup = group;
	}

	public Set<Payroll> getPayrolls() {
		return new HashSet<>(payrolls);
	}

	Set<Payroll> _getPayrolls() {
		return this.payrolls;
	}
	
	public void setStartDate(LocalDate date) {
		this.startDate = date;
	}

	public void _setState(ContractState state) {
		this.state = state;
	}

	public void setFiredMechanic(Mechanic mechanic2) {
		this.firedMechanic = mechanic2;
	}

	@Override
	public int hashCode() {
		return Objects.hash(mechanic, startDate);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Contract other = (Contract) obj;
		return Objects.equals(mechanic, other.mechanic) && Objects.equals(startDate, other.startDate);
	}

	public void _setContractType(ContractType type2) {
		this.contracttype = type2;
	}

	@Override
	public String toString() {
		return "Contract [annualBaseWage=" + annualBaseWage + ", endDate=" + endDate + ", settlement=" + settlement
				+ ", startDate=" + startDate + ", state=" + state + ", type=" + contracttype + "]";
	}

	
	
}
