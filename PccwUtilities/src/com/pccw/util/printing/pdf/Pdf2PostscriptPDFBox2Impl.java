package com.pccw.util.printing.pdf;

import java.awt.print.PrinterJob;
import java.io.InputStream;
import java.io.OutputStream;

import javax.print.DocFlavor;
import javax.print.StreamPrintService;
import javax.print.StreamPrintServiceFactory;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.printing.Orientation;
import org.apache.pdfbox.printing.PDFPrinter;
import org.apache.pdfbox.printing.Scaling;

import com.pccw.util.FastByteArrayOutputStream;
import com.pccw.util.printing.JavaPrintingHelper;

public class Pdf2PostscriptPDFBox2Impl implements Pdf2Postscript {

	private static final Logger logger = Logger.getLogger(Pdf2PostscriptPDFBox2Impl.class);

	@Override
	public InputStream convert(InputStream pPdfStream) throws Exception {
		FastByteArrayOutputStream fbaos = new FastByteArrayOutputStream();
		PDDocument document = null;
		try {
			document = PDDocument.load(pPdfStream);

			PrinterJob printJob = PrinterJob.getPrinterJob();
			printJob.setPrintService(JavaPrintingHelper.getPostscriptPrintService(fbaos));

			PDFPrinter pdfPrinter = new PDFPrinter(document, printJob, Scaling.ACTUAL_SIZE, Orientation.AUTO, JavaPrintingHelper.ISO_PAPER_A4, true, 300);
			pdfPrinter.silentPrint();
		} catch (Exception e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
			throw e;
		} finally {
			if (document != null) {
				document.close();
			}
		}
		return fbaos.getInputStream();

	}
}