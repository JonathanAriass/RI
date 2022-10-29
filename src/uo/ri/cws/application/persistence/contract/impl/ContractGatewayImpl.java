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

import uo.ri.cws.application.persistence.util.Conf;

public class ContractGatewayImpl implements ContractGateway {

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
		PreparedStatement pst = null;
		Connection c = null;
		ResultSet rs = null;
		
		try {
			c = Jdbc.getCurrentConnection();
			
			pst = c.prepareStatement(Conf.getInstance().getProperty("TCONTRACTS_FINDBYID"));
			pst.setString(1, id);
			
			rs = pst.executeQuery();
			
			return ContractAssembler.toContractDALDto(rs);
 		} catch (SQLException e ) {
 			throw new PersistenceException(e);
 		} finally {
 			Jdbc.close(rs, pst);
 		}
	}

	@Override
	public List<ContractDALDto> findAll() {
		PreparedStatement pst = null;
		Connection c = null;
		ResultSet rs = null;
		
		try {
			c = Jdbc.getCurrentConnection();
			
			pst = c.prepareStatement(Conf.getInstance().getProperty("TCONTRACTS_FINDALL"));
			rs = pst.executeQuery();
			
			return ContractAssembler.toContractDALDtoList(rs);
 		} catch (SQLException e ) {
 			throw new PersistenceException(e);
 		} finally {
 			Jdbc.close(rs, pst);
 		}
	}

	@Override
	public Optional<ContractDALDto> findByMechanic(String mechanicid) {
		PreparedStatement pst = null;
		Connection c = null;
		ResultSet rs = null;
		
		try {
			c = Jdbc.getCurrentConnection();
			
			pst = c.prepareStatement(Conf.getInstance().getProperty(
					"TCONTRACTS_FINDBYMECHANIC"));
			
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
			
			pst = c.prepareStatement(Conf.getInstance().getProperty(
					"TCONTRACTS_FINDMECHANICSIDWITHCONTRACT"));
			
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
	public List<ContractDALDto> findByProfessionalGroupId(String groupid) {
		PreparedStatement pst = null;
		Connection c = null;
		ResultSet rs = null;
		
		try {
			c = Jdbc.getCurrentConnection();
			
			pst = c.prepareStatement(Conf.getInstance().getProperty(
					"TCONTRACTS_FINDBYPROFESSIONALGROUPID"));
			
			pst.setString(1, groupid);
			
			rs = pst.executeQuery();
			
			return ContractAssembler.toContractDALDtoList(rs);
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
			
			pst = c.prepareStatement(Conf.getInstance().getProperty(
					"TCONTRACTS_FINDMECHANICSFORPROFESSIONALGROUPSNAME"));
			
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
			
			pst = c.prepareStatement(Conf.getInstance().getProperty(
					"TCONTRACTS_FINDMECHANICSIDWITHCONTRACTTYPE"));
			
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

	@Override
	public String findProfessionaGroupByContractId(String id) {
		PreparedStatement pst = null;
		Connection c = null;
		ResultSet rs = null;
		
		try {
			c = Jdbc.getCurrentConnection();
			
			pst = c.prepareStatement(Conf.getInstance().getProperty(
					"TCONTRACTS_FINDPROFESSIONAGROUPBYCONTRACTID"));
			
			pst.setString(1, id);
			
			rs = pst.executeQuery();
			
			rs.next();
			
			return rs.getString("professionalgroup_id");
 		} catch (SQLException e ) {
 			throw new PersistenceException(e);
 		} finally {
 			Jdbc.close(rs, pst);
 		}
	}

}
