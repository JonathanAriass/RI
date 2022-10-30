package uo.ri.cws.domain;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import uo.ri.util.assertion.ArgumentChecks;

public class VehicleType {
	// natural attributes
	private String name;
	private double pricePerHour;

	// accidental attributes
	private Set<Vehicle> vehicles = new HashSet<>();

	public VehicleType(String nombre, double precio) {
		// validar argumentos
		ArgumentChecks.isNotBlank(nombre, "VEHICLE_TYPE: invalid name");
		ArgumentChecks.isNotEmpty(nombre, "VEHICLE_TYPE: invalid name");
//		ArgumentChecks.isNotBlank(precio, "VEHICLE_TYPE: invalid price");
//		ArgumentChecks.isNotEmpty(precio, "VEHICLE_TYPE: invalid price");
		
		
		this.name = nombre;
		this.pricePerHour = precio;
	}


	public String getName() {
		return name;
	}


	public double getPricePerHour() {
		return pricePerHour;
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
		VehicleType other = (VehicleType) obj;
		return Objects.equals(name, other.name);
	}


	@Override
	public String toString() {
		return "VehicleType [name=" + name + ", pricePerHour=" + pricePerHour + "]";
	}


	public Set<Vehicle> getVehicles() {
		return new HashSet<>( vehicles );
	}

	Set<Vehicle> _getVehicles() {
		return vehicles;
	}

}
