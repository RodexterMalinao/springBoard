package com.bomwebportal.service.qc;

import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.dto.QcStaffDTO;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.ims.dto.ImsAlertMsgDTO;
import com.bomwebportal.ims.dto.ui.ImsDsQCProcessUI;
import com.bomwebportal.ims.dto.ui.ImsDsQCStaffAdminUI;
import com.bomwebportal.ims.dto.ui.DsQCImsOrderEnquiryUI;
import com.bomwebportal.ims.dto.ui.ImsDsQcProcessDetailUI;
import com.bomwebportal.ims.dto.ui.ImsQcAssignUI;
import com.bomwebportal.ims.dto.ui.ImsQcComOrderSearchUI;
import com.bomwebportal.ims.dto.ui.QcImsAdminUI;



public interface ImsDSQCService {
	public void insertSalesTypeAndLocation(BomSalesUserDTO amend);
	public void updateSalesTypeAndLocation(BomSalesUserDTO amend);
	public boolean isDcStaffExist(String staffId) throws DAOException ;
    
	//QC Assigment
	public List<ImsAlertMsgDTO> getImsDsQcOrderEnquiryInfo(DsQCImsOrderEnquiryUI enquiry, BomSalesUserDTO bomSalesUserDTO);
	public List<ImsAlertMsgDTO> getImsDsQcProcessEnquiryInfo(ImsDsQCProcessUI enquiry, BomSalesUserDTO bomSalesUserDTO);
	public List<String> getRoleCodeByUserIDLkupCode(String userId, String lkupCode, String lkupFuncCode);
	public List<String> getChannelCodeListByChannelID (int channelId);
	public Boolean checkIfSalesManager(String user);
	public List<String> getOrderStatusList();
	public String getSalesTypeCode(String salesType) throws DAOException;
	public List<ImsQcAssignUI> getQcStaffInfo(int channelID) throws DAOException;
	public BomSalesUserDTO getDSinfo(BomSalesUserDTO user) throws DAOException;
	public List<String>getQcSkills(String staffID) throws DAOException;
	public void insertQcAssign(ImsQcAssignUI imsQcAssignUI) throws DAOException;
	public void insertQcAssignBulk(List<ImsQcAssignUI> imsQcAssignUIList)throws DAOException;
	public List<ImsDsQcProcessDetailUI>getQcRemark(String orderId)throws DAOException;
	public boolean checkIfAssignisNull(String orderId);
	public void updateQcAssign(ImsQcAssignUI imsQcAssignUI) throws DAOException ;	
	//QC Assigment END
	
	//QC Admin END
	public List<QcImsAdminUI> getQcStaffInfoAdmin(int channelID) throws DAOException;
	public void insertNewQcStaffSkills(QcStaffDTO qcStaffDTO )throws DAOException;
	public void insertNewQcStaffInfo(QcStaffDTO qcStaffDTO) throws DAOException; 
	public boolean isQcStaffExist(String staffId) throws DAOException;
	public Boolean isStaffExist(String salesCD)	throws DAOException;
	public void updateQCstaffSkillsAndStatus(QcStaffDTO qcStaffDTO) throws DAOException;
	//QC Admin END
	
	//QC staff admin
	public void updateControlStaffRatio(ImsDsQCStaffAdminUI ImsStaffAdminUI) throws DAOException;
	public void updateCleanStaffRatio(ImsDsQCStaffAdminUI ImsStaffAdminUI) throws DAOException;
	public List<ImsDsQCStaffAdminUI> getQcStaffRatio(String type)throws DAOException;
	//QC NTV staff admin
	public void updateControlNTVStaffRatio(ImsDsQCStaffAdminUI ImsStaffAdminUI) throws DAOException;
	public void updateCleanNTVStaffRatio(ImsDsQCStaffAdminUI ImsStaffAdminUI) throws DAOException;
	public List<ImsDsQCStaffAdminUI> getQcNTVStaffRatio(String type)throws DAOException;
	
	public void releaseQcOrders(QcStaffDTO qcStaffDTO) throws DAOException;
	public void removeQcStaff(QcStaffDTO qcStaffDTO) throws DAOException;
	public List<ImsQcAssignUI> getTodayOutstandingOrders (String staffId)throws DAOException ;
	public List<ImsQcAssignUI> getTotalOrders (String staffId)throws DAOException ;
	public List<ImsQcAssignUI> getP7dayOutstandingOrders (String staffId)throws DAOException ;
	public List<ImsQcAssignUI> getTodayAssignedOrders (String staffId)throws DAOException; 
	public void updateOrderCount (ImsQcAssignUI imsQcAssignUI) throws DAOException;
	//QC staff admin END
	
	//QC process 
	public void insertQcProcess(ImsDsQcProcessDetailUI imsDsQcProcessDetailUI) throws DAOException;
	public void updateQcProcess(ImsDsQcProcessDetailUI imsDsQcProcessDetailUI) throws DAOException;
	public boolean checkOrderExist(String OrderID) throws DAOException;
	public String getAssignDate(String OrderID) throws DAOException;
	public String getAssignee(String OrderID) throws DAOException;
	public List<ImsDsQcProcessDetailUI> getReasonCQDesc() throws DAOException;
	public List<ImsDsQcProcessDetailUI> getReasonFailDesc() throws DAOException;
	public String getReasonCode(String desc) throws DAOException;
	public List<ImsDsQcProcessDetailUI> getQCProcessInfo(String orderId) throws DAOException ;
	public String getPreInstallInd(String OrderID) throws DAOException;
	public List<String> getQcStatusList();
	public List<Map<String, String>> getQcOrderTypeSelectionList();
	public String getAmendRemarks(String orderId);
	public void updateQcRemarks(String orderId,BomSalesUserDTO userDto,String remarks) throws DAOException;
	public boolean isActiveQCstaff(String staffID) throws DAOException;
	public boolean isOrderAwaitQC(String orderId) throws DAOException;
	public boolean isOrderAwaitSignOff(String orderId) throws DAOException;
	//Celia
	public List<String> getTeamCodeList(int channelId);
	public List<String> getQCHousingType(); 
	public List<String> getQCStaffType(); 
	public List<ImsAlertMsgDTO> getImsDSQCOrderEnquiryListInfo(
			ImsQcComOrderSearchUI enquiry, BomSalesUserDTO bomSalesUserDTO);
	public void genImsDSQcComXls(List<ImsAlertMsgDTO> imsOrderList,OutputStream outputStream);
	//Celia end
	/*public AppointmentTimeSlotDTO getAmendSRDInfoStorProd(String orderId, String amendType) throws DAOException;
	public List<WorkQueueNatureDTO> getTypeSubtypeByNatureID(List<String> WQ_NATURE_ID);
	public List<DelayReasonDTO> getCancelReason();
	public List<AmendWqDTO> getAmendNature();
	public void insertBomwebAmendCategory(AmendOrderImsUI amend, WorkQueueDTO dto);
	public List<HousingTypeDTO>  getHousingTypeByOrderID (String orderId);
	public List<DelayReasonDTO> getDelayReasons(String what);
	public Boolean  isPendingExist (String orderId);
	public Boolean  isOCIDexist (String orderId);
	public WorkQueueDTO imsAmendWorkQueue(AmendOrderImsUI amend);
	public List<ImsAlertMsgDTO> getImsAlertMsgList(List<String> sbid);
	public List<ImsAutoSyncWQDTO> getPendingFromSpringBoard();
	public ImsAutoSyncWQDTO getPendingFromBOM (ImsAutoSyncWQDTO sbIds);
	public Boolean insertNewWQStatus(String wqWpAssgnId);
	public Boolean setWQStatusOutdated(String wqWpAssgnId);
	public Boolean updateBomwebAmendCategory(String sbid, String wqNatureId);
	public Boolean updateBomwebAmendCategoryFS(String sbid);
	public List<String> getPrivilegedOrderIdList(List<String> orderIdList, BomSalesUserDTO userDto, String lkupCode, String lkupFuncCode);
	public Boolean  isPaymentMethodIsCC (String orderId);
	public String lockBy(String orderId);
	public void updateWqSrd(AmendOrderImsUI amend);
	public Boolean isShortage(String orderId);
	public void setWqSrdNull(String orderId);
	public Boolean isL1Distributed(String orderId);
	*/

	//kinman for QC front order enquiry
	public List<ImsAlertMsgDTO> getQcFrontOrderEnquiryInfo(DsQCImsOrderEnquiryUI enquiry, BomSalesUserDTO bomSalesUserDTO);
	
	
	//kinman for QC process detail
	public List<String> getSameCustOrder(String orderId);
	
	
	public int changeAwaitQCOrderStatus(String orderId)throws DAOException;
	
}
