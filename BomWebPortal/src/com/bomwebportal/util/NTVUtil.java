package com.bomwebportal.util;

import org.apache.commons.lang.StringUtils;


import com.bomwebportal.dao.NTVUtlDAO;
import com.bomwebportal.exception.DAOException;

public class NTVUtil {
	private static  NTVUtlDAO dao;
	private static boolean bInit = false;
	private static boolean rInit = false;
	
	public static boolean isbInit() {
		return bInit;
	}

	public static void setbInit(boolean bInit) {
		NTVUtil.bInit = bInit;
	}

	public static boolean isrInit() {
		return rInit;
	}

	public static void setrInit(boolean rInit) {
		NTVUtil.rInit = rInit;
	}

	public NTVUtlDAO getDao() {
		return dao;
	}

	public void setDao(NTVUtlDAO udao) {
		dao = udao;
	}

	public static String[][] STRING_REPLACEMENT = null;
	
	public static String[][] STRING_REPACEMENT_RPT = null;
	//new String[][] {
//		{"now TV", "<span class='now'>now</span> TV"},
//		{"nowTV", "<span class='now'>now</span>TV"},
//		{"now \u5BEC\u983B\u96FB\u8996", "<span class='now'>now</span> \u5BEC\u983B\u96FB\u8996"},
//		{"now\u5BEC\u983B\u96FB\u8996", "<span class='now'>now</span>\u5BEC\u983B\u96FB\u8996"},
//		{"[now]", "<span class='now'>now</span>"},
//		{"[eye]", "<span class='eye'>eye</span>"},
//		{"eye Home", "<span class='eye'>eye</span> Home"},
//		{"EYE HOME", "<span class='eye'>eye</span> HOME"},
//		{"eye HOME", "<span class='eye'>eye</span> HOME"},
//		{"eye \u5BB6\u5C45", "<span class='eye'>eye</span>\u5BB6\u5C45"},
//		{"eye\u5BB6\u5C45", "<span class='eye'>eye</span>\u5BB6\u5C45"},
//		{"EYE \u5BB6\u5C45", "<span class='eye'>eye</span>\u5BB6\u5C45"},
//		{"EYE\u5BB6\u5C45", "<span class='eye'>eye</span>\u5BB6\u5C45"},
//		
//		//Special characters (en)
//		{"now TV","<span class='now'>now</span> TV"},
//		{"now player","<span class='now'>now</span> player"},
//		{"now Select","<span class='now'>now</span> Select"},
//		{"now 101","<span class='now'>now</span> 101"},
//		{"now Super 4","<span class='now'>now</span> Super 4"},
//		{"now Baogu","<span class='now'>now</span> Baogu"},
//		{"now HaiRun","<span class='now'>now</span> HaiRun"},
//		{"now News","<span class='now'>now</span> News"},
//		{"now Data Channel","<span class='now'>now</span> Data Channel"},
//		{"now Direct","<span class='now'>now</span> Direct"},
//		{"now Business News Channel","<span class='now'>now</span> Business News Channel"},
//		{"now Hong Kong","<span class='now'>now</span> Hong Kong"},
//		{"now Entertainment Channel","<span class='now'>now</span> Entertainment Channel"},
//		{"now Entertainment Drama Combo ","<span class='now'>now</span> Entertainment Drama Combo "},
//		{"now Variety Entertainment Combo","<span class='now'>now</span> Variety Entertainment Combo"},
//		{"now Mango","<span class='now'>now</span> Mango"},
//		{"now SPORTS","<span class='now'>now</span> SPORTS"},
//		{"now SPORTS Prime","<span class='now'>now</span> SPORTS Prime"},
//		{"now Golf","<span class='now'>now</span> Golf"},
//		{"now DOLLAR","<span class='now'>now</span> DOLLAR"},
//		{"now VIDEO Express","<span class='now'>now</span> VIDEO Express"},
//		{"now Record","<span class='now'>now</span> Record"},
//		{"now Game","<span class='now'>now</span> Game"},
//		{"now Shop","<span class='now'>now</span> Shop"},
//		{"eye2 Device","<span class='eye'>eye</span>2 Device"},
//		{"eye Home Smartphone","<span class='eye'>eye</span> Home Smartphone"},
//		{"PS3TM","PS3\u2122"},
//		//Special characters (chi)
//		{"now \u5BEC\u983B\u96FB\u8996","<span class='now'>now</span> \u5BEC\u983B\u96FB\u8996"},
//		{"now \u96A8\u8EAB\u7747","<span class='now'>now</span> \u96A8\u8EAB\u7747"},
//		{"now \u81EA\u9078\u670D\u52D9","<span class='now'>now</span> \u81EA\u9078\u670D\u52D9"},
//		{"now 101","<span class='now'>now</span> 101"},
//		{"now Super 4","<span class='now'>now</span> Super 4"},
//		{"now \u7206\u8C37\u53F0","<span class='now'>now</span> \u7206\u8C37\u53F0"},
//		{"now \u6D77\u6F64\u53F0","<span class='now'>now</span> \u6D77\u6F64\u53F0"},
//		{"now \u65B0\u805E\u53F0","<span class='now'>now</span> \u65B0\u805E\u53F0"},
//		{"now \u5831\u50F9\u53F0","<span class='now'>now</span> \u5831\u50F9\u53F0"},
//		{"now \u76F4\u64AD\u53F0","<span class='now'>now</span> \u76F4\u64AD\u53F0"},
//		{"now \u8CA1\u7D93\u53F0","<span class='now'>now</span> \u8CA1\u7D93\u53F0"},
//		{"now \u9999\u6E2F\u53F0","<span class='now'>now</span> \u9999\u6E2F\u53F0"},
//		{"now \u89C0\u661F\u53F0","<span class='now'>now</span> \u89C0\u661F\u53F0"},
//		{"now \u5A1B\u6A02\u5287\u96C6\u7D44\u5408","<span class='now'>now</span> \u5A1B\u6A02\u5287\u96C6\u7D44\u5408"},
//		{"now \u7D9C\u85DD\u5A1B\u6A02\u7D44\u5408","<span class='now'>now</span> \u7D9C\u85DD\u5A1B\u6A02\u7D44\u5408"},
//		{"now \u8292\u679C\u53F0","<span class='now'>now</span> \u8292\u679C\u53F0"},
//		{"now SPORTS","<span class='now'>now</span> SPORTS"},
//		{"now SPORTS \u7CBE\u9078","<span class='now'>now</span> SPORTS \u7CBE\u9078"},
//		{"now Golf","<span class='now'>now</span> Golf"},
//		{"now DOLLAR","<span class='now'>now</span> DOLLAR"},
//		{"now \u7387\u5148\u7747","<span class='now'>now</span> \u7387\u5148\u7747"},
//		{"now\u9304\u5F71\u670D\u52D9","<span class='now'>now</span>\u9304\u5F71\u670D\u52D9"},
//		{"now Game","<span class='now'>now</span> Game"},
//		{"now Shop","<span class='now'>now</span> Shop"},
//		{"eye2 \u4E3B\u6A5F","<span class='eye'>eye</span>2 \u4E3B\u6A5F"},
//		{"eye \u5BB6\u5C45\u667A\u80FD\u96FB\u8A71","<span class='eye'>eye</span> \u5BB6\u5C45\u667A\u80FD\u96FB\u8A71"},
//		{"PS3TM","PS3\u2122"},
//
//
//		//steven added start 20131004
//		{"now \u8ca1\u7d93\u53f0", "<span class='now'>now</span> \u8ca1\u7d93\u53f0"},
//		{"now Sports \u7cbe\u9078", "<span class='now'>now</span> Sports \u7cbe\u9078"},
//		{"now PLAYER", "<span class='now'>now</span> PLAYER"},
//		{"now SELECT", "<span class='now'>now</span> SELECT"},
//		{"now SUPER 4", "<span class='now'>now</span> SUPER 4"},
//		{"now BAOGU", "<span class='now'>now</span> BAOGU"},
//		{"now NEWS", "<span class='now'>now</span> NEWS"},
//		{"now DIRECT", "<span class='now'>now</span> DIRECT"},
//		{"now BNC", "<span class='now'>now</span> BNC"},
//		{"now HK", "<span class='now'>now</span> HK"},
//		{"now Sports Prime", "<span class='now'>now</span> Sports Prime"},
//		{"now GOLF", "<span class='now'>now</span> GOLF"},
//		{"now Video Express", "<span class='now'>now</span> Video Express"},
//		{"now RECORD", "<span class='now'>now</span> RECORD"},
//		{"now GAME", "<span class='now'>now</span> GAME"},
//		{"now SHOP", "<span class='now'>now</span> SHOP"},
//		{"PS3TM", "PS3\u2122"},
//		{"nowBAOGU", "<span class='now'>now</span>BAOGU"},
//		{"nowHaiRun", "<span class='now'>now</span>HaiRun"},
//		{"nowNEWS", "<span class='now'>now</span>NEWS"},
//		{"nowData Channel", "<span class='now'>now</span>Data Channel"},
//		{"nowDIRECT", "<span class='now'>now</span>DIRECT"},
//		{"nowBusiness News Channel", "<span class='now'>now</span>Business News Channel"},
//		{"nowBNC", "<span class='now'>now</span>BNC"},
//		{"nowHK", "<span class='now'>now</span>HK"},
//		{"nowEntertainment Channel", "<span class='now'>now</span>Entertainment Channel"},
//		{"nowEntertainment Drama Combo ", "<span class='now'>now</span>Entertainment Drama Combo "},
//		{"nowVariety Entertainment Combo", "<span class='now'>now</span>Variety Entertainment Combo"},
//		{"nowSports", "<span class='now'>now</span>Sports"},
//		{"nowSports Prime", "<span class='now'>now</span>Sports Prime"},
//		{"nowGOLF", "<span class='now'>now</span>GOLF"},
//		{"nowDOLLAR", "<span class='now'>now</span>DOLLAR"},
//		{"nowVideo Express", "<span class='now'>now</span>Video Express"},
//		{"nowRECORD", "<span class='now'>now</span>RECORD"},
//		{"nowGAME", "<span class='now'>now</span>GAME"},
//		{"nowSHOP", "<span class='now'>now</span>SHOP"},
//		{"nowplayer", "<span class='now'>now</span>player"},
//		{"nowSelect", "<span class='now'>now</span>Select"},
//		{"nowBaogu", "<span class='now'>now</span>Baogu"},
//		{"nowHaiRun", "<span class='now'>now</span>HaiRun"},
//		{"nowNews", "<span class='now'>now</span>News"},
//		{"nowData Channel", "<span class='now'>now</span>Data Channel"},
//		{"nowDirect", "<span class='now'>now</span>Direct"},
//		{"nowBusiness News Channel", "<span class='now'>now</span>Business News Channel"},
//		{"nowHong Kong", "<span class='now'>now</span>Hong Kong"},
//		{"nowEntertainment Channel", "<span class='now'>now</span>Entertainment Channel"},
//		{"nowEntertainment Drama Combo ", "<span class='now'>now</span>Entertainment Drama Combo "},
//		{"nowVariety Entertainment Combo", "<span class='now'>now</span>Variety Entertainment Combo"},
//		{"nowMango", "<span class='now'>now</span>Mango"},
//		{"nowSPORTS", "<span class='now'>now</span>SPORTS"},
//		{"nowSPORTS Prime", "<span class='now'>now</span>SPORTS Prime"},
//		{"nowGolf", "<span class='now'>now</span>Golf"},
//		{"nowDOLLAR", "<span class='now'>now</span>DOLLAR"},
//		{"nowVIDEO Express", "<span class='now'>now</span>VIDEO Express"},
//		{"nowRecord", "<span class='now'>now</span>Record"},
//		{"nowGame", "<span class='now'>now</span>Game"},
//		{"nowShop", "<span class='now'>now</span>Shop"},
//		{"now TV", "<span class='now'>now</span> TV"},
//		{"now Sports", "<span class='now'>now</span> Sports"},
//		{"now SPORTS Prime", "<span class='now'>now</span> SPORTS Prime"},
//		{"now NEWS Channel", "<span class='now'>now</span> NEWS Channel"},
//		{"now Business News Channel", "<span class='now'>now</span> Business News Channel"},
//		{"now \u5BEC\u983B\u96FB\u8996", "<span class='now'>now</span>\u5BEC\u983B\u96FB\u8996"},
//		{"now\u5BEC\u983B\u96FB\u8996", "<span class='now'>now</span>\u5BEC\u983B\u96FB\u8996"},
//		{"now\u65b0\u805e\u53f0", "<span class='now'>now</span>\u65b0\u805e\u53f0"},
//		{"now\u8ca1\u7d93\u53f0", "<span class='now'>now</span>\u8ca1\u7d93\u53f0"},
//		{"now SPORTS \u7cbe\u9078", "<span class='now'>now</span> SPORTS \u7cbe\u9078"},
//		{"[now]", "<span class='now'>now</span>"},
//		{"now player", "<span class='now'>now</span> player"},
//		{"now Select", "<span class='now'>now</span> Select"},
//		{"now 101", "<span class='now'>now</span> 101"},
//		{"now 621", "<span class='now'>now</span> 621"},
//		{"now 622", "<span class='now'>now</span> 622"},
//		{"now 623", "<span class='now'>now</span> 623"},
//		{"now 624", "<span class='now'>now</span> 624"},
//		{"now 625", "<span class='now'>now</span> 625"},
//		{"now Super 4", "<span class='now'>now</span> Super 4"},
//		{"now Baogu", "<span class='now'>now</span> Baogu"},
//		{"now HaiRun", "<span class='now'>now</span> HaiRun"},
//		{"now News", "<span class='now'>now</span> News"},
//		{"now Data Channel", "<span class='now'>now</span> Data Channel"},
//		{"now Direct", "<span class='now'>now</span> Direct"},
//		{"now Hong Kong", "<span class='now'>now</span> Hong Kong"},
//		{"now Entertainment Channel", "<span class='now'>now</span> Entertainment Channel"},
//		{"now Entertainment Drama Combo ", "<span class='now'>now</span> Entertainment Drama Combo "},
//		{"now Variety Entertainment Combo", "<span class='now'>now</span> Variety Entertainment Combo"},
//		{"now Mango", "<span class='now'>now</span> Mango"},
//		{"now SPORTS", "<span class='now'>now</span> SPORTS"},
//		{"now Golf", "<span class='now'>now</span> Golf"},
//		{"now DOLLAR", "<span class='now'>now</span> DOLLAR"},
//		{"now VIDEO Express", "<span class='now'>now</span> VIDEO Express"},
//		{"now Record", "<span class='now'>now</span> Record"},
//		{"now Game", "<span class='now'>now</span> Game"},
//		{"now Shop", "<span class='now'>now</span> Shop"},
//		{"eye2 Device", "<span class='eye'>eye</span> Device"},
//		{"eye Home Smartphone", "<span class='eye'>eye</span> Home Smartphone"},
//		{"now \u96a8\u8eab\u7747", "<span class='now'>now</span> \u96a8\u8eab\u7747"},
//		{"now \u81ea\u9078\u670d\u52d9", "<span class='now'>now</span> \u81ea\u9078\u670d\u52d9"},
//		{"now \u7206\u8c37\u53f0", "<span class='now'>now</span> \u7206\u8c37\u53f0"},
//		{"now \u6d77\u6f64\u53f0", "<span class='now'>now</span> \u6d77\u6f64\u53f0"},
//		{"now \u65b0\u805e\u53f0", "<span class='now'>now</span> \u65b0\u805e\u53f0"},
//		{"now \u5831\u50f9\u53f0", "<span class='now'>now</span> \u5831\u50f9\u53f0"},
//		{"now \u76f4\u64ad\u53f0", "<span class='now'>now</span> \u76f4\u64ad\u53f0"},
//		{"now \u9999\u6e2f\u53f0", "<span class='now'>now</span> \u9999\u6e2f\u53f0"},
//		{"now \u89c0\u661f\u53f0", "<span class='now'>now</span> \u89c0\u661f\u53f0"},
//		{"now \u5a1b\u6a02\u5287\u96c6\u7d44\u5408", "<span class='now'>now</span> \u5a1b\u6a02\u5287\u96c6\u7d44\u5408"},
//		{"now \u7d9c\u85dd\u5a1b\u6a02\u7d44\u5408", "<span class='now'>now</span> \u7d9c\u85dd\u5a1b\u6a02\u7d44\u5408"},
//		{"now \u8292\u679c\u53f0", "<span class='now'>now</span> \u8292\u679c\u53f0"},
//		{"now \u7387\u5148\u7747", "<span class='now'>now</span> \u7387\u5148\u7747"},
//		{"now\u9304\u5f71\u670d\u52d9", "<span class='now'>now</span>\u9304\u5f71\u670d\u52d9"},
//		{"eye2 \u4e3b\u6a5f", "<span class='eye'>eye2</span> \u4e3b\u6a5f"},
//		{"eye\u5bb6\u5c45\u667a\u80fd\u96fb\u8a71","<span class='eye'>eye</span>\u5bb6\u5c45\u667a\u80fd\u96fb\u8a71"},
//		{"now\u96a8\u8eab\u7747","<span class='now'>now</span>\u96a8\u8eab\u7747"},
//		{"now\u81ea\u9078\u670d\u52d9","<span class='now'>now</span>\u81ea\u9078\u670d\u52d9"},
//		{"now\u7206\u8c37\u53f0","<span class='now'>now</span>\u7206\u8c37\u53f0"},
//		{"now\u6d77\u6f64\u53f0","<span class='now'>now</span>\u6d77\u6f64\u53f0"},
//		{"now\u65b0\u805e\u53f0","<span class='now'>now</span>\u65b0\u805e\u53f0"},
//		{"now\u5831\u50f9\u53f0","<span class='now'>now</span>\u5831\u50f9\u53f0"},
//		{"now\u76f4\u64ad\u53f0","<span class='now'>now</span>\u76f4\u64ad\u53f0"},
//		{"now\u9999\u6e2f\u53f0","<span class='now'>now</span>\u9999\u6e2f\u53f0"},
//		{"now\u89c0\u661f\u53f0","<span class='now'>now</span>\u89c0\u661f\u53f0"},
//		{"now\u5a1b\u6a02\u5287\u96c6\u7d44\u5408","<span class='now'>now</span>\u5a1b\u6a02\u5287\u96c6\u7d44\u5408"},
//		{"now\u7d9c\u85dd\u5a1b\u6a02\u7d44\u5408","<span class='now'>now</span>\u7d9c\u85dd\u5a1b\u6a02\u7d44\u5408"},
//		{"now\u8292\u679c\u53f0","<span class='now'>now</span>\u8292\u679c\u53f0"},
//		{"nowSPORTS","<span class='now'>now</span>SPORTS"},
//		{"nowGolf","<span class='now'>now</span>Golf"},
//		{"nowDOLLAR","<span class='now'>now</span>DOLLAR"},
//		{"now\u7387\u5148\u7747","<span class='now'>now</span>\u7387\u5148\u7747"},
//		{"now\u9304\u5f71\u670d\u52d9","<span class='now'>now</span>\u9304\u5f71\u670d\u52d9"},
//		{"nowGame","<span class='now'>now</span>Game"},
//		{"nowShop","<span class='now'>now</span>Shop"},
//		{"eye2\u4e3b\u6a5f","<span class='eye'>eye2</span>\u4e3b\u6a5f"},
//		{"eye\u5bb6\u5c45\u667a\u80fd\u96fb\u8a71","<span class='eye'>eye</span>\u5bb6\u5c45\u667a\u80fd\u96fb\u8a71"},
//		{"[eye]", "<span class='eye'>eye</span>"},
//		{"eye Home", "<span class='eye'>eye</span> Home"},
//		{"EYE HOME", "<span class='eye'>eye</span> HOME"},
//		{"eye HOME", "<span class='eye'>eye</span> HOME"},
//		{"eye Multimedia Service", "<span class='eye'>eye</span> Multimedia Service"},
//		{"eye \u5BB6\u5C45", "<span class='eye'>eye</span>\u5BB6\u5C45"},
//		{"eye\u5BB6\u5C45", "<span class='eye'>eye</span>\u5BB6\u5C45"},
//		{"EYE \u5BB6\u5C45", "<span class='eye'>eye</span>\u5BB6\u5C45"},
//		{"eye\u591a\u5a92\u7747", "<span class='eye'>eye</span>\u591a\u5a92\u7747"},
//		{"EYE\u5BB6\u5C45", "<span class='eye'>eye</span>\u5BB6\u5C45"},
//		{"eye3 Smart", "<span class='eye'>eye3</span> Smart"},
//		{"EYE3 SMART", "<span class='eye'>eye3</span> SMART"},
//		{"eye3 SMART", "<span class='eye'>eye3</span> SMART"},
//		{"eye3 \u667a\u80fd", "<span class='eye'>eye3</span>\u667a\u80fd"},
//		{"eye3\u667a\u80fd", "<span class='eye'>eye3</span>\u667a\u80fd"},
//		{"EYE3 \u667a\u80fd", "<span class='eye'>eye3</span>\u667a\u80fd"},
//		{"EYE3\u667a\u80fd", "<span class='eye'>eye3</span>\u667a\u80fd"},
//		{"eye3 Device", "<span class='eye'>eye3</span> Device"},
//		{"eye3\u4e3b\u6a5f", "<span class='eye'>eye3</span>\u4e3b\u6a5f"},
//		{"eye2 Communication Package", "<span class='eye'>eye2</span> Communication Package"},
//		{"eye2 \u901a\u8a0a\u7d44\u5408", "<span class='eye'>eye2</span>\u901a\u8a0a\u7d44\u5408"},
//		{"eye2\u901a\u8a0a\u7d44\u5408", "<span class='eye'>eye2</span>\u901a\u8a0a\u7d44\u5408"}
//		//steven added end 20131004
//	};
	public static void InitUtil() {
		bInit = true;
		try {
			STRING_REPLACEMENT = dao.GetNtvUtilCode();
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static String defaultString(String pString) {
		if(!bInit)
			InitUtil();
		String rtnStr = StringUtils.defaultString(pString, "");
		for (String[] replacement : STRING_REPLACEMENT) {
			rtnStr = StringUtils.replace(rtnStr, replacement[0], replacement[1]);
		}
		
		return rtnStr;
	}
	
	public static void InitUtilRpt() {
		rInit = true;
		try {
			STRING_REPACEMENT_RPT = dao.GetRptUtilCode();
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static String defaultStringRpt(String pString) {
		if(!rInit)
			InitUtilRpt();
		String rtnStr = StringUtils.defaultString(pString, "");
		for (String[] replacement : STRING_REPACEMENT_RPT) {
			rtnStr = StringUtils.replace(rtnStr, replacement[0], replacement[1]);
		}
		
		return rtnStr;
	}
}
