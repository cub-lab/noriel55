package com.rsystems.noriel.parsing;
//15.11.2009 AME removed some special chars; used conversion to UTF-8
//18.11.2009 AME Corrected cleartags()
//22.11.2009 AME br is treated in clearTags; some methods are public - to be called sepparately
//32.01.2010 AME removed conversion to UTF-8 - it's made now in downloadPage.
//26.01.2010 AME Added replaceInQuery(). Should create a new class with formatting functions.


import java.util.ArrayList;
import java.util.List;

public class Parser implements ParsingMethods{

	public int first = -1;
	public int last = -1;
	protected boolean okMatch = false;
	protected boolean failed = false;
	protected String match = new String();
	public String lineSep = "\n"; //System.getProperty("line.separator"); // \r\n ??
	
	public Parser(){
	}
	
	public int seek(String stringToSeek, String stringSource, StringDirectionMod direction, StringActionMod action) {
		// TODO Auto-generated method stub
		
		if (direction.mod.equals("forward")){
			first = stringSource.indexOf(stringToSeek, first);
			if (first != -1){
				if (action.mod.equals("skip")){
					first = first + stringToSeek.length();
				}
			}
		}
		else if (direction.mod.equals("backward")){
			first = stringSource.lastIndexOf(stringToSeek, first);
			if (first != -1){
				if (action.mod.equals("skip")){
					first = first + stringToSeek.length();
				}}
		}
		return first;
	}
		
	public int seek(String stringToSeek, StringBuffer stringSource, StringDirectionMod direction, StringActionMod action) {
		// TODO Auto-generated method stub
		if (direction.mod.equals("forward")){
			first = stringSource.indexOf(stringToSeek, first);
			if (first != -1){
				if (action.mod.equals("skip")){
					first = first + stringToSeek.length();
				}
			}
		}
		else if (direction.mod.equals("backward")){
			first = stringSource.lastIndexOf(stringToSeek, first);
			if (first != -1){
				
				if (action.mod.equals("skip")){
					first = first + stringToSeek.length();
				}}
		}
		return first;
	}
	
	public String extract(String startMatch, String endMatch, StringBuffer content, StringActionMod actionFirst, StringActionMod actionLast){
		failed = false;
		first = content.indexOf(startMatch, first);
		if (first != -1){
			if (actionFirst.mod.equals("skip")){
				first = first + startMatch.length();}
			last = content.indexOf(endMatch, first);
			if (last != -1){
				if (actionLast.mod.equals("nothing")){ last = last + endMatch.length();}
				okMatch = true;
				match = content.substring(first, last);
				match = match.trim();
			}else{
				okMatch =false;
				
			}
		}else{
			failed = true;
			
		}
		return match;
	}
	
	public String extract(String startMatch, String endMatch, String content, StringActionMod actionFirst, StringActionMod actionLast){
		failed = false;
		first = content.indexOf(startMatch, first);
		if (first != -1){
			if (actionFirst.mod.equals("skip")){
				first = first + startMatch.length();}
			last = content.indexOf(endMatch, first);
			if (last != -1){
				if (actionLast.mod.equals("nothing")){ last = last + endMatch.length();}
				okMatch = true;
				match = content.substring(first, last);
				match = match.trim();
			}else{
				okMatch =false;
				
			}
		}else{
			failed = true;
			
		}
		return match;
	}
	
	
	// TODO: to implement this
	public List<String> extractRepeated(String startMatch, String endMatch, String content){
		List<String> matches = new ArrayList<String>();
		
		int beginIndex = -1; 
		int endIndex = beginIndex;
		String newMatch = "";
		
		do {
			beginIndex = content.indexOf(startMatch, endIndex + 1) + startMatch.length();
			endIndex = content.indexOf(endMatch, beginIndex+1);
			
			if (beginIndex == startMatch.length() -1 || endIndex == -1 || 
					beginIndex  > endIndex - 1){
				break;
			}
			
			newMatch = content.substring(beginIndex, endIndex);
			matches.add(newMatch);
			
		} while (true);
		
		return matches;
	}
	
	
	public String clearTags(String tags){

		tags = tags.replace("<br>", lineSep); 
		tags = tags.replace("<br />", lineSep);		
		tags = tags.replace(lineSep.concat(lineSep), lineSep);
		
		int begin,end;
		String tag = tags;
		while (tag.indexOf("<")!= -1 && tag.indexOf(">")!= -1){ // Serving Size : </b></td></tr><tr><td
			begin = tag.indexOf("<");			
			if ( begin != -1){
				end = tag.indexOf(">",begin);
				if (end != -1){			
					tags = tag.substring(0, begin) + " " + tag.substring(end+1,tag.length());
					tag = tags; 
				}
			}
		}
		return tags;
	}
	
	
	// this method replaces clearEnters, clearWhiteSP and clearDescription
	// TODO: this also replaces \n !!
	public String clearString(String tags){
		tags = tags.replaceAll("\\s", " ");
		return tags;
	}
	
//	private String clearEnters(String tags){
//		while (tags.indexOf("\n") != -1){
//			tags = tags.replaceAll("\n", " ");
//		}
//		return tags;
//	}

	private String clearNBSP(String tags){
         tags = tags.replaceAll("&nbsp;", "&");
        	tags = tags.trim();
        	if (tags.indexOf("&") == 0){
           	tags = tags.replace("&", "").trim();
         }
		return tags;
	}
	
	public String clearWhiteSP(String tags){
		//tags = tags.replaceAll("  ", "");
		while (tags.indexOf("  ") != -1){
			tags = tags.replace("  ", "").trim();
		}
		return tags;
	}
	
//	private String clearDescription(String tags){
//     
//		tags = tags.replaceAll("    		 	", "");
//		tags = tags.replaceAll("    		 ", "");
//
//	return tags;
// }
	
	public String replaceSpecialChars(String str){
		
		// general
		str = str.replace("&euro;", "�");
		str = str.replace("&nbsp;", " ");
		str = str.replace("&amp;", "&");
		str = str.replace("&#174;", "�");
		str = str.replace("&#039;", "'"); 
		str = str.replace("&#8217;", "'");
		str = str.replace("&reg;", "�");	
//		str = str.replace("&raquo;", ">>");
				
		str = str.replace("\u00A0", "");
		//str = str.replace("\n", " ");
		str = str.replace("\t", "");
//		
//		// spanish
//		str = str.replace("&ccedil;", "�");
//		str = str.replace("&atilde;", "�"); 
//		str = str.replace("&agrave", "�");
//		str = str.replace("&aacute;", "�");
//		str = str.replace("&otilde;", "�");
//		str = str.replace("&eacute;", "�");
//		str = str.replace("&iacute;", "�");
//		str = str.replace("&oacute;", "o"); // ??
//		str = str.replace("&Aacute;", "A"); //??
//		str = str.replace("&Oacute;", "O"); //??
//		str = str.replace("&Iacute;", "I"); //??
//
//		// german	
		
		

		String conv = str;
//		try {
//			conv = new String(str.getBytes(),"UTF-8");
//		} catch (UnsupportedEncodingException ex) {
//			ex.printStackTrace();
//		}
			
		return conv;
	}
	
	
	private String clearEnd(String tags){
     
		if (tags.trim().endsWith(",")){
			tags = tags.substring(0, tags.lastIndexOf(","));
		}
	return tags;
}
	
	public String formatField(String tags){		
			
		tags = clearTags(tags);
		tags = replaceSpecialChars(tags);
		
		//tags = clearNBSP(tags);
		//tags = clearString(tags);
		return tags.trim();
	}
	
	// formateaza din 16.04.2005 in yyyy-mm-dd

	public String formatDate(String date){ 
		
		String formatted = null;
		date = date.trim();
		
		if (date != null && date.length() > 0)
		{				
			if (date.indexOf(".") != -1 && date.length() > 4) // 16.04.2005
			{
				String d = date.substring(0, date.indexOf("."));
				if (d.length() == 1)
					d = "0" + d;
				String m = date.substring(date.indexOf(".") + 1, date.lastIndexOf("."));
				if (m.length() == 1)
					m = "0" + m;
				String y = date.substring(date.lastIndexOf(".") + 1);
				formatted = y + "-" + m + "-" + d;
			}
			else{ // avem doar an ( anul nasterii)
				formatted = date + "-01-01";
			}
		}
		else 
		{
			formatted = "1900-01-01";
		}
		return formatted;
	}
	
	// YYYY-MM-DD HH:MM:SS
	public String formatTimestamp(String date){
		String formatted = formatDate(date) + " 00:00:00";
		return formatted;
	}

	public boolean isNumber(String str){
		return true;
	}
	
	
	public static String replaceInQuery(String query, String tagName, String tagValue){
		
		String newQuery = "";
		
		if (query == null || tagName == null ){
			return newQuery;
		}
		
		if (query.indexOf(tagName + "=") == -1){
			return query;
		} else {
			int beginIndex = query.indexOf(tagName + "=");
			int endIndex = query.indexOf("&", beginIndex);
			if (endIndex == -1){
				endIndex = query.length();
			}
			String oldTag = query.substring(beginIndex, endIndex);
			newQuery = query.replace(oldTag, tagName + "=" + tagValue);
		}
		
		
		return newQuery;
	}
}
