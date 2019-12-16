package com.bomwebportal.validator;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.bomwebportal.bean.LookupTableBean;
import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.dto.CustomerProfileDTO;
import com.bomwebportal.dto.ExclusiveItemDTO;
import com.bomwebportal.dto.ItemsRelationshipDTO;
import com.bomwebportal.dto.MnpDTO;
import com.bomwebportal.dto.MobileSimInfoDTO;
import com.bomwebportal.dto.MultiSimInfoDTO;
import com.bomwebportal.dto.MultiSimInfoMemberDTO;
import com.bomwebportal.dto.OrderDTO;
import com.bomwebportal.dto.PcRelationshipDTO;
import com.bomwebportal.dto.VasDetailDTO;
import com.bomwebportal.mob.ccs.dto.CodeLkupDTO;
import com.bomwebportal.mob.ccs.dto.ui.MRTUI;
import com.bomwebportal.mob.ccs.util.BomWebPortalCcsConstant;
import com.bomwebportal.mob.ds.service.MobDsMrtManagementService;
import com.bomwebportal.mob.validate.dto.ResultDTO;
import com.bomwebportal.mob.validate.service.ValidateService;
import com.bomwebportal.service.ItemsRelationshipService;
import com.bomwebportal.service.MnpService;
import com.bomwebportal.service.MobileSimInfoService;
import com.bomwebportal.service.MultiSimInfoService;
import com.bomwebportal.service.OrderService;
import com.bomwebportal.service.RelationshipCheckService;
import com.bomwebportal.service.VasDetailService;
import com.bomwebportal.util.BomWebPortalConstant;
import com.bomwebportal.util.Utility;
import com.bomwebportal.wsclient.BomCosOrderWsClient;
import com.bomwebportal.wsclient.exception.WsClientException;
import com.bomwebportal.mob.ccs.service.CodeLkupService;

import bom.mob.schema.javabean.si.springboard.xsd.SubInfoDTO;

public class MultiSimInfoValidator implements Validator{

	protected final Log logger = LogFactory.getLog(getClass());
	private MnpService mnpService;
	private MobileSimInfoService simService;
	private OrderService orderService;
	private VasDetailService vasDetailService;
	private ItemsRelationshipService itemsRelationshipService;
	private MultiSimInfoService multiSimInfoService;
	private RelationshipCheckService relationshipCheckService;
	private MobDsMrtManagementService mobDsMrtManagementService;
	private ValidateService validateService;
	private BomCosOrderWsClient bomCosOrderWsClient;
	
	public BomCosOrderWsClient getBomCosOrderWsClient() { return bomCosOrderWsClient; }
	public void setBomCosOrderWsClient(BomCosOrderWsClient bomCosOrderWsClient) { this.bomCosOrderWsClient = bomCosOrderWsClient; }
	
	public MnpService getMnpService() {
		return mnpService;
	}

	public void setMnpService(MnpService mnpService) {
		this.mnpService = mnpService;
	}

	public MobileSimInfoService getSimService() {
		return simService;
	}

	public void setSimService(MobileSimInfoService simService) {
		this.simService = simService;
	}

	public OrderService getOrderService() {
		return orderService;
	}

	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}

	public VasDetailService getVasDetailService() {
		return vasDetailService;
	}

	public void setVasDetailService(VasDetailService vasDetailService) {
		this.vasDetailService = vasDetailService;
	}

	public ItemsRelationshipService getItemsRelationshipService() {
		return itemsRelationshipService;
	}

	public void setItemsRelationshipService(
			ItemsRelationshipService itemsRelationshipService) {
		this.itemsRelationshipService = itemsRelationshipService;
	}

	public MultiSimInfoService getMultiSimInfoService() {
		return multiSimInfoService;
	}

	public void setMultiSimInfoService(MultiSimInfoService multiSimInfoService) {
		this.multiSimInfoService = multiSimInfoService;
	}

	public RelationshipCheckService getRelationshipCheckService() {
		return relationshipCheckService;
	}

	public void setRelationshipCheckService(
			RelationshipCheckService relationshipCheckService) {
		this.relationshipCheckService = relationshipCheckService;
	}

	public boolean supports(Class arg0) {
		return arg0.equals(MultiSimInfoDTO.class);
	}

	public MobDsMrtManagementService getMobDsMrtManagementService() {
		return mobDsMrtManagementService;
	}

	public void setMobDsMrtManagementService(MobDsMrtManagementService mobDsMrtManagementService) {
		this.mobDsMrtManagementService = mobDsMrtManagementService;
	}

	
	
	public ValidateService getValidateService() {
		return validateService;
	}

	public void setValidateService(ValidateService validateService) {
		this.validateService = validateService;
	}

	public void validate(Object arg0, Errors errors) {
		MultiSimInfoDTO multiSimInfoDTO = (MultiSimInfoDTO) arg0;
		BomSalesUserDTO user = (BomSalesUserDTO) multiSimInfoDTO.getSession().getAttribute("bomsalesuser");
		MnpDTO mnpDTO = (MnpDTO) multiSimInfoDTO.getSession().getAttribute("MNP");
		MRTUI mrtUI = (MRTUI) multiSimInfoDTO.getSession().getAttribute("mrt");
		CustomerProfileDTO sessionCustomer = (CustomerProfileDTO)multiSimInfoDTO.getValue("customer");
		
		//dennis enable mnp
		boolean enableMNP = true;
		List<CodeLkupDTO> enableMNPList  = (List<CodeLkupDTO>)LookupTableBean.getInstance().getCodeLkupList().get("MIP_SHOW_MNP_TICKET_BTN");
		if (CollectionUtils.isNotEmpty(enableMNPList)){
			for (CodeLkupDTO dto: enableMNPList){
				if ("N".equalsIgnoreCase(dto.getCodeId())){
					enableMNP = false;
					break;
				}
			}
		}
		
		int channelId = user.getChannelId(); 
		if (LookupTableBean.getInstance().getAllowFalloutChannelList()!=null && LookupTableBean.getInstance().getAllowFalloutChannelList().length>0 && Utility.isContainString(LookupTableBean.getInstance().getAllowFalloutChannelList(), user.getChannelCd())){
			channelId = 2;
		} else if (user.getChannelId() == 10 || user.getChannelId()==11) {
			channelId = 10;
		}
		OrderDTO orderDto = multiSimInfoDTO.getOrder();
		for (int i = 0; i < multiSimInfoDTO.getMemberCount(); i++) {
			MultiSimInfoMemberDTO memberDTO = multiSimInfoDTO.getMembers().get(i);

			// Check Empty in Member Order Type
			if (StringUtils.isEmpty(memberDTO.getMemberOrderType())) { 
				errors.rejectValue("members[" + i + "].memberOrderType", "dummy", "Member Order Type is empty.");
				continue;
			} else {
				System.out.println("Member Order Type " + memberDTO.getMemberNum() + " = " + memberDTO.getMemberOrderType());
			}
			
			// Ignore DO NOT CREATE Checking
			if (memberDTO.getMemberOrderType().equals("MUPS99")) {
				memberDTO.setMnpInd(null);
				continue;
			}
			
			if (memberDTO.getMemberOrderType().equals("MUPS04")) {
				if (!memberDTO.getCheckPass().equals("PASS")) {
					errors.rejectValue("members[" + i + "].memberOrderType", "dummy", "Failed in migrate current line.");
				}
				
				if (!memberDTO.getCurActiveContractparentProdType().equals("")) {
					if (StringUtils.isEmpty(memberDTO.getCurPenaltyWaiveInd())) {
						errors.rejectValue("members[" + i + "].memberOrderType", "dummy", "Failed - Press Check to correct.");
						errors.rejectValue("members[" + i + "].curPenaltyWaiveInd", "dummy", "Please choose charge or waive.");
					}
				}
				
				if (StringUtils.isEmpty(memberDTO.getToo1AdminChargeInd())) {
					errors.rejectValue("members[" + i + "].memberOrderType", "dummy", "Failed - Press Check to correct.");
					errors.rejectValue("members[" + i + "].too1AdminCharge", "dummy", "Please choose charge or waive.");
				}
				
				if (memberDTO.getToo1BrmOrder().equals("BRM")) {
					if (memberDTO.isChgSimBool()) {
						if (StringUtils.isEmpty(memberDTO.getChgSimWaiveReasonRadio())) {
							errors.rejectValue("members[" + i + "].memberOrderType", "dummy", "Failed - Press Check to correct.");
							errors.rejectValue("members[" + i + "].chgSimWaiveReason", "dummy", "Please choose charge or waive.");
						} else {
							if (channelId == 2) {
								String chgSimItemCd = memberDTO.getChgSimItemCd();
								boolean existFlag = false;
								for (VasDetailDTO vas : multiSimInfoDTO.getSimItemList()) {
									if (vas.getItemId().equals(chgSimItemCd)) {
										existFlag = true;
										memberDTO.getSim().setItemCode(vas.getPosItemCd());
										break;
									}
								}
								if (!existFlag) {
									errors.rejectValue("members[" + i + "].chgSimItemCd", "invalid.itemCdAndBoMWebItemCd");
								}
								String simType = vasDetailService.getMipSimType(chgSimItemCd);
								com.bomwebportal.mob.validate.dto.ResultDTO result = validateService.validateSimType(simType, sessionCustomer.getSimType(), "members[" + i + "].chgSimItemCd");
								validateService.bindErrors(result, errors);	
								if (!result.hasError()){
									memberDTO.getSim().setSimType(sessionCustomer.getSimType());
								}
							} else {
								boolean sameICCID = false;
								if (memberDTO.getOriginalSimICCID() != null && memberDTO.getChgSimIccid().equals(memberDTO.getOriginalSimICCID())) {
									sameICCID = true;
								} 
								if (orderDto == null || orderDto.getOcid() == null || orderDto.getOcid().trim().length() == 0 || (!sameICCID && (user.getChannelId() == 10 || user.getChannelId()==11))) {
									if (StringUtils.isNotEmpty(memberDTO.getChgSimIccid()) && memberDTO.getChgSimIccid().length() == 19) {
										MobileSimInfoDTO inMobileSimInfoDto = new MobileSimInfoDTO();
										inMobileSimInfoDto.setIccid(memberDTO.getChgSimIccid());
										MobileSimInfoDTO resultMobileSimInfoDto = simService.validateSim(inMobileSimInfoDto);
										if (resultMobileSimInfoDto != null && memberDTO.getChgSimIccid().equals(resultMobileSimInfoDto.getIccid())) {
											memberDTO.getSim().setImsi(resultMobileSimInfoDto.getImsi());
											memberDTO.getSim().setItemCode(resultMobileSimInfoDto.getItemCd());
											memberDTO.getSim().setPuk1(resultMobileSimInfoDto.getPuk1());
											memberDTO.getSim().setPuk2(resultMobileSimInfoDto.getPuk2());
											String simItemCd = resultMobileSimInfoDto.getItemCd();
										
											boolean existFlag = false;
											String selectedSIMItemId = "";
											for (VasDetailDTO vas : multiSimInfoDTO.getSimItemList()) {
												if (vas.getPosItemCd().equals(simItemCd)) {
													existFlag = true;
													selectedSIMItemId = vas.getItemId();
													break;
												}
											}
											memberDTO.setSelectedSimItemId(selectedSIMItemId);
											String pendingOrderId = "";
											if (user.getChannelId() == 10 || user.getChannelId() == 11) {
												if (orderDto != null && StringUtils.isNotBlank(orderDto.getOrderId())) {
													pendingOrderId = simService.getPendingOrderExistWithIccidOrderId(
															memberDTO.getChgSimIccid(), orderDto.getOrderId());
												} else {
													pendingOrderId = simService.getPendingOrderExistWithIccid(memberDTO.getChgSimIccid());
												}
											}
											if (!existFlag) {
												errors.rejectValue("members[" + i + "].chgSimIccid", "invalid.itemCdAndBoMWebItemCd");
											} else if (resultMobileSimInfoDto.getHwInvStatus() != BomWebPortalConstant.HWINV_VALID_STATUS) {							
												errors.rejectValue("members[" + i + "].chgSimIccid", "", "SIM is invalid in HW Inventory.");	
											} else if ((channelId == 10 || channelId == 11) && !resultMobileSimInfoDto.getShopCd().equals("P"+mnpDTO.getShopCd())) {
												errors.rejectValue("members[" + i + "].chgSimIccid", "shopCd.notMatch");
											} else if (channelId != 10 && channelId != 11 &&!resultMobileSimInfoDto.getShopCd().equals("P"+user.getBomShopCd())) {
												errors.rejectValue("members[" + i + "].chgSimIccid", "shopCd.notMatch");
											} else if (StringUtils.isNotBlank(pendingOrderId)){
												errors.rejectValue("members[" + i + "].chgSimIccid", "", "Pending SB order exists with this input SIM ICCID, order ID =" + pendingOrderId);
											}
											
											ResultDTO result = validateService.validateSimTypeByIccid(memberDTO.getChgSimIccid(), sessionCustomer.getSimType(), "members[" + i + "].chgSimIccid");				
											validateService.bindErrors(result, errors);	
											if (!result.hasError()){
												memberDTO.getSim().setSimType(sessionCustomer.getSimType());
											}
										} else {
											errors.rejectValue("members[" + i + "].memberOrderType", "dummy", "Failed - Press Check to correct.");
											errors.rejectValue("members[" + i + "].chgSimIccid", "", "SIM is not found in HW Inventory.");	
										}
										for (MultiSimInfoMemberDTO tempMember: multiSimInfoDTO.getMembers()) {
											if (!tempMember.getMemberNum().equals(memberDTO.getMemberNum())) {
												if (memberDTO.getChgSimIccid().equals(tempMember.getChgSimIccid())) {
													errors.rejectValue("members[" + i + "].chgSimIccid", "dummy", "Duplicated SIM number found.");
												}
											}
										}
									} else {
										errors.rejectValue("members[" + i + "].memberOrderType", "dummy", "Failed - Press Check to correct.");
										errors.rejectValue("members[" + i + "].chgSimIccid", "", "SIM Number is invalid or empty.");
									}
								}
							}
						}
					}
				}
					
				if (memberDTO.getMnpOrder().equals("Y")) {
					if (StringUtils.isEmpty(memberDTO.getServiceRequestDate())) {
						errors.rejectValue("members[" + i + "].memberOrderType", "dummy", "Failed - Press Check to correct.");
						errors.rejectValue("members[" + i + "].serviceRequestDate", "dummy", "Please input service request date.");
					} else {
						if (!Utility.isValidDate(memberDTO.getServiceRequestDate())) {
							errors.rejectValue("members[" + i + "].memberOrderType", "dummy", "Failed - Press Check to correct.");
							errors.rejectValue("members[" + i + "].serviceRequestDate", "dummy", "Please input correct service request date.");
						}
						
						Calendar serviceRequestDate= Calendar.getInstance();
						serviceRequestDate.setTime(Utility.string2Date(memberDTO.getServiceRequestDate()));
						
						if (channelId == 2 && orderDto != null) {
							//CCS
							Date srDate = orderDto.getSrvReqDate();
							if(srDate != null && StringUtils.isNotEmpty(memberDTO.getServiceRequestDate())){
							    
							    try {
							        Calendar serviceReqDate= Calendar.getInstance();
							        serviceReqDate.setTime(srDate);

							            Calendar serviceReqDatePlusN= Calendar.getInstance();
							            serviceReqDatePlusN.setTime(srDate);
							            serviceReqDatePlusN.add(Calendar.HOUR, BomWebPortalConstant.CCS_NEWMRTMNP_CUTOVER_SRD_EXTEND_DAYS*24);//DENNIS201606
							            
							            
							            
							            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
							            logger.info("serviceReqDate:"+sdf.format(serviceReqDate.getTime()));
							            logger.info("serviceRequestDate:"+sdf.format(serviceRequestDate.getTime()));
							            logger.info("serviceReqDatePlusN:"+sdf.format(serviceReqDatePlusN.getTime()));
							            
							            long diffHours = (serviceRequestDate.getTimeInMillis() - serviceReqDate.getTimeInMillis()) / (60 * 60 * 1000);
							            long diff2inHour = (serviceReqDatePlusN.getTimeInMillis() - serviceRequestDate.getTimeInMillis()) / (60 * 60 * 1000);
										String opssCases[]={"TGTG","T3TG","NPTG","M3M3","MPM3"};//dno rno
							            if (StringUtils.isNotEmpty(memberDTO.getOpssInd()) && "Y".equals(memberDTO.getOpssInd())  && ArrayUtils.contains(opssCases, mnpDTO.getDno() + mnpDTO.getRno())){
							            	if (diffHours<0)
							            		  errors.rejectValue("members[" + i + "].serviceRequestDate",
									                        "",
									                        "Sub service request date should be same as or after Service request days for OPSS ");
							            	}
							            else{
							            	if ("MUPS05".equalsIgnoreCase(memberDTO.getMemberOrderType())) {
							            		if (diffHours < 0 ) {
							            			errors.rejectValue("members[" + i + "].mnpCutOverDate", "", "Sub service request date should on or after Service request day");
							            		}
							            	} else {
							            		if (diffHours < 72) {
							            			errors.rejectValue("members[" + i + "].serviceRequestDate", "", "Sub service request date should after Service request + 3 days");
							            		} else if (diff2inHour < 0) {
							            			errors.rejectValue("members[" + i + "].serviceRequestDate", "", "Sub service request date should in Service request + " + BomWebPortalConstant.CCS_NEWMRTMNP_CUTOVER_SRD_EXTEND_DAYS + " days");
							            		}
							            	}
							            }
							    } catch (Exception e) {
							    	errors.rejectValue("members[" + i + "].memberOrderType", "dummy", "Failed - Press Check to correct.");
							    }
							}
						} else if (mnpDTO != null) {
							//For Direct Sales and Retail Mode
							String srDate = mnpDTO.getCutoverDateStr();
							if ("N".equals(mnpDTO.getMnp())) {
								srDate = mnpDTO.getServiceReqDateStr();
							}
							if(StringUtils.isNotEmpty(srDate) && StringUtils.isNotEmpty(memberDTO.getServiceRequestDate())){
								
								try {
									Calendar serviceReqDate= Calendar.getInstance();
									serviceReqDate.setTime(Utility.string2Date(srDate));
															
									Calendar serviceReqDatePlus90= Calendar.getInstance();
									serviceReqDatePlus90.setTime(Utility.string2Date( srDate ) );
									
									//DENNIS201606
									int mupCutoverSrdExtendDays = BomWebPortalConstant.RS_NEWMRTMNP_CUTOVER_SRD_EXTEND_DAYS;
									if (channelId == 10 || channelId == 11) {
										mupCutoverSrdExtendDays = BomWebPortalConstant.DS_NEWMRTMNP_CUTOVER_SRD_EXTEND_DAYS;
									}
									
									serviceReqDatePlus90.add(Calendar.HOUR, mupCutoverSrdExtendDays*24);//add 90 days
									
									SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
									logger.info("serviceReqDate:"+sdf.format(serviceReqDate.getTime()));
									logger.info("serviceRequestDate:"+sdf.format(serviceRequestDate.getTime()));
									logger.info("serviceReqDatePlus90:"+sdf.format(serviceReqDatePlus90.getTime()));
									
									long diffHours = (serviceRequestDate.getTimeInMillis() - serviceReqDate.getTimeInMillis()) / (60 * 60 * 1000);
									long diff2inHour = (serviceReqDatePlus90.getTimeInMillis() - serviceRequestDate.getTimeInMillis()) / (60 * 60 * 1000);
									String opssCases[]={"TGTG","T3TG","NPTG","M3M3","MPM3"};//dno rno
								   if (StringUtils.isNotEmpty(memberDTO.getOpssInd()) && "Y".equals(memberDTO.getOpssInd()) && ArrayUtils.contains(opssCases, mnpDTO.getDno() + mnpDTO.getRno())){
									   if (diffHours<0)
										   errors.rejectValue("members[" + i + "].memberOrderType", "dummy", "Failed - Press Check to correct.");
					            		  errors.rejectValue("members[" + i + "].serviceRequestDate",
							                        "",
							                        "Sub service request date should be same as or after Service request days for OPSS ");
					            	}else{
					            		if ("MUPS05".equalsIgnoreCase(memberDTO.getMemberOrderType())) {
						            		if (diffHours < 0 ) {
						            			errors.rejectValue("members[" + i + "].mnpCutOverDate", "", "Sub service request date should on or after Service request day");
						            		}
						            	} else {
						            		if (diffHours < 72) {
						            			errors.rejectValue("members[" + i + "].memberOrderType", "dummy", "Failed - Press Check to correct.");
												errors.rejectValue("members[" + i + "].serviceRequestDate", "", "Sub service request date should after Service request + 3 days");
											} else if (diff2inHour < 0) {
												errors.rejectValue("members[" + i + "].memberOrderType", "dummy", "Failed - Press Check to correct.");
												errors.rejectValue("members[" + i + "].serviceRequestDate", "", "Sub service request date should in Service request + "+mupCutoverSrdExtendDays+" days");
											}
						            	}
					            	}
									//dennis enable mnp
									if (enableMNP){
										if (channelId == 10 || channelId == 11) {
											Calendar currentDate = Calendar.getInstance();
											Calendar tPlus30Date = Calendar.getInstance();
											tPlus30Date.set(currentDate.get(Calendar.YEAR),
													currentDate.get(Calendar.MONTH),
													currentDate.get(Calendar.DATE));
											tPlus30Date.add(Calendar.DATE, 30);
											if (serviceRequestDate.after(tPlus30Date)) {
												logger.info("futhercutover date after T + 30");
												if (memberDTO.getMnpTicketNum().length()!=0 && memberDTO.getMnpTicketNum() != null)
													errors.rejectValue("members[" + i + "].mnpTicketNum", "mnpTicketNum.isblank");
											}
										}			
									}
									
								} catch (Exception e) {
									errors.rejectValue("members[" + i + "].serviceRequestDate",
											"futherCutoverDate.invalid.exception",	"e.getMessage()" + e.getMessage());
									logger.info("Exception: " + e.getMessage());
								}
							}else if (channelId != 10 && channelId != 11){
								errors.rejectValue("members[" + i + "].serviceRequestDate",
										"futherCutoverDate.invalid.exception",	"futherCutoverDate and serviceReqDate must input");
							}
						} 
					}
					}
					
			}
				
			if (memberDTO.getMemberOrderType().equals("MUPS01") || memberDTO.getMemberOrderType().equals("MUPS02")) {
			
			/////////////////////////////////////////////////////////////////////////////////
			// Check MSISDN
			
			if (!Utility.validatePhoneNum(memberDTO.getMsisdn())){
				errors.rejectValue("members[" + i + "].msisdn", "serviceNum.required");
			} else {
				//TODO: Check if order is recalled.  Skip checking process if Number is same as original order.
				if (mnpDTO != null) {
					if ("N".equals(mnpDTO.getMnp())) {
						if (mnpDTO.getNewMsisdn().equals(memberDTO.getMsisdn()) 
							|| (mnpDTO.isFutherMnp() && mnpDTO.getFutherMnpNumber().equals(memberDTO.getMsisdn()))) {
							errors.rejectValue("members[" + i + "].msisdn", "dummy", "MRT number should not be same as primary order.");
						}
					} else if (mnpDTO.getMnpMsisdn().equals(memberDTO.getMsisdn())) {
						errors.rejectValue("members[" + i + "].msisdn", "dummy", "MRT number should not be same as primary order.");
					}
				}
				for (MultiSimInfoMemberDTO tempMember: multiSimInfoDTO.getMembers()) {
					if (!tempMember.getMemberNum().equals(memberDTO.getMemberNum())) {
						if ((memberDTO.getMsisdn().equals(tempMember.getMsisdn())) 
							|| ("A".equals(tempMember.getMnpInd()) && memberDTO.getMsisdn().equals(tempMember.getMnpNumber()))) {
							errors.rejectValue("members[" + i + "].msisdn", "dummy", "Duplicated MRT number found.");
						}
					} else if ("A".equals(memberDTO.getMnpInd()) && memberDTO.getMsisdn().equals(memberDTO.getMnpNumber())) {
						errors.rejectValue("members[" + i + "].msisdn", "dummy", "Duplicated MRT number found.");
					}
				}
				
				//Used in Pending Order
				
				boolean sameMSISDN = false;
				if (memberDTO.getOriginalMsisdn() != null && memberDTO.getMsisdn().equals(memberDTO.getOriginalMsisdn())) {
					sameMSISDN = true;
				}
				if (orderDto == null 
						|| orderDto.getOcid() == null || orderDto.getOcid().trim().length() == 0 
						|| (!sameMSISDN && (user.getChannelId() == 10 || user.getChannelId()==11))) { //Don't check if has OCID 
					String bomPendingOrderMessage =mnpService.checkBomPendingOrder(memberDTO.getMsisdn());
					if(StringUtils.isNotEmpty(bomPendingOrderMessage)){
						errors.rejectValue("members[" + i + "].msisdn", "dummy", bomPendingOrderMessage);
					} else {
						//CNM Status
						if (StringUtils.isBlank(user.getBomShopCd())){
							errors.rejectValue("members[" + i + "].msisdn","dummy", "BOM Shop Code is empty.");	
						}else{
						
						
							MnpDTO inMnpDto = new MnpDTO();
							
							inMnpDto.setNewMsisdn(memberDTO.getMsisdn());
							if (user.getChannelId() == 10 || user.getChannelId()==11) {
								inMnpDto.setShopCd(mnpDTO.getShopCd());
								String orderIdUsingSameMRT = orderService.getOrderIdUsingSameMRTShop(memberDTO.getMsisdn(),
										(orderDto == null ? "New Order" : orderDto.getOrderId()),mnpDTO.getShopCd());
								if (StringUtils.isNotEmpty(orderIdUsingSameMRT)) {
									errors.rejectValue("members[" + i + "].msisdn", "dummy", 
										"Same MRT \"" + memberDTO.getMsisdn() + "\" "
											+ "is already used in existing order(s) \"" + orderIdUsingSameMRT + "\"");
								}
							} else {
								inMnpDto.setShopCd(user.getBomShopCd());
							}
							
							MnpDTO outMnpDto = null;
							if (user.getChannelId() == 1) {
								outMnpDto = mnpService.validateNewMsisdn(inMnpDto);
							} else {
								//outMnpDto = mnpService.validateCnmMsindn(inMnpDto.getNewMsisdn(), inMnpDto.getShopCd());
								outMnpDto = mnpService.validateNewMsisdn(inMnpDto); //DENNIS MIP3
							}
							
							List<String> dbMultiSimMRTList = new ArrayList<String>();
							if (orderDto != null && orderDto.getOrderId() != null) {
								dbMultiSimMRTList = multiSimInfoService.getDBSecondaryMRTs(orderDto.getOrderId());
							}
							
							String[] mrtInfo = mnpService.getMrtNum(memberDTO.getMsisdn(), "MUL", null); //Dennis MIP3
							if (mrtInfo == null && channelId == 2) {
								memberDTO.setMsisdnlvl("");//clear data
								errors.rejectValue("members[" + i + "].msisdn", "dummy", "Cannot find Line Number in MRT inventory");
							} else if (mrtInfo != null && channelId == 2 && !"MUL".equals(mrtInfo[2])) {
								memberDTO.setMsisdnlvl("");//clear data
								errors.rejectValue("members[" + i + "].msisdn", "dummy", "Line Number does not belong to MUL group");
							} else if (mrtInfo !=null && channelId == 2 && !"2".equals(mrtInfo[5]) && !dbMultiSimMRTList.contains(memberDTO.getMsisdn())) {
								memberDTO.setMsisdnlvl("");//clear data
								errors.rejectValue("members[" + i + "].msisdn", "dummy", "Line Number is not free in MRT inventory");
							} /*else if (mrtInfo !=null && channelId == 2 && 	validateService.validateNumType(mrtInfo[6], sessionCustomer.getNumType(), "members["+i+"].numType",true).hasError()	) {
								memberDTO.setMsisdnlvl("");//clear data
								validateService.bindErrors(validateService.validateNumType(mrtInfo[6], sessionCustomer.getNumType(), "members["+i+"].numType",true), errors);		
								
							}*/ else if (outMnpDto == null) {
								memberDTO.setMsisdnlvl("");//clear data
								memberDTO.setNumType("");
								errors.rejectValue("members[" + i + "].msisdn", "invalid.msisdn");
							} else if (outMnpDto != null
									&& BomWebPortalConstant.CNM_STATUS_NORMAL == outMnpDto.getCnmStatus()) {
								//MSISDN Level
								//if (user.getChannelId() == 1 || user.getChannelId() == 10 || user.getChannelId() == 11){ //Dennis MIP3
								System.out.println(Utility.toPrettyJson(outMnpDto));
								com.bomwebportal.mob.validate.dto.ResultDTO result = validateService.validateNumType(outMnpDto.getNumType(), sessionCustomer.getNumType(), "members["+i+"].numType",false);			
								if (result.hasError()){//Dennis MIP3	
									validateService.bindErrors(result, errors);			 
								}else{
									memberDTO.setNumType(outMnpDto.getNumType());
								}
								
								
								if ("N".equalsIgnoreCase(outMnpDto.getMsisdnLvl())) {
									memberDTO.setMsisdnlvl(outMnpDto.getMsisdnLvl());
									if (user.getChannelId() == 10 || user.getChannelId()==11) {
										mobDsMrtManagementService.deleteDsReuseMrtInventory(memberDTO.getMsisdn());
									}
								} else {
									memberDTO.setMsisdnlvl("");//clear data
									memberDTO.setNumType("");
									errors.rejectValue("members[" + i + "].msisdn", "dummy", "Member Line Number must has MSISDN Level = N1 / N2");
								}
							} else {
								memberDTO.setMsisdnlvl("");//clear data
								memberDTO.setNumType("");
								errors.rejectValue("members[" + i + "].msisdn", "invalid.msisdn");
							}
						
						
						}
					}
				}
			}
			
			
			if (channelId != 2) {
				/////////////////////////////////////////////////////////////////////////////////			
				//Check SIM Number
				
				//TODO: Check if order is recalled.  Skip checking process if SIM Number is same as original order.
				boolean sameICCID = false;
				if (memberDTO.getOriginalSimICCID() != null && memberDTO.getSim().getIccid().equals(memberDTO.getOriginalSimICCID())) {
					sameICCID = true;
				} 
				if (orderDto == null 
						|| orderDto.getOcid() == null || orderDto.getOcid().trim().length() == 0 
						|| (!sameICCID && (user.getChannelId() == 10 || user.getChannelId()==11))) {
					if (StringUtils.isNotEmpty(memberDTO.getSim().getIccid()) && memberDTO.getSim().getIccid().length() == 19) {
						MobileSimInfoDTO inMobileSimInfoDto = new MobileSimInfoDTO();
						inMobileSimInfoDto.setIccid(memberDTO.getSim().getIccid());
						MobileSimInfoDTO resultMobileSimInfoDto = simService.validateSim(inMobileSimInfoDto);
						if (resultMobileSimInfoDto != null
								&& memberDTO.getSim().getIccid().equals(
										resultMobileSimInfoDto.getIccid())) {
							memberDTO.getSim().setImsi(resultMobileSimInfoDto.getImsi());
							memberDTO.getSim().setItemCode(resultMobileSimInfoDto.getItemCd());
							memberDTO.getSim().setPuk1(resultMobileSimInfoDto.getPuk1());
							memberDTO.getSim().setPuk2(resultMobileSimInfoDto.getPuk2());
							//memberDTO.getSim().setSimType(Utility.checkSimType(memberDTO.getSim().getIccid()));
							String simItemCd = resultMobileSimInfoDto.getItemCd();
							
							
							boolean existFlag = false;
							String selectedSIMItemId = "";
							for (VasDetailDTO vas : multiSimInfoDTO.getSimItemList()) {
								if (vas.getPosItemCd().equals(simItemCd)) {
									existFlag = true;
									selectedSIMItemId = vas.getItemId();
									break;
								}
							}
							memberDTO.setSelectedSimItemId(selectedSIMItemId);
							
							String pendingOrderId = "";
							if (user.getChannelId() == 10 || user.getChannelId() == 11) {
								if (orderDto != null && StringUtils.isNotBlank(orderDto.getOrderId())) {
									pendingOrderId = simService.getPendingOrderExistWithIccidOrderId(
											memberDTO.getSim().getIccid(), orderDto.getOrderId());
								} else {
									pendingOrderId = simService
											.getPendingOrderExistWithIccid(memberDTO.getSim().getIccid());
								}
							}
							
							if (!existFlag) {
								errors.rejectValue("members[" + i + "].sim.iccid", "invalid.itemCdAndBoMWebItemCd");
							} else if (resultMobileSimInfoDto.getHwInvStatus() != BomWebPortalConstant.HWINV_VALID_STATUS) {							
								errors.rejectValue("members[" + i + "].sim.iccid", "", "SIM is invalid in HW Inventory.");	
							} else if ((channelId == 10 || channelId == 11) && !resultMobileSimInfoDto.getShopCd().equals("P"+mnpDTO.getShopCd())) {
								errors.rejectValue("members[" + i + "].sim.iccid", "shopCd.notMatch");
							} else if (channelId != 10 && channelId != 11 &&!resultMobileSimInfoDto.getShopCd().equals("P"+user.getBomShopCd())) {
								errors.rejectValue("members[" + i + "].sim.iccid", "shopCd.notMatch");
							} else if (StringUtils.isNotBlank(pendingOrderId)){
								errors.rejectValue("members[" + i + "].sim.iccid", "", "Pending SB order exists with this input SIM ICCID, order ID =" + pendingOrderId);
							}
							
							ResultDTO result = validateService.validateSimTypeByIccid(memberDTO.getSim().getIccid(), sessionCustomer.getSimType(), "members[" + i + "].sim.iccid");				
							validateService.bindErrors(result, errors);	
							if (!result.hasError()){
								memberDTO.getSim().setSimType(sessionCustomer.getSimType());
							}
							
							
						} else {
							errors.rejectValue("members[" + i + "].sim.iccid", "", "SIM is not found in HW Inventory.");	
						}
						for (MultiSimInfoMemberDTO tempMember: multiSimInfoDTO.getMembers()) {
							if (!tempMember.getMemberNum().equals(memberDTO.getMemberNum())) {
								if (memberDTO.getSim().getIccid().equals(tempMember.getSim().getIccid())) {
									errors.rejectValue("members[" + i + "].sim.iccid", "dummy", "Duplicated SIM number found.");
								}
							}
						}
					} else {
						errors.rejectValue("members[" + i + "].sim.iccid", "", "SIM Number is invalid or empty.");
					}
				}
			} else {
				/////////////////////////////////////////////////////////////////////////////////			
				//Check SIM Type
				String simItemId = memberDTO.getSelectedSimItemId();
				boolean existFlag = false;
				for (VasDetailDTO vas : multiSimInfoDTO.getSimItemList()) {
					if (vas.getItemId().equals(simItemId)) {
						existFlag = true;
						memberDTO.getSim().setItemCode(vas.getPosItemCd());
						break;
					}
				}
				if (!existFlag) {
					errors.rejectValue("members[" + i + "].selectedSimItemId", "invalid.itemCdAndBoMWebItemCd");
				} 
				
				
				//Dennis MIP3 
				//check simType with selectedSimType
				
				String simType = vasDetailService.getMipSimType(simItemId);
				com.bomwebportal.mob.validate.dto.ResultDTO result = validateService.validateSimType(simType, sessionCustomer.getSimType(), "members[" + i + "].selectedSimItemId");
				validateService.bindErrors(result, errors);	
				if (!result.hasError()){
					memberDTO.getSim().setSimType(sessionCustomer.getSimType());
				}
			}
			
			if("A".equalsIgnoreCase(memberDTO.getMnpInd())){
				//Check mnpNumber
				if (!Utility.validatePhoneNum(memberDTO.getMnpNumber().trim())){
					errors.rejectValue("members[" + i + "].mnpNumber", "serviceNum.required");
				} else {
					String mnpNumber = memberDTO.getMnpNumber().trim();
					if (!"".equals(mnpNumber)) {
						if (!Utility.validateHKMobilePrefix(mnpNumber)){
							errors.rejectValue("members[" + i + "].mnpNumber", "custInfoMRTPrefix.invalid", "custInfoMRTPrefix.invalid");
						}
						
						
						//DENNIS MIP3
						MnpDTO result = new MnpDTO();
						result.setMnpMsisdn(memberDTO.getMnpNumber());
						if ("A".equals(memberDTO.getMnpInd())){
							result.setPrepaidSimInd(true);
							result.setCustIdDocNum(memberDTO.getMnpDocNo());
						}
						com.bomwebportal.mob.validate.dto.ResultDTO validateResult = validateService.validateMNP(result, "members[" + i + "].mnpNumber", memberDTO.isIgnorePostpaidcheckbox(), "members[" + i + "].ignorePostpaidcheckbox");
					    if (validateResult.hasError()){
					    	validateService.bindErrors(validateResult, errors);
					    }else{
					    	memberDTO.setActualDno(result.getActualDno());
					    	memberDTO.setMnpDno(result.getDno());
					    	memberDTO.setIgnorePostpaidcheckbox(result.isIgnorePostpaidcheckbox());
					    	((MultiSimInfoDTO)arg0).getMembers().get(i).setOpssInd(validateResult.getOnePssInd());
					    	((MultiSimInfoDTO)arg0).getMembers().get(i).setStarterPack(validateResult.getStartPackNumber());
					    }
						
						
						/*MnpDTO result = mnpService.validateMnpMsisdn(mnpNumber.trim());
						
						if (result != null && !"".equals(result.getDno().trim())) {
							if (BomWebPortalConstant.DN_STR_PCCW3G.equals(result.getDno())
									|| BomWebPortalConstant.DN_STR_ERR.equals(result.getDno())) {
								errors.rejectValue("members[" + i + "].mnpNumber", "invalid.msisdn", "Futher Mnp invalid.msisdn1");
							} else if (BomWebPortalConstant.DN_STR_PCCW2G.equals(result.getDno()) && mnpService.isPccw3gPrepaidNumber(mnpNumber)){
								errors.rejectValue("members[" + i + "].mnpNumber", "dummy", "This is a PCCW 3G prepaid number, please use POSBOM to issue order.");
							} else if (BomWebPortalConstant.DN_STR_PCCW2G.equals(result.getDno()) ){
								errors.rejectValue("members[" + i + "].mnpNumber", "dummy", "DNO = MP(HKT 2G number), should handle as New Mobile Number instead of MNP.");							
							} else {
								memberDTO.setMnpDno(result.getDno());
							}						
						} else {
							errors.rejectValue("members[" + i + "].mnpNumber", "invalid.msisdn", "Futher Mnp invalid.msisdn3" );
						}*/
					}
				}
				
				if (mnpDTO != null) {
					if ("N".equals(mnpDTO.getMnp())) {
						if ((mnpDTO.getNewMsisdn().equals(memberDTO.getMnpNumber())) 
							|| (mnpDTO.isFutherMnp() && mnpDTO.getFutherMnpNumber().equals(memberDTO.getMnpNumber()))) {
							errors.rejectValue("members[" + i + "].mnpNumber", "dummy", "MNP MRT number should not be same as primary order.");
						}
					} else if (mnpDTO.getMnpMsisdn().equals(memberDTO.getMnpNumber())) {
						errors.rejectValue("members[" + i + "].mnpNumber", "dummy", "MNP MRT number number should not be same as primary order.");
					}
				}
				for (MultiSimInfoMemberDTO tempMember: multiSimInfoDTO.getMembers()) {
					if (!tempMember.getMemberNum().equals(memberDTO.getMemberNum())) {
						if (("A".equals(tempMember.getMnpInd()) && memberDTO.getMnpNumber().equals(tempMember.getMnpNumber())) 
								|| memberDTO.getMnpNumber().equals(tempMember.getMsisdn())) {
							errors.rejectValue("members[" + i + "].mnpNumber", "dummy", "Duplicated MRT number found.");
						}
					} else if (memberDTO.getMnpNumber().equals(memberDTO.getMsisdn())) {
						errors.rejectValue("members[" + i + "].mnpNumber", "dummy", "Duplicated MRT number found.");
					}
				}
				
				//mnpTicketNum, mnpCutOverDate, mnpCutOverTime
				
				if (!Utility.isValidDate(memberDTO.getMnpCutOverDate())) {
					errors.rejectValue("members[" + i + "].mnpCutOverDate", "invalid.Date", "Please input a valid date.");
				} else {
					List<String> results = new ArrayList<String>();
					try {
						 results = orderService.getFrozenWindow(memberDTO.getMnpCutOverDate());
					} catch (Exception e) {
						logger.error("getFrozenWindow:", e);
					}
					
					if (results != null) {
						for (String s : results) {
							if (s.equals(memberDTO.getMnpCutOverTime())) {
								errors.rejectValue("members[" + i + "].mnpCutOverTime", "invalid.cutOverTime", "This Cut Over Time is frozen");
							}
						}
					}
					Calendar futherMnpDate= Calendar.getInstance();
					futherMnpDate.setTime(Utility.string2Date( memberDTO.getMnpCutOverDate()));
					
					//dennis enable mnp
					if (enableMNP){
						if(!"".equals(memberDTO.getMnpTicketNum())){
							if(memberDTO.getMnpTicketNum()!=null && memberDTO.getMnpTicketNum().length()!=10){
								errors.rejectValue("members[" + i + "].mnpTicketNum", "mnpTicketNum.invalid");
							}else{
								String mmdd = Utility.date2String(futherMnpDate.getTime(), "MMdd");
								if(!mmdd.equals(memberDTO.getMnpTicketNum().substring(0,4))){
									errors.rejectValue("members[" + i + "].mnpTicketNum", "mnpTicketNum.invalid");
								}else if(memberDTO.getMnpTicketNum().charAt(4) != memberDTO.getMnpCutOverTime().charAt(0)){
									errors.rejectValue("members[" + i + "].mnpTicketNum", "mnpTicketNum.invalid");
								}else if(!Utility.isDigit(memberDTO.getMnpTicketNum().substring(5))){						
									errors.rejectValue("members[" + i + "].mnpTicketNum", "mnpTicketNum.invalid");
								}
							}
						}
					}
					
					if (channelId == 2 && orderDto != null) {
						//CCS
						Date srDate = orderDto.getSrvReqDate();
						if(srDate != null && StringUtils.isNotEmpty(memberDTO.getMnpCutOverDate())){
						    
						    try {
						        Calendar serviceReqDate= Calendar.getInstance();
						        serviceReqDate.setTime(srDate);

						            Calendar serviceReqDatePlusN= Calendar.getInstance();
						            serviceReqDatePlusN.setTime(srDate);
						            serviceReqDatePlusN.add(Calendar.HOUR, BomWebPortalConstant.CCS_NEWMRTMNP_CUTOVER_SRD_EXTEND_DAYS*24);//DENNIS201606
						            
						            
						            
						            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
						            logger.info("serviceReqDate:"+sdf.format(serviceReqDate.getTime()));
						            logger.info("futherMnpDate:"+sdf.format(futherMnpDate.getTime()));
						            logger.info("serviceReqDatePlusN:"+sdf.format(serviceReqDatePlusN.getTime()));
						            
						            long diffHours = (futherMnpDate.getTimeInMillis() - serviceReqDate.getTimeInMillis()) / (60 * 60 * 1000);
						            long diff2inHour = (serviceReqDatePlusN.getTimeInMillis() - futherMnpDate.getTimeInMillis()) / (60 * 60 * 1000);
									String opssCases[]={"TGTG","T3TG","NPTG","M3M3","MPM3"};//dno rno
						            if (StringUtils.isNotEmpty(memberDTO.getOpssInd()) && "Y".equals(memberDTO.getOpssInd())  && ArrayUtils.contains(opssCases, mnpDTO.getDno() + mnpDTO.getRno())){
						            	if (diffHours<0)
						            		  errors.rejectValue("members[" + i + "].mnpCutOverDate",
								                        "",
								                        "Futher Mnp Cutover date should be same as or after Service request days for OPSS ");
						            	}
						            else{
						            	if ("MUPS05".equalsIgnoreCase(memberDTO.getMemberOrderType())) {
						            		if (diffHours < 0 ) {
						            			errors.rejectValue("members[" + i + "].mnpCutOverDate", "", "Sub service request date should on or after Service request day");
						            		}
						            	} else {
						            		if (diffHours < 72) {
						            			errors.rejectValue("members[" + i + "].mnpCutOverDate", "", "Futher Mnp Cutover date should after Service request + 3 days");
								            } else if (diff2inHour < 0) {
								            	errors.rejectValue("members[" + i + "].mnpCutOverDate", "", "Futher Mnp Cutover date should in Service request + "+BomWebPortalConstant.CCS_NEWMRTMNP_CUTOVER_SRD_EXTEND_DAYS+" days");
									        }
						            	}
						            }
						    } catch (Exception e) {
						        errors.rejectValue("members[" + i + "].mnpCutOverDate",
						                "futherCutoverDate.invalid.exception",	"e.getMessage()" + e.getMessage());
						        logger.info("Exception: " + e.getMessage());
						    }
						}
					} else if (mnpDTO != null) {
						//For Direct Sales and Retail Mode
						String srDate = mnpDTO.getCutoverDateStr();
						if ("N".equals(mnpDTO.getMnp())) {
							srDate = mnpDTO.getServiceReqDateStr();
						}
						if(StringUtils.isNotEmpty(srDate) && StringUtils.isNotEmpty(memberDTO.getMnpCutOverDate())){
							
							try {
								Calendar serviceReqDate= Calendar.getInstance();
								serviceReqDate.setTime(Utility.string2Date(srDate));
														
								Calendar serviceReqDatePlus90= Calendar.getInstance();
								serviceReqDatePlus90.setTime(Utility.string2Date( srDate ) );
								
								//DENNIS201606
								int mupCutoverSrdExtendDays = BomWebPortalConstant.RS_NEWMRTMNP_CUTOVER_SRD_EXTEND_DAYS;
								if (channelId == 10 || channelId == 11) {
									mupCutoverSrdExtendDays = BomWebPortalConstant.DS_NEWMRTMNP_CUTOVER_SRD_EXTEND_DAYS;
								}
								
								serviceReqDatePlus90.add(Calendar.HOUR, mupCutoverSrdExtendDays*24);//add 90 days
								
								SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
								logger.info("serviceReqDate:"+sdf.format(serviceReqDate.getTime()));
								logger.info("futherMnpDate:"+sdf.format(futherMnpDate.getTime()));
								logger.info("serviceReqDatePlus90:"+sdf.format(serviceReqDatePlus90.getTime()));
								
								long diffHours = (futherMnpDate.getTimeInMillis() - serviceReqDate.getTimeInMillis()) / (60 * 60 * 1000);
								long diff2inHour = (serviceReqDatePlus90.getTimeInMillis() - futherMnpDate.getTimeInMillis()) / (60 * 60 * 1000);
								String opssCases[]={"TGTG","T3TG","NPTG","M3M3","MPM3"};//dno rno
							   if (StringUtils.isNotEmpty(memberDTO.getOpssInd()) && "Y".equals(memberDTO.getOpssInd()) && ArrayUtils.contains(opssCases, mnpDTO.getDno() + mnpDTO.getRno())){
								   if (diffHours<0)
				            		  errors.rejectValue("members[" + i + "].mnpCutOverDate",
						                        "",
						                        "Futher Mnp Cutover date should be same as or after Service request days for OPSS ");
				            	}else{
				            		if ("MUPS05".equalsIgnoreCase(memberDTO.getMemberOrderType())) {
					            		if (diffHours < 0 ) {
					            			errors.rejectValue("members[" + i + "].mnpCutOverDate", "", "Sub service request date should on or after Service request day");
					            		}
					            	} else {
					            		if (diffHours < 72) {
					            			errors.rejectValue("members[" + i + "].mnpCutOverDate", "futherCutoverDate.invalid", "Futher Mnp Cutover date should after Service request + 3 days");
										} else if (diff2inHour < 0) {
											errors.rejectValue("members[" + i + "].mnpCutOverDate", "futherCutoverDate.invalid2", "Futher Mnp Cutover date should in Service request + "+mupCutoverSrdExtendDays+" days");
										}
					            	}
				            	}
								//dennis enable mnp
								if (enableMNP){
									if (channelId == 10 || channelId == 11) {
										Calendar currentDate = Calendar.getInstance();
										Calendar tPlus30Date = Calendar.getInstance();
										tPlus30Date.set(currentDate.get(Calendar.YEAR),
												currentDate.get(Calendar.MONTH),
												currentDate.get(Calendar.DATE));
										tPlus30Date.add(Calendar.DATE, 30);
										if (futherMnpDate.after(tPlus30Date)) {
											logger.info("futhercutover date after T + 30");
											if (memberDTO.getMnpTicketNum().length()!=0 && memberDTO.getMnpTicketNum() != null)
												errors.rejectValue("members[" + i + "].mnpTicketNum", "mnpTicketNum.isblank");
										}
									}			
								}
								
							} catch (Exception e) {
								errors.rejectValue("members[" + i + "].mnpCutOverDate",
										"futherCutoverDate.invalid.exception",	"e.getMessage()" + e.getMessage());
								logger.info("Exception: " + e.getMessage());
							}
						}else if (channelId != 10 && channelId != 11){
							errors.rejectValue("members[" + i + "].mnpCutOverDate",
									"futherCutoverDate.invalid.exception",	"futherCutoverDate and serviceReqDate must input");
						}
					} 
				}
				//mnpCustName
				//mnpDocNo
				ValidationUtils.rejectIfEmptyOrWhitespace(errors, "members[" + i + "].mnpCustName", "custName.required");

				if (memberDTO.getMnpCustName().equals("Prepaid Sim") && memberDTO.getMnpDocNo().trim().length() == 0) {
					errors.rejectValue("members[" + i + "].mnpDocNo", "dummy", "Please input the prepaid SIM number.");
				} else if (memberDTO.getMnpCustName().equals("PREPAID SIM")) {
					errors.rejectValue("members[" + i + "].mnpCustName", "dummy", "Please select Prepaid SIM checkbox.");
				} else {
					ValidationUtils.rejectIfEmptyOrWhitespace(errors, "members[" + i + "].mnpDocNo", "custIdDocNum.required");
				}

				if (memberDTO.getMnpCustName().indexOf(',') != -1) {
					errors.rejectValue("members[" + i + "].mnpCustName", "custName.noComma");
				}
			}
		}
			
			if (memberDTO.getMemberOrderType().equals("MUPS05")) {
					//Check mnpNumber
					if (!Utility.validatePhoneNum(memberDTO.getMnpNumber().trim())){
						errors.rejectValue("members[" + i + "].mnpNumber", "serviceNum.required");
					} else {
						String mnpNumber = memberDTO.getMnpNumber().trim();
						if (!"".equals(mnpNumber)) {
							if (!Utility.validateHKMobilePrefix(mnpNumber)){
								errors.rejectValue("members[" + i + "].mnpNumber", "custInfoMRTPrefix.invalid", "custInfoMRTPrefix.invalid");
							}
							
							
							//DENNIS MIP3
							MnpDTO result = new MnpDTO();
							result.setMnpMsisdn(memberDTO.getMnpNumber());
							result.setPrepaidSimInd(true);
							result.setCustIdDocNum(memberDTO.getMnpDocNo());
							
							com.bomwebportal.mob.validate.dto.ResultDTO validateResult = validateService.validateMNP(result, "members[" + i + "].mnpNumber", memberDTO.isIgnorePostpaidcheckbox(), "members[" + i + "].ignorePostpaidcheckbox");
						    if (validateResult.hasError()){
						    	validateService.bindErrors(validateResult, errors);
						    }else{
						    	memberDTO.setActualDno(result.getActualDno());
						    	memberDTO.setMnpDno(result.getDno());
						    	memberDTO.setIgnorePostpaidcheckbox(result.isIgnorePostpaidcheckbox());
						    	((MultiSimInfoDTO)arg0).getMembers().get(i).setOpssInd(validateResult.getOnePssInd());
						    	((MultiSimInfoDTO)arg0).getMembers().get(i).setStarterPack(validateResult.getStartPackNumber());
						    }
							
							
							/*MnpDTO result = mnpService.validateMnpMsisdn(mnpNumber.trim());
							
							if (result != null && !"".equals(result.getDno().trim())) {
								if (BomWebPortalConstant.DN_STR_PCCW3G.equals(result.getDno())
										|| BomWebPortalConstant.DN_STR_ERR.equals(result.getDno())) {
									errors.rejectValue("members[" + i + "].mnpNumber", "invalid.msisdn", "Futher Mnp invalid.msisdn1");
								} else if (BomWebPortalConstant.DN_STR_PCCW2G.equals(result.getDno()) && mnpService.isPccw3gPrepaidNumber(mnpNumber)){
									errors.rejectValue("members[" + i + "].mnpNumber", "dummy", "This is a PCCW 3G prepaid number, please use POSBOM to issue order.");
								} else if (BomWebPortalConstant.DN_STR_PCCW2G.equals(result.getDno()) ){
									errors.rejectValue("members[" + i + "].mnpNumber", "dummy", "DNO = MP(HKT 2G number), should handle as New Mobile Number instead of MNP.");							
								} else {
									memberDTO.setMnpDno(result.getDno());
								}						
							} else {
								errors.rejectValue("members[" + i + "].mnpNumber", "invalid.msisdn", "Futher Mnp invalid.msisdn3" );
							}*/
						}
					}
					
					if (mnpDTO != null) {
						if ("N".equals(mnpDTO.getMnp())) {
							if ((mnpDTO.getNewMsisdn().equals(memberDTO.getMnpNumber())) 
								|| (mnpDTO.isFutherMnp() && mnpDTO.getFutherMnpNumber().equals(memberDTO.getMnpNumber()))) {
								errors.rejectValue("members[" + i + "].mnpNumber", "dummy", "MNP MRT number should not be same as primary order.");
							}
						} else if (mnpDTO.getMnpMsisdn().equals(memberDTO.getMnpNumber())) {
							errors.rejectValue("members[" + i + "].mnpNumber", "dummy", "MNP MRT number number should not be same as primary order.");
						}
					}
					for (MultiSimInfoMemberDTO tempMember: multiSimInfoDTO.getMembers()) {
						if (!"MUPS05".equalsIgnoreCase(memberDTO.getMemberOrderType()))	{
							if (!tempMember.getMemberNum().equals(memberDTO.getMemberNum())) {
								if (memberDTO.getMnpNumber().equals(tempMember.getMnpNumber())
										|| memberDTO.getMnpNumber().equals(tempMember.getMsisdn())) {
									errors.rejectValue("members[" + i + "].mnpNumber", "dummy", "Duplicated MRT number found.");
								}
							} else if (memberDTO.getMnpNumber().equals(memberDTO.getMsisdn())) {
								errors.rejectValue("members[" + i + "].mnpNumber", "dummy", "Duplicated MRT number found.");
							}
						}
					}
					
					if (channelId != 2) {
						/////////////////////////////////////////////////////////////////////////////////			
						//Check SIM Number
						
						//TODO: Check if order is recalled.  Skip checking process if SIM Number is same as original order.
						boolean sameICCID = false;
						if (memberDTO.getOriginalSimICCID() != null && memberDTO.getSim().getIccid().equals(memberDTO.getOriginalSimICCID())) {
							sameICCID = true;
						} 
						if (orderDto == null 
								|| orderDto.getOcid() == null || orderDto.getOcid().trim().length() == 0 
								|| (!sameICCID && (user.getChannelId() == 10 || user.getChannelId()==11))) {
							if (StringUtils.isNotEmpty(memberDTO.getSim().getIccid()) && memberDTO.getSim().getIccid().length() == 19) {
								MobileSimInfoDTO inMobileSimInfoDto = new MobileSimInfoDTO();
								inMobileSimInfoDto.setIccid(memberDTO.getSim().getIccid());
								MobileSimInfoDTO resultMobileSimInfoDto = simService.validateSim(inMobileSimInfoDto);
								if (resultMobileSimInfoDto != null
										&& memberDTO.getSim().getIccid().equals(
												resultMobileSimInfoDto.getIccid())) {
									memberDTO.getSim().setImsi(resultMobileSimInfoDto.getImsi());
									memberDTO.getSim().setItemCode(resultMobileSimInfoDto.getItemCd());
									memberDTO.getSim().setPuk1(resultMobileSimInfoDto.getPuk1());
									memberDTO.getSim().setPuk2(resultMobileSimInfoDto.getPuk2());
									//memberDTO.getSim().setSimType(Utility.checkSimType(memberDTO.getSim().getIccid()));
									String simItemCd = resultMobileSimInfoDto.getItemCd();
									
									
									boolean existFlag = false;
									String selectedSIMItemId = "";
									for (VasDetailDTO vas : multiSimInfoDTO.getSimItemList()) {
										if (vas.getPosItemCd().equals(simItemCd)) {
											existFlag = true;
											selectedSIMItemId = vas.getItemId();
											break;
										}
									}
									memberDTO.setSelectedSimItemId(selectedSIMItemId);
									
									String pendingOrderId = "";
									if (user.getChannelId() == 10 || user.getChannelId() == 11) {
										if (orderDto != null && StringUtils.isNotBlank(orderDto.getOrderId())) {
											pendingOrderId = simService.getPendingOrderExistWithIccidOrderId(
													memberDTO.getSim().getIccid(), orderDto.getOrderId());
										} else {
											pendingOrderId = simService
													.getPendingOrderExistWithIccid(memberDTO.getSim().getIccid());
										}
									}
									
									if (!existFlag) {
										errors.rejectValue("members[" + i + "].sim.iccid", "invalid.itemCdAndBoMWebItemCd");
									} else if (resultMobileSimInfoDto.getHwInvStatus() != BomWebPortalConstant.HWINV_VALID_STATUS) {							
										errors.rejectValue("members[" + i + "].sim.iccid", "", "SIM is invalid in HW Inventory.");	
									} else if ((channelId == 10 || channelId == 11) && !resultMobileSimInfoDto.getShopCd().equals("P"+mnpDTO.getShopCd())) {
										errors.rejectValue("members[" + i + "].sim.iccid", "shopCd.notMatch");
									} else if (channelId != 10 && channelId != 11 &&!resultMobileSimInfoDto.getShopCd().equals("P"+user.getBomShopCd())) {
										errors.rejectValue("members[" + i + "].sim.iccid", "shopCd.notMatch");
									} else if (StringUtils.isNotBlank(pendingOrderId)){
										errors.rejectValue("members[" + i + "].sim.iccid", "", "Pending SB order exists with this input SIM ICCID, order ID =" + pendingOrderId);
									}
									
									ResultDTO result = validateService.validateSimTypeByIccid(memberDTO.getSim().getIccid(), sessionCustomer.getSimType(), "members[" + i + "].sim.iccid");				
									validateService.bindErrors(result, errors);	
									if (!result.hasError()){
										memberDTO.getSim().setSimType(sessionCustomer.getSimType());
									}
									
									
								} else {
									errors.rejectValue("members[" + i + "].sim.iccid", "", "SIM is not found in HW Inventory.");	
								}
								for (MultiSimInfoMemberDTO tempMember: multiSimInfoDTO.getMembers()) {
									if (!tempMember.getMemberNum().equals(memberDTO.getMemberNum())) {
										if (memberDTO.getSim().getIccid().equals(tempMember.getSim().getIccid())) {
											errors.rejectValue("members[" + i + "].sim.iccid", "dummy", "Duplicated SIM number found.");
										}
									}
								}
							} else {
								errors.rejectValue("members[" + i + "].sim.iccid", "", "SIM Number is invalid or empty.");
							}
						}
					} else {
						/////////////////////////////////////////////////////////////////////////////////			
						//Check SIM Type
						String simItemId = memberDTO.getSelectedSimItemId();
						boolean existFlag = false;
						for (VasDetailDTO vas : multiSimInfoDTO.getSimItemList()) {
							if (vas.getItemId().equals(simItemId)) {
								existFlag = true;
								memberDTO.getSim().setItemCode(vas.getPosItemCd());
								break;
							}
						}
						if (!existFlag) {
							errors.rejectValue("members[" + i + "].selectedSimItemId", "invalid.itemCdAndBoMWebItemCd");
						} 
						
						
						//Dennis MIP3 
						//check simType with selectedSimType
						
						String simType = vasDetailService.getMipSimType(simItemId);
						com.bomwebportal.mob.validate.dto.ResultDTO result = validateService.validateSimType(simType, sessionCustomer.getSimType(), "members[" + i + "].selectedSimItemId");
						validateService.bindErrors(result, errors);	
						if (!result.hasError()){
							memberDTO.getSim().setSimType(sessionCustomer.getSimType());
						}
					}
					
					//mnpTicketNum, mnpCutOverDate, mnpCutOverTime
					
					if (!Utility.isValidDate(memberDTO.getMnpCutOverDate())) {
						errors.rejectValue("members[" + i + "].mnpCutOverDate", "invalid.Date", "Please input a valid date.");
					} else {
						List<String> results = new ArrayList<String>();
						try {
							 results = orderService.getFrozenWindow(memberDTO.getMnpCutOverDate());
						} catch (Exception e) {
							logger.error("getFrozenWindow:", e);
						}
						
						if (results != null) {
							for (String s : results) {
								if (s.equals(memberDTO.getMnpCutOverTime())) {
									errors.rejectValue("members[" + i + "].mnpCutOverTime", "invalid.cutOverTime", "This Cut Over Time is frozen");
								}
							}
						}
						Calendar futherMnpDate= Calendar.getInstance();
						futherMnpDate.setTime(Utility.string2Date( memberDTO.getMnpCutOverDate()));
						
						//dennis enable mnp
						if (enableMNP){
							if(!"".equals(memberDTO.getMnpTicketNum())){
								if(memberDTO.getMnpTicketNum()!=null && memberDTO.getMnpTicketNum().length()!=10){
									errors.rejectValue("members[" + i + "].mnpTicketNum", "mnpTicketNum.invalid");
								}else{
									String mmdd = Utility.date2String(futherMnpDate.getTime(), "MMdd");
									if(!mmdd.equals(memberDTO.getMnpTicketNum().substring(0,4))){
										errors.rejectValue("members[" + i + "].mnpTicketNum", "mnpTicketNum.invalid");
									}else if(memberDTO.getMnpTicketNum().charAt(4) != memberDTO.getMnpCutOverTime().charAt(0)){
										errors.rejectValue("members[" + i + "].mnpTicketNum", "mnpTicketNum.invalid");
									}else if(!Utility.isDigit(memberDTO.getMnpTicketNum().substring(5))){						
										errors.rejectValue("members[" + i + "].mnpTicketNum", "mnpTicketNum.invalid");
									}
								}
							}
						}
						
						if (channelId == 2 && orderDto != null) {
							//CCS
							Date srDate = orderDto.getSrvReqDate();
							if(srDate != null && StringUtils.isNotEmpty(memberDTO.getMnpCutOverDate())){
							    
							    try {
							        Calendar serviceReqDate= Calendar.getInstance();
							        serviceReqDate.setTime(srDate);

							            Calendar serviceReqDatePlusN= Calendar.getInstance();
							            serviceReqDatePlusN.setTime(srDate);
							            serviceReqDatePlusN.add(Calendar.HOUR, BomWebPortalConstant.CCS_MNP_APP_EXTEND_DAYS*24);//DENNIS201606
							            
							            
							            
							            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
							            logger.info("serviceReqDate:"+sdf.format(serviceReqDate.getTime()));
							            logger.info("futherMnpDate:"+sdf.format(futherMnpDate.getTime()));
							            logger.info("serviceReqDatePlusN:"+sdf.format(serviceReqDatePlusN.getTime()));
							            
							            long diffHours = (futherMnpDate.getTimeInMillis() - serviceReqDate.getTimeInMillis()) / (60 * 60 * 1000);
							            long diff2inHour = (serviceReqDatePlusN.getTimeInMillis() - futherMnpDate.getTimeInMillis()) / (60 * 60 * 1000);
							            
							            if (StringUtils.isNotEmpty(memberDTO.getOpssInd()) && "Y".equals(memberDTO.getOpssInd())  && mnpDTO.getDno().equals(mnpDTO.getRno())){
							            	if (diffHours<0)
							            		  errors.rejectValue("members[" + i + "].mnpCutOverDate",
									                        "",
									                        "Direct MNP Cutover date should be same as or after Service request days for OPSS ");
							            	}
							            else{
							            	if ("MUPS05".equalsIgnoreCase(memberDTO.getMemberOrderType())) {
							            		if (diffHours < 0 ) {
							            			errors.rejectValue("members[" + i + "].mnpCutOverDate", "", "Sub service request date should on or after Service request day");
							            		} else if (diff2inHour < 0) {
								            		errors.rejectValue("members[" + i + "].mnpCutOverDate", "", "Direct MNP Cutover date should in Service request + "+BomWebPortalConstant.CCS_MNP_APP_EXTEND_DAYS+" days");
									            }
							            	}
							            }
							    } catch (Exception e) {
							        errors.rejectValue("members[" + i + "].mnpCutOverDate",
							                "futherCutoverDate.invalid.exception",	"e.getMessage()" + e.getMessage());
							        logger.info("Exception: " + e.getMessage());
							    }
							}
						} else if (mnpDTO != null) {
							//For Direct Sales and Retail Mode
							String srDate = mnpDTO.getCutoverDateStr();
							if ("N".equals(mnpDTO.getMnp())) {
								srDate = mnpDTO.getServiceReqDateStr();
							}
							if(StringUtils.isNotEmpty(srDate) && StringUtils.isNotEmpty(memberDTO.getMnpCutOverDate())){
								
								try {
									Calendar serviceReqDate= Calendar.getInstance();
									serviceReqDate.setTime(Utility.string2Date(srDate));
															
									Calendar serviceReqDatePlus90= Calendar.getInstance();
									serviceReqDatePlus90.setTime(Utility.string2Date( srDate ) );
									
									//DENNIS201606
									int mupCutoverSrdExtendDays = BomWebPortalConstant.RS_MNP_APP_EXTEND_DAYS;
									if (channelId == 10 || channelId == 11) {
										mupCutoverSrdExtendDays = BomWebPortalConstant.DS_MNP_APP_EXTEND_DAYS;
									}
									
									serviceReqDatePlus90.add(Calendar.HOUR, mupCutoverSrdExtendDays*24);//add 90 days
									
									SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
									logger.info("serviceReqDate:"+sdf.format(serviceReqDate.getTime()));
									logger.info("futherMnpDate:"+sdf.format(futherMnpDate.getTime()));
									logger.info("serviceReqDatePlus90:"+sdf.format(serviceReqDatePlus90.getTime()));
									
									long diffHours = (futherMnpDate.getTimeInMillis() - serviceReqDate.getTimeInMillis()) / (60 * 60 * 1000);
									long diff2inHour = (serviceReqDatePlus90.getTimeInMillis() - futherMnpDate.getTimeInMillis()) / (60 * 60 * 1000);
								   if (StringUtils.isNotEmpty(memberDTO.getOpssInd()) && "Y".equals(memberDTO.getOpssInd()) && mnpDTO.getDno().equals(mnpDTO.getRno())){
									   if (diffHours<0)
					            		  errors.rejectValue("members[" + i + "].mnpCutOverDate",
							                        "",
							                        "Direct MNP Cutover date should be same as or after Service request days for OPSS ");
					            	}else{
					            		if ("MUPS05".equalsIgnoreCase(memberDTO.getMemberOrderType())) {
						            		if (diffHours < 0 ) {
						            			errors.rejectValue("members[" + i + "].mnpCutOverDate", "", "Sub service request date should on or after Service request day");
						            		} else if (diff2inHour < 0) {
							            		errors.rejectValue("members[" + i + "].mnpCutOverDate", "", "Direct MNP Cutover date should in Service request + "+BomWebPortalConstant.CCS_MNP_APP_EXTEND_DAYS+" days");
								            }
						            	}
					            	}
									//dennis enable mnp
									if (enableMNP){
										if (channelId == 10 || channelId == 11) {
											Calendar currentDate = Calendar.getInstance();
											Calendar tPlus30Date = Calendar.getInstance();
											tPlus30Date.set(currentDate.get(Calendar.YEAR),
													currentDate.get(Calendar.MONTH),
													currentDate.get(Calendar.DATE));
											tPlus30Date.add(Calendar.DATE, 30);
											if (futherMnpDate.after(tPlus30Date)) {
												logger.info("futhercutover date after T + 30");
												if (memberDTO.getMnpTicketNum().length()!=0 && memberDTO.getMnpTicketNum() != null)
													errors.rejectValue("members[" + i + "].mnpTicketNum", "mnpTicketNum.isblank");
											}
										}			
									}
									
								} catch (Exception e) {
									errors.rejectValue("members[" + i + "].mnpCutOverDate",
											"futherCutoverDate.invalid.exception",	"e.getMessage()" + e.getMessage());
									logger.info("Exception: " + e.getMessage());
								}
							}else if (channelId != 10 && channelId != 11){
								errors.rejectValue("members[" + i + "].mnpCutOverDate",
										"futherCutoverDate.invalid.exception",	"futherCutoverDate and serviceReqDate must input");
							}
						} 
					}
					//mnpCustName
					//mnpDocNo
					ValidationUtils.rejectIfEmptyOrWhitespace(errors, "members[" + i + "].mnpCustName", "custName.required");

					if (memberDTO.getMnpCustName().equals("Prepaid Sim") && memberDTO.getMnpDocNo().trim().length() == 0) {
						errors.rejectValue("members[" + i + "].mnpDocNo", "dummy", "Please input the prepaid SIM number.");
					} else if (memberDTO.getMnpCustName().equals("PREPAID SIM")) {
						errors.rejectValue("members[" + i + "].mnpCustName", "dummy", "Please select Prepaid SIM checkbox.");
					} else {
						ValidationUtils.rejectIfEmptyOrWhitespace(errors, "members[" + i + "].mnpDocNo", "custIdDocNum.required");
					}

					if (memberDTO.getMnpCustName().indexOf(',') != -1) {
						errors.rejectValue("members[" + i + "].mnpCustName", "custName.noComma");
					}
				}

			//Check VAS Items
			String[] vasList = null;
			if (memberDTO.getSelectedVasItemList() != null && memberDTO.getSelectedVasItemList().size() > 0) {
				vasList = memberDTO.getSelectedVasItemList().toArray(new String[0]);
				List<String> overMaxCountItemList = vasDetailService.getOverMaxCountItemList(vasList, multiSimInfoDTO.getBasket().getBasketId());			
				if (overMaxCountItemList != null && overMaxCountItemList.size() > 0) {
					String errorHtml =  "Max. data service count reached, please select less data VAS services. "
							+ "Please consider to remove below optional VAS: <ol>";
					for (String vasName: overMaxCountItemList) {
						errorHtml += "<li>" + vasName + "";
					}
					errorHtml += "</ol>";
					errors.rejectValue("members[" + i + "].selectedVasItemList", "", errorHtml);	
				}
				
				List<ExclusiveItemDTO> exclusiveItemList = null;
				exclusiveItemList = vasDetailService.getExclusiveItemList(memberDTO.getSelectedVasItemList());
				if (exclusiveItemList == null || exclusiveItemList.isEmpty()) {
					if (logger.isDebugEnabled()) {
						logger.debug("exclusiveItemList is null");
					}
				} else {
						errors.rejectValue("members[" + i + "].selectedVasItemList", "",	"VAS Selection Conflict");//print to the VASdetail header
						
						for (ExclusiveItemDTO dto : exclusiveItemList) {
							errors.rejectValue("members[" + i + "].selectedVasItemList", "","");
							errors.rejectValue("members[" + i + "].selectedVasItemList", "","Items cannot be selected together:");
							errors.rejectValue("members[" + i + "].selectedVasItemList", "","&lt;"+ dto.getItemDescriptionA()+ "&gt;");
							errors.rejectValue("members[" + i + "].selectedVasItemList", "","&lt;"+ dto.getItemDescriptionB()+ "&gt;");	
						}
				}
				
			}
			
				Date appDate = new Date();
				//OrderDTO orderDto = (OrderDTO)multiSimInfoDTO.getSession().getAttribute("OrderDto");
				if (orderDto != null) {
					appDate = orderDto.getAppInDate();
				}
				
				int vasListLength = (vasList == null? 0 : vasList.length);
				String[] vasItemIdWithSIM = null;
				if (memberDTO.getSelectedSimItemId() != null && memberDTO.getSelectedSimItemId().length() > 0) {
					
					vasItemIdWithSIM = new String[vasListLength + 1];
					for (int j = 0; j< vasListLength; j++) {
						vasItemIdWithSIM[j] = vasList[j];
					}
					vasItemIdWithSIM[vasListLength] = memberDTO.getSelectedSimItemId();
				} else {
				
					vasItemIdWithSIM = vasList;
				}
				
				
				if (!errors.hasFieldErrors("members[" + i + "].selectedVasItemList")) {
					List<ItemsRelationshipDTO> results = itemsRelationshipService.getItemsRelations(vasItemIdWithSIM, String.valueOf(user.getChannelId()), appDate);
					if (results!=null && results.size() > 0) {
						for (ItemsRelationshipDTO dto : results) {
							errors.rejectValue("members[" + i + "].selectedVasItemList", "", dto.getDescription());
						}
					}
				}
				
				if (!errors.hasFieldErrors("members[" + i + "].selectedVasItemList")) {
					List<PcRelationshipDTO> pcRelationshipList = this.relationshipCheckService.checkRelationshipSb(multiSimInfoDTO.getBasket().getBasketId(), appDate, vasItemIdWithSIM, sessionCustomer.getBrand(), sessionCustomer.getSimType());
					
					if (pcRelationshipList!=null && pcRelationshipList.size() > 0) {
						errors.rejectValue("members[" + i + "].selectedVasItemList", "", "Your selection violates relationship checking in BOM, please select again.");
					}
				}
				
				//Check Member Actual User
				//Actual User Validation
				
				if (StringUtils.isNotEmpty(memberDTO.getSameAsCustInd()) && !memberDTO.isSameAsCust()){
				//check rejectIfEmptyOrWhitespace
				ValidationUtils.rejectIfEmptyOrWhitespace(errors, "members[" + i + "].actualUserDTO.subTitle", "title.required");
				ValidationUtils.rejectIfEmptyOrWhitespace(errors, "members[" + i + "].actualUserDTO.subFirstName", "firstName.required");
				ValidationUtils.rejectIfEmptyOrWhitespace(errors, "members[" + i + "].actualUserDTO.subLastName", "lastName.required");
				ValidationUtils.rejectIfEmptyOrWhitespace(errors, "members[" + i + "].actualUserDTO.subDocType", "idDocType.required");
				ValidationUtils.rejectIfEmptyOrWhitespace(errors, "members[" + i + "].actualUserDTO.subDocNum", "idDocNum.required");
				ValidationUtils.rejectIfEmptyOrWhitespace(errors, "members[" + i + "].actualUserDTO.subContactTel", "contactPhone.required");

						if ("HKID".equals(memberDTO.getActualUserDTO().getSubDocType())){
							ValidationUtils.rejectIfEmptyOrWhitespace(errors, "members[" + i + "].actualUserDTO.subDateOfBirthStr", "dobDay.required");
						} else if ("PASS".equals(memberDTO.getActualUserDTO().getSubDocType())){
								ValidationUtils.rejectIfEmptyOrWhitespace(errors, "members[" + i + "].actualUserDTO.subDateOfBirthStr", "dobDay.required");
						} else if ("BS".equals(memberDTO.getActualUserDTO().getSubDocType())){
							ValidationUtils.rejectIfEmptyOrWhitespace(errors, "members[" + i + "].actualUserDTO.subCompanyName", "companyName.required");
						}
				}
				if (StringUtils.isNotEmpty(memberDTO.getSameAsCustInd()) && !memberDTO.isSameAsCust()){
					// select type checking
					if ("NONE".equals(memberDTO.getActualUserDTO().getSubTitle())) {
						errors.rejectValue("members[" + i + "].actualUserDTO.subTitle", "title.required");
					}

					if (memberDTO.getActualUserDTO().getSubDateOfBirthStr() != null && !"".equals(memberDTO.getActualUserDTO().getSubDateOfBirthStr().trim())) {
						if (!Utility.isValidDate(memberDTO.getActualUserDTO().getSubDateOfBirthStr())) {
							errors.rejectValue("members[" + i + "].actualUserDTO.subDateOfBirthStr", "invalid.Date");
						} else {
							// Check if the applicant is over 18
							Calendar currentDate = Calendar.getInstance();
							Calendar compareDate = Calendar.getInstance();

							compareDate.setTime(Utility.string2Date(memberDTO.getActualUserDTO().getSubDateOfBirthStr()));

								// Check the year range: 100 years
								compareDate.add(Calendar.YEAR, 100);
								logger.info("compareDate: " + compareDate.getTime());
								if (compareDate.before(currentDate)) {
									errors.rejectValue("members[" + i + "].actualUserDTO.subDateOfBirthStr", "invalid.Date");
								}
							
						}
					}

					if (memberDTO.getActualUserDTO().getSubDocNum().trim().length() > 30) {
						errors.rejectValue("members[" + i + "].actualUserDTO.subDocNum", "error.memberDTO.idDocNum.length", null,
								"error.memberDTO.idDocNum.lengthL(default)");
					}

					// Pattern check idDocType
					if (memberDTO.getActualUserDTO().getSubDocType() != null) {
						// Check the HKID pattern
						if ("HKID".equals(memberDTO.getActualUserDTO().getSubDocType()) && !"".equals(memberDTO.getActualUserDTO().getSubDocNum())) {
							if (Utility.validateHKID(memberDTO.getActualUserDTO().getSubDocNum()) == false) {
								errors.rejectValue("members[" + i + "].actualUserDTO.subDocNum", "invalid.hkid");
							} else {
								if (Utility.validateHKIDcheckDigit(memberDTO.getActualUserDTO().getSubDocNum()) == false) {
									errors.rejectValue("members[" + i + "].actualUserDTO.subDocNum", "invalid.hkidCheckDigit");
								}
							}							
						}
						// Check the HKBR pattern
						if ("BS".equals(memberDTO.getActualUserDTO().getSubDocType()) && !"".equals(memberDTO.getActualUserDTO().getSubDocNum())) {
							if (Utility.validateHKBR(memberDTO.getActualUserDTO().getSubDocNum()) == false) {
								errors.rejectValue("members[" + i + "].actualUserDTO.subDocNum", "invalid.hkbr");
							} else {
								if (Utility.validateHKBRcheckDigit(memberDTO.getActualUserDTO().getSubDocNum()) == false) {
									errors.rejectValue("members[" + i + "].actualUserDTO.subDocNum", "invalid.hkbrCheckDigit");
								}
							}							
						}

						if ("PASS".equals(memberDTO.getActualUserDTO().getSubDocType()) && !"".equals(memberDTO.getActualUserDTO().getSubDocNum())) {
							if (Utility.validatePassport(memberDTO.getActualUserDTO().getSubDocNum()) == false) {
								errors.rejectValue("members[" + i + "].actualUserDTO.subDocNum", "invalid.pass",
										"Invalid passport number. Please input again.");
							}
						}
					}
					
					//pattern check contactPhone 
					if(memberDTO.getActualUserDTO().getSubContactTel()!= null &&!"".equals(memberDTO.getActualUserDTO().getSubContactTel())){
						  if (!Utility.validatePhoneNum(memberDTO.getActualUserDTO().getSubContactTel())){
							errors.rejectValue("members[" + i + "].actualUserDTO.subContactTel", "invalid.contactPhone");	
						  }
						//add by wilson 20120314, Fault ID: 0002498
						if(!Utility.validateHKPhonePreFix(memberDTO.getActualUserDTO().getSubContactTel())){
							errors.rejectValue("members[" + i + "].actualUserDTO.subContactTel", "contactPhonePrefix.Invalid");
						}
					}
					
					//if have email address edit by wilson  20110302

						// pattern check e-mail
						if (memberDTO.getActualUserDTO().getSubEmailAddr() != null
								&& !"".equals(memberDTO.getActualUserDTO().getSubEmailAddr())) {
							if (!Utility.validateEmailMobSpecific(memberDTO.getActualUserDTO().getSubEmailAddr())) {//change validation function 20130722
								errors.rejectValue("members[" + i + "].actualUserDTO.subEmailAddr", "invalid.emailAddr");
							}

						}
				}
				
				if ("10".equals(channelId) || "11".equals(channelId) && StringUtils.isNotEmpty(memberDTO.getMsisdn())){
					if (mnpService.checkPendingOrder(memberDTO.getMsisdn(), orderDto.getOrderId()) > 0 || mnpService.checkPendingMUPOrder(memberDTO.getMsisdn(), orderDto.getOrderId()) > 0){
						errors.rejectValue("members[" + i + "].msisdn", "", "Invalid Mobile Number");
					}
				}

			}
		return;
	}
}
