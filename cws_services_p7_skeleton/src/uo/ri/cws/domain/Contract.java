package uo.ri.cws.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.eclipse.persistence.jpa.jpql.parser.DateTime;

import uo.ri.util.assertion.ArgumentChecks;

//@Entity
//@Table(name="tcontracts")
public class Contract {

	// Atributos naturales
	private double annualBaseWage;
	private LocalDate endDate;
	private double settlement;
	private LocalDate startDate;
	private ContractState state;
	
	// Atributos accidentales
	private Optional<Mechanic> mechanic;
	private ContractType type;
	private ProfessionalGroup professionalGroup;
	private Set<Payroll> payrolls = new HashSet<>();
	private Mechanic firedMechanic;
	
	public enum ContractState {
		TERMINATED,
		IN_FORCE
	}
	
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
//		this.mechanic = Optional.of(mechanic);
		this.type = type;
		this.professionalGroup = group;
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
		return mechanic;
	}

	public ProfessionalGroup getProfessionalGroup() {
		return professionalGroup;
	}

	public void terminate() {
		this.state = ContractState.TERMINATED;
		Associations.Fire.link(this);
	}

	public Optional<Mechanic> getFiredMechanic() {
//		if (mechanic.get().getTerminatedContracts().contains(this)) {
//			return mechanic;			
//		} else {
//			System.out.println("ENTRA AQUI");
//			return Optional.empty();
//		}
		if (firedMechanic != null) {
			return Optional.of(firedMechanic);
		} else {
			return Optional.empty();
		}
	}

	public ContractType getContractType() {
		return type;
	}

	public void _setMechanic(Optional<Mechanic> m) {
		this.mechanic = m;
	}

	public void _setProfessionalGroup(ProfessionalGroup group) {
		this.professionalGroup = group;
	}

	public Set<Payroll> getPayrolls() {
		return new HashSet<>(payrolls);
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

	@Override
	public String toString() {
		return "Contract [annualBaseWage=" + annualBaseWage + ", endDate=" + endDate + ", settlement=" + settlement
				+ ", startDate=" + startDate + ", state=" + state + ", mechanic=" + mechanic + ", type=" + type
				+ ", professionalGroup=" + professionalGroup + ", payrolls=" + payrolls + "]";
	}

	public void _setContractType(ContractType type2) {
		this.type = type2;
	}

	public Set<Payroll> _getPayrolls() {
		return this.payrolls;
	}

	
	
}
