package org.lgulab;

public class RemoteEJBNames {
	
public static final String CALCULATOR_NAME2 = //
        "ejb:calculator-ear/org.demo-calculator-jar-ejb-0.0.1-SNAPSHOT/CalculatorImpl!org.demo.ejb.calculator.CalculatorRemote";

public static final String CALCULATOR_NAME3 = // 
// syntax : "ejb:appName/moduleName/distinctName/beanName!viewClassName
        "ejb:calculator-ear/org.demo-calculator-jar-ejb-0.0.1-SNAPSHOT//CalculatorImpl!org.demo.ejb.calculator.CalculatorRemote";

//public static final String CALCULATOR_NAME4 = // NOT OK
//	"java:jboss/exported/calculator-ear/org.demo-calculator-jar-ejb-0.0.1-SNAPSHOT/CalculatorImpl!org.demo.ejb.calculator.CalculatorRemote";

public static final String CALCULATOR_JAVA_GLOBAL = // OK
	"java:global/calculator-ear/org.demo-calculator-jar-ejb-0.0.1-SNAPSHOT/CalculatorImpl!org.demo.ejb.calculator.CalculatorRemote";

}
