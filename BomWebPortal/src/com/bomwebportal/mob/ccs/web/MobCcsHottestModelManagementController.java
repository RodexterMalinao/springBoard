package com.bomwebportal.mob.ccs.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.bomwebportal.mob.ccs.service.StockService;

public class MobCcsHottestModelManagementController implements Controller {
    
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
    	
//    	String type = request.getParameter("type");
//    	String itemCode = request.getParameter("itemCode");
//    	String model = request.getParameter("model");
//    	String startDate
    	
    	Map referenceData = new HashMap<String, List<String>>();
    	
    	ArrayList<String[]> typeList = new ArrayList<String[]>();
    	typeList.add(new String[]{"%%","ALL"});
    	typeList.add(new String[]{"05","ANS"});
    	typeList.add(new String[]{"01","HANDSET"});
    	typeList.add(new String[]{"04","TABLET"});
    	
    	referenceData.put("typeList", typeList);
    	
    	return new ModelAndView("mobccshottestmodelmanagement", referenceData);
    }
}
