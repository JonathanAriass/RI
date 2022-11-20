package uo.ri.cws.domain;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import uo.ri.cws.domain.base.BaseEntity;
import uo.ri.util.assertion.ArgumentChecks;

@Entity
@Table(name = "tvehicles")
public class Vehicle extends BaseEntity {
	@Column(unique = true, nullable = false)
	private String plateNumber;
	@Column(name = "brand")
	private String make;
	private String model;

	@ManyToOne(optional = false)
	private Client client;
	@ManyToOne(optional = false)
	private VehicleType vehicletype;
	@OneToMany(mappedBy = "vehicle")
	private Set<WorkOrder> workorders = new HashSet<>();

	Vehicle() {
	}

	public Vehicle(String id, String marca, String modelo) {
		// validacion de parametros
		ArgumentChecks.isNotBlank(id, "VEHICLE: platenumber invalid");
		ArgumentChecks.isNotEmpty(id, "VEHICLE: platenumber invalid");
		ArgumentChecks.isNotBlank(marca, "VEHICLE: make invalid");
		ArgumentChecks.isNotEmpty(marca, "VEHICLE: make invalid");
		ArgumentChecks.isNotBlank(modelo, "VEHICLE: model invalid");
		ArgumentChecks.isNotEmpty(modelo, "VEHICLE: model invalid");

		this.plateNumber = id;
		this.make = marca;
		this.model = modelo;
	}

	public Vehicle(String plateNumber2) {
		ArgumentChecks.isNotBlank(plateNumber2, "VEHICLE: platenumber invalid");
		ArgumentChecks.isNotEmpty(plateNumber2, "VEHICLE: platenumber invalid");

		this.plateNumber = plateNumber2;
	}

	public String getPlateNumber() {
		return plateNumber;
	}

	public String getMake() {
		return make;
	}

	public String getModel() {
		return model;
	}

	@Override
	public int hashCode() {
		return Objects.hash(plateNumber);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Vehicle other = (Vehicle) obj;
		return Objects.equals(plateNumber, other.plateNumber);
	}

	@Override
	public String toString() {
		return "Vehicle [plateNumber=" + plateNumber + ", make=" + make
				+ ", model=" + model + "]";
	}

	void _setClient(Client client) {
		this.client = client;
	}

	public Client getClient() {
		return this.client;
	}

	void _setVehicleType(VehicleType type) {
		this.vehicletype = type;
	}

	public VehicleType getVehicleType() {
		return this.vehicletype;
	}

	Set<WorkOrder> _getWorkOrders() {
		return this.workorders;
	}

	public Set<WorkOrder> getWorkOrders() {
		return new HashSet<>(workorders);
	}

}
