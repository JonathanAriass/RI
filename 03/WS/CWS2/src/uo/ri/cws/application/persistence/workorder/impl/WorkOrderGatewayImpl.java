package uo.ri.cws.application.persistence.workorder.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import jdbc.Jdbc;
import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.workorder.WorkOrderGateway;
import uo.ri.cws.application.persistence.workorder.assembler.WorkOrderAssembler;

public class WorkOrderGatewayImpl implements WorkOrderGateway {

	private String TWORKORDERS_FINDBYMECHANIC = "select * from TWORKORDERS where mechanic_id = ?";
	private String TWORKORDERS_FINDNOTINVOICED = "select a.id, a.description, a.date, a.state, a.amount from TWorkOrders where vehicle_id = ? and state <> 'INVOICED'";
	
	@Override
	public void add(WorkOrderDALDto t) {
		// TODO Auto-generated method stub

	}

	@Override
	public void remove(String id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(WorkOrderDALDto t) {
		// TODO Auto-generated method stub

	}

	@Override
	public Optional<WorkOrderDALDto> findById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<WorkOrderDALDto> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<WorkOrderDALDto> findByMechanic(String id) {
		Connection c = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		
		try {
			c = Jdbc.getCurrentConnection();
			
			pst = c.prepareStatement(TWORKORDERS_FINDBYMECHANIC);
			pst.setString(1, id);
			
			rs = pst.executeQuery();
			return WorkOrderAssembler.toWorkOrderDALDtoList(rs);
		} catch (SQLException e) {
			throw new PersistenceException (e);
		} finally {
			Jdbc.close(rs, pst);
		}
	}

	@Override
	public List<WorkOrderDALDto> findNotInvoicedForVehicles(List<String> vehicleIds) {
		Connection c = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		
		List<WorkOrderDALDto>  result = new ArrayList<WorkOrderDALDto>();
		
		try {
			c = Jdbc.getCurrentConnection();
			
			pst = c.prepareStatement(TWORKORDERS_FINDNOTINVOICED);
			
			for (String vehicleId : vehicleIds) {
				pst.setString(1, vehicleId);
				
				rs = pst.executeQuery();
				result.addAll(WorkOrderAssembler.toWorkOrderDALDtoList(rs));				
			}
			
			return result;
		} catch (SQLException e) {
			throw new PersistenceException (e);
		} finally {
			Jdbc.close(rs, pst);
		}
	}

	@Override
	public List<WorkOrderDALDto> findByVehicleId(String vehicleId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<WorkOrderDALDto> findByIds(List<String> arg) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<WorkOrderDALDto> findByInvoice(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<WorkOrderDALDto> findInvoiced() {
		// TODO Auto-generated method stub
		return null;
	}

}
