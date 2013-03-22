package com.android.findme.util;

public class ValidateUtils {

	public static boolean validateIsNull(Object... objects){
		for(Object o : objects){
			if(o == null){
				return true;
			}
		}
		return false;
	}
}
