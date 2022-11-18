package uo.ri.cws.application.service.invoice.assembler;

import uo.ri.cws.application.service.invoice.InvoicingService.InvoiceDto;
import uo.ri.cws.domain.Invoice;

public class InvoiceAssembler {

	public static InvoiceDto toDto(Invoice arg) {
		InvoiceDto result = new InvoiceDto();
		result.id = arg.getId();
		result.number = arg.getNumber();
		result.state = arg.getState().toString();
		result.date = arg.getDate();
		result.total = arg.getAmount();
		result.vat = arg.getVat();
		return result;
	}

}
