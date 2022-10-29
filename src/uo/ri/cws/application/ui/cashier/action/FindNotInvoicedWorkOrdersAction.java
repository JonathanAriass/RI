package uo.ri.cws.application.ui.cashier.action;

import java.util.List;

import console.Console;
import menu.Action;
import uo.ri.cws.application.business.BusinessException;
import uo.ri.cws.application.business.invoice.InvoicingService;
import uo.ri.cws.application.business.invoice.InvoicingService.WorkOrderForInvoicingBLDto;
import uo.ri.cws.application.business.invoice.create.InvoicingServiceImpl;
import uo.ri.cws.application.ui.util.Printer;

public class FindNotInvoicedWorkOrdersAction implements Action {

	@Override
	public void execute() throws BusinessException {
		String dni = Console.readString("Client DNI ");
		
		Console.println("\nClient's not invoiced work orders\n");  

		// necesitamos la lista para hacer print
		InvoicingService service = new InvoicingServiceImpl();
		List<WorkOrderForInvoicingBLDto> result = service.findNotInvoicedWorkOrdersByClientDni(dni);
		Printer.printInvoicingWorkOrders(result);
	}

}