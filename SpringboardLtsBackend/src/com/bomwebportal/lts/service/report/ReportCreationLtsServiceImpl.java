package com.bomwebportal.lts.service.report;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;

import com.bomwebportal.dto.LookupItemDTO;
import com.bomwebportal.dto.report.ReportOutputDTO;
import com.bomwebportal.dto.report.ReportSetDTO;
import com.bomwebportal.dto.report.ReportSetDetailDTO;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.lts.dao.OfferDetailDAO;
import com.bomwebportal.lts.dto.order.AllOrdDocDTO;
import com.bomwebportal.lts.dto.order.AllOrdDocLtsDTO;
import com.bomwebportal.lts.dto.order.ItemDetailLtsDTO;
import com.bomwebportal.lts.dto.order.SbOrderDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailLtsDTO;
import com.bomwebportal.lts.service.LtsOrderDocumentService;
import com.bomwebportal.lts.util.LtsBackendConstant;
import com.bomwebportal.lts.util.LtsSbHelper;
import com.bomwebportal.service.CodeLkupCacheService;
import com.bomwebportal.service.ReportCreationServiceImpl;
import com.bomwebportal.service.ServiceActionBase;
import com.bomwebportal.service.ServiceActionImplBase;
import com.bomwebportal.util.FastByteArrayOutputStream;
import com.pccw.util.spring.SpringApplicationContext;

public class ReportCreationLtsServiceImpl extends ReportCreationServiceImpl implements ReportCreationLtsService {

	private OfferDetailDAO offerDetailDao = null;
	private ServiceActionImplBase allOrdDocLtsService;
	private LtsOrderDocumentService ltsOrderDocumentService;
	private CodeLkupCacheService cslSimItemLkupCacheService;
	private CodeLkupCacheService concertTicketItemLkupCacheService;
	private String storePathPrefix = null;
	
	public ReportOutputDTO generateReportWithWaterMark(ReportSetDTO pRptSet, Map<String,Object> pParmMap, String pWaterMark) {
		SbOrderDTO sbOrder = (SbOrderDTO)pParmMap.get(LtsBackendConstant.RPT_KEY_SBORDER);
		try {
			if (ReportCreationLtsService.RPT_SET_SUPPORT_DOC.equals(pRptSet.getRptSet())) {
				fillReportName(pRptSet, pParmMap);
				return saveAllSupportDoc(pRptSet, pParmMap);	
			}
			return super.generateReportWithWaterMark(pRptSet, pParmMap, pWaterMark);
		}
		catch (Exception e) {
			if (sbOrder != null) {
				logger.error("Exception in generateReportWithWaterMark: ["+sbOrder.getOrderId() + "] [" +pRptSet.getRptSet()+ "]" + ExceptionUtils.getFullStackTrace(e));	
			}
			throw new AppRuntimeException(e) ;
		}
	}
	
	public ReportOutputDTO generateReport(ReportSetDTO pRptSet, Map<String,Object> pParmMap) {
		SbOrderDTO sbOrder = (SbOrderDTO)pParmMap.get(LtsBackendConstant.RPT_KEY_SBORDER);
		try {
			if (ReportCreationLtsService.RPT_SET_SUPPORT_DOC.equals(pRptSet.getRptSet())) {
				fillReportName(pRptSet, pParmMap);
				return saveAllSupportDoc(pRptSet, pParmMap);	
			}
			return super.generateReport(pRptSet, pParmMap);	
		}
		catch (Exception e) {
			if (sbOrder != null) {
				logger.error("Exception in generateReportWithWaterMark: ["+sbOrder.getOrderId() + "] [" +pRptSet.getRptSet()+ "]" + ExceptionUtils.getFullStackTrace(e));	
			}
			throw new AppRuntimeException(e) ;
		}
	}
	
	public ReportOutputDTO getSavedReport(ReportSetDTO pRptSet, Map<String,Object> pParmMap) {
		
		SbOrderDTO sbOrder = (SbOrderDTO)pParmMap.get(LtsBackendConstant.RPT_KEY_SBORDER);
		
		if ("NSD_AF".equals(pRptSet.getRptSet())) {
			List<AllOrdDocDTO> nsdDocList = ltsOrderDocumentService.getCollectedOrdDocListByOrderIdAndDocType(sbOrder.getOrderId(), LtsBackendConstant.ORDER_DOC_TYPE_NSD);
			if (nsdDocList == null || nsdDocList.isEmpty()) {
				return super.generateReport(pRptSet, pParmMap);
			}else{
				fillReportName(pRptSet, pParmMap);
				return getLatestSupportDoc(pRptSet, pParmMap);	
			}
		}else{
			return null;
		}
		
	}
	
	public ReportOutputDTO getLatestSupportDoc(ReportSetDTO pReportSet, Map<String,Object> pParmMap) {
		
		SbOrderDTO sbOrder = (SbOrderDTO)pParmMap.get(LtsBackendConstant.RPT_KEY_SBORDER);
		List<AllOrdDocDTO> supportDocList = ltsOrderDocumentService.getLatestCollectedOrdDocListByOrderIdAndDocType(sbOrder.getOrderId(), LtsBackendConstant.ORDER_DOC_TYPE_NSD);
		
		if (supportDocList == null || supportDocList.isEmpty()) {
			return null;	
		}
		
		List<FastByteArrayOutputStream> outputStreamList = new ArrayList<FastByteArrayOutputStream>();
		String storePath = pReportSet.getSrcStorePath();
		String storeFileName = pReportSet.getSrcFileName();
		for (AllOrdDocDTO allOrdDoc : supportDocList) {

			if (StringUtils.isBlank(allOrdDoc.getFilePathName())) {
				continue;
			}
			
			ReportOutputDTO supportDoc = this.openOutputFile(storePath, allOrdDoc.getFilePathName());
			
			if (supportDoc == null) {
				continue;
			}
			outputStreamList.add(supportDoc.getOutputFileStream());
		}
		
		if (outputStreamList.isEmpty()) {
			return null;
		}
		
		InputStream[] inputStreams = new InputStream[outputStreamList.size()];
		for (int i=0; i<outputStreamList.size(); ++i) {
			inputStreams[i] = outputStreamList.get(i).getInputStream();
		}
		FastByteArrayOutputStream outputStream = this.concatPdf(inputStreams, false, true, null, null, null);
		
		try {
			ReportOutputDTO rptOut = new ReportOutputDTO();
			rptOut.setFileStoragePath(this.saveOutputFile(outputStream.getByteArray(), storePath, storeFileName));
			rptOut.setOutputFileStream(outputStream);
			return rptOut;
		} catch (Exception ex) {
			logger.error("Fail to create report " +  storeFileName + ": " + ExceptionUtils.getFullStackTrace(ex));
			throw new AppRuntimeException("Fail to create report " +  storeFileName , ex);
		}
	}
	
	public ReportOutputDTO getSupportDoc(ReportSetDTO pReportSet, Map<String,Object> pParmMap) {
		
		SbOrderDTO sbOrder = (SbOrderDTO)pParmMap.get(LtsBackendConstant.RPT_KEY_SBORDER);
		List<AllOrdDocDTO> supportDocList = ltsOrderDocumentService.getCollectedOrdDocListByOrderIdAndDocType(sbOrder.getOrderId(), LtsBackendConstant.ORDER_DOC_TYPE_NSD);
		
		if (supportDocList == null || supportDocList.isEmpty()) {
			return null;	
		}
		
		List<FastByteArrayOutputStream> outputStreamList = new ArrayList<FastByteArrayOutputStream>();
		String storePath = pReportSet.getSrcStorePath();
		String storeFileName = pReportSet.getSrcFileName();
		for (AllOrdDocDTO allOrdDoc : supportDocList) {

			if (StringUtils.isBlank(allOrdDoc.getFilePathName())) {
				continue;
			}
			
			ReportOutputDTO supportDoc = this.openOutputFile(storePath, allOrdDoc.getFilePathName());
			
			if (supportDoc == null) {
				continue;
			}
			outputStreamList.add(supportDoc.getOutputFileStream());
		}
		
		if (outputStreamList.isEmpty()) {
			return null;
		}
		
		InputStream[] inputStreams = new InputStream[outputStreamList.size()];
		for (int i=0; i<outputStreamList.size(); ++i) {
			inputStreams[i] = outputStreamList.get(i).getInputStream();
		}
		FastByteArrayOutputStream outputStream = this.concatPdf(inputStreams, false, true, null, null, null);
		
		try {
			ReportOutputDTO rptOut = new ReportOutputDTO();
			rptOut.setFileStoragePath(this.saveOutputFile(outputStream.getByteArray(), storePath, storeFileName));
			rptOut.setOutputFileStream(outputStream);
			return rptOut;
		} catch (Exception ex) {
			logger.error("Fail to create report " +  storeFileName + ": " + ExceptionUtils.getFullStackTrace(ex));
			throw new AppRuntimeException("Fail to create report " +  storeFileName , ex);
		}
	}
	
	protected void fillReportName(ReportSetDTO pReportSet, Map<String, Object> pParmMap) {

		String storePathPattern = pReportSet.getStorePath();
		String fileName = pReportSet.getFileName();
		String srcStorePathPattern = pReportSet.getSrcStorePath();
		String srcFileName = pReportSet.getSrcFileName();
		SbOrderDTO sbOrder = (SbOrderDTO)pParmMap.get(LtsBackendConstant.RPT_KEY_SBORDER);
		String seq =  pParmMap.get(LtsBackendConstant.FILE_SEQ)!= null? pParmMap.get(LtsBackendConstant.FILE_SEQ).toString() : null;
				
		storePathPattern = StringUtils.replace(storePathPattern, PATH_PATTERN_PREFIX, this.storePathPrefix);
		storePathPattern = StringUtils.replace(storePathPattern, PATH_PATTERN_ORDER_ID, sbOrder.getOrderId());
		srcStorePathPattern = StringUtils.replace(srcStorePathPattern, PATH_PATTERN_PREFIX, this.storePathPrefix);
		srcStorePathPattern = StringUtils.replace(srcStorePathPattern, PATH_PATTERN_ORDER_ID, sbOrder.getOrderId());
		fileName = StringUtils.replace(fileName, PATH_PATTERN_ORDER_ID, sbOrder.getOrderId());
		fileName = StringUtils.replace(fileName, PATH_PATTERN_LANG, sbOrder.getEsigEmailLang());
		fileName = StringUtils.replace(fileName, PATH_PATTERN_SEQ, seq);
		srcFileName = StringUtils.replace(srcFileName, PATH_PATTERN_ORDER_ID, sbOrder.getOrderId());
		srcFileName = StringUtils.replace(srcFileName, PATH_PATTERN_LANG, sbOrder.getEsigEmailLang());
		srcFileName = StringUtils.replace(srcFileName, PATH_PATTERN_SEQ, seq);
		pReportSet.setStorePath(storePathPattern);
		pReportSet.setFileName(fileName);
		pReportSet.setSrcStorePath(srcStorePathPattern);
		pReportSet.setSrcFileName(srcFileName);
		
		if (StringUtils.equals(LtsBackendConstant.DISTRIBUTE_MODE_ESIGNATURE, sbOrder.getDisMode())) {
			pReportSet.setCopies(COPY_INTERNAL_ONLY);
		}
	}

	protected void fillReportName(ReportSetDetailDTO pReportDtl, Map<String, Object> pParmMap) {

		String storePathPattern = pReportDtl.getStorePath();
		String fileName = pReportDtl.getFileName();
		String srcStorePathPattern = pReportDtl.getSrcStorePath();
		String srcFileName = pReportDtl.getSrcFileName();
		SbOrderDTO sbOrder = (SbOrderDTO)pParmMap.get(LtsBackendConstant.RPT_KEY_SBORDER);
		String lang = pReportDtl.getLang();
		String seq =  pParmMap.get(LtsBackendConstant.FILE_SEQ)!= null? pParmMap.get(LtsBackendConstant.FILE_SEQ).toString() : null;
		String rptName = pParmMap.get(LtsBackendConstant.RPT_KEY_NAME) != null ? pParmMap.get(LtsBackendConstant.RPT_KEY_NAME).toString() : null;
		if (StringUtils.equals(LANG_ORDER, lang)) {
			lang = sbOrder.getEsigEmailLang();
		}
		storePathPattern = StringUtils.replace(storePathPattern, PATH_PATTERN_PREFIX, this.storePathPrefix);
		storePathPattern = StringUtils.replace(storePathPattern, PATH_PATTERN_ORDER_ID, sbOrder.getOrderId());
		srcStorePathPattern = StringUtils.replace(srcStorePathPattern, PATH_PATTERN_PREFIX, this.storePathPrefix);
		srcStorePathPattern = StringUtils.replace(srcStorePathPattern, PATH_PATTERN_ORDER_ID, sbOrder.getOrderId());
		fileName = StringUtils.replace(fileName, PATH_PATTERN_ORDER_ID, sbOrder.getOrderId());
		fileName = StringUtils.replace(fileName, PATH_PATTERN_LANG, lang);
		fileName = StringUtils.replace(fileName, PATH_PATTERN_SEQ, seq);
		srcFileName = StringUtils.replace(srcFileName, PATH_PATTERN_ORDER_ID, sbOrder.getOrderId());
		srcFileName = StringUtils.replace(srcFileName, PATH_PATTERN_LANG, lang);
		srcFileName = StringUtils.replace(srcFileName, PATH_PATTERN_SEQ, seq);
		pReportDtl.setStorePath(storePathPattern);
		pReportDtl.setFileName(fileName);
		pReportDtl.setSrcStorePath(srcStorePathPattern);
		pReportDtl.setSrcFileName(srcFileName);
		
		if (StringUtils.equals(LtsBackendConstant.RPT_SRV_TYPE_EYE_SPEC, pReportDtl.getRptType())) {
			pReportDtl.setRptName(StringUtils.replace(pReportDtl.getRptName(), PATH_PATTERN_PROD_SPEC, getProductSpecReportName(sbOrder)));
		}
		// TODO ITEM_SPEC
		if (StringUtils.equals(LtsBackendConstant.RPT_SRV_TYPE_ITEM_SPEC, pReportDtl.getRptType())) {
			pReportDtl.setRptName(rptName);
		}
		
		pParmMap.put(LtsBackendConstant.RPT_KEY_DTL_TYPE, pReportDtl.getRptType());
		if (!StringUtils.equals("Y", (String)pParmMap.get(LtsBackendConstant.RPT_KEY_EDIT_BUTTON))) {
			pParmMap.put(LtsBackendConstant.RPT_KEY_EDIT_BUTTON, pReportDtl.getIsEditable());	
		}
		//JT2017062
		if (StringUtils.equals("PRINT_AF", (String)pParmMap.get("ACTION")) && LtsBackendConstant.SIGNOFF_MODE_RETAIL.equals(sbOrder.getSignoffMode())) {
			pParmMap.put(LtsBackendConstant.RPT_KEY_ERASE_SIGNATURE, "N");
		}
		
		else {
			pParmMap.put(LtsBackendConstant.RPT_KEY_ERASE_SIGNATURE, pReportDtl.getIsPrintSignature());
		}
		//JT2017062
		pParmMap.put(LtsBackendConstant.RPT_KEY_PRINT_TERMS, pReportDtl.getIsPrintTerms());
		pParmMap.put(PARM_LANG, LANG_ENG.equals(lang) ? LtsBackendConstant.LOCALE_ENG : LtsBackendConstant.LOCALE_CHI);
	}
	
	private String[] getItemSpecReportName(SbOrderDTO sbOrder) {
		ServiceDetailLtsDTO coreService = LtsSbHelper.getLtsService(sbOrder);
		
		ItemDetailLtsDTO[] itemDetails =  coreService.getItemDtls();
		
		List<String> itemIdList = new ArrayList<String>();
		
		if (ArrayUtils.isNotEmpty(itemDetails)) {
			for (ItemDetailLtsDTO itemDetail : itemDetails) {
				itemIdList.add(itemDetail.getItemId());
			}
		}
		
		if (itemIdList.isEmpty()) {
			return null;
		}
		
		try {
			List<String> rptNameList = this.offerDetailDao.getItemSpecPath(itemIdList);
			if (rptNameList != null && !rptNameList.isEmpty()) {
				return rptNameList.toArray(new String[rptNameList.size()]);
			}
		} catch (DAOException ex) {
			throw new AppRuntimeException(ex.getMessage(), ex);
		}
		return null;
	}
	
	private String getProductSpecReportName(SbOrderDTO sbOrder) {

		// TODO ITEM_SPEC
//		ServiceDetailLtsDTO coreService = LtsSbHelper.getLtsService(sbOrder);
//		
//		ItemDetailLtsDTO[] itemDetails =  coreService.getItemDtls();
//		
//		List<String> itemIdList = new ArrayList<String>();
//		
//		if (ArrayUtils.isNotEmpty(itemDetails)) {
//			for (ItemDetailLtsDTO itemDetail : itemDetails) {
//				itemIdList.add(itemDetail.getItemId());
//			}
//		}
		
		
		try {
			ItemDetailLtsDTO planItem = LtsSbHelper.getPlanItem(LtsSbHelper.getLtsService(sbOrder));
			List<String> rptNameList = this.offerDetailDao.getBasketProductSpecPath(planItem.getBasketId());
			if (rptNameList != null && !rptNameList.isEmpty()) {
				return rptNameList.get(0);
			}
		} catch (DAOException ex) {
			throw new AppRuntimeException(ex.getMessage(), ex);
		}
		return null;
	}
	
	private String getSmartWrtyReportName(SbOrderDTO sbOrder){
		try {
			List<String> rptNameList = this.offerDetailDao.getSmartWarrantyPath(sbOrder.getOrderId());
			if (rptNameList != null && !rptNameList.isEmpty()) {
				return rptNameList.get(0);
			}
		} catch (DAOException ex) {
			throw new AppRuntimeException(ex.getMessage(), ex);
		}
		return null;
	}
	
	private boolean isCslSimExist(SbOrderDTO sbOrder){
		try {		
			ItemDetailLtsDTO[] itemList = LtsSbHelper.getLtsService(sbOrder).getItemDtls();
			LookupItemDTO[]  cslSimItemList = cslSimItemLkupCacheService.getCodeLkupDAO().getCodeLkup();
			
			for(ItemDetailLtsDTO itemDtl : itemList){
				for(LookupItemDTO cslSimItem : cslSimItemList){
					if(StringUtils.equals(itemDtl.getItemId(),cslSimItem.getItemKey())){
						return true;
					}
					
				}
			}
		}catch (DAOException ex) {
			throw new AppRuntimeException(ex.getMessage(), ex);
		}
		
		return false;
	}
	
	private boolean isConcertTicketExist(SbOrderDTO sbOrder){
		try {		
			ItemDetailLtsDTO[] itemList = LtsSbHelper.getLtsService(sbOrder).getItemDtls();
			LookupItemDTO[]  concertTicketItemList = concertTicketItemLkupCacheService.getCodeLkupDAO().getCodeLkup();
			
			for(ItemDetailLtsDTO itemDtl : itemList){
				for(LookupItemDTO concertTicketItem : concertTicketItemList){
					if(StringUtils.equals(itemDtl.getItemId(),concertTicketItem.getItemKey())){
						return true;
					}
					
				}
			}
		}catch (DAOException ex) {
			throw new AppRuntimeException(ex.getMessage(), ex);
		}
		
		return false;
	}
	
	private boolean isWeeeExist(SbOrderDTO sbOrder){
		
		try {		
			ItemDetailLtsDTO[] items = LtsSbHelper.getLtsService(sbOrder).getItemDtls();

			if (ArrayUtils.isEmpty(items)) {
				return false;
			}
			
			for(ItemDetailLtsDTO itemDtl : items){

				if(LtsBackendConstant.ITEM_TYPE_EPD_ACCEPT.equals(itemDtl.getItemType()) 
						|| LtsBackendConstant.ITEM_TYPE_EPD_FORFEIT.equals(itemDtl.getItemType()) 
						|| LtsBackendConstant.ITEM_TYPE_EPD_UNDER_CONSIDERATION.equals(itemDtl.getItemType())){
					return true;
				}	
			}
		}catch (Exception ex) {
			throw new AppRuntimeException(ex.getMessage(), ex);
		}
		
		return false;
	}
	
	protected String[] getReportNameRequired(ReportSetDetailDTO pReportDtl, Map<String,Object> pParmMap) {
		SbOrderDTO sbOrder = (SbOrderDTO)pParmMap.get(LtsBackendConstant.RPT_KEY_SBORDER);
		String reportType = pReportDtl.getRptType();
		if (StringUtils.equals(LtsBackendConstant.RPT_SRV_TYPE_ITEM_SPEC, reportType)) {
			return getItemSpecReportName(sbOrder);
		}
		return new String[] { pReportDtl.getRptName() };
	}
	
	protected boolean isReportRequired(ReportSetDetailDTO pReportDtl, Map<String,Object> pParmMap) {
		
		SbOrderDTO sbOrder = (SbOrderDTO)pParmMap.get(LtsBackendConstant.RPT_KEY_SBORDER);
		ServiceDetailDTO pipbService = LtsSbHelper.getAcqPipbService(sbOrder.getSrvDtls());
		String reportType = pReportDtl.getRptType();
		
		if (StringUtils.equals(LtsBackendConstant.RPT_SRV_TYPE_TDO_EYE, reportType)) {
			return LtsSbHelper.getLtsEyeService(sbOrder) != null;
		} else if (StringUtils.equals(LtsBackendConstant.RPT_SRV_TYPE_TDO_DEL, reportType)) {
			return (LtsSbHelper.getDelServices(sbOrder) != null || LtsSbHelper.get2ndDelService(sbOrder) != null);
		} else if (StringUtils.equals(LtsBackendConstant.RPT_SRV_TYPE_PORT_IN, reportType)) {
			return LtsSbHelper.isPortInOrder(sbOrder);
		
			// TODO ITEM_SPEC
		
		} else if (StringUtils.equals(LtsBackendConstant.RPT_SRV_TYPE_ITEM_SPEC, reportType)) {
			return LtsSbHelper.getLtsService(sbOrder) != null && ArrayUtils.isNotEmpty(getItemSpecReportName(sbOrder));
		} else if (StringUtils.equals(LtsBackendConstant.RPT_SRV_TYPE_EYE_SPEC, reportType)) {
			return LtsSbHelper.getLtsEyeService(sbOrder) != null 
				&& StringUtils.isNotEmpty(getProductSpecReportName(sbOrder));
		} else if (StringUtils.equals(LtsBackendConstant.RPT_SRV_TYPE_SMART_WARRANTY, reportType)){
            return StringUtils.isNotEmpty(getSmartWrtyReportName(sbOrder));
		} else if (StringUtils.equals(LtsBackendConstant.RPT_SRV_TYPE_CSL_SIM, reportType)){
            return isCslSimExist(sbOrder);
		} else if (StringUtils.equals(LtsBackendConstant.RPT_SRV_TYPE_CONCERT_TICKET, reportType)){
            return isConcertTicketExist(sbOrder);
		} else if (StringUtils.equals(LtsBackendConstant.RPT_SRV_TYPE_WEEE, reportType)){
            return isWeeeExist(sbOrder);
		} else if (StringUtils.equals(LtsBackendConstant.RPT_SRV_TYPE_RECONTRACT, reportType)) {
			return (LtsSbHelper.isRecontractOrder(sbOrder) && LtsBackendConstant.SIGNOFF_MODE_RETAIL.equals(sbOrder.getSignoffMode())
					&& StringUtils.isEmpty(LtsSbHelper.getLtsService(sbOrder).getUpdateDnProfile()))
					||(pipbService != null && StringUtils.equals(pipbService.getPipb().getFromDiffCustInd(), "Y"));
//		} else if (StringUtils.equals(LtsBackendConstant.RPT_SRV_TYPE_RECONTRACT_TERMS, reportType)) {
//			return LtsSbHelper.isRecontractOrder(sbOrder)||(pipbService != null && StringUtils.equals(pipbService.getPipb().getFromDiffCustInd(), "Y"));
//		} else if (StringUtils.equals(LtsBackendConstant.RPT_SRV_TYPE_RECONTRACT_PIPB, reportType)) {
//			return StringUtils.equals(pipbService.getPipb().getFromDiffCustInd(), "Y");
		} else if (StringUtils.equals(LtsBackendConstant.RPT_SRV_TYPE_NSD_PIPB, reportType)) {
			return pipbService != null;		
		} else if (StringUtils.equals(LtsBackendConstant.RPT_SRV_TYPE_CRF_PIPB, reportType)) {
			return pipbService != null;	
		} else if (StringUtils.equals(LtsBackendConstant.RPT_SRV_TYPE_EYE, reportType)) {
			return LtsSbHelper.getLtsEyeService(sbOrder) != null;
		} else if (StringUtils.equals(LtsBackendConstant.RPT_SRV_TYPE_DEL, reportType)) {
			return LtsSbHelper.getDelServices(sbOrder) != null;
		} else if (StringUtils.equals(LtsBackendConstant.RPT_SRV_TYPE_2ND_DEL, reportType)) {
			return LtsSbHelper.get2ndDelServices(sbOrder.getSrvDtls()) != null;
		} else if (StringUtils.equals(LtsBackendConstant.RPT_SRV_TYPE_AMEND, reportType)) {
			return true;
		} else if (StringUtils.equals(LtsBackendConstant.RPT_SRV_TYPE_AMEND_FORM, reportType)) {
			return true;
		} else if (StringUtils.equals(LtsBackendConstant.RPT_SRV_TYPE_PAYMENT_SLIP, reportType)) {
			return true;
		} else if (StringUtils.equals(LtsBackendConstant.RPT_SRV_TYPE_IGUARD_CARECASH, reportType)) {   //MB2016081 TC
			if(LtsSbHelper.getIguardCareCashService(sbOrder)!= null){
				if (StringUtils.isNotBlank(LtsSbHelper.getIguardCareCashService(sbOrder).getCarecashInd())){
					return "I".equals(LtsSbHelper.getIguardCareCashService(sbOrder).getCarecashInd());
				}
			}
		} else if (StringUtils.equals(LtsBackendConstant.RPT_SRV_TYPE_IGUARD_PICS, reportType)) {   //MB2016081 TC
			if(LtsSbHelper.getIguardCareCashService(sbOrder)!= null){
				if (StringUtils.isNotBlank(LtsSbHelper.getIguardCareCashService(sbOrder).getCarecashInd())){
					return "I".equals(LtsSbHelper.getIguardCareCashService(sbOrder).getCarecashInd());
				}
			}
		}				
		return false;
	}
	
	public void saveReportDocFile(ReportSetDTO pReportSet, String pOrderId, int pSeq, byte[] pFileBytes, String pUser) {
		
		String storePathPattern = pReportSet.getStorePath();
		String fileName = pReportSet.getFileName();
		storePathPattern = StringUtils.replace(storePathPattern, PATH_PATTERN_PREFIX, this.storePathPrefix);
		storePathPattern = StringUtils.replace(storePathPattern, PATH_PATTERN_ORDER_ID, pOrderId);
		fileName = StringUtils.replace(fileName, PATH_PATTERN_ORDER_ID, pOrderId);
		fileName = StringUtils.replace(fileName, PATH_PATTERN_SEQ, String.valueOf(pSeq));
		
		try {
			this.saveOutputFile(pFileBytes, storePathPattern, fileName);
		} catch (Exception ex) {
			logger.error("Fail to saveReportDocFile " +  fileName + ": " + ExceptionUtils.getFullStackTrace(ex));
			throw new AppRuntimeException("Fail to saveReportDocFile " +  fileName , ex);
		}
		AllOrdDocLtsDTO doc = new AllOrdDocLtsDTO();
		doc.setOrderId(pOrderId);
		doc.setDocType(LtsBackendConstant.ORDER_DOC_TYPE_ORDER_AMEND_FORM);
		doc.setSeqNum(String.valueOf(pSeq));
		ServiceActionBase allOrdDocLtsService = SpringApplicationContext.getBean("allOrdDocLtsService");
		allOrdDocLtsService.doSave(doc, pUser, new Object());
	}

	public ReportOutputDTO saveAllSupportDoc(ReportSetDTO pReportSet, Map<String,Object> pParmMap) {
		
		SbOrderDTO sbOrder = (SbOrderDTO)pParmMap.get(LtsBackendConstant.RPT_KEY_SBORDER);
		List<AllOrdDocDTO> allOrdDocList = ltsOrderDocumentService.getCollectedOrdDocListByOrderId(sbOrder.getOrderId());
		
		if (allOrdDocList == null || allOrdDocList.isEmpty()) {
			return null;	
		}
		
		logger.info("saveAllSupportDoc - filteredDocList [STARTS]");
		List<AllOrdDocDTO> filteredDocList = new ArrayList<AllOrdDocDTO>();
		String requiredDocNameList = (String)pParmMap.get(LtsBackendConstant.RPT_TYPE_SUPPORT_DOC);
		if(StringUtils.isNotBlank(requiredDocNameList)){
			String[] requiredDocNames = requiredDocNameList.split(",");
			for(int i=0; requiredDocNames!=null && i<requiredDocNames.length; i++){
				for(AllOrdDocDTO doc : allOrdDocList){
					if(doc != null && StringUtils.equals(doc.getDocType(), requiredDocNames[i])){
						filteredDocList.add(doc);
					}
				}
			}
			allOrdDocList = filteredDocList;
			logger.info("saveAllSupportDoc - filteredDocList: Document list has been filtered");
		}
		logger.info("saveAllSupportDoc - filteredDocList [ENDS]");
		
		List<FastByteArrayOutputStream> outputStreamList = new ArrayList<FastByteArrayOutputStream>();
		String storePath = pReportSet.getSrcStorePath();
		String storeFileName = pReportSet.getSrcFileName();
		ServiceDetailDTO ltsService = LtsSbHelper.getLtsService(sbOrder);
		for (AllOrdDocDTO allOrdDoc : allOrdDocList) {
			
			// PIPB 0012320 
			// For PIPB order, skip NSD form in full WQ (i.s. SELECT * FROM BOMWEB_ALL_DOC WHERE doc_type = 'L009')
			if ((ltsService != null && ltsService.getPipb() != null && LtsBackendConstant.ORDER_DOC_TYPE_NSD.equals(allOrdDoc.getDocType()))
					|| StringUtils.isBlank(allOrdDoc.getFilePathName())) {
				continue;
			}
			
			ReportOutputDTO supportDoc = this.openOutputFile(storePath, allOrdDoc.getFilePathName());
			
			if (supportDoc == null) {
				continue;
			}
			outputStreamList.add(supportDoc.getOutputFileStream());
		}
		
		if (outputStreamList.isEmpty()) {
			return null;
		}
		
		InputStream[] inputStreams = new InputStream[outputStreamList.size()];
		for (int i=0; i<outputStreamList.size(); ++i) {
			inputStreams[i] = outputStreamList.get(i).getInputStream();
		}
		FastByteArrayOutputStream outputStream = this.concatPdf(inputStreams, false, true, null, null, null);
		
		try {
			ReportOutputDTO rptOut = new ReportOutputDTO();
			rptOut.setFileStoragePath(this.saveOutputFile(outputStream.getByteArray(), storePath, storeFileName));
			rptOut.setOutputFileStream(outputStream);
			return rptOut;
		} catch (Exception ex) {
			logger.error("Fail to create report " +  storeFileName + ": " + ExceptionUtils.getFullStackTrace(ex));
			throw new AppRuntimeException("Fail to create report " +  storeFileName , ex);
		}
	}
	
	
	protected String generateEncryptionPassword(ReportSetDTO pReportDtl, Map<String, Object> pParmMap) {
		return LtsSbHelper.getEncryptFilePassword((SbOrderDTO)pParmMap.get(LtsBackendConstant.RPT_KEY_SBORDER));
	}

	public OfferDetailDAO getOfferDetailDao() {
		return offerDetailDao;
	}

	public void setOfferDetailDao(OfferDetailDAO offerDetailDao) {
		this.offerDetailDao = offerDetailDao;
	}

	public String getStorePathPrefix() {
		return storePathPrefix;
	}

	public void setStorePathPrefix(String storePathPrefix) {
		this.storePathPrefix = storePathPrefix;
	}

	public ServiceActionImplBase getAllOrdDocLtsService() {
		return allOrdDocLtsService;
	}

	public void setAllOrdDocLtsService(ServiceActionImplBase allOrdDocLtsService) {
		this.allOrdDocLtsService = allOrdDocLtsService;
	}

	public LtsOrderDocumentService getLtsOrderDocumentService() {
		return ltsOrderDocumentService;
	}

	public void setLtsOrderDocumentService(
			LtsOrderDocumentService ltsOrderDocumentService) {
		this.ltsOrderDocumentService = ltsOrderDocumentService;
	}

	public CodeLkupCacheService getCslSimItemLkupCacheService() {
		return cslSimItemLkupCacheService;
	}

	public void setCslSimItemLkupCacheService(CodeLkupCacheService cslSimItemLkupCacheService) {
		this.cslSimItemLkupCacheService = cslSimItemLkupCacheService;
	}

	public CodeLkupCacheService getConcertTicketItemLkupCacheService() {
		return concertTicketItemLkupCacheService;
	}

	public void setConcertTicketItemLkupCacheService(
			CodeLkupCacheService concertTicketItemLkupCacheService) {
		this.concertTicketItemLkupCacheService = concertTicketItemLkupCacheService;
	}
	
	
	
}
