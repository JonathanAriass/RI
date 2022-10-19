package uo.ri.cws.application.business.invoice.create.commands;

import java.util.ArrayList;
import java.util.List;

import assertion.Argument;
import uo.ri.cws.application.business.BusinessException;
import uo.ri.cws.application.business.invoice.InvoicingService.WorkOrderForInvoicingBLDto;
import uo.ri.cws.application.business.invoice.assembler.InvoicingAssembler;
import uo.ri.cws.application.business.util.command.Command;
import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.PersistenceFactory;
import uo.ri.cws.application.persistence.client.ClientGateway;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway;
import uo.ri.cws.application.persistence.vehicle.VehicleGateway;
import uo.ri.cws.application.persistence.vehicle.VehicleGateway.VehicleDALDto;
import uo.ri.cws.application.persistence.workorder.WorkOrderGateway;
import uo.ri.cws.application.persistence.workorder.WorkOrderGateway.WorkOrderDALDto;

public class FindNotInvoicedWorkOrders implements Command<List<WorkOrderForInvoicingBLDto>>{
	
	private String dni;
	
	public FindNotInvoicedWorkOrders(String dni) {
		validate(dni);
		this.dni = dni;
	}
	
	private ClientGateway cw = PersistenceFactory.forClient();
	private VehicleGateway vw = PersistenceFactory.forVehicle();
	private WorkOrderGateway ww = PersistenceFactory.forWorkOrder();
	
	public List<WorkOrderForInvoicingBLDto> execute() throws BusinessException {
		
		// Comprobar si el dni existe
		if (!existClient(dni)) {
			throw new BusinessException("Repeated mechanic");
		}
		
		// Sacar el cliente por el dni
		String idCliente = cw.findByDni(dni).get().id;
		
		// Buscar coches en funcion del dni
		List<VehicleDALDto> clientVehicles = vw.findByClient(idCliente);
		
		// Sacar los ids de los coches
		List<String> vehicleIds =  getVehicleIds(clientVehicles);
		
		// Ver cuales tienen workorders <> 'INVOICED'
		List<WorkOrderDALDto> notInvoiced = ww.findNotInvoicedForVehicles(vehicleIds);
		
		// Sacar lista con el invoice assembler
		return InvoicingAssembler.toInvoicingWorkOrderList(notInvoiced);
	}

	
	
	private boolean existClient(String dni) throws PersistenceException {
		if (cw.findByDni(dni).isPresent()) {
			return true;
		} else {
			return false;
		}
	}

	private List<String> getVehicleIds(List<VehicleDALDto> clientVehicles) {
		List<String> result = new ArrayList<String>();
		for (VehicleDALDto v : clientVehicles) {
			result.add(v.id);
		}
		return result;
	}
	
	private void validate(String arg) {
		// usar clase del proyecto util Argumen
		Argument.isNotNull(arg, "Null dni");
		Argument.isNotEmpty(arg, "Null or empty dni");
	}
	
}
