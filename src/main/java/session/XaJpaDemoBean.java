package session;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import domain.Customer;
import domain.Order;


public class XaJpaDemoBean {

	@PersistenceContext EntityManager customerEntityManager;
	@PersistenceContext EntityManager orderEntityManager;

	public void saveCustomerOrder(Customer c, Order o, Boolean succeeds) {
		try {
			EntityTransaction customerTransaction = customerEntityManager.getTransaction();
			customerTransaction.begin();

			// Update the customer entity in the database.
			customerEntityManager.merge(c);
			c.setNumberOfOrders(c.getNumberOfOrders() + 1);
			customerTransaction.commit();
			
			int cid = c.getId();
			System.out.println("Updated Customer with Id " + cid);
			
			EntityTransaction orderTransaction = orderEntityManager.getTransaction();
			orderTransaction.begin();

			// Insert the order entity in the database.
			orderEntityManager.persist(c);
			orderTransaction.commit();
			
			int oid = o.getId();
			System.out.println("Created order with Id " + oid);
			Query query = customerEntityManager.createQuery("select c from Customer c order by c.name");

			List<Customer> list = query.getResultList();
			System.out.println("There are " + list.size() + " persons:");
			for (Customer p : list) {
				System.out.println(p.getName());
			}
			System.out.println();
			
		} finally {	
			if (!succeeds) {
				throw new RuntimeException("Simulated failure!");
			}
		}
	}
}
