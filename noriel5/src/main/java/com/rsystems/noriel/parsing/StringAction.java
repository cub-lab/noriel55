package com.rsystems.noriel.parsing;

public class StringAction {
	/**
	 * @ Move the index after the string for which is used
	 */
	public StringActionMod SKIP = new StringActionMod("skip");
	
	/**
	 * @ if it is used for SEEK method - Keep the index at the beginning of a string for which is used
	 * @ If it is used for EXTRACT method, NOTHING will include the endMatch into substring
	 */
	public StringActionMod NOTHING = new StringActionMod("nothing");
	
	//public StringAction SKIP1 = new StringAction();
	
	public StringAction(){}
}

