package com.bomwebportal.util;

import org.apache.commons.lang.StringUtils;

import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.ColumnText;
import com.lowagie.text.pdf.PdfAction;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfPageEventHelper;
import com.lowagie.text.pdf.PdfWriter;

public class PdfHeaderFooter extends PdfPageEventHelper {

	public static final int ALIGN_LEFT = Element.ALIGN_LEFT;

	public static final int ALIGN_CENTER = Element.ALIGN_CENTER;

	public static final int ALIGN_RIGHT = Element.ALIGN_RIGHT;

	private int headerAlign;

	private int footerAlign;

	private int headerFontSize = 10;

	private int footerFontSize = 10;

	private String headerText;

	private String footerText;

	private Phrase header;
	private PdfPTable footer;

	public PdfHeaderFooter() {
	}

	public static String getCssAlign(int pAlign) {
		if (pAlign == ALIGN_LEFT) {
			return "left";
		} else if (pAlign == ALIGN_CENTER) {
			return "center";
		} else if (pAlign == ALIGN_RIGHT) {
			return "right";
		}
		return null;
	}

	public int getHeaderAlign() {
		return this.headerAlign;
	}

	public void setHeaderAlign(int pHeaderAlign) {
		this.headerAlign = pHeaderAlign;
	}

	public int getFooterAlign() {
		return this.footerAlign;
	}

	public void setFooterAlign(int pFooterAlign) {
		this.footerAlign = pFooterAlign;
	}

	public String getHeaderText() {
		return this.headerText;
	}

	public void setHeaderText(String pHeaderText) {
		this.headerText = pHeaderText;
	}

	public String getFooterText() {
		return this.footerText;
	}

	public void setFooterText(String pFooterText) {
		this.footerText = pFooterText;
	}

	public String getHeaderCss() {
		return "font-size:" + String.valueOf(this.headerFontSize)
				+ ";text-align:" + getCssAlign(this.headerAlign)
				+ ";vertical-align:top";
	}

	public float getHeaderLeftPosition(Rectangle pPageSize, float pMargin) {
		return this.getLeftPosition(this.headerAlign, pPageSize, pMargin);
	}

	private float getLeftPosition(int pAlign, Rectangle pPageSize, float pMargin) {
		if (pAlign == ALIGN_CENTER) {
			return (pPageSize.getRight() - pPageSize.getLeft()) / 2;
		} else if (pAlign == ALIGN_RIGHT) {
			return (pPageSize.getRight() - pMargin);
		}
		return pPageSize.getLeft() + pMargin;
	}

	public String getFooterCss() {
		return "font-size:" + String.valueOf(this.footerFontSize)
				+ ";text-align:" + getCssAlign(this.footerAlign);
	}

	public float getFooterLeftPosition(Rectangle pPageSize, float pMargin) {
		return this.getLeftPosition(this.footerAlign, pPageSize, pMargin);
	}

	public int getHeaderFontSize() {
		return this.headerFontSize;
	}

	public void setHeaderFontSize(int pHeaderFontSize) {
		this.headerFontSize = pHeaderFontSize;
	}

	public int getFooterFontSize() {
		return this.footerFontSize;
	}

	public void setFooterFontSize(int pFooterFontSize) {
		this.footerFontSize = pFooterFontSize;
	}
	
	public void onEndPage(PdfWriter pWriter, Document pDocument) {
		PdfContentByte cb = pWriter.getDirectContent();
		if (StringUtils.isNotBlank(this.headerText)) {
			ColumnText.showTextAligned(
					cb,
					this.headerAlign,
					this.getHeader(),
					this.getLeftPosition(this.getHeaderAlign(), pDocument), 
					pDocument.top() + 10, 0);
		}

		if (StringUtils.isNotBlank(this.footerText)) {
			this.getFooter().writeSelectedRows(
					0,
					-1,
					this.getLeftPosition(this.getFooterAlign(), pDocument), 
					pDocument.bottom() - 10, cb);
		}
	}
	
	private float getLeftPosition(int pAlign, Document pDocument) {
		if (pAlign == ALIGN_CENTER) {
			return (pDocument.right() - pDocument.left()) / 2
					+ pDocument.leftMargin();
		} else if (pAlign == ALIGN_RIGHT) {
			return (pDocument.right() - pDocument.rightMargin());
		}
		return pDocument.leftMargin();
	}
	
	public Phrase getHeader() {
		if (this.header == null) {
			this.header = new Phrase(this.headerText, new Font(Font.COURIER, this.headerFontSize, Font.NORMAL));
		}
		return this.header;
	}
	
	public PdfPTable getFooter() {
		if (this.footer == null) {
			footer = new PdfPTable(1);
			footer.setTotalWidth(300);
			footer.getDefaultCell().setHorizontalAlignment(this.footerAlign);
			Phrase footerPhrase = new Phrase(new Chunk(this.footerText).setAction(new PdfAction(PdfAction.FIRSTPAGE)));
			footerPhrase.setFont(new Font(Font.COURIER, this.footerFontSize, Font.NORMAL));
			footer.addCell(footerPhrase);
		}
		return this.footer;
	}

}
