package com.bomwebportal.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.bomwebportal.bean.LookupTableBean;
import com.bomwebportal.dto.BasketDTO;
import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.dto.CustomerProfileDTO;
import com.bomwebportal.mob.ccs.dto.CodeLkupDTO;
import com.bomwebportal.mob.ccs.service.MobCcsMrtService;
import com.bomwebportal.mob.ccs.util.MobCcsSessionUtil;
import com.bomwebportal.service.MnpService;
import com.bomwebportal.util.Utility;

public class MrtSelectionController implements Controller{
    
    protected final Log logger = LogFactory.getLog(getClass());
    private MnpService mnpService;
	public MnpService getMnpService() {
		return mnpService;
	}
	public void setMnpService(MnpService mnpService) {
		this.mnpService = mnpService;
	}

    public ModelAndView handleRequest(HttpServletRequest request,
		HttpServletResponse response) throws ServletException, IOException {
	
	Map referenceData = new HashMap<String, List<String>>();
	String shopCd = null;
	String requestType = request.getParameter("type");
	String numType = request.getParameter("numType");
	BomSalesUserDTO salesUserDto = (BomSalesUserDTO) request.getSession().getAttribute("bomsalesuser");
	if (salesUserDto!=null){
		shopCd = salesUserDto.getBomShopCd();
	}
	CustomerProfileDTO sessionCustomer = null;
	if (salesUserDto.getChannelId() == 2) {
		sessionCustomer = (CustomerProfileDTO) MobCcsSessionUtil.getSession(request, "customer");
	} else {
		sessionCustomer = (CustomerProfileDTO) request.getSession().getAttribute("customer");
	}
	
	if (StringUtils.isBlank(numType)){
		if (sessionCustomer!=null){
			numType = sessionCustomer.getNumType();
		}
	}
	
	if("primary".equalsIgnoreCase(requestType) || "customerprofile".equalsIgnoreCase(requestType) ){		
	    try{
		  List<CodeLkupDTO> primaryMrtGradeList  = (List<CodeLkupDTO>)LookupTableBean.getInstance().getCodeLkupList().get("HW_MSISDN_LEVEL");
		  referenceData.put("mrtGradeList", primaryMrtGradeList);
	
    	    	
	    }catch(Exception e){
		logger.error(e);
	    }
	}else if("secondary".equalsIgnoreCase(requestType)){ 
	    try{
	    	
	    	List<CodeLkupDTO> secondaryMrtGradeList = new ArrayList<CodeLkupDTO>();
	    	CodeLkupDTO out = new CodeLkupDTO();
	    	out.setCodeId("N");
	    	out.setCodeDesc("Non Golden Number");
	    	out.setCodeType("HW_MSISDN_LEVEL");
	    	secondaryMrtGradeList.add(out);

	    	
		referenceData.put("mrtGradeList", secondaryMrtGradeList);

	    }catch(Exception e){
		logger.error(e);
	    }
	}
	
	 List<CodeLkupDTO> mrtNumTypeList  = (List<CodeLkupDTO>)LookupTableBean.getInstance().getCodeLkupList().get("MIP_NUM_TYPE");
	  referenceData.put("mrtNumTypeList", mrtNumTypeList);
	
	referenceData.put("shopCd", shopCd);
	referenceData.put("selectedNumType", numType);
	return new ModelAndView("mrtselection",referenceData);
    }
    
}
