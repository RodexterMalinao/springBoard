<%@ include file="/WEB-INF/jsp/header.jsp"
%><%@ taglib prefix="my" uri="http://www.mobccs.com/mob-taglib" 
%>
<%@ taglib prefix="sb-util" tagdir="/WEB-INF/tags/util" %>
<link type="text/css" href="css/custom-theme/jquery-ui-1.8.9.custom.css" rel="stylesheet" />
<script src="js/jquery-ui-1.8.9.custom.min.js"></script>
<script src="js/jquery.autocomplete.js"></script>
<script type="text/javascript">
$(document).ready(function() {
	$('#processDateDatepicker').datepicker({
		changeMonth : true,
		changeYear : true,

		showButtonPanel : true,
		dateFormat : 'dd/mm/yy', //'yy' is 4 digit in jquery UI, eg. 1920 
		minDate : "-100Y",
		yearRange : "c-100:c+100" //the range shown in the year selection box, c= inpu date
	//yearRange: "1911:1993" //the range shown in the year selection box
	});
	$("#checkAll").click(function() {
		var checked = $(this).is(":checked");
		$("#exportForm input[name=orderIds]").attr("checked", checked);
		$("#exportForm input[name=formLangs]").attr("disabled", !checked);
		$("#exportForm input[name=orderTypes]").attr("disabled", !checked);
		$("#exportForm input[name=searchLocations]").attr("disabled", !checked);
	});
	$("#exportForm input[name=orderIds]").click(function() {
		var totalOrderIds = $("#exportForm input[name=orderIds]").length;
		var checkedOrderIds = $("#exportForm input[name=orderIds]:checked").length;
		$(this).parent().find("input[name=formLangs]").attr("disabled", !$(this).is(":checked"));
		$(this).parent().find("input[name=orderTypes]").attr("disabled", !$(this).is(":checked"));
		$("#exportForm input[name=searchLocations]").attr("disabled", !$(this).is(":checked"));
		$("#checkAll").attr("checked", checkedOrderIds == totalOrderIds);
	});
	$(".downloadDeliveryReport").click(function(e) {
		e.preventDefault();
		var $tr = $(this).parent().parent();
		var $form = $("<form/>").attr({method: "post", action: "mobccsurgentdeliveryreport.html"});
		var orderId = $.trim($tr.find("input[name=orderIds]").val());
		$form.append($("<input/>").attr({type: "hidden", name: "orderId"}).val(orderId));
		$form.append($("<input/>").attr({type: "hidden", name: "processDate"}).val($.trim($tr.find(".processDate").text())));
		$form.append($("<input/>").attr({type: "hidden", name: "exportCsv"}).val(true));
		$(document.body).append($form);
		$form.submit();
		$(document.body).remove($form);
		return false;
	});
	$("#exportForm").submit(function(e) {
		if ($(this).find("input[name=orderIds]:checked").length == 0) {
			alert("Please select at least one Order ID");
			e.preventDefault();
			return false;
		}
	});
});
</script>

<style type="text/css">
.centerText { text-align: center }
.label { display: inline-block; width: 150px }
</style>
<!--[if lte IE 7]>
<style type="text/css">
form { margin: 0 }
</style>
<![endif]-->

<form:form method="POST" name="mobccsbulkprint">
<table width="100%" border="1" cellspacing="0" class="contenttext" bgcolor="#FFFFFF">
<tr class="contenttextgreen">
	<td class="table_title">Bulk Print</td>
</tr>
<tr>
	<td>
		<span class="label">Process Date:</span>
		<form:input id="processDateDatepicker" path="processDateStr" readonly="true" />
		<div style="clear:both"></div>
		<span class="label">Status:</span>
		<form:select path="checkPoint">
			<form:option value="" label="" />
			<c:forEach items="${checkPointALL}" var="c">
			<form:option value="${c.key}">
			<c:choose>
			<c:when test="${c.key == '999'}">
			<my:code id="N005" codeType="DOA" />
			</c:when>
			<c:otherwise>
			<my:code id="${c.key}" codeType="ORD_CHECK_POINT" />
			</c:otherwise>
			</c:choose>
			</form:option>
			</c:forEach>
		</form:select>
		<div style="clear:both"></div>
		<input type="submit" value="Search" style="float:right">
		<span class="label">Location:</span>
		<form:select path="location" >
			<form:option value="ALL" label="ALL" />
			<form:options items="${stockLocation}" itemLabel="codeDesc" itemValue="codeId" />
		</form:select>
	</td>
</tr>
</table>
</form:form>
<%@ page import="
					com.bomwebportal.bean.LookupTableBean,
					com.bomwebportal.mob.ccs.dto.CodeLkupDTO,
					com.bomwebportal.util.ConfigProperties,
					java.util.*
				"
%>
<%
String scheme = ConfigProperties.getPropertyByEnvironment("mobcos.url.base");

%>
<div style="clear:both;padding-top:5px"></div>

<form action="mobccsbulkprintexport.html" method="POST" id="exportForm">
<input type="hidden" name="searchLocations" value="<my:code id="${searchLocation}" codeType="STOCK_LOC" source=""></my:code>" disabled="disabled">
<table width="100%" border="1" cellspacing="0" class="contenttext" bgcolor="#FFFFFF">
<thead>
	<tr class="contenttextgreen">
		<td class="table_title"><input type="checkbox" id="checkAll"></td>
		<td class="table_title">Order ID</td>
		<td class="table_title">Location</td>
		<td class="table_title">Order Type</td>
		<td class="table_title">OCID</td>
		<td class="table_title">Customer Name</td>
		<td class="table_title">Billing Language</td>
		<td class="table_title">MSISDN</td>
		<td class="table_title">Service Request Date</td>
		<td class="table_title">Delivery Date</td>
		<td class="table_title">Process Date</td>
		<td class="table_title">Urgent Delivery</td>
		<td class="table_title">Order Status</td>
		<td class="table_title" colspan="2">Order Form</td>
		<td class="table_title">Summary Report</td>
	</tr>
</thead>
<tbody>
	<c:choose>
	<c:when test="${resultList == null}"></c:when>
	<c:when test="${empty resultList}">
	<tr>
		<td colspan="14">
			<span>Total count: 0</span>
		</td>
	</tr>
	</c:when>
	<c:otherwise>
	<c:forEach items="${resultList}" var="r">
	<tr>
		<td class="orderId">
		<!-- remove check box when, SBS order -->
		<c:if test="${! fn:startsWith(r.orderId, 'CSBSM')}">
			<c:if test="${r.orderStatus != '99' && r.checkPoint != '999'}">
			<input type="checkbox" name="orderIds" value="${r.orderId}">
			<c:choose>
			<c:when test="${r.custSmsLang == '02'}">
			<input type="hidden" name="formLangs" value="en" disabled="disabled">
			</c:when>
			<c:otherwise>
			<input type="hidden" name="formLangs" value="zh_HK" disabled="disabled">
			</c:otherwise>
			</c:choose>
			</c:if>
			<input type="hidden" name="orderTypes" value="${r.orderType}" disabled="disabled">
		</c:if>
		
		</td>
			<td>
			<sb-util:orderpreview orderId="${r.orderId}" shopCd="${r.shopCd}"  mobcosbaseurl="<%=scheme %>"/>
				<%-- <c:if test="${fn:length(orderList.orderId) == 11}">
					<c:if test="${fn:startsWith(orderList.orderId, 'R') || fn:startsWith(orderList.orderId, 'P')}">
						<a href="orderdetail.html?orderId=${orderList.orderId}&action=ENQUIRY"
								title="Order Enquiry for MOB">${orderList.orderId}</a>
					</c:if>
					<c:if test="${fn:startsWith(orderList.orderId, 'C')}">
						<c:choose>
							<c:when test="${fn:startsWith(orderList.orderId, 'CSBS') }">
								<a href="sbsorderdetail.html?orderId=${orderList.orderId}"	title="Order Enquiry for MOB">${orderList.orderId}</a>
							</c:when>
							<c:otherwise>
								<a href="mobccspreview.html?orderId=${orderList.orderId}&action=ENQUIRY"
									title="Order Enquiry for MOB">${orderList.orderId}</a>
							</c:otherwise>
						</c:choose>
					</c:if>
				</c:if> --%>
				
			</td>
		<td>
		<my:code id="${r.location}" codeType="STOCK_LOC" source=""></my:code>
		</td>
		<td>			
		<c:choose>
		<c:when test="${r.orderType == 'COS'}">
		COS
		</c:when>
		<c:when test="${r.orderType == 'PEND'}">
		PEND
		</c:when>
		<c:when test="${r.orderType == 'TOO1'}">
		TOO1
		</c:when>
		<c:when test="${r.orderType == 'BRM'}">
		BRM
		</c:when>
		<c:otherwise>
		<input type="hidden" name="orderTypes" value="" disabled="disabled">
		</c:otherwise>
		</c:choose></td>
		<td><c:out value="${r.ocId}" /></td>
		<td><c:out value="${r.custTitle} ${r.custLastName} ${r.custFirstName}" /></td>
		<td>
		<c:choose>
		<c:when test="${r.custSmsLang == '00'}">Traditional Chinese</c:when>
		<c:when test="${r.custSmsLang == '02'}">English</c:when>
		</c:choose>
		</td>
		<td><c:out value="${r.msisdn}" /></td>
		<td><fmt:formatDate pattern="dd/MM/yyyy" value="${r.srvReqDate}" /></td>
		<td><fmt:formatDate pattern="dd/MM/yyyy" value="${r.deliveryDate}" /></td>
		<td class="processDate"><fmt:formatDate pattern="dd/MM/yyyy" value="${r.processDate}" /></td>
		<td><c:out value="${r.urgentInd}" /></td>
		<td>
			<c:choose>
			<c:when test="${r.orderStatus == '01'}">
				<my:code id="${r.checkPoint}" codeType="" source="ORDER_STATUS"></my:code>
			</c:when>
			<c:when test="${r.orderStatus == '02'}">
				Completed
			</c:when>
			<c:when test="${r.orderStatus == '03'}">
				Cancelling
			</c:when>
			<c:when test="${r.orderStatus == '04'}">
				Cancelled
			</c:when>
			<c:when test="${r.orderStatus == '99'}">
				<span<c:if test="${r.salesFalloutFlag == true}"> style="color:red"</c:if>>
					<my:code id="${r.reasonCd}" codeType="" source="ORDER_STATUS"></my:code>
				</span>
			</c:when>
			</c:choose>
		</td>
		<c:choose>
		<c:when test="${r.orderStatus != '99' && r.checkPoint != '999'}">
		<td class="centerText" style="width:65px">
		<!-- preview link -->
			<c:choose>
			<c:when test="${fn:startsWith(r.orderId, 'CSBSM')}">
				<a target="_blank" href="<c:url value="mobccsreport.html">
				<c:param name="osOrderId" value="${r.orderId}"/>
				<c:choose>
				<c:when test="${r.custSmsLang == '02'}">
				<c:param name="pdfLang" value="en"/>
				</c:when>
				<c:otherwise>
				<c:param name="pdfLang" value="zh_HK"/>
				</c:otherwise>
				</c:choose>
				<c:param name="upDateStatus" value="N"/>
				</c:url>">SBS Preview</a>

			</c:when>
			<c:otherwise>
						<c:choose>

            <c:when test="${r.orderType == 'COS' || r.orderType == 'BRM' || r.orderType == 'TOO1'}">

                  <a target="_blank"href="<c:url value="mobcosreport.html">
                  <c:param name="orderId" value="${r.orderId}"/>
                  <c:choose>
                  <c:when test="${r.custSmsLang == '02'}">
                  <c:param name="pdfLang" value="en"/>
                  </c:when>
                  <c:otherwise>
                  <c:param name="pdfLang" value="zh_HK"/>
                  </c:otherwise>
                  </c:choose>
                  <c:choose>
                  <c:when test="${r.orderType == 'BRM'}">
                  <c:param name="nature" value="BRM"/>
                  </c:when>
                  <c:when test="${r.orderType == 'TOO1'}">
                  <c:param name="nature" value="TOO1"/>
                  </c:when>
                  <c:otherwise>
                  <c:param name="nature" value="COS"/>
     			  </c:otherwise>
                  </c:choose>
                  <c:param name="channel" value="2"/>
                  <c:param name="companyCopyInd" value="Y"/>
                  <!-- <c:param name="signJson" value=""/> -->
                  <c:param name="copInd" value="N"/>
                  <c:param name="delInd" value="Y"/>
                  <c:param name="dmsInd" value="N"/>
                  <c:param name="save" value="N"/>
                  <!-- <c:param name="filePath" value=""/> -->
                  <c:param name="watermark" value="N"/>
                  <c:param name="upDateStatus" value="N"/>
                  </c:url>">Preview</a>
                </c:when>
                <c:otherwise>
				<a target="_blank"href="<c:url value="mobccsreport.html">
				<c:param name="orderId" value="${r.orderId}"/>
				<c:choose>
				<c:when test="${r.custSmsLang == '02'}">
				<c:param name="pdfLang" value="en"/>
				</c:when>
				<c:otherwise>
				<c:param name="pdfLang" value="zh_HK"/>
				</c:otherwise>
				</c:choose>
				<c:param name="printSeq" value="SDGFCIRHPTN"/>
				<c:param name="upDateStatus" value="N"/>
				</c:url>">Preview</a>
			</c:otherwise>
			</c:choose>
			</c:otherwise>
			</c:choose>
		</td>
		<td class="centerText" style="width:65px">
		<!-- update to Y -->
			<c:choose>
			<c:when test="${fn:startsWith(r.orderId, 'CSBSM')}">
				<a target="_blank" href="<c:url value="mobccsreport.html">
				<c:param name="osOrderId" value="${r.orderId}"/>
				<c:choose>
				<c:when test="${r.custSmsLang == '02'}">
				<c:param name="pdfLang" value="en"/>
				</c:when>
				<c:otherwise>
				<c:param name="pdfLang" value="zh_HK"/>
				</c:otherwise>
				</c:choose>
				<c:param name="upDateStatus" value="Y"/>
				</c:url>">SBS Print</a>

			</c:when>
			<c:otherwise>
			<c:choose>
            <c:when test="${r.orderType == 'COS' || r.orderType == 'BRM' || r.orderType == 'TOO1'}">
                  <a target="_blank"href="<c:url value="mobcosreport.html">
                  <c:param name="orderId" value="${r.orderId}"/>
                  <c:choose>
                  <c:when test="${r.custSmsLang == '02'}">
                  <c:param name="pdfLang" value="en"/>
                  </c:when>
                  <c:otherwise>
                  <c:param name="pdfLang" value="zh_HK"/>
                  </c:otherwise>
                  </c:choose>
                  <c:choose>
                  <c:when test="${r.orderType == 'BRM'}">
                  <c:param name="nature" value="BRM"/>
                  </c:when>
                  <c:when test="${r.orderType == 'TOO1'}">
                  <c:param name="nature" value="TOO1"/>
                  </c:when>
                  <c:otherwise>
                  <c:param name="nature" value="COS"/>
     			  </c:otherwise>
                  </c:choose>
                  <c:param name="channel" value="2"/>
                  <c:param name="companyCopyInd" value="Y"/>
                  <!-- <c:param name="signJson" value=""/> -->
                  <c:param name="copInd" value="N"/>
                  <c:param name="delInd" value="Y"/>
                  <c:param name="dmsInd" value="N"/>
                  <c:param name="save" value="N"/>
                  <!-- <c:param name="filePath" value=""/> -->
                  <c:param name="watermark" value="N"/>
                  <c:param name="updateStatus" value="Y"/>
                  </c:url>">Print</a>
            </c:when>

			<c:otherwise>
				<a target="_blank" href="<c:url value="mobccsreport.html">
				<c:param name="orderId" value="${r.orderId}"/>
				<c:choose>
				<c:when test="${r.custSmsLang == '02'}">
				<c:param name="pdfLang" value="en"/>
				</c:when>
				<c:otherwise>
				<c:param name="pdfLang" value="zh_HK"/>
				</c:otherwise>
				</c:choose>
				<c:param name="printSeq" value="SDGFCIRHPTN"/>
				<c:param name="upDateStatus" value="Y"/>
				</c:url>">Print</a>
				</c:otherwise>

            </c:choose>			
			</c:otherwise>
			</c:choose>	
		</td>
		</c:when>
		<c:otherwise>
		<!-- DOA PRINT -->
		<td class="centerText" colspan="2" style="width:65px">
			<c:choose>
				 <c:when test="${fn:startsWith(r.orderId, 'CSBSM')}">	
				<a target="_blank"href="<c:url value="mobcosreport.html">
				<c:param name="orderId" value="${r.orderId}"/>
				<c:choose>
				<c:when test="${r.custSmsLang == '02'}">
				<c:param name="pdfLang" value="en"/>
				</c:when>
				<c:otherwise>
				<c:param name="pdfLang" value="zh_HK"/>
				</c:otherwise>
				</c:choose>
				<c:param name="nature" value="ACQ"/>
				<c:param name="channel" value="2"/>
				<c:param name="companyCopyInd" value="Y"/>
				<!-- <c:param name="signJson" value=""/> -->
				<c:param name="copInd" value="N"/>
				<c:param name="delInd" value="Y"/>
				<c:param name="dmsInd" value="N"/>
				<c:param name="save" value="N"/>
				<!-- <c:param name="filePath" value=""/> -->
				<c:param name="watermark" value="N"/>
				<c:param name="upDateStatus" value="N"/>
				</c:url>">Preview</a>
				</c:when>
			<c:otherwise>
			<c:choose>

            <c:when test="${r.orderType == 'COS'|| r.orderType == 'BRM' || r.orderType == 'TOO1'}">

                  <a target="_blank"href="<c:url value="mobcosreport.html">
                  <c:param name="orderId" value="${r.orderId}"/>
                  <c:choose>
                  <c:when test="${r.custSmsLang == '02'}">
                  <c:param name="pdfLang" value="en"/>
                  </c:when>
                  <c:otherwise>
                  <c:param name="pdfLang" value="zh_HK"/>
                  </c:otherwise>
                  </c:choose>
                  <c:param name="nature" value="COS"/>
                  <c:param name="channel" value="2"/>
                  <c:param name="companyCopyInd" value="Y"/>
                  <!-- <c:param name="signJson" value=""/> -->
                  <c:param name="copInd" value="N"/>
                  <c:param name="delInd" value="Y"/>
                  <c:param name="dmsInd" value="N"/>
                  <c:param name="save" value="N"/>
                  <!-- <c:param name="filePath" value=""/> -->
                  <c:param name="watermark" value="N"/>
                  <c:param name="upDateStatus" value="N"/>
                  </c:url>">Preview</a>
                </c:when>
                <c:otherwise>
				 <a target="_blank"href="<c:url value="mobcosreport.html">
                  <c:param name="orderId" value="${r.orderId}"/>
                  <c:choose>
                  <c:when test="${r.custSmsLang == '02'}">
                  <c:param name="pdfLang" value="en"/>
                  </c:when>
                  <c:otherwise>
                  <c:param name="pdfLang" value="zh_HK"/>
                  </c:otherwise>
                  </c:choose>
                  <c:param name="nature" value="ACQ"/>
                  <c:param name="channel" value="2"/>
                  <c:param name="companyCopyInd" value="Y"/>
                  <!-- <c:param name="signJson" value=""/> -->
                  <c:param name="copInd" value="N"/>
                  <c:param name="delInd" value="Y"/>
                  <c:param name="dmsInd" value="N"/>
                  <c:param name="save" value="N"/>
                  <!-- <c:param name="filePath" value=""/> -->
                  <c:param name="watermark" value="N"/>
                  <c:param name="upDateStatus" value="N"/>
				</c:url>">Preview</a>
				</c:otherwise>
            </c:choose>	
			</c:otherwise>
			</c:choose>
		</td>
		
		</c:otherwise>
		</c:choose>
		<td class="centerText" style="width:65px">
			<c:if test="${r.urgentInd == 'Y'}">
				<a class="downloadDeliveryReport" href="#">Report</a>
			</c:if>
		</td>

<%-- 			<c:choose>
			<c:when test="${r.orderType == 'COS'}">
			<input type="hidden" name="orderTypes" value="COS" disabled="disabled">
			</c:when>
			<c:when test="${r.orderType == 'PEND'}">
			<input type="hidden" name="orderTypes" value="PEND" disabled="disabled">
			</c:when>
			<c:otherwise>
			<input type="hidden" name="orderTypes" value="" disabled="disabled">
			</c:otherwise>
			</c:choose> --%>

	</tr>
	</c:forEach>
	<tr>
		<td colspan="19">
			<input type="submit" value="Export CSV" style="float:right">
			<span>Total count: <c:out value="${fn:length(resultList)}" /></span>
		</td>
	</tr>
	</c:otherwise>
	</c:choose>
</tbody>
</table>
</form>

<%@ include file="/WEB-INF/jsp/footer.jsp"%>