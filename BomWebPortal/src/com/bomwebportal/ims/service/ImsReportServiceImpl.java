package com.bomwebportal.ims.service;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;


import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.transaction.annotation.Transactional;

import com.bomwebportal.ims.dao.ImsReport1DAO;
import com.bomwebportal.ims.dao.ImsReport2DAO;
import com.bomwebportal.ims.dto.AttbDTO;
import com.bomwebportal.ims.dto.ImsRptBasketItemDTO;
import com.bomwebportal.ims.dto.ImsRptGiftDTO;
import com.bomwebportal.ims.dto.ImsSignoffDTO;
import com.bomwebportal.ims.dto.SubscribedCslOfferDTO;
import com.bomwebportal.bean.LookupTableBean;
import com.bomwebportal.dto.VasParmDTO;
import com.bomwebportal.dto.report.RptIGuardCareCashDTO;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.exception.ImsAfException;
import com.bomwebportal.exception.ImsEmptySignatureException;
import com.bomwebportal.ims.dto.ImsRptServicePlanDetailDTO;
import com.bomwebportal.ims.dto.report.ReportDTO;
import com.bomwebportal.ims.dto.report.RptIms3PartyCreditCardDTO;
import com.bomwebportal.ims.dto.report.RptImsAppServDTO;
import com.bomwebportal.ims.dto.report.RptServiceDetailDTO;
import com.bomwebportal.ims.dto.report.RptServiceInfoDTO;
import com.bomwebportal.ims.dto.ui.NowTVAddUI;
import com.bomwebportal.ims.dto.ui.NowTVOfferUI;
import com.bomwebportal.ims.dto.ui.NowTvCampaignUI;
import com.bomwebportal.ims.dto.ui.NowTvPackUI;
import com.bomwebportal.ims.dto.ui.NowTvTopUpUI;
import com.bomwebportal.ims.dto.ui.OrderImsUI;
import com.bomwebportal.util.FastByteArrayOutputStream;
import com.bomwebportal.util.PdfUtil;
import com.bomwebportal.util.ReportUtil;
import com.bomwebportal.util.Utility;
import com.google.gson.Gson;
import com.bomwebportal.ims.dto.ui.NowTvVasBundle;

@Transactional(readOnly = true)
public class ImsReportServiceImpl implements ImsReportService {
	protected final Log logger = LogFactory.getLog(getClass());
	private Gson gson = new Gson();
	private ImsNowTVService imsNowTVService;

	private ImsReport2DAO imsReport2Dao;	

	public ImsReport2DAO getImsReport2Dao() {
		return imsReport2Dao;
	}
	public void setImsReport2Dao(ImsReport2DAO imsReport2Dao) {
		this.imsReport2Dao = imsReport2Dao;
	}
	
	private ImsReport1DAO imsReport1Dao;	

	public ImsReport1DAO getImsReport1Dao() {
		return imsReport1Dao;
	}
	public void setImsReport1Dao(ImsReport1DAO imsReport1Dao) {
		this.imsReport1Dao = imsReport1Dao;
	}
	
	private String imageFilePath;	
	public String getImageFilePath() {
		return imageFilePath;
	}
	public void setImageFilePath(String imageFilePath) {
		logger.debug("imageFilePath=" + imageFilePath);
		this.imageFilePath = imageFilePath;
	}
	
	private String pdfFilePath;
	public String getPdfFilePath() {
		return pdfFilePath;
	}
	public void setPdfFilePath(String pdfFilePath) {
		logger.debug("pdfFilePath=" + pdfFilePath);
		this.pdfFilePath = pdfFilePath;
	}
	
	private String pdfDefaultFilePath;
	public String getPdfDefaultFilePath() {
		return pdfDefaultFilePath;
	}
	public void setPdfDefaultFilePath(String pdfDefaultFilePath) {
		logger.debug("pdfDefaultFilePath=" + pdfDefaultFilePath);
		this.pdfDefaultFilePath = pdfDefaultFilePath;
	}
	
	private String dataFilePath;
	public String getDataFilePath() {
		return dataFilePath;
	}
	public void setDataFilePath(String dataFilePath) {
		logger.debug("dataFilePath=" + dataFilePath);
		this.dataFilePath = dataFilePath;
	}
	
	
	private String imsRetailJasperName;
	public String getImsRetailJasperName() {
		return imsRetailJasperName;
	}
	public void setImsRetailJasperName(String imsRetailJasperName) {
		this.imsRetailJasperName = imsRetailJasperName;
	}
	
	private String imsRetailPTJasperName;	
	public String getImsRetailPTJasperName() {
		return imsRetailPTJasperName;
	}
	public void setImsRetailPTJasperName(String imsRetailPTJasperName) {
		this.imsRetailPTJasperName = imsRetailPTJasperName;
	}

	private String imsRetail3PartyJasperName;
	public void setImsRetail3PartyJasperName(String imsRetail3PartyJasperName) {
		this.imsRetail3PartyJasperName = imsRetail3PartyJasperName;
	}
	public String getImsRetail3PartyJasperName() {
		return imsRetail3PartyJasperName;
	}
	
	private String imsDsJasperName;
	public String getImsDsJasperName() {
		return imsDsJasperName;
	}
	public void setImsDsJasperName(String imsDsJasperName) {
		this.imsDsJasperName = imsDsJasperName;
	}
	private String imsDsCPJasperName;
	
	private String imsCareCashJasperName;
	public String getImsCareCashJasperName() {
		return imsCareCashJasperName;
	}
	public void setImsCareCashJasperName(String imsCareCashJasperName) {
		this.imsCareCashJasperName = imsCareCashJasperName;
	}
	private ImsSignOffLogService signOffLogService;
	public void setSignOffLogService(ImsSignOffLogService signOffLogService) {
		this.signOffLogService = signOffLogService;
	}
	public ImsSignOffLogService getSignOffLogService() {
		return signOffLogService;
	}

	
//Gary isPT Test
	//boolean isPT;//=true;

	private static String COMPANY_LOGO_FILE = "pccw_logo.png";
	private static String NETVIGATOR_LOGO_FILE_EN = "netvigator_logo_eng.PNG";
	private static String NETVIGATOR_LOGO_FILE_ZH = "netvigator_logo_chi.PNG";
	private static String FOOTER_PCCW_LOGO_FILE_EN = "footer_pccw_eng.PNG";
	private static String FOOTER_PCCW_LOGO_FILE_ZH = "footer_pccw_chi.PNG";
	private static String FOOTER_HKT_LOGO_FILE_EN = "footer_hkt_eng.PNG";
	private static String FOOTER_HKT_LOGO_FILE_ZH = "footer_hkt_chi.PNG";
	private static String FORM_ZH_EXT = "_zh";
	private static String RPT_LANG_ZH = "zh";
	private static String CUST_COPY = "_cust_copy";
	
	// martin 20160422 (start) - third party credit card form setup
	private static String NOWTV_LOFO_FILE = "now_tv_logo_bw.png";
	private static String NNETVIGATOR_LOGO = "netvigator_logo_bw.png";
	// martin 20160422 (end)
	
	private ArrayList<String> getJasperFileName(Boolean needCustCopy,  Boolean isPT, Boolean cOrder, Boolean isCC, Boolean isDS){
		ArrayList<String> tempReportSeq = new ArrayList<String>();
		
		if(isDS){
			tempReportSeq.add(imsDsJasperName);
			tempReportSeq.add(imsDsJasperName + FORM_ZH_EXT);  
//			tempReportSeq.add(imsRetailJasperName); 
//			tempReportSeq.add(imsRetailJasperName + FORM_ZH_EXT); 
		}else if(!isPT){
			tempReportSeq.add(imsRetailJasperName);
			tempReportSeq.add(imsRetailJasperName + FORM_ZH_EXT);
		}else{
			tempReportSeq.add(imsRetailPTJasperName);
			tempReportSeq.add(imsRetailPTJasperName + FORM_ZH_EXT);
		}
		if(!cOrder)
			tempReportSeq.add("PIS");
		if(needCustCopy && !cOrder && !isCC && !isDS){  ////?????? 
			tempReportSeq.add(imsRetailJasperName + CUST_COPY);
			tempReportSeq.add(imsRetailJasperName + FORM_ZH_EXT + CUST_COPY);
			tempReportSeq.add("PIS");
		}
		tempReportSeq.add(imsRetail3PartyJasperName);
		tempReportSeq.add(imsCareCashJasperName);
		
		//tempReportSeq.add(imsDsCPJasperName);	
		
		
//		for(int i=0;i<tempReportSeq.size();i++){
//			logger.debug("tempReportSeq.get("+i+"):"+tempReportSeq.get(i));
//		}	
		return tempReportSeq;
	}

	public ImsReportServiceImpl() {
	}

	public void viewPdfReports(OutputStream pOutputStream, String orderId) {
		byte[] bbuf = new byte[1024];
		DataInputStream in;
		int length = 0;
		String fileName;

		try {
			fileName = dataFilePath + orderId + File.separatorChar + orderId+"_AF.pdf";
			logger.debug("View PDF report from " + fileName);

			in = new DataInputStream(new FileInputStream(fileName));
			while ((in != null) && ((length = in.read(bbuf)) != -1)) {
				pOutputStream.write(bbuf, 0, length);
			}

			pOutputStream.flush();
			in.close();
		} catch (Exception e) {
			logger.error("Exception caught in view pdf of generatePdfReports()", e);
		}
		
	}
	public void generatePdfReports(ArrayList<Object> dataList, OutputStream screenOutputStream, String pdfLang, 
			String orderId, Boolean signed, String disMode,Boolean isCC,Boolean isPT,Boolean isDs, Boolean cOrder, Boolean isPreview) throws ImsAfException, ImsEmptySignatureException{
		this.generatePdfReports( dataList,  screenOutputStream,  pdfLang, 
				 orderId,  signed,  disMode, isCC, isPT, isDs, cOrder, isPreview, false);
	}
	
	public void generatePdfReports(ArrayList<Object> dataList, OutputStream screenOutputStream, String pdfLang, 
			String orderId, Boolean signed, String disMode,Boolean isCC,Boolean isPT,Boolean isDS,Boolean cOrder, Boolean isPreview, Boolean signOffed) throws ImsAfException, ImsEmptySignatureException{
		try {
			logger.debug("generatePdfReports(pReportArrList) is called in ReportServiceImpl.java");


			if(cOrder){
				logger.debug("generatePdfReports(pReportArrList) is C order ");
			}else{
				logger.debug("generatePdfReports(pReportArrList) is not C order");
			}
			//  input data to corresponding reports
			//(Signed(dataList) == see if ImsSignoffDTO exist, therefore, see if signed or not
			Boolean signedInDataList = Signed(dataList);
			Boolean needCustCopy = (disMode.toString().equals("E"))?false:true;// E - Esign, P - Paper
			if (needCustCopy)logger.debug("tempReportSeq");
			HashMap<String, JasperReport> reportDataMap=null;
			
			if(isDS){
				reportDataMap = mapReportDsData(dataList, pdfLang, signedInDataList,isCC, cOrder, false, disMode, signOffed);
				//reportDataMap = mapReportData(dataList, pdfLang, signedInDataList,isCC, cOrder);
			}else if(!isPT){
				reportDataMap = mapReportData(dataList, pdfLang, signedInDataList,isCC, cOrder, disMode, signOffed);
			}else{
				reportDataMap = mapPremierReportData(dataList, pdfLang, signedInDataList, isCC, cOrder, disMode, signOffed);
			}
			ArrayList<String> reportSeq = getJasperFileName(needCustCopy,isPT,cOrder,isCC,isDS);
			List<String> pisPDFNameList = this.getPisNames(dataList);
			List<InputStream> inputStream = new ArrayList<InputStream>();
			List<InputStream> inputStreamCareCash = new ArrayList<InputStream>();
			FastByteArrayOutputStream mergedPDF = new FastByteArrayOutputStream();// concat all PDFs into 1 output files
			FastByteArrayOutputStream mergedCareCashPDF = new FastByteArrayOutputStream();// concat all PDFs into 1 output files
					
			// 1.to save pdf file into server - start
			generateStreams(pdfLang, reportDataMap, reportSeq, inputStream, pisPDFNameList, inputStreamCareCash);
//			logger.debug("######################################");
//			logger.debug("inputStream : " + new Gson().toJson(inputStream)); 
//			logger.debug("######################################");
			PdfUtil.concatPDFs(inputStream, mergedPDF, false);//false - pIsAddPageNum
			byte[] finalPDF = mergedPDF.getByteArray();
			File directory = new File(dataFilePath + orderId);
			File pdfName;
			try {
				if (directory.exists() == false) {
					directory.mkdir();
					logger.debug("Directory " + directory.toString() + " created.");
				} else {
					logger.debug("Directory " + directory.toString() + " is existed.");
				}
		        Calendar cal = Calendar.getInstance();
		        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
//		        System.out.println( sdf.format(cal.getTime()) );
				if(cOrder){
					pdfName = new File(directory, orderId+"_AF_FS.pdf");
				}else{
//					pdfName = new File(directory, orderId+"_AF"+sdf.format(cal.getTime())+".pdf");
					pdfName = new File(directory, orderId+"_AF.pdf");
				}
				FileOutputStream saveToServer = new FileOutputStream(pdfName);
				saveToServer.write(finalPDF);
				if(signOffed!=null && signOffed){
					pdfName = new File(directory, orderId+"_AF_SignOffed.pdf");
					FileOutputStream saveToServer2 = new FileOutputStream(pdfName);
					saveToServer2.write(finalPDF);
				}
				saveToServer.close();
				CloseAllInputStream(inputStream);
				
				//CareCash pdf generation
				
				if(!cOrder){
					if(inputStreamCareCash!=null&&inputStreamCareCash.size()>0){
						
						PdfUtil.concatPDFs(inputStreamCareCash, mergedCareCashPDF, false);//false - pIsAddPageNum
						byte[] finalCareCashPDF = mergedCareCashPDF.getByteArray();
						File careCahsepdfName;
						
						careCahsepdfName = new File(directory, orderId+"_CARECash.pdf");
						FileOutputStream saveCareCashToServer = new FileOutputStream(careCahsepdfName);
						saveCareCashToServer.write(finalCareCashPDF);
					
						saveCareCashToServer.close();
					}else{
						File careCashpdfName;
						File careCashEcrptpdfName;

						careCashpdfName = new File(directory, orderId+"_CARECash.pdf");
						careCashEcrptpdfName = new File(directory, orderId+"_CARECash_encry.pdf");
						
						if(careCashpdfName.exists() && !careCashpdfName.isDirectory()) {
							careCashpdfName.delete();
						}
						
						if(careCashEcrptpdfName.exists() && !careCashEcrptpdfName.isDirectory()) {
							careCashEcrptpdfName.delete();
						}

						
					}
				}
				CloseAllInputStream(inputStreamCareCash);
				
			} catch (Exception e) {
				logger.error("Exception caught in save pdf of generatePdfReports()", e);
			}
			// 1.to save pdf file into server - end

			

			//2. to show in summary - for e-sign - start
			if(screenOutputStream!=null && signed!=null){
				if (isDS){
					reportDataMap = mapReportDsData(dataList, pdfLang, signed,isCC,cOrder,isPreview,disMode,signOffed);//gary
				}else if(!isPT){
					reportDataMap = mapReportData(dataList, pdfLang, signed,isCC,cOrder,disMode,signOffed);//gary
				}else{
					reportDataMap = mapPremierReportData(dataList, pdfLang, signed, isCC, cOrder, disMode,signOffed);//gary
				}
				inputStream = new ArrayList<InputStream>();
				inputStreamCareCash = new ArrayList<InputStream>();
				generateStreams(pdfLang, reportDataMap, reportSeq, inputStream, pisPDFNameList, inputStreamCareCash);
				if(inputStreamCareCash!=null&&inputStreamCareCash.size()>0){
					inputStream.addAll(inputStreamCareCash);
				}
				mergedPDF = new FastByteArrayOutputStream();// concat all PDFs into 1 output files
				if(signed){
					PdfUtil.concatPDFs(inputStream, mergedPDF, false, true, null, (Integer)PdfUtil.ALLOW_SCREENREADERS);
					logger.debug("protected report created");
				}else{
					PdfUtil.concatPDFs(inputStream, mergedPDF, false);//false - pIsAddPageNum
				}
				finalPDF = mergedPDF.getByteArray();
				screenOutputStream.write(finalPDF);
				screenOutputStream.flush();
				CloseAllInputStream(inputStream);
			}
			//2. to show in summary - for e-sign end
			
			
			
			//3. save pdf with pwd start
			String password = this.getIdForPwd(dataList);
			ByteArrayOutputStream fbaos = new ByteArrayOutputStream();
			InputStream fis = null;
			try {
				fis = new FileInputStream(new File(directory, orderId+"_AF.pdf"));
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
			PdfUtil.concatPDFs(Arrays.asList(fis), fbaos, false, false, null, PdfUtil.ALLOW_SCREENREADERS | PdfUtil.ALLOW_PRINTING, password, password);
			finalPDF = fbaos.toByteArray();
			try {
				FileOutputStream saveToServer = new FileOutputStream(new File(directory,orderId+"_AF_encry.pdf"));
				saveToServer.write(finalPDF);
				saveToServer.close();
			} catch (Exception e) {
				logger.error("Exception caught in encry pdf of generatePdfReports()", e);
			}
			
			//care cash
			ByteArrayOutputStream cbaos = new ByteArrayOutputStream();
			InputStream cis = null;
			try {
				File f = new File(directory, orderId+"_CARECash.pdf");
				if(f.exists() && !f.isDirectory()) {
					cis = new FileInputStream(f);
				}
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
			
			if(cis !=null){
				PdfUtil.concatPDFs(Arrays.asList(cis), cbaos, false, false, null, PdfUtil.ALLOW_SCREENREADERS | PdfUtil.ALLOW_PRINTING, password, password);
				finalPDF = cbaos.toByteArray();
				try {
					FileOutputStream saveToServer = new FileOutputStream(new File(directory,orderId+"_CARECash_encry.pdf"));
					saveToServer.write(finalPDF);
					saveToServer.close();
				} catch (Exception e) {
					logger.error("Exception caught in encry pdf of generatePdfReports()", e);
				}
			}
			//care cash end
			
			//3. save pdf with pwd end
			
			
			mergedPDF.close();
		} catch (Exception de) {
			logger.error("Exception caught in generatePdfReports()", de);
			if (de instanceof ImsEmptySignatureException){
				throw new ImsEmptySignatureException();
			}else{
				throw new AppRuntimeException(de);
			}
		}
	}
	
	
	
	public boolean isDBSignOffEd(String orderId) {
		try {
			return imsReport2Dao.isDBSignOffed(orderId);
		} catch (DAOException e) {
			e.printStackTrace();
			return true;
		}
	}
	private String getIdForPwd(ArrayList<Object> dataList) {
		Object obj = null;
		OrderImsUI order = null;
		String password = "";
		try{
            for (int i = 0; i < dataList.size(); i++) {
    			obj = dataList.get(i);
    			if (obj instanceof OrderImsUI) {
    				order = (OrderImsUI) obj;
    				password = order.getCustomer().getIdDocNum();
    				
    				if (StringUtils.isBlank(password) || password.length() < 6) {
    					logger.warn("password length: " + (password == null ? -1 : password.length()));
    					return null;
    				}
    				password = password.substring(0, 6).toUpperCase();
					logger.debug("AF password: " + password);
    				
    				final String passwordPattern = "^[A-Z0-9]{6}$";
    				if (!password.matches(passwordPattern)) {
    					logger.warn("password: " + password + " not match with pattern: " + passwordPattern);
    					return null;
    				}
    				
    				return password;
    			}
    		}
       } catch (Exception e) {
           logger.error(ExceptionUtils.getFullStackTrace(e));
       }
		return null;
	}
	
	
	
	
	
	private void generateStreams(String pdfLang,
			HashMap<String, JasperReport> reportDataMap,
			ArrayList<String> reportSeq, List<InputStream> inputStream, List<String> pisPDFNameList, List<InputStream> inputStreamCareCash
			) throws Exception {
		FastByteArrayOutputStream tmpOutputStream = new FastByteArrayOutputStream();
		ArrayList<InputStream> tmpInputStream;
		String currentReport;
		JasperReport currentPCD;
		
		//Gary
		/*20130807 TEST add pdf file*/
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		
		
		
		String pisPdfName;// = imsReport2DAO.getPISPdf("abc").get(0);
		InputStream pisInput = null;// = classLoader.getResourceAsStream(pdf_name);
		InputStream careCashPICSInput = null;// = classLoader.getResourceAsStream(pdf_name);
		InputStream careCashTNCInput = null;// = classLoader.getResourceAsStream(pdf_name);
		
//		logger.debug("######################################");
//		logger.debug(new Gson().toJson(pisPDFNameList));
//		logger.debug(new Gson().toJson(reportSeq)); 
//		logger.debug("######################################");
		
		for (int i = 0; i < reportSeq.size(); i++) {
			currentReport = reportSeq.get(i);
			if(currentReport.equals("PIS")){
				//Gary
				if(pisPDFNameList!=null && pisPDFNameList.size()>0){
					for (int j=0;j< pisPDFNameList.size();j++){
						pisPdfName = pisPDFNameList.get(j);
						
						try {
							pisInput = new FileInputStream(new File(pdfFilePath+"/"+pisPdfName));
						}
						catch (FileNotFoundException e) { 
							pisInput = classLoader.getResourceAsStream(pdfDefaultFilePath+"/"+pisPdfName);
						}
					
						if(pisInput != null){
							inputStream.add(pisInput);
						}else {
							logger.error("pisInput is null, path="+pdfDefaultFilePath+"/"+pisPdfName);
						}
					}
				}
			}else{
				currentPCD = reportDataMap.get(currentReport);
				if (currentPCD != null) {
					logger.debug("currentReport - PCD     : "+currentReport);
					if(currentPCD.getJasperName().equals(imsCareCashJasperName)){
						tmpInputStream = new ArrayList<InputStream>();
						ArrayList<RptIGuardCareCashDTO> inputList = new ArrayList<RptIGuardCareCashDTO>();
						for(int j=0;j<currentPCD.getRptDtoArrList().size();j++){
							RptImsAppServDTO dto = (RptImsAppServDTO)currentPCD.getRptDtoArrList().get(j);
							inputList.add(dto.getRptIGuardCareCashDTO());
						}
						tmpInputStream.add(generateCareCashInputStream(currentPCD.getJasperName(),inputList));
						PdfUtil.concatPDFs(tmpInputStream, tmpOutputStream, true);// true = pIsAddPageNum
						inputStreamCareCash.add(tmpOutputStream.getInputStream());

						String careCashPICSName = "careCash_pics_" + pdfLang + ".pdf";
						String careCashTNCName = "careCash_tnc_" + pdfLang + ".pdf";
						
						try {
							careCashPICSInput = new FileInputStream(new File(pdfFilePath+"/"+careCashPICSName));
						}
						catch (FileNotFoundException e) { 
							careCashPICSInput = classLoader.getResourceAsStream(pdfDefaultFilePath+"/"+careCashPICSName);
						}
						
						if(careCashPICSInput != null){
							inputStreamCareCash.add(careCashPICSInput);
						}else {
							logger.error("careCashPICSInput is null, path="+pdfDefaultFilePath+"/"+careCashPICSName);
						}
						
						try {
							careCashTNCInput = new FileInputStream(new File(pdfFilePath+"/"+careCashTNCName));
						}
						catch (FileNotFoundException e) { 
							careCashTNCInput = classLoader.getResourceAsStream(pdfDefaultFilePath+"/"+careCashTNCName);
						}
						
						if(careCashTNCInput != null){
							inputStreamCareCash.add(careCashTNCInput);
						}else {
							logger.error("careCashTNCInput is null, path="+pdfDefaultFilePath+"/"+careCashTNCName);
						}
						
					}else{
						tmpInputStream = new ArrayList<InputStream>();
						tmpInputStream.add(generatePdfInputStream(currentPCD.getJasperName(),currentPCD.getRptDtoArrList()));
						PdfUtil.concatPDFs(tmpInputStream, tmpOutputStream, true);// true = pIsAddPageNum
						inputStream.add(tmpOutputStream.getInputStream());
					}
				}
			}
		}
	}
	/**
	 * Generate PDF form/report from jasper template
	 * 
	 * @param pJasperName
	 *            jasper template name
	 * @param dtoArrList
	 *            ArrayList storing report data
	 * @return InputStream of generated pdf
	 * @throws IOException
	 */
	private InputStream generatePdfInputStream(String pJasperName, ArrayList<ReportDTO> dtoArrList) 
	throws Exception {
FastByteArrayOutputStream baos = new FastByteArrayOutputStream();
JRDataSource ds = new JRBeanCollectionDataSource(dtoArrList);
logger.debug("jasper name:"+pJasperName);
//if(!isPT){
//	
//}else{
//	
//}

ReportUtil.generatePdfReport("ims/"+pJasperName, ds, baos);

baos.close();

return baos.getInputStream();
}
	

	private InputStream generateCareCashInputStream(String pJasperName, ArrayList<RptIGuardCareCashDTO> dtoArrList) 
	throws Exception {
FastByteArrayOutputStream baos = new FastByteArrayOutputStream();
JRDataSource ds = new JRBeanCollectionDataSource(dtoArrList);
logger.debug("jasper name:"+pJasperName);
//if(!isPT){
//	
//}else{
//	
//}

ReportUtil.generatePdfReport("mob/"+pJasperName, ds, baos);

baos.close();

return baos.getInputStream();
}

	/**
	 * Assign report data to specific report. Size of Map return should be same
	 * of number of report template generate.
	 */
	private HashMap<String, JasperReport> mapReportData(ArrayList<Object> pDTOs, String pLang, Boolean Signed, Boolean isCC, Boolean cOrder, String disMode, Boolean signOffed)//gary
			throws InvocationTargetException, IllegalAccessException, ImsEmptySignatureException {
		
		String planCd = "("+"";
		String campignCd =  "("+"";
		Boolean isPT =false;
		HashMap<String, HashMap<String, RptServiceInfoDTO>> dbRptstaticWords  = new HashMap<String, HashMap<String, RptServiceInfoDTO>>();
		dbRptstaticWords=this.getDBRptStaticWords(pDTOs);
		
		if(dbRptstaticWords==null){
			logger.debug("dbRptstaticWords==null, use properties files");
		}else{
			logger.debug("dbRptstaticWords!=null, use db words");
		}
		ImsSignoffDTO customerSign = null;
		ImsSignoffDTO creditCardsign = null;
		ImsSignoffDTO thirdPartySign = null;
		ImsSignoffDTO careCashSign = null;
		OrderImsUI order = null;
		ImsRptServicePlanDetailDTO servicePlan = null;
		int fixedTerm = 0;
		int extenTerm = 0;
		int totalTerm = 0;
		
		String configFiles = "";
		configFiles = "imsReportConstant.xml";
		ApplicationContext appCtx = new ClassPathXmlApplicationContext(configFiles);
		
		MessageSource msg = new ClassPathXmlApplicationContext(configFiles);
		
		HashMap<String, JasperReport> map = new HashMap<String, JasperReport>();
		ArrayList<ReportDTO> ssList = new ArrayList<ReportDTO>();
		ArrayList<ReportDTO> ssCustCopyList = new ArrayList<ReportDTO>();
		ArrayList<ReportDTO> ccList = new ArrayList<ReportDTO>();

		
		RptImsAppServDTO rptImsAppServDTO = new RptImsAppServDTO();
		RptImsAppServDTO rptImsAppServCustCopyDTO = new RptImsAppServDTO();
		RptIms3PartyCreditCardDTO rptIms3PartyCreditCardDTO = new RptIms3PartyCreditCardDTO();

		Boolean isShowAllForTest = false;
		if(this.isAFShowALL(pDTOs)&&this.isAFShowAllByID(pDTOs)){
			isShowAllForTest = true;
		}
		if(isShowAllForTest){
			rptImsAppServDTO.setShowAllForTest(true);
			rptImsAppServDTO.setShowRegHKTLoginId(false);
		}
		
		if ("zh".equals(pLang)){
			rptImsAppServDTO.setNetvigatorLogo(imageFilePath + "/"	+ NETVIGATOR_LOGO_FILE_ZH);
			rptImsAppServDTO.setFooterPccwLogo(imageFilePath + "/"	+ FOOTER_PCCW_LOGO_FILE_ZH);
			rptImsAppServDTO.setFooterHktLogo(imageFilePath + "/"	+ FOOTER_HKT_LOGO_FILE_ZH);
		}else{
			rptImsAppServDTO.setNetvigatorLogo(imageFilePath + "/"	+ NETVIGATOR_LOGO_FILE_EN);
			rptImsAppServDTO.setFooterPccwLogo(imageFilePath + "/"	+ FOOTER_PCCW_LOGO_FILE_EN);
			rptImsAppServDTO.setFooterHktLogo(imageFilePath + "/"	+ FOOTER_HKT_LOGO_FILE_EN);
		}	
		rptImsAppServDTO.setCompanyLogo(imageFilePath + "/"	+ COMPANY_LOGO_FILE);
		rptImsAppServCustCopyDTO.setCompanyLogo(imageFilePath + "/"	+ COMPANY_LOGO_FILE);
		rptIms3PartyCreditCardDTO.setCompanyLogo(imageFilePath + "/"	+ COMPANY_LOGO_FILE);

		Object obj = null;
		boolean isReq3PartyCreditCardForm = false;		
		
		ArrayList<RptServiceDetailDTO> coreServiceList = new ArrayList<RptServiceDetailDTO>();
		ArrayList<RptServiceDetailDTO> vasAndOptList = new ArrayList<RptServiceDetailDTO>();
		ArrayList<RptServiceInfoDTO> coreChrgList = new ArrayList<RptServiceInfoDTO>();
		ArrayList<RptServiceInfoDTO> imprtInfoList = new ArrayList<RptServiceInfoDTO>();
		ArrayList<RptServiceInfoDTO> glossaryList = new ArrayList<RptServiceInfoDTO>();
		ArrayList<RptServiceInfoDTO> servPlanList = new ArrayList<RptServiceInfoDTO>();
		ArrayList<RptServiceInfoDTO> serviceProvideList = new ArrayList<RptServiceInfoDTO>();
		ArrayList<RptServiceInfoDTO> personalInfoListEC = new ArrayList<RptServiceInfoDTO>();
		ArrayList<RptServiceInfoDTO> personalInfoListNewCustOptIn = new ArrayList<RptServiceInfoDTO>();
		ArrayList<RptServiceInfoDTO> personalInfoListNewCustOptOut = new ArrayList<RptServiceInfoDTO>();
		ArrayList<RptServiceInfoDTO> custAgreeList = new ArrayList<RptServiceInfoDTO>();
		ArrayList<RptServiceInfoDTO> creditCardList = new ArrayList<RptServiceInfoDTO>();
		ArrayList<RptServiceInfoDTO> kisList = new ArrayList<RptServiceInfoDTO>();
		ArrayList<RptServiceInfoDTO> hktServPortalList = new ArrayList<RptServiceInfoDTO>();
		ArrayList<RptServiceInfoDTO> internalUseRemark = new ArrayList<RptServiceInfoDTO>();
		
		if ("zh".equals(pLang)){
			rptImsAppServDTO.setLocale("zh_HK");
		}else{
			rptImsAppServDTO.setLocale("en");
		}	
		
		//RptServiceInfoDTO	tempKIS01	  = new RptServiceInfoDTO();
		RptServiceInfoDTO	tempKIS02	  = new RptServiceInfoDTO();
		RptServiceInfoDTO	tempKIS03	  = new RptServiceInfoDTO();
		RptServiceInfoDTO	tempKIS04	  = new RptServiceInfoDTO();
		RptServiceInfoDTO	tempKIS05	  = new RptServiceInfoDTO();
		RptServiceInfoDTO	tempKIS06	  = new RptServiceInfoDTO();

		
		

		if("en".equals(rptImsAppServDTO.getLocale())){
			//tempKIS01 = (RptServiceInfoDTO) appCtx.getBean("kisEng01");
			tempKIS02 = (RptServiceInfoDTO) appCtx.getBean("kisEng02");
			tempKIS03 = (RptServiceInfoDTO) appCtx.getBean("kisEng03");
			tempKIS04 = (RptServiceInfoDTO) appCtx.getBean("kisEng04");
			tempKIS05 = (RptServiceInfoDTO) appCtx.getBean("kisEng05");
			tempKIS06 = (RptServiceInfoDTO) appCtx.getBean("kisEng06");	
		}else if("zh_HK".equals(rptImsAppServDTO.getLocale())){
			//tempKIS01 = (RptServiceInfoDTO) appCtx.getBean("kisChi01");
			tempKIS02 = (RptServiceInfoDTO) appCtx.getBean("kisChi02");
			tempKIS03 = (RptServiceInfoDTO) appCtx.getBean("kisChi03");
			tempKIS04 = (RptServiceInfoDTO) appCtx.getBean("kisChi04");
			tempKIS05 = (RptServiceInfoDTO) appCtx.getBean("kisChi05");
			tempKIS06 = (RptServiceInfoDTO) appCtx.getBean("kisChi06");			
		};
	     if(dbRptstaticWords!=null){
	    	 tempKIS02.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("kis02"));
	    	 tempKIS03.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("kis03"));
	    	 tempKIS04.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("kis04"));
	    	 tempKIS05.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("kis05"));
	    	 tempKIS06.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("kis06"));
	     };
		//kisList.add(tempKIS01);
		kisList.add(tempKIS02);
		kisList.add(tempKIS03);
		kisList.add(tempKIS04);
		kisList.add(tempKIS05);
		kisList.add(tempKIS06);
		rptImsAppServDTO.setKisDtls(kisList);
		
		for (int i = 0; i < pDTOs.size(); i++) {
			obj = pDTOs.get(i);
			if (obj instanceof OrderImsUI) {
				order = (OrderImsUI) obj;
				

			
				rptImsAppServDTO.setAppNo(order.getOrderId());
				rptIms3PartyCreditCardDTO.setAppNo(order.getOrderId());
				
				
				logger.debug("section A - customer details");
				rptImsAppServDTO.setNewCustInd("Y");
				if (order.getCustomer() != null) {
					if("Y".equalsIgnoreCase(order.getHasBBDailup())){
						rptImsAppServDTO.setNewCustInd("N");
					}else{
						rptImsAppServDTO.setNewCustInd("Y");
					}
					rptImsAppServDTO.setTitle(order.getCustomer().getTitle());
					rptImsAppServDTO.setLastName(order.getCustomer().getLastName());
					rptImsAppServDTO.setFirstName(order.getCustomer().getFirstName());
					if(order.getCustomer().getLastName().length()>13 || order.getCustomer().getFirstName().length()>13){//Gary
						rptImsAppServDTO.setIsNameTooLong(true);
					}else{
						rptImsAppServDTO.setIsNameTooLong(false);
					}
					rptIms3PartyCreditCardDTO.setCustLastName(order.getCustomer().getLastName());
					rptIms3PartyCreditCardDTO.setCustFirstName(order.getCustomer().getFirstName());
					rptImsAppServDTO.setDob(Utility.date2String(order.getCustomer().getDob(),"dd/MM/yyyy"));
					rptImsAppServDTO.setIdDocType(order.getCustomer().getIdDocType());
					rptImsAppServDTO.setIdDocVerifyInd(order.getCustomer().getIdVerified());
					rptImsAppServDTO.setIdDocNum(order.getCustomer().getIdDocNum());
					//Gary
					rptImsAppServDTO.setCompanyName(order.getCustomer().getCompanyName());
					//isCC
					rptImsAppServDTO.setIsCC(isCC);
					//end
					rptIms3PartyCreditCardDTO.setIdDocType(order.getCustomer().getIdDocType());
					rptIms3PartyCreditCardDTO.setIdDocNum(order.getCustomer().getIdDocNum());
					if (order.getCustomer().getContact() != null) { 
						String custContact = order.getCustomer().getContact().getContactPhone();
						if(custContact!= null && custContact.length()>=8){
							custContact = custContact.substring(0, 8);					
						}
						rptImsAppServDTO.setContactPhone(custContact);				
						rptImsAppServDTO.setContactEmail(order.getCustomer().getContact().getEmailAddr());
						rptIms3PartyCreditCardDTO.setContactPhone(custContact);				
						rptImsAppServDTO.setOtherPhone(order.getCustomer().getContact().getOtherPhone());
					}
				}
		
				if (order.getFixedLineNo() == null || (order.getFixedLineNo().isEmpty())) {
					rptImsAppServDTO.setFixedLineNumPccwInd("N");
				} else {
					rptImsAppServDTO.setFixedLineNumPccwInd("Y");
				}
				rptImsAppServDTO.setFixedLineNum(order.getFixedLineNo());
		
				if (order.getCustomer() != null) {
					if (order.getCustomer().getAccount() != null) {
						rptImsAppServDTO.setEmailAddr(order.getCustomer().getAccount().getEmailAddr());				
					}
				}
				logger.debug("section A - installation address");
				if (order.getInstallAddress() != null) {
					rptImsAppServDTO.setFlat(order.getInstallAddress().getUnitNo());
					rptImsAppServDTO.setFloor(order.getInstallAddress().getFloorNo());
					rptImsAppServDTO.setBuildingName(order.getInstallAddress().getBuildNo());
					rptImsAppServDTO.setLotNum(order.getInstallAddress().getHiLotNo());
					rptImsAppServDTO.setStreetNum(order.getInstallAddress().getStrNo());
					rptImsAppServDTO.setStreetName(order.getInstallAddress().getStrName());
					rptImsAppServDTO.setStreetCatgDesc(order.getInstallAddress().getStrCatDesc());
					rptImsAppServDTO.setSectionDesc(order.getInstallAddress().getSectDesc());
					rptImsAppServDTO.setDistrictDesc(order.getInstallAddress().getDistDesc());
					rptImsAppServDTO.setAreaCode(order.getInstallAddress().getAreaCd());
				}
				logger.debug("section A - billing address");
				if ( order.getBillingAddress() == null || (order.getBillingAddress().getHiLotNo().equals("") && order.getBillingAddress().getBuildNo().equals("") && order.getBillingAddress().getStrNo().equals(""))) {
					rptImsAppServDTO.setNoBillingAddressFlag(false); 
				} else {
					rptImsAppServDTO.setNoBillingAddressFlag(true); 
				}
				if (rptImsAppServDTO.isNoBillingAddressFlag()) {
					rptImsAppServDTO.setBillingFlat(order.getBillingAddress().getUnitNo());
					rptImsAppServDTO.setBillingFloor(order.getBillingAddress().getFloorNo());
					rptImsAppServDTO.setBillingBuildingName(order.getBillingAddress().getBuildNo());
					rptImsAppServDTO.setBillingLotNum(order.getBillingAddress().getHiLotNo());
					rptImsAppServDTO.setBillingStreetNum(order.getBillingAddress().getStrNo());
					rptImsAppServDTO.setBillingStreetName(order.getBillingAddress().getStrName());
					rptImsAppServDTO.setBillingStreetCatgDesc(order.getBillingAddress().getStrCatDesc());
					rptImsAppServDTO.setBillingSectionDesc(order.getBillingAddress().getSectDesc());
					rptImsAppServDTO.setBillingDistrictDesc(order.getBillingAddress().getDistDesc());
					rptImsAppServDTO.setBillingAreaCode(order.getBillingAddress().getAreaCd());
				}
		
				logger.debug("section B - service provider");
				if(dbRptstaticWords!=null){
					serviceProvideList.add(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("serviceProvide01"));
				}else{
					if ("en".equals(rptImsAppServDTO.getLocale())) {					
						serviceProvideList.add((RptServiceInfoDTO) appCtx.getBean("serviceProvideEng01"));
					} else if ("zh_HK".equals(rptImsAppServDTO.getLocale())) {
						serviceProvideList.add((RptServiceInfoDTO) appCtx.getBean("serviceProvideChi01"));
					}
				}
					
				
				rptImsAppServDTO.setServiceProvideDtls(serviceProvideList);
				logger.debug("section C - core service");
				String startTime, endTime, ampm;

				rptImsAppServDTO.setPreInstInd(order.getPreInstallInd());
				rptImsAppServDTO.setMobileOfferInd(order.getMobileOfferInd());
				
				logger.debug(">>Total Term=" + order.getWarrPeriod() + " >>Exten Term=" + order.getTermExt());
				if (order.isMonthlyPlan().equalsIgnoreCase("Y")) {
					rptImsAppServDTO.setFixedTerm("");
					rptImsAppServDTO.setExtenTerm("");
					rptImsAppServDTO.setTotalTerm("");
				} else {
					if (order.getTermExt() == null || order.getTermExt() == "" || (Integer.parseInt(order.getTermExt())==0)) {
						rptImsAppServDTO.setFixedTerm(order.getWarrPeriod());
						rptImsAppServDTO.setExtenTerm("");
						rptImsAppServDTO.setTotalTerm("");
					} else {
						totalTerm = Integer.parseInt(order.getWarrPeriod());
						extenTerm = Integer.parseInt(order.getTermExt());
						fixedTerm = totalTerm - extenTerm;
						rptImsAppServDTO.setTotalTerm(order.getWarrPeriod());
						rptImsAppServDTO.setExtenTerm(order.getTermExt());
						rptImsAppServDTO.setFixedTerm(((fixedTerm < 0)?"":fixedTerm+""));
					}
				}
				
				if (order.getAppointment() != null && order.getAppointment().getAppntEndDateDisplay() != null) {					
					String tempTargetInstallDate;
					if(cOrder){
						tempTargetInstallDate=Utility.date2String(order.getAppointment().getAppntStartDate(), "dd/MM/yyyy");
					}else{
						tempTargetInstallDate=Utility.date2String(order.getAppointment().getAppntEndDateDisplay(), "dd/MM/yyyy");
						//order.getAppointment().getAppntStartDate() T+7
						//order.getAppointment().getAppntEndDateDisplay() T+30
					}
					rptImsAppServDTO.setTargetInstallDate(tempTargetInstallDate);
					if (order.isBookingNotAllowed().equalsIgnoreCase("Y")) {
						if(cOrder&&"Y".equalsIgnoreCase(order.getInstallAddress().getAddrInventory().getResourceShortage())){
							rptImsAppServDTO.setTargetInstallTimeSlot("10:00-18:00");
						}
					} else {						
						startTime = Utility.date2String(order.getAppointment().getAppntStartDateDisplay(), "h");
						endTime = Utility.date2String(order.getAppointment().getAppntEndDateDisplay(), "h");
						
						if(Integer.parseInt(Utility.date2String(order.getAppointment().getAppntStartDateDisplay(), "H"))==9 &&
								Integer.parseInt(Utility.date2String(order.getAppointment().getAppntEndDateDisplay(), "H"))==13 ){
							startTime="10";
							endTime="1";								
						}else if(Integer.parseInt(Utility.date2String(order.getAppointment().getAppntStartDateDisplay(), "H"))==9 &&
								Integer.parseInt(Utility.date2String(order.getAppointment().getAppntEndDateDisplay(), "H"))==18 ){
							startTime="10";
							endTime="6";	
						}
						
						if (Integer.parseInt(Utility.date2String(order.getAppointment().getAppntEndDateDisplay(), "H")) < 12) {
							ampm = "am";
						} else {
							ampm = "pm"; 
						}
//						if("zh_HK".equals(rptImsAppServDTO.getLocale()))//Gary for installation time display
//							if(Integer.parseInt(Utility.date2String(order.getAppointment().getAppntStartDateDisplay(), "H")) < 12){
//								if(Integer.parseInt(Utility.date2String(order.getAppointment().getAppntEndDateDisplay(), "H")) < 12)
//									rptImsAppServDTO.setTargetInstallTimeSlot(am_zh + startTime + "-" + endTime);
//								else
//									rptImsAppServDTO.setTargetInstallTimeSlot(am_zh + startTime + "-" + pm_zh +endTime);
//							}else
//								rptImsAppServDTO.setTargetInstallTimeSlot(pm_zh + startTime + "-" + endTime);
//						
//							//rptImsAppServDTO.setTargetInstallTimeSlot(((Integer.parseInt(Utility.date2String(order.getAppointment().getAppntStartDateDisplay(), "H")) < 12)?am_zh:pm_zh) + startTime + "-" + ((Integer.parseInt(Utility.date2String(order.getAppointment().getAppntEndDateDisplay(), "H")) < 12)?am_zh:pm_zh) + endTime);
//						else if("en".equals(rptImsAppServDTO.getLocale()))
							rptImsAppServDTO.setTargetInstallTimeSlot(startTime + "-" + endTime + ampm);
					}
				}
				if (order.getTargetCommDate() == null) {
					rptImsAppServDTO.setTargetCommDate(Utility.date2String(order.getAppointment().getAppntEndDateDisplay(), "dd/MM/yyyy"));
					if("Y".equalsIgnoreCase(order.getInstallAddress().getAddrInventory().getResourceShortage())&& !"Y".equalsIgnoreCase(order.getPreInstallInd())){				
						rptImsAppServDTO.setTargetCommDate(Utility.date2String(order.getAppointment().getAppntStartDate(), "dd/MM/yyyy"));
					}
				} else {
					rptImsAppServDTO.setTargetCommDate(Utility.date2String(order.getTargetCommDate(), "dd/MM/yyyy"));
				}
				rptImsAppServDTO.setLoginId(order.getLoginId() + "@netvigator.com");
				rptImsAppServDTO.setBillingEmailAddr(order.getCustomer().getAccount().getEmailAddr());

				if(!cOrder&&"Y".equalsIgnoreCase(order.getInstallAddress().getAddrInventory().getResourceShortage())){
					rptImsAppServDTO.setTargetInstallDate(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("appointmentInsuffResource").getItemHtml());
					rptImsAppServDTO.setTargetInstallTimeSlot(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("appointmentInsuffResource").getItemHtml());
					rptImsAppServDTO.setTargetCommDate(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("appointmentInsuffResource").getItemHtml());
				}
		
				// core service description
				rptImsAppServDTO.setBandwidth(order.getBandwidth() + "M");


				// section D - optional premium, optional services and values added services
				// in part 2
		
				// section E - gifts and premiums
				// in part 2
		
				logger.debug("section F - charges for core services / optional services / values added services / miscellaneous");
				

				RptServiceInfoDTO tempCoreCharge01 = new RptServiceInfoDTO();
				RptServiceInfoDTO tempCoreCharge02A = new RptServiceInfoDTO();
				RptServiceInfoDTO tempCoreCharge02B = new RptServiceInfoDTO();
				RptServiceInfoDTO tempCoreCharge03A = new RptServiceInfoDTO();
				RptServiceInfoDTO tempCoreCharge03AForShowAll = new RptServiceInfoDTO();
				RptServiceInfoDTO tempCoreCharge03C = new RptServiceInfoDTO();
				RptServiceInfoDTO tempCoreCharge03D = new RptServiceInfoDTO();
				RptServiceInfoDTO tempCoreCharge03E = new RptServiceInfoDTO();
				RptServiceInfoDTO tempCoreCharge03F = new RptServiceInfoDTO();
				RptServiceInfoDTO tempCoreCharge03G = new RptServiceInfoDTO();
				RptServiceInfoDTO tempCoreCharge04 = new RptServiceInfoDTO();
				RptServiceInfoDTO tempCoreCharge05C = new RptServiceInfoDTO();
				RptServiceInfoDTO tempCoreCharge06 = new RptServiceInfoDTO();
				RptServiceInfoDTO tempCoreCharge07 = new RptServiceInfoDTO();
				RptServiceInfoDTO tempCoreCharge08 = new RptServiceInfoDTO();
				RptServiceInfoDTO tempCoreCharge09 = new RptServiceInfoDTO();
				RptServiceInfoDTO tempCoreCharge10 = new RptServiceInfoDTO();
				RptServiceInfoDTO tempCoreCharge11 = new RptServiceInfoDTO();
				
				if("en".equals(rptImsAppServDTO.getLocale())){
					  tempCoreCharge01 = (RptServiceInfoDTO) appCtx.getBean("coreChargeEng01");
					  tempCoreCharge02A = (RptServiceInfoDTO) appCtx.getBean("coreChargeEng02A");
					  tempCoreCharge02B = (RptServiceInfoDTO) appCtx.getBean("coreChargeEng02B");
			          tempCoreCharge03A =(RptServiceInfoDTO) appCtx.getBean("coreChargeEng03A");//
			          tempCoreCharge03C =(RptServiceInfoDTO) appCtx.getBean("coreChargeEng03C");//Waived
			          tempCoreCharge03D =(RptServiceInfoDTO) appCtx.getBean("coreChargeEng03D");
			          tempCoreCharge04 = (RptServiceInfoDTO) appCtx.getBean("coreChargeEng04");
			          tempCoreCharge05C = (RptServiceInfoDTO) appCtx.getBean("coreChargeEng05C");
			          tempCoreCharge06 =(RptServiceInfoDTO) appCtx.getBean("coreChargeEng06");//
			          tempCoreCharge07 =(RptServiceInfoDTO) appCtx.getBean("coreChargeEng07");//Waived
			          tempCoreCharge08 =(RptServiceInfoDTO) appCtx.getBean("coreChargeEng08");
				      tempCoreCharge09 =(RptServiceInfoDTO) appCtx.getBean("coreChargeEng09");
				      
				}else if("zh_HK".equals(rptImsAppServDTO.getLocale())){

					  tempCoreCharge01 = (RptServiceInfoDTO) appCtx.getBean("coreChargeChi01");
					  tempCoreCharge02A = (RptServiceInfoDTO) appCtx.getBean("coreChargeChi02A");
					  tempCoreCharge02B = (RptServiceInfoDTO) appCtx.getBean("coreChargeChi02B");
			          tempCoreCharge03A =(RptServiceInfoDTO) appCtx.getBean("coreChargeChi03A");//
			          tempCoreCharge03C =(RptServiceInfoDTO) appCtx.getBean("coreChargeChi03C");//Waived
			          tempCoreCharge03D =(RptServiceInfoDTO) appCtx.getBean("coreChargeChi03D");
			          tempCoreCharge04 = (RptServiceInfoDTO) appCtx.getBean("coreChargeChi04");
			          tempCoreCharge05C = (RptServiceInfoDTO) appCtx.getBean("coreChargeChi05C");
			          tempCoreCharge06 =(RptServiceInfoDTO) appCtx.getBean("coreChargeChi06");//
			          tempCoreCharge07 =(RptServiceInfoDTO) appCtx.getBean("coreChargeChi07");//Waived
			          tempCoreCharge08 =(RptServiceInfoDTO) appCtx.getBean("coreChargeChi08");
				      tempCoreCharge09 =(RptServiceInfoDTO) appCtx.getBean("coreChargeChi09");
			         
				}
			
			     if(dbRptstaticWords!=null){//setItemHtml left, setItemHtml2 right
					 tempCoreCharge01.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("coreCharge01"));
					 tempCoreCharge01.setItemHtml2(tempCoreCharge01.getItemHtml());
					 tempCoreCharge01.setItemHtml(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("coreChargeTitle01").getItemHtml());
					 
					 tempCoreCharge02A.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("coreCharge02A"));
					 tempCoreCharge02A.setItemHtml2(tempCoreCharge02A.getItemHtml());
					 tempCoreCharge02A.setItemHtml(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("coreChargeTitle02").getItemHtml());
					 
					 tempCoreCharge02B.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("coreCharge02B"));	
					 tempCoreCharge02B.setItemHtml2(tempCoreCharge02B.getItemHtml());
					 tempCoreCharge02B.setItemHtml(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("coreChargeTitle02").getItemHtml());	
					 
		        	 tempCoreCharge03A.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("coreCharge03A"));
		        	 tempCoreCharge03A.setItemHtml2(tempCoreCharge03A.getItemHtml());
					 tempCoreCharge03A.setItemHtml(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("coreChargeTitle03").getItemHtml());
					 tempCoreCharge03AForShowAll.copy(tempCoreCharge03A);
		        	 
		        	 tempCoreCharge03C.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("coreCharge03C"));
		        	 tempCoreCharge03C.setItemHtml2(tempCoreCharge03C.getItemHtml());
		        	 tempCoreCharge03C.setItemHtml(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("coreChargeTitle03").getItemHtml());
		        	 
		        	 tempCoreCharge03D.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("coreCharge03D"));
		        	 tempCoreCharge03D.setItemHtml2(tempCoreCharge03D.getItemHtml());
		        	 tempCoreCharge03D.setItemHtml(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("coreChargeTitle03").getItemHtml());
		        	 
		        	 tempCoreCharge03E.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("coreCharge03E"));
		        	 tempCoreCharge03E.setItemHtml2(tempCoreCharge03E.getItemHtml());
		        	 tempCoreCharge03E.setItemHtml(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("coreChargeTitle03").getItemHtml());
		        	 
		        	 tempCoreCharge03F.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("coreCharge03F"));
		        	 tempCoreCharge03F.setItemHtml2(tempCoreCharge03F.getItemHtml());
		        	 tempCoreCharge03F.setItemHtml(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("coreChargeTitle03").getItemHtml());
		        	 
		        	 tempCoreCharge03G.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("coreCharge03G"));
		        	 tempCoreCharge03G.setItemHtml2(tempCoreCharge03G.getItemHtml());
		        	 tempCoreCharge03G.setItemHtml(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("coreChargeTitle03").getItemHtml());

		        	 tempCoreCharge04.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("coreCharge04"));
		        	 tempCoreCharge04.setItemHtml2(tempCoreCharge04.getItemHtml());
		        	 tempCoreCharge04.setItemHtml(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("coreChargeTitle04").getItemHtml());
		        	 
		        	 tempCoreCharge05C.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("coreCharge05C"));
		        	 tempCoreCharge05C.setItemHtml2(tempCoreCharge05C.getItemHtml());
		        	 tempCoreCharge05C.setItemHtml(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("coreChargeTitle05").getItemHtml());
		        	 
		        	 tempCoreCharge06.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("coreCharge06"));
		        	 tempCoreCharge06.setItemHtml2(tempCoreCharge06.getItemHtml());
		        	 tempCoreCharge06.setItemHtml(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("coreChargeTitle06").getItemHtml());
		        	 
		        	 tempCoreCharge07.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("coreCharge07"));
		        	 tempCoreCharge07.setItemHtml2(tempCoreCharge07.getItemHtml());
					 tempCoreCharge07.setItemHtml(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("coreChargeTitle07").getItemHtml());
					 
		        	 tempCoreCharge08.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("coreCharge08"));	
		        	 tempCoreCharge08.setItemHtml2(tempCoreCharge08.getItemHtml());
					 tempCoreCharge08.setItemHtml(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("coreChargeTitle08").getItemHtml());		

		        	 tempCoreCharge09.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("coreCharge09"));
		        	 
		        	 tempCoreCharge10.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("coreCharge10"));
		        	 tempCoreCharge10.setItemHtml2(tempCoreCharge10.getItemHtml());
		        	 tempCoreCharge10.setItemHtml(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("coreChargeTitle10").getItemHtml());
		        	 
		        	 tempCoreCharge11.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("coreCharge11"));
		        	 tempCoreCharge11.setItemHtml2(tempCoreCharge11.getItemHtml());
		        	 tempCoreCharge11.setItemHtml(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("coreChargeTitle11").getItemHtml());
		        	 
			     }
				

				if(isShowAllForTest){//show all

					RptServiceInfoDTO tempCore1 = new RptServiceInfoDTO();
					RptServiceInfoDTO tempCore2 = new RptServiceInfoDTO();
					RptServiceInfoDTO tempCore3 = new RptServiceInfoDTO();
					RptServiceInfoDTO tempCore4 = new RptServiceInfoDTO();
					RptServiceInfoDTO tempCore5 = new RptServiceInfoDTO();
					RptServiceInfoDTO tempCore6 = new RptServiceInfoDTO();
					RptServiceInfoDTO tempCore7 = new RptServiceInfoDTO();
					RptServiceInfoDTO tempCore8 = new RptServiceInfoDTO();
					RptServiceInfoDTO tempCore9 = new RptServiceInfoDTO();
					RptServiceInfoDTO tempCore10 = new RptServiceInfoDTO();
					RptServiceInfoDTO tempCore11 = new RptServiceInfoDTO();
					RptServiceInfoDTO tempCore12 = new RptServiceInfoDTO();
					RptServiceInfoDTO tempCore13 = new RptServiceInfoDTO();
					RptServiceInfoDTO tempCore14 = new RptServiceInfoDTO();
					RptServiceInfoDTO tempCore15 = new RptServiceInfoDTO();
					RptServiceInfoDTO lineBreak = new RptServiceInfoDTO();
					

					tempCore5.setItemHtml("*** 1. Pre-installation ***");
					coreChrgList.add(tempCore5);
					// for pre-installation option only
					if("en".equals(rptImsAppServDTO.getLocale())){
						tempCoreCharge01.setItemHtml2(msg.getMessage("coreChargeDesc01", new Object[] {order.getPrepayAmt()}, null, new Locale("en", "US")));
					}else if("zh_HK".equals(rptImsAppServDTO.getLocale())){
						tempCoreCharge01.setItemHtml2(msg.getMessage("coreChargeDesc01", new Object[] {order.getPrepayAmt()}, null, new Locale("zh", "HK")));
					}
//					logger.debug("tmpServiceInfo.getItemHtml2():"+tempCoreCharge01.getItemHtml2());
				     if(dbRptstaticWords!=null){
				    	 tempCoreCharge01.setItemHtml2(tempCoreCharge01.getItemHtml2().replace("${0}", "$"+order.getPrepayAmt()));
				     }
//					logger.debug("tmpServiceInfo.getItemHtml2():"+tempCoreCharge01.getItemHtml2());
					tempCoreCharge01.setItemHtml2(tempCoreCharge01.getItemHtml2().replace("$", "HK$"));
					coreChrgList.add(tempCoreCharge01);
					tempCore13.setItemHtml("  *** 1. end***");
					coreChrgList.add(tempCore13);
					coreChrgList.add(lineBreak);
					
					
					tempCore6.setItemHtml("*** 2a. Non Monthly Plan without term extension ***");
					coreChrgList.add(tempCore6);
					coreChrgList.add(tempCoreCharge02B);
					tempCore8.setItemHtml("  *** 2a. end***");
					coreChrgList.add(tempCore8);
					coreChrgList.add(lineBreak);
					
					
					
					
					tempCore7.setItemHtml("  *** 2b.  Non Monthly Plan with term extension ***");
					coreChrgList.add(tempCore7);
					coreChrgList.add(tempCoreCharge02A);
					tempCore9.setItemHtml("  *** 2b. end***");
					coreChrgList.add(tempCore9);
					coreChrgList.add(lineBreak);
					
					
					
					
					tempCore1.setItemHtml("  ***  3a.One Time Install Charge waived  ***");
					coreChrgList.add(tempCore1);
			        tempCoreCharge03C.setItemHtml2("HK$"+Integer.valueOf(order.getOtInstallChrg())*-1+" "+tempCoreCharge03C.getItemHtml2());
			        coreChrgList.add(tempCoreCharge03C); 
					tempCore10.setItemHtml("  ***  3a.end  ***");
					coreChrgList.add(tempCore10);
					coreChrgList.add(lineBreak);
					
					
					

					tempCore2.setItemHtml("  ***  3b. not 3a, Installment ***");
					coreChrgList.add(tempCore2);
					if(order.getOtInstallChrg()!=null&&Integer.valueOf(order.getOtInstallChrg())==280){
						tempCoreCharge03AForShowAll.setItemHtml2("HK$ 50 X 6 "+tempCoreCharge03AForShowAll.getItemHtml2());
					}else if(order.getOtInstallChrg()!=null&&Integer.valueOf(order.getOtInstallChrg())==380){
						tempCoreCharge03AForShowAll.setItemHtml2("HK$ 65 X 6 "+tempCoreCharge03AForShowAll.getItemHtml2());
					}else {
						tempCoreCharge03AForShowAll.setItemHtml2("HK$ 114 X 6 "+tempCoreCharge03AForShowAll.getItemHtml2());
					}
			         coreChrgList.add(tempCoreCharge03AForShowAll); 
						tempCore11.setItemHtml("  ***  3b.end  ***");
						coreChrgList.add(tempCore11);
						coreChrgList.add(lineBreak);
			         
			         
					tempCore3.setItemHtml("  ***  3c. 3c Will appear if not 3a and not 3b ***");
					coreChrgList.add(tempCore3);
			         tempCoreCharge03A.setItemHtml2("HK$"+Integer.valueOf(order.getOtInstallChrg()));
			         coreChrgList.add(tempCoreCharge03A);  
						tempCore12.setItemHtml("  ***  3c.end  ***");
						coreChrgList.add(tempCore12);
						coreChrgList.add(lineBreak);
			         
			         

			         tempCoreCharge03D.setItemHtml("");
			         coreChrgList.add(tempCoreCharge03D);
			         
			         
					tempCore4.setItemHtml("*** 4.The below statment will only appear for Pre-installation ***");
					coreChrgList.add(tempCore4);
					coreChrgList.add(tempCoreCharge04);
					tempCore14.setItemHtml("  ***  4.end  ***");
					coreChrgList.add(tempCore14);
					coreChrgList.add(lineBreak);
					
					

				}else{//normal flow
					

					
					if (order.isPreinstallation().equalsIgnoreCase("Y")) {	// for pre-installation option only
						if("en".equals(rptImsAppServDTO.getLocale())){
							tempCoreCharge01.setItemHtml2(msg.getMessage("coreChargeDesc01", new Object[] {order.getPrepayAmt()}, null, new Locale("en", "US")));
						}else if("zh_HK".equals(rptImsAppServDTO.getLocale())){
							tempCoreCharge01.setItemHtml2(msg.getMessage("coreChargeDesc01", new Object[] {order.getPrepayAmt()}, null, new Locale("zh", "HK")));
						}
//						logger.debug("tmpServiceInfo.getItemHtml2():"+tempCoreCharge01.getItemHtml2());
					     if(dbRptstaticWords!=null){
					    	 tempCoreCharge01.setItemHtml2(tempCoreCharge01.getItemHtml2().replace("${0}", "$"+order.getPrepayAmt()));
					     }
//						logger.debug("tmpServiceInfo.getItemHtml2():"+tempCoreCharge01.getItemHtml2());
						tempCoreCharge01.setItemHtml2(tempCoreCharge01.getItemHtml2().replace("$", "HK$"));
						coreChrgList.add(tempCoreCharge01);
					}
					logger.debug("section F - isMonthlyPlan");
					
					if (!order.isMonthlyPlan().equalsIgnoreCase("Y")) {		// for all except monthly plan
						if (order.getTermExt() == null || order.getTermExt() == "" || (Integer.parseInt(order.getTermExt())==0)) { // without term extension
							coreChrgList.add(tempCoreCharge02B);
						} else { // with term extension
							coreChrgList.add(tempCoreCharge02A);
						}
					}
					
					if("Y".equalsIgnoreCase(order.getPreInstallInd())){
						coreChrgList.add(tempCoreCharge10);
					}
					
					if("Y".equalsIgnoreCase(order.getPreUseInd())){
						coreChrgList.add(tempCoreCharge11);
					}
					
				if(order.getOtInstallChrg()==null||order.getOtInstallChrg().isEmpty()){
					//mass project WAIVED case
					tempCoreCharge03C.setItemHtml2(tempCoreCharge03C.getItemHtml2());
					coreChrgList.add(tempCoreCharge02A);
					coreChrgList.add(tempCoreCharge03C); 
				}else if("0".equalsIgnoreCase(order.getOtInstallChrg())){
					tempCoreCharge03C.setItemHtml2(tempCoreCharge03C.getItemHtml2());
					coreChrgList.add(tempCoreCharge02A);
					coreChrgList.add(tempCoreCharge03C); 
				}else{
					 String compForOtInstallChrg ;
			         if("zh_HK".equals(rptImsAppServDTO.getLocale())){
			        	 compForOtInstallChrg = order.getInstallOTDesc_zh();
			         }else{
			        	 compForOtInstallChrg = order.getInstallOTDesc_en();
			         }
			         if(compForOtInstallChrg==null||compForOtInstallChrg.isEmpty()){
			        	 compForOtInstallChrg = "";
			         }
			     if (Integer.valueOf(order.getOtInstallChrg())<0) {
			        	//logger.debug(Integer.valueOf(order.getOtInstallChrg()));
					coreChrgList.add(tempCoreCharge02A);
					if("B".equalsIgnoreCase(order.getServiceWaiver())){
						tempCoreCharge03C.setItemHtml2("HK$"+Integer.valueOf(order.getOtInstallChrg())*-1+compForOtInstallChrg+" "+tempCoreCharge03E.getItemHtml2());
					}else if("V".equalsIgnoreCase(order.getServiceWaiver())){
						tempCoreCharge03C.setItemHtml2("HK$"+Integer.valueOf(order.getOtInstallChrg())*-1+compForOtInstallChrg+" "+tempCoreCharge03G.getItemHtml2());
					}else if("G".equalsIgnoreCase(order.getServiceWaiver())||"G".equalsIgnoreCase(order.getServiceWaiverNowTVPage())){
						tempCoreCharge03C.setItemHtml2("HK$"+Integer.valueOf(order.getOtInstallChrg())*-1+compForOtInstallChrg+" "+tempCoreCharge03F.getItemHtml2());
					}else{
						tempCoreCharge03C.setItemHtml2("HK$"+Integer.valueOf(order.getOtInstallChrg())*-1+compForOtInstallChrg+" "+tempCoreCharge03C.getItemHtml2());
					}
			         coreChrgList.add(tempCoreCharge03C); 
			         }else if((order.getInstallmentMonth()!=null && order.getInstallmentChrg()!=null)&&
			        		 (Integer.valueOf(order.getInstallmentMonth())>0 && Integer.valueOf(order.getInstallmentChrg())>0)){
//				        	logger.debug("order.getInstallmentMonth():"+order.getInstallmentMonth());
//				        	logger.debug("order.getInstallmentChrg():"+order.getInstallmentChrg());
					         tempCoreCharge03A.setItemHtml2("HK$"+order.getInstallmentChrg()+" X "+order.getInstallmentMonth()+" "+tempCoreCharge03A.getItemHtml2());
					         coreChrgList.add(tempCoreCharge03A); 
				     }else{ 
			        	//logger.debug(Integer.valueOf(order.getOtInstallChrg()));
				     String otCode = "";
				     if(cOrder){
				    	 otCode = " &lt ONE Time Code: "+order.getInstallOtCode()+" / Quantity: "+order.getInstallOtQty()+" >";
				     }
			         tempCoreCharge03A.setItemHtml2("HK$"+Integer.valueOf(order.getOtInstallChrg())+otCode+compForOtInstallChrg);
			         coreChrgList.add(tempCoreCharge03A); 
			        }  
				}

		         tempCoreCharge03D.setItemHtml("");
		         coreChrgList.add(tempCoreCharge03D);
		         
					if (order.isPreinstallation().equalsIgnoreCase("Y")) {	// for pre-installation option only
						coreChrgList.add(tempCoreCharge04);
					}
				}
				
				coreChrgList.add(tempCoreCharge05C);
				coreChrgList.add(tempCoreCharge06);
				coreChrgList.add(tempCoreCharge07);
				coreChrgList.add(tempCoreCharge08); 
		        tempCoreCharge09.setItemHtml2(tempCoreCharge09.getItemHtml());
		        tempCoreCharge09.setItemHtml("");
				coreChrgList.add(tempCoreCharge09);
				rptImsAppServDTO.setCoreChrgDtls(coreChrgList);
				
				
		
				logger.debug("section G - bill preferences");
				if (order.getCustomer() != null) {
					if (order.getCustomer().getAccount() != null) {
						rptImsAppServDTO.setBillMedia(order.getCustomer().getAccount().getBillMedia());  // E - Email, P - Paper
					}
				}
				if(dbRptstaticWords!=null){
					rptImsAppServDTO.setBillPreference01(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("billPreferences01").getItemHtml());
					rptImsAppServDTO.setBillPreference02(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("billPreferences02").getItemHtml());
					rptImsAppServDTO.setBillPreference03(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("billPreferences03").getItemHtml()+order.getMrt()
							+dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("billPreferences04").getItemHtml());
					rptImsAppServDTO.setBillPreference04(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("billPreferences05").getItemHtml());
				}
				
				
				
				logger.debug("section H - important information");
				RptServiceInfoDTO	tempImprtInfo00	  = new RptServiceInfoDTO();
				RptServiceInfoDTO	tempImprtInfo01	  = new RptServiceInfoDTO();
				RptServiceInfoDTO	tempImprtInfo02	  = new RptServiceInfoDTO();
				RptServiceInfoDTO	tempImprtInfo03	  = new RptServiceInfoDTO();
				RptServiceInfoDTO	tempImprtInfo04	  = new RptServiceInfoDTO();
				RptServiceInfoDTO	tempImprtInfo05	  = new RptServiceInfoDTO();
				RptServiceInfoDTO	tempImprtInfo06	  = new RptServiceInfoDTO();
				RptServiceInfoDTO	tempImprtInfo07	  = new RptServiceInfoDTO();
				RptServiceInfoDTO	tempImprtInfo08	  = new RptServiceInfoDTO();
				RptServiceInfoDTO	tempImprtInfo09	  = new RptServiceInfoDTO();
				RptServiceInfoDTO	tempImprtInfo10	  = new RptServiceInfoDTO();
				RptServiceInfoDTO	tempImprtInfo11	  = new RptServiceInfoDTO();
				RptServiceInfoDTO	tempImprtInfo12	  = new RptServiceInfoDTO();
				RptServiceInfoDTO	tempImprtInfo13	  = new RptServiceInfoDTO();
				RptServiceInfoDTO	tempImprtInfo14	  = new RptServiceInfoDTO();
				RptServiceInfoDTO	tempImprtInfo15	  = new RptServiceInfoDTO();
				RptServiceInfoDTO	tempImprtInfo16	  = new RptServiceInfoDTO();
				RptServiceInfoDTO	tempImprtInfo17	  = new RptServiceInfoDTO();
				RptServiceInfoDTO	tempImprtInfo18	  = new RptServiceInfoDTO();
				RptServiceInfoDTO	tempImprtInfo19	  = new RptServiceInfoDTO();
				RptServiceInfoDTO	tempImprtInfo20	  = new RptServiceInfoDTO();				
				RptServiceInfoDTO	tempImprtInfo21	  = new RptServiceInfoDTO();

				
				if ("en".equals(rptImsAppServDTO.getLocale())) {	
					tempImprtInfo00=(RptServiceInfoDTO) appCtx.getBean("imprtInfoEng00");
					tempImprtInfo01=(RptServiceInfoDTO) appCtx.getBean("imprtInfoEng01");
					tempImprtInfo02=(RptServiceInfoDTO) appCtx.getBean("imprtInfoEng02");
					tempImprtInfo03=(RptServiceInfoDTO) appCtx.getBean("imprtInfoEng03");
					tempImprtInfo04=(RptServiceInfoDTO) appCtx.getBean("imprtInfoEng04phhos");//BomWebPortal dont have this logic
					tempImprtInfo05=(RptServiceInfoDTO) appCtx.getBean("imprtInfoEng05");
					tempImprtInfo06=(RptServiceInfoDTO) appCtx.getBean("imprtInfoEng06");
					tempImprtInfo07=(RptServiceInfoDTO) appCtx.getBean("imprtInfoEng07");
					tempImprtInfo08=(RptServiceInfoDTO) appCtx.getBean("imprtInfoEng08");
					tempImprtInfo09=(RptServiceInfoDTO) appCtx.getBean("imprtInfoEng09");
					tempImprtInfo10=(RptServiceInfoDTO) appCtx.getBean("imprtInfoEng10");
					tempImprtInfo11=(RptServiceInfoDTO) appCtx.getBean("imprtInfoEng11");
					tempImprtInfo12=(RptServiceInfoDTO) appCtx.getBean("imprtInfoEng12");
					tempImprtInfo13=(RptServiceInfoDTO) appCtx.getBean("imprtInfoEng13");
					tempImprtInfo14=(RptServiceInfoDTO) appCtx.getBean("imprtInfoEng14");
					tempImprtInfo15=(RptServiceInfoDTO) appCtx.getBean("imprtInfoEng15");
					tempImprtInfo16=(RptServiceInfoDTO) appCtx.getBean("imprtInfoEng16");
					tempImprtInfo17=(RptServiceInfoDTO) appCtx.getBean("imprtInfoEng17");
					tempImprtInfo18=(RptServiceInfoDTO) appCtx.getBean("imprtInfoEng18");
					tempImprtInfo19=(RptServiceInfoDTO) appCtx.getBean("imprtInfoEng19");
					tempImprtInfo20=(RptServiceInfoDTO) appCtx.getBean("imprtInfoEng20");
					tempImprtInfo21=(RptServiceInfoDTO) appCtx.getBean("imprtInfoEng21");

				}else if ("zh_HK".equals(rptImsAppServDTO.getLocale())) {
					tempImprtInfo00=(RptServiceInfoDTO) appCtx.getBean("imprtInfoChi00");
					tempImprtInfo01=(RptServiceInfoDTO) appCtx.getBean("imprtInfoChi01");
					tempImprtInfo02=(RptServiceInfoDTO) appCtx.getBean("imprtInfoChi02");
					tempImprtInfo03=(RptServiceInfoDTO) appCtx.getBean("imprtInfoChi03");
					tempImprtInfo04=(RptServiceInfoDTO) appCtx.getBean("imprtInfoChi04phhos");//BomWebPortal dont have this logic
					tempImprtInfo05=(RptServiceInfoDTO) appCtx.getBean("imprtInfoChi05");
					tempImprtInfo06=(RptServiceInfoDTO) appCtx.getBean("imprtInfoChi06");
					tempImprtInfo07=(RptServiceInfoDTO) appCtx.getBean("imprtInfoChi07");
					tempImprtInfo08=(RptServiceInfoDTO) appCtx.getBean("imprtInfoChi08");
					tempImprtInfo09=(RptServiceInfoDTO) appCtx.getBean("imprtInfoChi09");
					tempImprtInfo10=(RptServiceInfoDTO) appCtx.getBean("imprtInfoChi10");
					tempImprtInfo11=(RptServiceInfoDTO) appCtx.getBean("imprtInfoChi11");
					tempImprtInfo12=(RptServiceInfoDTO) appCtx.getBean("imprtInfoChi12");
					tempImprtInfo13=(RptServiceInfoDTO) appCtx.getBean("imprtInfoChi13");
					tempImprtInfo14=(RptServiceInfoDTO) appCtx.getBean("imprtInfoChi14");
					tempImprtInfo15=(RptServiceInfoDTO) appCtx.getBean("imprtInfoChi15");
					tempImprtInfo16=(RptServiceInfoDTO) appCtx.getBean("imprtInfoChi16");
					tempImprtInfo17=(RptServiceInfoDTO) appCtx.getBean("imprtInfoChi17");
					tempImprtInfo18=(RptServiceInfoDTO) appCtx.getBean("imprtInfoChi18");
					tempImprtInfo19=(RptServiceInfoDTO) appCtx.getBean("imprtInfoChi19");
					tempImprtInfo20=(RptServiceInfoDTO) appCtx.getBean("imprtInfoChi20");
					tempImprtInfo21=(RptServiceInfoDTO) appCtx.getBean("imprtInfoChi21");
				}
			     if(dbRptstaticWords!=null){
			    	 tempImprtInfo00.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("imprtInfo00"));
			    	 tempImprtInfo01.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("imprtInfo01"));
			    	 tempImprtInfo02.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("imprtInfo02"));
			    	 tempImprtInfo03.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("imprtInfo03"));
			    	 tempImprtInfo04.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("imprtInfo04phhos"));
			    	 tempImprtInfo05.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("imprtInfo05"));
			    	 tempImprtInfo06.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("imprtInfo06"));
			    	 tempImprtInfo07.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("imprtInfo07"));
			    	 tempImprtInfo08.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("imprtInfo08"));
			    	 tempImprtInfo09.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("imprtInfo09"));
			    	 tempImprtInfo10.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("imprtInfo10"));
			    	 tempImprtInfo11.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("imprtInfo11"));
			    	 tempImprtInfo12.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("imprtInfo12"));
			    	 tempImprtInfo13.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("imprtInfo13"));
			    	 tempImprtInfo14.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("imprtInfo14"));
			    	 tempImprtInfo15.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("imprtInfo15"));
			    	 tempImprtInfo16.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("imprtInfo16"));
			    	 tempImprtInfo17.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("imprtInfo17"));
			    	 tempImprtInfo18.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("imprtInfo18"));
			    	 tempImprtInfo19.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("imprtInfo19"));
			    	 tempImprtInfo20.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("imprtInfo20"));
			    	 tempImprtInfo21.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("imprtInfo21"));
			     }
				
				imprtInfoList.add(tempImprtInfo00);
				imprtInfoList.add(tempImprtInfo01);
				imprtInfoList.add(tempImprtInfo21);
				imprtInfoList.add(tempImprtInfo02);
				imprtInfoList.add(tempImprtInfo03);
				imprtInfoList.add(tempImprtInfo04);
				imprtInfoList.add(tempImprtInfo05);
				imprtInfoList.add(tempImprtInfo06);
				imprtInfoList.add(tempImprtInfo07);
				imprtInfoList.add(tempImprtInfo08);
				imprtInfoList.add(tempImprtInfo09);
				imprtInfoList.add(tempImprtInfo10);
				imprtInfoList.add(tempImprtInfo11);
				imprtInfoList.add(tempImprtInfo12);
				imprtInfoList.add(tempImprtInfo13);
				imprtInfoList.add(tempImprtInfo14);
				imprtInfoList.add(tempImprtInfo15);
				imprtInfoList.add(tempImprtInfo16);
				imprtInfoList.add(tempImprtInfo17);
				imprtInfoList.add(tempImprtInfo18);
				imprtInfoList.add(tempImprtInfo19);
				imprtInfoList.add(tempImprtInfo20);
				rptImsAppServDTO.setImprtInfoDtls(imprtInfoList);
		
				logger.debug("section I - glossary and other information");
				RptServiceInfoDTO tempGlossary01  = new RptServiceInfoDTO();
				RptServiceInfoDTO tempGlossary00  = new RptServiceInfoDTO();
				RptServiceInfoDTO tempGlossary02  = new RptServiceInfoDTO();
				RptServiceInfoDTO tempGlossary03  = new RptServiceInfoDTO();
				RptServiceInfoDTO tempGlossary04  = new RptServiceInfoDTO();
				RptServiceInfoDTO tempGlossary05  = new RptServiceInfoDTO();
				RptServiceInfoDTO tempGlossary06  = new RptServiceInfoDTO();
				RptServiceInfoDTO tempGlossary07  = new RptServiceInfoDTO();
				RptServiceInfoDTO tempGlossary08  = new RptServiceInfoDTO();
				RptServiceInfoDTO tempGlossary09  = new RptServiceInfoDTO();
				
				if ("en".equals(rptImsAppServDTO.getLocale())) {	
					tempGlossary01=(RptServiceInfoDTO) appCtx.getBean("glossaryEng01");
					tempGlossary02=(RptServiceInfoDTO) appCtx.getBean("glossaryEng02");
					tempGlossary03=(RptServiceInfoDTO) appCtx.getBean("glossaryEng03");
					tempGlossary04=(RptServiceInfoDTO) appCtx.getBean("glossaryEng04");
					tempGlossary05=(RptServiceInfoDTO) appCtx.getBean("glossaryEng05");
					tempGlossary06=(RptServiceInfoDTO) appCtx.getBean("glossaryEng06");
					tempGlossary07=(RptServiceInfoDTO) appCtx.getBean("glossaryEng07");
				}else if ("zh_HK".equals(rptImsAppServDTO.getLocale())) {
					tempGlossary01=(RptServiceInfoDTO) appCtx.getBean("glossaryChi01");
					tempGlossary02=(RptServiceInfoDTO) appCtx.getBean("glossaryChi02");
					tempGlossary03=(RptServiceInfoDTO) appCtx.getBean("glossaryChi03");
					tempGlossary04=(RptServiceInfoDTO) appCtx.getBean("glossaryChi04");
					tempGlossary05=(RptServiceInfoDTO) appCtx.getBean("glossaryChi05");
					tempGlossary06=(RptServiceInfoDTO) appCtx.getBean("glossaryChi06");
					tempGlossary07=(RptServiceInfoDTO) appCtx.getBean("glossaryChi07");
				}

			     if(dbRptstaticWords!=null){
			    	 tempGlossary01.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("glossary01"));
			    	 tempGlossary02.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("glossary02"));
			    	 tempGlossary00.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("glossary"));
			    	 tempGlossary03.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("glossary03"));//Gary myhkt glossary
			    	 tempGlossary04.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("glossary04"));
			    	 tempGlossary05.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("glossary05"));
			    	 tempGlossary06.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("glossary06"));
			    	 tempGlossary07.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("glossary07"));
			    	 tempGlossary08.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("glossary08"));
			    	 tempGlossary09.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("glossary09"));
			     }
				

				if(isShowAllForTest){//show all
					RptServiceInfoDTO tempGlossary01a  = new RptServiceInfoDTO();
					RptServiceInfoDTO tempGlossary01aEnd  = new RptServiceInfoDTO();
					RptServiceInfoDTO tempGlossary02a  = new RptServiceInfoDTO();
					RptServiceInfoDTO tempGlossary02aEnd  = new RptServiceInfoDTO();
					tempGlossary01a.setItemHtml("*** 1.The below Statement will only appear if Not Monthly Plan ***");
					glossaryList.add(tempGlossary01a);
					glossaryList.add(tempGlossary01);
					tempGlossary01aEnd.setItemHtml("*** 1.end ***");
					glossaryList.add(tempGlossary01aEnd);
					
					tempGlossary02a.setItemHtml("*** 2.The below Statement will only appear if term extension >0 ***");
					glossaryList.add(tempGlossary02a);
					glossaryList.add(tempGlossary02);
					tempGlossary02aEnd.setItemHtml("*** 2.end ***");
					glossaryList.add(tempGlossary02aEnd);

					
				}else{
					if (!order.isMonthlyPlan().equalsIgnoreCase("Y")) {
						glossaryList.add(tempGlossary01);
					}
					if (order.getTermExt() != null && order.getTermExt() != "" && (Integer.parseInt(order.getTermExt()) > 0)) {
						glossaryList.add(tempGlossary02);
					}
				}
				glossaryList.add(tempGlossary00);//Gary
				glossaryList.add(tempGlossary03);
				glossaryList.add(tempGlossary04);
//				if("Y".equalsIgnoreCase(order.getPreInstallInd())){
					glossaryList.add(tempGlossary08);
					glossaryList.add(tempGlossary09);
//				}else{
//					glossaryList.add(tempGlossary05);
//					glossaryList.add(tempGlossary06);
//				}
				if (order.getTermExt() != null && order.getTermExt() != "" && (Integer.parseInt(order.getTermExt()) > 0)) {
					glossaryList.add(tempGlossary07);
				}
				rptImsAppServDTO.setGlossaryDtls(glossaryList);
		

				logger.debug("section K - payment and authorisation");
				Map<String, String> mobileFuturePayment = new HashMap<String, String>();
				try {
					List<Map<String, String>> list = imsReport1Dao.getImsLookUpCode("SB_IMS_INTERCOM");
					if(list!=null){
						for(int p=0; p<list.size(); p++){
							mobileFuturePayment.put(list.get(p).get("code"), list.get(p).get("description"));
						}
					}
					
				} catch (DAOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				rptImsAppServDTO.setMobileFuturePayment("INTERCOM CCC: "+ mobileFuturePayment.get("CCC") + "<br/>END DATE: "+mobileFuturePayment.get("END_DATE"));
				rptImsAppServDTO.setCreditCardDtls(creditCardList);
				if (order.getCustomer() != null) {
					if (order.getCustomer().getAccount() != null) {
						if (order.getCustomer().getAccount().getPayment() != null) {
							if(isShowAllForTest){//Show all cash and credit card
								rptImsAppServDTO.setCreditCardType(order.getCustomer().getAccount().getPayment().getCcType());  // V - Visa, M - Master, AE - AE
								rptImsAppServDTO.setCreditCardHolderName(order.getCustomer().getAccount().getPayment().getCcHoldName());
								rptImsAppServDTO.setCreditCardNum(order.getCustomer().getAccount().getPayment().getCcNum());
								rptImsAppServDTO.setCreditExpiryDate(order.getCustomer().getAccount().getPayment().getCcExpDate());
								logger.debug("getCcExpDate:"+order.getCustomer().getAccount().getPayment().getCcExpDate());
								rptImsAppServDTO.setCreditCardVerifyInd(order.getCustomer().getAccount().getPayment().getCcVerified());
								rptIms3PartyCreditCardDTO.setCreditCardType(order.getCustomer().getAccount().getPayment().getCcType());  // V - Visa, M - Master, AE - AE
								rptIms3PartyCreditCardDTO.setCreditCardHolderName(order.getCustomer().getAccount().getPayment().getCcHoldName());
								rptIms3PartyCreditCardDTO.setCreditCardNum(order.getCustomer().getAccount().getPayment().getCcNum());
								rptIms3PartyCreditCardDTO.setCreditExpiryDate(order.getCustomer().getAccount().getPayment().getCcExpDate());
//								if (isShowAllForTest||order.getCustomer().getAccount().getPayment().getThirdPartyInd().equalsIgnoreCase("Y")) {
//									isReq3PartyCreditCardForm = true;
//								}

								//cash1
								String cash1="***  a.CashFsPrepay  ***<br/>";
							     if(dbRptstaticWords!=null){
							    	 String temp = dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("cashPayDesc03").getItemHtml();
							    	 cash1+=temp.replace("${0}", "$"+order.getPrepayAmt());
							     }else{
										if ("en".equals(rptImsAppServDTO.getLocale())) {
											logger.debug("message : " + msg.getMessage("cashPayDesc03", new Object[] {order.getPrepayAmt()}, null, new Locale("en", "US")));
											cash1+=msg.getMessage("cashPayDesc03", new Object[] {order.getPrepayAmt()}, null, new Locale("en", "US"));
										} else if ("zh_HK".equals(rptImsAppServDTO.getLocale())) { 
											logger.debug("message : " + msg.getMessage("cashPayDesc03", new Object[] {order.getPrepayAmt()}, null, new Locale("zh", "HK")));
											cash1+=msg.getMessage("cashPayDesc03", new Object[] {order.getPrepayAmt()}, null, new Locale("zh", "HK"));
										}
							     }
							     cash1+="<br/>***  a.end  ***<br/>";
									
	
								//cash2
								String cash2="<br/><br/>***  b.not CashFsPrepay and WaivedPrepay  ***<br/>";
								     if(dbRptstaticWords!=null){
								    	 String temp = dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("cashPayDesc01").getItemHtml();
								    	 cash2+=temp;
								     }else{
											if ("en".equals(rptImsAppServDTO.getLocale())) {
												logger.debug("message : " + msg.getMessage("cashPayDesc01", null, null, new Locale("en")));
												cash2+=msg.getMessage("cashPayDesc01", null, null, new Locale("en"));
											} else if ("zh_HK".equals(rptImsAppServDTO.getLocale())) { 
												logger.debug("message : " + msg.getMessage("cashPayDesc01", null, null, new Locale("zh", "HK")));
												cash2+=msg.getMessage("cashPayDesc01", null, null, new Locale("zh", "HK"));
											}
								     }
								     cash2+="<br/>*** b.end  ***<br/>";
								     
								     
								//cash3
								String cash3="<br/><br/>***  c.not CashFsPrepay and not WaivedPrepay  ***<br/>";
							     if(dbRptstaticWords!=null){
							    	 String temp = dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("cashPayDesc02").getItemHtml();
							    	 cash3+=temp.replace("${0}", "$"+order.getPrepayAmt());
							     }else{
										if ("en".equals(rptImsAppServDTO.getLocale())) {
											logger.debug("message : " + msg.getMessage("cashPayDesc02", new Object[] {order.getPrepayAmt()}, null, new Locale("en")));
											cash3+=msg.getMessage("cashPayDesc02", new Object[] {order.getPrepayAmt()}, null, new Locale("en"));
										} else if ("zh_HK".equals(rptImsAppServDTO.getLocale())) { 
											logger.debug("message : " + msg.getMessage("cashPayDesc02", new Object[] {order.getPrepayAmt()}, null, new Locale("zh", "HK")));
											cash3+=msg.getMessage("cashPayDesc02", new Object[] {order.getPrepayAmt()}, null, new Locale("zh", "HK"));
										}
							     }
							     cash3+="<br/>*** c.end  ***<br/>";
							     
							     rptImsAppServDTO.setCashOption(cash1+cash2+cash3);
							     
							}else{//not show all, normal flow
								rptImsAppServDTO.setPayMethodType(order.getCustomer().getAccount().getPayment().getPayMtdType());	// C - Credit Card, M - Cash
								if (order.getCustomer().getAccount().getPayment().getPayMtdType().equalsIgnoreCase("C")) {
									rptImsAppServDTO.setCreditCardType(order.getCustomer().getAccount().getPayment().getCcType());  // V - Visa, M - Master, AE - AE
									rptImsAppServDTO.setCreditCardHolderName(order.getCustomer().getAccount().getPayment().getCcHoldName());
									rptImsAppServDTO.setCreditCardNum(order.getCustomer().getAccount().getPayment().getCcNum());
									rptImsAppServDTO.setCreditExpiryDate(order.getCustomer().getAccount().getPayment().getCcExpDate());
									logger.debug("getCcExpDate:"+order.getCustomer().getAccount().getPayment().getCcExpDate());
									rptImsAppServDTO.setCreditCardVerifyInd(order.getCustomer().getAccount().getPayment().getCcVerified());
									rptIms3PartyCreditCardDTO.setCreditCardType(order.getCustomer().getAccount().getPayment().getCcType());  // V - Visa, M - Master, AE - AE
									rptIms3PartyCreditCardDTO.setCreditCardHolderName(order.getCustomer().getAccount().getPayment().getCcHoldName());
									rptIms3PartyCreditCardDTO.setCreditCardNum(order.getCustomer().getAccount().getPayment().getCcNum());
									rptIms3PartyCreditCardDTO.setCreditExpiryDate(order.getCustomer().getAccount().getPayment().getCcExpDate());
									if (order.getCustomer().getAccount().getPayment().getThirdPartyInd().equalsIgnoreCase("Y")) {
										isReq3PartyCreditCardForm = true;
									}
								} else {
									if ("Y".equalsIgnoreCase(order.getCashFsPrepay())) {
										if ("en".equals(rptImsAppServDTO.getLocale())) {
											logger.debug("message : " + msg.getMessage("cashPayDesc03", new Object[] {order.getPrepayAmt()}, null, new Locale("en", "US")));
											rptImsAppServDTO.setCashOption(msg.getMessage("cashPayDesc03", new Object[] {order.getPrepayAmt()}, null, new Locale("en", "US")));
										} else if ("zh_HK".equals(rptImsAppServDTO.getLocale())) { 
											logger.debug("message : " + msg.getMessage("cashPayDesc03", new Object[] {order.getPrepayAmt()}, null, new Locale("zh", "HK")));
											rptImsAppServDTO.setCashOption(msg.getMessage("cashPayDesc03", new Object[] {order.getPrepayAmt()}, null, new Locale("zh", "HK")));
										}
										String temp;
									     if(dbRptstaticWords!=null){
									    	 if(cOrder)
									    		 temp = dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("cashPayDesc03CC").getItemHtml();
									    	 else
										    	 temp = dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("cashPayDesc03").getItemHtml();

									    	 rptImsAppServDTO.setCashOption(temp.replace("${0}", "$"+order.getPrepayAmt()));
									     }
									} else {
										if ("Y".equalsIgnoreCase(order.getWaivedPrepay())) {
											if ("en".equals(rptImsAppServDTO.getLocale())) {
												logger.debug("message : " + msg.getMessage("cashPayDesc01", null, null, new Locale("en")));
												rptImsAppServDTO.setCashOption(msg.getMessage("cashPayDesc01", null, null, new Locale("en")));
											} else if ("zh_HK".equals(rptImsAppServDTO.getLocale())) { 
												logger.debug("message : " + msg.getMessage("cashPayDesc01", null, null, new Locale("zh", "HK")));
												rptImsAppServDTO.setCashOption(msg.getMessage("cashPayDesc01", null, null, new Locale("zh", "HK")));
											}
										     if(dbRptstaticWords!=null){
										    	 String temp = dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("cashPayDesc01").getItemHtml();
										    	 rptImsAppServDTO.setCashOption(temp);
										     }
										} else {
											if ("en".equals(rptImsAppServDTO.getLocale())) {
												logger.debug("message : " + msg.getMessage("cashPayDesc02", new Object[] {order.getPrepayAmt()}, null, new Locale("en")));
												rptImsAppServDTO.setCashOption(msg.getMessage("cashPayDesc02", new Object[] {order.getPrepayAmt()}, null, new Locale("en")));
											} else if ("zh_HK".equals(rptImsAppServDTO.getLocale())) { 
												logger.debug("message : " + msg.getMessage("cashPayDesc02", new Object[] {order.getPrepayAmt()}, null, new Locale("zh", "HK")));
												rptImsAppServDTO.setCashOption(msg.getMessage("cashPayDesc02", new Object[] {order.getPrepayAmt()}, null, new Locale("zh", "HK")));
											}
										     if(dbRptstaticWords!=null){
										    	 String temp = dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("cashPayDesc02").getItemHtml();
										    	 if(cOrder)
											    	 temp += "<br/><br/><span style='font-weight: bold; font-size: 12pt;'>"+"Pre-generated Account No: "+order.getPrimaryAcctNo()+"</span><br/>";
										    	 rptImsAppServDTO.setCashOption(temp.replace("${0}", "$"+order.getPrepayAmt()));
										     }
										}
									}
								}
							}
						}
					}
				}
				
				RptServiceInfoDTO tempPayment01  = new RptServiceInfoDTO();
				RptServiceInfoDTO tempPayment02  = new RptServiceInfoDTO();
				RptServiceInfoDTO custAgree01  = new RptServiceInfoDTO();
				
				if ("en".equals(rptImsAppServDTO.getLocale())) {	
					tempPayment01=(RptServiceInfoDTO) appCtx.getBean("creditCardEng01");
					tempPayment02=(RptServiceInfoDTO) appCtx.getBean("creditCardEng02");
					custAgree01=(RptServiceInfoDTO) appCtx.getBean("custAgreeEng01");
				}else if ("zh_HK".equals(rptImsAppServDTO.getLocale())) {
					tempPayment01=(RptServiceInfoDTO) appCtx.getBean("creditCardChi01");
					tempPayment02=(RptServiceInfoDTO) appCtx.getBean("creditCardChi02");
					custAgree01=(RptServiceInfoDTO) appCtx.getBean("custAgreeChi01");
				}
			     if(dbRptstaticWords!=null){
			    	 tempPayment01.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("creditCard01"));
			    	 tempPayment02.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("creditCard02"));
			    	 custAgree01.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("custAgree01"));
			     }
				
				if (isReq3PartyCreditCardForm) {						
					rptImsAppServDTO.setThirdPartyCC(true);
					creditCardList.add(tempPayment02);
				}else{			
					rptImsAppServDTO.setThirdPartyCC(false);
					creditCardList.add(tempPayment01);
				}
		
				logger.debug("section M - MY HKT CUSTOMER SERVICE"); //:::
				//Gary added for Printing CS portal in AF Section M 13
				CsPortalAndTheClubAFDisplay(order, rptImsAppServDTO,  dbRptstaticWords);
				//nowid
				setNowIDSection(dbRptstaticWords, rptImsAppServDTO,order);
				
						//Gary end

						
						logger.debug("section N - customer's agreement");
				custAgreeList.add(custAgree01);
				rptImsAppServDTO.setCustAgreeDtls(custAgreeList);
		
		
				// Customer Signature
				rptImsAppServDTO.setAppDate(Utility.date2String(order.getAppDate(), "dd/MM/yyyy"));
				rptIms3PartyCreditCardDTO.setAppDate(Utility.date2String(order.getAppDate(), "dd/MM/yyyy"));
				
				// for internal use
				rptImsAppServDTO.setSalesChannel(order.getSalesChannel());
				rptImsAppServDTO.setCreatedBy(order.getCreateBy());
				rptImsAppServDTO.setSalesCd(order.getSalesCd());
				rptImsAppServDTO.setSalesName(order.getSalesName());
				rptImsAppServDTO.setSalesContactNum(order.getSalesContactNum());
				rptImsAppServDTO.setShopCd(order.getShopCd());
				if (order.getAppointment() == null) {
					rptImsAppServDTO.setUamsNo("");
				} else {
					rptImsAppServDTO.setUamsNo(order.getAppointment().getSerialNum());
				}
				if (order.getCustomer() != null && order.getCustomer().getCustOptInfo() != null) {
					rptImsAppServDTO.setCustOptOutDirectMailingInd(order.getCustomer().getCustOptInfo().getDirectMailing());
					rptImsAppServDTO.setCustOptOutOutBoundInd(order.getCustomer().getCustOptInfo().getOutbound());
					rptImsAppServDTO.setCustOptOutSmsInd(order.getCustomer().getCustOptInfo().getSms());
					rptImsAppServDTO.setCustOptOutEmailInd(order.getCustomer().getCustOptInfo().getEmail());
					rptImsAppServDTO.setCustOptOutWebBillInd(order.getCustomer().getCustOptInfo().getWebBill());
					rptImsAppServDTO.setCustOptOutNonSalesSmsInd(order.getCustomer().getCustOptInfo().getNonsalesSms());
					rptImsAppServDTO.setCustOptOutInternetInd(order.getCustomer().getCustOptInfo().getInternet());
//					logger.debug("DirectMailingInd:"+(order.getCustomer().getCustOptInfo().getDirectMailing()));
//					logger.debug("OutBoundInd:"+(order.getCustomer().getCustOptInfo().getOutbound()));
//					logger.debug("SmsInd:"+(order.getCustomer().getCustOptInfo().getSms()));
//					logger.debug("EmailInd:"+(order.getCustomer().getCustOptInfo().getEmail()));
//					logger.debug("WebBillInd:"+(order.getCustomer().getCustOptInfo().getWebBill()));
//					logger.debug("NonSalesSmsInd:"+(order.getCustomer().getCustOptInfo().getNonsalesSms()));
//					logger.debug("InternetInd:"+(order.getCustomer().getCustOptInfo().getInternet()));
				}

				

				setPersonalAndClubHkt(dbRptstaticWords, order,
						rptImsAppServDTO, isShowAllForTest, personalInfoListEC,
						personalInfoListNewCustOptIn,
						personalInfoListNewCustOptOut);
				
			} else if (obj instanceof ImsRptServicePlanDetailDTO) {
				servicePlan = (ImsRptServicePlanDetailDTO) obj;
				logger.debug("part 2 - DB ServicePlan - Section C - Core Service");
				// for internal use
				rptImsAppServDTO.setProgramCode(servicePlan.getCoreServiceDetail().getOfferCd());
				//header
				RptServiceDetailDTO coreService;
				coreService = new RptServiceDetailDTO();
				
				coreService.setItemRecurrentAmt(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("monthlyRate01").getItemHtml());
				coreService.setItemRecurrentAmt2(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("monthlyRate02").getItemHtml());
			

				coreServiceList.add(coreService);
				
				rptImsAppServDTO.setAppMethod(servicePlan.getAppMethod());
				// Program Item 
				String cOrderOfferCode = "";
				if(cOrder && servicePlan.getCoreServiceDetail().getOfferCd()!=null && 
						!"x".equalsIgnoreCase(servicePlan.getCoreServiceDetail().getOfferCd())){
					cOrderOfferCode = " ( "+servicePlan.getCoreServiceDetail().getOfferCd();
					if(servicePlan.getCoreServiceDetail().getOfferCdOfOther()!=null && servicePlan.getCoreServiceDetail().getOfferCdOfOther()!="null")	
						cOrderOfferCode+=servicePlan.getCoreServiceDetail().getOfferCdOfOther()+" ) ";
					else
						cOrderOfferCode+=" ) ";		
					if("Y".equalsIgnoreCase(order.getPreInstallInd())&&servicePlan.getCoreServiceDetail().getProgItem().getPreInstOfferCd()!=null ){
						cOrderOfferCode+=" ( Pre-installation dummy offer code: "+servicePlan.getCoreServiceDetail().getProgItem().getPreInstOfferCd()+" ) ";
					}	
					if("Y".equalsIgnoreCase(order.getPreUseInd())&&servicePlan.getCoreServiceDetail().getProgItem().getPreUseOfferCd()!=null ){
						cOrderOfferCode+=" ( Pre-use dummy offer code: "+servicePlan.getCoreServiceDetail().getProgItem().getPreUseOfferCd()+" ) ";
					}
					}				
				coreService = new RptServiceDetailDTO();

				coreService.setItemHtml("<b>" +servicePlan.getCoreServiceDetail().getBandwidth_desc() + " " + servicePlan.getCoreServiceDetail().getProgItem().getItemTitle() +cOrderOfferCode+ "</b>");
					
				//Gary

//				logger.debug("servicePlan.getCoreServiceDetail().getProgItem().getContractPeriod():"+servicePlan.getCoreServiceDetail().getProgItem().getContractPeriod());
				if((servicePlan.getCoreServiceDetail().getProgItem().getContractPeriod())>0){
					coreService.setItemRecurrentAmt(servicePlan.getCoreServiceDetail().getProgItem().getItemMthFix());
					coreService.setItemRecurrentAmt2(servicePlan.getCoreServiceDetail().getProgItem().getItemMth2Mth());
				}
				else{
					coreService.setItemRecurrentAmt("");
					coreService.setItemRecurrentAmt2(servicePlan.getCoreServiceDetail().getProgItem().getItemMth2Mth());
				}
					//coreService.setItemRecurrentAmt3(servicePlan.getCoreServiceDetail().getProgItem().getItemMthFix()+" "+dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("monthlyRate01").getItemHtml());

//				slogger.debug("bbbb:"+coreService.getItemRecurrentAmt());
				coreServiceList.add(coreService);
				
				String COP_Service_Plan_Remark = "";
				try {
					COP_Service_Plan_Remark = imsReport1Dao.getCOP_Service_Plan_Remark(servicePlan.getCoreServiceDetail().getOfferCd(),rptImsAppServDTO.getLocale());
				} catch (DAOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				if(COP_Service_Plan_Remark!=null && !COP_Service_Plan_Remark.isEmpty()){
					coreService = new RptServiceDetailDTO();
					coreService.setItemHtml("<b>"+COP_Service_Plan_Remark+"</b><br>");
					coreServiceList.add(coreService);
				}	
				 

				// bandwidth
				RptServiceInfoDTO tempBW01  = new RptServiceInfoDTO();
				
				if ("en".equals(rptImsAppServDTO.getLocale())) {	
					tempBW01=(RptServiceInfoDTO) appCtx.getBean("bandwidthEng01");
				}else if ("zh_HK".equals(rptImsAppServDTO.getLocale())) {
					tempBW01=(RptServiceInfoDTO) appCtx.getBean("bandwidthChi01");
				}
			     if(dbRptstaticWords!=null){
			    	 tempBW01.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("bandwidth01"));
			     }
				coreService = new RptServiceDetailDTO();
					
				coreService.setItemHtml(tempBW01.getItemHtml()+servicePlan.getCoreServiceDetail().getBandwidth_desc());
			
				coreServiceList.add(coreService);

				//pre-install wording
				if("Y".equalsIgnoreCase(order.getPreInstallInd())){
					RptServiceInfoDTO tempPreInst  = new RptServiceInfoDTO();
					
					if(dbRptstaticWords!=null){
						tempPreInst.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("preInstWord"));
				     }
					coreService = new RptServiceDetailDTO();
					coreService.setItemHtml(tempPreInst.getItemHtml());
					coreService.setItemRecurrentAmt(null);
					coreService.setItemRecurrentAmt2(null);
					coreServiceList.add(coreService);
				}
				
				//pre-use wording
				if("Y".equalsIgnoreCase(order.getPreUseInd())){
					RptServiceInfoDTO tempPreUse  = new RptServiceInfoDTO();
					
					if(dbRptstaticWords!=null){
						tempPreUse.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("preUseWord"));
				     }
					coreService = new RptServiceDetailDTO();
					coreService.setItemHtml(tempPreUse.getItemHtml());
					coreService.setItemRecurrentAmt(null);
					coreService.setItemRecurrentAmt2(null);
					coreServiceList.add(coreService);
				}
				
				coreService = new RptServiceDetailDTO();
				String itemDesc = "";
				coreService.setItemId(servicePlan.getCoreServiceDetail().getProgItem().getItemID());
				if (!"".equals(coreService.getItemId()) && coreService.getItemId()!=null) {
					coreService.setItemHtml(itemDesc);
					coreService.setItemRecurrentAmt(null);
					coreService.setItemRecurrentAmt2(null);
					coreServiceList.add(coreService);
				}
				
				// BVAS Mandatory Item 
				coreService = new RptServiceDetailDTO();
				
			    itemDesc = "";
				for (int j=0; j < servicePlan.getCoreServiceDetail().getBvasMandItemList().size(); j++) {
					if (servicePlan.getCoreServiceDetail().getBvasMandItemList().get(j).getItemTitle() != null &&
						servicePlan.getCoreServiceDetail().getBvasMandItemList().get(j).getItemTitle().length() > 0) {
						if (j > 0) {
							itemDesc += " & ";
						}
						itemDesc += servicePlan.getCoreServiceDetail().getBvasMandItemList().get(j).getItemTitle();
						
						
						//MRT number and pin 
						coreService.setItemId(servicePlan.getCoreServiceDetail().getBvasMandItemList().get(j).getItemID());
						/*
						logger.debug("servicePlan.getCoreServiceDetail().getBvasMandItemList().get("+j+"): " + gson.toJson(servicePlan.getCoreServiceDetail().getBvasMandItemList().get(j)));
						
						for(VasParmDTO dto:servicePlan.getCslMrtNumPinList()){
							if(servicePlan.getCoreServiceDetail().getBvasMandItemList().get(j).getItemID().equals(dto.getItem_id())){
								if("Y".equals(dto.getVas_parm_a_af_ind())){
								itemDesc += "<br/>(" +dto.getVas_parm_a_display()+dto.getVas_parm_a() +")";
								}
								if("Y".equals(dto.getVas_parm_b_af_ind())){
								itemDesc += "<br/>(" +dto.getVas_parm_b_display()+dto.getVas_parm_b() +")<br/>";
								}
							}
						}
						*/
						
						if(cOrder){//gary
							if(servicePlan.getCoreServiceDetail().getBvasMandItemList().get(j).getOfferCode()!=null&&
								!"".equals(servicePlan.getCoreServiceDetail().getBvasMandItemList().get(j).getOfferCode())){
								itemDesc += "( " + servicePlan.getCoreServiceDetail().getBvasMandItemList().get(j).getOfferCode()+" )";
							}
							if(servicePlan.getCoreServiceDetail().getBvasMandItemList().get(j).getIncentiveCd()!=null){
									itemDesc += "( " + servicePlan.getCoreServiceDetail().getBvasMandItemList().get(j).getIncentiveCd()+" )";
								}
							
							if("Y".equalsIgnoreCase(order.getPreInstallInd())&&servicePlan.getCoreServiceDetail().getBvasMandItemList().get(j).getPreInstOfferCd()!=null ){
								itemDesc+=" ( Pre-installation dummy offer code: "+servicePlan.getCoreServiceDetail().getBvasMandItemList().get(j).getPreInstOfferCd()+" ) ";
							}
							
							if("Y".equalsIgnoreCase(order.getPreUseInd())&&servicePlan.getCoreServiceDetail().getBvasMandItemList().get(j).getPreUseOfferCd()!=null ){
								itemDesc+=" ( Pre-use dummy offer code: "+servicePlan.getCoreServiceDetail().getBvasMandItemList().get(j).getPreUseOfferCd()+" ) ";
							}
							
							String tempItemId = servicePlan.getCoreServiceDetail().getBvasMandItemList().get(j).getItemID();
							String tempMobileNum = "";
							if(order.getComponents()!=null){
								for(int k = 0; k<order.getComponents().length;k++){
									if(tempItemId!=null && tempItemId.equalsIgnoreCase(order.getComponents()[k].getItemId())){
										logger.debug("Section C Non-Mandatory abt mobile num, item_id:"+tempItemId +" equal Components id:" +order.getComponents()[k].getItemId());
										if(tempMobileNum.equals("")){
											tempMobileNum = order.getComponents()[k].getAttbValue();
										}else{
											tempMobileNum += tempMobileNum + " ," +order.getComponents()[k].getAttbValue();
										}
									}else{
										logger.debug("Section C Non-Mandatory abt mobile num, item_id:"+tempItemId +" not equal Components id:" +order.getComponents()[k].getItemId());
									}
								}
							}
							if(!tempMobileNum.equals("")){
								itemDesc += " (Mobile No: " + tempMobileNum +") ";
								logger.debug("Section C Non-Mandatory abt mobile num, temp_mobile_num is not null:"+tempMobileNum);
							}else{
								logger.debug("Section C Non-Mandatory abt mobile num, temp_mobile_num is null");
							}
						}
					}
				}
				if (itemDesc.length() > 0) {
					itemDesc = "(+ " + itemDesc + ")";
				}
				
				if (itemDesc.length() > 0) {
					coreService.setItemHtml(itemDesc);
					coreService.setItemRecurrentAmt(null);
					coreService.setItemRecurrentAmt2(null);
					coreServiceList.add(coreService);
				}

//				logger.debug("AF order.getComponents():"+gson.toJson(order.getComponents()));//Gary
				// BVAS Non-Mandatory Item 				
				for (int j=0; j < servicePlan.getCoreServiceDetail().getBvasNonMItemList().size(); j++) {
					coreService = new RptServiceDetailDTO();
					if (servicePlan.getCoreServiceDetail().getBvasNonMItemList().get(j).getItemTitle() != null &&
						servicePlan.getCoreServiceDetail().getBvasNonMItemList().get(j).getItemTitle().length() > 0) {
						coreService.setItemHtml("+ " + servicePlan.getCoreServiceDetail().getBvasNonMItemList().get(j).getItemTitle());
					} else {
						coreService.setItemHtml("");
					}
					coreService.setItemId(servicePlan.getCoreServiceDetail().getBvasNonMItemList().get(j).getItemID());
//					logger.debug(gson.toJson(servicePlan));//Gary
					if(cOrder){
						if(servicePlan.getCoreServiceDetail().getBvasNonMItemList().get(j).getOfferCode()!=null&&
							!"".equals(servicePlan.getCoreServiceDetail().getBvasNonMItemList().get(j).getOfferCode())){
							coreService.setItemHtml(coreService.getItemHtml()+"( " + servicePlan.getCoreServiceDetail().getBvasNonMItemList().get(j).getOfferCode()+" )");
						}
						if(servicePlan.getCoreServiceDetail().getBvasNonMItemList().get(j).getIncentiveCd()!=null){
								coreService.setItemHtml(coreService.getItemHtml()+"( " + servicePlan.getCoreServiceDetail().getBvasNonMItemList().get(j).getIncentiveCd()+" )");
							}
						
						if("Y".equalsIgnoreCase(order.getPreInstallInd())&&servicePlan.getCoreServiceDetail().getBvasNonMItemList().get(j).getPreInstOfferCd()!=null ){
							coreService.setItemHtml(coreService.getItemHtml()+" ( Pre-installation dummy offer code: "+servicePlan.getCoreServiceDetail().getBvasNonMItemList().get(j).getPreInstOfferCd()+" ) ");
						}
						
						if("Y".equalsIgnoreCase(order.getPreUseInd())&&servicePlan.getCoreServiceDetail().getBvasNonMItemList().get(j).getPreUseOfferCd()!=null ){
							coreService.setItemHtml(coreService.getItemHtml()+" ( Pre-use dummy offer code: "+servicePlan.getCoreServiceDetail().getBvasNonMItemList().get(j).getPreUseOfferCd()+" ) ");
						}
						
						String tempItemId = servicePlan.getCoreServiceDetail().getBvasNonMItemList().get(j).getItemID();
						String tempMobileNum = "";
						if(order.getComponents()!=null){
							for(int k = 0; k<order.getComponents().length;k++){
								if(tempItemId!=null && tempItemId.equalsIgnoreCase(order.getComponents()[k].getItemId())){
									logger.debug("Section C Non-Mandatory abt mobile num, item_id:"+tempItemId +" equal Components id:" +order.getComponents()[k].getItemId());
									if(tempMobileNum.equals("")){
										tempMobileNum = order.getComponents()[k].getAttbValue();
									}else{
										tempMobileNum += tempMobileNum + " ," +order.getComponents()[k].getAttbValue();
									}
								}else{
									logger.debug("Section C Non-Mandatory abt mobile num, item_id:"+tempItemId +" not equal Components id:" +order.getComponents()[k].getItemId());
								}
							}
						}
						if(!tempMobileNum.equals("")){
							coreService.setItemHtml(coreService.getItemHtml()+ " (Mobile No: " + tempMobileNum +" ) ");
							logger.debug("Section C Non-Mandatory abt mobile num, temp_mobile_num is not null:"+tempMobileNum);
						}else{
							logger.debug("Section C Non-Mandatory abt mobile num, temp_mobile_num is null");
						}
					}
//					logger.debug(coreService.getItemHtml());
//					logger.debug("servicePlan.getCoreServiceDetail().getBvasNonMItemList().get(j).getContractPeriod():"+servicePlan.getCoreServiceDetail().getBvasNonMItemList().get(j).getContractPeriod());
					
					if(servicePlan.getCoreServiceDetail().getBvasNonMItemList().get(j).getContractPeriod()>0){
						coreService.setItemRecurrentAmt(servicePlan.getCoreServiceDetail().getBvasNonMItemList().get(j).getItemMthFix());
						coreService.setItemRecurrentAmt2(servicePlan.getCoreServiceDetail().getBvasNonMItemList().get(j).getItemMth2Mth());
					}else{
						coreService.setItemRecurrentAmt("");
						coreService.setItemRecurrentAmt2(servicePlan.getCoreServiceDetail().getBvasNonMItemList().get(j).getItemMth2Mth());
					}
//					//gary 
//					if(isCC){
//						coreService.setItemRecurrentAmt(servicePlan.getCoreServiceDetail().getBvasNonMItemList().get(j).getItemMthFix()+" "+dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("monthlyRate01").getItemHtml());
//						coreService.setItemRecurrentAmt2(null);
//					}
					
					
					coreServiceList.add(coreService);
//					logger.debug("coreservice print: "+gson.toJson(coreService));
				}
				
				if((servicePlan.getCoreServiceDetail().getProgItem().getContractPeriod())>0){
					RptServiceInfoDTO sectionCMonthlyRateIsForRef  = new RptServiceInfoDTO();
				     if(dbRptstaticWords!=null){
				    	 sectionCMonthlyRateIsForRef.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("monthlyRateWord01"));
				     }
					coreService = new RptServiceDetailDTO();
					coreService.setItemRecurrentAmt2(sectionCMonthlyRateIsForRef.getItemHtml());
					coreServiceList.add(coreService);
				}else{
					RptServiceInfoDTO sectionCMonthlyRateIsForRef2  = new RptServiceInfoDTO();
					RptServiceInfoDTO sectionCMonthlyRateIsForRef3  = new RptServiceInfoDTO();
				     if(dbRptstaticWords!=null){
				    	 sectionCMonthlyRateIsForRef2.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("monthlyRateWord02"));
				    	 sectionCMonthlyRateIsForRef3.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("monthlyRateWord03"));
				     }
					coreService = new RptServiceDetailDTO();
					coreService.setItemRecurrentAmt(sectionCMonthlyRateIsForRef2.getItemHtml());
					coreService.setItemRecurrentAmt2(sectionCMonthlyRateIsForRef3.getItemHtml());
					coreServiceList.add(coreService);
				}

				
				setMrtItemDesc(servicePlan, coreServiceList, cOrder);		
				rptImsAppServDTO.setCoreServDtls(coreServiceList);
//				logger.debug(" coreServiceList:");
//				for (int i1=0;i1<coreServiceList.size();i1++)
//					logger.trace(i1+": "+coreServiceList.get(i1).getItemHtml()+": "+coreServiceList.get(i1).getItemHtml2());
				
				

				logger.debug("part 2 - DB ServicePlan - Section D - Optional Service");
				RptServiceDetailDTO optService;

				// Optional Premium
				if (servicePlan.getOptServiceDetail().getOptPremItemList().size() > 0) {
					optService = new RptServiceDetailDTO();

					RptServiceInfoDTO termDesc  = new RptServiceInfoDTO();
					if ("en".equals(rptImsAppServDTO.getLocale())) {	
						termDesc=(RptServiceInfoDTO) appCtx.getBean("termDescEng02");
					}else if ("zh_HK".equals(rptImsAppServDTO.getLocale())) {
						termDesc=(RptServiceInfoDTO) appCtx.getBean("termDescChi02");
					}
				     if(dbRptstaticWords!=null){
				    	 termDesc.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("termDesc02"));
				     }
					optService.setItemHtml(termDesc.getItemHtml());
					printRptServiceDetailDTO(optService);
					vasAndOptList.add(optService);
				}

				for (int j=0; j < servicePlan.getOptServiceDetail().getOptPremItemList().size(); j++) {
					optService = new RptServiceDetailDTO();
					if(cOrder){
						cOrderOfferCode = "";
						if(servicePlan.getOptServiceDetail().getOptPremItemList().get(j).getOfferCode()!=null && 
								!"x".equalsIgnoreCase(servicePlan.getOptServiceDetail().getOptPremItemList().get(j).getOfferCode())){
							cOrderOfferCode = " ( "+servicePlan.getOptServiceDetail().getOptPremItemList().get(j).getOfferCode()+" ) ";
						}
						
						if(servicePlan.getOptServiceDetail().getOptPremItemList().get(j).getIncentiveCd()!=null && 
								!"x".equalsIgnoreCase(servicePlan.getOptServiceDetail().getOptPremItemList().get(j).getIncentiveCd())){
							cOrderOfferCode += " ( "+servicePlan.getOptServiceDetail().getOptPremItemList().get(j).getIncentiveCd()+" ) ";
						}
						
						if("Y".equalsIgnoreCase(order.getPreInstallInd())&&servicePlan.getOptServiceDetail().getOptPremItemList().get(j).getPreInstOfferCd()!=null ){
							cOrderOfferCode+=" ( Pre-installation dummy offer code: "+servicePlan.getOptServiceDetail().getOptPremItemList().get(j).getPreInstOfferCd()+" ) ";
						}
						
						if("Y".equalsIgnoreCase(order.getPreUseInd())&&servicePlan.getOptServiceDetail().getOptPremItemList().get(j).getPreInstOfferCd()!=null ){
							cOrderOfferCode+=" ( Pre-use dummy offer code: "+servicePlan.getOptServiceDetail().getOptPremItemList().get(j).getPreInstOfferCd()+" ) ";
						}
						
					}
					//optService.setItemHtml(servicePlan.getOptServiceDetail().getOptPremItemList().get(j).getItemTitle()+cOrderOfferCode);
					optService.setItemId(servicePlan.getOptServiceDetail().getOptPremItemList().get(j).getItemID());
					optService.setItemHtml(servicePlan.getOptServiceDetail().getOptPremItemList().get(j).getItemTitle()+cOrderOfferCode);
					
//					logger.debug("opt 1111:"+servicePlan.getOptServiceDetail().getOptPremItemList().get(j).getItemMthFix());
//					logger.debug("opt 2222:"+servicePlan.getOptServiceDetail().getOptPremItemList().get(j).getItemMth2Mth());

//					if(servicePlan.getOptServiceDetail().getOptPremItemList().get(j).getContractPeriod()>0)
						optService.setItemRecurrentAmt(servicePlan.getOptServiceDetail().getOptPremItemList().get(j).getItemMthFix()+" "+dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("monthlyRate01").getItemHtml());
//					else
//						optService.setItemRecurrentAmt(servicePlan.getOptServiceDetail().getOptPremItemList().get(j).getItemMth2Mth()+" "+dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("monthlyRate02").getItemHtml());
					//optService.setItemRecurrentAmt3(servicePlan.getOptServiceDetail().getOptPremItemList().get(j).getItemMthFix());//Gary DDD
					
					printRptServiceDetailDTO(optService);
					vasAndOptList.add(optService);
				}
				
				// Optional Service (WL_BB, ANTI_VIR)
				if (servicePlan.getOptServiceDetail().getOptServItemList().size() > 0) {
//					optService = new RptServiceDetailDTO();
//					optService.setItemHtml("<br />");
//					printRptServiceDetailDTO(optService);
//					vasAndOptList.add(optService);
					
					optService = new RptServiceDetailDTO();
					RptServiceInfoDTO termDesc = new RptServiceInfoDTO();		
					if ("en".equals(rptImsAppServDTO.getLocale())) {	
						termDesc=(RptServiceInfoDTO) appCtx.getBean("termDescEng03");
					}else if ("zh_HK".equals(rptImsAppServDTO.getLocale())) {
						termDesc=(RptServiceInfoDTO) appCtx.getBean("termDescChi03");
					}
					
				     if(dbRptstaticWords!=null){
				    	 termDesc.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("termDesc03"));				     
				     }
				   
					optService.setItemHtml(termDesc.getItemHtml());
					printRptServiceDetailDTO(optService);
					vasAndOptList.add(optService);
				}
	
				for (int j=0; j < servicePlan.getOptServiceDetail().getOptServItemList().size(); j++) {
					logger.debug("i am now looping getOptServItemList");
					optService = new RptServiceDetailDTO();
					if(cOrder){
						cOrderOfferCode = "";
						if(servicePlan.getOptServiceDetail().getOptServItemList().get(j).getOfferCode()!=null && 
								!"x".equalsIgnoreCase(servicePlan.getOptServiceDetail().getOptServItemList().get(j).getOfferCode())){
							cOrderOfferCode = " ( "+servicePlan.getOptServiceDetail().getOptServItemList().get(j).getOfferCode()+" ) ";
						}
						
						if(servicePlan.getOptServiceDetail().getOptServItemList().get(j).getIncentiveCd()!=null && 
								!"x".equalsIgnoreCase(servicePlan.getOptServiceDetail().getOptServItemList().get(j).getIncentiveCd())){
							cOrderOfferCode += " ( "+servicePlan.getOptServiceDetail().getOptServItemList().get(j).getIncentiveCd()+" ) ";
						}
						
						if("Y".equalsIgnoreCase(order.getPreInstallInd())&&servicePlan.getOptServiceDetail().getOptServItemList().get(j).getPreInstOfferCd()!=null ){
							cOrderOfferCode+=" ( Pre-installation dummy offer code: "+servicePlan.getOptServiceDetail().getOptServItemList().get(j).getPreInstOfferCd()+" ) ";
						}
						
						if("Y".equalsIgnoreCase(order.getPreUseInd())&&servicePlan.getOptServiceDetail().getOptServItemList().get(j).getPreInstOfferCd()!=null ){
							cOrderOfferCode+=" ( Pre-use dummy offer code: "+servicePlan.getOptServiceDetail().getOptServItemList().get(j).getPreInstOfferCd()+" ) ";
						}
						if(servicePlan.getOptServiceDetail().getOptServItemList().get(j).getVasDummyGiftDesc()!=null){
							cOrderOfferCode+=" ( VAS link dummy gift : "+servicePlan.getOptServiceDetail().getOptServItemList().get(j).getVasDummyGiftDesc()+" ) ";
						}
						
					}
					//Gary cOrderMobileNum
				  	 String cOrderMobileNum = "";
						if(cOrder && servicePlan.getOptServiceDetail().getOptServItemList().get(j).getMobileNum()!=null && 
								!"x".equalsIgnoreCase(servicePlan.getOptServiceDetail().getOptServItemList().get(j).getMobileNum())){
							cOrderMobileNum = " ( "+servicePlan.getOptServiceDetail().getOptServItemList().get(j).getMobileNum()+" ) ";								
						}
						logger.debug("cOrderMobileNum"+cOrderMobileNum);
				     
						optService.setItemId(servicePlan.getOptServiceDetail().getOptServItemList().get(j).getItemID());
						optService.setItemHtml(servicePlan.getOptServiceDetail().getOptServItemList().get(j).getItemTitle()+cOrderOfferCode+cOrderMobileNum);

					if(servicePlan.getOptServiceDetail().getOptServItemList().get(j).getContractPeriod()>0)
						optService.setItemRecurrentAmt(servicePlan.getOptServiceDetail().getOptServItemList().get(j).getItemMthFix()+" "+dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("monthlyRate01").getItemHtml());
					else
						optService.setItemRecurrentAmt(servicePlan.getOptServiceDetail().getOptServItemList().get(j).getItemMth2Mth()+" "+dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("monthlyRate02").getItemHtml());
					//optService.setItemRecurrentAmt3(servicePlan.getOptServiceDetail().getOptServItemList().get(j).getItemMthFix());//Gary ddd
					printRptServiceDetailDTO(optService);
					vasAndOptList.add(optService);
				}
				
				// Gift
				for (int k=0; k < servicePlan.getGiftList().size(); k++) {
					logger.debug("i am now looping gift list");
					optService = new RptServiceDetailDTO();
//					if(cOrder){
//						cOrderOfferCode = "";
//						if(servicePlan.getOptServiceDetail().getOptServItemList().get(j).getOfferCode()!=null && 
//								!"x".equalsIgnoreCase(servicePlan.getOptServiceDetail().getOptServItemList().get(j).getOfferCode())){
//							cOrderOfferCode = " ( "+servicePlan.getOptServiceDetail().getOptServItemList().get(j).getOfferCode()+" ) ";
//						}
//						
//						if(servicePlan.getOptServiceDetail().getOptServItemList().get(j).getIncentiveCd()!=null && 
//								!"x".equalsIgnoreCase(servicePlan.getOptServiceDetail().getOptServItemList().get(j).getIncentiveCd())){
//							cOrderOfferCode += " ( "+servicePlan.getOptServiceDetail().getOptServItemList().get(j).getIncentiveCd()+" ) ";
//						}
//					}
					
					String giftInputAttb = "";
					
					if(servicePlan.getGiftList().get(k).getGiftAttbList()!=null&&order.getComponents()!=null){
						for(AttbDTO dto:servicePlan.getGiftList().get(k).getGiftAttbList()){
							if("Y".equalsIgnoreCase(dto.getVisualInd())||cOrder){
								for(int m = 0; m<order.getComponents().length;m++){
									if(servicePlan.getGiftList().get(k).getItemID()!=null && servicePlan.getGiftList().get(k).getItemID().equalsIgnoreCase(order.getComponents()[m].getItemId())
											&&dto.getAttbId().equalsIgnoreCase(order.getComponents()[m].getAttbId())){
										if(giftInputAttb.equals("")){
											giftInputAttb = "<br/>("+dto.getAttbDesc()+": "+order.getComponents()[m].getAttbValue();
										}else{
											giftInputAttb += giftInputAttb + "<br/>"+dto.getAttbDesc()+": "+order.getComponents()[m].getAttbValue();
										}
									}
								}
							}
						}
					}
					
					if(!giftInputAttb.equals(""))
						giftInputAttb = giftInputAttb+")";
					
					String dediVASCode = "";
					
					if(cOrder){
						if(servicePlan.getGiftList().get(k).getDediVASList()!=null&&servicePlan.getGiftList().get(k).getDediVASList().size()>0){
							for(ImsRptBasketItemDTO dediVAS:servicePlan.getGiftList().get(k).getDediVASList()){
								dediVASCode += "<br/>(Auto tie VAS : ( " + dediVAS.getOfferCode() + " ) ";
								if(dediVAS.getIncentiveCd()!=null){
									dediVASCode += " ( "+dediVAS.getIncentiveCd()+" ) ";
								}
								if("Y".equalsIgnoreCase(order.getPreInstallInd())&&dediVAS.getPreInstOfferCd()!=null){
									dediVASCode += " ( Pre-installation dummy offer code: "+dediVAS.getPreInstOfferCd()+" ) ";
								}
								dediVASCode += ")";
							}
							dediVASCode += "<br/>";
						}
					}
					
					optService.setItemHtml(servicePlan.getGiftList().get(k).getGiftTitle()+giftInputAttb+dediVASCode);
					optService.setItemRecurrentAmt("");
//					optService.setItemRecurrentAmt(servicePlan.getGiftList().get(i)+" "+dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("monthlyRate01").getItemHtml());
					
//					if(servicePlan.getOptServiceDetail().getOptServItemList().get(j).getContractPeriod()>0)
//						optService.setItemRecurrentAmt(servicePlan.getOptServiceDetail().getOptServItemList().get(j).getItemMthFix()+" "+dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("monthlyRate01").getItemHtml());
//					else
//						optService.setItemRecurrentAmt(servicePlan.getOptServiceDetail().getOptServItemList().get(j).getItemMth2Mth()+" "+dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("monthlyRate02").getItemHtml());
					printRptServiceDetailDTO(optService);
					vasAndOptList.add(optService);
				}

				rptImsAppServDTO.setNowTVPurchased("N");
				logger.debug("Now TV Bundle");
				// Now TV Bundle
					 //Now TV Bundle //Gary Newpricing
				if (order.getTvPriceInd() != null && "Y".equalsIgnoreCase(order.getTvPriceInd())) {
					if(servicePlan.getNtvServiceDetail().getNewTVPricingItemList().getFtaCampaign().isSelected()){
					logger.debug("New Pricing");
					
					rptImsAppServDTO.setNowTVPurchased("Y");
					
					// NTV_FREE heading
					if (servicePlan.getNtvServiceDetail().getNtvFreeItemList().size() > 0) {
//						optService = new RptServiceDetailDTO();
//						optService.setItemHtml("<br />");
//						printRptServiceDetailDTO(optService);
//						vasAndOptList.add(optService);
						optService = new RptServiceDetailDTO();
						RptServiceInfoDTO termDesc = new RptServiceInfoDTO();
						
					     if(dbRptstaticWords!=null){
					    	 termDesc.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("termDesc04"));
					     }
					     cOrderOfferCode="";
						if(cOrder){
							if(servicePlan.getNtvServiceDetail().getNtvFreeItemList().get(0).getOfferCode()!=null ){
								cOrderOfferCode = " ( "+servicePlan.getNtvServiceDetail().getNtvFreeItemList().get(0).getOfferCode()+" )";
							}
							
							if(servicePlan.getNtvServiceDetail().getNtvFreeItemList().get(0).getIncentiveCd()!=null && 
									!"x".equalsIgnoreCase(servicePlan.getNtvServiceDetail().getNtvFreeItemList().get(0).getIncentiveCd())){
								cOrderOfferCode += " ( "+servicePlan.getNtvServiceDetail().getNtvFreeItemList().get(0).getIncentiveCd()+" ) ";
							}
						}
						optService.setItemHtml(termDesc.getItemHtml()+cOrderOfferCode);
//						if(servicePlan.getNtvServiceDetail().getNtvFreeItemList().get(0).getContractPeriod()>0)
							optService.setItemRecurrentAmt(servicePlan.getNtvServiceDetail().getNtvFreeItemList().get(0).getItemMthFix().replaceAll(((char)10)+"", "<br />")
							+" "+dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("monthlyRate01").getItemHtml());
//						else
//							optService.setItemRecurrentAmt(servicePlan.getNtvServiceDetail().getNtvFreeItemList().get(0).getItemMth2Mth()+" "+dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("monthlyRate02").getItemHtml());
//							logger.debug("1111:"+servicePlan.getNtvServiceDetail().getNtvFreeItemList().get(0).getItemMthFix().replaceAll(((char)10)+"", "<br />"));
//							logger.debug("2222:"+servicePlan.getNtvServiceDetail().getNtvFreeItemList().get(0).getItemMth2Mth());
////							optService.setItemRecurrentAmt3(servicePlan.getNtvServiceDetail().getNtvFreeItemList().get(0).getItemMthFix().replaceAll(((char)10)+"", "<br />"));
//							optService.setItemRecurrentAmt2(null);
//						}
						printRptServiceDetailDTO(optService);
						vasAndOptList.add(optService);
					}
					
					NowTVAddUI  newPricingItem = servicePlan.getNtvServiceDetail().getNewTVPricingItemList();
//					logger.debug("New TV Pricing: " + gson.toJson(newPricingItem));
					
					optService = new RptServiceDetailDTO();
					if(cOrder){
					optService.setItemHtml("<b>TV Pack Pricing Indicator:</b> "+order.getTvPriceInd()+"<br/>");
					vasAndOptList.add(optService);

					optService = new RptServiceDetailDTO();	
					if(dbRptstaticWords!=null){
						optService.setItemHtml(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("termDesc04").getItemHtml()+"<br/>");
					}
					vasAndOptList.add(optService);

					}
					
					if(cOrder&&order.getDecodeType()!=null&&!order.getDecodeType().equals("")){
						optService = new RptServiceDetailDTO();
						optService.setItemHtml("Decoder Type:"+order.getDecodeType());
						optService.setItemRecurrentAmt(null);
						optService.setItemRecurrentAmt2(null);
						vasAndOptList.add(optService);
					}
					
					String topUpHeading="<b>"+dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("termDesc10").getItemHtml()+"</b><br/>";
					List<String> packTitles= new ArrayList<String>();
					packTitles.add(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("termDesc07").getItemHtml());
					packTitles.add(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("termDesc08").getItemHtml());
					packTitles.add(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("termDesc09").getItemHtml());
					
					this.getDisplayNowTvAddReport(vasAndOptList, newPricingItem, cOrder, topUpHeading, isPT, dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("newPricingDot").getItemHtml(),packTitles,order,dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("fRecDesc").getItemHtml());
					//logger.debug("New TV Pricing: " + gson.toJson(vasAndOptList));//test********
					

				}}else{
						logger.debug("Non-New Pricing");
				
				// NTV_FREE heading
				if (servicePlan.getNtvServiceDetail().getNtvFreeItemList().size() > 0) {
//					optService = new RptServiceDetailDTO();
//					optService.setItemHtml("<br />");
//					printRptServiceDetailDTO(optService);
//					vasAndOptList.add(optService);
					rptImsAppServDTO.setNowTVPurchased("Y");
					optService = new RptServiceDetailDTO();
					RptServiceInfoDTO termDesc = new RptServiceInfoDTO();
					if ("en".equals(rptImsAppServDTO.getLocale())) {	
						termDesc=(RptServiceInfoDTO) appCtx.getBean("termDescEng04");
					}else if ("zh_HK".equals(rptImsAppServDTO.getLocale())) {
						termDesc=(RptServiceInfoDTO) appCtx.getBean("termDescChi04");
					}
				     if(dbRptstaticWords!=null){
				    	 termDesc.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("termDesc04"));
				     }
				     cOrderOfferCode="";
					if(cOrder){
						if(servicePlan.getNtvServiceDetail().getNtvFreeItemList().get(0).getOfferCode()!=null ){
							cOrderOfferCode = " ( "+servicePlan.getNtvServiceDetail().getNtvFreeItemList().get(0).getOfferCode()+" )";
						}
						
						if(servicePlan.getNtvServiceDetail().getNtvFreeItemList().get(0).getIncentiveCd()!=null && 
								!"x".equalsIgnoreCase(servicePlan.getNtvServiceDetail().getNtvFreeItemList().get(0).getIncentiveCd())){
							cOrderOfferCode += " ( "+servicePlan.getNtvServiceDetail().getNtvFreeItemList().get(0).getIncentiveCd()+" ) ";
						}
					}
					optService.setItemHtml(termDesc.getItemHtml()+cOrderOfferCode);
//					if(servicePlan.getNtvServiceDetail().getNtvFreeItemList().get(0).getContractPeriod()>0)
						optService.setItemRecurrentAmt(servicePlan.getNtvServiceDetail().getNtvFreeItemList().get(0).getItemMthFix().replaceAll(((char)10)+"", "<br />")
						+" "+dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("monthlyRate01").getItemHtml());
//					else
//						optService.setItemRecurrentAmt(servicePlan.getNtvServiceDetail().getNtvFreeItemList().get(0).getItemMth2Mth()+" "+dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("monthlyRate02").getItemHtml());
//						logger.debug("1111:"+servicePlan.getNtvServiceDetail().getNtvFreeItemList().get(0).getItemMthFix().replaceAll(((char)10)+"", "<br />"));
//						logger.debug("2222:"+servicePlan.getNtvServiceDetail().getNtvFreeItemList().get(0).getItemMth2Mth());
////						optService.setItemRecurrentAmt3(servicePlan.getNtvServiceDetail().getNtvFreeItemList().get(0).getItemMthFix().replaceAll(((char)10)+"", "<br />"));
//						optService.setItemRecurrentAmt2(null);
//					}
					printRptServiceDetailDTO(optService);
					vasAndOptList.add(optService);
				}

				if(cOrder&&order.getDecodeType()!=null&&!order.getDecodeType().equals("")){
					optService = new RptServiceDetailDTO();
					optService.setItemHtml("Decoder Type:"+order.getDecodeType());
					optService.setItemRecurrentAmt(null);
					optService.setItemRecurrentAmt2(null);
					vasAndOptList.add(optService);
				}

				logger.debug("NTV_FREE StarterPack Channel");
				if (servicePlan.getNtvServiceDetail().getNtvFreeSPChannelList().size() > 0) {
					optService = new RptServiceDetailDTO();
					optService.setItemHtml("<b>" + servicePlan.getNtvServiceDetail().getNtvFreeSPChannelList().get(0).getChannelGroupDesc() + "</b>");
					printRptServiceDetailDTO(optService);
					vasAndOptList.add(optService);

					for (int k=0; k < servicePlan.getNtvServiceDetail().getNtvFreeSPChannelList().size(); k++) {
						optService = new RptServiceDetailDTO();
						String temp = servicePlan.getNtvServiceDetail().getNtvFreeSPChannelList().get(0).getChannelDesc().replaceAll(((char)10)+"", "<br />");
						planCd="";
						campignCd="";
						if(cOrder){
							if(servicePlan.getNtvServiceDetail().getNtvFreeSPChannelList().get(0).getPlanCd()!=null ){
								planCd = "( "+servicePlan.getNtvServiceDetail().getNtvFreeSPChannelList().get(0).getPlanCd()+" )";
							}
							if(servicePlan.getNtvServiceDetail().getNtvFreeSPChannelList().get(0).getCampaignCd()!=null ){
								campignCd =  " ( "+servicePlan.getNtvServiceDetail().getNtvFreeSPChannelList().get(0).getCampaignCd() + " )";
							}
							temp+=planCd+campignCd;
						}
						optService.setItemHtml(temp);
						printRptServiceDetailDTO(optService);
						vasAndOptList.add(optService);
					}
				}

				logger.debug("NTV_FREE EntertainmentPack Channel");
				if (servicePlan.getNtvServiceDetail().getNtvFreeEPChannelList().size() > 0) {
					optService = new RptServiceDetailDTO();
					optService.setItemHtml("<b>" + servicePlan.getNtvServiceDetail().getNtvFreeEPChannelList().get(0).getChannelGroupDesc() + "</b>");
					printRptServiceDetailDTO(optService);
					vasAndOptList.add(optService);

					for (int k=0; k < servicePlan.getNtvServiceDetail().getNtvFreeEPChannelList().size(); k++) {
						optService = new RptServiceDetailDTO();
						String temp = servicePlan.getNtvServiceDetail().getNtvFreeEPChannelList().get(k).getChannelDesc();
						planCd="";
						campignCd="";
						if(cOrder){
							if(servicePlan.getNtvServiceDetail().getNtvFreeEPChannelList().get(k).getPlanCd()!=null ){
								planCd = " ( "+servicePlan.getNtvServiceDetail().getNtvFreeEPChannelList().get(k).getPlanCd()+" )";
							}
							if(servicePlan.getNtvServiceDetail().getNtvFreeEPChannelList().get(k).getCampaignCd()!=null ){
								campignCd =  " ( "+servicePlan.getNtvServiceDetail().getNtvFreeEPChannelList().get(k).getCampaignCd() + " )";
							}
							temp+=planCd+campignCd;
						}
						logger.debug("EntertainmentPack Channel:"+temp);
						optService.setItemHtml(temp.replaceAll(((char)10)+"", "<br />"));
						printRptServiceDetailDTO(optService);
						vasAndOptList.add(optService);
					}
				}
				
				logger.debug("NTV_FREE Free2HD");
				if (servicePlan.getNtvServiceDetail().getNtvFreeHDChannelList().size() > 0) {
					optService = new RptServiceDetailDTO();
					optService.setItemHtml("<b>" + servicePlan.getNtvServiceDetail().getNtvFreeHDChannelList().get(0).getChannelGroupDesc() + "</b>");
					printRptServiceDetailDTO(optService);
					vasAndOptList.add(optService);

					String tmpFreeHDItemHtml = "";
					for (int k=0; k < servicePlan.getNtvServiceDetail().getNtvFreeHDChannelList().size(); k++) {
						if (k > 0) {
							if (servicePlan.getNtvServiceDetail().getNtvFreeHDChannelList().get(k).getParentChannelId() == null ||
								servicePlan.getNtvServiceDetail().getNtvFreeHDChannelList().get(k).getParentChannelId().length() == 0) {
								optService = new RptServiceDetailDTO();
								optService.setItemHtml(tmpFreeHDItemHtml.trim() + "<br />");
								printRptServiceDetailDTO(optService);
								vasAndOptList.add(optService);
								tmpFreeHDItemHtml = "";
							}
						}
						
						tmpFreeHDItemHtml +=  " " + servicePlan.getNtvServiceDetail().getNtvFreeHDChannelList().get(k).getChannelDesc().replaceAll(((char)10)+"", "<br />");
						planCd="";
						campignCd="";
						if(cOrder){
							if(servicePlan.getNtvServiceDetail().getNtvFreeHDChannelList().get(k).getPlanCd()!=null ){
								planCd = " ( "+servicePlan.getNtvServiceDetail().getNtvFreeHDChannelList().get(k).getPlanCd()+" )";
							}
							if(servicePlan.getNtvServiceDetail().getNtvFreeHDChannelList().get(k).getCampaignCd()!=null ){
								campignCd =  " ( "+servicePlan.getNtvServiceDetail().getNtvFreeHDChannelList().get(k).getCampaignCd() + " )";
							}
							tmpFreeHDItemHtml+=planCd+campignCd;
						}
					}
					if (tmpFreeHDItemHtml != "") {
						optService = new RptServiceDetailDTO();
						optService.setItemHtml(tmpFreeHDItemHtml.trim());
						logger.debug("tmpFreeHDItemHtml.trim():"+optService.getItemHtml());
						printRptServiceDetailDTO(optService);
						vasAndOptList.add(optService);
					}
				}
				
				
				logger.debug("NTV_PAY, NTV_P_30F6");
				if (servicePlan.getNtvServiceDetail().getNtvPayItemList().size() > 0) {
					
					optService = new RptServiceDetailDTO();

					logger.debug("getNtvPayItemList");
					for (int k=0; k < servicePlan.getNtvServiceDetail().getNtvPayItemList().size(); k++) {
						logger.debug("getType:"+servicePlan.getNtvServiceDetail().getNtvPayItemList().get(k).getType());
						if(servicePlan.getNtvServiceDetail().getNtvPayItemList().get(k).getType().indexOf("NTV_P")!=-1 ||
								servicePlan.getNtvServiceDetail().getNtvPayItemList().get(k).getType().indexOf("NTV_SP")!=-1){
							optService = new RptServiceDetailDTO();
							cOrderOfferCode = "";
							
							if(cOrder){
								if(servicePlan.getNtvServiceDetail().getNtvPayItemList().get(k).getOfferCode()!=null ){
									cOrderOfferCode = "( "+servicePlan.getNtvServiceDetail().getNtvPayItemList().get(k).getOfferCode()+" )";
								}
								if(servicePlan.getNtvServiceDetail().getNtvPayItemList().get(k).getIncentiveCd()!=null && 
										!"x".equalsIgnoreCase(servicePlan.getNtvServiceDetail().getNtvPayItemList().get(k).getIncentiveCd())){
									cOrderOfferCode += " ( "+servicePlan.getNtvServiceDetail().getNtvPayItemList().get(k).getIncentiveCd()+" ) ";
								}
							}
							optService.setItemHtml("<b>"+ servicePlan.getNtvServiceDetail().getNtvPayItemList().get(k).getItemTitle() + "</b> "+cOrderOfferCode);
//							if(servicePlan.getNtvServiceDetail().getNtvPayItemList().get(k).getContractPeriod()>0)
								optService.setItemRecurrentAmt(servicePlan.getNtvServiceDetail().getNtvPayItemList().get(k).getItemMthFix()+" "+dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("monthlyRate01").getItemHtml());
//							else
//								optService.setItemRecurrentAmt(servicePlan.getNtvServiceDetail().getNtvPayItemList().get(k).getItemMth2Mth()+" "+dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("monthlyRate02").getItemHtml());
//							optService.setItemRecurrentAmt3(servicePlan.getNtvServiceDetail().getNtvPayItemList().get(k).getItemMthFix());//Gary DDD
							printRptServiceDetailDTO(optService);
							vasAndOptList.add(optService);
						}
					}
					logger.debug("getNtvPayChannelList");
					String tmpPayItemHtml = "";
					int count=0;boolean addCd=false;//Gary
					for (int k=0; k < servicePlan.getNtvServiceDetail().getNtvPayChannelList().size(); k++) {
						if (k > 0) {
							if (servicePlan.getNtvServiceDetail().getNtvPayChannelList().get(k).getParentChannelId() == null ||
								servicePlan.getNtvServiceDetail().getNtvPayChannelList().get(k).getParentChannelId().length() == 0) {
								optService = new RptServiceDetailDTO();
								optService.setItemHtml(tmpPayItemHtml.trim() + "<br />");
								printRptServiceDetailDTO(optService);
								vasAndOptList.add(optService);
								tmpPayItemHtml = "";
							}
						}
						if (servicePlan.getNtvServiceDetail().getNtvPayChannelList().get(k).getChannelCd() == null || 
							servicePlan.getNtvServiceDetail().getNtvPayChannelList().get(k).getChannelCd().length() == 0) {
							tmpPayItemHtml +=  " " + servicePlan.getNtvServiceDetail().getNtvPayChannelList().get(k).getChannelDesc().replaceAll(((char)10)+"", "<br />");
						} else {
							tmpPayItemHtml +=  " Ch." + servicePlan.getNtvServiceDetail().getNtvPayChannelList().get(k).getChannelCd() + " " 
							+ servicePlan.getNtvServiceDetail().getNtvPayChannelList().get(k).getChannelDesc().replaceAll(((char)10)+"", "<br />");
						}
						planCd="";
						campignCd="";
						
						if(k+1 < servicePlan.getNtvServiceDetail().getNtvPayChannelList().size()&& (servicePlan.getNtvServiceDetail().getNtvPayChannelList().get(k+1).getChannelCd() == null || 
							servicePlan.getNtvServiceDetail().getNtvPayChannelList().get(k+1).getChannelCd().length() == 0)){
							count+=1;
							addCd=false;
						}else{
							addCd=true;
						}
						
//						if(cOrder){
//							if(servicePlan.getNtvServiceDetail().getNtvPayChannelList().get(k).getPlanCd()!=null ){
//								planCd = " ("+servicePlan.getNtvServiceDetail().getNtvPayChannelList().get(k).getPlanCd()+")";
//							}
//							if(servicePlan.getNtvServiceDetail().getNtvPayChannelList().get(k).getCampaignCd()!=null ){
//								campignCd =  " ("+servicePlan.getNtvServiceDetail().getNtvPayChannelList().get(k).getCampaignCd() + ")";
//							}
//							tmpPayItemHtml+=planCd+campignCd;
//						}
						if(cOrder && addCd){
							for(;count>=0;count--){
								if(servicePlan.getNtvServiceDetail().getNtvPayChannelList().get(k-count).getPlanCd()!=null ){
									planCd = "( "+servicePlan.getNtvServiceDetail().getNtvPayChannelList().get(k-count).getPlanCd()+" )";
								}
								if(servicePlan.getNtvServiceDetail().getNtvPayChannelList().get(k-count).getCampaignCd()!=null ){
									campignCd =  "( "+servicePlan.getNtvServiceDetail().getNtvPayChannelList().get(k-count).getCampaignCd() + " )";
								}
								tmpPayItemHtml+=planCd+campignCd;
							}
							addCd=false;
							count=0;
							
						}
					}
					if (tmpPayItemHtml != "") {
						optService = new RptServiceDetailDTO();
						optService.setItemHtml(tmpPayItemHtml.trim());
						printRptServiceDetailDTO(optService);
						vasAndOptList.add(optService);
					}
}
					
				}
				logger.debug("getNtvPayItemList");
					for (int k=0; k < servicePlan.getNtvServiceDetail().getNtvPayItemList().size(); k++) {
						if(servicePlan.getNtvServiceDetail().getNtvPayItemList().get(k).getType().indexOf("NTV_P")==-1 &&
								servicePlan.getNtvServiceDetail().getNtvPayItemList().get(k).getType().indexOf("NTV_SP")==-1){
							optService = new RptServiceDetailDTO();
							cOrderOfferCode = "";
								
							if(cOrder){
								if(servicePlan.getNtvServiceDetail().getNtvPayItemList().get(k).getOfferCode()!=null ){
									cOrderOfferCode = " ( "+servicePlan.getNtvServiceDetail().getNtvPayItemList().get(k).getOfferCode()+" )";
								}
															
								if(servicePlan.getNtvServiceDetail().getNtvPayItemList().get(k).getIncentiveCd()!=null ){
									cOrderOfferCode += " ( "+servicePlan.getNtvServiceDetail().getNtvPayItemList().get(k).getIncentiveCd()+" )";
								}
							}
							optService.setItemHtml("<b>"+ servicePlan.getNtvServiceDetail().getNtvPayItemList().get(k).getItemTitle() + "</b> "+cOrderOfferCode);
//							if(servicePlan.getNtvServiceDetail().getNtvPayItemList().get(k).getContractPeriod()>0)
								optService.setItemRecurrentAmt(servicePlan.getNtvServiceDetail().getNtvPayItemList().get(k).getItemMthFix()+" "+dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("monthlyRate01").getItemHtml());
//							else
								optService.setItemRecurrentAmt2(servicePlan.getNtvServiceDetail().getNtvPayItemList().get(k).getItemMth2Mth()+" "+dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("monthlyRate02").getItemHtml());
//							optService.setItemRecurrentAmt3(servicePlan.getNtvServiceDetail().getNtvPayItemList().get(k).getItemMthFix());//Gary DDD

							printRptServiceDetailDTO(optService);
							vasAndOptList.add(optService);
						}
					}
				logger.debug("NTV_OTHER");
				if (servicePlan.getNtvServiceDetail().getNtvOtherItemList().size() > 0) {
//					optService = new RptServiceDetailDTO();
//					optService.setItemHtml("<br />");
//					printRptServiceDetailDTO(optService);
//					vasAndOptList.add(optService);
					
					optService = new RptServiceDetailDTO();
					RptServiceInfoDTO termDesc = new RptServiceInfoDTO();
					if ("en".equals(rptImsAppServDTO.getLocale())) {	
						termDesc=(RptServiceInfoDTO) appCtx.getBean("termDescEng05");
					}else if ("zh_HK".equals(rptImsAppServDTO.getLocale())) {
						termDesc=(RptServiceInfoDTO) appCtx.getBean("termDescChi05");
					}
				     if(dbRptstaticWords!=null){
				    	 termDesc.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("termDesc05"));
				     }
					optService.setItemHtml(termDesc.getItemHtml());
					printRptServiceDetailDTO(optService);
					vasAndOptList.add(optService);

					for (int k=0; k < servicePlan.getNtvServiceDetail().getNtvOtherItemList().size(); k++) {
						optService = new RptServiceDetailDTO();
						cOrderOfferCode = "";
						
						if(cOrder){
							if(servicePlan.getNtvServiceDetail().getNtvOtherItemList().get(k).getOfferCode()!=null ){
								cOrderOfferCode = " ( "+servicePlan.getNtvServiceDetail().getNtvOtherItemList().get(k).getOfferCode()+" )";
							}
							
							if(servicePlan.getNtvServiceDetail().getNtvOtherItemList().get(k).getIncentiveCd()!=null ){
								cOrderOfferCode += " ( "+servicePlan.getNtvServiceDetail().getNtvOtherItemList().get(k).getIncentiveCd()+" )";
							}
						}
						optService.setItemHtml(servicePlan.getNtvServiceDetail().getNtvOtherItemList().get(k).getItemTitle()+cOrderOfferCode);
//						if(servicePlan.getNtvServiceDetail().getNtvOtherItemList().get(k).getContractPeriod()>0){
							if(servicePlan.getNtvServiceDetail().getNtvOtherItemList().get(k).getItemMthFix()!=null&&!servicePlan.getNtvServiceDetail().getNtvOtherItemList().get(k).getItemMthFix().isEmpty()){
								optService.setItemRecurrentAmt(servicePlan.getNtvServiceDetail().getNtvOtherItemList().get(k).getItemMthFix()+" "+dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("monthlyRate01").getItemHtml());
							}else{
								optService.setItemRecurrentAmt("");
							}
//						}
//						else{
							if(servicePlan.getNtvServiceDetail().getNtvOtherItemList().get(k).getItemMth2Mth()!=null&&!servicePlan.getNtvServiceDetail().getNtvOtherItemList().get(k).getItemMth2Mth().isEmpty()){
								optService.setItemRecurrentAmt2(servicePlan.getNtvServiceDetail().getNtvOtherItemList().get(k).getItemMth2Mth()+" "+dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("monthlyRate02").getItemHtml());
							}else{
								optService.setItemRecurrentAmt2("");
							}
//						}
//						optService.setItemRecurrentAmt3(servicePlan.getNtvServiceDetail().getNtvOtherItemList().get(k).getItemMthFix());//Gary DDD
						printRptServiceDetailDTO(optService);
						vasAndOptList.add(optService);
					}
				}

				logger.debug("New Media Option");
				if (servicePlan.getOptServiceDetail().getMediaItemList().size() > 0) {
//					optService = new RptServiceDetailDTO();
//					optService.setItemHtml("<br />");
//					printRptServiceDetailDTO(optService);
//					vasAndOptList.add(optService);
					
					optService = new RptServiceDetailDTO();
					RptServiceInfoDTO termDesc = new RptServiceInfoDTO();
					if ("en".equals(rptImsAppServDTO.getLocale())) {	
						termDesc=(RptServiceInfoDTO) appCtx.getBean("termDescEng06");
					}else if ("zh_HK".equals(rptImsAppServDTO.getLocale())) {
						termDesc=(RptServiceInfoDTO) appCtx.getBean("termDescChi06");
					}
				     if(dbRptstaticWords!=null){
				    	 termDesc.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("termDesc06"));
				     }
					optService.setItemHtml(termDesc.getItemHtml());
					printRptServiceDetailDTO(optService);
					vasAndOptList.add(optService);
									
					for (int j=0; j < servicePlan.getOptServiceDetail().getMediaItemList().size(); j++) {
						optService = new RptServiceDetailDTO();
						cOrderOfferCode = "";
						
						if(cOrder){
							
							if(servicePlan.getOptServiceDetail().getMediaItemList().get(j).getOfferCode()!=null && 
									!"x".equalsIgnoreCase(servicePlan.getOptServiceDetail().getMediaItemList().get(j).getOfferCode())){
								cOrderOfferCode = " ( "+servicePlan.getOptServiceDetail().getMediaItemList().get(j).getOfferCode()+" ) ";
							}
							
							if(servicePlan.getOptServiceDetail().getMediaItemList().get(j).getIncentiveCd()!=null && 
									!"x".equalsIgnoreCase(servicePlan.getOptServiceDetail().getMediaItemList().get(j).getIncentiveCd())){
								cOrderOfferCode += " ( "+servicePlan.getOptServiceDetail().getMediaItemList().get(j).getIncentiveCd()+" ) ";
							}
						}
						optService.setItemId(servicePlan.getOptServiceDetail().getMediaItemList().get(j).getItemID());
						optService.setItemHtml(servicePlan.getOptServiceDetail().getMediaItemList().get(j).getItemTitle()+cOrderOfferCode);
						if(servicePlan.getOptServiceDetail().getMediaItemList().get(j).getContractPeriod()>0)
							optService.setItemRecurrentAmt(servicePlan.getOptServiceDetail().getMediaItemList().get(j).getItemMthFix()+" "+dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("monthlyRate01").getItemHtml());
						else
							optService.setItemRecurrentAmt(servicePlan.getOptServiceDetail().getMediaItemList().get(j).getItemMth2Mth()+" "+dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("monthlyRate02").getItemHtml());
//						optService.setItemRecurrentAmt3(servicePlan.getOptServiceDetail().getMediaItemList().get(j).getItemMthFix());//Gary DDD
						printRptServiceDetailDTO(optService);
						vasAndOptList.add(optService);
					}
				}
				
				logger.debug("backend Vas");
				if (servicePlan.getOptServiceDetail().getBackendVasItemList().size() > 0 && cOrder) {
					
					optService = new RptServiceDetailDTO();
					RptServiceInfoDTO termDesc = new RptServiceInfoDTO();
														
					for (int j=0; j < servicePlan.getOptServiceDetail().getBackendVasItemList().size(); j++) {
						optService = new RptServiceDetailDTO();
						cOrderOfferCode = "";
						
						if(cOrder){
							
							if(servicePlan.getOptServiceDetail().getBackendVasItemList().get(j).getOfferCode()!=null && 
									!"x".equalsIgnoreCase(servicePlan.getOptServiceDetail().getBackendVasItemList().get(j).getOfferCode())){
								cOrderOfferCode = " ( "+servicePlan.getOptServiceDetail().getBackendVasItemList().get(j).getOfferCode()+" ) ";
							}
							
							if(servicePlan.getOptServiceDetail().getBackendVasItemList().get(j).getIncentiveCd()!=null && 
									!"x".equalsIgnoreCase(servicePlan.getOptServiceDetail().getBackendVasItemList().get(j).getIncentiveCd())){
								cOrderOfferCode += " ( "+servicePlan.getOptServiceDetail().getBackendVasItemList().get(j).getIncentiveCd()+" ) ";
							}
						}
						optService.setItemHtml((cOrder?"":servicePlan.getOptServiceDetail().getBackendVasItemList().get(j).getItemTitle())+cOrderOfferCode);
						if(servicePlan.getOptServiceDetail().getBackendVasItemList().get(j).getContractPeriod()>0)
							optService.setItemRecurrentAmt(servicePlan.getOptServiceDetail().getBackendVasItemList().get(j).getItemMthFix()+" "+dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("monthlyRate01").getItemHtml());
						else
							optService.setItemRecurrentAmt(servicePlan.getOptServiceDetail().getBackendVasItemList().get(j).getItemMth2Mth()+" "+dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("monthlyRate02").getItemHtml());
//						optService.setItemRecurrentAmt3(servicePlan.getOptServiceDetail().getMediaItemList().get(j).getItemMthFix());//Gary DDD
						printRptServiceDetailDTO(optService);
						vasAndOptList.add(optService);
						logger.debug(gson.toJson(optService));
					}
				}
				
				logger.debug("NTV_Backend");
				if (cOrder && servicePlan.getNtvServiceDetail().getNtvBackendChannelList().size() > 0) {

					
					optService = new RptServiceDetailDTO();
					
					
					optService.setItemHtml("<b>Backend Channel:</b> ");
					printRptServiceDetailDTO(optService);
					vasAndOptList.add(optService);

					for (int k=0; k < servicePlan.getNtvServiceDetail().getNtvBackendChannelList().size(); k++) {
						optService = new RptServiceDetailDTO();
						cOrderOfferCode = "";
						
						if(cOrder){
							
							if(servicePlan.getNtvServiceDetail().getNtvBackendChannelList().get(k).getCampaignCd()!=null && 
									!"x".equalsIgnoreCase(servicePlan.getNtvServiceDetail().getNtvBackendChannelList().get(k).getCampaignCd())){
								cOrderOfferCode = " ( "+servicePlan.getNtvServiceDetail().getNtvBackendChannelList().get(k).getCampaignCd()+" ) ";
							}
							if(servicePlan.getNtvServiceDetail().getNtvBackendChannelList().get(k).getOfferCd()!=null ){
								cOrderOfferCode += " ( "+servicePlan.getNtvServiceDetail().getNtvBackendChannelList().get(k).getOfferCd()+" )";
							}
						}
						optService.setItemHtml(cOrderOfferCode);
						printRptServiceDetailDTO(optService);
						vasAndOptList.add(optService);
					}
				}
				
				setMrtItemDesc(servicePlan, vasAndOptList, cOrder);
				setImsVasParmItemDesc(servicePlan, vasAndOptList);
				rptImsAppServDTO.setVasAndOptDtls(vasAndOptList);
				
				

				logger.debug("part 2 - DB ServicePlan - section J - details of service plan, optional and value added services");
				RptServiceInfoDTO serviceInfo;
				
				RptServiceInfoDTO tempServicePlanI01  = new RptServiceInfoDTO();
				RptServiceInfoDTO tempServicePlanI02  = new RptServiceInfoDTO();
				RptServiceInfoDTO tempServicePlanI03  = new RptServiceInfoDTO();
				RptServiceInfoDTO tempServicePlanI04  = new RptServiceInfoDTO();
				RptServiceInfoDTO tempServicePlanI05  = new RptServiceInfoDTO();
				RptServiceInfoDTO tempServicePlanI06  = new RptServiceInfoDTO();
				RptServiceInfoDTO tempServicePlanI07  = new RptServiceInfoDTO();
				RptServiceInfoDTO tempServicePlanI08  = new RptServiceInfoDTO();
				RptServiceInfoDTO tempServicePlanI09  = new RptServiceInfoDTO();
				if ("en".equals(rptImsAppServDTO.getLocale())) {	
					tempServicePlanI01=(RptServiceInfoDTO) appCtx.getBean("termDescEng01");
					tempServicePlanI02=(RptServiceInfoDTO) appCtx.getBean("servPlanEng02");
					tempServicePlanI03=(RptServiceInfoDTO) appCtx.getBean("servPlanEng03");
					tempServicePlanI04=(RptServiceInfoDTO) appCtx.getBean("servPlanEng04");
					tempServicePlanI05=(RptServiceInfoDTO) appCtx.getBean("servPlanEng05");
					tempServicePlanI06=(RptServiceInfoDTO) appCtx.getBean("servPlanEng06phhos");
				}else if ("zh_HK".equals(rptImsAppServDTO.getLocale())) {
					tempServicePlanI01=(RptServiceInfoDTO) appCtx.getBean("termDescChi01");
					tempServicePlanI02=(RptServiceInfoDTO) appCtx.getBean("servPlanChi02");
					tempServicePlanI03=(RptServiceInfoDTO) appCtx.getBean("servPlanChi03");
					tempServicePlanI04=(RptServiceInfoDTO) appCtx.getBean("servPlanChi04");
					tempServicePlanI05=(RptServiceInfoDTO) appCtx.getBean("servPlanChi05");
					tempServicePlanI06=(RptServiceInfoDTO) appCtx.getBean("servPlanChi06phhos");
				}
			     if(dbRptstaticWords!=null){
			    	 tempServicePlanI01.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("termDesc01"));
			    	 tempServicePlanI02.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("servPlan02"));
			    	 tempServicePlanI03.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("servPlan03"));
			    	 tempServicePlanI04.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("servPlan04"));
			    	 tempServicePlanI05.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("servPlan05"));
			    	 tempServicePlanI06.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("servPlan06phhos"));
			    	 tempServicePlanI07.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("servPlan07"));//pre-install t&c
			    	 tempServicePlanI08.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("servPlan08"));//pre-use t&c
			    	 tempServicePlanI09.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("servPlan09"));//pre-inst mesh-router t&c
			     }
				

				servPlanList.add(tempServicePlanI01);
				servPlanList.add(tempServicePlanI02);
				servPlanList.add(tempServicePlanI03);
				servPlanList.add(tempServicePlanI04);
				servPlanList.add(tempServicePlanI05);
				servPlanList.add(tempServicePlanI06);
				
				if("Y".equalsIgnoreCase(order.getPreInstallInd())){
					servPlanList.add(tempServicePlanI07);
					if("Y".equalsIgnoreCase(servicePlan.getPreInstRouterPurchasedInd())){
						servPlanList.add(tempServicePlanI09);
					}
				}
				
				if("Y".equalsIgnoreCase(order.getPreUseInd())){
					servPlanList.add(tempServicePlanI08);
				}
		
				
//				termDescEng01=<b>CORE SERVICES:</b>
//				termDescEng05=<b>SPECIAL RENTAL EQUIPMENT:</b>
				
				RptServiceInfoDTO tempTermDesc02 = new RptServiceInfoDTO();
				RptServiceInfoDTO tempTermDesc03 = new RptServiceInfoDTO();
				RptServiceInfoDTO tempTermDesc04 = new RptServiceInfoDTO();
				RptServiceInfoDTO tempTermDesc06 = new RptServiceInfoDTO();
				if ("en".equals(rptImsAppServDTO.getLocale())) {	
					tempTermDesc02=(RptServiceInfoDTO) appCtx.getBean("termDescEng02");//termDescEng02=<b>EXTRA PREMIUMS:</b>
					tempTermDesc03=(RptServiceInfoDTO) appCtx.getBean("termDescEng03");//termDescEng03=<b>EXTRA SERVICES:</b>
					tempTermDesc04=(RptServiceInfoDTO) appCtx.getBean("termDescEng04");//termDescEng04=<b>now TV BUNDLE:</b>
					tempTermDesc06=(RptServiceInfoDTO) appCtx.getBean("termDescEng06");//termDescEng06=<b>NEW MEDIA OPTION:</b>
				}else if ("zh_HK".equals(rptImsAppServDTO.getLocale())) {
					tempTermDesc02=(RptServiceInfoDTO) appCtx.getBean("termDescChi02");
					tempTermDesc03=(RptServiceInfoDTO) appCtx.getBean("termDescChi03");
					tempTermDesc04=(RptServiceInfoDTO) appCtx.getBean("termDescChi04");
					tempTermDesc06=(RptServiceInfoDTO) appCtx.getBean("termDescChi06");
				}
			     if(dbRptstaticWords!=null){
			    	 tempTermDesc02.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("termDesc02"));
			    	 tempTermDesc03.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("termDesc03"));
			    	 tempTermDesc04.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("termDesc04"));
			    	 tempTermDesc06.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("termDesc06"));
			     }
					     
					     
		// Program Item
				String tempHtml;
				serviceInfo = new RptServiceInfoDTO();
				serviceInfo.setItemHtml("<b>" + servicePlan.getCoreServiceDetail().getProgItem().getItemTitle() + "</b>");
				servPlanList.add(serviceInfo);
				
				serviceInfo = new RptServiceInfoDTO();
				tempHtml = servicePlan.getCoreServiceDetail().getProgItem().getItemDetails();
				if (tempHtml != null && tempHtml.length() > 0) {
					tempHtml = tempHtml.replaceAll(((char)10)+"", "<br />");
					tempHtml = tempHtml + "<br />";
				}
				
				serviceInfo.setItemHtml(tempHtml);
				servPlanList.add(serviceInfo);

				
				// BVAS Mandatory Item 
				for (int j=0; j < servicePlan.getCoreServiceDetail().getBvasMandItemList().size(); j++) {
					serviceInfo = new RptServiceInfoDTO();
					tempHtml = servicePlan.getCoreServiceDetail().getBvasMandItemList().get(j).getItemDetails();
					if (tempHtml != null && tempHtml.length() > 0) {
						if(tempHtml.indexOf(((char)10)+"")!=-1){
							tempHtml = "<b>" + tempHtml.replaceFirst(((char)10)+"", "</b><br />");
						}else if(tempHtml.indexOf("<br/>")!=-1){
							tempHtml = "<b>" + tempHtml.replaceFirst("<br/>", "</b><br />");
						}
						tempHtml = tempHtml.replaceAll(((char)10)+"", "<br />");
						tempHtml = tempHtml + "<br />";
						serviceInfo.setItemHtml(tempHtml);
						servPlanList.add(serviceInfo);
					}
					
				}
				
				
				// BVAS Non-Mandatory Item 
				for (int j=0; j < servicePlan.getCoreServiceDetail().getBvasNonMItemList().size(); j++) {
					serviceInfo = new RptServiceInfoDTO();
					tempHtml = servicePlan.getCoreServiceDetail().getBvasNonMItemList().get(j).getItemDetails();
					if (tempHtml != null && tempHtml.length() > 0) {
						if(tempHtml.indexOf(((char)10)+"")!=-1){
							tempHtml = "<b>" + tempHtml.replaceFirst(((char)10)+"", "</b><br />");
						}else if(tempHtml.indexOf("<br/>")!=-1){
							tempHtml = "<b>" + tempHtml.replaceFirst("<br/>", "</b><br />");
						}
						tempHtml = tempHtml.replaceAll(((char)10)+"", "<br />");
						tempHtml = tempHtml + "<br />";
						serviceInfo.setItemHtml(tempHtml);
						servPlanList.add(serviceInfo);
					}
					
				}
				

				// Optional Premium
				if (servicePlan.getOptServiceDetail().getOptPremItemList().size() > 0) {
//					serviceInfo = new RptServiceInfoDTO();
//					serviceInfo.setItemHtml("<br />");
//					servPlanList.add(serviceInfo);
//					serviceInfo.setItemHtml("<b>EXTRA PREMIUM:</b>");
					servPlanList.add(tempTermDesc02);
				
					for (int j=0; j < servicePlan.getOptServiceDetail().getOptPremItemList().size(); j++) {
						serviceInfo = new RptServiceInfoDTO();
						tempHtml = servicePlan.getOptServiceDetail().getOptPremItemList().get(j).getItemDetails();
						if (tempHtml != null && tempHtml.length() > 0) {
							if(tempHtml.indexOf(((char)10)+"")!=-1){
								tempHtml = "<b>" + tempHtml.replaceFirst(((char)10)+"", "</b><br />");
							}else if(tempHtml.indexOf("<br/>")!=-1){
								tempHtml = "<b>" + tempHtml.replaceFirst("<br/>", "</b><br />");
							}
							tempHtml = tempHtml.replaceAll(((char)10)+"", "<br />");
							tempHtml = tempHtml + "<br />";
							serviceInfo.setItemHtml(tempHtml);
							servPlanList.add(serviceInfo);
						}
					}
				}
				
				
				// Optional Service
				//if (servicePlan.getOptServiceDetail().getWlBbItemList().size() > 0 || servicePlan.getOptServiceDetail().getAntiVirItemList().size() > 0) {
				if (servicePlan.getOptServiceDetail().getOptServItemList().size() > 0) {
//					serviceInfo = new RptServiceInfoDTO();
//					serviceInfo.setItemHtml("<br />");
//					servPlanList.add(serviceInfo);
//					serviceInfo.setItemHtml("<b>EXTRA SERVICES:</b>");
					servPlanList.add(tempTermDesc03);
				
					for (int j=0; j < servicePlan.getOptServiceDetail().getOptServItemList().size(); j++) {
						serviceInfo = new RptServiceInfoDTO();
						tempHtml = servicePlan.getOptServiceDetail().getOptServItemList().get(j).getItemDetails();
						if (tempHtml != null && tempHtml.length() > 0) {
							if(tempHtml.indexOf(((char)10)+"")!=-1){
								tempHtml = "<b>" + tempHtml.replaceFirst(((char)10)+"", "</b><br />");
							}else if(tempHtml.indexOf("<br/>")!=-1){
								tempHtml = "<b>" + tempHtml.replaceFirst("<br/>", "</b><br />");
							}
							tempHtml = tempHtml.replaceAll(((char)10)+"", "<br />");
							tempHtml = tempHtml + "<br />";
							serviceInfo.setItemHtml(tempHtml);
							servPlanList.add(serviceInfo);
						}
					}
				}
				
				// Gift
				for (ImsRptGiftDTO giftList: servicePlan.getGiftList()) {
					logger.debug("i am now looping gift list Tnc");
					optService = new RptServiceDetailDTO();
					serviceInfo = new RptServiceInfoDTO();
				     
					optService.setItemHtml("<b>"+giftList.getGiftTitle()+"</b><br/><br/>"+giftList.getGiftDetail()+"<br/>");
					printRptServiceDetailDTO(optService);
					serviceInfo.setItemHtml(optService.getItemHtml());
					servPlanList.add(serviceInfo);
					
				}
				
				
				// now TV
				//Gary add newPricing
				if (order.getTvPriceInd() != null && "Y".equalsIgnoreCase(order.getTvPriceInd())) {
					logger.debug("New Pricing");
					servPlanList.add(tempTermDesc04);
					
					
					NowTVAddUI  newPricingItem = servicePlan.getNtvServiceDetail().getNewTVPricingItemList();
					
					
					 NowTvCampaignUI  ftaCampaign =  newPricingItem.getFtaCampaign();
					 if(ftaCampaign!=null && ftaCampaign.isSelected()){
						 serviceInfo = new RptServiceInfoDTO();
							String tnc="";
							if(!StringUtils.isEmpty(ftaCampaign.getTnc()))
								tnc+=ftaCampaign.getTnc()+"<br/>";//ftaCampaign
						for(NowTvPackUI ftaPacks :ftaCampaign.getTvPacks()){							
							if(ftaPacks.isSelected()){
								
								if(!StringUtils.isEmpty(ftaPacks.getTnc()))
									tnc+=ftaPacks.getTnc()+"<br/>";//ftaPacks
								
							}
							
						}
						for(NowTvVasBundle vasBundle:ftaCampaign.getVasBundles() )
							if( !StringUtils.isEmpty(vasBundle.getTnc()))
									tnc+=vasBundle.getTnc()+"<br/>";//ftaCampaign.getVasBundles()
						//tnc=tnc.replace("null<br/>","");
						logger.debug("FTA: "+tnc);
						if(tnc!=""){
							serviceInfo.setItemHtml(tnc);
							servPlanList.add(serviceInfo);
						}
						serviceInfo = new RptServiceInfoDTO();
						String topTnc="";
						for(NowTvTopUpUI ftatopups:ftaCampaign.getTopUps()){
							if(ftatopups.isSelected()&& !StringUtils.isEmpty(ftatopups.getTnc())){								
								topTnc+=ftatopups.getTnc()+"<br/>";								
							}								
						}
						if(topTnc!=""){
							serviceInfo.setItemHtml(topTnc);
							servPlanList.add(serviceInfo);
						}
					 }
						
						
						
					
				NowTvCampaignUI  hardCampaign =  newPricingItem.getHardCampaign();
				if(hardCampaign!=null && hardCampaign.isSelected()){
					String tnc="";
					if(!StringUtils.isEmpty(hardCampaign.getTnc()))
						tnc+=hardCampaign.getTnc()+"<br/>";
						for(NowTvPackUI hardpacks :hardCampaign.getTvPacks()){
							
							if(hardpacks.isSelected()){
								serviceInfo = new RptServiceInfoDTO();
								
								if(!StringUtils.isEmpty(hardpacks.getTnc()))
									tnc+=hardpacks.getTnc()+"<br/>";
								
								
							}
							
						}
						for(NowTvVasBundle vasBundle:hardCampaign.getVasBundles() )
							if( !StringUtils.isEmpty(vasBundle.getTnc()))
									tnc+=vasBundle.getTnc()+"<br/>";
						logger.debug("HB: "+tnc);
						//tnc=tnc.replace("null<br/>","");
						if(tnc!=""){
							serviceInfo.setItemHtml(tnc);
							servPlanList.add(serviceInfo);
						}
						
						serviceInfo = new RptServiceInfoDTO();
						String topTnc="";
						for(NowTvTopUpUI hardtopups:hardCampaign.getTopUps()){
							if(hardtopups.isSelected()&& !StringUtils.isEmpty(hardtopups.getTnc())){
								topTnc+=hardtopups.getTnc()+"<br/>";	
							}
						}
						if(topTnc!=""){
							serviceInfo.setItemHtml(topTnc);
							servPlanList.add(serviceInfo);
						}
						}
					
					for(NowTvCampaignUI campaigns:newPricingItem.getPayTvCampaign()){
						if(campaigns.isSelected()){
							String tnc="";
							if(!StringUtils.isEmpty(campaigns.getTnc()))
								tnc+=campaigns.getTnc()+"<br/>";
							for(NowTvPackUI packs :campaigns.getTvPacks()){
								if(packs.isSelected()){
									serviceInfo = new RptServiceInfoDTO();
									
									if(!StringUtils.isEmpty(packs.getTnc()))
										tnc+=packs.getTnc()+"<br/>";
								}
							}
							for(NowTvVasBundle vasBundle:campaigns.getVasBundles() )
								if( !StringUtils.isEmpty(vasBundle.getTnc()))
										tnc+=vasBundle.getTnc()+"<br/>";
							//tnc=tnc.replace("null<br/>","");
							logger.debug("PB: "+tnc);
							if(tnc!=""){
								serviceInfo.setItemHtml(tnc);
								servPlanList.add(serviceInfo);
							}
							
							serviceInfo = new RptServiceInfoDTO();
							String topTnc="";
							for(NowTvTopUpUI topups:campaigns.getTopUps()){
								if(topups.isSelected()&& !StringUtils.isEmpty(topups.getTnc())){
									topTnc+=topups.getTnc()+"<br/>";
								}
							}
							if(topTnc!=""){
								serviceInfo.setItemHtml(topTnc);
								servPlanList.add(serviceInfo);
							}
						}
						
					}
					
						
					
					
				}else{
					logger.debug("Non-New Pricing");
				if (servicePlan.getNtvServiceDetail().getNtvFreeItemList().size() > 0 ||
					servicePlan.getNtvServiceDetail().getNtvPayItemList().size() > 0 ||
					servicePlan.getNtvServiceDetail().getNtvOtherItemList().size() > 0) {
						serviceInfo = new RptServiceInfoDTO();
						//serviceInfo.setItemHtml("<br />");
						//servPlanList.add(serviceInfo);
//						serviceInfo.setItemHtml("<b>now TV Bundle:</b>");
						servPlanList.add(tempTermDesc04);
					for (int j=0; j < servicePlan.getNtvServiceDetail().getNtvFreeItemList().size(); j++) {
						serviceInfo = new RptServiceInfoDTO();
						tempHtml = servicePlan.getNtvServiceDetail().getNtvFreeItemList().get(j).getItemDetails();
						if (tempHtml != null && tempHtml.length() > 0) {
							if(tempHtml.indexOf(((char)10)+"")!=-1){
								tempHtml = "<b>" + tempHtml.replaceFirst(((char)10)+"", "</b><br />");
							}else if(tempHtml.indexOf("<br/>")!=-1){
								tempHtml = "<b>" + tempHtml.replaceFirst("<br/>", "</b><br />");
							}
							tempHtml = tempHtml.replaceAll(((char)10)+"", "<br />");
							tempHtml = tempHtml + "<br />";
							serviceInfo.setItemHtml(tempHtml);
							servPlanList.add(serviceInfo);
						}
					}
				
					for (int j=0; j < servicePlan.getNtvServiceDetail().getNtvPayItemList().size(); j++) {
						serviceInfo = new RptServiceInfoDTO();
						tempHtml = servicePlan.getNtvServiceDetail().getNtvPayItemList().get(j).getItemDetails();
						if (tempHtml != null && tempHtml.length() > 0) {
							if(tempHtml.indexOf(((char)10)+"")!=-1){
								tempHtml = "<b>" + tempHtml.replaceFirst(((char)10)+"", "</b><br />");
							}else if(tempHtml.indexOf("<br/>")!=-1){
								tempHtml = "<b>" + tempHtml.replaceFirst("<br/>", "</b><br />");
							}
							tempHtml = tempHtml.replaceAll(((char)10)+"", "<br />");
							tempHtml = tempHtml + "<br />";
							serviceInfo.setItemHtml(tempHtml);
							servPlanList.add(serviceInfo);
						}
					}
				
					for (int j=0; j < servicePlan.getNtvServiceDetail().getNtvOtherItemList().size(); j++) {
						serviceInfo = new RptServiceInfoDTO();
						tempHtml = servicePlan.getNtvServiceDetail().getNtvOtherItemList().get(j).getItemDetails();
						if (tempHtml != null && tempHtml.length() > 0 && (!isOnlyOneLine(tempHtml))) {
							if(tempHtml.indexOf(((char)10)+"")!=-1){
								tempHtml = "<b>" + tempHtml.replaceFirst(((char)10)+"", "</b><br />");
							}else if(tempHtml.indexOf("<br/>")!=-1){
								tempHtml = "<b>" + tempHtml.replaceFirst("<br/>", "</b><br />");
							}
							tempHtml = tempHtml.replaceAll(((char)10)+"", "<br />");
							tempHtml = tempHtml + "<br />";
							serviceInfo.setItemHtml(tempHtml);
							servPlanList.add(serviceInfo);
						}
					}
				}
			}

				// New Media Option
				if (servicePlan.getOptServiceDetail().getMediaItemList().size() > 0) {
					serviceInfo = new RptServiceInfoDTO();
//					serviceInfo.setItemHtml("<br />");
//					servPlanList.add(serviceInfo);
//					serviceInfo.setItemHtml("<b>New Media Option:</b>");
					servPlanList.add(tempTermDesc06);
				
					for (int j=0; j < servicePlan.getOptServiceDetail().getMediaItemList().size(); j++) {
						serviceInfo = new RptServiceInfoDTO();
						tempHtml = servicePlan.getOptServiceDetail().getMediaItemList().get(j).getItemDetails();
						if (tempHtml != null && tempHtml.length() > 0) {
							if(tempHtml.indexOf(((char)10)+"")!=-1){
								tempHtml = "<b>" + tempHtml.replaceFirst(((char)10)+"", "</b><br />");
							}else if(tempHtml.indexOf("<br/>")!=-1){
								tempHtml = "<b>" + tempHtml.replaceFirst("<br/>", "</b><br />");
							}
							tempHtml = tempHtml.replaceAll(((char)10)+"", "<br />");
							tempHtml = tempHtml + "<br />";
							serviceInfo.setItemHtml(tempHtml);
							servPlanList.add(serviceInfo);
						}
					}
				}
				rptImsAppServDTO.setServPlanDtls(servPlanList);
				
				rptImsAppServDTO.setQosMeasureInd(servicePlan.getQosMeasureInd());

			} else if (obj instanceof ImsSignoffDTO) {
				if (ImsSignoffDTO.SignatureTypeEnum.ThirdParty_SIGN == ((ImsSignoffDTO) obj).getSignatureType()){
					thirdPartySign = (ImsSignoffDTO) obj;
					logger.debug("inside ImsReportServiceImpl, ThirdParty_SIGN received");
				}else if (ImsSignoffDTO.SignatureTypeEnum.CREDIT_CARD_SIGN 	== ((ImsSignoffDTO) obj).getSignatureType()){
					creditCardsign = (ImsSignoffDTO) obj;
					logger.debug("inside ImsReportServiceImpl, CREDIT_CARD_SIGN received");
				}else if (ImsSignoffDTO.SignatureTypeEnum.CUSTOMER_SIGN 	== ((ImsSignoffDTO) obj).getSignatureType()){
					customerSign = (ImsSignoffDTO) obj;
					logger.debug("inside ImsReportServiceImpl, signature received");
				}else if (ImsSignoffDTO.SignatureTypeEnum.CareCash_SIGN 	== ((ImsSignoffDTO) obj).getSignatureType()){
					careCashSign = (ImsSignoffDTO) obj;
					logger.debug("inside ImsReportServiceImpl, careCashSign received");
				}
			}
		}

		if(cOrder){			

			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			//logger.debug("servicePlan.getImsWQDTO().getApplicationDate():"+servicePlan.getImsWQDTO().getApplicationDate());
			rptImsAppServDTO.setWq_Application_date(dateFormat.format(order.getAppDate()));
			logger.debug("getResourceShortage:"+order.getInstallAddress().getAddrInventory().getResourceShortage());
			if("Y".equals(order.getInstallAddress().getAddrInventory().getResourceShortage())){
				rptImsAppServDTO.setWq_Earliest_srd_reason(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("WQEarliestSrdReason").getItemHtml());
			}
			String temp = servicePlan.getWqRemarkForInterialUse();
			logger.debug("temp:"+temp);
			String[] array = temp.split((char)10+"");
			for(int m=0;m<array.length;m++){
				logger.debug("array:"+array[m]);
				RptServiceInfoDTO bomOrderRemarks = new RptServiceInfoDTO();
				bomOrderRemarks.setItemHtml(array[m]);
				internalUseRemark.add(bomOrderRemarks);
			}
			rptImsAppServDTO.setBomOrderRemarks(internalUseRemark);
			
			//Eddie 20150813 Add Welcome Letter for FS
			if (order.getSMSno()!=null && !"".equals(order.getSMSno())){
				rptImsAppServDTO.setSms_email("SMS " + order.getSMSno() + " " + order.getEsigEmailLang());
			}
			else if(order.getEmailAddress()!=null && !"".equals(order.getEmailAddress())){
				rptImsAppServDTO.setSms_email("EMAIL " + order.getEmailAddress() + " " + order.getEsigEmailLang());
			}
			else {
				if(order.getCustomer().getContact().getContactPhone()!=null && !"".equals(order.getCustomer().getContact().getContactPhone())){
					rptImsAppServDTO.setSms_email("SMS " + order.getCustomer().getContact().getContactPhone() + " " + order.getEsigEmailLang());
				}
				else if(order.getAppointment().getInstSmsNum()!=null && !"".equals(order.getAppointment().getInstSmsNum())){
					rptImsAppServDTO.setSms_email("SMS " + order.getAppointment().getInstSmsNum() + " " + order.getEsigEmailLang());
				}
				else if(order.getCustomer().getAccount().getEmailAddr()!=null && !"".equals(order.getCustomer().getAccount().getEmailAddr())){
					rptImsAppServDTO.setSms_email("EMAIL " + order.getCustomer().getAccount().getEmailAddr() + " " + order.getEsigEmailLang());
				}
				else
					rptImsAppServDTO.setSms_email("EMAIL " + order.getLoginId() + "@netvigator.com" + " " + order.getEsigEmailLang());
			}
		}
		
		if(isCC){
			rptImsAppServDTO.setSalesChannel(order.getSalesChannel());
			rptImsAppServDTO.setShopCd(order.getSourceCd());
		}
		
		if(cOrder){			
			rptImsAppServDTO.setShow_Section_THINGS_TO_KNOW(false);		//1
//			rptImsAppServDTO.setShow_Section_CUSTOMER_DETAILS(false);		//2
			rptImsAppServDTO.setShow_Section_SERVICE_PROVIDER(false);		//3
//			rptImsAppServDTO.setShow_Section_CORE_SERVICES(false);		//4
//			rptImsAppServDTO.setShow_Section_OPTIONAL_SERVICES(false);		//5
			rptImsAppServDTO.setShow_Section_GIFTS_AND_PREMIUMS(false);		//6
//			rptImsAppServDTO.setShow_Section_CHARGES_FOR_CORE_SERVICES(false);		//7
//			rptImsAppServDTO.setShow_Section_BILL_PREFERENCES(false);		//8
			rptImsAppServDTO.setShow_Section_IMPORTANT_INFORMATION(false);		//9
			rptImsAppServDTO.setShow_Section_GLOSSARY(false);		//10
			rptImsAppServDTO.setShow_Section_DETAILS(false);		//11
//			rptImsAppServDTO.setShow_Section_PAYMENT(false);		//12
//			rptImsAppServDTO.setShow_Section_PERSONAL(false);		//13
			rptImsAppServDTO.setShow_Section_AGREEMENT(false);		//15
//			rptImsAppServDTO.setShow_Section_Interial_Use(false);		//16
			rptImsAppServDTO.setcOrder(true);		
		}

		
		rptImsAppServDTO.setCustomerCopyInd("N");
		// S&S Customer Copy
		BeanUtils.copyProperties(rptImsAppServCustCopyDTO, rptImsAppServDTO);
		rptImsAppServCustCopyDTO.setCustomerCopyInd("Y");
		
		if(Signed){
			int signatureSize=0;
			if(customerSign!=null){
				try {
					signatureSize = customerSign.getSignatureInputStream().available();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					signatureSize = 0;
				}
			}
			if(signOffed&&(customerSign==null||signatureSize<=0)&&"E".equalsIgnoreCase(disMode)){
				signOffLogService.signOffOrderLog(order, "EmptySignature", "Customer signature is empty.");
				throw new ImsEmptySignatureException();
			}
			if(customerSign!=null){
				rptImsAppServDTO.setCustSignature(customerSign.getSignatureInputStream());
				rptImsAppServCustCopyDTO.setCustSignature(customerSign.getSignatureInputStream());
				rptIms3PartyCreditCardDTO.setCustSignature(customerSign.getSignatureInputStream());
				logger.debug("inside ImsReportServiceImpl, customer sign is not null");
			}
			if(creditCardsign!=null){
				rptImsAppServDTO.setCustSignaturePay(creditCardsign.getSignatureInputStream());
				rptImsAppServCustCopyDTO.setCustSignaturePay(creditCardsign.getSignatureInputStream());
				logger.debug("inside ImsReportServiceImpl, Credit Card sign is not null");
			}
			if(thirdPartySign!=null){
				rptImsAppServDTO.setCustSignaturePay(thirdPartySign.getSignatureInputStream());
				rptImsAppServCustCopyDTO.setCustSignaturePay(thirdPartySign.getSignatureInputStream());
				rptIms3PartyCreditCardDTO.setThirdPartySignature(thirdPartySign.getSignatureInputStream());
				logger.debug("inside ImsReportServiceImpl, 3Party Sign is not null");
			}
			if(careCashSign!=null){
				rptImsAppServDTO.setCareCashSignature(careCashSign.getSignatureInputStream());
				logger.debug("inside ImsReportServiceImpl, careCash Sign is not null");
			}
		}
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy");
		Date date = new Date();
		rptImsAppServDTO.setAfFooter(dateFormat.format(date)+ " Hong Kong Telecommunications (HKT) Limited");
		
		ssList.add(rptImsAppServDTO);	
		ssCustCopyList.add(rptImsAppServCustCopyDTO);

		// Add Application for Service Form
		String key;
			 key = imsRetailJasperName + (RPT_LANG_ZH.equalsIgnoreCase(pLang) ? FORM_ZH_EXT : "");
		map.put(key, new JasperReport(key, ssList));
		map.put(key + CUST_COPY, new JasperReport(key, ssCustCopyList));

		// Add Credit Card Authorization Form
		if (isReq3PartyCreditCardForm&&!isCC) {
			ccList.add(rptIms3PartyCreditCardDTO);
			map.put(imsRetail3PartyJasperName, new JasperReport(imsRetail3PartyJasperName, ccList));			
		}

		String careCashChannelCode = "";

		if(!isCC&&!"DS".equals(order.getImsOrderType())){
			careCashChannelCode = order.getShopCd();
		}else{
			if(order.getSalesChannel()!=null&&!order.getSalesChannel().isEmpty()){
				careCashChannelCode = order.getSalesChannel();
			}else{
				careCashChannelCode = order.getShopCd();
			}
		}
		
		if ("I".equalsIgnoreCase(order.getCustomer().getCareCashInd()))
		{
			setRptIGuardCareCashDTO(rptImsAppServDTO, disMode, order.getCustomer().getCareCashOptOutInd(), order.getCustomer().getCareCashEmail(), order.getCustomer().getCareCashMobile(), order.getAppDate(), careCashChannelCode);
			map.put(imsCareCashJasperName, new JasperReport(imsCareCashJasperName, ssList));	
		}
		
//		//Cooling off period
//		if (true) {
//			ccList.add(rptIms3PartyCreditCardDTO);
//			map.put(imsDsCPJasperName, new JasperReport(imsDsCPJasperName, ccList));			
//		}
		
		return map;
	}	
	
	//PT AF
	private HashMap<String, JasperReport> mapPremierReportData(ArrayList<Object> pDTOs, String pLang, Boolean Signed, Boolean isCC,Boolean cOrder, String disMode, Boolean signOffed)//gary
	throws InvocationTargetException, IllegalAccessException, ImsEmptySignatureException {

String planCd = "("+"";
String campignCd =  "("+"";
Boolean isPT =true;

HashMap<String, HashMap<String, RptServiceInfoDTO>> dbRptstaticWords  = new HashMap<String, HashMap<String, RptServiceInfoDTO>>();
dbRptstaticWords=this.getDBRptStaticWords(pDTOs);

if(dbRptstaticWords==null){
	logger.debug("dbRptstaticWords==null, use properties files");
}else{
	logger.debug("dbRptstaticWords!=null, use db words");
}
ImsSignoffDTO customerSign = null;
ImsSignoffDTO creditCardsign = null;
ImsSignoffDTO thirdPartySign = null;
ImsSignoffDTO careCashSign = null;
OrderImsUI order = null;
ImsRptServicePlanDetailDTO servicePlan = null;
int fixedTerm = 0;
int extenTerm = 0;
int totalTerm = 0;

String configFiles = "";
configFiles = "imsReportConstant.xml";
ApplicationContext appCtx = new ClassPathXmlApplicationContext(configFiles);

MessageSource msg = new ClassPathXmlApplicationContext(configFiles);

HashMap<String, JasperReport> map = new HashMap<String, JasperReport>();
ArrayList<ReportDTO> ssList = new ArrayList<ReportDTO>();
ArrayList<ReportDTO> ssCustCopyList = new ArrayList<ReportDTO>();
ArrayList<ReportDTO> ccList = new ArrayList<ReportDTO>();


RptImsAppServDTO rptImsAppServDTO = new RptImsAppServDTO();
RptImsAppServDTO rptImsAppServCustCopyDTO = new RptImsAppServDTO();
RptIms3PartyCreditCardDTO rptIms3PartyCreditCardDTO = new RptIms3PartyCreditCardDTO();

Boolean isShowAllForTest = false;
if(this.isAFShowALL(pDTOs)&&this.isAFShowAllByID(pDTOs)){
	isShowAllForTest = true;
}
if(isShowAllForTest){
	rptImsAppServDTO.setShowAllForTest(true);
	rptImsAppServDTO.setShowRegHKTLoginId(false);
}

if ("zh".equals(pLang)){
	rptImsAppServDTO.setNetvigatorLogo(imageFilePath + "/"	+ NETVIGATOR_LOGO_FILE_ZH);
	rptImsAppServDTO.setFooterPccwLogo(imageFilePath + "/"	+ FOOTER_PCCW_LOGO_FILE_ZH);
	rptImsAppServDTO.setFooterHktLogo(imageFilePath + "/"	+ FOOTER_HKT_LOGO_FILE_ZH);
}else{
	rptImsAppServDTO.setNetvigatorLogo(imageFilePath + "/"	+ NETVIGATOR_LOGO_FILE_EN);
	rptImsAppServDTO.setFooterPccwLogo(imageFilePath + "/"	+ FOOTER_PCCW_LOGO_FILE_EN);
	rptImsAppServDTO.setFooterHktLogo(imageFilePath + "/"	+ FOOTER_HKT_LOGO_FILE_EN);
}	
rptImsAppServDTO.setCompanyLogo(imageFilePath + "/"	+ COMPANY_LOGO_FILE);
rptImsAppServCustCopyDTO.setCompanyLogo(imageFilePath + "/"	+ COMPANY_LOGO_FILE);
rptIms3PartyCreditCardDTO.setCompanyLogo(imageFilePath + "/"	+ COMPANY_LOGO_FILE);

Object obj = null;
boolean isReq3PartyCreditCardForm = false;		

ArrayList<RptServiceDetailDTO> coreServiceList = new ArrayList<RptServiceDetailDTO>();
ArrayList<RptServiceDetailDTO> vasAndOptList = new ArrayList<RptServiceDetailDTO>();
ArrayList<RptServiceInfoDTO> coreChrgList = new ArrayList<RptServiceInfoDTO>();
ArrayList<RptServiceInfoDTO> imprtInfoList = new ArrayList<RptServiceInfoDTO>();
ArrayList<RptServiceInfoDTO> glossaryList = new ArrayList<RptServiceInfoDTO>();
ArrayList<RptServiceInfoDTO> servPlanList = new ArrayList<RptServiceInfoDTO>();
ArrayList<RptServiceInfoDTO> serviceProvideList = new ArrayList<RptServiceInfoDTO>();
ArrayList<RptServiceInfoDTO> personalInfoListEC = new ArrayList<RptServiceInfoDTO>();
ArrayList<RptServiceInfoDTO> personalInfoListNewCustOptIn = new ArrayList<RptServiceInfoDTO>();
ArrayList<RptServiceInfoDTO> personalInfoListNewCustOptOut = new ArrayList<RptServiceInfoDTO>();
ArrayList<RptServiceInfoDTO> custAgreeList = new ArrayList<RptServiceInfoDTO>();
ArrayList<RptServiceInfoDTO> creditCardList = new ArrayList<RptServiceInfoDTO>();
ArrayList<RptServiceInfoDTO> kisList = new ArrayList<RptServiceInfoDTO>();
ArrayList<RptServiceInfoDTO> hktServPortalList = new ArrayList<RptServiceInfoDTO>();
ArrayList<RptServiceInfoDTO> internalUseRemark = new ArrayList<RptServiceInfoDTO>();

if ("zh".equals(pLang)){
	rptImsAppServDTO.setLocale("zh_HK");
}else{
	rptImsAppServDTO.setLocale("en");
}	

//RptServiceInfoDTO	tempKIS01	  = new RptServiceInfoDTO();
RptServiceInfoDTO	tempKIS02	  = new RptServiceInfoDTO();
RptServiceInfoDTO	tempKIS03	  = new RptServiceInfoDTO();
RptServiceInfoDTO	tempKIS04	  = new RptServiceInfoDTO();
RptServiceInfoDTO	tempKIS05	  = new RptServiceInfoDTO();
RptServiceInfoDTO	tempKIS06	  = new RptServiceInfoDTO();




if("en".equals(rptImsAppServDTO.getLocale())){
	//tempKIS01 = (RptServiceInfoDTO) appCtx.getBean("kisEng01");
	tempKIS02 = (RptServiceInfoDTO) appCtx.getBean("kisEng02");
	tempKIS03 = (RptServiceInfoDTO) appCtx.getBean("kisEng03");
	tempKIS04 = (RptServiceInfoDTO) appCtx.getBean("kisEng04");
	tempKIS05 = (RptServiceInfoDTO) appCtx.getBean("kisEng05");
	tempKIS06 = (RptServiceInfoDTO) appCtx.getBean("kisEng06");	
}else if("zh_HK".equals(rptImsAppServDTO.getLocale())){
	//tempKIS01 = (RptServiceInfoDTO) appCtx.getBean("kisChi01");
	tempKIS02 = (RptServiceInfoDTO) appCtx.getBean("kisChi02");
	tempKIS03 = (RptServiceInfoDTO) appCtx.getBean("kisChi03");
	tempKIS04 = (RptServiceInfoDTO) appCtx.getBean("kisChi04");
	tempKIS05 = (RptServiceInfoDTO) appCtx.getBean("kisChi05");
	tempKIS06 = (RptServiceInfoDTO) appCtx.getBean("kisChi06");			
};
 if(dbRptstaticWords!=null){
	 tempKIS02.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("kis02"));
	 tempKIS03.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("kis03"));
	 tempKIS04.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("kis04"));
	 tempKIS05.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("kis05"));
	 tempKIS06.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("kis06"));
 };
//kisList.add(tempKIS01);
kisList.add(tempKIS02);
kisList.add(tempKIS03);
kisList.add(tempKIS04);
kisList.add(tempKIS05);
kisList.add(tempKIS06);
rptImsAppServDTO.setKisDtls(kisList);

for (int i = 0; i < pDTOs.size(); i++) {
	obj = pDTOs.get(i);
	if (obj instanceof OrderImsUI) {
		order = (OrderImsUI) obj;
		
		
	
		rptImsAppServDTO.setAppNo(order.getOrderId());
		rptIms3PartyCreditCardDTO.setAppNo(order.getOrderId());
		
		
		logger.debug("section A - customer details");
		rptImsAppServDTO.setNewCustInd("Y");
		if (order.getCustomer() != null) {
			if("Y".equalsIgnoreCase(order.getHasBBDailup())){
				rptImsAppServDTO.setNewCustInd("N");
			}else{
				rptImsAppServDTO.setNewCustInd("Y");
			}
			rptImsAppServDTO.setTitle(order.getCustomer().getTitle());
			rptImsAppServDTO.setLastName(order.getCustomer().getLastName());
			rptImsAppServDTO.setFirstName(order.getCustomer().getFirstName());
			if(order.getCustomer().getLastName().length()>13 || order.getCustomer().getFirstName().length()>13){//Gary
				rptImsAppServDTO.setIsNameTooLong(true);
			}else{
				rptImsAppServDTO.setIsNameTooLong(false);
			}
			rptIms3PartyCreditCardDTO.setCustLastName(order.getCustomer().getLastName());
			rptIms3PartyCreditCardDTO.setCustFirstName(order.getCustomer().getFirstName());
			rptImsAppServDTO.setDob(Utility.date2String(order.getCustomer().getDob(),"dd/MM/yyyy"));
			rptImsAppServDTO.setIdDocType(order.getCustomer().getIdDocType());
			rptImsAppServDTO.setIdDocVerifyInd(order.getCustomer().getIdVerified());
			rptImsAppServDTO.setIdDocNum(order.getCustomer().getIdDocNum());
			//Gary
			rptImsAppServDTO.setCompanyName(order.getCustomer().getCompanyName());
			//isCC
			rptImsAppServDTO.setIsCC(isCC);
			//end
			rptIms3PartyCreditCardDTO.setIdDocType(order.getCustomer().getIdDocType());
			rptIms3PartyCreditCardDTO.setIdDocNum(order.getCustomer().getIdDocNum());
			if (order.getCustomer().getContact() != null) { 
				String custContact = order.getCustomer().getContact().getContactPhone();
				if(custContact!= null && custContact.length()>=8){
					custContact = custContact.substring(0, 8);					
				}
				rptImsAppServDTO.setContactPhone(custContact);				
				rptImsAppServDTO.setContactEmail(order.getCustomer().getContact().getEmailAddr());
				rptIms3PartyCreditCardDTO.setContactPhone(custContact);				
				rptImsAppServDTO.setOtherPhone(order.getCustomer().getContact().getOtherPhone());
			}
		}

		if (order.getFixedLineNo() == null || (order.getFixedLineNo().isEmpty())) {
			rptImsAppServDTO.setFixedLineNumPccwInd("N");
		} else {
			rptImsAppServDTO.setFixedLineNumPccwInd("Y");
		}
		rptImsAppServDTO.setFixedLineNum(order.getFixedLineNo());

		if (order.getCustomer() != null) {
			if (order.getCustomer().getAccount() != null) {
				rptImsAppServDTO.setEmailAddr(order.getCustomer().getAccount().getEmailAddr());				
			}
		}
		logger.debug("section A - installation address");
		if (order.getInstallAddress() != null) {
			rptImsAppServDTO.setFlat(order.getInstallAddress().getUnitNo());
			rptImsAppServDTO.setFloor(order.getInstallAddress().getFloorNo());
			rptImsAppServDTO.setBuildingName(order.getInstallAddress().getBuildNo());
			rptImsAppServDTO.setLotNum(order.getInstallAddress().getHiLotNo());
			rptImsAppServDTO.setStreetNum(order.getInstallAddress().getStrNo());
			rptImsAppServDTO.setStreetName(order.getInstallAddress().getStrName());
			rptImsAppServDTO.setStreetCatgDesc(order.getInstallAddress().getStrCatDesc());
			rptImsAppServDTO.setSectionDesc(order.getInstallAddress().getSectDesc());
			rptImsAppServDTO.setDistrictDesc(order.getInstallAddress().getDistDesc());
			rptImsAppServDTO.setAreaCode(order.getInstallAddress().getAreaCd());
		}
		logger.debug("section A - billing address");
		if ( order.getBillingAddress() == null || (order.getBillingAddress().getHiLotNo().equals("") && order.getBillingAddress().getBuildNo().equals("") && order.getBillingAddress().getStrNo().equals(""))) {
			rptImsAppServDTO.setNoBillingAddressFlag(false); 
		} else {
			rptImsAppServDTO.setNoBillingAddressFlag(true); 
		}
		if (rptImsAppServDTO.isNoBillingAddressFlag()) {
			rptImsAppServDTO.setBillingFlat(order.getBillingAddress().getUnitNo());
			rptImsAppServDTO.setBillingFloor(order.getBillingAddress().getFloorNo());
			rptImsAppServDTO.setBillingBuildingName(order.getBillingAddress().getBuildNo());
			rptImsAppServDTO.setBillingLotNum(order.getBillingAddress().getHiLotNo());
			rptImsAppServDTO.setBillingStreetNum(order.getBillingAddress().getStrNo());
			rptImsAppServDTO.setBillingStreetName(order.getBillingAddress().getStrName());
			rptImsAppServDTO.setBillingStreetCatgDesc(order.getBillingAddress().getStrCatDesc());
			rptImsAppServDTO.setBillingSectionDesc(order.getBillingAddress().getSectDesc());
			rptImsAppServDTO.setBillingDistrictDesc(order.getBillingAddress().getDistDesc());
			rptImsAppServDTO.setBillingAreaCode(order.getBillingAddress().getAreaCd());
		}

		logger.debug("section B - service provider");
		if(dbRptstaticWords!=null){
			serviceProvideList.add(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("serviceProvide01"));
		}else{
			if ("en".equals(rptImsAppServDTO.getLocale())) {					
				serviceProvideList.add((RptServiceInfoDTO) appCtx.getBean("serviceProvideEng01"));
			} else if ("zh_HK".equals(rptImsAppServDTO.getLocale())) {
				serviceProvideList.add((RptServiceInfoDTO) appCtx.getBean("serviceProvideChi01"));
			}
		}
			
		
		rptImsAppServDTO.setServiceProvideDtls(serviceProvideList);
		logger.debug("section C - core service");
		String startTime, endTime, ampm;
		
		rptImsAppServDTO.setPreInstInd(order.getPreInstallInd());
		rptImsAppServDTO.setMobileOfferInd(order.getMobileOfferInd());
		
		if("R".equalsIgnoreCase(order.getMode())){
			rptImsAppServDTO.setRetailMode("Y");
		}else{
			rptImsAppServDTO.setRetailMode("N");
		}

		logger.debug(">>Total Term=" + order.getWarrPeriod() + " >>Exten Term=" + order.getTermExt());
		if (order.isMonthlyPlan().equalsIgnoreCase("Y")) {
			rptImsAppServDTO.setFixedTerm("");
			rptImsAppServDTO.setExtenTerm("");
			rptImsAppServDTO.setTotalTerm("");
		} else {
			if (order.getTermExt() == null || order.getTermExt() == "" || (Integer.parseInt(order.getTermExt())==0)) {
				rptImsAppServDTO.setFixedTerm(order.getWarrPeriod());
				rptImsAppServDTO.setExtenTerm("");
				rptImsAppServDTO.setTotalTerm("");
			} else {
				totalTerm = Integer.parseInt(order.getWarrPeriod());
				extenTerm = Integer.parseInt(order.getTermExt());
				fixedTerm = totalTerm - extenTerm;
				rptImsAppServDTO.setTotalTerm(order.getWarrPeriod());
				rptImsAppServDTO.setExtenTerm(order.getTermExt());
				rptImsAppServDTO.setFixedTerm(((fixedTerm < 0)?"":fixedTerm+""));
			}
		}
		
		if (order.getAppointment() != null && order.getAppointment().getAppntEndDateDisplay() != null) {
			String tempTargetInstallDate;
			if(cOrder){
				tempTargetInstallDate=Utility.date2String(order.getAppointment().getAppntStartDate(), "dd/MM/yyyy");
			}else{
				tempTargetInstallDate=Utility.date2String(order.getAppointment().getAppntEndDateDisplay(), "dd/MM/yyyy");
				//order.getAppointment().getAppntStartDate() T+7
				//order.getAppointment().getAppntEndDateDisplay() T+30
			}
			rptImsAppServDTO.setTargetInstallDate(tempTargetInstallDate);
			if (order.isBookingNotAllowed().equalsIgnoreCase("Y")) {
				if(cOrder&&"Y".equalsIgnoreCase(order.getInstallAddress().getAddrInventory().getResourceShortage())){
					rptImsAppServDTO.setTargetInstallTimeSlot("10:00-18:00");
				}	
			} else {						
				startTime = Utility.date2String(order.getAppointment().getAppntStartDateDisplay(), "h");
				endTime = Utility.date2String(order.getAppointment().getAppntEndDateDisplay(), "h");
				
				if(Integer.parseInt(Utility.date2String(order.getAppointment().getAppntStartDateDisplay(), "H"))==9 &&
						Integer.parseInt(Utility.date2String(order.getAppointment().getAppntEndDateDisplay(), "H"))==13 ){
					startTime="10";
					endTime="1";								
				}else if(Integer.parseInt(Utility.date2String(order.getAppointment().getAppntStartDateDisplay(), "H"))==9 &&
						Integer.parseInt(Utility.date2String(order.getAppointment().getAppntEndDateDisplay(), "H"))==18 ){
					startTime="10";
					endTime="6";	
				}
				
				if (Integer.parseInt(Utility.date2String(order.getAppointment().getAppntEndDateDisplay(), "H")) < 12) {
					ampm = "am";
				} else {
					ampm = "pm"; 
				}
				rptImsAppServDTO.setTargetInstallTimeSlot(startTime + "-" + endTime + ampm);
			}
		}
		if (order.getTargetCommDate() == null) {
			
			rptImsAppServDTO.setTargetCommDate(Utility.date2String(order.getAppointment().getAppntEndDateDisplay(), "dd/MM/yyyy"));
			if("Y".equalsIgnoreCase(order.getInstallAddress().getAddrInventory().getResourceShortage())&& !"Y".equalsIgnoreCase(order.getPreInstallInd())){				
				rptImsAppServDTO.setTargetCommDate(Utility.date2String(order.getAppointment().getAppntStartDate(), "dd/MM/yyyy"));
			}
		} else {
			rptImsAppServDTO.setTargetCommDate(Utility.date2String(order.getTargetCommDate(), "dd/MM/yyyy"));
		}
		rptImsAppServDTO.setLoginId(order.getLoginId() + "@netvigator.com");
		rptImsAppServDTO.setBillingEmailAddr(order.getCustomer().getAccount().getEmailAddr());

		if(!cOrder&&"Y".equalsIgnoreCase(order.getInstallAddress().getAddrInventory().getResourceShortage())){
			rptImsAppServDTO.setTargetInstallDate(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("appointmentInsuffResource").getItemHtml());
			rptImsAppServDTO.setTargetInstallTimeSlot(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("appointmentInsuffResource").getItemHtml());
			rptImsAppServDTO.setTargetCommDate(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("appointmentInsuffResource").getItemHtml());
		}

		// core service description
		rptImsAppServDTO.setBandwidth(order.getBandwidth() + "M");


		// section D - optional premium, optional services and values added services
		// in part 2

		// section E - gifts and premiums
		// in part 2

		logger.debug("section F - charges for core services / optional services / values added services / miscellaneous");
		

		RptServiceInfoDTO tempCoreCharge01 = new RptServiceInfoDTO();//
		RptServiceInfoDTO tempCoreCharge01A = new RptServiceInfoDTO();
		RptServiceInfoDTO tempCoreCharge01B = new RptServiceInfoDTO();
		RptServiceInfoDTO tempCoreCharge01C = new RptServiceInfoDTO();
		RptServiceInfoDTO tempCoreCharge01D = new RptServiceInfoDTO();
		RptServiceInfoDTO tempCoreCharge02 = new RptServiceInfoDTO();
		RptServiceInfoDTO tempCoreCharge02A = new RptServiceInfoDTO();//
		RptServiceInfoDTO tempCoreCharge02B = new RptServiceInfoDTO();//
		RptServiceInfoDTO tempCoreCharge03 = new RptServiceInfoDTO();
		RptServiceInfoDTO tempCoreCharge03AForShowAll = new RptServiceInfoDTO();//
		RptServiceInfoDTO tempCoreCharge03A = new RptServiceInfoDTO();//
		RptServiceInfoDTO tempCoreCharge03B = new RptServiceInfoDTO();//
		RptServiceInfoDTO tempCoreCharge03C = new RptServiceInfoDTO();//
		RptServiceInfoDTO tempCoreCharge03D = new RptServiceInfoDTO();//
		RptServiceInfoDTO tempCoreCharge03E = new RptServiceInfoDTO();
		RptServiceInfoDTO tempCoreCharge03F = new RptServiceInfoDTO();
		RptServiceInfoDTO tempCoreCharge03G = new RptServiceInfoDTO();
		RptServiceInfoDTO tempCoreCharge04 = new RptServiceInfoDTO();
		RptServiceInfoDTO tempCoreCharge05 = new RptServiceInfoDTO();
		RptServiceInfoDTO tempCoreCharge06 = new RptServiceInfoDTO();
		RptServiceInfoDTO tempCoreCharge07 = new RptServiceInfoDTO();
//		RptServiceInfoDTO tempCoreCharge08 = new RptServiceInfoDTO();//
//		RptServiceInfoDTO tempCoreCharge09 = new RptServiceInfoDTO();
		
//		if("en".equals(rptImsAppServDTO.getLocale())){
//			  tempCoreCharge01 = (RptServiceInfoDTO) appCtx.getBean("coreChargeEng01");
//			  tempCoreCharge02A = (RptServiceInfoDTO) appCtx.getBean("coreChargeEng02A");
//			  tempCoreCharge02B = (RptServiceInfoDTO) appCtx.getBean("coreChargeEng02B");
//	          tempCoreCharge03A =(RptServiceInfoDTO) appCtx.getBean("coreChargeEng03A");//
//	          tempCoreCharge03C =(RptServiceInfoDTO) appCtx.getBean("coreChargeEng03C");//Waived
//	          tempCoreCharge03D =(RptServiceInfoDTO) appCtx.getBean("coreChargeEng03D");
//	          tempCoreCharge04 = (RptServiceInfoDTO) appCtx.getBean("coreChargeEng04");
//	          tempCoreCharge05C = (RptServiceInfoDTO) appCtx.getBean("coreChargeEng05C");
//	          tempCoreCharge06 =(RptServiceInfoDTO) appCtx.getBean("coreChargeEng06");//
//	          tempCoreCharge07 =(RptServiceInfoDTO) appCtx.getBean("coreChargeEng07");//Waived
//	          tempCoreCharge08 =(RptServiceInfoDTO) appCtx.getBean("coreChargeEng08");
//		      tempCoreCharge09 =(RptServiceInfoDTO) appCtx.getBean("coreChargeEng09");
//		      
//		}else if("zh_HK".equals(rptImsAppServDTO.getLocale())){
//
//			  tempCoreCharge01 = (RptServiceInfoDTO) appCtx.getBean("coreChargeChi01");
//			  tempCoreCharge02A = (RptServiceInfoDTO) appCtx.getBean("coreChargeChi02A");
//			  tempCoreCharge02B = (RptServiceInfoDTO) appCtx.getBean("coreChargeChi02B");
//	          tempCoreCharge03A =(RptServiceInfoDTO) appCtx.getBean("coreChargeChi03A");//
//	          tempCoreCharge03C =(RptServiceInfoDTO) appCtx.getBean("coreChargeChi03C");//Waived
//	          tempCoreCharge03D =(RptServiceInfoDTO) appCtx.getBean("coreChargeChi03D");
//	          tempCoreCharge04 = (RptServiceInfoDTO) appCtx.getBean("coreChargeChi04");
//	          tempCoreCharge05C = (RptServiceInfoDTO) appCtx.getBean("coreChargeChi05C");
//	          tempCoreCharge06 =(RptServiceInfoDTO) appCtx.getBean("coreChargeChi06");//
//	          tempCoreCharge07 =(RptServiceInfoDTO) appCtx.getBean("coreChargeChi07");//Waived
//	          tempCoreCharge08 =(RptServiceInfoDTO) appCtx.getBean("coreChargeChi08");
//		      tempCoreCharge09 =(RptServiceInfoDTO) appCtx.getBean("coreChargeChi09");
//	         
//		}
	
	     if(dbRptstaticWords!=null){//setItemHtml left, setItemHtml2 right
	    	 
//			 tempCoreCharge01.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("coreCharge01"));//
//			 tempCoreCharge01.setItemHtml2(tempCoreCharge01.getItemHtml());
//			 tempCoreCharge01.setItemHtml(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("coreChargeTitle01").getItemHtml());
			 
			 tempCoreCharge01A.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("coreCharge02"));//for gary template
			 tempCoreCharge01A.setItemHtml2(tempCoreCharge01A.getItemHtml());
			 tempCoreCharge01A.setItemHtml(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("coreChargeTitle01").getItemHtml());
			 
			 tempCoreCharge01B.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("coreCharge01B"));
			 tempCoreCharge01B.setItemHtml2(tempCoreCharge01B.getItemHtml());
			 tempCoreCharge01B.setItemHtml(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("coreChargeTitle01").getItemHtml());
			 
			 tempCoreCharge01C.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("coreCharge01C"));
			 tempCoreCharge01C.setItemHtml2(tempCoreCharge01C.getItemHtml());
			 tempCoreCharge01C.setItemHtml(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("coreChargeTitle01").getItemHtml());
			 
			 tempCoreCharge01D.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("coreCharge01D"));
			 tempCoreCharge01D.setItemHtml2(tempCoreCharge01D.getItemHtml());
			 tempCoreCharge01D.setItemHtml(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("coreChargeTitle01").getItemHtml());
			 
			 tempCoreCharge02.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("coreCharge02"));
			 tempCoreCharge02.setItemHtml2(tempCoreCharge02.getItemHtml());
			 tempCoreCharge02.setItemHtml(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("coreChargeTitle02").getItemHtml());

//			 tempCoreCharge02A.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("coreCharge02A"));	//
//			 tempCoreCharge02A.setItemHtml2(tempCoreCharge02A.getItemHtml());
//			 tempCoreCharge02A.setItemHtml(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("coreChargeTitle02R").getItemHtml());	
//			 
//			 tempCoreCharge02B.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("coreCharge02B"));	//
//			 tempCoreCharge02B.setItemHtml2(tempCoreCharge02B.getItemHtml());
//			 tempCoreCharge02B.setItemHtml(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("coreChargeTitle02R").getItemHtml());	
			 
        	 tempCoreCharge03.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("coreCharge03"));
        	 tempCoreCharge03.setItemHtml2(tempCoreCharge03.getItemHtml());
			 tempCoreCharge03.setItemHtml(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("coreChargeTitle03").getItemHtml());
//			 tempCoreCharge03AForShowAll.copy(tempCoreCharge03A);
        	 
//        	 tempCoreCharge03A.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("coreCharge03A"));//
//        	 tempCoreCharge03A.setItemHtml2(tempCoreCharge03A.getItemHtml());
//        	 tempCoreCharge03A.setItemHtml(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("coreChargeTitle03R").getItemHtml());
//        	 
//        	 tempCoreCharge03B.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("coreCharge03B"));//
//        	 tempCoreCharge03B.setItemHtml2(tempCoreCharge03B.getItemHtml());
//        	 tempCoreCharge03B.setItemHtml(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("coreChargeTitle03R").getItemHtml());
//        	 
			 tempCoreCharge03C.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("coreCharge03C"));//
        	 tempCoreCharge03C.setItemHtml2(tempCoreCharge03C.getItemHtml());
        	 tempCoreCharge03C.setItemHtml(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("coreChargeTitle03R").getItemHtml());
//        	 
//        	 tempCoreCharge03D.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("coreCharge03D"));//
//        	 tempCoreCharge03D.setItemHtml2(tempCoreCharge03D.getItemHtml());
//        	 tempCoreCharge03D.setItemHtml(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("coreChargeTitle03R").getItemHtml());
        	 
			 tempCoreCharge03E.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("coreCharge03E"));//
        	 tempCoreCharge03E.setItemHtml2(tempCoreCharge03E.getItemHtml());
        	 tempCoreCharge03E.setItemHtml(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("coreChargeTitle03R").getItemHtml());
        	 
			 tempCoreCharge03F.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("coreCharge03F"));//
        	 tempCoreCharge03F.setItemHtml2(tempCoreCharge03F.getItemHtml());
        	 tempCoreCharge03F.setItemHtml(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("coreChargeTitle03R").getItemHtml());
        	 
        	 tempCoreCharge03G.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("coreCharge03G"));
        	 tempCoreCharge03G.setItemHtml2(tempCoreCharge03G.getItemHtml());
        	 tempCoreCharge03G.setItemHtml(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("coreChargeTitle03R").getItemHtml());

        	 tempCoreCharge04.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("coreCharge04"));
        	 tempCoreCharge04.setItemHtml2(tempCoreCharge04.getItemHtml());
        	 tempCoreCharge04.setItemHtml(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("coreChargeTitle04").getItemHtml());
        	 
        	 tempCoreCharge05.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("coreCharge05"));
        	 tempCoreCharge05.setItemHtml2(tempCoreCharge05.getItemHtml());
        	 tempCoreCharge05.setItemHtml(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("coreChargeTitle05").getItemHtml());
        	 
        	 tempCoreCharge06.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("coreCharge06"));
        	 tempCoreCharge06.setItemHtml2(tempCoreCharge06.getItemHtml());
        	 tempCoreCharge06.setItemHtml(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("coreChargeTitle06").getItemHtml());
        	 
        	 tempCoreCharge07.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("coreCharge07"));
        	 tempCoreCharge07.setItemHtml2(tempCoreCharge07.getItemHtml());
			 tempCoreCharge07.setItemHtml(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("coreChargeTitle07").getItemHtml());
			 
//        	 tempCoreCharge08.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("coreCharge08"));	
//        	 tempCoreCharge08.setItemHtml2(tempCoreCharge08.getItemHtml());
//			 tempCoreCharge08.setItemHtml(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("coreChargeTitle08").getItemHtml());		
//			 
//        	 tempCoreCharge09.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("coreCharge09"));
	     }
		

	     ArrayList<RptServiceInfoDTO> coreChrgList2222 = new ArrayList<RptServiceInfoDTO>();
		if(isShowAllForTest){//show all

			RptServiceInfoDTO tempCore1 = new RptServiceInfoDTO();
			RptServiceInfoDTO tempCore2 = new RptServiceInfoDTO();
			RptServiceInfoDTO tempCore3 = new RptServiceInfoDTO();
			RptServiceInfoDTO tempCore4 = new RptServiceInfoDTO();
			RptServiceInfoDTO tempCore5 = new RptServiceInfoDTO();
			RptServiceInfoDTO tempCore6 = new RptServiceInfoDTO();
			RptServiceInfoDTO tempCore7 = new RptServiceInfoDTO();
			RptServiceInfoDTO tempCore8 = new RptServiceInfoDTO();
			RptServiceInfoDTO tempCore9 = new RptServiceInfoDTO();
			RptServiceInfoDTO tempCore10 = new RptServiceInfoDTO();
			RptServiceInfoDTO tempCore11 = new RptServiceInfoDTO();
			RptServiceInfoDTO tempCore12 = new RptServiceInfoDTO();
			RptServiceInfoDTO tempCore13 = new RptServiceInfoDTO();
			RptServiceInfoDTO tempCore14 = new RptServiceInfoDTO();
			RptServiceInfoDTO tempCore15 = new RptServiceInfoDTO();
			RptServiceInfoDTO lineBreak = new RptServiceInfoDTO();
			

//			tempCore5.setItemHtml("*** 1. Pre-installation ***");
//			coreChrgList.add(tempCore5);
//			// for pre-installation option only
			if("en".equals(rptImsAppServDTO.getLocale())){
				tempCoreCharge01.setItemHtml2(msg.getMessage("coreChargeDesc01", new Object[] {order.getPrepayAmt()}, null, new Locale("en", "US")));
			}else if("zh_HK".equals(rptImsAppServDTO.getLocale())){
				tempCoreCharge01.setItemHtml2(msg.getMessage("coreChargeDesc01", new Object[] {order.getPrepayAmt()}, null, new Locale("zh", "HK")));
			}
			logger.debug("tmpServiceInfo.getItemHtml2():"+tempCoreCharge01.getItemHtml2());
//		     if(dbRptstaticWords!=null){
//		    	 tempCoreCharge01.setItemHtml2(tempCoreCharge01.getItemHtml2().replace("${0}", "$"+order.getPrepayAmt()));
//		     }
//			logger.debug("tmpServiceInfo.getItemHtml2():"+tempCoreCharge01.getItemHtml2());
//			tempCoreCharge01.setItemHtml2(tempCoreCharge01.getItemHtml2().replace("$", "HK$"));
//			coreChrgList.add(tempCoreCharge01);
//			tempCore13.setItemHtml("  *** 1. end***");
//			coreChrgList.add(tempCore13);
//			coreChrgList.add(lineBreak);
			
			
//			tempCore6.setItemHtml("*** 1a. Non Monthly Plan without term extension ***");
//			coreChrgList.add(tempCore6);
//			coreChrgList.add(tempCoreCharge01);
//			tempCore8.setItemHtml("  *** 1a. end***");
//			coreChrgList.add(tempCore8);
//			coreChrgList.add(lineBreak);
//			
//			
//			
//			
//			tempCore7.setItemHtml("  *** 2b.  Non Monthly Plan with term extension ***");
//			coreChrgList.add(tempCore7);
//			coreChrgList.add(tempCoreCharge02A);
//			tempCore9.setItemHtml("  *** 2b. end***");
//			coreChrgList.add(tempCore9);
//			coreChrgList.add(lineBreak);
//			
//			
//			
//			
//			tempCore1.setItemHtml("  ***  3a.One Time Install Charge waived  ***");
//			coreChrgList.add(tempCore1);
//	        tempCoreCharge03C.setItemHtml2("HK$"+Integer.valueOf(order.getOtInstallChrg())*-1+" "+tempCoreCharge03C.getItemHtml2());
//	        coreChrgList.add(tempCoreCharge03C); 
//			tempCore10.setItemHtml("  ***  3a.end  ***");
//			coreChrgList.add(tempCore10);
//			coreChrgList.add(lineBreak);
			
			
			

			tempCore2.setItemHtml("  ***  3b. not 3a, Installment ***");
			coreChrgList.add(tempCore2);
			if(order.getOtInstallChrg()!=null&&Integer.valueOf(order.getOtInstallChrg())==280){
				tempCoreCharge03AForShowAll.setItemHtml2("HK$ 50 X 6 "+tempCoreCharge03AForShowAll.getItemHtml2());
			}else if(order.getOtInstallChrg()!=null&&Integer.valueOf(order.getOtInstallChrg())==380){
				tempCoreCharge03AForShowAll.setItemHtml2("HK$ 65 X 6 "+tempCoreCharge03AForShowAll.getItemHtml2());
			}else {
				tempCoreCharge03AForShowAll.setItemHtml2("HK$ 114 X 6 "+tempCoreCharge03AForShowAll.getItemHtml2());
			}
	         coreChrgList.add(tempCoreCharge03AForShowAll); 
				tempCore11.setItemHtml("  ***  3b.end  ***");
				coreChrgList.add(tempCore11);
				coreChrgList.add(lineBreak);
	         
//	         
//			tempCore3.setItemHtml("  ***  3c. 3c Will appear if not 3a and not 3b ***");
//			coreChrgList.add(tempCore3);
//	         tempCoreCharge03A.setItemHtml2("HK$"+Integer.valueOf(order.getOtInstallChrg()));
//	         coreChrgList.add(tempCoreCharge03A);  
//				tempCore12.setItemHtml("  ***  3c.end  ***");
//				coreChrgList.add(tempCore12);
//				coreChrgList.add(lineBreak);
//	         
//	         
//
//	         tempCoreCharge03D.setItemHtml("");
//	         coreChrgList.add(tempCoreCharge03D);
	         
	         
			tempCore4.setItemHtml("*** 4.The below statment will only appear for Pre-installation ***");
			coreChrgList.add(tempCore4);
			coreChrgList.add(tempCoreCharge04);
			tempCore14.setItemHtml("  ***  4.end  ***");
			coreChrgList.add(tempCore14);
			coreChrgList.add(lineBreak);
			
			

		}else{//normal flow
			

			
//			if (order.isPreinstallation().equalsIgnoreCase("Y")) {	// for pre-installation option only
//				if("en".equals(rptImsAppServDTO.getLocale())){
//					tempCoreCharge01.setItemHtml2(msg.getMessage("coreChargeDesc01", new Object[] {order.getPrepayAmt()}, null, new Locale("en", "US")));
//				}else if("zh_HK".equals(rptImsAppServDTO.getLocale())){
//					tempCoreCharge01.setItemHtml2(msg.getMessage("coreChargeDesc01", new Object[] {order.getPrepayAmt()}, null, new Locale("zh", "HK")));
//				}
//				logger.debug("tmpServiceInfo.getItemHtml2():"+tempCoreCharge01.getItemHtml2());
//			     if(dbRptstaticWords!=null){
//			    	 tempCoreCharge01.setItemHtml2(tempCoreCharge01.getItemHtml2().replace("${0}", "$"+order.getPrepayAmt()));
//			     }
//				logger.debug("tmpServiceInfo.getItemHtml2():"+tempCoreCharge01.getItemHtml2());
//				tempCoreCharge01.setItemHtml2(tempCoreCharge01.getItemHtml2().replace("$", "HK$"));
//				coreChrgList.add(tempCoreCharge01);
//			}
			logger.debug("section F - isMonthlyPlan");
			
//			if (!order.isMonthlyPlan().equalsIgnoreCase("Y")) {		// for all except monthly plan
//				if (order.getTermExt() == null || order.getTermExt() == "" || (Integer.parseInt(order.getTermExt())==0)) { // without term extension
//					coreChrgList.add(tempCoreCharge02B);
//				} else { // with term extension
//					coreChrgList.add(tempCoreCharge02A);
//				}
//			}
		
			
//	     if (Integer.valueOf(order.getOtInstallChrg())<0) {
//	        	//logger.debug(Integer.valueOf(order.getOtInstallChrg()));
//			coreChrgList.add(tempCoreCharge02A);
//	         tempCoreCharge03C.setItemHtml2("HK$"+Integer.valueOf(order.getOtInstallChrg())*-1+" "+tempCoreCharge03C.getItemHtml2());
//	         coreChrgList.add(tempCoreCharge03C); 
//	         }else if((order.getInstallmentMonth()!=null && order.getInstallmentChrg()!=null)&&
//	        		 (Integer.valueOf(order.getInstallmentMonth())>0 && Integer.valueOf(order.getInstallmentChrg())>0)){
//		        	logger.debug("order.getInstallmentMonth():"+order.getInstallmentMonth());
//		        	logger.debug("order.getInstallmentChrg():"+order.getInstallmentChrg());
//			         tempCoreCharge03A.setItemHtml2("HK$"+order.getInstallmentChrg()+" X "+order.getInstallmentMonth()+" "+tempCoreCharge03A.getItemHtml2());
//			         coreChrgList.add(tempCoreCharge03A); 
//		     }else{ 
//	        	//logger.debug(Integer.valueOf(order.getOtInstallChrg()));
//	         tempCoreCharge03A.setItemHtml2("HK$"+Integer.valueOf(order.getOtInstallChrg()));
//	         coreChrgList.add(tempCoreCharge03A); 
//	        }  
//
//         tempCoreCharge03D.setItemHtml("");
//         coreChrgList.add(tempCoreCharge03D);
//         
//			if (order.isPreinstallation().equalsIgnoreCase("Y")) {	// for pre-installation option only
//				coreChrgList.add(tempCoreCharge04);
//			}
		}
		
		if(order.getOtChrgType() == null){
			order.setOtChrgType("A");
			logger.debug("getOtChrgType is null");
		}
		logger.debug("getOtChrgType: "+order.getOtChrgType().toString());
		logger.debug("getOtInstallChrg: "+order.getOtInstallChrg().toString());
		//order.setOtInstallChrg(String.valueOf(Integer.valueOf(order.getOtInstallChrg())*-1));
		//logger.debug("getOtInstallChrg: "+order.getOtInstallChrg().toString());
		//logger.debug("tempCoreCharge03C.getItemHtml2(): "+tempCoreCharge03C.getItemHtml2());
		
		if(order.getOtInstallChrg()==null||order.getOtInstallChrg().isEmpty()){
			//mass project WAIVED case
			tempCoreCharge03C.setItemHtml2(tempCoreCharge03C.getItemHtml2());
			coreChrgList2222.add(tempCoreCharge02A);
			coreChrgList2222.add(tempCoreCharge03C); 
//		}else if("0".equalsIgnoreCase(order.getOtInstallChrg())){
//			tempCoreCharge03C.setItemHtml2(tempCoreCharge03C.getItemHtml2());
//			coreChrgList2222.add(tempCoreCharge02A);
//			coreChrgList2222.add(tempCoreCharge03C); 
		}else{
			String compForOtInstallChrg ;
			if("zh_HK".equals(rptImsAppServDTO.getLocale())){
				compForOtInstallChrg = order.getInstallOTDesc_zh();
			}else{
				compForOtInstallChrg = order.getInstallOTDesc_en();
			}
			if(compForOtInstallChrg==null||compForOtInstallChrg.isEmpty()){
				compForOtInstallChrg = "";
			}
		if (Integer.valueOf(order.getOtInstallChrg())<0) {
        	//logger.debug(Integer.valueOf(order.getOtInstallChrg()));
			if(order.getOtChrgType().equalsIgnoreCase("I")){
				logger.debug("af test case1");
				logger.debug("-ve");
				if("B".equalsIgnoreCase(order.getServiceWaiver())){
					tempCoreCharge01A.setItemHtml2("HK$"+Integer.valueOf(order.getOtInstallChrg())*-1+compForOtInstallChrg+" "+tempCoreCharge03E.getItemHtml2());
				}else if("V".equalsIgnoreCase(order.getServiceWaiver())){
					tempCoreCharge03C.setItemHtml2("HK$"+Integer.valueOf(order.getOtInstallChrg())*-1+compForOtInstallChrg+" "+tempCoreCharge03G.getItemHtml2());
				}else if("G".equalsIgnoreCase(order.getServiceWaiver())||"G".equalsIgnoreCase(order.getServiceWaiverNowTVPage())){
					tempCoreCharge01A.setItemHtml2("HK$"+Integer.valueOf(order.getOtInstallChrg())*-1+compForOtInstallChrg+" "+tempCoreCharge03F.getItemHtml2());
				}else{
					tempCoreCharge01A.setItemHtml2("HK$"+Integer.valueOf(order.getOtInstallChrg())*-1+compForOtInstallChrg+" "+tempCoreCharge03C.getItemHtml2());
				}
				logger.debug("waived msg"+tempCoreCharge01A.getItemHtml2());
				coreChrgList2222.add(tempCoreCharge01A);
			}else{
				logger.debug("af test case2");
				logger.debug("+ve");
				tempCoreCharge02.setItemHtml2("HK$"+Integer.valueOf(order.getOtInstallChrg())*-1+compForOtInstallChrg+" "+tempCoreCharge03C.getItemHtml2());
				coreChrgList2222.add(tempCoreCharge02);
			}
         ////tempCoreCharge02.setItemHtml2("HK$"+Integer.valueOf(order.getOtInstallChrg())*-1+" "+tempCoreCharge03C.getItemHtml2());
         //coreChrgList.add(tempCoreCharge03C); 
//         }else if((order.getInstallmentMonth()!=null && order.getInstallmentChrg()!=null)&&
//        		 (Integer.valueOf(order.getInstallmentMonth())>0 && Integer.valueOf(order.getInstallmentChrg())>0)){
//	        	logger.debug("order.getInstallmentMonth():"+order.getInstallmentMonth());
//	        	logger.debug("order.getInstallmentChrg():"+order.getInstallmentChrg());
//		         tempCoreCharge03A.setItemHtml2("HK$"+order.getInstallmentChrg()+" X "+order.getInstallmentMonth()+" "+tempCoreCharge03A.getItemHtml2());
//		         coreChrgList.add(tempCoreCharge03A); 
	     }else{ 
		     String otCode = "";
		     if(cOrder&&Integer.valueOf(order.getOtInstallChrg())>0){
		    	 otCode = " &lt ONE Time Code: "+order.getInstallOtCode()+" / Quantity: "+order.getInstallOtQty()+" >";
		     }
	    	 if(order.getOtChrgType().equalsIgnoreCase("I")){

					logger.debug("af test case3");
	    		 tempCoreCharge01A.setItemHtml2("HK$"+Integer.valueOf(order.getOtInstallChrg())+otCode+compForOtInstallChrg);
	    		 coreChrgList2222.add(tempCoreCharge01A);
				}else{
					logger.debug("af test case4");
					tempCoreCharge02.setItemHtml2("HK$"+Integer.valueOf(order.getOtInstallChrg())+otCode+compForOtInstallChrg);
					coreChrgList2222.add(tempCoreCharge02);
				}
        	//logger.debug(Integer.valueOf(order.getOtInstallChrg()));
         ///tempCoreCharge02.setItemHtml2("HK$"+Integer.valueOf(order.getOtInstallChrg()));
//         coreChrgList.add(tempCoreCharge02); 
        }  
		}
//
//     tempCoreCharge03D.setItemHtml("");
//     coreChrgList.add(tempCoreCharge03D);

		//order.setOtInstallChrg(String.valueOf(Integer.valueOf(order.getOtInstallChrg())*-1));
     
		//coreChrgList.add(tempCoreCharge02); //needed
		coreChrgList2222.add(tempCoreCharge03);
//		logger.debug("waived msg3"+tempCoreCharge03.getItemHtml2());
		coreChrgList2222.add(tempCoreCharge04);
//		logger.debug("waived msg4"+tempCoreCharge04.getItemHtml2());
		coreChrgList2222.add(tempCoreCharge05);
//		logger.debug("waived msg5"+tempCoreCharge05.getItemHtml2());
		coreChrgList2222.add(tempCoreCharge06);
//		logger.debug("waived msg6"+tempCoreCharge06.getItemHtml2());
		coreChrgList2222.add(tempCoreCharge07);
//		logger.debug("waived msg7"+tempCoreCharge07.getItemHtml2());
//		coreChrgList.add(tempCoreCharge08); 
//        tempCoreCharge09.setItemHtml2(tempCoreCharge09.getItemHtml());
//        tempCoreCharge09.setItemHtml("");
//		coreChrgList.add(tempCoreCharge09);
		rptImsAppServDTO.setCoreChrgDtls(coreChrgList2222);
		
		

		logger.debug("section G - bill preferences");
		if (order.getCustomer() != null) {
			if (order.getCustomer().getAccount() != null) {
				rptImsAppServDTO.setBillMedia(order.getCustomer().getAccount().getBillMedia());  // E - Email, P - Paper
			}
		}
		if(dbRptstaticWords!=null){
			rptImsAppServDTO.setBillPreference01(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("billPreferences01").getItemHtml());
			rptImsAppServDTO.setBillPreference02(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("billPreferences02").getItemHtml());
			rptImsAppServDTO.setBillPreference03(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("billPreferences03").getItemHtml()+order.getMrt()
					+dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("billPreferences04").getItemHtml());
			rptImsAppServDTO.setBillPreference04(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("billPreferences05").getItemHtml());
		}
		
		
		logger.debug("section H - important information");
		RptServiceInfoDTO	tempImprtInfo00	  = new RptServiceInfoDTO();
		RptServiceInfoDTO	tempImprtInfo01	  = new RptServiceInfoDTO();
		RptServiceInfoDTO	tempImprtInfo02	  = new RptServiceInfoDTO();
		RptServiceInfoDTO	tempImprtInfo03	  = new RptServiceInfoDTO();
		RptServiceInfoDTO	tempImprtInfo04	  = new RptServiceInfoDTO();
		RptServiceInfoDTO	tempImprtInfo05	  = new RptServiceInfoDTO();
		RptServiceInfoDTO	tempImprtInfo06	  = new RptServiceInfoDTO();
		RptServiceInfoDTO	tempImprtInfo07	  = new RptServiceInfoDTO();
		RptServiceInfoDTO	tempImprtInfo08	  = new RptServiceInfoDTO();
		RptServiceInfoDTO	tempImprtInfo09	  = new RptServiceInfoDTO();
		RptServiceInfoDTO	tempImprtInfo10	  = new RptServiceInfoDTO();
		RptServiceInfoDTO	tempImprtInfo11	  = new RptServiceInfoDTO();
		RptServiceInfoDTO	tempImprtInfo12	  = new RptServiceInfoDTO();
		RptServiceInfoDTO	tempImprtInfo13	  = new RptServiceInfoDTO();
		RptServiceInfoDTO	tempImprtInfo14	  = new RptServiceInfoDTO();
		RptServiceInfoDTO	tempImprtInfo15	  = new RptServiceInfoDTO();
		RptServiceInfoDTO	tempImprtInfo16	  = new RptServiceInfoDTO();
		RptServiceInfoDTO	tempImprtInfo17	  = new RptServiceInfoDTO();
		RptServiceInfoDTO	tempImprtInfo18	  = new RptServiceInfoDTO();
		RptServiceInfoDTO	tempImprtInfo19	  = new RptServiceInfoDTO();
		RptServiceInfoDTO	tempImprtInfo20	  = new RptServiceInfoDTO();				
		RptServiceInfoDTO	tempImprtInfo21	  = new RptServiceInfoDTO();

		
//		if ("en".equals(rptImsAppServDTO.getLocale())) {	
//			tempImprtInfo00=(RptServiceInfoDTO) appCtx.getBean("imprtInfoEng00");
//			tempImprtInfo01=(RptServiceInfoDTO) appCtx.getBean("imprtInfoEng01");
//			tempImprtInfo02=(RptServiceInfoDTO) appCtx.getBean("imprtInfoEng02");
//			tempImprtInfo03=(RptServiceInfoDTO) appCtx.getBean("imprtInfoEng03");
//			tempImprtInfo04=(RptServiceInfoDTO) appCtx.getBean("imprtInfoEng04phhos");//BomWebPortal dont have this logic
//			tempImprtInfo05=(RptServiceInfoDTO) appCtx.getBean("imprtInfoEng05");
//			tempImprtInfo06=(RptServiceInfoDTO) appCtx.getBean("imprtInfoEng06");
//			tempImprtInfo07=(RptServiceInfoDTO) appCtx.getBean("imprtInfoEng07");
//			tempImprtInfo08=(RptServiceInfoDTO) appCtx.getBean("imprtInfoEng08");
//			tempImprtInfo09=(RptServiceInfoDTO) appCtx.getBean("imprtInfoEng09");
//			tempImprtInfo10=(RptServiceInfoDTO) appCtx.getBean("imprtInfoEng10");
//			tempImprtInfo11=(RptServiceInfoDTO) appCtx.getBean("imprtInfoEng11");
//			tempImprtInfo12=(RptServiceInfoDTO) appCtx.getBean("imprtInfoEng12");
//			tempImprtInfo13=(RptServiceInfoDTO) appCtx.getBean("imprtInfoEng13");
//			tempImprtInfo14=(RptServiceInfoDTO) appCtx.getBean("imprtInfoEng14");
//			tempImprtInfo15=(RptServiceInfoDTO) appCtx.getBean("imprtInfoEng15");
//			tempImprtInfo16=(RptServiceInfoDTO) appCtx.getBean("imprtInfoEng16");
//			tempImprtInfo17=(RptServiceInfoDTO) appCtx.getBean("imprtInfoEng17");
//			tempImprtInfo18=(RptServiceInfoDTO) appCtx.getBean("imprtInfoEng18");
//			tempImprtInfo19=(RptServiceInfoDTO) appCtx.getBean("imprtInfoEng19");
//			tempImprtInfo20=(RptServiceInfoDTO) appCtx.getBean("imprtInfoEng20");
//			tempImprtInfo21=(RptServiceInfoDTO) appCtx.getBean("imprtInfoEng21");
//
//		}else if ("zh_HK".equals(rptImsAppServDTO.getLocale())) {
//			tempImprtInfo00=(RptServiceInfoDTO) appCtx.getBean("imprtInfoChi00");
//			tempImprtInfo01=(RptServiceInfoDTO) appCtx.getBean("imprtInfoChi01");
//			tempImprtInfo02=(RptServiceInfoDTO) appCtx.getBean("imprtInfoChi02");
//			tempImprtInfo03=(RptServiceInfoDTO) appCtx.getBean("imprtInfoChi03");
//			tempImprtInfo04=(RptServiceInfoDTO) appCtx.getBean("imprtInfoChi04phhos");//BomWebPortal dont have this logic
//			tempImprtInfo05=(RptServiceInfoDTO) appCtx.getBean("imprtInfoChi05");
//			tempImprtInfo06=(RptServiceInfoDTO) appCtx.getBean("imprtInfoChi06");
//			tempImprtInfo07=(RptServiceInfoDTO) appCtx.getBean("imprtInfoChi07");
//			tempImprtInfo08=(RptServiceInfoDTO) appCtx.getBean("imprtInfoChi08");
//			tempImprtInfo09=(RptServiceInfoDTO) appCtx.getBean("imprtInfoChi09");
//			tempImprtInfo10=(RptServiceInfoDTO) appCtx.getBean("imprtInfoChi10");
//			tempImprtInfo11=(RptServiceInfoDTO) appCtx.getBean("imprtInfoChi11");
//			tempImprtInfo12=(RptServiceInfoDTO) appCtx.getBean("imprtInfoChi12");
//			tempImprtInfo13=(RptServiceInfoDTO) appCtx.getBean("imprtInfoChi13");
//			tempImprtInfo14=(RptServiceInfoDTO) appCtx.getBean("imprtInfoChi14");
//			tempImprtInfo15=(RptServiceInfoDTO) appCtx.getBean("imprtInfoChi15");
//			tempImprtInfo16=(RptServiceInfoDTO) appCtx.getBean("imprtInfoChi16");
//			tempImprtInfo17=(RptServiceInfoDTO) appCtx.getBean("imprtInfoChi17");
//			tempImprtInfo18=(RptServiceInfoDTO) appCtx.getBean("imprtInfoChi18");
//			tempImprtInfo19=(RptServiceInfoDTO) appCtx.getBean("imprtInfoChi19");
//			tempImprtInfo20=(RptServiceInfoDTO) appCtx.getBean("imprtInfoChi20");
//			tempImprtInfo21=(RptServiceInfoDTO) appCtx.getBean("imprtInfoChi21");
//		}
	     if(dbRptstaticWords!=null){
	    	 tempImprtInfo00.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("imprtInfo00"));
	    	 tempImprtInfo01.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("imprtInfo01"));
	    	 tempImprtInfo02.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("imprtInfo02"));
	    	 tempImprtInfo03.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("imprtInfo03"));
	    	 tempImprtInfo04.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("imprtInfo04"));
	    	 tempImprtInfo05.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("imprtInfo05"));
	    	 tempImprtInfo06.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("imprtInfo06"));
	    	 tempImprtInfo07.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("imprtInfo07"));
	    	 tempImprtInfo08.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("imprtInfo08"));
	    	 tempImprtInfo09.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("imprtInfo09"));
	    	 tempImprtInfo10.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("imprtInfo10"));
	    	 tempImprtInfo11.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("imprtInfo11"));
	    	 tempImprtInfo12.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("imprtInfo12"));
	    	 tempImprtInfo13.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("imprtInfo13"));
	    	 tempImprtInfo14.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("imprtInfo14"));
	    	 tempImprtInfo15.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("imprtInfo15"));
	    	 tempImprtInfo16.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("imprtInfo16"));
	    	 tempImprtInfo17.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("imprtInfo17"));
	    	 tempImprtInfo18.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("imprtInfo18"));
	    	 tempImprtInfo19.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("imprtInfo19"));
	    	 tempImprtInfo20.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("imprtInfo20"));
	    	 tempImprtInfo21.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("imprtInfo21"));
	     }
		
		imprtInfoList.add(tempImprtInfo00);
		imprtInfoList.add(tempImprtInfo01);
		imprtInfoList.add(tempImprtInfo21);
		imprtInfoList.add(tempImprtInfo02);
		imprtInfoList.add(tempImprtInfo03);
		imprtInfoList.add(tempImprtInfo04);
		imprtInfoList.add(tempImprtInfo05);
		imprtInfoList.add(tempImprtInfo06);
		imprtInfoList.add(tempImprtInfo07);
		imprtInfoList.add(tempImprtInfo08);
		imprtInfoList.add(tempImprtInfo09);
		imprtInfoList.add(tempImprtInfo10);
		imprtInfoList.add(tempImprtInfo11);
		imprtInfoList.add(tempImprtInfo12);
		imprtInfoList.add(tempImprtInfo13);
		imprtInfoList.add(tempImprtInfo14);
		imprtInfoList.add(tempImprtInfo15);
//		imprtInfoList.add(tempImprtInfo16);
//		imprtInfoList.add(tempImprtInfo17);
//		imprtInfoList.add(tempImprtInfo18);
//		imprtInfoList.add(tempImprtInfo19);
//		imprtInfoList.add(tempImprtInfo20);
		rptImsAppServDTO.setImprtInfoDtls(imprtInfoList);

		logger.debug("section I - glossary and other information");
		RptServiceInfoDTO tempGlossary01  = new RptServiceInfoDTO();
		RptServiceInfoDTO tempGlossary02  = new RptServiceInfoDTO();
		RptServiceInfoDTO tempGlossary03  = new RptServiceInfoDTO();
		RptServiceInfoDTO tempGlossary04  = new RptServiceInfoDTO();
		RptServiceInfoDTO tempGlossary05  = new RptServiceInfoDTO();
		RptServiceInfoDTO tempGlossary06  = new RptServiceInfoDTO();
		RptServiceInfoDTO tempGlossary07  = new RptServiceInfoDTO();
		RptServiceInfoDTO tempGlossary08  = new RptServiceInfoDTO();
		RptServiceInfoDTO tempGlossary09  = new RptServiceInfoDTO();
		
//		if ("en".equals(rptImsAppServDTO.getLocale())) {	
//			tempGlossary01=(RptServiceInfoDTO) appCtx.getBean("glossaryEng01");
//			tempGlossary02=(RptServiceInfoDTO) appCtx.getBean("glossaryEng02");
//			tempGlossary03=(RptServiceInfoDTO) appCtx.getBean("glossaryEng03");
//			tempGlossary04=(RptServiceInfoDTO) appCtx.getBean("glossaryEng04");
//			tempGlossary05=(RptServiceInfoDTO) appCtx.getBean("glossaryEng05");
//			tempGlossary06=(RptServiceInfoDTO) appCtx.getBean("glossaryEng06");
//			tempGlossary07=(RptServiceInfoDTO) appCtx.getBean("glossaryEng07");
//		}else if ("zh_HK".equals(rptImsAppServDTO.getLocale())) {
//			tempGlossary01=(RptServiceInfoDTO) appCtx.getBean("glossaryChi01");
//			tempGlossary02=(RptServiceInfoDTO) appCtx.getBean("glossaryChi02");
//			tempGlossary03=(RptServiceInfoDTO) appCtx.getBean("glossaryChi03");
//			tempGlossary04=(RptServiceInfoDTO) appCtx.getBean("glossaryChi04");
//			tempGlossary05=(RptServiceInfoDTO) appCtx.getBean("glossaryChi05");
//			tempGlossary06=(RptServiceInfoDTO) appCtx.getBean("glossaryChi06");
//			tempGlossary07=(RptServiceInfoDTO) appCtx.getBean("glossaryChi07");
//		}

	     if(dbRptstaticWords!=null){
	    	 tempGlossary01.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("glossary01"));
	    	 tempGlossary02.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("glossary02"));
	    	 tempGlossary03.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("glossary03"));
	    	 tempGlossary04.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("glossary04"));
	    	 tempGlossary05.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("glossary05"));
	    	 tempGlossary06.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("glossary06"));
	    	 tempGlossary07.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("glossary07"));
	    	 tempGlossary08.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("glossary08"));
	    	 tempGlossary09.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("glossary09"));
	     }
		

//		if(isShowAllForTest){//show all
//			RptServiceInfoDTO tempGlossary01a  = new RptServiceInfoDTO();
//			RptServiceInfoDTO tempGlossary01aEnd  = new RptServiceInfoDTO();
//			RptServiceInfoDTO tempGlossary02a  = new RptServiceInfoDTO();
//			RptServiceInfoDTO tempGlossary02aEnd  = new RptServiceInfoDTO();
//			tempGlossary01a.setItemHtml("*** 1.The below Statement will only appear if Not Monthly Plan ***");
//			glossaryList.add(tempGlossary01a);
//			glossaryList.add(tempGlossary01);
//			tempGlossary01aEnd.setItemHtml("*** 1.end ***");
//			glossaryList.add(tempGlossary01aEnd);
//			
//			tempGlossary02a.setItemHtml("*** 2.The below Statement will only appear if term extension >0 ***");
//			glossaryList.add(tempGlossary02a);
//			glossaryList.add(tempGlossary02);
//			tempGlossary02aEnd.setItemHtml("*** 2.end ***");
//			glossaryList.add(tempGlossary02aEnd);
//
//			
//		}else{
//			if (!order.isMonthlyPlan().equalsIgnoreCase("Y")) {
//				glossaryList.add(tempGlossary01);
//			}
//			if (order.getTermExt() != null && order.getTermExt() != "" && (Integer.parseInt(order.getTermExt()) > 0)) {
//				glossaryList.add(tempGlossary02);
//			}
//		}
	    glossaryList.add(tempGlossary01);
		glossaryList.add(tempGlossary02);
		glossaryList.add(tempGlossary03);
		glossaryList.add(tempGlossary04);
//		if("Y".equalsIgnoreCase(order.getPreInstallInd())){
			glossaryList.add(tempGlossary08);
			glossaryList.add(tempGlossary09);
//		}else{
//			glossaryList.add(tempGlossary05);
//			glossaryList.add(tempGlossary06);
//		}
//		if (order.getTermExt() != null && order.getTermExt() != "" && (Integer.parseInt(order.getTermExt()) > 0)) {
//			glossaryList.add(tempGlossary07);
//		}
		rptImsAppServDTO.setGlossaryDtls(glossaryList);


		logger.debug("section K - payment and authorisation");
		Map<String, String> mobileFuturePayment = new HashMap<String, String>();
		try {
			List<Map<String, String>> list = imsReport1Dao.getImsLookUpCode("SB_IMS_INTERCOM");
			if(list!=null){
				for(int p=0; p<list.size(); p++){
					mobileFuturePayment.put(list.get(p).get("code"), list.get(p).get("description"));
				}
			}
			
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		rptImsAppServDTO.setMobileFuturePayment("INTERCOM CCC: "+ mobileFuturePayment.get("CCC") + "<br/>END DATE: "+mobileFuturePayment.get("END_DATE"));
		rptImsAppServDTO.setCreditCardDtls(creditCardList);
		if (order.getCustomer() != null) {
			if (order.getCustomer().getAccount() != null) {
				if (order.getCustomer().getAccount().getPayment() != null) {
					if(isShowAllForTest){//Show all cash and credit card
						rptImsAppServDTO.setCreditCardType(order.getCustomer().getAccount().getPayment().getCcType());  // V - Visa, M - Master, AE - AE
						rptImsAppServDTO.setCreditCardHolderName(order.getCustomer().getAccount().getPayment().getCcHoldName());
						rptImsAppServDTO.setCreditCardNum(order.getCustomer().getAccount().getPayment().getCcNum());
						rptImsAppServDTO.setCreditExpiryDate(order.getCustomer().getAccount().getPayment().getCcExpDate());
						logger.debug("getCcExpDate:"+order.getCustomer().getAccount().getPayment().getCcExpDate());
						rptImsAppServDTO.setCreditCardVerifyInd(order.getCustomer().getAccount().getPayment().getCcVerified());
						rptIms3PartyCreditCardDTO.setCreditCardType(order.getCustomer().getAccount().getPayment().getCcType());  // V - Visa, M - Master, AE - AE
						rptIms3PartyCreditCardDTO.setCreditCardHolderName(order.getCustomer().getAccount().getPayment().getCcHoldName());
						rptIms3PartyCreditCardDTO.setCreditCardNum(order.getCustomer().getAccount().getPayment().getCcNum());
						rptIms3PartyCreditCardDTO.setCreditExpiryDate(order.getCustomer().getAccount().getPayment().getCcExpDate());
//						if (isShowAllForTest||order.getCustomer().getAccount().getPayment().getThirdPartyInd().equalsIgnoreCase("Y")) {
//							isReq3PartyCreditCardForm = true;
//						}

						//cash1
						String cash1="***  a.CashFsPrepay  ***<br/>";
					     if(dbRptstaticWords!=null){
					    	 String temp = dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("cashPayDesc03").getItemHtml();
					    	 cash1+=temp.replace("${0}", "$"+order.getPrepayAmt());
					     }else{
								if ("en".equals(rptImsAppServDTO.getLocale())) {
									logger.debug("message : " + msg.getMessage("cashPayDesc03", new Object[] {order.getPrepayAmt()}, null, new Locale("en", "US")));
									cash1+=msg.getMessage("cashPayDesc03", new Object[] {order.getPrepayAmt()}, null, new Locale("en", "US"));
								} else if ("zh_HK".equals(rptImsAppServDTO.getLocale())) { 
									logger.debug("message : " + msg.getMessage("cashPayDesc03", new Object[] {order.getPrepayAmt()}, null, new Locale("zh", "HK")));
									cash1+=msg.getMessage("cashPayDesc03", new Object[] {order.getPrepayAmt()}, null, new Locale("zh", "HK"));
								}
					     }
					     cash1+="<br/>***  a.end  ***<br/>";
							

						//cash2
						String cash2="<br/><br/>***  b.not CashFsPrepay and WaivedPrepay  ***<br/>";
						     if(dbRptstaticWords!=null){
						    	 String temp = dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("cashPayDesc01").getItemHtml();
						    	 cash2+=temp;
						     }else{
									if ("en".equals(rptImsAppServDTO.getLocale())) {
										logger.debug("message : " + msg.getMessage("cashPayDesc01", null, null, new Locale("en")));
										cash2+=msg.getMessage("cashPayDesc01", null, null, new Locale("en"));
									} else if ("zh_HK".equals(rptImsAppServDTO.getLocale())) { 
										logger.debug("message : " + msg.getMessage("cashPayDesc01", null, null, new Locale("zh", "HK")));
										cash2+=msg.getMessage("cashPayDesc01", null, null, new Locale("zh", "HK"));
									}
						     }
						     cash2+="<br/>*** b.end  ***<br/>";
						     
						     
						//cash3
						String cash3="<br/><br/>***  c.not CashFsPrepay and not WaivedPrepay  ***<br/>";
					     if(dbRptstaticWords!=null){
					    	 String temp = dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("cashPayDesc02").getItemHtml();
					    	 cash3+=temp.replace("${0}", "$"+order.getPrepayAmt());
					     }else{
								if ("en".equals(rptImsAppServDTO.getLocale())) {
									logger.debug("message : " + msg.getMessage("cashPayDesc02", new Object[] {order.getPrepayAmt()}, null, new Locale("en")));
									cash3+=msg.getMessage("cashPayDesc02", new Object[] {order.getPrepayAmt()}, null, new Locale("en"));
								} else if ("zh_HK".equals(rptImsAppServDTO.getLocale())) { 
									logger.debug("message : " + msg.getMessage("cashPayDesc02", new Object[] {order.getPrepayAmt()}, null, new Locale("zh", "HK")));
									cash3+=msg.getMessage("cashPayDesc02", new Object[] {order.getPrepayAmt()}, null, new Locale("zh", "HK"));
								}
					     }
					     cash3+="<br/>*** c.end  ***<br/>";
					     
					     rptImsAppServDTO.setCashOption(cash1+cash2+cash3);
					     
					}else{//not show all, normal flow
						rptImsAppServDTO.setPayMethodType(order.getCustomer().getAccount().getPayment().getPayMtdType());	// C - Credit Card, M - Cash
						if (order.getCustomer().getAccount().getPayment().getPayMtdType().equalsIgnoreCase("C")) {
							rptImsAppServDTO.setCreditCardType(order.getCustomer().getAccount().getPayment().getCcType());  // V - Visa, M - Master, AE - AE
							rptImsAppServDTO.setCreditCardHolderName(order.getCustomer().getAccount().getPayment().getCcHoldName());
							rptImsAppServDTO.setCreditCardNum(order.getCustomer().getAccount().getPayment().getCcNum());
							rptImsAppServDTO.setCreditExpiryDate(order.getCustomer().getAccount().getPayment().getCcExpDate());
							logger.debug("getCcExpDate:"+order.getCustomer().getAccount().getPayment().getCcExpDate());
							rptImsAppServDTO.setCreditCardVerifyInd(order.getCustomer().getAccount().getPayment().getCcVerified());
							rptIms3PartyCreditCardDTO.setCreditCardType(order.getCustomer().getAccount().getPayment().getCcType());  // V - Visa, M - Master, AE - AE
							rptIms3PartyCreditCardDTO.setCreditCardHolderName(order.getCustomer().getAccount().getPayment().getCcHoldName());
							rptIms3PartyCreditCardDTO.setCreditCardNum(order.getCustomer().getAccount().getPayment().getCcNum());
							rptIms3PartyCreditCardDTO.setCreditExpiryDate(order.getCustomer().getAccount().getPayment().getCcExpDate());
							if (order.getCustomer().getAccount().getPayment().getThirdPartyInd().equalsIgnoreCase("Y")) {
								isReq3PartyCreditCardForm = true;
							}
						} else {
							if ("Y".equalsIgnoreCase(order.getCashFsPrepay())) {
								if ("en".equals(rptImsAppServDTO.getLocale())) {
									logger.debug("message : " + msg.getMessage("cashPayDesc03", new Object[] {order.getPrepayAmt()}, null, new Locale("en", "US")));
									rptImsAppServDTO.setCashOption(msg.getMessage("cashPayDesc03", new Object[] {order.getPrepayAmt()}, null, new Locale("en", "US")));
								} else if ("zh_HK".equals(rptImsAppServDTO.getLocale())) { 
									logger.debug("message : " + msg.getMessage("cashPayDesc03", new Object[] {order.getPrepayAmt()}, null, new Locale("zh", "HK")));
									rptImsAppServDTO.setCashOption(msg.getMessage("cashPayDesc03", new Object[] {order.getPrepayAmt()}, null, new Locale("zh", "HK")));
								}
								String temp;
							     if(dbRptstaticWords!=null){
							    	 if(cOrder)
							    		 temp = dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("cashPayDesc03CC").getItemHtml();
							    	 else
								    	 temp = dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("cashPayDesc03").getItemHtml();

							    	 rptImsAppServDTO.setCashOption(temp.replace("${0}", "$"+order.getPrepayAmt()));
							     }
							} else {
								if ("Y".equalsIgnoreCase(order.getWaivedPrepay())) {
									if ("en".equals(rptImsAppServDTO.getLocale())) {
										logger.debug("message : " + msg.getMessage("cashPayDesc01", null, null, new Locale("en")));
										rptImsAppServDTO.setCashOption(msg.getMessage("cashPayDesc01", null, null, new Locale("en")));
									} else if ("zh_HK".equals(rptImsAppServDTO.getLocale())) { 
										logger.debug("message : " + msg.getMessage("cashPayDesc01", null, null, new Locale("zh", "HK")));
										rptImsAppServDTO.setCashOption(msg.getMessage("cashPayDesc01", null, null, new Locale("zh", "HK")));
									}
								     if(dbRptstaticWords!=null){
								    	 String temp = dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("cashPayDesc01").getItemHtml();
								    	 rptImsAppServDTO.setCashOption(temp);
								     }
								} else {
									if ("en".equals(rptImsAppServDTO.getLocale())) {
										logger.debug("message : " + msg.getMessage("cashPayDesc02", new Object[] {order.getPrepayAmt()}, null, new Locale("en")));
										rptImsAppServDTO.setCashOption(msg.getMessage("cashPayDesc02", new Object[] {order.getPrepayAmt()}, null, new Locale("en")));
									} else if ("zh_HK".equals(rptImsAppServDTO.getLocale())) { 
										logger.debug("message : " + msg.getMessage("cashPayDesc02", new Object[] {order.getPrepayAmt()}, null, new Locale("zh", "HK")));
										rptImsAppServDTO.setCashOption(msg.getMessage("cashPayDesc02", new Object[] {order.getPrepayAmt()}, null, new Locale("zh", "HK")));
									}
								     if(dbRptstaticWords!=null){
								    	 String temp = dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("cashPayDesc02").getItemHtml();
								    	 rptImsAppServDTO.setCashOption(temp.replace("${0}", "$"+order.getPrepayAmt()));
								     }
								}
							}
						}
					}
				}
			}
		}
		
		RptServiceInfoDTO tempPayment01  = new RptServiceInfoDTO();
		RptServiceInfoDTO tempPayment02  = new RptServiceInfoDTO();
		RptServiceInfoDTO custAgree01  = new RptServiceInfoDTO();
		
		if ("en".equals(rptImsAppServDTO.getLocale())) {	
			tempPayment01=(RptServiceInfoDTO) appCtx.getBean("creditCardEng01");
			tempPayment02=(RptServiceInfoDTO) appCtx.getBean("creditCardEng02");
			custAgree01=(RptServiceInfoDTO) appCtx.getBean("custAgreeEng01");
		}else if ("zh_HK".equals(rptImsAppServDTO.getLocale())) {
			tempPayment01=(RptServiceInfoDTO) appCtx.getBean("creditCardChi01");
			tempPayment02=(RptServiceInfoDTO) appCtx.getBean("creditCardChi02");
			custAgree01=(RptServiceInfoDTO) appCtx.getBean("custAgreeChi01");
		}
	     if(dbRptstaticWords!=null){
	    	 tempPayment01.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("creditCard01"));
	    	 tempPayment02.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("creditCard02"));
	    	 custAgree01.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("custAgree01"));
	     }
		
		if (isReq3PartyCreditCardForm) {			
			rptImsAppServDTO.setThirdPartyCC(true);
			creditCardList.add(tempPayment02);
		}else{	
			rptImsAppServDTO.setThirdPartyCC(false);
			creditCardList.add(tempPayment01);
		}

		
		logger.debug("section M - MY HKT CUSTOMER SERVICE"); //:::
		//Gary added for Printing CS portal in AF Section M 13
		CsPortalAndTheClubAFDisplay(order, rptImsAppServDTO,  dbRptstaticWords);
		//nowid
		setNowIDSection(dbRptstaticWords, rptImsAppServDTO,order);
		logger.debug("section N - customer's agreement");
		custAgreeList.add(custAgree01);
		rptImsAppServDTO.setCustAgreeDtls(custAgreeList);


		// Customer Signature
		rptImsAppServDTO.setAppDate(Utility.date2String(order.getAppDate(), "dd/MM/yyyy"));
		rptIms3PartyCreditCardDTO.setAppDate(Utility.date2String(order.getAppDate(), "dd/MM/yyyy"));

		// for internal use
		rptImsAppServDTO.setSalesChannel(order.getSalesChannel());
		rptImsAppServDTO.setCreatedBy(order.getCreateBy());
		rptImsAppServDTO.setSalesCd(order.getSalesCd());
		rptImsAppServDTO.setSalesName(order.getSalesName());
		rptImsAppServDTO.setSalesContactNum(order.getSalesContactNum());
		rptImsAppServDTO.setShopCd(order.getShopCd());
		if (order.getAppointment() == null) {
			rptImsAppServDTO.setUamsNo("");
		} else {
			rptImsAppServDTO.setUamsNo(order.getAppointment().getSerialNum());
		}
		if (order.getCustomer() != null && order.getCustomer().getCustOptInfo() != null) {
			rptImsAppServDTO.setCustOptOutDirectMailingInd(order.getCustomer().getCustOptInfo().getDirectMailing());
			rptImsAppServDTO.setCustOptOutOutBoundInd(order.getCustomer().getCustOptInfo().getOutbound());
			rptImsAppServDTO.setCustOptOutSmsInd(order.getCustomer().getCustOptInfo().getSms());
			rptImsAppServDTO.setCustOptOutEmailInd(order.getCustomer().getCustOptInfo().getEmail());
			rptImsAppServDTO.setCustOptOutWebBillInd(order.getCustomer().getCustOptInfo().getWebBill());
			rptImsAppServDTO.setCustOptOutNonSalesSmsInd(order.getCustomer().getCustOptInfo().getNonsalesSms());
			rptImsAppServDTO.setCustOptOutInternetInd(order.getCustomer().getCustOptInfo().getInternet());
			logger.debug("DirectMailingInd:"+(order.getCustomer().getCustOptInfo().getDirectMailing()));
			logger.debug("OutBoundInd:"+(order.getCustomer().getCustOptInfo().getOutbound()));
			logger.debug("SmsInd:"+(order.getCustomer().getCustOptInfo().getSms()));
			logger.debug("EmailInd:"+(order.getCustomer().getCustOptInfo().getEmail()));
			logger.debug("WebBillInd:"+(order.getCustomer().getCustOptInfo().getWebBill()));
			logger.debug("NonSalesSmsInd:"+(order.getCustomer().getCustOptInfo().getNonsalesSms()));
			logger.debug("InternetInd:"+(order.getCustomer().getCustOptInfo().getInternet()));
		}

		

		setPersonalAndClubHkt(dbRptstaticWords, order, 
				rptImsAppServDTO, isShowAllForTest, personalInfoListEC,
				personalInfoListNewCustOptIn, personalInfoListNewCustOptOut);
		
		
	} else if (obj instanceof ImsRptServicePlanDetailDTO) {
		servicePlan = (ImsRptServicePlanDetailDTO) obj;
		logger.debug("part 2 - DB ServicePlan - Section C - Core Service");
		// for internal use
		rptImsAppServDTO.setProgramCode(servicePlan.getCoreServiceDetail().getOfferCd());
		RptServiceDetailDTO coreService;

		rptImsAppServDTO.setAppMethod(servicePlan.getAppMethod());
		// Program Item 
		coreService = new RptServiceDetailDTO();
		String cOrderOfferCode = "";
		if(cOrder && servicePlan.getCoreServiceDetail().getOfferCd()!=null && 
				!"x".equalsIgnoreCase(servicePlan.getCoreServiceDetail().getOfferCd())){
			cOrderOfferCode = " ( "+servicePlan.getCoreServiceDetail().getOfferCd();
			if(servicePlan.getCoreServiceDetail().getOfferCdOfOther()!=null && servicePlan.getCoreServiceDetail().getOfferCdOfOther()!="null")	
				cOrderOfferCode+=servicePlan.getCoreServiceDetail().getOfferCdOfOther()+" ) ";
			else
				cOrderOfferCode+=" ) ";			
			if("Y".equalsIgnoreCase(order.getPreInstallInd())&&servicePlan.getCoreServiceDetail().getProgItem().getPreInstOfferCd()!=null ){
				cOrderOfferCode+=" ( Pre-installation dummy offer code: "+servicePlan.getCoreServiceDetail().getProgItem().getPreInstOfferCd()+" ) ";
			}			
			if("Y".equalsIgnoreCase(order.getPreUseInd())&&servicePlan.getCoreServiceDetail().getProgItem().getPreUseOfferCd()!=null ){
				cOrderOfferCode+=" ( Pre-use dummy offer code: "+servicePlan.getCoreServiceDetail().getProgItem().getPreUseOfferCd()+" ) ";
			}		
		}
		if(cOrder && servicePlan.getCoreServiceDetail().getIncentiveCd()!=null ){
			cOrderOfferCode += " (  "+servicePlan.getCoreServiceDetail().getIncentiveCd()+" ) ";
		}
								
		coreService.setItemHtml("<b>" + servicePlan.getCoreServiceDetail().getBandwidth_desc() + " " + servicePlan.getCoreServiceDetail().getProgItem().getItemTitle() +cOrderOfferCode + "</b>");
		
		coreService.setItemRecurrentAmt(servicePlan.getCoreServiceDetail().getProgItem().getItemMthFix());
		coreService.setItemRecurrentAmt2(servicePlan.getCoreServiceDetail().getProgItem().getItemMth2Mth());
		coreServiceList.add(coreService);
		
		String COP_Service_Plan_Remark = "";
		try {
			COP_Service_Plan_Remark = imsReport1Dao.getCOP_Service_Plan_Remark(servicePlan.getCoreServiceDetail().getOfferCd(),rptImsAppServDTO.getLocale());
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(COP_Service_Plan_Remark!=null && !COP_Service_Plan_Remark.isEmpty()){
			coreService = new RptServiceDetailDTO();
			coreService.setItemHtml("<b>"+COP_Service_Plan_Remark+"</b><br>");
			coreServiceList.add(coreService);
		}	

		// bandwidth
		RptServiceInfoDTO tempBW01  = new RptServiceInfoDTO();
		
		if ("en".equals(rptImsAppServDTO.getLocale())) {	
			tempBW01=(RptServiceInfoDTO) appCtx.getBean("bandwidthEng01");
		}else if ("zh_HK".equals(rptImsAppServDTO.getLocale())) {
			tempBW01=(RptServiceInfoDTO) appCtx.getBean("bandwidthChi01");
		}
	     if(dbRptstaticWords!=null){
	    	 tempBW01.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("bandwidth01"));
	     }
		coreService = new RptServiceDetailDTO();

		coreService.setItemHtml(tempBW01.getItemHtml()+servicePlan.getCoreServiceDetail().getBandwidth_desc());
		coreServiceList.add(coreService);

		//pre-install wording
		if("Y".equalsIgnoreCase(order.getPreInstallInd())){
			RptServiceInfoDTO tempPreInst  = new RptServiceInfoDTO();
			
			if(dbRptstaticWords!=null){
				tempPreInst.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("preInstWord"));
		     }
			coreService = new RptServiceDetailDTO();
			coreService.setItemHtml(tempPreInst.getItemHtml());
			coreService.setItemRecurrentAmt(null);
			coreService.setItemRecurrentAmt2(null);
			coreServiceList.add(coreService);
		}
		
		//pre-use wording
		if("Y".equalsIgnoreCase(order.getPreUseInd())){
			RptServiceInfoDTO tempPreUse  = new RptServiceInfoDTO();
			
			if(dbRptstaticWords!=null){
				tempPreUse.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("preUseWord"));
		     }
			coreService = new RptServiceDetailDTO();
			coreService.setItemHtml(tempPreUse.getItemHtml());
			coreService.setItemRecurrentAmt(null);
			coreService.setItemRecurrentAmt2(null);
			coreServiceList.add(coreService);
		}
		
		coreService = new RptServiceDetailDTO();
		String itemDesc = "";
		coreService.setItemId(servicePlan.getCoreServiceDetail().getProgItem().getItemID());
		if (!"".equals(coreService.getItemId()) && coreService.getItemId()!=null) {
			coreService.setItemHtml(itemDesc);
			coreService.setItemRecurrentAmt(null);
			coreService.setItemRecurrentAmt2(null);
			coreServiceList.add(coreService);
		}
		// BVAS Mandatory Item 
		coreService = new RptServiceDetailDTO();
		
		itemDesc = "";
		for (int j=0; j < servicePlan.getCoreServiceDetail().getBvasMandItemList().size(); j++) {
			if (servicePlan.getCoreServiceDetail().getBvasMandItemList().get(j).getItemTitle() != null &&
				servicePlan.getCoreServiceDetail().getBvasMandItemList().get(j).getItemTitle().length() > 0) {
				if (j > 0) {
					itemDesc += " & ";
				}
				itemDesc += servicePlan.getCoreServiceDetail().getBvasMandItemList().get(j).getItemTitle();
				
				//MRT number and pin 
				coreService.setItemId(servicePlan.getCoreServiceDetail().getBvasMandItemList().get(j).getItemID());
				/*logger.debug("servicePlan.getCoreServiceDetail().getBvasMandItemList().get("+j+"): " + gson.toJson(servicePlan.getCoreServiceDetail().getBvasMandItemList().get(j)));
				
				for(VasParmDTO dto:servicePlan.getCslMrtNumPinList()){
					if(servicePlan.getCoreServiceDetail().getBvasMandItemList().get(j).getItemID().equals(dto.getItem_id())){
						if("Y".equals(dto.getVas_parm_a_af_ind())){
						itemDesc += "<br/>(" +dto.getVas_parm_a_display()+dto.getVas_parm_a() +")";
						}
						if("Y".equals(dto.getVas_parm_b_af_ind())){
						itemDesc += "<br/>(" +dto.getVas_parm_b_display()+dto.getVas_parm_b() +")<br/>";
						}
					}
				}
				*/
				if(cOrder){//gary
					if(servicePlan.getCoreServiceDetail().getBvasMandItemList().get(j).getOfferCode()!=null&&
						!"".equals(servicePlan.getCoreServiceDetail().getBvasMandItemList().get(j).getOfferCode())){
						itemDesc += "( " + servicePlan.getCoreServiceDetail().getBvasMandItemList().get(j).getOfferCode()+" )";
					}
					if(servicePlan.getCoreServiceDetail().getBvasMandItemList().get(j).getIncentiveCd()!=null){
							itemDesc += "( " + servicePlan.getCoreServiceDetail().getBvasMandItemList().get(j).getIncentiveCd()+" )";
						}
					
					if("Y".equalsIgnoreCase(order.getPreInstallInd())&&servicePlan.getCoreServiceDetail().getBvasMandItemList().get(j).getPreInstOfferCd()!=null ){
						itemDesc+=" ( Pre-installation dummy offer code: "+servicePlan.getCoreServiceDetail().getBvasMandItemList().get(j).getPreInstOfferCd()+" ) ";
					}
					
					if("Y".equalsIgnoreCase(order.getPreUseInd())&&servicePlan.getCoreServiceDetail().getBvasMandItemList().get(j).getPreUseOfferCd()!=null ){
						itemDesc+=" ( Pre-use dummy offer code: "+servicePlan.getCoreServiceDetail().getBvasMandItemList().get(j).getPreUseOfferCd()+" ) ";
					}
					String tempItemId = servicePlan.getCoreServiceDetail().getBvasMandItemList().get(j).getItemID();
					String tempMobileNum = "";
					if(order.getComponents()!=null){
						for(int k = 0; k<order.getComponents().length;k++){
							if(tempItemId!=null && tempItemId.equalsIgnoreCase(order.getComponents()[k].getItemId())){
								logger.debug("Section C Non-Mandatory abt mobile num, item_id:"+tempItemId +" equal Components id:" +order.getComponents()[k].getItemId());
								if(tempMobileNum.equals("")){
									tempMobileNum = order.getComponents()[k].getAttbValue();
								}else{
									tempMobileNum += tempMobileNum + " ," +order.getComponents()[k].getAttbValue();
								}
							}else{
								logger.debug("Section C Non-Mandatory abt mobile num, item_id:"+tempItemId +" not equal Components id:" +order.getComponents()[k].getItemId());
							}
						}
					}
					if(!tempMobileNum.equals("")){
						itemDesc += " (Mobile No: " + tempMobileNum +") ";
						logger.debug("Section C Non-Mandatory abt mobile num, temp_mobile_num is not null:"+tempMobileNum);
					}else{
						logger.debug("Section C Non-Mandatory abt mobile num, temp_mobile_num is null");
					}
				}
			}
		}
		if (itemDesc.length() > 0) {
			itemDesc = "(+ " + itemDesc + ")";
		}
		
		if (itemDesc.length() > 0) {
			coreService.setItemHtml(itemDesc);
			coreService.setItemRecurrentAmt(null);
			coreService.setItemRecurrentAmt2(null);
			coreServiceList.add(coreService);
		}
		
		// BVAS Non-Mandatory Item 				
		for (int j=0; j < servicePlan.getCoreServiceDetail().getBvasNonMItemList().size(); j++) {
			coreService = new RptServiceDetailDTO();
			if (servicePlan.getCoreServiceDetail().getBvasNonMItemList().get(j).getItemTitle() != null &&
				servicePlan.getCoreServiceDetail().getBvasNonMItemList().get(j).getItemTitle().length() > 0) {
				coreService.setItemHtml("+ " + servicePlan.getCoreServiceDetail().getBvasNonMItemList().get(j).getItemTitle());
			} else {
				coreService.setItemHtml("");
			}
			coreService.setItemId(servicePlan.getCoreServiceDetail().getBvasNonMItemList().get(j).getItemID());
			logger.debug(gson.toJson(servicePlan));//Gary
			if(cOrder){
				if(servicePlan.getCoreServiceDetail().getBvasNonMItemList().get(j).getOfferCode()!=null&&
					!"".equals(servicePlan.getCoreServiceDetail().getBvasNonMItemList().get(j).getOfferCode())){
					coreService.setItemHtml(coreService.getItemHtml()+"( " + servicePlan.getCoreServiceDetail().getBvasNonMItemList().get(j).getOfferCode()+" )");
				}
				if(servicePlan.getCoreServiceDetail().getBvasNonMItemList().get(j).getIncentiveCd()!=null){
					coreService.setItemHtml(coreService.getItemHtml()+"( " + servicePlan.getCoreServiceDetail().getBvasNonMItemList().get(j).getIncentiveCd()+" )");
				}

				if("Y".equalsIgnoreCase(order.getPreInstallInd())&&servicePlan.getCoreServiceDetail().getBvasNonMItemList().get(j).getPreInstOfferCd()!=null ){
					coreService.setItemHtml(coreService.getItemHtml()+" ( Pre-installation dummy offer code: "+servicePlan.getCoreServiceDetail().getBvasNonMItemList().get(j).getPreInstOfferCd()+" ) ");
				}
				if("Y".equalsIgnoreCase(order.getPreUseInd())&&servicePlan.getCoreServiceDetail().getBvasNonMItemList().get(j).getPreUseOfferCd()!=null ){
					coreService.setItemHtml(coreService.getItemHtml()+" ( Pre-use dummy offer code: "+servicePlan.getCoreServiceDetail().getBvasNonMItemList().get(j).getPreUseOfferCd()+" ) ");
				}
				String tempItemId = servicePlan.getCoreServiceDetail().getBvasNonMItemList().get(j).getItemID();
				String tempMobileNum = "";
				if(order.getComponents()!=null){
					for(int k = 0; k<order.getComponents().length;k++){
						if(tempItemId!=null && tempItemId.equalsIgnoreCase(order.getComponents()[k].getItemId())){
							logger.debug("Section C Non-Mandatory abt mobile num, item_id:"+tempItemId +" equal Components id:" +order.getComponents()[k].getItemId());
							if(tempMobileNum.equals("")){
								tempMobileNum = order.getComponents()[k].getAttbValue();
							}else{
								tempMobileNum += tempMobileNum + " ," +order.getComponents()[k].getAttbValue();
							}
						}else{
							logger.debug("Section C Non-Mandatory abt mobile num, item_id:"+tempItemId +" not equal Components id:" +order.getComponents()[k].getItemId());
						}
					}
				}
				if(!tempMobileNum.equals("")){
					coreService.setItemHtml(coreService.getItemHtml()+ " (Mobile No: " + tempMobileNum +") ");
					logger.debug("Section C Non-Mandatory abt mobile num, temp_mobile_num is not null:"+tempMobileNum);
				}else{
					logger.debug("Section C Non-Mandatory abt mobile num, temp_mobile_num is null");
				}
			}
			logger.debug(coreService.getItemHtml());
			coreService.setItemRecurrentAmt(servicePlan.getCoreServiceDetail().getBvasNonMItemList().get(j).getItemMthFix());
			coreService.setItemRecurrentAmt2(servicePlan.getCoreServiceDetail().getBvasNonMItemList().get(j).getItemMth2Mth());
			coreServiceList.add(coreService);
		}		
		
		setMrtItemDesc(servicePlan, coreServiceList, cOrder);
		rptImsAppServDTO.setCoreServDtls(coreServiceList);
		logger.debug(" coreServiceList:");
//		for (int i1=0;i1<coreServiceList.size();i1++)
//			logger.trace(i1+": "+coreServiceList.get(i1).getItemHtml()+": "+coreServiceList.get(i1).getItemHtml2());
		

		logger.debug("part 2 - DB ServicePlan - Section D - Optional Service");
		RptServiceDetailDTO optService;

		// Optional Premium
		if (servicePlan.getOptServiceDetail().getOptPremItemList().size() > 0) {
			optService = new RptServiceDetailDTO();

			RptServiceInfoDTO termDesc  = new RptServiceInfoDTO();
			if ("en".equals(rptImsAppServDTO.getLocale())) {	
				termDesc=(RptServiceInfoDTO) appCtx.getBean("termDescEng02");
			}else if ("zh_HK".equals(rptImsAppServDTO.getLocale())) {
				termDesc=(RptServiceInfoDTO) appCtx.getBean("termDescChi02");
			}
		     if(dbRptstaticWords!=null){
		    	 termDesc.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("termDesc02"));
		     }
			optService.setItemHtml(termDesc.getItemHtml());
			printRptServiceDetailDTO(optService);
			vasAndOptList.add(optService);
		}

		for (int j=0; j < servicePlan.getOptServiceDetail().getOptPremItemList().size(); j++) {
			optService = new RptServiceDetailDTO();
			cOrderOfferCode = "";
			
			if(cOrder){
				
				if(servicePlan.getOptServiceDetail().getOptPremItemList().get(j).getOfferCode()!=null && 
						!"x".equalsIgnoreCase(servicePlan.getOptServiceDetail().getOptPremItemList().get(j).getOfferCode())){
					cOrderOfferCode = " ( "+servicePlan.getOptServiceDetail().getOptPremItemList().get(j).getOfferCode()+" ) ";
				}
				
				if(servicePlan.getOptServiceDetail().getOptPremItemList().get(j).getIncentiveCd()!=null && 
						!"x".equalsIgnoreCase(servicePlan.getOptServiceDetail().getOptPremItemList().get(j).getIncentiveCd())){
					cOrderOfferCode += " ( "+servicePlan.getOptServiceDetail().getOptPremItemList().get(j).getIncentiveCd()+" ) ";
				}
				
				if("Y".equalsIgnoreCase(order.getPreInstallInd())&&servicePlan.getOptServiceDetail().getOptPremItemList().get(j).getPreInstOfferCd()!=null ){
					cOrderOfferCode+=" ( Pre-installation dummy offer code: "+servicePlan.getOptServiceDetail().getOptPremItemList().get(j).getPreInstOfferCd()+" ) ";
				}
				
				if("Y".equalsIgnoreCase(order.getPreUseInd())&&servicePlan.getOptServiceDetail().getOptPremItemList().get(j).getPreInstOfferCd()!=null ){
					cOrderOfferCode+=" ( Pre-use dummy offer code: "+servicePlan.getOptServiceDetail().getOptPremItemList().get(j).getPreInstOfferCd()+" ) ";
				}
			}
			optService.setItemId(servicePlan.getOptServiceDetail().getOptPremItemList().get(j).getItemID());
			optService.setItemHtml(servicePlan.getOptServiceDetail().getOptPremItemList().get(j).getItemTitle()+cOrderOfferCode);
			optService.setItemRecurrentAmt(servicePlan.getOptServiceDetail().getOptPremItemList().get(j).getItemMthFix());
			optService.setItemRecurrentAmt2(servicePlan.getOptServiceDetail().getOptPremItemList().get(j).getItemMth2Mth());
			printRptServiceDetailDTO(optService);
			vasAndOptList.add(optService);
		}
		
		// Optional Service (WL_BB, ANTI_VIR)
		if (servicePlan.getOptServiceDetail().getOptServItemList().size() > 0) {
//			optService = new RptServiceDetailDTO();
//			optService.setItemHtml("<br />");
//			printRptServiceDetailDTO(optService);
//			vasAndOptList.add(optService);
			
			optService = new RptServiceDetailDTO();
			RptServiceInfoDTO termDesc = new RptServiceInfoDTO();		
			if ("en".equals(rptImsAppServDTO.getLocale())) {	
				termDesc=(RptServiceInfoDTO) appCtx.getBean("termDescEng03");
			}else if ("zh_HK".equals(rptImsAppServDTO.getLocale())) {
				termDesc=(RptServiceInfoDTO) appCtx.getBean("termDescChi03");
			}
		     if(dbRptstaticWords!=null){
		    	 termDesc.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("termDesc03"));
		     }
			optService.setItemHtml(termDesc.getItemHtml());
			printRptServiceDetailDTO(optService);
			vasAndOptList.add(optService);
		}

		for (int j=0; j < servicePlan.getOptServiceDetail().getOptServItemList().size(); j++) {
			logger.debug("i am now looping getOptServItemList");
			optService = new RptServiceDetailDTO();
			cOrderOfferCode = "";
			
			if(cOrder){
				
				if(servicePlan.getOptServiceDetail().getOptServItemList().get(j).getOfferCode()!=null && 
						!"x".equalsIgnoreCase(servicePlan.getOptServiceDetail().getOptServItemList().get(j).getOfferCode())){
					cOrderOfferCode = " ( "+servicePlan.getOptServiceDetail().getOptServItemList().get(j).getOfferCode()+" ) ";
				}
				
				if(servicePlan.getOptServiceDetail().getOptServItemList().get(j).getIncentiveCd()!=null && 
						!"x".equalsIgnoreCase(servicePlan.getOptServiceDetail().getOptServItemList().get(j).getIncentiveCd())){
					cOrderOfferCode += " ( "+servicePlan.getOptServiceDetail().getOptServItemList().get(j).getIncentiveCd()+" ) ";
				}
				
				if("Y".equalsIgnoreCase(order.getPreInstallInd())&&servicePlan.getOptServiceDetail().getOptServItemList().get(j).getPreInstOfferCd()!=null ){
					cOrderOfferCode+=" ( Pre-installation dummy offer code: "+servicePlan.getOptServiceDetail().getOptServItemList().get(j).getPreInstOfferCd()+" ) ";
				}
				
				if("Y".equalsIgnoreCase(order.getPreUseInd())&&servicePlan.getOptServiceDetail().getOptServItemList().get(j).getPreInstOfferCd()!=null ){
					cOrderOfferCode+=" ( Pre-use dummy offer code: "+servicePlan.getOptServiceDetail().getOptServItemList().get(j).getPreInstOfferCd()+" ) ";
				}
				
				if(servicePlan.getOptServiceDetail().getOptServItemList().get(j).getVasDummyGiftDesc()!=null){
					cOrderOfferCode+=" ( VAS link dummy gift : "+servicePlan.getOptServiceDetail().getOptServItemList().get(j).getVasDummyGiftDesc()+" ) ";
				}
			}
			//Gary cOrderMobileNum
		  	 String cOrderMobileNum = "";
				if(cOrder && servicePlan.getOptServiceDetail().getOptServItemList().get(j).getMobileNum()!=null && 
						!"x".equalsIgnoreCase(servicePlan.getOptServiceDetail().getOptServItemList().get(j).getMobileNum())){
					cOrderMobileNum = " ( "+servicePlan.getOptServiceDetail().getOptServItemList().get(j).getMobileNum()+" ) ";								
				}
				logger.debug("cOrderMobileNum"+cOrderMobileNum);
			
			optService.setItemId(servicePlan.getOptServiceDetail().getOptServItemList().get(j).getItemID());	 
			optService.setItemHtml(servicePlan.getOptServiceDetail().getOptServItemList().get(j).getItemTitle()+cOrderOfferCode+cOrderMobileNum);
			
			optService.setItemRecurrentAmt(servicePlan.getOptServiceDetail().getOptServItemList().get(j).getItemMthFix());
			optService.setItemRecurrentAmt2(servicePlan.getOptServiceDetail().getOptServItemList().get(j).getItemMth2Mth());
			printRptServiceDetailDTO(optService);
			vasAndOptList.add(optService);
		}
		
		// Gift
		for (int k=0; k < servicePlan.getGiftList().size(); k++) {
			logger.debug("i am now looping gift list");
			optService = new RptServiceDetailDTO();
//			if(cOrder){
//				cOrderOfferCode = "";
//				if(servicePlan.getOptServiceDetail().getOptServItemList().get(j).getOfferCode()!=null && 
//						!"x".equalsIgnoreCase(servicePlan.getOptServiceDetail().getOptServItemList().get(j).getOfferCode())){
//					cOrderOfferCode = " ( "+servicePlan.getOptServiceDetail().getOptServItemList().get(j).getOfferCode()+" ) ";
//				}
//				
//				if(servicePlan.getOptServiceDetail().getOptServItemList().get(j).getIncentiveCd()!=null && 
//						!"x".equalsIgnoreCase(servicePlan.getOptServiceDetail().getOptServItemList().get(j).getIncentiveCd())){
//					cOrderOfferCode += " ( "+servicePlan.getOptServiceDetail().getOptServItemList().get(j).getIncentiveCd()+" ) ";
//				}
//			}
			
			String giftInputAttb = "";
			
			if(servicePlan.getGiftList().get(k).getGiftAttbList()!=null&&order.getComponents()!=null){
				for(AttbDTO dto:servicePlan.getGiftList().get(k).getGiftAttbList()){
					if("Y".equalsIgnoreCase(dto.getVisualInd())||cOrder){
						for(int m = 0; m<order.getComponents().length;m++){
							if(servicePlan.getGiftList().get(k).getItemID()!=null && servicePlan.getGiftList().get(k).getItemID().equalsIgnoreCase(order.getComponents()[m].getItemId())
									&&dto.getAttbId().equalsIgnoreCase(order.getComponents()[m].getAttbId())){
								if(giftInputAttb.equals("")){
									giftInputAttb = "<br/>("+dto.getAttbDesc()+": "+order.getComponents()[m].getAttbValue();
								}else{
									giftInputAttb += giftInputAttb + "<br/>"+dto.getAttbDesc()+": "+order.getComponents()[m].getAttbValue();
								}
							}
						}
					}
				}
			}
			
			if(!giftInputAttb.equals(""))
				giftInputAttb = giftInputAttb+")";
			
			String dediVASCode = "";
			
			if(cOrder){
				if(servicePlan.getGiftList().get(k).getDediVASList()!=null&&servicePlan.getGiftList().get(k).getDediVASList().size()>0){
					for(ImsRptBasketItemDTO dediVAS:servicePlan.getGiftList().get(k).getDediVASList()){
						dediVASCode += "<br/>(Auto tie VAS : ( " + dediVAS.getOfferCode() + " ) ";
						if(dediVAS.getIncentiveCd()!=null){
							dediVASCode += " ( "+dediVAS.getIncentiveCd()+" ) ";
						}
						if("Y".equalsIgnoreCase(order.getPreInstallInd())&&dediVAS.getPreInstOfferCd()!=null){
							dediVASCode += " ( Pre-installation dummy offer code: "+dediVAS.getPreInstOfferCd()+" ) ";
						}
						dediVASCode += ")";
					}
					dediVASCode += "<br/>";
				}
			}
		     
			optService.setItemHtml(servicePlan.getGiftList().get(k).getGiftTitle()+giftInputAttb+dediVASCode);
			optService.setItemRecurrentAmt("");
//			optService.setItemRecurrentAmt(servicePlan.getGiftList().get(i)+" "+dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("monthlyRate01").getItemHtml());
			
//			if(servicePlan.getOptServiceDetail().getOptServItemList().get(j).getContractPeriod()>0)
//				optService.setItemRecurrentAmt(servicePlan.getOptServiceDetail().getOptServItemList().get(j).getItemMthFix()+" "+dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("monthlyRate01").getItemHtml());
//			else
//				optService.setItemRecurrentAmt(servicePlan.getOptServiceDetail().getOptServItemList().get(j).getItemMth2Mth()+" "+dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("monthlyRate02").getItemHtml());
			printRptServiceDetailDTO(optService);
			vasAndOptList.add(optService);
		}

		rptImsAppServDTO.setNowTVPurchased("N");
		
		logger.debug("Now TV Bundle");
		// Now TV Bundle
			 //Now TV Bundle //Gary Newpricing
				if (order.getTvPriceInd() != null && "Y".equalsIgnoreCase(order.getTvPriceInd())) {
					if(servicePlan.getNtvServiceDetail().getNewTVPricingItemList().getFtaCampaign().isSelected()){
					logger.debug("New Pricing");
					
					rptImsAppServDTO.setNowTVPurchased("Y");
					
					// NTV_FREE heading
					if (servicePlan.getNtvServiceDetail().getNtvFreeItemList().size() > 0) {
//						optService = new RptServiceDetailDTO();
//						optService.setItemHtml("<br />");
//						printRptServiceDetailDTO(optService);
//						vasAndOptList.add(optService);
						optService = new RptServiceDetailDTO();
						RptServiceInfoDTO termDesc = new RptServiceInfoDTO();
						
					     if(dbRptstaticWords!=null){
					    	 termDesc.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("termDesc04"));
					     }
					     cOrderOfferCode="";
					     
							if(cOrder){
								if(servicePlan.getNtvServiceDetail().getNtvFreeItemList().get(0).getOfferCode()!=null ){
									cOrderOfferCode = " ( "+servicePlan.getNtvServiceDetail().getNtvFreeItemList().get(0).getOfferCode()+" )";
								}
								
								if(servicePlan.getNtvServiceDetail().getNtvFreeItemList().get(0).getIncentiveCd()!=null && 
										!"x".equalsIgnoreCase(servicePlan.getNtvServiceDetail().getNtvFreeItemList().get(0).getIncentiveCd())){
									cOrderOfferCode += " ( "+servicePlan.getNtvServiceDetail().getNtvFreeItemList().get(0).getIncentiveCd()+" ) ";
								}
								
							}
						optService.setItemHtml(termDesc.getItemHtml()+cOrderOfferCode);
						//Gary 
						boolean showWaiveMsg=true;
						for (int k=0; k < servicePlan.getNtvServiceDetail().getNtvPayItemList().size(); k++) {
							logger.debug("getType:"+servicePlan.getNtvServiceDetail().getNtvPayItemList().get(k).getType());
							
							if(servicePlan.getNtvServiceDetail().getNtvPayItemList().get(k).getType().indexOf("NTV_STB")!=-1 ){
								showWaiveMsg=false;
								break;
							}
						}
							if(showWaiveMsg){
								optService.setItemRecurrentAmt(servicePlan.getNtvServiceDetail().getNtvFreeItemList().get(0).getItemMthFix().replaceAll(((char)10)+"", "<br />"));
								optService.setItemRecurrentAmt2(servicePlan.getNtvServiceDetail().getNtvFreeItemList().get(0).getItemMth2Mth());
							}
						printRptServiceDetailDTO(optService);
						vasAndOptList.add(optService);
					}
					
					NowTVAddUI  newPricingItem = servicePlan.getNtvServiceDetail().getNewTVPricingItemList();
//					logger.debug("New TV Pricing: " + gson.toJson(newPricingItem));
					
					optService = new RptServiceDetailDTO();
					if(cOrder){
					optService.setItemHtml("<b>TV Pack Pricing Indicator:</b> "+order.getTvPriceInd()+"<br/>");
					vasAndOptList.add(optService);

					optService = new RptServiceDetailDTO();	
					if(dbRptstaticWords!=null){
						optService.setItemHtml(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("termDesc04").getItemHtml()+"<br/>");
					}
					vasAndOptList.add(optService);

					}
					
					if(cOrder&&order.getDecodeType()!=null&&!order.getDecodeType().equals("")){
						optService = new RptServiceDetailDTO();
						optService.setItemHtml("Decoder Type:"+order.getDecodeType());
						optService.setItemRecurrentAmt(null);
						optService.setItemRecurrentAmt2(null);
						vasAndOptList.add(optService);
					}
					
					String topUpHeading="<b>"+dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("termDesc10").getItemHtml()+"</b><br/>";
					List<String> packTitles= new ArrayList<String>();
					packTitles.add(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("termDesc07").getItemHtml());
					packTitles.add(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("termDesc08").getItemHtml());
					packTitles.add(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("termDesc09").getItemHtml());
					
					this.getDisplayNowTvAddReport(vasAndOptList, newPricingItem, cOrder, topUpHeading, isPT, dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("newPricingDot").getItemHtml(),packTitles,order,dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("fRecDesc").getItemHtml());
					//logger.debug("New TV Pricing: " + gson.toJson(vasAndOptList));//test********
					

				}}else{
						logger.debug("Non-New Pricing");
		
		// NTV_FREE heading
		if (servicePlan.getNtvServiceDetail().getNtvFreeItemList().size() > 0) {
//			optService = new RptServiceDetailDTO();
//			optService.setItemHtml("<br />");
//			printRptServiceDetailDTO(optService);
//			vasAndOptList.add(optService);
			rptImsAppServDTO.setNowTVPurchased("Y");
			optService = new RptServiceDetailDTO();
			RptServiceInfoDTO termDesc = new RptServiceInfoDTO();
			if ("en".equals(rptImsAppServDTO.getLocale())) {	
				termDesc=(RptServiceInfoDTO) appCtx.getBean("termDescEng04");
			}else if ("zh_HK".equals(rptImsAppServDTO.getLocale())) {
				termDesc=(RptServiceInfoDTO) appCtx.getBean("termDescChi04");
			}
		     if(dbRptstaticWords!=null){
		    	 termDesc.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("termDesc04"));
		     }
		     cOrderOfferCode="";
		     
				if(cOrder){
					if(servicePlan.getNtvServiceDetail().getNtvFreeItemList().get(0).getOfferCode()!=null ){
						cOrderOfferCode = " ( "+servicePlan.getNtvServiceDetail().getNtvFreeItemList().get(0).getOfferCode()+" )";
					}
					
					if(servicePlan.getNtvServiceDetail().getNtvFreeItemList().get(0).getIncentiveCd()!=null && 
							!"x".equalsIgnoreCase(servicePlan.getNtvServiceDetail().getNtvFreeItemList().get(0).getIncentiveCd())){
						cOrderOfferCode += " ( "+servicePlan.getNtvServiceDetail().getNtvFreeItemList().get(0).getIncentiveCd()+" ) ";
					}
					
				}
			optService.setItemHtml(termDesc.getItemHtml()+cOrderOfferCode);
			//Gary 
			boolean showWaiveMsg=true;
			for (int k=0; k < servicePlan.getNtvServiceDetail().getNtvPayItemList().size(); k++) {
				logger.debug("getType:"+servicePlan.getNtvServiceDetail().getNtvPayItemList().get(k).getType());
				
				if(servicePlan.getNtvServiceDetail().getNtvPayItemList().get(k).getType().indexOf("NTV_STB")!=-1 ){
					showWaiveMsg=false;
					break;
				}
			}
				if(showWaiveMsg){
					optService.setItemRecurrentAmt(servicePlan.getNtvServiceDetail().getNtvFreeItemList().get(0).getItemMthFix().replaceAll(((char)10)+"", "<br />"));
					optService.setItemRecurrentAmt2(servicePlan.getNtvServiceDetail().getNtvFreeItemList().get(0).getItemMth2Mth());
				}
			printRptServiceDetailDTO(optService);
			vasAndOptList.add(optService);
		}
		
		if(cOrder&&order.getDecodeType()!=null&&!order.getDecodeType().equals("")){
			optService = new RptServiceDetailDTO();
			optService.setItemHtml("Decoder Type:"+order.getDecodeType());
			optService.setItemRecurrentAmt(null);
			optService.setItemRecurrentAmt2(null);
			vasAndOptList.add(optService);
		}

		logger.debug("NTV_FREE StarterPack Channel");
		if (servicePlan.getNtvServiceDetail().getNtvFreeSPChannelList().size() > 0) {
			optService = new RptServiceDetailDTO();
			optService.setItemHtml("<b>" + servicePlan.getNtvServiceDetail().getNtvFreeSPChannelList().get(0).getChannelGroupDesc() + "</b>");
			printRptServiceDetailDTO(optService);
			vasAndOptList.add(optService);

			for (int k=0; k < servicePlan.getNtvServiceDetail().getNtvFreeSPChannelList().size(); k++) {
				optService = new RptServiceDetailDTO();
				String temp = servicePlan.getNtvServiceDetail().getNtvFreeSPChannelList().get(0).getChannelDesc().replaceAll(((char)10)+"", "<br />");
				planCd="";
				campignCd="";
				if(cOrder){
					if(servicePlan.getNtvServiceDetail().getNtvFreeSPChannelList().get(0).getPlanCd()!=null ){
						planCd = " ( "+servicePlan.getNtvServiceDetail().getNtvFreeSPChannelList().get(0).getPlanCd()+" )";
					}
					if(servicePlan.getNtvServiceDetail().getNtvFreeSPChannelList().get(0).getCampaignCd()!=null ){
						campignCd =  " ( "+servicePlan.getNtvServiceDetail().getNtvFreeSPChannelList().get(0).getCampaignCd() + " )";
					}
					temp+=planCd+campignCd;
				}
				optService.setItemHtml(temp);
				printRptServiceDetailDTO(optService);
				vasAndOptList.add(optService);
			}
		}

		logger.debug("NTV_FREE EntertainmentPack Channel");
		if (servicePlan.getNtvServiceDetail().getNtvFreeEPChannelList().size() > 0) {
			optService = new RptServiceDetailDTO();
			optService.setItemHtml("<b>" + servicePlan.getNtvServiceDetail().getNtvFreeEPChannelList().get(0).getChannelGroupDesc() + "</b>");
			printRptServiceDetailDTO(optService);
			vasAndOptList.add(optService);

			for (int k=0; k < servicePlan.getNtvServiceDetail().getNtvFreeEPChannelList().size(); k++) {
				optService = new RptServiceDetailDTO();
				String temp = servicePlan.getNtvServiceDetail().getNtvFreeEPChannelList().get(k).getChannelDesc();
				planCd="";
				campignCd="";
				if(cOrder){
					if(servicePlan.getNtvServiceDetail().getNtvFreeEPChannelList().get(k).getPlanCd()!=null ){
						planCd = " ( "+servicePlan.getNtvServiceDetail().getNtvFreeEPChannelList().get(k).getPlanCd()+" )";
					}
					if(servicePlan.getNtvServiceDetail().getNtvFreeEPChannelList().get(k).getCampaignCd()!=null ){
						campignCd =  " ( "+servicePlan.getNtvServiceDetail().getNtvFreeEPChannelList().get(k).getCampaignCd() + " )";
					}
					temp+=planCd+campignCd;
				}
				logger.debug("EntertainmentPack Channel:"+temp);
				optService.setItemHtml(temp.replaceAll(((char)10)+"", "<br />"));
				printRptServiceDetailDTO(optService);
				vasAndOptList.add(optService);
			}
		}
		
		logger.debug("NTV_FREE Free2HD");
		if (servicePlan.getNtvServiceDetail().getNtvFreeHDChannelList().size() > 0) {
			optService = new RptServiceDetailDTO();
			optService.setItemHtml("<b>" + servicePlan.getNtvServiceDetail().getNtvFreeHDChannelList().get(0).getChannelGroupDesc() + "</b>");
			printRptServiceDetailDTO(optService);
			vasAndOptList.add(optService);

			String tmpFreeHDItemHtml = "";
			for (int k=0; k < servicePlan.getNtvServiceDetail().getNtvFreeHDChannelList().size(); k++) {
				if (k > 0) {
					if (servicePlan.getNtvServiceDetail().getNtvFreeHDChannelList().get(k).getParentChannelId() == null ||
						servicePlan.getNtvServiceDetail().getNtvFreeHDChannelList().get(k).getParentChannelId().length() == 0) {
						optService = new RptServiceDetailDTO();
						optService.setItemHtml(tmpFreeHDItemHtml.trim() + "<br />");
						printRptServiceDetailDTO(optService);
						vasAndOptList.add(optService);
						tmpFreeHDItemHtml = "";
					}
				}
				
				tmpFreeHDItemHtml +=  " " + servicePlan.getNtvServiceDetail().getNtvFreeHDChannelList().get(k).getChannelDesc().replaceAll(((char)10)+"", "<br />");
				planCd="";
				campignCd="";
				if(cOrder){
					if(servicePlan.getNtvServiceDetail().getNtvFreeHDChannelList().get(k).getPlanCd()!=null ){
						planCd = " ( "+servicePlan.getNtvServiceDetail().getNtvFreeHDChannelList().get(k).getPlanCd()+" )";
					}
					if(servicePlan.getNtvServiceDetail().getNtvFreeHDChannelList().get(k).getCampaignCd()!=null ){
						campignCd =  " ( "+servicePlan.getNtvServiceDetail().getNtvFreeHDChannelList().get(k).getCampaignCd() + " )";
					}
					tmpFreeHDItemHtml+=planCd+campignCd;
				}
			}
			if (tmpFreeHDItemHtml != "") {
				optService = new RptServiceDetailDTO();
				optService.setItemHtml(tmpFreeHDItemHtml.trim());
				logger.debug("tmpFreeHDItemHtml.trim():"+optService.getItemHtml());
				printRptServiceDetailDTO(optService);
				vasAndOptList.add(optService);
			}
		}
		
		
		logger.debug("NTV_PAY, NTV_P_30F6");
		if (servicePlan.getNtvServiceDetail().getNtvPayItemList().size() > 0) {
			
			optService = new RptServiceDetailDTO();
			
			for (int k=0; k < servicePlan.getNtvServiceDetail().getNtvPayItemList().size(); k++) {
				logger.debug("getType:"+servicePlan.getNtvServiceDetail().getNtvPayItemList().get(k).getType());
				if(servicePlan.getNtvServiceDetail().getNtvPayItemList().get(k).getType().indexOf("NTV_P")!=-1 ||
						servicePlan.getNtvServiceDetail().getNtvPayItemList().get(k).getType().indexOf("NTV_SP")!=-1){
					optService = new RptServiceDetailDTO();
					cOrderOfferCode = "";
					
					if(cOrder){
						if(servicePlan.getNtvServiceDetail().getNtvPayItemList().get(k).getOfferCode()!=null ){
							cOrderOfferCode = " ( "+servicePlan.getNtvServiceDetail().getNtvPayItemList().get(k).getOfferCode()+" )";
						}
						if(servicePlan.getNtvServiceDetail().getNtvPayItemList().get(k).getIncentiveCd()!=null && 
								!"x".equalsIgnoreCase(servicePlan.getNtvServiceDetail().getNtvPayItemList().get(k).getIncentiveCd())){
							cOrderOfferCode += " ( "+servicePlan.getNtvServiceDetail().getNtvPayItemList().get(k).getIncentiveCd()+" ) ";
						}
					}
					optService.setItemHtml("<b>"+ servicePlan.getNtvServiceDetail().getNtvPayItemList().get(k).getItemTitle() + "</b> "+cOrderOfferCode);
					optService.setItemRecurrentAmt(servicePlan.getNtvServiceDetail().getNtvPayItemList().get(k).getItemMthFix());
					optService.setItemRecurrentAmt2(servicePlan.getNtvServiceDetail().getNtvPayItemList().get(k).getItemMth2Mth());
					printRptServiceDetailDTO(optService);
					vasAndOptList.add(optService);
				}
			}
			String tmpPayItemHtml = "";
			int count=0;boolean addCd=false;//Gary
			for (int k=0; k < servicePlan.getNtvServiceDetail().getNtvPayChannelList().size(); k++) {
				if (k > 0) {
					if (servicePlan.getNtvServiceDetail().getNtvPayChannelList().get(k).getParentChannelId() == null ||
						servicePlan.getNtvServiceDetail().getNtvPayChannelList().get(k).getParentChannelId().length() == 0) {
						optService = new RptServiceDetailDTO();
						optService.setItemHtml(tmpPayItemHtml.trim() + "<br />");
						printRptServiceDetailDTO(optService);
						vasAndOptList.add(optService);
						tmpPayItemHtml = "";
					}
				}
				if (servicePlan.getNtvServiceDetail().getNtvPayChannelList().get(k).getChannelCd() == null || 
					servicePlan.getNtvServiceDetail().getNtvPayChannelList().get(k).getChannelCd().length() == 0) {
					tmpPayItemHtml +=  " " + servicePlan.getNtvServiceDetail().getNtvPayChannelList().get(k).getChannelDesc().replaceAll(((char)10)+"", "<br />");
				} else {
					tmpPayItemHtml +=  " Ch." + servicePlan.getNtvServiceDetail().getNtvPayChannelList().get(k).getChannelCd() + " " 
					+ servicePlan.getNtvServiceDetail().getNtvPayChannelList().get(k).getChannelDesc().replaceAll(((char)10)+"", "<br />");
				}
				planCd="";
				campignCd="";
				
				if(k+1 < servicePlan.getNtvServiceDetail().getNtvPayChannelList().size()&& (servicePlan.getNtvServiceDetail().getNtvPayChannelList().get(k+1).getChannelCd() == null || 
					servicePlan.getNtvServiceDetail().getNtvPayChannelList().get(k+1).getChannelCd().length() == 0)){
					count+=1;
					addCd=false;
				}else{
					addCd=true;
				}
				if(cOrder && addCd){
					for(;count>=0;count--){
						if(servicePlan.getNtvServiceDetail().getNtvPayChannelList().get(k-count).getPlanCd()!=null ){
							planCd = "( "+servicePlan.getNtvServiceDetail().getNtvPayChannelList().get(k-count).getPlanCd()+" )";
						}
						if(servicePlan.getNtvServiceDetail().getNtvPayChannelList().get(k-count).getCampaignCd()!=null ){
							campignCd =  "( "+servicePlan.getNtvServiceDetail().getNtvPayChannelList().get(k-count).getCampaignCd() + " )";
						}
						tmpPayItemHtml+=planCd+campignCd;
					}
					addCd=false;
					count=0;
					
				}
//				if(cOrder){
//					if(servicePlan.getNtvServiceDetail().getNtvPayChannelList().get(k).getPlanCd()!=null ){
//						planCd = " ("+servicePlan.getNtvServiceDetail().getNtvPayChannelList().get(k).getPlanCd()+")";
//					}
//					if(servicePlan.getNtvServiceDetail().getNtvPayChannelList().get(k).getCampaignCd()!=null ){
//						campignCd =  " ("+servicePlan.getNtvServiceDetail().getNtvPayChannelList().get(k).getCampaignCd() + ")";
//					}
//					tmpPayItemHtml+=planCd+campignCd;
//				}
			}
			if (tmpPayItemHtml != "") {
				optService = new RptServiceDetailDTO();
				optService.setItemHtml(tmpPayItemHtml.trim());
				printRptServiceDetailDTO(optService);
				vasAndOptList.add(optService);
			}
			}
			
		}
				for (int k=0; k < servicePlan.getNtvServiceDetail().getNtvPayItemList().size(); k++) {
					if(servicePlan.getNtvServiceDetail().getNtvPayItemList().get(k).getType().indexOf("NTV_P")==-1 &&
							servicePlan.getNtvServiceDetail().getNtvPayItemList().get(k).getType().indexOf("NTV_SP")==-1){
						optService = new RptServiceDetailDTO();
						cOrderOfferCode = "";
						
						if(cOrder){
							if(servicePlan.getNtvServiceDetail().getNtvPayItemList().get(k).getOfferCode()!=null ){
								cOrderOfferCode = "( "+servicePlan.getNtvServiceDetail().getNtvPayItemList().get(k).getOfferCode()+" )";
							}
							if(servicePlan.getNtvServiceDetail().getNtvPayItemList().get(k).getIncentiveCd()!=null && 
									!"x".equalsIgnoreCase(servicePlan.getNtvServiceDetail().getNtvPayItemList().get(k).getIncentiveCd())){
								cOrderOfferCode += " ( "+servicePlan.getNtvServiceDetail().getNtvPayItemList().get(k).getIncentiveCd()+" ) ";
							}
						}
						optService.setItemHtml("<b>"+ servicePlan.getNtvServiceDetail().getNtvPayItemList().get(k).getItemTitle() + "</b> "+cOrderOfferCode);
						optService.setItemRecurrentAmt(servicePlan.getNtvServiceDetail().getNtvPayItemList().get(k).getItemMthFix());
						optService.setItemRecurrentAmt2(servicePlan.getNtvServiceDetail().getNtvPayItemList().get(k).getItemMth2Mth());
						printRptServiceDetailDTO(optService);
						vasAndOptList.add(optService);
					}
				}
		logger.debug("NTV_OTHER");
		if (servicePlan.getNtvServiceDetail().getNtvOtherItemList().size() > 0) {
//			optService = new RptServiceDetailDTO();
//			optService.setItemHtml("<br />");
//			printRptServiceDetailDTO(optService);
//			vasAndOptList.add(optService);
			
			optService = new RptServiceDetailDTO();
			RptServiceInfoDTO termDesc = new RptServiceInfoDTO();
			if ("en".equals(rptImsAppServDTO.getLocale())) {	
				termDesc=(RptServiceInfoDTO) appCtx.getBean("termDescEng05");
			}else if ("zh_HK".equals(rptImsAppServDTO.getLocale())) {
				termDesc=(RptServiceInfoDTO) appCtx.getBean("termDescChi05");
			}
		     if(dbRptstaticWords!=null){
		    	 termDesc.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("termDesc05"));
		     }
			optService.setItemHtml(termDesc.getItemHtml());
			printRptServiceDetailDTO(optService);
			vasAndOptList.add(optService);

			for (int k=0; k < servicePlan.getNtvServiceDetail().getNtvOtherItemList().size(); k++) {
				optService = new RptServiceDetailDTO();
				cOrderOfferCode = "";
				
				if(cOrder){
					if(servicePlan.getNtvServiceDetail().getNtvOtherItemList().get(k).getOfferCode()!=null ){
						cOrderOfferCode = " ( "+servicePlan.getNtvServiceDetail().getNtvOtherItemList().get(k).getOfferCode()+" )";
					}
					if(servicePlan.getNtvServiceDetail().getNtvOtherItemList().get(k).getIncentiveCd()!=null && 
							!"x".equalsIgnoreCase(servicePlan.getNtvServiceDetail().getNtvOtherItemList().get(k).getIncentiveCd())){
						cOrderOfferCode += " ( "+servicePlan.getNtvServiceDetail().getNtvOtherItemList().get(k).getIncentiveCd()+" ) ";
					}
				}
				optService.setItemHtml(servicePlan.getNtvServiceDetail().getNtvOtherItemList().get(k).getItemTitle()+cOrderOfferCode);
				if(servicePlan.getNtvServiceDetail().getNtvOtherItemList().get(k).getItemMthFix()!=null&&!servicePlan.getNtvServiceDetail().getNtvOtherItemList().get(k).getItemMthFix().isEmpty()){
					optService.setItemRecurrentAmt(servicePlan.getNtvServiceDetail().getNtvOtherItemList().get(k).getItemMthFix());
				}else{
					optService.setItemRecurrentAmt("");
				}
				if(servicePlan.getNtvServiceDetail().getNtvOtherItemList().get(k).getItemMth2Mth()!=null&&!servicePlan.getNtvServiceDetail().getNtvOtherItemList().get(k).getItemMth2Mth().isEmpty()){
					optService.setItemRecurrentAmt2(servicePlan.getNtvServiceDetail().getNtvOtherItemList().get(k).getItemMth2Mth());
				}else{
					optService.setItemRecurrentAmt2("");
				}
				printRptServiceDetailDTO(optService);
				vasAndOptList.add(optService);
			}
		}

		logger.debug("New Media Option");
		if (servicePlan.getOptServiceDetail().getMediaItemList().size() > 0) {
//			optService = new RptServiceDetailDTO();
//			optService.setItemHtml("<br />");
//			printRptServiceDetailDTO(optService);
//			vasAndOptList.add(optService);
			
			optService = new RptServiceDetailDTO();
			RptServiceInfoDTO termDesc = new RptServiceInfoDTO();
			if ("en".equals(rptImsAppServDTO.getLocale())) {	
				termDesc=(RptServiceInfoDTO) appCtx.getBean("termDescEng06");
			}else if ("zh_HK".equals(rptImsAppServDTO.getLocale())) {
				termDesc=(RptServiceInfoDTO) appCtx.getBean("termDescChi06");
			}
		     if(dbRptstaticWords!=null){
		    	 termDesc.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("termDesc06"));
		     }
			optService.setItemHtml(termDesc.getItemHtml());
			printRptServiceDetailDTO(optService);
			vasAndOptList.add(optService);
		
							
			for (int j=0; j < servicePlan.getOptServiceDetail().getMediaItemList().size(); j++) {
				optService = new RptServiceDetailDTO();
				cOrderOfferCode = "";
				
				if(cOrder){
					
					if(servicePlan.getOptServiceDetail().getMediaItemList().get(j).getOfferCode()!=null && 
							!"x".equalsIgnoreCase(servicePlan.getOptServiceDetail().getMediaItemList().get(j).getOfferCode())){
						cOrderOfferCode = " ( "+servicePlan.getOptServiceDetail().getMediaItemList().get(j).getOfferCode()+" ) ";
					}
					if(servicePlan.getOptServiceDetail().getMediaItemList().get(j).getIncentiveCd()!=null && 
							!"x".equalsIgnoreCase(servicePlan.getOptServiceDetail().getMediaItemList().get(j).getIncentiveCd())){
						cOrderOfferCode += " ( "+servicePlan.getOptServiceDetail().getMediaItemList().get(j).getIncentiveCd()+" ) ";
					}
				}
				optService.setItemId(servicePlan.getOptServiceDetail().getMediaItemList().get(j).getItemID());
				optService.setItemHtml(servicePlan.getOptServiceDetail().getMediaItemList().get(j).getItemTitle()+cOrderOfferCode);
				optService.setItemRecurrentAmt(servicePlan.getOptServiceDetail().getMediaItemList().get(j).getItemMthFix());
				optService.setItemRecurrentAmt2(servicePlan.getOptServiceDetail().getMediaItemList().get(j).getItemMth2Mth());
				printRptServiceDetailDTO(optService);
				vasAndOptList.add(optService);
			}
		}
		
		logger.debug("backend Vas");
		if (servicePlan.getOptServiceDetail().getBackendVasItemList().size() > 0 && cOrder) {
			
			optService = new RptServiceDetailDTO();
			RptServiceInfoDTO termDesc = new RptServiceInfoDTO();
												
			for (int j=0; j < servicePlan.getOptServiceDetail().getBackendVasItemList().size(); j++) {
				optService = new RptServiceDetailDTO();
				cOrderOfferCode = "";
				
				if(cOrder){
					
					if(servicePlan.getOptServiceDetail().getBackendVasItemList().get(j).getOfferCode()!=null && 
							!"x".equalsIgnoreCase(servicePlan.getOptServiceDetail().getBackendVasItemList().get(j).getOfferCode())){
						cOrderOfferCode = " ( "+servicePlan.getOptServiceDetail().getBackendVasItemList().get(j).getOfferCode()+" ) ";
					}
					
					if(servicePlan.getOptServiceDetail().getBackendVasItemList().get(j).getIncentiveCd()!=null && 
							!"x".equalsIgnoreCase(servicePlan.getOptServiceDetail().getBackendVasItemList().get(j).getIncentiveCd())){
						cOrderOfferCode += " ( "+servicePlan.getOptServiceDetail().getBackendVasItemList().get(j).getIncentiveCd()+" ) ";
					}
				}
				optService.setItemHtml(servicePlan.getOptServiceDetail().getBackendVasItemList().get(j).getItemTitle()+cOrderOfferCode);
				if(servicePlan.getOptServiceDetail().getBackendVasItemList().get(j).getContractPeriod()>0)
					optService.setItemRecurrentAmt(servicePlan.getOptServiceDetail().getBackendVasItemList().get(j).getItemMthFix()+" "+dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("monthlyRate01").getItemHtml());
				else
					optService.setItemRecurrentAmt(servicePlan.getOptServiceDetail().getBackendVasItemList().get(j).getItemMth2Mth()+" "+dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("monthlyRate02").getItemHtml());
//				optService.setItemRecurrentAmt3(servicePlan.getOptServiceDetail().getMediaItemList().get(j).getItemMthFix());//Gary DDD
				printRptServiceDetailDTO(optService);
				vasAndOptList.add(optService);
				logger.debug(gson.toJson(optService));
			}
		}
		
		logger.debug("NTV_Backend");
		if (cOrder && servicePlan.getNtvServiceDetail().getNtvBackendChannelList().size() > 0) {

			
			optService = new RptServiceDetailDTO();
			
			
			optService.setItemHtml("<b>Backend Channel:</b> ");
			printRptServiceDetailDTO(optService);
			vasAndOptList.add(optService);

			for (int k=0; k < servicePlan.getNtvServiceDetail().getNtvBackendChannelList().size(); k++) {
				optService = new RptServiceDetailDTO();
				cOrderOfferCode = "";
				
				if(cOrder){
					
					if(servicePlan.getNtvServiceDetail().getNtvBackendChannelList().get(k).getCampaignCd()!=null && 
							!"x".equalsIgnoreCase(servicePlan.getNtvServiceDetail().getNtvBackendChannelList().get(k).getCampaignCd())){
						cOrderOfferCode = " ( "+servicePlan.getNtvServiceDetail().getNtvBackendChannelList().get(k).getCampaignCd()+" ) ";
					}
					if(servicePlan.getNtvServiceDetail().getNtvBackendChannelList().get(k).getOfferCd()!=null ){
						cOrderOfferCode += " ( "+servicePlan.getNtvServiceDetail().getNtvBackendChannelList().get(k).getOfferCd()+" )";
					}
				}
				optService.setItemHtml(cOrderOfferCode);
				printRptServiceDetailDTO(optService);
				vasAndOptList.add(optService);
			}
		}
		
		
		setMrtItemDesc(servicePlan, vasAndOptList, cOrder);
		setImsVasParmItemDesc(servicePlan, vasAndOptList);
		rptImsAppServDTO.setVasAndOptDtls(vasAndOptList);
		logger.debug(" vasAndOptList:");
//		for (int i1=0;i1<vasAndOptList.size();i1++)
//			logger.trace(i1+": "+vasAndOptList.get(i1).getItemHtml()+": "+vasAndOptList.get(i1).getItemHtml2());
		
		
		

		logger.debug("part 2 - DB ServicePlan - section J - details of service plan, optional and value added services");
		RptServiceInfoDTO serviceInfo;
		
		RptServiceInfoDTO tempServicePlanI01  = new RptServiceInfoDTO();
		RptServiceInfoDTO tempServicePlanI02  = new RptServiceInfoDTO();
		RptServiceInfoDTO tempServicePlanI03  = new RptServiceInfoDTO();
		RptServiceInfoDTO tempServicePlanI04  = new RptServiceInfoDTO();
		RptServiceInfoDTO tempServicePlanI05  = new RptServiceInfoDTO();
		RptServiceInfoDTO tempServicePlanI06  = new RptServiceInfoDTO();
		RptServiceInfoDTO tempServicePlanI07  = new RptServiceInfoDTO();
		RptServiceInfoDTO tempServicePlanI08  = new RptServiceInfoDTO();
		RptServiceInfoDTO tempServicePlanI09  = new RptServiceInfoDTO();
		if ("en".equals(rptImsAppServDTO.getLocale())) {	
			tempServicePlanI01=(RptServiceInfoDTO) appCtx.getBean("termDescEng01");
			tempServicePlanI02=(RptServiceInfoDTO) appCtx.getBean("servPlanEng02");
			tempServicePlanI03=(RptServiceInfoDTO) appCtx.getBean("servPlanEng03");
			tempServicePlanI04=(RptServiceInfoDTO) appCtx.getBean("servPlanEng04");
			tempServicePlanI05=(RptServiceInfoDTO) appCtx.getBean("servPlanEng05");
			tempServicePlanI06=(RptServiceInfoDTO) appCtx.getBean("servPlanEng06phhos");
		}else if ("zh_HK".equals(rptImsAppServDTO.getLocale())) {
			tempServicePlanI01=(RptServiceInfoDTO) appCtx.getBean("termDescChi01");
			tempServicePlanI02=(RptServiceInfoDTO) appCtx.getBean("servPlanChi02");
			tempServicePlanI03=(RptServiceInfoDTO) appCtx.getBean("servPlanChi03");
			tempServicePlanI04=(RptServiceInfoDTO) appCtx.getBean("servPlanChi04");
			tempServicePlanI05=(RptServiceInfoDTO) appCtx.getBean("servPlanChi05");
			tempServicePlanI06=(RptServiceInfoDTO) appCtx.getBean("servPlanChi06phhos");
		}
	     if(dbRptstaticWords!=null){
	    	 tempServicePlanI01.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("termDesc01"));
	    	 tempServicePlanI02.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("servPlan02"));
	    	 tempServicePlanI03.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("servPlan03"));
	    	 tempServicePlanI04.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("servPlan04"));
	    	 tempServicePlanI05.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("servPlan05"));
	    	 tempServicePlanI06.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("servPlan06phhos"));
	    	 tempServicePlanI07.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("servPlan07"));//pre-install t&c
	    	 tempServicePlanI08.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("servPlan08"));//pre-use t&c
	    	 tempServicePlanI09.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("servPlan09"));//pre-inst mesh-router t&c
	     }
		

		servPlanList.add(tempServicePlanI01);
		servPlanList.add(tempServicePlanI02);
		servPlanList.add(tempServicePlanI03);
		servPlanList.add(tempServicePlanI04);
		servPlanList.add(tempServicePlanI05);
		servPlanList.add(tempServicePlanI06);
		
		if("Y".equalsIgnoreCase(order.getPreInstallInd())){
			servPlanList.add(tempServicePlanI07);
			if("Y".equalsIgnoreCase(servicePlan.getPreInstRouterPurchasedInd())){
				servPlanList.add(tempServicePlanI09);
			}
		}
		
		if("Y".equalsIgnoreCase(order.getPreUseInd())){
			servPlanList.add(tempServicePlanI08);
		}

		
//		termDescEng01=<b>CORE SERVICES:</b>
//		termDescEng05=<b>SPECIAL RENTAL EQUIPMENT:</b>
		
		RptServiceInfoDTO tempTermDesc02 = new RptServiceInfoDTO();
		RptServiceInfoDTO tempTermDesc03 = new RptServiceInfoDTO();
		RptServiceInfoDTO tempTermDesc04 = new RptServiceInfoDTO();
		RptServiceInfoDTO tempTermDesc06 = new RptServiceInfoDTO();
		if ("en".equals(rptImsAppServDTO.getLocale())) {	
			tempTermDesc02=(RptServiceInfoDTO) appCtx.getBean("termDescEng02");//termDescEng02=<b>EXTRA PREMIUMS:</b>
			tempTermDesc03=(RptServiceInfoDTO) appCtx.getBean("termDescEng03");//termDescEng03=<b>EXTRA SERVICES:</b>
			tempTermDesc04=(RptServiceInfoDTO) appCtx.getBean("termDescEng04");//termDescEng04=<b>now TV BUNDLE:</b>
			tempTermDesc06=(RptServiceInfoDTO) appCtx.getBean("termDescEng06");//termDescEng06=<b>NEW MEDIA OPTION:</b>
		}else if ("zh_HK".equals(rptImsAppServDTO.getLocale())) {
			tempTermDesc02=(RptServiceInfoDTO) appCtx.getBean("termDescChi02");
			tempTermDesc03=(RptServiceInfoDTO) appCtx.getBean("termDescChi03");
			tempTermDesc04=(RptServiceInfoDTO) appCtx.getBean("termDescChi04");
			tempTermDesc06=(RptServiceInfoDTO) appCtx.getBean("termDescChi06");
		}
	     if(dbRptstaticWords!=null){
	    	 tempTermDesc02.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("termDesc02"));
	    	 tempTermDesc03.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("termDesc03"));
	    	 tempTermDesc04.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("termDesc04"));
	    	 tempTermDesc06.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("termDesc06"));
	     }
			     
			     
		// Program Item
		String tempHtml;
		serviceInfo = new RptServiceInfoDTO();
		serviceInfo.setItemHtml("<b>" + servicePlan.getCoreServiceDetail().getProgItem().getItemTitle() + "</b>");
		servPlanList.add(serviceInfo);
		
		serviceInfo = new RptServiceInfoDTO();
		tempHtml = servicePlan.getCoreServiceDetail().getProgItem().getItemDetails();
		if (tempHtml != null && tempHtml.length() > 0) {
			tempHtml = tempHtml.replaceAll(((char)10)+"", "<br />");
		}
		tempHtml = tempHtml + "<br />";
		serviceInfo.setItemHtml(tempHtml);
		servPlanList.add(serviceInfo);
		
		// BVAS Mandatory Item 
		for (int j=0; j < servicePlan.getCoreServiceDetail().getBvasMandItemList().size(); j++) {
			serviceInfo = new RptServiceInfoDTO();
			tempHtml = servicePlan.getCoreServiceDetail().getBvasMandItemList().get(j).getItemDetails();
			if (tempHtml != null && tempHtml.length() > 0) {
				if(tempHtml.indexOf(((char)10)+"")!=-1){
					tempHtml = "<b>" + tempHtml.replaceFirst(((char)10)+"", "</b><br />");
				}else if(tempHtml.indexOf("<br/>")!=-1){
					tempHtml = "<b>" + tempHtml.replaceFirst("<br/>", "</b><br />");
				}
				tempHtml = tempHtml.replaceAll(((char)10)+"", "<br />");
				tempHtml = tempHtml + "<br />";
				serviceInfo.setItemHtml(tempHtml);
				servPlanList.add(serviceInfo);
			}
			
		}
		
		// BVAS Non-Mandatory Item 
		for (int j=0; j < servicePlan.getCoreServiceDetail().getBvasNonMItemList().size(); j++) {
			serviceInfo = new RptServiceInfoDTO();
			tempHtml = servicePlan.getCoreServiceDetail().getBvasNonMItemList().get(j).getItemDetails();
			if (tempHtml != null && tempHtml.length() > 0) {
				if(tempHtml.indexOf(((char)10)+"")!=-1){
					tempHtml = "<b>" + tempHtml.replaceFirst(((char)10)+"", "</b><br />");
				}else if(tempHtml.indexOf("<br/>")!=-1){
					tempHtml = "<b>" + tempHtml.replaceFirst("<br/>", "</b><br />");
				}
				tempHtml = tempHtml.replaceAll(((char)10)+"", "<br />");
				tempHtml = tempHtml + "<br />";
				serviceInfo.setItemHtml(tempHtml);
				servPlanList.add(serviceInfo);
			}
			
		}

		// Optional Premium
		if (servicePlan.getOptServiceDetail().getOptPremItemList().size() > 0) {
//			serviceInfo = new RptServiceInfoDTO();
//			serviceInfo.setItemHtml("<br />");
//			servPlanList.add(serviceInfo);
//			serviceInfo.setItemHtml("<b>EXTRA PREMIUM:</b>");
			servPlanList.add(tempTermDesc02);
		
			for (int j=0; j < servicePlan.getOptServiceDetail().getOptPremItemList().size(); j++) {
				serviceInfo = new RptServiceInfoDTO();
				tempHtml = servicePlan.getOptServiceDetail().getOptPremItemList().get(j).getItemDetails();
				if (tempHtml != null && tempHtml.length() > 0) {
					if(tempHtml.indexOf(((char)10)+"")!=-1){
						tempHtml = "<b>" + tempHtml.replaceFirst(((char)10)+"", "</b><br />");
					}else if(tempHtml.indexOf("<br/>")!=-1){
						tempHtml = "<b>" + tempHtml.replaceFirst("<br/>", "</b><br />");
					}
					tempHtml = tempHtml.replaceAll(((char)10)+"", "<br />");
					tempHtml = tempHtml + "<br />";
					serviceInfo.setItemHtml(tempHtml);
					servPlanList.add(serviceInfo);
				}
			}
		}
		
		// Optional Service
		//if (servicePlan.getOptServiceDetail().getWlBbItemList().size() > 0 || servicePlan.getOptServiceDetail().getAntiVirItemList().size() > 0) {
		if (servicePlan.getOptServiceDetail().getOptServItemList().size() > 0) {
//			serviceInfo = new RptServiceInfoDTO();
//			serviceInfo.setItemHtml("<br />");
//			servPlanList.add(serviceInfo);
//			serviceInfo.setItemHtml("<b>EXTRA SERVICES:</b>");
			servPlanList.add(tempTermDesc03);
		
			for (int j=0; j < servicePlan.getOptServiceDetail().getOptServItemList().size(); j++) {
				serviceInfo = new RptServiceInfoDTO();
				tempHtml = servicePlan.getOptServiceDetail().getOptServItemList().get(j).getItemDetails();
				if (tempHtml != null && tempHtml.length() > 0) {
					if(tempHtml.indexOf(((char)10)+"")!=-1){
						tempHtml = "<b>" + tempHtml.replaceFirst(((char)10)+"", "</b><br />");
					}else if(tempHtml.indexOf("<br/>")!=-1){
						tempHtml = "<b>" + tempHtml.replaceFirst("<br/>", "</b><br />");
					}
					tempHtml = tempHtml.replaceAll(((char)10)+"", "<br />");
					tempHtml = tempHtml + "<br />";
					serviceInfo.setItemHtml(tempHtml);
					servPlanList.add(serviceInfo);
				}
			}
		}

		// Gift
		for (ImsRptGiftDTO giftList: servicePlan.getGiftList()) {
			logger.debug("i am now looping gift list Tnc");
			optService = new RptServiceDetailDTO();
			serviceInfo = new RptServiceInfoDTO();
		     
			optService.setItemHtml("<b>"+giftList.getGiftTitle()+"</b><br/><br/>"+giftList.getGiftDetail()+"<br/>");
			printRptServiceDetailDTO(optService);
			serviceInfo.setItemHtml(optService.getItemHtml());
			servPlanList.add(serviceInfo);
			
		}
		
		// now TV
		//Gary add newPricing
		if (order.getTvPriceInd() != null && "Y".equalsIgnoreCase(order.getTvPriceInd())) {
			logger.debug("New Pricing");
			servPlanList.add(tempTermDesc04);
			
			NowTVAddUI  newPricingItem = servicePlan.getNtvServiceDetail().getNewTVPricingItemList();
			
			
			 NowTvCampaignUI  ftaCampaign =  newPricingItem.getFtaCampaign();
			 if(ftaCampaign!=null && ftaCampaign.isSelected()){
				 serviceInfo = new RptServiceInfoDTO();
					String tnc="";
					if(!StringUtils.isEmpty(ftaCampaign.getTnc()))
						tnc+=ftaCampaign.getTnc()+"<br/>";//ftaCampaign
				for(NowTvPackUI ftaPacks :ftaCampaign.getTvPacks()){							
					if(ftaPacks.isSelected()){
						
						if(!StringUtils.isEmpty(ftaPacks.getTnc()))
							tnc+=ftaPacks.getTnc()+"<br/>";//ftaPacks
						
					}
					
				}
				for(NowTvVasBundle vasBundle:ftaCampaign.getVasBundles() )
					if( !StringUtils.isEmpty(vasBundle.getTnc()))
							tnc+=vasBundle.getTnc()+"<br/>";//ftaCampaign.getVasBundles()
				//tnc=tnc.replace("null<br/>","");
				logger.debug("FTA: "+tnc);
				if(tnc!=""){
					serviceInfo.setItemHtml(tnc);
					servPlanList.add(serviceInfo);
				}
				serviceInfo = new RptServiceInfoDTO();
				String topTnc="";
				for(NowTvTopUpUI ftatopups:ftaCampaign.getTopUps()){
					if(ftatopups.isSelected()&& !StringUtils.isEmpty(ftatopups.getTnc())){								
						topTnc+=ftatopups.getTnc()+"<br/>";								
					}								
				}
				if(topTnc!=""){
					serviceInfo.setItemHtml(topTnc);
					servPlanList.add(serviceInfo);
				}
			 }
				
				
				
			
		NowTvCampaignUI  hardCampaign =  newPricingItem.getHardCampaign();
		if(hardCampaign!=null && hardCampaign.isSelected()){
			String tnc="";
			if(!StringUtils.isEmpty(hardCampaign.getTnc()))
				tnc+=hardCampaign.getTnc()+"<br/>";
				for(NowTvPackUI hardpacks :hardCampaign.getTvPacks()){
					
					if(hardpacks.isSelected()){
						serviceInfo = new RptServiceInfoDTO();
						
						if(!StringUtils.isEmpty(hardpacks.getTnc()))
							tnc+=hardpacks.getTnc()+"<br/>";
						
						
					}
					
				}
				for(NowTvVasBundle vasBundle:hardCampaign.getVasBundles() )
					if( !StringUtils.isEmpty(vasBundle.getTnc()))
							tnc+=vasBundle.getTnc()+"<br/>";
				logger.debug("HB: "+tnc);
				//tnc=tnc.replace("null<br/>","");
				if(tnc!=""){
					serviceInfo.setItemHtml(tnc);
					servPlanList.add(serviceInfo);
				}
				
				serviceInfo = new RptServiceInfoDTO();
				String topTnc="";
				for(NowTvTopUpUI hardtopups:hardCampaign.getTopUps()){
					if(hardtopups.isSelected()&& !StringUtils.isEmpty(hardtopups.getTnc())){
						topTnc+=hardtopups.getTnc()+"<br/>";	
					}
				}
				if(topTnc!=""){
					serviceInfo.setItemHtml(topTnc);
					servPlanList.add(serviceInfo);
				}
				}
			
			for(NowTvCampaignUI campaigns:newPricingItem.getPayTvCampaign()){
				if(campaigns.isSelected()){
					String tnc="";
					if(!StringUtils.isEmpty(campaigns.getTnc()))
						tnc+=campaigns.getTnc()+"<br/>";
					for(NowTvPackUI packs :campaigns.getTvPacks()){
						if(packs.isSelected()){
							serviceInfo = new RptServiceInfoDTO();
							
							if(!StringUtils.isEmpty(packs.getTnc()))
								tnc+=packs.getTnc()+"<br/>";
						}
					}
					for(NowTvVasBundle vasBundle:campaigns.getVasBundles() )
						if( !StringUtils.isEmpty(vasBundle.getTnc()))
								tnc+=vasBundle.getTnc()+"<br/>";
					//tnc=tnc.replace("null<br/>","");
					logger.debug("PB: "+tnc);
					if(tnc!=""){
						serviceInfo.setItemHtml(tnc);
						servPlanList.add(serviceInfo);
					}
					
					serviceInfo = new RptServiceInfoDTO();
					String topTnc="";
					for(NowTvTopUpUI topups:campaigns.getTopUps()){
						if(topups.isSelected()&& !StringUtils.isEmpty(topups.getTnc())){
							topTnc+=topups.getTnc()+"<br/>";
						}
					}
					if(topTnc!=""){
						serviceInfo.setItemHtml(topTnc);
						servPlanList.add(serviceInfo);
					}
				}
				
			}
			
				
			
			
		}else{
			logger.debug("Non-New Pricing");
		if (servicePlan.getNtvServiceDetail().getNtvFreeItemList().size() > 0 ||
			servicePlan.getNtvServiceDetail().getNtvPayItemList().size() > 0 ||
			servicePlan.getNtvServiceDetail().getNtvOtherItemList().size() > 0) {
//				serviceInfo = new RptServiceInfoDTO();
//				serviceInfo.setItemHtml("<br />");
//				servPlanList.add(serviceInfo);
//				serviceInfo.setItemHtml("<b>now TV Bundle:</b>");
				servPlanList.add(tempTermDesc04);
		
			for (int j=0; j < servicePlan.getNtvServiceDetail().getNtvFreeItemList().size(); j++) {
				serviceInfo = new RptServiceInfoDTO();
				tempHtml = servicePlan.getNtvServiceDetail().getNtvFreeItemList().get(j).getItemDetails();
				if (tempHtml != null && tempHtml.length() > 0) {
					if(tempHtml.indexOf(((char)10)+"")!=-1){
						tempHtml = "<b>" + tempHtml.replaceFirst(((char)10)+"", "</b><br />");
					}else if(tempHtml.indexOf("<br/>")!=-1){
						tempHtml = "<b>" + tempHtml.replaceFirst("<br/>", "</b><br />");
					}
					tempHtml = tempHtml.replaceAll(((char)10)+"", "<br />");
					tempHtml = tempHtml + "<br />";
					serviceInfo.setItemHtml(tempHtml);
					servPlanList.add(serviceInfo);
				}
			}
		
			for (int j=0; j < servicePlan.getNtvServiceDetail().getNtvPayItemList().size(); j++) {
				serviceInfo = new RptServiceInfoDTO();
				tempHtml = servicePlan.getNtvServiceDetail().getNtvPayItemList().get(j).getItemDetails();
				if (tempHtml != null && tempHtml.length() > 0) {
					if(tempHtml.indexOf(((char)10)+"")!=-1){
						tempHtml = "<b>" + tempHtml.replaceFirst(((char)10)+"", "</b><br />");
					}else if(tempHtml.indexOf("<br/>")!=-1){
						tempHtml = "<b>" + tempHtml.replaceFirst("<br/>", "</b><br />");
					}
					tempHtml = tempHtml.replaceAll(((char)10)+"", "<br />");
					tempHtml = tempHtml + "<br />";
					serviceInfo.setItemHtml(tempHtml);
					servPlanList.add(serviceInfo);
				}
			}
		
			for (int j=0; j < servicePlan.getNtvServiceDetail().getNtvOtherItemList().size(); j++) {
				serviceInfo = new RptServiceInfoDTO();
				tempHtml = servicePlan.getNtvServiceDetail().getNtvOtherItemList().get(j).getItemDetails();
				if (tempHtml != null && tempHtml.length() > 0 && (!isOnlyOneLine(tempHtml))) {
					if(tempHtml.indexOf(((char)10)+"")!=-1){
						tempHtml = "<b>" + tempHtml.replaceFirst(((char)10)+"", "</b><br />");
					}else if(tempHtml.indexOf("<br/>")!=-1){
						tempHtml = "<b>" + tempHtml.replaceFirst("<br/>", "</b><br />");
					}
					tempHtml = tempHtml.replaceAll(((char)10)+"", "<br />");
					tempHtml = tempHtml + "<br />";
					serviceInfo.setItemHtml(tempHtml);
					servPlanList.add(serviceInfo);
				}
			}
		}
	}

		// New Media Option
		if (servicePlan.getOptServiceDetail().getMediaItemList().size() > 0) {
//			serviceInfo = new RptServiceInfoDTO();
//			serviceInfo.setItemHtml("<br />");
//			servPlanList.add(serviceInfo);
//			serviceInfo.setItemHtml("<b>New Media Option:</b>");
			servPlanList.add(tempTermDesc06);
		
			for (int j=0; j < servicePlan.getOptServiceDetail().getMediaItemList().size(); j++) {
				serviceInfo = new RptServiceInfoDTO();
				tempHtml = servicePlan.getOptServiceDetail().getMediaItemList().get(j).getItemDetails();
				if (tempHtml != null && tempHtml.length() > 0) {
					if(tempHtml.indexOf(((char)10)+"")!=-1){
						tempHtml = "<b>" + tempHtml.replaceFirst(((char)10)+"", "</b><br />");
					}else if(tempHtml.indexOf("<br/>")!=-1){
						tempHtml = "<b>" + tempHtml.replaceFirst("<br/>", "</b><br />");
					}
					tempHtml = tempHtml.replaceAll(((char)10)+"", "<br />");
					tempHtml = tempHtml + "<br />";
					serviceInfo.setItemHtml(tempHtml);
					servPlanList.add(serviceInfo);
				}
			}
		}
		
		rptImsAppServDTO.setServPlanDtls(servPlanList);
		logger.debug(" servPlanList:");
//		for (int i1=0;i1<servPlanList.size();i1++)
//			logger.debug(i1+": "+servPlanList.get(i1).getItemHtml()+": "+servPlanList.get(i1).getItemHtml2());
		
		
		rptImsAppServDTO.setQosMeasureInd(servicePlan.getQosMeasureInd());

	} else if (obj instanceof ImsSignoffDTO) {
		if (ImsSignoffDTO.SignatureTypeEnum.ThirdParty_SIGN == ((ImsSignoffDTO) obj).getSignatureType()){
			thirdPartySign = (ImsSignoffDTO) obj;
			logger.debug("inside ImsReportServiceImpl, ThirdParty_SIGN received");
		}else if (ImsSignoffDTO.SignatureTypeEnum.CREDIT_CARD_SIGN 	== ((ImsSignoffDTO) obj).getSignatureType()){
			creditCardsign = (ImsSignoffDTO) obj;
			logger.debug("inside ImsReportServiceImpl, CREDIT_CARD_SIGN received");
		}else if (ImsSignoffDTO.SignatureTypeEnum.CUSTOMER_SIGN 	== ((ImsSignoffDTO) obj).getSignatureType()){
			customerSign = (ImsSignoffDTO) obj;
			logger.debug("inside ImsReportServiceImpl, signature received");
		}else if (ImsSignoffDTO.SignatureTypeEnum.CareCash_SIGN 	== ((ImsSignoffDTO) obj).getSignatureType()){
			careCashSign = (ImsSignoffDTO) obj;
			logger.debug("inside ImsReportServiceImpl, careCashSign received");
		}
	}
}


if(cOrder){			
	DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
//	logger.debug("servicePlan.getImsWQDTO().getApplicationDate():"+servicePlan.getImsWQDTO().getApplicationDate());
	rptImsAppServDTO.setWq_Application_date(dateFormat.format(order.getAppDate()));
	logger.debug("getResourceShortage:"+order.getInstallAddress().getAddrInventory().getResourceShortage());
	if("Y".equals(order.getInstallAddress().getAddrInventory().getResourceShortage())){
		rptImsAppServDTO.setWq_Earliest_srd_reason(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("WQEarliestSrdReason").getItemHtml());
	}
	String temp = servicePlan.getWqRemarkForInterialUse();
	logger.debug("temp:"+temp);
	String[] array = temp.split((char)10+"");
	for(int m=0;m<array.length;m++){
		logger.debug("array:"+array[m]);
		RptServiceInfoDTO bomOrderRemarks = new RptServiceInfoDTO();
		bomOrderRemarks.setItemHtml(array[m]);
		internalUseRemark.add(bomOrderRemarks);
	}
	rptImsAppServDTO.setBomOrderRemarks(internalUseRemark);

	//Eddie 20150813 Add Welcome Letter for FS
	if (order.getSMSno()!=null && !"".equals(order.getSMSno())){
		rptImsAppServDTO.setSms_email("SMS " + order.getSMSno() + " " + order.getEsigEmailLang());
	}
	else if(order.getEmailAddress()!=null && !"".equals(order.getEmailAddress())){
		rptImsAppServDTO.setSms_email("EMAIL " + order.getEmailAddress() + " " + order.getEsigEmailLang());
	}
	else {
		if(order.getCustomer().getContact().getContactPhone()!=null && !"".equals(order.getCustomer().getContact().getContactPhone())){
			rptImsAppServDTO.setSms_email("SMS " + order.getCustomer().getContact().getContactPhone() + " " + order.getEsigEmailLang());
		}
		else if(order.getAppointment().getInstSmsNum()!=null && !"".equals(order.getAppointment().getInstSmsNum())){
			rptImsAppServDTO.setSms_email("SMS " + order.getAppointment().getInstSmsNum() + " " + order.getEsigEmailLang());
		}
		else if(order.getCustomer().getAccount().getEmailAddr()!=null && !"".equals(order.getCustomer().getAccount().getEmailAddr())){
			rptImsAppServDTO.setSms_email("EMAIL " + order.getCustomer().getAccount().getEmailAddr() + " " + order.getEsigEmailLang());
		}
		else
			rptImsAppServDTO.setSms_email("EMAIL " + order.getLoginId() + "@netvigator.com" + " " + order.getEsigEmailLang());
	}
}

if(isCC){
	rptImsAppServDTO.setSalesChannel(order.getSalesChannel());
	rptImsAppServDTO.setShopCd(order.getSourceCd());
}
if(cOrder){			
	rptImsAppServDTO.setShow_Section_THINGS_TO_KNOW(false);		//1
//	rptImsAppServDTO.setShow_Section_CUSTOMER_DETAILS(false);		//2
	rptImsAppServDTO.setShow_Section_SERVICE_PROVIDER(false);		//3
//	rptImsAppServDTO.setShow_Section_CORE_SERVICES(false);		//4
//	rptImsAppServDTO.setShow_Section_OPTIONAL_SERVICES(false);		//5
	rptImsAppServDTO.setShow_Section_GIFTS_AND_PREMIUMS(false);		//6
//	rptImsAppServDTO.setShow_Section_CHARGES_FOR_CORE_SERVICES(false);		//7
//	rptImsAppServDTO.setShow_Section_BILL_PREFERENCES(false);		//8
	rptImsAppServDTO.setShow_Section_IMPORTANT_INFORMATION(false);		//9
	rptImsAppServDTO.setShow_Section_GLOSSARY(false);		//10
	rptImsAppServDTO.setShow_Section_DETAILS(false);		//11
//	rptImsAppServDTO.setShow_Section_PAYMENT(false);		//12
//	rptImsAppServDTO.setShow_Section_PERSONAL(false);		//13
	rptImsAppServDTO.setShow_Section_AGREEMENT(false);		//15
//	rptImsAppServDTO.setShow_Section_Interial_Use(false);		//16
	rptImsAppServDTO.setcOrder(true);
}

rptImsAppServDTO.setCustomerCopyInd("N");
// S&S Customer Copy
BeanUtils.copyProperties(rptImsAppServCustCopyDTO, rptImsAppServDTO);
rptImsAppServCustCopyDTO.setCustomerCopyInd("Y");

if(Signed){
	int signatureSize=0;
	if(customerSign!=null){
		try {
			signatureSize = customerSign.getSignatureInputStream().available();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			signatureSize = 0;
		}
	}
	if(signOffed&&(customerSign==null||signatureSize<=0)&&"E".equalsIgnoreCase(disMode)){
		signOffLogService.signOffOrderLog(order, "EmptySignature", "Customer signature is empty.");
		throw new ImsEmptySignatureException();
	}
	if(customerSign!=null){
		rptImsAppServDTO.setCustSignature(customerSign.getSignatureInputStream());
		rptImsAppServCustCopyDTO.setCustSignature(customerSign.getSignatureInputStream());
		rptIms3PartyCreditCardDTO.setCustSignature(customerSign.getSignatureInputStream());
		logger.debug("inside ImsReportServiceImpl, customer sign is not null");
	}
	if(creditCardsign!=null){
		rptImsAppServDTO.setCustSignaturePay(creditCardsign.getSignatureInputStream());
		rptImsAppServCustCopyDTO.setCustSignaturePay(creditCardsign.getSignatureInputStream());
		logger.debug("inside ImsReportServiceImpl, Credit Card sign is not null");
	}
	if(thirdPartySign!=null){
		rptImsAppServDTO.setCustSignaturePay(thirdPartySign.getSignatureInputStream());
		rptImsAppServCustCopyDTO.setCustSignaturePay(thirdPartySign.getSignatureInputStream());
		rptIms3PartyCreditCardDTO.setThirdPartySignature(thirdPartySign.getSignatureInputStream());
		logger.debug("inside ImsReportServiceImpl, 3Party Sign is not null");
	}
	if(careCashSign!=null){
		rptImsAppServDTO.setCareCashSignature(careCashSign.getSignatureInputStream());
		logger.debug("inside ImsReportServiceImpl, careCash Sign is not null");
	}
}

DateFormat dateFormat = new SimpleDateFormat("yyyy");
Date date = new Date();
rptImsAppServDTO.setAfFooter(dateFormat.format(date)+ " Hong Kong Telecommunications (HKT) Limited");

ssList.add(rptImsAppServDTO);	
ssCustCopyList.add(rptImsAppServCustCopyDTO);

// Add Application for Service Form
String key;
	 key = imsRetailPTJasperName + (RPT_LANG_ZH.equalsIgnoreCase(pLang) ? FORM_ZH_EXT : "");

map.put(key, new JasperReport(key, ssList));
map.put(key + CUST_COPY, new JasperReport(key, ssCustCopyList));



// Add Credit Card Authorization Form
if (isReq3PartyCreditCardForm && !isCC) {
	ccList.add(rptIms3PartyCreditCardDTO);
	map.put(imsRetail3PartyJasperName, new JasperReport(imsRetail3PartyJasperName, ccList));			
}

String careCashChannelCode = "";

if(!isCC&&!"DS".equals(order.getImsOrderType())){
	careCashChannelCode = order.getShopCd();
}else{
	if(order.getSalesChannel()!=null&&!order.getSalesChannel().isEmpty()){
		careCashChannelCode = order.getSalesChannel();
	}else{
		careCashChannelCode = order.getShopCd();
	}
}


if ("I".equalsIgnoreCase(order.getCustomer().getCareCashInd()))
{
	setRptIGuardCareCashDTO(rptImsAppServDTO, disMode, order.getCustomer().getCareCashOptOutInd(), order.getCustomer().getCareCashEmail(), order.getCustomer().getCareCashMobile(), order.getAppDate(), careCashChannelCode);
	map.put(imsCareCashJasperName, new JasperReport(imsCareCashJasperName, ssList));	
}

return map;
}
	public void setPersonalAndClubHkt(
			HashMap<String, HashMap<String, RptServiceInfoDTO>> dbRptstaticWords,
			OrderImsUI order, 
			RptImsAppServDTO af, Boolean isShowAllForTest,
			ArrayList<RptServiceInfoDTO> personalInfoListEC,
			ArrayList<RptServiceInfoDTO> personalInfoListNewCustOptIn,
			ArrayList<RptServiceInfoDTO> personalInfoListNewCustOptOut) {
		logger.debug("section L - personal information collection statement");
		HashMap<String, RptServiceInfoDTO> db = dbRptstaticWords.get(af.getLocale());
		
		ArrayList<RptServiceInfoDTO> personalInfoListNewCustOptInBtm = new ArrayList<RptServiceInfoDTO>();
		
		RptServiceInfoDTO tempPersonalInfo01  = new RptServiceInfoDTO();
		RptServiceInfoDTO tempPersonalInfo02  = new RptServiceInfoDTO();
		RptServiceInfoDTO tempPersonalInfo03  = new RptServiceInfoDTO();
		RptServiceInfoDTO tempPersonalInfo04  = new RptServiceInfoDTO();
		
	     if(dbRptstaticWords!=null){
	    	 tempPersonalInfo01.copy(db.get("personalInfo01"));
	    	 tempPersonalInfo03.copy(db.get("clubHKTagree01"));
	    	 tempPersonalInfo04.copy(db.get("clubHKTpersonal09"));

	 		String clubInd = order.getCustomer().getTheClubInd(); //A for existing cust, Y for new registration, N for not Reg 
	 		String csportalInd = order.getCustomer().getCsPortalInd();
	 		
	 		if("Y".equalsIgnoreCase(af.getNewCustInd())){
				if(clubInd.equalsIgnoreCase("Y")&&csportalInd.equalsIgnoreCase("Y")){
			    	 tempPersonalInfo01.copy(db.get("clubHKTpersonal01"));
			    	 af.setOptOutServiceSubject(db.get("clubHKToptout01").getItemHtml());
			    	 af.setOptOutHktClubSubject(db.get("clubHKToptout02").getItemHtml());
			    	 af.setIsRegHKTLoginId("Y");
				}else if(clubInd.equalsIgnoreCase("Y")&&csportalInd.equalsIgnoreCase("A")){
			    	 tempPersonalInfo01.copy(db.get("clubHKTpersonal02"));
			    	 af.setOptOutServiceSubject(db.get("clubHKToptout01").getItemHtml());
			    	 af.setOptOutHktClubSubject(db.get("clubHKToptout03").getItemHtml());
			    	 af.setIsRegHKTLoginId("Y");
				}else if(clubInd.equalsIgnoreCase("Y")&&csportalInd.equalsIgnoreCase("N")){
			    	 tempPersonalInfo01.copy(db.get("clubHKTpersonal07"));
			    	 af.setOptOutServiceSubject(db.get("clubHKToptout01").getItemHtml());
			    	 af.setOptOutHktClubSubject(db.get("clubHKToptout03").getItemHtml());
			    	 af.setIsRegHKTLoginId("Y");
				}else if(clubInd.equalsIgnoreCase("A")&&csportalInd.equalsIgnoreCase("Y")){
			    	 tempPersonalInfo01.copy(db.get("clubHKTpersonal03"));
			    	 af.setOptOutServiceSubject(db.get("clubHKToptout01").getItemHtml());
			    	 af.setOptOutHktClubSubject(db.get("clubHKToptout04").getItemHtml());
			    	 af.setIsRegHKTLoginId("Y");
				}else if(clubInd.equalsIgnoreCase("A")&&csportalInd.equalsIgnoreCase("N")){
			    	 tempPersonalInfo01.copy(db.get("clubHKTpersonal04"));
			    	 af.setOptOutServiceSubject(db.get("clubHKToptout01").getItemHtml());
				}else if(clubInd.equalsIgnoreCase("A")&&csportalInd.equalsIgnoreCase("A")){
			    	 tempPersonalInfo01.copy(db.get("clubHKTpersonal05"));
			    	 af.setOptOutServiceSubject(db.get("clubHKToptout01").getItemHtml());
				}else{
			    	 tempPersonalInfo01.copy(db.get("clubHKTpersonal06"));
			    	 af.setOptOutServiceSubject(db.get("clubHKToptout01").getItemHtml());
				}
	 		}else{
				if(clubInd.equalsIgnoreCase("Y")&&csportalInd.equalsIgnoreCase("Y")){
			    	 tempPersonalInfo01.copy(db.get("clubHKTpersonal01"));
			    	 af.setOptOutHktClubSubject(db.get("clubHKToptout02").getItemHtml());
			    	 af.setIsRegHKTLoginId("Y");
				}else if(clubInd.equalsIgnoreCase("Y")&&csportalInd.equalsIgnoreCase("A")){
			    	 tempPersonalInfo01.copy(db.get("clubHKTpersonal02"));
			    	 af.setOptOutHktClubSubject(db.get("clubHKToptout03").getItemHtml());
			    	 af.setIsRegHKTLoginId("Y");
				}else if(clubInd.equalsIgnoreCase("Y")&&csportalInd.equalsIgnoreCase("N")){
			    	 tempPersonalInfo01.copy(db.get("clubHKTpersonal07"));
			    	 af.setOptOutHktClubSubject(db.get("clubHKToptout03").getItemHtml());
			    	 af.setIsRegHKTLoginId("Y");
				}else if(clubInd.equalsIgnoreCase("A")&&csportalInd.equalsIgnoreCase("Y")){
			    	 tempPersonalInfo01.copy(db.get("clubHKTpersonal03"));
			    	 af.setOptOutHktClubSubject(db.get("clubHKToptout04").getItemHtml());
			    	 af.setIsRegHKTLoginId("Y");
				}else if(clubInd.equalsIgnoreCase("A")&&csportalInd.equalsIgnoreCase("N")){
			    	 tempPersonalInfo01.copy(db.get("clubHKTpersonal08"));
			    	 tempPersonalInfo03  = null;
				}else if(clubInd.equalsIgnoreCase("A")&&csportalInd.equalsIgnoreCase("A")){
			    	 tempPersonalInfo01.copy(db.get("clubHKTpersonal08"));
			    	 tempPersonalInfo03  = null;
				}else{
			    	 tempPersonalInfo01.copy(db.get("clubHKTpersonal08"));
			    	 tempPersonalInfo03  = null;
				}	 			
	 		}
	    	 
	    	 
	    	 tempPersonalInfo02.copy(db.get("personalInfo02"));
	     }
	     

	 		
	 		String clubOptInd = order.getCustomer().getTheClubOptOutInd()==null||order.getCustomer().getTheClubOptOutInd().isEmpty()?"X":order.getCustomer().getTheClubOptOutInd();
	 		String csportalOptInd = order.getCustomer().getCsPortalOptOutInd()==null||order.getCustomer().getCsPortalOptOutInd().isEmpty()?"X":order.getCustomer().getCsPortalOptOutInd();
				
	 		String hktClubOptoutInd = clubOptInd + csportalOptInd;

			logger.debug("###hktClubOptout Status: " + hktClubOptoutInd);
	 		af.setHktClubOptOutInd(hktClubOptoutInd);

			if(isShowAllForTest){
				RptServiceInfoDTO ec  = new RptServiceInfoDTO();
				ec.setItemHtml("***  1. existing customer  ***<br/><br/>");
			    personalInfoListEC.add(ec);
				RptServiceInfoDTO optin  = new RptServiceInfoDTO();
				optin.setItemHtml("***  2. new customer opt in  ***<br/><br/>");
				personalInfoListNewCustOptIn.add(optin);
				RptServiceInfoDTO optout  = new RptServiceInfoDTO();
				optout.setItemHtml("***  3. new customer opt out  ***<br/><br/>");
				personalInfoListNewCustOptOut.add(optout);
			}
	     personalInfoListEC.add(tempPersonalInfo02);
	     personalInfoListNewCustOptIn.add(tempPersonalInfo01);
	     personalInfoListNewCustOptIn.add(tempPersonalInfo03);
	     personalInfoListNewCustOptInBtm.add(tempPersonalInfo04);
	     personalInfoListNewCustOptOut.add(tempPersonalInfo01);
//		if("Y".equalsIgnoreCase(rptImsAppServDTO.getNewCustInd())){	//comment by Tony no need to check new or existing now
			logger.debug("section L - personalInfo01 new cust");
			af.setPersonalInfoExistingCust(false);
			if(!"Y".equalsIgnoreCase(af.getCustOptOutDirectMailingInd()  ) ||
					!"Y".equalsIgnoreCase( af.getCustOptOutOutBoundInd() ) ||
					!"Y".equalsIgnoreCase( af.getCustOptOutSmsInd() ) ||
					!"Y".equalsIgnoreCase( af.getCustOptOutEmailInd() ) ||
					!"Y".equalsIgnoreCase( af.getCustOptOutWebBillInd() ) ||
					!"Y".equalsIgnoreCase( af.getCustOptOutNonSalesSmsInd() ) ||
					!"Y".equalsIgnoreCase( af.getCustOptOutInternetInd() )){
				af.setPersonalInfoNewCustOptIn(true);
			}else{
				af.setPersonalInfoNewCustOptIn(false);// all tick = opt out = cross in the box
			}
			
//		}else{
//			logger.debug("section L - personalInfo02 existing cust");
//			rptImsAppServDTO.setPersonalInfoExistingCust(true);
//		}
		af.setPersonalInfoDtlsEC(personalInfoListEC);
		af.setPersonalInfoDtlsOptIn(personalInfoListNewCustOptIn);
		af.setPersonalInfoDtlsOptInBtm(personalInfoListNewCustOptInBtm);
		af.setPersonalInfoDtlsOptOut(personalInfoListNewCustOptOut);
	}	

	
	
	public void setNowIDSection(HashMap<String, HashMap<String, RptServiceInfoDTO>> dbRptstaticWords, RptImsAppServDTO rptImsAppServDTO,OrderImsUI order){
		
		
		logger.debug("setNowIDSection");
		ArrayList<RptServiceInfoDTO> nowIDPortalList = new ArrayList<RptServiceInfoDTO>();
		ArrayList<RptServiceInfoDTO> nowIDPortalList2 = new ArrayList<RptServiceInfoDTO>();

		RptServiceInfoDTO nowIDBottom  = new RptServiceInfoDTO();
		RptServiceInfoDTO nowID  = new RptServiceInfoDTO();

		if(dbRptstaticWords!=null){
            nowID.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("nowID01"));
            nowIDBottom.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("nowIDBottom01"));
            rptImsAppServDTO.setNowIDEmailSubject(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("nowID01Email").getItemHtml());           
            rptImsAppServDTO.setNowIDCkBoxText(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("nowID01CkBox").getItemHtml());              
		}
		logger.debug("nowID:"+ nowID);
		nowIDPortalList.add(nowID);
		logger.debug("nowIDBottom:"+ nowIDBottom);
		nowIDPortalList2.add(nowIDBottom);
		rptImsAppServDTO.setNowIDListDtls(nowIDPortalList);
		rptImsAppServDTO.setNowIDListDtls2(nowIDPortalList2);
		rptImsAppServDTO.setIsRegNowID(order.getCustomer().getIsRegNowId());
		rptImsAppServDTO.setNowID(order.getCustomer().getNowId());
		logger.debug("rptImsAppServDTO:"+gson.toJson(rptImsAppServDTO));
	}


	
	
	public void setMrtItemDesc(ImsRptServicePlanDetailDTO servicePlan,
			ArrayList<RptServiceDetailDTO> coreServiceList, boolean cOrder) {
		
		
		
		logger.debug("servicePlan.getCslMrtNumPinList():"+gson.toJson(servicePlan.getCslMrtNumPinList()));//Gary
		logger.debug("coreServiceList:"+gson.toJson(coreServiceList));//Gary
		String itemDesc;
		
		if(cOrder){
			if(servicePlan.getCslMrtNumPinList()!=null && servicePlan.getCslMrtNumPinList().get(0)!=null){
				for(VasParmDTO dto:servicePlan.getCslMrtNumPinList()){
					for(RptServiceDetailDTO serviceDto:coreServiceList){
						if(dto.getItem_id().equals(serviceDto.getItemId())){
							itemDesc=serviceDto.getItemHtml();
							if("Y".equals(dto.getVas_parm_a_af_ind())){
								itemDesc += "<br/>( " +dto.getVas_parm_a_display()+dto.getVas_parm_a() +" )";
							}
							if("Y".equals(dto.getVas_parm_b_af_ind())){
								itemDesc += "<br/>( " +dto.getVas_parm_b_display()+dto.getVas_parm_b() +" )";
							}
							if("Y".equals(dto.getVas_parm_c_af_ind())){
								itemDesc += "<br/>( " +dto.getVas_parm_c_display()+dto.getVas_parm_c() +" )";
							}
							itemDesc+="<br/>";
							serviceDto.setItemHtml(itemDesc);
						}
					}
				}
			}
		}else{
			if(servicePlan.getCslMrtNumPinList()!=null && servicePlan.getCslMrtNumPinList().get(0)!=null){
				for(VasParmDTO dto:servicePlan.getCslMrtNumPinList()){
					for(RptServiceDetailDTO serviceDto:coreServiceList){
						if(dto.getItem_id().equals(serviceDto.getItemId())){
							itemDesc=serviceDto.getItemHtml();
							if("Y".equals(dto.getVas_parm_a_af_ind())){
								itemDesc += "<br/>(" +dto.getVas_parm_a_display()+dto.getVas_parm_a() +")";
							}
							itemDesc+="<br/>";
							serviceDto.setItemHtml(itemDesc);
						}
					}
				}
			}
		}
		

	}		
	

	
	public void setImsVasParmItemDesc(ImsRptServicePlanDetailDTO servicePlan,
			ArrayList<RptServiceDetailDTO> optVasList) {
		
		
		
		logger.debug("servicePlan.getImsVasParmList():"+gson.toJson(servicePlan.getImsVasParmList()));//Gary
		logger.debug("optVasList:"+gson.toJson(optVasList));//Gary
		String itemDesc;
		
			if(servicePlan.getImsVasParmList()!=null){
				for(VasParmDTO dto:servicePlan.getImsVasParmList()){
					for(RptServiceDetailDTO serviceDto:optVasList){
						if(dto.getItem_id().equals(serviceDto.getItemId())){
							itemDesc=serviceDto.getItemHtml();
							if(dto.getVas_parm_a()!=null&&!"".equals(dto.getVas_parm_a())){
								itemDesc += "<br/>( " +dto.getVas_parm_a_label()+": "+dto.getVas_parm_a() +" )";
							}
							if(dto.getVas_parm_b()!=null&&!"".equals(dto.getVas_parm_b())){
								itemDesc += "<br/>( " +dto.getVas_parm_b_label()+": "+dto.getVas_parm_b() +" )";
							}
							if(dto.getVas_parm_c()!=null&&!"".equals(dto.getVas_parm_c())){
								itemDesc += "<br/>( " +dto.getVas_parm_c_label()+": "+dto.getVas_parm_c() +" )";
							}
							itemDesc+="<br/>";
							serviceDto.setItemHtml(itemDesc);
						}
					}
				}
			}
	}		
	
	public ImsNowTVService getImsNowTVService() {
		return imsNowTVService;
	}
	public void setImsNowTVService(ImsNowTVService imsNowTVService) {
		this.imsNowTVService = imsNowTVService;
	}
	private Boolean Signed(ArrayList<Object> pDTOs)
	throws InvocationTargetException, IllegalAccessException {
		Object obj = null;
		for (int i = 0; i < pDTOs.size(); i++) {
			obj = pDTOs.get(i);
			if (obj instanceof ImsSignoffDTO) {
				return true;
			}
		}
		return false;
	}

	private class JasperReport {
		private String jasperName = null;
		private ArrayList<ReportDTO> rptDtoArrList = null;

		public JasperReport(String pJasperName,	ArrayList<ReportDTO> pRptDtoArrList) {
			jasperName = pJasperName;
			rptDtoArrList = pRptDtoArrList;
		}

		protected String getJasperName() {
			return jasperName;
		}

		protected ArrayList<ReportDTO> getRptDtoArrList() {
			return rptDtoArrList;
		}
	}



	private boolean isOnlyOneLine(String itemDetail) {
		String tmpItemDetail;
		tmpItemDetail = itemDetail.replaceAll(((char) 10) + "", "");
		if (tmpItemDetail.trim().equalsIgnoreCase(getFirstLine(itemDetail))) {
			return true;
		} else {
			return false;
		}
	}

	private String getFirstLine(String input) {
		String output = "";

		if (input != null && input.length() > 0) {
			if (input.indexOf(((char) 10) + "") == -1) {
				output = input;
			} else {
				output = input.substring(0, input.indexOf(((char) 10) + ""));
			}
		}

		return output;

	}
	
	
	private List<String> getPisNames(ArrayList<Object> dataList) {
        Object obj = null;
		ImsRptServicePlanDetailDTO servicePlan = null;
		try{
            for (int i = 0; i < dataList.size(); i++) {
    			obj = dataList.get(i);
    			if (obj instanceof ImsRptServicePlanDetailDTO) {
    				servicePlan = (ImsRptServicePlanDetailDTO) obj;
        			if(servicePlan.getPisPdfs() != null){
            			return servicePlan.getPisPdfs();
        			}
    			}
    		}
       } catch (Exception e) {
           logger.error(ExceptionUtils.getFullStackTrace(e));
       }
		return null;		
	}
	
	private HashMap<String, HashMap<String, RptServiceInfoDTO>> getDBRptStaticWords(ArrayList<Object> dataList) {
        Object obj = null;
		ImsRptServicePlanDetailDTO servicePlan = null;
		try{
            for (int i = 0; i < dataList.size(); i++) {
    			obj = dataList.get(i);
    			if (obj instanceof ImsRptServicePlanDetailDTO) {
    				servicePlan = (ImsRptServicePlanDetailDTO) obj;
    		        logger.debug("servicePlan found in getDBRptStaticWords");
            		return servicePlan.getdBRptStaticWords();
    			}
    		}
       } catch (Exception e) {
           logger.error(ExceptionUtils.getFullStackTrace(e));
       }
       logger.debug("return null in getDBRptStaticWords");
		return null;		
	}
	
	private void CloseAllInputStream(List<InputStream> inputStream) throws IOException
	{
		for(InputStream is:inputStream)
		{
			is.close();
		}
	}
	private void printRptServiceInfoDTO (RptServiceInfoDTO dto){
	       logger.debug("ItemHtml:"+dto.getItemHtml()+"\tItemHtml2:"+dto.getItemHtml2()+"\tType:"+dto.getItemType());}
	private void printRptServiceDetailDTO(RptServiceDetailDTO dto){
	       logger.debug("ItemHtml:"+dto.getItemHtml()+"\tAmt:"+dto.getItemRecurrentAmt()+"\tAmt2:"+dto.getItemRecurrentAmt2());
	}
	

	
	private Boolean isAFShowALL(ArrayList<Object> dataList) {
        Object obj = null;
		ImsRptServicePlanDetailDTO servicePlan = null;
		try{
            for (int i = 0; i < dataList.size(); i++) {
    			obj = dataList.get(i);
    			if (obj instanceof ImsRptServicePlanDetailDTO) {
    				servicePlan = (ImsRptServicePlanDetailDTO) obj;
    				if("Y".equalsIgnoreCase(servicePlan.getIsAFShowALL())){
    					return true;
    				}else{
    					return false;
    				}
    			}
    		}
       } catch (Exception e) {
           logger.error(ExceptionUtils.getFullStackTrace(e));
       }
		return false;	
	}
	

	private Boolean isAFShowAllByID(ArrayList<Object> dataList) {
        Object obj = null;
		OrderImsUI order = null;
		try{
            for (int i = 0; i < dataList.size(); i++) {
    			obj = dataList.get(i);
    			if (obj instanceof OrderImsUI) {
    				order = (OrderImsUI) obj;
    				if("PASS".equalsIgnoreCase(order.getCustomer().getIdDocType())&&"AFTEST".equalsIgnoreCase(order.getCustomer().getIdDocNum())){
    					return true;
    				}else{
    					return false;
    				}
    			}
    		}
       } catch (Exception e) {
           logger.error(ExceptionUtils.getFullStackTrace(e));
       }
		return false;	
	}
    
    //Direct Sales
	public void setImsDsCPJasperName(String imsDsCPJasperName) {
		this.imsDsCPJasperName = imsDsCPJasperName;
	}
	public String getImsDsCPJasperName() {
		return imsDsCPJasperName;
	}
	
	private HashMap<String, JasperReport> mapReportDsData(ArrayList<Object> pDTOs, String pLang, Boolean Signed, Boolean isCC, Boolean cOrder, Boolean isPreview, String disMode, Boolean signOffed)//gary
			throws InvocationTargetException, IllegalAccessException, ImsEmptySignatureException {
		
		String planCd = "("+"";
		String campignCd =  "("+"";
		Boolean isPT=false;
		HashMap<String, HashMap<String, RptServiceInfoDTO>> dbRptstaticWords  = new HashMap<String, HashMap<String, RptServiceInfoDTO>>();
		dbRptstaticWords=this.getDBRptStaticWords(pDTOs);
		
		if(dbRptstaticWords==null){
			logger.debug("dbRptstaticWords==null, use properties files");
		}else{
			logger.debug("dbRptstaticWords!=null, use db words");
		}
		ImsSignoffDTO customerSign = null;
		ImsSignoffDTO creditCardsign = null;
		ImsSignoffDTO thirdPartySign = null;
		ImsSignoffDTO careCashSign = null;
		OrderImsUI order = null;
		ImsRptServicePlanDetailDTO servicePlan = null;
		int fixedTerm = 0;
		int extenTerm = 0;
		int totalTerm = 0;
		
		
		HashMap<String, JasperReport> map = new HashMap<String, JasperReport>();
		ArrayList<ReportDTO> ssList = new ArrayList<ReportDTO>();
		ArrayList<ReportDTO> ssCustCopyList = new ArrayList<ReportDTO>();
		ArrayList<ReportDTO> ccList = new ArrayList<ReportDTO>();
		
		
		RptImsAppServDTO rptImsAppServDTO = new RptImsAppServDTO();
		RptImsAppServDTO rptImsAppServCustCopyDTO = new RptImsAppServDTO();
		RptIms3PartyCreditCardDTO rptIms3PartyCreditCardDTO = new RptIms3PartyCreditCardDTO();
		
		Boolean isShowAllForTest = false;
		if(this.isAFShowALL(pDTOs)&&this.isAFShowAllByID(pDTOs)){
			isShowAllForTest = true;
		}
		if(isShowAllForTest){
			rptImsAppServDTO.setShowAllForTest(true);
			rptImsAppServDTO.setShowRegHKTLoginId(false);
		}
		
		if ("zh".equals(pLang)){
			rptImsAppServDTO.setNetvigatorLogo(imageFilePath + "/"	+ NETVIGATOR_LOGO_FILE_ZH);
			rptImsAppServDTO.setFooterPccwLogo(imageFilePath + "/"	+ FOOTER_PCCW_LOGO_FILE_ZH);
			rptImsAppServDTO.setFooterHktLogo(imageFilePath + "/"	+ FOOTER_HKT_LOGO_FILE_ZH);
		}else{
			rptImsAppServDTO.setNetvigatorLogo(imageFilePath + "/"	+ NETVIGATOR_LOGO_FILE_EN);
			rptImsAppServDTO.setFooterPccwLogo(imageFilePath + "/"	+ FOOTER_PCCW_LOGO_FILE_EN);
			rptImsAppServDTO.setFooterHktLogo(imageFilePath + "/"	+ FOOTER_HKT_LOGO_FILE_EN);
		}	
		rptImsAppServDTO.setCompanyLogo(imageFilePath + "/"	+ COMPANY_LOGO_FILE);
		rptImsAppServCustCopyDTO.setCompanyLogo(imageFilePath + "/"	+ COMPANY_LOGO_FILE);
		rptIms3PartyCreditCardDTO.setCompanyLogo(imageFilePath + "/"	+ COMPANY_LOGO_FILE);
		
		Object obj = null;
		boolean isReq3PartyCreditCardForm = false;		
		
		ArrayList<RptServiceDetailDTO> coreServiceList = new ArrayList<RptServiceDetailDTO>();
		ArrayList<RptServiceDetailDTO> vasAndOptList = new ArrayList<RptServiceDetailDTO>();
		ArrayList<RptServiceInfoDTO> coreChrgList = new ArrayList<RptServiceInfoDTO>();
		ArrayList<RptServiceInfoDTO> imprtInfoList = new ArrayList<RptServiceInfoDTO>();
		ArrayList<RptServiceInfoDTO> glossaryList = new ArrayList<RptServiceInfoDTO>();
		ArrayList<RptServiceInfoDTO> servPlanList = new ArrayList<RptServiceInfoDTO>();
		ArrayList<RptServiceInfoDTO> serviceProvideList = new ArrayList<RptServiceInfoDTO>();
		ArrayList<RptServiceInfoDTO> personalInfoListEC = new ArrayList<RptServiceInfoDTO>();
		ArrayList<RptServiceInfoDTO> personalInfoListNewCustOptIn = new ArrayList<RptServiceInfoDTO>();
		ArrayList<RptServiceInfoDTO> personalInfoListNewCustOptOut = new ArrayList<RptServiceInfoDTO>();
		ArrayList<RptServiceInfoDTO> custAgreeList = new ArrayList<RptServiceInfoDTO>();
		ArrayList<RptServiceInfoDTO> creditCardList = new ArrayList<RptServiceInfoDTO>();
		ArrayList<RptServiceInfoDTO> kisList = new ArrayList<RptServiceInfoDTO>();
//		ArrayList<RptServiceInfoDTO> hktServPortalList = new ArrayList<RptServiceInfoDTO>();
		ArrayList<RptServiceInfoDTO> internalUseRemark = new ArrayList<RptServiceInfoDTO>();
		
		if ("zh".equals(pLang)){
			rptImsAppServDTO.setLocale("zh_HK");
		}else{
			rptImsAppServDTO.setLocale("en");
		}	
		
		//RptServiceInfoDTO	tempKIS01	  = new RptServiceInfoDTO();
		RptServiceInfoDTO	tempKIS02	  = new RptServiceInfoDTO();
		RptServiceInfoDTO	tempKIS03	  = new RptServiceInfoDTO();
		RptServiceInfoDTO	tempKIS04	  = new RptServiceInfoDTO();
		RptServiceInfoDTO	tempKIS05	  = new RptServiceInfoDTO();
		RptServiceInfoDTO	tempKIS06	  = new RptServiceInfoDTO();
		
		
		
		
		 if(dbRptstaticWords!=null){
			 tempKIS02.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("kis02"));
			 tempKIS03.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("kis03"));
			 tempKIS04.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("kis04"));
			 tempKIS05.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("kis05"));
			 tempKIS06.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("kis06"));
		 };
		//kisList.add(tempKIS01);
		kisList.add(tempKIS02);
		kisList.add(tempKIS03);
		kisList.add(tempKIS04);
		kisList.add(tempKIS05);
		kisList.add(tempKIS06);
		rptImsAppServDTO.setKisDtls(kisList);
		
		for (int i = 0; i < pDTOs.size(); i++) {
			obj = pDTOs.get(i);
			if (obj instanceof OrderImsUI) {
				order = (OrderImsUI) obj;
				
		
			
				rptImsAppServDTO.setAppNo(order.getOrderId());
				rptIms3PartyCreditCardDTO.setAppNo(order.getOrderId());
				
				
				logger.debug("section A - customer details");
				rptImsAppServDTO.setNewCustInd("Y");
				if (order.getCustomer() != null) {
					if("Y".equalsIgnoreCase(order.getHasBBDailup())){
						rptImsAppServDTO.setNewCustInd("N");
					}else{
						rptImsAppServDTO.setNewCustInd("Y");
					}
					rptImsAppServDTO.setTitle(order.getCustomer().getTitle());
					rptImsAppServDTO.setLastName(order.getCustomer().getLastName());
					rptImsAppServDTO.setFirstName(order.getCustomer().getFirstName());
					if(order.getCustomer().getLastName().length()>13 || order.getCustomer().getFirstName().length()>13){//Gary
						rptImsAppServDTO.setIsNameTooLong(true);
					}else{
						rptImsAppServDTO.setIsNameTooLong(false);
					}
					rptIms3PartyCreditCardDTO.setCustLastName(order.getCustomer().getLastName());
					rptIms3PartyCreditCardDTO.setCustFirstName(order.getCustomer().getFirstName());
					rptImsAppServDTO.setDob(Utility.date2String(order.getCustomer().getDob(),"dd/MM/yyyy"));
					rptImsAppServDTO.setIdDocType(order.getCustomer().getIdDocType());
					rptImsAppServDTO.setIdDocVerifyInd(order.getCustomer().getIdVerified());
					rptImsAppServDTO.setIdDocNum(order.getCustomer().getIdDocNum());
					//Gary
					rptImsAppServDTO.setCompanyName(order.getCustomer().getCompanyName());
					//isCC
					rptImsAppServDTO.setIsCC(isCC);
					//end
					rptIms3PartyCreditCardDTO.setIdDocType(order.getCustomer().getIdDocType());
					rptIms3PartyCreditCardDTO.setIdDocNum(order.getCustomer().getIdDocNum());
					if (order.getCustomer().getContact() != null) { 
						String custContact = order.getCustomer().getContact().getContactPhone();
						if(custContact!= null && custContact.length()>=8){
							custContact = custContact.substring(0, 8);					
						}
						rptImsAppServDTO.setContactPhone(custContact);				
						rptImsAppServDTO.setContactEmail(order.getCustomer().getContact().getEmailAddr());
						rptIms3PartyCreditCardDTO.setContactPhone(custContact);				
						rptImsAppServDTO.setOtherPhone(order.getCustomer().getContact().getOtherPhone());
					}
				}
		
				if (order.getFixedLineNo() == null || (order.getFixedLineNo().isEmpty())) {
					rptImsAppServDTO.setFixedLineNumPccwInd("N");
				} else {
					rptImsAppServDTO.setFixedLineNumPccwInd("Y");
				}
				rptImsAppServDTO.setFixedLineNum(order.getFixedLineNo());
		
				if (order.getCustomer() != null) {
					if (order.getCustomer().getAccount() != null) {
						rptImsAppServDTO.setEmailAddr(order.getCustomer().getAccount().getEmailAddr());				
					}
				}
				logger.debug("section A - installation address");
				if (order.getInstallAddress() != null) {
					rptImsAppServDTO.setFlat(order.getInstallAddress().getUnitNo());
					rptImsAppServDTO.setFloor(order.getInstallAddress().getFloorNo());
					rptImsAppServDTO.setBuildingName(order.getInstallAddress().getBuildNo());
					rptImsAppServDTO.setLotNum(order.getInstallAddress().getHiLotNo());
					rptImsAppServDTO.setStreetNum(order.getInstallAddress().getStrNo());
					rptImsAppServDTO.setStreetName(order.getInstallAddress().getStrName());
					rptImsAppServDTO.setStreetCatgDesc(order.getInstallAddress().getStrCatDesc());
					rptImsAppServDTO.setSectionDesc(order.getInstallAddress().getSectDesc());
					rptImsAppServDTO.setDistrictDesc(order.getInstallAddress().getDistDesc());
					rptImsAppServDTO.setAreaCode(order.getInstallAddress().getAreaCd());
				}
				logger.debug("section A - billing address");
				if ( order.getBillingAddress() == null || (order.getBillingAddress().getHiLotNo().equals("") && order.getBillingAddress().getBuildNo().equals("") && order.getBillingAddress().getStrNo().equals(""))) {
					rptImsAppServDTO.setNoBillingAddressFlag(false); 
				} else {
					rptImsAppServDTO.setNoBillingAddressFlag(true); 
				}
				if (rptImsAppServDTO.isNoBillingAddressFlag()) {
					rptImsAppServDTO.setBillingFlat(order.getBillingAddress().getUnitNo());
					rptImsAppServDTO.setBillingFloor(order.getBillingAddress().getFloorNo());
					rptImsAppServDTO.setBillingBuildingName(order.getBillingAddress().getBuildNo());
					rptImsAppServDTO.setBillingLotNum(order.getBillingAddress().getHiLotNo());
					rptImsAppServDTO.setBillingStreetNum(order.getBillingAddress().getStrNo());
					rptImsAppServDTO.setBillingStreetName(order.getBillingAddress().getStrName());
					rptImsAppServDTO.setBillingStreetCatgDesc(order.getBillingAddress().getStrCatDesc());
					rptImsAppServDTO.setBillingSectionDesc(order.getBillingAddress().getSectDesc());
					rptImsAppServDTO.setBillingDistrictDesc(order.getBillingAddress().getDistDesc());
					rptImsAppServDTO.setBillingAreaCode(order.getBillingAddress().getAreaCd());
				}
		
				logger.debug("section B - service provider");
				serviceProvideList.add(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("serviceProvide01"));
					
				
				rptImsAppServDTO.setServiceProvideDtls(serviceProvideList);
				logger.debug("section C - core service");
				String startTime, endTime, ampm;
				
				rptImsAppServDTO.setPreInstInd(order.getPreInstallInd());
				rptImsAppServDTO.setMobileOfferInd(order.getMobileOfferInd());
		
				logger.debug(">>Total Term=" + order.getWarrPeriod() + " >>Exten Term=" + order.getTermExt());
				if (order.isMonthlyPlan().equalsIgnoreCase("Y")) {
					rptImsAppServDTO.setFixedTerm("");
					rptImsAppServDTO.setExtenTerm("");
					rptImsAppServDTO.setTotalTerm("");
				} else {
					if (order.getTermExt() == null || order.getTermExt() == "" || (Integer.parseInt(order.getTermExt())==0)) {
						rptImsAppServDTO.setFixedTerm(order.getWarrPeriod());
						rptImsAppServDTO.setExtenTerm("");
						rptImsAppServDTO.setTotalTerm("");
					} else {
						totalTerm = Integer.parseInt(order.getWarrPeriod());
						extenTerm = Integer.parseInt(order.getTermExt());
						fixedTerm = totalTerm - extenTerm;
						rptImsAppServDTO.setTotalTerm(order.getWarrPeriod());
						rptImsAppServDTO.setExtenTerm(order.getTermExt());
						rptImsAppServDTO.setFixedTerm(((fixedTerm < 0)?"":fixedTerm+""));
					}
				}
				
				if (order.getAppointment() != null && order.getAppointment().getAppntEndDateDisplay() != null) {					
					String tempTargetInstallDate;
					if(cOrder){
						tempTargetInstallDate=Utility.date2String(order.getAppointment().getAppntStartDate(), "dd/MM/yyyy");
					}else{
						tempTargetInstallDate=Utility.date2String(order.getAppointment().getAppntEndDateDisplay(), "dd/MM/yyyy");
						//order.getAppointment().getAppntStartDate() T+7
						//order.getAppointment().getAppntEndDateDisplay() T+30
					}
					rptImsAppServDTO.setTargetInstallDate(tempTargetInstallDate);
					if (order.isBookingNotAllowed().equalsIgnoreCase("Y")) {
						if(cOrder&&"Y".equalsIgnoreCase(order.getInstallAddress().getAddrInventory().getResourceShortage())){
							rptImsAppServDTO.setTargetInstallTimeSlot("10:00-18:00");
						}
					} else {						
						startTime = Utility.date2String(order.getAppointment().getAppntStartDateDisplay(), "h");
						endTime = Utility.date2String(order.getAppointment().getAppntEndDateDisplay(), "h");
						
						if(Integer.parseInt(Utility.date2String(order.getAppointment().getAppntStartDateDisplay(), "H"))==9 &&
								Integer.parseInt(Utility.date2String(order.getAppointment().getAppntEndDateDisplay(), "H"))==13 ){
							startTime="10";
							endTime="1";								
						}else if(Integer.parseInt(Utility.date2String(order.getAppointment().getAppntStartDateDisplay(), "H"))==9 &&
								Integer.parseInt(Utility.date2String(order.getAppointment().getAppntEndDateDisplay(), "H"))==18 ){
							startTime="10";
							endTime="6";	
						}
						
						if (Integer.parseInt(Utility.date2String(order.getAppointment().getAppntEndDateDisplay(), "H")) < 12) {
							ampm = "am";
						} else {
							ampm = "pm"; 
						}
		//				if("zh_HK".equals(rptImsAppServDTO.getLocale()))//Gary for installation time display
		//					if(Integer.parseInt(Utility.date2String(order.getAppointment().getAppntStartDateDisplay(), "H")) < 12){
		//						if(Integer.parseInt(Utility.date2String(order.getAppointment().getAppntEndDateDisplay(), "H")) < 12)
		//							rptImsAppServDTO.setTargetInstallTimeSlot(am_zh + startTime + "-" + endTime);
		//						else
		//							rptImsAppServDTO.setTargetInstallTimeSlot(am_zh + startTime + "-" + pm_zh +endTime);
		//					}else
		//						rptImsAppServDTO.setTargetInstallTimeSlot(pm_zh + startTime + "-" + endTime);
		//				
		//					//rptImsAppServDTO.setTargetInstallTimeSlot(((Integer.parseInt(Utility.date2String(order.getAppointment().getAppntStartDateDisplay(), "H")) < 12)?am_zh:pm_zh) + startTime + "-" + ((Integer.parseInt(Utility.date2String(order.getAppointment().getAppntEndDateDisplay(), "H")) < 12)?am_zh:pm_zh) + endTime);
		//				else if("en".equals(rptImsAppServDTO.getLocale()))
							rptImsAppServDTO.setTargetInstallTimeSlot(startTime + "-" + endTime + ampm);
					}
				}
				if (order.getTargetCommDate() == null) {
					rptImsAppServDTO.setTargetCommDate(Utility.date2String(order.getAppointment().getAppntEndDateDisplay(), "dd/MM/yyyy"));
					if("Y".equalsIgnoreCase(order.getInstallAddress().getAddrInventory().getResourceShortage())&& !"Y".equalsIgnoreCase(order.getPreInstallInd())){				
						rptImsAppServDTO.setTargetCommDate(Utility.date2String(order.getAppointment().getAppntStartDate(), "dd/MM/yyyy"));
					}
				} else {
					rptImsAppServDTO.setTargetCommDate(Utility.date2String(order.getTargetCommDate(), "dd/MM/yyyy"));
				}
				rptImsAppServDTO.setLoginId(order.getLoginId() + "@netvigator.com");
				rptImsAppServDTO.setBillingEmailAddr(order.getCustomer().getAccount().getEmailAddr());

				if(!cOrder&&"Y".equalsIgnoreCase(order.getInstallAddress().getAddrInventory().getResourceShortage())){
					rptImsAppServDTO.setTargetInstallDate(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("appointmentInsuffResource").getItemHtml());
					rptImsAppServDTO.setTargetInstallTimeSlot(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("appointmentInsuffResource").getItemHtml());
					rptImsAppServDTO.setTargetCommDate(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("appointmentInsuffResource").getItemHtml());
				}
		
				// core service description
				rptImsAppServDTO.setBandwidth(order.getBandwidth() + "M");
		
		
				// section D - optional premium, optional services and values added services
				// in part 2
		
				// section E - gifts and premiums
				// in part 2
		
				logger.debug("section F - charges for core services / optional services / values added services / miscellaneous");
				
		
				RptServiceInfoDTO tempCoreCharge01 = new RptServiceInfoDTO();
				RptServiceInfoDTO tempCoreCharge02A = new RptServiceInfoDTO();
				RptServiceInfoDTO tempCoreCharge02B = new RptServiceInfoDTO();
				RptServiceInfoDTO tempCoreCharge03A = new RptServiceInfoDTO();
				RptServiceInfoDTO tempCoreCharge03AForShowAll = new RptServiceInfoDTO();
				RptServiceInfoDTO tempCoreCharge03C = new RptServiceInfoDTO();
				RptServiceInfoDTO tempCoreCharge03D = new RptServiceInfoDTO();
				RptServiceInfoDTO tempCoreCharge03E = new RptServiceInfoDTO();
				RptServiceInfoDTO tempCoreCharge03F = new RptServiceInfoDTO();
				RptServiceInfoDTO tempCoreCharge03G = new RptServiceInfoDTO();
				RptServiceInfoDTO tempCoreCharge04 = new RptServiceInfoDTO();
				RptServiceInfoDTO tempCoreCharge05C = new RptServiceInfoDTO();
				RptServiceInfoDTO tempCoreCharge06 = new RptServiceInfoDTO();
				RptServiceInfoDTO tempCoreCharge07 = new RptServiceInfoDTO();
				RptServiceInfoDTO tempCoreCharge08 = new RptServiceInfoDTO();
				RptServiceInfoDTO tempCoreCharge09 = new RptServiceInfoDTO();
				RptServiceInfoDTO tempCoreCharge10 = new RptServiceInfoDTO();
				RptServiceInfoDTO tempCoreCharge11 = new RptServiceInfoDTO();
				
			
			     if(dbRptstaticWords!=null){//setItemHtml left, setItemHtml2 right
					 tempCoreCharge01.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("coreCharge01"));
					 tempCoreCharge01.setItemHtml2(tempCoreCharge01.getItemHtml());
					 tempCoreCharge01.setItemHtml(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("coreChargeTitle01").getItemHtml());
					 
					 tempCoreCharge02A.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("coreCharge02A"));
					 tempCoreCharge02A.setItemHtml2(tempCoreCharge02A.getItemHtml());
					 tempCoreCharge02A.setItemHtml(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("coreChargeTitle02").getItemHtml());
					 
					 tempCoreCharge02B.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("coreCharge02B"));	
					 tempCoreCharge02B.setItemHtml2(tempCoreCharge02B.getItemHtml());
					 tempCoreCharge02B.setItemHtml(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("coreChargeTitle02").getItemHtml());	
					 
		        	 tempCoreCharge03A.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("coreCharge03A"));
		        	 tempCoreCharge03A.setItemHtml2(tempCoreCharge03A.getItemHtml());
					 tempCoreCharge03A.setItemHtml(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("coreChargeTitle03").getItemHtml());
					 tempCoreCharge03AForShowAll.copy(tempCoreCharge03A);
		        	 
		        	 tempCoreCharge03C.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("coreCharge03C"));
		        	 tempCoreCharge03C.setItemHtml2(tempCoreCharge03C.getItemHtml());
		        	 tempCoreCharge03C.setItemHtml(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("coreChargeTitle03").getItemHtml());
		        	 
		        	 tempCoreCharge03D.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("coreCharge03D"));
		        	 tempCoreCharge03D.setItemHtml2(tempCoreCharge03D.getItemHtml());
		        	 tempCoreCharge03D.setItemHtml(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("coreChargeTitle03").getItemHtml());
		        	 
		        	 tempCoreCharge03E.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("coreCharge03E"));
		        	 tempCoreCharge03E.setItemHtml2(tempCoreCharge03E.getItemHtml());
		        	 tempCoreCharge03E.setItemHtml(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("coreChargeTitle03").getItemHtml());
		        	 
		        	 tempCoreCharge03F.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("coreCharge03F"));
		        	 tempCoreCharge03F.setItemHtml2(tempCoreCharge03F.getItemHtml());
		        	 tempCoreCharge03F.setItemHtml(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("coreChargeTitle03").getItemHtml());

		        	 tempCoreCharge03G.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("coreCharge03G"));
		        	 tempCoreCharge03G.setItemHtml2(tempCoreCharge03G.getItemHtml());
		        	 tempCoreCharge03G.setItemHtml(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("coreChargeTitle03").getItemHtml());
		        	 
		
		        	 tempCoreCharge04.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("coreCharge04"));
		        	 tempCoreCharge04.setItemHtml2(tempCoreCharge04.getItemHtml());
		        	 tempCoreCharge04.setItemHtml(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("coreChargeTitle04").getItemHtml());
		        	 
		        	 tempCoreCharge05C.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("coreCharge05C"));
		        	 tempCoreCharge05C.setItemHtml2(tempCoreCharge05C.getItemHtml());
		        	 tempCoreCharge05C.setItemHtml(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("coreChargeTitle05").getItemHtml());
		        	 
		        	 tempCoreCharge06.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("coreCharge06"));
		        	 tempCoreCharge06.setItemHtml2(tempCoreCharge06.getItemHtml());
		        	 tempCoreCharge06.setItemHtml(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("coreChargeTitle06").getItemHtml());
		        	 
		        	 tempCoreCharge07.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("coreCharge07"));
		        	 tempCoreCharge07.setItemHtml2(tempCoreCharge07.getItemHtml());
					 tempCoreCharge07.setItemHtml(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("coreChargeTitle07").getItemHtml());
					 
		        	 tempCoreCharge08.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("coreCharge08"));	
		        	 tempCoreCharge08.setItemHtml2(tempCoreCharge08.getItemHtml());
					 tempCoreCharge08.setItemHtml(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("coreChargeTitle08").getItemHtml());		
					 
		        	 tempCoreCharge09.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("coreCharge09"));
		        	 
		        	 tempCoreCharge10.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("coreCharge10"));
		        	 tempCoreCharge10.setItemHtml2(tempCoreCharge10.getItemHtml());
		        	 tempCoreCharge10.setItemHtml(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("coreChargeTitle10").getItemHtml());
		        	 
		        	 tempCoreCharge11.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("coreCharge11"));
		        	 tempCoreCharge11.setItemHtml2(tempCoreCharge11.getItemHtml());
		        	 tempCoreCharge11.setItemHtml(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("coreChargeTitle11").getItemHtml());
			     }
				
		
				if(isShowAllForTest){//show all
		
					RptServiceInfoDTO tempCore1 = new RptServiceInfoDTO();
					RptServiceInfoDTO tempCore2 = new RptServiceInfoDTO();
					RptServiceInfoDTO tempCore3 = new RptServiceInfoDTO();
					RptServiceInfoDTO tempCore4 = new RptServiceInfoDTO();
					RptServiceInfoDTO tempCore5 = new RptServiceInfoDTO();
					RptServiceInfoDTO tempCore6 = new RptServiceInfoDTO();
					RptServiceInfoDTO tempCore7 = new RptServiceInfoDTO();
					RptServiceInfoDTO tempCore8 = new RptServiceInfoDTO();
					RptServiceInfoDTO tempCore9 = new RptServiceInfoDTO();
					RptServiceInfoDTO tempCore10 = new RptServiceInfoDTO();
					RptServiceInfoDTO tempCore11 = new RptServiceInfoDTO();
					RptServiceInfoDTO tempCore12 = new RptServiceInfoDTO();
					RptServiceInfoDTO tempCore13 = new RptServiceInfoDTO();
					RptServiceInfoDTO tempCore14 = new RptServiceInfoDTO();
					RptServiceInfoDTO tempCore15 = new RptServiceInfoDTO();
					RptServiceInfoDTO lineBreak = new RptServiceInfoDTO();
					
		
					tempCore5.setItemHtml("*** 1. Pre-installation ***");
					coreChrgList.add(tempCore5);
					// for pre-installation option only
					logger.debug("tmpServiceInfo.getItemHtml2():"+tempCoreCharge01.getItemHtml2());
				     if(dbRptstaticWords!=null){
				    	 tempCoreCharge01.setItemHtml2(tempCoreCharge01.getItemHtml2().replace("${0}", "$"+order.getPrepayAmt()));
				     }
					logger.debug("tmpServiceInfo.getItemHtml2():"+tempCoreCharge01.getItemHtml2());
					tempCoreCharge01.setItemHtml2(tempCoreCharge01.getItemHtml2().replace("$", "HK$"));
					coreChrgList.add(tempCoreCharge01);
					tempCore13.setItemHtml("  *** 1. end***");
					coreChrgList.add(tempCore13);
					coreChrgList.add(lineBreak);
					
					
					tempCore6.setItemHtml("*** 2a. Non Monthly Plan without term extension ***");
					coreChrgList.add(tempCore6);
					coreChrgList.add(tempCoreCharge02B);
					tempCore8.setItemHtml("  *** 2a. end***");
					coreChrgList.add(tempCore8);
					coreChrgList.add(lineBreak);
					
					
					
					
					tempCore7.setItemHtml("  *** 2b.  Non Monthly Plan with term extension ***");
					coreChrgList.add(tempCore7);
					coreChrgList.add(tempCoreCharge02A);
					tempCore9.setItemHtml("  *** 2b. end***");
					coreChrgList.add(tempCore9);
					coreChrgList.add(lineBreak);
					
					
					
					
					tempCore1.setItemHtml("  ***  3a.One Time Install Charge waived  ***");
					coreChrgList.add(tempCore1);
			        tempCoreCharge03C.setItemHtml2("HK$"+Integer.valueOf(order.getOtInstallChrg())*-1+" "+tempCoreCharge03C.getItemHtml2());
			        coreChrgList.add(tempCoreCharge03C); 
					tempCore10.setItemHtml("  ***  3a.end  ***");
					coreChrgList.add(tempCore10);
					coreChrgList.add(lineBreak);
					
					
					
		
					tempCore2.setItemHtml("  ***  3b. not 3a, Installment ***");
					coreChrgList.add(tempCore2);
					if(order.getOtInstallChrg()!=null&&Integer.valueOf(order.getOtInstallChrg())==280){
						tempCoreCharge03AForShowAll.setItemHtml2("HK$ 50 X 6 "+tempCoreCharge03AForShowAll.getItemHtml2());
					}else if(order.getOtInstallChrg()!=null&&Integer.valueOf(order.getOtInstallChrg())==380){
						tempCoreCharge03AForShowAll.setItemHtml2("HK$ 65 X 6 "+tempCoreCharge03AForShowAll.getItemHtml2());
					}else {
						tempCoreCharge03AForShowAll.setItemHtml2("HK$ 114 X 6 "+tempCoreCharge03AForShowAll.getItemHtml2());
					}
			         coreChrgList.add(tempCoreCharge03AForShowAll); 
						tempCore11.setItemHtml("  ***  3b.end  ***");
						coreChrgList.add(tempCore11);
						coreChrgList.add(lineBreak);
			         
			         
					tempCore3.setItemHtml("  ***  3c. 3c Will appear if not 3a and not 3b ***");
					coreChrgList.add(tempCore3);
			         tempCoreCharge03A.setItemHtml2("HK$"+Integer.valueOf(order.getOtInstallChrg()));
			         coreChrgList.add(tempCoreCharge03A);  
						tempCore12.setItemHtml("  ***  3c.end  ***");
						coreChrgList.add(tempCore12);
						coreChrgList.add(lineBreak);
			         
			         
		
			         tempCoreCharge03D.setItemHtml("");
			         coreChrgList.add(tempCoreCharge03D);
			         
			         
					tempCore4.setItemHtml("*** 4.The below statment will only appear for Pre-installation ***");
					coreChrgList.add(tempCore4);
					coreChrgList.add(tempCoreCharge04);
					tempCore14.setItemHtml("  ***  4.end  ***");
					coreChrgList.add(tempCore14);
					coreChrgList.add(lineBreak);
					
					
		
				}else{//normal flow
					
		
					
					if (order.isPreinstallation().equalsIgnoreCase("Y")) {	// for pre-installation option only
						logger.debug("tmpServiceInfo.getItemHtml2():"+tempCoreCharge01.getItemHtml2());
					     if(dbRptstaticWords!=null){
					    	 tempCoreCharge01.setItemHtml2(tempCoreCharge01.getItemHtml2().replace("${0}", "$"+order.getPrepayAmt()));
					     }
						logger.debug("tmpServiceInfo.getItemHtml2():"+tempCoreCharge01.getItemHtml2());
						tempCoreCharge01.setItemHtml2(tempCoreCharge01.getItemHtml2().replace("$", "HK$"));
						coreChrgList.add(tempCoreCharge01);
					}
					logger.debug("section F - isMonthlyPlan");
					
					if (!order.isMonthlyPlan().equalsIgnoreCase("Y")) {		// for all except monthly plan
						if (order.getTermExt() == null || order.getTermExt() == "" || (Integer.parseInt(order.getTermExt())==0)) { // without term extension
							coreChrgList.add(tempCoreCharge02B);
						} else { // with term extension
							coreChrgList.add(tempCoreCharge02A);
						}
					}
					
					if("Y".equalsIgnoreCase(order.getPreInstallInd())){
						coreChrgList.add(tempCoreCharge10);
					}
					
					if("Y".equalsIgnoreCase(order.getPreUseInd())){
						coreChrgList.add(tempCoreCharge11);
					}
				
					if(order.getOtInstallChrg()==null||order.getOtInstallChrg().isEmpty()){
						//mass project WAIVED case
						tempCoreCharge03C.setItemHtml2(tempCoreCharge03C.getItemHtml2());
						coreChrgList.add(tempCoreCharge02A);
						coreChrgList.add(tempCoreCharge03C); 
					}else if("0".equalsIgnoreCase(order.getOtInstallChrg())){
						tempCoreCharge03C.setItemHtml2(tempCoreCharge03C.getItemHtml2());
						coreChrgList.add(tempCoreCharge02A);
						coreChrgList.add(tempCoreCharge03C); 
					}else{
						 String compForOtInstallChrg ;
				         if("zh_HK".equals(rptImsAppServDTO.getLocale())){
				        	 compForOtInstallChrg = order.getInstallOTDesc_zh();
				         }else{
				        	 compForOtInstallChrg = order.getInstallOTDesc_en();
				         }
				         if(compForOtInstallChrg==null||compForOtInstallChrg.isEmpty()){
				        	 compForOtInstallChrg = "";
				         }
			     if (Integer.valueOf(order.getOtInstallChrg())<0) {
			        	//logger.debug(Integer.valueOf(order.getOtInstallChrg()));
					coreChrgList.add(tempCoreCharge02A);
					if("B".equalsIgnoreCase(order.getServiceWaiver())){
						tempCoreCharge03C.setItemHtml2("HK$"+Integer.valueOf(order.getOtInstallChrg())*-1+compForOtInstallChrg+" "+tempCoreCharge03E.getItemHtml2());
					}else if("V".equalsIgnoreCase(order.getServiceWaiver())){
						tempCoreCharge03C.setItemHtml2("HK$"+Integer.valueOf(order.getOtInstallChrg())*-1+compForOtInstallChrg+" "+tempCoreCharge03G.getItemHtml2());
					}else if("G".equalsIgnoreCase(order.getServiceWaiver())||"G".equalsIgnoreCase(order.getServiceWaiverNowTVPage())){
						tempCoreCharge03C.setItemHtml2("HK$"+Integer.valueOf(order.getOtInstallChrg())*-1+compForOtInstallChrg+" "+tempCoreCharge03F.getItemHtml2());
					}else{
						tempCoreCharge03C.setItemHtml2("HK$"+Integer.valueOf(order.getOtInstallChrg())*-1+compForOtInstallChrg+" "+tempCoreCharge03C.getItemHtml2());
					}
			         coreChrgList.add(tempCoreCharge03C); 
			         }else if((order.getInstallmentMonth()!=null && order.getInstallmentChrg()!=null)&&
			        		 (Integer.valueOf(order.getInstallmentMonth())>0 && Integer.valueOf(order.getInstallmentChrg())>0)){
				        	logger.debug("order.getInstallmentMonth():"+order.getInstallmentMonth());
				        	logger.debug("order.getInstallmentChrg():"+order.getInstallmentChrg());
					         tempCoreCharge03A.setItemHtml2("HK$"+order.getInstallmentChrg()+" X "+order.getInstallmentMonth()+" "+tempCoreCharge03A.getItemHtml2());
					         coreChrgList.add(tempCoreCharge03A); 
				     }else{ 
			        	//logger.debug(Integer.valueOf(order.getOtInstallChrg()));
					 String otCode = "";
					 if(cOrder&&Integer.valueOf(order.getOtInstallChrg())>0){
						 otCode = " &lt ONE Time Code: "+order.getInstallOtCode()+" / Quantity: "+order.getInstallOtQty()+" >";
					 }
			         tempCoreCharge03A.setItemHtml2("HK$"+Integer.valueOf(order.getOtInstallChrg())+otCode+compForOtInstallChrg);
			         coreChrgList.add(tempCoreCharge03A); 
			        }  
					}
		
		         tempCoreCharge03D.setItemHtml("");
		         coreChrgList.add(tempCoreCharge03D);
		         
					if (order.isPreinstallation().equalsIgnoreCase("Y")) {	// for pre-installation option only
						coreChrgList.add(tempCoreCharge04);
					}
				}
				
				coreChrgList.add(tempCoreCharge05C);
				coreChrgList.add(tempCoreCharge06);
				coreChrgList.add(tempCoreCharge07);
				coreChrgList.add(tempCoreCharge08); 
		        tempCoreCharge09.setItemHtml2(tempCoreCharge09.getItemHtml());
		        tempCoreCharge09.setItemHtml("");
				coreChrgList.add(tempCoreCharge09);
				rptImsAppServDTO.setCoreChrgDtls(coreChrgList);
				
				
		
				logger.debug("section G - bill preferences");
				if (order.getCustomer() != null) {
					if (order.getCustomer().getAccount() != null) {
						rptImsAppServDTO.setBillMedia(order.getCustomer().getAccount().getBillMedia());  // E - Email, P - Paper
					}
				}
				if(dbRptstaticWords!=null){
					rptImsAppServDTO.setBillPreference01(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("billPreferences01").getItemHtml());
					rptImsAppServDTO.setBillPreference02(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("billPreferences02").getItemHtml());
					rptImsAppServDTO.setBillPreference03(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("billPreferences03").getItemHtml()+order.getMrt()
							+dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("billPreferences04").getItemHtml());
					rptImsAppServDTO.setBillPreference04(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("billPreferences05").getItemHtml());
				}
				
				
				
				logger.debug("section H - important information");
				RptServiceInfoDTO	tempImprtInfo00	  = new RptServiceInfoDTO();
				RptServiceInfoDTO	tempImprtInfo01	  = new RptServiceInfoDTO();
				RptServiceInfoDTO	tempImprtInfo02	  = new RptServiceInfoDTO();
				RptServiceInfoDTO	tempImprtInfo03	  = new RptServiceInfoDTO();
				RptServiceInfoDTO	tempImprtInfo04	  = new RptServiceInfoDTO();
				RptServiceInfoDTO	tempImprtInfo05	  = new RptServiceInfoDTO();
				RptServiceInfoDTO	tempImprtInfo06	  = new RptServiceInfoDTO();
				RptServiceInfoDTO	tempImprtInfo07	  = new RptServiceInfoDTO();
				RptServiceInfoDTO	tempImprtInfo08	  = new RptServiceInfoDTO();
				RptServiceInfoDTO	tempImprtInfo09	  = new RptServiceInfoDTO();
				RptServiceInfoDTO	tempImprtInfo10	  = new RptServiceInfoDTO();
				RptServiceInfoDTO	tempImprtInfo11	  = new RptServiceInfoDTO();
				RptServiceInfoDTO	tempImprtInfo12	  = new RptServiceInfoDTO();
				RptServiceInfoDTO	tempImprtInfo13	  = new RptServiceInfoDTO();
				RptServiceInfoDTO	tempImprtInfo14	  = new RptServiceInfoDTO();
				RptServiceInfoDTO	tempImprtInfo15	  = new RptServiceInfoDTO();
				RptServiceInfoDTO	tempImprtInfo16	  = new RptServiceInfoDTO();
				RptServiceInfoDTO	tempImprtInfo17	  = new RptServiceInfoDTO();
				RptServiceInfoDTO	tempImprtInfo18	  = new RptServiceInfoDTO();
				RptServiceInfoDTO	tempImprtInfo19	  = new RptServiceInfoDTO();
				RptServiceInfoDTO	tempImprtInfo20	  = new RptServiceInfoDTO();				
				RptServiceInfoDTO	tempImprtInfo21	  = new RptServiceInfoDTO();
		
			     if(dbRptstaticWords!=null){
			    	 tempImprtInfo00.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("imprtInfo00"));
			    	 tempImprtInfo01.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("imprtInfo01"));
			    	 tempImprtInfo02.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("imprtInfo02"));
			    	 tempImprtInfo03.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("imprtInfo03"));
			    	 tempImprtInfo04.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("imprtInfo04phhos"));
			    	 tempImprtInfo05.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("imprtInfo05"));
			    	 tempImprtInfo06.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("imprtInfo06"));
			    	 tempImprtInfo07.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("imprtInfo07"));
			    	 tempImprtInfo08.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("imprtInfo08"));
			    	 tempImprtInfo09.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("imprtInfo09"));
			    	 tempImprtInfo10.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("imprtInfo10"));
			    	 tempImprtInfo11.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("imprtInfo11"));
			    	 tempImprtInfo12.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("imprtInfo12"));
			    	 tempImprtInfo13.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("imprtInfo13"));
			    	 tempImprtInfo14.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("imprtInfo14"));
			    	 tempImprtInfo15.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("imprtInfo15"));
			    	 tempImprtInfo16.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("imprtInfo16"));
			    	 tempImprtInfo17.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("imprtInfo17"));
			    	 tempImprtInfo18.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("imprtInfo18"));
			    	 tempImprtInfo19.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("imprtInfo19"));
			    	 tempImprtInfo20.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("imprtInfo20"));
			    	 tempImprtInfo21.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("imprtInfo21"));
			     }
				
				imprtInfoList.add(tempImprtInfo00);
				imprtInfoList.add(tempImprtInfo01);
				imprtInfoList.add(tempImprtInfo21);
				imprtInfoList.add(tempImprtInfo02);
				imprtInfoList.add(tempImprtInfo03);
				imprtInfoList.add(tempImprtInfo04);
				imprtInfoList.add(tempImprtInfo05);
				imprtInfoList.add(tempImprtInfo06);
				imprtInfoList.add(tempImprtInfo07);
				imprtInfoList.add(tempImprtInfo08);
				imprtInfoList.add(tempImprtInfo09);
				imprtInfoList.add(tempImprtInfo10);
				imprtInfoList.add(tempImprtInfo11);
				imprtInfoList.add(tempImprtInfo12);
				imprtInfoList.add(tempImprtInfo13);
				imprtInfoList.add(tempImprtInfo14);
				imprtInfoList.add(tempImprtInfo15);
				imprtInfoList.add(tempImprtInfo16);
				imprtInfoList.add(tempImprtInfo17);
				imprtInfoList.add(tempImprtInfo18);
				imprtInfoList.add(tempImprtInfo19);
				imprtInfoList.add(tempImprtInfo20);
				rptImsAppServDTO.setImprtInfoDtls(imprtInfoList);
				
				logger.debug("section I - Cooling-Off period declaration");
				if(dbRptstaticWords!=null){
					if (order.getDsType().equalsIgnoreCase("Door Knocked")){
						if (order.getDsWaiveCoolOff().equalsIgnoreCase("Y")){
							rptImsAppServDTO.setWaivedQC(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("waviedQC").getItemHtml());
						}else {
							rptImsAppServDTO.setWaivedQC(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("notWaiveQC").getItemHtml());
						}
					}
					else rptImsAppServDTO.setWaivedQC("N/A");
				}
				
				logger.debug("section J - glossary and other information");
				RptServiceInfoDTO tempGlossary01  = new RptServiceInfoDTO();
				RptServiceInfoDTO tempGlossary00  = new RptServiceInfoDTO();
				RptServiceInfoDTO tempGlossary02  = new RptServiceInfoDTO();
				RptServiceInfoDTO tempGlossary03  = new RptServiceInfoDTO();
				RptServiceInfoDTO tempGlossary04  = new RptServiceInfoDTO();
				RptServiceInfoDTO tempGlossary05  = new RptServiceInfoDTO();
				RptServiceInfoDTO tempGlossary06  = new RptServiceInfoDTO();
				RptServiceInfoDTO tempGlossary07  = new RptServiceInfoDTO();
				RptServiceInfoDTO tempGlossary08  = new RptServiceInfoDTO();
				RptServiceInfoDTO tempGlossary09  = new RptServiceInfoDTO();
				
		
			     if(dbRptstaticWords!=null){
			    	 tempGlossary01.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("glossary01"));
			    	 tempGlossary02.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("glossary02"));
			    	 tempGlossary00.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("glossary"));
			    	 tempGlossary03.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("glossary03"));//Gary myhkt glossary
			    	 tempGlossary04.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("glossary04"));
			    	 tempGlossary05.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("glossary05"));
			    	 tempGlossary06.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("glossary06"));
			    	 tempGlossary07.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("glossary07"));
			    	 tempGlossary08.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("glossary08"));
			    	 tempGlossary09.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("glossary09"));
			     }
				
		
				if(isShowAllForTest){//show all
					RptServiceInfoDTO tempGlossary01a  = new RptServiceInfoDTO();
					RptServiceInfoDTO tempGlossary01aEnd  = new RptServiceInfoDTO();
					RptServiceInfoDTO tempGlossary02a  = new RptServiceInfoDTO();
					RptServiceInfoDTO tempGlossary02aEnd  = new RptServiceInfoDTO();
					tempGlossary01a.setItemHtml("*** 1.The below Statement will only appear if Not Monthly Plan ***");
					glossaryList.add(tempGlossary01a);
					glossaryList.add(tempGlossary01);
					tempGlossary01aEnd.setItemHtml("*** 1.end ***");
					glossaryList.add(tempGlossary01aEnd);
					
					tempGlossary02a.setItemHtml("*** 2.The below Statement will only appear if term extension >0 ***");
					glossaryList.add(tempGlossary02a);
					glossaryList.add(tempGlossary02);
					tempGlossary02aEnd.setItemHtml("*** 2.end ***");
					glossaryList.add(tempGlossary02aEnd);
		
					
				}else{
					if (!order.isMonthlyPlan().equalsIgnoreCase("Y")) {
						glossaryList.add(tempGlossary01);
					}
					if (order.getTermExt() != null && order.getTermExt() != "" && (Integer.parseInt(order.getTermExt()) > 0)) {
						glossaryList.add(tempGlossary02);
					}
				}
				glossaryList.add(tempGlossary00);//Gary
				glossaryList.add(tempGlossary03);
				glossaryList.add(tempGlossary04);
//				if("Y".equalsIgnoreCase(order.getPreInstallInd())){
					glossaryList.add(tempGlossary08);
					glossaryList.add(tempGlossary09);
//				}else{
//					glossaryList.add(tempGlossary05);
//					glossaryList.add(tempGlossary06);
//				}
				if (order.getTermExt() != null && order.getTermExt() != "" && (Integer.parseInt(order.getTermExt()) > 0)) {
					glossaryList.add(tempGlossary07);
				}
				rptImsAppServDTO.setGlossaryDtls(glossaryList);
		
		
				logger.debug("section K - payment and authorisation");
				Map<String, String> mobileFuturePayment = new HashMap<String, String>();
				try {
					List<Map<String, String>> list = imsReport1Dao.getImsLookUpCode("SB_IMS_INTERCOM");
					if(list!=null){
						for(int p=0; p<list.size(); p++){
							mobileFuturePayment.put(list.get(p).get("code"), list.get(p).get("description"));
						}
					}
					
				} catch (DAOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				rptImsAppServDTO.setMobileFuturePayment("INTERCOM CCC: "+ mobileFuturePayment.get("CCC") + "<br/>END DATE: "+mobileFuturePayment.get("END_DATE"));
				rptImsAppServDTO.setCreditCardDtls(creditCardList);
				if (order.getCustomer() != null) {
					if (order.getCustomer().getAccount() != null) {
						if (order.getCustomer().getAccount().getPayment() != null) {
							if(isShowAllForTest){//Show all cash and credit card
								rptImsAppServDTO.setCreditCardType(order.getCustomer().getAccount().getPayment().getCcType());  // V - Visa, M - Master, AE - AE
								rptImsAppServDTO.setCreditCardHolderName(order.getCustomer().getAccount().getPayment().getCcHoldName());
								rptImsAppServDTO.setCreditCardNum(order.getCustomer().getAccount().getPayment().getCcNum());
								rptImsAppServDTO.setCreditExpiryDate(order.getCustomer().getAccount().getPayment().getCcExpDate());
								logger.debug("getCcExpDate:"+order.getCustomer().getAccount().getPayment().getCcExpDate());
								rptImsAppServDTO.setCreditCardVerifyInd(order.getCustomer().getAccount().getPayment().getCcVerified());
								rptIms3PartyCreditCardDTO.setCreditCardType(order.getCustomer().getAccount().getPayment().getCcType());  // V - Visa, M - Master, AE - AE
								rptIms3PartyCreditCardDTO.setCreditCardHolderName(order.getCustomer().getAccount().getPayment().getCcHoldName());
								rptIms3PartyCreditCardDTO.setCreditCardNum(order.getCustomer().getAccount().getPayment().getCcNum());
								rptIms3PartyCreditCardDTO.setCreditExpiryDate(order.getCustomer().getAccount().getPayment().getCcExpDate());
		//						if (isShowAllForTest||order.getCustomer().getAccount().getPayment().getThirdPartyInd().equalsIgnoreCase("Y")) {
		//							isReq3PartyCreditCardForm = true;
		//						}
		
								//cash1
								String cash1="***  a.CashFsPrepay  ***<br/>";
							     if(dbRptstaticWords!=null){
							    	 String temp = dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("cashPayDesc03").getItemHtml();
							    	 cash1+=temp.replace("${0}", "$"+order.getPrepayAmt());
							     }
							     cash1+="<br/>***  a.end  ***<br/>";
									
		
								//cash2
								String cash2="<br/><br/>***  b.not CashFsPrepay and WaivedPrepay  ***<br/>";
								     if(dbRptstaticWords!=null){
								    	 String temp = dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("cashPayDesc01").getItemHtml();
								    	 cash2+=temp;
								     }
								     cash2+="<br/>*** b.end  ***<br/>";
								     
								     
								//cash3
								String cash3="<br/><br/>***  c.not CashFsPrepay and not WaivedPrepay  ***<br/>";
							     if(dbRptstaticWords!=null){
							    	 String temp = dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("cashPayDesc02").getItemHtml();
							    	 cash3+=temp.replace("${0}", "$"+order.getPrepayAmt());
							     }
							     cash3+="<br/>*** c.end  ***<br/>";
							     
							     rptImsAppServDTO.setCashOption(cash1+cash2+cash3);
							     
							}else{//not show all, normal flow
								rptImsAppServDTO.setPayMethodType(order.getCustomer().getAccount().getPayment().getPayMtdType());	// C - Credit Card, M - Cash
								if (order.getCustomer().getAccount().getPayment().getPayMtdType().equalsIgnoreCase("C")) {
									rptImsAppServDTO.setCreditCardType(order.getCustomer().getAccount().getPayment().getCcType());  // V - Visa, M - Master, AE - AE
									rptImsAppServDTO.setCreditCardHolderName(order.getCustomer().getAccount().getPayment().getCcHoldName());
									rptImsAppServDTO.setCreditCardNum(order.getCustomer().getAccount().getPayment().getCcNum());
									rptImsAppServDTO.setCreditExpiryDate(order.getCustomer().getAccount().getPayment().getCcExpDate());
									logger.debug("getCcExpDate:"+order.getCustomer().getAccount().getPayment().getCcExpDate());
									rptImsAppServDTO.setCreditCardVerifyInd(order.getCustomer().getAccount().getPayment().getCcVerified());
									rptIms3PartyCreditCardDTO.setCreditCardType(order.getCustomer().getAccount().getPayment().getCcType());  // V - Visa, M - Master, AE - AE
									rptIms3PartyCreditCardDTO.setCreditCardHolderName(order.getCustomer().getAccount().getPayment().getCcHoldName());
									rptIms3PartyCreditCardDTO.setCreditCardNum(order.getCustomer().getAccount().getPayment().getCcNum());
									rptIms3PartyCreditCardDTO.setCreditExpiryDate(order.getCustomer().getAccount().getPayment().getCcExpDate());
									if (order.getCustomer().getAccount().getPayment().getThirdPartyInd().equalsIgnoreCase("Y")) {
										isReq3PartyCreditCardForm = true;
									}
								} else {
									if ("Y".equalsIgnoreCase(order.getCashFsPrepay())) {
										String temp;
									     if(dbRptstaticWords!=null){
									    	 if(cOrder)
									    		 temp = dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("cashPayDesc03CC").getItemHtml();									    		
									    	 else
										    	 temp = dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("cashPayDesc03").getItemHtml();
		
									    	 rptImsAppServDTO.setCashOption(temp.replace("${0}", "$"+order.getPrepayAmt()));
									     }
									} else {
										if ("Y".equalsIgnoreCase(order.getWaivedPrepay())) {
										     if(dbRptstaticWords!=null){
										    	 String temp = dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("cashPayDesc01").getItemHtml();										    	
										    	 rptImsAppServDTO.setCashOption(temp);
										     }
										} else {
										     if(dbRptstaticWords!=null){
										    	 String temp = dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("cashPayDesc02").getItemHtml();
										    	 if(cOrder)
											    	 temp += "<br/><br/><span style='font-weight: bold; font-size: 12pt;'>"+"Pre-generated Account No: "+order.getPrimaryAcctNo()+"</span><br/>";
										    	 rptImsAppServDTO.setCashOption(temp.replace("${0}", "$"+order.getPrepayAmt()));
										     }
										}
									}
								}
							}
						}
					}
				}
				
				RptServiceInfoDTO tempPayment01  = new RptServiceInfoDTO();
				RptServiceInfoDTO tempPayment02  = new RptServiceInfoDTO();
				RptServiceInfoDTO custAgree01  = new RptServiceInfoDTO();
				
			     if(dbRptstaticWords!=null){
			    	 tempPayment01.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("creditCard01"));
			    	 tempPayment02.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("creditCard02"));
			    	 custAgree01.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("custAgree01"));
			     }
				
				if (isReq3PartyCreditCardForm) {						
					rptImsAppServDTO.setThirdPartyCC(true);
					creditCardList.add(tempPayment02);
				}else{			
					rptImsAppServDTO.setThirdPartyCC(false);
					creditCardList.add(tempPayment01);
				}
				logger.debug("section M - MY HKT CUSTOMER SERVICE"); //:::
				//Gary added for Printing CS portal in AF Section M 13
				CsPortalAndTheClubAFDisplay(order, rptImsAppServDTO,  dbRptstaticWords);
				//nowid
				setNowIDSection(dbRptstaticWords, rptImsAppServDTO,order);		
						logger.debug("section N - customer's agreement");
				custAgreeList.add(custAgree01);
				rptImsAppServDTO.setCustAgreeDtls(custAgreeList);
		
		
				// Customer Signature
				rptImsAppServDTO.setAppDate(Utility.date2String(order.getAppDate(), "dd/MM/yyyy"));
				rptIms3PartyCreditCardDTO.setAppDate(Utility.date2String(order.getAppDate(), "dd/MM/yyyy"));
		
				String appMethod = LookupTableBean.getInstance().getImsDSAppMethodMap().get(order.getShopCd());
				
				if(appMethod==null||appMethod.isEmpty()){
					appMethod = order.getSourceCd();
				}
				
				// for internal use
				rptImsAppServDTO.setSalesChannel(appMethod);
				rptImsAppServDTO.setCreatedBy(order.getCreateBy());
				rptImsAppServDTO.setSalesCd(order.getSalesCd());
				rptImsAppServDTO.setSalesName(order.getSalesName());
				rptImsAppServDTO.setSalesContactNum(order.getSalesContactNum());
				rptImsAppServDTO.setShopCd(order.getSourceCd());
				if (order.getAppointment() == null) {
					rptImsAppServDTO.setUamsNo("");
				} else {
					rptImsAppServDTO.setUamsNo(order.getAppointment().getSerialNum());
				}
				if (order.getCustomer() != null && order.getCustomer().getCustOptInfo() != null) {
					rptImsAppServDTO.setCustOptOutDirectMailingInd(order.getCustomer().getCustOptInfo().getDirectMailing());
					rptImsAppServDTO.setCustOptOutOutBoundInd(order.getCustomer().getCustOptInfo().getOutbound());
					rptImsAppServDTO.setCustOptOutSmsInd(order.getCustomer().getCustOptInfo().getSms());
					rptImsAppServDTO.setCustOptOutEmailInd(order.getCustomer().getCustOptInfo().getEmail());
					rptImsAppServDTO.setCustOptOutWebBillInd(order.getCustomer().getCustOptInfo().getWebBill());
					rptImsAppServDTO.setCustOptOutNonSalesSmsInd(order.getCustomer().getCustOptInfo().getNonsalesSms());
					rptImsAppServDTO.setCustOptOutInternetInd(order.getCustomer().getCustOptInfo().getInternet());
					logger.debug("DirectMailingInd:"+(order.getCustomer().getCustOptInfo().getDirectMailing()));
					logger.debug("OutBoundInd:"+(order.getCustomer().getCustOptInfo().getOutbound()));
					logger.debug("SmsInd:"+(order.getCustomer().getCustOptInfo().getSms()));
					logger.debug("EmailInd:"+(order.getCustomer().getCustOptInfo().getEmail()));
					logger.debug("WebBillInd:"+(order.getCustomer().getCustOptInfo().getWebBill()));
					logger.debug("NonSalesSmsInd:"+(order.getCustomer().getCustOptInfo().getNonsalesSms()));
					logger.debug("InternetInd:"+(order.getCustomer().getCustOptInfo().getInternet()));
				}
		
				
		
				setPersonalAndClubHkt(dbRptstaticWords, order, 
						rptImsAppServDTO, isShowAllForTest, personalInfoListEC,
						personalInfoListNewCustOptIn,
						personalInfoListNewCustOptOut);
				
			} else if (obj instanceof ImsRptServicePlanDetailDTO) {
				servicePlan = (ImsRptServicePlanDetailDTO) obj;
				logger.debug("part 2 - DB ServicePlan - Section C - Core Service");
				// for internal use
				rptImsAppServDTO.setProgramCode(servicePlan.getCoreServiceDetail().getOfferCd());
				RptServiceDetailDTO coreService;
				// header
				coreService = new RptServiceDetailDTO();
				
				coreService.setItemRecurrentAmt(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("monthlyRate01").getItemHtml());
				coreService.setItemRecurrentAmt2(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("monthlyRate02").getItemHtml());
				coreServiceList.add(coreService);
				
				rptImsAppServDTO.setAppMethod(servicePlan.getAppMethod());
				// Program Item 
				String cOrderOfferCode = "";
				if(cOrder && servicePlan.getCoreServiceDetail().getOfferCd()!=null && 
						!"x".equalsIgnoreCase(servicePlan.getCoreServiceDetail().getOfferCd())){
					cOrderOfferCode = " ( "+servicePlan.getCoreServiceDetail().getOfferCd();
					if(servicePlan.getCoreServiceDetail().getOfferCdOfOther()!=null && servicePlan.getCoreServiceDetail().getOfferCdOfOther()!="null")	
						cOrderOfferCode+=servicePlan.getCoreServiceDetail().getOfferCdOfOther()+" ) ";
					else
						cOrderOfferCode+=" ) ";					
					if("Y".equalsIgnoreCase(order.getPreInstallInd())&&servicePlan.getCoreServiceDetail().getProgItem().getPreInstOfferCd()!=null ){
						cOrderOfferCode+=" ( Pre-installation dummy offer code: "+servicePlan.getCoreServiceDetail().getProgItem().getPreInstOfferCd()+" ) ";
					}			
					if("Y".equalsIgnoreCase(order.getPreUseInd())&&servicePlan.getCoreServiceDetail().getProgItem().getPreUseOfferCd()!=null ){
						cOrderOfferCode+=" ( Pre-use dummy offer code: "+servicePlan.getCoreServiceDetail().getProgItem().getPreUseOfferCd()+" ) ";
					}
					}				
				coreService = new RptServiceDetailDTO();

				coreService.setItemHtml("<b>" + servicePlan.getCoreServiceDetail().getBandwidth_desc() + " " + servicePlan.getCoreServiceDetail().getProgItem().getItemTitle() +cOrderOfferCode+ "</b>");
				
				//Gary
		
				logger.debug("servicePlan.getCoreServiceDetail().getProgItem().getContractPeriod():"+servicePlan.getCoreServiceDetail().getProgItem().getContractPeriod());
				if((servicePlan.getCoreServiceDetail().getProgItem().getContractPeriod())>0){
					coreService.setItemRecurrentAmt(servicePlan.getCoreServiceDetail().getProgItem().getItemMthFix());
					coreService.setItemRecurrentAmt2(servicePlan.getCoreServiceDetail().getProgItem().getItemMth2Mth());
				}else{
					coreService.setItemRecurrentAmt("");
					coreService.setItemRecurrentAmt2(servicePlan.getCoreServiceDetail().getProgItem().getItemMth2Mth());
				}
					//coreService.setItemRecurrentAmt3(servicePlan.getCoreServiceDetail().getProgItem().getItemMthFix()+" "+dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("monthlyRate01").getItemHtml());
		
				logger.debug("bbbb:"+coreService.getItemRecurrentAmt());
				coreServiceList.add(coreService);
				
				String COP_Service_Plan_Remark = "";
				try {
					COP_Service_Plan_Remark = imsReport1Dao.getCOP_Service_Plan_Remark(servicePlan.getCoreServiceDetail().getOfferCd(),rptImsAppServDTO.getLocale());
				} catch (DAOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				if(COP_Service_Plan_Remark!=null && !COP_Service_Plan_Remark.isEmpty()){
					coreService = new RptServiceDetailDTO();
					coreService.setItemHtml("<b>"+COP_Service_Plan_Remark+"</b><br>");
					coreServiceList.add(coreService);
				}	

				// bandwidth
				RptServiceInfoDTO tempBW01  = new RptServiceInfoDTO();
			     if(dbRptstaticWords!=null){
			    	 tempBW01.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("bandwidth01"));
			     }
				coreService = new RptServiceDetailDTO();

				coreService.setItemHtml(tempBW01.getItemHtml()+servicePlan.getCoreServiceDetail().getBandwidth_desc());
				coreServiceList.add(coreService);

				//pre-install wording
				if("Y".equalsIgnoreCase(order.getPreInstallInd())){
					RptServiceInfoDTO tempPreInst  = new RptServiceInfoDTO();
					
					if(dbRptstaticWords!=null){
						tempPreInst.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("preInstWord"));
				     }
					coreService = new RptServiceDetailDTO();
					coreService.setItemHtml(tempPreInst.getItemHtml());
					coreService.setItemRecurrentAmt(null);
					coreService.setItemRecurrentAmt2(null);
					coreServiceList.add(coreService);
				}
				
				//pre-use wording
				if("Y".equalsIgnoreCase(order.getPreUseInd())){
					RptServiceInfoDTO tempPreUse  = new RptServiceInfoDTO();
					
					if(dbRptstaticWords!=null){
						tempPreUse.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("preUseWord"));
				     }
					coreService = new RptServiceDetailDTO();
					coreService.setItemHtml(tempPreUse.getItemHtml());
					coreService.setItemRecurrentAmt(null);
					coreService.setItemRecurrentAmt2(null);
					coreServiceList.add(coreService);
				}
				
				coreService = new RptServiceDetailDTO();
				String itemDesc = "";
				coreService.setItemId(servicePlan.getCoreServiceDetail().getProgItem().getItemID());
				if (!"".equals(coreService.getItemId()) && coreService.getItemId()!=null) {
					coreService.setItemHtml(itemDesc);
					coreService.setItemRecurrentAmt(null);
					coreService.setItemRecurrentAmt2(null);
					coreServiceList.add(coreService);
				}
				
				// BVAS Mandatory Item 
				coreService = new RptServiceDetailDTO();
				
				itemDesc = "";
				for (int j=0; j < servicePlan.getCoreServiceDetail().getBvasMandItemList().size(); j++) {
					if (servicePlan.getCoreServiceDetail().getBvasMandItemList().get(j).getItemTitle() != null &&
						servicePlan.getCoreServiceDetail().getBvasMandItemList().get(j).getItemTitle().length() > 0) {
						if (j > 0) {
							itemDesc += " & ";
						}
						itemDesc += servicePlan.getCoreServiceDetail().getBvasMandItemList().get(j).getItemTitle();
						coreService.setItemId(servicePlan.getCoreServiceDetail().getBvasMandItemList().get(j).getItemID());
						
						
						if(cOrder){//gary
							if(servicePlan.getCoreServiceDetail().getBvasMandItemList().get(j).getOfferCode()!=null&&
								!"".equals(servicePlan.getCoreServiceDetail().getBvasMandItemList().get(j).getOfferCode())){
								itemDesc += "( " + servicePlan.getCoreServiceDetail().getBvasMandItemList().get(j).getOfferCode()+" )";
							}
							if(servicePlan.getCoreServiceDetail().getBvasMandItemList().get(j).getIncentiveCd()!=null){
									itemDesc += "( " + servicePlan.getCoreServiceDetail().getBvasMandItemList().get(j).getIncentiveCd()+" )";
								}
							
							if("Y".equalsIgnoreCase(order.getPreInstallInd())&&servicePlan.getCoreServiceDetail().getBvasMandItemList().get(j).getPreInstOfferCd()!=null ){
								itemDesc+=" ( Pre-installation dummy offer code: "+servicePlan.getCoreServiceDetail().getBvasMandItemList().get(j).getPreInstOfferCd()+" ) ";
							}
							
							if("Y".equalsIgnoreCase(order.getPreUseInd())&&servicePlan.getCoreServiceDetail().getBvasMandItemList().get(j).getPreUseOfferCd()!=null ){
								itemDesc+=" ( Pre-use dummy offer code: "+servicePlan.getCoreServiceDetail().getBvasMandItemList().get(j).getPreUseOfferCd()+" ) ";
							}
							String tempItemId = servicePlan.getCoreServiceDetail().getBvasMandItemList().get(j).getItemID();
							String tempMobileNum = "";
							if(order.getComponents()!=null){
								for(int k = 0; k<order.getComponents().length;k++){
									if(tempItemId!=null && tempItemId.equalsIgnoreCase(order.getComponents()[k].getItemId())){
										logger.debug("Section C Non-Mandatory abt mobile num, item_id:"+tempItemId +" equal Components id:" +order.getComponents()[k].getItemId());
										if(tempMobileNum.equals("")){
											tempMobileNum = order.getComponents()[k].getAttbValue();
										}else{
											tempMobileNum += tempMobileNum + " ," +order.getComponents()[k].getAttbValue();
										}
									}else{
										logger.debug("Section C Non-Mandatory abt mobile num, item_id:"+tempItemId +" not equal Components id:" +order.getComponents()[k].getItemId());
									}
								}
							}
							if(!tempMobileNum.equals("")){
								itemDesc += " (Mobile No: " + tempMobileNum +") ";
								logger.debug("Section C Non-Mandatory abt mobile num, temp_mobile_num is not null:"+tempMobileNum);
							}else{
								logger.debug("Section C Non-Mandatory abt mobile num, temp_mobile_num is null");
							}
						}
					}
				}
				if (itemDesc.length() > 0) {
					itemDesc = "(+ " + itemDesc + ")";
				}
				
				if (itemDesc.length() > 0) {
					coreService.setItemHtml(itemDesc);
					coreService.setItemRecurrentAmt(null);
					coreService.setItemRecurrentAmt2(null);
					coreServiceList.add(coreService);
				}
		
				logger.debug("AF order.getComponents():"+gson.toJson(order.getComponents()));//Gary
				// BVAS Non-Mandatory Item 				
				for (int j=0; j < servicePlan.getCoreServiceDetail().getBvasNonMItemList().size(); j++) {
					coreService = new RptServiceDetailDTO();
					if (servicePlan.getCoreServiceDetail().getBvasNonMItemList().get(j).getItemTitle() != null &&
						servicePlan.getCoreServiceDetail().getBvasNonMItemList().get(j).getItemTitle().length() > 0) {
						coreService.setItemHtml("+ " + servicePlan.getCoreServiceDetail().getBvasNonMItemList().get(j).getItemTitle());
					} else {
						coreService.setItemHtml("");
					}
					logger.debug(gson.toJson(servicePlan));//Gary
					
					coreService.setItemId(servicePlan.getCoreServiceDetail().getBvasNonMItemList().get(j).getItemID());

					if(cOrder){
						if(servicePlan.getCoreServiceDetail().getBvasNonMItemList().get(j).getOfferCode()!=null&&
							!"".equals(servicePlan.getCoreServiceDetail().getBvasNonMItemList().get(j).getOfferCode())){
							coreService.setItemHtml(coreService.getItemHtml()+"( " + servicePlan.getCoreServiceDetail().getBvasNonMItemList().get(j).getOfferCode()+" )");
						}
						if(servicePlan.getCoreServiceDetail().getBvasNonMItemList().get(j).getIncentiveCd()!=null){
								coreService.setItemHtml(coreService.getItemHtml()+"( " + servicePlan.getCoreServiceDetail().getBvasNonMItemList().get(j).getIncentiveCd()+" )");
							}
						
						if("Y".equalsIgnoreCase(order.getPreInstallInd())&&servicePlan.getCoreServiceDetail().getBvasNonMItemList().get(j).getPreInstOfferCd()!=null ){
							coreService.setItemHtml(coreService.getItemHtml()+" ( Pre-installation dummy offer code: "+servicePlan.getCoreServiceDetail().getBvasNonMItemList().get(j).getPreInstOfferCd()+" ) ");
						}
						
						if("Y".equalsIgnoreCase(order.getPreUseInd())&&servicePlan.getCoreServiceDetail().getBvasNonMItemList().get(j).getPreUseOfferCd()!=null ){
							coreService.setItemHtml(coreService.getItemHtml()+" ( Pre-use dummy offer code: "+servicePlan.getCoreServiceDetail().getBvasNonMItemList().get(j).getPreUseOfferCd()+" ) ");
						}
						String tempItemId = servicePlan.getCoreServiceDetail().getBvasNonMItemList().get(j).getItemID();
						String tempMobileNum = "";
						if(order.getComponents()!=null){
							for(int k = 0; k<order.getComponents().length;k++){
								if(tempItemId!=null && tempItemId.equalsIgnoreCase(order.getComponents()[k].getItemId())){
									logger.debug("Section C Non-Mandatory abt mobile num, item_id:"+tempItemId +" equal Components id:" +order.getComponents()[k].getItemId());
									if(tempMobileNum.equals("")){
										tempMobileNum = order.getComponents()[k].getAttbValue();
									}else{
										tempMobileNum += tempMobileNum + " ," +order.getComponents()[k].getAttbValue();
									}
								}else{
									logger.debug("Section C Non-Mandatory abt mobile num, item_id:"+tempItemId +" not equal Components id:" +order.getComponents()[k].getItemId());
								}
							}
						}
						if(!tempMobileNum.equals("")){
							coreService.setItemHtml(coreService.getItemHtml()+ " (Mobile No: " + tempMobileNum +" ) ");
							logger.debug("Section C Non-Mandatory abt mobile num, temp_mobile_num is not null:"+tempMobileNum);
						}else{
							logger.debug("Section C Non-Mandatory abt mobile num, temp_mobile_num is null");
						}
					}
					logger.debug(coreService.getItemHtml());
					logger.debug("servicePlan.getCoreServiceDetail().getBvasNonMItemList().get(j).getContractPeriod():"+servicePlan.getCoreServiceDetail().getBvasNonMItemList().get(j).getContractPeriod());
					
					if(servicePlan.getCoreServiceDetail().getBvasNonMItemList().get(j).getContractPeriod()>0){
						coreService.setItemRecurrentAmt(servicePlan.getCoreServiceDetail().getBvasNonMItemList().get(j).getItemMthFix());
						coreService.setItemRecurrentAmt2(servicePlan.getCoreServiceDetail().getBvasNonMItemList().get(j).getItemMth2Mth());
					}else{
						coreService.setItemRecurrentAmt("");
						coreService.setItemRecurrentAmt2(servicePlan.getCoreServiceDetail().getBvasNonMItemList().get(j).getItemMth2Mth());
					}
		//			//gary 
		//			if(isCC){
		//				coreService.setItemRecurrentAmt(servicePlan.getCoreServiceDetail().getBvasNonMItemList().get(j).getItemMthFix()+" "+dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("monthlyRate01").getItemHtml());
		//				coreService.setItemRecurrentAmt2(null);
		//			}
					
					
					coreServiceList.add(coreService);
					logger.debug("coreservice print: "+gson.toJson(coreService));
				}
				
				if((servicePlan.getCoreServiceDetail().getProgItem().getContractPeriod())>0){
					RptServiceInfoDTO sectionCMonthlyRateIsForRef  = new RptServiceInfoDTO();
				     if(dbRptstaticWords!=null){
				    	 sectionCMonthlyRateIsForRef.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("monthlyRateWord01"));
				     }
					coreService = new RptServiceDetailDTO();
					coreService.setItemRecurrentAmt2(sectionCMonthlyRateIsForRef.getItemHtml());
					coreServiceList.add(coreService);
				}else{
					RptServiceInfoDTO sectionCMonthlyRateIsForRef2  = new RptServiceInfoDTO();
					RptServiceInfoDTO sectionCMonthlyRateIsForRef3  = new RptServiceInfoDTO();
				     if(dbRptstaticWords!=null){
				    	 sectionCMonthlyRateIsForRef2.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("monthlyRateWord02"));
				    	 sectionCMonthlyRateIsForRef3.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("monthlyRateWord03"));
				     }
					coreService = new RptServiceDetailDTO();
					coreService.setItemRecurrentAmt(sectionCMonthlyRateIsForRef2.getItemHtml());
					coreService.setItemRecurrentAmt2(sectionCMonthlyRateIsForRef3.getItemHtml());
					coreServiceList.add(coreService);
				}
		
				setMrtItemDesc(servicePlan, coreServiceList, cOrder);
						
				rptImsAppServDTO.setCoreServDtls(coreServiceList);
				logger.debug(" coreServiceList:");
//				for (int i1=0;i1<coreServiceList.size();i1++)
//					logger.trace(i1+": "+coreServiceList.get(i1).getItemHtml()+": "+coreServiceList.get(i1).getItemHtml2());
				
				
		
				logger.debug("part 2 - DB ServicePlan - Section D - Optional Service");
				RptServiceDetailDTO optService;
		
				// Optional Premium
				if (servicePlan.getOptServiceDetail().getOptPremItemList().size() > 0) {
					optService = new RptServiceDetailDTO();
		
					RptServiceInfoDTO termDesc  = new RptServiceInfoDTO();
				     if(dbRptstaticWords!=null){
				    	 termDesc.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("termDesc02"));
				     }
					optService.setItemHtml(termDesc.getItemHtml());
					printRptServiceDetailDTO(optService);
					vasAndOptList.add(optService);
				}
		
				for (int j=0; j < servicePlan.getOptServiceDetail().getOptPremItemList().size(); j++) {
					optService = new RptServiceDetailDTO();
					if(cOrder){
						cOrderOfferCode = "";
						if(servicePlan.getOptServiceDetail().getOptPremItemList().get(j).getOfferCode()!=null && 
								!"x".equalsIgnoreCase(servicePlan.getOptServiceDetail().getOptPremItemList().get(j).getOfferCode())){
							cOrderOfferCode = " ( "+servicePlan.getOptServiceDetail().getOptPremItemList().get(j).getOfferCode()+" ) ";
						}
						
						if(servicePlan.getOptServiceDetail().getOptPremItemList().get(j).getIncentiveCd()!=null && 
								!"x".equalsIgnoreCase(servicePlan.getOptServiceDetail().getOptPremItemList().get(j).getIncentiveCd())){
							cOrderOfferCode += " ( "+servicePlan.getOptServiceDetail().getOptPremItemList().get(j).getIncentiveCd()+" ) ";
						}
						
						if("Y".equalsIgnoreCase(order.getPreInstallInd())&&servicePlan.getOptServiceDetail().getOptPremItemList().get(j).getPreInstOfferCd()!=null ){
							cOrderOfferCode+=" ( Pre-installation dummy offer code: "+servicePlan.getOptServiceDetail().getOptPremItemList().get(j).getPreInstOfferCd()+" ) ";
						}
						
						if("Y".equalsIgnoreCase(order.getPreUseInd())&&servicePlan.getOptServiceDetail().getOptPremItemList().get(j).getPreInstOfferCd()!=null ){
							cOrderOfferCode+=" ( Pre-use dummy offer code: "+servicePlan.getOptServiceDetail().getOptPremItemList().get(j).getPreInstOfferCd()+" ) ";
						}
					}
					optService.setItemId(servicePlan.getOptServiceDetail().getOptPremItemList().get(j).getItemID());
					optService.setItemHtml(servicePlan.getOptServiceDetail().getOptPremItemList().get(j).getItemTitle()+cOrderOfferCode);
					
		//			logger.debug("opt 1111:"+servicePlan.getOptServiceDetail().getOptPremItemList().get(j).getItemMthFix());
		//			logger.debug("opt 2222:"+servicePlan.getOptServiceDetail().getOptPremItemList().get(j).getItemMth2Mth());
		
		//			if(servicePlan.getOptServiceDetail().getOptPremItemList().get(j).getContractPeriod()>0)
						optService.setItemRecurrentAmt(servicePlan.getOptServiceDetail().getOptPremItemList().get(j).getItemMthFix()+" "+dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("monthlyRate01").getItemHtml());
		//			else
		//				optService.setItemRecurrentAmt(servicePlan.getOptServiceDetail().getOptPremItemList().get(j).getItemMth2Mth()+" "+dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("monthlyRate02").getItemHtml());
					//optService.setItemRecurrentAmt3(servicePlan.getOptServiceDetail().getOptPremItemList().get(j).getItemMthFix());//Gary DDD
					
					printRptServiceDetailDTO(optService);
					vasAndOptList.add(optService);
				}
				
				// Optional Service (WL_BB, ANTI_VIR)
				if (servicePlan.getOptServiceDetail().getOptServItemList().size() > 0) {
		//			optService = new RptServiceDetailDTO();
		//			optService.setItemHtml("<br />");
		//			printRptServiceDetailDTO(optService);
		//			vasAndOptList.add(optService);
					
					optService = new RptServiceDetailDTO();
					RptServiceInfoDTO termDesc = new RptServiceInfoDTO();							
				     if(dbRptstaticWords!=null){
				    	 termDesc.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("termDesc03"));				     
				     }
				   
					optService.setItemHtml(termDesc.getItemHtml());
					printRptServiceDetailDTO(optService);
					vasAndOptList.add(optService);
				}
		
				for (int j=0; j < servicePlan.getOptServiceDetail().getOptServItemList().size(); j++) {
					logger.debug("i am now looping getOptServItemList");
					optService = new RptServiceDetailDTO();
					if(cOrder){
						cOrderOfferCode = "";
						if(servicePlan.getOptServiceDetail().getOptServItemList().get(j).getOfferCode()!=null && 
								!"x".equalsIgnoreCase(servicePlan.getOptServiceDetail().getOptServItemList().get(j).getOfferCode())){
							cOrderOfferCode = " ( "+servicePlan.getOptServiceDetail().getOptServItemList().get(j).getOfferCode()+" ) ";
						}
						
						if(servicePlan.getOptServiceDetail().getOptServItemList().get(j).getIncentiveCd()!=null && 
								!"x".equalsIgnoreCase(servicePlan.getOptServiceDetail().getOptServItemList().get(j).getIncentiveCd())){
							cOrderOfferCode += " ( "+servicePlan.getOptServiceDetail().getOptServItemList().get(j).getIncentiveCd()+" ) ";
						}
						
						if("Y".equalsIgnoreCase(order.getPreInstallInd())&&servicePlan.getOptServiceDetail().getOptServItemList().get(j).getPreInstOfferCd()!=null ){
							cOrderOfferCode+=" ( Pre-installation dummy offer code: "+servicePlan.getOptServiceDetail().getOptServItemList().get(j).getPreInstOfferCd()+" ) ";
						}
						
						if("Y".equalsIgnoreCase(order.getPreUseInd())&&servicePlan.getOptServiceDetail().getOptServItemList().get(j).getPreInstOfferCd()!=null ){
							cOrderOfferCode+=" ( Pre-use dummy offer code: "+servicePlan.getOptServiceDetail().getOptServItemList().get(j).getPreInstOfferCd()+" ) ";
						}
						
						if(servicePlan.getOptServiceDetail().getOptServItemList().get(j).getVasDummyGiftDesc()!=null){
							cOrderOfferCode+=" ( VAS link dummy gift : "+servicePlan.getOptServiceDetail().getOptServItemList().get(j).getVasDummyGiftDesc()+" ) ";
						}
					}
					//Gary cOrderMobileNum
				  	 String cOrderMobileNum = "";
						if(cOrder && servicePlan.getOptServiceDetail().getOptServItemList().get(j).getMobileNum()!=null && 
								!"x".equalsIgnoreCase(servicePlan.getOptServiceDetail().getOptServItemList().get(j).getMobileNum())){
							cOrderMobileNum = " ( "+servicePlan.getOptServiceDetail().getOptServItemList().get(j).getMobileNum()+" ) ";								
						}
						logger.debug("cOrderMobileNum"+cOrderMobileNum);
				     
					optService.setItemHtml(servicePlan.getOptServiceDetail().getOptServItemList().get(j).getItemTitle()+cOrderOfferCode+cOrderMobileNum);
					optService.setItemId(servicePlan.getOptServiceDetail().getOptServItemList().get(j).getItemID());
					
					if(servicePlan.getOptServiceDetail().getOptServItemList().get(j).getContractPeriod()>0)
						optService.setItemRecurrentAmt(servicePlan.getOptServiceDetail().getOptServItemList().get(j).getItemMthFix()+" "+dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("monthlyRate01").getItemHtml());
					else
						optService.setItemRecurrentAmt(servicePlan.getOptServiceDetail().getOptServItemList().get(j).getItemMth2Mth()+" "+dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("monthlyRate02").getItemHtml());
					//optService.setItemRecurrentAmt3(servicePlan.getOptServiceDetail().getOptServItemList().get(j).getItemMthFix());//Gary ddd
					printRptServiceDetailDTO(optService);
					vasAndOptList.add(optService);
				}
				
				// Gift
				for (int k=0; k < servicePlan.getGiftList().size(); k++) {
					logger.debug("i am now looping gift list");
					optService = new RptServiceDetailDTO();
//					if(cOrder){
//						cOrderOfferCode = "";
//						if(servicePlan.getOptServiceDetail().getOptServItemList().get(j).getOfferCode()!=null && 
//								!"x".equalsIgnoreCase(servicePlan.getOptServiceDetail().getOptServItemList().get(j).getOfferCode())){
//							cOrderOfferCode = " ( "+servicePlan.getOptServiceDetail().getOptServItemList().get(j).getOfferCode()+" ) ";
//						}
//						
//						if(servicePlan.getOptServiceDetail().getOptServItemList().get(j).getIncentiveCd()!=null && 
//								!"x".equalsIgnoreCase(servicePlan.getOptServiceDetail().getOptServItemList().get(j).getIncentiveCd())){
//							cOrderOfferCode += " ( "+servicePlan.getOptServiceDetail().getOptServItemList().get(j).getIncentiveCd()+" ) ";
//						}
//					}
					
					String giftInputAttb = "";
					
					if(servicePlan.getGiftList().get(k).getGiftAttbList()!=null&&order.getComponents()!=null){
						for(AttbDTO dto:servicePlan.getGiftList().get(k).getGiftAttbList()){
							if("Y".equalsIgnoreCase(dto.getVisualInd())||cOrder){
								for(int m = 0; m<order.getComponents().length;m++){
									if(servicePlan.getGiftList().get(k).getItemID()!=null && servicePlan.getGiftList().get(k).getItemID().equalsIgnoreCase(order.getComponents()[m].getItemId())
											&&dto.getAttbId().equalsIgnoreCase(order.getComponents()[m].getAttbId())){
										if(giftInputAttb.equals("")){
											giftInputAttb = "<br/>("+dto.getAttbDesc()+": "+order.getComponents()[m].getAttbValue();
										}else{
											giftInputAttb += giftInputAttb + "<br/>"+dto.getAttbDesc()+": "+order.getComponents()[m].getAttbValue();
										}
									}
								}
							}
						}
					}
					
					if(!giftInputAttb.equals(""))
						giftInputAttb = giftInputAttb+")";
					
					String dediVASCode = "";
					
					if(cOrder){
						if(servicePlan.getGiftList().get(k).getDediVASList()!=null&&servicePlan.getGiftList().get(k).getDediVASList().size()>0){
							for(ImsRptBasketItemDTO dediVAS:servicePlan.getGiftList().get(k).getDediVASList()){
								dediVASCode += "<br/>(Auto tie VAS : ( " + dediVAS.getOfferCode() + " ) ";
								if(dediVAS.getIncentiveCd()!=null){
									dediVASCode += " ( "+dediVAS.getIncentiveCd()+" ) ";
								}
								if("Y".equalsIgnoreCase(order.getPreInstallInd())&&dediVAS.getPreInstOfferCd()!=null){
									dediVASCode += " ( Pre-installation dummy offer code: "+dediVAS.getPreInstOfferCd()+" ) ";
								}
								dediVASCode += ")";
							}
							dediVASCode += "<br/>";
						}
					}
				     
					optService.setItemHtml(servicePlan.getGiftList().get(k).getGiftTitle()+giftInputAttb+dediVASCode);
					optService.setItemRecurrentAmt("");
//					optService.setItemRecurrentAmt(servicePlan.getGiftList().get(i)+" "+dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("monthlyRate01").getItemHtml());
					
//					if(servicePlan.getOptServiceDetail().getOptServItemList().get(j).getContractPeriod()>0)
//						optService.setItemRecurrentAmt(servicePlan.getOptServiceDetail().getOptServItemList().get(j).getItemMthFix()+" "+dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("monthlyRate01").getItemHtml());
//					else
//						optService.setItemRecurrentAmt(servicePlan.getOptServiceDetail().getOptServItemList().get(j).getItemMth2Mth()+" "+dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("monthlyRate02").getItemHtml());
					printRptServiceDetailDTO(optService);
					vasAndOptList.add(optService);
				}
				
				rptImsAppServDTO.setNowTVPurchased("N");
				logger.debug("Now TV Bundle");
				// Now TV Bundle
					 //Now TV Bundle //Gary Newpricing
				if (order.getTvPriceInd() != null && "Y".equalsIgnoreCase(order.getTvPriceInd())) {
					if(servicePlan.getNtvServiceDetail().getNewTVPricingItemList().getFtaCampaign().isSelected()){
					logger.debug("New Pricing");
					
					rptImsAppServDTO.setNowTVPurchased("Y");
					// NTV_FREE heading
					if (servicePlan.getNtvServiceDetail().getNtvFreeItemList().size() > 0) {
			//			optService = new RptServiceDetailDTO();
			//			optService.setItemHtml("<br />");
			//			printRptServiceDetailDTO(optService);
			//			vasAndOptList.add(optService);
						optService = new RptServiceDetailDTO();
						RptServiceInfoDTO termDesc = new RptServiceInfoDTO();
					
					     if(dbRptstaticWords!=null){
					    	 termDesc.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("termDesc04"));
					     }
					     cOrderOfferCode="";
						if(cOrder){
							if(servicePlan.getNtvServiceDetail().getNtvFreeItemList().get(0).getOfferCode()!=null ){
								cOrderOfferCode = " ( "+servicePlan.getNtvServiceDetail().getNtvFreeItemList().get(0).getOfferCode()+" )";
							}
							
							if(servicePlan.getNtvServiceDetail().getNtvFreeItemList().get(0).getIncentiveCd()!=null && 
									!"x".equalsIgnoreCase(servicePlan.getNtvServiceDetail().getNtvFreeItemList().get(0).getIncentiveCd())){
								cOrderOfferCode += " ( "+servicePlan.getNtvServiceDetail().getNtvFreeItemList().get(0).getIncentiveCd()+" ) ";
							}
						}
						optService.setItemHtml(termDesc.getItemHtml()+cOrderOfferCode);
			//			if(servicePlan.getNtvServiceDetail().getNtvFreeItemList().get(0).getContractPeriod()>0)
							optService.setItemRecurrentAmt(servicePlan.getNtvServiceDetail().getNtvFreeItemList().get(0).getItemMthFix().replaceAll(((char)10)+"", "<br />")
							+" "+dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("monthlyRate01").getItemHtml());
			//			else
			//				optService.setItemRecurrentAmt(servicePlan.getNtvServiceDetail().getNtvFreeItemList().get(0).getItemMth2Mth()+" "+dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("monthlyRate02").getItemHtml());
			//				logger.debug("1111:"+servicePlan.getNtvServiceDetail().getNtvFreeItemList().get(0).getItemMthFix().replaceAll(((char)10)+"", "<br />"));
			//				logger.debug("2222:"+servicePlan.getNtvServiceDetail().getNtvFreeItemList().get(0).getItemMth2Mth());
			////				optService.setItemRecurrentAmt3(servicePlan.getNtvServiceDetail().getNtvFreeItemList().get(0).getItemMthFix().replaceAll(((char)10)+"", "<br />"));
			//				optService.setItemRecurrentAmt2(null);
			//			}
						printRptServiceDetailDTO(optService);
						vasAndOptList.add(optService);
					}
					
					NowTVAddUI  newPricingItem = servicePlan.getNtvServiceDetail().getNewTVPricingItemList();
//					logger.debug("New TV Pricing: " + gson.toJson(newPricingItem));
					
					optService = new RptServiceDetailDTO();
					if(cOrder){
					optService.setItemHtml("<b>TV Pack Pricing Indicator:</b> "+order.getTvPriceInd()+"<br/>");
					vasAndOptList.add(optService);

					optService = new RptServiceDetailDTO();	
					if(dbRptstaticWords!=null){
						optService.setItemHtml(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("termDesc04").getItemHtml()+"<br/>");
					}
					vasAndOptList.add(optService);

					}
					
					if(cOrder&&order.getDecodeType()!=null&&!order.getDecodeType().equals("")){
						optService = new RptServiceDetailDTO();
						optService.setItemHtml("Decoder Type:"+order.getDecodeType());
						optService.setItemRecurrentAmt(null);
						optService.setItemRecurrentAmt2(null);
						vasAndOptList.add(optService);
					}
					
					String topUpHeading="<b>"+dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("termDesc10").getItemHtml()+"</b><br/>";
					List<String> packTitles= new ArrayList<String>();
					packTitles.add(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("termDesc07").getItemHtml());
					packTitles.add(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("termDesc08").getItemHtml());
					packTitles.add(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("termDesc09").getItemHtml());
					
					this.getDisplayNowTvAddReport(vasAndOptList, newPricingItem, cOrder, topUpHeading, isPT, dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("newPricingDot").getItemHtml(),packTitles,order,dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("fRecDesc").getItemHtml());
					//logger.debug("New TV Pricing: " + gson.toJson(vasAndOptList));//test********
					

				}}else{
						logger.debug("Non-New Pricing");
				
				// NTV_FREE heading
				if (servicePlan.getNtvServiceDetail().getNtvFreeItemList().size() > 0) {
		//			optService = new RptServiceDetailDTO();
		//			optService.setItemHtml("<br />");
		//			printRptServiceDetailDTO(optService);
		//			vasAndOptList.add(optService);
					rptImsAppServDTO.setNowTVPurchased("Y");
					optService = new RptServiceDetailDTO();
					RptServiceInfoDTO termDesc = new RptServiceInfoDTO();
				     if(dbRptstaticWords!=null){
				    	 termDesc.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("termDesc04"));
				     }
				     cOrderOfferCode="";
					if(cOrder){
						if(servicePlan.getNtvServiceDetail().getNtvFreeItemList().get(0).getOfferCode()!=null ){
							cOrderOfferCode = " ( "+servicePlan.getNtvServiceDetail().getNtvFreeItemList().get(0).getOfferCode()+" )";
						}
						
						if(servicePlan.getNtvServiceDetail().getNtvFreeItemList().get(0).getIncentiveCd()!=null && 
								!"x".equalsIgnoreCase(servicePlan.getNtvServiceDetail().getNtvFreeItemList().get(0).getIncentiveCd())){
							cOrderOfferCode += " ( "+servicePlan.getNtvServiceDetail().getNtvFreeItemList().get(0).getIncentiveCd()+" ) ";
						}
					}
					optService.setItemHtml(termDesc.getItemHtml()+cOrderOfferCode);
		//			if(servicePlan.getNtvServiceDetail().getNtvFreeItemList().get(0).getContractPeriod()>0)
						optService.setItemRecurrentAmt(servicePlan.getNtvServiceDetail().getNtvFreeItemList().get(0).getItemMthFix().replaceAll(((char)10)+"", "<br />")
						+" "+dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("monthlyRate01").getItemHtml());
		//			else
		//				optService.setItemRecurrentAmt(servicePlan.getNtvServiceDetail().getNtvFreeItemList().get(0).getItemMth2Mth()+" "+dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("monthlyRate02").getItemHtml());
		//				logger.debug("1111:"+servicePlan.getNtvServiceDetail().getNtvFreeItemList().get(0).getItemMthFix().replaceAll(((char)10)+"", "<br />"));
		//				logger.debug("2222:"+servicePlan.getNtvServiceDetail().getNtvFreeItemList().get(0).getItemMth2Mth());
		////				optService.setItemRecurrentAmt3(servicePlan.getNtvServiceDetail().getNtvFreeItemList().get(0).getItemMthFix().replaceAll(((char)10)+"", "<br />"));
		//				optService.setItemRecurrentAmt2(null);
		//			}
					printRptServiceDetailDTO(optService);
					vasAndOptList.add(optService);
				}
		
				if(cOrder&&order.getDecodeType()!=null&&!order.getDecodeType().equals("")){
					optService = new RptServiceDetailDTO();
					optService.setItemHtml("Decoder Type:"+order.getDecodeType());
					optService.setItemRecurrentAmt(null);
					optService.setItemRecurrentAmt2(null);
					vasAndOptList.add(optService);
				}
		
				logger.debug("NTV_FREE StarterPack Channel");
				if (servicePlan.getNtvServiceDetail().getNtvFreeSPChannelList().size() > 0) {
					optService = new RptServiceDetailDTO();
					optService.setItemHtml("<b>" + servicePlan.getNtvServiceDetail().getNtvFreeSPChannelList().get(0).getChannelGroupDesc() + "</b>");
					printRptServiceDetailDTO(optService);
					vasAndOptList.add(optService);
		
					for (int k=0; k < servicePlan.getNtvServiceDetail().getNtvFreeSPChannelList().size(); k++) {
						optService = new RptServiceDetailDTO();
						String temp = servicePlan.getNtvServiceDetail().getNtvFreeSPChannelList().get(0).getChannelDesc().replaceAll(((char)10)+"", "<br />");
						planCd="";
						campignCd="";
						if(cOrder){
							if(servicePlan.getNtvServiceDetail().getNtvFreeSPChannelList().get(0).getPlanCd()!=null ){
								planCd = "( "+servicePlan.getNtvServiceDetail().getNtvFreeSPChannelList().get(0).getPlanCd()+" )";
							}
							if(servicePlan.getNtvServiceDetail().getNtvFreeSPChannelList().get(0).getCampaignCd()!=null ){
								campignCd =  " ( "+servicePlan.getNtvServiceDetail().getNtvFreeSPChannelList().get(0).getCampaignCd() + " )";
							}
							temp+=planCd+campignCd;
						}
						optService.setItemHtml(temp);
						printRptServiceDetailDTO(optService);
						vasAndOptList.add(optService);
					}
				}
		
				logger.debug("NTV_FREE EntertainmentPack Channel");
				if (servicePlan.getNtvServiceDetail().getNtvFreeEPChannelList().size() > 0) {
					optService = new RptServiceDetailDTO();
					optService.setItemHtml("<b>" + servicePlan.getNtvServiceDetail().getNtvFreeEPChannelList().get(0).getChannelGroupDesc() + "</b>");
					printRptServiceDetailDTO(optService);
					vasAndOptList.add(optService);
		
					for (int k=0; k < servicePlan.getNtvServiceDetail().getNtvFreeEPChannelList().size(); k++) {
						optService = new RptServiceDetailDTO();
						String temp = servicePlan.getNtvServiceDetail().getNtvFreeEPChannelList().get(k).getChannelDesc();
						planCd="";
						campignCd="";
						if(cOrder){
							if(servicePlan.getNtvServiceDetail().getNtvFreeEPChannelList().get(k).getPlanCd()!=null ){
								planCd = " ( "+servicePlan.getNtvServiceDetail().getNtvFreeEPChannelList().get(k).getPlanCd()+" )";
							}
							if(servicePlan.getNtvServiceDetail().getNtvFreeEPChannelList().get(k).getCampaignCd()!=null ){
								campignCd =  " ( "+servicePlan.getNtvServiceDetail().getNtvFreeEPChannelList().get(k).getCampaignCd() + " )";
							}
							temp+=planCd+campignCd;
						}
						logger.debug("EntertainmentPack Channel:"+temp);
						optService.setItemHtml(temp.replaceAll(((char)10)+"", "<br />"));
						printRptServiceDetailDTO(optService);
						vasAndOptList.add(optService);
					}
				}
				
				logger.debug("NTV_FREE Free2HD");
				if (servicePlan.getNtvServiceDetail().getNtvFreeHDChannelList().size() > 0) {
					optService = new RptServiceDetailDTO();
					optService.setItemHtml("<b>" + servicePlan.getNtvServiceDetail().getNtvFreeHDChannelList().get(0).getChannelGroupDesc() + "</b>");
					printRptServiceDetailDTO(optService);
					vasAndOptList.add(optService);
		
					String tmpFreeHDItemHtml = "";
					for (int k=0; k < servicePlan.getNtvServiceDetail().getNtvFreeHDChannelList().size(); k++) {
						if (k > 0) {
							if (servicePlan.getNtvServiceDetail().getNtvFreeHDChannelList().get(k).getParentChannelId() == null ||
								servicePlan.getNtvServiceDetail().getNtvFreeHDChannelList().get(k).getParentChannelId().length() == 0) {
								optService = new RptServiceDetailDTO();
								optService.setItemHtml(tmpFreeHDItemHtml.trim() + "<br />");
								printRptServiceDetailDTO(optService);
								vasAndOptList.add(optService);
								tmpFreeHDItemHtml = "";
							}
						}
						
						tmpFreeHDItemHtml +=  " " + servicePlan.getNtvServiceDetail().getNtvFreeHDChannelList().get(k).getChannelDesc().replaceAll(((char)10)+"", "<br />");
						planCd="";
						campignCd="";
						if(cOrder){
							if(servicePlan.getNtvServiceDetail().getNtvFreeHDChannelList().get(k).getPlanCd()!=null ){
								planCd = " ( "+servicePlan.getNtvServiceDetail().getNtvFreeHDChannelList().get(k).getPlanCd()+" )";
							}
							if(servicePlan.getNtvServiceDetail().getNtvFreeHDChannelList().get(k).getCampaignCd()!=null ){
								campignCd =  " ( "+servicePlan.getNtvServiceDetail().getNtvFreeHDChannelList().get(k).getCampaignCd() + " )";
							}
							tmpFreeHDItemHtml+=planCd+campignCd;
						}
					}
					if (tmpFreeHDItemHtml != "") {
						optService = new RptServiceDetailDTO();
						optService.setItemHtml(tmpFreeHDItemHtml.trim());
						logger.debug("tmpFreeHDItemHtml.trim():"+optService.getItemHtml());
						printRptServiceDetailDTO(optService);
						vasAndOptList.add(optService);
					}
				}
				
				
				logger.debug("NTV_PAY, NTV_P_30F6");
				if (servicePlan.getNtvServiceDetail().getNtvPayItemList().size() > 0) {
					
					optService = new RptServiceDetailDTO();
		
					logger.debug("getNtvPayItemList");
					for (int k=0; k < servicePlan.getNtvServiceDetail().getNtvPayItemList().size(); k++) {
						logger.debug("getType:"+servicePlan.getNtvServiceDetail().getNtvPayItemList().get(k).getType());
						if(servicePlan.getNtvServiceDetail().getNtvPayItemList().get(k).getType().indexOf("NTV_P")!=-1 ||
								servicePlan.getNtvServiceDetail().getNtvPayItemList().get(k).getType().indexOf("NTV_SP")!=-1){
							optService = new RptServiceDetailDTO();
							cOrderOfferCode = "";
							
							if(cOrder){
								if(servicePlan.getNtvServiceDetail().getNtvPayItemList().get(k).getOfferCode()!=null ){
									cOrderOfferCode = "( "+servicePlan.getNtvServiceDetail().getNtvPayItemList().get(k).getOfferCode()+" )";
								}
								if(servicePlan.getNtvServiceDetail().getNtvPayItemList().get(k).getIncentiveCd()!=null && 
										!"x".equalsIgnoreCase(servicePlan.getNtvServiceDetail().getNtvPayItemList().get(k).getIncentiveCd())){
									cOrderOfferCode += " ( "+servicePlan.getNtvServiceDetail().getNtvPayItemList().get(k).getIncentiveCd()+" ) ";
								}
							}
							optService.setItemHtml("<b>"+ servicePlan.getNtvServiceDetail().getNtvPayItemList().get(k).getItemTitle() + "</b> "+cOrderOfferCode);
		//					if(servicePlan.getNtvServiceDetail().getNtvPayItemList().get(k).getContractPeriod()>0)
								optService.setItemRecurrentAmt(servicePlan.getNtvServiceDetail().getNtvPayItemList().get(k).getItemMthFix()+" "+dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("monthlyRate01").getItemHtml());
		//					else
		//						optService.setItemRecurrentAmt(servicePlan.getNtvServiceDetail().getNtvPayItemList().get(k).getItemMth2Mth()+" "+dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("monthlyRate02").getItemHtml());
		//					optService.setItemRecurrentAmt3(servicePlan.getNtvServiceDetail().getNtvPayItemList().get(k).getItemMthFix());//Gary DDD
							printRptServiceDetailDTO(optService);
							vasAndOptList.add(optService);
						}
					}
					logger.debug("getNtvPayChannelList");
					String tmpPayItemHtml = "";
					int count=0;boolean addCd=false;//Gary
					for (int k=0; k < servicePlan.getNtvServiceDetail().getNtvPayChannelList().size(); k++) {
						if (k > 0) {
							if (servicePlan.getNtvServiceDetail().getNtvPayChannelList().get(k).getParentChannelId() == null ||
								servicePlan.getNtvServiceDetail().getNtvPayChannelList().get(k).getParentChannelId().length() == 0) {
								optService = new RptServiceDetailDTO();
								optService.setItemHtml(tmpPayItemHtml.trim() + "<br />");
								printRptServiceDetailDTO(optService);
								vasAndOptList.add(optService);
								tmpPayItemHtml = "";
							}
						}
						if (servicePlan.getNtvServiceDetail().getNtvPayChannelList().get(k).getChannelCd() == null || 
							servicePlan.getNtvServiceDetail().getNtvPayChannelList().get(k).getChannelCd().length() == 0) {
							tmpPayItemHtml +=  " " + servicePlan.getNtvServiceDetail().getNtvPayChannelList().get(k).getChannelDesc().replaceAll(((char)10)+"", "<br />");
						} else {
							tmpPayItemHtml +=  " Ch." + servicePlan.getNtvServiceDetail().getNtvPayChannelList().get(k).getChannelCd() + " " 
							+ servicePlan.getNtvServiceDetail().getNtvPayChannelList().get(k).getChannelDesc().replaceAll(((char)10)+"", "<br />");
						}
						planCd="";
						campignCd="";
						
						if(k+1 < servicePlan.getNtvServiceDetail().getNtvPayChannelList().size()&& (servicePlan.getNtvServiceDetail().getNtvPayChannelList().get(k+1).getChannelCd() == null || 
							servicePlan.getNtvServiceDetail().getNtvPayChannelList().get(k+1).getChannelCd().length() == 0)){
							count+=1;
							addCd=false;
						}else{
							addCd=true;
						}
						
		//				if(cOrder){
		//					if(servicePlan.getNtvServiceDetail().getNtvPayChannelList().get(k).getPlanCd()!=null ){
		//						planCd = " ("+servicePlan.getNtvServiceDetail().getNtvPayChannelList().get(k).getPlanCd()+")";
		//					}
		//					if(servicePlan.getNtvServiceDetail().getNtvPayChannelList().get(k).getCampaignCd()!=null ){
		//						campignCd =  " ("+servicePlan.getNtvServiceDetail().getNtvPayChannelList().get(k).getCampaignCd() + ")";
		//					}
		//					tmpPayItemHtml+=planCd+campignCd;
		//				}
						if(cOrder && addCd){
							for(;count>=0;count--){
								if(servicePlan.getNtvServiceDetail().getNtvPayChannelList().get(k-count).getPlanCd()!=null ){
									planCd = "( "+servicePlan.getNtvServiceDetail().getNtvPayChannelList().get(k-count).getPlanCd()+" )";
								}
								if(servicePlan.getNtvServiceDetail().getNtvPayChannelList().get(k-count).getCampaignCd()!=null ){
									campignCd =  "( "+servicePlan.getNtvServiceDetail().getNtvPayChannelList().get(k-count).getCampaignCd() + " )";
								}
								tmpPayItemHtml+=planCd+campignCd;
							}
							addCd=false;
							count=0;
							
						}
					}
					if (tmpPayItemHtml != "") {
						optService = new RptServiceDetailDTO();
						optService.setItemHtml(tmpPayItemHtml.trim());
						printRptServiceDetailDTO(optService);
						vasAndOptList.add(optService);
					}
		}
			
				}
				logger.debug("getNtvPayItemList");
					for (int k=0; k < servicePlan.getNtvServiceDetail().getNtvPayItemList().size(); k++) {
						if(servicePlan.getNtvServiceDetail().getNtvPayItemList().get(k).getType().indexOf("NTV_P")==-1 &&
								servicePlan.getNtvServiceDetail().getNtvPayItemList().get(k).getType().indexOf("NTV_SP")==-1){
							optService = new RptServiceDetailDTO();
							cOrderOfferCode = "";
								
							if(cOrder){
								if(servicePlan.getNtvServiceDetail().getNtvPayItemList().get(k).getOfferCode()!=null ){
									cOrderOfferCode = " ( "+servicePlan.getNtvServiceDetail().getNtvPayItemList().get(k).getOfferCode()+" )";
								}
															
								if(servicePlan.getNtvServiceDetail().getNtvPayItemList().get(k).getIncentiveCd()!=null ){
									cOrderOfferCode += " ( "+servicePlan.getNtvServiceDetail().getNtvPayItemList().get(k).getIncentiveCd()+" )";
								}
							}
							optService.setItemHtml("<b>"+ servicePlan.getNtvServiceDetail().getNtvPayItemList().get(k).getItemTitle() + "</b> "+cOrderOfferCode);
		//					if(servicePlan.getNtvServiceDetail().getNtvPayItemList().get(k).getContractPeriod()>0)
								optService.setItemRecurrentAmt(servicePlan.getNtvServiceDetail().getNtvPayItemList().get(k).getItemMthFix()+" "+dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("monthlyRate01").getItemHtml());
		//					else
								optService.setItemRecurrentAmt2(servicePlan.getNtvServiceDetail().getNtvPayItemList().get(k).getItemMth2Mth()+" "+dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("monthlyRate02").getItemHtml());
		//					optService.setItemRecurrentAmt3(servicePlan.getNtvServiceDetail().getNtvPayItemList().get(k).getItemMthFix());//Gary DDD
		
							printRptServiceDetailDTO(optService);
							vasAndOptList.add(optService);
						}
					}
				logger.debug("NTV_OTHER");
				if (servicePlan.getNtvServiceDetail().getNtvOtherItemList().size() > 0) {
		//			optService = new RptServiceDetailDTO();
		//			optService.setItemHtml("<br />");
		//			printRptServiceDetailDTO(optService);
		//			vasAndOptList.add(optService);
					
					optService = new RptServiceDetailDTO();
					RptServiceInfoDTO termDesc = new RptServiceInfoDTO();
				     if(dbRptstaticWords!=null){
				    	 termDesc.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("termDesc05"));
				     }
					optService.setItemHtml(termDesc.getItemHtml());
					printRptServiceDetailDTO(optService);
					vasAndOptList.add(optService);
		
					for (int k=0; k < servicePlan.getNtvServiceDetail().getNtvOtherItemList().size(); k++) {
						optService = new RptServiceDetailDTO();
						cOrderOfferCode = "";
						
						if(cOrder){
							if(servicePlan.getNtvServiceDetail().getNtvOtherItemList().get(k).getOfferCode()!=null ){
								cOrderOfferCode = " ( "+servicePlan.getNtvServiceDetail().getNtvOtherItemList().get(k).getOfferCode()+" )";
							}
							
							if(servicePlan.getNtvServiceDetail().getNtvOtherItemList().get(k).getIncentiveCd()!=null ){
								cOrderOfferCode += " ( "+servicePlan.getNtvServiceDetail().getNtvOtherItemList().get(k).getIncentiveCd()+" )";
							}
						}
						optService.setItemHtml(servicePlan.getNtvServiceDetail().getNtvOtherItemList().get(k).getItemTitle()+cOrderOfferCode);
//						if(servicePlan.getNtvServiceDetail().getNtvOtherItemList().get(k).getContractPeriod()>0){
							if(servicePlan.getNtvServiceDetail().getNtvOtherItemList().get(k).getItemMthFix()!=null&&!servicePlan.getNtvServiceDetail().getNtvOtherItemList().get(k).getItemMthFix().isEmpty()){
								optService.setItemRecurrentAmt(servicePlan.getNtvServiceDetail().getNtvOtherItemList().get(k).getItemMthFix()+" "+dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("monthlyRate01").getItemHtml());
							}else{
								optService.setItemRecurrentAmt("");
							}
//						}else{
							if(servicePlan.getNtvServiceDetail().getNtvOtherItemList().get(k).getItemMth2Mth()!=null&&!servicePlan.getNtvServiceDetail().getNtvOtherItemList().get(k).getItemMth2Mth().isEmpty()){
								optService.setItemRecurrentAmt2(servicePlan.getNtvServiceDetail().getNtvOtherItemList().get(k).getItemMth2Mth()+" "+dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("monthlyRate02").getItemHtml());
							}else{
								optService.setItemRecurrentAmt2("");
							}
//						}
		//				optService.setItemRecurrentAmt3(servicePlan.getNtvServiceDetail().getNtvOtherItemList().get(k).getItemMthFix());//Gary DDD
						printRptServiceDetailDTO(optService);
						vasAndOptList.add(optService);
					}
				}
		
				logger.debug("New Media Option");
				if (servicePlan.getOptServiceDetail().getMediaItemList().size() > 0) {
		//			optService = new RptServiceDetailDTO();
		//			optService.setItemHtml("<br />");
		//			printRptServiceDetailDTO(optService);
		//			vasAndOptList.add(optService);
					
					optService = new RptServiceDetailDTO();
					RptServiceInfoDTO termDesc = new RptServiceInfoDTO();
				     if(dbRptstaticWords!=null){
				    	 termDesc.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("termDesc06"));
				     }
					optService.setItemHtml(termDesc.getItemHtml());
					printRptServiceDetailDTO(optService);
					vasAndOptList.add(optService);
									
					for (int j=0; j < servicePlan.getOptServiceDetail().getMediaItemList().size(); j++) {
						optService = new RptServiceDetailDTO();
						cOrderOfferCode = "";
						
						if(cOrder){
							
							if(servicePlan.getOptServiceDetail().getMediaItemList().get(j).getOfferCode()!=null && 
									!"x".equalsIgnoreCase(servicePlan.getOptServiceDetail().getMediaItemList().get(j).getOfferCode())){
								cOrderOfferCode = " ( "+servicePlan.getOptServiceDetail().getMediaItemList().get(j).getOfferCode()+" ) ";
							}
							
							if(servicePlan.getOptServiceDetail().getMediaItemList().get(j).getIncentiveCd()!=null && 
									!"x".equalsIgnoreCase(servicePlan.getOptServiceDetail().getMediaItemList().get(j).getIncentiveCd())){
								cOrderOfferCode += " ( "+servicePlan.getOptServiceDetail().getMediaItemList().get(j).getIncentiveCd()+" ) ";
							}
						}
						optService.setItemId(servicePlan.getOptServiceDetail().getMediaItemList().get(j).getItemID());
						optService.setItemHtml(servicePlan.getOptServiceDetail().getMediaItemList().get(j).getItemTitle()+cOrderOfferCode);
						if(servicePlan.getOptServiceDetail().getMediaItemList().get(j).getContractPeriod()>0)
							optService.setItemRecurrentAmt(servicePlan.getOptServiceDetail().getMediaItemList().get(j).getItemMthFix()+" "+dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("monthlyRate01").getItemHtml());
						else
							optService.setItemRecurrentAmt(servicePlan.getOptServiceDetail().getMediaItemList().get(j).getItemMth2Mth()+" "+dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("monthlyRate02").getItemHtml());
		//				optService.setItemRecurrentAmt3(servicePlan.getOptServiceDetail().getMediaItemList().get(j).getItemMthFix());//Gary DDD
						printRptServiceDetailDTO(optService);
						vasAndOptList.add(optService);
					}
				}
				
				logger.debug("backend Vas");
				if (servicePlan.getOptServiceDetail().getBackendVasItemList().size() > 0 && cOrder) {
					
					optService = new RptServiceDetailDTO();
					RptServiceInfoDTO termDesc = new RptServiceInfoDTO();
														
					for (int j=0; j < servicePlan.getOptServiceDetail().getBackendVasItemList().size(); j++) {
						optService = new RptServiceDetailDTO();
						cOrderOfferCode = "";
						
						if(cOrder){
							
							if(servicePlan.getOptServiceDetail().getBackendVasItemList().get(j).getOfferCode()!=null && 
									!"x".equalsIgnoreCase(servicePlan.getOptServiceDetail().getBackendVasItemList().get(j).getOfferCode())){
								cOrderOfferCode = " ( "+servicePlan.getOptServiceDetail().getBackendVasItemList().get(j).getOfferCode()+" ) ";
							}
							
							if(servicePlan.getOptServiceDetail().getBackendVasItemList().get(j).getIncentiveCd()!=null && 
									!"x".equalsIgnoreCase(servicePlan.getOptServiceDetail().getBackendVasItemList().get(j).getIncentiveCd())){
								cOrderOfferCode += " ( "+servicePlan.getOptServiceDetail().getBackendVasItemList().get(j).getIncentiveCd()+" ) ";
							}
						}
						optService.setItemHtml((cOrder?"":servicePlan.getOptServiceDetail().getBackendVasItemList().get(j).getItemTitle())+cOrderOfferCode);
						if(servicePlan.getOptServiceDetail().getBackendVasItemList().get(j).getContractPeriod()>0)
							optService.setItemRecurrentAmt(servicePlan.getOptServiceDetail().getBackendVasItemList().get(j).getItemMthFix()+" "+dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("monthlyRate01").getItemHtml());
						else
							optService.setItemRecurrentAmt(servicePlan.getOptServiceDetail().getBackendVasItemList().get(j).getItemMth2Mth()+" "+dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("monthlyRate02").getItemHtml());
//						optService.setItemRecurrentAmt3(servicePlan.getOptServiceDetail().getMediaItemList().get(j).getItemMthFix());//Gary DDD
						printRptServiceDetailDTO(optService);
						vasAndOptList.add(optService);
						logger.debug(gson.toJson(optService));
					}
				}
				setMrtItemDesc(servicePlan, vasAndOptList, cOrder);
				setImsVasParmItemDesc(servicePlan, vasAndOptList);
				rptImsAppServDTO.setVasAndOptDtls(vasAndOptList);
				
				
		
				logger.debug("part 2 - DB ServicePlan - section K - details of service plan, optional and value added services");
				RptServiceInfoDTO serviceInfo;
				
				RptServiceInfoDTO tempServicePlanI01  = new RptServiceInfoDTO();
				RptServiceInfoDTO tempServicePlanI02  = new RptServiceInfoDTO();
				RptServiceInfoDTO tempServicePlanI03  = new RptServiceInfoDTO();
				RptServiceInfoDTO tempServicePlanI04  = new RptServiceInfoDTO();
				RptServiceInfoDTO tempServicePlanI05  = new RptServiceInfoDTO();
				RptServiceInfoDTO tempServicePlanI06  = new RptServiceInfoDTO();
				RptServiceInfoDTO tempServicePlanI07  = new RptServiceInfoDTO();
				RptServiceInfoDTO tempServicePlanI08  = new RptServiceInfoDTO();
				RptServiceInfoDTO tempServicePlanI09  = new RptServiceInfoDTO();
			     if(dbRptstaticWords!=null){
			    	 tempServicePlanI01.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("termDesc01"));
			    	 tempServicePlanI02.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("servPlan02"));
			    	 tempServicePlanI03.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("servPlan03"));
			    	 tempServicePlanI04.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("servPlan04"));
			    	 tempServicePlanI05.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("servPlan05"));
			    	 tempServicePlanI06.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("servPlan06phhos"));
			    	 tempServicePlanI07.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("servPlan07"));//pre-install t&c
			    	 tempServicePlanI08.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("servPlan08"));//pre-use t&c
			    	 tempServicePlanI09.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("servPlan09"));//pre-inst mesh-router t&c
			     }
				
		
				servPlanList.add(tempServicePlanI01);
				servPlanList.add(tempServicePlanI02);
				servPlanList.add(tempServicePlanI03);
				servPlanList.add(tempServicePlanI04);
				servPlanList.add(tempServicePlanI05);
				servPlanList.add(tempServicePlanI06);
				
				if("Y".equalsIgnoreCase(order.getPreInstallInd())){
					servPlanList.add(tempServicePlanI07);
					if("Y".equalsIgnoreCase(servicePlan.getPreInstRouterPurchasedInd())){
						servPlanList.add(tempServicePlanI09);
					}
				}
				
				if("Y".equalsIgnoreCase(order.getPreUseInd())){
					servPlanList.add(tempServicePlanI08);
				}
		
				
		//		termDescEng01=<b>CORE SERVICES:</b>
		//		termDescEng05=<b>SPECIAL RENTAL EQUIPMENT:</b>
				
				RptServiceInfoDTO tempTermDesc02 = new RptServiceInfoDTO();
				RptServiceInfoDTO tempTermDesc03 = new RptServiceInfoDTO();
				RptServiceInfoDTO tempTermDesc04 = new RptServiceInfoDTO();
				RptServiceInfoDTO tempTermDesc06 = new RptServiceInfoDTO();
			     if(dbRptstaticWords!=null){
			    	 tempTermDesc02.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("termDesc02"));
			    	 tempTermDesc03.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("termDesc03"));
			    	 tempTermDesc04.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("termDesc04"));
			    	 tempTermDesc06.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("termDesc06"));
			     }
					     
					     
				// Program Item
				String tempHtml;
				serviceInfo = new RptServiceInfoDTO();
				serviceInfo.setItemHtml("<b>" + servicePlan.getCoreServiceDetail().getProgItem().getItemTitle() + "</b>");
				servPlanList.add(serviceInfo);
				
				serviceInfo = new RptServiceInfoDTO();
				tempHtml = servicePlan.getCoreServiceDetail().getProgItem().getItemDetails();
				if (tempHtml != null && tempHtml.length() > 0) {
					tempHtml = tempHtml.replaceAll(((char)10)+"", "<br />");
					tempHtml = tempHtml + "<br />";
				}
				
				serviceInfo.setItemHtml(tempHtml);
				servPlanList.add(serviceInfo);
		
				
				// BVAS Mandatory Item 
				for (int j=0; j < servicePlan.getCoreServiceDetail().getBvasMandItemList().size(); j++) {
					serviceInfo = new RptServiceInfoDTO();
					tempHtml = servicePlan.getCoreServiceDetail().getBvasMandItemList().get(j).getItemDetails();
					if (tempHtml != null && tempHtml.length() > 0) {
						if(tempHtml.indexOf(((char)10)+"")!=-1){
							tempHtml = "<b>" + tempHtml.replaceFirst(((char)10)+"", "</b><br />");
						}else if(tempHtml.indexOf("<br/>")!=-1){
							tempHtml = "<b>" + tempHtml.replaceFirst("<br/>", "</b><br />");
						}
						tempHtml = tempHtml.replaceAll(((char)10)+"", "<br />");
						tempHtml = tempHtml + "<br />";
						serviceInfo.setItemHtml(tempHtml);
						servPlanList.add(serviceInfo);
					}
					
				}
				
				
				// BVAS Non-Mandatory Item 
				for (int j=0; j < servicePlan.getCoreServiceDetail().getBvasNonMItemList().size(); j++) {
					serviceInfo = new RptServiceInfoDTO();
					tempHtml = servicePlan.getCoreServiceDetail().getBvasNonMItemList().get(j).getItemDetails();
					if (tempHtml != null && tempHtml.length() > 0) {
						if(tempHtml.indexOf(((char)10)+"")!=-1){
							tempHtml = "<b>" + tempHtml.replaceFirst(((char)10)+"", "</b><br />");
						}else if(tempHtml.indexOf("<br/>")!=-1){
							tempHtml = "<b>" + tempHtml.replaceFirst("<br/>", "</b><br />");
						}
						tempHtml = tempHtml.replaceAll(((char)10)+"", "<br />");
						tempHtml = tempHtml + "<br />";
						serviceInfo.setItemHtml(tempHtml);
						servPlanList.add(serviceInfo);
					}
					
				}
				
		
				// Optional Premium
				if (servicePlan.getOptServiceDetail().getOptPremItemList().size() > 0) {
		//			serviceInfo = new RptServiceInfoDTO();
		//			serviceInfo.setItemHtml("<br />");
		//			servPlanList.add(serviceInfo);
		//			serviceInfo.setItemHtml("<b>EXTRA PREMIUM:</b>");
					servPlanList.add(tempTermDesc02);
				
					for (int j=0; j < servicePlan.getOptServiceDetail().getOptPremItemList().size(); j++) {
						serviceInfo = new RptServiceInfoDTO();
						tempHtml = servicePlan.getOptServiceDetail().getOptPremItemList().get(j).getItemDetails();
						if (tempHtml != null && tempHtml.length() > 0) {
							if(tempHtml.indexOf(((char)10)+"")!=-1){
								tempHtml = "<b>" + tempHtml.replaceFirst(((char)10)+"", "</b><br />");
							}else if(tempHtml.indexOf("<br/>")!=-1){
								tempHtml = "<b>" + tempHtml.replaceFirst("<br/>", "</b><br />");
							}
							tempHtml = tempHtml.replaceAll(((char)10)+"", "<br />");
							tempHtml = tempHtml + "<br />";
							serviceInfo.setItemHtml(tempHtml);
							servPlanList.add(serviceInfo);
						}
					}
				}
				
				
				// Optional Service
				//if (servicePlan.getOptServiceDetail().getWlBbItemList().size() > 0 || servicePlan.getOptServiceDetail().getAntiVirItemList().size() > 0) {
				if (servicePlan.getOptServiceDetail().getOptServItemList().size() > 0) {
		//			serviceInfo = new RptServiceInfoDTO();
		//			serviceInfo.setItemHtml("<br />");
		//			servPlanList.add(serviceInfo);
		//			serviceInfo.setItemHtml("<b>EXTRA SERVICES:</b>");
					servPlanList.add(tempTermDesc03);
				
					for (int j=0; j < servicePlan.getOptServiceDetail().getOptServItemList().size(); j++) {
						serviceInfo = new RptServiceInfoDTO();
						tempHtml = servicePlan.getOptServiceDetail().getOptServItemList().get(j).getItemDetails();
						if (tempHtml != null && tempHtml.length() > 0) {
							if(tempHtml.indexOf(((char)10)+"")!=-1){
								tempHtml = "<b>" + tempHtml.replaceFirst(((char)10)+"", "</b><br />");
							}else if(tempHtml.indexOf("<br/>")!=-1){
								tempHtml = "<b>" + tempHtml.replaceFirst("<br/>", "</b><br />");
							}
							tempHtml = tempHtml.replaceAll(((char)10)+"", "<br />");
							tempHtml = tempHtml + "<br />";
							serviceInfo.setItemHtml(tempHtml);
							servPlanList.add(serviceInfo);
						}
					}
				}
				
				// Gift
				for (ImsRptGiftDTO giftList: servicePlan.getGiftList()) {
					logger.debug("i am now looping gift list Tnc");
					optService = new RptServiceDetailDTO();
					serviceInfo = new RptServiceInfoDTO();
				     
					optService.setItemHtml("<b>"+giftList.getGiftTitle()+"</b><br/><br/>"+giftList.getGiftDetail()+"<br/>");
					printRptServiceDetailDTO(optService);
					serviceInfo.setItemHtml(optService.getItemHtml());
					servPlanList.add(serviceInfo);
					
				}
				
				// now TV
				//Gary add newPricing
				if (order.getTvPriceInd() != null && "Y".equalsIgnoreCase(order.getTvPriceInd())) {
					logger.debug("New Pricing");
					servPlanList.add(tempTermDesc04);
					
					NowTVAddUI  newPricingItem = servicePlan.getNtvServiceDetail().getNewTVPricingItemList();
					
					
					 NowTvCampaignUI  ftaCampaign =  newPricingItem.getFtaCampaign();
					 if(ftaCampaign!=null && ftaCampaign.isSelected()){
						 serviceInfo = new RptServiceInfoDTO();
							String tnc="";
							if(!StringUtils.isEmpty(ftaCampaign.getTnc()))
								tnc+=ftaCampaign.getTnc()+"<br/>";//ftaCampaign
						for(NowTvPackUI ftaPacks :ftaCampaign.getTvPacks()){							
							if(ftaPacks.isSelected()){
								
								if(!StringUtils.isEmpty(ftaPacks.getTnc()))
									tnc+=ftaPacks.getTnc()+"<br/>";//ftaPacks
								
							}
							
						}
						for(NowTvVasBundle vasBundle:ftaCampaign.getVasBundles() )
							if( !StringUtils.isEmpty(vasBundle.getTnc()))
									tnc+=vasBundle.getTnc()+"<br/>";//ftaCampaign.getVasBundles()
						//tnc=tnc.replace("null<br/>","");
						logger.debug("FTA: "+tnc);
						if(tnc!=""){
							serviceInfo.setItemHtml(tnc);
							servPlanList.add(serviceInfo);
						}
						serviceInfo = new RptServiceInfoDTO();
						String topTnc="";
						for(NowTvTopUpUI ftatopups:ftaCampaign.getTopUps()){
							if(ftatopups.isSelected()&& !StringUtils.isEmpty(ftatopups.getTnc())){								
								topTnc+=ftatopups.getTnc()+"<br/>";								
							}								
						}
						if(topTnc!=""){
							serviceInfo.setItemHtml(topTnc);
							servPlanList.add(serviceInfo);
						}
					 }
						
						
						
					
				NowTvCampaignUI  hardCampaign =  newPricingItem.getHardCampaign();
				if(hardCampaign!=null && hardCampaign.isSelected()){
					String tnc="";
					if(!StringUtils.isEmpty(hardCampaign.getTnc()))
						tnc+=hardCampaign.getTnc()+"<br/>";
						for(NowTvPackUI hardpacks :hardCampaign.getTvPacks()){
							
							if(hardpacks.isSelected()){
								serviceInfo = new RptServiceInfoDTO();
								
								if(!StringUtils.isEmpty(hardpacks.getTnc()))
									tnc+=hardpacks.getTnc()+"<br/>";
								
								
							}
							
						}
						for(NowTvVasBundle vasBundle:hardCampaign.getVasBundles() )
							if( !StringUtils.isEmpty(vasBundle.getTnc()))
									tnc+=vasBundle.getTnc()+"<br/>";
						logger.debug("HB: "+tnc);
						//tnc=tnc.replace("null<br/>","");
						if(tnc!=""){
							serviceInfo.setItemHtml(tnc);
							servPlanList.add(serviceInfo);
						}
						
						serviceInfo = new RptServiceInfoDTO();
						String topTnc="";
						for(NowTvTopUpUI hardtopups:hardCampaign.getTopUps()){
							if(hardtopups.isSelected()&& !StringUtils.isEmpty(hardtopups.getTnc())){
								topTnc+=hardtopups.getTnc()+"<br/>";	
							}
						}
						if(topTnc!=""){
							serviceInfo.setItemHtml(topTnc);
							servPlanList.add(serviceInfo);
						}
						}
					
					for(NowTvCampaignUI campaigns:newPricingItem.getPayTvCampaign()){
						if(campaigns.isSelected()){
							String tnc="";
							if(!StringUtils.isEmpty(campaigns.getTnc()))
								tnc+=campaigns.getTnc()+"<br/>";
							for(NowTvPackUI packs :campaigns.getTvPacks()){
								if(packs.isSelected()){
									serviceInfo = new RptServiceInfoDTO();
									
									if(!StringUtils.isEmpty(packs.getTnc()))
										tnc+=packs.getTnc()+"<br/>";
								}
							}
							for(NowTvVasBundle vasBundle:campaigns.getVasBundles() )
								if( !StringUtils.isEmpty(vasBundle.getTnc()))
										tnc+=vasBundle.getTnc()+"<br/>";
							//tnc=tnc.replace("null<br/>","");
							logger.debug("PB: "+tnc);
							if(tnc!=""){
								serviceInfo.setItemHtml(tnc);
								servPlanList.add(serviceInfo);
							}
							
							serviceInfo = new RptServiceInfoDTO();
							String topTnc="";
							for(NowTvTopUpUI topups:campaigns.getTopUps()){
								if(topups.isSelected()&& !StringUtils.isEmpty(topups.getTnc())){
									topTnc+=topups.getTnc()+"<br/>";
								}
							}
							if(topTnc!=""){
								serviceInfo.setItemHtml(topTnc);
								servPlanList.add(serviceInfo);
							}
						}
						
					}
					
						
					
					
				}else{
					logger.debug("Non-New Pricing");
				if (servicePlan.getNtvServiceDetail().getNtvFreeItemList().size() > 0 ||
					servicePlan.getNtvServiceDetail().getNtvPayItemList().size() > 0 ||
					servicePlan.getNtvServiceDetail().getNtvOtherItemList().size() > 0) {
						serviceInfo = new RptServiceInfoDTO();
						//serviceInfo.setItemHtml("<br />");
						//servPlanList.add(serviceInfo);
		//				serviceInfo.setItemHtml("<b>now TV Bundle:</b>");
						servPlanList.add(tempTermDesc04);
					for (int j=0; j < servicePlan.getNtvServiceDetail().getNtvFreeItemList().size(); j++) {
						serviceInfo = new RptServiceInfoDTO();
						tempHtml = servicePlan.getNtvServiceDetail().getNtvFreeItemList().get(j).getItemDetails();
						if (tempHtml != null && tempHtml.length() > 0) {
							if(tempHtml.indexOf(((char)10)+"")!=-1){
								tempHtml = "<b>" + tempHtml.replaceFirst(((char)10)+"", "</b><br />");
							}else if(tempHtml.indexOf("<br/>")!=-1){
								tempHtml = "<b>" + tempHtml.replaceFirst("<br/>", "</b><br />");
							}
							tempHtml = tempHtml.replaceAll(((char)10)+"", "<br />");
							tempHtml = tempHtml + "<br />";
							serviceInfo.setItemHtml(tempHtml);
							servPlanList.add(serviceInfo);
						}
					}
				
					for (int j=0; j < servicePlan.getNtvServiceDetail().getNtvPayItemList().size(); j++) {
						serviceInfo = new RptServiceInfoDTO();
						tempHtml = servicePlan.getNtvServiceDetail().getNtvPayItemList().get(j).getItemDetails();
						if (tempHtml != null && tempHtml.length() > 0) {
							if(tempHtml.indexOf(((char)10)+"")!=-1){
								tempHtml = "<b>" + tempHtml.replaceFirst(((char)10)+"", "</b><br />");
							}else if(tempHtml.indexOf("<br/>")!=-1){
								tempHtml = "<b>" + tempHtml.replaceFirst("<br/>", "</b><br />");
							}
							tempHtml = tempHtml.replaceAll(((char)10)+"", "<br />");
							tempHtml = tempHtml + "<br />";
							serviceInfo.setItemHtml(tempHtml);
							servPlanList.add(serviceInfo);
						}
					}
				
					for (int j=0; j < servicePlan.getNtvServiceDetail().getNtvOtherItemList().size(); j++) {
						serviceInfo = new RptServiceInfoDTO();
						tempHtml = servicePlan.getNtvServiceDetail().getNtvOtherItemList().get(j).getItemDetails();
						if (tempHtml != null && tempHtml.length() > 0 && (!isOnlyOneLine(tempHtml))) {
							if(tempHtml.indexOf(((char)10)+"")!=-1){
								tempHtml = "<b>" + tempHtml.replaceFirst(((char)10)+"", "</b><br />");
							}else if(tempHtml.indexOf("<br/>")!=-1){
								tempHtml = "<b>" + tempHtml.replaceFirst("<br/>", "</b><br />");
							}
							tempHtml = tempHtml.replaceAll(((char)10)+"", "<br />");
							tempHtml = tempHtml + "<br />";
							serviceInfo.setItemHtml(tempHtml);
							servPlanList.add(serviceInfo);
						}
					}
				}
			}
		
				// New Media Option
				if (servicePlan.getOptServiceDetail().getMediaItemList().size() > 0) {
					serviceInfo = new RptServiceInfoDTO();
		//			serviceInfo.setItemHtml("<br />");
		//			servPlanList.add(serviceInfo);
		//			serviceInfo.setItemHtml("<b>New Media Option:</b>");
					servPlanList.add(tempTermDesc06);
				
					for (int j=0; j < servicePlan.getOptServiceDetail().getMediaItemList().size(); j++) {
						serviceInfo = new RptServiceInfoDTO();
						tempHtml = servicePlan.getOptServiceDetail().getMediaItemList().get(j).getItemDetails();
						if (tempHtml != null && tempHtml.length() > 0) {
							if(tempHtml.indexOf(((char)10)+"")!=-1){
								tempHtml = "<b>" + tempHtml.replaceFirst(((char)10)+"", "</b><br />");
							}else if(tempHtml.indexOf("<br/>")!=-1){
								tempHtml = "<b>" + tempHtml.replaceFirst("<br/>", "</b><br />");
							}
							tempHtml = tempHtml.replaceAll(((char)10)+"", "<br />");
							tempHtml = tempHtml + "<br />";
							serviceInfo.setItemHtml(tempHtml);
							servPlanList.add(serviceInfo);
						}
					}
				}
				logger.debug(" servPlanList:");
				for (int i1=0;i1<servPlanList.size();i1++)
					logger.debug(i1+": "+servPlanList.get(i1).getItemHtml()+": "+servPlanList.get(i1).getItemHtml2());
				rptImsAppServDTO.setServPlanDtls(servPlanList);
				
				rptImsAppServDTO.setQosMeasureInd(servicePlan.getQosMeasureInd());
		
			} else if (obj instanceof ImsSignoffDTO) {
				if (ImsSignoffDTO.SignatureTypeEnum.ThirdParty_SIGN == ((ImsSignoffDTO) obj).getSignatureType()){
					thirdPartySign = (ImsSignoffDTO) obj;
					logger.debug("inside ImsReportServiceImpl, ThirdParty_SIGN received");
				}else if (ImsSignoffDTO.SignatureTypeEnum.CREDIT_CARD_SIGN 	== ((ImsSignoffDTO) obj).getSignatureType()){
					creditCardsign = (ImsSignoffDTO) obj;
					logger.debug("inside ImsReportServiceImpl, CREDIT_CARD_SIGN received");
				}else if (ImsSignoffDTO.SignatureTypeEnum.CUSTOMER_SIGN 	== ((ImsSignoffDTO) obj).getSignatureType()){
					customerSign = (ImsSignoffDTO) obj;
					logger.debug("inside ImsReportServiceImpl, signature received");
				}else if (ImsSignoffDTO.SignatureTypeEnum.CareCash_SIGN 	== ((ImsSignoffDTO) obj).getSignatureType()){
					careCashSign = (ImsSignoffDTO) obj;
					logger.debug("inside ImsReportServiceImpl, careCashSign received");
				}
			}
		}
		
		if(cOrder){			
		
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			//logger.debug("servicePlan.getImsWQDTO().getApplicationDate():"+servicePlan.getImsWQDTO().getApplicationDate());
			rptImsAppServDTO.setWq_Application_date(dateFormat.format(order.getAppDate()));
			logger.debug("getResourceShortage:"+order.getInstallAddress().getAddrInventory().getResourceShortage());
			if("Y".equals(order.getInstallAddress().getAddrInventory().getResourceShortage())){
				rptImsAppServDTO.setWq_Earliest_srd_reason(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("WQEarliestSrdReason").getItemHtml());
			}
			String temp = servicePlan.getWqRemarkForInterialUse();
			logger.debug("temp:"+temp);
			String[] array = temp.split((char)10+"");
			for(int m=0;m<array.length;m++){
				logger.debug("array:"+array[m]);
				RptServiceInfoDTO bomOrderRemarks = new RptServiceInfoDTO();
				bomOrderRemarks.setItemHtml(array[m]);
				internalUseRemark.add(bomOrderRemarks);
			}
			rptImsAppServDTO.setBomOrderRemarks(internalUseRemark);
			
			//Eddie 20150813 Add Welcome Letter for FS
			if (order.getSMSno()!=null && !"".equals(order.getSMSno())){
				rptImsAppServDTO.setSms_email("SMS " + order.getSMSno() + " " + order.getEsigEmailLang());
			}
			else if(order.getEmailAddress()!=null && !"".equals(order.getEmailAddress())){
				rptImsAppServDTO.setSms_email("EMAIL " + order.getEmailAddress() + " " + order.getEsigEmailLang());
			}
			else {
				if(order.getCustomer().getContact().getContactPhone()!=null && !"".equals(order.getCustomer().getContact().getContactPhone())){
					rptImsAppServDTO.setSms_email("SMS " + order.getCustomer().getContact().getContactPhone() + " " + order.getEsigEmailLang());
				}
				else if(order.getAppointment().getInstSmsNum()!=null && !"".equals(order.getAppointment().getInstSmsNum())){
					rptImsAppServDTO.setSms_email("SMS " + order.getAppointment().getInstSmsNum() + " " + order.getEsigEmailLang());
				}
				else if(order.getCustomer().getAccount().getEmailAddr()!=null && !"".equals(order.getCustomer().getAccount().getEmailAddr())){
					rptImsAppServDTO.setSms_email("EMAIL " + order.getCustomer().getAccount().getEmailAddr() + " " + order.getEsigEmailLang());
				}
				else
					rptImsAppServDTO.setSms_email("EMAIL " + order.getLoginId() + "@netvigator.com" + " " + order.getEsigEmailLang());
			}
		}
		
		if(isCC){
			rptImsAppServDTO.setSalesChannel(order.getSalesChannel());
			rptImsAppServDTO.setShopCd(order.getSourceCd());
		}
		
		if(cOrder){			
			rptImsAppServDTO.setShow_Section_THINGS_TO_KNOW(false);		//1
		//	rptImsAppServDTO.setShow_Section_CUSTOMER_DETAILS(false);		//2
			rptImsAppServDTO.setShow_Section_SERVICE_PROVIDER(false);		//3
		//	rptImsAppServDTO.setShow_Section_CORE_SERVICES(false);		//4
		//	rptImsAppServDTO.setShow_Section_OPTIONAL_SERVICES(false);		//5
			rptImsAppServDTO.setShow_Section_GIFTS_AND_PREMIUMS(false);		//6
		//	rptImsAppServDTO.setShow_Section_CHARGES_FOR_CORE_SERVICES(false);		//7
		//	rptImsAppServDTO.setShow_Section_BILL_PREFERENCES(false);		//8
			rptImsAppServDTO.setShow_Section_IMPORTANT_INFORMATION(false);		//9
			rptImsAppServDTO.setShow_Section_GLOSSARY(false);		//10
			rptImsAppServDTO.setShow_Section_DETAILS(false);		//11
		//	rptImsAppServDTO.setShow_Section_PAYMENT(false);		//12
		//	rptImsAppServDTO.setShow_Section_PERSONAL(false);		//13
			rptImsAppServDTO.setShow_Section_AGREEMENT(false);		//15
		//	rptImsAppServDTO.setShow_Section_Interial_Use(false);		//16
			rptImsAppServDTO.setcOrder(true);		
		}
		
		
		rptImsAppServDTO.setCustomerCopyInd("N");
		// S&S Customer Copy
		BeanUtils.copyProperties(rptImsAppServCustCopyDTO, rptImsAppServDTO);
		rptImsAppServCustCopyDTO.setCustomerCopyInd("Y");
		rptImsAppServDTO.setIsPreview(isPreview);
		if(Signed){
			int signatureSize=0;
			if(customerSign!=null){
				try {
					signatureSize = customerSign.getSignatureInputStream().available();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					signatureSize = 0;
				}
			}
			if(signOffed&&(customerSign==null||signatureSize<=0)&&"E".equalsIgnoreCase(disMode)){
				signOffLogService.signOffOrderLog(order, "EmptySignature", "Customer signature is empty.");
				throw new ImsEmptySignatureException();
			}
			if(customerSign!=null){
				rptImsAppServDTO.setCustSignature(customerSign.getSignatureInputStream());
				rptImsAppServCustCopyDTO.setCustSignature(customerSign.getSignatureInputStream());
				rptIms3PartyCreditCardDTO.setCustSignature(customerSign.getSignatureInputStream());
				logger.debug("inside ImsReportServiceImpl, customer sign is not null");
			}
			if(creditCardsign!=null){
				rptImsAppServDTO.setCustSignaturePay(creditCardsign.getSignatureInputStream());
				rptImsAppServCustCopyDTO.setCustSignaturePay(creditCardsign.getSignatureInputStream());
				logger.debug("inside ImsReportServiceImpl, Credit Card sign is not null");
			}
			if(thirdPartySign!=null){
				rptImsAppServDTO.setCustSignaturePay(thirdPartySign.getSignatureInputStream());
				rptImsAppServCustCopyDTO.setCustSignaturePay(thirdPartySign.getSignatureInputStream());
				rptIms3PartyCreditCardDTO.setThirdPartySignature(thirdPartySign.getSignatureInputStream());
				logger.debug("inside ImsReportServiceImpl, 3Party Sign is not null");
			}
			if(careCashSign!=null){
				rptImsAppServDTO.setCareCashSignature(careCashSign.getSignatureInputStream());
				logger.debug("inside ImsReportServiceImpl, careCash Sign is not null");
			}
		}
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy");
		Date date = new Date();
		rptImsAppServDTO.setAfFooter(dateFormat.format(date)+ " Hong Kong Telecommunications (HKT) Limited");
		
		ssList.add(rptImsAppServDTO);	
		ssCustCopyList.add(rptImsAppServCustCopyDTO);
		
		// Add Application for Service Form
		String key;
			 key = imsDsJasperName + (RPT_LANG_ZH.equalsIgnoreCase(pLang) ? FORM_ZH_EXT : "");
		map.put(key, new JasperReport(key, ssList));
		map.put(key + CUST_COPY, new JasperReport(key, ssCustCopyList));
		
		// Add Credit Card Authorization Form
		if (isReq3PartyCreditCardForm&&!isCC) {
			ccList.add(rptIms3PartyCreditCardDTO);
			map.put(imsRetail3PartyJasperName, new JasperReport(imsRetail3PartyJasperName, ccList));			
		}

		String careCashChannelCode = "";

		if(!isCC&&!"DS".equals(order.getImsOrderType())){
			careCashChannelCode = order.getShopCd();
		}else{
			if(order.getSalesChannel()!=null&&!order.getSalesChannel().isEmpty()){
				careCashChannelCode = order.getSalesChannel();
			}else{
				careCashChannelCode = order.getShopCd();
			}
		}
		
		if ("I".equalsIgnoreCase(order.getCustomer().getCareCashInd()))
		{
			setRptIGuardCareCashDTO(rptImsAppServDTO, disMode, order.getCustomer().getCareCashOptOutInd(), order.getCustomer().getCareCashEmail(), order.getCustomer().getCareCashMobile(), order.getAppDate(), careCashChannelCode);
			map.put(imsCareCashJasperName, new JasperReport(imsCareCashJasperName, ssList));	
		}
		
//		if (true) {
//			ccList.add(rptIms3PartyCreditCardDTO);
//			map.put(imsDsCPJasperName, new JasperReport(imsDsCPJasperName, ccList));			
//		}
		
		return map;
}
	public void getDisplayNowTvAddReport(List<RptServiceDetailDTO> optServList ,NowTVAddUI newPricingItem, Boolean cOrder, String topUpHeading, Boolean isPT,String newPricingDot,List<String> packTitles, OrderImsUI order, String hddDesc){

		String isAdultViewAllow="";
		String AdultViewAllow=order.getAdultViewAllow();
		NowTvCampaignUI  ftaCampaign =  newPricingItem.getFtaCampaign();
		if(ftaCampaign!=null)
		for(NowTvPackUI ftaPacks :ftaCampaign.getTvPacks()){
			if(ftaPacks.isSelected()){
				addDisplayDetailsForAF(optServList,ftaCampaign,cOrder,topUpHeading, isPT, newPricingDot, packTitles, newPricingItem.getNtvConnType());
				break;
			}
		}			
		NowTvCampaignUI  hardCampaign =  newPricingItem.getHardCampaign();
		if(hardCampaign!=null)
		for(NowTvPackUI hardpacks :hardCampaign.getTvPacks()){
			if(hardpacks.isSelected()){
				addDisplayDetailsForAF(optServList,hardCampaign,cOrder,topUpHeading, isPT, newPricingDot, packTitles, newPricingItem.getNtvConnType());
				break;
			}
		}
		for(NowTvCampaignUI campaigns:newPricingItem.getPayTvCampaign()){
			for(NowTvPackUI packs :campaigns.getTvPacks()){
				if(packs.isSelected()){
					addDisplayDetailsForAF(optServList,campaigns,cOrder,topUpHeading, isPT,newPricingDot, packTitles, newPricingItem.getNtvConnType());
					break;
				}
			}
		}
		String hddPurchased = "N";
		for(int i=0;i<order.getSubscribedItems().length;i++){
			if(order.getSubscribedItems()[i].getType()!=null&&order.getSubscribedItems()[i].getType().equalsIgnoreCase("BE_F_REC")){
				hddPurchased = "Y";
			}
		}
		if(hddPurchased.equalsIgnoreCase("Y")){
			RptServiceDetailDTO optService;
			optService = new RptServiceDetailDTO();	
			optService.setItemHtml(hddDesc);	
			optServList.add(optService);
		}
		
		if(cOrder){
			RptServiceDetailDTO optService;
			optService = new RptServiceDetailDTO();	
			if("Y".equalsIgnoreCase(AdultViewAllow))
				isAdultViewAllow="Y";
			else
				isAdultViewAllow="N";
			
			optService.setItemHtml("Adult View Allow: "+isAdultViewAllow);	
			optServList.add(optService);
		}
	}
	public void addDisplayDetailsForAF(List<RptServiceDetailDTO> optServList, NowTvCampaignUI selectedCamp, Boolean cOrder, String topUpHeading, Boolean isPT,String newPricingDot,List<String> packTitles,int connType){
		RptServiceDetailDTO optService;
		String detail="";
		Boolean showTitle = true;
		
		String title ="FTA".equalsIgnoreCase(selectedCamp.getTvPacks().get(0).getType())?packTitles.get(0):("HB".equalsIgnoreCase(selectedCamp.getTvPacks().get(0).getType())?packTitles.get(1):packTitles.get(2));
		detail+= "<b>"+title+ (!cOrder?"</b> <br/>":"");
		
		if(cOrder){
		String tempTopUp = "";
		
		List<NowTvTopUpUI> topUp = new ArrayList<NowTvTopUpUI>();

		topUp = selectedCamp.getTopUps();
			if (topUp.size()>0 && topUpHeading!=null){
					
				
				for (int t=0;t<topUp.size() && selectedCamp.getTopUps().get(t).isSelected(); t++){								
					tempTopUp += "<br/><b>" + topUp.get(t).getDetail();
					if(cOrder)
						tempTopUp+=" ( "+topUp.get(t).getCampaignCd_SOPHIE()+" | "+topUp.get(t).getTopUpCd()+" )";
					tempTopUp += "</b><br/>";
				}		
				if(tempTopUp != "")
					tempTopUp = "<b>"+topUpHeading + tempTopUp;
			}
			tempTopUp = tempTopUp.replace("null", "");
			
			if(tempTopUp != ""){
			detail+=tempTopUp+"<br/>";
			}
		}

		
		int packCount=0;
		String hdConnectionFee="";
		String hdConnectionFeePrice="";
		
		for(NowTvPackUI packs:selectedCamp.getTvPacks()){
			if(cOrder && showTitle && "FTA".equalsIgnoreCase(packs.getParentType())){				
				detail+=" ( "+packs.getCampaignCd_SOPHIE()+" | " + packs.getPackCd_SOPHIE()+" )</b> <br/>";
				showTitle=false;				
			}else{
				String cOrderOfferCode = "";
				if(cOrder && showTitle){
					cOrderOfferCode += "( ";
					for ( NowTvVasBundle vasBundles: selectedCamp.getVasBundles()){	
						for (NowTVOfferUI offerCdObj:vasBundles.getOfferCd()){
						if(!StringUtils.isEmpty(offerCdObj.getOfferCd())){		
							if(!cOrderOfferCode.equalsIgnoreCase("( ")){
								cOrderOfferCode+=" | ";
							}
							cOrderOfferCode+=offerCdObj.getOfferCd();
						}
						}
					}
					cOrderOfferCode += " )";
					if (!StringUtils.isEmpty(cOrderOfferCode)&&!cOrderOfferCode.equalsIgnoreCase("(  )"))
						detail+=cOrderOfferCode;
					showTitle=false;
				}
				
				detail+=" </b> <br/>";
			}
			
			if(packs.isSelected()){
				optService = new RptServiceDetailDTO();
				if(isPT){
					optService.setItemRecurrentAmt("<br/><br/>"+selectedCamp.getFix_term_rate());
					optService.setItemRecurrentAmt2("<br/><br/>"+selectedCamp.getMth_to_mth_rate());
				}else{
				if(selectedCamp.getContractPeriod()>0)
					optService.setItemRecurrentAmt("<br/><br/>"+selectedCamp.getFix_term_rate());
				else
					optService.setItemRecurrentAmt("<br/><br/>"+selectedCamp.getMth_to_mth_rate());
				}
				if("PB".equalsIgnoreCase(packs.getType())){
					if(packCount==0){
						detail+=packs.getDisplayDtailsForReport(cOrder, newPricingDot, connType);
						packCount++;
					}					
					else{
						String tempTitle=packs.getCampaign_title();
						optService.setItemRecurrentAmt("");
						optService.setItemRecurrentAmt2("");
						packs.setCampaign_title("");
						detail+=packs.getDisplayDtailsForReport(cOrder, newPricingDot, connType);
						packs.setCampaign_title(tempTitle);
					}
					
				}else
					detail+=packs.getDisplayDtailsForReport(cOrder, newPricingDot, connType);
				detail=detail.replace("null", "");
				optService.setItemHtml(detail);
				optServList.add(optService);
				detail="";
			}
			
			
//			for( NowTvVasBundle selectedCam : selectedCamp.getVasBundles()){
//				if (selectedCam.isSelected() && "NTV_HD".equalsIgnoreCase(selectedCam.getType())){
//					hdConnectionFee = selectedCam.getTitle();// + (cOrder?selectedCam.getOfferCd().get:"");
//					if(cOrder){
//						String cOrderOfferCode = "( ";
//						for ( NowTVOfferUI offerCd: selectedCam.getOfferCd()){
//							if(!StringUtils.isEmpty(offerCd.getOfferCd())){		
//								if(!cOrderOfferCode.equalsIgnoreCase("( ")){
//									cOrderOfferCode+=" | ";
//								}
//								cOrderOfferCode+=offerCd.getOfferCd();
//							}						
//						}
//						cOrderOfferCode += " )";
//						if (!StringUtils.isEmpty(cOrderOfferCode)&&!cOrderOfferCode.equalsIgnoreCase("(  )"))							
//							hdConnectionFee +=cOrderOfferCode;
//					}
//					hdConnectionFeePrice = selectedCam.getFixedTermRate();					
//				}
//				
//			}
			
		}
		optService = new RptServiceDetailDTO();		
		optService.setItemHtml(hdConnectionFee);
		optService.setItemRecurrentAmt(hdConnectionFeePrice);		
		optServList.add(optService);
		
		
		if(!cOrder){
		String tempTopUp = "";
		
		List<NowTvTopUpUI> topUp = new ArrayList<NowTvTopUpUI>();

		topUp = selectedCamp.getTopUps();
			if (topUp.size()>0 && topUpHeading!=null){
					for (int t=0;t<topUp.size() && selectedCamp.getTopUps().get(t).isSelected(); t++){								
					tempTopUp += "<b>" + topUp.get(t).getDetail();
					if(cOrder)
						tempTopUp+=" ( "+topUp.get(t).getCampaignCd_SOPHIE()+" | "+topUp.get(t).getTopUpCd()+" )";
					tempTopUp += "</b><br/>";
				}								
if(tempTopUp != "")
					tempTopUp = "<b>"+topUpHeading + tempTopUp;
			}
			tempTopUp = tempTopUp.replace("null", "");
			optService = new RptServiceDetailDTO();
			if(tempTopUp != ""){
			optService.setItemHtml(tempTopUp);
			optServList.add(optService);
			}
		}
	}
	
	public void CsPortalAndTheClubAFDisplay(OrderImsUI order, RptImsAppServDTO rptImsAppServDTO, HashMap<String, HashMap<String, RptServiceInfoDTO>> dbRptstaticWords ){//ArrayList<RptServiceInfoDTO> hktServPortalList ){
		ArrayList<RptServiceInfoDTO> hktServPortalList = new ArrayList<RptServiceInfoDTO>();
		ArrayList<RptServiceInfoDTO> hktServPortalList2 = new ArrayList<RptServiceInfoDTO>();
		
		if(order.getCustomer().getCsPortalInd()==null){ //A for existing cust, Y for new registration, N for not Reg for HKT account
			order.getCustomer().setCsPortalInd("N");
		}
		logger.debug("gary order.getCustomer().getCsPortalInd():"+order.getCustomer().getCsPortalInd());
		logger.debug("gary order.getCustomer().getTheClubInd():"+order.getCustomer().getTheClubInd());
		logger.debug("gary order.getCustomer().getHktLoginId():"+order.getCustomer().getCsPortalLogin());
		logger.debug("gary order.getCustomer().getTheClubLogin():"+order.getCustomer().getTheClubLogin());
		logger.debug("gary order.getCustomer().getHktMobileNum():"+order.getCustomer().getCsPortalMobile());
		String clubInd =order.getCustomer().getTheClubInd(); //A for existing cust, Y for new registration, N for not Reg 
		String csportalInd = order.getCustomer().getCsPortalInd();
		String registrationInd="";
		
		registrationInd = clubInd + csportalInd;
		
		logger.debug("###Reg Status: " + registrationInd);
		rptImsAppServDTO.setIsRegHKTLoginId(registrationInd);
		rptImsAppServDTO.setCsLang(order.getLangPreference());
		
		
				RptServiceInfoDTO hktClubServ01  = new RptServiceInfoDTO(); //reg both
				RptServiceInfoDTO hktClubServ02  = new RptServiceInfoDTO();	//myhkt reged, reg club
				RptServiceInfoDTO hktClubServ03  = new RptServiceInfoDTO();	//club regged, reg myhkt
				RptServiceInfoDTO hktClubServ04  = new RptServiceInfoDTO();	//club regged, NOT reg myhkt
				RptServiceInfoDTO hktClubServ05  = new RptServiceInfoDTO();	//both reged
				RptServiceInfoDTO hktClubServ06  = new RptServiceInfoDTO();	//not eligible
				RptServiceInfoDTO hktClubServ07  = new RptServiceInfoDTO();	//dummy email or mobile used
				RptServiceInfoDTO hktClubServBottom01  = new RptServiceInfoDTO();
				RptServiceInfoDTO hktClubServBottom02  = new RptServiceInfoDTO();
				RptServiceInfoDTO hktClubServBottom03  = new RptServiceInfoDTO();
										
				if(dbRptstaticWords!=null){
					logger.debug(" CSP"); 
					hktClubServ01.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("clubHKT01"));
					hktClubServ02.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("clubHKT02"));
					hktClubServ03.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("clubHKT03"));
					hktClubServ04.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("clubHKT04"));
					hktClubServ05.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("clubHKT05"));
					hktClubServ06.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("clubHKT06"));
					hktClubServ07.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("clubHKT07"));
					hktClubServBottom01.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("hktClubServBottom01"));
					hktClubServBottom02.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("hktClubServBottom02"));
					hktClubServBottom03.copy(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("hktClubServBottom03"));
			     }
				
				if(clubInd.equalsIgnoreCase("Y")&&csportalInd.equalsIgnoreCase("Y")){
					rptImsAppServDTO.setHktLoginId(order.getCustomer().getCsPortalLogin());
					rptImsAppServDTO.setHktMobileNum(order.getCustomer().getTheClubMoblie());
					hktServPortalList.add(hktClubServ01);
					rptImsAppServDTO.setHktLoginIdSubject(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("hktClubServ01Email").getItemHtml());
					rptImsAppServDTO.setHktMobileNumSubject(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("hktClubServ01Mobile").getItemHtml());
				}else if(clubInd.equalsIgnoreCase("Y")&&csportalInd.equalsIgnoreCase("A")){
					rptImsAppServDTO.setHktLoginId(order.getCustomer().getTheClubLogin());
					rptImsAppServDTO.setHktMobileNum(order.getCustomer().getTheClubMoblie());
					hktServPortalList.add(hktClubServ02);
					rptImsAppServDTO.setHktLoginIdSubject(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("hktClubServ01Email").getItemHtml());
					rptImsAppServDTO.setHktMobileNumSubject(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("hktClubServ01Mobile").getItemHtml());
				}else if(clubInd.equalsIgnoreCase("Y")&&csportalInd.equalsIgnoreCase("N")){
					rptImsAppServDTO.setHktLoginId(order.getCustomer().getTheClubLogin());
					rptImsAppServDTO.setHktMobileNum(order.getCustomer().getTheClubMoblie());
					hktServPortalList.add(hktClubServ07);
					rptImsAppServDTO.setHktLoginIdSubject(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("hktClubServ01Email").getItemHtml());
					rptImsAppServDTO.setHktMobileNumSubject(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("hktClubServ01Mobile").getItemHtml());
				}else if(clubInd.equalsIgnoreCase("A")&&csportalInd.equalsIgnoreCase("Y")){
					rptImsAppServDTO.setHktLoginId(order.getCustomer().getCsPortalLogin());
					rptImsAppServDTO.setHktMobileNum(order.getCustomer().getCsPortalMobile());
					hktServPortalList.add(hktClubServ03);
					rptImsAppServDTO.setHktLoginIdSubject(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("hktClubServ01Email").getItemHtml());
					rptImsAppServDTO.setHktMobileNumSubject(dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("hktClubServ01Mobile").getItemHtml());
				}else if(clubInd.equalsIgnoreCase("A")&&csportalInd.equalsIgnoreCase("N")){
					hktServPortalList.add(hktClubServ04);
				}else if(clubInd.equalsIgnoreCase("A")&&csportalInd.equalsIgnoreCase("A")){
					hktServPortalList.add(hktClubServ05);
				}else{
					hktServPortalList.add(hktClubServ06);
				}
				if(rptImsAppServDTO.getHktMobileNum()!=null&&rptImsAppServDTO.getHktMobileNum().equals("00000000")){
					rptImsAppServDTO.setHktMobileNum("");
				}
				logger.debug("portal static words: "+dbRptstaticWords.get(rptImsAppServDTO.getLocale()).get("hktClubServ03").getItemHtml()+"\n"+rptImsAppServDTO.getHktLoginIdSubject());
				
				rptImsAppServDTO.setHktServPortalDtls(hktServPortalList);
				rptImsAppServDTO.setHktServPortalDtls2(hktServPortalList2);
	}
	
	// martin 20160422, third party credit card form setup
	public void set3rdPartyCreditCardForm(RptIms3PartyCreditCardDTO thirdPartyCcDTO, HashMap<String, String> db, OrderImsUI order) {
		thirdPartyCcDTO.setNtvLogo(imageFilePath + "/" + NOWTV_LOFO_FILE);
		thirdPartyCcDTO.setNetvigatorLogo(imageFilePath + "/" + NNETVIGATOR_LOGO);
		thirdPartyCcDTO.setTitleChi(db.get("3rdPartyCCAuth-title_chi"));
		thirdPartyCcDTO.setTitleEn(db.get("3rdPartyCCAuth-title_en"));
		thirdPartyCcDTO.setIntroChi(db.get("3rdPartyCCAuth-intro_chi"));
		thirdPartyCcDTO.setIntroEn(db.get("3rdPartyCCAuth-intro_en"));
		thirdPartyCcDTO.setSubMethodSalesChi(db.get("3rdPartyCCAuth-subMethodSales_chi"));
		thirdPartyCcDTO.setSubMethodSalesEn(db.get("3rdPartyCCAuth-subMethodSales_en"));
		
		thirdPartyCcDTO.setDeclarationChi(db.get("3rdPartyCCAuth-declaration_chi"));
		thirdPartyCcDTO.setDeclarationEn(db.get("3rdPartyCCAuth-declaration_en"));
		thirdPartyCcDTO.setAuthChi(db.get("3rdPartyCCAuth-auth_chi"));
		thirdPartyCcDTO.setAuthEn(db.get("3rdPartyCCAuth-auth_en"));
		thirdPartyCcDTO.setFootnoteChi(db.get("3rdPartyCCAuth-footnote_chi"));
		thirdPartyCcDTO.setFootnoteEn(db.get("3rdPartyCCAuth-footnote_en"));
		
		thirdPartyCcDTO.setShowVerNum(db.get("3rdPartyCCAuth-showVer_en"));
		thirdPartyCcDTO.setVerNum(db.get("3rdPartyCCAuth-ver_en"));
		
		if ("NTV-A".equals(order.getOrderType())||order.isOrderTypeNowRet()) {
			thirdPartyCcDTO.setNtvAppNo(order.getOrderId());
			//thirdPartyCcDTO.setSubscribeMethod("S");
		} else {
			thirdPartyCcDTO.setPcdAppNo(order.getOrderId());
		}
		
		if ("Y".equals(order.getIsCC())) {
			thirdPartyCcDTO.setSubscribeMethod("C");
		} else {
			thirdPartyCcDTO.setSubscribeMethod("S");
		}
		
		String temp = db.get("3rdPartyCCAuth-subMethodHotline_chi");
		String input1 = StringUtils.center("________", 20, "_");
		String input2 = StringUtils.center("________", 20, "_");
		//String input3 = input1.replaceAll("\\s", "&nbsp;");
		if (!StringUtils.isEmpty(temp)) {
			temp = temp.replace("${0}","<span style='font-family:PCCW-Arial'>"+input1+"</span>")
					.replace("${1}","<span style='font-family:PCCW-Arial'>"+input2+"</span>");
		}
		thirdPartyCcDTO.setSubMethodHotlineChi(temp);
		temp = db.get("3rdPartyCCAuth-subMethodHotline_en");
		if (!StringUtils.isEmpty(temp)) {
			temp = temp.replace("${0}","<span style='font-family:PCCW-Arial'>"+input1+"</span>")
					.replace("${1}","<span style='font-family:PCCW-Arial'>"+input2+"</span>");
		}
		thirdPartyCcDTO.setSubMethodHotlineEn(temp);
		
		thirdPartyCcDTO.setCustLastName(order.getCustomer().getLastName());
		thirdPartyCcDTO.setCustFirstName(order.getCustomer().getFirstName());
		//thirdPartyCcDTO.setIdDocType(order.getCustomer().getIdDocType());
		thirdPartyCcDTO.setIdDocNum(order.getCustomer().getIdDocNum());
		if (order.getCustomer().getContact() != null) { 
			String custContact = order.getCustomer().getContact().getContactPhone();
			if(StringUtils.isEmpty(custContact)){
				custContact = order.getCustomer().getContact().getOtherPhone();
			}
			if (custContact.length() >= 8) custContact = custContact.substring(0, 8);
			thirdPartyCcDTO.setContactPhone(custContact);
		}
		if (order.getSignOffDate() != null) {
			thirdPartyCcDTO.setAppDate(Utility.date2String(order.getSignOffDate(), "dd/MM/yyyy"));
		} else if (order.getLastUpdDate() != null) {
			thirdPartyCcDTO.setAppDate(Utility.date2String(order.getLastUpdDate(), "dd/MM/yyyy"));
		} else {
			thirdPartyCcDTO.setAppDate(Utility.date2String(order.getCreateDate(), "dd/MM/yyyy"));
		}
		
		if (order.getCustomer() != null) {
			if (order.getCustomer().getAccount() != null) {
				if (order.getCustomer().getAccount().getPayment() != null) {
					if ("C".equals(order.getCustomer().getAccount().getPayment().getPayMtdType())) {
						thirdPartyCcDTO.setCreditCardType(order.getCustomer().getAccount().getPayment().getCcType());  // V - Visa, M - Master, AE - AE
						thirdPartyCcDTO.setCreditCardHolderName(order.getCustomer().getAccount().getPayment().getCcHoldName());
						
						String maskedCreditCardNumber = order.getCustomer().getAccount().getPayment().getCcNum();
						if(!StringUtils.isEmpty(maskedCreditCardNumber)){
							if (maskedCreditCardNumber.length() > 10) {
								String firstSixDigits = maskedCreditCardNumber.substring(0,6);
								String lastFourDigits = maskedCreditCardNumber.substring(maskedCreditCardNumber.length()-4);
								String maskedString = StringUtils.rightPad("", maskedCreditCardNumber.length()-10, "*");
								
								maskedCreditCardNumber = firstSixDigits + maskedString + lastFourDigits;
							}
							thirdPartyCcDTO.setCreditCardNum(maskedCreditCardNumber);
						}
						thirdPartyCcDTO.setCreditExpiryDate(order.getCustomer().getAccount().getPayment().getCcExpDate());
					}
				}
			}
		}
		
	}
	
	public void setRptIGuardCareCashDTO(RptImsAppServDTO rptImsAppServDTO, String disMode, String careCashOptOutInd, String careCashEmail, String careCashMobile, Date appDate, String careCashChannelCode){
		
		RptIGuardCareCashDTO rptIGuardCareCashDTO = new RptIGuardCareCashDTO();

		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		rptIGuardCareCashDTO.setOrderId(rptImsAppServDTO.getAppNo());
		rptIGuardCareCashDTO.setShopCd(careCashChannelCode);
		rptIGuardCareCashDTO.setStaffId(rptImsAppServDTO.getSalesCd());
		rptIGuardCareCashDTO.setIdDocNum(rptImsAppServDTO.getIdDocNum());
		rptIGuardCareCashDTO.setCustEngName(rptImsAppServDTO.getTitle()+" "+rptImsAppServDTO.getLastName()+" "+rptImsAppServDTO.getFirstName());
		rptIGuardCareCashDTO.setSignatureInd("E".equals(disMode)||"P".equals(disMode)?"Y":"N");
		rptIGuardCareCashDTO.setEmailAddr(careCashEmail);
		rptIGuardCareCashDTO.setContactPhone(careCashMobile);
		rptIGuardCareCashDTO.setMob("");
		rptIGuardCareCashDTO.setLts("");
		rptIGuardCareCashDTO.setIms("1");
		rptIGuardCareCashDTO.setNtv("");
		rptIGuardCareCashDTO.setPrivacyInd("O".equalsIgnoreCase(careCashOptOutInd)?"Y":"N");
		rptIGuardCareCashDTO.setCustSignature(rptImsAppServDTO.getCareCashSignature());
		try {
			rptIGuardCareCashDTO.setDob(formatter.parse(rptImsAppServDTO.getDob()));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		rptIGuardCareCashDTO.setAppDate(appDate);
		
		rptImsAppServDTO.setRptIGuardCareCashDTO(rptIGuardCareCashDTO);
		
	}
}
