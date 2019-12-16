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
//import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

import com.bomwebportal.dto.ItemDisplayDTO;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.service.ItemDisplayService;

public class ItemDisplayController extends SimpleFormController {

	protected final Log logger = LogFactory.getLog(getClass());

	private ItemDisplayService itemDisplayService;

	public ItemDisplayService getItemDisplayService() {
		return itemDisplayService;
	}

	public void setItemDisplayService(ItemDisplayService itemDisplayService) {
		this.itemDisplayService = itemDisplayService;
	}

	public ItemDisplayController() {
	}

	public Object formBackingObject(HttpServletRequest request)
			throws ServletException {

		logger.info("ItemDisplayController formBackingObject  called");
		
		String itemId= request.getParameter("itemId2");
		String locale= request.getParameter("locale2");
		String itemType= request.getParameter("type2");
		//String from= request.getParameter("from");
		logger.info("itemId:"+itemId);
		
		//if(itemId!=null && locale !=null && itemType!=null & "itemdisplay".equals(from)){
			if(itemId!=null && locale !=null && itemType!=null ){	
			
			ItemDisplayDTO itemDisplay = itemDisplayService.getItemDisplay(
					Integer.parseInt(itemId), locale,itemType);
			
		    return itemDisplay;
	
		}
		
		

		ItemDisplayDTO sessionItemDisplay = (ItemDisplayDTO) request
				.getSession().getAttribute("itemDisplay");
		if (sessionItemDisplay == null) {
			ItemDisplayDTO itemDisplay = new ItemDisplayDTO();
			
			return itemDisplay;
		} else {
			
			return sessionItemDisplay;
		}
	}

	

	public ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws ServletException, AppRuntimeException {

		logger.info("ItemDisplayController onSubmit called");

		//String locale = RequestContextUtils.getLocale(request).toString();
		ItemDisplayDTO itemDisplay = (ItemDisplayDTO) command;
		
		//Comment & modify by herbert 20110822, standardize the log
		//System.out.println(itemDisplay.getItemId());
		logger.debug("itemDisplay.getItemId():"+itemDisplay.getItemId());
		
		request.getSession().setAttribute("itemDisplay", itemDisplay);// save session

		// System.out.println("itemDisplay.getFormAction():"+itemDisplay.getFormAction());
		// System.out.println("from form ==>itemDisplay.getItemId():"+itemDisplay.getItemId());

		if ("SEARCH".equals(itemDisplay.getFormAction())) {
			// System.out.println("SEARCH_SEARCH_SEARCH called");
			ItemDisplayDTO itemDisplayReturn = null;
			try {
				itemDisplayReturn = itemDisplayService.getItemDisplay(
						itemDisplay.getItemId(), itemDisplay.getLocale(),
						itemDisplay.getDisplayType());
				itemDisplayReturn.setFormMessage("Search finish:"
						+ itemDisplay.getItemId());
			} catch (Exception e) {
				itemDisplayReturn = new ItemDisplayDTO();
				itemDisplayReturn.setFormMessage("NOT found:"
						+ itemDisplay.getItemId());
				itemDisplayReturn.setItemId(itemDisplay.getItemId());
			}

			request.getSession().setAttribute("itemDisplay", itemDisplayReturn);// save
																				// session
			// System.out.println("SEARCH_SEARCH_SEARCH finish");
		} else if ("INSERT".equals(itemDisplay.getFormAction())) {
			// System.out.println("INSERT_INSERT_INSERT called");
			ItemDisplayDTO sessionItemDisplay = (ItemDisplayDTO) request
					.getSession().getAttribute("itemDisplay");
			try {
				itemDisplayService.insertItemDisplay(sessionItemDisplay);
				itemDisplay.setFormMessage("INSERT finish:"
						+ itemDisplay.getItemId());
				request.getSession().setAttribute("itemDisplay", itemDisplay);// save
																				// session
			} catch (Exception e) {
				itemDisplayService.updateItemDisplay(sessionItemDisplay);
				itemDisplay.setFormMessage("Update finish:"
						+ itemDisplay.getItemId());
				request.getSession().setAttribute("itemDisplay", itemDisplay);// save
																				// session
			}

			// ((ItemDisplayDTO)command).setFormAction("SEARCH");
			// itemDisplay.setFormAction("SEARCH");

			//System.out.println("INSERT_INSERT_INSERT finish");

		}/*
		 * else if ("UPDATE".equals(itemDisplay.getFormAction())){
		 * 
		 * ItemDisplayDTO sessionItemDisplay
		 * =(ItemDisplayDTO)request.getSession().getAttribute("itemDisplay");
		 * itemDisplayService.updateItemDisplay(sessionItemDisplay);
		 * 
		 * itemDisplay.setFormMessage("Update finish:"+
		 * itemDisplay.getItemId());
		 * request.getSession().setAttribute("itemDisplay", itemDisplay);//save
		 * session System.out.println("UPDATE_UPDATE_UPDATE");
		 * 
		 * }
		 */
		else if ("DELETE".equals(itemDisplay.getFormAction())) {

			ItemDisplayDTO sessionItemDisplay = (ItemDisplayDTO) request
					.getSession().getAttribute("itemDisplay");
			ItemDisplayDTO returnDTO = new ItemDisplayDTO();
			try {
				itemDisplayService.deleteItemDisplay(sessionItemDisplay);
				returnDTO.setFormMessage("Delete finish:"
						+ sessionItemDisplay.getItemId());
				request.getSession().setAttribute("itemDisplay", returnDTO);//save session

			} catch (Exception e) {
				logger.info("Exception: " + e.getMessage());
				returnDTO.setFormMessage("Delete error:"
						+ sessionItemDisplay.getItemId() + e.getMessage());
				request.getSession().setAttribute("itemDisplay", sessionItemDisplay);// save session
				// System.out.println("Exception" + e.getMessage());
			}

			//System.out.println("DELETE_DELETE_DELETE");

		}
		else if ("CLEAN".equals(itemDisplay.getFormAction())) {

			
			ItemDisplayDTO returnDTO = new ItemDisplayDTO();
			request.getSession().setAttribute("itemDisplay", returnDTO);//save session
			

		}

		
		// ModelAndView myView =new ModelAndView("itemdisplayeditor");
		// myView.addObject("itemDisplay", itemDisplayReturn);
		// return new ModelAndView("itemdisplay"); no work. . . . .

		return new ModelAndView(new RedirectView("itemdisplay.html?itemId2="+itemDisplay.getItemId()+""));//this is ok

		
	}

	// SimpleFormController
	protected Map referenceData(HttpServletRequest request)
			throws Exception {
		logger.info("ReferenceData called 2");
		
		Map referenceData = new HashMap<String, List<String>>();

		List<String> displayTypeList = new ArrayList<String>();
		displayTypeList.add("HSRB_PROMOT");
		displayTypeList.add("ITEM_SELECT");
		displayTypeList.add("QUOTATION");
		displayTypeList.add("RP_PROMOT");

		displayTypeList.add("SS_FORM_REBATE");
		displayTypeList.add("SS_FORM_RP");
		displayTypeList.add("SS_FORM_VAS");
		referenceData.put("displayTypeList", displayTypeList);

		List<String> localeList = new ArrayList<String>();
		localeList.add("en");
		localeList.add("zh_HK");
		referenceData.put("localeList", localeList);
		
		
		String itemId= request.getParameter("itemId2");
		//logger.info("itemId:"+itemId);
		
		if(itemId!=null){
			
			
			List<ItemDisplayDTO> itemDisplayDTOList=itemDisplayService.getItemDisplayAll(
					Integer.parseInt(itemId), "","");
			
			//Comment & modify by herbert 20110822, standardize the log
			//System.out.println("itemDisplayDTOList.size:"+itemDisplayDTOList.size());
			logger.debug("itemDisplayDTOList.size:"+itemDisplayDTOList.size());
			
			referenceData.put("itemDisplayDTOList", itemDisplayDTOList);
					
			
		}

		return referenceData;
	}

}
