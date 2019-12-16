package com.bomwebportal.mob.ccs.dto;

import java.io.Serializable;
import java.util.Date;
/**
 * This class is the base class for MobCcsMrtChinaDTO, MobCcsMrtDTO, and MobCcsMrtMnpDTO.
 * Mobile number for each of its child is stored into msisdn attribute.
 * @author 01280072 James Wong
 *
 */
public class MobCcsMrtBaseDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7308503751466798355L;

	public static final String[] NORMAL_MRT_ARRAY = new String[]{"N1", "N2", "GD"};
	public static final String[] NORMAL_MRT_ARRAY_N_ONLY = new String[]{"N1", "N2"};
	
	public static final String NORMAL_MRT_N1 = "N1";
	public static final String NORMAL_MRT_N2 = "N2";
	
	private String orderId;
    private String mnpInd;
    private String goldenInd;
    private String chinaInd;
    private String msisdn;
    private String msisdnLvl;
    private String rno;
    private String dno;
    private String actualDno;
    private String createdBy;
    private Date createdDate;
    private String lastUpdBy;
    private Date lastUpdDate;
    private int cnmStatus;
    private int seqId;
    private String reserveId;
    private String operId;
    private boolean byPassValidation;
    private String opssInd;
    private String starterPack;
    
    private String numType; //Dennis MIP3
    
	/**
	 * @return the orderId
	 */
	public String getOrderId() {
		return orderId;
	}
	/**
	 * @param orderId the orderId to set
	 */
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	/**
	 * @return the mnpInd
	 */
	public String getMnpInd() {
		return mnpInd;
	}
	/**
	 * @param mnpInd the mnpInd to set
	 */
	public void setMnpInd(String mnpInd) {
		this.mnpInd = mnpInd;
	}
	/**
	 * @return the goldenInd
	 */
	public String getGoldenInd() {
		return goldenInd;
	}
	/**
	 * @param goldenInd the goldenInd to set
	 */
	public void setGoldenInd(String goldenInd) {
		this.goldenInd = goldenInd;
	}
	/**
	 * @return the chinaInd
	 */
	public String getChinaInd() {
		return chinaInd;
	}
	/**
	 * @param chinaInd the chinaInd to set
	 */
	public void setChinaInd(String chinaInd) {
		this.chinaInd = chinaInd;
	}
	/**
	 * @return the msisdn
	 */
	public String getMsisdn() {
		return msisdn;
	}
	/**
	 * @param msisdn the msisdn to set
	 */
	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}
	/**
	 * @return the msisdnLvl
	 */
	public String getMsisdnLvl() {
		return msisdnLvl;
	}
	/**
	 * @param msisdnLvl the msisdnLvl to set
	 */
	public void setMsisdnLvl(String msisdnLvl) {
		this.msisdnLvl = msisdnLvl;
	}
	/**
	 * @return the rno
	 */
	public String getRno() {
		return rno;
	}
	/**
	 * @param rno the rno to set
	 */
	public void setRno(String rno) {
		this.rno = rno;
	}
	/**
	 * @return the dno
	 */
	public String getDno() {
		return dno;
	}
	/**
	 * @param dno the dno to set
	 */
	public void setDno(String dno) {
		this.dno = dno;
	}
	/**
	 * @return the createdBy
	 */
	public String getCreatedBy() {
		return createdBy;
	}
	/**
	 * @param createdBy the createdBy to set
	 */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	/**
	 * @return the createdDate
	 */
	public Date getCreatedDate() {
		return createdDate;
	}
	/**
	 * @param createdDate the createdDate to set
	 */
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	/**
	 * @return the lastUpdBy
	 */
	public String getLastUpdBy() {
		return lastUpdBy;
	}
	/**
	 * @param lastUpdBy the lastUpdBy to set
	 */
	public void setLastUpdBy(String lastUpdBy) {
		this.lastUpdBy = lastUpdBy;
	}
	/**
	 * @return the lastUpdDate
	 */
	public Date getLastUpdDate() {
		return lastUpdDate;
	}
	/**
	 * @param lastUpdDate the lastUpdDate to set
	 */
	public void setLastUpdDate(Date lastUpdDate) {
		this.lastUpdDate = lastUpdDate;
	}
	/**
	 * @return the cnmStatus
	 */
	public int getCnmStatus() {
		return cnmStatus;
	}
	/**
	 * @param cnmStatus the cnmStatus to set
	 */
	public void setCnmStatus(int cnmStatus) {
		this.cnmStatus = cnmStatus;
	}
	/**
	 * @return the seqId
	 */
	public int getSeqId() {
		return seqId;
	}
	/**
	 * @param seqId the seqId to set
	 */
	public void setSeqId(int seqId) {
		this.seqId = seqId;
	}
	/**
	 * @return the reserveId
	 */
	public String getReserveId() {
		return reserveId;
	}
	/**
	 * @param reserveId the reserveId to set
	 */
	public void setReserveId(String reserveId) {
		this.reserveId = reserveId;
	}
	/**
	 * @return the operId
	 */
	public String getOperId() {
		return operId;
	}
	/**
	 * @param operId the operId to set
	 */
	public void setOperId(String operId) {
		this.operId = operId;
	}
	/**
	 * @return the byPassValidation
	 */
	public boolean isByPassValidation() {
		return byPassValidation;
	}
	/**
	 * @param byPassValidation the byPassValidation to set
	 */
	public void setByPassValidation(boolean byPassValidation) {
		this.byPassValidation = byPassValidation;
	}
	public String getNumType() {
		return numType;
	}
	public void setNumType(String numType) {
		this.numType = numType;
	}
	public String getActualDno() {
		return actualDno;
	}
	public void setActualDno(String actualDno) {
		this.actualDno = actualDno;
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
