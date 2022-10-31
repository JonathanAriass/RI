package uo.ri.cws.domain;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import uo.ri.util.assertion.ArgumentChecks;

public class Vehicle {
	private String plateNumber;
	private String make;
	private String model;
	
	private Client client;
	private VehicleType type;
	private Set<WorkOrder> workorders = new HashSet<>();
	
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
		return "Vehicle [plateNumber=" + plateNumber + ", make=" + make + 
				", model=" + model + "]";
	}

	void _setClient(Client client) {
		// TODO Auto-generated method stub
		this.client = client;
	}

	public Client getClient() {
		return this.client;
	}

	void _setVehicleType(VehicleType type) {
		this.type = type;
	}

	public VehicleType getVehicleType() {
		return this.type;
	}

	Set<WorkOrder> _getWorkOrders() {
		return this.workorders;
	}

	public Set<WorkOrder> getWorkOrders() {
		return new HashSet<>(workorders);
	}
	
}
