package uo.ri.cws.domain;

import uo.ri.cws.domain.Invoice.InvoiceState;
import uo.ri.util.assertion.ArgumentChecks;

public class Charge {
	// natural attributes
	private double amount = 0.0;

	// accidental attributes
	private Invoice invoice;
	private PaymentMean paymentMean;

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

}
