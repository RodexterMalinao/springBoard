package com.bomwebportal.lts.util.apn;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.util.CellReference;

public class ExcelLoaderHelper {
	private static Logger logger = Logger.getLogger(ExcelLoaderHelper.class);
	private static DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
	
	public static String getStringCellValue(FormulaEvaluator pFormulaEvaluator, HSSFRow pRow, int pCellIndex) throws Exception {
		Cell cell = pRow.getCell(pCellIndex); 
		if (cell == null) {
			return "";
		}
		return getStringCellValue(pFormulaEvaluator, cell);
	}
	
	public static String getStringCellValue(FormulaEvaluator pFormulaEvaluator, Cell pCell) throws Exception {
		try {
			switch (pCell.getCellType()) {
		        case Cell.CELL_TYPE_BOOLEAN:
		            return String.valueOf(pCell.getBooleanCellValue());
		        case Cell.CELL_TYPE_NUMERIC:
		        	if (HSSFDateUtil.isCellDateFormatted(pCell)) {
		        	    return dateFormat.format(HSSFDateUtil.getJavaDate(pCell.getNumericCellValue()));
		        	}
		        	if (((long) pCell.getNumericCellValue()) != ((double) pCell.getNumericCellValue())) {
		        		return String.valueOf((double) pCell.getNumericCellValue()).trim();
		        	}
		        	return String.valueOf((long) pCell.getNumericCellValue()).trim();
		        case Cell.CELL_TYPE_STRING:
		        	return StringUtils.defaultIfEmpty(pCell.getStringCellValue(), "").trim();
		        case Cell.CELL_TYPE_BLANK:
		        	return "";
		        case Cell.CELL_TYPE_FORMULA:
					switch (pFormulaEvaluator.evaluateFormulaCell(pCell)) {
				        case Cell.CELL_TYPE_BOOLEAN:
				            return String.valueOf(pCell.getBooleanCellValue());
				        case Cell.CELL_TYPE_NUMERIC:
				        	if (((long) pCell.getNumericCellValue()) != ((double) pCell.getNumericCellValue())) {
				        		return String.valueOf((double) pCell.getNumericCellValue()).trim();
				        	}
				        	return String.valueOf((long) pCell.getNumericCellValue()).trim();
				        case Cell.CELL_TYPE_STRING:
				        	return StringUtils.defaultIfEmpty(pCell.getStringCellValue(), "").trim();
				        case Cell.CELL_TYPE_BLANK:
				        	return "";
				        case Cell.CELL_TYPE_ERROR:
							System.out.println("CELL: " + new CellReference(pCell).formatAsString() + " / CELL ERROR: " + pCell.getErrorCellValue() + " CELL VALUE: " + pCell.getStringCellValue());
				        	return "";
				        case Cell.CELL_TYPE_FORMULA:
				        	System.out.println("CELL contains another Cell Formula");
						default:
				        	return "";
					}
				default:
		        	return "";
			}
		} catch (Exception e) {
			logger.error("CELL: " + new CellReference(pCell).formatAsString() + " / CELL TYPE: " + pCell.getCellType() +  " CELL VALUE: " + pCell.getStringCellValue() + "CELL FORMULA: " + pCell.getCellFormula() + "\n" + 
							ExceptionUtils.getFullStackTrace(e));
			throw e;
		}
	}
}
