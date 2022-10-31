package uo.ri.cws.domain;

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
	@ManyToOne private SparePart sparePart;
	@ManyToOne private Intervention intervention;

	Substitution() {}

	public Substitution(SparePart r, Intervention interv, int i) {
		// validar parametros
		ArgumentChecks.isNotNull(r);
		ArgumentChecks.isNotNull(interv);
		ArgumentChecks.isTrue(i > 0);
		
		this.quantity = i;
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

}
