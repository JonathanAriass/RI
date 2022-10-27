package uo.ri.cws.application.business.workorder.assembler;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.business.workorder.WorkOrderService.WorkOrderBLDto;
import uo.ri.cws.application.persistence.workorder.WorkOrderGateway.WorkOrderDALDto;

public class WorkOrderAssembler {

	public static Optional<WorkOrderBLDto> toBLDto(Optional<WorkOrderDALDto> arg) {
		Optional<WorkOrderBLDto> result = arg.isEmpty() ? Optional.ofNullable(null)
				: Optional.ofNullable(toWorkOrderDto(arg.get()));
		return result;
	}

	public static List<WorkOrderBLDto> toDtoList(List<WorkOrderDALDto> arg) {
		List<WorkOrderBLDto> result = new ArrayList<WorkOrderBLDto>();
		for (WorkOrderDALDto mr : arg)
			result.add(toWorkOrderDto(mr));
		return result;
	}

	public static WorkOrderDALDto toDALDto(WorkOrderBLDto arg) {
		WorkOrderDALDto result = new WorkOrderDALDto();
		result.id = arg.id;
		result.version = arg.version;
		result.vehicle_id = arg.vehicleId;
		result.description = arg.description;
		result.date = arg.date;
		result.amount = arg.total;
		result.state = arg.state;
		result.mechanic_id = arg.mechanicId;
		result.invoice_id = arg.invoiceId;
		return result;
	}

	private static WorkOrderBLDto toWorkOrderDto(WorkOrderDALDto arg) {

		WorkOrderBLDto result = new WorkOrderBLDto();
		result.id = arg.id;
		result.version = arg.version;
		result.vehicleId = arg.vehicle_id;
		result.description = arg.description;
		result.date = arg.date;
		result.total = arg.amount;
		result.state = arg.state;
		result.mechanicId = arg.mechanic_id;
		result.invoiceId = arg.invoice_id;
		return result;
	}
	
}
