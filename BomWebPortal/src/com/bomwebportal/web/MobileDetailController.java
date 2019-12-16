package com.bomwebportal.web;

//import org.springframework.validation.BindException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
//import org.springframework.web.servlet.view.RedirectView;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.bomwebportal.bean.LookupTableBean;
import com.bomwebportal.dto.BasketDTO;
import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.dto.CustomerInformationQuotaDTO;
import com.bomwebportal.dto.CustomerProfileDTO;
import com.bomwebportal.dto.MnpDTO;
import com.bomwebportal.dto.ModelDTO;
import com.bomwebportal.mob.ccs.util.MobCcsSessionUtil;
import com.bomwebportal.service.CustomerInformationQuotaService;
import com.bomwebportal.service.MobileDetailService;
import com.bomwebportal.util.Utility;

//import com.bomwebportal.dto.BomSalesUserDTO;

public class MobileDetailController implements Controller {

	protected final Log logger = LogFactory.getLog(getClass());

	private MobileDetailService service;
	private CustomerInformationQuotaService customerInformationQuotaService;

	public MobileDetailService getService() {
		return service;
	}

	public void setService(MobileDetailService service) {
		this.service = service;
	}
	public CustomerInformationQuotaService getCustomerInformationQuotaService() {
		return customerInformationQuotaService;
	}

	public void setCustomerInformationQuotaService(
			CustomerInformationQuotaService customerInformationQuotaService) {
		this.customerInformationQuotaService = customerInformationQuotaService;
	}

	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String locale = RequestContextUtils.getLocale(request).toString();

		String appMode = (String) request.getSession().getAttribute("appMode");
		// two session for mob/mobccs
		CustomerProfileDTO sessionCustomer = (CustomerProfileDTO) request
				.getSession().getAttribute("customer");

		if ("mobccs".equals(appMode)) {
			sessionCustomer = (CustomerProfileDTO) MobCcsSessionUtil
					.getSession(request, "customer");
		}
		String phInd="";
		try{
			 phInd = sessionCustomer.getPhInd();
			
		}catch (Exception e) {
			phInd ="N";
		}

		BomSalesUserDTO user = (BomSalesUserDTO) request.getSession()
				.getAttribute("bomsalesuser");
		//String channelId = "" + user.getChannelId();
		int channelId=(user.getChannelId() == 11 ? 10 : user.getChannelId());//get channelId , convert CFM(66), CPA(68) => IBS, OBS(2)
		
		if (LookupTableBean.getInstance().getAllowFalloutChannelList()!=null && LookupTableBean.getInstance().getAllowFalloutChannelList().length>0 && Utility.isContainString(LookupTableBean.getInstance().getAllowFalloutChannelList(), user.getChannelCd())){
			channelId =2;//convert channel ID
		}
		// Quota info , get Quota from session
		List<CustomerInformationQuotaDTO> existQuotaList = null;
		if (sessionCustomer != null) {
			existQuotaList = (List<CustomerInformationQuotaDTO>) MobCcsSessionUtil.getSession(request, "customerInfoQuotaOverLimitList");
			if (existQuotaList == null) {
				logger.info("get Quota from service....");
				// get Quota overLimitDTOList
				existQuotaList = customerInformationQuotaService
						.getCustomerInformationQuotaOverLimitDTOList(
								sessionCustomer.getIdDocType(),
								sessionCustomer.getIdDocNum(),
								sessionCustomer.getOrderId());
				
			} else {
				logger.info("Quota exist e....");
			}
		}
		String appDate = (String) request.getSession().getAttribute("appDate");

		logger.info("locale:" + locale);
		logger.info("MobileDetailController.handleRequest() Start");

		String selectedBrandId = request.getParameter("brand");
		String selectedModelId = request.getParameter("model");
		String selectedColorId = request.getParameter("color");

		String customerTier = request.getParameter("customertier");
		
		
    	MnpDTO mnpDTO = (MnpDTO) request.getSession().getAttribute("MNP");
    	logger.info("customerTier request: " + customerTier);
    	
    	if (sessionCustomer != null){
    		sessionCustomer.setCustomerTier(customerTier);
    		request.getSession().setAttribute("customer", sessionCustomer);
    	} 
		
		String basketType = request.getParameter("baskettype");
		request.getSession().setAttribute("baskettypeSession", basketType);//save session for RS, wilson 20120417PRD
		String rpType = request.getParameter("rptype");
		String callList = request.getParameter("callList");
		String callListDesc = request.getParameter("callListDesc");
		/**********************************/
		
		if (!"NONE".equalsIgnoreCase(callList) && callListDesc != null && !callListDesc.isEmpty()) {
			request.getSession().setAttribute("callListSession", new String[]{callList, callListDesc});
		}
		
		// List<ModelDTO> modelListAll=new ArrayList<ModelDTO>(); //from menu
		// modelListAll=service.getModelList(locale, "", "1", customerTier,
		// basketType);//no brand means load all info

		// selectedBrandId==NONE, assign value=""
		if (("NONE").equals(selectedBrandId) || selectedBrandId == null) {
			// logger.info("==============selectedBrandId==NONE=====================");
			selectedBrandId = "";
		}

		if (("NONE").equals(selectedModelId) || selectedModelId == null) {
			// logger.info("==============selectedModelId==NONE=====================");
			selectedModelId = "";
		}

		if (selectedColorId == null) {
			// logger.info("==============selectedModelId==NONE=====================");
			selectedColorId = "";
		}
		// get select list from DB
		// List<BrandDTO> brandList=new ArrayList<BrandDTO>();
		// List<ModelDTO> modelList=new ArrayList<ModelDTO>();
		List<BasketDTO> basketList = new ArrayList<BasketDTO>();

		// for test get sim only basket
		List<BasketDTO> simOnlybasketList = new ArrayList<BasketDTO>();

		// for test get sim only basket
		ModelDTO mobileDetail = new ModelDTO();

		// call service to get data from DB
		// brandList= service.getBrandList(locale, "1", customerTier,
		// basketType);//channelId =1 fix
		// modelList= service.getModelList(locale, selectedBrandId, "1",
		// customerTier,basketType);//channelId =1 fix

		// only select model, not select brand, ==>get brand id
		// selectedModelId!="" && selectedBrandId==""
		/*
		 * if (!"".equals(selectedModelId) && "".equals(selectedBrandId) ){ for
		 * ( int i=0; i<modelListAll.size(); i++){ if
		 * (modelList.get(i).getModelId().equals(selectedModelId)){
		 * selectedBrandId=modelListAll.get(i).getBrandId(); } } }
		 */

		// get mobile detail
		if ("NONE".equals(callList) || "".equals(callList) || callList==null ) {//add callList==null by wilson 20120210
			// get mobile detail, from cautomerTier
			logger.debug("get mobile detail, from cautomerTier........101");

			if (!"".equals(selectedModelId)) {
				if (!"".equals(selectedBrandId) && !"".equals(selectedModelId)) {
					mobileDetail = service.getMobileDetail(locale,
							selectedBrandId, selectedModelId, appDate);
					// set default color
					if (mobileDetail.getColorDto().size() > 0
							&& "".equals(selectedColorId)) {
						selectedColorId = mobileDetail.getColorDto().get(0)
								.getColorId();
					}
				}

				if (selectedBrandId != null && selectedModelId != null
						&& selectedColorId != null) {

					basketList = service.getBasketList(locale, selectedBrandId,
							selectedModelId, selectedColorId,  channelId+"",
							customerTier, basketType, appDate, existQuotaList,
							sessionCustomer.getBrand(), sessionCustomer.getSimType());
				}
			} else { // get simonly
				simOnlybasketList = service.getSimOnlyBasketList(locale,
						rpType, basketType, customerTier, channelId+"", appDate,existQuotaList,
						sessionCustomer.getBrand(), sessionCustomer.getSimType());
				basketList.addAll(simOnlybasketList); // for testing
			}

		} else {
			System.out.println("get basket list from callList ........201");
			// get basket list from call list
			if (!"".equals(selectedModelId)) {
				if (!"".equals(selectedBrandId) && !"".equals(selectedModelId)) {
					mobileDetail = service.getCallListMobileDetail(locale,
							channelId+"", callList, basketType, rpType,
							selectedBrandId, selectedModelId, appDate);
					// set default color
					if (mobileDetail.getColorDto().size() > 0
							&& "".equals(selectedColorId)) {
						selectedColorId = mobileDetail.getColorDto().get(0)
								.getColorId();
					}
				}

				if (selectedBrandId != null && selectedModelId != null
						&& selectedColorId != null) {

					basketList = service.getCallListBasketList(locale,
							selectedBrandId, selectedModelId, selectedColorId,
							channelId+"", customerTier, basketType, rpType,
							callList, appDate, existQuotaList, 
							sessionCustomer.getBrand(), sessionCustomer.getSimType());
					// IPHONE4, IPAD2
					// for each basketlist check quota

					//
				}
			} else {
				simOnlybasketList = service.getCallListSimOnlyBasketList(
						locale, rpType, basketType, callList, channelId+"",
						appDate, existQuotaList, 
						sessionCustomer.getBrand(), sessionCustomer.getSimType());
				basketList.addAll(simOnlybasketList);
			}
		}

		// return new ModelAndView
		ModelAndView myView = new ModelAndView("mobiledetail");
		// myView.addObject( "brandList", brandList);
		// myView.addObject( "modelList", modelList);
		myView.addObject("mobileDetail", mobileDetail);
		myView.addObject("basketList", basketList);
		// selectedBrandId =mobileDetail.getBrandId();
		// return value to JSP, for select default option use
		myView.addObject("selectedBrandId", selectedBrandId);
		myView.addObject("selectedModelId", selectedModelId);
		myView.addObject("selectedColorId", selectedColorId);

		myView.addObject("customerTier", customerTier);
		myView.addObject("callList", callList);
		myView.addObject("basketType", basketType);
		myView.addObject("rpType", rpType);
		myView.addObject("phInd", phInd);

		// myView.addObject( "simOnlybasketList", simOnlybasketList); // for
		// test only
		// myView.addObject( "mobileScriptArrayString",
		// getMobileScriptArrayLine0(modelListAll)+getMobileScriptArray(modelListAll));
		// //test return string
		/* test uid*/
		String attrUid=(String)request.getParameter("sbuid");
		myView.addObject("sbuid", attrUid);
		/* test uid*/
		return myView;
	}

	// create java script using Array text
	/*
	 * public String getMobileScriptArray(List<ModelDTO> modelList){ String
	 * result=""; String brandId="";
	 * 
	 * int j=1;
	 * 
	 * for ( int i=0; i<modelList.size(); i++){ ModelDTO tempDTO =
	 * modelList.get(i); if (brandId!="" &&
	 * !brandId.equals(tempDTO.getBrandId())){ result +="\"]\n"; j++; } if
	 * (!brandId.equals(tempDTO.getBrandId())){
	 * 
	 * result +="model["+j+"]=[\"" ; brandId=tempDTO.getBrandId(); }
	 * 
	 * if (brandId.equals(tempDTO.getBrandId())){ result
	 * +=tempDTO.getModelId()+"|"+tempDTO.getModelName()+"\",\""; brandId
	 * =tempDTO.getBrandId(); } }
	 * 
	 * if (!"".equals(result)){ result+="\"]\n";; }
	 * 
	 * result =result.replace(",\"\"]", "]"); return result; }
	 * 
	 * //create java script using Array first line text public String
	 * getMobileScriptArrayLine0(List<ModelDTO> modelList){ String result="";
	 * result +="model[0]=[\"" ;
	 * 
	 * for ( int i=0; i<modelList.size(); i++){ ModelDTO tempDTO =
	 * modelList.get(i); result
	 * +=tempDTO.getModelId()+"|"+tempDTO.getModelName()+"\",\""; } if
	 * (!"".equals(result)){ result+="\"]\n";; result =result.replace(",\"\"]",
	 * "]"); } return result; }
	 */

}
