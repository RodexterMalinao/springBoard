package com.bomwebportal.lts.theclub.service;

import java.util.List;

//import org.apache.commons.httpclient.NameValuePair;

//import com.bomwebportal.exception.AppRuntimeException;
//import com.bomwebportal.lts.notification.dto.LtsNotification;
import com.bomwebportal.lts.theclub.dto.BLtsTheClubTxnDTO;
import com.bomwebportal.lts.theclub.dto.LtsTheClubResponseFormDTO;
import com.bomwebportal.lts.theclub.service.LtsTheClubPointConstant;

public interface LtsTheClubPointService extends LtsTheClubPointConstant {
	public List<BLtsTheClubTxnDTO> getTxnByStatus(String transStatus);
//	public int createTxn(String theClubOrderId, String custNum, String memberId, Integer ocId, Integer dtlId,
//			Integer dtlSeq, String action, String agreementNum, String idDocNum, String idDocType, Integer itemId, Integer offerId,
//			String psefCd, String transId, Integer retryCnt, Integer retryLmt, Integer clubPoints, String transStatus);
	/**
	 * Sending earn club point request to the club system.
	 * 
	 * First the system will check if a record of the supplied theClubOrderId already exists in the transaction table B_LTS_THE_CLUB_TXN. If yes, will stop and return failed message, otherwise
	 * will insert a new record in B_LTS_THE_CLUB_TXN with status 'SENDING' and send the request immediately.
	 * 
	 * Either custNum or memberId must be supplied. custNum is the custNum of below sql:  SELECT CUST_NUM, ID_DOC_NUM, ID_DOC_TYPE from B_CUSTOMER where CUST_NUM=? and SYSTEM_ID='DRG' .
	 * The system use the ID_DOC_NUM, ID_DOC_TYPE to query the club system to get member id to proceed. If no membership info is found, throw new AppRuntimeException("Cannot get membership information from the Club system!");
	 * 
	 * @param pTheClubOrderId Required. Supplied by the caller to identify a request to preventing repeat earning/reversing club point of the same order. 
	 * @param pCustNum Required except pMemberId or pIdDocType and pIdDocNum is supplied, if supplied, will retrieve ID_DOC_NUM and ID_DOC_TYPE from BOM and retrieve the membership info from the club system to get member id to proceed.
	 * @param pMemberId Required except pCustNum or pIdDocType and pIdDocNum is supplied, if supplied, will use as the club member id and skip calling the club api to get membership info.
	 * @param pIdDocType Required except pCustNum or pMemberId is supplied, if suppied, skip retrieving cust info from BOM and will use pIdDocType and pIdDocNum to get membership info from the club system.
	 * @param pIdDocNum Required except pCustNum or pMemberId is supplied, if suppied, skip retrieving cust info from BOM and will use pIdDocType and pIdDocNum to get membership info from the club system.
	 * @param pOcId Optional, for reference.
	 * @param pDtlId Optional, for reference.
	 * @param pDtlSeq Optional, for reference.
	 * @param pItemId Optional, for reference.
	 * @param pOfferId Optional, for reference.
	 * @param pPsefCd Optional, for reference.
	 * @param pPackageCode Required. It is required by the club api.
	 * @param pClubPoints Required. Club points to earn
	 * @param pAgreementNum Optional, for reference.
	 * @param pChannel Optional, for reference.
	 * @param pVerify If true, will use theClubOrderId for checking whether an existing record of the request exists. False to skip the checking.
	 * @param pTest If true, will not send request to the Club API. 
	 * @return LtsTheClubResponseFormDTO Containing response information from the club api. 
	 */
	public LtsTheClubResponseFormDTO earnClubPoint(String pTheClubOrderId, String pCustNum, String pMemberId, String pIdDocType, String pIdDocNum, Integer pOcId, Integer pDtlId
			,Integer pDtlSeq, Integer pItemId, Integer pOfferId, String pPsefCd, String pPackageCode, Integer pClubPoints, String pAgreementNum, String pChannel, boolean pVerify, boolean pTest);  
	/**
	 * Sending deduct club point request to the club system. It has the same work flow of calling the club API except this is for reversing club point. Please see earnClubPoint.
	 * 
	 * @see com.bomwebportal.lts.theclub.service.LtsTheClubPointService#earnClubPoint(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.Integer, java.lang.Integer, java.lang.Integer, java.lang.Integer, java.lang.Integer, java.lang.String, java.lang.String, java.lang.Integer, java.lang.String, java.lang.String, boolean, boolean)
	 * 
	 * @param pTheClubOrderId theClubOrderId Required. Supplied by the caller to identify a request to preventing repeat earning/reversing club point of the same order. 
	 * @param pCustNum Required except pMemberId or pIdDocType and pIdDocNum is supplied, if supplied, will retrieve ID_DOC_NUM and ID_DOC_TYPE from BOM and retrieve the membership info from the club system to get member id to proceed.
	 * @param pMemberId Required except pCustNum or pIdDocType and pIdDocNum is supplied, if supplied, will use as the club member id and skip calling the club api to get membership info.
	 * @param pIdDocType Required except pCustNum or pMemberId is supplied, if suppied, skip retrieving cust info from BOM and will use pIdDocType and pIdDocNum to get membership info from the club system.
	 * @param pIdDocNum Required except pCustNum or pMemberId is supplied, if suppied, skip retrieving cust info from BOM and will use pIdDocType and pIdDocNum to get membership info from the club system.
	 * @param pOcId Optional, for reference.
	 * @param pDtlId Optional, for reference.
	 * @param pDtlSeq Optional, for reference.
	 * @param pItemId Optional, for reference.
	 * @param pOfferId Optional, for reference.
	 * @param pPsefCd Optional, for reference.
	 * @param pPackageCode Required. It is required by the club api.
	 * @param pReverseTransId Required. The trans id of a previous success earning request that this request deduct the club point with.
	 * @param pClubPoints Required. Club points to deduct.
	 * @param pAgreementNum Optional, for reference.
	 * @param pChannel Optional, for reference.
	 * @param pVerify If true, will use theClubOrderId for checking whether an existing record of the request exists. False to skip the checking.
	 * @param pTest If true, will not send request to the Club API. 
	 * @return LtsTheClubResponseFormDTO Containing response information from the club api. 
	 */
	public LtsTheClubResponseFormDTO reverseClubPoint(String pTheClubOrderId, String pCustNum, String pMemberId, String pIdDocType, String pIdDocNum, Integer pOcId, Integer pDtlId
			,Integer pDtlSeq, Integer pItemId, Integer pOfferId, String pPsefCd, String pPackageCode, String pReverseTransId, Integer pClubPoints, String pAgreementNum, String pChannel, boolean pVerify, boolean pTest) ;
	/**
	 * Enquiring membership information from the club system. This method will try to determine appropriate search type if none is supplied.
	 * 
	 * Depends on searchType, if searchType is MEMBERID, the memberId should not be null
	 * if searchType is MEMBERID, the memberId should not be null
	 * if searchType is LOGINID, the loginId should not be null
	 * if searchType is DOCUMENT, the idDocType and idDocNum should not be null
	 * Document type must be a type acceptable by the club system. With reference to the document, available document type is HKID, PASS, CHINAID and MACAUID.
	 * 
	 * @param searchingType Available search type: LtsTheClubPointConstant.MEMBER_SEARCH_TYPE_MEMBERID, LtsTheClubPointConstant.MEMBER_SEARCH_TYPE_LOGINID, LtsTheClubPointConstant.MEMBER_SEARCH_TYPE_DOCUMENT
	 * @param memberId Required if search type=LtsTheClubPointConstant.MEMBER_SEARCH_TYPE_MEMBERID. The system will search membership info of the account of the supplied club member id.
	 * @param loginId Required if search type=LtsTheClubPointConstant.MEMBER_SEARCH_TYPE_LOGINID. The system will search membership info of the supplied the club member login id.
	 * @param idDocType Required if search type=LtsTheClubPointConstant.MEMBER_SEARCH_TYPE_DOCUMENT. The system will search membership info by the supplied document type with document id. Available document type: HKID, PASS, CHINAID and MACAUID.
	 * @param idDocNum Required if search type=LtsTheClubPointConstant.MEMBER_SEARCH_TYPE_DOCUMENT. The system will search membership info by the supplied document id with document type.
	 * @return LtsTheClubResponseFormDTO Containing response information from the club api. 
	 */
	public LtsTheClubResponseFormDTO getTheClubMembershipInfo(String searchingType, String memberId, String loginId, String idDocType, String idDocNum);
	public void processRequest(String transStatus, boolean verify, boolean test);
}
