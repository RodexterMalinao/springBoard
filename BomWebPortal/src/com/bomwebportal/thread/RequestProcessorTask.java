package com.bomwebportal.thread;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bomwebportal.service.OrderService;
import com.bomwebportal.util.BomWebPortalConstant;

public class RequestProcessorTask extends Thread{
	  
	  protected final Log logger = LogFactory.getLog(getClass());
	  private OrderService orderService;
	  private BomOrderRequestPool orderRequestPool;
	  private CreateBomOrder createBomOrder;
	  	

	  public RequestProcessorTask(OrderService orderService, BomOrderRequestPool orderRequestPool, CreateBomOrder createBomOrder) {
		  this.orderService = orderService;
		  this.orderRequestPool = orderRequestPool;
		  this.createBomOrder = createBomOrder;
	  }
	  
	    public void run() {
	    	while (true){
	    		logger.info("Thread ["+this.getId()+"] is free.");
	    		try {
	    			//logger.info("Request Order ID List in request pool [size]: "+orderRequestPool.getRequestPoolSize());
	    			if(!orderRequestPool.isRequestPoolEmpty()){
	    				
	    				String requestOrderId = orderRequestPool.getBomOrderReq();
	    				logger.info(this.getId()+"; "+ requestOrderId);
	    				orderService.updateBomWebOrderStatus(requestOrderId, BomWebPortalConstant.BWP_ORDER_PROCESS);
	    				
	    				createBomOrder.processRequest(requestOrderId);

	    			}
					sleep(5000);
					
				} catch (InterruptedException e) {						
					e.printStackTrace();
				} catch (Exception e){
					e.printStackTrace();
				}
	    	}		    	
	    }
	  
}