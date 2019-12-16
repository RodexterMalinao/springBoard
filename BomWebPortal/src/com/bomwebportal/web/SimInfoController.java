package com.bomwebportal.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.bomwebportal.dto.MobileSimInfoDTO;
import com.bomwebportal.dto.SimDTO;
import com.bomwebportal.mob.ccs.service.StockManualDetailService;
import com.bomwebportal.service.MobileSimInfoService;
import com.bomwebportal.util.Utility;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class SimInfoController implements Controller {

	protected final Log logger = LogFactory.getLog(getClass());
	
	private MobileSimInfoService mobileSimInfoService;
	private StockManualDetailService stockManualDetailService;
	
	public MobileSimInfoService getMobileSimInfoService() {
		return mobileSimInfoService;
	}

	public void setMobileSimInfoService(MobileSimInfoService mobileSimInfoService) {
		this.mobileSimInfoService = mobileSimInfoService;
	}

	public StockManualDetailService getStockManualDetailService() {
		return stockManualDetailService;
	}

	public void setStockManualDetailService(StockManualDetailService stockManualDetailService) {
		this.stockManualDetailService = stockManualDetailService;
	}

	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		
		String type = request.getParameter("type");
		
		if (type.equalsIgnoreCase("iccid")) {
			String iccid = request.getParameter("a");
			String basketId = request.getParameter("b");
			String dateString = request.getParameter("c");
			
			MobileSimInfoDTO inputIccid = new MobileSimInfoDTO();
			inputIccid.setIccid(iccid);
			MobileSimInfoDTO resultSimInfo = mobileSimInfoService.validateSim(inputIccid);
			
			SimDTO sim = new SimDTO();
			sim.setIccid(resultSimInfo.getIccid());
			sim.setItemCode(resultSimInfo.getItemCd());
			sim.setHwInvStatus(resultSimInfo.getHwInvStatus());
			sim.setImsi(resultSimInfo.getImsi());
			sim.setPuk1(resultSimInfo.getPuk1());
			sim.setPuk2(resultSimInfo.getPuk2());
			sim.setShopCd(resultSimInfo.getShopCd());
			if (sim.getItemCode() != null && sim.getItemCode().length() > 0) {
				sim.setItemId(mobileSimInfoService.getBomWebSimItemId(basketId, sim.getItemCode()));
				if (sim.getItemCode() != null && sim.getItemCode().length() > 0) {
					List<String[]> simPriceResult = mobileSimInfoService.getSimPrice(sim.getItemId(), Utility.string2Date(dateString));
					if (simPriceResult != null && simPriceResult.size() > 0) {
						try {
							sim.setCharge(Double.parseDouble(simPriceResult.get(0)[1]));
						} catch (Exception e) {
							sim.setCharge(0);
						}
						sim.setWaivable("Y".equals(simPriceResult.get(0)[0]));
						
						GsonBuilder builder = new GsonBuilder();
				        Gson gson = builder.create();
				        logger.info("SimInfo Inquiry Result: " + gson.toJson(sim));
				        return new ModelAndView("ajax_siminfo", "gson", gson.toJson(sim));
					}
				}
			}
		} else if (type.equalsIgnoreCase("itemId")) {
			String itemId = request.getParameter("a");
			String dateString = request.getParameter("c");
			
			SimDTO sim = new SimDTO();
			sim.setItemId(itemId);
			List<String[]> simPriceResult = mobileSimInfoService.getSimPrice(sim.getItemId(), Utility.string2Date(dateString));
			if (simPriceResult != null && simPriceResult.size() > 0) {
				try {
					sim.setCharge(Double.parseDouble(simPriceResult.get(0)[1]));
				} catch (Exception e) {
					sim.setCharge(0);
				}
				sim.setWaivable("Y".equals(simPriceResult.get(0)[0]));
				
				GsonBuilder builder = new GsonBuilder();
		        Gson gson = builder.create();
		        logger.info("SimInfo Inquiry Result: " + gson.toJson(sim));
		        return new ModelAndView("ajax_siminfo", "gson", gson.toJson(sim));
			}
			
		} else if (type.equalsIgnoreCase("basketSimType")) {
			String basketId = request.getParameter("a");
			String simType = request.getParameter("b");
			String dateString = request.getParameter("c");
			String channelCd = request.getParameter("d");
			System.out.println(("Calling SimInfoController.basketSimType: " + basketId + ", " + simType + ", " + dateString + ", " + channelCd));
			try {
				String stockPool = stockManualDetailService.getCCSStockPool(channelCd);
				String itemId = mobileSimInfoService.getMockSimItemId(basketId, simType, stockPool, Utility.string2Date(dateString));
				
				System.out.println("Item ID =" + itemId);
				SimDTO sim = new SimDTO();
				sim.setItemId(itemId);
				List<String[]> simPriceResult = mobileSimInfoService.getSimPrice(sim.getItemId(), Utility.string2Date(dateString));
				if (simPriceResult != null && simPriceResult.size() > 0) {
					try {
						sim.setCharge(Double.parseDouble(simPriceResult.get(0)[1]));
					} catch (Exception e) {
						sim.setCharge(0);
					}
					sim.setWaivable("Y".equals(simPriceResult.get(0)[0]));
					
					GsonBuilder builder = new GsonBuilder();
				    Gson gson = builder.create();
				    logger.info("SimInfo Inquiry Result: " + gson.toJson(sim));
				    return new ModelAndView("ajax_siminfo", "gson", gson.toJson(sim));
				}
			} catch (Exception e) {
				logger.error("Exception caught in SimInfoController.basketSimType: " + basketId + ", " + simType + ", " + dateString + ", " + channelCd);
				e.printStackTrace();
			}
		}
		
		return null;
	}
	
}
