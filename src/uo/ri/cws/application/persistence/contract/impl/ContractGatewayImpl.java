package uo.ri.cws.application.persistence.contract.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import jdbc.Jdbc;
import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.contract.ContractGateway;
import uo.ri.cws.application.persistence.contract.assembler.ContractAssembler;

public class ContractGatewayImpl implements ContractGateway {

	private String TCONTRACTS_FINDBYMECHANIC = "SELECT * FROM TCONTRACTS WHERE mechanic_id = ?";
	private String TCONTRACTS_FINDMECHANICSIDWITHCONTRACT = "SELECT mechanic_id FROM TCONTRACTS WHERE state = 'IN_FORCE'";
	
	@Override
	public void add(ContractDALDto t) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void remove(String id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(ContractDALDto t) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Optional<ContractDALDto> findById(String id) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public List<ContractDALDto> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<ContractDALDto> findByMechanic(String mechanicid) {
		PreparedStatement pst = null;
		Connection c = null;
		ResultSet rs = null;
		
		try {
			c = Jdbc.getCurrentConnection(); // Con esto obtenemos la conexion a la base de datos
			
			pst = c.prepareStatement(TCONTRACTS_FINDBYMECHANIC);
			pst.setString(1, mechanicid);
			
			rs = pst.executeQuery();
			
			return ContractAssembler.toContractDALDto(rs);
 		} catch (SQLException e ) {
 			throw new PersistenceException(e);
 		} finally {
 			Jdbc.close(rs, pst);
 		}
	}

	@Override
	public List<String> findMechanicsIdWithContract() {
		PreparedStatement pst = null;
		Connection c = null;
		ResultSet rs = null;
		
		List<String> aux = new ArrayList<>();
		
		try {
			c = Jdbc.getCurrentConnection(); // Con esto obtenemos la conexion a la base de datos
			
			pst = c.prepareStatement(TCONTRACTS_FINDMECHANICSIDWITHCONTRACT);
			
			rs = pst.executeQuery();
			
			while(rs.next()) {
				aux.add(rs.getString("mechanic_id"));
			}
			
			return aux;
 		} catch (SQLException e ) {
 			throw new PersistenceException(e);
 		} finally {
 			Jdbc.close(rs, pst);
 		}
	}

}
