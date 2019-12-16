package com.pccw.util.printing.pdf;

import java.awt.print.PrinterJob;
import java.io.InputStream;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.apache.pdfbox.pdmodel.PDDocument;

import com.pccw.util.FastByteArrayOutputStream;
import com.pccw.util.printing.JavaPrintingHelper;

public class Pdf2PostscriptPDFBox1Impl implements Pdf2Postscript {

	private static final Logger logger = Logger.getLogger(Pdf2PostscriptPDFBox1Impl.class);

	@Override
	public InputStream convert(InputStream pPdfStream) throws Exception {
		FastByteArrayOutputStream fbaos = new FastByteArrayOutputStream();

		// Open the PDF file
		PDDocument document = null;
		try {
			document = PDDocument.load(pPdfStream);

			PrinterJob printJob = PrinterJob.getPrinterJob();
			printJob.setPrintService(JavaPrintingHelper.getPostscriptPrintService(fbaos));

			document.silentPrint(printJob);
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
