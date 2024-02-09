package com.prosperica.util;


public class UtilString {

	final static public String className = UtilString.class.getName();
	final static public long serialVersionUID = className.hashCode();

	static private String SPACES = "                                                                                 ";
	
	final static public String alignRight(Object o, int n){
		return alignRight(o.toString().trim(), n);
	}
	
	final static public String alignRight(String s, int n){
		int length = s.length();
		if( length > n){
			s = s.trim();
			length = s.length();
		}
		if( length < n)
			return (SPACES.substring(0, n-length))+s;
		return s;
	}
	
	final static public String alignLeft(String s, int n){
		int length = s.length();
		if( length > n){
			s = s.trim();
			length = s.length();
		}
		if( length < n)
			return s+(SPACES.substring(0, n-length));
		return s;
	}
	
	final static public String alignCenter(String s, int n){
		int length = s.length();
		if( length > n){
			s = s.trim();
			length = s.length();
		}
		if( length < n){
			int left = (n-length)/2;
			int right = (n-length)-left;
			return (SPACES.substring(0, left))+s+SPACES.substring(0, right);
		}
		return s;
	}
	
	final static public String getRightString(String str, String[] substr) {
		for(int i=0, idx; i<substr.length; i++){
			if( (idx = str.indexOf(substr[i])) != -1 ){
				return str.substring(idx+substr[i].length());
			}
		}
		return null;
	}

	final static public boolean containsString(String str, String[] substr) {
		for(int i=0; i<substr.length; i++){
			if( str.indexOf(substr[i]) != -1 )
				return true;
		}
		return false;
	}

	final static public String getLeftString(String str, String[] substr) {
		for(int i=0, idx; i<substr.length; i++){
			if( (idx=str.indexOf(substr[i])) != -1 ){
				return str.substring(0, idx);
			}
		}
		return null;
	}

	final static public String capitolize(String str) {
		if( str.length() > 1)
			return str.substring(0,1).toUpperCase()+str.substring(1).toLowerCase();
		else
			return str.toUpperCase();
	}

	final static public String buildIdentifier(String str) {
		String newStr = "";
		
		String[] words = str.split("[ ()-]");
		for(int i=0; i<words.length; i++){
			newStr += capitolize(words[i]);
		}
		return newStr;
	}

}


