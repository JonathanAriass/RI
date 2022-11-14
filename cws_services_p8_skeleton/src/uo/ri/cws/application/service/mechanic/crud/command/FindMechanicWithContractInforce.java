package uo.ri.cws.application.service.mechanic.crud.command;

import java.util.List;

import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.MechanicRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.cws.application.service.mechanic.assembler.MechanicAssembler;
import uo.ri.cws.application.util.command.Command;

public class FindMechanicWithContractInforce
		implements Command<List<MechanicDto>> {

	private static MechanicRepository repo = Factory.repository.forMechanic();

	@Override
	public List<MechanicDto> execute() throws BusinessException {
		List<MechanicDto> result = MechanicAssembler.toListDto(
				repo.findAllInForce());

		return result;
	}

}
