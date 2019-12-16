package com.bomwebportal.ims.service;

import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

import javax.xml.rpc.ServiceException;
import javax.xml.soap.SOAPException;

import com.bigmachines.soap.Fault;
import com.bigmachines.soap.LoginResponse;
import com.bigmachines.soap.LogoutResponse;
import com.bigmachines.soap.UserType;
import com.bomwebportal.ims.dto.CPQNTVInfo;
import com.bomwebportal.ims.dto.CPQNTVRetInfo;

public interface CPQInterfacingWSService {
	public String getCpqJsonRecord(String txnId);
	public List<Map<String, String>> getCpqJsonRecordForTest(String testingOrderType);
//	public String getCpqJsonRecordForTest(String testingOrderType);
	public LoginResponse login(String username, String pw) throws java.rmi.RemoteException, com.bigmachines.soap.Fault;
	public LogoutResponse logout(String username, String pw) throws java.rmi.RemoteException, com.bigmachines.soap.Fault;
	
	public CPQNTVInfo saveCPQTransaction(String txnId, String orderActionInd, String amendSeq) throws Fault, RemoteException, ServiceException, SOAPException;
	public CPQNTVRetInfo saveCPQTransactionRet(String txnId, String orderActionInd, String amendSeq, String createBy, String cpqEnv) throws Fault, RemoteException, ServiceException, SOAPException;
	public void clearTransationList();
	public String updateCPQTransaction(String txnId,String orderId, String cpqStatus) throws Fault, RemoteException, ServiceException, SOAPException;

	public String addCPQUsers(String userName) throws Fault, RemoteException, ServiceException, SOAPException;
	public String updateCPQUsers(String userName) throws Fault, RemoteException, ServiceException, SOAPException;

	public String addCPQUserTeam(String userName)throws Fault, RemoteException, ServiceException, SOAPException;
	public String updateCPQUserTeam(String userName)throws Fault, RemoteException, ServiceException, SOAPException;
	public String deleteCPQUserTeam(String userName)throws Fault, RemoteException, ServiceException, SOAPException;
	public String deployCPQUserTeam(String userName)throws Fault, RemoteException, ServiceException, SOAPException;
	public String getCPQUserTeam(String userName)throws Fault, RemoteException, ServiceException, SOAPException;

}
