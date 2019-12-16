package com.bomwebportal.web;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindException;
import org.springframework.web.HttpSessionRequiredException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractCommandController;

import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.dto.OrdDocAssgnAdminDTO;
import com.bomwebportal.dto.OrdDocAssgnAdminDTO.DmsInd;
import com.bomwebportal.dto.OrderDTO.CollectMethod;
import com.bomwebportal.service.OrderService;
import com.bomwebportal.service.SupportDocAdminService;
import com.bomwebportal.util.Utility;

public class SupportDocAdminController extends AbstractCommandController {
	
	private final static int PAGE_SIZE = 20;
	
	protected final Log logger = LogFactory.getLog(getClass());

	private OrderService orderService;
	private SupportDocAdminService supportDocAdminService;

	
	public SupportDocAdminController() {
		setCommandClass(SearchCriteria.class);
		setCommandName("command");
	}

	public OrderService getOrderService() {
		return orderService;
	}

	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}

	public SupportDocAdminService getSupportDocAdminService() {
		return supportDocAdminService;
	}

	public void setSupportDocAdminService(
			SupportDocAdminService supportDocAdminService) {
		this.supportDocAdminService = supportDocAdminService;
	}

	protected ModelAndView handle(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)	throws Exception {

		
		ModelAndView myView = new ModelAndView("supportdocadmin");
		
		SearchCriteria sc = (SearchCriteria)command;
		

		
		int page = sc.getPage();
		
		// firstpage = 0
		int pageSize = PAGE_SIZE;
		int returnedSize = 0;	
		long numOfPages = 0;
		
		BomSalesUserDTO salesUserDto = (BomSalesUserDTO) request.getSession()
			.getAttribute("bomsalesuser");
		
		String shopCode = salesUserDto.getBomShopCd();// original
		if (2 == salesUserDto.getChannelId()) {// chennelId=2, mobccs
			shopCode = salesUserDto.getChannelCd();// convert shopCode to		// ChannelCd
		}
		
		String allowSearchAllInd="";
		if("TTW".equals(shopCode) || "ALL".equals(shopCode) ){
			if ( StringUtils.isNotBlank(sc.getSelectedShopCode())){
				shopCode=sc.getSelectedShopCode();//only allow TTW shop sales change shop code
			}
			
			allowSearchAllInd=orderService.getAllowSearchAllInd(salesUserDto.getUsername());
			
		}
		
		
		if (StringUtils.isBlank(sc.getStartDate())) {
			Date date = new Date();
			sc.setStartDate(Utility.date2String(date, "dd/MM/yyyy"));
			if (StringUtils.isBlank(sc.getEndDate())) {
				sc.setEndDate(Utility.date2String(date, "dd/MM/yyyy"));
			}
		}
		
		//String collectedInd = "ALL".equals(sc.getCollectionStatus()) ? null : "N";
		
		String lob = "ALL".equals(sc.getLob()) ? null : sc.getLob();
		
		if ("SEARCH".equals(sc.getAction())) {
			
			int start = page * pageSize + 1;
			
			
			long count = supportDocAdminService.countDocAssigned(shopCode, lob
						, Utility.string2Date(sc.getStartDate()), Utility.string2Date(sc.getEndDate())
						, sc.getOrderId()
						, sc.getCollectMethod(), sc.getDmsInd());
			numOfPages = (count + pageSize-1) / pageSize;

			//System.out.println("Num of records = " + count);
			
			List<OrdDocAssgnAdminDTO> list = supportDocAdminService.findDocAssigned(shopCode, lob
					, Utility.string2Date(sc.getStartDate()), Utility.string2Date(sc.getEndDate())
					, StringUtils.stripToNull(sc.getOrderId())
					, sc.getCollectMethod(), sc.getDmsInd()
					, start, pageSize);
			
			returnedSize = list.size();
			myView.addObject("supportDocList", list);

		}


		myView.addObject("shopCode", shopCode);

		
		myView.addObject("allowSearchAllInd", allowSearchAllInd);
		myView.addObject("pageSize", pageSize);
		myView.addObject("currentSize", returnedSize);
		myView.addObject("numOfPages", numOfPages);
		myView.addObject("hasNext", page < numOfPages-1);
		myView.addObject("hasPrev", page > 0);

		
		myView.addAllObjects(errors.getModel());

		return myView;
	}
	
	protected final Object getCommand(HttpServletRequest request)
		throws Exception {
		
		String formAttrName = getClass().getName() + ".FORM." + getCommandName();
		HttpSession session = request.getSession(false);
		
		if (session == null) {
			throw new HttpSessionRequiredException("Must have session when trying to bind (in session-form mode)");
		}
		
		String action = request.getParameter("action");
		
		Object command = null;
		
		if ("true".equals(request.getParameter("reload"))) {
			command = session.getAttribute(formAttrName);
		} else {
			session.removeAttribute(formAttrName);
		}
		
		if (command == null) {
			command = super.getCommand(request);
		}
		
		if ("SEARCH".equals(action)) {
			session.setAttribute(formAttrName, command);
		}
		
		return command;
			
	}
	
	

	
	public static class SearchCriteria {
		private String orderId;
		private String startDate;
		private String endDate;
		private String selectedShopCode;
		private String lob;
		//private String collectionStatus;
		private int page ;
		private String action;
		private CollectMethod collectMethod;
		private DmsInd dmsInd;
		
		public String getOrderId() {
			return orderId;
		}
		public void setOrderId(String orderId) {
			this.orderId = orderId;
		}
		public String getStartDate() {
			return startDate;
		}
		public void setStartDate(String startDate) {
			this.startDate = startDate;
		}
		public String getEndDate() {
			return endDate;
		}
		public void setEndDate(String endDate) {
			this.endDate = endDate;
		}
		public String getSelectedShopCode() {
			return selectedShopCode;
		}
		public void setSelectedShopCode(String selectedShopCode) {
			this.selectedShopCode = selectedShopCode;
		}
		public String getLob() {
			return lob;
		}
		public void setLob(String lob) {
			this.lob = lob;
		}
		/*
		public String getCollectionStatus() {
			return collectionStatus;
		}
		public void setCollectionStatus(String collectionStatus) {
			this.collectionStatus = collectionStatus;
		}
		*/
		public int getPage() {
			return page;
		}
		public void setPage(int page) {
			this.page = page;
		}
		public String getAction() {
			return action;
		}
		public void setAction(String action) {
			this.action = action;
		}
		public CollectMethod getCollectMethod() {
			return collectMethod;
		}
		public void setCollectMethod(CollectMethod collectMethod) {
			this.collectMethod = collectMethod;
		}
		public DmsInd getDmsInd() {
			return dmsInd;
		}
		public void setDmsInd(DmsInd dmsInd) {
			this.dmsInd = dmsInd;
		}
		
		
	}
	
	
	
	
}
