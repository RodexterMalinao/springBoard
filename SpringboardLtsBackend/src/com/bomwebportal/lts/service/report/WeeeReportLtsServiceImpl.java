package com.bomwebportal.lts.service.report;

import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.lts.dao.OfferDetailDAO;
import com.bomwebportal.lts.dto.ItemAttbDTO;
import com.bomwebportal.lts.dto.order.ItemAttributeDetailLtsDTO;
import com.bomwebportal.lts.dto.order.ItemDetailLtsDTO;
import com.bomwebportal.lts.dto.order.SbOrderDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailLtsDTO;
import com.bomwebportal.lts.util.LtsBackendConstant;
import com.bomwebportal.lts.util.LtsDateFormatHelper;
import com.bomwebportal.lts.util.LtsSbHelper;
import com.bomwebportal.service.CodeLkupCacheService;
import com.pccw.rpt.schema.dto.ReportDTO;
import com.pccw.rpt.schema.dto.weeeForm.WeeeFormRptDTO;

public class WeeeReportLtsServiceImpl implements ReportLtsService{
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	protected CodeLkupCacheService swNoSignatureDisplayLkupCacheService;
	protected OfferDetailDAO offerDetailDao;
	
	public CodeLkupCacheService getSwNoSignatureDisplayLkupCacheService() {
		return swNoSignatureDisplayLkupCacheService;
	}
	public void setSwNoSignatureDisplayLkupCacheService(
			CodeLkupCacheService swNoSignatureDisplayLkupCacheService) {
		this.swNoSignatureDisplayLkupCacheService = swNoSignatureDisplayLkupCacheService;
	}


	public OfferDetailDAO getOfferDetailDao() {
		return offerDetailDao;
	}
	public void setOfferDetailDao(OfferDetailDAO offerDetailDao) {
		this.offerDetailDao = offerDetailDao;
	}
	
	@Override
	public void fillReport(ReportDTO pReportDTO, SbOrderDTO sbOrder,
			String pLocale, String pRptName, boolean pIsEditable,
			boolean pIsPrintSignature) {
		// TODO Auto-generated method stub
		ServiceDetailLtsDTO coreService = LtsSbHelper.getLtsService(sbOrder);
		ItemDetailLtsDTO[] srvItemDtls = coreService.getItemDtls();
		
		WeeeFormRptDTO rptDTO = (WeeeFormRptDTO) pReportDTO;
//		ItemAttributeDetailLtsDTO
		
		rptDTO.setOrderNum(sbOrder.getOrderId());
		rptDTO.setAppDate(StringUtils.split(sbOrder.getAppDate(), " ")[0]);
		
		rptDTO.setSignDate(StringUtils.defaultIfEmpty(StringUtils.substring(sbOrder.getSignOffDate(), 0, 10), 
				LtsDateFormatHelper.getSysDate("dd/MM/yyyy")));
		
		for (int i=0; srvItemDtls != null && i<srvItemDtls.length; i++) {
			if ((LtsBackendConstant.ITEM_TYPE_EPD_ACCEPT).equals(srvItemDtls[i].getItemType())) {
				rptDTO.setEpdAChkBox("X");
				rptDTO.setReeNum("1");
				rptDTO.setTelNum(getWeeeContactInput(srvItemDtls, "CONTACT_NUM"));
				rptDTO.setCustName(getWeeeContactInput(srvItemDtls, "CONTACT_NAME"));
			}
			
			else if ((LtsBackendConstant.ITEM_TYPE_EPD_FORFEIT).equals(srvItemDtls[i].getItemType())) {
				rptDTO.setEpdFChkBox("X");
			}
			
			else if ((LtsBackendConstant.ITEM_TYPE_EPD_UNDER_CONSIDERATION).equals(srvItemDtls[i].getItemType())) {
				rptDTO.setEpdUCChkBox("X");
				rptDTO.setReeNum("1");
				rptDTO.setSignoffDate(getConsiderDueDate(sbOrder));
			}
			
		}
		
		if (pIsPrintSignature) {
			ServiceDetailDTO ltsService =  LtsSbHelper.getLtsService(sbOrder);
			
			if (ltsService.getThirdPartyDtl() != null) {
				byte[] signature = LtsSbHelper.getSignature(sbOrder, (LtsSbHelper.getLtsEyeService(sbOrder) != null) ? LtsBackendConstant.SIGN_TYPE_EYE_3RD_PARTY : LtsBackendConstant.SIGN_TYPE_DEL_3RD_PARTY);
					if (StringUtils.startsWith(sbOrder.getOrderId(), LtsBackendConstant.ORDER_PREFIX_CALL_CENTRE)) {
						rptDTO.setNoSignatureR((String)swNoSignatureDisplayLkupCacheService.get(pLocale));
					}
				
					else {
						rptDTO.setSignature(signature);
					}
			}
			
			else {
				byte[] signature = LtsSbHelper.getSignature(sbOrder, (LtsSbHelper.getLtsEyeService(sbOrder) != null) ? LtsBackendConstant.SIGN_TYPE_EYE_CUST : LtsBackendConstant.SIGN_TYPE_DEL_CUST);
					if (StringUtils.startsWith(sbOrder.getOrderId(), LtsBackendConstant.ORDER_PREFIX_CALL_CENTRE)) {
						rptDTO.setNoSignatureR((String)swNoSignatureDisplayLkupCacheService.get(pLocale));
					}
				
					else {
						rptDTO.setSignature(signature);
					}
				
				if ((signature == null || ArrayUtils.isEmpty(signature)) && !StringUtils.startsWith(sbOrder.getOrderId(), LtsBackendConstant.ORDER_PREFIX_RETAIL) && !StringUtils.startsWith(sbOrder.getOrderId(), LtsBackendConstant.ORDER_PREFIX_DIRECT_SALES)) {
					rptDTO.setNoSignatureR((String)swNoSignatureDisplayLkupCacheService.get(pLocale));
				}
			}
		}
		
		else {
			
		}
		
	}
	
	private String getConsiderDueDate(SbOrderDTO sbOrder) {
		
		DateTime signOffDate = DateTime.parse(StringUtils.defaultIfEmpty(StringUtils.substring(sbOrder.getSignOffDate(), 0, 10), 
				LtsDateFormatHelper.getSysDate("dd/MM/yyyy")), DateTimeFormat.forPattern("dd/MM/yyyy"));

		return signOffDate.plusDays(3).toString(DateTimeFormat.forPattern("dd/MM/yyyy")); 
	}

	private String getWeeeContactInput(ItemDetailLtsDTO[] srvItemDtls, String inputType) {
		
		if (ArrayUtils.isEmpty(srvItemDtls)) {
			return null;
		}
		
		for (ItemDetailLtsDTO item : srvItemDtls) {
			if (LtsBackendConstant.ITEM_TYPE_EPD_ACCEPT.equals(item.getItemType())) {
				try {		
					if (ArrayUtils.isEmpty(item.getItemAttbs())) {
						return null;
					}
					
					List<ItemAttbDTO> itemAttbList = offerDetailDao.getItemAttb(item.getItemId());
					if (itemAttbList == null || itemAttbList.isEmpty()) {
						return null;
					}

					for (ItemAttributeDetailLtsDTO itemAttbDtl : item.getItemAttbs()) {
						for (ItemAttbDTO itemAttb : itemAttbList) {
							if (StringUtils.equals(itemAttb.getAttbID(), itemAttbDtl.getAttbCd()) 
									&& inputType.equalsIgnoreCase(itemAttb.getInputFormat())) {
								return itemAttbDtl.getAttbValue();
							}	
						}
					}
				}
				catch (DAOException e) {
					logger.error(ExceptionUtils.getFullStackTrace(e));
					throw new AppRuntimeException(e);
				}
			}
		}
		return null;
	}
}
