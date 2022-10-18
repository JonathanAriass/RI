package uo.ri.cws.application.business.invoice.create.commands;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import math.Round;
import uo.ri.cws.application.business.BusinessException;
import uo.ri.cws.application.business.invoice.InvoicingService.InvoiceBLDto;
import uo.ri.cws.application.business.util.command.Command;
import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.PersistenceFactory;
import uo.ri.cws.application.persistence.invoice.InvoiceGateway;
import uo.ri.cws.application.persistence.invoice.InvoiceGateway.InvoiceDALDto;
import uo.ri.cws.application.persistence.workorder.WorkOrderGateway;
import uo.ri.cws.application.persistence.workorder.WorkOrderGateway.WorkOrderDALDto;

public class CreateInvoice implements Command<InvoiceBLDto> {

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
	
	
	private void updateVersion(List<String> workOrderIds) throws PersistenceException {
		
		WorkOrderDALDto aux = null;
		// Obtenemos el DALDto para tener toda la informacion y acualizar sobre este
		for (String id: workOrderIds) {
			Optional<WorkOrderDALDto> dto = ww.findById(id);
			
			if (dto.isPresent()) {
				aux = dto.get();
				aux.version = aux.version + 1;
				ww.update(aux);
			}
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
	private void linkWorkordersToInvoice (String invoiceId, List<String> workOrderIDS) throws PersistenceException {
		
		WorkOrderDALDto aux = null;
		// Obtenemos el DALDto para tener toda la informacion y acualizar sobre este
		for (String id: workOrderIDS) {
			Optional<WorkOrderDALDto> dto = ww.findById(id);
			
			if (dto.isPresent()) {
				aux = dto.get();
				aux.invoice_id=invoiceId;
				ww.update(aux);
			}
		}
		
	}


	/*
	 * Sets state to INVOICED for every workorder
	 */
	private void markWorkOrderAsInvoiced(List<String> ids) throws PersistenceException {

		WorkOrderDALDto aux = null;
		// Obtenemos el DALDto para tener toda la informacion y acualizar sobre este
		for (String id: ids) {
			Optional<WorkOrderDALDto> dto = ww.findById(id);
			
			if (dto.isPresent()) {
				aux = dto.get();
				aux.state = "INVOICED";
				ww.update(aux);
			}
		}
	}
	
	
}
