package com.bomwebportal.lts.theclub.service;

import java.io.Reader;
import java.io.StringReader;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.http.client.methods.HttpPost;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bomwebportal.lts.theclub.dto.LtsTheClubRequestFormDTO;
import com.bomwebportal.lts.theclub.dto.LtsTheClubResponseFormDTO;
import com.google.gson.Gson;

public class LtsTheClubPointUtil implements LtsTheClubPointConstant{
	private static final Logger logger = LoggerFactory.getLogger(LtsTheClubPointUtil.class);
	public static void sendRequestToTheClub(){
		
	}
	
	public static String sendPostRequest(String serviceUrl, NameValuePair[] data) throws Exception {
		HttpClient client = new HttpClient();
		if(logger.isDebugEnabled()){
			String msg = "Calling:"+serviceUrl+" Param:";
			for(NameValuePair nvp:data){
				msg = msg + nvp.getName()+":"+nvp.getValue()+" ";
			}
			logger.debug(msg);
		}
//		PostMethod post = new PostMethod("http://uat.theclub.sitelb.hkcsl.net/pd/getMemberBasicInfoWithMaskedID");
		PostMethod post = new PostMethod(serviceUrl);
		post.setQueryString(data);
//		post.setRequestBody(data);
//		HttpPost httppost = new HttpPost(serviceUrl);
		
		String ret = "";
		client.executeMethod(post);
		
		ret = post.getResponseBodyAsString();
		if(logger.isDebugEnabled()){
			logger.debug("Result:"+ret);
		}
		return ret;
	}
	public static NameValuePair[] getMemberBaseInfoWithMaskedIdValuePair(String userName, String password, String source, String lang, String lob
			, String searchType, String memberId, String loginId, String idDocType, String idDocNum
			){
		NameValuePair[] ret = new NameValuePair[] { new NameValuePair("username", userName),
				new NameValuePair("password", password), new NameValuePair("source", source),
				new NameValuePair("lang", lang), new NameValuePair("lob", lob),
				new NameValuePair("searchType", searchType),
				new NameValuePair("memberId", memberId),
				new NameValuePair("loginId", loginId),
				new NameValuePair("idDocType", idDocType),
				new NameValuePair("idDocNum", idDocNum) };
		return ret;
	}

	public static NameValuePair[] doInstantEarnPointValuePair(String userName, String password, String source, String lang, String lob
			, String memberId, String packageCode, String orderNo, String point, String channel
			){
		NameValuePair[] ret = new NameValuePair[] { new NameValuePair("username", userName),
				new NameValuePair("password", password), new NameValuePair("source", source),
				new NameValuePair("lang", lang), new NameValuePair("lob", lob),
				new NameValuePair("memberId", memberId),
				new NameValuePair("packageCode", packageCode),
				new NameValuePair("orderNo", orderNo),
				new NameValuePair("point", point),
				new NameValuePair("channel", channel) };
		return ret;
	}
	
	public static NameValuePair[] doInstantReversePointValuePair(String userName, String password, String source, String lang, String lob
			, String memberId, String transId, String orderNo, Integer point, String channel
			){
		NameValuePair[] ret = new NameValuePair[] { new NameValuePair("username", userName),
				new NameValuePair("password", password), new NameValuePair("source", source),
				new NameValuePair("lang", lang), new NameValuePair("lob", lob),
				new NameValuePair("memberId", memberId),
				new NameValuePair("transId", transId),
				new NameValuePair("orderNo", orderNo),
				new NameValuePair("point", point==null?"":point.toString()),
				new NameValuePair("channel", channel) };
		return ret;
	}
	
	public static NameValuePair[] convertToNameValue(LtsTheClubRequestFormDTO form, String methodName) {
		NameValuePair[] ret = null;
		if (GET_MEMBER_BASIC_INFO_WITH_MASKED_ID.equals(methodName)) {
			ret = getMemberBaseInfoWithMaskedIdValuePair(form.getUsername(), form.getPassword(), form.getSource(), form.getLang(), form.getLob()
					, form.getMemberId(), form.getSearchType(), form.getLoginId(), form.getIdDocType(), form.getIdDocNum());
		} else if (DO_INSTANT_EARN_POINT.equals(methodName)) {
			ret = doInstantEarnPointValuePair(form.getUsername(), form.getPassword(), form.getSource(), form.getLang(), form.getLob()
					, form.getMemberId(), form.getPackageCode(), form.getOrderNo(), form.getPoint() == null ? null : form.getPoint().toString(), form.getChannel());
		} else if (DO_INSTANT_EARN_REVERSE_POINT.equals(methodName)) {
			ret = doInstantEarnPointValuePair(form.getUsername(), form.getPassword(), form.getSource(), form.getLang(), form.getLob()
					, form.getMemberId(), form.getTransId(), form.getOrderNo(), form.getPoint() == null ? null : form.getPoint().toString(), form.getChannel());
		}

		return ret;
	}
	
	public static LtsTheClubResponseFormDTO jsonToResponse(String responseStr){
		Gson gson = new Gson();
		Reader reader = new StringReader(responseStr);
		LtsTheClubResponseFormDTO ret = gson.fromJson(reader, LtsTheClubResponseFormDTO.class);
		return ret;
	}
	
	public static String responseToJson(LtsTheClubResponseFormDTO responseDto){
		Gson gson = new Gson();
		String ret = gson.toJson(responseDto);
		return ret;
	}
}
