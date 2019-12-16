package com.bomwebportal.lts.util;

public class LtsCommonHelper {
	
	public static <E extends Enum<E>> boolean isValidEnum(Class<E> enumClass, String enumName) {
	    if (enumName == null) {
	        return false;
	    }
	    try {
	        Enum.valueOf(enumClass, enumName);
	        return true;
	    } catch (IllegalArgumentException ex) {
	        return false;
	    }
	}
	
}
