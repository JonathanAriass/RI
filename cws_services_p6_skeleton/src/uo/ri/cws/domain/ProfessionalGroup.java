package uo.ri.cws.domain;

import java.util.HashSet;
import java.util.Set;

import uo.ri.util.assertion.ArgumentChecks;

public class ProfessionalGroup {

	// Atributos naturales
	private String name; // Identidad natural
	private double productivityBonusPercentage;
	private double trienniumPayment;
	
	private Set<Contract> contracts = new HashSet<>();
	
	public ProfessionalGroup(String string, double d, double e) {
		// TODO Auto-generated constructor stub
		ArgumentChecks.isNotBlank(string, "PROFESSIONALGROUP: invalid name");
		ArgumentChecks.isNotNull(string, "PROFESSIONALGROUP: invalid name");
		ArgumentChecks.isTrue(d > 0, "PROFESSIONALGROUP: bonus percentage");
		ArgumentChecks.isTrue(e > 0, "PROFESSIONALGROUP: invalid triennium payment");
		
		
		this.name = string;
		this.productivityBonusPercentage = d;
		this.trienniumPayment = e;
	}



	public Set<Contract> getContracts() {
		// TODO Auto-generated method stub
		return null;
	}

}
