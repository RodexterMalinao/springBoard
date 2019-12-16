package com.bomwebportal.mob.ccs.web;

import java.lang.reflect.InvocationTargetException;
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
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

import com.bomwebportal.bean.LookupTableBean;
import com.bomwebportal.dto.BasketDTO;
import com.bomwebportal.dto.BasketQuotaDTO;
import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.dto.ComponentDTO;
import com.bomwebportal.dto.CustomerProfileDTO;
import com.bomwebportal.dto.ItemFuncAssgnMobDTO;
import com.bomwebportal.dto.MultiSimInfoDTO;
import com.bomwebportal.dto.MultiSimInfoMemberDTO;
import com.bomwebportal.dto.OrderDTO;
import com.bomwebportal.dto.VasDetailDTO;
import com.bomwebportal.dto.VasMrtSelectionDTO;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.mob.ccs.dto.CodeLkupDTO;
import com.bomwebportal.mob.ccs.dto.MobCcsMrtBaseDTO;
import com.bomwebportal.mob.ccs.dto.ui.DeliveryUI;
import com.bomwebportal.mob.ccs.dto.ui.MRTUI;
import com.bomwebportal.mob.ccs.service.GoldenMrtAdminService;
import com.bomwebportal.mob.ccs.service.MobCcsApprovalLogService;
import com.bomwebportal.mob.ccs.service.MobCcsMrtService;
import com.bomwebportal.mob.ccs.util.BeanUtilsHelper;
import com.bomwebportal.mob.ccs.util.BomWebPortalCcsConstant;
import com.bomwebportal.mob.ccs.util.MobCcsSessionUtil;
import com.bomwebportal.service.ItemFuncMobService;
import com.bomwebportal.service.VasDetailService;
import com.bomwebportal.util.BomWebPortalConstant;
import com.bomwebportal.util.DateUtils;
import com.bomwebportal.util.Utility;

public class MobCcsMRTController extends SimpleFormController {

	protected final Log logger = LogFactory.getLog(getClass());

	private MobCcsMrtService mobCcsMrtService;
	private GoldenMrtAdminService goldenMrtAdminService;
	private MobCcsApprovalLogService mobCcsApprovalLogService;
	private ItemFuncMobService itemFuncMobService;
	
	private String appDate;
	private BasketDTO basketDto;
	private MRTUI sessionMRTUI;
	
	private VasDetailService vasDetailService;
	
	public VasDetailService getVasDetailService() {
		return vasDetailService;
	}

	public void setVasDetailService(VasDetailService vasDetailService) {
		this.vasDetailService = vasDetailService;
	}

	
	final String[] toCheckMnpProperties = new String[] {
		 "mnpMsisdn", "cutOverDateStr"
		, "cutOverTime", "custName", "docNum", "cityCd"
		, "unicomMobMsisdn", "unicomMobGrade"
	};
	
	final String[] toCheckMrtProperties = new String[] {
			"mobMsisdn", "msisdnLvl", "serviceReqDateStr"
			, "cityCd", "unicomMobMsisdn", "unicomMobGrade"
	};
	
	/**
	 * @return the basketDto
	 */
	public BasketDTO getBasketDto() {
		return basketDto;
	}

	/**
	 * @return the appDate
	 */
	public String getAppDate() {
		return appDate;
	}

	public MobCcsMrtService getMobCcsMrtService() {
		return mobCcsMrtService;
	}

	public void setMobCcsMrtService(MobCcsMrtService mobCcsMrtService) {
		this.mobCcsMrtService = mobCcsMrtService;
	}

	public GoldenMrtAdminService getGoldenMrtAdminService() {
		return goldenMrtAdminService;
	}

	public void setGoldenMrtAdminService(
			GoldenMrtAdminService goldenMrtAdminService) {
		this.goldenMrtAdminService = goldenMrtAdminService;
	}

	public MobCcsApprovalLogService getMobCcsApprovalLogService() {
		return mobCcsApprovalLogService;
	}

	public void setMobCcsApprovalLogService(
			MobCcsApprovalLogService mobCcsApprovalLogService) {
		this.mobCcsApprovalLogService = mobCcsApprovalLogService;
	}

	public ItemFuncMobService getItemFuncMobService() {
		return itemFuncMobService;
	}

	public void setItemFuncMobService(ItemFuncMobService itemFuncMobService) {
		this.itemFuncMobService = itemFuncMobService;
	}

	public Object formBackingObject(HttpServletRequest request)
			throws ServletException {
		logger.info("MobCcsMRTController@formBackingObject called");
		sessionMRTUI = (MRTUI) MobCcsSessionUtil.getSession(request, "mrt");
		
		basketDto = (BasketDTO) MobCcsSessionUtil.getSession(request, "basket");
		appDate = (String) request.getSession().getAttribute("appDate");
		CustomerProfileDTO sessionCust = (CustomerProfileDTO) MobCcsSessionUtil
				.getSession(request, "customer");
		
		VasDetailDTO vasDetail = (VasDetailDTO)MobCcsSessionUtil.getSession(request, "vasDetail");
		List<MultiSimInfoDTO> multiSimInfos = (List<MultiSimInfoDTO>) request.getSession().getAttribute("MultiSimInfoList");
		
		BomSalesUserDTO user = (BomSalesUserDTO) request.getSession().getAttribute("bomsalesuser");
		
		if (sessionMRTUI == null) {
			logger.info("MobCcsMRTController@formBackingObject called, , sessionMRTDto is null");
			sessionMRTUI = new MRTUI();
			sessionMRTUI.setSimType(sessionCust.getSimType());
			sessionMRTUI.setNumType(sessionCust.getNumType());
			// sessionMRTUI.getMrtDtoList()[1] = new MobCcsMrtDTO();
			// mrt_ind : "O" mnp or mob, "C" 1C2N, "A" mnp and mob
			//when sim type is 4, only show 1C2N front end (removed)
			sessionMRTUI.setMrtInd("O");
			sessionMRTUI.setChinaInd("N");

			// mnp_ind : "N" new mob, "Y" mnp
			sessionMRTUI.setMnpInd("N");
			sessionMRTUI.setGoldenInd("N");
			sessionMRTUI.setContractPeriod(Integer.parseInt(basketDto.getContractPeriod()));
			sessionMRTUI.setRpRecurChange(Integer.parseInt(basketDto.getRecurrentAmt()));
			
			if (StringUtils.isBlank(basketDto.getGrossPlanFee())) {
				basketDto.setGrossPlanFee("0");
			}
			sessionMRTUI.setGrossPlanFee(Integer.parseInt(basketDto.getGrossPlanFee()));
			
			if (null != sessionCust) {
				sessionMRTUI.setDocNum(sessionCust.getIdDocNum());

				if ("BS".equals(sessionCust.getIdDocType())) {
					sessionMRTUI.setCustName(sessionCust.getCompanyName());
				} else {
					sessionMRTUI.setCustName(sessionCust.getContactName());
				}
			}
			
		} else {
			VasMrtSelectionDTO vasMrtSelectionSession = (VasMrtSelectionDTO)request.getSession().getAttribute("vasMrtSelectionSession");
			
			// check special num by log
			if(mobCcsApprovalLogService.isApproval(sessionMRTUI.getOrderId(),"AU05")){
				sessionMRTUI.setSpecialNumInd(true);
			}else{
				sessionMRTUI.setSpecialNumInd(false);
			}
			
			if ("A".equalsIgnoreCase(sessionMRTUI.getMrtInd()) && vasMrtSelectionSession != null) {
				sessionMRTUI.setMrtInd("O");
				sessionMRTUI.setMnpInd("N");
			}
			sessionMRTUI.setChinaInd("N");
			
			sessionMRTUI.setContractPeriod(Integer.parseInt(basketDto.getContractPeriod()));
			sessionMRTUI.setRpRecurChange(Integer.parseInt(basketDto.getRecurrentAmt()));
			sessionMRTUI.setGrossPlanFee(Integer.parseInt(basketDto.getGrossPlanFee()));
		}
		
		if (appDate != null && !"".equals(appDate))
			sessionMRTUI.setAppDate(Utility.string2Date(appDate));
		
		if (null != sessionCust) {
			if (sessionMRTUI.getDocNum() == null || sessionMRTUI.getDocNum().isEmpty()) {
				sessionMRTUI.setDocNum(sessionCust.getIdDocNum());
			}
			
			if (sessionMRTUI.getCustName() == null || sessionMRTUI.getCustName().isEmpty()) { 
				if ("BS".equals(sessionCust.getIdDocType())) {
					sessionMRTUI.setCustName(sessionCust.getCompanyName());
				} else {
					sessionMRTUI.setCustName(sessionCust.getContactName());
				}
			}
		}
		
		List<String> vasList = vasDetailService.getSubscribedVASList(basketDto.getBasketId(), vasDetail.getVasitem(), sessionCust.getBrand(), sessionCust.getSimType());
		if(multiSimInfos != null && multiSimInfos.size() != 0){
			List<String> msiServiceDateList = new ArrayList<String> ();
			List<String> multiSimDnoList = new ArrayList<String> ();
			List<String> multiSimOpssInd = new ArrayList<String> ();
			List<String> multiSimMemberOrderTypeList = new ArrayList<String> ();
			for(String item : vasList){
				List<ItemFuncAssgnMobDTO> funcList = itemFuncMobService.findItemFuncAssgnMobDTO(item, appDate);
				for(ItemFuncAssgnMobDTO func : funcList) {
					if("EX03".equals(func.getFuncId())) {
						for(MultiSimInfoDTO msi : multiSimInfos){
							if(item.equals(msi.getItemId())){
								for(MultiSimInfoMemberDTO msim : msi.getMembers()){
									if(msim.getMnpCutOverDate() != null)
										msiServiceDateList.add(msim.getMnpCutOverDate());
										multiSimDnoList.add(msim.getActualDno());
										multiSimOpssInd.add(msim.getOpssInd());
										multiSimMemberOrderTypeList.add(msim.getMemberOrderType());
										
								}
							}	
						}
					}
				}
			}
			sessionMRTUI.setMsiServiceDateList(msiServiceDateList);
			sessionMRTUI.setMultiSimDnoList(multiSimDnoList);
			sessionMRTUI.setMultiSimOpssInd(multiSimOpssInd);
			sessionMRTUI.setMultiSimMemberOrderTypeList(multiSimMemberOrderTypeList);
		}
		sessionMRTUI.setSession(request.getSession());
		sessionMRTUI.setValue("customer", sessionCust); //Dennis MIP3
		sessionMRTUI.setValue("bomsalesuser", user); //Dennis MIP3
	
		
		return sessionMRTUI;
	}

	// SimpleFormController
	protected Map referenceData(HttpServletRequest request) throws Exception {
		logger.info("ReferenceData called");
		Map referenceData = new HashMap<String, List<String>>();

		BomSalesUserDTO salesUserDto = (BomSalesUserDTO) request.getSession().getAttribute("bomsalesuser");
		CustomerProfileDTO sessionCust = (CustomerProfileDTO) MobCcsSessionUtil.getSession(request, "customer");

		VasDetailDTO vasDetail = (VasDetailDTO)MobCcsSessionUtil.getSession(request, "vasDetail");
		List<MultiSimInfoDTO> multiSimInfos = (List<MultiSimInfoDTO>) request.getSession().getAttribute("MultiSimInfoList");
		
		
		Date activeSrvReqDateStart = new Date();
		Date activeCutOverDateStart = new Date();
		Date activeSrvReqDateEnd = new Date();
		Date activeCutOverDateEnd = new Date(); //MNP 
		Date activeCutOverDateExtendedEnd = new Date(); //MNP and authorized
		Date activeFurtherCutOverDateEnd = new Date(); //MNP_IND A
		
		DeliveryUI deliveryUI = (DeliveryUI) MobCcsSessionUtil.getSession(request, "delivery");
		
		try {
			mobCcsMrtService.handleExpiredMRT(salesUserDto.getUsername());
		} catch (Exception e) {
			logger.error(e);
		}

		String hottestModelInd = "N";
				
		if (deliveryUI != null && deliveryUI.getDeliveryDate() != null) {
			activeSrvReqDateStart = deliveryUI.getDeliveryDate();
			activeCutOverDateStart = deliveryUI.getDeliveryDate();
			
			activeSrvReqDateStart = DateUtils.dateAfterdays(activeSrvReqDateStart, 1);
			activeCutOverDateStart = DateUtils.dateAfterdays(activeCutOverDateStart, 1);
			
		} else {
			activeSrvReqDateStart = Utility.string2Date(appDate);
			activeCutOverDateStart = Utility.string2Date(appDate);
			
			activeSrvReqDateStart = DateUtils.dateAfterdays(activeSrvReqDateStart, 1);
			activeCutOverDateStart = DateUtils.dateAfterdays(activeCutOverDateStart, 1);
		}
		
		
		
		if ("A".equalsIgnoreCase(sessionMRTUI.getMnpInd())) {
			//DENNIS201606
			if ("Y".equals(basketDto.getHottestModelInd())) {
				hottestModelInd = "Y";
				activeSrvReqDateEnd = DateUtils.dateAfterdays(Utility.string2Date(appDate), BomWebPortalConstant.CCS_NEWMRTMNP_SRD_APP_EXTEND_DAYS);
			}	else {
				activeSrvReqDateEnd = DateUtils.dateAfterdays(Utility.string2Date(appDate), BomWebPortalConstant.CCS_NEWMRTMNP_SRD_APP_EXTEND_DAYS);
			}
		}else{
			//OLD
			if ("Y".equals(basketDto.getHottestModelInd())) {
				hottestModelInd = "Y";
				activeSrvReqDateEnd = DateUtils.dateAfterdays(Utility.string2Date(appDate), 45); //30 to 45  align with mnp_ind A
			} else {
				activeSrvReqDateEnd = DateUtils.dateAfterdays(Utility.string2Date(appDate), 45);
			}
		}
		
		
		/*if ("A".equalsIgnoreCase(sessionMRTUI.getMnpInd())) {
			Date cutOverDateEnd = Utility.string2Date(appDate);
			if (StringUtils.isNotBlank(sessionMRTUI.getServiceReqDateStr())) {
				cutOverDateEnd = Utility.string2Date(sessionMRTUI.getServiceReqDateStr());
			}
			activeFurtherCutOverDateEnd = DateUtils.dateAfterdays(cutOverDateEnd, BomWebPortalConstant.CCS_NEWMRTMNP_CUTOVER_SRD_EXTEND_DAYS);
		} else {
			if ("Y".equals(basketDto.getHottestModelInd())) {
				hottestModelInd = "Y";
				activeCutOverDateEnd = DateUtils.dateAfterdays(Utility.string2Date(appDate), 30);
			} else {
				activeCutOverDateEnd = DateUtils.dateAfterdays(Utility.string2Date(appDate), 90);
			}
			
			//DENNIS201606
			activeCutOverDateEnd = DateUtils.dateAfterdays(Utility.string2Date(appDate), BomWebPortalConstant.CCS_MNP_APP_EXTEND_DAYS);
		}*/
		
		Date cutOverDateEnd = Utility.string2Date(appDate);
		if (StringUtils.isNotBlank(sessionMRTUI.getServiceReqDateStr())) {
			cutOverDateEnd = Utility.string2Date(sessionMRTUI.getServiceReqDateStr());
		}
		activeFurtherCutOverDateEnd = DateUtils.dateAfterdays(cutOverDateEnd, BomWebPortalConstant.CCS_NEWMRTMNP_CUTOVER_SRD_EXTEND_DAYS);
		
		activeCutOverDateEnd = DateUtils.dateAfterdays(Utility.string2Date(appDate), BomWebPortalConstant.CCS_MNP_APP_EXTEND_DAYS);
		
		activeCutOverDateExtendedEnd = DateUtils.dateAfterdays(Utility.string2Date(appDate), BomWebPortalConstant.CCS_MNP_APP_EXTEND_DAYS + BomWebPortalConstant.SM_APPROVAL_MNP_EXTEND_DAYS);
		
		List<BasketQuotaDTO> quotaList = basketDto.getBasketQuotaList();
		String quota = "N";
		String basketTypeId = basketDto.getBasketTypeId();
		
		for (BasketQuotaDTO dto : quotaList) {
			if ("M004".equalsIgnoreCase(dto.getQuotaId())) {
				quota = "Y";
			}
		}
		List<String> vasList = vasDetailService.getSubscribedVASList(basketDto.getBasketId(), vasDetail.getVasitem(), sessionCust.getBrand(), sessionCust.getSimType());
		
		if(multiSimInfos != null && multiSimInfos.size() != 0){
			List<String> msiServiceDateList = new ArrayList<String> ();
			for(String item : vasList){
				List<ItemFuncAssgnMobDTO> funcList = itemFuncMobService.findItemFuncAssgnMobDTO(item, appDate);
				if(funcList.contains("EX03")) {
					for(MultiSimInfoDTO msi : multiSimInfos){
						if(item.equals(msi.getItemId())){
							for(MultiSimInfoMemberDTO msim : msi.getMembers()){
								if(msim.getMnpCutOverDate() != null)
									msiServiceDateList.add(msim.getMnpCutOverDate());
							}
						}	
					}
				}
			}
			referenceData.put("msiServiceDateList", msiServiceDateList);
		}

		referenceData.put("sessionCust", sessionCust);
		referenceData.put("channelCd", salesUserDto.getChannelCd());
		referenceData.put("staff_id", salesUserDto.getUsername());
//		referenceData.put("unicomCityList", unicomCityList);
		referenceData.put("orderIdSession",
				MobCcsSessionUtil.getSession(request, "orderIdSession"));
		referenceData.put("workStatus",
				MobCcsSessionUtil.getSession(request, "workStatus"));
		referenceData.put("contractPeriod", basketDto.getContractPeriod());
		referenceData.put("rpRecurChange", basketDto.getRecurrentAmt());
		// add by Eliot 20120413
		referenceData.put("hottestModelInd", hottestModelInd);
		referenceData.put("activeSrvReqDateStart", Utility.date2String(activeSrvReqDateStart, BomWebPortalConstant.DATE_FORMAT));
		referenceData.put("activeCutOverDateStart", Utility.date2String(activeCutOverDateStart, BomWebPortalConstant.DATE_FORMAT));
		referenceData.put("activeSrvReqDateEnd", Utility.date2String(activeSrvReqDateEnd, BomWebPortalConstant.DATE_FORMAT));
		referenceData.put("activeFurtherCutOverDateEnd", Utility.date2String(activeFurtherCutOverDateEnd, BomWebPortalConstant.DATE_FORMAT));
		referenceData.put("activeCutOverDateEnd", Utility.date2String(activeCutOverDateEnd, BomWebPortalConstant.DATE_FORMAT));
		referenceData.put("activeCutOverDateExtendedEnd", Utility.date2String(activeCutOverDateExtendedEnd, BomWebPortalConstant.DATE_FORMAT));
		referenceData.put("quota", quota);
		referenceData.put("appDate", appDate);
		referenceData.put("basketTypeId", basketTypeId);
		
		OrderDTO orderDTO =(OrderDTO)MobCcsSessionUtil.getSession(request, "orderDTO");
		referenceData.put("orderDTO", orderDTO);
		
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

		//referenceData.put("ccsFurtherMnpMaxDate", BomWebPortalCcsConstant.CCS_FURTHER_MNP_MAX_DATE);
		referenceData.put("ccsFurtherMnpMaxDate", BomWebPortalConstant.CCS_NEWMRTMNP_CUTOVER_SRD_EXTEND_DAYS);
		return referenceData;
	}
	
	
	protected void onBind(HttpServletRequest request, Object command) throws Exception {
		
		MRTUI mrtUI = (MRTUI) command;
		logger.info("(MRTUI)command mnp_ind :" + mrtUI.getMnpInd());
		
		mrtUI.setPreviousMrtUi(sessionMRTUI);
		
		List<String> goldenType = mobCcsMrtService.getGoldenNumLvL(false);
		
		//set MNP_IND, GOLDEN_IND & CHINA_IND based on MRT_IND
		if ("C".equalsIgnoreCase(mrtUI.getMrtInd())) {
			mrtUI.setChinaInd("Y");
		} else if ("A".equalsIgnoreCase(mrtUI.getMrtInd())) {
			mrtUI.setChinaInd("N");
			mrtUI.setMnpInd("A");
		} else if ("O".equalsIgnoreCase(mrtUI.getMrtInd())) {
			mrtUI.setChinaInd("N");
		}
		
		if ("A".equalsIgnoreCase(mrtUI.getMrtInd()) && goldenType.contains(mrtUI.getMsisdnLvl())) {
			mrtUI.setGoldenInd("Y");
		} else if (("O".equalsIgnoreCase(mrtUI.getMrtInd()) || "C".equalsIgnoreCase(mrtUI.getMrtInd())) 
				&& "N".equalsIgnoreCase(mrtUI.getMnpInd()) 
				&& goldenType.contains(mrtUI.getMsisdnLvl())) {
			mrtUI.setGoldenInd("Y");
		} else {
			mrtUI.setGoldenInd("N");
		}
		
	}
	
	public ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws ServletException, AppRuntimeException {

		String nextView = (String) request.getAttribute("nextView");
		logger.info("nextView: " + nextView);

		String currentView = (String) request.getParameter("currentView");
		logger.info("currentView: " + currentView);

		MRTUI mrtUI = (MRTUI) command;
		logger.info("(MRTUI)command mnp_ind :" + mrtUI.getMnpInd());
		
		// new MRT
		if ("N".equals(mrtUI.getMnpInd()) && "N".equals(mrtUI.getGoldenInd())
				&& "N".equals(mrtUI.getChinaInd())) {
			clearMnpField(mrtUI);
			clearUnicomField(mrtUI);
		}

		// new Golden MRT
		else if ("Y".equals(mrtUI.getGoldenInd()) && "N".equals(mrtUI.getChinaInd())
				&& "N".equals(mrtUI.getMnpInd())) {
			clearMnpField(mrtUI);
			clearUnicomField(mrtUI);
		}

		// MNP
		else if ("Y".equals(mrtUI.getMnpInd()) && "N".equals(mrtUI.getGoldenInd()) 
				&& "N".equals(mrtUI.getChinaInd())) {
			clearMobField(mrtUI);
			clearUnicomField(mrtUI);
		}

		// 1C2N(New MRT)
		else if ("N".equals(mrtUI.getMnpInd()) && "N".equals(mrtUI.getGoldenInd()) 
				&& "Y".equals(mrtUI.getChinaInd())) {
			clearMnpField(mrtUI);
		}

		// 1C2N(New Golden MRT)
		else if ("N".equals(mrtUI.getMnpInd()) && "Y".equals(mrtUI.getChinaInd()) 
				&& "Y".equals(mrtUI.getGoldenInd())) {
			clearMnpField(mrtUI);
		}

		// 1C2N(MNP)
		else if ("Y".equals(mrtUI.getMnpInd()) && "N".equals(mrtUI.getGoldenInd())
				&& "Y".equals(mrtUI.getChinaInd())) {
			clearMobField(mrtUI);
		}

		// New MRT + MNP
		else if (BomWebPortalCcsConstant.MRT_NEW_MRT_AND_MNP.equals(mrtUI
				.getMnpInd())
				&& "N".equals(mrtUI.getChinaInd())) {
			clearUnicomField(mrtUI);
		}
		
		//check for data amendment in MRTUI and order contains OCID
		OrderDTO sessionOrderDTO = (OrderDTO) MobCcsSessionUtil.getSession(request, "orderDTO");
		
		if (sessionOrderDTO != null && StringUtils.isNotBlank(sessionOrderDTO.getOcid()) 
				&& StringUtils.isNotBlank(sessionOrderDTO.getOrderId())) {
			
			ArrayList<MobCcsMrtBaseDTO> mobCcsMrtDtoList = mobCcsMrtService.getMobCcsMrtDTO(sessionOrderDTO.getOrderId());
			MRTUI dbMrtUI = null;
			if (mobCcsMrtDtoList != null && !mobCcsMrtDtoList.isEmpty()) {
		    	 dbMrtUI = mobCcsMrtService.mrtDtoChangeToUiDto(mobCcsMrtDtoList);
		    }
			
			if (dbMrtUI != null) {
				try {
					boolean mnpSame = BeanUtilsHelper.compareSpecificProperties(dbMrtUI, mrtUI, toCheckMnpProperties);
					boolean mrtSame = BeanUtilsHelper.compareSpecificProperties(dbMrtUI, mrtUI, toCheckMrtProperties);
					
					if (!mnpSame) {
						mrtUI.setMnpAmend(true);
					} else {
						mrtUI.setMnpAmend(false);
					}
					
					if (!mrtSame) {
						mrtUI.setMrtAmend(true);
					} else {
						mrtUI.setMrtAmend(false);
					}
					
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
			}
		}
		
		MobCcsSessionUtil.setSession(request, "mrt", mrtUI);

		return new ModelAndView(new RedirectView(nextView));
	}

	private void clearMnpField(MRTUI mrtUI) {
		mrtUI.setCustName("");
		mrtUI.setMnpMsisdn("");
		mrtUI.setDocNum("");
		mrtUI.setCutOverDateStr("");
		mrtUI.setCutOverTime("");
		mrtUI.setDocNum("");
		mrtUI.setMnpTicketNum("");
	}

	private void clearMobField(MRTUI mrtUI) {
		mrtUI.setMobMsisdn("");
		mrtUI.setMsisdnLvl("");
		mrtUI.setServiceReqDateStr("");
		//mrtUI.setNumType(""); 
	}

	private void clearUnicomField(MRTUI mrtUI) {
		mrtUI.setUnicomMobGrade("");
		mrtUI.setUnicomMobMsisdn("");
		mrtUI.setUnicomMobStatus("");
		mrtUI.setCityCd("");
	}

}
