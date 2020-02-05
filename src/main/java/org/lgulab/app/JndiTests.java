package org.lgulab.app;

import java.util.Hashtable;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class JndiTests {

	public static void main(String[] args) throws NamingException {

		/*
		 * The javax.naming package comprises the JNDI API. Since it's just an API,
		 * rather than an implementation, you need to tell it which implementation of
		 * JNDI to use. The implementations are typically specific to the server you're
		 * trying to talk to. To specify an implementation, you pass in a Properties
		 * object when you construct the InitialContext. These properties specify the
		 * implementation to use, as well as the location of the server. The default
		 * InitialContext constructor is only useful when there are system properties
		 * present, but the properties are the same as if you passed them in manually.
		 */

//		Context context = getContext1(); // NoInitialContextException: Need to specify class name in environment or system property...
//		Context context = getContextForActiveMQ(); // ClassNotFoundException: org.apache.activemq.jndi.ActiveMQInitialContextFactory
//		Context context = getContextForLDAP(); // java.net.UnknownHostException: ldap.wiz.com
		Context context = getContextForFS(); // ClassNotFoundException: com.sun.jndi.fscontext.RefFSContextFactory
//		context.bind("jndiName", "value");
		context.close();
	}

	public static Context getContext1() throws NamingException {
		Context context = new InitialContext(); // no INITIAL_CONTEXT_FACTORY
		return context;
	}

	public static Context getContextForActiveMQ() throws NamingException {
		Properties props = new Properties();
		props.setProperty(Context.INITIAL_CONTEXT_FACTORY, "org.apache.activemq.jndi.ActiveMQInitialContextFactory");
		props.setProperty(Context.PROVIDER_URL, "tcp://localhost:61616");
		Context context = new InitialContext(props); // InitialContext implements Context
		return context;
	}

	/**
	 * INITIAL_CONTEXT_FACTORY : LDAP service provider from Sun Microsystems
	 * @return
	 * @throws NamingException
	 */
	public static Context getContextForLDAP() throws NamingException {
		Hashtable<String, String> env = new Hashtable<>();
		env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
		env.put(Context.PROVIDER_URL, "ldap://ldap.wiz.com:389");
		env.put(Context.SECURITY_PRINCIPAL, "joe-user");
		env.put(Context.SECURITY_CREDENTIALS, "joe-password");
		Context context = new InitialContext(env); // InitialContext implements Context
		return context;
	}

	/**
	 * INITIAL_CONTEXT_FACTORY : File System service provider from Sun Microsystems
	 * @return
	 * @throws NamingException
	 */
	public static Context getContextForFS() throws NamingException {
		Hashtable<String, String> env = new Hashtable<>();
		env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.fscontext.RefFSContextFactory"); // Maven dependency not found !!!
		env.put(Context.PROVIDER_URL, "file:c:/"); 
		Context context = new InitialContext(env); // InitialContext implements Context
		return context;
	}
}
