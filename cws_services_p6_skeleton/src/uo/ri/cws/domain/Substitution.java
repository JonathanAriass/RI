package uo.ri.cws.domain;

import uo.ri.util.assertion.ArgumentChecks;

public class Substitution {
	// natural attributes
	private int quantity;

	// accidental attributes
	private SparePart sparePart;
	private Intervention intervention;



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
