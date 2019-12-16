package com.bomwebportal.util;

import java.awt.Color;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.Character.UnicodeBlock;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bomwebportal.exception.AppRuntimeException;
import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Image;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.AcroFields;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.ColumnText;
import com.lowagie.text.pdf.PRAcroForm;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfCopy;
import com.lowagie.text.pdf.PdfImportedPage;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;
import com.lowagie.text.pdf.PdfTable;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.pdf.RandomAccessFileOrArray;
import com.lowagie.text.pdf.SimpleBookmark;
import com.lowagie.text.pdf.TextField;
import com.lowagie.text.pdf.codec.GifImage;
import com.lowagie.text.pdf.codec.TiffImage;
import com.pccw.util.FastByteArrayOutputStream;
import com.pccw.util.printing.JavaPrintingHelper;

public class PdfUtil {	
	protected static final Log logger = LogFactory.getLog(PdfUtil.class);
	public static final int ALLOW_ASSEMBLY = PdfWriter.ALLOW_ASSEMBLY;
	public static final int ALLOW_COPY = PdfWriter.ALLOW_COPY;
	public static final int ALLOW_DEGRADED_PRINTING = PdfWriter.ALLOW_DEGRADED_PRINTING;
	public static final int ALLOW_FILL_IN = PdfWriter.ALLOW_FILL_IN;
	public static final int ALLOW_MODIFY_ANNOTATIONS = PdfWriter.ALLOW_MODIFY_ANNOTATIONS;
	public static final int ALLOW_MODIFY_CONTENTS = PdfWriter.ALLOW_MODIFY_CONTENTS;
	public static final int ALLOW_PRINTING = PdfWriter.ALLOW_PRINTING;
	public static final int ALLOW_SCREENREADERS = PdfWriter.ALLOW_SCREENREADERS;
	
	private static BaseFont BASE_FONT_EN = null;
	private static BaseFont BASE_FONT_ZH_HK = null;
	private static final float TOP_BOTTOM_MARGIN = 5;
	private static final float LEFT_RIGHT_MARGIN = 5;
	private static final float PRINT_DIMENSION_TOP_BOTTOM_MARGIN = (float) JavaPrintingHelper.mmToPrintingDimension(TOP_BOTTOM_MARGIN);
	private static final float PRINT_DIMENSION_LEFT_RIGHT_MARGIN = (float) JavaPrintingHelper.mmToPrintingDimension(LEFT_RIGHT_MARGIN);
	
	private static final Set<UnicodeBlock> CHINESE_UNI_CODE_BLOCKS = new HashSet<UnicodeBlock>();
	
	private static final TreeMap<String, Color> HTML_COLOR_MAP = new TreeMap<String, Color>();
	
	static {
		
		CHINESE_UNI_CODE_BLOCKS.add(UnicodeBlock.CJK_COMPATIBILITY);
		CHINESE_UNI_CODE_BLOCKS.add(UnicodeBlock.CJK_COMPATIBILITY_FORMS);
		CHINESE_UNI_CODE_BLOCKS.add(UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS);
		CHINESE_UNI_CODE_BLOCKS.add(UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS_SUPPLEMENT);
		CHINESE_UNI_CODE_BLOCKS.add(UnicodeBlock.CJK_RADICALS_SUPPLEMENT);
		CHINESE_UNI_CODE_BLOCKS.add(UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION);
		CHINESE_UNI_CODE_BLOCKS.add(UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS);
		CHINESE_UNI_CODE_BLOCKS.add(UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A);
		CHINESE_UNI_CODE_BLOCKS.add(UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B);
		CHINESE_UNI_CODE_BLOCKS.add(UnicodeBlock.KANGXI_RADICALS);
		CHINESE_UNI_CODE_BLOCKS.add(UnicodeBlock.IDEOGRAPHIC_DESCRIPTION_CHARACTERS);
		
		HTML_COLOR_MAP.put("black", new Color(0x000000));
		HTML_COLOR_MAP.put("green", new Color(0x008000));
		HTML_COLOR_MAP.put("silver", new Color(0xC0C0C0));
		HTML_COLOR_MAP.put("lime", new Color(0x00FF00));
		HTML_COLOR_MAP.put("darkgrey", new Color(0xA9A9A9));
		HTML_COLOR_MAP.put("lightgrey", new Color(0xD3D3D3));
		HTML_COLOR_MAP.put("gray", new Color(0x808080));
		HTML_COLOR_MAP.put("olive", new Color(0x808000));
		HTML_COLOR_MAP.put("white", new Color(0xFFFFFF));
		HTML_COLOR_MAP.put("yellow", new Color(0xFFFF00));
		HTML_COLOR_MAP.put("maroon", new Color(0x800000));
		HTML_COLOR_MAP.put("navy", new Color(0x000080));
		HTML_COLOR_MAP.put("red", new Color(0xFF0000));
		HTML_COLOR_MAP.put("blue", new Color(0x0000FF));
		HTML_COLOR_MAP.put("purple", new Color(0x800080));
		HTML_COLOR_MAP.put("teal", new Color(0x008080));
		HTML_COLOR_MAP.put("fuchsia", new Color(0xFF00FF));
		HTML_COLOR_MAP.put("aqua", new Color(0x00FFFF));
		
		try {
			byte[] fontContent = RandomAccessFileOrArray.InputStreamToArray(PdfUtil.class.getClassLoader().getResourceAsStream("fonts/arial.ttf"));
			BASE_FONT_EN = BaseFont.createFont("PCCW-Arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED, BaseFont.NOT_CACHED, fontContent, null);
			
			fontContent = RandomAccessFileOrArray.InputStreamToArray(PdfUtil.class.getClassLoader().getResourceAsStream("fonts/PCCW-zh-Ming.ttf"));
			BASE_FONT_ZH_HK = BaseFont.createFont("PCCW-zh-Ming.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED, BaseFont.NOT_CACHED, fontContent, null);
		} catch (Exception ignore) {
			// SYSTEM will use PDF Default Font (Font.HELVETICA)
			logger.error(ExceptionUtils.getFullStackTrace(ignore));
			
		}
	}
	
	public static void concatPDFs(List<InputStream> pSourcePdfInputStreamList,
			OutputStream pOutputStream, boolean pIsAddPageNum, boolean pIsCopMerge) {
		concatPDFs(pSourcePdfInputStreamList, pOutputStream, pIsAddPageNum, false, null, null, null, null, null, pIsCopMerge);
	}
	
	public static void concatPDFs(List<InputStream> pSourcePdfInputStreamList,
			OutputStream pOutputStream, boolean pIsAddPageNum) {
		concatPDFs(pSourcePdfInputStreamList, pOutputStream, pIsAddPageNum, true, null);
	}

	public static void concatPDFs(List<InputStream> pSourcePdfInputStreamList,
			OutputStream pOutputStream, boolean pIsAddPageNum, PdfHeaderFooter pPdfHeaderFooter) {
		concatPDFs(pSourcePdfInputStreamList, pOutputStream, pIsAddPageNum, true, pPdfHeaderFooter);
	}
	
	public static void concatPDFs(List<InputStream> pSourcePdfInputStreamList,
			OutputStream pOutputStream, boolean pIsAddPageNum,
			boolean pIsMergePageNum, PdfHeaderFooter pPdfHeaderFooter) {
		concatPDFs(pSourcePdfInputStreamList, pOutputStream, pIsAddPageNum, pIsMergePageNum, pPdfHeaderFooter, null);
	}
	
	public static void concatPDFs(List<InputStream> pSourcePdfInputStreamList,
			OutputStream pOutputStream, boolean pIsAddPageNum,
			boolean pIsMergePageNum, PdfHeaderFooter pPdfHeaderFooter, Integer pPdfPermission) {
		concatPDFs(pSourcePdfInputStreamList, pOutputStream, pIsAddPageNum, pIsMergePageNum, pPdfHeaderFooter, pPdfPermission, null, null);
	}
	
	public static void concatPDFs(List<InputStream> pSourcePdfInputStreamList,
			OutputStream pOutputStream, boolean pIsAddPageNum,
			boolean pIsMergePageNum, PdfHeaderFooter pPdfHeaderFooter,
			Integer pPdfPermission, String pUserPin, String pOwnerPin) {
		concatPDFs(pSourcePdfInputStreamList, pOutputStream, pIsAddPageNum, pIsMergePageNum, null, pPdfHeaderFooter, pPdfPermission, pUserPin, pOwnerPin, false);
	}
	
	public static void concatPDFs(List<InputStream> pSourcePdfInputStreamList,
			OutputStream pOutputStream, boolean pIsAddPageNum,
			boolean pIsMergePageNum, String pWatermark, PdfHeaderFooter pPdfHeaderFooter,
			Integer pPdfPermission, String pUserPin, String pOwnerPin) {
		concatPDFs(pSourcePdfInputStreamList, pOutputStream, pIsAddPageNum, pIsMergePageNum, pWatermark, pPdfHeaderFooter, pPdfPermission, pUserPin, pOwnerPin, false);
	}
	
	public static void concatPDFs(List<InputStream> pSourcePdfInputStreamList,
			OutputStream pOutputStream, boolean pIsAddPageNum,
			boolean pIsMergePageNum, String pWatermark, PdfHeaderFooter pPdfHeaderFooter,
			Integer pPdfPermission, String pUserPin, String pOwnerPin, boolean pIsCopMerge) {

		Document document = new Document();
		PdfCopy writer = null;
		try {
			ArrayList<PDF> pdfList = new ArrayList<PDF>();
			int totalPages = 0;
			PDF sourcePdf = null;
			for (InputStream pdfStream : pSourcePdfInputStreamList) {
				sourcePdf = new PDF(pdfStream);
				pdfList.add(sourcePdf);
				totalPages += sourcePdf.getNumberOfPages();
			}
			
			// Create a writer for the outputstream
			writer = new PdfCopy(document, pOutputStream);

			if (pPdfPermission != null || StringUtils.isNotBlank(pUserPin) || StringUtils.isNotBlank(pOwnerPin)) {
				writer.setEncryption(
						pUserPin == null ? null : pUserPin.getBytes(), 
						pOwnerPin == null ? null : pOwnerPin.getBytes(), 
						pPdfPermission, PdfWriter.STANDARD_ENCRYPTION_128);
			}

			document.open();

			// data

			int pageOfMergedPdf = 0;
			ArrayList<HashMap<String,Object>> masterBookMarkList = new ArrayList<HashMap<String,Object>>();
			PdfReader reader = null;
			PRAcroForm form = null;
	        
			// Loop through the PDF files and add to the output.
			for (PDF pdf : pdfList) {
				
				reader = pdf.getPdfReader();
				
				/**
				 * Replace all the local named links with the actual
				 * destinations.
				 */
				reader.consolidateNamedDestinations();

				if (pdf.isContainBookmark()) {
					pdf.mergeBookmark(pageOfMergedPdf, masterBookMarkList);
				}
				
				for (int pageOfCurrentPdf = 1; pageOfCurrentPdf <= pdf.getNumberOfPages(); pageOfCurrentPdf++) {
					// Create a new page in the target for each source page.
					document.newPage();
					pageOfMergedPdf++;

					pdf.addPageToMasterPdf(writer, pageOfCurrentPdf, pIsAddPageNum, pIsMergePageNum, pageOfMergedPdf, totalPages, pWatermark, pPdfHeaderFooter);
				}
				
				if (pIsCopMerge) {
					if (pageOfMergedPdf % 2 != 0) {
						document.newPage();
						//writer.setPageEmpty(false);
						writer.addPage(reader.getPageSize(1), reader.getPageRotation(1));
						pageOfMergedPdf++;
					}
				}
				
				/**
				* This will get the documents acroform.
				* This will return null if no acroform is part of the document.
				*
				* Acroforms are PDFs that have been turned into fillable forms.
				*/
				form = reader.getAcroForm();
				if (form != null) {
					((PdfCopy) writer).copyAcroForm(reader);
				}
			}

			/**
			* After looping through all the files, add the master bookmarklist.
			* If individual PDF documents had separate bookmarks, master bookmark
			* list will contain a combination of all those bookmarks in the
			* merged document.
			*/
			if (!masterBookMarkList.isEmpty()) {
				writer.setOutlines(masterBookMarkList);
			}
			
			pOutputStream.flush();
			document.close();
			pOutputStream.close();
		} catch (Exception e) {
			logger.error("Failure in concat pdf files.\n" + ExceptionUtils.getFullStackTrace(e));
		} finally {
			if (document.isOpen()) {
				document.close();
			}
			try {
				if (pOutputStream != null) {
					pOutputStream.close();
				}
			} catch (IOException ioe) {
				logger.error(ExceptionUtils.getFullStackTrace(ioe));
			}
		}
	}
	
	public static void fillPdfForm(String pFormPDF, Map<String, String> pFormValueMap, OutputStream pOutputStream) throws Exception {
		fillPdfForm(pFormPDF, pFormValueMap, null, pOutputStream);
	}
	
	public static void fillPdfForm(String pFormPDF, Map<String, String> pFormValueMap, Map<String, byte[]> pFormImageValueMap, OutputStream pOutputStream) throws Exception {
		fillPdfForm(new PdfReader(pFormPDF), pFormValueMap, pFormImageValueMap, pOutputStream);
	}

	public static void fillPdfForm(InputStream pFormPdfInputStream, Map<String, String> pFormValueMap, OutputStream pOutputStream) throws Exception {
		fillPdfForm(new PdfReader(pFormPdfInputStream), pFormValueMap, pOutputStream);
	}

	public static void fillPdfForm(InputStream pFormPdfInputStream, Map<String, String> pFormValueMap, Map<String, byte[]> pFormImageValueMap, OutputStream pOutputStream) throws Exception {
		fillPdfForm(new PdfReader(pFormPdfInputStream), pFormValueMap, pFormImageValueMap, pOutputStream);
	}

	private static void fillPdfForm(PdfReader pReader, Map<String, String> pFormValueMap, OutputStream pOutputStream) throws Exception {
		fillPdfForm(pReader, pFormValueMap, null, pOutputStream);
	}
	
	private static void fillPdfForm(PdfReader pReader, Map<String, String> pFormValueMap, Map<String, byte[]> pFormImageValueMap, OutputStream pOutputStream) throws Exception {
        PdfStamper filledOutForm = new PdfStamper(pReader, pOutputStream);
            
        AcroFields form = filledOutForm.getAcroFields();
        Iterator<Map.Entry<String, String>> it = pFormValueMap.entrySet().iterator();
        Map.Entry<String, String> entry = null;
        AcroFields.Item acroFieldItem  = null;
        float[] fieldPos = null;
        Rectangle rect = null;
        PdfContentByte cb = null;
        int pageNum = -1;

        for (;it.hasNext();) {
        	entry = it.next();
        	acroFieldItem = form.getFieldItem(entry.getKey());
        	if (acroFieldItem == null) {
	    		 continue;
	    	}
        	pageNum = acroFieldItem.getPage(0);
        	cb = filledOutForm.getOverContent(pageNum);
        	fieldPos = form.getFieldPositions(entry.getKey());
        	
        	addTextToPdf(cb, fieldPos[1], fieldPos[2], fieldPos[3], fieldPos[4], 0, entry.getValue(), pFormValueMap.get(entry.getKey() + ".css"), pReader.getPageSize(pageNum));
			form.removeField(entry.getKey());
        }
        
        if (pFormImageValueMap != null) {
             Image img = null;
             
         	 Map.Entry<String, byte[]> imgEntry = null;
             Iterator<Map.Entry<String, byte[]>> imgIterator = pFormImageValueMap.entrySet().iterator();
             for (;imgIterator.hasNext();) {
            	 imgEntry = imgIterator.next();
            	 acroFieldItem = form.getFieldItem(imgEntry.getKey());
            	 if (acroFieldItem == null) {
            		 continue;
            	 }
            	 pageNum = acroFieldItem.getPage(0);
            	 cb = filledOutForm.getOverContent(pageNum);
                 fieldPos = form.getFieldPositions(imgEntry.getKey());
                 rect = new Rectangle(fieldPos[1], fieldPos[2], fieldPos[3], fieldPos[4]); 
                 img = Image.getInstance(imgEntry.getValue());
                 img.scaleToFit(rect.getWidth(), rect.getHeight());
                 img.setAbsolutePosition(fieldPos[1] 
                                         + (rect.getWidth() - img.getScaledWidth()) / 2,
                                         fieldPos[2]
                                         + (rect.getHeight() - img.getScaledHeight()) / 2);
                 cb.addImage(img);
     			 form.removeField(imgEntry.getKey());
             }
        }
        
        
        @SuppressWarnings("unchecked")
		String[] fields = (String[]) form.getFields().keySet().toArray(new String[0]);
        if (fields != null) {
            for (String fieldName : fields) {
            	form.removeField(fieldName);
    		}
        }
        
        filledOutForm.close();
	}

	public static void copyPdfFormFields(String pSrc, String pDestination, OutputStream pOutputStream) throws Exception {
		copyPdfFormFields(new PdfReader(pSrc), new PdfReader(pDestination), pOutputStream, 0, 0);
	}

	public static void copyPdfFormFields(String pSrc, String pDestination, OutputStream pOutputStream, float pPosXoffset, float pPosYoffset) throws Exception {
		copyPdfFormFields(new PdfReader(pSrc), new PdfReader(pDestination), pOutputStream, pPosXoffset, pPosYoffset);
	}

	private static void copyPdfFormFields(PdfReader pSrcReader, PdfReader pDestinationReader, OutputStream pOutputStream, float pPosXoffset, float pPosYoffset) throws Exception {
        FastByteArrayOutputStream srcOs = new FastByteArrayOutputStream();
        PdfStamper srcStamper = new PdfStamper(pSrcReader, srcOs);
        AcroFields srcForm = srcStamper.getAcroFields();
        PdfStamper destStamper = new PdfStamper(pDestinationReader, pOutputStream);
        
        @SuppressWarnings("unchecked")
		String[] fields = (String[]) srcForm.getFields().keySet().toArray(new String[0]);
        if (fields != null) {
        	TextField field = null;
            float[] fieldPos = null;
            AcroFields.Item srcAcroFieldItem  = null;
            for (String fieldName : fields) {
            	srcAcroFieldItem = srcForm.getFieldItem(fieldName);
            	fieldPos = srcForm.getFieldPositions(fieldName);
            	field = new TextField(destStamper.getWriter(), new Rectangle(fieldPos[1] + pPosXoffset, fieldPos[2] + pPosYoffset, fieldPos[3] + pPosXoffset, fieldPos[4] + pPosYoffset), fieldName);
            	destStamper.addAnnotation(field.getTextField(), srcAcroFieldItem.getPage(0));
    		}
        }

        srcStamper.close();
        destStamper.close();
	}
	
	private static BaseFont getBaseFont(String pValue) {
		if (StringUtils.isBlank(pValue)) {
			return BASE_FONT_EN;
		}
		for (char c : pValue.toCharArray()) {
		    if (CHINESE_UNI_CODE_BLOCKS.contains(UnicodeBlock.of(c))) {
		    	return BASE_FONT_ZH_HK;
		    }
		}
		return BASE_FONT_EN;
	}
	
/**
 * 
 * @param cb
 * @param pPosX
 * @param pPosY
 * @param pRotation
 * @param pText
 * @param pCss
 * 		text-align:center
 *		text-align:left
 *		text-align:right
 *		font-size:9
 *      font-weight:normal
 *      font-weight:bold
 *      font-style:normal
 *      font-style:italic
 *      color:
 */
	public static void addTextToPdf(PdfContentByte pPdfContentByte, float pPosX, float pPosY, float pPosRX, float pPosRY, float pRotation, String pText, String pCss, Rectangle pPageSize) {
		if (StringUtils.isBlank(pText)) {
			return;
		}
		if (pPosRX==0) {
			pPosRX = pPageSize.getRight() - PRINT_DIMENSION_LEFT_RIGHT_MARGIN;
		}
		float fontSize = 9;
		int fontStyle = Font.NORMAL;
		int align = Element.ALIGN_LEFT;
		Color fontColor = Color.BLACK;
		int verticalAlign = Element.ALIGN_BOTTOM;
		boolean wrapText = false;
		String supScript = null;
		Float strokeWidth = 1.0f;
		
		if (StringUtils.isNotBlank(pCss)) {
			String[] fontAttributes = pCss.split(";");
			String[] fontAttribute = null;
			for (String fontAttributeCssString : fontAttributes) {
				if (StringUtils.isBlank(fontAttributeCssString)) {
					continue;
				}

				fontAttribute = fontAttributeCssString.trim().split(":");
				if (StringUtils.isBlank(fontAttribute[0]) || StringUtils.isBlank(fontAttribute[1])) {
					continue;
				}
				if ("font-size".equals(fontAttribute[0])) {
					fontSize = Float.parseFloat(fontAttribute[1].trim());
				} else if ("text-align".equals(fontAttribute[0]) && "center".equals(fontAttribute[1].trim())) {
					align = Element.ALIGN_CENTER;
				} else if ("text-align".equals(fontAttribute[0]) && "left".equals(fontAttribute[1].trim())) {
					align = Element.ALIGN_LEFT;
				} else if ("text-align".equals(fontAttribute[0]) && "right".equals(fontAttribute[1].trim())) {
					align = Element.ALIGN_RIGHT;
				} else if ("vertical-align".equals(fontAttribute[0])  && "top".equals(fontAttribute[1].trim())) {
					verticalAlign = Element.ALIGN_TOP;
				} else if ("vertical-align".equals(fontAttribute[0])  && "middle".equals(fontAttribute[1].trim())) {
					verticalAlign = Element.ALIGN_MIDDLE;
				} else if ("color".equals(fontAttribute[0])) {
					fontColor = HTML_COLOR_MAP.get(fontAttribute[1].trim().toLowerCase());
					if (fontColor == null) {
						fontColor = Color.BLACK;
					}
				} else if ("word-wrap".equals(fontAttribute[0])) {
					verticalAlign = Element.ALIGN_TOP;
					wrapText=true;
				} else if ("sup".equals(fontAttribute[0])) {
					supScript = fontAttribute[1];
				}
				else if ("width".equals(fontAttribute[0])) {
					strokeWidth = Float.valueOf(fontAttribute[1]);
				}
				
				
			}
			
			if (pCss.contains("font-weight:bold") && pCss.contains("font-style:italic")) {
				fontStyle = Font.BOLDITALIC;
			} else if (pCss.contains("font-weight:bold")) {
				fontStyle = Font.BOLD;
			} else if (pCss.contains("font-style:italic")) {
				fontStyle = Font.ITALIC;
			}
		}

		Font font = new Font(getBaseFont(pText), fontSize, fontStyle, fontColor);
		BaseFont bf = font.getCalculatedBaseFont(false);
		Phrase phrase = new Phrase(pText, font);
		if (StringUtils.isNotBlank(supScript)) {
			Font supFont = new Font(bf);
			supFont.setStyle(Font.BOLDITALIC);
			supFont.setSize(6);
			Chunk superscript = new Chunk(supScript);
			superscript.setTextRise(5f);
			superscript.setFont(supFont);
			phrase.add(superscript);
		}
		
		float lineHeight = bf.getAscentPoint(pText, fontSize) - bf.getDescentPoint(pText, fontSize);

		float textPosX = pPosX;
		float textPosY = pPosY;

		if (verticalAlign == Element.ALIGN_TOP) {
			textPosY = (float) (pPosY - lineHeight);
		} else if (verticalAlign == Element.ALIGN_MIDDLE) {
			textPosY = (float) (pPosY - (lineHeight / 2));
		}

		
		if("<hr>".equals(pText)){
			pPdfContentByte.setLineWidth(strokeWidth);	// Make a bit thicker than 1.0 default 
			//pPdfContentByte.setGrayStroke(1f); // 1 = black, 0 = white 
			pPdfContentByte.moveTo(pPosX,pPosY); 
			pPdfContentByte.lineTo(pPosRX, pPosY); 
			pPdfContentByte.stroke(); 
		}
		else if (wrapText) {
			ColumnText columnText = new ColumnText(pPdfContentByte);
			columnText.setSimpleColumn(pPosX, pPosY, pPosRX, pPosRY);
			
			PdfPCell cell = new PdfPCell(phrase);
			cell.setFixedHeight(pPosRY-pPosY);
			cell.setBorder(PdfPCell.NO_BORDER);
			cell.setVerticalAlignment(verticalAlign);
			cell.setHorizontalAlignment(align);
			cell.setPaddingLeft(-7);
			
			
			PdfPTable table = new PdfPTable(new float[]{pPosRX-pPosX});
			table.addCell(cell);
			
			columnText.addElement(table);
			try {
				columnText.go();
			} catch (DocumentException e) {
				e.printStackTrace();
				throw new AppRuntimeException("wrap-text generation error");
			}
		} else {
			if (align == Element.ALIGN_RIGHT) {
				textPosX = pPosRX;
			} else if (align == Element.ALIGN_MIDDLE) {
				textPosX = (pPosX + pPosRX) / 2;
			}
			
			ColumnText.showTextAligned(
					pPdfContentByte, 
					align,
					phrase,
					textPosX, 
					textPosY, 
					pRotation);
		}
		
	}
	
	public static final int IMG_TYPE_JPG_PNG = 0;

	public static final int IMG_TYPE_TIFF = 1;

	public static final int IMG_TYPE_GIF = 2;

	public static void imageToPdf(int pImageType, byte[] pImageSource,
			OutputStream pOutputStream) throws Exception {
		Document document = new Document();
		PdfWriter writer = PdfWriter.getInstance(document, pOutputStream);
		writer.open();
		document.open();
		Image pdfImage = null;
		switch (pImageType) {
		case IMG_TYPE_JPG_PNG:
			pdfImage = Image.getInstance(pImageSource);
			addImageToPdf(document, pdfImage);
			break;

		case IMG_TYPE_TIFF:
			RandomAccessFileOrArray ra = new RandomAccessFileOrArray(pImageSource);
			int pages = TiffImage.getNumberOfPages(ra);
			for(int i = 1; i <= pages; i++){
				if (i > 1) {
					document.newPage();
				}
				pdfImage = TiffImage.getTiffImage(ra, i);
				addImageToPdf(document, pdfImage);
			}		
			break;

		case IMG_TYPE_GIF:
			GifImage img = new GifImage(pImageSource);
			int frame_count = img.getFrameCount();
			for (int i = 1; i <= frame_count; i++) {
				if (i > 1) {
					document.newPage();
				}
				addImageToPdf(document, img.getImage(i));
			}
			break;
		}
		document.close();
		writer.close();
	}
	
	private static void addImageToPdf(Document pDocument, Image pImage) throws Exception {
		if (pImage.getDpiX() > 0) {
			pImage.scalePercent(7200f / pImage.getDpiX(), 7200f / pImage.getDpiY());
		}
		pDocument.add(pImage);
	}
	
	public static void imageToPdf(java.awt.Image pAwtImg,
			OutputStream pOutputStream) throws Exception {
		Document document = new Document();
		PdfWriter writer = PdfWriter.getInstance(document, pOutputStream);
		writer.open();
		document.open();
		Image pdfImage = Image.getInstance(pAwtImg, null);
		addImageToPdf(document, pdfImage);
		document.add(pdfImage);
		document.close();
		writer.close();
	}
	
	public static int countPdfPage (List<InputStream> pSourcePdfInputStreamList) {
		int totalPages = 0;
		
		try {
			for (InputStream pdfStream : pSourcePdfInputStreamList) {
				totalPages += countPdfPage(pdfStream);
			}
		} catch (Exception e) {
			logger.error("Failure in count page of pdf files.\n" + ExceptionUtils.getFullStackTrace(e));
		} 
			
		return totalPages;
	}
	
	public static int countPdfPage (InputStream pSourcePdfInputStream) {
		int totalPages = 0;
		
		try {
			PDF sourcePdf = null;
			sourcePdf = new PDF(pSourcePdfInputStream);
			totalPages += sourcePdf.getNumberOfPages();
		} catch (Exception e) {
			logger.error("Failure in count page of pdf files.\n" + ExceptionUtils.getFullStackTrace(e));
		} 
			
		return totalPages;
	}

	private static class PDF {
		PdfReader pdfReader;
		
		int numberOfPages;
		boolean containBookmark = false;
		List<HashMap<String,Object>> bookmarkList = null;
		
		@SuppressWarnings("unchecked")
		public PDF(InputStream pPdfStream) throws Exception {
			this.pdfReader = new PdfReader(pPdfStream);
			this.numberOfPages = pdfReader.getNumberOfPages();
			this.bookmarkList = SimpleBookmark.getBookmark(pdfReader);
		}

		public PdfReader getPdfReader() {
			return this.pdfReader;
		}

		public int getNumberOfPages() {
			return this.numberOfPages;
		}

		public boolean isContainBookmark() {
			return this.containBookmark;
		}

		public void mergeBookmark(int pPageOffSet, ArrayList<HashMap<String,Object>> pMasterBookMarkList) {
			if (this.bookmarkList != null) {
				if (pPageOffSet != 0) {
					SimpleBookmark.shiftPageNumbers(bookmarkList, pPageOffSet, null);
				}
				if (pMasterBookMarkList != null) {
					pMasterBookMarkList.addAll(this.bookmarkList);
				}
			}
		}
		
		public void addPageToMasterPdf(PdfCopy pPdfWriter, int pCurrentPageNum,
				boolean pIsInsertPageNum, boolean pIsMergePageNum,
				int pCurrentPageOfMergedPdf, int pTotalPageOfMergedPdf,
				String pWatermarkString, PdfHeaderFooter pPdfHeaderFooter) throws Exception {
			
			PdfCopy.PageStamp stamp;
			PdfImportedPage page = pPdfWriter.getImportedPage(this.pdfReader, pCurrentPageNum);;
			
			if (pIsInsertPageNum 
					|| StringUtils.isNotBlank(pWatermarkString)
					|| pPdfHeaderFooter != null) {
				stamp = pPdfWriter.createPageStamp(page);

				if (pIsInsertPageNum) {
					insertPageNumber(stamp, pCurrentPageNum, pIsMergePageNum, pCurrentPageOfMergedPdf, pTotalPageOfMergedPdf, this.pdfReader.getPageSize(pCurrentPageNum));
				}
				
				if (pPdfHeaderFooter != null) {
					insertHeaderFooter(pPdfHeaderFooter, stamp, this.pdfReader.getPageSize(pCurrentPageNum));
				}

				if (StringUtils.isNotBlank(pWatermarkString)) {
					insertWatermark(stamp, pWatermarkString, this.pdfReader.getPageSize(pCurrentPageNum));
				}
				
				stamp.alterContents();
			}
			pPdfWriter.addPage(page);
			
		}

		public void insertPageNumber(PdfCopy.PageStamp pStamp, int pCurrentPageNum,
				boolean pIsMergePageNum, int pCurrentPageOfMergedPdf,
				int pTotalPageOfMergedPdf, Rectangle pPageSize) {
			
			String pageNumText = String.valueOf(pCurrentPageNum) + " of " + String.valueOf(this.getNumberOfPages());
			if (pIsMergePageNum) {
				pageNumText = String.valueOf(pCurrentPageOfMergedPdf) + " of " + String.valueOf(pTotalPageOfMergedPdf);
			}

			addTextToPdf(pStamp.getUnderContent(), 
					pPageSize.getLeft() + PRINT_DIMENSION_LEFT_RIGHT_MARGIN, 
					PRINT_DIMENSION_TOP_BOTTOM_MARGIN, pPageSize.getRight() - PRINT_DIMENSION_LEFT_RIGHT_MARGIN, 0, 
					0, pageNumText, "font-size:10;text-align:right", pPageSize);
		}

		public void insertHeaderFooter(PdfHeaderFooter pPdfHeaderFooter, PdfCopy.PageStamp pStamp, Rectangle pPageSize) {
			if (pPdfHeaderFooter == null) {
				return;
			}
			
			if (StringUtils.isNotBlank(pPdfHeaderFooter.getHeaderText())) {
				addTextToPdf(pStamp.getUnderContent(), 
						pPageSize.getLeft() + PRINT_DIMENSION_LEFT_RIGHT_MARGIN, 
						pPageSize.getTop() - PRINT_DIMENSION_TOP_BOTTOM_MARGIN, pPdfHeaderFooter.getHeaderLeftPosition(pPageSize, PRINT_DIMENSION_LEFT_RIGHT_MARGIN), 0, 0, pPdfHeaderFooter.getHeaderText(), pPdfHeaderFooter.getHeaderCss(), pPageSize);
			}
			
			if (StringUtils.isNotBlank(pPdfHeaderFooter.getFooterText())) {
				addTextToPdf(pStamp.getUnderContent(), 
						pPageSize.getLeft() + PRINT_DIMENSION_LEFT_RIGHT_MARGIN, 
						PRINT_DIMENSION_TOP_BOTTOM_MARGIN, pPdfHeaderFooter.getFooterLeftPosition(pPageSize, PRINT_DIMENSION_LEFT_RIGHT_MARGIN), 0, 0, pPdfHeaderFooter.getFooterText(), pPdfHeaderFooter.getFooterCss(), pPageSize);
			}

		}
		
		public void insertWatermark(PdfCopy.PageStamp pStamp, String pWatermarkString, Rectangle pPageSize) {
			addTextToPdf(pStamp.getUnderContent(), pPageSize.getRight() / 2, pPageSize.getHeight() / 2, 0, 0, 0, pWatermarkString, "font-size:100;color:lightgrey;text-align:center;vertical-align:middle", pPageSize);
		}
	}
}
