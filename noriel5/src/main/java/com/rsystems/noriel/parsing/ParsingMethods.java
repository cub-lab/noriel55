package com.rsystems.noriel.parsing;


public interface ParsingMethods {

    /**
     * Used to seek a substring into string-this method returns the start index
     * of the substring(if action==NOTHING)or the index after the substring(if action==SKIP) 
     * @param stringToSeek-is substring to seek		
     * @param stringSource-is the string in which is searched the substring
     * @param direction-is the direction in which it will be started to search(FORWARD/BACKWARD)
     * @param action-used to finish the substring search at the beginning of the stringToMatch(NOTHING) or after the stringToMatch(SKIP)   
     */
	public int seek(String stringToMatch, String stringSource, StringDirectionMod direction, StringActionMod action);
    
	/**
     * Used to seek a substring into string(type:StringBuffer)-this method returns the start index
     * of the substring(if action==NOTHING)or the index after the substring(if action==SKIP) 
     * @param stringToSeek-is substring to seek		
     * @param stringSource-is the string(type:StringBuffer) in which is searched the substring
     * @param direction-is the direction in which it will be started to search(FORWARD/BACKWARD)
     * @param action-used to finish the substring search at the beginning of the stringToMatch(NOTHING) or after the stringToMatch(SKIP)   
     */
	public int seek(String stringToMatch, StringBuffer stringSource, StringDirectionMod direction, StringActionMod action);
	
	/**
	 * Used to extract a substring(between startMatch and endMatch) from a stringSource-this method returns the extracted substring
	 * @param startMatch-the string before the substring
	 * @param endMatch-the string after the substring
	 * @param stringSource-the string from which is wanted to be extracted the substring
	 * @param actionFirst-(SKIP-if startMatch is wanted to miss from the substring extracted/NOTHING-to be included)
	 * @param actionLast-(SKIP-if endMatch is wanted to miss from the substring extracted/NOTHING-to be included)
	 * @return
	 */
	public String extract(String startMatch, String endMatch, String stringSource, StringActionMod actionFirst, StringActionMod actionLast);
	
	/**
	 * Used to extract a substring(between startMatch and endMatch) from a stringSource(type:StringBuffer)-this method returns the extracted substring
	 * @param startMatch-the string before the substring
	 * @param endMatch-the string after the substring
	 * @param stringSource-the string from which is wanted to be extracted the substring
	 * @param actionFirst-(SKIP-if startMatch is wanted to miss from the substring extracted/NOTHING-to be included)
	 * @param actionLast-(SKIP-if endMatch is wanted to miss from the substring extracted/NOTHING-to be included)
	 * @return
	 */
	public String extract(String startMatch, String endMatch, StringBuffer stringSource, StringActionMod actionFirst, StringActionMod actionLast);
}

