package uo.ri.cws.application.business.invoice.create.commands;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import math.Round;
import uo.ri.cws.application.business.BusinessException;
import uo.ri.cws.application.business.invoice.InvoicingService.InvoiceBLDto;
import uo.ri.cws.application.business.invoice.InvoicingService.InvoiceBLDto.InvoiceState;
import uo.ri.cws.application.business.invoice.assembler.InvoicingAssembler;
import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.PersistenceFactory;
import uo.ri.cws.application.persistence.invoice.InvoiceGateway;
import uo.ri.cws.application.persistence.invoice.InvoiceGateway.InvoiceDALDto;
import uo.ri.cws.application.persistence.invoice.assembler.InvoiceAssembler;
import uo.ri.cws.application.persistence.workorder.WorkOrderGateway;

public class CreateInvoice {

	private static final String URL = "jdbc:hsqldb:hsql://localhost:1522/";
	private static final String USER = "sa";
	private static final String PASSWORD = "";

	
	// Se tiene que hacer un findWorkorderStateById()
	private static final String SQL_CHECK_WORKORDER_STATE = 
			"select state from TWorkOrders where id = ?";

	//  Se tiene que hacer un findLastInvoiceNumber() en invoice
	private static final String SQL_LAST_INVOICE_NUMBER = 
			"select max(number) from TInvoices";

	// Se tiene que hacer un findWorkorderAmount()
	private static final String SQL_FIND_WORKORDER_AMOUNT = 
			"select amount from TWorkOrders where id = ?";
	
	// Se tiene que hacer un addInvoice() en invoice
	private static final String SQL_INSERT_INVOICE = 
			"insert into TInvoices(id, number, date, vat, amount, state, version) "
					+ "	values(?, ?, ?, ?, ?, ?, ?)";

	// Se tiene que hacer un update en workorder
	private static final String SQL_LINK_WORKORDER_TO_INVOICE = 
			"update TWorkOrders set invoice_id = ? where id = ?";

	// Se tiene que hacer un update en workorder
	private static final String SQL_MARK_WORKORDER_AS_INVOICED = 
			"update TWorkOrders set state = 'INVOICED' where id = ?";

	// Se hace un select de workorders 
	private static final String SQL_FIND_WORKORDERS = 
			"select * from TWorkOrders where id = ?";
	
	// Se tiene que hacer un update en workorder
	private static final String SQL_UPDATEVERSION_WORKORDERS = 
			"update TWorkOrders set version=version+1 where id = ?";
	
	private Connection connection;	

	private InvoiceBLDto dto = null;
	
	private List<String> workOrderIds = new ArrayList<>();
	
	private WorkOrderGateway ww = PersistenceFactory.forWorkOrder();
	private InvoiceGateway iw = PersistenceFactory.forInvoice();
	
	public CreateInvoice(List<String> workOrdersIds) {
		this.workOrderIds = workOrdersIds;
	}
	
	public InvoiceBLDto execute() throws BusinessException {

			if (! checkWorkOrdersExist(workOrderIds) )
				throw new BusinessException ("Workorder does not exist");
			if (! checkWorkOrdersFinished(workOrderIds) )
				throw new BusinessException ("Workorder is not finished yet");

			long numberInvoice = generateInvoiceNumber();
			LocalDate dateInvoice = LocalDate.now();
			double amount = calculateTotalInvoice(workOrderIds); // vat not included
			double vat = vatPercentage(amount, dateInvoice);
			double total = amount * (1 + vat/100); // vat included
			total = Round.twoCents(total);
			
			String idInvoice = createInvoice(numberInvoice, dateInvoice, vat, total);
			linkWorkordersToInvoice(idInvoice, workOrderIds);
			markWorkOrderAsInvoiced(workOrderIds);
			updateVersion(workOrderIds);

//			connection.commit();
			
			return dto;
		
	}
	
	
	private void updateVersion(List<String> workOrderIds) throws SQLException {
		PreparedStatement pst = null;
		
		try {
			pst = connection.prepareStatement(SQL_UPDATEVERSION_WORKORDERS);

			for (String workOrderID : workOrderIds) {
				pst.setString(1, workOrderID);
				pst.executeUpdate();
				}
		} finally {
			if (pst != null) try { pst.close(); } catch(SQLException e) { /* ignore */ }
		}		
	}
	
	/*
	 * checks whether every work order exist	 
	 */
	private boolean checkWorkOrdersExist(List<String> workOrderIDS) throws PersistenceException {
		if (ww.findByIds(workOrderIDS).isEmpty()) {
			return false;
		}
		return true;
	}

	/*
	 * checks whether every work order id is FINISHED	 
	 */
	private boolean checkWorkOrdersFinished(List<String> workOrderIDS) throws PersistenceException {

		for (String workOrderID : workOrderIDS) {
			String state = ww.findState(workOrderID);
			if (!"FINISHED".equalsIgnoreCase(state)) {
				return false;
			}
		}

		return true;
	}

	/*
	 * Generates next invoice number (not to be confused with the inner id)
	 */
	private Long generateInvoiceNumber() throws PersistenceException{
		return iw.findLastInvoiced();
	}

	/*
	 * Compute total amount of the invoice  (as the total of individual work orders' amount 
	 */
	private double calculateTotalInvoice(List<String> workOrderIDS) throws PersistenceException {

		double totalInvoice = 0.0;
		for (String workOrderID : workOrderIDS) {
			totalInvoice += getWorkOrderTotal(workOrderID);
		}
		return totalInvoice;
	}

	/*
	 * checks whether every work order id is FINISHED	 
	 */
	private Double getWorkOrderTotal(String workOrderID) throws PersistenceException {
		return ww.findAmount(workOrderID);
	}

	/*
	 * returns vat percentage 
	 */
	private double vatPercentage(double totalInvoice, LocalDate dateInvoice) {
		return LocalDate.parse("2012-07-01").isBefore(dateInvoice) ? 21.0 : 18.0;

	}

	/*
	 * Creates the invoice in the database; returns the id
	 */
	private String createInvoice(long numberInvoice, LocalDate dateInvoice, 
			double vat, double total) throws PersistenceException {

		String idInvoice;

		idInvoice = UUID.randomUUID().toString();

		InvoiceDALDto t = new InvoiceDALDto();
		t.id = idInvoice;
		t.version = 1L;
		t.vat = vat;
		t.number = numberInvoice;
		t.date = dateInvoice;
		t.state = "NOT_YET_PAID";
		
		iw.add(t);

		return idInvoice;
	}

	/*
	 * Set the invoice number field in work order table to the invoice number generated
	 */
	private void linkWorkordersToInvoice (String invoiceId, List<String> workOrderIDS) throws SQLException {

		PreparedStatement pst = null;
		try {
			pst = connection.prepareStatement(SQL_LINK_WORKORDER_TO_INVOICE);

			for (String workOrderId : workOrderIDS) {
				pst.setString(1, invoiceId);
				pst.setString(2, workOrderId);

				pst.executeUpdate();
			}
		} finally {
			if (pst != null) try { pst.close(); } catch(SQLException e) { /* ignore */ }
		}
	}


	/*
	 * Sets state to INVOICED for every workorder
	 */
	private void markWorkOrderAsInvoiced(List<String> ids) throws SQLException {

		PreparedStatement pst = null;
		try {
			pst = connection.prepareStatement(SQL_MARK_WORKORDER_AS_INVOICED);

			for (String id: ids) {
				pst.setString(1, id);

				pst.executeUpdate();
			}
		} finally {
			if (pst != null) try { pst.close(); } catch(SQLException e) { /* ignore */ }
		}
	}
	
	
}
