package com.pccw.wq.test;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.pccw.util.spring.SpringApplicationContext;
import com.pccw.wq.schema.dto.WorkQueueDTO;
import com.pccw.wq.schema.dto.WorkQueueNatureDTO;
import com.pccw.wq.service.WorkQueueService;

public class UnitTest {
	public static void main(String[] args) {
		new ClassPathXmlApplicationContext("spring-service-bean-wq.xml");
		
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
		} catch (Exception e) {
			System.out.println(ExceptionUtils.getFullStackTrace(e));
		}
	}
}
