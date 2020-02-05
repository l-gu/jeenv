package org.lgulab;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet
 */
@WebServlet(urlPatterns = "/jndi/lookup")
public class JndiLookupServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor.
	 */
	public JndiLookupServlet() {
		super();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		
		String name = request.getParameter("name");
		if ( name != null ) {
			lookup(out, name);
		}
		else {
			out.append("No 'name' parameter :-/" );
		}
	}

	private void lookup(PrintWriter out, String name) {
		try {
			Object o = jndiLookup(name);
			out.append("OK : Found " );
			out.append("\n" );
			out.append(" . Name       = " + name);
			out.append("\n" );
			out.append(" . Class      = " + o.getClass().getCanonicalName() );
			out.append("\n" );
			out.append(" . toString() = " + o.toString() );
			out.append("\n" );
			showClass(out, o.getClass());
			
		} catch (Exception e) {
			out.append("ERROR : ");
			out.append("\n" );
			out.append("Exception :  " + e);
			out.append("\n" );
		}
	}
	
	private Object jndiLookup(String name) throws Exception {
		try {
			Context initialContext = new InitialContext();
			Object o = initialContext.lookup(name); //
			if ( o != null ) {
//				Class<?> clazz = o.getClass();
//				if ( clazz.isInterface() ) {
//					return o;
//				}
//				else {
//					throw new Exception("Found but class " + clazz.getCanonicalName() + " is not an interface");
//				}
				return o;
			}
			else {
				throw new Exception("Not found ( '" + name + "' )");
			}
		} catch (NamingException e) {
			throw new Exception("NamingException", e);
		}		
	}

	private void showClass(PrintWriter out, Class<?> clazz) {
		out.append("METHODS : \n");
		for ( Method m : clazz.getMethods() ) {
			int n = 0 ;
			out.append(" . " + m.getName() + " ( ");
			for ( Parameter p : m.getParameters() ) {
				if ( n > 0 ) {
					out.append(", ");
				}
				n++;
				out.append(p.getType().getSimpleName() + " " + p.getName() ) ;
			}
			out.append(" ) \n");
		}
	}
}
