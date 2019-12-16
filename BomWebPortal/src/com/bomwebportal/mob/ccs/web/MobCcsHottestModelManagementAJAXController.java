package com.bomwebportal.mob.ccs.web;

import java.io.IOException;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.mob.ccs.dto.ui.HottestModelManagementUI;
import com.bomwebportal.mob.ccs.service.StockService;
import com.bomwebportal.util.Utility;

public class MobCcsHottestModelManagementAJAXController implements Controller{
    
    protected final Log logger = LogFactory.getLog(getClass());
    
    private StockService stockService;
    
    public StockService getStockService() {
        return stockService;
    }

    public void setStockService(StockService stockService) {
        this.stockService = stockService;
    }

    public ModelAndView handleRequest(HttpServletRequest request,
		HttpServletResponse response) throws ServletException, IOException {
    	
    	BomSalesUserDTO user = (BomSalesUserDTO)request.getSession().getAttribute("bomsalesuser");

		String type = this.getStringValue(request, "type");
		
		JSONArray jsonArray = new JSONArray();
			
		if ("ModelSearch".equals(type)) {
		    
		    String itemCode = this.getStringValue(request, "itemCode");
		    String itemDesc = this.getStringValue(request, "itemDesc");
		    String codeId = this.getStringValue(request, "codeId");
		    
		    if("".equals(itemCode)){
		    	itemCode = "%";
		    }
		    
		    if(itemCode.contains("*")){
		    	itemCode = StringUtils.replaceChars(itemCode, '*', '%');
		    }
		    
		    if("".equals(itemDesc)){
		    	itemDesc = "%";
		    }
		    
		    if(itemDesc.contains("*")){
		    	itemDesc = StringUtils.replaceChars(itemDesc, '*', '%');
		    }
		    
		    List<String[]> modellist = stockService.getModelSearch(itemCode, itemDesc, codeId);
		    
		    if (modellist == null) {
				modellist = Collections.emptyList();
				jsonArray = JSONArray.fromObject(modellist);
		    }else{
			
				Iterator<String[]> itr = modellist.iterator();
				while (itr.hasNext()){
				    String[] item = itr.next();
				    /*
				     	item[0] = code_desc
					item[1] = item_code
					item[2] = item_desc
				     */
				    
				    jsonArray.add("{\"code_desc\":\""	+ item[0]
					    	+ "\",\"item_code\":\""	+ item[1]
					    	+ "\",\"item_desc\":\""	+ item[2]
		        			+ "\"}");
				}
		    }

		}
		
		if("HottestPeriodSearch".equals(type)){
		    List<HottestModelManagementUI> modellist = stockService.getHottestModelAndPeriod(this.getStringValue(request, "itemCode"));
		    
		    if (modellist == null) {
				modellist = Collections.emptyList();
				jsonArray = JSONArray.fromObject(modellist);
		    }else{
			
				Iterator<HottestModelManagementUI> itr = modellist.iterator();
				while (itr.hasNext()){
				    HottestModelManagementUI item = itr.next();
				    /*
				     	item[0] = code_desc
					item[1] = item_code
					item[2] = item_desc
					item[3] = start_date
					item[4] = end_date
				     */
				    
				    jsonArray.add("{\"code_desc\":\"" + item.getType()
					    	+ "\",\"item_code\":\""	  + item.getItemCode()
					    	+ "\",\"item_desc\":\""	  + item.getModel()
					    	+ "\",\"start_date\":\""  + Utility.date2String(item.getStartDate(), "dd/MM/yyyy")
					    	+ "\",\"end_date\":\""	  + Utility.date2String(item.getEndDate(), "dd/MM/yyyy")			    	
					    	+ "\"}");
				    
				}
		    }	    	    
		}
		
		if("HottestValidate".equals(type)){
		    
		    boolean result = false;
		    result = stockService.validHottestModelManagementOverlap(
		    								this.getStringValue(request, "actionType"),
		    								this.getStringValue(request, "itemCode"),
			    							this.getDateValue(request, "startDateStr"),
			    							this.getDateValue(request, "endDateStr"));
		    
		    if (result == true) {
		    	jsonArray.add("{\"result\":\"valid\"}");
		    }else{
		    	jsonArray.add("{\"result\":\"invalid\"}");
		    }	    	    
		}
		
		if("Save".equals(type)){
		    
		    boolean result = false;
		    int resultCode = -1;
		    
		    if ("update".equalsIgnoreCase(this.getStringValue(request, "actionType"))) {
		    	resultCode = stockService.updateHottestModelStartDate(
		    					this.getDateValue(request, "endDateStr"), 
		    					user.getUsername(), 
		    					this.getStringValue(request, "itemCode"), 
		    					this.getDateValue(request, "startDateStr"));
		    	if (resultCode == 1) {
		    		result = true;
		    	}
		    } else if ("create".equalsIgnoreCase(this.getStringValue(request, "actionType"))) {
		    	resultCode = stockService.insertHottestModel(
		    					this.getStringValue(request, "itemCode"), 
		    					this.getDateValue(request, "startDateStr"), 
		    					this.getDateValue(request, "endDateStr"), 
		    					user.getUsername());
				if (resultCode == 1) {
					result = true;
				}
			}
		    
		    if (result == true) {
		    	jsonArray.add("{\"result\":\"saved\"}");
		    } else {
		    	jsonArray.add("{\"result\":\"notSave\"}"); 
		    }	    	    
		}
		
		logger.info("jsonArray : " + jsonArray);
	
		return new ModelAndView("ajax_HottestModel", "jsonArray", jsonArray);
    }
    
    private String getStringValue(HttpServletRequest request, String name) {
		String value = "";
		try {
		    if(!"".equals((String)request.getParameter(name)) && (String)request.getParameter(name) != null){
			value = new String(request.getParameter(name));
		    }	    
		} catch (NumberFormatException nfe) {}
		return value;
    }
    
    private Date getDateValue(HttpServletRequest request, String name) {
		Date value = null;
		try {
		    value = Utility.string2Date(request.getParameter(name));
		} catch (NumberFormatException nfe) {}
		return value;
    }
}
