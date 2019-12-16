package com.bomwebportal.sbs;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.bomwebportal.dto.BomSalesUserDTO;

public class SbsFunction {
	public final static String UpdateContactDelivery = "updatecontactdelivery";
	public final static String DoaRequest = "doarequest";
	public final static String RequestCancelOrder = "requestcancelorder";
	public final static String UpdatePosSm = "updatepossm";
	public final static String OnsiteDoa = "onsitedoa";
	public final static String Doa = "doa";
	public final static String ReportCancelled = "reportcancelled";

	public static Set<String> allowedFunctions(BomSalesUserDTO user, String status, String checkPoint, String reasonCd) {
		TreeSet<String> fnset = new TreeSet<String>();
		String fn[] = {};
		String channelCd = user.getChannelCd();
		
		
		//if ("CFM".equals(channelCd)) {
		if (user.getChannelId() == 66) {
			if ("01".equals(status)) {
				if ("490".equals(checkPoint)) {
					fn = new String[] {UpdatePosSm, UpdateContactDelivery, RequestCancelOrder};
				}
				if ("500".equals(checkPoint)) {
					fn = new String[] {UpdatePosSm};
				}
				if ("599".equals(checkPoint)) {
					fn = new String[] {UpdatePosSm};
				}
			}  else if ("99".equals(status)) {
				if ("999".equals(checkPoint)) {
					if ("G001".equals(reasonCd)) {
						fn = new String[] {UpdatePosSm, RequestCancelOrder};
					} else if ("G002".equals(reasonCd)) {
						fn = new String[] {UpdatePosSm, OnsiteDoa, RequestCancelOrder};
					} else if ("G003".equals(reasonCd)) {
						fn = new String[] {UpdatePosSm, OnsiteDoa, RequestCancelOrder};
					} else if ("G004".equals(reasonCd)) {
						fn = new String[] {UpdatePosSm, RequestCancelOrder};
					}  else if ("N000".equals(reasonCd)) {
						fn = new String[] {UpdatePosSm, DoaRequest};
					} else if ("N001".equals(reasonCd)) {
						fn = new String[] {UpdatePosSm, DoaRequest};
					} else if ("N002".equals(reasonCd)) {
						fn = new String[] {UpdatePosSm, Doa, DoaRequest};
					} else if ("N003".equals(reasonCd)) {
						fn = new String[] {UpdatePosSm, Doa, DoaRequest};
					} else if ("N004".equals(reasonCd)) {
						fn = new String[] {UpdatePosSm, Doa, DoaRequest};
					} else if ("N005".equals(reasonCd)) {
						fn = new String[] {DoaRequest};
					} else if (StringUtils.startsWith(reasonCd, "F")) {
						fn = new String[] {UpdatePosSm, UpdateContactDelivery, RequestCancelOrder};
					} else {
						fn = new String[] { };
					}
				}
			} else if ("02".equals(status)) {
				fn = new String[] {DoaRequest};
			} else if ("03".equals(status)) {
				fn = new String[] {UpdatePosSm, ReportCancelled};
			} else if ("04".equals(status)) {
				fn = new String[] {};
			}	
		} else if (user.getChannelId() == 2){
			if ("01".equals(status)) {
				if ("490".equals(checkPoint)) {
					fn = new String[] {UpdateContactDelivery, RequestCancelOrder};
				}
			} else if ("02".equals(status)) {
				fn = new String[] {DoaRequest};
			} else if ("99".equals(status)) {
				if ("999".equals(checkPoint)) {
					if ("G001".equals(reasonCd)) {
						fn = new String[] {RequestCancelOrder};
					} else if ("G002".equals(reasonCd)) {
						fn = new String[] {RequestCancelOrder};
					} else if ("G003".equals(reasonCd)) {
						fn = new String[] {RequestCancelOrder};
					} else if ("G004".equals(reasonCd)) {
						fn = new String[] { RequestCancelOrder };
					} else if (StringUtils.startsWith(reasonCd, "N")) {
						fn = new String[] { DoaRequest };
					} else if (StringUtils.startsWith(reasonCd, "F")) {
						fn = new String[] {UpdateContactDelivery, RequestCancelOrder};
					} else {
						fn = new String[] {};
					}
				}
			} else {
				fn = new String[0];
			}			
		}

		
		
		CollectionUtils.addAll(fnset, fn);
		return fnset;
	}

}
