package com.bomwebportal.ims.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.ims.constant.ImsConstants;
import com.bomwebportal.ims.dto.ui.OrderImsUI;
import com.bomwebportal.util.BomWebPortalConstant;
import com.bomwebportal.util.uENC;


public class GetQRJpgController implements Controller {
	protected final Log logger = LogFactory.getLog(getClass());
	private String dataFilePath;	

	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		OrderImsUI orderIms = (OrderImsUI) request.getSession().getAttribute(ImsConstants.IMS_ORDER);
		
		String tempSbid = "";
		String signOffDate = "";
		String cashPayMthd = "";
		if (request.getParameter("sbid") != null) {
			tempSbid = request.getParameter("sbid");
		}
		if (request.getParameter("SignOffDate") != null) {
			signOffDate = request.getParameter("SignOffDate");
			if (!StringUtils.isEmpty(signOffDate) && signOffDate.length() >= 6) {
				signOffDate = signOffDate.substring(0, 6);
			}
			if (StringUtils.isBlank(signOffDate)) {
				signOffDate = "";
			}
		}
		if (request.getParameter("cashPayMthd") != null) {
			cashPayMthd = request.getParameter("cashPayMthd");
		}
		
		String orderId = "";
		if (!StringUtils.isEmpty(tempSbid)) {
			if (tempSbid.length() > 11) {
				orderId = uENC.decAES(BomWebPortalConstant.CEKS_ENC_KEY, tempSbid);
			} else {
				orderId = tempSbid;
			}
		}
		
		if (StringUtils.isEmpty(orderId)) {
			if (orderIms != null) {
				orderId = orderIms.getOrderId();
			}
		}
		
		String withYyyyMmPath = dataFilePath + "ims" +File.separator + signOffDate + File.separator + orderId + File.separator + orderId + "_QR.jpg";
		String withoutYyyyMmPath = dataFilePath + orderId + File.separator + orderId + "_QR.jpg";
		if (!StringUtils.isEmpty(cashPayMthd)) {
			if ("fps".equals(cashPayMthd)) {
				withYyyyMmPath = dataFilePath + "ims" +File.separator + signOffDate + File.separator + orderId + File.separator + orderId + "_FPS_QR.jpg";
				withoutYyyyMmPath = dataFilePath + orderId + File.separator + orderId + "_FPS_QR.jpg";
			}
		}
		
		File QRCodeFile;
		if (!StringUtils.isEmpty(orderId)) {
			if (!StringUtils.isEmpty(signOffDate)) {
				logger.info("have signOffDate Parameter: " + signOffDate + ", orderId:" + orderId);
				QRCodeFile = new File(withYyyyMmPath);
				if (!QRCodeFile.exists()) {
					logger.info("withYyyyMmPath not exists:" + withYyyyMmPath);
					QRCodeFile = new File(withoutYyyyMmPath);
					if (!QRCodeFile.exists()) {
						QRCodeFile = null;
						logger.info("withoutYyyyMmPath not exists:" + withoutYyyyMmPath);
					} else {
						logger.info("withoutYyyyMmPath exists:" + withoutYyyyMmPath);
					}
				} else {
					logger.info("withYyyyMmPath exists:" + withYyyyMmPath);
				}
			} else {
				logger.info("do not have signOffDate Parameter, orderId: " + orderId);
				QRCodeFile = new File(withoutYyyyMmPath);
				if (!QRCodeFile.exists()) {
					QRCodeFile = null;
					logger.info("withoutYyyyMmPath not exists:" + withoutYyyyMmPath);
				} else {
					logger.info("withoutYyyyMmPath exists:" + withoutYyyyMmPath);
				}
			}
		} else {
			QRCodeFile = null;
			logger.info("Invalid: No order id");
		}
		
		if (QRCodeFile != null && QRCodeFile.exists()) {
			logger.info("File exist and Path: " + QRCodeFile.getPath());
			FileInputStream getJpeg = new FileInputStream(QRCodeFile);
			
			response.setHeader("Cache-Control", "no-store");
	        response.setHeader("Pragma", "no-cache");
	        response.setDateHeader("Expires", 0);
	        response.setContentType("image/jpeg");
	        
	        ServletOutputStream responseOutputStream = response.getOutputStream();
	        responseOutputStream.write(IOUtils.toByteArray(getJpeg)); 
	        responseOutputStream.flush();
	        responseOutputStream.close();
	        
	        getJpeg.close();
		} else {
			logger.debug("No file " + QRCodeFile.getPath() + " found.");
			throw new AppRuntimeException("No file found.");
		}
        
		return null;
	}

	public void setDataFilePath(String dataFilePath) {
		this.dataFilePath = dataFilePath;
	}

	public String getDataFilePath() {
		return dataFilePath;
	}
}
