package uo.ri.cws.infrastructure.persistence.jpa.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;

import uo.ri.cws.application.repository.PayrollRepository;
import uo.ri.cws.domain.Payroll;
import uo.ri.cws.infrastructure.persistence.jpa.util.BaseJpaRepository;
import uo.ri.cws.infrastructure.persistence.jpa.util.Jpa;

public class PayrollJpaRepository extends BaseJpaRepository<Payroll>
		implements PayrollRepository {

	@Override
	public List<Payroll> findByContract(String contractId) {
		// TODO Auto-generated method stub
		return null;
	}

//	Payroll.getCurrentMonthPayrolls
	@Override
	public List<Payroll> findCurrentMonthPayrolls() {
		EntityManager em = Jpa.getManager();
		List<Payroll> payrolls = em	.createNamedQuery(
											"Payroll.getCurrentMonthPayrolls",
											Payroll.class)
									.setParameter(1,
											LocalDate.now().getMonthValue())
									.setParameter(2, LocalDate.now().getYear())
									.getResultList();

		return payrolls;
	}

	@Override
	public Optional<Payroll> findCurrentMonthByContractId(String contractId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Payroll> findPayrollsForMechanicId(String mechanicId) {
		EntityManager em = Jpa.getManager();
		List<Payroll> payrolls = em	.createNamedQuery(
											"Payroll.getPayrollByMechanicId",
											Payroll.class)
									.setParameter(1, mechanicId)
									.getResultList();

		return payrolls;
	}

	@Override
	public List<Payroll> findPayrollsForProfessionalGroup(String name) {
		EntityManager em = Jpa.getManager();
		List<Payroll> payrolls = em	.createNamedQuery(
											"Payroll.getPayrollByProfessionalGroup",
											Payroll.class)
									.setParameter(1, name)
									.getResultList();

		return payrolls;
	}

}
