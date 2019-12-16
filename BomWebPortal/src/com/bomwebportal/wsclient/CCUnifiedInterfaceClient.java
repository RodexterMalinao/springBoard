package com.bomwebportal.wsclient;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.ws.BindingProvider;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.bomwebportal.wsclient.exception.WsClientException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pccw.bom.mob.prepaid.unified.GetCardInformation;
import com.pccw.bom.mob.prepaid.unified.GetCardInformationResponse;
import com.pccw.bom.mob.prepaid.unified.service.CCUnifiedInterface;
import com.pccw.bom.mob.prepaid.unified.service.CCUnifiedInterfaceSoap;
import com.pccw.bom.mob.schemas.prepaid.unified.TradeInput;
import com.pccw.bom.mob.schemas.prepaid.unified.TradeOutput;

@Service
public class CCUnifiedInterfaceClient {

	protected final Log logger = LogFactory.getLog(getClass());

	private String wsdl;
	private String username;
	private String password;
	
	
	private CCUnifiedInterfaceSoap port;
	
	private final static int CONN_TIMEOUT = 15000;
	
	public CCUnifiedInterfaceClient(String wsdl, String username, String password) {
		this.wsdl = wsdl;
		this.username = username;
		this.password = password;
		
		
		port = getPort();
		
		/*
		Client client =  ClientProxy.getClient(port);
		LoggingInInterceptor loggingIn = new LoggingInInterceptor();
		LoggingOutInterceptor loggingOut = new LoggingOutInterceptor();
		loggingIn.setPrettyLogging(true);
		loggingOut.setPrettyLogging(true);
		client.getInInterceptors().add(loggingIn);
		client.getOutInterceptors().add(loggingOut);
		*/

	}

	
	
	
	
	
	public Map<String,String>  getCardInformation(String msisdn) throws WsClientException {
		GetCardInformation req = new GetCardInformation();
		TradeInput ti = new TradeInput();
		TradeInput.ParamList pl = new TradeInput.ParamList();
		
		ti.setParamList(pl);
		
		
		TradeInput.ParamList.Param p = new TradeInput.ParamList.Param();
		p.setName("OperationType");
		p.setValue("0");
		pl.getParam().add(p);
		
		p = new TradeInput.ParamList.Param();
		p.setName("AccessMethod");
		p.setValue("12");
		pl.getParam().add(p);

		p = new TradeInput.ParamList.Param();
		p.setName("MDN");
		p.setValue(msisdn);
		pl.getParam().add(p);
		
		
		req.setTradeInput(ti);
		GetCardInformationResponse resp = port.getCardInformation(req);
		
		TradeOutput to = resp.getTradeOutput();

		TradeOutput.ParamList opl = to.getParamList();
		String retCode = opl.getRetCode();
		String desc = opl.getDesc();
		
		if (!"0".equals(retCode)) {
			WsClientException ex = new WsClientException(retCode, desc);
			throw ex;
		}
		List<TradeOutput.ParamList.Param> ops = opl.getParam();
		
		Map<String, String> result = new HashMap<String,String>();
		
		if (ops != null) {
			for (TradeOutput.ParamList.Param op : ops) {
				result.put(op.getName(), op.getValue());
			}
		}
		return result;
	}
	
	private CCUnifiedInterfaceSoap getPort() {

		URL localWsdl = this.getClass().getClassLoader().getResource("CCUnifiedInterface.wsdl");
		CCUnifiedInterface service = new CCUnifiedInterface(localWsdl);
		//resolver.addHandler(new SOAPLoggingHandler());
		
		service.setHandlerResolver(
				new ClientHandlerResolver(
						new UsernameTokenHeaderHandler(username, password)));
		
		CCUnifiedInterfaceSoap port = service.getCCUnifiedInterfaceSoap();
		BindingProvider bindingProvider = (BindingProvider) port;
		bindingProvider.getRequestContext()
			.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, this.wsdl);
		
		bindingProvider.getRequestContext()
			.put("javax.xml.ws.client.connectionTimeout", CONN_TIMEOUT);
		bindingProvider.getRequestContext()
			.put("com.sun.xml.internal.ws.connect.timeout", CONN_TIMEOUT);
		
		

		
		return port;
	}
	
	
	public static void main(String args[]) {
		/*
		System.setProperty("com.sun.xml.ws.transport.http.client.HttpTransportPipe.dump", "true");
		System.setProperty("com.sun.xml.internal.ws.transport.http.client.HttpTransportPipe.dump", "true");
		System.setProperty("com.sun.xml.ws.transport.http.HttpAdapter.dump", "true");
		System.setProperty("com.sun.xml.internal.ws.transport.http.HttpAdapter.dump", "true");
		*/
		//System.setProperty("http.proxyHost", "proxy.pccw.com");
		//System.setProperty("http.proxyPort", "8080");
		CCUnifiedInterfaceClient client = new CCUnifiedInterfaceClient("http://bomeaidev:7681/BOM_PrepaidWeb/ws/CCUnifiedInterface.jws?WSDL", "aesbppd", "pass1234");

		Gson gson = new GsonBuilder().setPrettyPrinting().create();

		try {
			Map<String,String> result = client.getCardInformation("99881913");//"99881755"); //"99811782");
			System.out.println(gson.toJson(result));
			
			String xx = ToStringBuilder.reflectionToString(result,
                    ToStringStyle.MULTI_LINE_STYLE, true);
			System.out.println(xx);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	

}
