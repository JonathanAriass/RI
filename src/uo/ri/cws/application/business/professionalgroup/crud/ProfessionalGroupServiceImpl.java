package uo.ri.cws.application.business.professionalgroup.crud;

import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.business.BusinessException;
import uo.ri.cws.application.business.mechanic.crud.commands.DeleteMechanic;
import uo.ri.cws.application.business.professionalgroup.ProfessionalGroupService;
import uo.ri.cws.application.business.professionalgroup.crud.commands.AddProfessionalGroup;
import uo.ri.cws.application.business.util.command.CommandExecutor;

public class ProfessionalGroupServiceImpl implements ProfessionalGroupService {

	private CommandExecutor executor = new CommandExecutor();
	
	@Override
	public ProfessionalGroupBLDto addProfessionalGroup(ProfessionalGroupBLDto dto) throws BusinessException {
		return executor.execute(new AddProfessionalGroup(dto));
	}

	@Override
	public void deleteProfessionalGroup(String name) throws BusinessException {
		executor.execute(new DeleteMechanic(name));
	}

	@Override
	public void updateProfessionalGroup(ProfessionalGroupBLDto dto) throws BusinessException {
		// TODO Auto-generated method stub

	}

	@Override
	public Optional<ProfessionalGroupBLDto> findProfessionalGroupByName(String name) throws BusinessException {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public List<ProfessionalGroupBLDto> findAllProfessionalGroups() throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

}
