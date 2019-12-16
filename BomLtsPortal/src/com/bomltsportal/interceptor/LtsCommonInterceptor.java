package com.bomltsportal.interceptor;

import java.util.Arrays;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.ListUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.bomltsportal.dto.BuildingMarkerDTO;
import com.bomltsportal.dto.OrderCaptureDTO;
import com.bomltsportal.exception.UserTimeoutException;
import com.bomltsportal.service.LivechatpathService;
import com.bomltsportal.util.BomLtsConstant;
import com.bomltsportal.util.SearchHelper;
import com.bomltsportal.util.SessionHelper;

public class LtsCommonInterceptor extends HandlerInterceptorAdapter{
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	private SearchHelper searchHelper;
//	private String liveChatUrl;
	//private static final String LTS_MARKER = "marker";
	private static final String LTS_CHANNEL = "channel";
	private static final String LIVE_CHAT_URL = "live_chat_url";
	private static final String LIVE_CHAT_SRV_TYPE = "live_chat_srv_type";
	private LivechatpathService livechatpathService;
	
	public boolean preHandle(HttpServletRequest request, 
			HttpServletResponse response, Object handler)throws UserTimeoutException{
				
			OrderCaptureDTO orderCapture = SessionHelper.getOrderCapture(request);
			String reqPage = request.getServletPath().replace(".html", "").replace("/", "");
			
			/* test uid*/
			String[] ignoreTestUid = {"registration", "message", "saleslead", "acknowledgement", "amendment", "captcha", "getimage", "exceptionlog", "report", "connectioncheck"};
			if(!Arrays.asList(ignoreTestUid).contains(reqPage)){
				String parameterUid="";
				String sessionUid ="";
				
				parameterUid=(String)request.getParameter(BomLtsConstant.SESSION_OSUID);
				sessionUid =(String)request.getSession().getAttribute(BomLtsConstant.SESSION_OSUID);
				
				if(StringUtils.isBlank(sessionUid) ){
					throw new UserTimeoutException("Session UID is empty. Request Page: "  + reqPage);
				}else{
					if(StringUtils.isNotBlank(parameterUid)){
						if(!sessionUid.equalsIgnoreCase(parameterUid)){
							throw new UserTimeoutException("Session UID is different from form UID. Request Page: " + reqPage);
						}
					}
			
				}
			}
			
			
			if (orderCapture == null || isAjaxCall(request)|| "getimage".equals(reqPage)) {
				return true;
			}
			
//			String marker_idx = null;
//			BuildingMarkerDTO 
//			OrderImsUI order = (OrderImsUI) request.getSession().getAttribute(BomLtsConstant.LTS_ORDER);
			String channel = request.getParameter("channel");
			String url = null;
			String flat = null;
			String floor = null;
			boolean isChi = false;
			String custName = null;
			String srv_Id = request.getParameter("srv");
//			boolean newMarker = false;

			
			BuildingMarkerDTO marker = null;
			if(!"registration".equals(reqPage) && !"addresslookup".equals(reqPage)){
				if (SessionHelper.getOrderCapture(request).getAddressLookupForm() != null) {
					marker = SessionHelper.getOrderCapture(request).getAddressLookupForm().getBuildingMarker();	
				}
				
			}
			
			if (SessionHelper.getOrderCapture(request).getAddressRolloutForm()!= null) {
				flat = SessionHelper.getOrderCapture(request).getAddressRolloutForm().getFlat();	
				floor = SessionHelper.getOrderCapture(request).getAddressRolloutForm().getFloor();
			}
			
			
			
//			if(FlowControlInterceptor.LANDING_URLS.contains(reqPage)){
//				newMarker = true;
//				marker = null;
//				request.getSession().setAttribute(LTS_MARKER, null);
//			}
			
			Locale locale = RequestContextUtils.getLocale(request);
			if (locale.toString().equalsIgnoreCase("zh_HK")){
				isChi = true;
			}
			
//			if (!"registration".equals(reqPage)&& !"addresslookup".equals(reqPage) && !"addressrollout".equals(reqPage)&&  
//				!"basketselect".equals(reqPage)&& !"basketdetail".equals(reqPage) && !"vasdetail".equals(reqPage)&& !"applicantinfo".equals(reqPage)){
//				
//				if (SessionHelper.getOrderCapture(request).getApplicantInfoForm() != null) {
//					custName = SessionHelper.getOrderCapture(request).getApplicantInfoForm().getGivenName();	
//				}
//				
//			}
			
			if( srv_Id == null)
			{
				srv_Id = SessionHelper.getOrderCapture(request).getServiceTypeInd();
			}
//			if(request.getParameter("marker_idx")!=null){
//				
//				marker_idx = new String(request.getParameter("marker_idx").getBytes("ISO8859-1"), "UTF-8");
//				if(marker==null || !marker_idx.equals(marker.getIndexKey())){
//					marker = searchHelper.getBuildingMarkerDetailByKey(marker_idx);
//					request.getSession().setAttribute(LTS_MARKER, marker);
//					newMarker = true;
//				}
//							
//			}
			
			if(channel!=null){
				request.getSession().setAttribute(LTS_CHANNEL, channel);
			}
			
			channel = (String)request.getSession().getAttribute(LTS_CHANNEL);
			
			if(marker != null){ // && order!=null){
				//url = generateLiveChatUrl(marker, channel, isChi, order.getInstallAddress().getHousing_type(), order.getFullAddrEn(), order.getFullAddrCh(), reqPage);
				url = livechatpathService.generateLiveChatUrl(marker, channel, isChi, srv_Id, marker.getAddressEn(), marker.getAddressCh(), reqPage, flat, floor);
			}else{
				url = livechatpathService.generateLiveChatUrl(marker, channel, isChi, srv_Id, null, null, reqPage, flat, floor);
			}
			
			request.getSession().setAttribute(LIVE_CHAT_URL, url);	
			request.getSession().setAttribute(LIVE_CHAT_SRV_TYPE, srv_Id);
		
			return true;
	}
	
	
	private boolean isAjaxCall(HttpServletRequest request) {

		final String ajaxHeader = "X-Requested-With";
		
		if (request.getHeader(ajaxHeader) != null) {
			return true;
		}

		return false;
	}
	
/*
private String generateLiveChatUrl(BuildingMarkerDTO marker, String channel, boolean isChi, String nickName, String srv_Id, String addressEn, String addressCh,String reqPage, String flat, String floor){ //,String orderHousingType, String addressWithFlatFloorEn, String addressWithFlatFloorCh, ){
		String generateLiveChatUrl = liveChatUrl;
		
		String custId = "Guest";
		String moduleName = "SALES_LTS_HOME";
		
		if("EYE".equals(srv_Id)){
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
		String chatToken = "NTR";
//		String nickName = "Guest";
		String EntrySite = "www.pccweye.com";
		//String custSegment = "";
		String buildingType = "";
		String buildId = "";
		String address = "";
		String housingType = "";
		
		if (nickName == null){
			nickName = "Guest";
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
			}else if(channel.toLowerCase().indexOf("now")>=0){
				EntrySite = "www.now-tv.com";
			}
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
								(housingType.length()>0 ? "&h_t="+housingType :""); 
		
		return generateLiveChatUrl;
	}
*/
	public SearchHelper getSearchHelper() {
		return searchHelper;
	}

	public void setSearchHelper(SearchHelper searchHelper) {
		this.searchHelper = searchHelper;
	}

//	public String getLiveChatUrl() {
//		return liveChatUrl;
//	}
//
//	public void setLiveChatUrl(String liveChatUrl) {
//		this.liveChatUrl = liveChatUrl;
//	}


	public LivechatpathService getLivechatpathService() {
		return livechatpathService;
	}


	public void setLivechatpathService(LivechatpathService livechatpathService) {
		this.livechatpathService = livechatpathService;
	}
	
	

}
