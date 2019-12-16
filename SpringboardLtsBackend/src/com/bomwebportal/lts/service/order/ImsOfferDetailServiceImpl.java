package com.bomwebportal.lts.service.order;

import java.util.ArrayList;
import java.util.Arrays;

import org.apache.commons.lang.StringUtils;

import com.bomwebportal.dto.ObjectActionBaseDTO;
import com.bomwebportal.lts.dao.order.ImsOfferDetailDAO;
import com.bomwebportal.lts.dto.order.ImsOfferDetailDTO;
import com.bomwebportal.lts.util.LtsBackendConstant;
import com.bomwebportal.service.ServiceActionImplBase;
import com.pccw.util.db.DaoBase;

public class ImsOfferDetailServiceImpl extends ServiceActionImplBase {

	public ImsOfferDetailServiceImpl() {
		this.selectConditionFieldList = new ArrayList<String>(Arrays.asList("orderId"));
		this.updateDeleteConditionFieldList = new ArrayList<String>(Arrays.asList("orderId", "dtlId", "offerId"));
	}
	
	public ObjectActionBaseDTO convertToDto(DaoBase pDaoBase) {

		ImsOfferDetailDTO offer = new ImsOfferDetailDTO();
		ImsOfferDetailDAO offerDao = (ImsOfferDetailDAO)pDaoBase;
		offer.setObjectAction(ObjectActionBaseDTO.ACTION_NO_CHANGE);
		this.DAO2DTO(offerDao, offer);
		return offer;
	}
	
	protected void setDTO2DAODetails(ObjectActionBaseDTO pBaseDTO, Object... args) {

		ImsOfferDetailDTO offer = (ImsOfferDetailDTO)pBaseDTO;
		ImsOfferDetailDAO offerDao = (ImsOfferDetailDAO)this.dao;
		this.DTO2DAO(offer, offerDao);
		offerDao.setOrderId((String)args[0]);
		offerDao.setDtlId((String)args[1]);
		
		if (StringUtils.equals(LtsBackendConstant.PENALTY_ACTION_MANUAL_WAIVE, offer.getPenaltyHandling())
				|| StringUtils.equals(LtsBackendConstant.PENALTY_ACTION_AUTO_WAIVE, offer.getPenaltyHandling())) {
			offerDao.setPenaltyWaiveInd("Y");
		} else {
			offerDao.setPenaltyWaiveInd(null);
		}
	}

	protected void setDAO2DTODetails(Object... pArgs) {
		((ImsOfferDetailDAO)this.dao).setOrderId((String)pArgs[0]);
	}
}
