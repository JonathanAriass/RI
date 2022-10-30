package uo.ri.cws.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import uo.ri.util.assertion.StateChecks;

public class WorkOrder {
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
		StateChecks.isTrue(WorkOrderState.OPEN.equals(state));
		StateChecks.isFalse(this.mechanic != null);
		Associations.Assign.link(mechanic, this);
		this.state = WorkOrderState.ASSIGNED;
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
		this.state = WorkOrderState.OPEN;
	}

	/**
	 * In order to assign a work order to another mechanic is first have to
	 * be moved back to OPEN state and unlinked from the previous mechanic.
	 * @see UML_State diagrams on the problem statement document
	 * @throws IllegalStateException if
	 * 	- The work order is not in FINISHED state
	 */
	public void reopen() {

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

}
