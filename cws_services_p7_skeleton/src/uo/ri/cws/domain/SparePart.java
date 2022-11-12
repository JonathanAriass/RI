package uo.ri.cws.domain;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import uo.ri.cws.domain.base.BaseEntity;
import uo.ri.util.assertion.ArgumentChecks;

@Entity
@Table(name="tspareparts")
public class SparePart extends BaseEntity {
	// natural attributes
	@Column(unique=true)@Basic(optional=false) private String code;
	private String description;
	private double price;

	// accidental attributes
	@OneToMany(mappedBy="sparePart") private Set<Substitution> substitutions = new HashSet<>();

	SparePart() {}
	
	public SparePart(String codigo, String descripcion, double precio) {
		//Validacion
		ArgumentChecks.isNotBlank(codigo, "SPAREPART: invalid code");
		ArgumentChecks.isNotEmpty(codigo, "SPAREPART: invalid code");
		
		this.code = codigo;
		this.description = descripcion;
		this.price = precio;
	}


	public String getCode() {
		return code;
	}


	public String getDescription() {
		return description;
	}


	public double getPrice() {
		return price;
	}


	@Override
	public int hashCode() {
		return Objects.hash(code);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SparePart other = (SparePart) obj;
		return Objects.equals(code, other.code);
	}


	@Override
	public String toString() {
		return "SparePart [code=" + code + ", description=" + description + ", price=" + price + "]";
	}


	public Set<Substitution> getSubstitutions() {
		return new HashSet<>( substitutions );
	}

	Set<Substitution> _getSubstitutions() {
		return this.substitutions;
	}

}
