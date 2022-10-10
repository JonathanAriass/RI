package uo.ri.cws.application.business.invoice.create.commands;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import console.Console;
import uo.ri.cws.application.business.invoice.InvoicingService.WorkOrderForInvoicingBLDto;
import uo.ri.cws.application.business.invoice.assembler.InvoicingAssembler;

public class FindNotInvoicedWorkOrders {

	private static final String URL = "jdbc:hsqldb:hsql://localhost:1522/";
	private static final String USER = "sa";
	private static final String PASS = "";
	
	/**
	 * Process:
	 * 
	 *   - Ask customer dni
	 *    
	 *   - Display all uncharged workorder  
	 *   		(state <> 'INVOICED'). For each workorder, display 
	 *   		id, vehicle id, date, state, amount and description
	 */

	private static final String SQL =
		"select a.id, a.description, a.date, a.state, a.amount " +
		"from TWorkOrders as a, TVehicles as v, TClients as c " +
		"where a.vehicle_id = v.id" +
		"	and v.client_id = c.id" +
		"	and state <> 'INVOICED'" +
		"	and dni like ?";
	
	private String dni;
	
	public FindNotInvoicedWorkOrders(String dni) {
		//validate(dni);
		this.dni = dni;
	}
	
	public List<WorkOrderForInvoicingBLDto> execute() {
		List<WorkOrderForInvoicingBLDto> result = new ArrayList<WorkOrderForInvoicingBLDto>();
		
		Connection c = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			c = DriverManager.getConnection(URL, USER, PASS);
			
			pst = c.prepareStatement(SQL);
			pst.setString(1, dni);
			
			rs = pst.executeQuery();
			
			result = InvoicingAssembler.toWorkOrderForInvoicingDtoList(rs);
			
			return result;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		finally {
			if (rs != null) try { rs.close(); } catch(SQLException e) { /* ignore */ }
			if (pst != null) try { pst.close(); } catch(SQLException e) { /* ignore */ }
			if (c != null) try { c.close(); } catch(SQLException e) { /* ignore */ }
		}
	}
	
}
