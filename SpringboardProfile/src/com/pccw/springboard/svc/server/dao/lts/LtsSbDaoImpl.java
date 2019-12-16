package com.pccw.springboard.svc.server.dao.lts;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import com.bomwebportal.lts.dto.ImsPendingOrderDTO;
import com.bomwebportal.lts.dao.order.SbOrderInfoLtsDAO;
import com.bomwebportal.lts.dto.ItemDetailDTO;
import com.bomwebportal.lts.dto.profile.ItemDetailProfileLtsDTO;
import com.bomwebportal.lts.dto.profile.OfferDetailProfileLtsDTO;
import com.bomwebportal.lts.service.OfferProfileService;
import com.bomwebportal.lts.service.bom.OfferProfileLtsService;
import com.bomwebportal.lts.service.order.ItemOfferMappingService;
import com.bomwebportal.lts.service.order.SbOrderInfoLtsService;
import com.bomwebportal.lts.util.LtsProfileConstant;
import com.pccw.springboard.svc.server.dao.DaoException;
import com.pccw.springboard.svc.server.dto.ContractDescDTO;
import com.pccw.springboard.svc.server.dto.OrderHistoryDTO;
import com.pccw.util.spring.SpringApplicationContext;
import com.pccw.wq.service.WorkQueueService;

public class LtsSbDaoImpl implements LtsSbDao {
	
	private OfferProfileLtsService offerProfileLtsService = null;
	private ItemOfferMappingService itemOfferMappingService = null;
	private OfferProfileService offerProfileService = null;
	private SbOrderInfoLtsService sbOrderInfoLtsService = null;
	
	@Override
	public long getLtsAlertCnt(String pLoginUserId) throws DaoException {
		try {
			return (long) SpringApplicationContext.getBean(WorkQueueService.class).searchSbIdsWithOutstandingWq("TEL", pLoginUserId).getTotalCount();
		} catch (Exception e) {
			throw new DaoException(e);
		}
	}
	
	@Override
	public String checkSbPendingOrder(String pSrvNum, String pSrvType) throws DaoException {
		return this.sbOrderInfoLtsService.getSbLtsLatestPendingOrderBySrvNum(pSrvNum, pSrvType);
	}
	
	public List<ImsPendingOrderDTO> checkSbImsPendingOrder(String pSrvNum) throws DaoException {
		return this.sbOrderInfoLtsService.getSbImsLatestPendingOrderBySrvNum(pSrvNum);
	}

	@Override
	public List<OrderHistoryDTO> getSbOrderHistory(String pIdDocType, String pIdDocNum, String pServiceType, String pServiceNumber) throws DaoException {
		return SpringApplicationContext.getBean(SbOrderInfoLtsDAO.class).getSbOrderHistory(pIdDocType, pIdDocNum, pServiceType, pServiceNumber);
	}
	
	@Override
	public ContractDescDTO getContractDesc(String pServiceId, String pLocale) throws DaoException {
		
		OfferDetailProfileLtsDTO[] offers = this.offerProfileLtsService.getLtsOfferProfile(pServiceId, null);
		
		if (ArrayUtils.isEmpty(offers)) {
			return null;
		}
		List<OfferDetailProfileLtsDTO> offerList = new ArrayList<OfferDetailProfileLtsDTO>(Arrays.asList(offers));
		Collection<ItemDetailProfileLtsDTO> items = this.itemOfferMappingService.getServiceItems(offerList);
		Iterator<ItemDetailProfileLtsDTO> it = items.iterator();
		ItemDetailProfileLtsDTO item = null;
		
		while (it.hasNext()) {
			item = it.next();
			
			if (StringUtils.equals(LtsProfileConstant.ITEM_TYPE_SERVICE, item.getItemType())) {
				ContractDescDTO contract = new ContractDescDTO();
				contract.setContactEndate(item.getConditionEffEndDate());
				contract.setContactStartDate(item.getConditionEffStartDate());
				ItemDetailDTO itemDesc = this.offerProfileService.getTpVasItemDetail(item.getItemId(), pLocale);
				contract.setDescription(itemDesc.getItemDisplayHtml());
				return contract;
			}
		}
		return null;
	}

	public OfferProfileLtsService getOfferProfileLtsService() {
		return offerProfileLtsService;
	}

	public void setOfferProfileLtsService(
			OfferProfileLtsService offerProfileLtsService) {
		this.offerProfileLtsService = offerProfileLtsService;
	}

	public ItemOfferMappingService getItemOfferMappingService() {
		return itemOfferMappingService;
	}

	public void setItemOfferMappingService(
			ItemOfferMappingService itemOfferMappingService) {
		this.itemOfferMappingService = itemOfferMappingService;
	}

	public OfferProfileService getOfferProfileService() {
		return offerProfileService;
	}

	public void setOfferProfileService(OfferProfileService offerProfileService) {
		this.offerProfileService = offerProfileService;
	}

	public SbOrderInfoLtsService getSbOrderInfoLtsService() {
		return sbOrderInfoLtsService;
	}

	public void setSbOrderInfoLtsService(SbOrderInfoLtsService sbOrderInfoLtsService) {
		this.sbOrderInfoLtsService = sbOrderInfoLtsService;
	}
}