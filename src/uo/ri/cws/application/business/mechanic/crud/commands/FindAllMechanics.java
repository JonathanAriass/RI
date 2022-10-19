package uo.ri.cws.application.business.mechanic.crud.commands;

import java.util.ArrayList;
import java.util.List;

import uo.ri.cws.application.business.mechanic.MechanicService.MechanicBLDto;
import uo.ri.cws.application.business.mechanic.assembler.MechanicAssembler;
import uo.ri.cws.application.business.util.command.Command;
import uo.ri.cws.application.persistence.PersistenceFactory;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway;

public class FindAllMechanics implements Command<List<MechanicBLDto>> {

	private MechanicGateway mg = PersistenceFactory.forMechanic();

	public FindAllMechanics() {

	}

	public List<MechanicBLDto> execute() {
		List<MechanicBLDto> result = new ArrayList<MechanicBLDto>();

		result = MechanicAssembler.toDtoList(mg.findAll());

		return result;
	}

}
