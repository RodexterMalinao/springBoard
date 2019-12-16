package com.bomwebportal.ims.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

import com.bomwebportal.dto.AllOrdDocAssgnDTO;
import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.dto.AllOrdDocAssgnDTO.CollectedInd;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.ims.constant.ImsConstants;
import com.bomwebportal.ims.dto.ImsCollectDocDTO;
import com.bomwebportal.ims.dto.ImsCollectDocFormDTO;
import com.bomwebportal.ims.dto.ui.OrderImsUI;
import com.bomwebportal.ims.dto.ImsAllOrdDocDTO;
import com.bomwebportal.ims.service.ImsOrderDocumentService;
import com.bomwebportal.service.OrdDocService;
import com.bomwebportal.service.SupportDocService;

public class ImsCollectDocController extends SimpleFormController {

	private final String commandName = "imsCollectDocCmd";
	private final String viewName = "imscollectdoc";


	private final String nextView = "imscollectdoc.html?submit=true";

	private ImsOrderDocumentService imsOrderDocumentService;
	private OrdDocService ordDocService;
	private SupportDocService supportDocService; 

	public ImsCollectDocController() {
		setCommandClass(ImsCollectDocFormDTO.class);
		setCommandName(commandName);
		setFormView(viewName);
	}


	@Override
	public Object formBackingObject(HttpServletRequest request)	throws ServletException {
		logger.info("formBackingObject");
		
		OrderImsUI sessionOrder = (OrderImsUI)request.getSession().getAttribute(ImsConstants.IMS_ORDER);
		
		ImsCollectDocFormDTO form = new ImsCollectDocFormDTO();

		initialize(sessionOrder, form);
		
		logger.info("order id "+form.getOrderId());
		logger.info("list size "+form.getImsCollectDocDtoList().size());

		return form;
	}
	
	public ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws ServletException, AppRuntimeException {
		
		OrderImsUI sessionOrder = (OrderImsUI)request.getSession().getAttribute(ImsConstants.IMS_ORDER);
		ImsCollectDocFormDTO form = (ImsCollectDocFormDTO) command;
		
		logger.info("ImsCollectDoc onSubmit");
		logger.info("Order ID: "+sessionOrder.getOrderId());
		
		String user = ((BomSalesUserDTO)request.getSession().getAttribute("bomsalesuser")).getUsername();
		
		//update outdated indicator
		for(ImsCollectDocDTO doc : form.getImsCollectDocDtoList()){
			ordDocService.updateAllOrderDocDTOOutdatedInd(form.getOrderId(), doc.getDocType());
		}

		//insert BOMWEB_ALL_ORD_DOC table
		String faxSerial = null;
		for(ImsCollectDocDTO doc : form.getImsCollectDocDtoList()){
			faxSerial = doc.getFaxSerial();
		}
		List<ImsAllOrdDocDTO> imsAllOrdDocDtoList = null;
		imsAllOrdDocDtoList = createImsAllOrdDocDTO(form, form.getOrderId(), user);
		if(imsAllOrdDocDtoList != null){
			imsOrderDocumentService.insertImsAllOrdDocDTO(imsAllOrdDocDtoList);
			imsOrderDocumentService.updateWqFaxSerial(sessionOrder.getOrderId(), faxSerial, user);
		}
		

		//update collected indicator
		List<AllOrdDocAssgnDTO> allOrdDocAssgns = sessionOrder.getAllOrdDocAssgnDTOs();
		for(AllOrdDocAssgnDTO allOrdDocAssgn: allOrdDocAssgns){
			allOrdDocAssgn.setCollectedInd(CollectedInd.N);
			for(ImsCollectDocDTO collectDoc: form.getImsCollectDocDtoList()){
				if(StringUtils.equals(allOrdDocAssgn.getDocType().toString(), collectDoc.getDocType())
						&& StringUtils.isNotBlank(collectDoc.getFaxSerial())){
					collectDoc.setCollectedInd("Y");
					allOrdDocAssgn.setCollectedInd(CollectedInd.Y);
				}
			}
			//allOrdDocAssgn.setObjectAction(ObjectActionBaseDTO.ACTION_UPDATED);
		}
		sessionOrder.setAllOrdDocAssgnDTOs(allOrdDocAssgns);
		
		sessionOrder.setImsCollectDocDtoList(form.getImsCollectDocDtoList());
		
		// martin, 20170720, BOM2017086, for nowtv done page to update fax serial number
		if ("Y".equals(sessionOrder.isSignoffed()) && 
				sessionOrder.getOrderId()!=null && !"".equals(sessionOrder.getOrderId()) &&
				sessionOrder.getOrderType()!=null && "NTV-A".equals(sessionOrder.getOrderType())) {
			supportDocService.ims_mark_all_delete(sessionOrder.getOrderId());
			supportDocService.insertImsAllOrdDocAssgnDTOALL(allOrdDocAssgns);
		}
		
//		List<ImsCollectDocDTO> imsCollectDocDTOs = sessionOrder.getImsCollectDocDTOs();
//
//		for(ImsCollectDocDTO imsCollectDoc: imsCollectDocDTOs){
//			imsCollectDoc.setCollectedInd("N");
//				if(StringUtils.isNotBlank(imsCollectDoc.getFaxSerial())){
//					imsCollectDoc.setCollectedInd("Y");
//			}
//		//	allOrdDocAssgn.setObjectAction(ObjectActionBaseDTO.ACTION_UPDATED);
//		}
		//ltsOrderDocumentService.saveAllAllOrdDocAssgn(Lists.newArrayList(allOrdDocAssgns), user, form.getOrderId());

		return new ModelAndView(new RedirectView(nextView)); //closePopupPage
	}
	
	private List<ImsAllOrdDocDTO> createImsAllOrdDocDTO(ImsCollectDocFormDTO form, String orderId, String user){
		if(form.getImsCollectDocDtoList() == null){
				return null;
		}
		
		List<ImsAllOrdDocDTO> imsAllOrdDocDtoList = new ArrayList<ImsAllOrdDocDTO>();
		for(ImsCollectDocDTO doc : form.getImsCollectDocDtoList()){
			ImsAllOrdDocDTO imsAllOrdDocDto = new ImsAllOrdDocDTO();
			imsAllOrdDocDto.setDocType(doc.getDocType());
			imsAllOrdDocDto.setOrderId(orderId);
			imsAllOrdDocDto.setSerial(doc.getFaxSerial());
			imsAllOrdDocDto.setLastUpdBy(user);
			imsAllOrdDocDto.setCreateBy(user);
			imsAllOrdDocDtoList.add(imsAllOrdDocDto);
		}

		return imsAllOrdDocDtoList.size() > 0? imsAllOrdDocDtoList: null;
		
	}
	
	private void initialize(OrderImsUI sessionOrder, ImsCollectDocFormDTO pForm){
		if (sessionOrder == null || sessionOrder.getAllOrdDocAssgnDTOs().size()==0) {
			return;
		}
		
		List<ImsCollectDocDTO> imsCollectDocDtoList = new ArrayList<ImsCollectDocDTO>();
		String faxSerial = "";
		//HashMap<String, String> faxSerialMap = ltsOrderDocumentService.getFaxSerialMap(pSbOrder.getOrderId()); // new HashMap<String, String>();

		
		for(AllOrdDocAssgnDTO ordDocAssgn: sessionOrder.getAllOrdDocAssgnDTOs()){
			ImsCollectDocDTO imsCollectDoc = new ImsCollectDocDTO();
			imsCollectDoc.setDocType(ordDocAssgn.getDocType().toString());
			imsCollectDoc.setDocTypeDisplay(ordDocAssgn.getDocName());
			imsCollectDoc.setWaiveReason(ordDocAssgn.getWaiveReason());
			//imsCollectDoc.setMarkDelInd(ordDocAssgn.getMarkDelInd().toString());
			faxSerial = imsOrderDocumentService.getImsAllOrderDocFaxSerial(sessionOrder.getOrderId(),imsCollectDoc.getDocType());
			if(faxSerial==null){
				imsCollectDoc.setCollectedInd("N");
				imsCollectDoc.setFaxSerial("");
			}else{
				imsCollectDoc.setCollectedInd("Y");
				imsCollectDoc.setFaxSerial(faxSerial);
			}
		//	collectDoc.setFaxSerial(faxSerialMap.get(ordDocAssgn.getDocType()));
			imsCollectDocDtoList.add(imsCollectDoc);
		}
		
		pForm.setOrderId(sessionOrder.getOrderId());
		pForm.setImsCollectDocDtoList(imsCollectDocDtoList);
		
	}
	
	public ImsOrderDocumentService getImsOrderDocumentService() {
		return imsOrderDocumentService;
	}

	public void setImsOrderDocumentService(ImsOrderDocumentService imsOrderDocumentService) {
		this.imsOrderDocumentService = imsOrderDocumentService;
	}

	public void setOrdDocService(OrdDocService ordDocService) {
		this.ordDocService = ordDocService;
	}

	public OrdDocService getOrdDocService() {
		return ordDocService;
	}

	public SupportDocService getSupportDocService() {
		return supportDocService;
	}

	public void setSupportDocService(SupportDocService supportDocService) {
		this.supportDocService = supportDocService;
	}
}
