package com.bomwebportal.lts.service.order;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.util.StringUtils;

import com.bomwebportal.lts.dto.order.AmendOrderRecDTO;
import com.pccw.wq.schema.form.SbWqInquiryResultFormDTO;
import com.pccw.wq.service.WorkQueueDataService;

public class AmendRetrieveServiceImpl implements AmendRetrieveService {

	private WorkQueueDataService workQueueDataService;

	
	public AmendOrderRecDTO[] retrieveWQAmendment(String pSbId, String pUser) {
		AmendOrderRecDTO[] amendOrderArray = {};
		List<AmendOrderRecDTO> amendOrderList = new ArrayList<AmendOrderRecDTO>();
		AmendOrderRecDTO tempDTO;
		String oldDate, newDate;
		Date date;
		SimpleDateFormat wqDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		SimpleDateFormat displayDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		String remarkString="";
		String workingPartyAssignee;
		String statusDate;
		try{
		SbWqInquiryResultFormDTO[] wqResultList = this.workQueueDataService.getWorkQueueViewBySbId(pSbId, pUser);
		Arrays.sort(wqResultList, Collections.reverseOrder());
		if (wqResultList!=null){
			for (int i=0; i<wqResultList.length; i++){
				tempDTO = new AmendOrderRecDTO();
				tempDTO.setServiceNum(wqResultList[i].getServiceId());
				oldDate = wqResultList[i].getDisplayDate();
				date = wqDateFormat.parse(oldDate);
				newDate = displayDateFormat.format(date);
				tempDTO.setDate(newDate);
				/*remarkString = wqResultList[i].getRemarksNatureDesc()==null? "": wqResultList[i].getRemarksNatureDesc() + "<br/ > <br/ >";*/
				remarkString = (wqResultList[i].getRemarks()==null? "": wqResultList[i].getRemarks()[0].getRemarkContentHtml());
				tempDTO.setRemarks(remarkString);
				if (StringUtils.countOccurrencesOf(remarkString, "<br/>" )> 3){
					tempDTO.setEnableShowMore(true);
				}
				if (wqResultList[i].getWqWpAssgnId()!=null){
					tempDTO.setWqWpAssgnId(wqResultList[i].getWqWpAssgnId());
					workingPartyAssignee = wqResultList[i].getWpDesc()==null? "":wqResultList[i].getWpDesc();
					if (wqResultList[i].getAssignee()!=null){
						workingPartyAssignee = workingPartyAssignee.concat( " / " + wqResultList[i].getAssignee());
					}
					tempDTO.setWorkingParty(workingPartyAssignee);
					tempDTO.setWqSerial(wqResultList[i].getWqSerial());
					
					oldDate = wqResultList[i].getWqStatusDate();
					date = wqDateFormat.parse(oldDate);
					newDate = displayDateFormat.format(date);
					statusDate = wqResultList[i].getWqStatusDesc() + "<br/>(" + newDate + ")";
					
					tempDTO.setWqStatus(statusDate);
					tempDTO.setWqStatusCd(wqResultList[i].getWqStatus());
					tempDTO.setEnableStatusChg(wqResultList[i].isAllowChangeStatus());
					tempDTO.setEnableStatusChg(wqResultList[i].isAllowChangeStatus());
					tempDTO.setNature(wqResultList[i].getWqNatureDesc());
					tempDTO.setNatureId(wqResultList[i].getRemarks()==null? null: wqResultList[i].getRemarks()[0].getRemarkNatureId());
				}
				amendOrderList.add(tempDTO);
			}
		}
		}catch (Exception e){
			e.printStackTrace();
		}
		amendOrderArray = amendOrderList.toArray(amendOrderArray);
		return amendOrderArray;
	}
	
	public WorkQueueDataService getWorkQueueDataService() {
		return this.workQueueDataService;
	}


	public void setWorkQueueDataService(WorkQueueDataService pWorkQueueDataService) {
		this.workQueueDataService = pWorkQueueDataService;
	}
	
}
