package session;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import domain.Customer;
import domain.Order;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class XaJpaDemoBean {

	@PersistenceContext(unitName="customer") EntityManager customerEntityManager;
	@PersistenceContext(unitName="orders") EntityManager orderEntityManager;
	@Resource private SessionContext sessionCtx;
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Customer findCustomer(int id) {
		return customerEntityManager.find(Customer.class, id);
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void saveCustomerOrder(Customer c, Order o, Boolean succeeds) {
		System.out.println("XaJpaDemoBean.saveCustomerOrder()");
		try {

			// Update the customer entity in the database.
			c.setNumberOfOrders(c.getNumberOfOrders() + 1);
			System.out.println("XaJpaDemoBean.saveCustomerOrder(): Updated Customer with Id " + c.getId());

			// Insert the order entity in the database.
			orderEntityManager.persist(o);

			System.out.println("XaJpaDemoBean.saveCustomerOrder(): Created order with Id " + o.getId());

		} finally {	
			if (!succeeds) {
				if (sessionCtx != null) {
					sessionCtx.setRollbackOnly();
				} else {
					throw new RuntimeException("SessionContext is null, wrong @Resource!");
				}
			}
		}
	}

	/** Unusual, for demo purposes only */
	public SessionContext getSessionContext() {
		return sessionCtx;
	}
}
