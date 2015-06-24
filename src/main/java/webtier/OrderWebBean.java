package webtier;

import java.util.Date;

import javax.ejb.EJB;
import javax.ejb.TransactionManagement;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

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
	@EJB XaJpaDemoBean ejb;
	Customer customer;
	int orderQuantity;
	
	public String saveOrder() {
		Customer c = ejb.findCustomer(1);
		Order o = new Order();
		o.setDate(new Date());
		o.setQuantity(42);
		
		ejb.saveCustomerOrder(c, o, true);
		
		final FacesMessage facesMessage = new FacesMessage();
		facesMessage.setSummary("Added OK");
		facesMessage.setDetail("Saved order " + c + " " + o);
		FacesContext.getCurrentInstance().addMessage(null, facesMessage);
		return "done";
	}
	
	public String saveOrderFail() {
		Customer c = ejb.findCustomer(1);
		
		Order o2 = new Order();
		o2.setDate(new Date());
		o2.setQuantity(86);

		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("(Simulated failure: setRollBackOnly)"));
		
		// The 3rd arg 'false' will invoke setRollbackOnly();
		ejb.saveCustomerOrder(c, o2, false);
		return "done"; // NOTREACHED
	}
}

