package com.bomwebportal.service;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bomwebportal.dao.BSbMobApiPkgDAO;
import com.bomwebportal.dao.ItemOfferProductDAO;
import com.bomwebportal.dto.ItemOfferProductDTO;
import com.bomwebportal.dto.PcRelationshipDTO;
import com.bomwebportal.dto.PcRelationshipDTO.RelType;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;

public class RelationshipCheckServiceImpl implements RelationshipCheckService {
	protected final Log logger = LogFactory.getLog(getClass());
	
	private ItemOfferProductDAO itemOfferProductDAO;
	private BSbMobApiPkgDAO bSbMobApiPkgDAO;
	
	public ItemOfferProductDAO getItemOfferProductDAO() {
		return itemOfferProductDAO;
	}

	public void setItemOfferProductDAO(ItemOfferProductDAO itemOfferProductDAO) {
		this.itemOfferProductDAO = itemOfferProductDAO;
	}

	public BSbMobApiPkgDAO getbSbMobApiPkgDAO() {
		return bSbMobApiPkgDAO;
	}

	public void setbSbMobApiPkgDAO(BSbMobApiPkgDAO bSbMobApiPkgDAO) {
		this.bSbMobApiPkgDAO = bSbMobApiPkgDAO;
	}

	/**
	More information for your coding:
	<br>v_sb_item_id_varray = "offer id" or "offer id, product id"
	<br>v_sb_item_type_varray = "O" or "P"
	<br>
	<br>(above 2 arrays offer/product list should be extracted 
	<br>from joining <bomweb_subscribed_item>, <w_item_offer_assgn> 
	<br>& <w_item_offer_product_assgn> and in sequence
	<br>
	<br>e.g.
	<br>{"oid1", "oid2", "oid1,pid11", "oid1,pid12", "oid2,pid21"}
	<br>{"O","O","P","P","P"}
	<br>)
	<br>
	<br>v_rel_type_varray = 'CO', 'EX', 'PR', 'PO', 'CP'
	<br>CO = Co-Exists
	<br>EX = Exclusive
	<br>PR = Prerequisite
	<br>PO = Prerequisite Or
	<br>CP = Compatible
	<br>
	<br>v_applicationdate = order application date (yyyymmdd)
	 **/
	public List<PcRelationshipDTO> checkRelationshipSb(String basketId, Date appInDate, String[] vasItemIds, String mipBrand, String mipSimType) {
		try {
			if (logger.isDebugEnabled()) {
				logger.debug("basketId: " + basketId + ", appInDate: " + appInDate + ", vasItemIds: " + (vasItemIds == null ? "(null)" : StringUtils.join(vasItemIds, ',')));
			}
			List<ItemOfferProductDTO> itemOfferProductDTOs = itemOfferProductDAO.getItemOfferProductDTOs(basketId, appInDate, vasItemIds,   mipBrand,  mipSimType);
			if (this.isEmpty(itemOfferProductDTOs)) {
				if (logger.isDebugEnabled()) {
					logger.debug("empty itemOfferProductDTOs");
				}
				return Collections.emptyList();
			}
			if (logger.isDebugEnabled()) {
				for (ItemOfferProductDTO dto : itemOfferProductDTOs) {
					logger.debug("itemId: " + dto.getItemId() +
							", offerId: " + dto.getOfferId() +
							", offerCd: " + dto.getOfferCd() +
							", productId: " + dto.getProductId() +
							", productCd: " + dto.getProductCd());
				}
			}
			return this.bSbMobApiPkgDAO.checkRelationshipSb(itemOfferProductDTOs, Arrays.asList(RelType.values()), appInDate);
		} catch (DAOException de) {
			if (logger.isWarnEnabled()) {
				logger.warn("DAOException found in checkRelationshipSb", de);
			}
			throw new AppRuntimeException(de);
		}
	}
	
	private boolean isEmpty(List<?> list) {
		return list == null || list.isEmpty();
	}

}
