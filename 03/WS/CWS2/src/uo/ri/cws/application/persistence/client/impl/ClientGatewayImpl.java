package uo.ri.cws.application.persistence.client.impl;

import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.persistence.client.ClientGateway;

public class ClientGatewayImpl implements ClientGateway {

	@Override
	public void add(ClientDALDto t) {
		// TODO Auto-generated method stub

	}

	@Override
	public void remove(String id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(ClientDALDto t) {
		// TODO Auto-generated method stub

	}

	@Override
	public Optional<ClientDALDto> findById(String id) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public List<ClientDALDto> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<ClientDALDto> findByDni(String clientdni) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

}
