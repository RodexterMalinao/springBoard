package com.bomwebportal.mob.ccs.service;

import java.util.List;

import com.bomwebportal.mob.ccs.dto.MrtStatusDTO;

public interface MobCcsMrtStatusService {
    public enum MsisdnStatus {
    	FREE(2, "FREE")
    	, FROZEN(5, "FROZEN")
		, RESERVE(18, "RESERVE")
		, ASSIGN(19, "ASSIGN")
		, SOLD(20, "SOLD")
		, SUSPEND(25, "SUSPEND")
    	;
    	MsisdnStatus(int status, String desc) {
    		this.status = status;
    		this.desc = desc;
    	}
    	public int getStatus() {
			return status;
		}
		public String getDesc() {
			return desc;
		}
		private int status;
    	private String desc;
    	public static MsisdnStatus valueOf(int status) {
    		for (MsisdnStatus m : MsisdnStatus.values()) {
    			if (m.getStatus() == status) {
    				return m;
    			}
    		}
    		return null;
    	}
    };
    
	List<MrtStatusDTO> getMrtStatusDTOByMsisdn(String msisdn);
	List<MrtStatusDTO> getMrtStatusDTOByMsisdnAndStatus(String msisdn, MsisdnStatus status);
	int deleteMrtStatusDTOByMsisdnAndStatus(String msisdn, MsisdnStatus status);
}
