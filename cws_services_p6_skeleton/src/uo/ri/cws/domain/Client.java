package uo.ri.cws.domain;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import uo.ri.util.assertion.ArgumentChecks;

public class Client {
	// Atributos naturales
	private String dni; // Identidad natural
	private String name;
	private String surname;
	private String email;
	private String phone;
	private Address address;
	
	private Set<Vehicle> vehicles =  new HashSet<>();
	private Set<PaymentMean> payments = new HashSet<>();
	
	public Client(String dni, String nombre, String apellidos) {
		// validacion de parametros
		ArgumentChecks.isNotEmpty(dni, "CLIENT: invalid dni");
		ArgumentChecks.isNotBlank(dni, "CLIENT: invalid dni");	
		ArgumentChecks.isNotEmpty(nombre, "CLIENT: invalid name");
		ArgumentChecks.isNotBlank(nombre, "CLIENT: invalid name");
		ArgumentChecks.isNotEmpty(apellidos, "CLIENT: invalid surname");
		ArgumentChecks.isNotBlank(apellidos, "CLIENT: invalid surname");	
		
		this.dni = dni;
		this.name = nombre;
		this.surname = apellidos;
	}
	
	public String getDni() {
		return dni;
	}
	public String getName() {
		return name;
	}
	public String getSurname() {
		return surname;
	}
	public String getEmail() {
		return email;
	}
	public String getPhone() {
		return phone;
	}
	public Address getAddress() {
		return address;
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
		Client other = (Client) obj;
		return Objects.equals(dni, other.dni);
	}
	@Override
	public String toString() {
		return "Client [dni=" + dni + ", name=" + name + ", surname=" + surname + ", email=" + email + ", phone="
				+ phone + ", address=" + address + "]";
	}

	Set<Vehicle> _getVehicles() {
		return vehicles;
	}

	public Set<Vehicle> getVehicles() {
		return new HashSet<>(vehicles);
	}

	Set<PaymentMean> _getPaymentMeans() {
		return this.payments;
	}

	public Set<PaymentMean> getPaymentMeans() {
		return new HashSet<>(payments);
	}
	
	
}

