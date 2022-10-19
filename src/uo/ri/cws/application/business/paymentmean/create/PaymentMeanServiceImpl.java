package uo.ri.cws.application.business.paymentmean.create;

import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.business.BusinessException;
import uo.ri.cws.application.business.paymentmean.PaymentMeanService;
import uo.ri.cws.application.business.paymentmean.voucher.VoucherService.VoucherBLDto;

public class PaymentMeanServiceImpl implements PaymentMeanService {

	@Override
	public void addCardPaymentMean(Card_BLDto card) throws BusinessException {
		// TODO Auto-generated method stub

	}

	@Override
	public void deletePaymentMean(String id) throws BusinessException {
		// TODO Auto-generated method stub

	}

	@Override
	public Optional<Card_BLDto> findCreditCardById(String id) throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<PaymentMeanBLDto> findById(String id) throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PaymentMeanBLDto> findPaymentMeansByClientId(String id) throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addVoucherPaymentMean(VoucherBLDto voucher) throws BusinessException {
		// TODO Auto-generated method stub
		
	}

}
