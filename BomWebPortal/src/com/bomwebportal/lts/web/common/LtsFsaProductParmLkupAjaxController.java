package com.bomwebportal.lts.web.common;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.bomwebportal.dao.CodeLkupDAO;
import com.bomwebportal.dto.LookupItemDTO;
import com.bomwebportal.lts.service.bom.ImsServiceProfileAccessService;
import com.bomwebportal.service.CodeLkupCacheService;

public class LtsFsaProductParmLkupAjaxController implements Controller {
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	private ImsServiceProfileAccessService imsServiceProfileAccessService;
	private CodeLkupCacheService eyeFsaParmMsgLkupCacheService;
	
	public ImsServiceProfileAccessService getImsServiceProfileAccessService() {
		return imsServiceProfileAccessService;
	}

	public void setImsServiceProfileAccessService(
			ImsServiceProfileAccessService imsServiceProfileAccessService) {
		this.imsServiceProfileAccessService = imsServiceProfileAccessService;
	}

	public CodeLkupCacheService getEyeFsaParmMsgLkupCacheService() {
		return eyeFsaParmMsgLkupCacheService;
	}

	public void setEyeFsaParmMsgLkupCacheService(
			CodeLkupCacheService eyeFsaParmMsgLkupCacheService) {
		this.eyeFsaParmMsgLkupCacheService = eyeFsaParmMsgLkupCacheService;
	}

	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String fsa = request.getParameter("fsa");
		String isFsaParmExist = null;
		StringBuilder returnMsg = new StringBuilder();
		try {
			CodeLkupDAO codeLkupDao = eyeFsaParmMsgLkupCacheService.getCodeLkupDAO();
			LookupItemDTO[] lookupItems = codeLkupDao.getCodeLkup();
			if (ArrayUtils.isNotEmpty(lookupItems)) {
				for (LookupItemDTO lookupItem : lookupItems) {
					isFsaParmExist = this.imsServiceProfileAccessService.checkProductParmByFsa(fsa, lookupItem.getItemKey());
					if (StringUtils.equalsIgnoreCase("Y", isFsaParmExist)) {
						if (returnMsg.length() != 0) {
							returnMsg.append("\n\n");
						}
						returnMsg.append(lookupItem.getItemValue());
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		JSONArray jsonArray = new JSONArray();
		jsonArray.add(
				"{\"Message\":\""	+ (returnMsg.length() == 0 ? "NULL" : returnMsg.toString()) + "\"}");
		
		return new ModelAndView("ajax_", "jsonArray",	jsonArray);
	}

}
