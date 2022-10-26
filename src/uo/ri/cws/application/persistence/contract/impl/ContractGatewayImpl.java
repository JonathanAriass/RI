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
	private String TCONTRACTS_FINDPROFESSIONALGROUPBYID = "SELECT * FROM TCONTRACTS WHERE professionalgroup_id = ?";
	private String TCONTRACTS_FINDMECHANICSIDFORPROFESSIONALGROUP = "SELECT mechanic_id FROM TCONTRACTS WHERE professionalgroup_id = ?";
	private String TCONTRACTS_FINDMECHANICSIDWITHCONTRACTTYPE = "SELECT mechanic_id FROM TCONTRACTS WHERE state = 'IN_FORCE' and contracttype_id = ?";
	
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
			c = Jdbc.getCurrentConnection();
			
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
			c = Jdbc.getCurrentConnection();
			
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

	@Override
	public Optional<ContractDALDto> findByProfessionalGroupId(String groupid) {
		PreparedStatement pst = null;
		Connection c = null;
		ResultSet rs = null;
		
		try {
			c = Jdbc.getCurrentConnection();
			
			pst = c.prepareStatement(TCONTRACTS_FINDPROFESSIONALGROUPBYID);
			
			pst.setString(1, groupid);
			
			rs = pst.executeQuery();
			
			return ContractAssembler.toContractDALDto(rs);
 		} catch (SQLException e ) {
 			throw new PersistenceException(e);
 		} finally {
 			Jdbc.close(rs, pst);
 		}
	}

	@Override
	public List<String> findMechanicsForProfessionalGroupsName(String groupId) {
		PreparedStatement pst = null;
		Connection c = null;
		ResultSet rs = null;
		
		List<String> aux = new ArrayList<>();
		
		try {
			c = Jdbc.getCurrentConnection();
			
			pst = c.prepareStatement(TCONTRACTS_FINDMECHANICSIDFORPROFESSIONALGROUP);
			
			pst.setString(1, groupId);
			
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

	@Override
	public List<String> findMechanicsIdWithContractType(String id) {
		PreparedStatement pst = null;
		Connection c = null;
		ResultSet rs = null;
		
		List<String> aux = new ArrayList<>();
		
		try {
			c = Jdbc.getCurrentConnection();
			
			pst = c.prepareStatement(TCONTRACTS_FINDMECHANICSIDWITHCONTRACTTYPE);
			
			pst.setString(1, id);
			
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
