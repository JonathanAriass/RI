package uo.ri.cws.domain;

import java.util.Objects;

public class Voucher extends PaymentMean {
	private String code;
	private double available = 0.0;
	private String description;

	public Voucher(String codigo, double d) {
		// validaciones de parametros
		this(codigo, "no-description", d);
	}

	public Voucher(String codigo, String descrip, double d) {
		// validaciones de parametros
		this.code = codigo;
		this.description = descrip;
		this.available = d;
	}

	public String getCode() {
		return code;
	}

	public double getAvailable() {
		return available;
	}

	public String getDescription() {
		return description;
	}

	

	@Override
	public String toString() {
		return "Voucher [code=" + code + ", available=" + available +
				", description=" + description + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(code);
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
		Voucher other = (Voucher) obj;
		return Objects.equals(code, other.code);
	}

	/**
	 * Augments the accumulated (super.pay(amount) ) and decrements the available
	 * @throws IllegalStateException if not enough available to pay
	 */
	@Override
	public void pay(double amount) {

	}

}
