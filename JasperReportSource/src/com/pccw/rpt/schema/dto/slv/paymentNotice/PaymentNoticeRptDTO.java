package com.pccw.rpt.schema.dto.slv.paymentNotice;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.pccw.rpt.schema.dto.ReportDTO;

public class PaymentNoticeRptDTO extends ReportDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3233680532176518298L;

	private String custOrCompanyName;
	private String address;
	private String attn;
	private String paymentNoticeNo;
	private String issueDate;   //YYYYMMDD
	private String paymentDueDate;   //YYYYMMDD
	private String sbId;
	private String profileIdTitle;
	private String footer;
	private String slvBannerTop;
	private String slvPaymentNoticeBannerLeft;
	private List<PaymentNoticeDetailRptDTO> paymentNoticeDetail = new ArrayList<PaymentNoticeDetailRptDTO>();
	
	public String getCustOrCompanyName() {
		if (this.custOrCompanyName == null) {
			return "";
		}
		
		return this.custOrCompanyName;
	}

	public void setCustOrCompanyName(String pCustOrCompanyName) {
		this.custOrCompanyName = pCustOrCompanyName;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String pAddress) {
		this.address = pAddress;
	}

	public String getAttn() {
		if (this.attn == null) {
			return "";
		}		
		return this.attn;
	}

	public void setAttn(String pAttn) {
		this.attn = pAttn;
	}

	public String getPaymentNoticeNo() {
		return this.paymentNoticeNo;
	}

	public void setPaymentNoticeNo(String pPaymentNoticeNo) {
		this.paymentNoticeNo = pPaymentNoticeNo;
	}

	public String getIssueDate() throws ParseException {
		if (this.issueDate == null) {
			return null;
		}
		SimpleDateFormat DateFormat = new SimpleDateFormat("yyyyMMdd");
		Date tmp = DateFormat.parse(this.issueDate);
		DateFormat.applyPattern("dd MMM yyyy");
		
		return DateFormat.format(tmp);
	}

	public void setIssueDate(String pIssue) {
		this.issueDate = pIssue;
	}

	public String getSbId() {
		return this.sbId;
	}

	public void setSbId(String pSbId) {
		this.sbId = pSbId;
	}
	
	public List<PaymentNoticeDetailRptDTO> getPaymentNoticeDetail() {
		return this.paymentNoticeDetail;
	}

	public void setPaymentNoticeDetail(
			List<PaymentNoticeDetailRptDTO> pPaymentNoticeDetail) {
		this.paymentNoticeDetail = pPaymentNoticeDetail;
	}

	public String getTotalAmount() {
		double totalAmount = 0;
		for (int i=0; i<getPaymentNoticeDetail().size(); i++) {
			totalAmount = totalAmount + Double.parseDouble(getPaymentNoticeDetail().get(i).getAmount().replaceAll(",",""));
			if(!StringUtils.isEmpty(getPaymentNoticeDetail().get(i).getAdjustment()))
				totalAmount += Double.parseDouble(getPaymentNoticeDetail().get(i).getAdjustment().replaceAll(",",""));
		}
		
		DecimalFormat formatter = new DecimalFormat("#,###,##0.00");
		return String.valueOf(formatter.format(totalAmount));
	}

	public String getPaymentDueDate() throws ParseException {
		if (this.paymentDueDate == null) {
			return null;
		}
		SimpleDateFormat DateFormat = new SimpleDateFormat("yyyyMMdd");
		Date tmp = DateFormat.parse(this.paymentDueDate);
		DateFormat.applyPattern("dd MMM yyyy");
		
		return DateFormat.format(tmp);
	}	
	
	public void setPaymentDueDate(String pPaymentDueDate) {
		this.paymentDueDate = pPaymentDueDate;
	}

	public String getProfileIdTitle() {
		return this.profileIdTitle;
	}

	public void setProfileIdTitle(String pProfileIdTitle) {
		this.profileIdTitle = pProfileIdTitle;
	}

	public String getFooter() {
		return this.footer;
	}

	public void setFooter(String pFooter) {
		this.footer = pFooter;
	}

	public String getSlvBannerTop() {
		return this.slvBannerTop;
	}

	public void setSlvBannerTop(String pSlvBannerTop) {
		this.slvBannerTop = pSlvBannerTop;
	}

	public String getSlvPaymentNoticeBannerLeft() {
		return this.slvPaymentNoticeBannerLeft;
	}

	public void setSlvPaymentNoticeBannerLeft(String pSlvPaymentNoticeBannerLeft) {
		this.slvPaymentNoticeBannerLeft = pSlvPaymentNoticeBannerLeft;
	}
}
