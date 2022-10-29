package uo.ri.cws.application.persistence.contracttype.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.List;
import java.util.Optional;

import jdbc.Jdbc;
import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.contracttype.ContractTypeGateway;
import uo.ri.cws.application.persistence.contracttype.assembler.ContractTypeAssembler;
import uo.ri.cws.application.persistence.util.Conf;

public class ContractTypeGatewayImpl implements ContractTypeGateway {

	
	@Override
	public void add(ContractTypeDALDto t) {
		// TODO Auto-generated method stub

	}

	@Override
	public void remove(String id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(ContractTypeDALDto t) {
		// TODO Auto-generated method stub

	}

	@Override
	public Optional<ContractTypeDALDto> findById(String id) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public List<ContractTypeDALDto> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<ContractTypeDALDto> findContractTypeIdByName(String name) {
		PreparedStatement pst = null;
		Connection c = null;
		ResultSet rs = null;
		
		try {
			c = Jdbc.getCurrentConnection();
			
			pst = c.prepareStatement(Conf.getInstance().getProperty(
					"TCONTRACTTYPES_FINDCONTRACTTYPEIDBYNAME"));
			
			pst.setString(1, name);
			
			rs = pst.executeQuery();
			
			return ContractTypeAssembler.toContractTypeDALDto(rs);
 		} catch (SQLException e ) {
 			throw new PersistenceException(e);
 		} finally {
 			Jdbc.close(rs, pst);
 		}
	}

}
