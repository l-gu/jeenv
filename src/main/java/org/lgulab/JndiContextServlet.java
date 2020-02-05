package org.lgulab;

import java.io.IOException;
import java.io.PrintWriter;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NameClassPair;
import javax.naming.NamingEnumeration;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet
 */
@WebServlet(urlPatterns = "/jndi/context")
public class JndiContextServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor.
	 */
	public JndiContextServlet() {
		super();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		
		String name = request.getParameter("name");
		if ( name != null ) {
			listContextEntries(out, name.trim());
		}
		else {
			listContextEntries(out, "");
		}

	}

	private void listContextEntries(PrintWriter out, String name) {
		out.append("JNDI entries for context name '" + name + "' : \n");
		try {
			Context context = new InitialContext();
			NamingEnumeration<NameClassPair> list = context.list(name);
			while (list.hasMore()) {
				NameClassPair pair = list.next();
				out.append(" . '" + pair.getName() + "' " 
						+ ( pair.isRelative() ? "(relative)" : "(not relative)")
						+ " : '" + pair.getClassName() + "' "
						+ getMoreInfo(pair) );
				out.append("\n");
			}
		} catch (Exception e) {
			out.append("ERROR : ");
			out.append("\n");
			out.append("Exception :  " + e);
			out.append("\n");
		}
	}

	private String getMoreInfo(NameClassPair pair) {
		String info = "";
		String nameInNamespace = null ;
		try {
			// throws UnsupportedOperationException if undefined
			nameInNamespace = pair.getNameInNamespace();
		} catch (UnsupportedOperationException e) {
			nameInNamespace = null ;
		}
		if ( nameInNamespace != null ) {
			info = "(" + nameInNamespace + ")";
		}
		return info;
	}
}
