package com.bomwebportal.lts.service.bom;

import com.bomwebportal.lts.dto.order.SbOrderDTO;
import com.pccw.custProfileGateway.serviceProf.DNAssignOutput;
import com.pccw.custProfileGateway.serviceProf.DNAssignmentResultDTO;


public interface BomInvPreassgnService {
	
	public void updateInvPreassgnJunctionMsg(SbOrderDTO sbOrder, String userId);
	
	public DNAssignOutput dnAssignReq(String srvNum, String inventStsCd, String action, String gatewayNum);
	
	public String getSrvDnStatus(String srvNum, String srvType);
	
	public DNAssignmentResultDTO getDNAssignment(String srvNum, String accountCd, String boc, String projectCd, String specialSrvGrp, String specialSrvName);

}
