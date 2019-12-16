/*
 * Created on Oct 19, 2012
 *
 * @author Alfredo P. Ricafort
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bomwebportal.lts.wsServiceLts;

import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;

import com.bomwebportal.lts.dto.export.ItemDetailProfileExportLtsDTO;
import com.bomwebportal.lts.dto.profile.OfferDetailProfileLtsDTO;
import com.bomwebportal.lts.wsExportLts.ItemExportService;
import com.bomwebportal.lts.wsServiceLts.schemas.ItemDetailsProfileRequest;
import com.bomwebportal.lts.wsServiceLts.schemas.ItemDetailsProfileResponse;
import com.bomwebportal.lts.wsServiceLts.schemas.ObjectFactory;
import com.bomwebportal.lts.wsServiceLts.schemas.ItemDetailsProfileRequest.OfferDetailsProfile;
import com.bomwebportal.lts.wsServiceLts.schemas.ItemDetailsProfileResponse.OfferDetailsResult;



@Endpoint
public class ItemDetailsProfileEndPoint implements ItemDetailsProfileEndPointConstants {

	protected final Log logger = LogFactory.getLog(getClass());
    private final ObjectFactory JAXB_OBJECT_FACTORY = new ObjectFactory();	
	private ItemExportService itemExportService;
	
    @PayloadRoot(localPart = MAP_OFFERS_TO_ITEM_REQUEST, namespace = MESSAGES_NAMESPACE)	
	public JAXBElement<ItemDetailsProfileResponse> mapOffersToItem(ItemDetailsProfileRequest itemDetailsProfileRequest) {
    	
    	ItemDetailsProfileResponse itemDetailsProfileResponse = null;
    	OfferDetailsResult offerDetailsResult = null;
    	
    	try {
			OfferDetailProfileLtsDTO[] offerDetailProfileLtsDTO = null;
			if (itemDetailsProfileRequest != null
					&& itemDetailsProfileRequest.getOfferDetailsProfile() != null
					&& itemDetailsProfileRequest.getOfferDetailsProfile()
							.size() > 0) {
				OfferDetailsProfile[] offerDetailProfileRequest = itemDetailsProfileRequest
						.getOfferDetailsProfile()
						.toArray(
								new OfferDetailsProfile[itemDetailsProfileRequest
										.getOfferDetailsProfile().size()]);
				offerDetailProfileLtsDTO = new OfferDetailProfileLtsDTO[offerDetailProfileRequest.length];

				logger.debug(" ItemDetailsProfileEndPoint.mapOffersToItem <IN>OfferDetailsProfile: { ");
				for (int i = 0; i < offerDetailProfileRequest.length; i++) {
					offerDetailProfileLtsDTO[i] = new OfferDetailProfileLtsDTO();
					BeanUtils.copyProperties(offerDetailProfileRequest[i],
							offerDetailProfileLtsDTO[i]);
					logger.debug("    {");
					logger.debug("        OfferId: " + offerDetailProfileRequest[i].getOfferId());
					logger.debug("        ConditionEffEndDate: " + offerDetailProfileRequest[i].getConditionEffEndDate());
					logger.debug("        ConditionEffStartDate: " + offerDetailProfileRequest[i].getConditionEffStartDate());
					logger.debug("        ExpiredMonths: " + offerDetailProfileRequest[i].getExpiredMonths());
					logger.debug("        PromotionStartDate: " + offerDetailProfileRequest[i].getPromotionStartDate());
					logger.debug("        PromotionEndDate: " + offerDetailProfileRequest[i].getPromotionEndDate());
					logger.debug("        PromotionExpiredMonths: " + offerDetailProfileRequest[i].getPromotionExpiredMonths());
					logger.debug("    }");
				}
				
				logger.debug(" } ");
			}

			String[] iddCallPlanCds = itemDetailsProfileRequest
					.getIddCallPlanCds().toArray(
							new String[itemDetailsProfileRequest
									.getIddCallPlanCds().size()]);
			logger.debug(" ItemDetailsProfileEndPoint.mapOffersToItem <IN>IddCallPlanCds: {");
			logger.debug("     " + StringUtils.join(iddCallPlanCds, ", "));
			logger.debug(" }");

			ItemDetailProfileExportLtsDTO[] itemDetailProfileExportLtsDTO = itemExportService
					.mapOffersToItem(offerDetailProfileLtsDTO, iddCallPlanCds);

			itemDetailsProfileResponse = JAXB_OBJECT_FACTORY
					.createItemDetailsProfileResponse();
			if (itemDetailProfileExportLtsDTO != null
					&& itemDetailProfileExportLtsDTO.length > 0) {
				logger.debug(" ItemDetailsProfileEndPoint.mapOffersToItem <OUT>OfferDetailsResult: { ");
				for (int i = 0; i < itemDetailProfileExportLtsDTO.length; i++) {
					offerDetailsResult = JAXB_OBJECT_FACTORY
							.createItemDetailsProfileResponseOfferDetailsResult();
					BeanUtils.copyProperties(itemDetailProfileExportLtsDTO[i],
							offerDetailsResult);
					offerDetailsResult.setTpEffDate(itemDetailProfileExportLtsDTO[i].getConditionEffStartDate());
					offerDetailsResult.setTpEndDate(itemDetailProfileExportLtsDTO[i].getConditionEffEndDate());
					offerDetailsResult.setPromoStartDate(itemDetailProfileExportLtsDTO[i].getPromotionStartDate());
					offerDetailsResult.setPromoEndDate(itemDetailProfileExportLtsDTO[i].getPromotionEndDate());
					
					itemDetailsProfileResponse.getOfferDetailsResult().add(
							offerDetailsResult);
					
					logger.debug("    {");
					logger.debug("        ArpuType: " + itemDetailProfileExportLtsDTO[i].getArpuType());
					logger.debug("        Category: " + itemDetailProfileExportLtsDTO[i].getCategory());
					logger.debug("        ConditionEffEndDate: " + itemDetailProfileExportLtsDTO[i].getConditionEffEndDate());
					logger.debug("        ConditionEffStartDate: " + itemDetailProfileExportLtsDTO[i].getConditionEffStartDate());
					logger.debug("        ContractMonth: " + itemDetailProfileExportLtsDTO[i].getContractMonth());
					logger.debug("        Description: " + itemDetailProfileExportLtsDTO[i].getDescription());
					logger.debug("        GrossEffPrice: " + itemDetailProfileExportLtsDTO[i].getGrossEffPrice());
					logger.debug("        IddFreeMin: " + itemDetailProfileExportLtsDTO[i].getIddFreeMin());
					logger.debug("        ItemId: " + itemDetailProfileExportLtsDTO[i].getItemId());
					logger.debug("        NetEffPrice: " + itemDetailProfileExportLtsDTO[i].getNetEffPrice());
					logger.debug("        PromotionEndDate: " + itemDetailProfileExportLtsDTO[i].getPromotionEndDate());
					logger.debug("        PromotionExpiredMonths: " + itemDetailProfileExportLtsDTO[i].getPromotionExpiredMonths());
					logger.debug("        PromotionStartDate: " + itemDetailProfileExportLtsDTO[i].getPromotionStartDate());
					logger.debug("        ReturnCd: " + itemDetailProfileExportLtsDTO[i].getReturnCd());
					logger.debug("        ReturnMsg: " + itemDetailProfileExportLtsDTO[i].getReturnMsg());
					logger.debug("        TpExpiredMonths: " + itemDetailProfileExportLtsDTO[i].getTpExpiredMonths());
					logger.debug("    }");
					
				}

				logger.debug(" } ");
			}

		} catch (Exception e) {
			itemDetailsProfileResponse = new ItemDetailsProfileResponse();
			offerDetailsResult = new OfferDetailsResult();
			offerDetailsResult.setReturnCd(99);
			offerDetailsResult.setReturnMsg(ExceptionUtils.getFullStackTrace(e));
			itemDetailsProfileResponse.getOfferDetailsResult().add(
					offerDetailsResult);
			e.printStackTrace();
		}

        return new JAXBElement<ItemDetailsProfileResponse>(new QName(
                MESSAGES_NAMESPACE,MAP_OFFERS_TO_ITEM_RESPONSE), ItemDetailsProfileResponse.class, itemDetailsProfileResponse);		
	}
	

	public ItemExportService getItemExportService() {
		return itemExportService;
	}

	public void setItemExportService(ItemExportService itemExportService) {
		this.itemExportService = itemExportService;
	}
}
