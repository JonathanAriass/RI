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
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import uo.ri.cws.domain.base.BaseEntity;
import uo.ri.util.assertion.ArgumentChecks;

@Entity
@Table(name="tcontracts",
uniqueConstraints=@UniqueConstraint(columnNames = {"mechanic_id"}))
public class Contract extends BaseEntity {

	// Atributos naturales
	private double annualBaseWage;
	private LocalDate endDate;
	private double settlement;
	@Basic(optional=false) private LocalDate startDate;
	private ContractState state;
	
	// Atributos accidentales
//	@ManyToOne private Optional<Mechanic> mechanic; // TODO: cambiar el optional por un mecanico normal
	@ManyToOne private Mechanic mechanic;
	@ManyToOne private ContractType type;
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
//		this.mechanic = Optional.of(mechanic);
//		this.type = type;
//		this.professionalGroup = group;
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
//		if (startDate.isBefore(endDate.minusYears(1))) {
//			settlement = annualBaseWage / 12;
//		}
		int years = endDate.getYear() - startDate.getYear();

		if (years > 0) {
			double salary = payrolls.stream().sorted((p1, p2) -> -p1.getDate().compareTo(p2.getDate())).limit(12)
					.mapToDouble(
//							p -> p.getMonthlyWage())
							p -> p.getMonthlyWage() + p.getBonus() + p.getProductivityBonus() + p.getTrienniumPayment())
					.sum();
//			double salary = 0.0;
//			int i = 0;
//			for (Payroll p : payrolls) {
//				if (i==12) {
//					break;
//				} else {
//					salary += 
//				}
//				i++;
//			}
			
			double media = salary / 365;
			this.settlement = media * years * type.getCompensationDays();
		}
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

	@Override
	public String toString() {
		return "Contract [annualBaseWage=" + annualBaseWage + ", endDate=" + endDate + ", settlement=" + settlement
				+ ", startDate=" + startDate + ", state=" + state + ", mechanic=" + mechanic + ", type=" + type
				+ ", professionalGroup=" + professionalGroup + ", payrolls=" + payrolls + "]";
	}

	public void _setContractType(ContractType type2) {
		this.type = type2;
	}

	
	
}
