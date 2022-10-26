package uo.ri.cws.application.business.payroll.create.commands;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.business.BusinessException;
import uo.ri.cws.application.business.contract.ContractService.ContractBLDto;
import uo.ri.cws.application.business.contract.ContractService.ContractState;
import uo.ri.cws.application.business.contract.assembler.ContractAssembler;
import uo.ri.cws.application.business.mechanic.MechanicService.MechanicBLDto;
import uo.ri.cws.application.business.mechanic.assembler.MechanicAssembler;
import uo.ri.cws.application.business.util.command.Command;
import uo.ri.cws.application.persistence.PersistenceFactory;
import uo.ri.cws.application.persistence.contract.ContractGateway;

import uo.ri.cws.application.persistence.mechanic.MechanicGateway;

public class GeneratePayrolls implements Command<Void> {

	@Override
	public Void execute() throws BusinessException {
		// Comprobar para que empleados se pueden generar las payroll
		// Son aquellos que tienen contrato en vigor (IN_FORCE) o se extingue este mes
		// No puede tener 2 nominas generadas el mismo empleado para un mes
		
		/* Cosas a tener en cuenta en la nomina: 
		 * 		- monthlyWage (base / 14) 
		 * 		
		 * 		- bonus (meses correspondientes con importe igual al salario base)
		 * 		
		 * 		- productivityBonus (porcentaje importe total workorders asignadas al mecanico
		 * 							 de este mes y que hayan sido facturadas. El porcentaje depende
		 * 							 del grupo profesional)
		 * 		
		 * 		- trienniumPayment (complemento por antiguedad. Cada 3 anios acumulados en el mismo contrato
		 * 							se acumulara un trienio. Por cada trienio se recibe lo del grupo profesional)
		 * 
		 * 		- incomeTax (va por tramos - mirar en el pdf de la extension)
		 * */
		// Todos estos datos tienen que estar detallados al generar una factura
		
		List<String> mechanicsIds = checkMechanicsState();
		
		return null;
	}

	private MechanicGateway mg = PersistenceFactory.forMechanic();
	private ContractGateway cg = PersistenceFactory.forContract();
	// Se recorren todos los mecanicos, se comprueba el estado del contrato
	private List<String> checkMechanicsState() {
		List<String> result = new ArrayList<String>();
		
		List<MechanicBLDto> mechanicsId = MechanicAssembler.toDtoList(mg.findAll());
		ContractState state;
		LocalDate endDate;
		for (MechanicBLDto m : mechanicsId) {
			Optional<ContractBLDto> aux = ContractAssembler.toBLDto(cg.findByMechanic(m.dni));
			if (aux.isPresent()) {
				state = aux.get().state;
				endDate = aux.get().endDate;
				if (state == ContractState.IN_FORCE) {
					result.add(m.id);
				} else if (endDate.getMonthValue() >= LocalDate.now().getMonthValue()) {
					result.add(m.id);
				}
			}
		}
		
		return result;
	}

}
