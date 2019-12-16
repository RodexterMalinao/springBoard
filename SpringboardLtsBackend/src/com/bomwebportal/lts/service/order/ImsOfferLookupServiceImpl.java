package com.bomwebportal.lts.service.order;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.lts.dao.bom.OfferProductLkupDAO;
import com.bomwebportal.lts.dao.order.ImsOfferLookupDAO;
import com.bomwebportal.lts.dto.order.ImsOfferDetailDTO;
import com.bomwebportal.lts.dto.order.ServiceActionTypeDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailOtherLtsDTO;

public class ImsOfferLookupServiceImpl implements ImsOfferLookupService {

	private final Log logger = LogFactory.getLog(getClass());
	private ImsOfferLookupDAO imsOfferLookupDao;
	private OfferProductLkupDAO offerProductLkupDao;
	
	public List<String> getOfferIdListByProductParam(String pParameter, String pValue) {
		try {
			return this.offerProductLkupDao.getOfferIdListByProductParam(pParameter, pValue);
		} catch (DAOException ex) {
			logger.error("Error in getImsOfferByGroupId()");
			throw new AppRuntimeException(ex);
		}
	}
	
	public ImsOfferDetailDTO[] getImsOffersByService(ServiceDetailOtherLtsDTO pSrvDtl, String pDeviceType, String pWgOfferInd) {
		
		ServiceActionTypeDTO[] srvActions = pSrvDtl.getSrvActions();
		List<ImsOfferDetailDTO> offerList = new ArrayList<ImsOfferDetailDTO>();
		
		for (int i=0; srvActions!=null && i<srvActions.length; ++i) {
			if (srvActions[i]!= null && StringUtils.isNotEmpty(srvActions[i].getOfferGrpId())) {
				offerList.addAll(Arrays.asList(this.getImsOfferByGroupId(srvActions[i].getOfferGrpId(), pDeviceType, pWgOfferInd, StringUtils.defaultIfEmpty(pSrvDtl.getShareRentalRouter(), "N"))));
				offerList.addAll(Arrays.asList(this.getImsNowtvOffer(srvActions[i].getOfferGrpId(), pSrvDtl)));
			}
		}
		offerList.addAll(Arrays.asList(this.getImsTechnologyOffer(pSrvDtl.getExistTechnology(), pSrvDtl.getAssgnTechnology())));
		return offerList.toArray(new ImsOfferDetailDTO[offerList.size()]);
	}
	
	private ImsOfferDetailDTO[] getImsOfferByGroupId(String pGrpId, String pDeviceType, String pWgOfferInd, String pRentalRouterInd) {
		
		try {
			return this.imsOfferLookupDao.getImsOfferByGroupId(pGrpId, pDeviceType, pWgOfferInd, pRentalRouterInd);
		} catch (DAOException ex) {
			logger.error("Error in getImsOfferByGroupId()");
			throw new AppRuntimeException(ex);
		}
	}
	
	private ImsOfferDetailDTO[] getImsNowtvOffer(String pGrpId, ServiceDetailOtherLtsDTO pSrvDtl) {
		
		try {
			return this.imsOfferLookupDao.getImsNowtvOffer(pGrpId, pSrvDtl);
		} catch (DAOException ex) {
			logger.error("Error in getImsNowtvOffer()");
			throw new AppRuntimeException(ex);
		}
	}
	
	private ImsOfferDetailDTO[] getImsTechnologyOffer(String pExistTech, String pNewTech) {

		try {
			return this.imsOfferLookupDao.getImsTechnologyOffer(pExistTech, pNewTech);
		} catch (DAOException ex) {
			logger.error("Error in getImsTechnologyOffer()");
			throw new AppRuntimeException(ex);
		}
	}

	public ImsOfferLookupDAO getImsOfferLookupDao() {
		return imsOfferLookupDao;
	}

	public void setImsOfferLookupDao(ImsOfferLookupDAO imsOfferLookupDao) {
		this.imsOfferLookupDao = imsOfferLookupDao;
	}

	public OfferProductLkupDAO getOfferProductLkupDao() {
		return offerProductLkupDao;
	}

	public void setOfferProductLkupDao(OfferProductLkupDAO offerProductLkupDao) {
		this.offerProductLkupDao = offerProductLkupDao;
	}
	
	
}
