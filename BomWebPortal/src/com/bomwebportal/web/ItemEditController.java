package com.bomwebportal.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
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
import org.springframework.web.servlet.view.RedirectView;

import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.dto.ItemEditDTO;
import com.bomwebportal.dto.ItemOfferDTO;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.service.ItemEditService;

public class ItemEditController extends SimpleFormController {

	private static final String ItemOfferDTO = null;

	protected final Log logger = LogFactory.getLog(getClass());

	private ItemEditService itemEditService;

	public ItemEditController() {
	}

	public Object formBackingObject(HttpServletRequest request)
			throws ServletException {

		logger.info("ItemEditController formBackingObject  called");

		ItemEditDTO sessionItemEdit = (ItemEditDTO) request.getSession()
				.getAttribute("itemEdit");
		BomSalesUserDTO salesUserDto = (BomSalesUserDTO) request.getSession()
				.getAttribute("bomsalesuser");

		if (sessionItemEdit == null) {
			ItemEditDTO itemEdit = new ItemEditDTO();
			itemEdit.setLastUpdBy(salesUserDto.getUsername());

			return itemEdit;
		} else {

			return sessionItemEdit;
		}
	}

	public ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws ServletException, AppRuntimeException {

		logger.info("ItemEditController onSubmit called");
		BomSalesUserDTO salesUserDto = (BomSalesUserDTO) request.getSession()
				.getAttribute("bomsalesuser");

		ItemEditDTO itemEdit = (ItemEditDTO) command;
		request.getSession().setAttribute("itemEdit", itemEdit);// save session

		if ("SEARCH".equals(itemEdit.getFormAction())) {

			ItemEditDTO itemEditReturn = null;
			try {
				itemEditReturn = itemEditService.getItem(itemEdit.getId());
				itemEditReturn.setFormAction("SELECT");
				itemEditReturn.setFormMessage("Item Search finish:"
						+ itemEdit.getId());

				// System.out.println("itemEditReturn.getItemPricing().size()"+itemEditReturn.getItemPricing().size());
			} catch (Exception e) {
				itemEditReturn = new ItemEditDTO();
				itemEditReturn.setFormMessage("Item NOT found:"
						+ itemEdit.getId());
				itemEditReturn.setId(itemEdit.getId());
			}

			request.getSession().setAttribute("itemEdit", itemEditReturn);// save
																			// session

		} else if ("INSERT".equals(itemEdit.getFormAction())) {
			itemEdit.setLastUpdBy(salesUserDto.getUsername());
			itemEdit.setCreateBy(salesUserDto.getUsername());
			// itemEdit.setCreateDate(createDate);
			try {
				int itemId = itemEditService.insertItemAll(itemEdit);
				itemEdit.setId(itemId);
				
				if(!"".equals(itemEdit.getOfferString())  && itemEdit.getOfferString()!=null){
					List<ItemOfferDTO> itemOfferList = itemEditService.stringToListItemOfferDTO(
							itemEdit.getId(), itemEdit.getOfferString());

					// insert to db

					for (ItemOfferDTO dto : itemOfferList) {
						itemEditService.insertItemOffer(dto);

					}
					
					
				}
				itemEdit.setFormMessage("INSERT finish:" + itemEdit.getId());
				request.getSession().setAttribute("itemEdit", itemEdit);// save
																		// session
			} catch (Exception e) {

				logger.info("Exception: " + e.getMessage());
				itemEdit.setFormMessage(itemEdit.getId()
						+ " Insert Error; Message:" + e.getMessage());
				request.getSession().setAttribute("itemEdit", itemEdit);// save
																		// session
			}
		} else if ("UPDATE".equals(itemEdit.getFormAction())) {
			itemEdit.setLastUpdBy(salesUserDto.getUsername());
			itemEditService.updateItemAll(itemEdit);
			
			if(!"".equals(itemEdit.getOfferString()) && itemEdit.getOfferString()!=null){
				//delete the data
				itemEditService.deleteItemOffer(itemEdit);
				//insert again
				List<ItemOfferDTO> itemOfferList = itemEditService.stringToListItemOfferDTO(
						itemEdit.getId(), itemEdit.getOfferString());

				// insert to db

				for (ItemOfferDTO dto : itemOfferList) {
					itemEditService.insertItemOffer(dto);

				}
				
				
			}
			itemEdit.setFormMessage("Update finish:" + itemEdit.getId());
			request.getSession().setAttribute("itemEdit", itemEdit);// save
																	// session

		} else if ("DELETE".equals(itemEdit.getFormAction())) {

			ItemEditDTO sessionitemEdit = (ItemEditDTO) request.getSession()
					.getAttribute("itemEdit");
			ItemEditDTO returnDTO = new ItemEditDTO();
			try {
				itemEditService.deleteItemAll(sessionitemEdit);
				returnDTO.setFormMessage("Delete finish:"
						+ sessionitemEdit.getId());
				request.getSession().setAttribute("itemEdit", returnDTO);// save
																			// session

			} catch (Exception e) {
				logger.info("Exception: " + e.getMessage());
				sessionitemEdit.setFormMessage(sessionitemEdit.getId()
						+ " Delete Error; Message:" + sessionitemEdit.getId()
						+ e.getMessage());
				request.getSession().setAttribute("itemEdit", sessionitemEdit);// save
																				// session

			}

		} else if ("CLEAN".equals(itemEdit.getFormAction())) {

			ItemEditDTO returnDTO = new ItemEditDTO();
			request.getSession().setAttribute("itemEdit", returnDTO);// save
																		// session

		}

		/*else if ("OFFER_PREVIEW".equals(itemEdit.getFormAction())) {

		//	request.getSession().setAttribute("itemEdit", itemEdit);// save
																	// session
		} else if ("OFFER_INSERT".equals(itemEdit.getFormAction())) {

			List<ItemOfferDTO> itemOfferList = itemEditService.stringToListItemOfferDTO(
					itemEdit.getId(), itemEdit.getOfferString());

			// insert to db

			for (ItemOfferDTO dto : itemOfferList) {
				itemEditService.insertItemOffer(dto);

			}
			request.getSession().setAttribute("itemEdit", itemEdit);// save
																	// session
		}*/

		// ModelAndView myView =new ModelAndView("itemedit");
		// return myView;//no workj
		// myView.addObject("itemEdit", itemEditReturn);
		// return new ModelAndView("itemEdit");// no work. . . . .
		// System.out.println("RedirectView:itemedit.html");

		// return showForm(request, errors, "itemedit");

		// 1.
		// return new ModelAndView("itemedit","itemEdit",(ItemEditDTO)command);
		// //no data
		// return new ModelAndView("itemedit","itemEdit", command);
		// 2.
		return new ModelAndView(new RedirectView("itemedit.html"));// this is
																	// ok, but
																	// insert
																	// two times

		// 3.
		// ItemEditDTO sessionItemEdit = (ItemEditDTO)
		// request.getSession().getAttribute("itemEdit");
		// return new ModelAndView("itemedit","itemEdit",sessionItemEdit);

	}

	protected Map referenceData(HttpServletRequest request) throws Exception {

		logger.info("ReferenceData called  ==> itemEditController");
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

		List<String> itemLobList = new ArrayList<String>();
		itemLobList.add("3G");
		referenceData.put("itemLobList", itemLobList);

		List<String> itemTypeList = new ArrayList<String>();
		itemTypeList.add("AP_INC");
		itemTypeList.add("BFEE");
		itemTypeList.add("BVAS");
		itemTypeList.add("HS");
		itemTypeList.add("HSRB");
		itemTypeList.add("MNP_INC");
		itemTypeList.add("RP");
		itemTypeList.add("SIM");
		itemTypeList.add("VAS");
		referenceData.put("itemTypeList", itemTypeList);

		Map<String, String> brandList = new LinkedHashMap<String, String>();
		// for( int i=0;
		// i<LookupTableBean.getInstance().getAddressCategoryList().size();
		// i++){
		brandList.put("1", "Apple");
		brandList.put("2", "SAMSUNG");
		brandList.put("3", "NOKIA");
		brandList.put("4", "HTC");
		brandList.put("5", "SONYERI");
		brandList.put("6", "LG");//add by wilson 20110407
		// }
		referenceData.put("brandList", brandList);

		Map<String, String> modelList = new LinkedHashMap<String, String>();
		modelList.put("1", "iPhone 4 16GB");
		modelList.put("2", "iPhone 4 32GB");
		modelList.put("3", "GALAXY S I9000");
		modelList.put("4", "N8-00");
		modelList.put("5", "GALAXY TAB P1000");
		modelList.put("6", "WILDFIRE");
		modelList.put("7", "5230");
		modelList.put("8", "C5");
		modelList.put("9", "C5-03");
		modelList.put("10", "SE X8");
		
		modelList.put("11", "LG P990 (OPTIMUS 2X)");//add by wilson 20110407
		modelList.put("12", "SS GALAXY ACE S5830");//add by wilson 20110407
		modelList.put("13", "SE XPERIA ARC");//add by wilson 20110407

		referenceData.put("modelList", modelList);

		Map<String, String> colorList = new LinkedHashMap<String, String>();
		colorList.put("10001", "Black");
		colorList.put("10002", "Blue");
		colorList.put("10003", "Dark Grey");
		colorList.put("10004", "Graphite Black");
		colorList.put("10005", "Green");
		colorList.put("10006", "Light Pink");
		colorList.put("10007", "Lime Green");
		colorList.put("10008", "Orange");
		colorList.put("10009", "Red");
		colorList.put("10010", "Silver white");
		colorList.put("10011", "Warm Grey");
		colorList.put("10012", "White");
		colorList.put("10013", "Dark Brown");
		colorList.put("10014", "Silver");
		
		referenceData.put("colorList", colorList);

		// select * from w_display_lkup
		// where type='RP_TYPE'
		// and locale='en'

		Map<String, String> rpTypeList = new LinkedHashMap<String, String>();
		rpTypeList.put("1", "Voice Only");
		rpTypeList.put("2", "DATA");
		rpTypeList.put("3", "VOICE + DATA");
		referenceData.put("rpTypeList", rpTypeList);

		Map<String, String> penaltyTypeList = new LinkedHashMap<String, String>();
		penaltyTypeList.put("R", "Remainder commitment Period");
		penaltyTypeList.put("F", "Fix");
		referenceData.put("penaltyTypeList", penaltyTypeList);

		Map<String, String> onetimeTypeList = new LinkedHashMap<String, String>();
		onetimeTypeList.put("U", "Upfront");
		onetimeTypeList.put("P", "Prepayment");
		referenceData.put("onetimeTypeList", onetimeTypeList);

		Map<String, String> envList = new LinkedHashMap<String, String>();
		envList.put("uat", "UAT");
		envList.put("prd", "PROD");
		referenceData.put("envList", envList);

		// create offer previce string
		ItemEditDTO sessionItemEdit = (ItemEditDTO) request.getSession()
				.getAttribute("itemEdit");
		if (sessionItemEdit != null
				&& "OFFER_PREVIEW".equals(sessionItemEdit.getFormAction())) {

			// call function to split
			List<ItemOfferDTO> itemOfferList = itemEditService.stringToListItemOfferDTO(
					sessionItemEdit.getId(), sessionItemEdit.getOfferString());
			// output
			StringBuilder offerTableString = new StringBuilder();
			for (ItemOfferDTO i : itemOfferList) {
				offerTableString.append(i.getTableRowString());
			}

			referenceData.put("offerTable", offerTableString.toString());

			request.getSession().setAttribute("itemEdit", sessionItemEdit);// save
																			// session
		}
		if (sessionItemEdit != null){
			//Comment & modify by herbert 20110822, standardize the log
			//System.out.println("sessionItemEdit.getFormAction():"+sessionItemEdit.getFormAction());
			logger.debug("sessionItemEdit.getFormAction():"+sessionItemEdit.getFormAction());
			
		}
		if (sessionItemEdit != null
				&& "SELECT".equals(sessionItemEdit.getFormAction())) {
			//Comment & modify by herbert 20110822, standardize the log
			//System.out.println("sessionItemEdit.getFormAction():"+sessionItemEdit.getFormAction());
			logger.debug("sessionItemEdit.getFormAction():"+sessionItemEdit.getFormAction());
			
			// call function to split
			List<ItemOfferDTO> itemOfferList = itemEditService.getItemOffer(sessionItemEdit.getId());

			// output
			StringBuilder offerTableString = new StringBuilder();
			StringBuilder offerCvsString = new StringBuilder();
			for (ItemOfferDTO i : itemOfferList) {
				offerTableString.append(i.getTableRowString());
				//offerId,offerSubId,offerType,productId,productType
				offerCvsString.append(i.getOfferId()+","+i.getOfferSubId()+","+i.getOfferType()+","+i.getProductId()+","+i.getProductType()+"\r\n");
			}
			sessionItemEdit.setOfferString(offerCvsString.toString());
			request.getSession().setAttribute("itemEdit", sessionItemEdit);// save session

			referenceData.put("offerTable", offerTableString.toString());

			
		}
		return referenceData;
	}

	

	

	public void setItemEditService(ItemEditService itemEditService) {
		this.itemEditService = itemEditService;
	}

	public ItemEditService getItemEditService() {
		return itemEditService;
	}

}
