package webtier;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@RequestScoped
public class JsfErrorBean {
	
	/** The the current error's throwable, if there is one. */
	public Throwable getThrowable() {
		FacesContext context = FacesContext.getCurrentInstance();
		Map<String, Object> request = context.getExternalContext()
				.getRequestMap();
		return (Throwable) request.get("javax.servlet.error.exception");
	}
	
	/**
	 * Get the throwable's stacktrace, as a String.
	 * Hopefully you will place it in a <pre> or equivalent.
	 * @return The stacktrace
	 */
	public String getStacktrace() {
		StringWriter so = new StringWriter();
		try (final PrintWriter pw = new PrintWriter(so)) {
			getThrowable().printStackTrace(pw);
			return so.toString();
		}
	}
}
