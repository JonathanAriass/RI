package uo.ri.cws.domain;

import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import uo.ri.cws.domain.Contract.ContractState;
import uo.ri.cws.domain.base.BaseEntity;
import uo.ri.util.assertion.ArgumentChecks;

@Entity
@Table(name = "tmechanics")
public class Mechanic extends BaseEntity {
	// natural attributes
	@Column(unique = true)
	@Basic(optional = false)
	private String dni;
	@Basic(optional = false)
	private String surname;
	@Basic(optional = false)
	private String name;

	// accidental attributes
	@OneToMany(mappedBy = "mechanic")
	private Set<WorkOrder> assigned = new HashSet<>();

	@OneToMany(mappedBy = "mechanic")
	private Set<Intervention> interventions = new HashSet<>();

	@OneToOne(mappedBy = "mechanic")
	private Contract contract;

	@OneToMany(mappedBy = "firedMechanic")
	private Set<Contract> terminatedContracts = new HashSet<>();

	Mechanic() {
	}

	public Mechanic(String dni, String nombre, String apellido) {
		ArgumentChecks.isNotBlank(nombre, "MECHANICS: empty mechanic name.");
		ArgumentChecks.isNotNull(nombre, "MECHANICS: invalid mechanic name.");
		ArgumentChecks.isNotBlank(apellido,
				"MECHANICS: empty mechanic surname.");
		ArgumentChecks.isNotNull(apellido,
				"MECHANICS: invalid mechanic surname.");
		ArgumentChecks.isNotBlank(dni, "MECHANICS: empty mechanic dni.");
		ArgumentChecks.isNotNull(dni, "MECHANICS: invalid mechanic dni.");

		this.dni = dni;
		this.name = nombre;
		this.surname = apellido;
	}

	public Mechanic(String dni) {
		this(dni, "noname", "nosurname");
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
		return "Mechanic [dni=" + dni + ", surname=" + surname + ", name="
				+ name + "]";
	}

	public Set<WorkOrder> getAssigned() {
		return new HashSet<>(assigned);
	}

	Set<WorkOrder> _getAssigned() {
		return assigned;
	}

	public Set<Intervention> getInterventions() {
		return new HashSet<>(interventions);
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
		return new HashSet<>(terminatedContracts);
	}

	public void _setContract(Contract contract2) {
		this.contract = contract2;
	}

	public void setName(String name2) {
		this.name = name2;
	}

	public void setSurname(String surname2) {
		this.surname = surname2;
	}

}
