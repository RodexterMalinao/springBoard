package com.bomwebportal.web;

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
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

import com.bomwebportal.bean.LookupTableBean;
import com.bomwebportal.dao.MultiSimInfoDAO;
import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.dto.CustomerProfileDTO;
import com.bomwebportal.dto.MnpDTO;
import com.bomwebportal.dto.MultiSimInfoDTO;
import com.bomwebportal.dto.MultiSimInfoMemberDTO;
import com.bomwebportal.dto.OrderDTO;
import com.bomwebportal.dto.SimDTO;
import com.bomwebportal.dto.SuperOrderDTO;
import com.bomwebportal.dto.VasDetailDTO;
import com.bomwebportal.mob.ccs.dto.CodeLkupDTO;
import com.bomwebportal.mob.ccs.service.CodeLkupService;
import com.bomwebportal.mob.ccs.service.MobCcsOrderSearchService;
import com.bomwebportal.mob.ccs.util.MobCcsSessionUtil;
import com.bomwebportal.mob.ds.service.MobDsMrtManagementService;
import com.bomwebportal.service.MnpService;
import com.bomwebportal.service.MultiSimInfoService;
import com.bomwebportal.service.VasDetailService;
import com.bomwebportal.util.Utility;
import com.bomwebportal.wsclient.exception.WsClientException;

import bom.mob.schema.javabean.si.springboard.xsd.SubActiveContract;
import bom.mob.schema.javabean.si.springboard.xsd.SubInfoDTO;

import com.bomwebportal.dto.SubscriberDTO;
import com.bomwebportal.dto.BomMupInfoDTO;

public class MultiSimInfoController extends SimpleFormController {
	
	public MultiSimInfoController() {
		super();
	}
	//public static final String NANO_SIM_ITEM_ID = "104000008";
	//public static final String NANO_SIM_ADAPTER_ITEM_ID = "106000411";
	private MultiSimInfoService multiSimInfoService;
	private VasDetailService vasDetailService;
	private MobCcsOrderSearchService mobCcsOrderSearchService;
	private MnpService mnpService;
	private CodeLkupService codeLkupService;
	private MobDsMrtManagementService mobDsMrtManagementService;

	public MultiSimInfoService getMultiSimInfoService() {
		return multiSimInfoService;
	}

	public void setMultiSimInfoService(MultiSimInfoService multiSimInfoService) {
		this.multiSimInfoService = multiSimInfoService;
	}

	public VasDetailService getVasDetailService() {
		return vasDetailService;
	}

	public void setVasDetailService(VasDetailService vasDetailService) {
		this.vasDetailService = vasDetailService;
	}
	
	public CodeLkupService getCodeLkupService() {
		return codeLkupService;
	}

	public void setCodeLkupService(CodeLkupService codeLkupService) {
		this.codeLkupService = codeLkupService;
	}

	public MobCcsOrderSearchService getMobCcsOrderSearchService() {
		return mobCcsOrderSearchService;
	}

	public void setMobCcsOrderSearchService(
			MobCcsOrderSearchService mobCcsOrderSearchService) {
		this.mobCcsOrderSearchService = mobCcsOrderSearchService;
	}

	public MnpService getMnpService() {
		return mnpService;
	}

	public void setMnpService(MnpService mnpService) {
		this.mnpService = mnpService;
	}

	public MobDsMrtManagementService getMobDsMrtManagementService() {
		return mobDsMrtManagementService;
	}

	public void setMobDsMrtManagementService(MobDsMrtManagementService mobDsMrtManagementService) {
		this.mobDsMrtManagementService = mobDsMrtManagementService;
	}

	public Object formBackingObject(HttpServletRequest request)
			throws ServletException {
		String appDate = (String) request.getSession().getAttribute("appDate");
		String vasItemId = (String) request.getParameter("vasitem");
		BomSalesUserDTO user = (BomSalesUserDTO) request.getSession().getAttribute("bomsalesuser");
		CustomerProfileDTO sessionCustomer = null;
		if (user.getChannelId() == 2) {
			sessionCustomer = (CustomerProfileDTO) MobCcsSessionUtil.getSession(request, "customer");
		} else {
			sessionCustomer = (CustomerProfileDTO) request.getSession().getAttribute("customer");
		}
		
		
		List<MultiSimInfoDTO> multiSimInfoDTOs = (List<MultiSimInfoDTO>) request.getSession().getAttribute("MultiSimInfoList");
		MultiSimInfoDTO multiSimInfoDTO = null;
		if (multiSimInfoDTOs != null) {
			for (MultiSimInfoDTO msi : multiSimInfoDTOs) {
				
				
				if (vasItemId.equals(msi.getItemId())) {
					multiSimInfoDTO = msi;
				}
				
				if ("2".equalsIgnoreCase(sessionCustomer.getPcrfMupAlert())){
					msi.setPcrfMupAlert(true);
				}
				
				for (MultiSimInfoMemberDTO msim :msi.getMembers()){
					if ("MUPS02".equals(msim.getMemberOrderType())) {
						msim.setMemberOrderType("MUPS01");
						msim.setMnpInd("A");
					}
					
					if ("MUPS04".equals(msim.getMemberOrderType())) {
						msim.setCurMsisdn(msim.getMsisdn());
						if ("Y".equals(msim.getBrmChgSimInd())) {
							msim.setChgSimBool(true);
						}
					}
				}
			}
		}


		String locale = RequestContextUtils.getLocale(request).toString();
		int channelId=user.getChannelId();
		
		/////////////////////////////////////////////////////////////////////////////////////////
		// Initial DTO Object
		if (multiSimInfoDTO == null) {
			multiSimInfoDTO = new MultiSimInfoDTO();
			multiSimInfoDTO.setItemId(vasItemId);
		} else {
			multiSimInfoDTO = multiSimInfoDTO.getClone();
		}
		multiSimInfoDTO.setAmend(false);
		multiSimInfoDTO.setAmendSim(false);
		Map<String, List<CodeLkupDTO>> entityCodeMap = LookupTableBean.getInstance().getCodeLkupList();
		multiSimInfoDTO.setMemberCount(0);
		for (CodeLkupDTO dto : entityCodeMap.get("MULTISIM_SIM_COUNT")) {
			if (vasItemId.equalsIgnoreCase(dto.getCodeId())) {
				multiSimInfoDTO.setMemberCount(Integer.parseInt(dto.getCodeDesc()));
			}
		}
		
		if (multiSimInfoDTO.getMembers() == null) {
			multiSimInfoDTO.setMembers(new ArrayList<MultiSimInfoMemberDTO>());
		}
		for (int i=1; i<=multiSimInfoDTO.getMemberCount(); i++) {
			if (multiSimInfoDTO.getMembers().size() < i) {
				multiSimInfoDTO.getMembers().add(new MultiSimInfoMemberDTO());
				multiSimInfoDTO.getMembers().get(i-1).setSelectedVasItemList(new ArrayList<String>());
				multiSimInfoDTO.getMembers().get(i-1).setSim(new SimDTO());
			}
			multiSimInfoDTO.getMembers().get(i-1).setMemberNum("ABCDEFGHIJKLMNOPQRSTUVWXYZ".substring(i-1, i));
			
			//DENNIS MIP3
			multiSimInfoDTO.getMembers().get(i-1).setMnpRno((Utility.getRno(sessionCustomer.getSimType())));
		}
		
		for (CodeLkupDTO dto : entityCodeMap.get("MULTISIM_ITEM_BASKET")) {
			if (vasItemId.equalsIgnoreCase(dto.getCodeId())) {
				multiSimInfoDTO.setBasket(vasDetailService.getBasketAttribute(dto.getCodeDesc(), appDate));
			}
		}
		
		SuperOrderDTO superOrderDto = (SuperOrderDTO) request.getSession().getAttribute("superOrderDto");
		String channelCd = superOrderDto.getOrderShopCd();
		
		multiSimInfoDTO.setSimItemList(new ArrayList<VasDetailDTO>());
		multiSimInfoDTO.setBundleItemList(new ArrayList<VasDetailDTO>());
		multiSimInfoDTO.setOptionalItemList(new ArrayList<VasDetailDTO>());
		
		// MIP.P4 modification
		String basketId = multiSimInfoDTO.getBasket().getBasketId();
		String nature = vasDetailService.getBasketAttribute(basketId, appDate).getNature();
		List<VasDetailDTO> vasList=vasDetailService.getRPHSRPList(locale, multiSimInfoDTO.getBasket().getBasketId(), appDate, channelCd, sessionCustomer.getBrand(), sessionCustomer.getSimType(), nature);
		
		// MIP.P4 modification
		List<VasDetailDTO> simVasList=vasDetailService.getSimTypeSelection(locale, appDate, multiSimInfoDTO.getBasket().getBasketId(), "", channelCd, sessionCustomer.getSimType(), sessionCustomer.getBrand(), multiSimInfoDTO.getBasket().getNature());

		// MIP.P4 modification
		// String nature = service.getBasketAttribute(selectedBasketId, appDate).getNature();
		List<VasDetailDTO> optionalVasList = vasDetailService.getVasDetailList(channelId+"" ,locale, multiSimInfoDTO.getBasket().getBasketId(), null, "ITEM_SELECT" , appDate, channelCd, sessionCustomer.getBrand(), sessionCustomer.getSimType(), nature);
	
		for (VasDetailDTO vas: vasList) {
			if ("M".equalsIgnoreCase(vas.getItemMdoInd())) {
				multiSimInfoDTO.getBundleItemList().add(vas);
			} else if ("D".equalsIgnoreCase(vas.getItemMdoInd())) {
				multiSimInfoDTO.getBundleItemList().add(vas);
			} else{
				//multiSimInfoDTO.getOptionalItemList().add(vas);
				multiSimInfoDTO.getBundleItemList().add(vas);
			}
		}
		for (VasDetailDTO vas: simVasList) {
			vas.setItemType("SIM");
			vas.setItemHtml2(vas.getItemHtml().replaceAll("\\<.*?>",""));
			multiSimInfoDTO.getSimItemList().add(vas);
		}
		
		for (VasDetailDTO vas: optionalVasList) {
			vas.setItemMdoInd("O");
			multiSimInfoDTO.getOptionalItemList().add(vas);
		}
		
		multiSimInfoDTO.setSession(request.getSession());
		multiSimInfoDTO.setOrder((OrderDTO)MobCcsSessionUtil.getSession(request, "orderDTO"));
		if (channelId == 10 || channelId == 11 || channelId == 1 || channelId == 3) {
			multiSimInfoDTO.setOrder((OrderDTO)request.getSession().getAttribute("orderDtoOriginal"));
		}
		
		multiSimInfoDTO.setValue("customer", sessionCustomer); //Dennis MIP3
		
		return multiSimInfoDTO;
	}
	
	public ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws ServletException {
		BomSalesUserDTO user = (BomSalesUserDTO) request.getSession().getAttribute("bomsalesuser");
		List<MultiSimInfoDTO> multiSimInfoDTOs = (List<MultiSimInfoDTO>) request.getSession().getAttribute("MultiSimInfoList");
		
		MultiSimInfoDTO multiSimInfoDTO = (MultiSimInfoDTO) command;
		MnpDTO mnpDTO = (MnpDTO) multiSimInfoDTO.getSession().getAttribute("MNP");
		MultiSimInfoDTO originalMSIDTO = null;
		
		SubInfoDTO subInfoDto = (SubInfoDTO) request.getSession().getAttribute("subInfo");
		CustomerProfileDTO customerProfileDTO = null;
		String appMode = (String) request.getSession().getAttribute("appMode");
		if ("shop".equals(appMode) || "directsales".equals(appMode)) {
			customerProfileDTO = (CustomerProfileDTO) request.getSession().getAttribute("customer");
		} else {
			customerProfileDTO = (CustomerProfileDTO) MobCcsSessionUtil.getSession(request, "customer");
		}
		
		
		
		if (multiSimInfoDTOs != null) {
			for (MultiSimInfoDTO msi : multiSimInfoDTOs) {
				if (multiSimInfoDTO.getItemId().equals(msi.getItemId())) {
					originalMSIDTO = msi;
				}
			}
			if (originalMSIDTO != null) {
				multiSimInfoDTOs.remove(originalMSIDTO);
			}
		} else {
			multiSimInfoDTOs = new ArrayList<MultiSimInfoDTO>();
		}
		for (int i=1; i<=multiSimInfoDTO.getMemberCount(); i++) {
			if (multiSimInfoDTO.getMembers().get(i-1).getSelectedVasItemList() == null) {
				multiSimInfoDTO.getMembers().get(i-1).setSelectedVasItemList(new ArrayList<String>());
			}
		}
		
		OrderDTO orderDTO= (OrderDTO)MobCcsSessionUtil.getSession(request, "orderDTO");
		
		for (MultiSimInfoMemberDTO msim : multiSimInfoDTO.getMembers()) {
			
			msim.setToo1Ind(multiSimInfoService.getToo1Ind(msim, customerProfileDTO));
			msim.setBrmInd(multiSimInfoService.getBrmInd(msim, customerProfileDTO));
			msim.setAutoInd(multiSimInfoService.getAutoInd(msim));
			msim.setCosInd(multiSimInfoService.getCosInd(msim));
			msim.setCmnInd(multiSimInfoService.getCmnInd(msim));
			msim.setCsimInd(multiSimInfoService.getCsimInd(msim));
			
			if ("MUPS01".equals(msim.getMemberOrderType()) && !"A".equals(msim.getMnpInd())) {
				msim.setCurAcctNo("");
				msim.setCurCustNo("");
				msim.setCurFirstName("");
				msim.setCurIdDocNum("");
				msim.setCurIdDocType("");
				msim.setCurLastName("");
				msim.setCurMobCustNo("");
				msim.setCurMsisdn("");
				msim.setCurPenaltyWaiveInd("");
				msim.setCurSimIccid("");
				msim.setCurSimItemCd("");
				msim.setCurSimItemDesc("");
				msim.setCurTitle("");
				msim.setToo1WaiveReasonCd("");
				msim.setToo1AdminCharge("");
				msim.setMnpCustName("");
				msim.setMnpCutOverDate("");
				msim.setMnpCutOverTime("");
				msim.setMnpDno("");
				msim.setMnpDocNo("");
				msim.setMnpTicketNum("");
				msim.setCurCheckResult("");
				msim.setCurCheckErrMsg("");
				msim.setCurAcctNo("");
				msim.setCurActiveContractduration("");
				msim.setCurActiveContracteffEndDate("");
				msim.setCurActiveContracteffStartDate("");
				msim.setCurActiveContractparentProdType("");
				msim.setCurActiveContracttcDesc("");
				msim.setPrePaidSimDocInd("");
				if (msim.isSameAsCust()) {
					msim.getActualUserDTO().setSubTitle("");
					msim.getActualUserDTO().setSubLastName("");
					msim.getActualUserDTO().setSubFirstName("");
					msim.getActualUserDTO().setSubDocType("");
					msim.getActualUserDTO().setSubDocNum("");
					msim.getActualUserDTO().setSubEmailAddr("");
					msim.getActualUserDTO().setSubCompanyName("");
					msim.getActualUserDTO().setSubContactTel("");
					msim.getActualUserDTO().setSubDateOfBirthStr("");
				}
				if (!"Prepaid Sim".equalsIgnoreCase(msim.getMnpCustName())) {
					msim.setPrePaidSimDocInd("");
				}
				
				msim.setMnpInd("N");
				msim.setBrmChgSimInd("");
			}
			
			if ("MUPS01".equals(msim.getMemberOrderType()) && "A".equals(msim.getMnpInd())) {
				msim.setCurAcctNo("");		
				msim.setCurCustNo("");
				msim.setCurFirstName("");
				msim.setCurIdDocNum("");
				msim.setCurIdDocType("");
				msim.setCurLastName("");
				msim.setCurMobCustNo("");
				msim.setCurMsisdn("");
				msim.setCurPenaltyWaiveInd("");
				msim.setCurSimIccid("");
				msim.setCurSimItemCd("");
				msim.setCurSimItemDesc("");
				msim.setCurTitle("");
				msim.setToo1WaiveReasonCd("");
				msim.setToo1AdminCharge("");
				msim.setCurCheckResult("");
				msim.setCurCheckErrMsg("");
				msim.setCurActiveContractduration("");
				msim.setCurActiveContracteffEndDate("");
				msim.setCurActiveContracteffStartDate("");
				msim.setCurActiveContractparentProdType("");
				msim.setCurActiveContracttcDesc("");
				if (msim.isSameAsCust()) {
					msim.getActualUserDTO().setSubTitle("");
					msim.getActualUserDTO().setSubLastName("");
					msim.getActualUserDTO().setSubFirstName("");
					msim.getActualUserDTO().setSubDocType("");
					msim.getActualUserDTO().setSubDocNum("");
					msim.getActualUserDTO().setSubEmailAddr("");
					msim.getActualUserDTO().setSubCompanyName("");
					msim.getActualUserDTO().setSubContactTel("");
					msim.getActualUserDTO().setSubDateOfBirthStr("");
				}
				
				msim.setMemberOrderType("MUPS02");
				msim.setMnpInd("A");
				msim.setBrmChgSimInd("");
			}
			
			if ("MUPS04".equals(msim.getMemberOrderType())) {
				
				msim.setMnpCustName("");
				if ("Y".equals(msim.getMnpOrder())) {
					msim.setMnpCutOverDate(msim.getServiceRequestDate());
					msim.setMnpCutOverTime("0");
					msim.setMnpInd("Y");
				} else {
					msim.setMnpCutOverDate("");
					msim.setMnpCutOverTime("");
					msim.setMnpInd("");
				}
				if ("BRM".equals(msim.getToo1BrmOrder())) {
					if (msim.isChgSimBool()) {
						msim.setBrmChgSimInd("Y");
					} else {
						msim.setBrmChgSimInd("N");
					}
				}
				if ("BRM".equals(msim.getToo1BrmOrder()) && "Y".equals(msim.getBrmChgSimInd())) {
					msim.getSim().setIccid(msim.getChgSimIccid());
				} else {
					msim.getSim().setIccid("");
					msim.getSim().setImsi("");
					msim.getSim().setPuk1("");
					msim.getSim().setPuk2("");
				}
				msim.setMnpDno("");
				msim.setMnpDocNo("");
				msim.setMnpRno("");
				msim.setMnpTicketNum("");
				msim.setMsisdnlvl("");
				msim.setPrePaidSimDocInd("");
				msim.setSameAsCust(true);
				msim.getActualUserDTO().setSubTitle("");
				msim.getActualUserDTO().setSubLastName("");
				msim.getActualUserDTO().setSubFirstName("");
				msim.getActualUserDTO().setSubDocType("");
				msim.getActualUserDTO().setSubDocNum("");
				msim.getActualUserDTO().setSubEmailAddr("");
				msim.getActualUserDTO().setSubCompanyName("");
				msim.getActualUserDTO().setSubContactTel("");
				msim.getActualUserDTO().setSubDateOfBirthStr("");
				if ("".equalsIgnoreCase(msim.getCurActiveContractduration())){
					msim.setCurPenaltyWaiveInd("");
				}
				msim.setMsisdn(msim.getCurMsisdn());
			}
			
			if ("MUPS05".equals(msim.getMemberOrderType())) {
				msim.setCurAcctNo("");		
				msim.setCurCustNo("");
				msim.setCurFirstName("");
				msim.setCurIdDocNum("");
				msim.setCurIdDocType("");
				msim.setCurLastName("");
				msim.setCurMobCustNo("");
				msim.setCurMsisdn("");
				msim.setCurPenaltyWaiveInd(null);
				msim.setCurSimIccid("");
				msim.setCurSimItemCd("");
				msim.setCurSimItemDesc("");
				msim.setCurTitle("");
				msim.setToo1WaiveReasonCd("");
				msim.setToo1AdminCharge("");
				msim.setCurCheckResult("");
				msim.setCurCheckErrMsg("");
				msim.setCurActiveContractduration("");
				msim.setCurActiveContracteffEndDate("");
				msim.setCurActiveContracteffStartDate("");
				msim.setCurActiveContractparentProdType("");
				msim.setCurActiveContracttcDesc("");
				if (msim.isSameAsCust()) {
					msim.getActualUserDTO().setSubTitle("");
					msim.getActualUserDTO().setSubLastName("");
					msim.getActualUserDTO().setSubFirstName("");
					msim.getActualUserDTO().setSubDocType("");
					msim.getActualUserDTO().setSubDocNum("");
					msim.getActualUserDTO().setSubEmailAddr("");
					msim.getActualUserDTO().setSubCompanyName("");
					msim.getActualUserDTO().setSubContactTel("");
					msim.getActualUserDTO().setSubDateOfBirthStr("");
				}
				if (!"Prepaid Sim".equalsIgnoreCase(msim.getMnpCustName())) {
					msim.setPrePaidSimDocInd("");
				}
				
				msim.setMsisdn(msim.getMnpNumber());
				msim.setMnpInd("Y");
				msim.setBrmChgSimInd("");
			}
			
			if ("MUPS99".equals(msim.getMemberOrderType())) {	
				msim.setMsisdn("");
				msim.setMsisdnlvl("");
				msim.setMnpInd("");
				msim.setMnpNumber("");
				msim.setMnpCutOverDate("");
				msim.setMnpCutOverTime("");
				msim.setMnpRno("");
				msim.setMnpDno("");
				msim.setMnpCustName("");
				msim.setMnpDocNo("");
				msim.setMnpTicketNum("");
				msim.getSim().setIccid("");
				msim.getSim().setImsi("");
				msim.getSim().setPuk1("");
				msim.getSim().setPuk2("");
				msim.getSim().setItemCode("");
				msim.setOcid("");
				msim.setMemberStatus("");
				msim.setErrMsg("");
				msim.setErrCd("");
				msim.setPrePaidSimDocInd("");
				msim.setAutoInd("N");
				msim.setCurCustNo("");
				msim.setCurMobCustNo("");
				msim.setCurAcctNo("");
				msim.setCurIdDocType("");
				msim.setCurIdDocNum("");
				msim.setCurTitle("");
				msim.setCurFirstName("");
				msim.setCurLastName("");
				msim.setCurSimIccid("");
				msim.setCurSimItemCd("");
				msim.setCurSimItemDesc("");
				msim.setCurPenaltyWaiveInd("");
				msim.setToo1AdminCharge("");
				msim.setToo1WaiveReasonCd("");
				msim.setNfcInd("");
				msim.setNumType("");
				msim.getSim().setSimType("");

				msim.setSelectedVasItemList(new ArrayList<String>());
				msim.setSelectedSimItemId("");
				msim.setCurCheckResult("");
				msim.setCurCheckErrMsg("");
				msim.setCurActiveContractduration("");
				msim.setCurActiveContracteffEndDate("");
				msim.setCurActiveContracteffStartDate("");
				msim.setCurActiveContractparentProdType("");
				msim.setCurActiveContracttcDesc("");
				msim.getActualUserDTO().setSubTitle("");
				msim.getActualUserDTO().setSubLastName("");
				msim.getActualUserDTO().setSubFirstName("");
				msim.getActualUserDTO().setSubDocType("");
				msim.getActualUserDTO().setSubDocNum("");
				msim.getActualUserDTO().setSubEmailAddr("");
				msim.getActualUserDTO().setSubCompanyName("");
				msim.getActualUserDTO().setSubContactTel("");
				msim.getActualUserDTO().setSubDateOfBirthStr("");
				if(!"Prepaid Sim".equalsIgnoreCase(msim.getMnpCustName())) {
					msim.setPrePaidSimDocInd("");
				}
				msim.setSameAsCust(true);
				msim.setBrmChgSimInd("");
			}
		}
		
		if (originalMSIDTO != null) {
			
			for (MultiSimInfoMemberDTO msim : originalMSIDTO.getMembers()) {
				
				if ("N".equals(msim.getMnpInd())) {
					
					msim.setMnpCustName("");
					msim.setMnpCutOverDate("");
					msim.setMnpCutOverTime("");
					msim.setMnpCustName("");
					msim.setMnpDno("");
					msim.setMnpDocNo("");
					msim.setMnpNumber("");
					msim.setMnpTicketNum("");
					
				}
				
			}
			
			if (orderDTO != null && orderDTO.getOcid() != null && orderDTO.getOcid().length() > 0) {
				
				if (originalMSIDTO.getMembers().size() == multiSimInfoDTO.getMembers().size()) {
					
					for (int i = 0; i < originalMSIDTO.getMembers().size(); i++) {
						
						MultiSimInfoMemberDTO oMember = originalMSIDTO.getMembers().get(i);
						MultiSimInfoMemberDTO member = multiSimInfoDTO.getMembers().get(i);
						
						if (StringUtils.isNotEmpty(member.getSelectedSimItemId())) {
							
							if (!member.getSelectedSimItemId().equals(oMember.getSelectedSimItemId()) || (user.getChannelId() != 2 && !member.getSim().getIccid().equals(oMember.getSim().getIccid()))) {
								
								multiSimInfoDTO.setAmendSim(true);
							
							}
							
						}
						
						if (!member.getMnpInd().equals(oMember.getMnpInd()) ||
							!member.getMnpCustName().equals(oMember.getMnpCustName()) ||
							!member.getMnpCutOverDate().equals(oMember.getMnpCutOverDate()) ||
							!member.getMnpCutOverTime().equals(oMember.getMnpCutOverTime()) ||
							!member.getMnpNumber().equals(oMember.getMnpNumber()) ||
							!member.getMnpDocNo().equals(oMember.getMnpDocNo()) ||
							!member.getMnpTicketNum().equals(oMember.getMnpTicketNum())) {
							
								multiSimInfoDTO.setAmend(true);
								member.setAmendMNP(true);
								
						}
						
					}
					
				}
				
			}
			
		}
		
		//add or release lock for new msisdn -- Retail Sales
		if (user.getChannelId() == 1 ) {
			
			//reserve part
			if (multiSimInfoDTO != null){
				for (MultiSimInfoMemberDTO msim : multiSimInfoDTO.getMembers()) {
					String checkMsisdn = msim.getMsisdn();
					boolean exist = false;
					
					if (originalMSIDTO != null){
						for (int i = 0; i < originalMSIDTO.getMembers().size(); i++) {
							if (originalMSIDTO.getMembers()!=null && originalMSIDTO.getMembers().get(i)!=null && StringUtils.isNotEmpty(originalMSIDTO.getMembers().get(i).getOriginalNewMsisdn())){
								if (checkMsisdn.equals(originalMSIDTO.getMembers().get(i).getOriginalNewMsisdn())){
									exist = true;
									break;
								}
							}
							
						}
					}
					
					if (!exist){
						if (mnpDTO!=null){
							logger.info("reserve number: "+ checkMsisdn);
							mnpService.reserveMsisdn(checkMsisdn, "A", mnpDTO.getShopCd());
						}
					}
					
				}
			}
			
			//release part
			if (originalMSIDTO != null){
				for (MultiSimInfoMemberDTO msim : originalMSIDTO.getMembers()) {
					String checkMsisdn = msim.getOriginalNewMsisdn();
					boolean exist = false;
					
					if (multiSimInfoDTO != null){
						for (int i = 0; i < multiSimInfoDTO.getMembers().size(); i++) {
							if (multiSimInfoDTO.getMembers()!=null && multiSimInfoDTO.getMembers().get(i)!=null && StringUtils.isNotEmpty(multiSimInfoDTO.getMembers().get(i).getMsisdn())){
								if (!"MUPS99".equalsIgnoreCase(msim.getMemberOrderType())){
									if (checkMsisdn.equals(multiSimInfoDTO.getMembers().get(i).getMsisdn())){
										exist = true;
										break;
									}								
								}
							}
						}
					}
					
					if (!exist){
						if (mnpDTO!=null){
							logger.info("release number: "+ checkMsisdn);
							mnpService.reserveMsisdn(checkMsisdn, "D", mnpDTO.getShopCd());	
						}
					}
				}
			}
			
			if (multiSimInfoDTO!=null && multiSimInfoDTO.getMembers()!=null){
				for (int i = 0; i < multiSimInfoDTO.getMembers().size(); i++) {
					if (multiSimInfoDTO.getMembers() != null && multiSimInfoDTO.getMembers().get(i)!=null){
						multiSimInfoDTO.getMembers().get(i).setOriginalNewMsisdn(multiSimInfoDTO.getMembers().get(i).getMsisdn());
					}
				}
			}
		}
		
		
		
		multiSimInfoDTOs.add(multiSimInfoDTO);
		if ("amend".equals(multiSimInfoDTO.getActionType()) && multiSimInfoDTO.isAmend()) {
			multiSimInfoService.orderHistoryProcess(orderDTO.getOrderId());
			multiSimInfoService.updateBomWebMultiSimMNP(multiSimInfoDTO, user.getUsername());
			multiSimInfoService.insertSbOrderAmendDTO(multiSimInfoDTO, user.getUsername());
		}
		request.getSession().setAttribute("MultiSimInfoList", multiSimInfoDTOs);
		return new ModelAndView(new RedirectView("closepopup.jsp"));
	}
	
	protected Map<String, Object> referenceData(HttpServletRequest request) throws Exception {
		String action = (String) request.getParameter("action");
		Map<String, Object> referenceData = new HashMap<String, Object>();
		
		BomSalesUserDTO user = (BomSalesUserDTO) request.getSession().getAttribute("bomsalesuser");
		OrderDTO orderDTO= (OrderDTO)MobCcsSessionUtil.getSession(request, "orderDTO");
		CustomerProfileDTO sessionCustomer = null; //Dennis MIP3
		if (user.getChannelId() == 2) {
			sessionCustomer = (CustomerProfileDTO) MobCcsSessionUtil.getSession(request, "customer");
		} else {
			sessionCustomer = (CustomerProfileDTO) request.getSession().getAttribute("customer");
		}
		referenceData.put("sessionCustomer", sessionCustomer);
		
		int channelId=user.getChannelId();
		if (LookupTableBean.getInstance().getAllowFalloutChannelList()!=null && LookupTableBean.getInstance().getAllowFalloutChannelList().length>0 && Utility.isContainString(LookupTableBean.getInstance().getAllowFalloutChannelList(), user.getChannelCd())){
			channelId =2;
		} else if (user.getChannelId() == 10 || user.getChannelId()==11) {
			channelId = 10;
		}
		referenceData.put("channelId", channelId);
		referenceData.put("order", orderDTO);
		referenceData.put("action", action);
		
		if (channelId == 10) {
			try{
				String staffId = user.getUsername();
				String channelCd = user.getChannelCd();
				OrderDTO originalOrder = (OrderDTO) request.getSession().getAttribute("orderDtoOriginal");
				Date appDate = new Date();
				if (originalOrder != null) {
					staffId = originalOrder.getSalesCd();
					appDate = originalOrder.getAppInDate();
					channelCd = mobDsMrtManagementService.getSalesChannelCd(staffId, appDate);
				}
				List<String[]> numList = new ArrayList<String[]>();
				List<String[]> tempNumList = mnpService.getMrtNum(staffId, Arrays.asList(new String[] {"N1"}), appDate, sessionCustomer.getNumType());
				if (tempNumList != null) {
					numList.addAll(tempNumList);
				}
				tempNumList = mnpService.getMrtNum(staffId, Arrays.asList(new String[] {"N2"}), appDate,sessionCustomer.getNumType());
				if (tempNumList != null) {
					numList.addAll(tempNumList);
				}
				String oldMsisdn = (String)request.getSession().getAttribute("msisdn");
				if (oldMsisdn != null) {
					String[] oldMrt = mnpService.getMrtNum(oldMsisdn, channelCd, sessionCustomer.getNumType()); //Dennis MIP3
					if (oldMrt != null) {
						numList.add(oldMrt);
					}
				}
				referenceData.put("numList", numList);
			}catch(Exception e){
				logger.error(e);
				e.printStackTrace();
		    }
		} else if (user.getChannelId() == 2) {
			try{
				List<String[]> numList = new ArrayList<String[]>();
				List<String[]> tempNumList = mnpService.getCCSMrtNum("MUL", "N1", sessionCustomer.getNumType());
				if (tempNumList != null) {
					numList.addAll(tempNumList);
				}
				tempNumList = mnpService.getCCSMrtNum("MUL", "N2", sessionCustomer.getNumType());
				if (tempNumList != null) {
					numList.addAll(tempNumList);
				}
				String oldMsisdn = (String)request.getSession().getAttribute("msisdn");
				if (oldMsisdn != null) {
					String[] oldMrt = mnpService.getMrtNum(oldMsisdn, "MUL", sessionCustomer.getNumType()); //Dennis MIP3
					if (oldMrt != null) {
						numList.add(oldMrt);
					}
				}
				referenceData.put("numList", numList);
			}catch(Exception e){
				logger.error(e);
				e.printStackTrace();
		    }
		}
		
		List<String> titleList = new ArrayList<String>();
		titleList.add("Dr");
		titleList.add("Mdm");
		titleList.add("Miss");
		titleList.add("Mr");
		titleList.add("Mrs");
		titleList.add("Ms");
		titleList.add("Prof");
		titleList.add("Rev");
		referenceData.put("titleList", titleList);
		
		return referenceData;
	}

	
}
