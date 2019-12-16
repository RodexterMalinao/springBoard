package com.bomwebportal.validator;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.Errors;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.bomwebportal.dto.BasketDTO;
import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.dto.CustomerProfileDTO;
import com.bomwebportal.dto.ExclusiveItemDTO;
import com.bomwebportal.dto.ItemFuncAssgnMobDTO;
import com.bomwebportal.dto.ItemsRelationshipDTO;
import com.bomwebportal.dto.MnpDTO;
import com.bomwebportal.dto.MultiSimInfoDTO;
import com.bomwebportal.dto.MultiSimInfoMemberDTO;
import com.bomwebportal.dto.OrderDTO;
import com.bomwebportal.dto.PcRelationshipDTO;
import com.bomwebportal.dto.QuotaPlanInfoUI;
import com.bomwebportal.dto.SuperOrderDTO;
import com.bomwebportal.dto.VasDetailDTO;
import com.bomwebportal.dto.VasMrtSelectionDTO;
import com.bomwebportal.eagle.dto.BlacklistRequest;
import com.bomwebportal.eagle.dto.EagleErrorResponse;
import com.bomwebportal.mob.ccs.dto.CodeLkupDTO;
import com.bomwebportal.mob.ccs.dto.MobSponsorshipDTO;
import com.bomwebportal.mob.ccs.service.CodeLkupService;
import com.bomwebportal.mob.ccs.util.MobCcsSessionUtil;
import com.bomwebportal.mob.ccs.validator.MRTBaseValidator;
import com.bomwebportal.mob.ccs.web.MobCcsAuthorizeController;
import com.bomwebportal.mob.validate.dto.ResultDTO;
import com.bomwebportal.mob.validate.service.ValidateService;
import com.bomwebportal.mobquota.dto.MobQuotaUsageDTO;
import com.bomwebportal.mobquota.dto.QuotaConsumeRequest;
import com.bomwebportal.mobquota.exception.AppServiceException;
import com.bomwebportal.mobquota.service.MobQuotaService;
import com.bomwebportal.service.IGuardService;
import com.bomwebportal.service.ItemFuncMobService;
import com.bomwebportal.service.ItemsRelationshipService;
import com.bomwebportal.service.MnpService;
import com.bomwebportal.service.MobItemQuotaService;
import com.bomwebportal.service.RelationshipCheckService;
import com.bomwebportal.service.VasDetailService;
import com.bomwebportal.util.BomWebPortalConstant;
import com.bomwebportal.util.Utility;
import com.bomwebportal.web.util.GenericReportHelper;
import com.bomwebportal.web.util.ProjectEagleReportHelper;

public class VasDetailValidator extends MRTBaseValidator {

	protected final Log logger = LogFactory.getLog(getClass());

	private VasDetailService vasDetailservice;
	private MnpService service;
	private RelationshipCheckService relationshipCheckService;
	private CodeLkupService codeLkupService;
	private ItemsRelationshipService itemsRelationshipService;
	private IGuardService iGuardService;
	private ItemFuncMobService itemFuncMobService;
	private MobItemQuotaService mobItemQuotaService;
	private MobQuotaService mobQuotaService;
	private ValidateService validateService;
	
	
	public IGuardService getiGuardService() {
		return iGuardService;
	}

	public void setiGuardService(IGuardService iGuardService) {
		this.iGuardService = iGuardService;
	}

	public ItemsRelationshipService getItemsRelationshipService() {
		return itemsRelationshipService;
	}

	public void setItemsRelationshipService(
			ItemsRelationshipService itemsRelationshipService) {
		this.itemsRelationshipService = itemsRelationshipService;
	}

	public MnpService getService() {
		return service;
	}

	public void setService(MnpService service) {
		this.service = service;
	}

	public void setVasDetailservice(VasDetailService vasDetailservice) {
		this.vasDetailservice = vasDetailservice;
	}

	public VasDetailService getVasDetailservice() {
		return vasDetailservice;
	}
	
	public RelationshipCheckService getRelationshipCheckService() {
		return relationshipCheckService;
	}

	public void setRelationshipCheckService(
			RelationshipCheckService relationshipCheckService) {
		this.relationshipCheckService = relationshipCheckService;
	}

	public CodeLkupService getCodeLkupService() {
		return codeLkupService;
	}

	public void setCodeLkupService(CodeLkupService codeLkupService) {
		this.codeLkupService = codeLkupService;
	}

	public ItemFuncMobService getItemFuncMobService() {
		return itemFuncMobService;
	}
	public void setItemFuncMobService(ItemFuncMobService itemFuncMobService) {
		this.itemFuncMobService = itemFuncMobService;
	}
	
	public MobItemQuotaService getMobItemQuotaService() {
		return mobItemQuotaService;
	}

	public void setMobItemQuotaService(MobItemQuotaService mobItemQuotaService) {
		this.mobItemQuotaService = mobItemQuotaService;
	}
	
	public MobQuotaService getMobQuotaService() {
		return mobQuotaService;
	}

	public void setMobQuotaService(MobQuotaService mobQuotaService) {
		this.mobQuotaService = mobQuotaService;
	}

	public ValidateService getValidateService() {
		return validateService;
	}

	public void setValidateService(ValidateService validateService) {
		this.validateService = validateService;
	}

	public boolean supports(Class clazz) {
		return clazz.equals(VasDetailDTO.class);
	}

	public void validate(Object obj, Errors errors) {
		VasDetailDTO vasDetail = (VasDetailDTO) obj;

		if (vasDetail == null) {
			return;
		}
		
		long startTimeTotal = System.currentTimeMillis();

		//try {
			CustomerProfileDTO sessionCustomer = (CustomerProfileDTO) vasDetail.getValue("customer");
			BasketDTO sessionBasket = (BasketDTO) vasDetail.getValue("basket");
			MnpDTO sessionMnp = (MnpDTO) vasDetail.getValue("MNP");
			BomSalesUserDTO user = (BomSalesUserDTO) vasDetail.getValue("user"); //add by wilson 20121203
			HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
			//List<MultiSimInfoDTO> multiSimInfos = (List<MultiSimInfoDTO>) vasDetail.getValue("MultiSimInfoList"); //add by nancy 20140120
		
			Boolean checkIsWhiteList = sessionCustomer.getCheckIsWhiteList();
			String customerTierId = sessionBasket.getCustomerTierId();
			
			if (checkIsWhiteList != null && customerTierId != null){
				if (!checkIsWhiteList && "62".equals(customerTierId)){
					errors.rejectValue("itemHtml", "dummy", "Please select another basket!" );
				}
			}
			
			Map<String,List<ItemFuncAssgnMobDTO>> itemFuncInfos = (HashMap<String,List<ItemFuncAssgnMobDTO>>)vasDetail.getValue("itemFuncInfos");
			MobSponsorshipDTO mobSponsorshipDTO = (MobSponsorshipDTO)vasDetail.getValue("mobSponsorshipDTO");
			
			List<MultiSimInfoDTO> multiSimInfos = (List<MultiSimInfoDTO>) vasDetail.getValue("MultiSimInfoList"); //add by nancy 20140120
			
			Map<String,QuotaPlanInfoUI> quotaPlanInfoUi= (HashMap <String,QuotaPlanInfoUI>)request.getSession().getAttribute("quotaPlanInfoMapSession");
			List<VasDetailDTO> vasHSRPList = (List<VasDetailDTO>) MobCcsSessionUtil.getSession(request,"vasHSRPList");
			
			Date appDate = new Date();
			OrderDTO orderDto = (OrderDTO)vasDetail.getValue("orderDto");
			
			if (orderDto != null) {
				appDate = orderDto.getAppInDate();
			}
			
			String appDateStr = Utility.date2String(appDate, "dd/MM/yyyy");
			
			
			//validate basket sim type and brand type FIRST

			com.bomwebportal.dto.ResultDTO spResult = new com.bomwebportal.dto.ResultDTO();
			spResult= vasDetailservice.basketValidate(sessionBasket.getBasketId(), appDate);
			 if (!spResult.getReturnBool()){
				 errors.rejectValue("itemHtml", "dummy",
						 spResult.getReturnMessage());
				 return;
			 }
			
			
			long startTime = System.currentTimeMillis();
			String checkBasketResult = vasDetailservice.checkBasketVas(sessionBasket.getBasketId(), appDateStr, vasDetail.getVasitem());
			if (checkBasketResult != null) {
				errors.rejectValue("itemHtml", "dummy", checkBasketResult);
			}
			long endTime   = System.currentTimeMillis();
			logger.info("totalTime call VasDetailValidator in vasDetailservice.checkBasketVas(): "+((endTime - startTime))+"ms");
			
			if ("5".equals(sessionCustomer.getAddrProofInd())) {
				if (("1".equals(sessionBasket.getBasketTypeId()) || "4".equals(sessionBasket.getBasketTypeId())) 
						&& "0".equals(sessionBasket.getUpfrontAmt())) {
					errors.rejectValue("itemHtml", "dummy",
							"Not allow to select $0 upfront HS basket for Pre-activation.");
				}
			}
			
			//Check custoemr tier for student plan sub
			//System.out.println("CustomerTier:"+sessionBasket.getCustomerTierId());
			ResultDTO validateCustomerTier = validateService.validateStudentPlanBasketCustomerTier(sessionCustomer.isStudentPlanSubInd(), sessionBasket.getCustomerTierId(), "itemHtml");
			validateService.bindErrors(validateCustomerTier, errors);
			
			
			// for checking sponsorship ...
			if (itemFuncInfos != null) {
				for (List<ItemFuncAssgnMobDTO> funcList : itemFuncInfos.values()) {
					for (ItemFuncAssgnMobDTO func : funcList) {
						if ("EX01".equals(func.getFuncId())) {
							if (mobSponsorshipDTO == null || mobSponsorshipDTO.isDirty()) {
								errors.rejectValue("itemHtml", "dummy",
										"Information of Staff Sponsorship is required.");
							}
						}
					}
				}
			}
			
			startTime = System.currentTimeMillis();
			/***************************QuotaPlanInfo**************************/
			//check quotaPlan ...
			//check existing basket func_id correct or not
			if(CollectionUtils.isNotEmpty(vasHSRPList)){
				for(VasDetailDTO basketItem : vasHSRPList){
					List<ItemFuncAssgnMobDTO> funcList = itemFuncMobService.findItemFuncAssgnMobDTO(basketItem.getItemId(), appDateStr);
					for(ItemFuncAssgnMobDTO func : funcList) {
						if ("EX05".equals(func.getFuncId())){
							if (quotaPlanInfoUi==null ||  !quotaPlanInfoUi.containsKey(basketItem.getItemId())){
								errors.rejectValue("itemHtml", "dummy",
										"Information of Quota Plan Basket is required");
							}
						}
					}
				}
			}

			/***************************QuotaPlanInfo END**************************/
			endTime   = System.currentTimeMillis();
			logger.info("totalTime call VasDetailValidator in check quotaPlan ItemFuncAssgn in HSRPVasList: "+((endTime - startTime))+"ms");
			
			if (vasDetail.getSelectedSimItemId() == null || vasDetail.getSelectedSimItemId().length() <= 0) {
				errors.rejectValue("itemHtml", "dummy",
						"Please select a valid SIM.");
				return;
			}
			
			boolean isMup = false;
			
			List<String> msiItems = new ArrayList<String> ();
			if(multiSimInfos != null && multiSimInfos.size() != 0) {
				for(MultiSimInfoDTO msi : multiSimInfos){
					msiItems.add(msi.getItemId());
				}
			}
			
			VasMrtSelectionDTO cnMrtSelectionUi = (VasMrtSelectionDTO) request.getSession().getAttribute("vasMrtSelectionSession");
			VasMrtSelectionDTO ssMrtSelectionUi = (VasMrtSelectionDTO) request.getSession().getAttribute("ssMrtSelectionSession");
			if (cnMrtSelectionUi!=null){
				cnMrtSelectionUi.setChinaNumberSubscribed(false);
			}
			if (ssMrtSelectionUi!=null){
				ssMrtSelectionUi.setSsSubscribed(false);
			}
			
			
			startTime = System.currentTimeMillis();
			if(vasDetail.getVasitem() != null && vasDetail.getVasitem().length != 0){
				for(String item : vasDetail.getVasitem()){
					List<ItemFuncAssgnMobDTO> funcList = itemFuncMobService.findItemFuncAssgnMobDTO(item, appDateStr);
					for(ItemFuncAssgnMobDTO func : funcList) {
						if("EX03".equals(func.getFuncId())) {
							if(!msiItems.contains(item)) {
								errors.rejectValue("itemHtml", "dummy",
										"Information of MUP SIM is required.");
							}else{
								isMup = true;
							}
						}
						/***************************QuotaPlanInfo**************************/
						else if ("EX05".equals(func.getFuncId())){
							if (quotaPlanInfoUi==null ||  !quotaPlanInfoUi.containsKey(item)){
								errors.rejectValue("itemHtml", "dummy",
										"Information of Quota Plan Selection VAS is required");
							}
						}
						/***************************QuotaPlanInfoEND**************************/
						/***************************1C2N**************************/
						else if ("EX06".equals(func.getFuncId())){
	
							if (cnMrtSelectionUi==null ||  !item.equals(cnMrtSelectionUi.getVasItemId())){
								errors.rejectValue("itemHtml", "dummy",
										"Information of 1C2N Selection VAS is required");
							} else {		
								
								cnMrtSelectionUi.setChinaNumberSubscribed(true);		
								
								//1. check num type
								String selectedNumType = "";
								if (sessionCustomer!=null){
									selectedNumType = sessionCustomer.getNumType();
								}
								com.bomwebportal.mob.validate.dto.ResultDTO result = validateService.validateNumType(cnMrtSelectionUi.getNumType(), selectedNumType, "itemHtml",false);
								if (result!=null && result.hasError()){
									for (int i=0; i<result.getErrorList().size(); i++ ) {
										errors.rejectValue("itemHtml", "", result.getErrorList().get(i).getErrorMsg()+"(1C2N)");
									}
								}								
								
							}
						}
						/***************************1C2N END**************************/
						/***************************Secretarial Service**************************/
						else if ("EX07".equals(func.getFuncId())){						
							if (ssMrtSelectionUi==null ||  !item.equals(ssMrtSelectionUi.getVasItemId())){
								errors.rejectValue("itemHtml", "dummy",
										"Information of Secretarial Service is required");
							} else {
								
								ssMrtSelectionUi.setSsSubscribed(true);
								
								//1. check num type
								String selectedNumType = "";
								if (sessionCustomer!=null){
									selectedNumType = sessionCustomer.getNumType();
								}
								com.bomwebportal.mob.validate.dto.ResultDTO result = validateService.validateNumType(ssMrtSelectionUi.getNumType(), selectedNumType, "itemHtml",false);
								if (result!=null && result.hasError()){
									for (int i=0; i<result.getErrorList().size(); i++ ) {
										errors.rejectValue("itemHtml", "", result.getErrorList().get(i).getErrorMsg()+"(Secretarial Service)");
									}
								}													
							}
						}
						/***************************Secretarial Service END**************************/
					}
				}
			}
			endTime   = System.currentTimeMillis();
			logger.info("totalTime call VasDetailValidator in check quotaPlan/1C2N/Secretarial Service ItemFuncAssgn in SelectedVasList: "+((endTime - startTime))+"ms");
			/*//check combine account
			logger.info("sessionCustomer.getAcctType() for combine account:"+sessionCustomer.getAcctType());
			logger.info("isMup in combine account:"+isMup);
			//acctType: new=new account , current=current account 
			if( "current".equalsIgnoreCase(sessionCustomer.getAcctType()) 	&& isMup ){
				
				errors.rejectValue("itemHtml", "dummy",	"MUP basket not allow to use combine account");
				return;
			}*/
			
			
			if (isMup){
				if(multiSimInfos != null && multiSimInfos.size() != 0) {
					for(String selectedItem : vasDetail.getVasitem()){
						for(MultiSimInfoDTO msi : multiSimInfos) {
							if(selectedItem.equals(msi.getItemId())){
								for(MultiSimInfoMemberDTO msim : msi.getMembers()){
									//Dennis MIP3
									boolean error = false;
									
									if(msim.getMemberOrderType() == "MUPS01" || msim.getMemberOrderType() == "MUPS02") {
										//1. check num type
										String selectedNumType = "";
										if (sessionCustomer!=null){
											selectedNumType = sessionCustomer.getNumType();
										}
										System.out.println(selectedNumType + " = selectedNumType");
										com.bomwebportal.mob.validate.dto.ResultDTO result = validateService.validateNumType(msim.getNumType(), selectedNumType, "itemHtml",false);
										System.out.println(result + " = result");
										if (result!=null && result.hasError()){
											for (int i=0; i<result.getErrorList().size(); i++ ) {
												errors.rejectValue("itemHtml", "", result.getErrorList().get(i).getErrorMsg()+"(Multi SIM)");
											}
											error = true;
										}
										
										//2. check sim type
										if (user.getChannelId() == 2){
										
											String selectedSimType = "";
											if (sessionCustomer!=null){
												selectedSimType = sessionCustomer.getSimType();
											}
											
											String simItemId = msim.getSelectedSimItemId();
											String simType = vasDetailservice.getMipSimType(simItemId);
											com.bomwebportal.mob.validate.dto.ResultDTO simTypeResult = validateService.validateSimType(simType, selectedSimType, "itemHtml");
											if (simTypeResult!=null && simTypeResult.hasError()){
												for (int i=0; i<simTypeResult.getErrorList().size(); i++ ) {
													errors.rejectValue("itemHtml", "", simTypeResult.getErrorList().get(i).getErrorMsg()+"(Multi SIM)");
												}
												error = true;
											}
										
										}
									}
									
									
									if (error){
										break;
									}
									
								}
							}
						}
					}
				}
			}
			
			
			List<String> iGuardInd=new ArrayList();
			
			iGuardInd=	iGuardService.isIGuardOrder(sessionBasket.getBasketId(), vasDetail.getVasitem(), appDate);
			//add VAS validation for HKTCare item
			if (vasDetail.getVasitem() != null) {
				boolean helperCareAllowInd = vasDetailservice.existInSelectionGrpList(GenericReportHelper.HELPERCARE_INSURANCE_FORM_ITEM_SELECTTION_GROUP_ID, vasDetail.getVasitem());
				boolean travelInsuranceAllowInd = vasDetailservice.existInSelectionGrpList(GenericReportHelper.TRAVEL_INSURANCE_FORM_ITEM_SELECTTION_GROUP_ID, vasDetail.getVasitem());
				if (!"HKID".equalsIgnoreCase(sessionCustomer.getIdDocType()) && (helperCareAllowInd || travelInsuranceAllowInd)) {
					errors.rejectValue("itemHtml", "dummy", "Only HKID customer can choose 'HKTCare' item" );
				}
				if (StringUtils.isNotBlank(sessionCustomer.getDobStr())) {
					if ((Utility.getAgeByDob(sessionCustomer.getDobStr()) <18) && (helperCareAllowInd || travelInsuranceAllowInd)) {
						errors.rejectValue("itemHtml", "dummy", "Age above 18 customer can choose 'HKTCare' item");
					}
				}
			}
			startTime = System.currentTimeMillis();
			if (sessionMnp != null) {
				
				// add golden num check
				sessionBasket.setByPassGoldenNum(vasDetail.getByPassGoldenNum());
				goldenNumCheck(logger, sessionBasket, sessionMnp, errors, 
								Utility.date2String(appDate, 
										BomWebPortalConstant.DATE_FORMAT));   //change new Date to app Date 20141103 
				vasDetail.setShowGoldenNumAuth(sessionBasket.getShowGoldenNumAuth());

				if (!"6".equals(sessionBasket.getBasketTypeId()) && user.getChannelId() != 10 && user.getChannelId() != 11) {
					if (StringUtils.isBlank(sessionMnp.getCutoverDateStr()) && StringUtils.isBlank(sessionMnp.getServiceReqDateStr())) {
						errors.rejectValue("itemHtml",
								"itemHtml.srvDataCutDateRequire",
								"Service request date & cutover Date must be input");
					}
				}
			}
			endTime   = System.currentTimeMillis();
			logger.info("totalTime call VasDetailValidator in goldenNumCheck(): "+((endTime - startTime))+"ms");
			

			if (sessionCustomer!=null){
				/** Email cannot null checking **/
				
				logger.debug("sessionBasket.getDataOnlyInd():"+ sessionBasket.getDataOnlyInd()+";");
				if (sessionBasket.getDataOnlyInd()!=null && !sessionBasket.getDataOnlyInd().equalsIgnoreCase("")){
					if (sessionCustomer.getEmailAddr() == null || sessionCustomer.getEmailAddr().equalsIgnoreCase(""))
						errors.rejectValue("itemHtml", "itemHtml.dataOnlyNotMatch", "This basket is only suit for email as mandatory. Please input the email address in Customer Profile" );
				}
				
				if ("N".equalsIgnoreCase(sessionCustomer.getPhInd()) && "Y".equalsIgnoreCase(sessionBasket.getPublicHouseBaksetInd())) {
					errors.rejectValue("itemHtml", "itemHtml.phIndNotMatch", "This basket only suit for public housing customer" );
				}
				
				
				if (!"HKID".equalsIgnoreCase(sessionCustomer.getIdDocType())  && iGuardInd != null && iGuardInd.size() > 0) {
					errors.rejectValue("itemHtml", "dummy", "non-HKID application not allow choose 'i-Guard Phone Repair Plan Indicator' item" );
				}
				
				List<String> unmatchItemList = vasDetailservice.getUnmatchDocAssignedVas(vasDetail.getVasitem(), sessionCustomer.getIdDocType(), appDate);
				if (CollectionUtils.isNotEmpty(unmatchItemList)) {
					errors.rejectValue("vasitem", "dummy", "Items cannot be selected for unmatched subscriber document type");
					for (String item : unmatchItemList) {
						errors.rejectValue("vasitem", "dummy", item);
					}
					
				}
			}else{
				errors.rejectValue("itemHtml", "dummy", "Customer info not Found" );
				
			}
			
			if ( iGuardInd != null) {
				for(int j=0;j<iGuardInd.size();j++){
					String message= new String();
					
					    Boolean testGetPlan= iGuardService.getIGuardPlan(appDate, sessionBasket, iGuardInd.get(j), message);
						
						if (!testGetPlan){
							errors.rejectValue("itemHtml", "dummy", "The price of handset is not able to choose iGuard Phone Repair Plan" + message);
						}
				}
			}
			
			if(!vasDetailservice.hasSimInBasket(sessionBasket.getBasketId(),Utility.date2String(appDate, "dd/MM/yyyy"))){
				errors.rejectValue("itemHtml", "dummy", "This basket is invalid, which does not contain any SIM. Please Choose again." );
			}
			
			
			// clean existing PcRelationshipList
			vasDetail.setPcRelationshipList(null);
			
			startTime = System.currentTimeMillis();
			List<String> overMaxCountItemList = vasDetailservice.getOverMaxCountItemList(vasDetail.getVasitem(), sessionBasket.getBasketId());			
			endTime   = System.currentTimeMillis();
			logger.info("totalTime call VasDetailValidator in vasDetailservice.getOverMaxCountItemList(): "+((endTime - startTime))+"ms");
			
			
			if (overMaxCountItemList != null && overMaxCountItemList.size() > 0) {
				String errorHtml =  "Max. data service count reached, please select less data VAS services. "
						+ "Please consider to remove below optional VAS: <ol>";
				for (String vasName: overMaxCountItemList) {
					errorHtml += "<li>" + vasName + "";
				}
				errorHtml += "</ol>";
				errors.rejectValue("vasitem", "", errorHtml);	
			}
			
			List<ExclusiveItemDTO> exclusiveItemList = null;
			if (!this.isEmpty(vasDetail.getVasitem())) {
				startTime = System.currentTimeMillis();
				exclusiveItemList = vasDetailservice.getExclusiveItemList(Arrays.asList(vasDetail.getVasitem()));
				endTime   = System.currentTimeMillis();
				logger.info("totalTime call VasDetailValidator in vasDetailservice.getExclusiveItemList(): "+((endTime - startTime))+"ms");
			}
			if (isEmpty(exclusiveItemList)) {
				if (logger.isDebugEnabled()) {
					logger.debug("exclusiveItemList is null");
				}
				
				startTime = System.currentTimeMillis();
				
				String appMode = (String) vasDetail.getValue("appMode");
				boolean relationCheck = false;
				String codeType = null;
				if ("shop".equals(appMode) || "directsales".equals(appMode)) {
					codeType = "RS_BOM_RELATION_CHK";
				} else if ("mobccs".equals(appMode)) {
					codeType = "CCS_BOM_RELATION_CHK";
				}
				if (StringUtils.isNotBlank(codeType)) {
					List<CodeLkupDTO> codeIds = this.codeLkupService.getCodeLkupDTOALL(codeType);
					if (!this.isEmpty(codeIds)) {
						for (CodeLkupDTO dto : codeIds) {
							if ("Y".equals(dto.getCodeId())) {
								relationCheck = true;
								break;
							}
						}
					}
				}
				if (logger.isDebugEnabled()) {
					logger.debug("appMode: " + appMode + ", relationCheck: " + relationCheck);
				}
				if (relationCheck) {
					// further check with bom check_relationship_sb store proc
					String basketId = sessionBasket.getBasketId();
					Date appInDate;
					if (vasDetail.getValue("orderDto") instanceof OrderDTO && ((OrderDTO) vasDetail.getValue("orderDto")).getAppInDate() != null) {
						appInDate = ((OrderDTO) vasDetail.getValue("orderDto")).getAppInDate();
					} else {
						appInDate = new Date();
					}
					String[] vasItemIds = vasDetail.getVasitem();
					int numSelectedVAS = vasItemIds == null? 0 : vasItemIds.length;
					String[] vasItemIdWithSIM = new String[numSelectedVAS + 1];
					for (int i = 0; i< numSelectedVAS; i++) {
						vasItemIdWithSIM[i] = vasItemIds[i];
					}
					vasItemIdWithSIM[numSelectedVAS] = vasDetail.getSelectedSimItemId();
					List<PcRelationshipDTO> pcRelationshipList = this.relationshipCheckService.checkRelationshipSb(basketId, appInDate, vasItemIdWithSIM, sessionCustomer.getBrand(), sessionCustomer.getSimType());
					vasDetail.setPcRelationshipList(pcRelationshipList);
					if (!this.isEmpty(pcRelationshipList)) {
						errors.rejectValue("itemHtml", null, "Your selection violates relationship checking in BOM, please select again.");
					}
					
					
				}
				
				endTime   = System.currentTimeMillis();
				logger.info("totalTime call VasDetailValidator in relationshipCheckService.checkRelationshipSb(): "+((endTime - startTime))+"ms");
			} else {
					errors.rejectValue("itemHtml", "",	"VAS Selection Conflict");//print to the VASdetail header
					
					for (ExclusiveItemDTO dto : exclusiveItemList) {
						errors.rejectValue("vasitem", "","");
						errors.rejectValue("vasitem", "","Items cannot be selected together:");
						errors.rejectValue("vasitem", "","&lt;"+ dto.getItemDescriptionA()+ "&gt;");
						errors.rejectValue("vasitem", "","&lt;"+ dto.getItemDescriptionB()+ "&gt;");	
					}
			}
			
			List<String> list = new ArrayList<String>();
			list.add(vasDetail.getSelectedSimItemId());
			if(vasDetail.getVasitem() != null){
				list.addAll(Arrays.asList(vasDetail.getVasitem()));
			}
			//String[] s = (String[]) list.toArray();
			String[] s = list.toArray(new String[list.size()]);
			startTime = System.currentTimeMillis();
			List<ItemsRelationshipDTO> results = itemsRelationshipService.getItemsRelations(s, String.valueOf(user.getChannelId()), appDate);
			endTime   = System.currentTimeMillis();
			logger.info("totalTime call VasDetailValidator in itemsRelationshipService.getItemsRelations(): "+((endTime - startTime))+"ms");
			
			for (ItemsRelationshipDTO dto : results) {
				errors.rejectValue("vasitem", "", dto.getDescription());
			}
			
		/*	if(vasDetail.getVasitem() != null){
				List<String> list = Arrays.asList(vasDetail.getVasitem());
				list.add(vasDetail.getSelectedSimItemId());
				if (list != null) {
					String[] s = (String[]) list.toArray();
					
					List<ItemsRelationshipDTO> results = itemsRelationshipService.getItemsRelations(s, String.valueOf(user.getChannelId()), appDate);
					for (ItemsRelationshipDTO dto : results) {
						errors.rejectValue("vasitem", "", dto.getDescription());
					}
				}
			}*/
									
			/****************************** Start Min VAS Requirement Validation ***********************************/
			validateMinVas(errors, vasDetail, sessionBasket, request);
			/****************************** End Min VAS Validation ***********************************/
			
			/****************************** Start Quota Validation ***********************************/
			startTime = System.currentTimeMillis();
			if ("HKID".equalsIgnoreCase(sessionCustomer.getIdDocType()) || "PASS".equalsIgnoreCase(sessionCustomer.getIdDocType())) {
				//HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
				// get the list of finalQuotaList again, in case not come from prev. page...
				List<String> selectedItemList = null;
				if (vasDetail.getVasitem() != null && vasDetail.getVasitem().length > 0) {
					selectedItemList = Arrays.asList(vasDetail.getVasitem());
				}
				vasDetail.setFinalOuotaList(mobItemQuotaService.getFinalQuota(sessionBasket.getBasketId(), appDateStr, selectedItemList));
				
				//createMobQuotaConsumeRequest
				SuperOrderDTO superOrderDto = (SuperOrderDTO) request.getSession().getAttribute("superOrderDto");
				String orderId = (superOrderDto == null) ? "" : superOrderDto.getOrderId();
				String authBy = MobCcsAuthorizeController.getSessionAuthorizedBy(request, "AU15");  // tmp use AU15 ...
				QuotaConsumeRequest qcr = mobItemQuotaService.createMobQuotaConsumeRequest(
						sessionCustomer.getIdDocType(), sessionCustomer.getIdDocNum(), orderId, 
						user.getUsername(), vasDetail, authBy, appDate);
				
				//Validate Quota
				superOrderDto.setQuotaConsumeRequest(null);
				
				if (CollectionUtils.isNotEmpty(qcr.getItems())) {
					if (!vasDetail.isIgnoreQuotaCheck()) {
						MobQuotaUsageDTO[] quotaUsages = this.checkQuota(qcr, errors);
					}
				} else {
					qcr = null;
				}
				
				if (qcr != null) {
					superOrderDto.setQuotaConsumeRequest(qcr);
				}
			}
			endTime   = System.currentTimeMillis();
			logger.info("totalTime call VasDetailValidator in Quota Validation(): "+((endTime - startTime))+"ms");
			/****************************** End Quota Validation ***********************************/
			
			/****************************** Start TNG Validation ***********************************/
			if (user.getChannelId() == 2) {
				if (vasDetailservice.isExistTNGCardItem(vasDetail.getVasitem())){
					if (!"HKID".equalsIgnoreCase(sessionCustomer.getIdDocType())) {
						errors.rejectValue("vasitem", "dummy", "Tap & Go Cards cannot be selected for non HKID customer.");
					}
				}
			}
			/****************************** End TNG Validation ***********************************/
			
			
			/****************************** Start Project Eagle Validation ***********************************/			
			boolean hasEagleSpringboardError = false;
			String[] selectedVas = vasDetail.getVasitem();
			if (selectedVas != null && selectedVas.length > 0 
					&& vasDetailservice.existInSelectionGrpList(ProjectEagleReportHelper.ITEM_SELECTTION_GROUP_ID, selectedVas)) {
				// Only age above 18
				if (StringUtils.isNotBlank(sessionCustomer.getDobStr())) {
					if ((Utility.getAgeByDob(sessionCustomer.getDobStr()) <18)) {
						errors.rejectValue("vasitem", "dummy", "Age above 18 customer can choose 'Restart Service' item");
					}
				}
				// Only HKID and Passport
				if (!((StringUtils.equalsIgnoreCase("HKID", sessionCustomer.getIdDocType())) 
						|| (StringUtils.equalsIgnoreCase("PASS", sessionCustomer.getIdDocType())))) {
					errors.rejectValue("vasitem", "dummy", "Only HKID or Passport customer can choose 'Restart Service' item.");
					hasEagleSpringboardError = true;
				}
				
				// District & Area of the Address should not be null
				if (StringUtils.isBlank(sessionCustomer.getAreaDesc()) || StringUtils.isBlank(sessionCustomer.getDistrictDesc())) {
					errors.rejectValue("vasitem", "dummy", "Address without District or Area can not choose 'Restart Service' item.");
				}
				
				// Only SIM + HANDSET
				if (!(StringUtils.equalsIgnoreCase("1", sessionBasket.getBasketTypeId()))) {
					errors.rejectValue("vasitem", "dummy", "Only 'SIM + Handset' basket can choose 'Restart Service' item.");
					hasEagleSpringboardError = true;
				}
				
				// Only specified channel(s), skip if not specify any channel
				List<CodeLkupDTO> eligibleChannelIdList = codeLkupService.findCodeLkupByType("EAGLE_ELIGIBLE_CHANNEL_ID");
				if (CollectionUtils.isNotEmpty(eligibleChannelIdList)) {
					String eligibleChannelsDescription = "";
					Set<String> eligibleChannelIds = new HashSet<String>();
					for (CodeLkupDTO eligibleChannelId : eligibleChannelIdList) {
						eligibleChannelIds.add(eligibleChannelId.getCodeId());
						eligibleChannelsDescription += eligibleChannelId.getCodeDesc() + ", ";
					}
					eligibleChannelsDescription = StringUtils.substringBeforeLast(eligibleChannelsDescription, ", ");
					if (!eligibleChannelIds.contains(user.getChannelId() + "")) {
						errors.rejectValue("vasitem", "dummy", "Only '" + eligibleChannelsDescription + "' channel can choose 'Restart Service' item.");
						hasEagleSpringboardError = true;
					}
				}
				
				if (StringUtils.isEmpty(sessionBasket.getHsPosItemCd())) {
					//For digital coupon
					errors.rejectValue("vasitem", "dummy", "This handset item cannot choose 'Restart Service' item.");
				} else {
				// Only specified model(s), skip if not specify any model
					List<CodeLkupDTO> eligibleHandsetModelList = codeLkupService.findCodeLkupByType("EAGLE_ELIGIBLE_HANDSET_MODEL_ID");
					if (CollectionUtils.isNotEmpty(eligibleHandsetModelList)) {
						boolean isEligibleHandsetModel = false;
						for (CodeLkupDTO eligibleHandsetModel : eligibleHandsetModelList) {
							if (StringUtils.equalsIgnoreCase(eligibleHandsetModel.getCodeId(), sessionBasket.getModelId())) {
								isEligibleHandsetModel = true;
								break;
							}
						}
						if (!isEligibleHandsetModel) {
							errors.rejectValue("vasitem", "dummy", "This model cannot choose 'Restart Service' item.");
							hasEagleSpringboardError = true;
						}
					}
					
					// Only meet with ns price range set up
					Date appInDate = (orderDto == null)? new Date() : orderDto.getAppInDate();
					String selectedEagleProductCode = "";
					
					BigDecimal selectedNsPrice = iGuardService.getNsPrice(sessionBasket.getHsPosItemCd(), appInDate);
					if (selectedNsPrice == null) {
						errors.rejectValue("vasitem", "dummy", "Corresponding NS price is not set up. Please inform SB support!");
						hasEagleSpringboardError = true;
					}
					
					boolean fulfillSubscribeProjectEagle = false;
					for (int i = 0; i < selectedVas.length; i++) {
						selectedEagleProductCode = iGuardService.getProductCodeBySelectGrpAndLineGrp(selectedVas[i], ProjectEagleReportHelper.ITEM_SELECTTION_GROUP_ID, ProjectEagleReportHelper.SERVICE_LINE_GROUP_CATEGORY);
						if (StringUtils.isNotBlank(selectedEagleProductCode)
								&& iGuardService.fulfillSubscribeProjectEagle(selectedNsPrice, selectedEagleProductCode, appInDate)) {
							fulfillSubscribeProjectEagle = true;
							break;
						}
					}
					if (!fulfillSubscribeProjectEagle) {
						errors.rejectValue("vasitem", "dummy", "Corresponding NS price (HK$" + selectedNsPrice + ") cannot choose 'Restart Service' item.");
						hasEagleSpringboardError = true;
					}
				}
				// Project Eagle API Blacklist Checking
				if (!hasEagleSpringboardError) {
					EagleErrorResponse response = new EagleErrorResponse();
					BlacklistRequest blacklistRequest = new BlacklistRequest();
					blacklistRequest.setIdDocType(sessionCustomer.getIdDocType());
					blacklistRequest.setIdDocNum(sessionCustomer.getIdDocNum());
					response = iGuardService.eagleBlacklist(blacklistRequest);
					if (response != null) {
						if ("0".equals(response.getErrCode())) {
							if ("true".equalsIgnoreCase(response.getErrMsg())) {
								errors.rejectValue("vasitem", "dummy", "Customer with ID (Type: " + sessionCustomer.getIdDocType() + ", No.: " + sessionCustomer.getIdDocNum() + ") is in Restart Service Blacklist.");
							}
						} else {
							if (!vasDetail.isIgnoreEagleAPICheck()) {
								errors.rejectValue("vasitem", "dummy", "Failed in Calling Restart Service Blacklist (WS): " + response.getErrCode() + ", " + response.getErrMsg());
								vasDetail.setIsEagleAPIFailedInd("Failed");
							}
						}
					} else {
						if (!vasDetail.isIgnoreEagleAPICheck()) {
							errors.rejectValue("vasitem", "dummy", "Failed in Calling Restart Service Blacklist (SBExt).");
							vasDetail.setIsEagleAPIFailedInd("Failed");
						}
					}
				}
				
			}
			/****************************** End Project Eagle Validation ***********************************/
			
			long endTimeTotal   = System.currentTimeMillis();
			logger.info("totalTime call VasDetailValidator: "+((endTimeTotal - startTimeTotal))+"ms");
	}

	private void validateMinVas(Errors errors, VasDetailDTO vasDetail, BasketDTO sessionBasket,
			HttpServletRequest request) {
		
		double basketMinVasAmt = vasDetail.getMinVas();
		
		if (sessionBasket == null || basketMinVasAmt <= 0 || StringUtils.equalsIgnoreCase("Y", vasDetail.getMinVasAuthInd())) {
			return;
		}
		
		List<VasDetailDTO> vasList = (List<VasDetailDTO>) MobCcsSessionUtil.getSession(request,"vasList");
		String[] selectedVasArray = vasDetail.getVasitem();
		if (CollectionUtils.isEmpty(vasList)) {
			return;
		}
		if (basketMinVasAmt > 0 && ArrayUtils.isEmpty(selectedVasArray)) {
			String message = "Selected total VAS net monthly fee cannot meet the minimum requirement of $%d VAS";
			message += " (Please select additional $%d VAS)";
			errors.rejectValue("minVas", "", String.format(message, (int)basketMinVasAmt, (int)basketMinVasAmt));
			return;
		}
		
		double allSelectedVasAmtTotal = 0.0;
		double qualifiedVasAmtTotal = 0.0;
		int basketContractPeriod = strToInt(sessionBasket.getContractPeriod());
		boolean isNotFulfillContractPeriod = false;
		
		for (String selectedVas : selectedVasArray) {
			for (VasDetailDTO vasItem : vasList) {
				if (StringUtils.equalsIgnoreCase(selectedVas, vasItem.getItemId())) {
					double vasContractPeriod = strToInt(vasItem.getContractPeriod());
					double vasGrossMonthlyAmt = strToDbl(vasItem.getItemRecurrentAmt());
					double vasNetMonthlyAmt = strToDbl(vasItem.getNetMonthlyAmt());
					if (vasContractPeriod >= basketContractPeriod) {
						if (vasGrossMonthlyAmt > 0 && vasNetMonthlyAmt > 0) {
							qualifiedVasAmtTotal += vasNetMonthlyAmt;
						}
					} else {
						isNotFulfillContractPeriod = true;
					}	
					allSelectedVasAmtTotal += vasNetMonthlyAmt;
				}
			}
		}
		
		boolean isNotFulfillQualifiedNetMonthly = qualifiedVasAmtTotal < basketMinVasAmt;
		
		if (isNotFulfillQualifiedNetMonthly) {
			if (isNotFulfillContractPeriod && (allSelectedVasAmtTotal >= basketMinVasAmt)) {
				String message = "Selected contract period cannot meet the minimum requirement of %d mths";
				errors.rejectValue("minVas", "", String.format(message, basketContractPeriod));
				
			} else if (isNotFulfillContractPeriod) {
				String message = "Selected total VAS net monthly fee and contract period cannot meet the minimum requirement of $%d VAS and %d mths";
				message += " (Please select additional $%d VAS)";
				errors.rejectValue("minVas", "", String.format(message, (int)basketMinVasAmt, basketContractPeriod,
						(int)((int)basketMinVasAmt - (int)qualifiedVasAmtTotal)));
			
			} else {
				String message = "Selected total VAS net monthly fee cannot meet the minimum requirement of $%d VAS";
				message += " (Please select additional $%d VAS)";
				errors.rejectValue("minVas", "", String.format(message, (int)basketMinVasAmt,
						(int)((int)basketMinVasAmt - (int)qualifiedVasAmtTotal)));
			}
		}

	}

	@Override
	protected String getSbType() {
		return "VasDetail";
	}
	
	private <E> boolean isEmpty(E[] values) {
		return values == null || values.length == 0;
	}
	
	private boolean isEmpty(List<?> list) {
		return list == null || list.isEmpty();
	}
	
	private MobQuotaUsageDTO[] checkQuota(QuotaConsumeRequest qcr, Errors errors) {
		MobQuotaUsageDTO[] quotaUsages = null;
		boolean validQuota = true;
		try {
			quotaUsages = mobQuotaService.mockConsumeQuota(qcr);
		} catch (AppServiceException e) {
			logger.error("Error while checking quota", e);
			errors.rejectValue("ignoreQuotaCheck", "", "Quota Check is not available.");
		}
		
		if (quotaUsages != null) {
			if (StringUtils.isEmpty(qcr.getAuthBy())) {
				for (MobQuotaUsageDTO usage : quotaUsages) {
					// check also the returned orderid , over quota error if the quota id is a newly added one ...
					if (usage.isOverQuota() && StringUtils.isEmpty(usage.getOrderId())) {
						errors.rejectValue("isQuotaExceededInd", "validator.mobquotausage.exceeded",
								new String[]{usage.getEngDesc(), ""+usage.getUsed(), ""+usage.getCeilCnt()},
								"Exceeded Quota : " + usage.getEngDesc() + " - Ceiling: " + usage.getCeilCnt());
						validQuota = false;
					}
				}
			}
		}
		return quotaUsages;
	}
	
	
	private int strToInt(String str){
	    
		 int intNum = 0;
		  
	     try{
	    	 intNum = Integer.parseInt(str.trim());
	     }
	     catch(Exception e){
	    	 intNum = 0;
	     }
		return intNum;
	}
	
	private double strToDbl(String str) {
		double dblNum = 0.00;
		
		try {
			dblNum = Double.parseDouble(str);
	       } catch(Exception e) {
	    	dblNum = 0.00;
	       }
		
		return dblNum;
	}
}
