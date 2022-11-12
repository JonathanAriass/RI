package uo.ri.cws.domain;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import uo.ri.util.assertion.ArgumentChecks;

public class ContractType {

	private String name;
	private double compensationDays;
	
	private Set<Contract> contracts = new HashSet<>();
	
	public ContractType(String string, double d) {
		ArgumentChecks.isNotBlank(string, "CONTRACTTYPE: invalid name");
		ArgumentChecks.isNotNull(string, "CONTRACTTYPE: invalid name");
		ArgumentChecks.isTrue(d > 0, "CONTRACTTYPE: invalid compensation days");
		
		this.name = string;
		this.compensationDays = d;
	}
	

	public String getName() {
		return name;
	}

	public double getCompensationDays() {
		return compensationDays;
	}

	
	@Override
	public String toString() {
		return "ContractType [name=" + name + ", compensationDays=" + compensationDays + "]";
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
		ContractType other = (ContractType) obj;
		return Objects.equals(name, other.name);
	}


	public Set<Contract> getContracts() {
		return new HashSet<>(contracts);
	}


	Set<Contract> _getContracts() {
		return contracts;
	}
}
