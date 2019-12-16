package com.bomwebportal.mob.ccs.dto;

import java.util.Date;
/**
 * This class extends from MobCcsMrtBaseDTO. This object holds data values of China number.
 * @author 01280072 James Wong
 *
 */
public class MobCcsMrtChinaDTO extends MobCcsMrtBaseDTO{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5876867857354582952L;
	
	private String cityCd;
	private Date serviceReqDate;
	private Date cutOverDate;
	private String cutOverTime;

	/**
	 * @return the cityCd
	 */
	public String getCityCd() {
		return cityCd;
	}

	/**
	 * @param cityCd the cityCd to set
	 */
	public void setCityCd(String cityCd) {
		this.cityCd = cityCd;
	}

	/**
	 * @return the serviceReqDate
	 */
	public Date getServiceReqDate() {
		return serviceReqDate;
	}

	/**
	 * @param serviceReqDate the serviceReqDate to set
	 */
	public void setServiceReqDate(Date serviceReqDate) {
		this.serviceReqDate = serviceReqDate;
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
}
