package com.activity.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.bomwebportal.dto.RemarkDTO;

public class ActivityHelper {

	public static RemarkDTO[] createRemarkDetails(String pRmkContent, String pRmkType, final int pLength) {
		
		if (StringUtils.isEmpty(pRmkContent)) {
			return null;
		}
		List<RemarkDTO> rmkList = new ArrayList<RemarkDTO>();
		RemarkDTO rmk;
		for (int start = 0; start < pRmkContent.length(); start += pLength) {
			rmk = new RemarkDTO();
			rmk.setRmkType(pRmkType);
			rmk.setRmkDtl(pRmkContent.substring(start, Math.min(pRmkContent.length(), start + pLength)));
			rmk.setRmkSeq(String.valueOf(rmkList.size() + 1));
			rmkList.add(rmk);
		}
		if (rmkList.size() == 0) { 
			return null;
		}
		return rmkList.toArray(new RemarkDTO[rmkList.size()]);
	}
}
