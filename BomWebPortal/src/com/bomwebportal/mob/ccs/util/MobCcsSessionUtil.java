package com.bomwebportal.mob.ccs.util;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bomwebportal.util.PdfUtil;



public class MobCcsSessionUtil {
	protected static final Log logger = LogFactory.getLog(PdfUtil.class);
	
	public static Object getSession(HttpServletRequest request,
			String sessionName) {
		
		//System.out.println("[100]MobCcsSessionUtil@getSession called, sessionName:" + sessionName);
		Map<String, Object> sessionCcsOrderHash = (Map<String, Object>) request.getSession().getAttribute("sessionCcsOrderHash");
		if (sessionCcsOrderHash == null) {
			//System.out.println("[110]MobCcsSessionUtil@getSession called, sessionCcsOrderHash is null");
			sessionCcsOrderHash = new HashMap<String, Object>();
			request.getSession().setAttribute("sessionCcsOrderHash",sessionCcsOrderHash);
		}
		//System.out.println("[120]MobCcsSessionUtil@getSession b4 return");
		return ((Map<String, Object>) request.getSession().getAttribute("sessionCcsOrderHash")).get(sessionName);
	}

	public static void setSession(HttpServletRequest request,
			String sessionName, Object saveObject) {
		//System.out.println("[200]MobCcsSessionUtil@setSession called, sessionName:" + sessionName);
		Map<String, Object> sessionCcsOrderHash = (Map<String, Object>) request.getSession().getAttribute("sessionCcsOrderHash");
		
		if (sessionCcsOrderHash == null) {
			//System.out.println("[210]MobCcsSessionUtil@getSession called, sessionCcsOrderHash is null");
			sessionCcsOrderHash = new HashMap<String, Object>();
			sessionCcsOrderHash.put(sessionName, saveObject);
			request.getSession().setAttribute("sessionCcsOrderHash",sessionCcsOrderHash);// save session hash

			//System.out.println("[220]MobCcsSessionUtil@getSession called, after save session sessionCcsOrderHash");
		} else {
			//System.out.println("[250]MobCcsSessionUtil@getSession called, sessionCcsOrderHash exist");
			sessionCcsOrderHash.put(sessionName, saveObject);
			//System.out.println("[260]MobCcsSessionUtil@getSession called, after save session sessionCcsOrderHash");
			request.getSession().setAttribute("sessionCcsOrderHash",sessionCcsOrderHash);
		}
	}

}
