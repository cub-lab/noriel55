package com.rsystems.noriel.parsing;

public class StringDirection {
	/**
	 * @ The direction in which is started to search a substring into a string.        
	 *  FORWARD - starting to RIGHT from stringToMatch
	 */
	public StringDirectionMod FORWARD = new StringDirectionMod("forward");
	
	/**
	 * @ The direction in which is started to search a substring into a string.     
	 *  BACKWARD - starting to LEFT from stringToMatch
	 */
	public StringDirectionMod BACKWARD = new StringDirectionMod("backward");
	
	public StringDirection(){}
}

