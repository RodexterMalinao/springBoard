package com.bomwebportal.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.bomwebportal.bean.LookupTableBean;
import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.dto.BrandDTO;
import com.bomwebportal.dto.CustomerProfileDTO;
import com.bomwebportal.dto.MnpDTO;
import com.bomwebportal.dto.ModelDTO;
import com.bomwebportal.mob.ccs.util.MobCcsSessionUtil;
import com.bomwebportal.service.ModelListService;
import com.bomwebportal.util.Utility;


public class ModelListController implements Controller{

    protected final Log logger = LogFactory.getLog(getClass());
    
    private ModelListService service;
    
    public ModelListService getService() {
		return service;
	}
	public void setService(ModelListService service) {
		this.service = service;
	}

    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	
    	long startTimeTotal = System.currentTimeMillis();
    	
    	String locale = RequestContextUtils.getLocale(request).toString();
    	BomSalesUserDTO user = (BomSalesUserDTO) request.getSession().getAttribute("bomsalesuser");
    	int channelId=(user.getChannelId() == 11 ? 10 : user.getChannelId());//get channelId , convert CFM(66), CPA(68) => IBS, OBS(2)
    	/*if (60>=user.getChannelId()){
			channelId =2;//convert channel ID
		}*/
    	if (LookupTableBean.getInstance().getAllowFalloutChannelList()!=null && LookupTableBean.getInstance().getAllowFalloutChannelList().length>0 && Utility.isContainString(LookupTableBean.getInstance().getAllowFalloutChannelList(), user.getChannelCd())){
			channelId =2;//convert channel ID
		}
    //	String channelId= ""+user.getChannelId();
		String appDate = (String) request.getSession().getAttribute("appDate");

		
		String appMode = (String) request.getSession().getAttribute("appMode");
		CustomerProfileDTO sessionCustomer = (CustomerProfileDTO) request
				.getSession().getAttribute("customer");

		if ("mobccs".equals(appMode)) {
			sessionCustomer = (CustomerProfileDTO) MobCcsSessionUtil
					.getSession(request, "customer");
		}
		
    	logger.info("locale:" +locale);
    	logger.info("ModelListController.handleRequest() Start");
    	//getParameter from prev. page 
    	String selectedBrandId=request.getParameter("brand");//select list
    	String selectedModelId=request.getParameter("model");//select list
    	
    	MnpDTO mnpDTO = (MnpDTO) request.getSession().getAttribute("MNP");
    	String customerTier= request.getParameter("customertier");//hidden value from serviceselection.html
    	logger.info("customerTier request: " + customerTier);
    	
    	if (sessionCustomer != null){
    		sessionCustomer.setCustomerTier(customerTier);
    		request.getSession().setAttribute("customer", sessionCustomer);
    	} 

    	String basketType = request.getParameter("baskettype");//hidden value from serviceselection.html
    	
    	String rpType = request.getParameter("rptype");//hidden value from serviceselection.html
    	String callList = request.getParameter("callList");//hidden value from serviceselection.html
//    	String groupSelection = request.getParameter("groupSelection");
    	String callListDesc = request.getParameter("callListDesc");
    	
    	//selectedBrandId==NONE, assign value=""
    	if (("NONE").equals(selectedBrandId) || selectedBrandId ==null){
    		selectedBrandId="";
    	}
    	
    	if (("NONE").equals(selectedModelId) || selectedBrandId ==null){
    		selectedModelId="";
    	}
    	
    	/**********************************/    
    	request.getSession().setAttribute("callListSession", new String[]{callList, callListDesc});//save session value for recall
//    	request.getSession().setAttribute("groupSelection", groupSelection);
    	/**********************************/
    	
    	//get select list from DB
    	List<BrandDTO> brandList=new ArrayList<BrandDTO>();
    	//List<ModelDTO> modelList=new ArrayList<ModelDTO>();
    	List<ModelDTO> modelListAll=new ArrayList<ModelDTO>(); //for menu javascript 
    	List<ModelDTO> handsetDisplayList=new ArrayList<ModelDTO>(); //right site 4 mobile
    	
    	long startTime = System.currentTimeMillis();
    	long endTime = System.currentTimeMillis();
    	if (!"NONE".equals(customerTier)){
    		logger.debug("customerTier search group1 left");
    	
	    	//call service to get data from DB
    		startTime = System.currentTimeMillis();
    		brandList= service.getBrandList(locale,  channelId+"",  customerTier,basketType, appDate, sessionCustomer.getBrand()); //channelId fix =1
    		endTime   = System.currentTimeMillis();
    		System.out.println("totalTime call in ModelListController in service.getBrandList(): "+((endTime - startTime))+"ms");
    		
    		//modelList= service.getModelList(locale, selectedBrandId ,channelId+"",  customerTier,basketType, appDate); //channelId fix =1
			startTime = System.currentTimeMillis();
			modelListAll=service.getModelList(locale, "" ,channelId+"",  customerTier,basketType, appDate, sessionCustomer.getBrand()); //channelId fix =1, no brand means load all info
			endTime   = System.currentTimeMillis();
			System.out.println("totalTime call in ModelListController in service.getModelList(): "+((endTime - startTime))+"ms");
			
			startTime = System.currentTimeMillis();
			handsetDisplayList =service.getHandSetDisplayList(locale,selectedBrandId, selectedModelId, channelId+"",  customerTier ,basketType, appDate, sessionCustomer.getBrand());//channelId = fix 1
			endTime   = System.currentTimeMillis();
			System.out.println("totalTime call in ModelListController in service.getHandSetDisplayList(): "+((endTime - startTime))+"ms");
			
    	}else{
    		logger.debug("Calllist search group2 Right");
    		//call mobccs function to get 
    		startTime = System.currentTimeMillis();
    		brandList= service.getCallListBrandList(locale, channelId+"", callList, basketType, rpType, appDate);
    		endTime   = System.currentTimeMillis();
    		System.out.println("totalTime call in ModelListController in service.getCallListBrandList(): "+((endTime - startTime))+"ms");
    		
    		//modelList= service.getCallListModelList(locale, channelId+"", callList, basketType, rpType, appDate);
			startTime = System.currentTimeMillis();
			modelListAll=service.getCallListModelList(locale, channelId+"", callList, basketType, rpType, appDate);//service.getModelList(locale, "" ,"1",  customerTier,basketType); //channelId fix =1, no brand means load all info
			endTime   = System.currentTimeMillis();
			System.out.println("totalTime call in ModelListController in service.getCallListModelList(): "+((endTime - startTime))+"ms");
			
			
			startTime = System.currentTimeMillis();
			handsetDisplayList =service.getCallListHandSetDisplayList(locale, selectedBrandId, selectedModelId, channelId+"", customerTier, basketType, rpType, callList, appDate);//channelId = fix 1
			endTime   = System.currentTimeMillis();
			System.out.println("totalTime call in ModelListController in service.getCallListHandSetDisplayList(): "+((endTime - startTime))+"ms");
    		
    	}
    	//return new ModelAndView
    	String uri = request.getRequestURI();
    
    	ModelAndView myView =new ModelAndView("modellist");
    	myView.addObject( "brandList", brandList);
    	//myView.addObject( "modelList", modelList);
    	myView.addObject( "handsetDisplayList", handsetDisplayList);
    	myView.addObject( "selectedBrandId", selectedBrandId); 
    	myView.addObject( "selectedModelId", selectedModelId); 
    	myView.addObject( "customerTier", customerTier); 
    	myView.addObject( "baskettype", basketType);  
    	myView.addObject( "callList", callList);  
    	myView.addObject( "rptype", rpType);  
    	//myView.addObject( "mobileScriptArrayString", getMobileScriptArrayLine0(modelListAll)+getMobileScriptArray(modelListAll)); // javascript function return string
    	myView.addObject("modelListAll", modelListAll);
    	
    	myView.addObject( "currentURI", uri);  
    	logger.info("ModellistController.handleRequest() End");
    	/* test uid*/
    	String attrUid=(String)request.getParameter("sbuid");
		myView.addObject("sbuid", attrUid);
		/* test uid*/
		
		
		long endTimeTotal   = System.currentTimeMillis();
		System.out.println("totalTime call in ModelListController: "+((endTimeTotal - startTimeTotal))+"ms");
		
    	return myView;
    	
/*}else{
	
	
	return new ModelAndView(new RedirectView("mobiledetail"));
}*/
    }
/*
    //create java script using Array text
    public String getMobileScriptArray(List<ModelDTO> modelList){
    	String result="";
    	String brandId="";
    	
    	int j=1;
    	
    	for ( int i=0; i<modelList.size(); i++){
    		ModelDTO tempDTO = modelList.get(i);
			if (brandId!="" && !brandId.equals(tempDTO.getBrandId())){
	    		result +="\"]\n";
	    		j++;
	    	}
	    	if (!brandId.equals(tempDTO.getBrandId())){
	    		
		    	result +="model["+j+"]=[\"" ;
		    	brandId=tempDTO.getBrandId();
	    	}
	    	
	    	if (brandId.equals(tempDTO.getBrandId())){
	    		result +=tempDTO.getModelId()+"|"+tempDTO.getModelName()+"\",\"";
	    		brandId =tempDTO.getBrandId();
	    	}
    	}
    	
    	if (!"".equals(result)){
    		result+="\"]\n";;
	    }
    	
    	result =result.replace(",\"\"]", "]");
    	return result;
    }
    
    //create java script using Array first line text
    public String getMobileScriptArrayLine0(List<ModelDTO> modelList){
    	String result="";
    	result +="model[0]=[\"" ;

    	for ( int i=0; i<modelList.size(); i++){
    		ModelDTO tempDTO = modelList.get(i);
    		result +=tempDTO.getModelId()+"|"+tempDTO.getModelName()+"\",\"";
    	}
    	if (!"".equals(result)){
		    	result+="\"]\n";;
		    	result =result.replace(",\"\"]", "]");
		}
    	return result;
    }
    
   


*/	
}
