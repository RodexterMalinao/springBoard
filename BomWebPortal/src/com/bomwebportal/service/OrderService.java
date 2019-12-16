package com.bomwebportal.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.bomwebportal.dto.AccountDTO;
import com.bomwebportal.dto.AllOrdDocAssgnDTO;
import com.bomwebportal.dto.BasketDTO;
import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.dto.BomWebMupDTO;
import com.bomwebportal.dto.CNMRTSupportDocDTO;
import com.bomwebportal.dto.ClubPointDetailDTO;
import com.bomwebportal.dto.ComponentDTO;
import com.bomwebportal.dto.CustomerProfileDTO;
import com.bomwebportal.dto.IGuardDTO;
import com.bomwebportal.dto.MnpDTO;
import com.bomwebportal.dto.MobBuoQuotaDTO;
import com.bomwebportal.dto.MobileSimInfoDTO;
import com.bomwebportal.dto.MultiSimInfoDTO;
import com.bomwebportal.dto.OrderDTO;
import com.bomwebportal.dto.OrderMobDTO;
import com.bomwebportal.dto.PaymentDTO;
import com.bomwebportal.dto.SubscriberDTO;
import com.bomwebportal.dto.VasDetailDTO;
import com.bomwebportal.dto.VasMrtSelectionDTO;
import com.bomwebportal.dto.VasOnetimeAmtDTO;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.mob.ccs.dto.CodeLkupDTO;
import com.bomwebportal.mob.ccs.dto.MobCcsMrtBaseDTO;
import com.bomwebportal.mob.ccs.dto.StockAssgnDTO;
import com.bomwebportal.mob.ccs.dto.StockDTO;
import com.bomwebportal.mob.ccs.dto.ui.MobCcsFalloutUI;
import com.bomwebportal.mob.ds.dto.MobDsPaymentTransDTO;
import com.bomwebportal.mob.ds.dto.MobDsPaymentUpfrontDTO;
import com.pccw.bom.mob.schemas.ProductDTO;

public interface OrderService {

	//For CCS
	void insertBomWebAll(String orderId, String selectedBasketId,
			OrderDTO orderDto, SubscriberDTO subscriberDto, MnpDTO mnpDto,
			MobileSimInfoDTO mobileSimInfo, PaymentDTO paymentDto,
			AccountDTO accountDto, CustomerProfileDTO customerInfoDto, String selectedItemList[], List<ComponentDTO> componentList, String channelId, IGuardDTO iGuardDto
			, VasDetailDTO vasDetailDto, BasketDTO basketDto, List<MultiSimInfoDTO> multiSimInfos, List<VasDetailDTO> systemAssignVasDetailList
			, List<MobCcsMrtBaseDTO> mrtDtoList,List<MobBuoQuotaDTO> mobBuoQuotaDtoList, List<String> authorizeLog);
	/*//For Retails
	void insertBomWebAll(String orderId, String selectedBasketId,
			OrderDTO orderDto, SubscriberDTO subscriberDto, MnpDTO mnpDto,
			MobileSimInfoDTO mobileSimInfo, PaymentDTO paymentDto,
			AccountDTO accountDto, CustomerProfileDTO customerInfoDto, String selectedItemList[], List<ComponentDTO> componentList, String channelId
			, List<AllOrdDocAssgnDTO> allOrdDocAssgnDTOs, IGuardDTO iGuardDto
			, VasDetailDTO vasDetailDto, BasketDTO basketDto);*/
	//For Direct Sales
	void insertBomWebAll(String orderId, String selectedBasketId,
			OrderDTO orderDto, SubscriberDTO subscriberDto, MnpDTO mnpDto,
			MobileSimInfoDTO mobileSimInfo, PaymentDTO paymentDto,
			AccountDTO accountDto, CustomerProfileDTO customerInfoDto, String selectedItemList[], List<ComponentDTO> componentList, String channelId
			, List<AllOrdDocAssgnDTO> allOrdDocAssgnDTOs, IGuardDTO iGuardDto
			, VasDetailDTO vasDetailDto, BasketDTO basketDto, List<MobDsPaymentTransDTO> paymentUpfrontDTOs, List<MultiSimInfoDTO> multiSimInfos
			, List<VasDetailDTO> systemAssignVasDetailList, List<MobCcsMrtBaseDTO> mrtDtoList,List<MobBuoQuotaDTO> mobBuoQuotaDtoList, List<String> authorizeLog);
	void updateBomWebOrderStatus (String orderId, String status);
	void updateBomWebOrderStatus (String orderId, String status, String supervisor_approve, String mobsupport_approve);
	void updateBomWebOrderStatus (String orderId, String status, String supervisor_approve, String mobsupport_approve, String checkPoint);
	void updateOrderStatus(String refNum, String status, String errCode, String errString, String lastUpdateBy);
	void updateBulkReqStatusSuccess(String refNum, String ocid, String status, String errCode, String lastUpdateBy);
	void deleteBomWebAllOrder (String orderId, String channelId, String oldMsisdn, String staffId, Date appDate);
	long getHandsetDeviceAmount(String orderId);
	long getFirstMonthFee(String orderId);
	long getPrepaymentWithoutHandset(String orderId);
	List<VasOnetimeAmtDTO> getVasOnetimeAmtList (String locale, String orderId);
	List<VasOnetimeAmtDTO> getRetChgSimAdmChargeList(String locale, String orderId);
	List<VasOnetimeAmtDTO> getRetToo1AdmChargeList(String locale, String orderId);
	List<VasDetailDTO> getRebateList (String locale, String orderId);
	String[] getPenaltyInfoList (String orderId);
	String[] getHandsetDeviceDescription (String locale, String brandId, String modelId, String colorId);
	List<ProductDTO> getBomProductList (String orderId);
	//public String getShopSeq(String shopCd);
	String getSecSrvContractPeriod(String orderId);
	List<OrderDTO> getOrderSummaryList (String shopCode, String dateStr, String orderIdStr);
	
	MnpDTO getMnp (String orderId );
	PaymentDTO getPayment (String orderId);
	MobileSimInfoDTO getSim (String orderId);
	MobileSimInfoDTO getTooMobileSimInfo (String orderId );
	MobDsPaymentUpfrontDTO getPaymentUpfront (String orderId);
	String[] getBasketBrandModelInfoList (String orderId);//0:basket_id, 1:brand_id, 2:model_id
	String[] getTOOBRMBasketBrandModelInfoList (String orderId);//0:basket_id, 1:brand_id, 2:model_id
	OrderDTO getOrder (String orderId );
	AccountDTO getAccount (String orderId );
	List<String> getBundleVASList(String basketId, String mipBrand, String mipSimType);
	List<String> getSelectedItemList(String orderId);
	List<ComponentDTO> getComponentList(String orderId);
	void deleteBomWebNoneSuitableSim(String orderId, String simKeepItemId);
	List<String> getSignoffOrderList();
	SubscriberDTO getBomWebSub(String orderId);
	String getConciergeInd(String orderId);
	long getFirstMonthServiceLicenceFee(String orderId);//2011-07-22 (only BFEE)
	String getBasketType(String orderId); //add eliot 20110822
	void insertPDF_To_DB(String orderId, byte[] pdfData); //add eliot 20110831
	String getOrderStatus(String orderId);
	
	String getShopSeq(String shopCd, String channelId);//add 2011-12-19 new order id format, eg.eg.RTP1M0000001
	/**
	 * select basket Id from bomweb_subscribed_item table
	 * @param orderId
	 * @return Basket ID
	 * @throws DAOException 
	 */
	String findBasketId(String orderId);
	/**
	 * Get Stock Assignment Information
	 * @param orderId
	 * @return List of StockDTO
	 */
	List<StockDTO> getStockAssignment(String orderId);
	List<StockDTO> getParentOrderStockAssignment(String orderId);
	//add by Eliot 20120509
	List<StockDTO> getDOAStockAssignment(String orderId);
	String orderStatusProcess(String orderId);
	int insertBomwebCustQuotaInUse(String idDocType,String  idDocNum,String  orderId,String userId,String basketId);
	String orderMrtUpdate(String orderId);
	String getMobCcsOrderStatusDesc(String orderId);
	//public long getOrderPaidAmount(String orderId);
	OrderDTO getOrderWithPaidAmount (String orderId );
	
	void updateBomWebSim(MobileSimInfoDTO dto);
	VasDetailDTO getVasDetailDTO(String orderId, Date appDate);
	
	void updateCcsOrderStatus(String refNum, String status, String checkPoint, String reasonCd, String errCode, String errString, String lastUpdateBy);
	void updateCcsCreateOrderStatus(String refNum, String status, String checkPoint, String reasonCd, String errCode, String errString, String lastUpdateBy);
	void updateCcsBulkReqStatusSuccess(String refNum, String ocid, String status, String checkPoint, String reasonCd, String errCode, String lastUpdateBy);
	void updatePendingOrderCheckPoint(String orderId, String fromCheckPoint, String toCheckPoint);
	
	String getMultiIMEI(String orderId);
	void updatePayment(PaymentDTO dto);
	
	void updateOrderFallOut(OrderDTO dto);
	String manualOrderStatusProcess(String orderId);
	String orderSubmitProcess(String orderId);
	// MIP.P4 modification
	List<VasDetailDTO> getSelectedVasList(String locale,	String orderId, String channelId, String basketId, String appDate, String channelCd, String offerNature);
	void insertOrderFallout(String orderId, String userName, String failType, String reasonCd, String remark);
	void synBomOrderStatus(String orderId, String orderStatus, String lastUpdateBy);
	List<MobCcsFalloutUI> getOrderFalloutHist(String orderId);
	void updateOrderSrvReqDate(String orderId, java.util.Date srvReqDate, String username);
	String cancelOrderReleaseGoods(String orderId);
	String assignSimForCancel(String orderId);
	String cancelCourierQuotaProcess(String orderId, String username);
	void updateOrderReasonCode(String orderId, String reasonCode, String username);
	/*public void backupDeleleInsertBomWebAll(String orderId, String selectedBasketId,
			OrderDTO orderDto, SubscriberDTO subscriberDto, MnpDTO mnpDto,
			MobileSimInfoDTO mobileSimInfo, PaymentDTO paymentDto,
			AccountDTO accountDto, CustomerProfileDTO customerInfoDto,
			String selectedItemList[], List<ComponentDTO> componentList) ;*/
	void backupDeleteBomWebAll(String orderId);
	void backupOrder(String orderId);
	void updateCcsOrderStatusToCancel(String orderId, String status, String checkPoint, String reasonCd
			, String lastUpdateBy, String histSeqNo);
	void cancelOrderProcess(String orderId, String cancelStatus, String lastUpdBy);
	
	void updateCcsBulkReqStatusSuccessHist(String refNum, String ocid, String status, String checkPoint, String reasonCd, String errCode, String lastUpdateBy);
	String getOrderId(String ocid); 
	void paymentTransProcess(String oldOrderId, String newOrderId,String lastUpdBy);
	String cancelOrderReleaseStock(String orderId);
	String getOrderHistSeqNo(String orderId);
	void copyBomwebStockAssign(String oldOrderId, String newOrderId, String lastUpdBy);
	String cancelRecreateOrderStockInsStockAssgnByCloneOrder(String oldOrderId, String newOrderId, String lastUpdBy);
	String cancelRecreateOrderStockMrtTransfer(String oldOrderId, String newOrderId);
	List<ComponentDTO> getPassToBomComponentList(String orderId);
	List<OrderDTO>  getOrderList(String shopCode, String lob, String orderStatus, String startDate, String endDate, String saleCd, String orderIdStr, String serviceNum, String dms, String bomStartDate, String bomEndDate);
	List<OrderDTO>  getOrderList(String shopCode, String lob, String orderStatus, String startDate, String endDate, String saleCd, String orderIdStr, String serviceNum, String dms, String bomStartDate, String bomEndDate, String orderType);
	String getAllowSearchAllInd(String loginStaffId);

	void insertEmailReq(String orderId, String templateId, String pdfFileName, String createBy);
	// update esig_email_addr
	int updateEsignEmailAddr(String orderId, String esigEmailAddr, String lastUpdateBy);
	
	int updateSMSNo(String orderId, String SMSno, String lastUpdateBy);
	
	int updateDmsInd(String orderId, String dmsInd, String lastUpdateBy);
	
	String getOcidByOrderID(String orderId);
	public String getShopSeqStoredProcedure(String shopCd, String channelId);
	
	boolean getBOMandSBOrderInfo(String orderId);
	
	OrderMobDTO getOrderMobDTO(String orderId);
	
	List<StockAssgnDTO> getStockAssgnListByOrderId(String orderId);
	
	void updateDsMsisdnStatus(String msisdn, int status, String orderId, String staffId, Date appDate);
	void updateBomWebPaymentTransStatus(String orderId, String status);
	List<String> getCodeIdList(String codeType);
	int cancelDSOrderReleaseStock(String orderId);
	String getDsOrderStatus(String orderId);
	void updateDsOrderStatus(String orderId, String status);
	String getDsPrevOrderStatus(String orderId);
	void updateDsPrevOrderStatus(String orderId, String status);
	void updateDsPaymentCheck(String orderId, String staffId, String paymentCheck);
	void updateDsSupoortDocCheck(String orderId, String staffId, String suppportDocCheck);
	BomSalesUserDTO getSalesUserInfo(String staffId, String appDate);
	List<String> getFrozenWindow(String inDate);
	String getOrderIdUsingSameMRT(String mrt, String orderId);
	String getOrderIdUsingSameMRTShop(String mrt, String orderId, String shopCd);
	void updateDsStockMrtAfterSuccess(String orderId);
	
	//multisim cancel
	boolean isMultiSimOrder(String orderId);
	void updateMultiSimMemberStatus(String parentOrderId, String memberOrderId, String status, String errCode, String errString , String lastUpdateBy);
	void cancelMultiSimMember(String parentOrderId, String lastUpdateBy);
	
	void updateMemOrder(String memOrderId, String checkPoint, String errCode, String errString, String lastUpdBy);
	
	//Retention
	String getOrderNature(String orderId);
	int getOrderChannel(String orderId);
	
	// svc
	Map<String,String> getOrderType(String orderId);
	
	String getSimTypeByCosOrder(String orderId);
	String getMdoInd(String orderId);
	boolean getOctFlag();
	void saveCslOrderRecord(String orderId, String staffId);
	MobileSimInfoDTO getChangedSim (String orderId);
	void copyBomwebMobOrderHsrmLog(String oldOrderId, String newOrderId, String lastUpdBy);
	
	boolean hasTNGServiceDummyVas(String orderId);
	boolean hasStudentPlanTNGStock(String orderId);
	List<MobDsPaymentTransDTO> updateDsCashPay(String orderId, List<MobDsPaymentTransDTO> list);

	boolean isCosOrderFrozen(String orderId);
	VasMrtSelectionDTO getVasMrtSelectionDTO(String orderId);
	VasMrtSelectionDTO getSsMrtSelectionDTO(String orderId);
	
	public String getOrderBrand(String orderId);
	public String getSignOffDate (String orderId);
	List<BomWebMupDTO> multiSimInfoDTOToBomwebMupDTO (MultiSimInfoDTO msi);
	public String getSbPendingOrderListString(String msisdn, String orderId);
	public void updateCNMRTSupportDoc(CNMRTSupportDocDTO dto);
	public List<CNMRTSupportDocDTO> getCNMRTSupportDocList(CNMRTSupportDocDTO dto);
	public String getCNMRTSupportDocNewSeqNo(CNMRTSupportDocDTO dto);
	public void createCNMRTSupportDoc(CNMRTSupportDocDTO dto);
	public String getTooOrderMrt(String OrderId);
	List<ClubPointDetailDTO> getClubPointDetailsByOrderId(String orderId);
	public String getMnpInd(String orderId);
	public String getOnlineInd(String orderId);
	public boolean isIphone7TradeInBasket (String orderId,List<CodeLkupDTO> codeIdList);
	}

