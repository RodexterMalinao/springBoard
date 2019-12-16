package com.bomwebportal.mob.ccs.dto;

import java.util.Date;
/**
 * This class extends from MobCcsMrtBaseDTO. This object holds data values of MNP number.
 * @author 01280072 James Wong
 *
 */
public class MobCcsMrtMnpDTO extends MobCcsMrtBaseDTO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2491108203673063196L;
	
	private String mnpTicketNum;
	private Date cutOverDate;
	private String cutOverTime;
	private String custName;
	private String docNum;
	private Date origActDate;
	
	private String custNameChi;
	private String PrePaidSimDocInd;
	
	private String opssInd;
	private String starterPack;
	
	public Date getOrigActDate() {
		return origActDate;
	}
	public void setOrigActDate(Date origActDate) {
		this.origActDate = origActDate;
	}
	/**
	 * @return the cutOverDate
	 */
	public Date getCutOverDate() {
		return cutOverDate;
	}
	/**
	 * @param cutOverDate the cutOverDate to set
	 */
	public void setCutOverDate(Date cutOverDate) {
		this.cutOverDate = cutOverDate;
	}
	/**
	 * @return the cutOverTime
	 */
	public String getCutOverTime() {
		return cutOverTime;
	}
	/**
	 * @param cutOverTime the cutOverTime to set
	 */
	public void setCutOverTime(String cutOverTime) {
		this.cutOverTime = cutOverTime;
	}
	/**
	 * @return the mnpTicketNum
	 */
	public String getMnpTicketNum() {
		return mnpTicketNum;
	}
	/**
	 * @param mnpTicketNum the mnpTicketNum to set
	 */
	public void setMnpTicketNum(String mnpTicketNum) {
		this.mnpTicketNum = mnpTicketNum;
	}
	/**
	 * @return the custName
	 */
	public String getCustName() {
		return custName;
	}
	/**
	 * @param custName the custName to set
	 */
	public void setCustName(String custName) {
		this.custName = custName;
	}
	/**
	 * @return the docNum
	 */
	public String getDocNum() {
		return docNum;
	}
	/**
	 * @param docNum the docNum to set
	 */
	public void setDocNum(String docNum) {
		this.docNum = docNum;
	}
	public String getCustNameChi() {
		return custNameChi;
	}
	public void setCustNameChi(String custNameChi) {
		this.custNameChi = custNameChi;
	}
	public String getPrePaidSimDocInd() {
		return PrePaidSimDocInd;
	}
	public void setPrePaidSimDocInd(String prePaidSimDocInd) {
		PrePaidSimDocInd = prePaidSimDocInd;
	}
	public String getOpssInd() {
		return opssInd;
	}
	public void setOpssInd(String opssInd) {
		this.opssInd = opssInd;
	}
	public String getStarterPack() {
		return starterPack;
	}
	public void setStarterPack(String starterPack) {
		this.starterPack = starterPack;
	}
}
