package session;

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

	@PersistenceContext(name="customer") EntityManager customerEntityManager;
	@PersistenceContext(name="orders") EntityManager orderEntityManager;
	
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
				throw new RuntimeException("XaJpaDemoBean.saveCustomerOrder(): Simulated failure!");
			}
		}
	}
}
