package com.bomwebportal.util;

import org.apache.commons.lang.StringUtils;

public class ServiceNumberUtil {
	
    public static final String OUTSCOPE_PRODUCT_PW = "PW";
    public static final String OUTSCOPE_PRODUCT_NUI = "NUI";
    public static final String OUTSCOPE_PRODUCT_PSS = "PSS";
    public static final String OUTSCOPE_PRODUCT_DDS = "DDS";
    public static final String OUTSCOPE_PRODUCT_DDI = "DDI";

    public static final String INSCOPE_PRODUCT_FSA = "FSA";
    public static final String INSCOPE_PRODUCT_TEL = "TEL";
    public static final String INSCOPE_PRODUCT_MOB = "MOB";
    public static final String INSCOPE_PRODUCT_AGR = "AGR";
    public static final String INSCOPE_PRODUCT_AGRSIM = "AGRSIM";
    public static final String INSCOPE_PRODUCT_NCR = "NCR";
    public static final String INSCOPE_PRODUCT_ITS = "ITS";
    public static final String INSCOPE_PRODUCT_E0060 = "E0060";
    public static final String INSCOPE_PRODUCT_EWDN = "EWDN";
    public static final String INSCOPE_PRODUCT_3G = "3G";
	
	public static String formatSrvNum(String pSrvType, String pSrvNum){
		int strLength = 0;
		strLength = pSrvNum.length();
		
		if (pSrvType.equals(INSCOPE_PRODUCT_TEL) || pSrvType.equals(OUTSCOPE_PRODUCT_DDI) 
				|| pSrvType.equals(INSCOPE_PRODUCT_MOB) || pSrvType.equals(INSCOPE_PRODUCT_NCR)) {
			pSrvNum = StringUtils.leftPad(pSrvNum, 12, "0");
		} else if (pSrvType.equals(OUTSCOPE_PRODUCT_DDS) || pSrvType.equals(OUTSCOPE_PRODUCT_PW)){
			if (pSrvNum.charAt(strLength-1) != 'A' && pSrvNum.charAt(strLength-1) != 'B') {
				pSrvNum = pSrvNum + "A";
			}
			strLength = pSrvNum.length();
			if (pSrvType.equals(OUTSCOPE_PRODUCT_PW) && strLength < 6) {
				pSrvNum = StringUtils.leftPad(pSrvNum, 7, " ");
			}
		}
		return pSrvNum;
	}
}