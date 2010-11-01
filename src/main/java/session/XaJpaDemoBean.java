package session;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;

import domain.Customer;
import domain.Order;

@Stateless
public class XaJpaDemoBean {

	@PersistenceContext(name="customer") EntityManager customerEntityManager;
	@PersistenceContext(name="orders") EntityManager orderEntityManager;

	public void saveCustomerOrder(Customer c, Order o, Boolean succeeds) {
		try {
			EntityTransaction customerTransaction = customerEntityManager.getTransaction();
			customerTransaction.begin();

			// Update the customer entity in the database.
			customerEntityManager.merge(c);
			c.setNumberOfOrders(c.getNumberOfOrders() + 1);
			customerTransaction.commit();
			
			int cid = c.getId();
			System.out.println("XaJpaDemoBean.saveCustomerOrder(): Updated Customer with Id " + cid);
			
			EntityTransaction orderTransaction = orderEntityManager.getTransaction();
			orderTransaction.begin();

			// Insert the order entity in the database.
			orderEntityManager.persist(c);
			orderTransaction.commit();
			
			int oid = o.getId();
			System.out.println("XaJpaDemoBean.saveCustomerOrder(): Created order with Id " + oid);
			
		} finally {	
			if (!succeeds) {
				throw new RuntimeException("XaJpaDemoBean.saveCustomerOrder(): Simulated failure!");
			}
		}
	}
}
