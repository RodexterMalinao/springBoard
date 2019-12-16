package com.bomwebportal.ims.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.bomwebportal.dto.BomwebSubscribedOfferImsDTO;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.ims.dto.ImsAllOrdDocAssgnDTO;
import com.bomwebportal.ims.dto.OrderImsDTO;
import com.bomwebportal.ims.dto.ui.ImsInstallationUI;
import com.bomwebportal.ims.dto.ui.OrderImsUI;


public interface ImsOrderService {
	public String getCodeFromWCodeLkup (String grpId);
	public List<ImsAllOrdDocAssgnDTO> getSupportDocFromDB(String orderId);
	public Boolean isSignOffAfExist(OrderImsUI order);
	public OrderImsUI saveNewOrder(OrderImsUI order);
	public String getNewOrderId(String shopCd,String staffId);
	public String getNewOrderId(String shopCd, String staffId,String orderType);
	public OrderImsUI getBomWebImsOrder(String orderId);
	public OrderImsUI updateOrder(OrderImsUI order);
	//public void syncOrderToBOM(String orderId);	
	public void submitCashPermitWQ(OrderImsUI order);
	public void submitWaivePrepayWQ(OrderImsUI order);
	public void submitWaiveOTCWQ(OrderImsUI order);
	public void submitDiscountedOTCWQ(OrderImsUI order);
	public void updateSyncOrderStatus(String orderId);
	public String getAutoProcessOrderId();
	public OrderImsUI getBomSyncOrderDetail(String orderId);
	public boolean isCreateChangeOrder(OrderImsUI order);
	public Date getApplicationDate();
	public void cancelPendingOrder();
	public List<String> getPendingOrderIdList(int withinMinute);
	public void submitSBWQ(OrderImsUI order, String reasonCd);
	
	//steven added for suspend wq 20131217 start
	public void orderStatusAlertWQ(OrderImsUI order, String wqNatureId, String reasonCd);
	public Boolean needSuspendWQ(String orderStatus, String createBy);
	//steven added for suspend wq 20131217 end
	public List<ImsInstallationUI> getImsCustomer(String idDocType, String idDocNum);
	
	// ims direct sales
	public String genQRCodeString(OrderImsUI order, String depInd, String amt);
	public void saveQRCodeFile(OrderImsUI order, String billType, String amt);
	public void dsAlertWQ(OrderImsUI order, String wqNatureId, String temp_BomWeb_Remark_dtl, String fsRemarks);
	public String genProgOfferChangeRmk(OrderImsUI order); 
	public void updateWqAttachment(String orderId, String docType);
	public void insertBomwebAmendTables(OrderImsUI order, String p_wq_batch_id, String isNtvOrder);
	public String isDsStaffMobbile(String staffId, String mobNum);
	public void getBackendOfferChange(OrderImsUI order);
	public String getL1OrderNum(String orderId);
	public void acqOrdBackendOfferChange(String orderId);
	public String isDs5DummyStaff(String staffId);
	public String isDsMobileAgent(String teamCd, String mobileNo);
	
	//NOWTVSALES
	public String readQRCode(OrderImsUI order);
	public boolean finishCashDepositRenameQR(OrderImsUI order);
	public void deleteQRCodeFile(String orderId);
	public void setCreateByUserDto(OrderImsUI order);
	public List<BomwebSubscribedOfferImsDTO> getBomwebSubOfferSophieNowRet(String txnId);	
	public void insertBomWebSubscribedOfferImsAmd(OrderImsUI order) ;
	public void insertNowRetAmendTvOnly(OrderImsUI order); 
	public String isNowRetAmendTvOnly(OrderImsUI order); 
	public BomwebSubscribedOfferImsDTO getHomeNetworkRequired(String sbno, String unit, String floor, String technology);
	public boolean checkQRCodeExist(OrderImsUI order, String fileName);

	public String isMatchRole(String channelCd, String catogery) throws DAOException;
	public String isDS6User(String staffID) throws DAOException;
	
	// martin, BOM2019039
	public String genDrgnAccount(String billingAccount, String depInd);
	public void saveQRCodeFile(byte[] file, String fileName, OrderImsUI order);
	public boolean updateOrderReasonCd_R014(String orderId, String updateBy);
	public boolean updateOrderReasonCd_R008(String orderId, String updateBy);
	public void updateImsOrderLast2Hours_R014() throws DAOException;
}
