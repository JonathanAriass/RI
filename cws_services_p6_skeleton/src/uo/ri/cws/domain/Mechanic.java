package uo.ri.cws.domain;

import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import uo.ri.cws.domain.Contract.ContractState;

public class Mechanic {
	// natural attributes
	private String dni;
	private String surname;
	private String name;

	// accidental attributes
	private Set<WorkOrder> assigned = new HashSet<>();
	private Set<Intervention> interventions = new HashSet<>();
	private Set<Contract> contracts = new HashSet<>();
	
	public Mechanic(String dni, String nombre, String apellido) {
		//validate
		
		this.dni = dni;
		this.name = nombre;
		this.surname = apellido;
	}

	public String getDni() {
		return dni;
	}

	public String getSurname() {
		return surname;
	}

	public String getName() {
		return name;
	}

	@Override
	public int hashCode() {
		return Objects.hash(dni);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Mechanic other = (Mechanic) obj;
		return Objects.equals(dni, other.dni);
	}
	
	@Override
	public String toString() {
		return "Mechanic [dni=" + dni + ", surname=" + surname + ", name=" + 
				name + "]";
	}

	public Set<WorkOrder> getAssigned() {
		return new HashSet<>( assigned );
	}

	Set<WorkOrder> _getAssigned() {
		return assigned;
	}

	public Set<Intervention> getInterventions() {
		return new HashSet<>( interventions );
	}

	Set<Intervention> _getInterventions() {
		return interventions;
	}

	public Set<Contract> getTerminatedContracts() {
		Set<Contract> result = new HashSet<>();
		
		for (Contract c : contracts) {
			if (c.getState() == ContractState.TERMINATED) {
				result.add(c);
			}
		}
		
		return result;
	}

	public Optional<Contract> getContractInForce() {
		for (Contract c : contracts) {
			if (c.getState() == ContractState.IN_FORCE) {
				return Optional.of(c);
			}
		}
		
		return Optional.empty();
	}

	public Set<Contract> _getContracts() {
		return this.contracts;
	}

	public boolean isInForce() {
		for (Contract c : contracts) {
			if (c.getState() == ContractState.IN_FORCE) {
				return true;
			}
		}
		return false;
	}

}
