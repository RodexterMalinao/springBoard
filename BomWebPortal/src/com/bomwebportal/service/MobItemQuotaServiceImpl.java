package com.bomwebportal.service;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bomwebportal.bean.LookupTableBean;
import com.bomwebportal.dao.MobItemQuotaDAO;
import com.bomwebportal.dto.MobItemQuotaDTO;
import com.bomwebportal.dto.VasDetailDTO;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.mobquota.dto.QuotaConsumeRequest;
import com.bomwebportal.mobquota.dto.QuotaItem;
import com.bomwebportal.util.Utility;

public class MobItemQuotaServiceImpl implements MobItemQuotaService {
 	protected final Log logger = LogFactory.getLog(getClass()); 

 	private MobItemQuotaDAO mobItemQuotaDAO;

 	public MobItemQuotaDAO getMobItemQuotaDAO() {
		return mobItemQuotaDAO;
	}

	public void setMobItemQuotaDAO(MobItemQuotaDAO mobItemQuotaDAO) {
		this.mobItemQuotaDAO = mobItemQuotaDAO;
	}

	public List<MobItemQuotaDTO> getMobItemQuotaDTOALL()  { 
 	
 		try { 
 			logger.info("getMobQuotaAuthLogALL() is called in MobQuotaAuthLogServiceService"); 
 			return  mobItemQuotaDAO.getMobItemQuotaDTOALL();
 			
 		} catch (DAOException de) { 
 			logger.error("Exception caught in getMobQuotaAuthLogALL()", de); 
 			throw new AppRuntimeException(de); 
 		} 
 	} 
 	
	public Map<String,MobItemQuotaDTO> getMobItemQuotaAsMap() {
		logger.debug("getMobItemQuotaAsMap called");
		HashMap<String,MobItemQuotaDTO> codeMap = new HashMap<String,MobItemQuotaDTO>();
		List<MobItemQuotaDTO> codeList = getMobItemQuotaDTOALL();
		if (CollectionUtils.isNotEmpty(codeList)) {
			for (MobItemQuotaDTO cl : codeList) {
				try {
					codeMap.put(cl.getItemId(), cl);
				} catch (Exception e) {
					logger.error("Quota exception");
				}
			}
		}
		return codeMap;
	}
 	
 	public List<MobItemQuotaDTO> getBasketMobItemQuotaDTOALL(String basketId, String appDate)  { 
 	 	
 		List<MobItemQuotaDTO> resultList = new ArrayList<MobItemQuotaDTO>();
 		try { 
 			logger.info("getBasketMobItemQuotaDTOALL() is called in MobQuotaAuthLogServiceService"); 
 			List<VasDetailDTO> basketItems = mobItemQuotaDAO.getEffectiveBasketItems(basketId, appDate);
 			
 			if (CollectionUtils.isNotEmpty(basketItems )){
 				
				HashMap<String,MobItemQuotaDTO> quotaMap=  (HashMap<String, MobItemQuotaDTO>) LookupTableBean.getInstance().getMobItemQuotaMap();
				for (VasDetailDTO dto: basketItems){
					
					MobItemQuotaDTO temp =quotaMap.get(dto.getItemId());
					//TODOMIP: check quota eff start end date
					if (temp!= null 
							&& Utility.dateWithin(Utility.string2Date(appDate) , temp.getEffStartDate(), temp.getEffEndDate())
							&& Utility.dateWithin(Utility.string2Date(appDate) , temp.getQuotaAssignEffStartDate(), temp.getQuotaAssignEffEndDate())
							){
						resultList.add(temp);
					}
				}
			}
 			return  resultList;
 			
 		} catch (DAOException de) { 
 			logger.error("Exception caught in getBasketMobItemQuotaDTOALL()", de); 
 			throw new AppRuntimeException(de); 
 		} 
 	} 
 	
 	public List<MobItemQuotaDTO> getMobQuotaByItemList(List<String> items)  { 
 	 	
 		
 		List<MobItemQuotaDTO> resultList = new ArrayList<MobItemQuotaDTO>();
 		
 			logger.info("getBasketMobItemQuotaDTOALL() is called in MobQuotaAuthLogServiceService"); 
 		//	List<VasDetailDTO> basketItems = mobItemQuotaDAO.getEffevtiveBasketItems(basketId, appDate);
 			
 			if (CollectionUtils.isNotEmpty(items )){
 				
 				System.out.println("items.size()"+ items.size());
				HashMap<String,MobItemQuotaDTO> quotaMap=  (HashMap<String, MobItemQuotaDTO>) LookupTableBean.getInstance().getMobItemQuotaMap();
				for (String item: items){
					System.out.println("item >>>" +item);
					MobItemQuotaDTO temp =quotaMap.get(item);
					//TODOMIP: check quota eff start end date
					if (temp!= null){
						resultList.add(temp);
					}
				}
			}
 			return  resultList;
 			
 		
 	} 
 	
 	
	public List<MobItemQuotaDTO> getFinalQuota(String basketId, String appDate, List<String> selectedVasList) {

		List<MobItemQuotaDTO> resultList = new ArrayList<MobItemQuotaDTO>();
		List<VasDetailDTO> finalBasketItems =  new ArrayList<VasDetailDTO>();

		logger.info("getBasketMobItemQuotaDTOALL() is called in MobQuotaAuthLogServiceService");
		try {
			List<VasDetailDTO> basketItems = mobItemQuotaDAO.getEffectiveBasketItems(basketId, appDate);
			finalBasketItems.addAll(basketItems);

		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// TODOMIP: VAS overwrite BVAS
		//finalBasketItems >>> copy ex
		
		if (CollectionUtils.isNotEmpty(finalBasketItems)) {
			HashMap<String, MobItemQuotaDTO> quotaMap = (HashMap<String, MobItemQuotaDTO>) LookupTableBean.getInstance().getMobItemQuotaMap();
			for (VasDetailDTO dto : finalBasketItems) {
				MobItemQuotaDTO temp = quotaMap.get(dto.getItemId());
				// TODOMIP: check quota eff start end date
				if (temp!= null 
						&& Utility.dateWithin(Utility.string2Date(appDate) , temp.getEffStartDate(), temp.getEffEndDate())
						&& Utility.dateWithin(Utility.string2Date(appDate) , temp.getQuotaAssignEffStartDate(), temp.getQuotaAssignEffEndDate())
						) {
					resultList.add(temp);
				}
			}
		}

		// VAS items Quota
		if (CollectionUtils.isNotEmpty(selectedVasList)) {

			System.out.println("selectedVasList.size()"
					+ selectedVasList.size());
			HashMap<String, MobItemQuotaDTO> quotaMap = (HashMap<String, MobItemQuotaDTO>) LookupTableBean.getInstance().getMobItemQuotaMap();
			for (String item : selectedVasList) {
				System.out.println("item >>>" + item);
				MobItemQuotaDTO temp = quotaMap.get(item);
				// TODOMIP: check quota eff start end date
				if (temp != null && Utility.dateWithin(Utility.string2Date(appDate) , temp.getEffStartDate(), temp.getEffEndDate())) {
					resultList.add(temp);
				}
			}
		}
		return resultList;

	}

	
	public QuotaConsumeRequest createMobQuotaConsumeRequest(String idDocType, String idDocNum, String orderId, String staffId, VasDetailDTO vasDetailDTO, String authBy, java.util.Date applnDate) {
		List<MobItemQuotaDTO> quotaDTOs = vasDetailDTO.getFinalOuotaList();
		QuotaConsumeRequest qcr = new QuotaConsumeRequest();
		qcr.setApplnDate(Utility.date2String(applnDate, "yyyy-MM-dd"));
		qcr.setDocType(idDocType);
		qcr.setDocNum(idDocNum);
		qcr.setOrderId(orderId);
		qcr.setOrderType("S");
		qcr.setUpdatedBy(staffId);
		qcr.setAuthBy(authBy);
		ArrayList<QuotaItem> qis = new ArrayList<QuotaItem>();
		if (CollectionUtils.isNotEmpty(quotaDTOs)) {
			for (MobItemQuotaDTO miq : quotaDTOs) {
				QuotaItem qi = new QuotaItem();
				qi.setQuotaId(miq.getQuotaId());
				qi.setQuantity(1);
				qi.setAuthBy(authBy);
				qis.add(qi);
			}
		}

		qcr.setItems(qis);
		
		return qcr;
	}
	
 } 
  
