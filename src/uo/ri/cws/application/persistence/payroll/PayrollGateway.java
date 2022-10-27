package uo.ri.cws.application.persistence.payroll;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.persistence.Gateway;
import uo.ri.cws.application.persistence.payroll.PayrollGateway.PayrollDALDto;


public interface PayrollGateway extends Gateway<PayrollDALDto> {

	List<PayrollDALDto> findPayrollByContractId(String contractId);
	
	public class PayrollDALDto {
		public String id;
		public Long version;
		
		public double bonus;
		public LocalDate date;
		public double incometax;
		public double monthlywage;
		public double nic;
		public double productivitybonus;
		public double trienniumpayment;
		public String contractid;
	}
	
}
