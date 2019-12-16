package com.bomwebportal.lts.theclub.service;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.Date;
import java.util.List;

import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.lts.theclub.dao.LtsTheClubTxnDAO;
import com.bomwebportal.lts.theclub.dto.CustomerDocInfoDTO;
import com.bomwebportal.lts.theclub.dto.LtsTheClubRequestFormDTO;
import com.bomwebportal.lts.theclub.dto.LtsTheClubResponseFormDTO;
import com.bomwebportal.lts.theclub.dto.BLtsTheClubTxnDTO;
import com.bomwebportal.lts.theclub.dto.BLtsTheClubTxnLogDTO;

/**
 * @author 01517028
 *
 */
@Transactional(readOnly = true)
public class LtsTheClubPointServiceImpl implements LtsTheClubPointService {
	private String userName, password, source, lang, lob, searchType, loginId;

	private String getMemberBasicInfoWithMaskedIDUrl;
	private String doInstantEarnPointUrl;
	private String doInstantEarnReversePointUrl;

	@Autowired
	LtsTheClubTxnDAO ltsTheClubTxnDAO;

	public LtsTheClubTxnDAO getLtsTheClubTxnDAO() {
		return ltsTheClubTxnDAO;
	}

	public void setLtsTheClubTxnDAO(LtsTheClubTxnDAO ltsTheClubTxnDAO) {
		this.ltsTheClubTxnDAO = ltsTheClubTxnDAO;
	}

	public List<BLtsTheClubTxnDTO> getTxnByStatus(String transStatus) {
		return this.ltsTheClubTxnDAO.getTxnByStatus(transStatus);
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int updateTxnStatus(String status, String transId, Integer retryCnt, Integer txnId) {
		return this.ltsTheClubTxnDAO.updateStatusByTxnId(status, transId, retryCnt, null, null, txnId);
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int updateTxnStatus(String status, String transId, Integer retryCnt, String rtnCode, String rtnMsg,
			Integer txnId) {
		return this.ltsTheClubTxnDAO.updateStatusByTxnId(status, transId, retryCnt, rtnCode, rtnMsg, txnId);
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int updateTxnLogStatus(String responseStr, String rtnCode, String rtnMsg, Integer txnLogId) {
		return this.ltsTheClubTxnDAO.updateStatusByTxnLogId(null, responseStr, rtnCode, rtnMsg, txnLogId);
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int createTxn(String theClubOrderId, String custNum, String memberId, Integer ocId, Integer dtlId,
			Integer dtlSeq, String action, String agreementNum, String idDocNum, String idDocType, Integer itemId,
			Integer offerId, String psefCd, String packageCode, String reverseTransId, Integer retryCnt, Integer retryLmt, Integer clubPoints,
			String transStatus, String rtnCode, String rtnMsg, String channel) {
		Integer txnId = this.ltsTheClubTxnDAO.getNewTxnId();
		String transId = null;
		BLtsTheClubTxnDTO ltsTheClubTxnDTO = new BLtsTheClubTxnDTO(txnId, theClubOrderId, custNum, memberId, ocId,
				dtlId, dtlSeq, action, agreementNum, idDocNum, idDocType, itemId, offerId, psefCd, packageCode, reverseTransId, transId, retryCnt,
				retryLmt, clubPoints, transStatus, rtnCode, rtnMsg, channel);
		int insertRet = this.ltsTheClubTxnDAO.insertTxn(ltsTheClubTxnDTO);
		return txnId;
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int createTxnLog(Integer txnId, String requestStr, String responseStr, String rtnCode,
			String rtnMsg) {
		Integer txnLogId = this.ltsTheClubTxnDAO.getNewTxnLogId();
		BLtsTheClubTxnLogDTO ltsTheClubTxnLogDTO = new BLtsTheClubTxnLogDTO(txnLogId, txnId, requestStr, responseStr, rtnCode, rtnMsg);
		int insertRet = this.ltsTheClubTxnDAO.insertTxnLog(ltsTheClubTxnLogDTO);
		return txnLogId;
	}
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public int updateMemberIdByTxnId(String memberId, Integer txnId) {
		int ret = this.ltsTheClubTxnDAO.updateMemberIdByTxnId(memberId, txnId);
		return ret;
	}

	public void processRequest(String transStatus, boolean verify, boolean test) throws AppRuntimeException {
		List<BLtsTheClubTxnDTO> orderClubPointOffers = this.getTxnByStatus(transStatus);
		StringBuffer sb = new StringBuffer();
		for (BLtsTheClubTxnDTO ltsTheClubTxnDTO : orderClubPointOffers) {
			try{
				this.verifyAndSendTheClubRequest(ltsTheClubTxnDTO.getTheClubOrderId(), ltsTheClubTxnDTO.getCustNum(), ltsTheClubTxnDTO.getMemberId(), ltsTheClubTxnDTO.getIdDocType(), ltsTheClubTxnDTO.getIdDocNum(),
						ltsTheClubTxnDTO.getOcId(), ltsTheClubTxnDTO.getDtlId(), ltsTheClubTxnDTO.getDtlSeq(), ltsTheClubTxnDTO.getItemId(), ltsTheClubTxnDTO.getOfferId(), ltsTheClubTxnDTO.getPsefCd(),
						ltsTheClubTxnDTO.getPackageCode(), ltsTheClubTxnDTO.getReverseTransId(), ltsTheClubTxnDTO.getClubPoints(), ltsTheClubTxnDTO.getAction(), ltsTheClubTxnDTO.getAgreementNum(), null, verify, test);
			}catch(AppRuntimeException are){
				are.printStackTrace();
				sb.append("Txn id:").append(ltsTheClubTxnDTO.getTxnId()).append(", message:").append(are.getCustomMessage());
			}catch(Exception e){
				e.printStackTrace();
				sb.append("Txn id:").append(ltsTheClubTxnDTO.getTxnId()).append(", message:").append(e.getMessage());
			}
		}
		
		if(sb.length()>0){
			throw new AppRuntimeException(sb.toString());
		}
	}
	
	/* (non-Javadoc)
	 * @see com.bomwebportal.lts.theclub.service.LtsTheClubPointService#earnClubPoint(java.lang.String, java.lang.String, java.lang.String, java.lang.Integer, java.lang.Integer, java.lang.Integer, java.lang.Integer, java.lang.Integer, java.lang.String, java.lang.String, java.lang.Integer, java.lang.String, java.lang.String, boolean, boolean)
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public LtsTheClubResponseFormDTO earnClubPoint(String pTheClubOrderId, String pCustNum, String pMemberId, String pIdDocType, String pIdDocNum,
			Integer pOcId, Integer pDtlId, Integer pDtlSeq, Integer pItemId, Integer pOfferId, String pPsefCd, String pPackageCode,
			Integer pClubPoints, String pAgreementNum, String pChannel, boolean pVerify, boolean pTest) {
		return verifyAndSendTheClubRequest(pTheClubOrderId, pCustNum, pMemberId, pIdDocType, pIdDocNum, pOcId, pDtlId, pDtlSeq, pItemId, pOfferId, pPsefCd,
				pPackageCode, null, pClubPoints, ACTION_EARN_POINT, pAgreementNum, pChannel, pVerify, pTest);
	}

	/* (non-Javadoc)
	 * @see com.bomwebportal.lts.theclub.service.LtsTheClubPointService#reverseClubPoint(java.lang.String, java.lang.String, java.lang.String, java.lang.Integer, java.lang.Integer, java.lang.Integer, java.lang.Integer, java.lang.Integer, java.lang.String, java.lang.String, java.lang.String, java.lang.Integer, java.lang.String, java.lang.String, boolean, boolean)
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public LtsTheClubResponseFormDTO reverseClubPoint(String pTheClubOrderId, String pCustNum, String pMemberId, String pIdDocType, String pIdDocNum,
			Integer pOcId, Integer pDtlId, Integer pDtlSeq, Integer pItemId, Integer pOfferId, String pPsefCd, String pPackageCode,
			String pReverseTransId, Integer pClubPoints, String pAgreementNum, String pChannel, boolean pVerify, boolean pTest) {
		return verifyAndSendTheClubRequest(pTheClubOrderId, pCustNum, pMemberId, pIdDocType, pIdDocNum, pOcId, pDtlId, pDtlSeq, pItemId, pOfferId, pPsefCd,
				pPackageCode, pReverseTransId, pClubPoints, ACTION_REVERSE_POINT, pAgreementNum, pChannel, pVerify, false);
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	private LtsTheClubResponseFormDTO verifyAndSendTheClubRequest(String pTheClubOrderId, String pCustNum, String pMemberId, String pIdDocType,
			String pIdDocNum, Integer pOcId, Integer pDtlId, Integer pDtlSeq, Integer pItemId, Integer pOfferId, String pPsefCd,
			String pPackageCode, String pReverseTransId, Integer pClubPoints, String pAction, String pAgreementNum, String pChannel, boolean pVerify, boolean pTest) {
		// Check if the order has earned club point already
//		String theClubOrderId = getTheClubPointOrderId(pOcId, pDtlId, pDtlSeq, pItemId);
		if(StringUtils.isBlank(pTheClubOrderId)){
//			LtsTheClubResponseFormDTO theClubResponse = new LtsTheClubResponseFormDTO();
//			theClubResponse.setStatus(AJAX_RESPONSE_STATUS_FAILED);
//			theClubResponse.setStatusDesc("Please provide theClubOrderId.");
//			// theClubResponse.setExistingTxn(existingTxn);
//			return theClubResponse;
			return this.createltsTheClubResponseFormDTOMessage(AJAX_RESPONSE_STATUS_FAILED, "Please provide theClubOrderId.");
		}
		List<BLtsTheClubTxnDTO> existingTxn = this.ltsTheClubTxnDAO.getExistingTxn(pTheClubOrderId, pPackageCode,
				pAction);
		BLtsTheClubTxnDTO retryTxn = null;
		if (pVerify) {
			if (existingTxn.size() == 1) {// verify whether a previous request
											// already sent
				retryTxn = existingTxn.get(0);
				// Except failed request allowing retry, below status should
				// quit immediately
//				if (TRANS_STATUS_SUCCESS.equals(retryTxn.getTransStatus())) {
//					LtsTheClubResponseFormDTO theClubResponse = new LtsTheClubResponseFormDTO();
//					theClubResponse.setStatus(AJAX_RESPONSE_STATUS_FAILED);
//					theClubResponse.setStatusDesc("This order has earned club point already.");
//					// theClubResponse.setExistingTxn(existingTxn);
//					return theClubResponse;
//				} else if (TRANS_STATUS_SENDING.equals(retryTxn.getTransStatus())) {
//					LtsTheClubResponseFormDTO theClubResponse = new LtsTheClubResponseFormDTO();
//					theClubResponse.setStatus(AJAX_RESPONSE_STATUS_FAILED);
//					theClubResponse.setStatusDesc("This order has a request sending in process.");
//					// theClubResponse.setExistingTxn(existingTxn);
//					return theClubResponse;
//				}
				if (!TRANS_STATUS_PENDING.equals(retryTxn.getTransStatus()) && !TRANS_STATUS_FAILED.equals(retryTxn.getTransStatus())) {
//					LtsTheClubResponseFormDTO theClubResponse = new LtsTheClubResponseFormDTO();
//					theClubResponse.setStatus(AJAX_RESPONSE_STATUS_FAILED);
//					theClubResponse.setStatusDesc("This order is not pending or failed, please check.");
//					// theClubResponse.setExistingTxn(existingTxn);
//					return theClubResponse;
					return this.createltsTheClubResponseFormDTOMessage(AJAX_RESPONSE_STATUS_FAILED, "This order is not pending or failed, please check.");
				} 
			} else if (existingTxn.size() > 1) {
//				LtsTheClubResponseFormDTO theClubResponse = new LtsTheClubResponseFormDTO();
//				theClubResponse.setStatus(AJAX_RESPONSE_STATUS_FAILED);
//				theClubResponse.setStatusDesc("This order has more than 1 record, please check.");
//				// theClubResponse.setExistingTxn(existingTxn);
//				return theClubResponse;
				return this.createltsTheClubResponseFormDTOMessage(AJAX_RESPONSE_STATUS_FAILED, "This order has more than 1 record, please check.");
			}
		}
		String theClubMemberId = pMemberId;
		String idDocType = null;
		String idDocNum = null;
		int txnId = 0;

		if (retryTxn != null) {// existing record found.
			if (retryTxn.getRetryCnt() == retryTxn.getRetryLmt()) {
//				LtsTheClubResponseFormDTO theClubResponse = new LtsTheClubResponseFormDTO();
//				theClubResponse.setStatus(AJAX_RESPONSE_STATUS_FAILED);
//				theClubResponse.setStatusDesc("This order has reached the retry limit.");
//				// theClubResponse.setExistingTxn(existingTxn);
//				return theClubResponse;
				return this.createltsTheClubResponseFormDTOMessage(AJAX_RESPONSE_STATUS_FAILED, "This order has reached the retry limit.");
			}
			txnId = retryTxn.getTxnId();
			this.updateTxnStatus(TRANS_STATUS_SENDING, retryTxn.getTransId(), retryTxn.getRetryCnt() + 1, txnId);
			theClubMemberId = retryTxn.getMemberId();
			idDocType = retryTxn.getIdDocType();
			idDocNum = retryTxn.getIdDocNum();
		}

		if (StringUtils.isBlank(theClubMemberId)) {

			// get customer info from BOM to get membershipinfo from the
			// club
			// system
			if(StringUtils.isBlank(pIdDocType) || StringUtils.isBlank(pIdDocNum)){
				List<CustomerDocInfoDTO> custDocs = this.ltsTheClubTxnDAO.getCustDocByCustNum(pCustNum);// only
																										// get
																										// the
																										// first
																										// one.
				String ret = null;
				CustomerDocInfoDTO custDoc = null;
				if (custDocs != null && custDocs.size() > 0) {
					custDoc = custDocs.get(0);
				} else {
//					throw new AppRuntimeException("No customer was found!");
					return this.createltsTheClubResponseFormDTOMessage(AJAX_RESPONSE_STATUS_FAILED, "No customer was found!");
				}
				idDocType = custDoc.getIdDocType();
				idDocNum = custDoc.getIdDocNum();
			}else{
				idDocType = pIdDocType;
				idDocNum = pIdDocNum;
			}
			LtsTheClubResponseFormDTO membershipInfo = this.getTheClubMembershipInfo(this.searchType, pMemberId, loginId,
					idDocType, idDocNum);
			if (membershipInfo != null && RTNCODE_SUCCEED.equals(membershipInfo.getRtnCode())) {
				theClubMemberId = membershipInfo.getMemberId();
			} else {
				this.updateTxnStatus(TRANS_STATUS_FAILED, null, null, "BOM_ERR_01", "Cannot get membership information from the Club system!", txnId);
//				throw new AppRuntimeException("Cannot get membership information from the Club system!");
				return this.createltsTheClubResponseFormDTOMessage(AJAX_RESPONSE_STATUS_FAILED, "Cannot get membership information from the Club system!");
			}
			if(retryTxn != null){
				this.updateMemberIdByTxnId(theClubMemberId, retryTxn.getTxnId());
			}
		}

		if (retryTxn == null) {
			try {
				txnId = this.createTxn(pTheClubOrderId, pCustNum, theClubMemberId, pOcId, pDtlId, pDtlSeq, pAction,
						pAgreementNum, idDocNum, idDocType, pItemId, pOfferId, pPsefCd, pPackageCode, pReverseTransId, 0, 200, pClubPoints,
						TRANS_STATUS_SENDING, null, null, pChannel);
			} catch (Exception e) {
				e.printStackTrace();
				this.updateTxnStatus(TRANS_STATUS_FAILED, null, null, txnId);
				return this.createltsTheClubResponseFormDTOMessage(AJAX_RESPONSE_STATUS_FAILED, e.getMessage());
			}
		}
		LtsTheClubResponseFormDTO theClubResponse = null;

		try {
			if(!pTest){
				theClubResponse = this.sendTheClubRequest(txnId, pTheClubOrderId, theClubMemberId, pPackageCode, pReverseTransId, pAction,
						pClubPoints, pChannel);
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.updateTxnStatus(TRANS_STATUS_FAILED, null, null, txnId);
			return this.createltsTheClubResponseFormDTOMessage(AJAX_RESPONSE_STATUS_FAILED, e.getMessage());
		}
		if (theClubResponse == null) {
			this.updateTxnStatus(TRANS_STATUS_FAILED, null, null, txnId);
		} else {
			if (RTNCODE_SUCCEED.equals(theClubResponse.getRtnCode())) {
				this.updateTxnStatus(TRANS_STATUS_SUCCESS, theClubResponse.getTransId(), null, theClubResponse.getRtnCode(), theClubResponse.getRtnMsg(), txnId);
			} else {
				this.updateTxnStatus(TRANS_STATUS_FAILED, theClubResponse.getTransId(), null, theClubResponse.getRtnCode(), theClubResponse.getRtnMsg(), txnId);
			}
		}
		return theClubResponse;
	}

	private LtsTheClubResponseFormDTO createltsTheClubResponseFormDTOMessage(String inStatusStr, String inStatueDescStr){
		LtsTheClubResponseFormDTO theClubResponse = new LtsTheClubResponseFormDTO();
		theClubResponse.setStatus(inStatusStr);
		theClubResponse.setStatusDesc(inStatueDescStr);
		// theClubResponse.setExistingTxn(existingTxn);
		return theClubResponse;
	}
	private LtsTheClubResponseFormDTO sendTheClubRequest(Integer txnId, String theClubOrderId, String theClubMemberId,
			String packageCode, String transId, String action, Integer clubPoints, String channel) throws Exception {

		LtsTheClubResponseFormDTO ret = null;

		NameValuePair[] webServiceRequestData = null;
		
		int txnLogId = 0;
		String responseStr = null;
		String wsUrl = null;
		if (ACTION_EARN_POINT.equals(action)) {
			wsUrl = getTheClubUrl(DO_INSTANT_EARN_POINT);
			webServiceRequestData = LtsTheClubPointUtil.doInstantEarnPointValuePair(userName, password,
					source, lang, lob, theClubMemberId, packageCode, theClubOrderId, clubPoints + "", channel);
		} else if (ACTION_REVERSE_POINT.equals(action)) {
			wsUrl = getTheClubUrl(DO_INSTANT_EARN_REVERSE_POINT);
			webServiceRequestData = LtsTheClubPointUtil.doInstantReversePointValuePair(userName, password,
					source, lang, lob, theClubMemberId, transId, theClubOrderId, clubPoints , channel);
		}
		String requestStr = valuePairToStr(webServiceRequestData);
		txnLogId = this.createTxnLog(txnId, requestStr, null, null, null);
		
		try {

			responseStr = LtsTheClubPointUtil.sendPostRequest(wsUrl, webServiceRequestData);
		} catch (Exception e) {
			e.printStackTrace();
			this.updateTxnLogStatus(responseStr, null, null, txnLogId);
			return null;
		}
		ret = LtsTheClubPointUtil.jsonToResponse(responseStr);

		if (ret == null) {
			this.updateTxnLogStatus(responseStr, null, null, txnLogId);
		} else {
			this.updateTxnLogStatus(responseStr, ret.getRtnCode(), ret.getRtnMsg(), txnLogId);
		}

		return ret;
	}

	// Depends on searchType, if searchType is MEMBERID, the memberId should not
	// be null
	// if searchType is MEMBERID, the memberId should not be null
	// if searchType is LOGINID, the loginId should not be null
	// if searchType is DOCUMENT, the idDocType and idDocNum should not be null

	public LtsTheClubResponseFormDTO getTheClubMembershipInfo(String searchType, String memberId, String loginId,
			String idDocType, String idDocNum) {
		if (searchType == null || !(MEMBER_SEARCH_TYPE_MEMBERID.equals(searchType)
				|| MEMBER_SEARCH_TYPE_LOGINID.equals(searchType) || MEMBER_SEARCH_TYPE_DOCUMENT.equals(searchType))) {
			if (memberId != null) {
				searchType = MEMBER_SEARCH_TYPE_MEMBERID;
			} else if (idDocType != null && idDocNum != null) {
				searchType = MEMBER_SEARCH_TYPE_DOCUMENT;
			} else if (loginId != null) {
				searchType = MEMBER_SEARCH_TYPE_LOGINID;
			}
		}
		NameValuePair[] webServiceRequestData = LtsTheClubPointUtil.getMemberBaseInfoWithMaskedIdValuePair(
				this.userName, this.password, this.source, this.lang, this.lob, searchType, memberId, loginId,
				idDocType, idDocNum);
		LtsTheClubResponseFormDTO response = this.getTheClubMembershipInfo(webServiceRequestData);
		return response;
	}

	private String getTheClubUrl(String methodName) {
		String ret = null;
		if (GET_MEMBER_BASIC_INFO_WITH_MASKED_ID.equals(methodName)) {
			ret = this.getMemberBasicInfoWithMaskedIDUrl;
		} else if (DO_INSTANT_EARN_POINT.equals(methodName)) {
			ret = this.doInstantEarnPointUrl;
		} else if (DO_INSTANT_EARN_REVERSE_POINT.equals(methodName)) {
			ret = this.doInstantEarnReversePointUrl;
		}
		return ret;
	}

	public String getUserName() {
		return userName;
	}

	public String getPassword() {
		return password;
	}

	public String getSource() {
		return source;
	}

	public String getLang() {
		return lang;
	}

	public String getLob() {
		return lob;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	public void setLob(String lob) {
		this.lob = lob;
	}

	public String getGetMemberBasicInfoWithMaskedIDUrl() {
		return getMemberBasicInfoWithMaskedIDUrl;
	}

	public String getDoInstantEarnPointUrl() {
		return doInstantEarnPointUrl;
	}

	public String getDoInstantEarnReversePointUrl() {
		return doInstantEarnReversePointUrl;
	}

	public void setGetMemberBasicInfoWithMaskedIDUrl(String getMemberBasicInfoWithMaskedIDUrl) {
		this.getMemberBasicInfoWithMaskedIDUrl = getMemberBasicInfoWithMaskedIDUrl;
	}

	public void setDoInstantEarnPointUrl(String doInstantEarnPointUrl) {
		this.doInstantEarnPointUrl = doInstantEarnPointUrl;
	}

	public void setDoInstantEarnReversePointUrl(String doInstantEarnReversePointUrl) {
		this.doInstantEarnReversePointUrl = doInstantEarnReversePointUrl;
	}

	public String getSearchType() {
		return searchType;
	}

	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public static String getTheClubPointOrderId(Integer ocId, Integer dtlId, Integer dtlSeq, Integer itemId) {
		String ret = ocId + "" + dtlId + "" + dtlSeq+ "" +itemId;
		return ret;
	}

	public static String valuePairToStr(NameValuePair[] valuePairs) {
		String ret = "[";
		if (valuePairs != null && valuePairs.length > 0) {
			ret += "\"" + valuePairs[0].getName() + "\":\"" + valuePairs[0].getValue() + "\"";
			for (int i = 1; i < valuePairs.length; i++) {
				ret += ",\"" + valuePairs[i].getName() + "\":\"" + valuePairs[i].getValue() + "\"";
			}
		}
		ret = ret + "]";
		return ret;
	}

	public LtsTheClubResponseFormDTO getTheClubMembershipInfo(NameValuePair[] webServiceRequestData) {
		String response = null;
		LtsTheClubResponseFormDTO ret = null;
		try {
			response = LtsTheClubPointUtil.sendPostRequest(getTheClubUrl(GET_MEMBER_BASIC_INFO_WITH_MASKED_ID),
					webServiceRequestData);
			ret = LtsTheClubPointUtil.jsonToResponse(response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return ret;
	}
}
