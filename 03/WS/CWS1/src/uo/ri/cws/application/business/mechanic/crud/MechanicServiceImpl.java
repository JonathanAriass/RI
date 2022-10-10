package uo.ri.cws.application.business.mechanic.crud;

import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.business.BusinessException;
import uo.ri.cws.application.business.mechanic.MechanicService;
import uo.ri.cws.application.business.mechanic.crud.commands.AddMechanic;
import uo.ri.cws.application.business.mechanic.crud.commands.DeleteMechanic;
import uo.ri.cws.application.business.mechanic.crud.commands.FindAllMechanics;
import uo.ri.cws.application.business.mechanic.crud.commands.FindMechanicById;
import uo.ri.cws.application.business.mechanic.crud.commands.UpdateMechanic;

public class MechanicServiceImpl implements MechanicService {

	@Override
	public MechanicBLDto addMechanic(MechanicBLDto dto) throws BusinessException {
		
		AddMechanic add = new AddMechanic(dto);
		return add.execute();
	}

	@Override
	public void deleteMechanic(String idMechanic) throws BusinessException {
		DeleteMechanic delete = new DeleteMechanic(idMechanic);
		delete.execute();
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<MechanicBLDto> findAllMechanics() throws BusinessException {
		FindAllMechanics find = new FindAllMechanics();
		return find.execute();
		 	
	}

}
