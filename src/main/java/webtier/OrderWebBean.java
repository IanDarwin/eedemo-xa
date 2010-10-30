package webtier;

import java.util.Date;

import javax.ejb.EJB;

import session.XaJpaDemoBean;

import domain.Customer;
import domain.Order;

/**
 * JSF Managed Bean for order
 * @author ian
 *
 */
public class OrderWebBean {
	@EJB XaJpaDemoBean dao;
	Customer customer;
	int orderQuantity;
	
	public void saveOrder() {
		Customer c = new Customer("Alpha");
		c.setId(1);	// ugly hack for now, see import.sql
		Order o = new Order();
		o.setDate(new Date());
		o.setQuantity(42);
		
		dao.saveCustomerOrder(c, o, true);
		
		Order o2 = new Order();
		o2.setDate(new Date());
		o2.setQuantity(100);
		dao.saveCustomerOrder(c, o, false);
	}
}

