package com.bomwebportal.web;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openuri.www.CustomerBasicInfoDTO;
import org.openuri.www.ServiceLineDTO;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

import com.bomwebportal.bean.LookupTableBean;
import com.bomwebportal.dto.ActualUserDTO;
import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.dto.CustomerProfileDTO;
import com.bomwebportal.dto.DepositLkupDTO;
import com.bomwebportal.dto.MobBillMediaDTO;
import com.bomwebportal.dto.OrderDTO;
import com.bomwebportal.dto.SuperOrderDTO;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.UserTimeoutException;
import com.bomwebportal.mob.ccs.dto.CodeLkupDTO;
import com.bomwebportal.mob.ccs.util.BeanUtilsHelper;
import com.bomwebportal.mob.ccs.util.BomWebPortalCcsConstant;
import com.bomwebportal.mob.ccs.util.MobCcsSessionUtil;
import com.bomwebportal.service.CustomerInformationService;
import com.bomwebportal.service.CustomerProfileService;
import com.bomwebportal.service.DepositService;
import com.bomwebportal.service.OrdEmailReqService;
import com.bomwebportal.service.OrderService;
import com.bomwebportal.util.SystemTime;
import com.bomwebportal.util.Utility;

public class CustomerProfileController extends SimpleFormController {
	protected final Log logger = LogFactory.getLog(getClass());

	private CustomerProfileService service;
	private CustomerInformationService customerInformationService;
	private DepositService depositService;
	private OrdEmailReqService ordEmailReqService;
	private OrderService orderService;
	
	final String[] toCheckProperties = new String[] 
			{"title", "custLastName", "custFirstName", "idDocType", "idDocNum"
			, "companyName", "dobStr", "contactPhone", "otherContactPhone", "emailAddr"
			, "quickSearch", "flat", "floor", "lotNum", "buildingName", "streetNum"
			, "streetName", "streetCatgDesc", "streetCatgCode", "addrProofInd"
			, "sectionDesc", "sectionCode", "districtDesc", "districtCode", "areaDesc"
			, "areaCode", "phInd", "billingQuickSearch", "billingFlat", "billingFloor"
			, "billingLotNum", "billingBuildingName", "billingStreetNum", "billingStreetName" 
			, "billingStreetCatgDesc", "billingStreetCatgCode", "billingSectionDesc"
			, "billingSectionCode", "billingDistrictDesc", "billingDistrictCode", "billingAreaDesc"
			, "billingAreaCode", "lob", "serviceNum", "billLang", "smsLang"};
	
	public CustomerInformationService getCustomerInformationService() {
		return customerInformationService;
	}

	public void setCustomerInformationService(
			CustomerInformationService customerInformationService) {
		this.customerInformationService = customerInformationService;
	}

	public CustomerProfileService getService() {
		return service;
	}

	public void setService(CustomerProfileService service) {
		this.service = service;
	}
	
	public DepositService getDepositService() {
		return depositService;
	}

	public void setDepositService(DepositService depositService) {
		this.depositService = depositService;
	}
	

	public OrdEmailReqService getOrdEmailReqService() {
		return ordEmailReqService;
	}

	public void setOrdEmailReqService(OrdEmailReqService ordEmailReqService) {
		this.ordEmailReqService = ordEmailReqService;
	}

	public OrderService getOrderService() {
		return orderService;
	}

	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}

	public CustomerProfileController() {
	}

	public Object formBackingObject(HttpServletRequest request)
			throws ServletException, UserTimeoutException {
		
		String appMode = (String)request.getSession().getAttribute("appMode");

		boolean careCashRegistrationInd = false;

		String acctType = (String)request.getSession().getAttribute("acctType");

		String workStatus = (String) MobCcsSessionUtil.getSession(request, "workStatus");
		request.getSession().setAttribute("workStatus", workStatus);
		CustomerProfileDTO sessionCustomer = getSessionCustomer(request);
		ServiceLineDTO selectedServiceLine = (ServiceLineDTO) request.getSession().getAttribute("selectedServiceLineSession");
		if (MobCcsSessionUtil.getSession(request, "origCustAddress") == null) {
			if (selectedServiceLine != null) {
				CustomerProfileDTO origCustomer = new CustomerProfileDTO();
				copyAddressFromSelectedServiceLine(origCustomer, selectedServiceLine);
				MobCcsSessionUtil.setSession(request, "origCustAddress", origCustomer);
				//referenceData.put("origCustAddress", origCustomer);
	
			} else if (sessionCustomer != null && "2".equals(sessionCustomer.getAddrProofInd())) {
				CustomerProfileDTO origCustomer = new CustomerProfileDTO();
				try {
					BeanUtils.copyProperties(origCustomer, sessionCustomer);
					MobCcsSessionUtil.setSession(request, "origCustAddress", origCustomer);
					
				} catch (Exception e) {
					logger.error(e);
				}
				//referenceData.put("origCustAddress", origCustomer);
			}
		}
		
		SuperOrderDTO superOrderDto = (SuperOrderDTO) request.getSession().getAttribute("superOrderDto");
		BomSalesUserDTO user = (BomSalesUserDTO) request.getSession().getAttribute("bomsalesuser");
		if (superOrderDto == null) {
			superOrderDto = new SuperOrderDTO();
			superOrderDto.setOrderSalesCd(user.getSalesCd());
			superOrderDto.setOrderShopCd(user.getChannelCd());
			request.getSession().setAttribute("superOrderDto", superOrderDto);
		}

		if (sessionCustomer == null) {
			
			CustomerProfileDTO customer = new CustomerProfileDTO();
			
			String channelId = String.valueOf(user.getChannelId());

			customer.setCareCashRegisterTimeInd(false);
			customer.setCareCashOrderSignOffInd(false);
			String careCashStartDate=service.getCareCashStartDate("CARECASH_START_DATE");
			Date today = new Date();
			if(StringUtils.isNotEmpty(careCashStartDate)){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				try {
					Date date = sdf.parse(careCashStartDate);
					if (today.after(date)){
						customer.setCareCashRegisterTimeInd(true);	
					}
				} catch (ParseException e) {

				}			
			}
			//combine account
		
			String combineAcctStartDate=service.getCareCashStartDate("COMBINEACCOUNT_START_DATE");
			if(StringUtils.isNotEmpty(combineAcctStartDate)){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				try {
					Date date = sdf.parse(combineAcctStartDate);
					if (today.after(date)){
						customer.setCombineAccountRegisterTimeInd(true);	
					}
				} catch (ParseException e) {

				}			
			}
			//combine account
		
			
			customer.setCareStatus("N");
			customer.setCareOptInd("I");
			
			customer.setCheckAcctInfo("N");
			customer.setAcctType("new");
			customer.setIsNew("Y");
			// Set default brand to CSL
			//boolean disable1010 = false;
			//boolean disableCSim = false;
			//List<CodeLkupDTO> MIP_DISABLE_1010_BRAND  = (List<CodeLkupDTO>)LookupTableBean.getInstance().getCodeLkupList().get("MIP_DISABLE_1010_BRAND");
			//List<CodeLkupDTO> MIP_DEFAULT_SIM  = (List<CodeLkupDTO>)LookupTableBean.getInstance().getCodeLkupList().get("MIP_DISABLE_C_SIM");
			
			/*if (CollectionUtils.isNotEmpty(MIP_DISABLE_1010_BRAND)){
				for (CodeLkupDTO dto : MIP_DISABLE_1010_BRAND){
					if ("Y".equals(dto.getCodeId())){
						disable1010 = true;
					}
				}
			}*/
			
			/*if (CollectionUtils.isNotEmpty(MIP_DISABLE_C_SIM)){
				for (CodeLkupDTO dto : MIP_DISABLE_C_SIM){
					if ("Y".equals(dto.getCodeId())){
						disableCSim = true;
					}
				}
			}*/
			
			List<CodeLkupDTO> MIP_DEFAULT_BRAND  = (List<CodeLkupDTO>)LookupTableBean.getInstance().getCodeLkupList().get("MIP_DEFAULT_BRAND");
			List<CodeLkupDTO> MIP_DEFAULT_SIM  = (List<CodeLkupDTO>)LookupTableBean.getInstance().getCodeLkupList().get("MIP_DEFAULT_SIM");
			
			if (CollectionUtils.isNotEmpty(MIP_DEFAULT_BRAND)){
				customer.setBrand(MIP_DEFAULT_BRAND.get(0).getCodeId());
			}else{
				customer.setBrand("1");
			}
			
			if (CollectionUtils.isNotEmpty(MIP_DEFAULT_SIM)){
				customer.setSimType(MIP_DEFAULT_SIM.get(0).getCodeId());
				customer.setNumType(MIP_DEFAULT_SIM.get(0).getCodeId());
			}else{
				//enableCSIM,default H   20160620
				customer.setSimType("H");
				customer.setNumType("H");
			}
			
			
			
			/*if (!disableCSim){
				customer.setSimType("C");
				customer.setNumType("C");
							
			}else{
				customer.setSimType("H");
				customer.setNumType("H");
			}*/
			
			request.getSession().setAttribute("appDate", Utility.date2String(SystemTime.asDate(), "dd/MM/yyyy"));
			
			CustomerBasicInfoDTO selectedCustInfo = (CustomerBasicInfoDTO) request.getSession().getAttribute("selectedCustInfoSession");
			
			if (selectedCustInfo != null) {
				customer.setCustomerBasicInfoDTO(selectedCustInfo);
				if (!"--".equals(selectedCustInfo.getTitle()))
					customer.setTitle(selectedCustInfo.getTitle());
				if (!"--".equals(selectedCustInfo.getFirstName()))
					customer.setCustFirstName(selectedCustInfo.getFirstName());
				if (!"--".equals(selectedCustInfo.getLastName()))
					customer.setCustLastName(selectedCustInfo.getLastName());
				if (!"--".equals(selectedCustInfo.getIdDocType()))
					customer.setIdDocType(selectedCustInfo.getIdDocType());
				if (!"--".equals(selectedCustInfo.getIdDocNum()))
					customer.setIdDocNum(selectedCustInfo.getIdDocNum());
				if (!"--".equals(selectedCustInfo.getSystemId()))
					customer.setSystemId(selectedCustInfo.getSystemId());
				if (!"--".equals(selectedCustInfo.getCompanyName()))
					customer.setCompanyName(selectedCustInfo.getCompanyName());

				if (null != selectedCustInfo.getDob()) {
					customer.setDobStr(selectedCustInfo.getDob());
					customer.setDob(Utility.string2Date(selectedCustInfo.getDob()));
				}

			}
			
			if (selectedServiceLine != null) {
				copyAddressFromSelectedServiceLine(customer, selectedServiceLine);
				customer.setUnlinkSectionFlag(true);//address copy bug fix 20130923

			}
			
			customer.setCsPortalBool(true);//cs portal Athena 20131004
			customer.setAppDateStr((String) request.getSession().getAttribute("appDate"));
			
			
			// Address Proof Default Value
			if (channelId.equals("2")) {
				customer.setAddrProofInd(null);
			} else {
				// Set default address proof indicator to "No Address Proof"
				customer.setAddrProofInd("1");
			}
			
			if(StringUtils.isNotEmpty(customer.getLob())) {
				customer.setAddrProofInd("2");
			}
		
			return customer;

		} else {// sessionCustomer is not null

			if (sessionCustomer.isNoBillingAddressFlag()) {
				sessionCustomer.setBillingQuickSearch("");
				sessionCustomer.setBillingFlat("");
				sessionCustomer.setBillingFloor("");
				sessionCustomer.setBillingLotNum("");
				sessionCustomer.setBillingBuildingName("");
				sessionCustomer.setBillingStreetNum("");
				sessionCustomer.setBillingStreetName("");
				sessionCustomer.setBillingStreetCatgDesc("");
				sessionCustomer.setBillingStreetCatgCode("");
				sessionCustomer.setBillingSectionDesc("");
				sessionCustomer.setBillingSectionCode("");
				sessionCustomer.setBillingDistrictDesc("");
				sessionCustomer.setBillingDistrictCode("");
				sessionCustomer.setBillingAreaDesc("");
				sessionCustomer.setBillingAreaCode("");
				sessionCustomer.setBillingUnlinkSectionFlag(false);
				sessionCustomer.setBillingCustAddressFlag(false);
				
			}
			
			if (selectedServiceLine != null
					&& "2".equals(sessionCustomer.getAddrProofInd())) {
				copyAddressFromSelectedServiceLine(sessionCustomer, selectedServiceLine);
			}
			if (StringUtils.isNotEmpty(sessionCustomer.getCareDmSupInd())){
				if ("I".equals(sessionCustomer.getCareDmSupInd())){
					sessionCustomer.setCustomercareDmSupInd(true);
				}
					
			}
			sessionCustomer.setCareCashOrderSignOffInd(false);
			sessionCustomer.setCareCashRegisterTimeInd(false);	
			String appDate = (String) request.getSession().getAttribute("appDate");
			if (StringUtils.isNotEmpty(appDate)){
				String careCashStartDate=service.getCareCashStartDate("CARECASH_START_DATE");
				if(StringUtils.isNotEmpty(careCashStartDate)){
				SimpleDateFormat careCashStartDatesdf = new SimpleDateFormat("yyyy-MM-dd");
				SimpleDateFormat appDatesdf = new SimpleDateFormat("dd/MM/yyyy");
					try {
						Date date = careCashStartDatesdf.parse(careCashStartDate);
						Date appDateDateFormat = appDatesdf.parse(appDate);
						if (appDateDateFormat.after(date)){
							sessionCustomer.setCareCashRegisterTimeInd(true);	
						}
					} catch (ParseException e) {

					}
				}	
			}
			//combine account
			String combineAcctStartDate=service.getCareCashStartDate("COMBINEACCOUNT_START_DATE");
			if(StringUtils.isNotEmpty(combineAcctStartDate)){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat appDatesdf = new SimpleDateFormat("dd/MM/yyyy");
				try {
					Date date = sdf.parse(combineAcctStartDate);
					Date appDateDateFormat = appDatesdf.parse(appDate);
					if (appDateDateFormat.after(date)){
						sessionCustomer.setCombineAccountRegisterTimeInd(true);	
					}
				} catch (ParseException e) {

				}			
			}
			
			if (superOrderDto != null) {
				String ocid =orderService.getOcidByOrderID(superOrderDto.getOrderId());
				if (StringUtils.isNotEmpty(ocid)){
					sessionCustomer.setCareCashOrderSignOffInd(true);
				}else{
					int noOfEmailSent=ordEmailReqService.isCareCashFormSent(superOrderDto.getOrderId());
					if (noOfEmailSent>0){
						sessionCustomer.setCareCashOrderSignOffInd(true);
					}
				}
			}

			//set appDateDay  studentPlan		
			sessionCustomer.setAppDateStr((String) request.getSession().getAttribute("appDate"));
				
		}
		
		return sessionCustomer;

	}

	public ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws ServletException, AppRuntimeException {

		
		
		String nextView = (String) request.getAttribute("nextView");
		logger.info("Next View: " + nextView);
		CustomerProfileDTO customer = (CustomerProfileDTO) command;
		
		customer.setDob(Utility.string2Date(customer.getDobStr()));
		
		if (customer.isStudentPlanSubInd()){
			customer.setMob0060OptOutInd(true);
		}

		if (" ".equals(customer.getCustFirstName())) {// add 20110610 " "
			customer.setCustFirstName(customer.getCustFirstName());
		} else {
			customer.setCustFirstName(customer.getCustFirstName().trim());
		}
		if (" ".equals(customer.getCustLastName())) {
			customer.setCustLastName(customer.getCustLastName());
		} else {
			customer.setCustLastName(customer.getCustLastName().trim());
		}
		
		if (StringUtils.isNotEmpty(customer.getIdDocNum())){
			if (customer.getIdDocNum().contains(" ")){
				customer.setIdDocNum(customer.getIdDocNum().trim());
			}
		}

		customer.setContactName(Utility.getContactName(
				customer.getTitle(), customer.getCustFirstName(), customer.getCustLastName()));

		if ((("".equalsIgnoreCase(customer.getCsPortalStatus()))||("H".equalsIgnoreCase(customer.getCsPortalStatus())))&&customer.isNoEmailFlag()) {
			String dummyEmail = customer.getCustLastName()+customer.getCustFirstName();
			dummyEmail = dummyEmail.replaceAll("[^A-Za-z]", "");
			dummyEmail = dummyEmail.toLowerCase();
			Date d = new Date();
			int appDate = (d.getYear()-100)*10000+(d.getMonth()+1)*100+d.getDate();
			String date = Integer.toString(appDate);
			dummyEmail = dummyEmail+date;
			dummyEmail = dummyEmail.substring(0, Math.min(dummyEmail.length(), 30));
			dummyEmail=dummyEmail+"@theclub.com.hk";
			customer.setDummyEmail(dummyEmail);
		}

		if (customer.isCsPortalBool()) {
			if( ("C".equalsIgnoreCase(customer.getCsPortalStatus()) && customer.isNoEmailFlag()) 
					|| "HC".equalsIgnoreCase(customer.getCsPortalStatus())||"BS".equalsIgnoreCase(customer.getIdDocType())  ){
				customer.setCsPortalInd("N");
			}
		} else {
			customer.setCsPortalInd("N");
		}
		
		if ("Y".equals(customer.getCsPortalInd())) {
			if (customer.getCsPortalStatus().contains("C")) {
				customer.setClubOptOut(false);
				customer.setHktClubOptOut(false);
				customer.setDummyEmail("");
				customer.setClubOptRea("");
				customer.setClubOptRmk("");
			}
			if (customer.getCsPortalStatus().contains("H")) {
				customer.setHktOptOut(false);
				customer.setHktClubOptOut(false);
			}
			if (!customer.getCsPortalStatus().contains("H") && !customer.getCsPortalStatus().contains("C")) {
				customer.setHktOptOut(customer.isHktClubOptOut());
				customer.setClubOptOut(customer.isHktClubOptOut());
			}
		} 
		
		if (customer.isNoEmailFlag()) {
			customer.setEmailAddr("");
		}
		
		ServiceLineDTO selectedServiceLine = (ServiceLineDTO) request.getSession().getAttribute("selectedServiceLineSession");
		if (selectedServiceLine != null
				&& "2".equals(customer.getAddrProofInd())) {
			copyAddressFromSelectedServiceLine(customer, selectedServiceLine);
		}
		
		if (customer.isNoBillingAddressFlag()) {
			// copy address to billing address
			customer.setBillingQuickSearch("");

			customer.setBillingFlat(customer.getFlat());
			customer.setBillingFloor(customer.getFloor());
			customer.setBillingLotNum(customer.getLotNum());
			customer.setBillingBuildingName(customer.getBuildingName());
			customer.setBillingStreetNum(customer.getStreetNum());
			customer.setBillingStreetName(customer.getStreetName());
			customer.setBillingStreetCatgDesc(customer.getStreetCatgDesc());
			customer.setBillingStreetCatgCode(customer.getStreetCatgCode());
			customer.setBillingSectionDesc(customer.getSectionDesc());
			customer.setBillingSectionCode(customer.getSectionCode());
			customer.setBillingDistrictDesc(customer.getDistrictDesc());
			customer.setBillingDistrictCode(customer.getDistrictCode());
			customer.setBillingAreaDesc(customer.getAreaDesc());
			customer.setBillingAreaCode(customer.getAreaCode());
			customer.setBillingUnlinkSectionFlag(customer.isUnlinkSectionFlag());
			customer.setBillingCustAddressFlag(customer.isCustAddressFlag());
		}
		
		if (StringUtils.isNotEmpty(customer.getMrtSelection())){
			request.getSession().setAttribute("mrtSelectionSession", customer.getMrtSelection());
			customer.setMrtSelection(null);
		}
		
		//if click ByPassValidation, order Type= BomWebPortalCcsConstant.ORD_CCS_TYPE_DRAF
		if (customer.isByPassValidation()){
			request.getSession().setAttribute("orderType", BomWebPortalCcsConstant.ORD_CCS_TYPE_DRAF);
			MobCcsSessionUtil.setSession(request, "orderType",	BomWebPortalCcsConstant.ORD_CCS_TYPE_DRAF);
		}
		
		if (StringUtils.isEmpty(customer.getSameAsCustInd()) && customer.isSameAsCust()){
			customer.setActualUserDTO(new ActualUserDTO());
		} else {
			customer.getActualUserDTO().setOrderId(customer.getOrderId());
			customer.getActualUserDTO().setOrderType("P");
			customer.getActualUserDTO().setMemberNum("0");
		}
		if ((("".equals(customer.getoBiptStatus()) ||"N".equals(customer.getoBiptStatus()))&& "Y".equals(customer.getCareStatus()))){
			if ("I".equals(customer.getCareOptInd())){
				if (customer.isCustomercareDmSupInd()){
					customer.setCareDmSupInd("I");
				}else{
					customer.setCareDmSupInd("O");
				}
			}else{
				customer.setCareDmSupInd("");
			}
		}else if ("N".equals(customer.getCareStatus())){
			customer.setCareOptInd("");
			customer.setCareDmSupInd("");
		}
		
		
		
		//check for data amendment in CustomerPrfileDTO and order contains OCID
		OrderDTO sessionOrderDTO = (OrderDTO) MobCcsSessionUtil.getSession(request, "orderDTO");
		if (sessionOrderDTO == null) {
			sessionOrderDTO = (OrderDTO) request.getSession().getAttribute("OrderDto");
		}
		
		if (sessionOrderDTO != null 
				&& StringUtils.isNotBlank(sessionOrderDTO.getOrderId())) {
			BomSalesUserDTO user = (BomSalesUserDTO) request.getSession().getAttribute("bomsalesuser");
			String channelId = String.valueOf(user.getChannelId());
			if (channelId.equals("10") || channelId.equals("11") || StringUtils.isNotBlank(sessionOrderDTO.getOcid())) {
				CustomerProfileDTO customerProfileDTO = service.getCustomerProfile(sessionOrderDTO.getOrderId());
				try {
					boolean same = BeanUtilsHelper.compareSpecificProperties(customerProfileDTO, customer, toCheckProperties);
					if (!same) {
						customer.setAmend(true);
					} else {
						customer.setAmend(false);
					}
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
			}
		}
		//Combine Account
		
		if("current".equalsIgnoreCase(customer.getAcctType())){
			customer.setIsNew("N");
		}
		else if ("new".equalsIgnoreCase(customer.getAcctType())){
			customer.setIsNew("Y");	
			customer.setAcctInfo("");
			customer.setActiveMobileNum("");
			customer.setAcctNum("");
			customer.setAcctName("");
			customer.setBomCustNum("");
			customer.setMobCustNum("");
			customer.setSrvNum("");
			customer.setBillPeriod("");
		}
		//Combine Account
				
		// save session

		//customer.setBrand("1");
		
		request.getSession().setAttribute("customer", customer);
		MobCcsSessionUtil.setSession(request, "customer", customer);
		
		request.getSession().setAttribute("numType", customer.getNumType()); //DENNIS MIP3
		request.getSession().setAttribute("simType", customer.getSimType());
		request.getSession().setAttribute("brandType", customer.getBrand());
		
		request.getSession().setAttribute("acctName", customer.getAcctName());//Combine Account MIP4
		request.getSession().setAttribute("acctType", customer.getAcctType());
		request.getSession().setAttribute("srvNum", customer.getSrvNum());
		
		/* test uid*/
		String attrUid=(String)request.getParameter("sbuid");
		ModelAndView modelAndView =  new ModelAndView(new RedirectView(nextView));
		modelAndView.addObject("sbuid", attrUid);
		/* test uid*/
		return modelAndView;

	}

	protected Map referenceData(HttpServletRequest request) throws Exception {
		logger.info("ReferenceData called");
		
		Map referenceData = new HashMap<String, List<String>>();
		
		BomSalesUserDTO user = (BomSalesUserDTO) request.getSession().getAttribute("bomsalesuser");
		String channelId = String.valueOf(user.getChannelId());
		String category = user.getCategory();
		referenceData.put("channelId", channelId);
		referenceData.put("category", category);
		
		OrderDTO orderDto = (OrderDTO)MobCcsSessionUtil.getSession(request, "orderDTO");
	
		
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

		List<String> lobList = new ArrayList<String>();
		lobList.add("Netvigator");
		lobList.add("Fixed Line");
		lobList.add("now TV");
		lobList.add("PCCW Mobile");
		referenceData.put("lobList", lobList);
		
		List<String> dummyTextList = new ArrayList<String>();
		dummyTextList.add("DUMMY");
		dummyTextList.add("DUM");
		dummyTextList.add("TEST");
		dummyTextList.add("TEMP");
		dummyTextList.add("#");
		dummyTextList.add("@");
		dummyTextList.add("*");
		dummyTextList.add("?");
		dummyTextList.add("!");
		referenceData.put("dummyTextList", dummyTextList); //add by dennis, HKT registration 

		/*Map<String, String> streetCatgList = new LinkedHashMap<String, String>();
		for (int i = 0; i < LookupTableBean.getInstance()
				.getAddressCategoryList().size(); i++) {
			streetCatgList.put(
					LookupTableBean.getInstance().getAddressCategoryList()
							.get(i).getCategoryCode(),
					LookupTableBean.getInstance().getAddressCategoryList()
							.get(i).getCategoryDesc());
		}
		referenceData.put("streetCatgList", streetCatgList);*/
				
		referenceData.put("orderDto", (OrderDTO) MobCcsSessionUtil.getSession(request, "orderDTO"));
		referenceData.put("workStatus", request.getSession().getAttribute("workStatus"));		
		referenceData.put("streetCatgList", LookupTableBean.getInstance().getAddressCategoryList());
		referenceData.put("workStatus", (String) MobCcsSessionUtil.getSession(request, "workStatus"));
		List<MobBillMediaDTO> billMediaList  = LookupTableBean.getInstance().getBillMediaOption(); //Paper bill Athena 20130925
		if(user.getChannelId() == 10 && "FRONTLINE".equals(user.getCategory())){
			List<MobBillMediaDTO> dsBillMediaList = new ArrayList<MobBillMediaDTO> ();
			for(MobBillMediaDTO billMedia: billMediaList) {
				if (!billMedia.getCodeDesc().contains("WAIVED")){
					dsBillMediaList.add(billMedia);
				}
			}
			billMediaList = dsBillMediaList;
		}
		referenceData.put("billMediaList", billMediaList);//Paper bill Athena 20130925
		
		List<DepositLkupDTO> depositLkupList = depositService.findAllDepositLkup(getAppDate(request));
		Map<String, DepositLkupDTO> customerProfileDeposit = new HashMap<String,DepositLkupDTO>();
		for (DepositLkupDTO depositLkup: depositLkupList) {
			if ("DEP0001".equals(depositLkup.getDepositCd())) {
				customerProfileDeposit.put("DEP0001", depositLkup);
			} else if ("DEP0007".equals(depositLkup.getDepositCd())) {
				customerProfileDeposit.put("DEP0007", depositLkup);
			}
		}
		//Map<String,DepositLkupDTO> depositLkup = Op.on(depositLkupList).forEach()..zipKeysBy(Get.s("depositCd")).get();
		referenceData.put("depositLkup", customerProfileDeposit);
		

		CustomerProfileDTO origCustomer = (CustomerProfileDTO)MobCcsSessionUtil.getSession(request, "origCustAddress");
		referenceData.put("origCustAddress", origCustomer);		
		
		List<CodeLkupDTO> clubOptReaList  = (List<CodeLkupDTO>)LookupTableBean.getInstance().getCodeLkupList().get("OPT_OUT_REASON");
		referenceData.put("clubOptReaList", clubOptReaList);	
		
		String appDate = (String) request.getSession().getAttribute("appDate");
		referenceData.put("appDateString", appDate);
		/*long currentDateTime = System.currentTimeMillis();	    
		Date today = new Date(currentDateTime);
		referenceData.put("today", Utility.date2String(today, "dd/MM/yyyy"));	*/
		

		return referenceData;
	}
	
	private void copyAddressFromSelectedServiceLine(CustomerProfileDTO customer, ServiceLineDTO selectedServiceLine) {
		if (selectedServiceLine != null) {
			if (!"--".equals(selectedServiceLine.getServiceNum())) {
				if (Utility.validatePhoneNum(selectedServiceLine.getServiceNum()) && Utility.validateHKPhonePreFix(selectedServiceLine.getServiceNum())) {
					customer.setContactPhone(selectedServiceLine.getServiceNum());
				}
			}
			customer.setCustAddressFlag(true);
			if (!"--".equals(selectedServiceLine.getUnitNum()))
				customer.setFlat(selectedServiceLine.getUnitNum());
			if (!"--".equals(selectedServiceLine.getFloorNum()))
				customer.setFloor(selectedServiceLine.getFloorNum());
			if (!"--".equals(selectedServiceLine.getHlotNum()))
				customer.setLotNum(selectedServiceLine.getHlotNum());
			if (!"--".equals(selectedServiceLine.getBuildName()))
				customer.setBuildingName(selectedServiceLine.getBuildName());
			if (!"--".equals(selectedServiceLine.getStreetNum()))
				customer.setStreetNum(selectedServiceLine.getStreetNum());
			if (!"--".equals(selectedServiceLine.getStreetName()))
				customer.setStreetName(selectedServiceLine.getStreetName());
			if (!"--".equals(selectedServiceLine.getStCatgCd()))
				customer.setStreetCatgCode(selectedServiceLine.getStCatgCd());

			String streetCatgDesc = customerInformationService.getStreetCatgDescFromStCatgCd(selectedServiceLine.getStCatgCd());
			String sectionDesc = customerInformationService.getSectDescFromSectCd(selectedServiceLine.getSectCd());
			String districtDesc = customerInformationService.getDistDscFromDistrNum(selectedServiceLine.getDistrNum());
			String areaDesc = customerInformationService.getAreaDescFromDistrNum(selectedServiceLine.getDistrNum());
			String areaCd = customerInformationService.getAreaCdFromDistrNum(selectedServiceLine.getDistrNum());

			customer.setStreetCatgDesc(streetCatgDesc);
			if (!"--".equals(selectedServiceLine.getSectCd()))
				customer.setSectionCode(selectedServiceLine.getSectCd());
			customer.setSectionDesc(sectionDesc);
			if (!"--".equals(selectedServiceLine.getDistrNum()))
				customer.setDistrictCode(selectedServiceLine.getDistrNum());
			customer.setDistrictDesc(districtDesc);
			if (!"--".equals(areaCd))
				customer.setAreaCode(areaCd);
			customer.setAreaDesc(areaDesc);
			
			//if ("MOB".equals(selectedServiceLine.getSystemId())) {
			//	customer.setLob("PCCW Mobile");
			customer.setLob(selectedServiceLine.getSystemId());

			customer.setServiceNum(selectedServiceLine.getServiceNum());
			
			customer.setPhInd("N");
		}		
	}
	
	private CustomerProfileDTO getSessionCustomer(HttpServletRequest request) {
		// three session for mobds/mob/mobccs

		String appMode = (String)request.getSession().getAttribute("appMode");
		CustomerProfileDTO sessionCustomer =null;
		if ("shop".equals(appMode) || "directsales".equals(appMode) ) {
			sessionCustomer=(CustomerProfileDTO) request.getSession().getAttribute("customer");
		}else{
			sessionCustomer = (CustomerProfileDTO) MobCcsSessionUtil.getSession(request, "customer");
		}
		
		return sessionCustomer;
	}
	
	private Date getAppDate(HttpServletRequest request) {
		Date appDate = null;
		if (request.getSession().getAttribute("OrderDTO") instanceof OrderDTO) {
			appDate = ((OrderDTO) request.getSession().getAttribute("OrderDTO")).getAppInDate();
		} else if (MobCcsSessionUtil.getSession(request, "orderDTO") instanceof OrderDTO) {
			appDate = ((OrderDTO)MobCcsSessionUtil.getSession(request, "orderDTO")).getAppInDate();
		}
		if (appDate == null) appDate = SystemTime.asDate();
		
		return appDate;
	}

}
