package uo.ri.cws.domain;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import uo.ri.cws.domain.base.BaseEntity;
import uo.ri.util.assertion.ArgumentChecks;

@Entity
@Table(name="tinterventions")
public class Intervention extends BaseEntity {
	// natural attributes
	private LocalDateTime date;
	private int minutes;

	// accidental attributes
	@Transient private WorkOrder workOrder;
	@Transient private Mechanic mechanic;
	@OneToMany(mappedBy="intervention") private Set<Substitution> substitutions = new HashSet<>();

	Intervention() {}
	
	public Intervention(Mechanic m, WorkOrder wo, int i) {
		ArgumentChecks.isTrue(i >= 0);
		ArgumentChecks.isNotNull(m, "INTERVENTION: mechanic invalid");
		ArgumentChecks.isNotNull(wo, "INTERVENTION: workorder invalid");
		
		this.mechanic = m;
		this.workOrder = wo;
		this.minutes = i;
		Associations.Intervene.link(workOrder, this, mechanic);
	}

	public LocalDateTime getDate() {
		return date;
	}

	public int getMinutes() {
		return minutes;
	}

	void _setWorkOrder(WorkOrder workOrder) {
		this.workOrder = workOrder;
	}
	
	public WorkOrder getWorkOrder() {
		return this.workOrder;
	}

	public Mechanic getMechanic() {
		return this.mechanic;
	}

	void _setMechanic(Mechanic mechanic) {
		this.mechanic = mechanic;
	}

	public Set<Substitution> getSubstitutions() {
		return new HashSet<>( substitutions );
	}

	Set<Substitution> _getSubstitutions() {
		return substitutions;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(date, mechanic, workOrder);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Intervention other = (Intervention) obj;
		return Objects.equals(date, other.date) && Objects.equals(mechanic, other.mechanic)
				&& Objects.equals(workOrder, other.workOrder);
	}
	
	@Override
	public String toString() {
		return "Intervention [date=" + date + ", minutes=" + minutes + ","
				+ " substitutions=" + substitutions + "]";
	}

	public double getAmount() {
		double a = 0;
		for (Substitution s : substitutions) {
			if (s.getIntervention() == this) {
				a += s.getAmount();
			}
		}
		return (workOrder.getVehicle().getVehicleType().getPricePerHour()
				* ((double) minutes / 60)) + a;
	}

}
