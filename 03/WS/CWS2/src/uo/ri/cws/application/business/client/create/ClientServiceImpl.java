package uo.ri.cws.application.business.client.create;

import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.business.BusinessException;
import uo.ri.cws.application.business.client.ClientService;

public class ClientServiceImpl implements ClientService {

	@Override
	public Client_BLDto addClient(Client_BLDto client, String recommenderId) throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteClient(String idClient) throws BusinessException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateClient(Client_BLDto client) throws BusinessException {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Client_BLDto> findAllClients() throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<Client_BLDto> findClientById(String idClient) throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Client_BLDto> findClientsRecommendedBy(String sponsorID) throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

}
