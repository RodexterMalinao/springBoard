package com.bomwebportal.mob.ccs.service;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import com.bomwebportal.dto.HSTradeDescDTO;

public interface MobCcsReportService {
    void generateCCSPdfReports(ArrayList<Object> pReportArrList,
	    ArrayList<Object> pCCSReportArrList, OutputStream pOutputStream,
	    String pLang, String orderId, String printSeq, boolean needWatermark, List<String> iGuardType, String brand);

    void savePdfReports(ArrayList<Object> pReportArrList, String pLang, String orderId, List<String> iGuardType, String brand);
    
    public String hasTradeInHS(String orderId);
    public String getTradeInRBSchedule(String tradeInItemID, String locale);
    public String getTradeInIMEIAttributeID();
    public boolean hasMobileSafetyPhoneOffer(String orderId);
    /*public boolean hasNFCSim(String orderId);*/
    /*public boolean hasOctopusSim(String orderId);*/
    public HSTradeDescDTO getHSTradeDescByItemCode(String itemCode);	
   public void generateStsDeliveryNote(ArrayList<Object> OsDNReportArrList, 
    		OutputStream pOutputStream,String pLang, boolean previewInd, Double paid,Double totalPayment, List<String> SmNoList,
    		String orderId,boolean custCopy, String brand);
	public void generatePISform(List<HSTradeDescDTO> hstd_DTOs, OutputStream pOutputStream,String pdfLang,String orderId, String brand);
	public void saveStssDeliveryPdfReports(ArrayList<Object> OsDNReportArrList,String pLang, 
			boolean previewInd, Double paid,Double totalPayment, List<String> SmNoList,String orderId, String brand);
	public void generateStsDOADeliveryNote(ArrayList<Object> StsDNReportArrList, OutputStream pOutputStream,
			String pLang,List<String> SmNoList,boolean custCopy, String osOrderId, String brand);
	public void saveCareCashPdfReports(ArrayList<Object> pReportArrList, String pLang, String orderId, List<String> iGuardType, String brand);
	public String maskedDocNum (String docNum);
	
}
