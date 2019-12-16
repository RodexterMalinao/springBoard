package com.bomwebportal.lts.service.report;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bomwebportal.lts.dto.BasketDetailDTO;
import com.bomwebportal.lts.dto.ItemDetailDTO;
import com.bomwebportal.lts.dto.order.SbOrderDTO;
import com.bomwebportal.lts.service.LtsQrCodeService;
import com.bomwebportal.lts.util.LtsBackendConstant;
import com.bomwebportal.lts.util.LtsDateFormatHelper;
import com.pccw.rpt.schema.dto.ReportDTO;
import com.pccw.rpt.schema.dto.paymentSlip.PaymentSlipRptDTO;

public class PaymentSlipReportDataLtsServiceImpl implements ReportLtsService {

	private final Log logger = LogFactory.getLog(getClass());
	private String dataFilePath;
	private LtsQrCodeService ltsQrCodeService;
	
	@Override
	public void fillReport(ReportDTO pReportDTO, SbOrderDTO sbOrder,
			String pLocale, String pRptName, boolean pIsEditable,
			boolean pIsPrintSignature) {
		
		// TODO Auto-generated method stub
		PaymentSlipRptDTO rptDTO = (PaymentSlipRptDTO) pReportDTO;						
		InputStream qrStream = null;
		ItemDetailDTO prepayItem = sbOrder.getPrepayItem();
		BasketDetailDTO selectedBasket = sbOrder.getSelectedBasket();
		try {
			if(sbOrder != null && StringUtils.isNotBlank(sbOrder.getOrderId())){				
				this.ltsQrCodeService.generateQrCodeInformation(generateQrCodeText(sbOrder.getPrepayAcctNum(), prepayItem.getOneTimeAmt())
						, dataFilePath + sbOrder.getOrderId() + "/", sbOrder.getOrderId() + "_QR.png");
				qrStream = new FileInputStream(dataFilePath + sbOrder.getOrderId() + "/" + sbOrder.getOrderId() + "_QR.png");
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			logger.error(e.toString());
			qrStream = null;
		}

		String[] desc = prepayItem.getItemDisplayHtml().split("<br/>");
		String itemDesc = "";
		if(desc != null){
			itemDesc = desc[0];
		}
		rptDTO.setPrintDate(LtsDateFormatHelper.getDateFromDTOFormat(sbOrder.getAppDate()));
		rptDTO.setServiceName(selectedBasket.getDesc());
		rptDTO.setPaymentItem(itemDesc);
		rptDTO.setAccountNumber(sbOrder.getPrepayAcctNum());
		rptDTO.setBillType(LtsBackendConstant.LTS_BILL_TYPE);
		rptDTO.setPaymentAmount(prepayItem.getOneTimeAmtTxt());		
		rptDTO.setQrCode(qrStream);		
		
		InputStream pccwLogoStream = PaymentSlipReportDataLtsServiceImpl.class.getResourceAsStream("/images/HKT_logo.png");
		rptDTO.setCompanyLogo(pccwLogoStream);
		
		InputStream footerImageStream = PaymentSlipReportDataLtsServiceImpl.class.getResourceAsStream("/images/HKT_footer.png");
		rptDTO.setFooterImage(footerImageStream);
		
	}
	
	private String generateQrCodeText(String pAccountNum, String pAmount){
		return LtsBackendConstant.MERCHANT_CODE + LtsBackendConstant.LTS_BILL_TYPE + pAccountNum + convertAmountToQrFormat(pAmount);
	}
	
	private String convertAmountToQrFormat(String pAmount){
		return StringUtils.leftPad(pAmount + "00", 10, "0");
	}

	public String getDataFilePath() {
		return dataFilePath;
	}

	public void setDataFilePath(String dataFilePath) {
		this.dataFilePath = dataFilePath;
	}

	public LtsQrCodeService getLtsQrCodeService() {
		return ltsQrCodeService;
	}

	public void setLtsQrCodeService(LtsQrCodeService ltsQrCodeService) {
		this.ltsQrCodeService = ltsQrCodeService;
	}
	
}

