package uo.ri.cws.application.persistence.invoice.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import jdbc.Jdbc;
import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.invoice.InvoiceGateway;

public class InvoiceGatewayImpl implements InvoiceGateway {

	private String TINVOICE_ADDINVOICE=  "insert into TInvoices(id, number, date, vat, amount, state, version)  values(?, ?, ?, ?, ?, ?, ?)";
	private String TINVOICE_FINDLASTINVOICED = "select max(number) from TInvoices";
	
	@Override
	public void add(InvoiceDALDto t) {
		Connection c = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			c = Jdbc.getCurrentConnection();
			
			pst = c.prepareStatement(TINVOICE_ADDINVOICE);
			pst.setString(1, t.id);
			pst.setLong(2, t.number);
			pst.setDate(3, java.sql.Date.valueOf(t.date));
			pst.setDouble(4, t.vat);
			pst.setDouble(5, t.amount);
			pst.setString(6, "NOT_YET_PAID");
			pst.setLong(7, 1L);
			
			 pst.executeUpdate();
		} catch (SQLException e) {
			throw new PersistenceException (e);
		} finally {
			Jdbc.close(rs, pst);
		}
	}

	@Override
	public void remove(String id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(InvoiceDALDto t) {
		// TODO Auto-generated method stub

	}

	@Override
	public Optional<InvoiceDALDto> findById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<InvoiceDALDto> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<InvoiceDALDto> findByNumber(Long number) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long getNextInvoiceNumber() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long findLastInvoiced() {
		Connection c = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			c = Jdbc.getCurrentConnection();
			
			pst = c.prepareStatement(TINVOICE_FINDLASTINVOICED);

			rs = pst.executeQuery();

			if (rs.next()) {
				return rs.getLong(1) + 1; // +1, next
			} else { // there is none yet
				return 1L;
			}
		} catch (SQLException e) {
			throw new PersistenceException (e);
		} finally {
			Jdbc.close(rs, pst);
		}
	}

}
