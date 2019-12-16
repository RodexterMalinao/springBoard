package com.bomwebportal.mob.ccs.dto;

import java.util.Date;

/**
 * This class extends from MobCcsMrtBaseDTO. This object holds data values of Mrt(New) number.
 * @author 01280072 James Wong
 *
 */
public class MobCcsMrtDTO extends MobCcsMrtBaseDTO {

    /**
     * 
     */
    private static final long serialVersionUID = 1560800947776125536L;
    
    private Date serviceReqDate;

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
}
