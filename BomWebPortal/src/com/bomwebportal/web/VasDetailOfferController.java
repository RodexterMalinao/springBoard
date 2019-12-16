package com.bomwebportal.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.dto.CustomerProfileDTO;
import com.bomwebportal.dto.ModelDTO;
import com.bomwebportal.dto.SuperOrderDTO;
import com.bomwebportal.dto.VasDetailDTO;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.service.MobileDetailService;
import com.bomwebportal.service.VasDetailService;



public class VasDetailOfferController  extends SimpleFormController{

    protected final Log logger = LogFactory.getLog(getClass());
    
    private VasDetailService service;
    private MobileDetailService mobileDetailService;
    
    public VasDetailService getService() {
		return service;
	}
	public void setService(VasDetailService service) {
		this.service = service;
	}
	
	public void setMobileDetailService(MobileDetailService mobileDetailService) {
		this.mobileDetailService = mobileDetailService;
	}
	public MobileDetailService getMobileDetailService() {
		return mobileDetailService;
	}
	
	protected Map<String, Object> referenceData(HttpServletRequest request) throws Exception {
		logger.info("ReferenceData called");
		
		//Comment & modify by herbert 20110822, standardize the log
		//System.out.print("==>ReferenceData called\n");
		logger.debug("==>ReferenceData called\n");
		//START, record the selection, add by wilson 20110218
		BomSalesUserDTO user = (BomSalesUserDTO) request.getSession().getAttribute("bomsalesuser");
		String channelId = String.valueOf(user.getChannelId());
		
		if (user !=null){
			channelId= ""+user.getChannelId();
		}
		
		
		String appDate = (String) request.getSession().getAttribute("appDate");
		String[] vasItem = (String[])request.getParameterValues("vasitem");
		if (vasItem != null){
			
			request.getSession().setAttribute("selectedVasItemList", vasItem);
		}
		//END, add by wilson 20110218
		
		
		Map<String, Object> referenceData = new HashMap<String, Object>();

		ModelDTO mobileDetail= new  ModelDTO();
		String locale = RequestContextUtils.getLocale(request).toString();
		
    	String selectedBrandId=request.getParameter("brand");
    	String selectedModelId=request.getParameter("model");
    //	String selectedColorId=request.getParameter("color");
    	String selectedBasketId=request.getParameter("basket");
    	String orderId= request.getParameter("orderId");
    	logger.info("selectedBasketId:======>"+selectedBasketId);
    	logger.info("selectedBrandId:======>"+selectedBrandId);
    	logger.info("selectedModelId:======>"+selectedModelId);
    	//logger.info("selectedColorId:======>"+selectedColorId);
    	String selectedColorName="";
    	
    	String pageMessage="";
    	
    	
    	//get Model detail --mark 20110426
    	/*if (!"".equals(selectedBrandId) && ! "".equals(selectedModelId) ){
        	
    	//if (!"".equals(selectedBrandId) && ! "".equals(selectedModelId) && !"".equals(selectedColorId)&& !"".equals(selectedModelId)){
    	
    		mobileDetail =mobileDetailService.getMobileDetail(locale,selectedBrandId, selectedModelId );
    		//get color name
    		for (int i=0; i<mobileDetail.getColorDto().size(); i++){
    			if (selectedColorId.equals(mobileDetail.getColorDto().get(i).getColorId())){
    				selectedColorName =mobileDetail.getColorDto().get(i).getColorName();
    			}
    		}

    	}else{
    		pageMessage ="please select Brand, Model and Color first!!";
    	}*/
    
    	String selectedColorId ="";
    	if (!"".equals(selectedBasketId) ){
        	
    		mobileDetail =mobileDetailService.getMobileDetail(locale,selectedBasketId );
    		if (mobileDetail !=null){
	    		selectedColorName =mobileDetail.getColorDto().get(0).getColorName();
	    		//get color name
	    		/*for (int i=0; i<mobileDetail.getColorDto().size(); i++){
	    			if (selectedColorId.equals(mobileDetail.getColorDto().get(i).getColorId())){
	    				selectedColorName =mobileDetail.getColorDto().get(i).getColorName();
	    			}
	    		}*/
	    		
	    		 selectedBrandId=mobileDetail.getBrandId();
	        	 selectedModelId=mobileDetail.getModelId();
	        	 selectedColorId =mobileDetail.getColorDto().get(0).getColorId();
    		}

    	}else{
    		pageMessage ="please select basket first!!";
    	}
    	
    	//get  vasDetailList
    	SuperOrderDTO superOrderDto = (SuperOrderDTO) request.getSession().getAttribute("superOrderDto");
    	List<VasDetailDTO> vasDetailList=new ArrayList<VasDetailDTO>(); //from menu
    	List<VasDetailDTO> vasHSRPList=new ArrayList<VasDetailDTO>(); //from menu
    	List<VasDetailDTO> vasBFEEList=new ArrayList<VasDetailDTO>(); 
    	VasDetailDTO noneDisplayItem = new VasDetailDTO();//add by wilson 20110426
    	String basketDisplayTitle="";
    	if (!"".equals(selectedBasketId) && selectedBasketId !=null ){
    	
    		//add by wilson 20110121
    		String [] selectedItemList = (String [])request.getSession().getAttribute("selectedVasItemList");
    		if (selectedItemList ==null){
    			selectedItemList =null;
    		}
    		
    		CustomerProfileDTO sessionCustomer=(CustomerProfileDTO) request.getSession().getAttribute("customer");		
    		
			// MIP.P4 modification
			String nature = service.getBasketAttribute(selectedBasketId, appDate).getNature();
    		vasHSRPList = service.getRPHSRPList(locale, selectedBasketId, appDate, superOrderDto.getOrderShopCd(), sessionCustomer.getBrand(), sessionCustomer.getSimType(), nature);
    		
    		for (int i=0; i<vasHSRPList.size(); i++){//get offer product into
    			//List<ProductDTO> productList= ;
    			vasHSRPList.get(i).setProductList(service.getBomProductList(vasHSRPList.get(i).getItemId())) ;
    			
    		}

    		// MIP.P4 modification
    		// String nature = service.getBasketAttribute(selectedBasketId, appDate).getNature();
    		vasDetailList=service.getVasDetailList(channelId,locale, selectedBasketId,selectedItemList, "ITEM_SELECT" , appDate, superOrderDto.getOrderShopCd(), sessionCustomer.getBrand(), sessionCustomer.getSimType(), nature);
    		//vasDetailList=service.getVasDetailtList(locale, selectedBasketId, selectedItemList,  "ITEM_SELECT" ); //
    		
    		for (int i=0; i<vasDetailList.size(); i++){//get offer product into
    			//List<ProductDTO> productList= ;
    			vasDetailList.get(i).setProductList(service.getBomProductList(vasDetailList.get(i).getItemId())) ;
    			
    		}
    		//vasBFEEList=service.getBFEEList(locale, selectedBasketId);
    		for (int i=0; i<vasBFEEList.size(); i++){//get offer product into
    			//List<ProductDTO> productList= ;
    			vasBFEEList.get(i).setProductList(service.getBomProductList(vasBFEEList.get(i).getItemId())) ;
    			
    		}
    		basketDisplayTitle =service.getBasketDisplayTitle(locale, selectedBasketId);
    		
    		noneDisplayItem.setProductList(service.getBomProductNonDisplayItemList(selectedBasketId));//add by wilson 20110426
    		
    		
    	}
    	//return new ModelAndView
 	
    	referenceData.put( "selectedBrandId", selectedBrandId); 
    	referenceData.put( "selectedModelId", selectedModelId); 
    //	referenceData.put( "selectedColorId", selectedColorId); 
    	referenceData.put( "selectedBasketId", selectedBasketId); 
    	referenceData.put( "selectedColorName", selectedColorName);
    	referenceData.put( "mobileDetail", mobileDetail);
    	referenceData.put( "pageMessage", pageMessage);
    	referenceData.put( "vasDetailList", vasDetailList); 
    	referenceData.put( "vasHSRPList", vasHSRPList); 
    	referenceData.put( "basketDisplayTitle", basketDisplayTitle); //add by wilson 20110125
    	referenceData.put( "vasBFEEList", vasBFEEList); //add by wilson 20110125
    	referenceData.put( "noneDisplayItem", noneDisplayItem); //add by wilson 20110426
	
		return referenceData;
	}

    /////START ////////////////////////////////////////////////////
	public Object formBackingObject(HttpServletRequest request) throws ServletException{				
	
		String[] vasItem = (String[])request.getParameterValues("vasitem");//edit by wilson 20110221
		//(String[])request.getSession().getAttribute("selectedVasItemList");

		VasDetailDTO vasDtl = new VasDetailDTO();
		vasDtl.setVasitem(vasItem);
		
		return vasDtl;
	}
	
	
	public ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) 
		throws ServletException, AppRuntimeException {
			//System.out.print("====>onSubmit called\n");
			String nextView = (String)request.getAttribute("nextView");
			logger.info("Next View: " + nextView);
			//DTO customer = (CustomerProfileDTO)command;

			request.getSession().setAttribute("brandSession", request.getParameter("brand"));
			request.getSession().setAttribute("modelSession", request.getParameter("model"));
			request.getSession().setAttribute("colorSession", request.getParameter("color"));
			request.getSession().setAttribute("basketSession", request.getParameter("basket"));
			request.getSession().setAttribute("selectedVasItemList", request.getParameterValues("vasitem"));
			/*******************************************************************/
			//service.insertOrderContact(customer);
			//request.setAttribute("enableOnloadJScript", true);
			return new ModelAndView(new RedirectView(nextView));
	
	}	
}
