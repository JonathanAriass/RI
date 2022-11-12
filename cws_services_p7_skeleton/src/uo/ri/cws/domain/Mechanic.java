package uo.ri.cws.domain;

import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import uo.ri.cws.domain.Contract.ContractState;
import uo.ri.cws.domain.base.BaseEntity;
import uo.ri.util.assertion.ArgumentChecks;

@Entity
@Table(name="tmechanics")
public class Mechanic extends BaseEntity {
	// natural attributes
	@Column(unique=true)@Basic(optional=false) private String dni;
	private String surname;
	private String name;

	// accidental attributes
	@OneToMany(mappedBy="mechanic") private Set<WorkOrder> assigned = new HashSet<>();
	@OneToMany(mappedBy="mechanic") private Set<Intervention> interventions = new HashSet<>();
	@Transient private Contract contract;
	@Transient private Set<Contract> terminatedContracts = new HashSet<>();
	
	Mechanic() {}
	
	public Mechanic(String dni, String nombre, String apellido) {
		//validate
		
		
		this(dni);
		this.name = nombre;
		this.surname = apellido;
	}

	public Mechanic(String dni) {
		ArgumentChecks.isNotBlank(dni, "TMECHANICS: empty mechanic dni.");
		ArgumentChecks.isNotNull(dni, "TMECHANICS: invalid mechanic dni.");
		
		this.dni = dni;
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

	public Optional<Contract> getContractInForce() {
		
		if (contract != null && contract.getState() == ContractState.IN_FORCE) {
			return Optional.of(contract);
		}

		return Optional.empty();
	}

	public Contract _getContract() {
		return this.contract;
	}

	public boolean isInForce() {
		if (contract.getState() == ContractState.IN_FORCE) {
			return true;
		}

		return false;
	}

	public Set<Contract> _getTerminatedContracts() {
		return terminatedContracts;
	}

	public Set<Contract> getTerminatedContracts() {
		return new HashSet<>( terminatedContracts );
	}

	public void _setContract(Contract contract2) {
		this.contract = contract2;
	}
	
}
