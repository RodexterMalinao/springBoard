package com.bomwebportal.web;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.dto.OrdDocDTO;
import com.bomwebportal.service.OrdDocService;
import com.bomwebportal.service.OrdDocService.AuditLogActionCd;
import com.bomwebportal.service.OrderService;

public class RequiredProofDownloadController implements Controller {
	protected final Log logger = LogFactory.getLog(getClass());
	
	private enum Result {
		SUCCESS
		, INVALID_PARAMETER
		, FILE_NOT_FOUND
		;
	}
	
	private String saveDirectory;
	private OrderService orderService;
	private OrdDocService ordDocService;

	public String getSaveDirectory() {
		return saveDirectory;
	}

	public void setSaveDirectory(String saveDirectory) {
		this.saveDirectory = saveDirectory;
	}

	public OrderService getOrderService() {
		return orderService;
	}

	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}

	public OrdDocService getOrdDocService() {
		return ordDocService;
	}

	public void setOrdDocService(OrdDocService ordDocService) {
		this.ordDocService = ordDocService;
	}

	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		BomSalesUserDTO salesUserDto = (BomSalesUserDTO) request.getSession().getAttribute("bomsalesuser");
		String username = salesUserDto.getUsername();
		String orderId = ServletRequestUtils.getRequiredStringParameter(request, "orderId");
		String docType = ServletRequestUtils.getRequiredStringParameter(request, "docType");
		int seqNum = ServletRequestUtils.getRequiredIntParameter(request, "seqNum");
		if (logger.isInfoEnabled()) {
			logger.info("orderId: " + orderId + ", docType: " + docType + ", seqNum: " + seqNum + ", username: " + username);
		}
		OrdDocDTO ordDocDto = this.ordDocService.getOrdDoc(orderId, docType, seqNum);
		if (ordDocDto == null) {
			if (logger.isDebugEnabled()) {
				logger.debug("ordDocDto is null");
			}
			return this.getResult(Result.INVALID_PARAMETER);
		}
		// TODO: save a audit record
		this.ordDocService.insertAuditLog(orderId, docType, seqNum, AuditLogActionCd.AD02, username);

		File baseDirectory = this.getBaseDirectory(ordDocDto.getOrderId());
		// server may archive old files
		if (!baseDirectory.exists() || !baseDirectory.isDirectory()) {
			if (logger.isDebugEnabled()) {
				logger.debug("baseDirectory: " + baseDirectory.getAbsolutePath() + " invalid");
			}
			return this.getResult(Result.FILE_NOT_FOUND);
		}

		// wrong db value or archived?
		if (StringUtils.isBlank(ordDocDto.getFilePathName())) {
			if (logger.isDebugEnabled()) {
				logger.debug("filePathName is blank");
			}
			return this.getResult(Result.FILE_NOT_FOUND);
		}

		File file = null;
		String fileName = ordDocDto.getFilePathName();
		if (StringUtils.equalsIgnoreCase("M052", docType) || StringUtils.equalsIgnoreCase("M053", docType) || StringUtils.equalsIgnoreCase("M055", docType)) {
			baseDirectory =  new File(baseDirectory.getAbsolutePath() + "/HKTCare");
			fileName = ordDocDto.getFilePathName().substring(8);
		} 
		file = this.getFile(baseDirectory, fileName);
		if (file == null) {
			if (logger.isDebugEnabled()) {
				logger.debug("baseDirectory: " + baseDirectory.getAbsolutePath() + ", targetFile: " + fileName + " invalid");
			}
			return this.getResult(Result.FILE_NOT_FOUND);
		}
		
		String mimeType = request.getSession().getServletContext().getMimeType(file.getName());
		OutputStream os = response.getOutputStream();
		BufferedInputStream ins = null;
		try {
			ins = new BufferedInputStream(new FileInputStream(file));
			response.setContentType(mimeType);
			response.setHeader("Content-Disposition", "attachment; filename=" + file.getName());
			FileCopyUtils.copy(ins, os);
			ins.close();
			ins = null;
			os.flush();
			os.close();
			os = null;
		} finally {
			if (ins != null) {
				try {
					ins.close();
				} catch (IOException ioe) {}
				ins = null;
			}
			if (os != null) {
				try {
					os.flush();
					os.close();
				} catch (IOException ioe) {}
				os = null;
			}
		}
		return this.getResult(Result.SUCCESS);
	}
	
	private File getBaseDirectory(String orderId) {
		return new File(saveDirectory + File.separator + orderId);
	}
	
	private File getFile(File baseDirectory, String filePathName) {
		File[] files = baseDirectory.listFiles();
		if (this.isEmpty(files)) {
			return null;
		}
		for (File file : files) {
			if (file.getName().equals(filePathName)) {
				return file;
			}
		}
		return null;
	}
	
	private <T> boolean isEmpty(T[] values) {
		return values == null || values.length == 0;
	}
	
	private ModelAndView getResult(Result result) {
		switch (result) {
		case SUCCESS:
			// download file directly from IOStream
			return null;
		case INVALID_PARAMETER:
		case FILE_NOT_FOUND:
		default:
			ModelAndView view = new ModelAndView("requiredproofdownload");
			view.addObject(result.toString(), true);
			return view;
		}
	}
}
