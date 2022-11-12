package uo.ri.cws.domain;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import uo.ri.cws.domain.base.BaseEntity;
import uo.ri.util.assertion.ArgumentChecks;

@Entity
@Table(name="tsubstitutions",
uniqueConstraints=@UniqueConstraint(columnNames = {"sparePart_id", "intervention_id"}))
public class Substitution extends BaseEntity {
	// natural attributes
	private int quantity;

	// accidental attributes
//	@ManyToOne(optional=false) private SparePart sparePart;
//	@ManyToOne(optional=false) private Intervention intervention;
	@ManyToOne private SparePart sparePart;
	@ManyToOne private Intervention intervention;

	Substitution() {}

	public Substitution(SparePart r, Intervention interv, int i) {
		// validar parametros
		ArgumentChecks.isNotNull(r);
		ArgumentChecks.isNotNull(interv);
		ArgumentChecks.isTrue(i > 0);
		
		this.quantity = i;
//		this.intervention = interv;
//		this.sparePart = r;
		Associations.Substitute.link(r, this, interv);
	}

	void _setSparePart(SparePart sparePart) {
		this.sparePart = sparePart;
	}

	void _setIntervention(Intervention intervention) {
		this.intervention = intervention;
	}

	public int getQuantity() {
		return quantity;
	}

	public SparePart getSparePart() {
		return sparePart;
	}

	public Intervention getIntervention() {
		return intervention;
	}

	public double getAmount() {
		return quantity * sparePart.getPrice();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(intervention, sparePart);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Substitution other = (Substitution) obj;
		return Objects.equals(intervention, other.intervention) && Objects.equals(sparePart, other.sparePart);
	}


}
