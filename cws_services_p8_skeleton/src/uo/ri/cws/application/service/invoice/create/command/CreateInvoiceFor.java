package uo.ri.cws.application.service.invoice.create.command;

import java.util.List;
import java.util.Optional;

import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.InvoiceRepository;
import uo.ri.cws.application.repository.WorkOrderRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.invoice.InvoicingService.InvoiceDto;
import uo.ri.cws.application.service.invoice.assembler.InvoiceAssembler;
import uo.ri.cws.application.util.BusinessChecks;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Invoice;
import uo.ri.cws.domain.WorkOrder;
import uo.ri.cws.domain.WorkOrder.WorkOrderState;
import uo.ri.util.assertion.ArgumentChecks;

public class CreateInvoiceFor implements Command<InvoiceDto> {

	private List<String> workOrderIds;
	private WorkOrderRepository wrkrsRepo = Factory.repository.forWorkOrder();
	private InvoiceRepository invsRepo = Factory.repository.forInvoice();

	public CreateInvoiceFor(List<String> workOrderIds) {
		ArgumentChecks.isNotNull(workOrderIds, "WorkOrders list is invalid");
		ArgumentChecks.isFalse(workOrderIds.isEmpty());
		checkWorkOrdersIds(workOrderIds);

		this.workOrderIds = workOrderIds;
	}

	private void checkWorkOrdersIds(List<String> wk) {
		for (String w : wk) {
			ArgumentChecks.isNotBlank(w);
			ArgumentChecks.isNotNull(w);
			ArgumentChecks.isNotEmpty(w);
		}
	}

	@Override
	public InvoiceDto execute() throws BusinessException {

		Long number = invsRepo.getNextInvoiceNumber();
		if (number == null) {
			number = 1L;
		}

		BusinessChecks.isTrue(check(workOrderIds), "Invalid WorkOrders");

		List<WorkOrder> workOrders = wrkrsRepo.findByIds(workOrderIds);
		Invoice i = new Invoice(number, workOrders);
		invsRepo.add(i);
		return InvoiceAssembler.toDto(i);

	}

	private boolean check(List<String> workOrders) {
		for (String w : workOrders) {
			Optional<WorkOrder> aux = wrkrsRepo.findById(w);
			if (aux.isEmpty()
					|| !aux.get().getState().equals(WorkOrderState.FINISHED)) {
				return false;
			}
		}
		return true;

	}

}
