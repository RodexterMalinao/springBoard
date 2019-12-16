package com.bomwebportal.lts.service.workQueue;

import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Transactional;

import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.lts.dao.BomwebWqTransDAO;
import com.bomwebportal.lts.dao.workQueue.SubcItemWqDAO;
import com.bomwebportal.lts.dto.BasketDetailDTO;
import com.bomwebportal.lts.dto.order.ItemDetailLtsDTO;
import com.bomwebportal.lts.dto.order.SbOrderDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailLtsDTO;
import com.bomwebportal.lts.dto.workQueue.SubcItemWqDTO;
import com.bomwebportal.lts.service.LtsBasketService;
import com.bomwebportal.lts.util.LtsBackendConstant;
import com.bomwebportal.lts.util.LtsConstant;
import com.bomwebportal.lts.util.LtsSbOrderHelper;
import com.pccw.util.spring.SpringApplicationContext;

public class WqTransSubmitServiceImpl implements WqTransSubmitService {
	
	protected final Log logger = LogFactory.getLog(getClass());
	protected final String SIGNOFF_USER = "sbSignoff";
	
	protected LtsBasketService ltsBasketService;
	protected SubcItemWqDAO subcItemWqDAO;
	
	public LtsBasketService getLtsBasketService() {
		return ltsBasketService;
	}

	public void setLtsBasketService(LtsBasketService ltsBasketService) {
		this.ltsBasketService = ltsBasketService;
	}

	public SubcItemWqDAO getSubcItemWqDAO() {
		return subcItemWqDAO;
	}

	public void setSubcItemWqDAO(SubcItemWqDAO subcItemWqDAO) {
		this.subcItemWqDAO = subcItemWqDAO;
	}

	public void submitPendingWqTrans(SbOrderDTO sbOrder, String userId, String shopCd) {
		if (!isExceptionalCase(sbOrder)) {
			submitSubcItemWqTrans(sbOrder, userId, shopCd);	
		}
	}
	
	@Transactional
	private void submitSubcItemWqTrans(SbOrderDTO sbOrder, String userId, String shopCd) {
		
		ServiceDetailDTO[] serviceDetails = sbOrder.getSrvDtls();
		
		if (ArrayUtils.isEmpty(serviceDetails)) {
			return;
		}
		List<SubcItemWqDTO> subcItemWqList = null;
		
		try {
			subcItemWqList = subcItemWqDAO.getSubcItemWorkQueue(); 
		}
		catch (Exception e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
		
		
		if (subcItemWqList == null || subcItemWqList.isEmpty()) {
			return;
		}
		
		for (ServiceDetailDTO serviceDetail : serviceDetails) {
			if (!(serviceDetail instanceof ServiceDetailLtsDTO)) {
				continue;
			}
			
			ItemDetailLtsDTO[] itemDetails = ((ServiceDetailLtsDTO)serviceDetail).getItemDtls();
			
			if (ArrayUtils.isEmpty(itemDetails)) {
				continue;
			}
			
			for (ItemDetailLtsDTO itemDetail : itemDetails) {
				for (SubcItemWqDTO subcItemWq : subcItemWqList) {
					if (StringUtils.equals(subcItemWq.getOrderType(), sbOrder.getOrderType())
							&& StringUtils.equals(subcItemWq.getItemId(), itemDetail.getItemId())
							&& StringUtils.equalsIgnoreCase(subcItemWq.getIoInd(), itemDetail.getIoInd())) {
						try {
							BomwebWqTransDAO  bomwebWqTrans = SpringApplicationContext.getBean("com.bomwebportal.lts.dao.BomwebWqTransDAO");
							bomwebWqTrans.setOrderId(sbOrder.getOrderId());
							bomwebWqTrans.setDtlId(serviceDetail.getDtlId());
							bomwebWqTrans.setStatus(LtsConstant.WQ_TRANS_STATUS_PENDING);
							bomwebWqTrans.setAction(subcItemWq.getActionType());
							bomwebWqTrans.setWqRemarks(subcItemWq.getWqRemarks());
							bomwebWqTrans.setStandardRemarks("Y");
//							bomwebWqTrans.setWqNatureId(subcItemWq.getWqNatureId());
//							bomwebWqTrans.setWqSubtype(subcItemWq.getWqSubType());
//							bomwebWqTrans.setWqType(subcItemWq.getWqType());
							bomwebWqTrans.setLkupKey("?");
							bomwebWqTrans.setLkupCache("?");
							bomwebWqTrans.setLastUpdBy(userId);
							bomwebWqTrans.setCreateBy(userId);
							bomwebWqTrans.setShopCd(shopCd);
							bomwebWqTrans.setUserId(userId);
							bomwebWqTrans.setHostIp("UNKNOW");
							bomwebWqTrans.doInsert();	
						}
						catch (Exception e) {
							throw new AppRuntimeException(e);
						}
					}	
				}
				
			}
		}
	}

	private boolean isExceptionalCase(SbOrderDTO sbOrder) {
		return isRenewBundleTvOrder(sbOrder);
	}
	
	private boolean isRenewBundleTvOrder(SbOrderDTO sbOrder) {
		if(!LtsConstant.ORDER_TYPE_SB_RETENTION.equals(sbOrder.getOrderType())) {
			return false;
		}
		ServiceDetailLtsDTO ltsService = LtsSbOrderHelper.getLtsService(sbOrder);
		ItemDetailLtsDTO[] subcItems = ltsService.getItemDtls();
		String subcBasketId = null;
		if (ArrayUtils.isNotEmpty(subcItems)) {
			for (ItemDetailLtsDTO itemDetail : subcItems) {
				subcBasketId = itemDetail.getBasketId();
				if (LtsConstant.IO_IND_INSTALL.equals(itemDetail.getIoInd())
						&& (LtsBackendConstant.ITEM_TYPE_NOWTV_PAY.equals(itemDetail.getItemType())
								|| LtsBackendConstant.ITEM_TYPE_NOWTV_SPEC.equals(itemDetail.getItemType()))) {
					return true;
				}
			}
		}
		if (StringUtils.isNotEmpty(subcBasketId)) {
			BasketDetailDTO subcBasket = ltsBasketService.getBasket(subcBasketId, LtsConstant.LOCALE_ENG, LtsConstant.DISPLAY_TYPE_RP_PROMOT);
			if (subcBasket != null && StringUtils.isNotBlank(subcBasket.getBundleTvCredit())) {
				return true;
			}
		}
		
		return false;
	}
}
