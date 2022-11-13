package uo.ri.cws.domain;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import uo.ri.cws.domain.WorkOrder.WorkOrderState;
import uo.ri.cws.domain.base.BaseEntity;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.assertion.StateChecks;

@Entity
@Table(name="tinvoices")
public class Invoice extends BaseEntity {
	public enum InvoiceState { NOT_YET_PAID, PAID }

	// natural attributes
	@Column(unique=true) @Basic(optional=false) private Long number; // Identidad
	private LocalDate date;
	private double amount;
	private double vat;
	private InvoiceState state = InvoiceState.NOT_YET_PAID;

	// accidental attributes
	@OneToMany(mappedBy="invoice") private Set<WorkOrder> workOrders = new HashSet<>();
	@OneToMany(mappedBy="invoice") private Set<Charge> charges = new HashSet<>();

	Invoice() {}
	
	public Invoice(Long number) {
		this(number, LocalDate.now(), List.of());
	}

	public Invoice(Long number, LocalDate date) {
		this(number, date, List.of());
	}

	public Invoice(Long number, List<WorkOrder> workOrders) {
		this(number, LocalDate.now(), workOrders);
	}

	public Invoice(Long number, LocalDate date, List<WorkOrder> workOrders) {
		ArgumentChecks.isTrue(number >= 0, "INVOICE: invalid number");
		ArgumentChecks.isNotNull(date, "INVOICE: invalid date");
		ArgumentChecks.isNotNull(workOrders, "INVOICE: invalid workorders");
	
		// store the number
		this.number = number;
		
		// store a copy of the date
		this.date = date;
		
		// add every work order calling addWorkOrder( w )
		addWorkOrders(workOrders);
	}

	private void addWorkOrders(List<WorkOrder> workOrders2) {
		for (WorkOrder w : workOrders2)
			addWorkOrder(w);
	}

	/**
	 * Computes amount and vat (vat depends on the date)
	 */
	private void computeAmount() {
		double total = 0;
		
		for (WorkOrder w : workOrders) {
			total += (w.getAmount()) * (getVatType() + 1);
		}
		
		this.amount = Math.rint(total * 100) / 100;
	}

	private double getVatType() {
		LocalDate july2012 = LocalDate.of(2012, 7, 1);
		return date.isAfter(july2012) ? 0.21 : 0.18;
	}
	
	/**
	 * Adds (double links) the workOrder to the invoice and updates the amount and vat
	 * @param workOrder
	 * @see UML_State diagrams on the problem statement document
	 * @throws IllegalStateException if the invoice status is not NOT_YET_PAID
	 */
	public void addWorkOrder(WorkOrder workOrder) {
		StateChecks.isTrue(InvoiceState.NOT_YET_PAID.equals(this.state));
		StateChecks.isTrue(workOrder.getState() == WorkOrderState.FINISHED);
		Associations.ToInvoice.link(this, workOrder);
		workOrder.markAsInvoiced();
		workOrders.add(workOrder);
		computeAmount();
	}

	/**
	 * Removes a work order from the invoice and recomputes amount and vat
	 * @param workOrder
	 * @see UML_State diagrams on the problem statement document
	 * @throws IllegalStateException if the invoice status is not NOT_YET_PAID
	 */
	public void removeWorkOrder(WorkOrder workOrder) {
		Associations.ToInvoice.unlink(this, workOrder);
		workOrder.markBackToFinished();
		workOrders.remove(workOrder);
		computeAmount();
	}

	/**
	 * Marks the invoice as PAID, but
	 * @throws IllegalStateException if
	 * 	- Is already settled
	 *  - Or the amounts paid with charges to payment means do not cover
	 *  	the total of the invoice
	 */
	public void settle() {
		ArgumentChecks.isTrue(state.equals(InvoiceState.NOT_YET_PAID));
		this.state = InvoiceState.PAID;
	}

	public Set<WorkOrder> getWorkOrders() {
		return new HashSet<>( workOrders );
	}

	Set<WorkOrder> _getWorkOrders() {
		return workOrders;
	}

	public Set<Charge> getCharges() {
		return new HashSet<>( charges );
	}

	Set<Charge> _getCharges() {
		return charges;
	}

	public Long getNumber() {
		return number;
	}

	public LocalDate getDate() {
		return date;
	}

	public double getAmount() {
		return amount;
	}

	public double getVat() {
		return vat;
	}

	public InvoiceState getState() {
		return state;
	}

	@Override
	public int hashCode() {
		return Objects.hash(number);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Invoice other = (Invoice) obj;
		return Objects.equals(number, other.number);
	}

	@Override
	public String toString() {
		return "Invoice [number=" + number + ", date=" + date + ", amount=" + amount + ", vat=" + vat + ", state="
				+ state + "]";
	}

	public boolean isNotSettled() {
		return state.equals(InvoiceState.NOT_YET_PAID);
	}

}
