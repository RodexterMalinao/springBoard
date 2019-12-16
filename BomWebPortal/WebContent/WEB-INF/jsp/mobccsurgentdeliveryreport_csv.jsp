<%@ page contentType="text/plain;charset=UTF-8" 
%><%@ page import="com.bomwebportal.mob.ccs.dto.ui.UrgentDeliveryReportUI" %><%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"
%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"
%><%
response.setContentType("text/plain");
java.util.Date current=new java.util.Date();
UrgentDeliveryReportUI form = (UrgentDeliveryReportUI) request.getAttribute("command");
java.text.SimpleDateFormat format=new java.text.SimpleDateFormat("yyyyMMdd"); 
String date=format.format(current);
response.setHeader("Content-Disposition", "attachment; filename=urgent_"+ date + "_" + form.getOrderId() + ".txt");
response.setHeader("Cache-Control", "");
response.setHeader("Pragma", "");
%>COURIER,PROCESS_DATE,ORDER_ID,OCID,MOBILE_NO,CUST_NAME,STAFF_ID,DELIVERY_TYPE,DELIVERY_DATE,DELIVERY_TIME_SLOT,PAYMENT_METHOD,PAYMENT_AMT,CONTACT_NAME,CONTACT_NUM_1,CONTACT_NUM_2,DELIVERY_ADDRESS,ITEM_TYPE,ITEM_CODE,ITEM_DESC,ITEM_SERIAL,DOA_ITEM_SERIAL,YAHOO_COUPON,TNG_CARD,TNG_SIM
<c:forEach var="r" items="${results}">${r.courier},<fmt:formatDate pattern="yyyy/MM/dd" value="${r.processDate}" />,${r.orderId},${r.ocid},${r.msisdn},${r.custName},${r.staffId},${r.deliveryType},<fmt:formatDate pattern="yyyy/MM/dd" value="${r.deliveryDate}"/>,${r.deliveryTimeSlot},${r.paymentMethod},${r.paymentAmt},${r.contactName},${r.contactNum1},${r.contactNum2},${r.deliveryAddress},${r.itemType},${r.itemCode},${r.itemDesc},${r.itemSerial},${r.doaitemSerial},${r.yahooCoupon},${r.tngCard},${r.tngSim}
</c:forEach>
