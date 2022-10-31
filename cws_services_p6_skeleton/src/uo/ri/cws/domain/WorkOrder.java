package uo.ri.cws.domain;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.assertion.StateChecks;

public class WorkOrder {
	@Override
	public int hashCode() {
		return Objects.hash(date, vehicle);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		WorkOrder other = (WorkOrder) obj;
		return Objects.equals(date, other.date) && Objects.equals(vehicle, other.vehicle);
	}

	public enum WorkOrderState {
		OPEN,
		ASSIGNED,
		FINISHED,
		INVOICED
	}

	// natural attributes
	private LocalDateTime date;
	private String description;

	private double amount = 0.0;
	private WorkOrderState state = WorkOrderState.OPEN;

	// accidental attributes
	private Vehicle vehicle;
	private Mechanic mechanic;
	private Invoice invoice;
	private Set<Intervention> interventions = new HashSet<>();

	public WorkOrder(Vehicle v, String descr) {
		// validacion de parametros
		ArgumentChecks.isNotNull(v);
		ArgumentChecks.isNotNull(descr);
		
		this.description = descr;
		this.date = LocalDateTime.now();
		
		// se realiza el linkeo
		Associations.Fix.link(v, this);
	}

	/**
	 * Changes it to INVOICED state given the right conditions
	 * This method is called from Invoice.addWorkOrder(...)
	 * @see UML_State diagrams on the problem statement document
	 * @throws IllegalStateException if
	 * 	- The work order is not FINISHED, or
	 *  - The work order is not linked with the invoice
	 */
	public void markAsInvoiced() {
		StateChecks.isTrue(state != WorkOrderState.ASSIGNED);
		StateChecks.isNotNull(invoice);
		this.state = WorkOrderState.INVOICED;
	}

	/**
	 * Changes it to FINISHED state given the right conditions and
	 * computes the amount
	 *
	 * @see UML_State diagrams on the problem statement document
	 * @throws IllegalStateException if
	 * 	- The work order is not in ASSIGNED state, or
	 *  - The work order is not linked with a mechanic
	 */
	public void markAsFinished() {
		StateChecks.isTrue(state == WorkOrderState.ASSIGNED);
		StateChecks.isNotNull(mechanic);
		computeAmount();
		this.state = WorkOrderState.FINISHED;
	}

	private void computeAmount() {
		double total = 0;
		
		for (Intervention i : interventions) {
			total += i.getAmount();
		}
		
		this.amount = total;
	}

	/**
	 * Changes it back to FINISHED state given the right conditions
	 * This method is called from Invoice.removeWorkOrder(...)
	 * @see UML_State diagrams on the problem statement document
	 * @throws IllegalStateException if
	 * 	- The work order is not INVOICED, or
	 *  - The work order is still linked with the invoice
	 */
	public void markBackToFinished() {
		StateChecks.isTrue(state == WorkOrderState.INVOICED);
		StateChecks.isNull(invoice);
		computeAmount();
		this.state = WorkOrderState.FINISHED;
	}

	/**
	 * Links (assigns) the work order to a mechanic and then changes its state
	 * to ASSIGNED
	 * @see UML_State diagrams on the problem statement document
	 * @throws IllegalStateException if
	 * 	- The work order is not in OPEN state, or
	 *  - The work order is already linked with another mechanic
	 */
	public void assignTo(Mechanic mechanic) {
		StateChecks.isTrue(WorkOrderState.OPEN == state);
		StateChecks.isFalse(this.mechanic != null);
		this.state = WorkOrderState.ASSIGNED;
		Associations.Assign.link(mechanic, this);
	}

	/**
	 * Unlinks (deassigns) the work order and the mechanic and then changes
	 * its state back to OPEN
	 * @see UML_State diagrams on the problem statement document
	 * @throws IllegalStateException if
	 * 	- The work order is not in ASSIGNED state
	 */
	public void desassign() {
		Associations.Assign.unlink(mechanic, this);
	}

	/**
	 * In order to assign a work order to another mechanic is first have to
	 * be moved back to OPEN state and unlinked from the previous mechanic.
	 * @see UML_State diagrams on the problem statement document
	 * @throws IllegalStateException if
	 * 	- The work order is not in FINISHED state
	 */
	public void reopen() {
		StateChecks.isTrue(state == WorkOrderState.FINISHED,
				"The work order is not in FINISHED status");
		this.state = WorkOrderState.OPEN;
		Associations.Assign.unlink(mechanic, this);
	}

	public Set<Intervention> getInterventions() {
		return new HashSet<>( interventions );
	}

	Set<Intervention> _getInterventions() {
		return interventions;
	}

	void _setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}

	void _setMechanic(Mechanic mechanic) {
		this.mechanic = mechanic;
	}

	void _setInvoice(Invoice invoice) {
		this.invoice = invoice;
	}

	public Mechanic getMechanic() {
		return this.mechanic;
	}

	public Invoice getInvoice() {
		return this.invoice;
	}

	public boolean isInvoiced() {
		return (WorkOrderState.INVOICED.equals(state)) ? true : false;
	}

	public boolean isFinished() {
		return (WorkOrderState.FINISHED.equals(state)) ? true : false;
	}

	public double getAmount() {
		return this.amount;
	}

	public Vehicle getVehicle() {
		return vehicle;
	}
	
	public LocalDateTime getDate() {
		return date;
	}

	public String getDescription() {
		return description;
	}

	public WorkOrderState getState() {
		return state;
	}

}
