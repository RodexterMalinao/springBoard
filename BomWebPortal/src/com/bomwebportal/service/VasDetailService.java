package com.bomwebportal.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.bomwebportal.dto.BasketDTO;
import com.bomwebportal.dto.BasketMinVasLkupDTO;
import com.bomwebportal.dto.BasketParmDTO;
import com.bomwebportal.dto.ExclusiveItemDTO;
import com.bomwebportal.dto.ProductInfoDTO;
import com.bomwebportal.dto.ResultDTO;
import com.bomwebportal.dto.SbItemPreDTO;
import com.bomwebportal.dto.SimDTO;
import com.bomwebportal.dto.VasAttbComponentDTO;
import com.bomwebportal.dto.VasDetailDTO;
import com.bomwebportal.dto.report.RptVasDetailDTO;
import com.bomwebportal.mob.ccs.dto.ui.MRTUI;
import com.bomwebportal.mob.ccs.dto.ui.PaymentMonthyUI;

public interface VasDetailService {

	//public List<VasDetailDTO> getVasDetailtList(String locale, String basketId, String [] selectedItemList, String displayType);//for vas select page use
	
	// MIP.P4 modification
	public List<VasDetailDTO> getVasDetailList(String channelId, String locale, String basketId, String[] selectedList, String displayType, String appDate, String channelCd, String mipBrand, String mipSimType, String offerNature); 

	// MIP.P4 modification
	public List<VasDetailDTO> getHardBundleVasDetailList(String channelId, String locale, String basketId, String[] selectedList,  String displayType, String appDate, String channelCd, String mipBrand, String mipSimType, String offerNature);

	public List<VasDetailDTO> getVasDetailSelectedList (String locale, String orderId); // for summary page use
	
	// MIP.P4 modification
	public List<VasDetailDTO> getRPHSRPList(String locale, String basketId, String appDate, String channelCd, String mipBrand, String mipSimType, String offerNature);
	
	//public String getOrderId();
	public List<VasDetailDTO> getReportUseVasDetailtList (String locale, String orderId, String basketId); //for report use only. 20110125

	public List<VasDetailDTO> getReportUseRPHSRPList (String locale, String basketId, String displayType, String orderId);
	// for vasdetail table edit
	public String getBasketDisplayTitle (String locale, String basketId);
	//public List<VasDetailDTO> getBFEEList (String locale, String basketId);
	public List<ExclusiveItemDTO> getExclusiveItemList(List<String> selectedList);

	public List<ProductInfoDTO> getBomProductList (String itemId);	
	public List<VasAttbComponentDTO> getVasAttbComponentList(List<String> vasList, String channelId);
	public List<String> getSubscribedVASList(String basketId, String[] selectedItemList, String mipBrand, String mipSimType);
	public List<ProductInfoDTO> getBomProductNonDisplayItemList (String basketId);
	public List<VasDetailDTO> getReportUseFreeGifsDetailtList(String locale,String orderId, String basketId) ;
	public List<VasDetailDTO> getReportUseVasOptionalDetailtList(String locale,
			String orderId, String basketId);
	public List<VasDetailDTO> getUserSelectedBasketItemList(String locale, String appDate, String basketId,String selectedItemList[]/*, String mnpInc, String apInc*/);
	public BasketDTO getBasketAttribute(String basketId, String appDate);
	
	// MIP.P4 modification
	public List<SimDTO> getSimSelection(String locale, String appDate, String basketId, String orderId, String channelCd, String mipSimType, String mipBrand, String ivOfferNature);
	// MIP.P4 modification
	public List<VasDetailDTO> getSimTypeSelection(String locale, String appDate, String basketId, String orderId, String channelCd, String mipSimType, String mipBrand, String offerNature);
	
	public String getDefaultSimItemId(String basketId, String appDate);
	public List<String> getItemSelectionGrpList(String grpId);
	
	boolean isSecretarialItem(String itemId);
	public String getOneCardTwoSimInd(String[] selectedItemList) ;
	public List<VasDetailDTO> getUserSelectedBasketItemList(String locale, String appDate, String basketId,String selectedItemList[], MRTUI mrtUI, PaymentMonthyUI paymentMonthyUI);
	boolean isItemsInGroup(String basketId, String[] vasList, Date appDate, String groupId);
	public Double getVasHSAmt(String selectedItemList[], String appDate);
	public Double getVasAmt(String selectedItemList[], String appDate);
	Map<SbItemPreDTO, List<SbItemPreDTO>> checkSbItemPre(List<String> itemIds);
	List<String> getItemIdByPosItemCd(String posItemCd);
	List<String> getPosItemCdByItemId(String itemId);
	/*public boolean hasNFCSim(String orderId);*/
	/*public boolean isNFCSim(String itemCd);*/
	/*public boolean hasOctopusSim(String orderId);*/
	/*public boolean isOctopusSim(String itemCd);*/
	public boolean hasProductionInfo(String[] vasList);
	public String getHSWaiveType(String basketId,String appDate);
	public String getWaiveVasItemCd(String basketId,String appDate, String channelId);
	public String getSimType(String itemCd);
	public String getOneTimeChargeAmount(String basketId, String appDate, String channelId);
	public boolean isOctopusWaived(String basketId, String appDate);
	public String getSelectedSimExTraFunction(String appDate, String basketId, String itemId) ;
	public Double getOneTimeChargeAmount (String orderId);
	public Double getMupAdminChargeAmount (String orderId);
	public List<String> getOverMaxCountItemList(String[] selectedList, String basketId);
	public String getSimOnlyBasketSimType(String basketId, String appDate);
	public BasketParmDTO getBasketParmByType(String basketId, String paramType);

	public List<RptVasDetailDTO> getReportUseMultiSimRPHSRPList(String locale, String basketId, String orderId,String memberNum);//MultiSim Athena 20140128
	public List<RptVasDetailDTO> getReportUseMultiSimVasDetailtList(String locale, String basketId, String orderId,String memberNum);//MultiSim Athena 20140128
	public List<RptVasDetailDTO> getReportUseMultiSimVasOptionalDetailtList(String locale, String basketId, String orderId,String memberNum);//MultiSim Athena 20140128
	public boolean hasSimInBasket(String basketId,String appDate);
	public List<String[]> getOctopusMspItemlist(String basketId, String appDate, String channelId);
	public boolean isExtraFunctionSim(String itemCd);
	public boolean isExtraFunctionSimByNfcInd(String nfcInd); 
	public boolean isAioSim(String selectedSimItemId);
	public boolean isExistAioMspItem(String[] vasItem);
	public List<String> getUnmatchDocAssignedVas(String[] selectedList, String docType, Date appDate);
	public List<VasDetailDTO> getSystemAssignVas(String vasCodeType, String appDate, String locale, String mipBrand, String mipSimType);
	public String checkBasketVas(String basketId, String appDate, String[] vasItemId);
	public List<String> getHsItemIdByBasket(String basketId, Date appDate);
	public List<String> getDummyWaiveRpPrepayItemExist(String basketId, String appDate);
	public boolean isYahooCoupon(String orderId);
	public boolean isExistTNGCardItem(String[] vasItem);
	public String getMipSimType(String itemId);
	public String getBasketHardBundleGrpId(String basketId, String mipBrand, String appDate);
	public BasketMinVasLkupDTO getBasketMinVasLkup(String basketId, String appDate);
	public ResultDTO basketValidate(String basketId , Date appDate);
	public boolean isUppReport(Date appDate);
	public boolean enableUADPlan(Date appDate);
	public boolean validJOC(String basketId, List<String> vasList, Date appDate);
	public String getHandsetDescriptionByItemCd(String itemCode);
	public List<VasDetailDTO> getOnlineReportUseRPHSRPList (String orderId);
	public boolean existInSelectionGrpList(String grpId, List<String> selectedVasList); 
	public boolean existInSelectionGrpList(String grpId, String[] selectedVasList); 
	public boolean existInSelectionGrpList(String grpId, String itemId);  
	public String getItemIdExistInSelectionGrpList(String grpId, List<String> selectedVasList); 
	public String getItemIdExistInSelectionGrpList(String grpId, String[] selectedVasList); 
}
