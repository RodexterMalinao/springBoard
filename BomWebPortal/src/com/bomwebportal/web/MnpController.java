package com.bomwebportal.web;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
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
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

import com.bomwebportal.bean.LookupTableBean;
import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.dto.CustomerProfileDTO;
import com.bomwebportal.dto.MnpDTO;
import com.bomwebportal.dto.OrderDTO;
import com.bomwebportal.dto.VasMrtSelectionDTO;
import com.bomwebportal.mob.ccs.dto.CodeLkupDTO;
import com.bomwebportal.mob.ccs.util.BeanUtilsHelper;
import com.bomwebportal.mob.ccs.util.MobCcsSessionUtil;
import com.bomwebportal.mob.ds.service.MobDsMrtManagementService;
import com.bomwebportal.service.MnpService;
import com.bomwebportal.service.OrderService;
import com.bomwebportal.util.BomWebPortalConstant;
import com.bomwebportal.util.Utility;

public class MnpController extends SimpleFormController {

	protected final Log logger = LogFactory.getLog(getClass());

	private MnpService service;
	private OrderService orderService;
	private MobDsMrtManagementService mobDsMrtManagementService;
	
	public MnpService getService() {
		return service;
	}

	public void setService(MnpService service) {
		this.service = service;
	}

	public OrderService getOrderService() {
		return orderService;
	}

	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}
	
	public MnpController() {
	}

	public MobDsMrtManagementService getMobDsMrtManagementService() {
		return mobDsMrtManagementService;
	}

	public void setMobDsMrtManagementService(MobDsMrtManagementService mobDsMrtManagementService) {
		this.mobDsMrtManagementService = mobDsMrtManagementService;
	}

	public Object formBackingObject(HttpServletRequest request)
			throws ServletException {
		
		MnpDTO mnp = (MnpDTO) request.getSession().getAttribute("MNP");
		BomSalesUserDTO sessionUserDTO = (BomSalesUserDTO) request.getSession().getAttribute("bomsalesuser");
		String mrtSelection = (String) request.getSession().getAttribute("mrtSelectionSession");	
		VasMrtSelectionDTO vasMrtSelectionDTO = (VasMrtSelectionDTO)request.getSession().getAttribute("vasMrtSelectionSession");
		
		CustomerProfileDTO sessionCustomer = (CustomerProfileDTO) request
				.getSession().getAttribute("customer");
		
		
		if (mnp == null) {
			mnp = new MnpDTO();
			mnp.setNumType(sessionCustomer.getNumType());
			
			if (mrtSelection!=null && sessionUserDTO.getChannelId() == 1){
				mnp.setMnp("N");
				mnp.setNewMsisdn(mrtSelection);
			}else{
				mnp.setMnp("Y");
			}
			
			if (sessionCustomer != null) {
				//modify by eliot 20110621
				if ("BS".equals(sessionCustomer.getIdDocType()))					
					mnp.setCustName(sessionCustomer.getCompanyName());
				else
					mnp.setCustName(sessionCustomer.getContactName());//edit 20110607 (sessionCustomer.getCustLastName() + " "		+ sessionCustomer.getCustFirstName());
				
				if ("BS".equals(sessionCustomer.getIdDocType()))					
					mnp.setFuthercustName(sessionCustomer.getCompanyName());
				else
					mnp.setFuthercustName(sessionCustomer.getContactName());
				
				mnp.setCustIdDocNum(sessionCustomer.getIdDocNum());
				mnp.setFuthercustIdDocNum(sessionCustomer.getIdDocNum());
		
			}

			
			if (sessionUserDTO != null) {
				mnp.setChannelId(String.valueOf(sessionUserDTO.getChannelId()));
				mnp.setShopCd(sessionUserDTO.getBomShopCd());
				logger.debug("mnp ShopCd = " + mnp.getShopCd());
			}
		} else { //add by herbert 20111018
			if (mnp.getMnp().equals("N")){
				//if (mnp.getActionType()!= null && mnp.getActionType().equals("REFRESH")){
				if (mnp.getMsisdn() != null && !"".equals(mnp.getMsisdn())){
					logger.info("New MRT Checking:" + mnp.getMsisdn() + "by Shop:" + mnp.getShopCd()); 	
					MnpDTO outMnpDto = null;
					if (sessionUserDTO.getChannelId() == 1) {
						outMnpDto = service.validateNewMsisdn(mnp);
					} else {
						outMnpDto = service.validateCnmMsindn(mnp.getNewMsisdn(), mnp.getShopCd());
					}
					mnp.setMsisdnLvl(outMnpDto.getMsisdnLvl());
					mnp.setCnmStatus(outMnpDto.getCnmStatus());
					mnp.setNumType(outMnpDto.getNumType());   //Dennis MIP3
					//add by dennis 20150127
					/*if (sessionUserDTO != null && sessionUserDTO.getChannelId() != 10 && sessionUserDTO.getChannelId() != 11) {
						logger.debug("set OriginalNewMsisdn = " + mnp.getMsisdn());
						mnp.setOriginalNewMsisdn(mnp.getMsisdn());
					}*/

					if (sessionUserDTO != null && (sessionUserDTO.getChannelId() == 10 || sessionUserDTO.getChannelId() == 11)) {
						if (StringUtils.isNotBlank(mnp.getReserveId())) {
							mnp.setIsReserveMrt("Y");
						}
					} else {
						if(mnp.getCnmStatus() == 18) {
							String reserveId = service.checkReserveInfo(mnp.getMsisdn(), mnp.getShopCd()).get(1);
							mnp.setReserveId(reserveId);
						} else {
							mnp.setReserveId("");
						}
					}
					logger.info(mnp.getCnmStatus());
				}
			}/*else{
				mnp.setOriginalNewMsisdn(null); //add by dennis
			}*/
		}
		String orderId= (String)request.getSession().getAttribute("orderIdSession");
		mnp.setOldMsisdn(null);
		if (orderId != null) {
			OrderDTO originalOrderDto = (OrderDTO) request.getSession().getAttribute("orderDtoOriginal");
			if (originalOrderDto != null) {
				mnp.setOldMsisdn(originalOrderDto.getMsisdn());
			}
		}
		
		mnp.setChinaMobile(vasMrtSelectionDTO != null);
		
		mnp.setValue("customer", sessionCustomer); //Dennis MIP3
		mnp.setValue("appDate", (String) request.getSession().getAttribute("appDate"));
		return mnp;
	}
	
	

	public ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws ServletException {
		
		BomSalesUserDTO sessionUserDTO = (BomSalesUserDTO) request.getSession().getAttribute("bomsalesuser");		
		
		CustomerProfileDTO sessionCustomer = (CustomerProfileDTO) request
				.getSession().getAttribute("customer");
		
		String nextView = (String) request.getAttribute("nextView");
		logger.info("nextView: " + nextView);

		String currentView = (String) request.getParameter("currentView");
		logger.info("currentView: " + currentView);

		MnpDTO mnpDTO = (MnpDTO) command;
		logger.info("(MnpDTO)command msisdn :" + mnpDTO.getMsisdn());

		String actionType = mnpDTO.getActionType();
		logger.info("mnpDTO.getActionType:"+mnpDTO.getActionType());
		String viewStatus = "";
		if ("REFRESH".equalsIgnoreCase(actionType))
		{
			//if (!"Y".equals(mnpDTO.getMnp())){
				logger.info("New MRT Checking:" + mnpDTO.getMsisdn() + "by Shop:" + mnpDTO.getShopCd());
				MnpDTO outMnpDto = null;
				if (sessionUserDTO.getChannelId() == 1) {
					outMnpDto = service.validateNewMsisdn(mnpDTO);
				} else {
					outMnpDto = service.validateCnmMsindn(mnpDTO.getNewMsisdn(), mnpDTO.getShopCd());
				}
				if (outMnpDto != null) {

					mnpDTO.setMsisdnLvl(outMnpDto.getMsisdnLvl());
					mnpDTO.setCnmStatus(outMnpDto.getCnmStatus());
					
					if (sessionUserDTO.getChannelId() != 10 && sessionUserDTO.getChannelId() != 11) {
						if(mnpDTO.getCnmStatus() == 18) {
							String reserveId = service.checkReserveInfo(mnpDTO.getMsisdn(), mnpDTO.getShopCd()).get(1);
							mnpDTO.setReserveId(reserveId);
						} else {
							mnpDTO.setReserveId("");
						}
					}
					logger.info(mnpDTO.getCnmStatus());
				}
			//}
			viewStatus = "mnp.html";
		}
		else if ("SUBMIT".equalsIgnoreCase(actionType))
		{
			if ("Y".equals(mnpDTO.getMnp())) {
				mnpDTO.setMnpType("MNP");
				mnpDTO.setMsisdn(mnpDTO.getMnpMsisdn());
				logger.info("DNO: " + mnpDTO.getDno());
				logger.debug("mnpDTO.getCutoverDateStr()==>1:" + mnpDTO.getCutoverDateStr());
				
				if (mnpDTO.getCutoverDateStr().length()!=0){
					logger.debug("mnpDTO.getCutoverDate()==>2:" + mnpDTO.getCutoverDate());
					mnpDTO.setCutoverDate(Utility.string2Date(mnpDTO.getCutoverDateStr()));
				}else{
					mnpDTO.setCutoverDate(null);
				}

				mnpDTO.setNewMsisdn("");
				mnpDTO.setServiceReqDateStr("");
				mnpDTO.setServiceReqDate(null);//add 20110607
				mnpDTO.setMsisdnLvl("");
				mnpDTO.setReserveId("");
				mnpDTO.setCnmStatus(0);
				
				if("Prepaid Sim".equalsIgnoreCase(mnpDTO.getCustName())){
					if (mnpDTO.isPrePaidSimDocWithCert()){
						mnpDTO.setPrePaidSimDocInd("Y");
					}
					if (mnpDTO.isPrePaidSimDocWithoutCert()){
						mnpDTO.setPrePaidSimDocInd("N");
					}
				}else{
					mnpDTO.setPrePaidSimDocInd("");
					mnpDTO.setPrePaidSimDocWithoutCert(false);
					mnpDTO.setPrePaidSimDocWithCert(false);
					
				}
				mnpDTO.setFutherMnp(false);
				mnpDTO.setFutherCutoverDate(null);
				mnpDTO.setFutherCutoverDateStr("");
				mnpDTO.setFutherMnpNumber("");
				
				//add by nancy
				mnpDTO.setFutherMnpTicketNum("");
				mnpDTO.setFutherCutoverTime(null);
				mnpDTO.setFuthercustName("");
				mnpDTO.setFuthercustNameChi("");
				mnpDTO.setFuthercustIdDocNum("");
				if (mnpDTO.getOrigActDateStr().length()!=0){
					mnpDTO.setOrigActDate(Utility.string2Date(mnpDTO.getOrigActDateStr()));
				}else{
					mnpDTO.setOrigActDate(null);
				}
	
				//add by dennis
				
				if (StringUtils.isNotEmpty(mnpDTO.getOriginalNewMsisdn())){
					logger.info("release number: "+ mnpDTO.getOriginalNewMsisdn());			
					service.reserveMsisdn(mnpDTO.getOriginalNewMsisdn(), BomWebPortalConstant.RELEASE_NUMBER, sessionUserDTO.getBomShopCd());
					mnpDTO.setOriginalNewMsisdn(null);
				}
				
				
			} else {
				mnpDTO.setMnpType("New Number");
				mnpDTO.setMsisdn(mnpDTO.getNewMsisdn());

				logger.debug("mnpDTO.getServiceReqDatetr()==>3:" + mnpDTO.getServiceReqDateStr());
				if (mnpDTO.getServiceReqDateStr().length()!=0){
					logger.debug("mnpDTO.getServiceReqDate()==>4:" + mnpDTO.getServiceReqDate());
					mnpDTO.setServiceReqDate(Utility.string2Date(mnpDTO.getServiceReqDateStr()));
				}else{
					mnpDTO.setServiceReqDate(null);
				}
				
				
				mnpDTO.setFutherCutoverDate(Utility.string2Date(mnpDTO.getFutherCutoverDateStr()));//add by wilson 20120723
				
				if("Prepaid Sim".equalsIgnoreCase(mnpDTO.getFuthercustName())){
					if (mnpDTO.isPrePaidSimDocWithCert()){
						mnpDTO.setPrePaidSimDocInd("Y");
					}
					if (mnpDTO.isPrePaidSimDocWithoutCert()){
						mnpDTO.setPrePaidSimDocInd("N");
					}
				}else{
					mnpDTO.setPrePaidSimDocInd("");
					mnpDTO.setPrePaidSimDocWithoutCert(false);
					mnpDTO.setPrePaidSimDocWithCert(false);
					
				}
	
				mnpDTO.setMnpTicketNum("");
				mnpDTO.setCutoverDateStr("");
				mnpDTO.setCutoverDate(null);
				mnpDTO.setCutoverTime("");
				mnpDTO.setCustName("");
				mnpDTO.setCustIdDocNum("");
				mnpDTO.setPrePaidSimDocInd("");
				mnpDTO.setPrePaidSimDocWithoutCert(false);
				mnpDTO.setPrePaidSimDocWithCert(false);
				mnpDTO.setMnpMsisdn("");
				mnpDTO.setDno("");
	
				logger.info("mnpDTO.getServiceReqDate(): "+ mnpDTO.getServiceReqDate());
				
				logger.info("Original number: "+ mnpDTO.getOriginalNewMsisdn());
				
				//add by dennis
				if (sessionUserDTO.getChannelId() == 1) {
					if (StringUtils.isEmpty(mnpDTO.getOriginalNewMsisdn())){		
						logger.info("reserve number: "+ mnpDTO.getMsisdn());
						service.reserveMsisdn(mnpDTO.getMsisdn(), BomWebPortalConstant.RESERVE_NUMBER, sessionUserDTO.getBomShopCd());
						mnpDTO.setOriginalNewMsisdn(mnpDTO.getMsisdn());
					}else{
						if (!mnpDTO.getOriginalNewMsisdn().equals(mnpDTO.getMsisdn())){		
							logger.info("release number: "+ mnpDTO.getOriginalNewMsisdn());						
							service.reserveMsisdn(mnpDTO.getOriginalNewMsisdn(), BomWebPortalConstant.RELEASE_NUMBER, sessionUserDTO.getBomShopCd());
							logger.info("reserve number: "+ mnpDTO.getMsisdn());
							service.reserveMsisdn(mnpDTO.getMsisdn(), BomWebPortalConstant.RESERVE_NUMBER, sessionUserDTO.getBomShopCd());
							mnpDTO.setOriginalNewMsisdn(mnpDTO.getMsisdn());
						}
					}
				}
				
			}
			
			mnpDTO.setRno(Utility.getRno(sessionCustomer.getSimType()));
			
			
			viewStatus = nextView;
		}
		
		//check for data amendment in CustomerPrfileDTO and order contains OCID
		OrderDTO sessionOrderDTO = (OrderDTO) MobCcsSessionUtil.getSession(request, "orderDTO");
		if (sessionOrderDTO == null) {
			sessionOrderDTO = (OrderDTO) request.getSession().getAttribute("OrderDto");
		}
		
		if (sessionOrderDTO != null 
				//&& StringUtils.isNotBlank(sessionOrderDTO.getOcid()) 
				&& StringUtils.isNotBlank(sessionOrderDTO.getOrderId())) {
			MnpDTO originalMnpDTO = orderService.getMnp(sessionOrderDTO.getOrderId());
			
			try {
				final String[] toCheckProperties = new String[] 
						{"mnp", "mnpMsisdn", "newMsisdn", "cutoverDateStr", "cutoverTime", "serviceReqDateStr", 
						"mnpTicketNum", "custIdDocNum", "custName", "custNameChi",  
						 "futherMnpNumber", "futherCutoverDateStr", "futherMnpTicketNum", "futherCutoverTime", 
						 "futhercustName", "futhercustNameChi", "futhercustIdDocNum" };
				boolean same = BeanUtilsHelper.compareSpecificProperties(originalMnpDTO, mnpDTO, toCheckProperties);
				if (!same) {
					mnpDTO.setAmend(true);
					//System.out.println("Set OA03/OA04");
				} else {
					mnpDTO.setAmend(false);
				}
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}
		
		// MIP - ACQ w/ Retention development
		//	check wether the mrt is in white list
		String mnpMsisdn = mnpDTO.getMnpMsisdn();
		String newMsisdn = mnpDTO.getNewMsisdn();
		logger.info("mnpMsisdn: " + mnpMsisdn);
		logger.info("newMsisdn: " + newMsisdn);
		Integer checkIsWhiteList = service.checkIsWhiteList(mnpMsisdn);

		checkIsWhiteList = checkIsWhiteList + service.checkIsWhiteList(newMsisdn);
		
		if (checkIsWhiteList != null && checkIsWhiteList > 0){
			sessionCustomer.setCheckIsWhiteList(true);
		} else {
			sessionCustomer.setCheckIsWhiteList(false);
		}
		
		request.getSession().setAttribute("customer", sessionCustomer);
		
		logger.info("checkIsWhiteList: " + checkIsWhiteList);

		request.getSession().removeAttribute("mrtSelectionSession");

		request.getSession().setAttribute("MNP", mnpDTO);
		//return new ModelAndView(new RedirectView(viewStatus));
		
		/* test uid*/
		String attrUid=(String)request.getParameter("sbuid");
		ModelAndView modelAndView =  new ModelAndView(new RedirectView(viewStatus));
		modelAndView.addObject("sbuid", attrUid);
		/* test uid*/
		return modelAndView;

	}

	protected Map<String, Object> referenceData(HttpServletRequest request)
			throws Exception {
		logger.info("ReferenceData called");
		BomSalesUserDTO sessionUserDTO = (BomSalesUserDTO) request.getSession().getAttribute("bomsalesuser");		
		CustomerProfileDTO sessionCustomer = (CustomerProfileDTO) request
				.getSession().getAttribute("customer");
		
		Map<String, Object> referenceData = new HashMap<String, Object>();
		if (sessionUserDTO.getChannelId() == 10 || sessionUserDTO.getChannelId()==11) {
			try{
				String staffId = sessionUserDTO.getUsername();
				String channelCd = sessionUserDTO.getChannelCd();
				OrderDTO originalOrder = (OrderDTO) request.getSession().getAttribute("orderDtoOriginal");
				Date appDate = new Date();
				if (originalOrder != null) {
					staffId = originalOrder.getSalesCd();
					appDate = originalOrder.getAppInDate();
					channelCd = mobDsMrtManagementService.getSalesChannelCd(staffId, appDate);
				}
				List<String[]> numList = service.getMrtNum(staffId, Arrays.asList(new String[] {"N1", "N2"}), appDate, sessionCustomer.getNumType());
				if (numList == null) {
					numList = new ArrayList<String[]>();
				}
				String oldMsisdn = (String)request.getSession().getAttribute("msisdn");
				if (oldMsisdn != null) {
					String[] oldMrt = service.getMrtNum(oldMsisdn, channelCd, sessionCustomer.getNumType()); //Dennis MIP3
					if (oldMrt != null)
						numList.add(oldMrt);
				}
				referenceData.put("numList", numList);
				
			}catch(Exception e){
				logger.error(e);
				e.printStackTrace();
		    }
		}	
		
		//csl enable ind
		List<CodeLkupDTO> codeIds= LookupTableBean.getInstance().getCodeLkupList().get("ENABLE_CSL");
		String enableCsl="N";
	
		if (!CollectionUtils.isEmpty( codeIds)) {
			for (CodeLkupDTO dto : codeIds) {
				if ("Y".equals(dto.getCodeId())) {
					enableCsl = "Y";
					break;
				}
			}
		}
		logger.info("enableCsl:"+enableCsl);
		referenceData.put("enableCsl", enableCsl);
		//cls dnoString
		List<CodeLkupDTO> clsDnoList  = (List<CodeLkupDTO>)LookupTableBean.getInstance().getCodeLkupList().get("CSL_DNO");
		String cslDnoString="";
		if (CollectionUtils.isNotEmpty(clsDnoList)){
			for(CodeLkupDTO a: clsDnoList){
				cslDnoString+=a.getCodeId() +",";
			}
			logger.info("cslDnoString:"+cslDnoString);
		}
		referenceData.put("cslDnoString", cslDnoString);
		
		referenceData.put("orderDto", (OrderDTO) MobCcsSessionUtil.getSession(request, "orderDTO"));
		
		return referenceData;
	}}
