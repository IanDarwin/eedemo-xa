package session;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import domain.Customer;
import domain.Order;

@Stateless
public class XaJpaDemoBean {

	@PersistenceContext(name="customer") EntityManager customerEntityManager;
	@PersistenceContext(name="orders") EntityManager orderEntityManager;
	
	public Customer findCustomer(int id) {
		return customerEntityManager.find(Customer.class, id);
	}

	public void saveCustomerOrder(Customer c, Order o, Boolean succeeds) {
		System.out.println("XaJpaDemoBean.saveCustomerOrder()");
		try {
			
			// Update the customer entity in the database.
			c.setNumberOfOrders(c.getNumberOfOrders() + 1);
			System.out.println("XaJpaDemoBean.saveCustomerOrder(): 3");
			
			int cid = c.getId();
			System.out.println("XaJpaDemoBean.saveCustomerOrder(): Updated Customer with Id " + cid);
			
			// Insert the order entity in the database.
			orderEntityManager.persist(o);
			
			int oid = o.getId();
			System.out.println("XaJpaDemoBean.saveCustomerOrder(): Created order with Id " + oid);
			
		} finally {	
			if (!succeeds) {
				throw new RuntimeException("XaJpaDemoBean.saveCustomerOrder(): Simulated failure!");
			}
		}
	}
}
