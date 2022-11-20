package uo.ri.cws.domain;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import uo.ri.cws.domain.base.BaseEntity;
import uo.ri.util.assertion.ArgumentChecks;

@Entity
@Table(name = "tvehicletypes", uniqueConstraints = @UniqueConstraint(columnNames = {
		"name" }))

public class VehicleType extends BaseEntity {
	// natural attributes
	@Column(unique = true)
	@Basic(optional = false)
	private String name;
	private double pricePerHour;

	// accidental attributes
	@OneToMany(mappedBy = "vehicletype")
	private Set<Vehicle> vehicles = new HashSet<>();

	VehicleType() {
	}

	public VehicleType(String name2) {
		this(name2, 0.0);
	}

	public VehicleType(String nombre, double precio) {
		// validar argumentos
		ArgumentChecks.isNotBlank(nombre, "VEHICLE_TYPE: invalid name");
		ArgumentChecks.isNotEmpty(nombre, "VEHICLE_TYPE: invalid name");
		ArgumentChecks.isTrue(precio >= 0.0);

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
		return "VehicleType [name=" + name + ", pricePerHour=" + pricePerHour
				+ "]";
	}

	public Set<Vehicle> getVehicles() {
		return new HashSet<>(vehicles);
	}

	Set<Vehicle> _getVehicles() {
		return vehicles;
	}

}
