package com.pccw.rpt.schema.dto.paymentSlip;

import java.io.IOException;
import java.io.InputStream;
import org.apache.commons.lang.ArrayUtils;

import com.bomwebportal.util.FastByteArrayInputStream;
import com.bomwebportal.util.FastByteArrayOutputStream;
import com.pccw.rpt.schema.dto.ReportDTO;

public class PaymentSlipRptDTO extends ReportDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1973432403772463297L;
	private boolean previewSlip;
	private String printDate;
	private String serviceName;
	private String paymentItem;
	private String accountNumber;
	private String billType;
	private String paymentAmount;
	private byte[] qrCode;
	private byte[] footerImage;
	private String remark;

	public boolean isPreviewSlip() {
		return previewSlip;
	}
	public void setPreviewSlip(boolean previewSlip) {
		this.previewSlip = previewSlip;
	}
	public String getPrintDate() {
		return printDate;
	}
	public void setPrintDate(String printDate) {
		this.printDate = printDate;
	}
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	public String getPaymentItem() {
		return paymentItem;
	}
	public void setPaymentItem(String paymentItem) {
		this.paymentItem = paymentItem;
	}
	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	public String getBillType() {
		return billType;
	}
	public void setBillType(String billType) {
		this.billType = billType;
	}
	public String getPaymentAmount() {
		return paymentAmount;
	}
	public void setPaymentAmount(String paymentAmount) {
		this.paymentAmount = paymentAmount;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public void setQrCode(byte[] pQrCode) {
		this.qrCode = pQrCode;
	}
	
	public InputStream getQrCode() {
		return new FastByteArrayInputStream(this.qrCode, ArrayUtils.isEmpty(this.qrCode) ? 0 : this.qrCode.length);
	}

	public void setQrCode(InputStream pQrCode) {	
		if(pQrCode != null){
	        try {
				FastByteArrayOutputStream fbaos = new FastByteArrayOutputStream();
	      
				byte[] b = new byte[1024];
				int noOfBytes = 0;
	      
				//read bytes from source file and write to destination file
				while( (noOfBytes = pQrCode.read(b)) != -1 ) {
				   fbaos.write(b, 0, noOfBytes);
				}
				this.qrCode = fbaos.getByteArray();
	      
				//close the streams
				pQrCode.close();
				fbaos.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}

	public InputStream getFooterImage() {
		return new FastByteArrayInputStream(this.footerImage, ArrayUtils.isEmpty(this.footerImage) ? 0 : this.footerImage.length);
	}

	public void setFooterImage(InputStream pFooterImage) {	
		if(pFooterImage != null){
	        try {
				FastByteArrayOutputStream fbaos = new FastByteArrayOutputStream();
	      
				byte[] b = new byte[1024];
				int noOfBytes = 0;
	      
				//read bytes from source file and write to destination file
				while( (noOfBytes = pFooterImage.read(b)) != -1 ) {
				   fbaos.write(b, 0, noOfBytes);
				}
				this.footerImage = fbaos.getByteArray();
	      
				//close the streams
				pFooterImage.close();
				fbaos.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}
}