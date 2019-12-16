package com.bomwebportal.util;

import java.io.OutputStream;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRRtfExporter;
import net.sf.jasperreports.engine.util.JRLoader;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ReportUtil {
	
	protected static final Log logger = LogFactory.getLog(ReportUtil.class);

	public static void generateRtfReport(String pReportName, JRDataSource pDS, OutputStream pOutputStream) throws Exception {
		// Fix the exception throw in AIX server
		//System.setProperty("java.awt.headless", "true");
		
		JasperPrint jasperPrint = fillReport(pReportName, pDS);
		exportReportToRtf(jasperPrint, pOutputStream);
	}

	
	public static void generatePdfReport(String pReportName, JRDataSource pDS, OutputStream pOutputStream) throws Exception {
		// Fix the exception throw in AIX server
		//System.setProperty("java.awt.headless", "true");
		
		JasperPrint jasperPrint = fillReport(pReportName, pDS);
		exportReportToPdf(jasperPrint, pOutputStream);
	}

	private static JasperPrint fillReport(String reportName, JRDataSource pDS) throws Exception {
		JasperPrint jasperPrint = null;
		
		// Generate jasper print
		try {
			JasperReport jasperReport = (JasperReport) JRLoader.loadObject(ReportUtil.class.getClassLoader().getResourceAsStream(reportName + ".jasper"));
			jasperPrint = JasperFillManager.fillReport(jasperReport, null, pDS) ;
		}
		catch (Exception e) {
			logger.error("Failure in generating JasperPrint.", e);
			throw e;
		}

		return jasperPrint;
	}

	private static void exportReportToPdf(JasperPrint pJasperPrint, OutputStream pOutputStream) throws Exception {
		exportReport(pJasperPrint, pOutputStream, new JRPdfExporter());
	}

	private static void exportReportToRtf(JasperPrint pJasperPrint, OutputStream pOutputStream) throws Exception {
		exportReport(pJasperPrint, pOutputStream, new JRRtfExporter());
	}

	
	private static void exportReport(JasperPrint pJasperPrint, OutputStream pOutputStream, JRExporter exporter) throws Exception {
		exporter.setParameter(JRExporterParameter.JASPER_PRINT, pJasperPrint);
		exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, pOutputStream);

		try {
			exporter.exportReport();
		}
		catch (JRException e) {
			logger.error("Failure in exporting report.", e);
			throw e;
		}
	}
}
