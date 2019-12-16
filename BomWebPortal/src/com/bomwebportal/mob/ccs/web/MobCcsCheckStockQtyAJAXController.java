package com.bomwebportal.mob.ccs.web;

import java.io.IOException;
import java.util.Collections;
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

import com.bomwebportal.mob.ccs.service.StockService;

public class MobCcsCheckStockQtyAJAXController implements Controller{
    
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
    	
    	String orderId = this.getStringValue(request, "orderId");
		
		JSONArray jsonArray = new JSONArray();
			
		if (StringUtils.isNotBlank(orderId)) {
		    
		    List<String> modellist = stockService.checkStockQty(orderId);
		    
		    if (modellist == null) {
				modellist = Collections.emptyList();
				jsonArray = JSONArray.fromObject(modellist);
				
		    } else {
				Iterator<String> itr = modellist.iterator();
				while (itr.hasNext()){
				    String item = itr.next();				    
				    jsonArray.add("{\"stock_qty\":\"" + item + "\"}");
				}
		    }

		}
		
		logger.info("jsonArray : " + jsonArray);
	
		return new ModelAndView("ajax_CheckStockQty", "jsonArray", jsonArray);
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

}
