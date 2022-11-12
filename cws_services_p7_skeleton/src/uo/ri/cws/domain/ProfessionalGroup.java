package uo.ri.cws.domain;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import uo.ri.util.assertion.ArgumentChecks;

public class ProfessionalGroup {

	// Atributos naturales
	private String name; // Identidad natural
	private double productivityBonusPercentage;
	private double trienniumPayment;
	
	private Set<Contract> contracts = new HashSet<>();
	
	public ProfessionalGroup(String string, double d, double e) {
		ArgumentChecks.isNotBlank(string, "PROFESSIONALGROUP: invalid name");
		ArgumentChecks.isNotNull(string, "PROFESSIONALGROUP: invalid name");
		ArgumentChecks.isTrue(d > 0, "PROFESSIONALGROUP: bonus percentage");
		ArgumentChecks.isTrue(e > 0, "PROFESSIONALGROUP: invalid triennium payment");
		
		this.name = string;
		this.productivityBonusPercentage = d;
		this.trienniumPayment = e;
	}

	
	public String getName() {
		return name;
	}

	public double getProductivityBonusPercentage() {
		return productivityBonusPercentage;
	}

	public double getTrienniumPayment() {
		return trienniumPayment;
	}

	public Set<Contract> getContracts() {
		return new HashSet<>( contracts );
	}
	
	Set<Contract> _getContracts() {
		return contracts;
	}

	@Override
	public int hashCode() {
		return Objects.hash(name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProfessionalGroup other = (ProfessionalGroup) obj;
		return Objects.equals(name, other.name);
	}

	@Override
	public String toString() {
		return "ProfessionalGroup [name=" + name + "]";
	}

}
