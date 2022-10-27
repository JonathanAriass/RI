package uo.ri.cws.application.persistence.workorder.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import jdbc.Jdbc;
import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.workorder.WorkOrderGateway;
import uo.ri.cws.application.persistence.workorder.assembler.WorkOrderAssembler;

public class WorkOrderGatewayImpl implements WorkOrderGateway {

	private String TWORKORDERS_UPDATE = "update TWORKORDERS set amount = ?, date = ?, description = ?, state = ?, version = ?, invoice_id = ?, mechanic_id = ?, vehicle_id = ? where id = ?";
	private String TWORKORDERS_FINDBYMECHANIC = "select * from TWORKORDERS where mechanic_id = ?";
	private String TWORKORDERS_FINDNOTINVOICED = "select * from TWorkOrders where vehicle_id = ? and state <> 'INVOICED'";
	private String TWORKORDERS_FINDSTATE = "select state from TWorkOrders where id = ?";
	private String TWORKORDERS_FINDBYID = "select * from TWorkOrders where id = ?";
	private String TWORKORDERS_FINDAMOUNT = "select amount from TWorkOrders where id = ?";
	
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
		Connection c = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			c = Jdbc.getCurrentConnection();
			
			pst = c.prepareStatement(TWORKORDERS_UPDATE);

			pst.setDouble(1, t.amount);
			
			LocalDateTime localTime = t.date;
			Instant instant = localTime.atZone(ZoneId.systemDefault()).toInstant();
			Date date = new java.sql.Date(Date.from(instant).getTime()) ;
			
			pst.setDate(2, date);
			pst.setString(3,  t.description);
			pst.setString(4, t.state);
			pst.setLong(5, t.version);
			pst.setString(6, t.invoice_id);
			pst.setString(7, t.mechanic_id);
			pst.setString(8, t.vehicle_id);
			pst.setString(9, t.id);

		   pst.executeUpdate();
			
		} catch (SQLException e) {
			throw new PersistenceException (e);
		} finally {
			Jdbc.close(rs, pst);
		}
	}

	@Override
	public Optional<WorkOrderDALDto> findById(String id) {
		Connection c = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			c = Jdbc.getCurrentConnection();
			
			pst = c.prepareStatement(TWORKORDERS_FINDBYID);

			pst.setString(1, id);

			rs = pst.executeQuery();
			
			return WorkOrderAssembler.toWorkOrderDALDto(rs);
		} catch (SQLException e) {
			throw new PersistenceException (e);
		} finally {
			Jdbc.close(rs, pst);
		}
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
		Connection c = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		List<WorkOrderDALDto> result = new ArrayList<>();
		
		try {
			c = Jdbc.getCurrentConnection();
			
			pst = c.prepareStatement(TWORKORDERS_FINDBYID);

			for (String workOrderID : arg) {
				pst.setString(1, workOrderID);

				rs = pst.executeQuery();

				Optional<WorkOrderDALDto> opt;
				
				do {
					opt = WorkOrderAssembler.toWorkOrderDALDto(rs);
					if (opt.isPresent()) result.add(opt.get());
				} while (opt.isPresent());
				
//				if (WorkOrderAssembler.toWorkOrderDALDto(rs).isPresent()) {
//					result.add(WorkOrderAssembler.toWorkOrderDALDto(rs).get());										
//				}

			}
			
			return result;
		} catch (SQLException e) {
			throw new PersistenceException (e);
		} finally {
			Jdbc.close(rs, pst);
		}
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

	@Override
	public String findState(String id) {
		Connection c = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			c = Jdbc.getCurrentConnection();
			
			pst = c.prepareStatement(TWORKORDERS_FINDSTATE);

			pst.setString(1, id);
				
			rs = pst.executeQuery();

			if (rs.next()) {
				return rs.getString("state");				
			}
			return "";
		} catch (SQLException e) {
			throw new PersistenceException (e);
		} finally {
			Jdbc.close(rs, pst);
		}
	}

	@Override
	public Double findAmount(String id) {
		Connection c = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		Double money = 0.0;
		
		try {
			c = Jdbc.getCurrentConnection();
			
			pst = c.prepareStatement(TWORKORDERS_FINDAMOUNT);

			pst.setString(1, id);

			rs = pst.executeQuery();

			if (rs.next()) {
				money = rs.getDouble(1);				
			}
			
			return money;
		} catch (SQLException e) {
			throw new PersistenceException (e);
		} finally {
			Jdbc.close(rs, pst);
		}
	}


}
