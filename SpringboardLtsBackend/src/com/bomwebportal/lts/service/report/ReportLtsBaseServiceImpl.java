package com.bomwebportal.lts.service.report;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.lts.dao.order.OrderDetailDAO;
import com.bomwebportal.lts.dao.order.WItemChargesRptTemplateVDAO;
import com.bomwebportal.lts.dto.ItemAttbDTO;
import com.bomwebportal.lts.dto.ItemDetailDTO;
import com.bomwebportal.lts.dto.ItemDetailSummaryDTO;
import com.bomwebportal.lts.dto.order.AppointmentDetailLtsDTO;
import com.bomwebportal.lts.dto.order.BillingAddressLtsDTO;
import com.bomwebportal.lts.dto.order.CustOptOutInfoLtsDTO;
import com.bomwebportal.lts.dto.order.CustomerDetailLtsDTO;
import com.bomwebportal.lts.dto.order.ItemAttributeDetailLtsDTO;
import com.bomwebportal.lts.dto.order.ItemDetailLtsDTO;
import com.bomwebportal.lts.dto.order.PaymentMethodDetailLtsDTO;
import com.bomwebportal.lts.dto.order.RecontractLtsDTO;
import com.bomwebportal.lts.dto.order.SbOrderDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailLtsDTO;
import com.bomwebportal.lts.dto.order.ThirdPartyDetailLtsDTO;
import com.bomwebportal.lts.service.BasketDetailService;
import com.bomwebportal.lts.service.LtsOrderDocumentService;
import com.bomwebportal.lts.service.OfferService;
import com.bomwebportal.lts.service.PaymentService;
import com.bomwebportal.lts.util.LtsBackendConstant;
import com.bomwebportal.lts.util.LtsDateFormatHelper;
import com.bomwebportal.lts.util.LtsSbHelper;
import com.bomwebportal.service.CodeLkupCacheService;
import com.pccw.rpt.dao.WItemRptTemplateVDAO;
import com.pccw.rpt.schema.dto.ReportDTO;
import com.pccw.rpt.schema.dto.eye.eyeAppForm.EyeAppFormRptDTO;
import com.pccw.rpt.schema.dto.eye.eyeAppForm.SectionARptDTO;
import com.pccw.rpt.schema.dto.eye.eyeAppForm.SectionDRptDTO;
import com.pccw.rpt.schema.dto.eye.eyeAppForm.SectionERptDTO;
import com.pccw.rpt.schema.dto.eye.eyeAppForm.SectionFRptDTO;
import com.pccw.rpt.schema.dto.eye.eyeAppForm.SectionInternalUseDTO;
import com.pccw.rpt.schema.dto.eye.eyeAppForm.SectionJRptDTO;
import com.pccw.rpt.schema.dto.eye.eyeAppForm.SectionKRptDTO;
import com.pccw.rpt.schema.dto.eye.eyeAppForm.SectionLRptDTO;
import com.pccw.rpt.schema.dto.eye.eyeAppForm.SectionNRptDTO;
import com.pccw.rpt.schema.dto.eye.eyeAppForm.SectionPRptDTO;
import com.pccw.rpt.schema.dto.eye.eyeAppForm.SectionQRptDTO;
import com.pccw.rpt.service.ReportFixedDataService;
import com.pccw.rpt.service.ReportService;
import com.pccw.rpt.util.ReportUtil;
import com.pccw.util.spring.SpringApplicationContext;

public abstract class ReportLtsBaseServiceImpl implements ReportLtsService {
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	protected BasketDetailService basketDetailService = null;
	protected OfferService offerService = null;
	protected CodeLkupCacheService penaltyHandleLtsLkupCacheService = null;
	protected CodeLkupCacheService erEyeHandleCacheService = null;
	protected CodeLkupCacheService erDelHandleCacheService = null;
	private PaymentService paymentService = null;
	private ReportFixedDataService reportFixedDataService = null;
	private LtsOrderDocumentService ltsOrderDocumentService = null;
	private CodeLkupCacheService ltsWaiveReasonCacheService = null;
	private CodeLkupCacheService ltsDocTypeLkupCacheService = null;
	private CodeLkupCacheService waiveReasonLkupCacheService = null;
	
	private OrderDetailDAO orderDetailDao = null;
	
	protected static final String[] SECTION_C_ITEM_TYPE = {LtsBackendConstant.ITEM_TYPE_PLAN, LtsBackendConstant.ITEM_TYPE_VAS_2DEL_HOT};
	protected static final String[] SECTION_D_ITEM_TYPE = {LtsBackendConstant.ITEM_TYPE_BB_RENTAL, LtsBackendConstant.ITEM_TYPE_PE_FREE, LtsBackendConstant.ITEM_TYPE_PE_SOCKET, LtsBackendConstant.ITEM_TYPE_CONTENT, LtsBackendConstant.ITEM_TYPE_MOOV, LtsBackendConstant.ITEM_TYPE_NOWTV_FREE, LtsBackendConstant.ITEM_TYPE_NOWTV_SPEC, LtsBackendConstant.ITEM_TYPE_NOWTV_CELE, LtsBackendConstant.ITEM_TYPE_NOWTV_SPT, LtsBackendConstant.ITEM_TYPE_NOWTV_PAY, LtsBackendConstant.ITEM_TYPE_NOWTV_STAR, LtsBackendConstant.ITEM_TYPE_NOWTV_MIRR, LtsBackendConstant.ITEM_TYPE_VIEW_ON_DEVICE, LtsBackendConstant.ITEM_TYPE_TV_EMAIL, LtsBackendConstant.ITEM_TYPE_EXIST_VAS, LtsBackendConstant.ITEM_TYPE_REPLAC_VAS, LtsBackendConstant.ITEM_TYPE_VAS_HOT, LtsBackendConstant.ITEM_TYPE_VAS_OTHER, LtsBackendConstant.ITEM_TYPE_IDD, LtsBackendConstant.ITEM_TYPE_0060E, LtsBackendConstant.ITEM_TYPE_PREM_PAY, LtsBackendConstant.ITEM_TYPE_VAS_2DEL_OTHER, LtsBackendConstant.ITEM_TYPE_BVAS, LtsBackendConstant.ITEM_TYPE_TV_PRINT, LtsBackendConstant.ITEM_TYPE_PAPER_BILL,LtsBackendConstant.ITEM_TYPE_PAPER_BILL_WAIVE, LtsBackendConstant.ITEM_TYPE_PAPER_BILL_GENERATE, LtsBackendConstant.ITEM_TYPE_PAPER_BILL_BR, LtsBackendConstant.ITEM_TYPE_TV_DEVICE, LtsBackendConstant.ITEM_TYPE_VAS_2DEL_FREE, LtsBackendConstant.ITEM_TYPE_OPT_ACC, LtsBackendConstant.ITEM_TYPE_VAS_2DEL_STANDALONE, LtsBackendConstant.ITEM_LTS_TP, LtsBackendConstant.ITEM_TYPE_INSTALL, LtsBackendConstant.ITEM_TYPE_INSTALL_WAIVE, LtsBackendConstant.ITEM_TYPE_ONLINE_VAS, LtsBackendConstant.ITEM_TYPE_FFP_HOT, LtsBackendConstant.ITEM_TYPE_FFP_OTHER, LtsBackendConstant.ITEM_TYPE_FFP_STAFF, LtsBackendConstant.ITEM_TYPE_VAS_FFP, LtsBackendConstant.ITEM_TYPE_VAS_PRE_WIRING, LtsBackendConstant.ITEM_TYPE_VAS_PRE_INSTALL, LtsBackendConstant.ITEM_TYPE_SMART_WARRANTY, LtsBackendConstant.ITEM_TYPE_DN_CHANGE, LtsBackendConstant.ITEM_TYPE_DNY_CHANGE, LtsBackendConstant.ITEM_TYPE_DN_CHANGE_WAIVE, LtsBackendConstant.ITEM_TYPE_DNY_CHANGE_WAIVE, LtsBackendConstant.ITEM_TYPE_SELF_PICKUP, LtsBackendConstant.ITEM_TYPE_FIELD_SERVICE};
	protected static final String[] SECTION_E_ITEM_TYPE = {LtsBackendConstant.ITEM_TYPE_PREMIUM};
	protected static final String[] SECTION_F_ITEM_TYPE = {LtsBackendConstant.ITEM_TYPE_PLAN, LtsBackendConstant.ITEM_TYPE_VAS_2DEL_HOT, LtsBackendConstant.ITEM_TYPE_BB_RENTAL, LtsBackendConstant.ITEM_TYPE_PE_FREE, LtsBackendConstant.ITEM_TYPE_PE_SOCKET, LtsBackendConstant.ITEM_TYPE_CONTENT, LtsBackendConstant.ITEM_TYPE_MOOV, LtsBackendConstant.ITEM_TYPE_NOWTV_FREE, LtsBackendConstant.ITEM_TYPE_NOWTV_SPEC, LtsBackendConstant.ITEM_TYPE_NOWTV_CELE, LtsBackendConstant.ITEM_TYPE_NOWTV_SPT, LtsBackendConstant.ITEM_TYPE_NOWTV_PAY, LtsBackendConstant.ITEM_TYPE_NOWTV_STAR, LtsBackendConstant.ITEM_TYPE_NOWTV_MIRR, LtsBackendConstant.ITEM_TYPE_VIEW_ON_DEVICE, LtsBackendConstant.ITEM_TYPE_TV_EMAIL, LtsBackendConstant.ITEM_TYPE_EXIST_VAS, LtsBackendConstant.ITEM_TYPE_REPLAC_VAS, LtsBackendConstant.ITEM_TYPE_VAS_HOT, LtsBackendConstant.ITEM_TYPE_VAS_OTHER, LtsBackendConstant.ITEM_TYPE_IDD, LtsBackendConstant.ITEM_TYPE_0060E, LtsBackendConstant.ITEM_TYPE_PREM_PAY, LtsBackendConstant.ITEM_TYPE_VAS_2DEL_OTHER, LtsBackendConstant.ITEM_TYPE_BVAS, LtsBackendConstant.ITEM_TYPE_VAS_2DEL_FREE, LtsBackendConstant.ITEM_TYPE_OPT_ACC, LtsBackendConstant.ITEM_TYPE_VAS_2DEL_STANDALONE, LtsBackendConstant.ITEM_LTS_TP, LtsBackendConstant.ITEM_TYPE_ONLINE_VAS, LtsBackendConstant.ITEM_TYPE_VAS_FFP};
	protected static final String[] SECTION_G_ITEM_TYPE = {LtsBackendConstant.ITEM_TYPE_PAPER_BILL,LtsBackendConstant.ITEM_TYPE_PAPER_BILL_WAIVE, LtsBackendConstant.ITEM_TYPE_PAPER_BILL_GENERATE, LtsBackendConstant.ITEM_TYPE_PAPER_BILL_BR, LtsBackendConstant.ITEM_TYPE_VIEW_ON_DEVICE, LtsBackendConstant.ITEM_TYPE_TV_EMAIL, LtsBackendConstant.ITEM_TYPE_TV_PRINT, LtsBackendConstant.ITEM_TYPE_TV_DEVICE, LtsBackendConstant.ITEM_TYPE_KEEP_EXIST_BILL, LtsBackendConstant.ITEM_TYPE_ONLINE_EBILL, LtsBackendConstant.ITEM_TYPE_EBILL, LtsBackendConstant.ITEM_TYPE_MYHKT_BILL, LtsBackendConstant.ITEM_TYPE_EXIST_MYHKT_BILL, LtsBackendConstant.ITEM_TYPE_EBILL, LtsBackendConstant.ITEM_TYPE_EMAIL_BILL};
	protected static final String[] SECTION_J_ITEM_TYPE = {LtsBackendConstant.ITEM_TYPE_PLAN, LtsBackendConstant.ITEM_TYPE_VAS_2DEL_HOT, LtsBackendConstant.ITEM_TYPE_BB_RENTAL, LtsBackendConstant.ITEM_TYPE_PE_FREE, LtsBackendConstant.ITEM_TYPE_PE_SOCKET, LtsBackendConstant.ITEM_TYPE_CONTENT, LtsBackendConstant.ITEM_TYPE_MOOV, LtsBackendConstant.ITEM_TYPE_NOWTV_FREE, LtsBackendConstant.ITEM_TYPE_NOWTV_SPEC, LtsBackendConstant.ITEM_TYPE_NOWTV_CELE, LtsBackendConstant.ITEM_TYPE_NOWTV_SPT, LtsBackendConstant.ITEM_TYPE_NOWTV_PAY, LtsBackendConstant.ITEM_TYPE_NOWTV_STAR, LtsBackendConstant.ITEM_TYPE_NOWTV_MIRR, LtsBackendConstant.ITEM_TYPE_VIEW_ON_DEVICE, LtsBackendConstant.ITEM_TYPE_TV_EMAIL, LtsBackendConstant.ITEM_TYPE_EXIST_VAS, LtsBackendConstant.ITEM_TYPE_REPLAC_VAS, LtsBackendConstant.ITEM_TYPE_VAS_HOT, LtsBackendConstant.ITEM_TYPE_VAS_OTHER, LtsBackendConstant.ITEM_TYPE_IDD, LtsBackendConstant.ITEM_TYPE_0060E, LtsBackendConstant.ITEM_TYPE_PREM_PAY, LtsBackendConstant.ITEM_TYPE_VAS_2DEL_OTHER, LtsBackendConstant.ITEM_TYPE_BVAS, LtsBackendConstant.ITEM_TYPE_VAS_2DEL_FREE, LtsBackendConstant.ITEM_TYPE_OPT_ACC, LtsBackendConstant.ITEM_TYPE_VAS_2DEL_STANDALONE, LtsBackendConstant.ITEM_LTS_TP, LtsBackendConstant.ITEM_TYPE_VAS_FFP};
	protected static final String[] SECTION_INTERNAL_USE_OPT_OUT = {LtsBackendConstant.ITEM_TYPE_IDD_OUT, LtsBackendConstant.ITEM_TYPE_0060E_OUT};
	protected static final String[] SECTION_INTERNAL_USE_OUT_LTS = {LtsBackendConstant.ITEM_LTS_VAS,  LtsBackendConstant.ITEM_LTS_TP};
	protected static final String[] SECTION_INTERNAL_USE_OUT_IMS = {LtsBackendConstant.ITEM_IMS_VAS};
	protected static final String[] SECTION_INTERNAL_USE_CHARGE = {LtsBackendConstant.ITEM_TYPE_ADMIN_CHARGE, LtsBackendConstant.ITEM_TYPE_CANCEL_CHARGE};
	protected static final String[] SECTION_INTERNAL_ER = {LtsBackendConstant.ITEM_TYPE_ER_CHARGE};
	
	protected Map<String, List<ItemDetailSummaryDTO>> itemDescMap = new TreeMap<String, List<ItemDetailSummaryDTO>>();
	protected Map<String, List<ItemDetailSummaryDTO>> itemRemarkMap = new TreeMap<String, List<ItemDetailSummaryDTO>>();

	public abstract void fillReport(ReportDTO pReportDTO, SbOrderDTO sbOrder, String pLocale, String pRptName, boolean pIsEditable, boolean pIsPrintSignature);
	
	protected abstract void addItemToSectionD(SectionDRptDTO pSectionD, ItemDetailSummaryDTO pItemDesc);
	protected abstract void addItemToSectionE(SectionERptDTO pSectionE, ItemDetailSummaryDTO pItemDesc);
	protected abstract void addItemToSectionF(SectionFRptDTO pSectionF, String pCharge, String pChargeDesc);
	protected abstract void addItemToSectionJ(SectionJRptDTO pSectionJ, ItemDetailSummaryDTO pItemDesc);
	
	
	protected void initializeItem(ItemDetailLtsDTO[] pItems, String pApplDate, String pLocale, boolean pIsEditable, boolean isInternalUse) throws Exception {
		
		if (ArrayUtils.isEmpty(pItems)) {
			return;
		}
		this.initializeItemDesc(pItems, pApplDate, pLocale, pIsEditable, isInternalUse);
		this.initializeItemRemark(pItems, pApplDate, pLocale, pIsEditable, isInternalUse);
	}
	
	private void initializeItemDesc(ItemDetailLtsDTO[] pItems, String pApplDate, String pLocale, boolean pIsEditable, boolean isInternalUse) throws Exception {
		
		Map<String, ItemDetailLtsDTO> pItemDtlMap = new HashMap<String, ItemDetailLtsDTO>();
		
		for (int i=0; pItems!=null && i<pItems.length; ++i) {
			pItemDtlMap.put(pItems[i].getItemId(), pItems[i]);
		}
		this.initializeItem(pApplDate, pLocale, this.itemDescMap, "ITEM_DESC", pItemDtlMap, pIsEditable, isInternalUse);
		String[] unmappedItemIds = pItemDtlMap.keySet().toArray(new String[pItemDtlMap.size()]);
		ItemDetailSummaryDTO itemDtlSummary = null;
		ItemDetailDTO unmappedItem = null;
		List<ItemDetailSummaryDTO> outVasItemList = this.getItemTypeList(this.itemDescMap, LtsBackendConstant.ITEM_LTS_VAS);
		List<ItemDetailSummaryDTO> outTpItemList = this.getItemTypeList(this.itemDescMap, LtsBackendConstant.ITEM_LTS_TP);
		List<ItemDetailSummaryDTO> outList = null;				
		
		for (int i=0; i<unmappedItemIds.length; ++i) {
			unmappedItem = this.offerService.getTpVasItemDetail(unmappedItemIds[i], LtsBackendConstant.LOCALE_ENG);
			
			if (unmappedItem != null) {
				if (StringUtils.equals(LtsBackendConstant.ITEM_LTS_VAS, unmappedItem.getItemType())) {
					itemDtlSummary = this.generateItemSummaryDTO(unmappedItem, pItemDtlMap.get(unmappedItem.getItemId()));
					outList = outVasItemList;
				} else if (StringUtils.equals(LtsBackendConstant.ITEM_LTS_TP, unmappedItem.getItemType())) {
					itemDtlSummary = this.generateItemSummaryDTO(unmappedItem, pItemDtlMap.get(unmappedItem.getItemId()));
					outList = outTpItemList;
				} else {
					continue;
				}
				if (StringUtils.equals(LtsBackendConstant.IO_IND_OUT, itemDtlSummary.getIoInd())) {
					outList.add(itemDtlSummary);
				}
			}
		}
	}
	
	private void initializeItemRemark(ItemDetailLtsDTO[] pItems, String pApplDate, String pLocale, boolean pIsEditable, boolean isInternalUse) throws Exception {
		
		Map<String, ItemDetailLtsDTO> pItemDtlMap = new HashMap<String, ItemDetailLtsDTO>();
		
		for (int i=0; pItems!=null && i<pItems.length; ++i) {
			pItemDtlMap.put(pItems[i].getItemId(), pItems[i]);
		}
		WItemRptTemplateVDAO[] itemRemarks = null;
		
		try {
			WItemRptTemplateVDAO criteria = SpringApplicationContext.getBean(WItemRptTemplateVDAO.class);
            criteria.setSearchKey("itemType", LtsBackendConstant.ITEM_TYPE_PLAN);
            criteria.setSearchKey("lob", LtsBackendConstant.LOB_LTS);
            criteria.setSearchKey("locale", pLocale);
			criteria.setExtraBind("appDate", LtsDateFormatHelper.getDateFromDTOFormat(pApplDate));
			criteria.setSearchKeyIn("itemId", pItemDtlMap.keySet().toArray(new String[pItemDtlMap.size()]));
            criteria.setSearchKeyIn("displayType", new String[] {"ITEM_RMK", "ITEM_RMK_TITLE"});
            criteria.setAdditionWhere(" TO_DATE( :appDate , 'DD/MM/YYYY') BETWEEN PRC_EFF_START_DATE AND PRC_EFF_END_DATE");
            itemRemarks = (WItemRptTemplateVDAO[]) criteria.doSelect(null, null, null, "ITEM_ID ASC, DISPLAY_TYPE DESC");			
		} catch (Exception e) {
			logger.error("Fail in ReportDataBaseService.initializeItem()", e);
			throw new AppRuntimeException(e);
		}
		String itemId = null;
		StringBuilder sbContents = null;
		List<ItemDetailSummaryDTO> itemPlanList = new ArrayList<ItemDetailSummaryDTO>();
		
		for (int i=0; itemRemarks!=null && i<itemRemarks.length; ++i) {
			if (!StringUtils.equals(itemRemarks[i].getItemId(), itemId)) {
				if (StringUtils.isNotBlank(itemId)) {
					itemPlanList.add(this.createDummyItemDetail(sbContents.toString(), LtsBackendConstant.ITEM_TYPE_PLAN));
				}
				itemId = itemRemarks[i].getItemId();
				sbContents = new StringBuilder();
			}
			if (StringUtils.equals(itemRemarks[i].getItemId(), itemId)) {
				if (sbContents.length() > 0) {
					sbContents.append("<br/>");
				}
			}
			sbContents.append(itemRemarks[i].getHtml());

			if (pIsEditable) {
				sbContents.append(SpringApplicationContext.getBean(ReportService.class).generateEditButtonHtml(itemRemarks[i]));
			}
		}
		if (StringUtils.isNotBlank(itemId)) {
			itemPlanList.add(this.createDummyItemDetail(sbContents.toString(), LtsBackendConstant.ITEM_TYPE_PLAN));
		}
		this.initializeItem(pApplDate, pLocale, this.itemRemarkMap, "ITEM_RMK", pItemDtlMap, pIsEditable, isInternalUse);
		this.itemRemarkMap.put(LtsBackendConstant.ITEM_TYPE_PLAN, itemPlanList);
	}
	
	private void initializeItem(String pApplDate, String pLocale, Map<String, List<ItemDetailSummaryDTO>> pItemDescMap, 
			String pDisplayType, Map<String, ItemDetailLtsDTO> pItemDtlMap, boolean pIsEditable, boolean isInternalUse) throws Exception {
		
		StringBuilder additionWhere = new StringBuilder();
		WItemRptTemplateVDAO[] itemDescs = null;

		try {
			WItemRptTemplateVDAO criteria = SpringApplicationContext.getBean(WItemRptTemplateVDAO.class);
			criteria.setSearchKey("displayType", pDisplayType);
			criteria.setSearchKey("locale", pLocale);
			additionWhere.append(" TO_DATE( :appDate , 'DD/MM/YYYY') BETWEEN PRC_EFF_START_DATE AND PRC_EFF_END_DATE");
			criteria.setExtraBind("appDate", LtsDateFormatHelper.getDateFromDTOFormat(pApplDate));
			criteria.setSearchKeyIn("itemId", pItemDtlMap.keySet().toArray(new String[pItemDtlMap.size()]));
			criteria.setAdditionWhere(additionWhere.toString());
			itemDescs = (WItemRptTemplateVDAO[])criteria.doSelect(null, null);
		} catch (Exception e) {
			logger.error("Fail in ReportDataBaseService.initializeItem()", e);
			throw new AppRuntimeException(e);
		}
		List<ItemDetailSummaryDTO> itemList = null;
		StringBuilder sbContents = null;
		ItemDetailSummaryDTO itemSummary = null;
		
		for (int i=0; itemDescs!=null && i<itemDescs.length; ++i) {
			itemList = this.getItemTypeList(pItemDescMap, itemDescs[i].getItemType());
			itemSummary = this.generateItemSummaryDTO(itemDescs[i], pItemDtlMap.get(itemDescs[i].getItemId()));
			
			if (StringUtils.equals(LtsBackendConstant.IO_IND_OUT, itemSummary.getIoInd())) {
				continue;
			}
			
			if(isInternalUse){
				String[] itemCds =  this.offerService.getItemOfferCodes(itemDescs[i].getItemId());
				StringBuilder psefCd = new StringBuilder();
				
				if(itemCds != null && itemCds.length>0){
					itemSummary.setItemDisplayHtml(itemSummary.getItemDisplayHtml() + " : ");
				}				
				
				for(String itemCd : itemCds){					
					psefCd.append("<b><font size=\"3\">" + itemCd.substring(2, 6)+ "</font></b>"  + " ");
				}
				
				itemSummary.setItemDisplayHtml(itemSummary.getItemDisplayHtml() + " " + psefCd.toString());
			}
			
			this.appendAttributeInfo(itemSummary);
			sbContents = new StringBuilder(ReportUtil.defaultString(itemSummary.getItemDisplayHtml()));
			
			if (pIsEditable) {
				sbContents.append(SpringApplicationContext.getBean(ReportService.class).generateEditButtonHtml(itemDescs[i]));
			}
			itemSummary.setItemDisplayHtml(sbContents.toString());			
			itemList.add(itemSummary);
			pItemDtlMap.remove(itemDescs[i].getItemId());
		}
	}
	
	private ItemDetailSummaryDTO createDummyItemDetail(String pItemDesc, String pItemType) {
		
		ItemDetailSummaryDTO itemDtl = new ItemDetailSummaryDTO();
		itemDtl.setItemDisplayHtml(pItemDesc);
		itemDtl.setItemType(pItemType);
		itemDtl.setIoInd(LtsBackendConstant.IO_IND_INSTALL);
		return itemDtl;
	}
	
	private ItemDetailSummaryDTO generateItemSummaryDTO(ItemDetailDTO pItemDesc, ItemDetailLtsDTO pItemDtl) throws Exception {
		
		ItemDetailSummaryDTO itemSummary = new ItemDetailSummaryDTO();
		pItemDesc.setItemDesc(pItemDesc.getItemDisplayHtml());
		BeanUtils.copyProperties(itemSummary, pItemDesc);
		itemSummary.setBasketId(pItemDtl.getBasketId());
		itemSummary.setItemQty(pItemDtl.getItemQty());
		itemSummary.setIoInd(pItemDtl.getIoInd());
		itemSummary.setPaidVas(StringUtils.equals("Y", pItemDtl.getPaidVasInd()));
		itemSummary.setForceRetain(StringUtils.equals("Y", pItemDtl.getForceRetain()));
		
		if (StringUtils.isNotBlank(pItemDtl.getExistEndDate())) {
			Date sysdate = new Date();
			Date tpExpDate = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH).parse(pItemDtl.getExistEndDate());
			itemSummary.setExpired(tpExpDate.before(sysdate));
		} else {
			itemSummary.setExpired(false);
		}
		itemSummary.setPenaltyHandling(pItemDtl.getPenaltyHandling());
		return itemSummary;
	}
	
	protected List<ItemDetailSummaryDTO> getItemTypeList(Map<String, List<ItemDetailSummaryDTO>> pItemMap, String pItemType) {
		
		List<ItemDetailSummaryDTO> itemList = null;
		
		if (pItemMap.containsKey(pItemType)) {
			itemList = pItemMap.get(pItemType);
		} else {
			itemList = new ArrayList<ItemDetailSummaryDTO>();
			pItemMap.put(pItemType, itemList);
		}
		return itemList;
	}
	
	private ItemDetailSummaryDTO generateItemSummaryDTO(WItemRptTemplateVDAO pItemDesc, ItemDetailLtsDTO pItemDtl) throws Exception {
		
		ItemDetailSummaryDTO itemSummary = new ItemDetailSummaryDTO();
		itemSummary.setItemId(pItemDesc.getItemId());
		itemSummary.setItemType(pItemDesc.getItemType());
		itemSummary.setItemDisplayHtml(pItemDesc.getHtml());
		itemSummary.setMthToMthAmtTxt(pItemDesc.getMthToMthRate());
		itemSummary.setRecurrentAmtTxt(pItemDesc.getFixTermRate());
		itemSummary.setPenaltyAmtTxt(pItemDesc.getPenaltyAmt());
		itemSummary.setBasketId(pItemDtl.getBasketId());
		itemSummary.setItemQty(pItemDtl.getItemQty());
		itemSummary.setIoInd(pItemDtl.getIoInd());
		itemSummary.setPaidVas(StringUtils.equals("Y", pItemDtl.getPaidVasInd()));
		itemSummary.setForceRetain(StringUtils.equals("Y", pItemDtl.getForceRetain()));
		
		ItemAttbDTO[] itemAttbs = this.offerService.getItemAttb(pItemDtl.getItemId());
		if (ArrayUtils.isEmpty(itemAttbs)) {
			this.offerService.getItemAttbByItemAttbAssign(pItemDtl.getItemId());
		}
		itemSummary.setItemAttbs(this.generateItemAttbSummaryDTOs(itemAttbs, pItemDtl.getItemAttbs()));
		itemSummary.setPenaltyHandling(pItemDtl.getPenaltyHandling());
		return itemSummary;
	}
	
	private ItemAttbDTO[] generateItemAttbSummaryDTOs(ItemAttbDTO[] pAttbDescs, ItemAttributeDetailLtsDTO[] pAttbValues) throws Exception {
		
		if (ArrayUtils.isEmpty(pAttbValues)) {
			return null;
		}
		List<ItemAttbDTO> attbList = new ArrayList<ItemAttbDTO>();
		ItemAttbDTO attbSummary = null;
		
		for (int i=0; i < pAttbValues.length; ++i) {
			for (int j=0; pAttbDescs!=null && j<pAttbDescs.length; ++j) {
				if (StringUtils.equals(pAttbDescs[j].getAttbID(), pAttbValues[i].getAttbCd())) {
					attbSummary = new ItemAttbDTO();
					BeanUtils.copyProperties(attbSummary, pAttbDescs[j]);
					attbSummary.setAttbValue(pAttbValues[i].getAttbValue());
					attbList.add(attbSummary);
					break;
				}
			}
		}
		return attbList.toArray(new ItemAttbDTO[attbList.size()]);
	}
	
	protected void generateReportTerms(EyeAppFormRptDTO pEyeRpt, String[] pAfTypes, String pLocale, String pRptName, boolean pIsEditable) throws Exception {
			
			Map<String, Object> rptParmMap = new HashMap<String, Object>();
			
			if (pIsEditable) {
				rptParmMap.put("EDIT_BUTTON", "Y");
			}
			
			for (int i=0; i < pAfTypes.length; i++) {
				boolean appendDtlInd = i>0; //no need to append for the first type, replace existing template instead
				this.reportFixedDataService.fillReportFixedData(pRptName + "/" + pAfTypes[i], pLocale, rptParmMap, pEyeRpt, appendDtlInd);	
			}
			
		}

	protected String[] getItemAfType(SbOrderDTO pSbOrder){
		ServiceDetailLtsDTO ltsSrv = LtsSbHelper.getLtsService(pSbOrder);
		Set<String> afTypeSet = new HashSet<String>();
		
		
		for (int i=0; ArrayUtils.isNotEmpty(ltsSrv.getItemDtls()) && i < ltsSrv.getItemDtls().length; i++) {
			if (!StringUtils.isEmpty(ltsSrv.getItemDtls()[i].getAfType())) {
				afTypeSet.add(ltsSrv.getItemDtls()[i].getAfType());
			}
		}
		return afTypeSet.toArray(new String[afTypeSet.size()]);
	}
	
	protected void generateSectionAReport(SectionARptDTO pSectionA, ServiceDetailLtsDTO pSrvDtl, SbOrderDTO pSbOrder, String locale, String orderMode, boolean internalUse) throws Exception {
		ServiceDetailLtsDTO ltsCoreService = LtsSbHelper.getLtsService(pSbOrder);
		if (ltsCoreService.getRecontract() != null) {
			this.generateRecontractCustomer(pSectionA, ltsCoreService, orderMode, internalUse, pSbOrder);
			this.generateBillingAddress(pSectionA, pSrvDtl, ltsCoreService.getRecontract().getBillingAddress(), pSbOrder);
		} else {
			this.generateCustomer(pSectionA, pSrvDtl, pSbOrder, orderMode, internalUse);
			this.generateBillingAddress(pSectionA, pSrvDtl, pSrvDtl.getAccount().getBillingAddress(), pSbOrder);
		}
		BeanUtils.copyProperties(pSectionA.getInstallAddress(), pSbOrder.getAddress());
		
		if (StringUtils.equals(LtsBackendConstant.LOCALE_ENG, locale)) {
			pSectionA.setFullAddress(pSbOrder.getAddress().getDisFullAddrDescEn());
		} else {
			pSectionA.setFullAddress(pSbOrder.getAddress().getDisFullAddrDescCh());
		}
		AppointmentDetailLtsDTO appt = pSrvDtl.getAppointmentDtl();
		
		if (appt != null) {
			if(LtsSbHelper.isNewAcquistionOrder(pSbOrder)){
				if(pSbOrder.getContact()!=null){
					pSectionA.getCustomer().setContactFixedLineNum(pSbOrder.getContact().getContactFixedLine());
					pSectionA.getCustomer().setContactMobileNum(pSbOrder.getContact().getContactMobile());
				}
			}else{
				pSectionA.getCustomer().setContactFixedLineNum(appt.getCustContactFix());
				pSectionA.getCustomer().setContactMobileNum(appt.getCustContactMobile());
			}
		}
		if (StringUtils.isNotEmpty(pSbOrder.getAddress().getHiLotNo())) {
			pSectionA.getInstallAddress().setBuildNo("LAND LOT NO " + pSbOrder.getAddress().getHiLotNo());
		}
	}
	
	private void generateRecontractCustomer(SectionARptDTO pSectionA, ServiceDetailLtsDTO pSrvDtl, String orderMode, boolean internalUse, SbOrderDTO pSbOrder) {
		
		RecontractLtsDTO recontract = pSrvDtl.getRecontract();
		pSectionA.getCustomer().setCompanyName(recontract.getCompanyName());
		pSectionA.getCustomer().setCustNo(recontract.getCustNum());
		pSectionA.getCustomer().setCustType(StringUtils.equals(recontract.getCustNum(), LtsBackendConstant.DUMMY_CUST_NO) ? LtsBackendConstant.AF_FORM_CUST_TYPE_NEW_CUST : LtsBackendConstant.AF_FORM_CUST_TYPE_EXIST_CUST);
		pSectionA.getCustomer().setEmailAddr(recontract.getEmailAddr());
		pSectionA.getCustomer().setFirstName(recontract.getCustFirstName());
		pSectionA.getCustomer().setLastName(recontract.getCustLastName());
		//pSectionA.getCustomer().setIdDocNum(LtsBackendConstant.ORDER_PREFIX_RETAIL.equals(orderMode)||internalUse?recontract.getIdDocNum():null);
		pSectionA.getCustomer().setIdDocType(recontract.getIdDocType());
		//JT2017 - for idDocNum, will hide part of the number
		StringBuilder idx = new StringBuilder();
		if(!internalUse){
			//BOM2017062 - add premier team order with "R" signoff mode under this checking
			if ((LtsBackendConstant.ORDER_PREFIX_RETAIL.equals(orderMode) || (LtsBackendConstant.ORDER_PREFIX_PREMIER_TEAM.equals(orderMode) && "R".equals(pSbOrder.getSignoffMode()))) && LtsBackendConstant.DOC_TYPE_HKID.equals(recontract.getIdDocType())) {
				idx.append(StringUtils.substring(recontract.getIdDocNum(), 0, 4));
				idx.append("XXX(X)");
			}
			
			//BOM2017062 - add premier team order with "R" signoff mode under this checking
			else if ((LtsBackendConstant.ORDER_PREFIX_RETAIL.equals(orderMode) || (LtsBackendConstant.ORDER_PREFIX_PREMIER_TEAM.equals(orderMode) && "R".equals(pSbOrder.getSignoffMode()))) && LtsBackendConstant.DOC_TYPE_PASSPORT.equals(recontract.getIdDocType())) {
				idx.append(StringUtils.substring(recontract.getIdDocNum(), 0, 4));
				idx.append("XXXXX");
			}
		}
		else {
			idx.append(recontract.getIdDocNum());
		}
		pSectionA.getCustomer().setIdDocNum(idx.toString());
		pSectionA.getCustomer().setTitle(recontract.getTitle());
		
		//JT 2017062
		if (LtsBackendConstant.ORDER_PREFIX_PREMIER_TEAM.equals(orderMode) && "R".equals(pSbOrder.getSignoffMode())) {
			pSectionA.getCustomer().setIdVerifiedInd(pSrvDtl.getAccount().getCustomer().getIdVerifiedInd());
			pSectionA.getCustomer().setSignoffMode("R");
		}
		
		else {
			pSectionA.getCustomer().setIdVerifiedInd("Y");
		}
	}
	
	private void generateCustomer(SectionARptDTO pSectionA, ServiceDetailLtsDTO pSrvDtl, SbOrderDTO pSbOrder, String orderMode, boolean internalUse) throws Exception {
		
		BeanUtils.copyProperties(pSectionA.getCustomer(), pSrvDtl.getAccount().getCustomer());
		pSectionA.getCustomer().setDob(LtsDateFormatHelper.getDateFromDTOFormat(pSrvDtl.getAccount().getCustomer().getDob()));
		
		if (StringUtils.equals("Y", pSrvDtl.getAccount().getCustomer().getCsPortalInd())) {
			//pSectionA.getCustomer().setEmailAddr(pSrvDtl.getAccount().getCustomer().getCsPortalLogin());
			pSectionA.getCustomer().setCsPortal(true);
		} else if(StringUtils.equals("Y", pSrvDtl.getAccount().getCustomer().getTheClubInd())
				   && !StringUtils.equals("Y", pSrvDtl.getAccount().getCustomer().getCsPortalInd())){
			//pSectionA.getCustomer().setEmailAddr(pSrvDtl.getAccount().getCustomer().getTheClubLogin());
			pSectionA.getCustomer().setCsPortal(true);
		} 
		else {
			pSectionA.getCustomer().setEmailAddr(pSbOrder.getEsigEmailAddr());
			pSectionA.getCustomer().setCsPortal(false);
		}
		pSectionA.getCustomer().setCustType(StringUtils.equals(pSectionA.getCustomer().getCustNo(), LtsBackendConstant.DUMMY_CUST_NO) ? LtsBackendConstant.AF_FORM_CUST_TYPE_NEW_CUST : LtsBackendConstant.AF_FORM_CUST_TYPE_EXIST_CUST);	
		//pSectionA.getCustomer().setIdDocNum(LtsBackendConstant.ORDER_PREFIX_RETAIL.equals(orderMode)||internalUse?pSrvDtl.getActualDocId():null));
		pSectionA.getCustomer().setIdDocType(pSrvDtl.getActualDocType());
		//JT2017 - for idDocNum, will hide part of the number
				StringBuilder idx = new StringBuilder();
				if(!internalUse){
					//BOM2017062 - add premier team order with "R" signoff mode under this checking
					if ((LtsBackendConstant.ORDER_PREFIX_RETAIL.equals(orderMode) || (LtsBackendConstant.ORDER_PREFIX_PREMIER_TEAM.equals(orderMode) && "R".equals(pSbOrder.getSignoffMode()))) && LtsBackendConstant.DOC_TYPE_HKID.equals(pSrvDtl.getActualDocType())) {
						idx.append(StringUtils.substring(pSrvDtl.getActualDocId(), 0, 4));
						idx.append("XXX(X)");
					}
					
					//BOM2017062 - add premier team order with "R" signoff mode under this checking
					else if ((LtsBackendConstant.ORDER_PREFIX_RETAIL.equals(orderMode) || (LtsBackendConstant.ORDER_PREFIX_PREMIER_TEAM.equals(orderMode) && "R".equals(pSbOrder.getSignoffMode()))) && LtsBackendConstant.DOC_TYPE_PASSPORT.equals(pSrvDtl.getActualDocType())) {
						idx.append(StringUtils.substring(pSrvDtl.getActualDocId(), 0, 4));
						idx.append("XXXXX");
					}
				}
				else {
					idx.append(pSrvDtl.getActualDocId());
				}
		pSectionA.getCustomer().setIdDocNum(idx.toString());
		//JT 2017062
		if (LtsBackendConstant.ORDER_PREFIX_PREMIER_TEAM.equals(orderMode) && "R".equals(pSbOrder.getSignoffMode())) {
				pSectionA.getCustomer().setIdVerifiedInd(pSrvDtl.getAccount().getCustomer().getIdVerifiedInd());
				pSectionA.getCustomer().setSignoffMode("R");
		}
	}
	
	private void generateBillingAddress(SectionARptDTO pSectionA, ServiceDetailLtsDTO pSrvDtl, BillingAddressLtsDTO pBillingAddr, SbOrderDTO pSbOrder) {
		
		if (LtsSbHelper.isOnlineAcquistionOrder(pSbOrder)) {
			pSectionA.setBaInd(LtsBackendConstant.BILL_ADDR_ACTION_IA);
		} else {
			pSectionA.setIaInd(pSrvDtl.getErInd());
			
			if (pBillingAddr != null) {
				if (StringUtils.equals(LtsBackendConstant.BILLING_ADDR_ACTION_NEW, pBillingAddr.getAction())) {
					StringBuilder addrSb = new StringBuilder();
					if (StringUtils.isNotEmpty(pBillingAddr.getAddrLine1())) {
						addrSb.append(pBillingAddr.getAddrLine1());
						addrSb.append("\n");
					}
					if (StringUtils.isNotEmpty(pBillingAddr.getAddrLine2())) {
						addrSb.append(pBillingAddr.getAddrLine2());
						addrSb.append("\n");
					}
					if (StringUtils.isNotEmpty(pBillingAddr.getAddrLine3())) {
						addrSb.append(pBillingAddr.getAddrLine3());
						addrSb.append("\n");
					}
					if (StringUtils.isNotEmpty(pBillingAddr.getAddrLine4())) {
						addrSb.append(pBillingAddr.getAddrLine4());
						addrSb.append("\n");
					}
					if (StringUtils.isNotEmpty(pBillingAddr.getAddrLine5())) {
						addrSb.append(pBillingAddr.getAddrLine5());
						addrSb.append("\n");
					}
					if (StringUtils.isNotEmpty(pBillingAddr.getAddrLine6())) {
						addrSb.append(pBillingAddr.getAddrLine6());
					}
					pSectionA.setBillingAddress(addrSb.toString());
				}
				pSectionA.setBaInd(pBillingAddr.getAction());
				pSectionA.setInstantUpdBA(StringUtils.equals("Y", pBillingAddr.getInstantUpdateInd()));
			} else {
				pSectionA.setBaInd(StringUtils.equals("Y", pSrvDtl.getCopyErIaToBa()) ? LtsBackendConstant.BILL_ADDR_ACTION_IA : LtsBackendConstant.BILLING_ADDR_ACTION_NEW);
			}
		}
	}
	
	protected void generateSectionDReport(SectionDRptDTO pSectionD) {
		
		List<ItemDetailSummaryDTO> itemDescList = null;
		
		for (int i=0; i<SECTION_D_ITEM_TYPE.length; ++i) {
			itemDescList = this.itemDescMap.get(SECTION_D_ITEM_TYPE[i]);
			
			for (int j=0; itemDescList!=null && j<itemDescList.size(); ++j) {
				if (StringUtils.equals(LtsBackendConstant.IO_IND_INSTALL, itemDescList.get(j).getIoInd())) {
					this.addItemToSectionD(pSectionD, itemDescList.get(j));	
				}
			}
		}
	}
	
	private void appendAttributeInfo(ItemDetailSummaryDTO pItem) {
		
		ItemAttbDTO[] attbs = pItem.getItemAttbs();
		
		if (ArrayUtils.isEmpty(attbs)) {
			return;
		}
		StringBuilder sb = new StringBuilder(pItem.getItemDisplayHtml());
		
		for (int i=0; i < attbs.length; ++i) {
			sb.append("<BR>");
			sb.append(attbs[i].getAttbDesc());
			sb.append(": ");
			sb.append(attbs[i].getAttbValue());
		}
		pItem.setItemDisplayHtml(sb.toString());
	}
	
	protected void generateSectionEReport(SectionERptDTO pSectionE) {
		
		List<ItemDetailSummaryDTO> itemDescList = null;
		
		for (int i=0; i<SECTION_E_ITEM_TYPE.length; ++i) {
			itemDescList = this.itemDescMap.get(SECTION_E_ITEM_TYPE[i]);
			
			for (int j=0; itemDescList!=null && j<itemDescList.size(); ++j) {
				if (StringUtils.equals(LtsBackendConstant.IO_IND_INSTALL, itemDescList.get(j).getIoInd())) {
					this.addItemToSectionE(pSectionE, itemDescList.get(j));
				}
			}
		}
	}
	
	protected void generateSectionFReport(SectionFRptDTO pSectionF, String pLocale) {
		
		List<ItemDetailSummaryDTO> itemDescList = null;	
		WItemChargesRptTemplateVDAO result = null;
		
		for (int i=0; i<SECTION_F_ITEM_TYPE.length; ++i) {
			itemDescList = this.itemRemarkMap.get(SECTION_F_ITEM_TYPE[i]);

			for (int j=0; itemDescList!=null && j<itemDescList.size(); ++j) {
				if (!StringUtils.equals(LtsBackendConstant.IO_IND_INSTALL, itemDescList.get(j).getIoInd())) {
					continue;
				}
				result = this.getChargeDesc(itemDescList.get(j).getItemId(), itemDescList.get(j).getItemType(), pLocale);
				
				if (result != null && StringUtils.isNotEmpty(result.getCharge())) {
					this.addItemToSectionF(pSectionF, result.getCharge(), result.getChargeDesc());
				}
			}
		}		
	}
	
	private WItemChargesRptTemplateVDAO getChargeDesc(String pItemId, String pItemType, String pLocale) {
		
		WItemChargesRptTemplateVDAO[] results = null;
		
		try {
			WItemChargesRptTemplateVDAO criteria = SpringApplicationContext.getBean(WItemChargesRptTemplateVDAO.class);
			criteria.setSearchKey("itemId", pItemId);
			criteria.setSearchKey("itemType", pItemType);
			criteria.setSearchKey("lob", LtsBackendConstant.LOB_LTS);
			criteria.setSearchKey("locale", pLocale);
			results = (WItemChargesRptTemplateVDAO[])criteria.doSelect(null, null);
		} catch (Exception ex) {
			logger.error("Fail in ReportDataBaseService.getChargeDesc()", ex);
			throw new AppRuntimeException(ex);
		}
		if (ArrayUtils.isEmpty(results)) {
			return null;
		}
		return results[0];
	}
	
	protected void generateSectionJReport(SectionJRptDTO pSectionJ) {

		List<ItemDetailSummaryDTO> itemDescList = null;
		
		for (int i=0; i<SECTION_J_ITEM_TYPE.length; ++i) {
			itemDescList = this.itemRemarkMap.get(SECTION_J_ITEM_TYPE[i]);
			
			for (int j=0; itemDescList!=null && j<itemDescList.size(); ++j) {
				if (StringUtils.equals(LtsBackendConstant.IO_IND_INSTALL, itemDescList.get(j).getIoInd())) {
					this.addItemToSectionJ(pSectionJ, itemDescList.get(j));
				}
			}
		}
	}
	
	protected void generateSectionKReport(SectionKRptDTO pSectionK, SbOrderDTO pSbOrder, ServiceDetailDTO pSrvDtlLts, boolean pIsPrintSignature) {

		PaymentMethodDetailLtsDTO futurePayment = null;
		PaymentMethodDetailLtsDTO existPayment = null;
		
		if (pSrvDtlLts.getRecontract() != null) {		
			futurePayment = pSrvDtlLts.getRecontract().getFuturePayment();
			existPayment = pSrvDtlLts.getRecontract().getExistPayment();
			pSectionK.setAcctNum(pSrvDtlLts.getRecontract().getAcctNum());
		} else {
			futurePayment = pSrvDtlLts.getAccount().getFuturePayment();
			existPayment = pSrvDtlLts.getAccount().getExistPayment();
		}
		if (futurePayment == null && existPayment == null) {
			return;
		}
		if (pSrvDtlLts instanceof ServiceDetailLtsDTO) {
			pSectionK.setDeviceType(((ServiceDetailLtsDTO)pSrvDtlLts).getDeviceType());	
		}
		if (futurePayment == existPayment) {
			pSectionK.setExistPaymentMethodInd(existPayment.getPayMtdType());
		} else if (futurePayment != null) {
			pSectionK.setPaymentMethodInd(futurePayment.getPayMtdType());
			
			if (StringUtils.equals(futurePayment.getPayMtdType(), LtsBackendConstant.PAYMENT_TYPE_AUTO_PAY)) {
				pSectionK.setAcctHolderName(futurePayment.getBankAcctHoldName());
				pSectionK.setBankAcctNum(futurePayment.getBankAcctNo());
				pSectionK.setIdDocNum(futurePayment.getBankAcctHoldNum());
				pSectionK.setIdDocType(futurePayment.getBankAcctHoldType());				
				pSectionK.setBankCd(this.paymentService.getBankNameByCode(futurePayment.getBankCd()) + " (" + futurePayment.getBankCd() + ")");
				pSectionK.setBranchCd(this.paymentService.getBranchNameByCode(futurePayment.getBankCd(), futurePayment.getBranchCd()) + " (" + futurePayment.getBranchCd() + ")");
				if (pIsPrintSignature) {
					pSectionK.setAutoPayCustSignature(LtsSbHelper.getSignature(pSbOrder, LtsBackendConstant.SIGN_TYPE_BANK));
				}
			} else if (StringUtils.equals(futurePayment.getPayMtdType(), LtsBackendConstant.PAYMENT_TYPE_CREDIT_CARD)) {
				pSectionK.setCardHolderName(futurePayment.getCcHoldName());
				pSectionK.setCardNum(futurePayment.getCcNum());
				pSectionK.setCardType(futurePayment.getCcType());
				pSectionK.setCardVerifiedInd(futurePayment.getCcVerifiedInd());
				pSectionK.setExpiryDate(futurePayment.getCcExpDate());
				pSectionK.setThirdPartyIdDocNum(futurePayment.getCcIdDocNo());
				pSectionK.setThirdPartyIdDocType(futurePayment.getCcIdDocType());
				pSectionK.setThirdPartyCreditCardInd(futurePayment.getThirdPartyInd());
				if (pIsPrintSignature) {
					pSectionK.setCardHolderSignature(LtsSbHelper.getSignature(pSbOrder,LtsBackendConstant.SIGN_TYPE_CCARD));
				}
			}
		}		
		if (LtsSbHelper.isOnlineAcquistionOrder(pSbOrder)) {
			pSectionK.setOlsPrepaymentAmt(futurePayment.getPrepayAmt());
		}
		List<ItemDetailSummaryDTO> prepaymentItemList = this.itemRemarkMap.get(LtsBackendConstant.ITEM_TYPE_PREPAY);
		
		if (prepaymentItemList != null && prepaymentItemList.size() > 0) {
			pSectionK.addPrepayment(prepaymentItemList.get(0).getItemDisplayHtml());
		}
		
		if(LtsSbHelper.isNewAcquistionOrder(pSbOrder) || LtsBackendConstant.ORDER_TYPE_SB_UPGRADE.equals(pSbOrder.getOrderType()) && pSrvDtlLts.getAccount()!=null){ //JT2017
			pSectionK.setAcctNum(pSrvDtlLts.getAccount().getAcctNo());
		}
	}
	
	protected void generateSectionNReport(SectionNRptDTO pSectionN, ServiceDetailDTO pSrvDtlLst, String orderMode, boolean internalUse) {
		
		ThirdPartyDetailLtsDTO thirdPtyDtl = pSrvDtlLst.getThirdPartyDtl();
		
		if (thirdPtyDtl == null) {
			return;
		}
		pSectionN.setApplicantName((thirdPtyDtl.getTitle() + " " + thirdPtyDtl.getAppntLastName() + " " + thirdPtyDtl.getAppntFirstName()).toUpperCase());
		pSectionN.setContactNum(thirdPtyDtl.getAppntContactNum());
		pSectionN.setIdVerifiedInd(thirdPtyDtl.getAppntIdVerifiedInd());
		pSectionN.setIdDocType(thirdPtyDtl.getAppntDocType());
		pSectionN.setIdDocNum(LtsBackendConstant.ORDER_PREFIX_RETAIL.equals(orderMode)||internalUse?thirdPtyDtl.getAppntDocId():null);
		pSectionN.setRelationship(LtsSbHelper.getRelationshipDesc(thirdPtyDtl.getRelationshipCode()));
	}
	
	protected void generateSectionLReport(SectionLRptDTO pSectionL, SbOrderDTO sbOrder) {
		
		
		ServiceDetailDTO coreService = LtsSbHelper.getLtsService(sbOrder);
		CustomerDetailLtsDTO customerDetail = coreService.getAccount().getCustomer();
		
		if (customerDetail == null) {
			return;
		}

		CustOptOutInfoLtsDTO custOptOutInfo = customerDetail.getCustOptOutInfo();
		
		if (custOptOutInfo !=  null) {
			if (LtsBackendConstant.DATA_PRIVACY_OPT_IND_OOA_CD.equals(custOptOutInfo.getOptInd()) || (StringUtils.equals("Y", custOptOutInfo.getSms()) && sbOrder.getOrderType().equals(LtsBackendConstant.ORDER_TYPE_SB_ACQUISITION))) {
				pSectionL.setOptOut("Y");	
			}
			pSectionL.setUpdBomStatus(custOptOutInfo.getUpdBomStatus());
		}
		else {
			pSectionL.setOptOut("NULL");
		}
			
		pSectionL.setCspOptOut(StringUtils.equals(customerDetail.getClubOptOut(), "Y") || StringUtils.equals(customerDetail.getHktOptOut(), "Y"));	
		
		pSectionL.setCspDummy(StringUtils.contains(customerDetail.getCsPortalLogin(), LtsBackendConstant.CSP_DUMMY_EMAIL_DOMAIN)
								||StringUtils.contains(customerDetail.getTheClubLogin(), LtsBackendConstant.CSP_DUMMY_EMAIL_DOMAIN)
								||StringUtils.contains(customerDetail.getTheClubMobile(), LtsBackendConstant.CSP_DUMMY_MOBILE_NUM)
								||StringUtils.contains(customerDetail.getCsPortalMobile(), LtsBackendConstant.CSP_DUMMY_MOBILE_NUM));				
		

		if (StringUtils.equals("Y", customerDetail.getCsPortalInd()) 
				|| StringUtils.equals("Y", customerDetail.getTheClubInd())
				|| (StringUtils.equals("R", customerDetail.getTheClubInd()) && StringUtils.equals("R", customerDetail.getCsPortalInd()))) {
              pSectionL.setCsPortal(true);
        }
		else {
              pSectionL.setCsPortal(false);
        }

		pSectionL.setCustType(LtsBackendConstant.DUMMY_CUST_NO.equals(customerDetail.getCustNo()) ? LtsBackendConstant.AF_FORM_CUST_TYPE_NEW_CUST : LtsBackendConstant.AF_FORM_CUST_TYPE_EXIST_CUST);	
	}
	
	protected void setCspItemType(EyeAppFormRptDTO rptDto, SbOrderDTO sbOrder){
		if (rptDto == null || sbOrder == null ){
			return;
		}
		
		ServiceDetailLtsDTO ltsServiceDetail = LtsSbHelper.getLtsService(sbOrder);

		// online sales
		if (LtsSbHelper.isOnlineAcquistionOrder(sbOrder)) {
			if (ltsServiceDetail.getAccount() != null && ltsServiceDetail.getAccount().getCustomer() != null) {
				if (StringUtils.equals("Y", ltsServiceDetail.getAccount().getCustomer().getCsPortalInd())
						&& StringUtils.equals("Y", ltsServiceDetail.getAccount().getCustomer().getTheClubInd())) {
					rptDto.setCspItemType(LtsBackendConstant.ITEM_TYPE_HKT_THE_CLUB_BILL);
				}
				else if (StringUtils.equals("Y", ltsServiceDetail.getAccount().getCustomer().getCsPortalInd())) {
					rptDto.setCspItemType(LtsBackendConstant.ITEM_TYPE_MYHKT_BILL);
				}
				else if (StringUtils.equals("Y", ltsServiceDetail.getAccount().getCustomer().getTheClubInd())) {
					rptDto.setCspItemType(LtsBackendConstant.ITEM_TYPE_THE_CLUB_BILL);
				}
			}
			return;
		}
		
		List<String> itemTypeList = new ArrayList<String>();
		
		itemTypeList.add(LtsBackendConstant.ITEM_TYPE_MYHKT_BILL);
		itemTypeList.add(LtsBackendConstant.ITEM_TYPE_THE_CLUB_BILL);
		itemTypeList.add(LtsBackendConstant.ITEM_TYPE_HKT_THE_CLUB_BILL);
		itemTypeList.add(LtsBackendConstant.ITEM_TYPE_EXIST_MYHKT_BILL);
		
				
		ItemDetailLtsDTO billItem = null;
		
		for(String itemType : itemTypeList){
			billItem = LtsSbHelper.getServiceSubcItem((ServiceDetailLtsDTO)ltsServiceDetail, itemType);
			
			if(billItem != null){
				rptDto.setCspItemType(itemType);
				break;
			}
		}

		
		
	}
	
	protected void generateSectionPReport(SectionPRptDTO pSectionP, SbOrderDTO sbOrder) {
		
				
		boolean isHkt = StringUtils.equals(sbOrder.getAccounts()[0].getCustomer().getCsPortalInd(), "Y");
		boolean isClub = StringUtils.equals(sbOrder.getAccounts()[0].getCustomer().getTheClubInd(), "Y");
		
		ServiceDetailLtsDTO ltsService = LtsSbHelper.getLtsService(sbOrder);
		
		if(isHkt){
			pSectionP.setMobileNum(StringUtils.equals(ltsService.getAccount().getCustomer().getCsPortalMobile(),LtsBackendConstant.CSP_DUMMY_MOBILE_NUM)?
									"" : ltsService.getAccount().getCustomer().getCsPortalMobile());
			pSectionP.setEmailAddr(ltsService.getAccount().getCustomer().getCsPortalLogin());
		}else if(!isHkt && isClub){
			pSectionP.setMobileNum(StringUtils.equals(ltsService.getAccount().getCustomer().getTheClubMobile(),LtsBackendConstant.CSP_DUMMY_MOBILE_NUM)?
									"" : ltsService.getAccount().getCustomer().getTheClubMobile());
			pSectionP.setEmailAddr(ltsService.getAccount().getCustomer().getTheClubLogin());
		}
		
	}
	
	protected void generateSectionQReport(SectionQRptDTO pSectionQ, SbOrderDTO sbOrder) {
		
		if(sbOrder.getLtsDsOrderInfo()!=null && StringUtils.equals(sbOrder.getLtsDsOrderInfo().getCoolOff(), "Y")){
			pSectionQ.setCoolingOffRequired(true);
		}else{
			pSectionQ.setCoolingOffRequired(false);
		}
		if(sbOrder.getLtsDsOrderInfo()!=null && StringUtils.equals(sbOrder.getLtsDsOrderInfo().getWaiveCloff(), "Y")){
			pSectionQ.setWaiveCoolingOff(true);
		}else{
			pSectionQ.setWaiveCoolingOff(false);
		}
		
	}
	
	protected void generateSectionInternalUseReport(SectionInternalUseDTO pSectionIntUse, SbOrderDTO pSbOrder, ServiceDetailLtsDTO pSrvDtlLts) {
		
		pSectionIntUse.setContactNum(pSbOrder.getSalesContactNum());
		pSectionIntUse.setLocation(pSbOrder.getSalesTeam());
		pSectionIntUse.setSalesStaffName(pSbOrder.getSalesName());
		pSectionIntUse.setSalesmanCd(pSbOrder.getStaffNum());
		pSectionIntUse.setCmrid(pSbOrder.getSalesCd());
		pSectionIntUse.setUamsNum(pSrvDtlLts.getAppointmentDtl().getSerialNum());
		pSectionIntUse.setOrderTakedBy(pSbOrder.getCreateBy());
		pSectionIntUse.setBlacklistAddr(StringUtils.equals("Y", pSbOrder.getAddress().getBlacklistInd()));
		pSectionIntUse.setSourceCd(pSbOrder.getSalesChannel());

		
		if (StringUtils.isNotEmpty(pSrvDtlLts.getAccount().getRedemptionMedia())) {
			String redemptionMedia = null;
			if (LtsBackendConstant.REDEMPTION_MEDIA_EMAIL.equals(pSrvDtlLts.getAccount().getRedemptionMedia())) {
				redemptionMedia = "Email";
			}
			else if (LtsBackendConstant.REDEMPTION_MEDIA_SMS.equals(pSrvDtlLts.getAccount().getRedemptionMedia())) {
				redemptionMedia = "SMS";
				pSectionIntUse.setSmsNumber(pSrvDtlLts.getAccount().getRedemptionSmsNo());
			}
			else if (LtsBackendConstant.REDEMPTION_MEDIA_POSTAL.equals(pSrvDtlLts.getAccount().getRedemptionMedia())) {
				redemptionMedia = "Postal";
			}
			pSectionIntUse.setRedemptionMedia(redemptionMedia);
		}
		
//		this.setDocuments(pSectionIntUse, pSbOrder);
		
		List<ItemDetailSummaryDTO> itemDescList = null;
		
		for (int i=0; i<SECTION_INTERNAL_USE_OPT_OUT.length; ++i) {
			itemDescList = this.itemDescMap.get(SECTION_INTERNAL_USE_OPT_OUT[i]);
			
			for (int j=0; itemDescList!=null && j<itemDescList.size(); ++j) {
				pSectionIntUse.addOptOutDtl(itemDescList.get(j).getItemDisplayHtml());
			}
		}
		String penaltyHandling = null;
		
		for (int i=0; i<SECTION_INTERNAL_USE_OUT_LTS.length; ++i) {
			itemDescList = this.itemDescMap.get(SECTION_INTERNAL_USE_OUT_LTS[i]);
			
			for (int j=0; itemDescList!=null && j<itemDescList.size(); ++j) {
				if (StringUtils.equals(LtsBackendConstant.IO_IND_OUT, itemDescList.get(j).getIoInd())
						&& !itemDescList.get(j).isForceRetain()) {
					if (StringUtils.isBlank(itemDescList.get(j).getPenaltyHandling())) {
						penaltyHandling = "Free to go";
					} else {
						penaltyHandling = (String)this.penaltyHandleLtsLkupCacheService.get(itemDescList.get(j).getPenaltyHandling());
					}
					pSectionIntUse.addOutLtsTp(itemDescList.get(j).getItemDisplayHtml(), penaltyHandling);
				}
			}
		}
		
		// temp generate reminder msg
		Set<String> plan2C = this.orderDetailDao.getPlan2C();
		itemDescList = this.itemDescMap.get(LtsBackendConstant.ITEM_TYPE_PLAN);
		
		for (int i=0; itemDescList!=null && i<itemDescList.size(); ++i) {
			if (LtsBackendConstant.IO_IND_INSTALL.equals(itemDescList.get(i).getIoInd()) && plan2C.contains(itemDescList.get(i).getItemId())) {
				pSectionIntUse.addOutLtsTp("Voice package / Deluxe package (if have)", "Auto waive");
				break;
			}
		}
		
		for (int i=0; i<SECTION_INTERNAL_USE_CHARGE.length; ++i) {
			itemDescList = this.itemDescMap.get(SECTION_INTERNAL_USE_CHARGE[i]);
			
			for (int j=0; itemDescList!=null && j<itemDescList.size(); ++j) {
				pSectionIntUse.addCharging(itemDescList.get(j).getItemDisplayHtml());
			}
		}
		if (StringUtils.equals("Y", pSrvDtlLts.getDummyDocIdInd())) {
			pSectionIntUse.setIdDocNum(pSrvDtlLts.getAccount().getCustomer().getIdDocNum());
			pSectionIntUse.setIdDocType(pSrvDtlLts.getAccount().getCustomer().getIdDocType());	
		}
		
		ItemDetailLtsDTO[] subcItems =  pSrvDtlLts.getItemDtls();
			
		for (ItemDetailLtsDTO subcItem : subcItems) {
			if (LtsBackendConstant.ITEM_TYPE_PAPER_BILL.equals(subcItem.getItemType())
					|| LtsBackendConstant.ITEM_TYPE_PAPER_BILL_BR.equals(subcItem.getItemType())
					|| LtsBackendConstant.ITEM_TYPE_PAPER_BILL_GENERATE.equals(subcItem.getItemType())
					|| LtsBackendConstant.ITEM_TYPE_PAPER_BILL_WAIVE.equals(subcItem.getItemType())) {
				pSectionIntUse.setBillType("Paper Bill");
				String acctWaivePaperInd = pSrvDtlLts.getAccount().getAcctWaivePaperInd();
				pSectionIntUse.setPaperBillCharging(StringUtils.equals("W", acctWaivePaperInd) ? "Waive" : (StringUtils.equals("Y", acctWaivePaperInd) ? "Generate" : "Remain Unchange"));
				if (StringUtils.equals("W", acctWaivePaperInd)) {
					String waiveReason =  (String) waiveReasonLkupCacheService.get(pSrvDtlLts.getAccount().getWaivePaperReaCd());	
					pSectionIntUse.setWaiveReason(waiveReason);
				}
				break;
			}
			else if (LtsBackendConstant.ITEM_TYPE_EBILL.equals(subcItem.getItemType())) {
				pSectionIntUse.setBillType("eBill");
				break;
			}
		}
	}
	
//	private void setDocuments(SectionInternalUseDTO pSectionIntUse, SbOrderDTO pSbOrder) {
//		
//		AllOrdDocAssgnLtsDTO[] docs = pSbOrder.getAllOrdDocAssgns();
//		Map<String, String> faxSerialMap = ltsOrderDocumentService.getFaxSerialMap(pSbOrder.getOrderId()); 
//		
//		for (int i=0; docs!=null && i<docs.length; ++i) {
//			pSectionIntUse.addDocument((String)this.ltsDocTypeLkupCacheService.get(docs[i].getDocType()), 
//					(String)this.ltsWaiveReasonCacheService.get(docs[i].getWaiveReason()), 
//					StringUtils.isBlank(docs[i].getCollectedInd()) ? "N" : docs[i].getCollectedInd(), faxSerialMap.get(docs[i].getDocType()));
//		}
//	}
	
	protected void appendRemark(StringBuilder pRemarkSb, String pRemarkContent, String pPrefix) {

		if (StringUtils.isNotEmpty(pRemarkContent)) {
			if (StringUtils.isNotEmpty(pPrefix)) {
				pRemarkSb.append("\n");
				pRemarkSb.append(pPrefix);
				pRemarkSb.append("\n");	
			}
			pRemarkSb.append(pRemarkContent);
			pRemarkSb.append("\n");	
		}
	}

	public PaymentService getPaymentService() {
		return paymentService;
	}

	public void setPaymentService(PaymentService paymentService) {
		this.paymentService = paymentService;
	}
	
	public ReportFixedDataService getReportFixedDataService() {
		return reportFixedDataService;
	}

	public void setReportFixedDataService(
			ReportFixedDataService reportFixedDataService) {
		this.reportFixedDataService = reportFixedDataService;
	}

	public BasketDetailService getBasketDetailService() {
		return basketDetailService;
	}

	public void setBasketDetailService(BasketDetailService basketDetailService) {
		this.basketDetailService = basketDetailService;
	}

	public OfferService getOfferService() {
		return offerService;
	}

	public void setOfferService(OfferService offerService) {
		this.offerService = offerService;
	}

	public CodeLkupCacheService getPenaltyHandleLtsLkupCacheService() {
		return penaltyHandleLtsLkupCacheService;
	}

	public void setPenaltyHandleLtsLkupCacheService(
			CodeLkupCacheService penaltyHandleLtsLkupCacheService) {
		this.penaltyHandleLtsLkupCacheService = penaltyHandleLtsLkupCacheService;
	}

	public CodeLkupCacheService getErEyeHandleCacheService() {
		return erEyeHandleCacheService;
	}

	public void setErEyeHandleCacheService(
			CodeLkupCacheService erEyeHandleCacheService) {
		this.erEyeHandleCacheService = erEyeHandleCacheService;
	}

	public CodeLkupCacheService getErDelHandleCacheService() {
		return erDelHandleCacheService;
	}

	public void setErDelHandleCacheService(
			CodeLkupCacheService erDelHandleCacheService) {
		this.erDelHandleCacheService = erDelHandleCacheService;
	}

	public LtsOrderDocumentService getLtsOrderDocumentService() {
		return ltsOrderDocumentService;
	}

	public void setLtsOrderDocumentService(
			LtsOrderDocumentService ltsOrderDocumentService) {
		this.ltsOrderDocumentService = ltsOrderDocumentService;
	}

	public CodeLkupCacheService getLtsWaiveReasonCacheService() {
		return ltsWaiveReasonCacheService;
	}

	public void setLtsWaiveReasonCacheService(
			CodeLkupCacheService ltsWaiveReasonCacheService) {
		this.ltsWaiveReasonCacheService = ltsWaiveReasonCacheService;
	}

	public CodeLkupCacheService getLtsDocTypeLkupCacheService() {
		return ltsDocTypeLkupCacheService;
	}

	public void setLtsDocTypeLkupCacheService(
			CodeLkupCacheService ltsDocTypeLkupCacheService) {
		this.ltsDocTypeLkupCacheService = ltsDocTypeLkupCacheService;
	}

	public OrderDetailDAO getOrderDetailDao() {
		return orderDetailDao;
	}

	public void setOrderDetailDao(OrderDetailDAO orderDetailDao) {
		this.orderDetailDao = orderDetailDao;
	}

	public CodeLkupCacheService getWaiveReasonLkupCacheService() {
		return waiveReasonLkupCacheService;
	}

	public void setWaiveReasonLkupCacheService(
			CodeLkupCacheService waiveReasonLkupCacheService) {
		this.waiveReasonLkupCacheService = waiveReasonLkupCacheService;
	}
	
	
}
