package com.pccw.wq.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.pccw.util.search.SearchResult;
import com.pccw.util.spring.SpringApplicationContext;
import com.pccw.wq.schema.dto.WorkQueueDTO;
import com.pccw.wq.schema.dto.WorkQueueInquiryCriteriaDTO;
import com.pccw.wq.schema.dto.WorkQueueNatureDTO;
import com.pccw.wq.schema.form.WqInquiryResultFormDTO;
import com.pccw.wq.service.WorkQueueService;

public class TestWorkQueueService {

	@Before
	public void init() {
		new ClassPathXmlApplicationContext("spring-service-bean-wq.xml");
	}
	
	@Test
	public void testSearchWorkQueue() {
		try {
			WorkQueueInquiryCriteriaDTO criteria = new WorkQueueInquiryCriteriaDTO();
			criteria.setSbIds(new String[] {"TP10000660"});
			SearchResult<WqInquiryResultFormDTO> searchResult = SpringApplicationContext.getBean(WorkQueueService.class).searchWorkQueue(
					criteria, null, "UNITTEST");
			assertTrue("NO RESULT FOUND", (searchResult.getTotalCount() > 0));
		} catch (Exception e) {
			fail(ExceptionUtils.getFullStackTrace(e));
		}
		
	}

	@Test
	public void testCreateWorkQueue() {
		WorkQueueDTO wqDTO = new WorkQueueDTO();
		wqDTO.setSbId("TP10000537");
		wqDTO.setSbDtlId("1");
		wqDTO.setSbShopCode("TP1");
		wqDTO.setTypeOfService("TEL");
		wqDTO.setServiceId("22249024");
		wqDTO.setSrd("201102280000");

		WorkQueueNatureDTO wqNatureDTO = new WorkQueueNatureDTO();
		wqNatureDTO.setWorkQueueType("FS-E");
		wqNatureDTO.setWorkQueueSubType("PARTIAL_WQ");
		wqNatureDTO.setWorkQueueNatureId("4");

		try {
			SpringApplicationContext.getBean(WorkQueueService.class).createWorkQueue(wqDTO, new WorkQueueNatureDTO[] {wqNatureDTO}, null);
			assertNotNull(wqDTO.getWqId());
		} catch (Exception e) {
			fail(ExceptionUtils.getFullStackTrace(e));
		}
	}
}
