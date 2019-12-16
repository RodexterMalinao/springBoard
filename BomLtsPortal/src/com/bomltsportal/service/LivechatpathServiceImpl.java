package com.bomltsportal.service;

import java.io.UnsupportedEncodingException;

import org.apache.commons.lang.StringUtils;

import com.bomltsportal.dto.BuildingMarkerDTO;

import com.bomwebportal.service.CodeLkupCacheService;

import com.bomwebportal.dto.LookupItemDTO;

public class LivechatpathServiceImpl implements LivechatpathService {

	private String liveChatUrl;
	private CodeLkupCacheService ltsLivechatCSLkupService;
	
	public String generateLiveChatUrl(BuildingMarkerDTO marker, String channel,	
			boolean isChi, String srv_Id, String addressEn,
			String addressCh, String reqPage, String flat, String floor) {
		return generateLiveChatUrl(marker, channel, isChi, srv_Id, addressEn, addressCh, reqPage, flat, floor, false, false);
	}
	
	public String generateLiveChatUrl(BuildingMarkerDTO marker, String channel, boolean isChi, String srv_Id, String addressEn, String addressCh,String reqPage, String flat, String floor, boolean isWip, boolean isSpecialHandleCust){ //,String orderHousingType, String addressWithFlatFloorEn, String addressWithFlatFloorCh, ){
		String generateLiveChatUrl = liveChatUrl;
		
		String custId = "Guest";
		String moduleName = "SALES_LTS_HOME";
		String EntrySite="www.pccw.com";
		
		if("EYE".equals(srv_Id)){
		 EntrySite = "www.pccweye.com";
			
		  if (reqPage.equalsIgnoreCase("addresslookup")||reqPage.equalsIgnoreCase("registration")) {
    		moduleName = "SALES_EYE_HOME";
		}else if (reqPage.equalsIgnoreCase("addressrollout") ) {
    		moduleName = "SALES_EYE_ADDRESS";
        }else  if (reqPage.equalsIgnoreCase("basketselect")) {
    		moduleName = "SALES_EYE_PLAN";
        }else  if (reqPage.equalsIgnoreCase("basketdetail")) {
    		moduleName = "SALES_EYE_PREMIUM";
        }else  if (reqPage.equalsIgnoreCase("vasdetail")) {
    		moduleName = "SALES_EYE_OPTIONAL_VAS";
        }else  if (reqPage.equalsIgnoreCase("applicantinfo")) {
    		moduleName = "SALES_EYE_PERSONAL_INFO";
        }else  if (reqPage.equalsIgnoreCase("summary")) {
    		moduleName = "SALES_EYE_ORDER_SUMM";
        }else  if (reqPage.equalsIgnoreCase("creditpayment")) {
    		moduleName = "SALES_EYE_ORDER_PAYMENT";
        }else  if (reqPage.equalsIgnoreCase("acknowledgement")) {
    		moduleName = "SALES_EYE_COMPLETE";
        }}
		else if("DEL".equals(srv_Id)){
		EntrySite = "www.hkt.com";
		
		if (reqPage.equalsIgnoreCase("addresslookup")||reqPage.equalsIgnoreCase("registration")) {
		    moduleName = "SALES_DEL_HOME";
		 }else if (reqPage.equalsIgnoreCase("addressrollout") ) {
			moduleName = "SALES_DEL_ADDRESS";
        }else  if (reqPage.equalsIgnoreCase("basketselect")) {
			moduleName = "SALES_DEL_PLAN";
		}else  if (reqPage.equalsIgnoreCase("basketdetail")) {
		    moduleName = "SALES_DEL_PREMIUM";
		}else  if (reqPage.equalsIgnoreCase("vasdetail")) {
		    moduleName = "SALES_DEL_OPTIONAL_VAS";
		}else  if (reqPage.equalsIgnoreCase("applicantinfo")) {
		    moduleName = "SALES_DEL_PERSONAL_INFO";
		}else  if (reqPage.equalsIgnoreCase("summary")) {
			moduleName = "SALES_DEL_ORDER_SUMM";
		}else  if (reqPage.equalsIgnoreCase("creditpayment")) {
			moduleName = "SALES_DEL_ORDER_PAYMENT";
		}else  if (reqPage.equalsIgnoreCase("acknowledgement")) {
		    moduleName = "SALES_DEL_COMPLETE";
		}}
		
		String lang = isChi? "zh":"en";
		String chatToken = "NTK";
		String nickName = "Guest";
		
		//String custSegment = "";
		String buildingType = "";
		String buildId = "";
		String address = "";
		String housingType = "";
		
//		if (StringUtils.isEmpty(nickName)){
//			nickName = "Guest";
//		}else 
		
		if(isSpecialHandleCust){
			nickName = "(Refer_BOM_remark)" + nickName;
		}
			
		if(isWip){
			nickName = "(WIP)" + nickName;
		}
		
		
		if(marker!=null){
			
			if(marker.getSfBldgRes().equals("Y") && marker.getSfBldgBus().equals("Y")){
				buildingType = "BOTH";
			}else if(marker.getSfBldgRes().equals("Y")){
				buildingType = "RES";
			}else if(marker.getSfBldgBus().equals("Y")){
				buildingType = "COM";
			}
			
			buildId = marker.getBuildXy();
			
//			if(orderHousingType!=null){
//				housingType = orderHousingType;
//			}else{
				if(marker.getIsPremier().equals("Y")){
					housingType = "Premier";
				}else if(marker.getIsRm().equals("Y")){
					housingType = "RM";
				}else if(marker.getisHos().equals("Y")){
					housingType = "HOS";
				}else if(marker.getisPh().equals("Y")){
					housingType = "PH";
				}else {
					housingType = "Private";
				}
//			}			
			
				
					try {
						if (StringUtils.isNotBlank(flat))
						{
							if (StringUtils.isNotBlank(floor))
								addressEn = "RM" + flat +","+ floor + "/F" + ","+ addressEn ;
							
							else addressEn = "RM" + flat + ","+ addressEn ;
						}
						String tempCountLength = addressEn;
						byte[] Utf8Bytes = tempCountLength.getBytes("UTF-8");
						if(Utf8Bytes.length>1000){
							addressEn = addressEn.substring(0, 999);
						}
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
					
					address = addressEn;
						
						try {
							if (StringUtils.isNotBlank(flat))
							{
								if (StringUtils.isNotBlank(floor))
							        address = address+" | "+addressCh + floor+ "\u6A13"+ flat+"\u5BA4" ;
								
								else address = address+" | "+addressCh + flat+"\u5BA4" ;
							}
							else address = address+" | "+addressCh;
							
							byte[] Utf8Bytes;
							Utf8Bytes = address.getBytes("UTF-8");
							if(Utf8Bytes.length>1000){
								address = address.substring(0, 999);
							}
						} catch (UnsupportedEncodingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
				
			
		}
		
		if(channel!=null){
			if(channel.toLowerCase().indexOf("hkt")>=0){
				EntrySite = "www.hkt.com";
			}else if(channel.toLowerCase().indexOf("pccw")>=0){
				EntrySite = "www.pccw.com";
			}
		}
		
		String c_s = "";
		
		try {
			for(LookupItemDTO tempLkup : ltsLivechatCSLkupService.getCodeLkupDAO().getCodeLkup()){
				c_s = tempLkup.getItemKey();
			}
		} catch (Exception e) {
		}
		
		generateLiveChatUrl += 	"?"+ 
								(custId.length()>0 ? "&c_id="+custId : "")+
								(moduleName.length()>0 ? "&m_n="+moduleName :"")+
								(lang.length()>0 ? "&l="+lang :"")+
								(chatToken.length()>0 ? "&c_t="+chatToken :"")+
								(nickName.length()>0 ? "&n_n="+nickName :"")+
								(EntrySite.length()>0 ? "&e_s="+EntrySite :"")+
								(buildingType.length()>0 ? "&b_t="+buildingType :"")+
								(buildId.length()>0 ? "&b_id="+buildId :"")+
								(address.length()>0 ? "&adr="+address :"")+
								(housingType.length()>0 ? "&h_t="+housingType :"")+
								"&c_s="+ c_s; 
		
		return generateLiveChatUrl;
	}
	

	public String getLiveChatUrl() {
		return liveChatUrl;
	}

	public void setLiveChatUrl(String liveChatUrl) {
		this.liveChatUrl = liveChatUrl;
	}

	public CodeLkupCacheService getLtsLivechatCSLkupService() {
		return ltsLivechatCSLkupService;
	}

	public void setLtsLivechatCSLkupService(
			CodeLkupCacheService ltsLivechatCSLkupService) {
		this.ltsLivechatCSLkupService = ltsLivechatCSLkupService;
	}


}
