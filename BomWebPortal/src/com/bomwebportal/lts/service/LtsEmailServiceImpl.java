package com.bomwebportal.lts.service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.mail.MailSendException;
import org.w3c.dom.Comment;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

import com.bomwebportal.configuration.BomPropertyPlaceholderConfigurer;
import com.bomwebportal.dto.OrdEmailReqDTO;
import com.bomwebportal.dto.OrderDTO.DisMode;
import com.bomwebportal.dto.OrderDTO.EsigEmailLang;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.lts.dao.LtsEmailDAO;
import com.bomwebportal.lts.dao.order.FormPrintReqDAO;
import com.bomwebportal.lts.dto.OrderSendRequestDTO;
import com.bomwebportal.lts.dto.order.CustomerDetailLtsDTO;
import com.bomwebportal.lts.dto.order.ItemDetailLtsDTO;
import com.bomwebportal.lts.dto.order.SbOrderDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailLtsDTO;
import com.bomwebportal.lts.dto.profile.AccountDetailProfileLtsDTO;
import com.bomwebportal.lts.service.bom.CustomerProfileLtsService;
import com.bomwebportal.lts.util.LtsConstant;
import com.bomwebportal.lts.util.LtsSbHelper;
import com.bomwebportal.lts.util.LtsSbOrderHelper;
import com.bomwebportal.service.CodeLkupCacheService;
import com.bomwebportal.service.OrdEmailReqService;
import com.bomwebportal.service.OrderEsignatureService;
import com.bomwebportal.service.OrderEsignatureService.EmailReqResult;
import com.bomwebportal.util.BomWebPortalConstant;
import com.bomwebportal.util.FastByteArrayOutputStream;
import com.bomwebportal.util.PdfUtil;

public class LtsEmailServiceImpl implements LtsEmailService {
	
	private OrderEsignatureService orderEsignatureService;
	private OrdEmailReqService ordEmailReqService;
	private LtsEmailDAO ltsEmailDao;
	private FormPrintReqDAO formPrintReqDao;
	private CustomerProfileLtsService customerProfileLtsService;
	private CodeLkupCacheService ltsSendDocLkupCacheService;
	private CodeLkupCacheService ltsDOrderEyeDescLkupCacheService;
	private CodeLkupCacheService ltsMCodeLkupCacheService;
	
	private String carecashAdminEmail;
	private BomPropertyPlaceholderConfigurer propertyConfigurer;
	
//	private static final String EMAIL_MESSAGE_FAIL = "Fail to send email";
//	private static final String EMAIL_MESSAGE_INVALID_EMAIL_ADDR = "Fail to send email due to invalid email address";
//	private static final String EMAIL_MESSAGE_INVALID_ENCRYPT_PASSWORD = "Fail to send email due to encrypt password";
//	private static final String EMAIL_MESSAGE_ATTACHMENT_NOT_FOUND = "Fail to send email due to attachment not found";
//	private static final String EMAIL_MESSAGE_NOT_ESIGN_ORDER = "Fail to send email due to paper signature";
//	private static final String EMAIL_MESSAGE_MAIL_SEND_EXCEPTION = "Fail to send email with unexpected exception";
	
	
	
	public CodeLkupCacheService getLtsMCodeLkupCacheService() {
		return ltsMCodeLkupCacheService;
	}

	public void setLtsMCodeLkupCacheService(
			CodeLkupCacheService ltsMCodeLkupCacheService) {
		this.ltsMCodeLkupCacheService = ltsMCodeLkupCacheService;
	}

	protected final Log logger = LogFactory.getLog(getClass());	
	
	
	
	public LtsEmailDAO getLtsEmailDao() {
		return ltsEmailDao;
	}
	
	public void setLtsEmailDao(LtsEmailDAO ltsEmailDao) {
		this.ltsEmailDao = ltsEmailDao;
	}

	public FormPrintReqDAO getFormPrintReqDao() {
		return formPrintReqDao;
	}

	public void setFormPrintReqDao(FormPrintReqDAO formPrintReqDao) {
		this.formPrintReqDao = formPrintReqDao;
	}

	public OrderEsignatureService getOrderEsignatureService() {
		return orderEsignatureService;
	}

	public void setOrderEsignatureService(
			OrderEsignatureService orderEsignatureService) {
		this.orderEsignatureService = orderEsignatureService;
	}

	public OrdEmailReqService getOrdEmailReqService() {
		return ordEmailReqService;
	}

	public void setOrdEmailReqService(OrdEmailReqService ordEmailReqService) {
		this.ordEmailReqService = ordEmailReqService;
	}
	
	public CustomerProfileLtsService getCustomerProfileLtsService() {
		return customerProfileLtsService;
	}

	public void setCustomerProfileLtsService(
			CustomerProfileLtsService customerProfileLtsService) {
		this.customerProfileLtsService = customerProfileLtsService;
	}

	public CodeLkupCacheService getLtsSendDocLkupCacheService() {
		return ltsSendDocLkupCacheService;
	}

	public void setLtsSendDocLkupCacheService(
			CodeLkupCacheService ltsSendDocLkupCacheService) {
		this.ltsSendDocLkupCacheService = ltsSendDocLkupCacheService;
	}

	public List<OrderSendRequestDTO> getCCOrderSendRequest(String orderId, String requestDate, 
			String staffId, String teamCd, String seqNo, String templateId, String[] channelId) {
		
		try {
			return ltsEmailDao.getCCOrderSendRequest(orderId, requestDate, staffId, teamCd, seqNo, templateId, channelId);
		}
		catch (Exception e) {
			throw new AppRuntimeException(e.getMessage());
		}
		
	}
	
	public String sendSignOffEmail(SbOrderDTO sbOrder, String emailAddr, String userId) {
		String filePathName1 = sbOrder.getOrderId() + LtsConstant.EMAIL_FILE_PATTERN;
		String filePathName2 = LtsSbOrderHelper.isOrderDS(sbOrder) && StringUtils.equals("Y", sbOrder.getPrepaymentSlipInd())? sbOrder.getOrderId() + "_PS.pdf" : null;
		String filePathName3 = null;
		return sendSignOffEmail(sbOrder, emailAddr, userId, filePathName1, filePathName2, filePathName3, false);
	}
	
	public String sendSignOffEmail(SbOrderDTO sbOrder, String userId, 
			String filePathName1, String filePathName2, String filePathName3, boolean prePayment) {
		return sendSignOffEmail(sbOrder, sbOrder.getEsigEmailAddr(), userId, filePathName1, filePathName2, filePathName3, prePayment);
	}
	
	public String sendSignOffEmail(SbOrderDTO sbOrder, String emailAddr, String userId, 
			String filePathName1, String filePathName2, String filePathName3, boolean prePayment) {
		
		try {
			
			String templateId = LtsSbOrderHelper.getEmailTemplateId(sbOrder, prePayment);
	
			OrdEmailReqDTO orderEmailReq = createOrderEmailReq(sbOrder.getOrderId(), templateId,
					emailAddr, null, LtsConstant.SEND_METHOD_EMAIL, userId,
					sbOrder.getEsigEmailLang(), filePathName1, filePathName2, filePathName3, null, null);
			
			return sendOrderEmail(orderEmailReq);
		}
		catch (Exception e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
			return OrderEsignatureService.EmailReqResult.FAIL.getMessage();
		}
	}

	private void mergeCareCashPdf(String sbId, String password){
		
		String filePathName1 = sbId + LtsConstant.IGUARD_FORM_AF_NAME + ".pdf";
		String filePathName2 = sbId + LtsConstant.IGUARD_PICS_AF_NAME + ".pdf";
		
		String appEnv = "";
		String serverPath = "";

		try {
			
			appEnv = propertyConfigurer.getMergedProperties().getProperty(BomWebPortalConstant.APP_ENV);
			serverPath = propertyConfigurer.getMergedProperties().getProperty(appEnv+"."+BomWebPortalConstant.REPORT_SERVER_PATH);
			
			String mergefilePathName = "";
			if(password==null)
			{
				mergefilePathName = sbId + LtsConstant.IGUARD_FORM_N_PICS_AF_NAME + "N.pdf";
			}
			else
			{
				mergefilePathName = sbId + LtsConstant.IGUARD_FORM_N_PICS_AF_NAME + "P.pdf";
			}
					
			
			File mergefile = new File(serverPath + "/" + sbId + "/" + mergefilePathName);
			if(mergefile.exists() && !mergefile.isDirectory()) { 
			    return; // if mergefile exists
			}
			else
			{
				mergefile.createNewFile();
			}
			
			InputStream fis1 = null;
			fis1 = new FileInputStream(new File(serverPath + "/" + sbId + "/" +filePathName1));
			InputStream fis2 = null;
			fis2 = new FileInputStream(new File(serverPath + "/" + sbId + "/" +filePathName2));
			
			FastByteArrayOutputStream baosMerged = new FastByteArrayOutputStream();
			List<InputStream> files = new ArrayList<InputStream>();
			files.add(fis1);
			files.add(fis2);
			
			PdfUtil.concatPDFs(files, baosMerged, false, true, null, PdfUtil.ALLOW_SCREENREADERS | PdfUtil.ALLOW_PRINTING, password, password);
					
			byte[] pdfData = baosMerged.getByteArray();
			FileOutputStream pOutputStream =  new FileOutputStream(mergefile);
			
			pOutputStream.write(pdfData);
			pOutputStream.flush();
	
			pOutputStream.close();
			baosMerged.close();
			fis1.close();
			fis2.close();
		
		} catch (Exception e) {
			logger.error("Exception caught in mergeCareCashPdf()", e);
			System.out.println("Exception caught in mergeCareCashPdf()"+e);
			throw new AppRuntimeException(e);
		}
	}

	public String sendCareCashEmailToCustomer(SbOrderDTO sbOrder, String emailAddr, String userId) {
		try {
			
			String templateId = LtsConstant.EMAIL_TEMPLATE_IGUARD_CUST_EMAIL;
			
			String filePathName1 = sbOrder.getOrderId() + LtsConstant.IGUARD_FORM_N_PICS_AF_NAME + "P.pdf";
			CustomerDetailLtsDTO cust = LtsSbHelper.getLtsService(sbOrder).getAccount().getCustomer();
			String password = cust.getIdDocNum().substring(0, 6);
			
			mergeCareCashPdf(sbOrder.getOrderId(),password);
			
			OrdEmailReqDTO orderEmailReq = createOrderEmailReq(sbOrder.getOrderId(), templateId,
					emailAddr, null, LtsConstant.SEND_METHOD_EMAIL, userId,
					sbOrder.getEsigEmailLang(), filePathName1, null, null, null, null);
			
			return sendOrderEmail(orderEmailReq);
		}
		catch (Exception e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
			System.out.println(ExceptionUtils.getFullStackTrace(e));
			return OrderEsignatureService.EmailReqResult.FAIL.getMessage();
		}
	}

	public String sendCareCashEmailToAdmin(SbOrderDTO sbOrder, String userId) {
		try {
			
			String templateId = LtsConstant.EMAIL_TEMPLATE_IGUARD_ADMIN_EMAIL;
			String filePathName1 = sbOrder.getOrderId() + LtsConstant.IGUARD_FORM_N_PICS_AF_NAME + "N.pdf";
			
			mergeCareCashPdf(sbOrder.getOrderId(),null);
			
			OrdEmailReqDTO orderEmailReq = createOrderEmailReq(sbOrder.getOrderId(), templateId,
					carecashAdminEmail, null, LtsConstant.SEND_METHOD_EMAIL, userId,
					sbOrder.getEsigEmailLang(), filePathName1, null, null, null, null);
			
			return sendOrderEmail(orderEmailReq);
		}
		catch (Exception e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
			System.out.println(ExceptionUtils.getFullStackTrace(e));
			return OrderEsignatureService.EmailReqResult.FAIL.getMessage();
		}
	}
	
	public OrdEmailReqDTO createOrderEmailReq(String orderId, String templateId, String emailAddr, String smsNo, 
			String method, String username, String lang, 
			String filePathName1, String filePathName2, String filePathName3, 
			String paramString, Long printReqId){
		return createOrderEmailReq(orderId, templateId, getNextSeqNo(orderId), emailAddr, smsNo, method, username, lang, filePathName1, filePathName2, filePathName3, paramString, printReqId);
	}
	
	public OrdEmailReqDTO createOrderEmailReq(String orderId, String templateId, int seqNo, String emailAddr, String smsNo, 
			String method, String username, String lang, 
			String filePathName1, String filePathName2, String filePathName3, String paramString,
			Long printReqId){
		
		OrdEmailReqDTO emailReq = new OrdEmailReqDTO();
		emailReq.setOrderId(orderId);
		emailReq.setTemplateId(templateId);
		emailReq.setRequestDate(new Date());
		emailReq.setEmailAddr(emailAddr);
		emailReq.setLocale(EsigEmailLang.valueOf(StringUtils.defaultIfEmpty(lang, LtsConstant.DISTRIBUTE_LANG_CHINESE)));
		emailReq.setSeqNo(seqNo);
		emailReq.setFilePathName1(filePathName1);
		emailReq.setFilePathName2(filePathName2);
		emailReq.setFilePathName3(filePathName3);
		emailReq.setMethod(DisMode.valueOf(method));
		emailReq.setSMSno(smsNo);
		emailReq.setLob(LtsConstant.LOB_LTS);
		emailReq.setCreateBy(username);
		emailReq.setLastUpdBy(username);
		emailReq.setParamString(paramString);
		emailReq.setPrintReqId(printReqId);
		
		ordEmailReqService.insertOrdEmailReq(emailReq);
		return emailReq;
	}
	


	
	
	public OrdEmailReqDTO createAmendmentNoticeReq(SbOrderDTO sbOrder, String templateId, String emailAddr, String smsNo, DisMode method, String username){

		Date now = new Date();
		OrdEmailReqDTO emailReq = new OrdEmailReqDTO();
		emailReq.setOrderId(sbOrder.getOrderId());
		emailReq.setTemplateId(templateId);
		emailReq.setRequestDate(now);
		emailReq.setEmailAddr(emailAddr);
		emailReq.setSMSno(smsNo);
		emailReq.setLob(LtsConstant.LOB_LTS);
		emailReq.setLocale(EsigEmailLang.valueOf(sbOrder.getEsigEmailLang()));
		emailReq.setSeqNo(getNextSeqNo(sbOrder.getOrderId()));
		emailReq.setMethod(method);
		emailReq.setCreateDate(now);
		emailReq.setCreateBy(username);
		emailReq.setLastUpdDate(now);
		emailReq.setLastUpdBy(username);
		
		ordEmailReqService.insertOrdEmailReq(emailReq);
		return emailReq;
	}
	
	public String sendOrderEmail(OrdEmailReqDTO insertedDto) {
		EmailReqResult result = null;
		insertedDto = this.ordEmailReqService.getOrdEmailReqDTOByOrderIdAndSeqNo(insertedDto.getOrderId(), insertedDto.getSeqNo(), insertedDto.getTemplateId());
		try {
			orderEsignatureService.sendOrderEmail(insertedDto);
			insertedDto.setSentDate(new Date());
			insertedDto.setErrMsg(null);
		} catch (Exception e) {
			logger.error("Exception in sendOrderEmail", e);
			result = parseExceptionE(insertedDto, e);
			StringBuilder errMsg = new StringBuilder(result.getMessage()); 
			if (e.getMessage() != null) {
				errMsg.append(" " + e.getMessage());
			}
			insertedDto.setErrMsg(errMsg.length() > 100 ? errMsg.substring(0, 100) : errMsg.toString());
		}

		try {
			ordEmailReqService.updateOrdEmailReq(insertedDto);
		} catch (Exception e) {
			logger.error("Exception in updateOrdEmailReq", e);
			return "Fail to update bomweb_ord_email_req: " + insertedDto.getOrderId();
		}
		return result != null ? result.toString() : EmailReqResult.SUCCESS.toString();
	}
	
	private EmailReqResult parseExceptionE(OrdEmailReqDTO dto, Exception e) {
		if (e instanceof MailSendException) {
			return EmailReqResult.MAIL_SEND_EXCEPTION;
		}
		if (e instanceof FileNotFoundException) {
			return EmailReqResult.ATTACHMENT_NOT_FOUND;
		}
		return EmailReqResult.FAIL;
	}
	
	public List<OrdEmailReqDTO> getOrdEmailReqDTOLTS(String orderId, String lob, String... templateIds){
		try {
			return ltsEmailDao.getOrdEmailReqDTOLTS(orderId, lob, templateIds);
		} catch (DAOException e) {
			throw new AppRuntimeException(e.getMessage());
		}
	}
	
	public int getNextSeqNo(String orderId) {
		try {
			return ltsEmailDao.getNextSeqNo(orderId);
		}
		catch (Exception e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
			throw new AppRuntimeException(e.getMessage());
		}
		
	}
	
	public String createLetterPrintReq(SbOrderDTO sbOrder, String userId) {
		ServiceDetailDTO ltsServiceDetail = LtsSbOrderHelper.getLtsService(sbOrder); 
		String bomBillAddress = getBomBillAddress(ltsServiceDetail);
		
		String[] splitedBillAddrLines = StringUtils.defaultIfEmpty(bomBillAddress, "").split("\\r?\\n");
		if (ArrayUtils.isEmpty(splitedBillAddrLines)) {
			return "Fail to retrieve postal address.";
		}

		String[] postalAddrLines = new String[6];
		for (int i=0; i < splitedBillAddrLines.length; i++){
			postalAddrLines[i] = StringUtils.defaultIfEmpty(splitedBillAddrLines[i], "");
		}
		
		String prod = LtsConstant.ORDER_TYPE_SB_DISCONNECT.equals(sbOrder.getOrderType()) ? ltsServiceDetail.getFromProd() : ltsServiceDetail.getToProd();
		String lookupKey = "P" + "^" + sbOrder.getOrderType() + "^" + prod;
		String lookupSendDocument = (String)ltsSendDocLkupCacheService.get(lookupKey);
			
		if (StringUtils.isEmpty(lookupSendDocument)) {
			return "Fail to retrieve send out documents.";
		}
		
		ItemDetailLtsDTO[] items = ((ServiceDetailLtsDTO)ltsServiceDetail).getItemDtls();
		HashMap<String, String> xmlParams = new HashMap<String, String>();
		String allMCode = null;
		if(!ArrayUtils.isEmpty(items)){
			for (ItemDetailLtsDTO item : items){
				if(LtsConstant.LTS_LETTER_PRINT_IO_IND.equals(item.getIoInd())){
					String mCode = (String)ltsMCodeLkupCacheService.get(item.getItemId());
					if(!StringUtils.isEmpty(mCode)){
						allMCode = StringUtils.isEmpty(allMCode)? mCode : allMCode + "," + mCode;
					}					
				}
			}
			if(!StringUtils.isEmpty(allMCode)){
				xmlParams.put(LtsConstant.LTS_LETTER_PRINT_XML_PARAM_LTS_M_CODE,allMCode);
			}
		}
			
		if (LtsConstant.ORDER_TYPE_SB_DISCONNECT.equals(sbOrder.getOrderType())){
			String lookupDOrderEyeDesc = (String)ltsDOrderEyeDescLkupCacheService.get(prod.toUpperCase());			
			if (StringUtils.isEmpty(lookupDOrderEyeDesc)) {
				return "Fail to retrieve eye description for disconnect order.";
			}else{
				xmlParams.put(LtsConstant.LTS_LETTER_PRINT_XML_PARAM_EYE_TYPE_DESC, lookupDOrderEyeDesc);
			}			
		}
		
		String xmlLookupDOrderEyeDesc = null;
		if (!xmlParams.isEmpty()){
			xmlLookupDOrderEyeDesc = getXmlEyeDescription(LtsConstant.LTS_LETTER_PRINT_XML_PARAM_NAMED_PARAMS, xmlParams);
			if (StringUtils.isEmpty(xmlLookupDOrderEyeDesc)) {
				return "Fail to convert to xml string.";
			}
		}

		
		try {
			//Retail orders do not require letter print 20160121
			Long printReqId =  isRetailOrder(sbOrder.getOrderId())? null : insertFormPrintReq(
					sbOrder.getOrderId(), "PAPER", postalAddrLines[0],
					postalAddrLines[1], postalAddrLines[2], postalAddrLines[3],
					postalAddrLines[4], postalAddrLines[5], "", "", "", "",
					userId, userId);
			// insert into bomweb_ord_email_req, multiple records if more than 1 send out document.
			int seqNo = getNextSeqNo(sbOrder.getOrderId());
			String[] sendDocuments = lookupSendDocument.split("_");        
			for (String sendDocument : sendDocuments){
	            String fileName = LtsConstant.SEND_DOCUMENT_AF.equals(sendDocument) ? sbOrder.getOrderId() + "_" + LtsConstant.SEND_DOCUMENT_AF + ".pdf" : sbOrder.getOrderId() + "_" + sendDocument + "_" + seqNo + ".pdf";
	            String templateId = LtsSbOrderHelper.getLetterPrintDocName(sendDocument);
			    createOrderEmailReq(sbOrder.getOrderId(), templateId, seqNo, null, null, LtsConstant.SEND_METHOD_PAPER, userId, sbOrder.getEsigEmailLang(), fileName, null, null, xmlLookupDOrderEyeDesc, printReqId);
	        }
			return EmailReqResult.SUCCESS.toString();
		}
		catch (Exception e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
			return "Fail to create letter print request. " + "[" + e.getMessage() + "]";
		}
	}
	
	public boolean isRetailOrder(String orderId){
		return StringUtils.startsWith(orderId, "R");
	}
	
	
	public long insertFormPrintReq(final String pOrderId, final String pPrintReqType,
			final String pPostalAddrLine1, final String pPostalAddrLine2,
			final String pPostalAddrLine3, final String pPostalAddrLine4,
			final String pPostalAddrLine5, final String pPostalAddrLine6,
			final String pEmailContent, final String pEmailSubject,
			final String pEmailFrom, final String pSmsContent,
			final String pCreateBy, final String pLastUpdBy){
		
		try {
			return formPrintReqDao.insertRecord(pOrderId, pPrintReqType, pPostalAddrLine1, pPostalAddrLine2, pPostalAddrLine3, pPostalAddrLine4, pPostalAddrLine5, pPostalAddrLine6, pEmailContent, pEmailSubject, pEmailFrom, pSmsContent, pCreateBy, pLastUpdBy);
		}
		catch (Exception e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
			throw new AppRuntimeException(e.getMessage());
		}
		
	}
	
	public String getBomBillAddress(ServiceDetailDTO serviceDetail) {
		String acctNo = serviceDetail.getAccount().getAcctNo();
		if (StringUtils.isNotEmpty(acctNo) && !LtsConstant.DUMMY_ACCT_NO.equals(acctNo)) {
			AccountDetailProfileLtsDTO profileAccount = customerProfileLtsService.getAccountbyAcctNum(acctNo, "DRG"); 
			if (profileAccount != null) {
				return profileAccount.getBillAddr();
			}
		}
		return null;
	}
	
	public String getXmlEyeDescription(String xmlParam1, HashMap<String, String> xmlParams) {
		String xmlEyeDescription = null;
		
        try {
            //Creating an empty XML Document
            DocumentBuilderFactory dbfac = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = dbfac.newDocumentBuilder();
            Document doc = docBuilder.newDocument();

            //Creating the XML tree

            //create the root element and add it to the document
            Element root = doc.createElement(xmlParam1);
            doc.appendChild(root);
            
            Map<String, String> map = xmlParams;

            for (String key : map.keySet()) {
                String value = map.get(key);
                Element child = doc.createElement(key);
                root.appendChild(child);
                Text text = doc.createTextNode(value);
                child.appendChild(text);
            }

            //Output the XML to a string

            //set up a transformer
            TransformerFactory transfac = TransformerFactory.newInstance();
            Transformer trans = transfac.newTransformer();
            trans.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            trans.setOutputProperty(OutputKeys.INDENT, "yes");

            //create string from xml tree
            StringWriter sw = new StringWriter();
            StreamResult result = new StreamResult(sw);
            DOMSource source = new DOMSource(doc);
            trans.transform(source, result);
            xmlEyeDescription = sw.toString();

        } catch (Exception e) {
            System.out.println(e);
        }
		return xmlEyeDescription;
    }

	public CodeLkupCacheService getLtsDOrderEyeDescLkupCacheService() {
		return ltsDOrderEyeDescLkupCacheService;
	}

	public void setLtsDOrderEyeDescLkupCacheService(
			CodeLkupCacheService ltsDOrderEyeDescLkupCacheService) {
		this.ltsDOrderEyeDescLkupCacheService = ltsDOrderEyeDescLkupCacheService;
	}

	public String getCarecashAdminEmail() {
		return carecashAdminEmail;
	}

	public void setCarecashAdminEmail(String carecashAdminEmail) {
		this.carecashAdminEmail = carecashAdminEmail;
	}

	public BomPropertyPlaceholderConfigurer getPropertyConfigurer() {
		return propertyConfigurer;
	}

	public void setPropertyConfigurer(
			BomPropertyPlaceholderConfigurer propertyConfigurer) {
		this.propertyConfigurer = propertyConfigurer;
	}

	
		
}
