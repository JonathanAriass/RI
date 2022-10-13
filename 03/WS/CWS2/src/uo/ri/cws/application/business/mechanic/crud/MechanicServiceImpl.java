package uo.ri.cws.application.business.mechanic.crud;

import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.business.BusinessException;
import uo.ri.cws.application.business.mechanic.MechanicService;
import uo.ri.cws.application.business.mechanic.crud.commands.AddMechanic;
import uo.ri.cws.application.business.mechanic.crud.commands.DeleteMechanic;
import uo.ri.cws.application.business.mechanic.crud.commands.FindAllMechanics;
import uo.ri.cws.application.business.mechanic.crud.commands.FindMechanicByDni;
import uo.ri.cws.application.business.mechanic.crud.commands.FindMechanicById;
import uo.ri.cws.application.business.mechanic.crud.commands.UpdateMechanic;
import uo.ri.cws.application.business.util.command.CommandExecutor;

public class MechanicServiceImpl implements MechanicService {

	private CommandExecutor executor = new CommandExecutor();
	
	@Override
	public MechanicBLDto addMechanic(MechanicBLDto dto) throws BusinessException {	
		return executor.execute(new AddMechanic(dto));
	}

	@Override
	public void deleteMechanic(String idMechanic) throws BusinessException {
		executor.execute(new DeleteMechanic(idMechanic));
	}

	@Override
	public void updateMechanic(MechanicBLDto mechanic) throws BusinessException {
		UpdateMechanic update = new UpdateMechanic(mechanic);
		update.execute();
	}

	@Override
	public Optional<MechanicBLDto> findMechanicById(String idMechanic) throws BusinessException {
		FindMechanicById mechanic = new FindMechanicById(idMechanic);
		return mechanic.execute();
	}

	@Override
	public Optional<MechanicBLDto> findMechanicByDni(String dniMechanic) throws BusinessException {
		FindMechanicByDni mechanic = new FindMechanicByDni(dniMechanic);
		return mechanic.execute();
	}

	@Override
	public List<MechanicBLDto> findAllMechanics() throws BusinessException {
		FindAllMechanics find = new FindAllMechanics();
		return find.execute();
		 	
	}

}
