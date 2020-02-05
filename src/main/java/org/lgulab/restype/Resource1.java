package org.lgulab.restype;

public class Resource1 {

	private String name ;

	public Resource1() {
		super();
		this.name = "no-name";
		System.out.println(this.getClass().getCanonicalName() + " default constructor ");
	}
	public Resource1(String name) {
		super();
		this.name = name;
		System.out.println(this.getClass().getCanonicalName() + " constructor(" + name + ")");
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
