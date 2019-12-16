package com.bomwebportal.mob.ccs.web;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

import com.bomwebportal.bean.LookupTableBean;
import com.bomwebportal.dto.CustomerProfileDTO;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.mob.ccs.dto.ui.CustomerProfileUI;
import com.bomwebportal.mob.ccs.util.MobCcsSessionUtil;
import com.bomwebportal.service.CustomerProfileService;
import com.bomwebportal.util.Utility;

public class MobCcsCustomerProfileController extends SimpleFormController{
	protected final Log logger = LogFactory.getLog(getClass());
	
	private CustomerProfileService service;
	
	public CustomerProfileService getService() {
		return service;
	}

	public void setService(CustomerProfileService service) {
		this.service = service;
	}

	public MobCcsCustomerProfileController() {
	}
	
	
	public Object formBackingObject(HttpServletRequest request) throws ServletException{
	
		CustomerProfileUI sessionCustomer = (CustomerProfileUI)MobCcsSessionUtil.getSession(request, "customer");//get session from hashMap session
		if(sessionCustomer==null){
			CustomerProfileUI customer = new CustomerProfileUI();
			//Set default address proof indicator to "No Address Proof"
			customer.setAddrProofInd("1");
			//customer.setIgnoreCustomerCheck(true);
			return customer;
		}else{
			
			if (sessionCustomer.isNoBillingAddressFlag()){ 
				sessionCustomer.setBillingQuickSearch("");
				
				sessionCustomer.setBillingFlat("");//edit 20110601
				sessionCustomer.setBillingFloor("");//edit 20110601
				sessionCustomer.setBillingLotNum("");//edit 20110601
				sessionCustomer.setBillingBuildingName("");//edit 20110601
				sessionCustomer.setBillingStreetNum("");//edit 20110601
				sessionCustomer.setBillingStreetName("");//edit 20110601
				sessionCustomer.setBillingStreetCatgDesc("");//edit 20110601
				sessionCustomer.setBillingStreetCatgCode("");//edit 20110601
				sessionCustomer.setBillingSectionDesc("");//edit 20110601
				sessionCustomer.setBillingSectionCode("");//edit 20110601
				sessionCustomer.setBillingDistrictDesc("");//edit 20110601
				sessionCustomer.setBillingDistrictCode("");//edit 20110601
				sessionCustomer.setBillingAreaDesc("");//edit 20110601
				sessionCustomer.setBillingAreaCode("");//edit 20110601
				
				sessionCustomer.setBillingUnlinkSectionFlag(false);
				sessionCustomer.setBillingCustAddressFlag(false);
			}
			
			return sessionCustomer;
		}
	}
	
	
	public ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) 
		throws ServletException, AppRuntimeException {
		
	
			String nextView = (String)request.getAttribute("nextView");
			logger.info("Next View: " + nextView);
			CustomerProfileUI customer = (CustomerProfileUI)command;
			
			customer.setDob(Utility.string2Date(customer.getDobStr()));
						
			
			if (" ".equals(customer.getCustFirstName())){//add 20110610 " " spqce case
				customer.setCustFirstName(customer.getCustFirstName());
			}else{
				customer.setCustFirstName(customer.getCustFirstName().trim());//add trim 20110607
			}
			if (" ".equals(customer.getCustLastName())){
				customer.setCustLastName(customer.getCustLastName());//add trim 20110607
			}else{
				customer.setCustLastName(customer.getCustLastName().trim());//add trim 20110607
			}
			
			customer.setContactName(Utility.getContactName(customer.getTitle(), customer.getCustFirstName(), customer.getCustLastName()));//add trim 20110607
									
			//clean email address, (when click noEmail check box ), add by wilson 20110302
			if (customer.isNoEmailFlag()){
				customer.setEmailAddr("");
			}

			if (customer.isNoBillingAddressFlag()){ 
				customer.setBillingQuickSearch("");
				
				customer.setBillingFlat(customer.getFlat());//edit 20110527 copy address to billing address
				customer.setBillingFloor(customer.getFloor());//edit 20110527
				customer.setBillingLotNum(customer.getLotNum());//edit 20110527
				customer.setBillingBuildingName(customer.getBuildingName());//edit 20110527
				customer.setBillingStreetNum(customer.getStreetNum());//edit 20110527
				customer.setBillingStreetName(customer.getStreetName());//edit 20110527
				customer.setBillingStreetCatgDesc(customer.getStreetCatgDesc());//edit 20110527
				customer.setBillingStreetCatgCode(customer.getStreetCatgCode());//edit 20110527
				customer.setBillingSectionDesc(customer.getSectionDesc());//edit 20110527
				customer.setBillingSectionCode(customer.getSectionCode());//edit 20110527
				customer.setBillingDistrictDesc(customer.getDistrictDesc());//edit 20110527
				customer.setBillingDistrictCode(customer.getDistrictCode());//edit 20110527
				customer.setBillingAreaDesc(customer.getAreaDesc());//edit 20110527
				customer.setBillingAreaCode(customer.getAreaCode());//edit 20110527
				
				customer.setBillingUnlinkSectionFlag(customer.isUnlinkSectionFlag());
				customer.setBillingCustAddressFlag(customer.isCustAddressFlag());
			}

			//request.getSession().setAttribute("customer", customer);
			
			
			
			
			
			
			
CustomerProfileDTO customerInfoDto = new CustomerProfileDTO();
try {
	BeanUtils.copyProperties(customerInfoDto, customer);
} catch (IllegalAccessException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
} catch (InvocationTargetException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}		










			//save session
			MobCcsSessionUtil.setSession(request, "customer", customer);// save session to hashMap session
			return new ModelAndView(new RedirectView(nextView));
	
	}	
	
	//SimpleFormController
	protected Map referenceData(HttpServletRequest request) throws Exception {
		logger.info("ReferenceData called"); 
		Map referenceData = new HashMap<String, List<String>>();
		//Map<String, List<String>> referenceData = new HashMap<String, List<String>>();
		//title
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
		
		/*Map<String,String> streetCatgList = new LinkedHashMap<String,String>();
		for( int i=0; i<LookupTableBean.getInstance().getAddressCategoryList().size(); i++){
			streetCatgList.put(LookupTableBean.getInstance().getAddressCategoryList().get(i).getCategoryCode(), LookupTableBean.getInstance().getAddressCategoryList().get(i).getCategoryDesc());
		}*/
		//referenceData.put("streetCatgList", streetCatgList);
		referenceData.put("streetCatgList", LookupTableBean.getInstance().getAddressCategoryList());
		
		

		return referenceData;
	}
	
	



}
