package uo.ri.cws.application.ui.manager.action.contractManagement;

import java.util.List;

import console.Console;
import menu.Action;
import uo.ri.cws.application.business.BusinessException;
import uo.ri.cws.application.business.BusinessFactory;
import uo.ri.cws.application.business.contract.ContractService;
import uo.ri.cws.application.business.contract.ContractService.ContractSummaryBLDto;
import uo.ri.cws.application.ui.util.Printer;

/**
 * Class to terminate a contract with an id
 * 
 * @author 
 *
 */
public class FinishContractAction implements Action {

	private ContractService service = BusinessFactory.forContractService();

	/**
	 * Finaliza el contrato cuyo id es pasado por consola
	 */
	@Override
	public void execute() throws BusinessException {

		displayAllContracts();
		String id = Console.readString("Type contract identifier");




		Console.println("Contract is terminated");

	}

	

}
