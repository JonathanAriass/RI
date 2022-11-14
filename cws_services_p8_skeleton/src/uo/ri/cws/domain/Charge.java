package uo.ri.cws.domain;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import uo.ri.cws.domain.Invoice.InvoiceState;
import uo.ri.cws.domain.base.BaseEntity;
import uo.ri.util.assertion.ArgumentChecks;

@Entity
@Table(name = "tcharges", uniqueConstraints = @UniqueConstraint(columnNames = {
		"invoice_id", "paymentMean_id" }))
public class Charge extends BaseEntity {
	// natural attributes
	private double amount = 0.0;

	// accidental attributes
	@ManyToOne
	private Invoice invoice;
	@ManyToOne
	private PaymentMean paymentMean;

	Charge() {
	}

	public Charge(Invoice invoice, PaymentMean paymentMean, double amount) {
		// Validate
		ArgumentChecks.isNotNull(invoice);
		ArgumentChecks.isNotNull(paymentMean);
		ArgumentChecks.isTrue(amount >= 0);

		this.amount = amount;
		// store the amount
		// increment the paymentMean accumulated -> paymentMean.pay( amount )
		paymentMean.pay(amount);
		// link invoice, this and paymentMean
		Associations.ToCharge.link(paymentMean, this, invoice);
	}

	/**
	 * Unlinks this charge and restores the accumulated to the payment mean
	 * 
	 * @throws IllegalStateException if the invoice is already settled
	 */
	public void rewind() {
		// asserts the invoice is not in PAID status
		ArgumentChecks.isTrue(invoice.getState() == InvoiceState.PAID);
		// decrements the payment mean accumulated ( paymentMean.pay( -amount) )
		paymentMean.pay(-amount);
		// unlinks invoice, this and paymentMean
		Associations.ToCharge.unlink(this);
	}

	public Invoice getInvoice() {
		return invoice;
	}

	public PaymentMean getPaymentMean() {
		return paymentMean;
	}

	public void _setInvoice(Invoice inovice) {
		this.invoice = inovice;
	}

	public void _setPaymentMean(PaymentMean pm) {
		this.paymentMean = pm;
	}

	public double getAmount() {
		return this.amount;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(invoice, paymentMean);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Charge other = (Charge) obj;
		return Objects.equals(invoice, other.invoice)
				&& Objects.equals(paymentMean, other.paymentMean);
	}

	@Override
	public String toString() {
		return "Charge [amount=" + amount + ", invoice=" + invoice
				+ ", paymentMean=" + paymentMean + "]";
	}

}
