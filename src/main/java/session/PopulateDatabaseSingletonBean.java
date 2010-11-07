package session;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import domain.Customer;
import domain.Order;

/** Create one of each entity so the database
 * is "not null" - using /import.sql only works
 * in Hibernate.
 * @author ian
 */
@Singleton
@TransactionManagement(TransactionManagementType.CONTAINER)
public class PopulateDatabaseSingletonBean {
	
		@PersistenceContext(name="customer") EntityManager customerEntityManager;
		@PersistenceContext(name="orders") EntityManager orderEntityManager;
		
		@PostConstruct
		public void initDB() {
			System.out.println("PopulateDatabaseSingletonBean.initCustomerDB()");
			Customer cust = new Customer("Idon T'kare");
			customerEntityManager.persist(cust);
		
			System.out.println("PopulateDatabaseSingletonBean.initOrdersDB()");
			Order order = new Order();
			orderEntityManager.persist(order);
		}
}
