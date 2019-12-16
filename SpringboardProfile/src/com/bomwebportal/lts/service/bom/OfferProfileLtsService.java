package com.bomwebportal.lts.service.bom;

import java.util.List;

import com.bomwebportal.lts.dto.bom.BomAttbDTO;
import com.bomwebportal.lts.dto.profile.OfferDetailProfileLtsDTO;
import com.bomwebportal.lts.dto.profile.ServiceDetailProfileLtsDTO;
import com.pccw.springboard.svc.server.dto.lts.OfferDetailCommitmentDTO;
import com.pccw.springboard.svc.server.dto.lts.OfferDetailDiscountDTO;
import com.pccw.springboard.svc.server.dto.lts.OfferDetailIncentiveDTO;
import com.pccw.springboard.svc.server.dto.lts.OfferDetailRecurringChargesDTO;



public interface OfferProfileLtsService {
	
	public OfferDetailProfileLtsDTO[] getLtsOfferProfile(String pCcSrvId, String pApplnDate);
	
	public OfferDetailProfileLtsDTO[] getLtsEndedOfferProfile(String pCcSrvId, String pApplnDate);
	
	public OfferDetailProfileLtsDTO[] getImsOfferProfile(String pFsa, String pApplnDate);
	
	public void getProfileDetails(ServiceDetailProfileLtsDTO pProfileSrv);
	
	public List<String> getPsefCdByOfferId(List<String> pOfferList);
	
	public List<OfferDetailCommitmentDTO> getCommitment(String pSystemId, String pServiceId, String effectiveInd);
	
	public List<OfferDetailRecurringChargesDTO> getRecurringCharges(String pSystemId, String pServiceId, String effectiveInd);
	
	public List<OfferDetailDiscountDTO> getDiscount(String pSystemId, String pServiceId, String effectiveInd);
	
	public List<OfferDetailIncentiveDTO> getIncentive(String pSystemId, String pServiceId, String effectiveInd);
	
	public List<BomAttbDTO> getBomAttb(String offerCd, String appDate);
	
	public List<BomAttbDTO> getBomAttbByOfferId(String offerId, String appDate);
}
