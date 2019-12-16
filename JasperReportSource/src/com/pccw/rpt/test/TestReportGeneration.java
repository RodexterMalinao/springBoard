package com.pccw.rpt.test;

import java.io.FileOutputStream;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.pccw.rpt.service.ReportService;
import com.pccw.util.spring.SpringApplicationContext;

public class TestReportGeneration {

	@Before
	public void init() {
		new ClassPathXmlApplicationContext("ApplicationContext.xml");
	}

	@Test
	public void testEyeAppFormPdf() throws Exception {
		String pdfFileName = "c:\\temp\\testEyeAppForm.pdf";
		FileOutputStream fos = new FileOutputStream(pdfFileName);
		try {
			TreeMap<String, Object> paramMap = new TreeMap<String, Object>();
			paramMap.put("EDIT_BUTTON", "Y");
			paramMap.put("DATA_SOURCE", "ALL_ITEM");
			
			SpringApplicationContext.getBean(ReportService.class).generateReport(
					"lts/eye/delUpgradeEye2AppForm", "en", 
					paramMap, 
					fos, ReportService.EXPORT_TYPE_PDF);
			fos.flush();
			fos.close();
		} catch (Exception e) {
			System.out.println(ExceptionUtils.getFullStackTrace(e));
			throw e;
		} finally {
			fos.close();
		}
		
		String command = "\"C:\\Program Files\\Adobe\\Reader 10.0\\Reader\\AcroRd32.exe\" " + pdfFileName;
	    Runtime.getRuntime().exec(command);
	}

	@Test
	public void testEyeAppFormRtf() throws Exception {
		String pdfFileName = "c:\\temp\\testEyeAppForm.rtf";
		FileOutputStream fos = new FileOutputStream(pdfFileName);
		try {
			TreeMap<String, Object> paramMap = new TreeMap<String, Object>();
			paramMap.put("EDIT_BUTTON", "Y");
			paramMap.put("DATA_SOURCE", "ALL_ITEM");
			
			SpringApplicationContext.getBean(ReportService.class).generateReport(
					"lts/eye/delUpgradeEye2AppForm", "en", 
					paramMap, 
					fos, ReportService.EXPORT_TYPE_RTF);
			fos.flush();
			fos.close();
		} catch (Exception e) {
			System.out.println(ExceptionUtils.getFullStackTrace(e));
			throw e;
		} finally {
			fos.close();
		}
	}

	
	@Test
	public void testEyeAppFormZhPdf() throws Exception {
		String pdfFileName = "c:\\temp\\testEyeAppFormZh.pdf";
		FileOutputStream fos = new FileOutputStream(pdfFileName);
		try {
			TreeMap<String, Object> paramMap = new TreeMap<String, Object>();
			paramMap.put("EDIT_BUTTON", "Y");
			paramMap.put("DATA_SOURCE", "ALL_ITEM");
			
			SpringApplicationContext.getBean(ReportService.class).generateReport(
					"lts/eye/delUpgradeEye2AppForm", "zh_HK", 
					paramMap, 
					fos, ReportService.EXPORT_TYPE_PDF);
			fos.flush();
			fos.close();
		} catch (Exception e) {
			System.out.println(ExceptionUtils.getFullStackTrace(e));
			throw e;
		} finally {
			fos.close();
		}
		
		String command = "\"C:\\Program Files\\Adobe\\Reader 10.0\\Reader\\AcroRd32.exe\" " + pdfFileName;
	    Runtime.getRuntime().exec(command);
	}

	@Test
	public void testEyeAppFormZhRtf() throws Exception {
		String pdfFileName = "c:\\temp\\testEyeAppFormZh.rtf";
		FileOutputStream fos = new FileOutputStream(pdfFileName);
		try {
			TreeMap<String, Object> paramMap = new TreeMap<String, Object>();
			paramMap.put("EDIT_BUTTON", "Y");
			
			SpringApplicationContext.getBean(ReportService.class).generateReport(
					"lts/eye/delUpgradeEye2AppForm", "zh_HK", 
					paramMap, 
					fos, ReportService.EXPORT_TYPE_RTF);
			fos.flush();
			fos.close();
		} catch (Exception e) {
			System.out.println(ExceptionUtils.getFullStackTrace(e));
			throw e;
		} finally {
			fos.close();
		}
	}

	
	@Test
	public void testWqCoverSheetPdf() throws Exception {
		String pdfFileName = "c:\\temp\\testWqCoverSheet.pdf";
		FileOutputStream fos = new FileOutputStream(pdfFileName);
		try {
			TreeMap<String, Object> paramMap = new TreeMap<String, Object>();
			
			SpringApplicationContext.getBean(ReportService.class).generateReport(
					"wq/wqCoverSheet", "en", 
					paramMap, 
					fos, ReportService.EXPORT_TYPE_PDF);
			fos.flush();
			fos.close();
		} catch (Exception e) {
			System.out.println(ExceptionUtils.getFullStackTrace(e));
			throw e;
		} finally {
			fos.close();
		}
		
		String command = "\"C:\\Program Files\\Adobe\\Reader 10.0\\Reader\\AcroRd32.exe\" " + pdfFileName;
	    Runtime.getRuntime().exec(command);
	}

	
	@Test
	public void testReplace() {
		String filename = "/tmp/springboard_files\\RTW1E000040\\EYE_AF_FnS.pdf";
		String wqSerial = "E12021600289";
		filename = StringUtils.replace(filename, "\\", "/");
		filename = filename.substring(filename.lastIndexOf("/") + 1);
		filename = wqSerial + "_" + filename;
		System.out.println(filename);
	}
}