package webtier;

import java.util.Date;

import javax.ejb.EJB;
import javax.ejb.TransactionManagement;
import javax.faces.bean.ManagedBean;

import session.XaJpaDemoBean;
import domain.Customer;
import domain.Order;

/**
 * JSF Managed Bean for order
 * @author ian
 */
@ManagedBean
@TransactionManagement()
public class OrderWebBean {
	@EJB XaJpaDemoBean dao;
	Customer customer;
	int orderQuantity;
	
	public void saveOrder() {
		Customer c = dao.findCustomer(1);
		Order o = new Order();
		o.setDate(new Date());
		o.setQuantity(42);
		
		dao.saveCustomerOrder(c, o, true);
	}

	public void saveOrderFail() {
		Customer c = dao.findCustomer(1);
		
		Order o2 = new Order();
		o2.setDate(new Date());
		o2.setQuantity(100);

		dao.saveCustomerOrder(c, o2, false);
	}
}

