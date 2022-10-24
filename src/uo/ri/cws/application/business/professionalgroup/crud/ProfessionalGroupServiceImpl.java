package uo.ri.cws.application.business.professionalgroup.crud;

import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.business.BusinessException;
import uo.ri.cws.application.business.professionalgroup.ProfessionalGroupService;
import uo.ri.cws.application.business.professionalgroup.crud.commands.AddProfessionalGroup;
import uo.ri.cws.application.business.professionalgroup.crud.commands.DeleteProfessionalGroup;
import uo.ri.cws.application.business.professionalgroup.crud.commands.FindAllProfessionalGroups;
import uo.ri.cws.application.business.professionalgroup.crud.commands.FindProfessionalGroupByName;
import uo.ri.cws.application.business.professionalgroup.crud.commands.UpdateProfessionalGroup;
import uo.ri.cws.application.business.util.command.CommandExecutor;

public class ProfessionalGroupServiceImpl implements ProfessionalGroupService {

	private CommandExecutor executor = new CommandExecutor();
	
	@Override
	public ProfessionalGroupBLDto addProfessionalGroup(ProfessionalGroupBLDto dto) throws BusinessException {
		return executor.execute(new AddProfessionalGroup(dto));
	}

	@Override
	public void deleteProfessionalGroup(String name) throws BusinessException {
		executor.execute(new DeleteProfessionalGroup(name));
	}

	@Override
	public void updateProfessionalGroup(ProfessionalGroupBLDto dto) throws BusinessException {
		executor.execute(new UpdateProfessionalGroup(dto));
	}

	@Override
	public Optional<ProfessionalGroupBLDto> findProfessionalGroupByName(String name) throws BusinessException {
		return executor.execute(new FindProfessionalGroupByName(name));
	}

	@Override
	public List<ProfessionalGroupBLDto> findAllProfessionalGroups() throws BusinessException {
		return executor.execute(new FindAllProfessionalGroups());
	}

}
