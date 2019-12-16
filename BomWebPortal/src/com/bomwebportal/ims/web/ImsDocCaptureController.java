package com.bomwebportal.ims.web;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.service.SupportDocService;
import com.bomwebportal.dto.AllDocDTO;
import com.bomwebportal.dto.OrdDocAssgnDTO;
import com.bomwebportal.dto.AllOrdDocAssgnDTO;
import com.bomwebportal.dto.OrdDocDTO;
import com.bomwebportal.dto.AllDocDTO.DocType;
import com.bomwebportal.dto.AllDocDTO.Type;
import com.bomwebportal.dto.AllOrdDocAssgnDTO.CollectedInd;
import com.bomwebportal.ims.constant.ImsConstants;
import com.bomwebportal.ims.dto.ui.ImsPaymentUI;
import com.bomwebportal.ims.dto.ui.OrderImsUI;
import com.bomwebportal.service.OrdDocService;

public class ImsDocCaptureController extends AbstractController {

	protected final Log logger = LogFactory.getLog(getClass());
	private SupportDocService supportDocService;
	private OrdDocService ordDocService;

	public OrdDocService getOrdDocService() {
		return ordDocService;
	}

	public void setOrdDocService(OrdDocService ordDocService) {
		this.ordDocService = ordDocService;
	}
	@Override
	protected ModelAndView handleRequestInternal(
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		String orderId = request.getParameter("orderId");
		request.getSession().setAttribute("sessionSummaryOrderId", orderId);
		String disMode;
		String iPadLink;

		logger.info("Doc Capture orderId:"+orderId);
		logger.info("Doc Capture disMode:"+request.getParameter("disMode"));
		logger.info("Doc Capture iPadLink:"+request.getParameter("iPadLink"));
		logger.info("Doc Capture SignOffed:"+request.getParameter("SignOffed"));
		
		if(request.getParameter("disMode")!=null){
			 disMode = request.getParameter("disMode");
			 request.getSession().setAttribute("disMode", disMode);
		}else if (request.getSession().getAttribute("disMode")!=null){
			disMode = (String) request.getSession().getAttribute("disMode");
		}else{
			 disMode = "null";
		}
		
		if(request.getParameter("iPadLink")!=null){
			iPadLink = request.getParameter("iPadLink");
		}else
			iPadLink = null;
		
		String SignOffed;
		if(request.getParameter("SignOffed")!=null){
			SignOffed = request.getParameter("SignOffed");
		}else{
			SignOffed = "null";
		}
		ImsPaymentUI ImsPaymentUIDto = (ImsPaymentUI) request.getSession().getAttribute("payment");

		
		BomSalesUserDTO salesUserDto = (BomSalesUserDTO) request.getSession().getAttribute("bomsalesuser");
		
		List<AllOrdDocAssgnDTO> allOrdDocAssgnDTOs = null;
	    if (!SignOffed.equals("null")) {
			 allOrdDocAssgnDTOs = this.supportDocService.getInUseAllOrdDocAssgnDTOALL(orderId); 
			logger.info("SignOffed, get from DB");
		}else{
			logger.info("Not SignOffed, create Required Docs");
			this.supportDocService.ims_mark_all_delete(orderId);
			allOrdDocAssgnDTOs = retrieveAllOrdDocAssgnDTOs(request, disMode);
		}
		
		for (Iterator<AllOrdDocAssgnDTO> it = allOrdDocAssgnDTOs.iterator(); it.hasNext(); ) {
			AllOrdDocAssgnDTO dto = it.next();
			dto.setWaivedBy(StringUtils.isBlank(dto.getWaiveReason()) ? null : salesUserDto.getUsername());

			if (DocType.I003.equals(dto.getDocType())) {//I003 - Cash Memo
				if (ImsPaymentUIDto != null) {
					if ("C".equals(ImsPaymentUIDto.getPayMtdType())) {// C - Credit Card
						it.remove();
					}
				}
			}
			if (DocType.I002.equals(dto.getDocType())) {//I002 - Credit card copy
				if (ImsPaymentUIDto != null) {
					if ("M".equals(ImsPaymentUIDto.getPayMtdType())) {// M - Cash
						it.remove();
					}
				}
			}
			if (DocType.I004.equals(dto.getDocType())) {
				if (disMode.equals("E")) {
					it.remove();
				}
			}
		}
		
		this.supportDocService.insertImsAllOrdDocAssgnDTOALL(allOrdDocAssgnDTOs);
		
		
		List<OrdDocAssgnDTO> docReq = ordDocService.getRequiredDoc(orderId);
		List<OrdDocDTO> docRecord = ordDocService.getImsOrdDoc(orderId);

		
		ModelAndView view = new ModelAndView("imsdoccapture");
		view.addObject("orderId", orderId);
		view.addObject("requiredDocList", docReq);
		view.addObject("capturedRecordList", docRecord);
		
		if(iPadLink!=null)
		{
			logger.info("Redirect to iPad app: "+ iPadLink);
			response.sendRedirect(iPadLink);
			return null;
		}else{
			logger.info("Redirect to : imsdoccapture" );
			return view;
		}

	}
	
	public SupportDocService getSupportDocService() {
		return supportDocService;
	}

	public void setSupportDocService(SupportDocService supportDocService) {
		this.supportDocService = supportDocService;
	}

	private boolean isEmpty(List<?> list) {
		return list == null || list.isEmpty();
	}

	private List<AllOrdDocAssgnDTO> retrieveAllOrdDocAssgnDTOs(HttpServletRequest request, String disMode) {
		OrderImsUI sessionOrder = (OrderImsUI)request.getSession().getAttribute(ImsConstants.IMS_ORDER);
		BomSalesUserDTO salesUserDto = (BomSalesUserDTO) request.getSession().getAttribute("bomsalesuser");
		if(sessionOrder!=null){logger.info("ImsOrderID:"+sessionOrder.getOrderId());};
		List<AllDocDTO> allDocDTOs = supportDocService.getAllDocDTOByTypeAndAppDate("IMS",Type.I,sessionOrder.getAppDate()); 
		if (this.isEmpty(allDocDTOs)) {
			return Collections.emptyList();
		}
		List<AllOrdDocAssgnDTO> allOrdDocAssgnDTOs = new ArrayList<AllOrdDocAssgnDTO>();
		for (AllDocDTO dto : allDocDTOs) {
			boolean required = false;
			switch (dto.getDocType()) {
				case I001:
					// NETVIGATOR Application Form
					break;
				case I002:
				{
					// Credit Card Copy
					if (sessionOrder != null) {// M - Cash, C - Credit Card
						if ("C".equals(sessionOrder.getCustomer().getAccount().getPayment().getPayMtdType())) {
							required = true;
						}
					}
					if(disMode.equals("E")){
						required = false;
					}
					if("DS".equals(sessionOrder.getImsOrderType())){required = false;}
					break;
				}
				case I003:
				{
					// Cash Memo
					if (sessionOrder != null) {// M - Cash, C - Credit Card
						if ("M".equals(sessionOrder.getCustomer().getAccount().getPayment().getPayMtdType())) {
							required = true;
						}
					}
					ImsPaymentUI sessionPayment = (ImsPaymentUI) request.getSession().getAttribute("payment");
					if(sessionPayment!=null && "Y".equals(sessionPayment.getCashFsPrepay())){
						required = false;
					}
					if("DS".equals(sessionOrder.getImsOrderType())){required = false;}
					break;
				}
				case I004:
				{
					//Signed Application Form
					//if distribution mode = paper
					if(disMode.equals("P")){
						required = true;
					}
					if("DS".equals(sessionOrder.getImsOrderType())){required = false;}
					break;
				}
				case I006:
				{	//HKBR						
					int createByChannelId = salesUserDto.getChannelId();
					if("BS".equals(sessionOrder.getCustomer().getIdDocType())&&
							(sessionOrder.isOrderTypeNowRet())&&
							(createByChannelId==1||createByChannelId==12||createByChannelId==13)
							){
						request.getSession().setAttribute(ImsConstants.IMS_DS_MISMATCH_HKID,true);
						required = true;
					}
					break;
				}
				case I007:
				{	//HK ID, for Direct Sales, MisMatch WQ
					//if MisMatch = true
					String CustProfileMissMatch = "F";
					CustProfileMissMatch = sessionOrder.getCustomer().getExistingCustomerConflictWithName();		
					if("Y".equals(CustProfileMissMatch)&& "DS".equals(sessionOrder.getImsOrderType())){
						request.getSession().setAttribute(ImsConstants.IMS_DS_MISMATCH_HKID,true);
						required = true;
					}
					break;
				}
				case I008:
				{
					// IMS - NTV Bill/ Statement
					if (sessionOrder!=null && sessionOrder.getOrderType()!=null && !"".equals(sessionOrder.getOrderType())
							&& "NTV-A".equals(sessionOrder.getOrderType())) {
						if ("Y".equals(sessionOrder.getIsNtvSwitchingOffer())) {
							required = true;
						}
					}
					break;
				}
				case I009:
				{
					// IMS - Staff Verification (NTV-A)
					if (sessionOrder!=null && sessionOrder.getOrderType()!=null && !"".equals(sessionOrder.getOrderType())
							&& "NTV-A".equals(sessionOrder.getOrderType())) {
						if (sessionOrder.getIsStaffOffer()!=null && !"".equals(sessionOrder.getIsStaffOffer())) {
							if (sessionOrder.getIsStaffOffer().indexOf(ImsConstants.IMS_NTV_CPQ_PROMO_CODE_STAFF)==0) {
								required = true;
							}
						}
					}
					break;
				}
				case I010:
				{
					// IMS - Corporate Staff Verification (NTV-A)
					if (sessionOrder!=null && sessionOrder.getOrderType()!=null && !"".equals(sessionOrder.getOrderType())
							&& "NTV-A".equals(sessionOrder.getOrderType())) {
						if (sessionOrder.getIsStaffOffer()!=null && !"".equals(sessionOrder.getIsStaffOffer())) {
							if (ImsConstants.IMS_NTV_CPQ_PROMO_CODE_CSTAFF.equals(sessionOrder.getIsStaffOffer())) {
								required = true;
							}
						}
					}
					break;
				}
			}
			if (required) {
	    		Date now = new Date();
				AllOrdDocAssgnDTO allOrdDocAssgnDTO = new AllOrdDocAssgnDTO();
				String temp_order_id = (String) request.getSession().getAttribute("sessionSummaryOrderId");
				allOrdDocAssgnDTO.setOrderId(temp_order_id);
				int number_of_collected_doc = ordDocService.getImsNumberOfCollectedDoc(temp_order_id, dto.getDocType().toString());
				logger.info("temp_order_id:"+temp_order_id);
				logger.info("number_of_collected_doc:"+number_of_collected_doc);
				if(number_of_collected_doc>0){
					logger.info("Doc_type:"+dto.getDocType()+"set collect ind to Y");
					allOrdDocAssgnDTO.setCollectedInd(CollectedInd.Y);
				}else{
					logger.info("Doc_type:"+dto.getDocType()+"set collect ind to N");
					allOrdDocAssgnDTO.setCollectedInd(CollectedInd.N);
				}				
				allOrdDocAssgnDTO.setCreateBy(salesUserDto.getUsername());
				allOrdDocAssgnDTO.setCreateDate(now);
				allOrdDocAssgnDTO.setLastUpdBy(salesUserDto.getUsername());
				allOrdDocAssgnDTO.setLastUpdDate(now);
				allOrdDocAssgnDTO.setDocType(dto.getDocType());
				allOrdDocAssgnDTO.setDocName(dto.getDocName());
				allOrdDocAssgnDTOs.add(allOrdDocAssgnDTO);	
				logger.info("required doc:\t"+allOrdDocAssgnDTO.getDocName()+"\t"+allOrdDocAssgnDTO.getDocType()+"\t"+allOrdDocAssgnDTO.getCollectedInd());			
			}
		}
		return allOrdDocAssgnDTOs;
	}
}
