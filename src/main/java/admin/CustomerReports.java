package admin;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import domain.Customer;

public class CustomerReports {
	@SuppressWarnings("unchecked")
	public static void main(String[] args) {

		System.out.println("CustomerReports.main()");

		EntityManagerFactory entityMgrFactory = null;
		EntityManager entityManager = null;
		try {
			long time = System.currentTimeMillis();
			entityMgrFactory = Persistence.createEntityManagerFactory("customer");
			long time2 = System.currentTimeMillis();
			System.out.printf("Created EntityManagerFactory in %f seconds%n", (time2 - time)/1000d);
			entityManager = entityMgrFactory.createEntityManager();
			long time3 = System.currentTimeMillis();
			System.out.printf("Created EntityManager in %f seconds%n", (time3 - time2)/1000d);
			
			// Query to list
			Query query = entityManager.createQuery("from Customer");
			List<Customer> res = query.getResultList();
			System.out.println("People found by query");
			if (res.size() == 0) {
				System.out.println("** NONE **");
			} else {
				for (Customer c : res) {
					System.out.println(c);
				}
			}
		} finally {	
			if (entityManager != null)
				entityManager.close();
			if (entityMgrFactory != null)
				entityMgrFactory.close();
		}
	}

}
