package com.pccw.wq.dao;

import com.pccw.util.search.Criteria;
import com.pccw.util.search.SearchResult;
import com.pccw.wq.schema.form.WqInquiryResultFormDTO;

public interface WorkQueueInquiryDAO {
	public SearchResult<WqInquiryResultFormDTO> searchWorkQueue(Criteria pCriteria) throws Exception;
	
	public SearchResult<WqInquiryResultFormDTO> searchSbIdsWithWq(Criteria[] pCriterias) throws Exception;
	
	public SearchResult<WqInquiryResultFormDTO> searchSbActvIdsWithWq(Criteria[] pCriterias) throws Exception;
	
	public SearchResult<WqInquiryResultFormDTO> serachWqNatureStatusWithSbId(Criteria pCriteria) throws Exception;
}