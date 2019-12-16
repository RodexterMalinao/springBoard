package com.bomwebportal.wsclient;

import java.net.URL;
import java.util.List;

import javax.xml.ws.BindingProvider;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bomwebportal.util.Utility;
import com.bomwebportal.wsclient.exception.WsClientException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pccw.schema.xsd.CancelPortInOrdRequest;
import com.pccw.schema.xsd.CancelPortInOrdResult;
import com.pccw.schema.xsd.CheckNoChannelRequest;
import com.pccw.schema.xsd.CheckNoChannelResult;
import com.pccw.schema.xsd.CheckNoStatusInCInvRequest;
import com.pccw.schema.xsd.CheckNoStatusInCInvResult;
import com.pccw.schema.xsd.CreatePortInOrdRequest;
import com.pccw.schema.xsd.CreatePortInOrdResult;
import com.pccw.schema.xsd.GetCurrDNORequest;
import com.pccw.schema.xsd.GetCurrDNOResult;
import com.pccw.schema.xsd.GetPortOutReqRequest;
import com.pccw.schema.xsd.GetPortOutReqResult;
import com.pccw.schema.xsd.ReplyPortOutOrdRequest;
import com.pccw.schema.xsd.ReplyPortOutOrdResult;
import com.pccw.schema.xsd.SendSbMvnoMdsRequest;
import com.pccw.schema.xsd.SendSbMvnoMdsResult;
import com.pccw.services.BomMobMvnoGatewayService;
import com.pccw.services.BomMobMvnoGatewayServicePortType;

import bom.mob.schema.javabean.si.xsd.CheckNoChannelDTO;
import bom.mob.schema.javabean.si.xsd.CslNoStatusDTO;
import bom.mob.schema.javabean.si.xsd.GetCurrentDNODTO;
import bom.mob.schema.javabean.si.xsd.MvnoMdsObjectDTO;
import bom.mob.schema.javabean.si.xsd.OperInfoDTO;
import bom.mob.schema.javabean.si.xsd.PortOutRequestDTO;
import bom.mob.schema.javabean.si.xsd.ResultValueDTO;


public class MvnoWSClient {

	protected final Log logger = LogFactory.getLog(getClass());

	private String wsdl;
	private String username;
	private String password;
	
	private final OperInfoDTO operInfoDTO;
	
	private BomMobMvnoGatewayServicePortType port;
	
	private final static int CONN_TIMEOUT = 15000;
	
	public MvnoWSClient(String wsdl, String username, String password) {
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
		
		operInfoDTO = new OperInfoDTO();
		operInfoDTO.setChannel("EAI");
		operInfoDTO.setOperId("SPB");
		
	}

	
	

	public MvnoMdsObjectDTO sendSbMvnoMdsRequest(MvnoMdsObjectDTO mds) throws WsClientException {
		SendSbMvnoMdsRequest req = new SendSbMvnoMdsRequest();
		req.setOperInfoDTO(operInfoDTO);
		req.setMvnoMdsObjectDTO(mds);

		SendSbMvnoMdsResult resp = port.sendSbMvnoMdsRequest(req);

		ResultValueDTO result = resp.getResultValue();
		String errCode = result.getResultCode();
		String errMsg = result.getErrorMsg();
		
		String txnSeqNum = result.getTxnSeqNum();   /// ??????????
		System.out.println("mds  txnSeqNum:"+	txnSeqNum);
		
		System.out.println("mds  ResultValueDTO:"+	Utility.toPrettyJson(	result));

		if (!"0".equals(errCode)) {
			WsClientException ex = new WsClientException(errCode, errMsg);
			throw ex;
		}
		return resp.getMvnoMdsObjectDTO();
	}
	
	public ResultValueDTO createPortInOrder(CreatePortInOrdRequest request) throws WsClientException {
		logger.info("createPortInOrder is called");
		System.out.println(Utility.toPrettyJson(request));
		request.setOperInfoDTO(operInfoDTO);
		
		CreatePortInOrdResult resp = port.createPortInOrder(request);
		
		ResultValueDTO result = resp.getResultValue();
		logger.info("createPortInOrder result: errCode = " + result.getResultCode() + ", errMsg = " + result.getErrorMsg());
		return result;
	}
	
	public ResultValueDTO cancelPortInOrder(CancelPortInOrdRequest request) throws WsClientException {
		logger.info("cancelPortInOrder is called");
		System.out.println(Utility.toPrettyJson(request));
		
		request.setOperInfoDTO(operInfoDTO);
		
		CancelPortInOrdResult resp = port.cancelPortInOrder(request);
		
		ResultValueDTO result = resp.getResultValue();
		logger.info("cancelPortInOrder result: errCode = " + result.getResultCode() + ", errMsg = " + result.getErrorMsg());
		return result;
	}
	
	public List<PortOutRequestDTO> getPortOutRequest(GetPortOutReqRequest request) throws WsClientException {

		request.setOperInfoDTO(operInfoDTO);
		
		GetPortOutReqResult resp = port.getPortOutRequest(request);
		
		ResultValueDTO result = resp.getResultValue();
		String errCode = result.getResultCode();
		String errMsg = result.getErrorMsg();
		
		String txnSeqNum = result.getTxnSeqNum();   /// ??????????
		
		System.out.println(Utility.toPrettyJson(result));

		if (!"0".equals(errCode)) {
			WsClientException ex = new WsClientException(errCode, errMsg);
			throw ex;
		}
		
		return resp.getPortOutRequestDTO();
	}
	
	public void replyPortOutOrder(ReplyPortOutOrdRequest request) throws WsClientException {
		request.setOperInfoDTO(operInfoDTO);

		ReplyPortOutOrdResult resp = port.replyPortOutOrder(request);
		
		ResultValueDTO result = resp.getResultValue();
		String errCode = result.getResultCode();
		String errMsg = result.getErrorMsg();
		
		String txnSeqNum = result.getTxnSeqNum();   /// ??????????
		

		if (!"0".equals(errCode)) {
			WsClientException ex = new WsClientException(errCode, errMsg);
			throw ex;
		}
	}
	
	@Deprecated
	public String _getCurrDNO(String directoryNo) throws WsClientException {
		GetCurrDNORequest request = new GetCurrDNORequest();
		request.setOperInfoDTO(operInfoDTO);
		request.setDirectoryNo(directoryNo);
		
		GetCurrDNOResult resp = port.getCurrDNO(request);
		
		ResultValueDTO result = resp.getResultValue();
		String errCode = result.getResultCode();
		String errMsg = result.getErrorMsg();
		
		String txnSeqNum = result.getTxnSeqNum();   /// ??????????
		
		
		
		if (!"0".equals(errCode)) {
			WsClientException ex = new WsClientException(errCode, errMsg);
			throw ex;
		}
		return resp.getGetCurrentDNODTO().getDno();
	}	
	
	
	public GetCurrentDNODTO getCurrDNO(String directoryNo) throws WsClientException {
		GetCurrDNORequest request = new GetCurrDNORequest();
		request.setOperInfoDTO(operInfoDTO);
		request.setDirectoryNo(directoryNo);
		
		GetCurrDNOResult resp = port.getCurrDNO(request);
		
		ResultValueDTO result = resp.getResultValue();
		String errCode = result.getResultCode();
		String errMsg = result.getErrorMsg();
		
		String txnSeqNum = result.getTxnSeqNum();   /// ??????????
		
		
		
		if (!"0".equals(errCode)) {
			WsClientException ex = new WsClientException(errCode, errMsg);
			throw ex;
		}
		return resp.getGetCurrentDNODTO();
	}
	
	
	public CslNoStatusDTO checkNoStatusInCInv(String srvNum) throws WsClientException {
		CheckNoStatusInCInvRequest request = new CheckNoStatusInCInvRequest();
		request.setOperInfoDTO(operInfoDTO);
		request.setSrvNum(srvNum);
		
		CheckNoStatusInCInvResult resp = port.checkNoStatusInCInv(request);
		
		
		ResultValueDTO result = resp.getResultValue();
		String errCode = result.getResultCode();
		String errMsg = result.getErrorMsg();
		
		
		logger.info("checkNoStatusInCInv() result: "+Utility.toPrettyJson(result));
		
		if (!"0".equals(errCode)) {
			WsClientException ex = new WsClientException(errCode, errMsg);
			throw ex;
		}
		
		return resp.getCslNoStatusDTO();

	}
	
	public CheckNoStatusInCInvResult checkNoStatusInCInvWithResult(String srvNum) throws WsClientException {
		CheckNoStatusInCInvRequest request = new CheckNoStatusInCInvRequest();
		request.setOperInfoDTO(operInfoDTO);
		request.setSrvNum(srvNum);
		
		CheckNoStatusInCInvResult resp = port.checkNoStatusInCInv(request);
		
		
		return resp;
	}
	
	
	public CheckNoChannelDTO checkNoChannel(String srvNum) throws WsClientException {
		CheckNoChannelRequest request = new CheckNoChannelRequest();
		request.setOperInfoDTO(operInfoDTO);
		request.setSrvNum(srvNum);
		
		CheckNoChannelResult resp = port.checkNoChannel(request);
		
		
		ResultValueDTO result = resp.getResultValue();
		String errCode = result.getResultCode();
		String errMsg = result.getErrorMsg();
		
		
		
		
		if (!"0".equals(errCode)) {
			WsClientException ex = new WsClientException(errCode, errMsg);
			throw ex;
		}
		
		return resp.getCheckNoChannelDTO();
	}
	
	public CheckNoChannelResult checkNoChannelWithResult(String srvNum) throws WsClientException {
		CheckNoChannelRequest request = new CheckNoChannelRequest();
		request.setOperInfoDTO(operInfoDTO);
		request.setSrvNum(srvNum);
		
		CheckNoChannelResult resp = port.checkNoChannel(request);
		return resp;
		
	}
	
	private BomMobMvnoGatewayServicePortType getPort() {

		URL localWsdl = this.getClass().getClassLoader().getResource("BomMobMvnoGatewayService.wsdl");
		BomMobMvnoGatewayService service = new BomMobMvnoGatewayService(localWsdl);
		//resolver.addHandler(new SOAPLoggingHandler());
		
		service.setHandlerResolver(
				new ClientHandlerResolver(
						new UsernameTokenHeaderHandler(username, password)));
		
		BomMobMvnoGatewayServicePortType port = service.getBomMobMvnoGatewayServiceSOAP11PortHttp();
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
		MvnoWSClient client = new MvnoWSClient("http://10.87.122.15:7401/axis2/services/BomMobMvnoGatewayService?wsdl", "aesbbomapp", "password");

		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		

		try {
			GetCurrentDNODTO dno = client.getCurrDNO("91234567");
			System.out.println("dno=" + gson.toJson(dno));
			
			System.out.println(gson.toJson(client.checkNoChannel("51080008")));
			System.out.println(gson.toJson(client.checkNoStatusInCInv("51080008")));
			/*
			CreatePortInOrdRequest pi = new CreatePortInOrdRequest();
			pi.setSourceSys("M");
			pi.setDirectoryNo("91234567");
			pi.setActRNOCode("MV1");
			pi.setRno("M3");
			pi.setDno("HG");
			pi.setCutOverWinDate("20151020");
			pi.setCutOverWinTime("01:00");
			pi.setCustName("HELLO WORLD");
			pi.setCustIDNo("A123456(7)");
			pi.setUserID("SBMVNO");
			
			
			//client.createPortInOrder(pi);
			
			GetPortOutReqRequest gpo = new GetPortOutReqRequest();
			gpo.setActDNO("MV1");
			gpo.setDno("M3");
			gpo.setCutOverWinDate("20151015");
			System.out.println("GetPortOutRequest = " + gson.toJson(client.getPortOutRequest(gpo)));
			
			ReplyPortOutOrdRequest rpo = new ReplyPortOutOrdRequest();
			rpo.setActDNO("MV1");
			rpo.setAcceptPortOut("N");
			rpo.setCutOverWinDate("20151016");
			rpo.setCutOverWinTime("01:00");
			rpo.setDirectoryNo("91234567");
			rpo.setDno("HG");
			rpo.setRejectReason("F");
			rpo.setUserID("SBMVNO");
			
			client.replyPortOutOrder(rpo);*/
			/*
			MvnoMdsObjectDTO mds = new MvnoMdsObjectDTO();
			mds.setOcId("12345679");
			mds.setBusTxnType("ACT");
			mds.setSrvId("123456");
			mds.setSrvReqDate("20151024");
			mds.setMsisdn("12345678");
			mds.setImsi("11111111111111111111");
			mds.setImsi("111111111111111");
			mds.setPcrfBrandId("123");//sun mobile, 2
			mds.setMvnoId("1234");//sun mobile, 1050
			
			SbMvnoOffItem itemA= new  SbMvnoOffItem();
			itemA.setOfferId("575507");
			itemA.setOfferType("R");
			itemA.setIoInd("I");
			mds.getOffItem().add(itemA);
			
			
			SbMvnoOffItem itemB= new  SbMvnoOffItem();
			itemB.setOfferId("200548");
			itemB.setOfferType("K");
			itemB.setIoInd("I");
			mds.getOffItem().add(itemB);
			
			mds.setBillCycle("10");
			mds.setPcrfAlertEmail("abc@email.com");
			mds.setPassword("123456");
			mds.setSmsLang("00");
			mds.setPcrfAlertType("00");
			mds.setPcrfSmsRecipient("00");
			mds.setSmsOptOut1StRoam("N");
			mds.setSmsOptOutRoamHU("N");
			mds.setUmFlag("0");
		
			
			System.out.println("mds www:"+	Utility.toPrettyJson(	client.sendSbMvnoMdsRequest(mds)));
			*/
			
			
		} catch (WsClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	

}
