package uo.ri.cws.application.ui.manager.action.contractTypeManagement;

import java.util.Optional;

import console.Console;
import menu.Action;
import uo.ri.cws.application.business.BusinessException;
import uo.ri.cws.application.business.BusinessFactory;
import uo.ri.cws.application.business.contracttype.ContractTypeService.ContractTypeBLDto;
import uo.ri.cws.application.ui.util.Printer;

public class ListContractTypeByNameAction implements Action  {

	@Override
	public void execute() throws BusinessException {
		
		String name = Console.readString("Contract type name ");
		
		Optional<ContractTypeBLDto> ct = null;



		Printer.printContractType(ct);
	}

}
