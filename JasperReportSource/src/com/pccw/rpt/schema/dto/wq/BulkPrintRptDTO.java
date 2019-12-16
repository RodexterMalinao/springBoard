package com.pccw.rpt.schema.dto.wq;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

import com.pccw.rpt.schema.dto.ReportDTO;

public class BulkPrintRptDTO extends ReportDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4329340248882331448L;
	private String printOutDate;
	private String totalCase;
	private ArrayList<BpDetailsRptDTO> detailsList = new ArrayList<BpDetailsRptDTO>();

	public void addDetails(String pWqSerial, String pSrvType, String pSrvNum, String pWqType,
			String pWq, String pShopCode, String pTotalPage, String pAssignee,
			String pPrintStatus) {
		this.detailsList.add(new BpDetailsRptDTO(String.valueOf(this.detailsList.size() + 1), pWqSerial, pSrvType, pSrvNum, pWqType,
				pWq, pShopCode, pTotalPage, pAssignee, pPrintStatus));
	}

	public String getPrintOutDate() throws Exception {
		if (this.printOutDate == null) {
			return null;
		}
		SimpleDateFormat DateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		Date tmp = DateFormat.parse(this.printOutDate);
		DateFormat.applyPattern("dd/MM/yyyy HH:mm");
		
		return DateFormat.format(tmp);
	}

	public void setPrintOutDate(String pPrintOutDate) {
		this.printOutDate = pPrintOutDate;
	}

	public String getTotalCase() {
		StringBuffer sbTotalCase = new StringBuffer(this.detailsList.size());
		sbTotalCase.append(" (");		
		TreeMap<String, Integer> ttCaseSummary = new TreeMap<String, Integer>();
		for (BpDetailsRptDTO bpDetail : this.detailsList) {
			if (!ttCaseSummary.containsKey(bpDetail.getWqType())) {
				ttCaseSummary.put(bpDetail.getWqType(), 0);
			}
			ttCaseSummary.put(bpDetail.getWqType(), ttCaseSummary.get(bpDetail.getWqType()) + 1);
		}
		int cnt = 0;
		for (Map.Entry<String, Integer> entry : ttCaseSummary.entrySet()) {
			cnt++;
			if (cnt != 0) {
				sbTotalCase.append(" ");
			}
			sbTotalCase.append(entry.getKey());
			sbTotalCase.append(":");
			sbTotalCase.append(String.valueOf(entry.getValue()));
		}

		sbTotalCase.append(" )");
		return sbTotalCase.toString();
	}

	public void setTotalCase(String pTotalCase) {
		this.totalCase = pTotalCase;
	}

	public ArrayList<BpDetailsRptDTO> getDetailsList() {
		return this.detailsList;
	}

	public void setDetailsList(ArrayList<BpDetailsRptDTO> pDetailsList) {
		this.detailsList = pDetailsList;
	}
}