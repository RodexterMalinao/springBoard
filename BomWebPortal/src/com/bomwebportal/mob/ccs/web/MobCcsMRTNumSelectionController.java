package com.bomwebportal.mob.ccs.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.bomwebportal.dto.BasketDTO;
import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.dto.CustomerProfileDTO;
import com.bomwebportal.mob.ccs.service.MobCcsMrtService;
import com.bomwebportal.mob.ccs.util.MobCcsSessionUtil;
import com.bomwebportal.util.Utility;

public class MobCcsMRTNumSelectionController implements Controller{
    
    protected final Log logger = LogFactory.getLog(getClass());
    
    private MobCcsMrtService mobCcsMrtService;
    
    public MobCcsMrtService getMobCcsMrtService() {
        return mobCcsMrtService;
    }

    public void setMobCcsMrtService(MobCcsMrtService mobCcsMrtService) {
        this.mobCcsMrtService = mobCcsMrtService;
    }

    public ModelAndView handleRequest(HttpServletRequest request,
		HttpServletResponse response) throws ServletException, IOException {
	
	Map referenceData = new HashMap<String, List<String>>();
	
	String requestType = request.getParameter("mrtType");
	
	BomSalesUserDTO salesUserDto = (BomSalesUserDTO) request.getSession().getAttribute("bomsalesuser");
	BasketDTO basketDto = (BasketDTO)MobCcsSessionUtil.getSession(request, "basket");
	CustomerProfileDTO sessionCustomer = (CustomerProfileDTO) MobCcsSessionUtil.getSession(request, "customer");
	
	String appDateStr=(String) request.getSession().getAttribute("appDate"); //add 20141103
	Date appDate = StringUtils.isBlank(appDateStr) ? null : Utility.string2Date(appDateStr); //add 20141103
	
	try{
	    mobCcsMrtService.handleExpiredMRT(salesUserDto.getUsername());
	}catch(Exception e){
	    logger.error(e);
	}
		
	if("newMRT".equalsIgnoreCase(requestType)){		
	    try{
		List<String[]> mobNumList = new ArrayList<String[]>();
		List<String[]> mobRandomNumList = new ArrayList<String[]>();
		
		mobRandomNumList = mobCcsMrtService.getRandomPCCW3GNumFromPool(salesUserDto.getChannelCd(),sessionCustomer.getNumType());
		mobNumList = mobCcsMrtService.getPCCW3GMrtNumByStaffId(salesUserDto.getUsername()); 
		
		referenceData.put("mobNumList", mobNumList);
		referenceData.put("mobRandomNumList", mobRandomNumList);
    	    	
	    }catch(Exception e){
		logger.error(e);
	    }
	}else if("unicom".equalsIgnoreCase(requestType)){ 
	    try{
		List<String> mobUnicomCityCdList = new ArrayList<String>();
		
		mobUnicomCityCdList = mobCcsMrtService.getDistinctUNICOM1C2NCityCd(salesUserDto.getChannelCd());
		
		referenceData.put("channelCd", salesUserDto.getChannelCd());
		referenceData.put("mobUnicomCityCdList", mobUnicomCityCdList);
	    }catch(Exception e){
		logger.error(e);
	    }
	}else if("goldenMRT".equalsIgnoreCase(requestType)){
	    try{
		List<String[]> mobGoldenRandomList = new ArrayList<String[]>();
		
		/*mobGoldenRandomList = mobCcsMrtService.getGoldenNumRandsomList("0", 
										basketDto.getContractPeriod(), 
										basketDto.getRecurrentAmt());	*/
		mobGoldenRandomList = mobCcsMrtService.getGoldenNumRandsomList("0", 
				basketDto.getContractPeriod(), 
				basketDto.getRecurrentAmt(),
				basketDto.getGrossPlanFee(),
				appDate,
				sessionCustomer.getBrand());					//add 20141103
		
		
		referenceData.put("mobGoldenRandomList", mobGoldenRandomList);
		referenceData.put("period", basketDto.getContractPeriod());
		referenceData.put("charge", basketDto.getRecurrentAmt());
		referenceData.put("grossPlanFee", basketDto.getGrossPlanFee());  //add 20141103
		referenceData.put("appDate", appDateStr); 
	    }catch(Exception e){
		logger.error(e);
	    }
	}
	
	referenceData.put("mrtType", requestType);
	referenceData.put("staff_id", salesUserDto.getUsername());
	referenceData.put("channelCd", salesUserDto.getChannelCd());
	
	return new ModelAndView("mobccsmrtnumselection",referenceData);
    }
    
}
