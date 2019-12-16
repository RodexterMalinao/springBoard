package com.bomwebportal.validator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.bomwebportal.dto.BasketDTO;
import com.bomwebportal.dto.MobileSimInfoDTO;
import com.bomwebportal.dto.SalesmanDTO;
import com.bomwebportal.mob.ccs.dto.SalesInfoDTO;
import com.bomwebportal.mob.ccs.dto.StockAssgnDTO;
import com.bomwebportal.mob.ccs.service.CodeLkupService;
import com.bomwebportal.mob.ccs.service.MobCcsSalesInfoService;
import com.bomwebportal.mob.ccs.service.StaffInfoService;
import com.bomwebportal.mob.ccs.service.StockService;
import com.bomwebportal.mob.validate.dto.ResultDTO;
import com.bomwebportal.mob.validate.service.ValidateService;
import com.bomwebportal.service.IGuardService;
import com.bomwebportal.service.LoginService;
import com.bomwebportal.service.MobileSimInfoService;
import com.bomwebportal.service.OrderService;
import com.bomwebportal.service.VasDetailService;
import com.bomwebportal.util.BomWebPortalConstant;
import com.bomwebportal.util.Utility;

public class MobileSimInfoValidator implements Validator {
	
	private static final String PROJECT_EAGLE_ITEM_SELECTTION_GROUP_ID = "6666666663";

	private MobileSimInfoService service;
	private IGuardService iGuardService;
    private VasDetailService vasDetailService;
	private OrderService orderService;
	private StockService stockService;
	private LoginService loginService;
	private ValidateService validateService;
	private CodeLkupService codeLkupService;

	/*Whitney*/
	private StaffInfoService staffInfoService;
	private MobCcsSalesInfoService mobCcsSalesInfoService; 
	
	public void setStaffInfoService(StaffInfoService staffInfoService) {
		this.staffInfoService = staffInfoService;
	}
	public StaffInfoService getStaffInfoService() {
		return staffInfoService;
	}
	public MobCcsSalesInfoService getMobCcsSalesInfoService() {
		return mobCcsSalesInfoService;
	}
	public void setMobCcsSalesInfoService(MobCcsSalesInfoService mobCcsSalesInfoService) {
		this.mobCcsSalesInfoService = mobCcsSalesInfoService;
	}
	
	
	
	public IGuardService getiGuardService() {
		return iGuardService;
	}

	public void setiGuardService(IGuardService iGuardService) {
		this.iGuardService = iGuardService;
	}

	public OrderService getOrderService() {
		return orderService;
	}

	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}

	public StockService getStockService() {
		return stockService;
	}

	public void setStockService(StockService stockService) {
		this.stockService = stockService;
	}

	public LoginService getLoginService() {
		return loginService;
	}

	public void setLoginService(LoginService loginService) {
		this.loginService = loginService;
	}

	public MobileSimInfoService getService() {
		return service;
	}

	public void setService(MobileSimInfoService service) {
		this.service = service;
	}
		
	public VasDetailService getVasDetailService() {
		return vasDetailService;
	}

	public void setVasDetailService(VasDetailService vasDetailService) {
		this.vasDetailService = vasDetailService;
	}

	public ValidateService getValidateService() {
		return validateService;
	}

	public void setValidateService(ValidateService validateService) {
		this.validateService = validateService;
	}

	public boolean supports(Class clazz) {
		return clazz.equals(MobileSimInfoDTO.class);
	}
	
	public CodeLkupService getCodeLkupService() {
		return codeLkupService;
	}
	public void setCodeLkupService(CodeLkupService codeLkupService) {
		this.codeLkupService = codeLkupService;
	}

	public void validate(Object obj, Errors errors) {
		MobileSimInfoDTO mobileSimInfoDTO = (MobileSimInfoDTO) obj;
		BasketDTO sessionBasket = (BasketDTO)mobileSimInfoDTO.getValue("basket");
		String selectedSimType = (String)mobileSimInfoDTO.getValue("simType");
		String[] vasItemList = (String[]) mobileSimInfoDTO.getValue("selectedItemList");// new 
		
		if (mobileSimInfoDTO.getChannelId() == 10 || mobileSimInfoDTO.getChannelId() == 11) {
			if (mobileSimInfoDTO.getSalesLocation() == null || mobileSimInfoDTO.getSalesLocation().length() == 0) {
				errors.rejectValue("salesLocation", "dummy", "Please select location.");
				return;
			}
		}
		
		if (mobileSimInfoDTO.getChannelId() != 10 && mobileSimInfoDTO.getChannelId() != 11) {
			if ("UATSIM".equals(mobileSimInfoDTO.getIccid())  || "UATSIMSIGNOFF".equals(mobileSimInfoDTO.getIccid())){// add 20110601
				mobileSimInfoDTO.setSimBrandType(selectedSimType);
				return;
			}
		}else{
			if ("UATSIM".equals(mobileSimInfoDTO.getIccid())  || "UATSIMSIGNOFF".equals(mobileSimInfoDTO.getIccid())){// add 20110601
				mobileSimInfoDTO.setSimBrandType(selectedSimType);			
			}
		}
		
		if (mobileSimInfoDTO.getChannelId() != 10 && mobileSimInfoDTO.getChannelId() != 11) {
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "iccid", "iccid.required");
		} else {
			if (mobileSimInfoDTO.getSalesContactNum() == null || mobileSimInfoDTO.getSalesContactNum().trim().length() < 4) {
				errors.rejectValue("salesContactNum", "dummy", "Please input Sales Contact Number!");
			} 
		}
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "salesCd", "salesCd.required");
		String simItemCd = null;
		String simItemId = null;
		List<StockAssgnDTO> oldStockAssgnList = null;
		String orderId = mobileSimInfoDTO.getOrderId();
		if (orderId != null) {
			oldStockAssgnList = orderService.getStockAssgnListByOrderId(orderId);
		} else {
			oldStockAssgnList = new ArrayList<StockAssgnDTO>();
		}
		String oldIccid = null;
		String oldSimItemCd = null;
		for (StockAssgnDTO oldStockItem:oldStockAssgnList) {
			if ("02".equals(oldStockItem.getItemType()) && StringUtils.isBlank(oldStockItem.getMemberNum())) {
				oldIccid = oldStockItem.getItemSerial();
				oldSimItemCd =  oldStockItem.getItemCode();
			}
		}
		List<String> selectedSimItemCdList = vasDetailService.getPosItemCdByItemId(mobileSimInfoDTO.getSelectedSimItemId());
		String selectedSimItemCd = "";
		if (selectedSimItemCdList != null && selectedSimItemCdList.size() > 0) {
			selectedSimItemCd = selectedSimItemCdList.get(0);
		}
		
		if (StringUtils.isNotBlank(mobileSimInfoDTO.getIccid())
				&& !"UATSIM".equals(mobileSimInfoDTO.getIccid())  
				&& !"UATSIMSIGNOFF".equals(mobileSimInfoDTO.getIccid())
				&& (!mobileSimInfoDTO.getIccid().equals(oldIccid) 				
						|| !selectedSimItemCd.equals(oldSimItemCd) 
						|| !(mobileSimInfoDTO.getChannelId() == 10 || mobileSimInfoDTO.getChannelId() == 11)
						)  ){
			
			List<String> iccidList = (List<String>)mobileSimInfoDTO.getValue("iccidList");
			if(iccidList != null){
				for(String iccid : iccidList){
					if(iccid.equals(mobileSimInfoDTO.getIccid())){
						errors.rejectValue("iccid", "dummy", "Duplicate Sim # used by Multi Sim");
						return;
					}
				}
			}
			
			
			MobileSimInfoDTO resultMobileSimInfoDto = service.validateSim(mobileSimInfoDTO);
			if (resultMobileSimInfoDto != null 
					&& mobileSimInfoDTO.getIccid().equals(resultMobileSimInfoDto.getIccid())) {
				
				mobileSimInfoDTO.setImsi(resultMobileSimInfoDto.getImsi());
				mobileSimInfoDTO.setItemCd(resultMobileSimInfoDto.getItemCd());
				mobileSimInfoDTO.setPuk1(resultMobileSimInfoDto.getPuk1());
				mobileSimInfoDTO.setPuk2(resultMobileSimInfoDto.getPuk2());
				mobileSimInfoDTO.setHwInvStatus(resultMobileSimInfoDto.getHwInvStatus());
				//mobileSimInfoDTO.setSimBrandType(selectedSimType);
				ResultDTO result = validateService.validateSimTypeByIccid(mobileSimInfoDTO.getIccid(), selectedSimType, "simBrandType");		
				
				validateService.bindErrors(result, errors);	
				if (!result.hasError()){
					mobileSimInfoDTO.setSimBrandType(selectedSimType);
				}
				/*if (StringUtils.isBlank(mobileSimInfoDTO.getSimBrandType())) {
					errors.rejectValue("iccid", "invalid.simType");
				}else{
					ResultDTO result = validateService.validateSimType(mobileSimInfoDTO.getIccid(), selectedSimType);				
					validateService.bindErrors(result, errors);				
				}*/
				
				if (mobileSimInfoDTO.getChannelId() == 10 || mobileSimInfoDTO.getChannelId() == 11) {
					String pendingOrderId = "";
					if (mobileSimInfoDTO != null && StringUtils.isNotBlank(mobileSimInfoDTO.getOrderId())) {
						pendingOrderId = service.getPendingOrderExistWithIccidOrderId(mobileSimInfoDTO.getIccid(),
								mobileSimInfoDTO.getOrderId());
					} else {
						pendingOrderId = service.getPendingOrderExistWithIccid(mobileSimInfoDTO.getIccid());
					}
					if (StringUtils.isNotBlank(pendingOrderId)){
						errors.rejectValue("iccid", "", "Pending SB order exists with this input SIM ICCID, order ID =" + pendingOrderId);
						return;
					}
				}
					
				simItemCd = resultMobileSimInfoDto.getItemCd();
				simItemId = service.getBomWebSimItemId(sessionBasket.getBasketId(), simItemCd);

				if (!simItemId.equals(mobileSimInfoDTO.getSelectedSimItemId())) {
					errors.rejectValue("iccid", "", String.format("Input SIM ICCID (Item Code = %s) does not match with selected SIM item Code.", simItemCd == null? "" : simItemCd));	
					return;
				}
				
				if (resultMobileSimInfoDto.getHwInvStatus() != BomWebPortalConstant.HWINV_VALID_STATUS) {
					if (mobileSimInfoDTO.getChannelId() != 10 && mobileSimInfoDTO.getChannelId() != 11) {
						errors.rejectValue("iccid", "sim.invalid");
					} else {
						errors.rejectValue("iccid", "", "SIM is invalid in HW Inventory.");	
					}
					return;
				}else if (!resultMobileSimInfoDto.getShopCd().equals(
						mobileSimInfoDTO.getShopCd())) {
					errors.rejectValue("iccid", "shopCd.notMatch");
					return;
				}
			} else {
				if (mobileSimInfoDTO.getChannelId() != 10 && mobileSimInfoDTO.getChannelId() != 11) {
					errors.rejectValue("iccid", "invalid.iccid");
				} else {
					errors.rejectValue("iccid", "", "SIM is not found in HW Inventory.");	
				}
				return;
			}
		}

		if (mobileSimInfoDTO.getChannelId() == 10 || mobileSimInfoDTO.getChannelId() == 11) {
			if (StringUtils.isBlank(mobileSimInfoDTO.getSimSalesMemoNum())) {
				errors.rejectValue("iccid", "", "Sales Memo Number cannot be blank.");
				return;
			} else {
				boolean smSimIsNew = true;
				for (StockAssgnDTO oldStockItem:oldStockAssgnList) {
					if (mobileSimInfoDTO.getSimSalesMemoNum().equals(oldStockItem.getSalesMemoNum()) || 
							mobileSimInfoDTO.getSimSalesMemoNum().equals(oldStockItem.getSalesMemoNum2())) {
						smSimIsNew = false;
					}
				}
				if (smSimIsNew && mobileSimInfoDTO.getSimSalesMemoNum().length() > 0) {
					if (stockService.isValidSerial(BomWebPortalConstant.SALES_MEMO_ITEM_CODE, mobileSimInfoDTO.getSimSalesMemoNum(), mobileSimInfoDTO.getSalesCd(), mobileSimInfoDTO.getChannelCd(), mobileSimInfoDTO.getCenterCd(), mobileSimInfoDTO.getTeamCd(), mobileSimInfoDTO.getAppDate()) <= 0) {
						errors.rejectValue("iccid", "", "Sales Memo Number is invalid.");
					}
				}
			}
		}
		
		
		if ("NONE".equals(mobileSimInfoDTO.getSalesType())) {
			errors.rejectValue("salesType", "salesType.required");
			return;
		} else {
			if ("S".equalsIgnoreCase(mobileSimInfoDTO.getSalesType())) {
				if (mobileSimInfoDTO.getSalesCd() != null
						&& !"".equals(mobileSimInfoDTO.getSalesCd().trim())) {
					boolean salesResult = service
							.validateSalesCd(mobileSimInfoDTO);
					if (!salesResult) {
						errors.rejectValue("salesCd", "invalid.salesCd");
						return;
					}
				}
			}
		}

		if ("C".equals(mobileSimInfoDTO.getSalesType())) { // "S">Salesman ID
															// ,"C">CMRID
			if (mobileSimInfoDTO.getSalesCd().length() != 4) {
				errors.rejectValue("salesCd", "invalid.salesCd");
				return;
			}
		}	
		
		String feSalesName = mobileSimInfoDTO.getSalesName(); // frontend display sales name
		SalesmanDTO dbSalesDto =  service.getSalesman(mobileSimInfoDTO.getSalesType(), 
													mobileSimInfoDTO.getSalesCd()); // database store sales name
		String dbSalesName = (dbSalesDto == null) ? "" : dbSalesDto.getSalesName();
		
		if (dbSalesDto == null || StringUtils.isEmpty(dbSalesName)) {
			errors.rejectValue("salesCd", "invalid.salesCd");
			return;
		} else {
			if (StringUtils.isNotEmpty(dbSalesName)) {
				feSalesName = dbSalesName;
				if (StringUtils.isEmpty(feSalesName)) {
					errors.rejectValue("salesName", "dummy", "Please press refresh!");
					return;
				} else if (!feSalesName.equals(dbSalesName)) {
					errors.rejectValue("salesName", "dummy", "Invalid sales name!");
					return;
				}
			}
		}
		
		if (CollectionUtils.isEmpty(mobileSimInfoDTO.getStockAssgnList())) {
			mobileSimInfoDTO.setStockAssgnList(new ArrayList<StockAssgnDTO>());
		}
		//check duplicated stock
		Set<String> checkDuplicationSet  = new HashSet<String>();
		
		mobileSimInfoDTO.setAoInd("N");
		mobileSimInfoDTO.setImei("");
		for (int i = 0; i < mobileSimInfoDTO.getStockAssgnList().size(); i++) {	
			StockAssgnDTO stockItem = mobileSimInfoDTO.getStockAssgnList().get(i);
			if (StringUtils.isNotBlank(stockItem.getItemSerial()) && !checkDuplicationSet.add(stockItem.getItemSerial())) {
				errors.rejectValue("stockAssgnList[" + i + "].itemSerial", "dummy", "Serial Number is duplicated.");
			}

			if (mobileSimInfoDTO.getChannelId() == 10 || mobileSimInfoDTO.getChannelId() == 11) {
				stockItem.setItemSerial(stockItem.getItemSerial().trim());
				stockItem.setSalesMemoNum(stockItem.getSalesMemoNum().trim());
				stockItem.setSalesMemoNum2(stockItem.getSalesMemoNum2().trim());
			}
			
			if(!"Y".equals(stockItem.getSalesMemoInd())) {
				stockItem.setSalesMemoInd("N");
			}
			

			if(!"Y".equals(stockItem.getAoInd())) {
				stockItem.setAoInd("N");
			} else {
				mobileSimInfoDTO.setAoInd("Y");
			}
			
			if (mobileSimInfoDTO.getChannelId() == 10 || mobileSimInfoDTO.getChannelId() == 11 || "01".equalsIgnoreCase(stockItem.getItemType())) {
				if (stockItem.getAoInd().equals("N")) {
					if (StringUtils.isBlank(stockItem.getItemSerial())) {
						errors.rejectValue("stockAssgnList[" + i + "].itemSerial", "dummy", "Serial Number cannot be blank.");
					} else {
						if ("HS".equalsIgnoreCase(stockItem.getType())&& stockItem.getItemSerial().length()!=15){
							errors.rejectValue("stockAssgnList[" + i + "].itemSerial", "dummy", "Handset item serial should be 15 digits.");
						}
						
						if ("HS".equalsIgnoreCase(stockItem.getType()) && stockItem.getItemCode().equalsIgnoreCase(sessionBasket.getHsPosItemCd())) {
							mobileSimInfoDTO.setImei(stockItem.getItemSerial());
						}
					}
				} else {
					if (StringUtils.isNotBlank(stockItem.getItemSerial())) {
						errors.rejectValue("stockAssgnList[" + i + "].itemSerial", "dummy", "Serial Number should be blank for Advanced Order.");
					}
					if (vasItemList != null) {
						boolean projectEagleInd = vasDetailService.existInSelectionGrpList(PROJECT_EAGLE_ITEM_SELECTTION_GROUP_ID, vasItemList);
						if (projectEagleInd) {
							errors.rejectValue("stockAssgnList[" + i + "].eagleValidate", "dummy", "Restart Service item can’t be chosen in Advanced Order.");
						}
					}
				}
			} else {
				if ("Y".equalsIgnoreCase(stockItem.getAoInd()) && StringUtils.isNotBlank(stockItem.getItemSerial())) {
					errors.rejectValue("stockAssgnList[" + i + "].itemSerial", "dummy", "Serial Number should be blank for Advanced Order.");
				}
			}
			
			if (mobileSimInfoDTO.getChannelId() == 10 || mobileSimInfoDTO.getChannelId() == 11) {
				if (StringUtils.isBlank(stockItem.getSalesMemoNum()) && StringUtils.isBlank(stockItem.getSalesMemoNum2())) {
					errors.rejectValue("stockAssgnList[" + i + "].itemSerial", "dummy", "Sales Memo Number cannot be blank.");
				}
				
				//DS Recall / Amend
				boolean itemChanged = true;
				boolean smNum1isNew = true;
				boolean smNum2isNew = true;
				if (oldStockAssgnList != null) {
					for (StockAssgnDTO oldStockItem:oldStockAssgnList) {
						if (stockItem.getItemSerial().equals(oldStockItem.getItemSerial()) &&
								stockItem.getItemCode().equals(oldStockItem.getItemCode())) {
							itemChanged = false;
						}
						if (stockItem.getSalesMemoNum().equals(oldStockItem.getSalesMemoNum()) || 
								stockItem.getSalesMemoNum().equals(oldStockItem.getSalesMemoNum2())) {
							smNum1isNew = false;
						}
						if (stockItem.getSalesMemoNum2().equals(oldStockItem.getSalesMemoNum()) || 
								stockItem.getSalesMemoNum2().equals(oldStockItem.getSalesMemoNum2())) {
							smNum2isNew = false;
						}
					}
				}
				if (itemChanged && stockItem.getItemSerial().length() > 0) {
					int tempResult = stockService.isValidSerial(stockItem.getItemCode(), stockItem.getItemSerial(), mobileSimInfoDTO.getSalesCd(), mobileSimInfoDTO.getChannelCd(), mobileSimInfoDTO.getCenterCd(), mobileSimInfoDTO.getTeamCd(), mobileSimInfoDTO.getAppDate());
					if (tempResult == 0) {
						errors.rejectValue("stockAssgnList[" + i + "].itemSerial", "dummy", "Serial Number is not found in inventory.");
					} else if (tempResult == -2) {
						errors.rejectValue("stockAssgnList[" + i + "].itemSerial", "dummy", "Serial Number is not available for use.");
					} else if (tempResult == -1) {
						errors.rejectValue("stockAssgnList[" + i + "].itemSerial", "dummy", "Shop Code Error for check serail number.");
					}
				}
				if (smNum1isNew && stockItem.getSalesMemoNum().length() > 0) {
					if (stockService.isValidSerial(BomWebPortalConstant.SALES_MEMO_ITEM_CODE, stockItem.getSalesMemoNum(), mobileSimInfoDTO.getSalesCd(), mobileSimInfoDTO.getChannelCd(), mobileSimInfoDTO.getCenterCd(), mobileSimInfoDTO.getTeamCd(), mobileSimInfoDTO.getAppDate()) <= 0) {
						errors.rejectValue("stockAssgnList[" + i + "].itemSerial", "dummy", "Sales Memo Number 1 is invalid.");
					}
				}
				if (smNum2isNew && stockItem.getSalesMemoNum2().length() > 0) {
					if (stockService.isValidSerial(BomWebPortalConstant.SALES_MEMO_ITEM_CODE, stockItem.getSalesMemoNum2(), mobileSimInfoDTO.getSalesCd(), mobileSimInfoDTO.getChannelCd(), mobileSimInfoDTO.getCenterCd(), mobileSimInfoDTO.getTeamCd(), mobileSimInfoDTO.getAppDate()) <= 0) {
						errors.rejectValue("stockAssgnList[" + i + "].itemSerial", "dummy", "Sales Memo Number 2 is invalid.");
					}
				}
			}
			
		}
		
		/*Add by Whitney 20160829*/
		/*Validate Ref. Sales*/
		String refSalesName = null, refSalesCentre = null, refSalesTeam = null;		

		if (!(StringUtils.isEmpty(mobileSimInfoDTO.getRefSalesId())&&StringUtils.isEmpty(mobileSimInfoDTO.getRefSalesName())
				&&StringUtils.isEmpty(mobileSimInfoDTO.getRefSalesCentre())&&StringUtils.isEmpty(mobileSimInfoDTO.getRefSalesTeam()))){
			if (mobileSimInfoDTO.getRefSalesId()!=""){
				if (!mobileSimInfoDTO.isManualInputBool()) {
					List<SalesInfoDTO> refTempDtoList = new ArrayList<SalesInfoDTO>();
					refSalesName = staffInfoService.getStaffName(mobileSimInfoDTO.getRefSalesId().toUpperCase().trim(), Utility.date2String(mobileSimInfoDTO.getAppDate(), BomWebPortalConstant.DATE_FORMAT));
					refTempDtoList = mobCcsSalesInfoService.getSalesInfoDTOByID(mobileSimInfoDTO.getRefSalesId().toUpperCase().trim(), Utility.date2String(mobileSimInfoDTO.getAppDate(), BomWebPortalConstant.DATE_FORMAT));		
					if (refTempDtoList != null && refTempDtoList.size() > 0) {
						refSalesCentre = refTempDtoList.get(0).getCentreCd();
						refSalesTeam = refTempDtoList.get(0).getTeamCd();
						mobileSimInfoDTO.setRefSalesName(refSalesName);
						mobileSimInfoDTO.setRefSalesCentre(refSalesCentre);
						mobileSimInfoDTO.setRefSalesTeam(refSalesTeam);
					}	
					else{
						mobileSimInfoDTO.setRefSalesName(null);
						mobileSimInfoDTO.setRefSalesCentre(null);
						mobileSimInfoDTO.setRefSalesTeam(null);
						errors.rejectValue("refSalesName", "refSalesName.required");
						errors.rejectValue("refSalesTeam", "refSalesTeam.required");
						errors.rejectValue("refSalesCentre", "refSalesCentre.required");
						errors.rejectValue("refSalesId", "invalid.refSalesId");
					}
				}
				else{
					if (StringUtils.isEmpty(mobileSimInfoDTO.getRefSalesId())){
						errors.rejectValue("refSalesId", "dummy", "Ref. Sales Id can't be empty.");
					}
					if (StringUtils.isEmpty(mobileSimInfoDTO.getRefSalesName())){
						errors.rejectValue("refSalesName", "dummy", "Ref. Sales Name can't be empty.");
					}
					if (StringUtils.isEmpty(mobileSimInfoDTO.getRefSalesCentre())){
						errors.rejectValue("refSalesCentre", "dummy", "Ref. Sales Centre can't be empty.");
					}
					if (StringUtils.isEmpty(mobileSimInfoDTO.getRefSalesTeam())){
						errors.rejectValue("refSalesTeam", "dummy", "Ref. Sales Team can't be empty.");
					}
				}
			}
			else{
				mobileSimInfoDTO.setRefSalesName(null);
				mobileSimInfoDTO.setRefSalesCentre(null);
				mobileSimInfoDTO.setRefSalesTeam(null);
				mobileSimInfoDTO.setManualInputBool(false);
			}
		}
		
		String iccid = mobileSimInfoDTO.getIccid().trim();
		String simItemCode = (mobileSimInfoDTO.getItemCd() == null)? "" : mobileSimInfoDTO.getItemCd();
		if (StringUtils.isNotBlank(iccid)) {
			boolean itemChanged = true;
			
			if (oldStockAssgnList != null) {
				for (StockAssgnDTO oldStockItem:oldStockAssgnList) {
					if (iccid.equals(oldStockItem.getItemSerial()) &&
							simItemCode.equals(oldStockItem.getItemCode())) {
						itemChanged = false;
					}
				}
			}
			
			int simValidateResult = 1;
			if (mobileSimInfoDTO.getChannelId() == 10 || mobileSimInfoDTO.getChannelId() == 11) {
				String simType = stockService.getSimType(simItemCode);
				if (simType != null) {
					if (!simType.equals(mobileSimInfoDTO.getSimType())) {
						errors.rejectValue("iccid", "dummy", "The SIM number does not match required item code.");
					}
				}
				
				simValidateResult = stockService.isValidSerial(simItemCd, iccid, mobileSimInfoDTO.getSalesCd(), mobileSimInfoDTO.getChannelCd(), mobileSimInfoDTO.getCenterCd(), mobileSimInfoDTO.getTeamCd(), mobileSimInfoDTO.getAppDate());
			}
			
			if (simValidateResult == 1
					|| "UATSIMSIGNOFF".equals(iccid)
					|| "UATSIM".equals(iccid)
					|| !itemChanged) {
				if (!errors.hasErrors()) {
					StockAssgnDTO simStockItem = new StockAssgnDTO();
					simStockItem.setAoInd("N");
					simStockItem.setItemSerial(iccid);
					simStockItem.setItemCode(simItemCode);
					simStockItem.setItemType("02");	
					if (mobileSimInfoDTO.getChannelId() == 10 || mobileSimInfoDTO.getChannelId() == 11) {
						simStockItem.setSalesMemoNum(mobileSimInfoDTO.getSimSalesMemoNum());
					}
					mobileSimInfoDTO.getStockAssgnList().add(simStockItem);
				}
			} else {
				errors.rejectValue("iccid", "dummy", "SIM number is not found in inventory.");
			}
		} else {
			errors.rejectValue("iccid", "dummy", "SIM number is invalid.");
		}

		System.out.println(Utility.toPrettyJson(errors.getAllErrors()));
	}
}
