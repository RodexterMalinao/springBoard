package com.bomwebportal.ims.service;

import com.bomwebportal.exception.DAOException;
import com.bomwebportal.exception.ImsAlreadySignOffException;
import com.bomwebportal.ims.dto.ImsSupportDocDTO;
import com.bomwebportal.ims.dto.NtvReplaceSelfPickHddDTO;
import com.bomwebportal.ims.dto.OrderImsDTO;
import com.bomwebportal.ims.dto.OrderImsSystemFindingDTO;
import com.bomwebportal.ims.dto.ui.CustomerUI;
import com.bomwebportal.ims.dto.ui.OrderImsUI;
import com.bomwebportal.ims.dto.ImsAllOrdDocWaiveDTO;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.openuri.www.CustomerBasicInfoDTO;

import com.bomwebportal.ims.dto.CustomerDTO;


public interface ImsOrderDetailService {
	public List<NtvReplaceSelfPickHddDTO> getReplaceSelfPickHddLkup();	
	public OrderImsUI signOffOrder(OrderImsUI order) throws ImsAlreadySignOffException;
	public OrderImsUI suspendOrder(OrderImsUI order, String reasonCd) throws ImsAlreadySignOffException;
	public OrderImsUI cancelOrder(OrderImsUI order, String reasonCd);
	public OrderImsUI getOrder(String orderid);		
	public String getNewOrderId(String shopCd,String staffId);
	public OrderImsUI getNewOrderTemplate(String shopCd);
	//add by steven
	public ImsSupportDocDTO get_PayMtd_ccNum_DisMode(String orderId);
	public List<ImsAllOrdDocWaiveDTO> getWaiveReasonList(String docType, String DisMode);
	public List<String> getImsDocTypeList();
	//add by steven for retention
	public List<CustomerBasicInfoDTO> getBomWebCustomer(String custId);//wrongly done, should use BOM but not SpringBoard, Steven 20140612
	public List<CustomerBasicInfoDTO> getBomWebCustomerBom(String custId);//wrongly done, should use BOM but not SpringBoard, Steven 20140612
	public String isImsShowChangeLang();
	public void updateOrderImgByLatestAmend(OrderImsUI order);
	
	//added for Direct Sales (Potential discount offer)
	public boolean checkPCDretentionPeriod(String service_num);
	public boolean checkLTSretentionPeriod(String service_num);
	public boolean checkPCDexistingServcie(String service_num);
	public List<OrderImsSystemFindingDTO> sysFchecking();
	public int updateDSSysFinding(OrderImsSystemFindingDTO dto) throws DAOException;
	public List<OrderImsSystemFindingDTO> sysFcheckingNowTV();
	public void addBackendOffers(String i_order_id) throws  SQLException;
	public void addBackendOffersDtl(OrderImsUI order) throws  SQLException;
	
	public void getSystemFinding(OrderImsUI order, String staffId, int channeId, String dob);
	public Map<String, String> getLookUpMapByLocale (Map<String, String> ilist , String Locale);
}
