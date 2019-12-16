package com.pccw.rpt.schema.dto.concertTicketForm;

import com.pccw.rpt.schema.dto.ReportDTO;

public class ConcertTicketRptDTO extends ReportDTO{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3649949606890904673L;
	
	private byte[] concertTicketSign;
	private String noSignatureR;

	public byte[] getConcertTicketSign() {
		return concertTicketSign;
	}

	public void setConcertTicketSign(byte[] concertTicketSign) {
		this.concertTicketSign = concertTicketSign;
	}

	public String getNoSignatureR() {
		return noSignatureR;
	}

	public void setNoSignatureR(String noSignatureR) {
		this.noSignatureR = noSignatureR;
	}

}
