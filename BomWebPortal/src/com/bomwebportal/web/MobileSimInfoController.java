package com.bomwebportal.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

import com.bomwebportal.dto.BasketDTO;
import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.dto.CustomerProfileDTO;
import com.bomwebportal.dto.MobileSimInfoDTO;
import com.bomwebportal.dto.MultiSimInfoDTO;
import com.bomwebportal.dto.MultiSimInfoMemberDTO;
import com.bomwebportal.dto.SalesmanDTO;
import com.bomwebportal.dto.VasDetailDTO;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.mob.ccs.dto.CodeLkupDTO;
import com.bomwebportal.mob.ccs.dto.SalesInfoDTO;
import com.bomwebportal.mob.ccs.dto.StockAssgnDTO;
import com.bomwebportal.mob.ccs.service.CodeLkupService;
import com.bomwebportal.mob.ccs.service.MobCcsSalesInfoService;
import com.bomwebportal.mob.ccs.service.StaffInfoService;
import com.bomwebportal.mob.ccs.service.StockService;
import com.bomwebportal.mob.ccs.util.MobCcsSessionUtil;
import com.bomwebportal.service.MobileSimInfoService;
import com.bomwebportal.service.OrderService;
import com.bomwebportal.service.VasDetailService;
import com.bomwebportal.util.BomWebPortalConstant;
import com.bomwebportal.util.Utility;

public class MobileSimInfoController extends SimpleFormController {
	protected final Log logger = LogFactory.getLog(getClass());

	/*ADD~*/
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
	
	private MobileSimInfoService mobileSimInfoService;
	private VasDetailService vasDetailService;
	private StockService stockService;
	private OrderService orderService;
	private CodeLkupService codeLkupService;

	public MobileSimInfoService getMobileSimInfoService() {
		return mobileSimInfoService;
	}

	public void setMobileSimInfoService(
			MobileSimInfoService mobileSimInfoService) {
		this.mobileSimInfoService = mobileSimInfoService;
	}

	public VasDetailService getVasDetailService() {
		return vasDetailService;
	}

	public void setVasDetailService(VasDetailService vasDetailService) {
		this.vasDetailService = vasDetailService;
	}

	public StockService getStockService() {
		return stockService;
	}

	public void setStockService(StockService stockService) {
		this.stockService = stockService;
	}

	public OrderService getOrderService() {
		return orderService;
	}

	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}

	public CodeLkupService getCodeLkupService() {
		return codeLkupService;
	}

	public void setCodeLkupService(CodeLkupService codeLkupService) {
		this.codeLkupService = codeLkupService;
	}

	public Object formBackingObject(HttpServletRequest request)
			throws ServletException {
		/*Add by Whitney 20160825*/
		String workStatus = (String) MobCcsSessionUtil.getSession(request, "workStatus");
		request.setAttribute("workStatus", workStatus);
		
		MobileSimInfoDTO mobileSimInfo = (MobileSimInfoDTO) request.getSession().getAttribute("MobileSimInfo");
		BomSalesUserDTO user = (BomSalesUserDTO) request.getSession().getAttribute("bomsalesuser");
		VasDetailDTO vasDetail =  (VasDetailDTO) MobCcsSessionUtil.getSession(request, "vasDetail");
		SalesmanDTO salesmanDTO = null;
		
		String selectedBasketId = (String) request.getSession().getAttribute("basketSession");
		String [] selectedItemList = (String [])request.getSession().getAttribute("selectedVasItemList");
		String orderId= (String)request.getSession().getAttribute("orderIdSession");
		List<StockAssgnDTO> stockAssgnList = stockService.getStockAssgnList(selectedBasketId, selectedItemList);
		
		if (mobileSimInfo == null) {
			mobileSimInfo = new MobileSimInfoDTO();		
			if (user != null) {
				mobileSimInfo.setSalesCd(user.getUsername());
				mobileSimInfo.setShopCd("P" + user.getBomShopCd());
				if (user.getChannelId() != 10 && user.getChannelId() != 11) {
					mobileSimInfo.setSalesContactNum("1000");
				}
				//set new shop info, else from OrderDetailController
				mobileSimInfo.setChannelId(user.getChannelId());
				mobileSimInfo.setChannelCd(user.getChannelCd());
				mobileSimInfo.setCenterCd(user.getAreaCd());
				mobileSimInfo.setTeamCd(user.getShopCd());
				mobileSimInfo.setAppDate(new Date());
			}
		}
		mobileSimInfo.setSimCharge(vasDetail.getSimCharge());
		mobileSimInfo.setSimWaiveReason(vasDetail.getSimWaiveReason());
		mobileSimInfo.setSimWaivable(vasDetail.isSimWaivable());
		mobileSimInfo.setRpWaiveReason(vasDetail.getRpWaiveReason());
		mobileSimInfo.setRpCharge(vasDetail.getRpCharge());
		if ("".equals(mobileSimInfo.getSalesType()) || mobileSimInfo.getSalesType() == null){
			salesmanDTO = mobileSimInfoService.getSalesman("S", mobileSimInfo.getSalesCd());
			mobileSimInfo.setSalesName(salesmanDTO.getSalesName());		
			mobileSimInfo.setSalesErrCode(salesmanDTO.getErrCode());
			mobileSimInfo.setSalesErrMsg(salesmanDTO.getErrMsg());
		} else if("".equals(mobileSimInfo.getSalesName()) || mobileSimInfo.getSalesName() == null){
			salesmanDTO = mobileSimInfoService.getSalesman(mobileSimInfo.getSalesType(), mobileSimInfo.getSalesCd());
			mobileSimInfo.setSalesName(salesmanDTO.getSalesName());	
			mobileSimInfo.setSalesErrCode(salesmanDTO.getErrCode());
			mobileSimInfo.setSalesErrMsg(salesmanDTO.getErrMsg());
		} 		
		
		
		mobileSimInfo.setSelectedSimItemId(vasDetail.getSelectedSimItemId());
		String simItemCd = getSimItemCodeByItemId(vasDetail.getSelectedSimItemId());
		mobileSimInfo.setSelectedSimItemCd(simItemCd);
		
		//set basket info and vas info and order info
		BasketDTO sessionBasket = (BasketDTO) MobCcsSessionUtil.getSession(request, "basket");
		mobileSimInfo.setValue("basket", sessionBasket);
		
		String appDate = (String) request.getSession().getAttribute("appDate");
		mobileSimInfo.setValue("appDate", appDate);		
		
		mobileSimInfo.setValue("bomsalesuser", user);
		
		mobileSimInfo.setValue("selectedItemList", selectedItemList);
		
		
		CustomerProfileDTO sessionCustomer = (CustomerProfileDTO) request.getSession().getAttribute("customer");
		mobileSimInfo.setValue("simType", sessionCustomer.getSimType());
		
		List<MultiSimInfoDTO> multiSimInfos = (List<MultiSimInfoDTO>) request.getSession().getAttribute("MultiSimInfoList");
		List<String> iccidList = new ArrayList<String> ();
		List<String> vasList = vasDetailService.getSubscribedVASList(sessionBasket.getBasketId(), selectedItemList, sessionCustomer.getBrand(), sessionCustomer.getSimType());
		if(multiSimInfos != null && multiSimInfos.size() != 0) {
			for(String selectedItem : vasList){
				for(MultiSimInfoDTO msi : multiSimInfos) {
					if(selectedItem.equals(msi.getItemId())){
						for(MultiSimInfoMemberDTO msim : msi.getMembers()){
							iccidList.add(msim.getSim().getIccid());
						}
					}
				}
			}
		}
		mobileSimInfo.setValue("iccidList", iccidList);
		
		if (user.getChannelId() == 10 || user.getChannelId() == 11) {
			if (mobileSimInfo.getStockAssgnList() != null) {
				for (StockAssgnDTO stockAssgn: mobileSimInfo.getStockAssgnList()) {
					if ("02".equals(stockAssgn.getItemType())) {
						//mobileSimInfo.setSimSalesMemoInd(stockAssgn.getSalesMemoInd());
						mobileSimInfo.setSimSalesMemoNum(stockAssgn.getSalesMemoNum());
					}
				}
			}
			
			String simType = "0";
			simType = this.vasDetailService.getSelectedSimExTraFunction(Utility.date2String(mobileSimInfo.getAppDate(), "dd/MM/yyyy"), sessionBasket.getBasketId(), vasDetail.getSelectedSimItemId());		
			List<String> simList = stockService.getDsSerialNum(simItemCd, mobileSimInfo.getSalesCd(), mobileSimInfo.getAppDate(), 
					mobileSimInfo.getChannelCd(), mobileSimInfo.getCenterCd(),  mobileSimInfo.getTeamCd());
			mobileSimInfo.setSimType(simType);
			if (simList == null) {
				simList = new ArrayList<String>();
			}
			
			if (orderId != null) {
				String sim = orderService.getSim(orderId).getIccid();
				simList.add(sim);
			}
			
			mobileSimInfo.setSimList(simList);
		}
		
		if (mobileSimInfo.getStockAssgnList() != null) {
			for (StockAssgnDTO stockAssgnSession: mobileSimInfo.getStockAssgnList()) {
				for (StockAssgnDTO stockAssgn:stockAssgnList) {
					if (stockAssgn.getItemCode().equals(stockAssgnSession.getItemCode()) &&  (stockAssgn.getAoInd() == null)) {
						stockAssgn.setAoInd(stockAssgnSession.getAoInd());
						stockAssgn.setItemSerial(stockAssgnSession.getItemSerial());
						stockAssgn.setSalesMemoInd(stockAssgnSession.getSalesMemoInd());
						stockAssgn.setSalesMemoNum(stockAssgnSession.getSalesMemoNum());
						stockAssgn.setSalesMemoNum2(stockAssgnSession.getSalesMemoNum2());
						stockAssgn.setErrorMsg(stockAssgnSession.getErrorMsg());
						break;
					}
				}
			}
		}
		if (user.getChannelId() == 10 || user.getChannelId() == 11) {
			for (StockAssgnDTO stockAssgn:stockAssgnList) {
				String itemCd = stockAssgn.getItemCode();
				List<String> serialList = stockService.getDsSerialNum(itemCd, mobileSimInfo.getSalesCd(), mobileSimInfo.getAppDate(), 
						mobileSimInfo.getChannelCd(), mobileSimInfo.getCenterCd(), mobileSimInfo.getTeamCd());
				if (serialList == null) {
					serialList = new ArrayList<String>();
				}
				if (orderId != null) {
					List<StockAssgnDTO> originalStockAssgnList = orderService.getStockAssgnListByOrderId(orderId);
					for (StockAssgnDTO originalStockAssgn:originalStockAssgnList) {
						if (originalStockAssgn.getItemCode() != null && originalStockAssgn.getItemCode().equals(itemCd)) {
							serialList.add(originalStockAssgn.getItemSerial());
						}
					}						
				}
				stockAssgn.setItemSerialList(serialList);
			}			
		}
		
		mobileSimInfo.setStockAssgnList(stockAssgnList);
		

		/*mobileSimInfo.setWorkStatus(workStatus);*/

		
		/*check whether Ref. Sale Id should be checked*/
		if (mobileSimInfo.getOrderId()!=null){
			if (StringUtils.isNotBlank(mobileSimInfo.getRefSalesId())){
				List<SalesInfoDTO> refTempDtoList = new ArrayList<SalesInfoDTO>();
				refTempDtoList = mobCcsSalesInfoService.getSalesInfoDTOByID(mobileSimInfo.getRefSalesId().toUpperCase().trim(), Utility.date2String(mobileSimInfo.getAppDate(), BomWebPortalConstant.DATE_FORMAT));		
				if (refTempDtoList != null && refTempDtoList.size() > 0) {
					mobileSimInfo.setManualInputBool(false);
				}
				else{
					mobileSimInfo.setManualInputBool(true);
				}
			}
		}
	
		
		return mobileSimInfo;
	}
	
	protected Map<String, Object> referenceData(HttpServletRequest request) throws Exception {
		boolean subscribedUADVas = false;
		Map<String, Object> referenceData = new HashMap<String, Object>();
		List<CodeLkupDTO> waiveReasons= codeLkupService.findActiveReasonCodeLkupByType("WAIVE_OT", new Date());
		Map<String,String> dsLocationList = mobileSimInfoService.getDSLocationList();
		String [] selectedItemList = (String [])request.getSession().getAttribute("selectedVasItemList");
		List<String> iguardUADItem = vasDetailService.getItemSelectionGrpList("6666666669");
		for (String iGuardUADItem:iguardUADItem){
			if (ArrayUtils.contains(selectedItemList, iGuardUADItem)){
				subscribedUADVas = true;
			}
		}
		
		referenceData.put("iGuardUAD", subscribedUADVas);
		referenceData.put("waiveReasons", waiveReasons);
		referenceData.put("dsLocationList", dsLocationList);
		return referenceData;
	}
	
	public ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws ServletException, AppRuntimeException {
		//modify eliot 20110623
		MobileSimInfoDTO mobileSimInfoDTO = (MobileSimInfoDTO) command;
		String actionType = mobileSimInfoDTO.getActionType();
		String viewStatus = "";		
		if ("REFRESH".equalsIgnoreCase(actionType))
		{	
			logger.info("(MobileSimInfoDTO)command iccid :"
					+ mobileSimInfoDTO.getIccid());
			logger.info("(MobileSimInfoController) page refresh");
			viewStatus = "mobilesiminfo.html";
		}
		else if ("SUBMIT".equalsIgnoreCase(actionType))
		{
			mobileSimInfoDTO.setAmend(true);
			String nextView = (String) request.getAttribute("nextView");
			logger.info("nextView: " + nextView);
	
			String currentView = (String) request.getParameter("currentView");
			logger.info("currentView: " + currentView);
				
			logger.info("(MobileSimInfoDTO)command iccid :"
					+ mobileSimInfoDTO.getIccid());
			logger.info("(MobileSimInfoController)nextView :" + nextView);
			
			
			SalesmanDTO dbSalesDto = mobileSimInfoService.getSalesman(mobileSimInfoDTO.getSalesType(), 
													mobileSimInfoDTO.getSalesCd());
			if (mobileSimInfoDTO == null
					|| (mobileSimInfoDTO != null && StringUtils.isEmpty(mobileSimInfoDTO.getSalesName()))
					|| dbSalesDto == null
					|| (dbSalesDto != null && StringUtils.isEmpty(dbSalesDto.getSalesName()))) {
				viewStatus = currentView + ".html";
			} else if (!mobileSimInfoDTO.getSalesName().equalsIgnoreCase(dbSalesDto.getSalesName())) {
				viewStatus = currentView + ".html";	
			} 
			else {
				viewStatus = nextView;
			} 
		}
		request.getSession().setAttribute("MobileSimInfo", mobileSimInfoDTO);
		String attrUid=(String)request.getParameter("sbuid");
		ModelAndView modelAndView =  new ModelAndView(new RedirectView(viewStatus));
		modelAndView.addObject("sbuid", attrUid);
		
		return modelAndView;
	}
	
	private String getSimItemCodeByItemId(String simItemId) {
		List<String> simItemCdList = vasDetailService.getPosItemCdByItemId(simItemId);
		String simItemCd = "";
		if (CollectionUtils.isNotEmpty(simItemCdList)) {
			simItemCd = simItemCdList.get(0);
		}
		return simItemCd;
	}
	
}
