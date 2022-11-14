package uo.ri.cws.application.service.mechanic.crud;

import java.util.List;
import java.util.Optional;

import uo.ri.conf.Factory;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.ejecutador.Executor;
import uo.ri.cws.application.service.mechanic.MechanicCrudService;
import uo.ri.cws.application.service.mechanic.crud.command.AddMechanic;
import uo.ri.cws.application.service.mechanic.crud.command.DeleteMechanic;
import uo.ri.cws.application.service.mechanic.crud.command.FindAllMechanics;
import uo.ri.cws.application.service.mechanic.crud.command.FindMechanicById;
import uo.ri.cws.application.service.mechanic.crud.command.FindMechanicWithContractInforce;
import uo.ri.cws.application.service.mechanic.crud.command.FindMechanicWithContractTypeInforce;
import uo.ri.cws.application.service.mechanic.crud.command.UpdateMechanic;
import uo.ri.cws.application.util.command.CommandExecutor;

public class MechanicCrudServiceImpl implements MechanicCrudService {

	private CommandExecutor executor = Factory.executor.forExecutor();

	@Override
	public MechanicDto addMechanic(MechanicDto dto) throws BusinessException {
//		return new AddMechanic( dto ).execute();
		return new Executor().execute(new AddMechanic(dto));
	}

	@Override
	public void updateMechanic(MechanicDto dto) throws BusinessException {
//		new UpdateMechanic( dto ).execute();
		new Executor().execute(new UpdateMechanic(dto));
	}

	@Override
	public void deleteMechanic(String iddto) throws BusinessException {
//		new DeleteMechanic(iddto).execute();
		new Executor().execute(new DeleteMechanic(iddto));
	}

	@Override
	public List<MechanicDto> findAllMechanics() throws BusinessException {
//		return new FindAllMechanics().execute();
		return new Executor().execute(new FindAllMechanics());
	}

	@Override
	public Optional<MechanicDto> findMechanicById(String id)
			throws BusinessException {
//		return new FindMechanicById(id).execute();
		return new Executor().execute(new FindMechanicById(id));
	}

	@Override
	public List<MechanicDto> findMechanicsInForce() throws BusinessException {
		return new Executor().execute(new FindMechanicWithContractInforce());
//		return null;
	}

	@Override
	public List<MechanicDto> findMechanicsWithContractInForceInContractType(
			String name) throws BusinessException {
		return new Executor().execute(
				new FindMechanicWithContractTypeInforce(name));
//		return null;
	}

	@Override
	public List<MechanicDto> findMechanicsInProfessionalGroups(String name)
			throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

}