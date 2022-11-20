package uo.ri.cws.infrastructure.persistence.jpa.repository;

import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.repository.PaymentMeanRepository;
import uo.ri.cws.domain.CreditCard;
import uo.ri.cws.domain.PaymentMean;
import uo.ri.cws.domain.Voucher;
import uo.ri.cws.infrastructure.persistence.jpa.util.BaseJpaRepository;

public class PaymentMeanJpaRepository extends BaseJpaRepository<PaymentMean>
		implements PaymentMeanRepository {

	@Override
	public List<PaymentMean> findPaymentMeansByClientId(String id) {

		return null;
	}

	@Override
	public List<PaymentMean> findPaymentMeansByInvoiceId(String idFactura) {

		return null;
	}

	@Override
	public List<PaymentMean> findByClientId(String id) {

		return null;
	}

	@Override
	public Object[] findAggregateVoucherDataByClientId(String id) {

		return null;
	}

	@Override
	public Optional<CreditCard> findCreditCardByNumber(String pan) {

		return null;
	}

	@Override
	public List<Voucher> findVouchersByClientId(String id) {

		return null;
	}

	@Override
	public Optional<Voucher> findVoucherByCode(String code) {

		return null;
	}

}
