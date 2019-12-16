package com.bomwebportal.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bomwebportal.dao.DepositDAO;
import com.bomwebportal.dao.ItemOfferProductDAO;
import com.bomwebportal.dto.DepositDTO;
import com.bomwebportal.dto.DepositLkupDTO;
import com.bomwebportal.dto.ItemOfferProductDTO;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.util.Utility;

@Transactional(readOnly = true)
public class DepositServiceImpl implements DepositService {

	protected final Log logger = LogFactory.getLog(getClass());

	private DepositDAO depositDAO;
	private ItemOfferProductDAO itemOfferProductDAO;

	public DepositDAO getDepositDAO() {
		return depositDAO;
	}

	public void setDepositDAO(DepositDAO depositDAO) {
		this.depositDAO = depositDAO;
	}	

	public ItemOfferProductDAO getItemOfferProductDAO() {
		return itemOfferProductDAO;
	}

	public void setItemOfferProductDAO(ItemOfferProductDAO itemOfferProductDAO) {
		this.itemOfferProductDAO = itemOfferProductDAO;
	}

	public DepositDTO getDepositDTO(String orderId, String depositId) {
		try {
			logger.debug("getDepositDTO() is called in DepositServiceImpl");
			return depositDAO.getDepositDTO(orderId, depositId);
		} catch (DAOException de) {
			logger.error("Exception caught in getDepositDTO()", de);
			throw new AppRuntimeException(de);
		}
	}

	public List<DepositDTO> findDepositDTOByOrderId(String orderId) {
		return findDepositDTOByOrderId(orderId, "en");
	}

	public List<DepositDTO> findDepositDTOByOrderId(String orderId, String lang) {
		try {
			logger.debug("findDepositDTOByOrderId() is called in DepositServiceImpl");
			if (! "zh_HK".equals(lang)) {
				lang = "en";
			}
			return depositDAO.findDepositDTOByOrderId(orderId, lang);
		} catch (DAOException de) {
			logger.error("Exception caught in findDepositDTOByOrderId()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public int insertBomwebDeposit(DepositDTO dto) {
		try {
			logger.debug("insertBomwebDeposit() is called in DepositServiceImpl");
			return depositDAO.insertBomwebDeposit(dto);
		} catch (DAOException de) {
			logger.error("Exception caught in insertBomwebDeposit()", de);
			throw new AppRuntimeException(de);
		}
	}

	public DepositLkupDTO getDepositLkup(String depositId) {
		try {
			logger.debug("getDepositLkup() is called in DepositServiceImpl");
			return depositDAO.getDepositLkup(depositId);
		} catch (DAOException de) {
			logger.error("Exception caught in getDepositLkup()", de);
			throw new AppRuntimeException(de);
		}
	}

	public List<DepositLkupDTO> findDepositLkupByDepositCd(String depositCd,
			Date appDate) {
		try {
			logger.debug("findDepositLkupByDepositCd() is called in DepositServiceImpl");
			return depositDAO.findDepositLkupByDepositCd(depositCd, appDate);
		} catch (DAOException de) {
			logger.error("Exception caught in findDepositLkupByDepositCd()", de);
			throw new AppRuntimeException(de);
		}
	}

	public List<DepositLkupDTO> findDepositLkupByProductId(String productIds[],
			Date appDate) {
		try {
			logger.debug("findDepositLkupByProductId() is called in DepositServiceImpl");
			return depositDAO.findDepositLkupByProductId(productIds, appDate);
		} catch (DAOException de) {
			logger.error("Exception caught in findDepositLkupByProductId()", de);
			throw new AppRuntimeException(de);
		}
	}

	public List<DepositLkupDTO> findAllDepositLkup() {
		return findAllDepositLkup(new Date());
	}
	
	public List<DepositLkupDTO> findAllDepositLkup(Date appDate) {
		try {
			logger.debug("findAllDepositLkup() is called in DepositServiceImpl");
			
			return depositDAO.findAllDepositLkup(appDate);
		} catch (DAOException de) {
			logger.error("Exception caught in findAllDepositLkup()", de);
			throw new AppRuntimeException(de);
		}
	}

	public List<DepositLkupDTO> findDepositLkupByItemIds(String basketId,
			Date appInDate, String[] vasItemIds, String mipBrand, String mipSimType) {
		try {
			logger.debug("findAllDepositLkup() is called in DepositServiceImpl");
			List<ItemOfferProductDTO> itemOfferProductDTOs = itemOfferProductDAO.getItemOfferProductDTOs(basketId, appInDate, vasItemIds,  mipBrand,  mipSimType);
			if (CollectionUtils.isNotEmpty(itemOfferProductDTOs)) {
				String productIds[] = new String[itemOfferProductDTOs.size()];
				for (int i=0; i < itemOfferProductDTOs.size(); i++) {
					productIds[i] = itemOfferProductDTOs.get(i).getProductId();
				}
				return findDepositLkupByProductId(productIds, appInDate);
			}
		} catch (DAOException de) {
			logger.error("Exception caught in findAllDepositLkup()", de);
			throw new AppRuntimeException(de);
		}
		return null;
	}

	public BigDecimal getDepositAmountForOrder(String orderId) {
		try {
			logger.debug("getDepositAmountForOrder() is called in DepositServiceImpl");
			return depositDAO.getDepositAmountForOrder(orderId);
		} catch (DAOException de) {
			logger.error("Exception caught in getDepositAmountForOrder()", de);
			throw new AppRuntimeException(de);
		}
	}

}
