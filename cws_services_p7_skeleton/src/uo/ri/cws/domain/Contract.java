package uo.ri.cws.domain;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

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
	private Mechanic mechanic;
	private ContractType type;
	private ProfessionalGroup professionalGroup;
	private Set<Payroll> payrolls = new HashSet<>();
	
	public enum ContractState {
		TERMINATED,
		IN_FORCE
	}
	
	public Contract(Mechanic mechanic, ContractType type, ProfessionalGroup group, double wage) {
		// Validar
		ArgumentChecks.isNotNull(mechanic, "CONTRACT: invalid mechanic");
		ArgumentChecks.isNotNull(group, "CONTRACT: invalid professiona group");
		ArgumentChecks.isNotNull(type, "CONTRACT: invalid contract type");
		ArgumentChecks.isTrue(wage > 0, "CONTRACT: invalid wage");
		
		this.mechanic = mechanic;
		this.type = type;
		this.professionalGroup = group;
		this.annualBaseWage = wage;
		Associations.Hire.link(this, mechanic);
	}

	public Contract(Mechanic mechanic, ContractType ct, ProfessionalGroup group,
			LocalDate endDate, double d) {
		this(mechanic, ct, group, d);
		ArgumentChecks.isNotNull(endDate, "CONTRACT: invalid date");
		this.endDate = endDate;
	}

	public double getAnnualBaseWage() {
		return annualBaseWage;
	}

	public LocalDate getEndDate() {
		return endDate;
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
		// TODO Auto-generated method stub
		
	}

	public Optional<Mechanic> getFiredMechanic() {
		if (mechanic.getTerminatedContracts().contains(this)) {
			return Optional.of(mechanic);			
		} else {
			return Optional.empty();
		}
	}

	public Object getContractType() {
		// TODO Auto-generated method stub
		return null;
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

	public void setStartDate(LocalDate date) {
		this.startDate = date;
	}

	public void _setState(ContractState state) {
		this.state = state;
	}

	
	
}
