package com.bomwebportal.service.qc;


import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.dto.QcStaffDTO;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.ims.dao.ImsDSQCDAO;
import com.bomwebportal.ims.dao.ImsOrderAmendBomDAO;
import com.bomwebportal.ims.dto.AppointmentDTO;
import com.bomwebportal.ims.dto.AppointmentTimeSlotDTO;
import com.bomwebportal.ims.dto.HousingTypeDTO;
import com.bomwebportal.ims.dto.ImsAlertMsgDTO;
import com.bomwebportal.ims.dto.ImsAutoSyncWQDTO;
import com.bomwebportal.ims.dto.ui.AmendOrderImsUI;
import com.bomwebportal.ims.dto.ui.ImsDsQCProcessUI;
import com.bomwebportal.ims.dto.ui.ImsDsQCStaffAdminUI;
import com.bomwebportal.ims.dto.ui.DsQCImsOrderEnquiryUI;
import com.bomwebportal.ims.dto.ui.ImsDsQcProcessDetailUI;
import com.bomwebportal.ims.dto.ui.ImsQcAssignUI;
import com.bomwebportal.ims.dto.ui.ImsQcComOrderSearchUI;
import com.bomwebportal.ims.dto.ui.QcImsAdminUI;
import com.bomwebportal.ims.service.ResourceLoaderService;
import com.bomwebportal.util.Utility;
import com.google.gson.Gson;
import com.pccw.wq.service.WorkQueueService;

import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;


public class ImsDSQCServiceImpl implements ImsDSQCService{



	protected final Log logger = LogFactory.getLog(getClass());

	private ImsDSQCDAO imsDSQCDao;

	public ImsDSQCDAO getImsDSQCDao() {
		return imsDSQCDao;
	}

	public void setImsDSQCDao(ImsDSQCDAO imsDSQCDao) {
		this.imsDSQCDao = imsDSQCDao;
	}
	
	
	public boolean isDcStaffExist(String staffId) throws DAOException{
		return imsDSQCDao.isDcStaffExist(staffId);
	};
	
	public void insertSalesTypeAndLocation(BomSalesUserDTO amend){
		try {
			imsDSQCDao.insertSalesTypeAndLocation(amend);

		} catch (DAOException e) {
			e.printStackTrace();
		}
			
	}
	public void updateSalesTypeAndLocation(BomSalesUserDTO amend){
		try {
			imsDSQCDao.updateSalesTypeAndLocation(amend);

		} catch (DAOException e) {
			e.printStackTrace();
		}
			
	}
	
	public String getSalesTypeCode(String salesType) throws DAOException{
	
		return imsDSQCDao.checkSalesTypeCode(salesType);
	}
	
	public List<ImsAlertMsgDTO> getImsDsQcOrderEnquiryInfo(DsQCImsOrderEnquiryUI enquiry, BomSalesUserDTO userDto) {
		try {
			
			return imsDSQCDao.getImsDsQcOrderEnquiryInfo(enquiry, userDto);
		} catch (DAOException e) { 
			e.printStackTrace();
		}
		return null;
	}
	
	public BomSalesUserDTO getDSinfo(BomSalesUserDTO user) throws DAOException{
		return  imsDSQCDao.getDSinfo(user);
	}
	
	//QC process
	public List<ImsAlertMsgDTO> getImsDsQcProcessEnquiryInfo(ImsDsQCProcessUI enquiry, BomSalesUserDTO userDto) {
		try {
			
			return imsDSQCDao.getImsDsQcProcessEnquiryInfo(enquiry, userDto);
		} catch (DAOException e) { 
			e.printStackTrace();
		}
		return null;
	}
	
	public List<ImsQcAssignUI> getQcStaffInfo(int channelID) throws DAOException {
		return imsDSQCDao.getQcStaffInfo(channelID);
	}
	
	public List<QcImsAdminUI> getQcStaffInfoAdmin(int channelID)throws DAOException {
		return imsDSQCDao.getQcStaffInfoAdmin(channelID);
	}
	
	private WorkQueueService workQueueService;
	//private ImsOrderAmendDAO imsDSQCDao;
	
	private ImsOrderAmendBomDAO imsAmendOrderBomDao;
	private Gson gson = new Gson();
	
	public ImsOrderAmendBomDAO getImsAmendOrderBomDao() {
		return imsAmendOrderBomDao;
	}

	public void setImsAmendOrderBomDao(ImsOrderAmendBomDAO imsAmendOrderBomDao) {
		this.imsAmendOrderBomDao = imsAmendOrderBomDao;
	}
	public WorkQueueService getWorkQueueService() {
		return workQueueService;
	}

	public void setWorkQueueService(WorkQueueService workQueueService) {
		this.workQueueService = workQueueService;
	}
	

	
	public String createWqSalesRemark(AmendOrderImsUI amend) {
		DateFormat printTimeNow = new SimpleDateFormat("dd/MM/yy HH:mm");
		Date timeNow = new Date();
		System.out.println("time now:"+printTimeNow.format(timeNow));
		return "Modi by: "+amend.getBomSalesUserDTO().getUsername() +", "+amend.getBomSalesUserDTO().getChannelCd()+"  "+(printTimeNow.format(timeNow))+
			"\nFr: "+amend.getApplicantName()+"/"+amend.getApplicantRelationship()+"/"+amend.getApplicantContactNo()+"\n";
	}
	
	public String createWqCreditCardRemark(AmendOrderImsUI amend) {
		String result = "";
		if("Y".equals(amend.getIsUpdateCreditEnabled())){
		String cctype ="American Express";
		if("V".equalsIgnoreCase(amend.getCcType())){
			cctype="Visa";
		}else if("M".equalsIgnoreCase(amend.getCcType())){
			cctype="Master";
		}
		
		result= "Card Holder Name:"+amend.getCcHolderName() +
					"	\nCard Type:"+ cctype+
					"	\nCard Number:"+amend.getCcNum() +
					" 	\nExpiry Date:"+amend.getCcExpDate()+
					"	\nIs 3rd Party Card:"+amend.getThirdPartyInd() +
					((amend.getFaxSerialNum()!=null&&!"".equals(amend.getFaxSerialNum()))?"	\nFax Serial Num:"+amend.getFaxSerialNum():"")+
					" \n ";
		};
		return result;
	}

	public String createWqChangeSrdRemark(AmendOrderImsUI amend) {
		String result = "";
		if("Y".equals(amend.getIsAppointmentEnabled())){
			Date instdate= new Date();
			if(amend.getOrderImsUI().getAppointment().getTargetInstallDate()!=null && !"".equals(amend.getOrderImsUI().getAppointment().getTargetInstallDate())){
				logger.info("getTargetInstallDate:"+amend.getOrderImsUI().getAppointment().getTargetInstallDate());
				String dateString = amend.getOrderImsUI().getAppointment().getTargetInstallDate();
				SimpleDateFormat takeInstDate = new SimpleDateFormat("yyyy/MM/dd");
				try {
					instdate = takeInstDate.parse(dateString);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				logger.info("instdate:"+instdate);
			}
			DateFormat printInstDate = new SimpleDateFormat("dd/MM/yy");
			result = "change appt date to :"+(printInstDate.format(instdate))+" " +amend.getTimeSlotDisplay().replaceAll(":00", "")+
			" 	\nDelay Reason Code:"+amend.getDelayReasonCode() +
			" 	\nDelay Reason:"+amend.getDelayReason() ;
			if(amend.getOrderImsUI().getAppointment().getBmoRmk()!=null 
					&& !amend.getOrderImsUI().getAppointment().getBmoRmk().equals("")){
				result += " 	\nBMO Remark:"+amend.getOrderImsUI().getAppointment().getBmoRmk();
			}
		}
		return result;
	}

	public String createWqOrderCancelRemark(AmendOrderImsUI amend) {
			return"Cancel Reason: "+amend.getCancelReason()+"\nCancel Remark: "+amend.getCancelRemark() ;
	}
	
	public String createBomWebAmendCategoryRemark(AmendOrderImsUI amend) {
				
		String wqRemarkString = createWqSalesRemark(amend);
		
		if("Y".equalsIgnoreCase(amend.getIsCancelOrderEnabled())){
			return createWqOrderCancelRemark(amend);
		}
		if("Y".equalsIgnoreCase(amend.getIsFsAmendEnabled())){
			wqRemarkString+=" 	\nAmend Remark:  "+amend.getAmendRemark1();
			if(amend.getAmendRemark2()!=null && !amend.getAmendRemark2().equals("")){
				wqRemarkString+=" 	\nAmend Remark2:  "+amend.getAmendRemark2();
				if(amend.getAmendRemark3()!=null && !amend.getAmendRemark3().equals("")){
					wqRemarkString+=" 	\nAmend Remark3:  "+amend.getAmendRemark3();
				}
			}
		}
		if("Y".equalsIgnoreCase(amend.getIsUpdateCreditEnabled())||"Y".equalsIgnoreCase(amend.getIsAppointmentEnabled())){
			wqRemarkString+="\n";
		}
		if("Y".equalsIgnoreCase(amend.getIsUpdateCreditEnabled())){
			wqRemarkString+=createWqCreditCardRemark(amend);
		}
		
		if("Y".equalsIgnoreCase(amend.getIsAppointmentEnabled())){
			wqRemarkString+=createWqChangeSrdRemark(amend);
		} 
		
		return wqRemarkString;
	}
	
	public String createWqCombinedCoverSheetRemark(AmendOrderImsUI amend) {
		
		String wqRemarkString = createBomWebAmendCategoryRemark(amend);
		if("Y".equalsIgnoreCase(amend.getIsAppointmentEnabled())){
			wqRemarkString+="\nUAMS serial no:"+" " +amend.getPreBkSerialNum();
		}
		
		return wqRemarkString;
	}
	
	public com.pccw.wq.schema.dto.RemarkDTO addUAMS(com.pccw.wq.schema.dto.RemarkDTO dto,AmendOrderImsUI amend) {
		String wqRemarkString = "";
		if("Y".equalsIgnoreCase(amend.getIsAppointmentEnabled())){
			wqRemarkString +="\nUAMS serial no:"+" " +amend.getPreBkSerialNum();
		}
		dto.setRemarkContent(dto.getRemarkContent()+wqRemarkString);
		return dto;
	}
	
	//steven added end

	public AppointmentTimeSlotDTO getAmendSRDInfoStorProd(String orderId, String amendType)
			throws DAOException {
		logger.info("BomSRDApptServiceImpl AmendSRDInfo is called");
		logger.info("orderId = " + orderId);

		try{
		
			AppointmentDTO appointmentDTO = imsAmendOrderBomDao.getSrdInfo(orderId, amendType);
			AppointmentTimeSlotDTO appointmentTimeSlotDTO = new AppointmentTimeSlotDTO();
			
			String SRDInfoApptDate = Utility.date2String(appointmentDTO.getAppntStartDate(),"yyyyMMdd");
			String SRDInfoApptTimeStart = Utility.date2String(appointmentDTO.getAppntStartDate(),"HH:mm");
			String SRDInfoApptTimeEnd = Utility.date2String(appointmentDTO.getAppntEndDate(),"HH:mm");
			
			String SRDInfoApptTimeSlot = SRDInfoApptTimeStart + "-" + SRDInfoApptTimeEnd; 
			
	
			appointmentTimeSlotDTO.setApptDate(SRDInfoApptDate);
			appointmentTimeSlotDTO.setTimeSlot(SRDInfoApptTimeSlot);
			
			appointmentTimeSlotDTO.setReturnValue(appointmentDTO.getStorProReturnValue());
			appointmentTimeSlotDTO.setErrorCode(appointmentDTO.getStorProErrorCode());
			appointmentTimeSlotDTO.setErrorMsg(appointmentDTO.getStorProErrorText());
			
			
			logger.debug("AmendSRDInfo() SRDInfoApptDate = "+SRDInfoApptDate);
			logger.debug("AmendSRDInfo() SRDInfoApptTimeStart = "+SRDInfoApptTimeStart);
			logger.debug("AmendSRDInfo() SRDInfoApptTimeEnd = "+SRDInfoApptTimeEnd);
			
			return appointmentTimeSlotDTO;
		}catch (DAOException de) {		
			logger.error("Exception caught in AmendSRDInfo()", de);
			throw new AppRuntimeException(de);
		}
	}

	public List<HousingTypeDTO> getHousingTypeByOrderID(String orderId) {
		try {
			return imsAmendOrderBomDao.getHousingTypeByOrderID(orderId);
		} catch (DAOException e) {
			e.printStackTrace();
		}
		return null;
	}

	
	public Boolean isPendingExist(String orderId) {
		try {
			return imsDSQCDao.isPendingExist(orderId);
		} catch (DAOException e) {
			e.printStackTrace();
		}
		return true;
	}

	public Boolean isOCIDexist(String orderId) {
		try {
			return imsDSQCDao.isOCIDexist(orderId);
		} catch (DAOException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public Boolean checkIfSalesManager(String user) {
		try {
			return imsDSQCDao.checkIfSalesManager(user);
		} catch (DAOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<String> getRoleCodeByUserIDLkupCode(String userId, String lkupCode, String lkupFuncCode) {
		try {
			return imsDSQCDao.getRoleCodeByUserIDLkupCode(userId, lkupCode, lkupFuncCode);
		} catch (DAOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<String> getChannelCodeListByChannelID(int channelId) {
		try {
			return imsDSQCDao.getChannelCodeListByChannelID(channelId);
		} catch (DAOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<String> getPrivilegedOrderIdList(List<String> orderIdList, BomSalesUserDTO userDto, String lkupCode, String lkupFuncCode) {
		logger.info("getPrivilegedOrderIdList is called");
		List<String> result = new ArrayList<String>();
		List<String> roleCodeList = this.getRoleCodeByUserIDLkupCode(userDto.getUsername(), lkupCode, lkupFuncCode);
		List<String> channelCodeList = this.getChannelCodeListByChannelID(userDto.getChannelId());
		List<ImsAlertMsgDTO> imsOrderList =this.getImsOrderListByOrderIdList(orderIdList);
		for(ImsAlertMsgDTO temp:imsOrderList){
			temp.checkRecallAmend(roleCodeList,	userDto, channelCodeList);
			if("Y".equalsIgnoreCase(temp.getRecall())){
				result.add(temp.getOrderId());
			}
		}
		logger.info("result:" + gson.toJson(result));
		return result;
	}

	private List<ImsAlertMsgDTO> getImsOrderListByOrderIdList(
			List<String> orderIdList) {
		try {
			return imsDSQCDao.getImsAlertMsgDTOListByOrderIdList(orderIdList);
		} catch (DAOException e) {
			e.printStackTrace();
		}
		return null;
	}

	
	public ImsAutoSyncWQDTO getPendingFromBOM (ImsAutoSyncWQDTO dto) {
		try {
			return imsAmendOrderBomDao.getPendingFromBOM(dto);
		} catch (DAOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Boolean  isPaymentMethodIsCC (String orderId){
		try {
			return imsDSQCDao.isPaymentMethodIsCC(orderId);
		} catch (DAOException e) {
			e.printStackTrace();
		}
		return false;
	}


	public List<String> getOrderStatusList() {
		try {
			return imsDSQCDao.getOrderStatusList();
		} catch (DAOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String lockBy(String orderId) {
		try {
			return imsAmendOrderBomDao.lockBy(orderId);
		} catch (DAOException de) {
			throw new AppRuntimeException(de);
		}
	}
	
	public Boolean isL1Distributed(String orderId) {
		try {
			return imsAmendOrderBomDao.isL1Distributed(orderId);
		} catch (DAOException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	//QC assignment
	public List<String> getQcSkills(String staffID) throws DAOException {
		return imsDSQCDao.getQcSkills(staffID);
	}

	public void insertQcAssign(ImsQcAssignUI imsQcAssignUI)  throws DAOException  {
		// TODO Auto-generated method stub
		imsDSQCDao.insertQcAssign(imsQcAssignUI);
	}
	
	public void updateQcAssign(ImsQcAssignUI imsQcAssignUI) throws DAOException {
		imsDSQCDao.updateQcAssign(imsQcAssignUI);
	}

	public void insertQcAssignBulk(List<ImsQcAssignUI> imsQcAssignUIList)  throws DAOException {
		// TODO Auto-generated method stub
		//imsDSQCDao.insertQcAssignBulk(imsQcAssignUIList);
	}

	public void insertNewQcStaffSkills(QcStaffDTO qcStaffDTO) throws DAOException {
		// TODO Auto-generated method stub
		imsDSQCDao.insertNewQcStaffSkills(qcStaffDTO);
	}
	
	public void insertNewQcStaffInfo(QcStaffDTO qcStaffDTO) throws DAOException{
		imsDSQCDao.insertNewQcStaffInfo(qcStaffDTO);
	}
	
	public void updateControlStaffRatio(ImsDsQCStaffAdminUI ImsStaffAdminUI) throws DAOException{
		imsDSQCDao.updateControlStaffRatio(ImsStaffAdminUI);
	}
	public void updateCleanStaffRatio(ImsDsQCStaffAdminUI ImsStaffAdminUI) throws DAOException{
		imsDSQCDao.updateCleanStaffRatio(ImsStaffAdminUI);
	}
    
	public List<ImsDsQCStaffAdminUI>  getQcStaffRatio(String type)throws DAOException{
		return imsDSQCDao.getQcStaffRatio(type);
	}
	
	
	public void updateControlNTVStaffRatio(ImsDsQCStaffAdminUI ImsStaffAdminUI) throws DAOException{
		imsDSQCDao.updateControlNTVStaffRatio(ImsStaffAdminUI);
	}
	public void updateCleanNTVStaffRatio(ImsDsQCStaffAdminUI ImsStaffAdminUI) throws DAOException{
		imsDSQCDao.updateCleanNTVStaffRatio(ImsStaffAdminUI);
	}
    
	public List<ImsDsQCStaffAdminUI>  getQcNTVStaffRatio(String type)throws DAOException{
		return imsDSQCDao.getQcNTVStaffRatio(type);
	}

	public void updateQcProcess(ImsDsQcProcessDetailUI imsDsQcProcessDetailUI)	throws DAOException {
		// TODO Auto-generated method stub
		imsDSQCDao.updateQcProcess(imsDsQcProcessDetailUI);
	}
	public boolean checkOrderExist(String OrderID) throws DAOException{
		return imsDSQCDao.checkOrderExist(OrderID);
	}
	public void insertQcProcess(ImsDsQcProcessDetailUI imsDsQcProcessDetailUI) throws DAOException{
		imsDSQCDao.insertQcProcess(imsDsQcProcessDetailUI);
	}
	public String getAssignDate(String OrderID) throws DAOException{
		return imsDSQCDao.getAssignDate(OrderID);
	};
	public String getAssignee(String OrderID) throws DAOException{
		return imsDSQCDao.getAssignee(OrderID);
	};
	public List<ImsDsQcProcessDetailUI> getReasonCQDesc() throws DAOException{
		return imsDSQCDao.getReasonCQDesc();
	};
	public List<ImsDsQcProcessDetailUI> getReasonFailDesc() throws DAOException{
		return imsDSQCDao.getReasonFailDesc();
	};
	public String getPreInstallInd(String OrderID) throws DAOException{
		return imsDSQCDao.getPreInstallInd(OrderID);
	};
	public String getReasonCode(String desc) throws DAOException{
		return imsDSQCDao.getReasonCode(desc);
	};
	public List<ImsDsQcProcessDetailUI> getQCProcessInfo(String OrderId) throws DAOException {
		return imsDSQCDao.getQCProcessInfo(OrderId);
	};
	public void releaseQcOrders(QcStaffDTO qcStaffDTO) throws DAOException{
		imsDSQCDao.releaseQcOrders(qcStaffDTO);
	};
	public void removeQcStaff(QcStaffDTO qcStaffDTO) throws DAOException{
		imsDSQCDao.removeQcStaff(qcStaffDTO);
	};
	public List<ImsDsQcProcessDetailUI>getQcRemark(String orderId)throws DAOException{
		return imsDSQCDao.getQcRemark(orderId);
	};
	
	public boolean checkIfAssignisNull(String orderId) {
		try {
			return imsDSQCDao.checkIfAssignisNull(orderId);
		} catch (DAOException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean isQcStaffExist(String staffId) throws DAOException{
		return imsDSQCDao.isQcStaffExist(staffId);
	};
	public Boolean isStaffExist(String staffId)	throws DAOException {
		return imsDSQCDao.isStaffExist(staffId);
	};
	public void updateQCstaffSkillsAndStatus(QcStaffDTO qcStaffDTO) throws DAOException{
		 imsDSQCDao.updateQCstaffSkillsAndStatus(qcStaffDTO);
	};
	public List<String> getQcStatusList() {
		try {
			return imsDSQCDao.getQcStatusList();
		} catch (DAOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	
	public List<Map<String, String>> getQcOrderTypeSelectionList() {
		try {
			return imsDSQCDao.getQcOrderTypeSelectionList();
		} catch (DAOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	
	public String getAmendRemarks(String orderId){
		try {
			return imsDSQCDao.getAmendRemarks(orderId);
		} catch (DAOException e) {
			e.printStackTrace();
		}
		return null;
	};
	public void updateQcRemarks(String orderId,BomSalesUserDTO userDto,String remarks) throws DAOException{
		imsDSQCDao.updateQcRemarks(orderId,userDto,remarks);
	}
	
	public boolean isActiveQCstaff(String staffID) throws DAOException{
		return imsDSQCDao.isActiveQCstaff(staffID);
	}
	
	public boolean isOrderAwaitQC(String orderId) throws DAOException{
		return imsDSQCDao.isOrderAwaitQC(orderId);
	}
	public boolean isOrderAwaitSignOff(String orderId) throws DAOException{
		return imsDSQCDao.isOrderAwaitSignOff(orderId);
	}
	public List<ImsQcAssignUI> getTodayOutstandingOrders (String staffId)throws DAOException{
		return imsDSQCDao.getTodayOutstandingOrders(staffId);
	} 
	public List<ImsQcAssignUI> getTotalOrders (String staffId)throws DAOException {
		return imsDSQCDao.getTotalOrders(staffId);
	}
	public List<ImsQcAssignUI> getP7dayOutstandingOrders (String staffId)throws DAOException {
		return imsDSQCDao.getP7dayOutstandingOrders(staffId);
	}
	public List<ImsQcAssignUI> getTodayAssignedOrders (String staffId)throws DAOException{
		return imsDSQCDao.getTodayAssignedOrders(staffId);
	} 
	public void updateOrderCount (ImsQcAssignUI imsQcAssignUI) throws DAOException{
		imsDSQCDao.updateOrderCount(imsQcAssignUI);
	}
	//Celia qc enquiry
	
	private ImsOrderAmendBomDAO imsOrderAmendBomDAO;
	public ImsOrderAmendBomDAO getImsOrderAmendBomDAO() {
		return imsOrderAmendBomDAO;
	}

	public void setImsOrderAmendBomDAO(ImsOrderAmendBomDAO imsOrderAmendBomDAO) {
		this.imsOrderAmendBomDAO = imsOrderAmendBomDAO;
	}
	public List<String> getTeamCodeList(int channelId){
		try {
			return imsDSQCDao.getTeamCodeList(channelId);
		} catch (DAOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<String> getQCHousingType() {
		try {
			return imsOrderAmendBomDAO.getQCHousingType();
		} catch (DAOException e) {
			e.printStackTrace();
		}
		return null;
	}
	public List<String> getQCStaffType() {
		try {
			return imsDSQCDao.getQCStaffType();
		} catch (DAOException e) {
			e.printStackTrace();
		}
		return null;
	}
	public List<ImsAlertMsgDTO> getImsDSQCOrderEnquiryListInfo(
			ImsQcComOrderSearchUI enquiry, BomSalesUserDTO bomSalesUserDTO)	{
		try {
				
				return imsDSQCDao.getImsQCOrderEnquiryListInfo(enquiry, bomSalesUserDTO);
			} catch (DAOException e) { 
				e.printStackTrace();
			}
			return null;
		}
	

	private ResourceLoaderService resourceLoaderService;
	 
	public ResourceLoaderService getResourceLoaderService() {
		return resourceLoaderService;
	}


	public void setResourceLoaderService(ResourceLoaderService resourceLoaderService) {
		this.resourceLoaderService = resourceLoaderService;
	}
	
	public void genImsDSQcComXls(List<ImsAlertMsgDTO> imsOrderList,OutputStream outputStream) {
		String TEMPLATE_EXCEL = "/excel/Direct_Sales.xls";
		String TEMPLATE_PAGE = "Sheet1";

		logger.info("imsOrderList "+imsOrderList.size());
		Resource resource = resourceLoaderService.getResource(TEMPLATE_EXCEL);

		HSSFWorkbook WB = null;

		try {
			InputStream is = resource.getInputStream();
			WB = new HSSFWorkbook(is);
			Map<String, HSSFFont> pFontMap = this.CreateHSSFFontMap(WB);
			HSSFSheet Sheet = WB.getSheet(TEMPLATE_PAGE);
			this.ExportSheet(Sheet, WB, pFontMap,imsOrderList);
			WB.write(outputStream);
			
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			
		} 
	}
	private void ExportSheet(HSSFSheet Sheet,HSSFWorkbook WB, Map<String, HSSFFont> pFontMap,List<ImsAlertMsgDTO> imsOrderList){
		
		logger.info("in ExportSheet");
		
		if(imsOrderList == null || imsOrderList.isEmpty())
			return;
		
		int startRow = 2;
		
		for(ImsAlertMsgDTO dto: imsOrderList){
				HSSFRow row = Sheet.createRow((short) startRow);
				//row.setHeight((short)600);
				row.createCell((short)0).setCellValue(dto.getOrderId());
				row.createCell((short)1).setCellValue(dto.getSalesTeam());
				row.createCell((short)2).setCellValue(dto.getCreateBy());
				row.createCell((short)3).setCellValue(dto.getCustName());
				row.createCell((short)4).setCellValue(dto.getIdDocNum());
				row.createCell((short)5).setCellValue(dto.getLoginId());
				row.createCell((short)6).setCellValue(dto.getPaymentMtd());
				row.createCell((short)7).setCellValue(dto.getIs3rdParty());
				row.createCell((short)8).setCellValue(dto.getHousingType());
				row.createCell((short)9).setCellValue(dto.getStaffType());
				row.createCell((short)10).setCellValue(dto.getAssignee());		
				row.createCell((short)11).setCellValue(dto.getAppDate());
				row.createCell((short)12).setCellValue(dto.getServiceReqDate());
				row.createCell((short)13).setCellValue(dto.getSignoffDate());
				row.createCell((short)14).setCellValue(dto.getAmendment());
				row.createCell((short)15).setCellValue(dto.getAmendmentHistory());				
				row.createCell((short)16).setCellValue(dto.getOrderStatus());
				row.createCell((short)17).setCellValue(dto.getQcStatus());
				
				
				startRow++;
			}
 }	
	
	public  Map<String, HSSFFont> CreateHSSFFontMap(HSSFWorkbook pWorkbook){
		
		Map<String, HSSFFont> pFontMap  = new TreeMap<String, HSSFFont>();
		
		for(int i = 0 ; i <7 ; i++){
			
			String key = Integer.toBinaryString(i);
			key =  String.format("%3s", key).replace(' ', '0');

			char isBold = key.charAt(0);
			char isItalic = key.charAt(1);
			char isUnderline = key.charAt(2);
			
			HSSFFont font = pWorkbook.createFont();
			
			if(isBold == '1')
				font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			
			if(isItalic == '1')
				font.setItalic(true);
			
			if(isUnderline == '1')
				font.setUnderline(HSSFFont.U_SINGLE);
			
			pFontMap.put(key, font);
			
		}
		
		
		return pFontMap;
	}


    //Celia end
	
	
	//kinman for QC front order enquiry
	public List<ImsAlertMsgDTO> getQcFrontOrderEnquiryInfo(DsQCImsOrderEnquiryUI enquiry, BomSalesUserDTO userDto){
		try {
			
			return imsDSQCDao.getQcFrontOrderEnquiryInfo(enquiry, userDto);
		} catch (DAOException e) { 
			e.printStackTrace();
		}
		return null;
		
	}
	
	//kinman for QC process detail
	public List<String> getSameCustOrder(String orderId){
		try {
			return imsDSQCDao.getSameCustOrder(orderId);
		} catch (DAOException e) { 
			e.printStackTrace();
		}
		
		return null;
	}
	
	public int changeAwaitQCOrderStatus(String orderId)throws DAOException{
		int returnCode = 0;
		try { 
			returnCode = imsDSQCDao.changeAwaitQCOrderStatus(orderId);	
		} catch (DAOException e) { 
			e.printStackTrace();			
		}
		
		return returnCode;
	}


	
}

