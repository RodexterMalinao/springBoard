package com.bomwebportal.lts.util;

import org.apache.commons.lang.StringUtils;

public class LtsProfileHelper {

	public static String[] reformatSrvBoundary(String pSb) {
		
		String[] reformatedSb = new String[2];
		reformatedSb[0] = pSb;
		
		if (pSb.length() < 6) {
			reformatedSb[1] = StringUtils.leftPad(pSb, 6, '0'); 
		} else if (pSb.indexOf('0') == 0) {
			reformatedSb[1] = pSb.replaceFirst("^0+(?!$)", "");
		}
		return reformatedSb;
	}
}
