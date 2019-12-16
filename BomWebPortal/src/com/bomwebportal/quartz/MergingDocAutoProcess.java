package com.bomwebportal.quartz;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.mail.internet.InternetAddress;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.FastDateFormat;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bomwebportal.dto.AllOrdDocDmsDTO;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.service.MailService;
import com.bomwebportal.service.OrderService;
import com.bomwebportal.service.SupportDocAdminService;
import com.bomwebportal.util.BomWebPortalConstant;
import com.bomwebportal.util.MailUtil;
import com.bomwebportal.web.util.ReportRepository;

public class MergingDocAutoProcess extends AutoProcessBase {

	protected final Log logger = LogFactory.getLog(getClass());
	
	
	private String outputDocPath;
	private ReportRepository docRepository;
	private SupportDocAdminService supportDocAdminService;
	private OrderService orderService;
	//private MailService mailService;
	
	private String finishEmailFrom;
	private String[] finishEmailTo;
	private String failureEmailFrom;
	private String[] failureEmailTo;
	
	private static FastDateFormat df = FastDateFormat.getInstance("yyyyMMdd");
	
	public String getOutputDocPath() {
		return outputDocPath;
	}

	public void setOutputDocPath(String outputDocPath) {
		this.outputDocPath = outputDocPath;
	}

	public ReportRepository getDocRepository() {
		return docRepository;
	}

	public void setDocRepository(ReportRepository docRepository) {
		this.docRepository = docRepository;
	}

	public SupportDocAdminService getSupportDocAdminService() {
		return supportDocAdminService;
	}

	public void setSupportDocAdminService(
			SupportDocAdminService supportDocAdminService) {
		this.supportDocAdminService = supportDocAdminService;
	}

	public OrderService getOrderService() {
		return orderService;
	}

	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}

	
	/*
	
	public MailService getMailService() {
		return mailService;
	}

	public void setMailService(MailService mailService) {
		this.mailService = mailService;
	}

	*/
	
	/*
	protected void trigger1() throws DAOException {
		
		logger.info("Start Merging document files");
		
		//supportDocAdminService.prepareOrdDocDmsForProcessing("SYSTEM");
		
		List<AllOrdDocDmsDTO> dmsDto = supportDocAdminService.findOrderForDmsPreprocessing();
		//findAllOrdDocDmsDTO(new Date(), "INITIAL", null);
		System.out.println("dmsdto = " + dmsDto);
		File dataFolder = new File(docRepository.getDataFilePath());
		FileFilter dirFilter = new FileFilter() {

			public boolean accept(File f) {
				return f.isDirectory();
			}
			
		};
		File orders[] = dataFolder.listFiles(dirFilter);
		if (orders == null || orders.length == 0) return;
		String orderId = null;
		
		String yyyyMMdd = df.format(new Date());
		
		for (File f : orders) {
			try {
				orderId = f.getName();

				logger.info("Merging document files for order - " + orderId);

				File outFile = new File(outputDocPath + "/" + orderId + "_"+ yyyyMMdd + ".pdf");
	
				File mergedFiles[] = docRepository.generateMergedPDF(outFile, orderId);
				System.out.println(StringUtils.join(mergedFiles, ","));
			} catch (Exception e) {
				logger.error("Document Merging failed for order - " + orderId, e);
			}
			
		}

		

	}
	*/
	


	public String getFinishEmailFrom() {
		return finishEmailFrom;
	}

	public void setFinishEmailFrom(String finishEmailFrom) {
		this.finishEmailFrom = finishEmailFrom;
	}

	public String[] getFinishEmailTo() {
		return finishEmailTo;
	}

	public void setFinishEmailTo(String finishEmailTo[]) {
		this.finishEmailTo = finishEmailTo;
	}

	public String getFailureEmailFrom() {
		return failureEmailFrom;
	}

	public void setFailureEmailFrom(String failureEmailFrom) {
		this.failureEmailFrom = failureEmailFrom;
	}

	public String[] getFailureEmailTo() {
		return failureEmailTo;
	}

	public void setFailureEmailTo(String failureEmailTo[]) {
		this.failureEmailTo = failureEmailTo;
	}

	@Override
	protected void trigger() throws DAOException {
		logger.info("Start Merging document files");
		Map<String,String> errors = new LinkedHashMap<String,String>();
		
		List<AllOrdDocDmsDTO> ordList = supportDocAdminService.findOrderForDmsPreprocessing("MOB");
		
		
		for (AllOrdDocDmsDTO dto: ordList) {
			

			boolean success = processOrder(dto, errors);
		}
		
		logger.debug("errors: " + errors);
		if (errors.size() > 0) {
			logger.error("Errors occured during the merging process.");

			emailFailureAlert(errors);
		}
		try {
			File ctrlFile = createControlFile(ordList);
			emailControlFile(ctrlFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	boolean processOrder(AllOrdDocDmsDTO ord, Map<String,String> errors) {
		String orderId = ord.getOrderId();
		String yyyyMMdd = df.format(new Date());
		String msisdn = ord.getMsisdn();
		
		try {
			logger.info("Processing order : " + orderId);
			
			ord.setProcessStatus("INITIAL");
			supportDocAdminService.insertOrdDocDms(ord);

			logger.info("Merging document files for order - " + orderId);
			
			File outFile = new File(ensureOutputFolder(), msisdn + "_M_"+ yyyyMMdd + ".pdf");

			File mergedFiles[] = docRepository.generateMergedPDF(outFile, orderId);
			if (ArrayUtils.isNotEmpty(mergedFiles)) {
				ord.setDmsFileName(outFile.getName());
				ord.setMergedFileList(toFileNameList(mergedFiles));
			}
			ord.setProcessStatus("COMPLETED");
			ord.setLastUpdBy("SYSTEM");
			supportDocAdminService.updateOrdDocDms(ord);
			
			orderService.updateDmsInd(orderId, "Y", "SYSTEM");
			
			return true;
			
		} catch (Exception e) {
			logger.error("Document Merging failed for order - " + orderId, e);
			errors.put(ord.getOrderId(), e.getMessage());
		}
		
		ord.setProcessStatus("FAILED");
		ord.setLastUpdBy("SYSTEM");
		supportDocAdminService.updateOrdDocDms(ord);
		return false;

		
	}
	
	
	private File createControlFile(List<AllOrdDocDmsDTO> dtos) throws IOException {

		String yyyyMMdd = df.format(new Date());
		File ctrlFile = new File(ensureOutputFolder(), "sb_mob_ctrl_" + yyyyMMdd + ".csv");
		
		logger.info("creating control file : " + ctrlFile.getName());
		
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(ctrlFile)));
		out.println("CUSTOMER_CODE,ID_DOC_NUM,MSISDN,PDF_FILENAME,BRAND");
		
		for (AllOrdDocDmsDTO dto: dtos) {
			
			if ("COMPLETED".equals(dto.getProcessStatus())
					&& StringUtils.isNotBlank(dto.getDmsFileName())) {
				
				out.printf("%s,%s,%s,%s,%s",
						"",
						dto.getIdDocNum(),
						dto.getMsisdn(),
						dto.getDmsFileName(),
						"3G");
				out.println();
				
			}
		}
		out.flush();
		out.close();
		return ctrlFile;
	}
	
	private void emailControlFile(File f) {
		logger.info("Sending control file");
		String sdate = df.format(new Date());
		
		File attachments[] = new File[] {f};
		String from = getFinishEmailFrom();
		String[] to = getFinishEmailTo();
		String subject = "SB Retails Sales - Support Document Handling on " + sdate;
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);

		pw.printf("Attached please find SB Mobile Retails merged support document list handled on %s.", sdate);
		pw.println();
		pw.println("If you have any concerns, please contact PCCW BOM Springboard MOB support.");
		pw.println();
		pw.println("Regards,");
		pw.println("SB Mobile");
		
		try {
			//mailService.send(new InternetAddress(from), new InternetAddress(to), null, null, subject, sw.toString(), attachments);
			MailUtil.sendMail(from, to, null, null, subject, sw.toString(), MailUtil.MAIL_TYPE_TEXT, attachments);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void emailFailureAlert(Map<String,String> errors) {
		
		logger.info("Sending failure alert");
		
		String sdate = df.format(new Date());
		
		String from = getFailureEmailFrom();
		String to[] = getFailureEmailTo();
		String subject = "Support Document Handling  on " + sdate + " FAILED";

		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		
		pw.println("Support Document handling auto process executed with errors :");
		pw.println("OrderID : Error Message");
		
		for (String orderId: errors.keySet()) {
			String err = errors.get(orderId);
			pw.printf("%s : %s", orderId, err);
			pw.println();
		}
		
		try {
			//mailService.send(new InternetAddress(from), new InternetAddress(to), null, null, subject, sw.toString(), null);
			MailUtil.sendMail(from, to, null, null, subject, sw.toString(), MailUtil.MAIL_TYPE_TEXT, (File[])null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	

	private File ensureOutputFolder() {
		String yyyyMMdd = df.format(new Date());
		File outFolder = new File(outputDocPath + "/dms_" + yyyyMMdd);
		if (!outFolder.isDirectory()) outFolder.mkdirs();
		return outFolder;

	}

	private String toFileNameList(File files[]) {
		if (files == null || files.length==0) return null;
		ArrayList<String> list = new ArrayList<String>();
		for (File f: files) {
			list.add(f.getName());
		}
		return StringUtils.join(list, ",");
	}
}
