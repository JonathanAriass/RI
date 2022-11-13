package uo.ri.cws.domain;

import java.time.LocalDate;
import java.util.Objects;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.assertion.StateChecks;

@Entity
@Table(name="tcreditcards")
public class CreditCard extends PaymentMean {
	
	@Column(unique=true)@Basic(optional=false) private String number;
	private String type;
	@Basic(optional=false) private LocalDate validThru;

	CreditCard() {}
	
	public CreditCard(String numero) {
		this(numero, "UNKNOWN", LocalDate.now().plusDays(1));
	}
	public CreditCard(String numero, String tipo, LocalDate v) {
		ArgumentChecks.isNotEmpty(numero, "CREDITCARD: invalid number");
		ArgumentChecks.isNotBlank(numero, "CREDITCARD: invalid number");	
		ArgumentChecks.isNotEmpty(tipo, "CREDITCARD: invalid type");
		ArgumentChecks.isNotBlank(tipo, "CREDITCARD: invalid type");
		ArgumentChecks.isNotNull(v, "CREDITCARD: invalid date");		
		
		this.number = numero;
		this.type = tipo;
		this.validThru = v;
	}
	public String getNumber() {
		return number;
	}
	public String getType() {
		return type;
	}
	public LocalDate getValidThru() {
		return validThru;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(number);
		return result;
	}
	@Override
	public String toString() {
		return "CreditCard [number=" + number + ", type=" + type + ", validThru=" + validThru + "]";
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		CreditCard other = (CreditCard) obj;
		return Objects.equals(number, other.number);
	}
	
	@Override
	public void pay(double amount) {
		StateChecks.isTrue(isValidNow());
		super.pay(amount);
	}
	
	public void setValidThru(LocalDate minusDays) {
		StateChecks.isTrue(validThru.getDayOfMonth() < minusDays.getDayOfMonth()
				&& validThru.getMonthValue() <= minusDays.getMonthValue()
				&& validThru.getYear() <= minusDays.getYear());
		this.validThru = minusDays;
	}
	
	public boolean isValidNow() {
		if (validThru.isAfter(LocalDate.now())) {
			return true;
		} else {
			return false;			
		}
	}
	
}
