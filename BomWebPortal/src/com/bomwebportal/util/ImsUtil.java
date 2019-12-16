package com.bomwebportal.util;

import com.bomwebportal.dto.ClubHktCheckIdDTO;



public class ImsUtil {
	
	private static final String CS_SUCESS = "RC_SUCC";
	private static final String CS_ALREADY_REG = "RC_CUS_ALDY_REG";
	private static final String CS_ALREADY_LOGIN_REG = "RC_LOGIN_ID_EXIST";	
	private static final String CS_LESS_THAN_6_DIGITS = "RC_ARQ_IVDOCNUM6DIG";

	private static final String CS_NOT_REG = "RC_NO_CUSTOMER";
	private static final String CS_EXIST_CUST = "RC_CUST_EXIST";
	private static final String CS_IN_USE = "RC_IN_USE";
	
	public static String[] splitString(String pString) {
		
		if(pString!=null){
			String[] strArray = new String[2];
			int newlineInx = pString.indexOf((char)10+"");
			int newlineInx2 = pString.indexOf("<br/>");
			
			if(newlineInx>0){
				strArray[0] = pString.substring(0, newlineInx);
				strArray[1] = pString.substring(newlineInx+1);
			}
			else if (newlineInx2>0){
				strArray[0] = pString.substring(0, newlineInx2);
				strArray[1] = pString.substring(newlineInx2+5);
			}
			else {
				strArray[0] = pString;
				strArray[1] = "";
			}
			
			return strArray;
		}
		else return null;
	}
	
	
	public static String GetHTMLString(String pString)
	{
		if(pString != null) 
			return pString.replaceAll(((char)10)+"", "<br>");
		else
			return null;
	}
	
	public static String getNextPage(String currentPage){
		
		
		return null;
	}
	

	
	public static String[] convertAPIReturnResult(ClubHktCheckIdDTO checkDto){

		String[] result = new String[9];
		
		String hktId = checkDto!=null?checkDto.iLi4MyHkt:null;
		String ClubId = checkDto!=null?checkDto.iLi4Club:null;

		if(hktId== null||hktId.isEmpty()){
			hktId= null;
		}
		if(ClubId == null || ClubId.isEmpty()){
			ClubId = null;
		}
		String retrieveLoginId = null;
		String retrieveClubLoginId = null;
		
		String reserveLoginId = null;
		String reserveClubLoginId = null;
		
		String oCareStatus = null;		
		String oCareVisit = null;
		String oCareEnable = null;
		String oCareBip = null;
		
		if(checkDto!=null && CS_SUCESS.equals(checkDto.reply)){
			
			if(hktId == null && ClubId == null){
		
				if(CS_ALREADY_REG.equals(checkDto.oIdStatus)){
					retrieveLoginId = checkDto.oCorrMyHktLi;
				}else if(CS_NOT_REG.equals(checkDto.oIdStatus)||CS_EXIST_CUST.equals(checkDto.oIdStatus)){
					retrieveLoginId = "not exist";
				}else{
					retrieveLoginId = "<APIFailed>";
				}
		
				if(checkDto.oCorrClubLi==null||checkDto.oCorrClubLi.isEmpty()){
					retrieveClubLoginId = "not exist";
				}else if(checkDto.oCorrClubLi!=null&&!checkDto.oCorrClubLi.isEmpty()){
					retrieveClubLoginId = checkDto.oCorrClubLi;
				}else{
					retrieveClubLoginId = "<APIFailed>";
				}
		
				reserveLoginId = null;
				reserveClubLoginId = null;
		
			}else if(hktId != null && ClubId != null){
				if(CS_IN_USE.equals(checkDto.oMyHktLiStatus)){
					reserveLoginId = "fail";
				}else{
					reserveLoginId = "success";
				}
				if(CS_IN_USE.equals(checkDto.oClubLiStatus)){
					reserveClubLoginId = "fail";
				}else{
					reserveClubLoginId = "success";
				}
				retrieveLoginId = null;
				retrieveClubLoginId = null;
			}else if(hktId != null){
				if(CS_IN_USE.equals(checkDto.oMyHktLiStatus)){
					reserveLoginId = "fail";
				}else{
					reserveLoginId = "success";
				}
				reserveClubLoginId = null;
				retrieveLoginId = null;
				retrieveClubLoginId = null;
			}else if(ClubId != null){
				if(CS_IN_USE.equals(checkDto.oClubLiStatus)){
					reserveClubLoginId = "fail";
				}else{
					reserveClubLoginId = "success";
				}
				reserveLoginId = null;
				retrieveLoginId = null;
				retrieveClubLoginId = null;
			}

			oCareBip = checkDto.oBiptStatus;
			
			if(CS_NOT_REG.equals(checkDto.oIdStatus)){
				oCareEnable = "N";
				oCareStatus= "";
				oCareVisit= "";
			}else{
				oCareEnable = "Y";
				oCareStatus = checkDto.oCareStatus;
				oCareVisit  = checkDto.oCareVisit;
			}		
			
		}else{
			//if call API fail, let customer register
//			retrieveLoginId = "<APIFailed>";
//			retrieveClubLoginId = "<APIFailed>";
//			reserveLoginId = "fail";
//			reserveClubLoginId = "fail";
			retrieveLoginId = "not exist";
			retrieveClubLoginId = "not exist";
			reserveLoginId = "success";
			reserveClubLoginId = "success";
			oCareStatus= "";
			oCareVisit= "";
			oCareEnable = "Y";
			oCareBip = "";
		}
		

		result[0] = retrieveLoginId;
		result[1] = retrieveClubLoginId;
		result[2] = reserveLoginId;
		result[3] = reserveClubLoginId;
		result[4] = checkDto!=null?checkDto.oPhantomAcc:null;
		result[5] = oCareStatus;
		result[6] = oCareVisit;
		result[7] = oCareEnable;
		result[8] = oCareBip;
		
		
		return result;
		
	}
}
