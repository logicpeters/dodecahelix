package org.dmp.gwtpurdy.client.utils;

public class GwtStringUtils {

	/**
	 *  Returns true if string is null or only blank space(s)
	 * 
	 * @param string
	 * @return
	 */
	public static boolean isBlank(String string) {
		boolean blank = false;
		if (string==null || string.trim().equals("")) {
			blank = true;
		}
		return blank;
	}
	
}
