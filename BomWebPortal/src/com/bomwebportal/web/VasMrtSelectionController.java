package com.bomwebportal.web;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

import com.bomwebportal.bean.LookupTableBean;
import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.dto.CnpDTO;
import com.bomwebportal.dto.CustomerProfileDTO;
import com.bomwebportal.dto.MultiSimInfoDTO;
import com.bomwebportal.dto.OrderDTO;
import com.bomwebportal.dto.SuperOrderDTO;
import com.bomwebportal.dto.VasMrtSelectionDTO;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.mob.ccs.dto.CodeLkupDTO;
import com.bomwebportal.mob.ccs.service.MobCcsMrtService;
import com.bomwebportal.mob.ccs.util.MobCcsSessionUtil;
import com.bomwebportal.service.MnpService;
import com.bomwebportal.util.BomWebPortalConstant;
import com.bomwebportal.util.Utility;



public class VasMrtSelectionController extends SimpleFormController {

	protected final Log logger = LogFactory.getLog(getClass());
	
	private MnpService mnpService;
	public MnpService getMnpService() {
		return mnpService;
	}
	public void setMnpService(MnpService mnpService) {
		this.mnpService = mnpService;
	}
	
	private MobCcsMrtService mobCcsMrtService;
	
	public MobCcsMrtService getMobCcsMrtService() {
		return mobCcsMrtService;
	}

	public void setMobCcsMrtService(MobCcsMrtService mobCcsMrtService) {
		this.mobCcsMrtService = mobCcsMrtService;
	}

	protected Map<String, Object> referenceData(HttpServletRequest request) throws Exception {
		Map<String, Object> referenceData = new HashMap<String, Object>();
		
		BomSalesUserDTO user = (BomSalesUserDTO) request.getSession().getAttribute("bomsalesuser");

		CustomerProfileDTO sessionCustomer = null; //Dennis MIP3
		if (user.getChannelId() == 2) {
			sessionCustomer = (CustomerProfileDTO) MobCcsSessionUtil.getSession(request, "customer");
		} else {
			sessionCustomer = (CustomerProfileDTO) request.getSession().getAttribute("customer");
		}
		referenceData.put("customer", sessionCustomer);
		
		if (user !=null){
			String shopCd = user.getBomShopCd();
			referenceData.put("shopCd", shopCd);
		
		int channelId=user.getChannelId();
		if (LookupTableBean.getInstance().getAllowFalloutChannelList()!=null && LookupTableBean.getInstance().getAllowFalloutChannelList().length>0 && Utility.isContainString(LookupTableBean.getInstance().getAllowFalloutChannelList(), user.getChannelCd())){
			channelId =2;
		} else if (user.getChannelId() == 10 || user.getChannelId()==11) {
			channelId = 10;
		}
		referenceData.put("channelId", channelId);
		
		String channelCd = user.getChannelCd();
		referenceData.put("channelCd", channelCd);
		}
		
		List<CodeLkupDTO> cityList  = (List<CodeLkupDTO>)LookupTableBean.getInstance().getCodeLkupList().get("CITY_CODE");
		referenceData.put("cityList", cityList);
		
		return referenceData;
	}
    
	//start and fill the DTO and return it
    public Object formBackingObject(HttpServletRequest request)
			throws ServletException {
    	
    	// get the User's type from session    	
    	// 
    	/*String action = request.getParameter("action");
    	String basketId = request.getParameter("basket");    	
    	*/
    	
    	String vasItemId = request.getParameter("vasitem");
    	String funcId = request.getParameter("funcid");
    	
    	VasMrtSelectionDTO vasMrtSelectionDTO = new VasMrtSelectionDTO();
    	vasMrtSelectionDTO.setFuncId(funcId);
    	
    	BomSalesUserDTO user = (BomSalesUserDTO) request.getSession().getAttribute("bomsalesuser");
    	String appMode = (String) request.getSession().getAttribute("appMode");
    	
    	if ("EX06".equals(funcId)){
    	
    	vasMrtSelectionDTO = (VasMrtSelectionDTO) request.getSession().getAttribute("vasMrtSelectionSession");
    	
    	CustomerProfileDTO sessionCustomer = null; //Dennis MIP3
    	
    	
    	 
		
		if ("mobccs".equals(appMode)) {
			sessionCustomer = (CustomerProfileDTO) MobCcsSessionUtil.getSession(request, "customer");
		} else {
			sessionCustomer = (CustomerProfileDTO) request.getSession().getAttribute("customer");
		}    		
    	
    	
    	if (vasMrtSelectionDTO == null || !vasItemId.equals(vasMrtSelectionDTO.getVasItemId())) {
    		vasMrtSelectionDTO = new VasMrtSelectionDTO();
        	vasMrtSelectionDTO.setFuncId(funcId);
    		
    		vasMrtSelectionDTO.setVasItemId(vasItemId);
    		
    		
    		if (sessionCustomer != null) {
    			vasMrtSelectionDTO.setNumType(sessionCustomer.getNumType());
    		}				
    		
		}  	else {
			if (StringUtils.isNotEmpty(vasMrtSelectionDTO.getMsisdn())){
					if ("mobccs".equals(appMode)) {
						List<String[]> lists = mobCcsMrtService.getOneCardTwoNumberByFullNumber(user.getChannelCd(), vasMrtSelectionDTO.getMsisdn(), vasMrtSelectionDTO.getNumType());

			
						Iterator<String[]> itr = lists.iterator();
					    while (itr.hasNext()){
					    	String[] item = itr.next();
					    	
							vasMrtSelectionDTO.setMsisdn(item[0]);
							vasMrtSelectionDTO.setMsisdnLvl(item[1]);
							vasMrtSelectionDTO.setChannelCd(item[2]);
							vasMrtSelectionDTO.setCityCd(item[3]);
							vasMrtSelectionDTO.setMsisdnStatus(item[4]);
							vasMrtSelectionDTO.setNumType(item[5]);
							vasMrtSelectionDTO.setSrvNumType(item[6]);
					    }
					} else {
						List<CnpDTO> output = mnpService.getNew1C2NMsisdn(vasMrtSelectionDTO.getMsisdn(),
								BomWebPortalConstant.CNM_STATUS_NORMAL, user.getBomShopCd(), "R", null,
								sessionCustomer.getNumType(), 1);
						if (output != null && !output.isEmpty() && output.size() == 1) {
							CnpDTO temp = output.get(0);
							vasMrtSelectionDTO.setMsisdn(temp.getMsisdn());
							vasMrtSelectionDTO.setNumType(temp.getNumType());
							vasMrtSelectionDTO.setCityCd(temp.getCity());
							vasMrtSelectionDTO.setMsisdnStatus(String.valueOf(temp.getStatus()));
							vasMrtSelectionDTO.setSrvNumType(temp.getType());
							vasMrtSelectionDTO.setShopCd(temp.getDepartmentCode().substring(1));
							vasMrtSelectionDTO.setMsisdnLvl(temp.getLevel());

						}
					}
			}
			
		}
    	
    	vasMrtSelectionDTO.setSession(request.getSession());
    	vasMrtSelectionDTO.setChinaInd("Y");
    	}
    	
    	if ("EX07".equals(funcId)){
        	
    	vasMrtSelectionDTO = (VasMrtSelectionDTO) request.getSession().getAttribute("ssMrtSelectionSession");

    	CustomerProfileDTO sessionCustomer = null; //Dennis MIP3
		
		if (user.getChannelId() == 2) {
			sessionCustomer = (CustomerProfileDTO) MobCcsSessionUtil.getSession(request, "customer");
		} else {
			sessionCustomer = (CustomerProfileDTO) request.getSession().getAttribute("customer");
		}    		
    	
    	if (vasMrtSelectionDTO == null || !vasItemId.equals(vasMrtSelectionDTO.getVasItemId())) {
    		vasMrtSelectionDTO = new VasMrtSelectionDTO();
        	vasMrtSelectionDTO.setFuncId(funcId);
    		
    		vasMrtSelectionDTO.setVasItemId(vasItemId);
    			
    		if (sessionCustomer != null) {
    			vasMrtSelectionDTO.setNumType(sessionCustomer.getNumType());
    		}
    		
		} else {
			if (StringUtils.isNotEmpty(vasMrtSelectionDTO.getSecSrvNum())){
				List<CnpDTO> output  = mnpService.getNewSsMsisdn(vasMrtSelectionDTO.getSecSrvNum(), BomWebPortalConstant.CNM_STATUS_NORMAL, user.getBomShopCd(), "R", sessionCustomer.getNumType(), 1);
				if (output != null && !output.isEmpty() && output.size() == 1){
					CnpDTO temp = output.get(0);
					vasMrtSelectionDTO.setMsisdn(temp.getMsisdn());
					vasMrtSelectionDTO.setNumType(temp.getNumType());
					vasMrtSelectionDTO.setCityCd(temp.getCity());
					vasMrtSelectionDTO.setMsisdnStatus(String.valueOf(temp.getStatus()));
					vasMrtSelectionDTO.setSrvNumType(temp.getType());
					vasMrtSelectionDTO.setShopCd(temp.getDepartmentCode().substring(1));										
					vasMrtSelectionDTO.setMsisdnLvl(temp.getLevel());
					
				}
			}
		}
    	
    	if (vasMrtSelectionDTO.getVasItemId() == null) {
    		vasMrtSelectionDTO.setVasItemId(vasItemId);
		}
    	
    	if (vasMrtSelectionDTO.getFuncId() == null) {
    		vasMrtSelectionDTO.setFuncId(funcId);
		}    	
		
    	}
    	
    	vasMrtSelectionDTO.setValue("user", user);
    	vasMrtSelectionDTO.setValue("request", request);
    	
		return vasMrtSelectionDTO;
    	
    }
    
    // select result
	public ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws ServletException, AppRuntimeException {
		
		VasMrtSelectionDTO vasMrtSelectionDTO = (VasMrtSelectionDTO)command;
		
		List<String> goldenType = mobCcsMrtService.getGoldenNumLvL(false);
		
		if (goldenType.contains(vasMrtSelectionDTO.getMsisdnLvl())) {
			vasMrtSelectionDTO.setGoldenInd("Y");
		} else {
			vasMrtSelectionDTO.setGoldenInd("N");
		}
		
		if (!errors.hasErrors()) {		
			if ("EX06".equals(vasMrtSelectionDTO.getFuncId())) {
			request.getSession().setAttribute("vasMrtSelectionSession", vasMrtSelectionDTO);
			} else if ("EX07".equals(vasMrtSelectionDTO.getFuncId())) {
				vasMrtSelectionDTO.setSecSrvNum(vasMrtSelectionDTO.getMsisdn());
				request.getSession().setAttribute("ssMrtSelectionSession", vasMrtSelectionDTO);
				}
		}


		return new ModelAndView(new RedirectView("closepopup.jsp"));
	}
}
