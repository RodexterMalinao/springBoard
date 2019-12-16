package com.bomwebportal.thread;

import java.util.*;
import com.bomwebportal.service.*;
import com.bomwebportal.util.BomWebPortalConstant;

//import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.task.TaskExecutor;

public class BomRequestProcessor {

	  protected final Log logger = LogFactory.getLog(getClass());
	  //private TaskExecutor loadExecutor;	
	  private TaskExecutor processExecutor;
	  private OrderService orderService;
	  private CreateBomOrder createBomOrder;
	  //private BomRequestService bomRequestService;
	  private BomOrderRequestPool bomOrderRequestPool;
	  
	  /*
	  public BomRequestService getBomRequestService() {
		return bomRequestService;
	 }

	  public void setBomRequestService(BomRequestService bomRequestService) {
		this.bomRequestService = bomRequestService;
		loadBomRequest();
	  }*/

	  public CreateBomOrder getCreateBomOrder() {
		  	return createBomOrder;
	  }

	  public void setCreateBomOrder(CreateBomOrder createBomOrder) {
		  	this.createBomOrder = createBomOrder;			  
	  }
	  
	  
	  public BomOrderRequestPool getBomOrderRequestPool() {
			return bomOrderRequestPool;
	  }
	
	  public void setBomOrderRequestPool(BomOrderRequestPool bomOrderRequestPool) {
			this.bomOrderRequestPool = bomOrderRequestPool;
	  }

	/*	  
	  public BomRequestProcessor(TaskExecutor loadExecutor, TaskExecutor processExecutor){
	    //this.loadExecutor = loadExecutor;
	    this.processExecutor = processExecutor;
	  }
	 */
	  public BomRequestProcessor(TaskExecutor processExecutor){
		    this.processExecutor = processExecutor;
	  }
	  
	  public OrderService getOrderService() {
			return orderService;
	  }
		
	  public void setOrderService(OrderService orderService) {		  
			this.orderService = orderService;
			loadBomRequest();
	  }
		
	  public void loadBomRequest() {	   
		  //loadExecutor.execute(new RequestLoaderTask(bomRequestService, orderRequestPool));
	      for(int i=0; i<BomWebPortalConstant.BWP_PROCESSOR_THREAD_SIZE; i++){
	    	  processExecutor.execute(new RequestProcessorTask(orderService, bomOrderRequestPool, createBomOrder));
	      }
	  }	  
	}

