package org.lgulab;

import java.io.IOException;
import java.io.PrintWriter;

import javax.naming.Binding;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet to print a tree with all entries from the given name level <br>
 */
@WebServlet(urlPatterns = "/jndi/context/tree")
public class JndiContextTreeServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor.
	 */
	public JndiContextTreeServlet() {
		super();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out = response.getWriter();

		Context context;
		try {
			context = new InitialContext();
		} catch (NamingException e) {
			printError(out,e);
			return;
		}
		String contextNameToList = "";
		String name = request.getParameter("name");
		if (name != null) {
			contextNameToList = name.trim();

		} else {
			contextNameToList = "";
		}
		out.append("JNDI tree ('Binding' items) from root '" + contextNameToList + "' : \n");
		listContext(out, 1, context, contextNameToList);
	}
	
	private void printError(PrintWriter out, Exception e) {
		out.append("ERROR : \n" ); 
		out.append(e.getClass().getCanonicalName() + "\n"); 
		out.append(e.getMessage() + "\n"); 
	}
	private Object processItem(PrintWriter out, int level, Binding binding) {
		String name = binding.getName();
		String className = binding.getClassName();
		Object object = binding.getObject();
		//String fullName = binding.getNameInNamespace(); // UnsupportedOperationException
		StringBuilder sb = new StringBuilder();
		for ( int i=0 ; i < level ; i++ ) {
			sb.append("  ");
		}
		String indent = sb.toString();
		if (object instanceof javax.naming.Context) {
			out.append( indent + ". [" + name + "] (context) \n");
		}
		else {
			out.append( indent + ". " + name + " : " + className + "\n"); 
		}
		return object;
	}
	
	/**
	 * Print the JNDI tree recursively
	 * 
	 * @param context
	 * @param contextNameToList
	 */
	private final void listContext(PrintWriter out, int level, Context context, String contextNameToList) {
		try {
			NamingEnumeration<Binding> bindingsList = context.listBindings(contextNameToList);
			while (bindingsList.hasMore()) {
				Binding binding = bindingsList.next();
				Object object = processItem(out, level, binding);
				if (object instanceof javax.naming.Context) {
					listContext(out, level+1, (Context) object, "");
				}
			}
		} catch (NamingException e) {
			printError(out,e);
		}
	}
}
