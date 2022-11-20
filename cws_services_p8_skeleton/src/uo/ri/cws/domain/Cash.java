package uo.ri.cws.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "tcashes")
public class Cash extends PaymentMean {

	Cash() {
	}

	public Cash(Client c) {
		super();
		Associations.Pay.link(c, this);
	}

}
