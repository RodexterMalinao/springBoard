package com.bomwebportal.lts.service.bom;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bomwebportal.lts.dto.order.SbOrderDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailLtsDTO;
import com.bomwebportal.lts.util.LtsBackendConstant;
import com.bomwebportal.lts.util.LtsSbHelper;
import com.bomwebportal.lts.wsClientLts.BomWsBackendClient;
import com.pccw.custProfileGateway.serviceProf.DNAssignOutput;
import com.pccw.custProfileGateway.serviceProf.DNAssignmentResultDTO;
import com.pccw.custProfileGateway.serviceProf.DnAssignReqResponse;
import com.pccw.custProfileGateway.serviceProf.GetDrgSvcDetailResponse;

public class BomInvPreassgnServiceImpl implements BomInvPreassgnService {

	private final Log logger = LogFactory.getLog(getClass());

	private BomWsBackendClient bomWsBackendClient;

	public void updateInvPreassgnJunctionMsg(SbOrderDTO sbOrder, String userId) {
		try {
			// primary del
			ServiceDetailLtsDTO ltsServiceDetail = LtsSbHelper.getLtsService(sbOrder);
			if (ltsServiceDetail != null
					&& LtsBackendConstant.DN_SOURCE_DN_POOL.equals(ltsServiceDetail.getDnSource())) {
				if (LtsBackendConstant.ORDER_TYPE_CHANGE.equals(ltsServiceDetail.getOrderType())
						&& StringUtils.isNotBlank(ltsServiceDetail.getNewSrvNum())) {
					bomWsBackendClient.updateInvPreassgnJunctionMsg(
							ltsServiceDetail.getNewSrvNum(), sbOrder.getOrderId(), userId);	
				}
				else {
					bomWsBackendClient.updateInvPreassgnJunctionMsg(
							ltsServiceDetail.getSrvNum(), sbOrder.getOrderId(), userId);	
				}
			}
			
			// DNY DN change service
			ServiceDetailLtsDTO duplexNumChangeService = LtsSbHelper.getDuplexChangeService(sbOrder.getSrvDtls());
			if (duplexNumChangeService != null
					&& LtsBackendConstant.DN_SOURCE_DN_POOL.equals(duplexNumChangeService.getDnSource())
					&& StringUtils.isNotBlank(duplexNumChangeService.getNewSrvNum())) {
				bomWsBackendClient.updateInvPreassgnJunctionMsg(
						duplexNumChangeService.getNewSrvNum(), sbOrder.getOrderId(), userId);
			}
			
			// second del
			ServiceDetailLtsDTO lts2ndDelServiceDetail = LtsSbHelper.get2ndDelServices(sbOrder.getSrvDtls());
			if (lts2ndDelServiceDetail != null
					&& LtsBackendConstant.DN_SOURCE_DN_POOL.equals(lts2ndDelServiceDetail.getDnSource())) {
				bomWsBackendClient.updateInvPreassgnJunctionMsg(
						lts2ndDelServiceDetail.getSrvNum(), sbOrder.getOrderId(), userId);
			}
		} catch (Exception e) {
			logger.warn("Exception in call remote service", e);
		}
	}
    
    public DNAssignOutput dnAssignReq(String srvNum, String inventStsCd, String action, String gatewayNum) {
    	DnAssignReqResponse response = bomWsBackendClient.dnAssignReq(srvNum, inventStsCd, action, gatewayNum);
    	if (response!=null && response.getDnAssignReqResult()!=null) {
    		return response.getDnAssignReqResult();
    	}
    	return null;
    }
    
	public String getSrvDnStatus(String srvNum, String srvType) {		
		GetDrgSvcDetailResponse response  = bomWsBackendClient.getDrgSvcDetail(srvNum, srvType);
		if (response!=null && response.getGetDrgSvcDetailResult()!=null) {
			return response.getGetDrgSvcDetailResult().getStsCd();
		}
		return null;
	}
	
	public DNAssignmentResultDTO getDNAssignment(String srvNum,
			String accountCd, String boc, String projectCd,
			String specialSrvGrp, String specialSrvName) {
		List<DNAssignmentResultDTO> list = bomWsBackendClient.getDNAssignment(
				srvNum, accountCd, boc, projectCd, specialSrvGrp,
				specialSrvName);
		if (list != null && list.size() > 0) {
			return (DNAssignmentResultDTO) list.get(0);
		}
		return null;
	}	
	
	/**
	 * @return the bomWsBackendClient
	 */
	public BomWsBackendClient getBomWsBackendClient() {
		return bomWsBackendClient;
	}

	/**
	 * @param bomWsBackendClient the bomWsBackendClient to set
	 */
	public void setBomWsBackendClient(BomWsBackendClient bomWsBackendClient) {
		this.bomWsBackendClient = bomWsBackendClient;
	}

}
