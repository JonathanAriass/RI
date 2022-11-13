package uo.ri.cws.application.service.mechanic.crud.command;

import java.util.Optional;

import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.MechanicRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.cws.application.service.mechanic.assembler.MechanicAssembler;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Mechanic;
import uo.ri.util.assertion.ArgumentChecks;

public class FindMechanicById implements Command<Optional<MechanicDto>> {

	private String id;
	private static MechanicRepository repo = Factory.repository.forMechanic();

	public FindMechanicById(String id) {
		ArgumentChecks.isNotEmpty(id);
		ArgumentChecks.isNotBlank(id);
		
		this.id = id;
	}

	public Optional<MechanicDto> execute() throws BusinessException {
		MechanicDto dto = null;
		Mechanic m = repo.findById(id).get();
		dto = MechanicAssembler.toOptionalDto(m);
		return Optional.of(dto);
	}

}
