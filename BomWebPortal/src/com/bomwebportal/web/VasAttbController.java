package com.bomwebportal.web;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

import com.bomwebportal.bean.LookupTableBean;
import com.bomwebportal.dto.BasketDTO;
import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.dto.ComponentDTO;
import com.bomwebportal.dto.CustomerProfileDTO;
import com.bomwebportal.dto.MnpDTO;
import com.bomwebportal.dto.OrderDTO;
import com.bomwebportal.dto.VasAttbComponentDTO;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.mob.ccs.dto.CodeLkupDTO;
import com.bomwebportal.mob.ccs.util.MobCcsSessionUtil;
import com.bomwebportal.service.QueueRefService;
import com.bomwebportal.service.VasDetailService;
import com.bomwebportal.util.Utility;

public class VasAttbController extends SimpleFormController{

	protected final Log logger = LogFactory.getLog(getClass());
	
    private VasDetailService service;
    private QueueRefService queueRefService;
	
	public VasDetailService getService() {
		return service;
	}

	public void setService(VasDetailService service) {
		this.service = service;
	}

	public QueueRefService getQueueRefService() {
		return queueRefService;
	}

	public void setQueueRefService(QueueRefService queueRefService) {
		this.queueRefService = queueRefService;
	}

	public Object formBackingObject(HttpServletRequest request) throws ServletException{
		logger.info("formBackingObject is called in vasAttbController");
		List<VasAttbComponentDTO> vasAttbCompList = (List<VasAttbComponentDTO>) request.getSession().getAttribute("vasAttbList");
		logger.info("JMETER CHECKPOINT: vasAttbCompList == null?: "+(vasAttbCompList == null));
		List<ComponentDTO> componentList = (List<ComponentDTO>) request.getSession().getAttribute("componentList");
		
		//List<VasAttbComponentDTO> vasAttbCompList = service.getVasAttbComponentList(vasAttbList);
		
		Map<String, List<VasAttbComponentDTO>> vasAttbMap = new LinkedHashMap<String, List<VasAttbComponentDTO>>();
		List<VasAttbComponentDTO> subVasAttbCompList = new ArrayList<VasAttbComponentDTO>();
		String subAttbId = new String();
		for(int i=0; i<vasAttbCompList.size(); i++){
			VasAttbComponentDTO vasAttb = (VasAttbComponentDTO)vasAttbCompList.get(i);
			//System.out.println(vasAttb.getAttbId());
			//System.out.println(vasAttb.getAttbDesc());
			if(i==0){
				subAttbId = vasAttb.getCompAttbId();
				//System.out.println("i: " + i);
				subVasAttbCompList.add(vasAttb);
			}else{
				if(subAttbId.equals(vasAttb.getCompAttbId())){
					//System.out.println("i: " + i);
					subVasAttbCompList.add(vasAttb);					
				}else{
					vasAttbMap.put(subAttbId, subVasAttbCompList);
					subAttbId = vasAttb.getCompAttbId();
					subVasAttbCompList = new ArrayList<VasAttbComponentDTO>();
					//System.out.println("i: " + i);
					subVasAttbCompList.add(vasAttb);
				}
			}			
		}
		if(vasAttbCompList.size()>=1){
			vasAttbMap.put(subAttbId, subVasAttbCompList);
		}
				
		request.setAttribute("vasAttbMap", vasAttbMap);
		request.setAttribute("componentList", componentList);
		return new Object();
	}
	
	
	
	protected Map<String, Object> referenceData(HttpServletRequest request) throws Exception {
		logger.info("ReferenceData called");
		
		BomSalesUserDTO user = (BomSalesUserDTO) request.getSession().getAttribute("bomsalesuser");	
		BasketDTO sessionBasket = (BasketDTO) MobCcsSessionUtil.getSession(request, "basket");
		List<ComponentDTO> componentList = (List<ComponentDTO>) request.getSession().getAttribute("componentList");
		
		String appDate = (String) request.getSession().getAttribute("appDate");
		boolean isWalkInProduct = false;
		CustomerProfileDTO sessionCustomer = null;
		boolean previousWalkInProduct = false;
		
		if (user.getChannelId() == 2) {
			sessionCustomer = (CustomerProfileDTO) MobCcsSessionUtil.getSession(request, "customer");
		} else {
			sessionCustomer = (CustomerProfileDTO) request.getSession().getAttribute("customer");
		}
		
		Map<String, Object> referenceData = new HashMap<String, Object>();
    	referenceData.put("appDateStr", Utility.date2String(Utility.string2Date(appDate), "yyyyMMdd"));
    	
    	if (sessionCustomer != null) {
    		referenceData.put("docType", sessionCustomer.getIdDocType());
    		referenceData.put("docId", sessionCustomer.getIdDocNum());
    	}
    	
    	referenceData.put("hsItemCode", sessionBasket.getHsPosItemCd());
    	referenceData.put("basketBrand", sessionBasket.getMipBrand());
    	
    	int channelId=(user.getChannelId() == 11 ? 10 : user.getChannelId());//get channelId , convert CFM(66), CPA(68) => IBS, OBS(2)
    	
		if (LookupTableBean.getInstance().getAllowFalloutChannelList()!=null && LookupTableBean.getInstance().getAllowFalloutChannelList().length>0 && Utility.isContainString(LookupTableBean.getInstance().getAllowFalloutChannelList(), user.getChannelCd())){
			channelId =2;//convert channel ID
		}
		
		referenceData.put("channelId", channelId);
		
		String orderId = (String) request.getSession().getAttribute("orderIdSession");
		referenceData.put("orderId", orderId);
		if (sessionBasket.getHsPosItemCd()!=null){
			isWalkInProduct = queueRefService.isWalkInByPassProduct(sessionBasket.getHsPosItemCd(), Utility.string2Date(appDate));
		}
			referenceData.put("isWalkInProduct",isWalkInProduct);
		
		
		List<CodeLkupDTO> projectSevenWalkInKey= LookupTableBean.getInstance().getCodeLkupList().get("PRJ_7_WLK");
		if (CollectionUtils.isNotEmpty(projectSevenWalkInKey)){
			referenceData.put("projectSevenWalkInKey",projectSevenWalkInKey.get(0).getCodeId());
		}
		
		List<CodeLkupDTO> projectSevenAttb= LookupTableBean.getInstance().getCodeLkupList().get("PRJ_7_ATTB");
		if (CollectionUtils.isNotEmpty(projectSevenAttb)){
			referenceData.put("projectSevenAttb",projectSevenAttb.get(0).getCodeId());
		}

		MnpDTO mnpDto = (MnpDTO) request.getSession().getAttribute("MNP");
		if (mnpDto != null) {
			referenceData.put("digitalCouponSms", mnpDto.getMsisdn());
		} else {
			referenceData.put("digitalCouponSms", "00000000");
		}
		
		List<CodeLkupDTO> digitalCouponAttb = LookupTableBean.getInstance().getCodeLkupList().get("COUPON_ATTB");
		if (CollectionUtils.isNotEmpty(digitalCouponAttb)) {
			referenceData.put("digitalCouponAttb",digitalCouponAttb.get(0).getCodeId());
		}
		
		if (CollectionUtils.isNotEmpty(componentList)){
			for(ComponentDTO componentDto:componentList){
				if (componentDto.getCompAttbId().equalsIgnoreCase(projectSevenAttb.get(0).getCodeId())){
					if (componentDto.getCompAttbVal().equalsIgnoreCase(projectSevenWalkInKey.get(0).getCodeId())){
						previousWalkInProduct= true;
					}
				}
			}
		}
		
		referenceData.put("previousWalkInProduct", previousWalkInProduct);
		
		return referenceData;
	}
	
	public ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) 
	throws ServletException, AppRuntimeException {
		logger.info("onSubmit is called in VasAttbController");
		Enumeration<String> paramNames = (Enumeration<java.lang.String>) request.getParameterNames();
		
		List<ComponentDTO> componentList = new ArrayList<ComponentDTO>();
		while(paramNames.hasMoreElements()){
			String paraName = paramNames.nextElement();
			logger.info("paraName: " + paraName);
			if(!"sbuid".equals(paraName) && !"sameAsMrt".equals(paraName)){
				ComponentDTO component = new ComponentDTO();
				component.setCompAttbId(paraName);
				component.setCompAttbValue(request.getParameter(paraName));
				componentList.add(component);
			}
		}
		
		request.getSession().setAttribute("componentList", componentList);
		
		
		String appMode = (String)request.getSession().getAttribute("appMode");
		if ("mobccs".equals(appMode)){
			return new ModelAndView(new RedirectView("mobccsmrt.html"));//mobccsmrt.html//mobccspaymentupfront.html
		} else if ("directsales".equals(appMode)){
			String attrUid=(String)request.getParameter("sbuid");
			ModelAndView modelAndView =  new ModelAndView(new RedirectView("mobilesiminfo.html?sbuid="+attrUid));
			return modelAndView;
		} else {
			//return new ModelAndView(new RedirectView("payment.html"));
			
			/* test uid*/
			String attrUid=(String)request.getParameter("sbuid");
			ModelAndView modelAndView =  new ModelAndView(new RedirectView("payment.html?sbuid="+attrUid));
			//modelAndView.addObject("sbuid", attrUid);
			/* test uid*/
			return modelAndView;
		}
		
	}
}
