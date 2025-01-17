
package com.pccw.cancelOrder;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.1.3-b02-
 * Generated source version: 2.1
 * 
 */
@WebService(name = "CancelOrderSoap", targetNamespace = "http://www.openuri.org/")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface CancelOrderSoap {


    /**
     * 
     */
    @WebMethod(action = "http://www.openuri.org/create")
    @RequestWrapper(localName = "create", targetNamespace = "http://www.openuri.org/", className = "com.pccw.cancelOrder.Create")
    @ResponseWrapper(localName = "createResponse", targetNamespace = "http://www.openuri.org/", className = "com.pccw.cancelOrder.CreateResponse")
    public void create();

    /**
     * 
     * @param pBoc
     * @param pCancelRemark
     * @param pOcId
     * @param pTypeOfSrv
     * @param pUserId
     * @param pSrvId
     * @param pCancelReasonCode
     * @return
     *     returns com.pccw.cancelOrder.ServiceResponseDTO
     */
    @WebMethod(action = "http://www.openuri.org/cancel")
    @WebResult(name = "cancelResult", targetNamespace = "http://www.openuri.org/")
    @RequestWrapper(localName = "cancel", targetNamespace = "http://www.openuri.org/", className = "com.pccw.cancelOrder.Cancel")
    @ResponseWrapper(localName = "cancelResponse", targetNamespace = "http://www.openuri.org/", className = "com.pccw.cancelOrder.CancelResponse")
    public ServiceResponseDTO cancel(
        @WebParam(name = "pOcId", targetNamespace = "http://www.openuri.org/")
        long pOcId,
        @WebParam(name = "pSrvId", targetNamespace = "http://www.openuri.org/")
        String pSrvId,
        @WebParam(name = "pTypeOfSrv", targetNamespace = "http://www.openuri.org/")
        String pTypeOfSrv,
        @WebParam(name = "pUserId", targetNamespace = "http://www.openuri.org/")
        String pUserId,
        @WebParam(name = "pBoc", targetNamespace = "http://www.openuri.org/")
        String pBoc,
        @WebParam(name = "pCancelReasonCode", targetNamespace = "http://www.openuri.org/")
        String pCancelReasonCode,
        @WebParam(name = "pCancelRemark", targetNamespace = "http://www.openuri.org/")
        String pCancelRemark);

}
