<%@ include file="/WEB-INF/jsp/header.jsp"%>
<%@ taglib prefix="my" uri="http://www.mobccs.com/mob-taglib" %>
<%@ taglib prefix="sb-util" tagdir="/WEB-INF/tags/util" %>
<% String mobCosUrl = ConfigProperties.getPropertyByEnvironment("mobcos.url.base"); %>
<link type="text/css" href="css/custom-theme/jquery-ui-1.8.9.custom.css" rel="stylesheet" />
<link href="js/jquery.autocomplete.css" rel="stylesheet" type="text/css" />
<script src="js/jquery-1.4.4.js"></script>
<script src="js/jquery-ui-1.8.9.custom.min.js"></script>
<script src="js/jquery.autocomplete.js"></script>
<script>

function alertMessage(staffId) {
	url="<%=mobCosUrl%>mobrscosorderalert.html?locale=en&staffId=" + staffId + "&_al=new";
	window.open(url, '_blank', 'toolbar=0,location=0,menubar=0,scrollbars=1,height=768,width=1024');
}

function formClear() {
	$("#orderSearch #orderId").val("");
	$("#orderSearch #startDate").val("");
	$("#orderSearch #endDate").val("").attr("disabled", true);
	$("#orderSearch #orderStsId option:first").attr("selected", true);
	$("#orderSearch #orderTypeId option:first").attr("selected", true);
	$("#orderSearch input[name=variousDateType]:first").attr("checked", true);
	$("#orderSearch #mrt").val("");
}

$(function() {
	// Datepicker
	$('#startDate').datepicker({
		changeMonth : true,
		changeYear : true,
		showButtonPanel : true,
		dateFormat : 'dd/mm/yy', //'yy' is 4 digit in jquery UI, eg. 1920 
		minDate : "-2Y",
		maxDate : "+1Y" //Y is year, M is month, D is day
		, onSelect: function(dateText, inst) {
			var futureDate = $(this).datepicker("getDate");
			futureDate.setDate(futureDate.getDate() + 7);
			$('#endDate').attr("disabled", false);
			$('#endDate').datepicker( "option", "minDate", $(this).datepicker("getDate"));
			$('#endDate').datepicker( "option", "maxDate", futureDate);
			$('#endDate').datepicker( "setDate", $(this).datepicker("getDate"));
		}
	});
});

$(function() {
	$('#endDate').datepicker({
		changeMonth : true,
		changeYear : false,
		showButtonPanel : true,
		dateFormat : 'dd/mm/yy',
		beforeShow: function(input, inst) {
			var currentDate = $('#startDate').datepicker("getDate");
			if (currentDate != null) {
				currentDate.setDate(currentDate.getDate() + 7);
				$('#endDate').datepicker( "option", "minDate", $('#startDate').datepicker("getDate"));
				$('#endDate').datepicker( "option", "maxDate", currentDate);
			}  
		}
	});
});

function ocidStatusSearch(ref) {
	$.ajax({
		url : 'mobccsordersearchlookup.html'
		, data: { ocid: $.trim($(ref).text()) }
		, type : 'POST'
		, dataType : 'JSON'
		, timeout : 5000
		, success : function(msg) {
			$.each(eval(msg), function(i, item) {
				alert("BOM Status: "+ item.status);
			});
		}
	});
}

$(document).ready(function() {
	if ($('#startDate').datepicker("getDate") == null) {
		$('#endDate').attr("disabled", true);
	} else {
		$('#endDate').attr("disabled", false);
	}
	
	$("#orderSearch").submit(function(e) {
		if ($('#orderStsId').val() != 'ALL' && $('#startDate').val() == "") {
			alert('Start Date and End Date can not be blank');
			e.preventDefault();
			return false;
		}
		if ($('#orderTypeId').val() != 'ALL' && $('#startDate').val() == "") {
			alert('Start Date and End Date can not be blank');
			e.preventDefault();
			return false;
		}
	});
	
	$('#pageIndex a').click(function(e) {
		e.preventDefault();
		switch ($(this).attr("id")) {
		case "prev":
			$('#index').val("previous");
			break;
		case "next":
			$('#index').val("next");
			break;
		default:
			$('#index').val($.trim($(this).text()));
		}
		$("#orderSearch").submit();
		return false;
	});
	
});
</script>

<style type="text/css">
.orderSearch { background-color: white; border: solid 2px #C0C0C0 }
.orderSearchButton { text-align: right; padding: 0 0.5em 0.5em 0 }
.orderSearchLabel { display: inline-block; width: 90px }
.overflowDiv { overflow-x: auto; overflow-y: hidden; display: inline-block; width: 100% }
.orderSearchResult { background-color: white }
</style>
<!--[if lte IE 7]>
<style type="text/css">
form { margin: 0 }
.orderSearch { padding-left: 0 }
.orderSearchResult { padding-bottom: 1em }
</style>
<![endif]-->

<form:form name="orderSearchForm" method="POST" commandName="orderSearch">
<div class="orderSearch">
	
	<h2 class="table_title" style="font-size:medium;margin:0">MOB Order Enquiry
	<!-- For CS , a shortcut to sboordersearch.html -->
			<c:choose>
				<c:when test="${empty channel}">
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				
					<sb-util:codelookup var="enableSBOMap" codeType="ENABLE_SBOORDERSEARCH_BY_STAFF_ID"  asEntryList="true" />
					<c:set var="enableSBOordersearch" value="false" />
					<c:forEach var="account" items="${enableSBOMap}">
					  <c:if test="${bomsalesuser.username eq account.key}">
					    <c:set var="enableSBOordersearch" value="true" />
					  </c:if>
					</c:forEach>
							
						
					<c:if test="${(orderSearch.searchChannel eq 'CSS') || ( bomsalesuser.channelId eq '2' && enableSBOordersearch ) }">
					
					<a class="nextbutton3" href="sboordersearch.html"
								title="Online Sales Order Search">Online Sales Order Search </a>
					</c:if>
				</c:when>
				<c:otherwise>
					
				</c:otherwise>
			</c:choose>
	<!-- end For CS , a shortcut to sboordersearch.html -->
	
	</h2>
	<div class="overflowDiv">
	<table width="100%" border="0" cellspacing="1" class="contenttext">
		<tr>
			<td colspan="4">
			<form:radiobutton path="variousDateType" value="APP" label="Application Date"/> 
			<form:radiobutton path="variousDateType" value="DEL" label="Delivery Date"/>
			<form:radiobutton path="variousDateType" value="SR" label="SR Date"/> 
			</td>
		<tr>
			<td>
				<span class="orderSearchLabel">Start Date:</span>
			</td>
			<td>
				<form:input path="searchStartDate" id="startDate" readonly="true"/>
			</td>
			<td>
				<span class="orderSearchLabel">End Date:</span>
			</td>
			<td>
				<form:input path="searchEndDate" id="endDate" readonly="true"/>
			</td>
		</tr>
		<tr>
			<td>
				<span class="orderSearchLabel">Order ID:</span>
			</td>
			<td>
				<form:input path="searchOrderId" id="orderId"/>
			</td>
			<td>
				<span class="orderSearchLabel">Order Status:</span>
			</td>
			<td>
				<form:select path="searchOrderSts" id="orderStsId" >
					<form:options items="${orderStatus}" itemLabel="codeDesc" itemValue="codeId" />
				</form:select>
			</td>
		</tr>
		<tr>
			<td>
				<span class="orderSearchLabel">Order Type:</span>
			</td>
			<td>
				<form:select path="searchOrderType" id="orderTypeId">
					<form:options items="${orderType}" itemLabel="codeDesc" itemValue="codeId"/>					
				</form:select>
			</td>
			<td>
				<span class="orderSearchLabel">Mobile Num:</span>
			</td>
			<td>
				<form:input path="searchMrt" id="mrt"/>
			</td>
		</tr>
		<tr>
			<td>
				<span class="orderSearchLabel">Channel:</span>
			</td>
			<td>
				<c:choose>
				<c:when test="${empty channel}">
					<c:out value="${orderSearch.searchChannel}"/>
					<form:hidden path="searchChannel"/>
				</c:when>
				<c:otherwise>
					<form:select path="searchChannel" >
						<form:options items="${channel}" itemLabel="codeId" itemValue="codeId" />
					</form:select>
				</c:otherwise>
				</c:choose>
			</td>
			<td>
				<span class="orderSearchLabel">Staff ID:</span>
			</td>
			<td>
				<c:out value="${orderSearch.searchStaffId}"/>
				<form:hidden path="searchStaffId" />
			</td>
		</tr>
		<tr>
		<td>
			<input type="button" value="MOB Alert Message" id="alertMessageButton" onClick="alertMessage('${orderSearch.searchStaffId}')">
		</td> 
		</tr>
	</table>
	
	</div>
	
	<div class="orderSearchButton">
		<input type="submit" value="Search" id="searchButton">
		<input type="button" value="Clear" onClick="javascript:formClear();">
	</div>
	<div style="clear:both"></div>
</div>
<input type="hidden" id="index" name="index" value=""/> 

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
<div class="overflowDiv orderSearchResult" style="margin-top:5px">
<table border="1" cellspacing="0" class="contenttext" bgcolor="#FFFFFF">
<thead>
	<tr class="contenttextgreen">
		<td class="table_title">Order ID</td>
		<td class="table_title">Order Type</td>
		<td class="table_title"><span style="display:inline-block;width: 300px">Order Status</span></td>
		<td class="table_title"><span style="display:inline-block;width: 200px">Customer Name</span></td>
		<td class="table_title">Service Num</td>
		<td class="table_title">OCID</td>
		<td class="table_title">BOM Order Status</td>
		<td class="table_title">Actual SR Date</td>
		<td class="table_title"><span style="display:inline-block;width: 250px">Error Message</span></td>
		<td class="table_title">SR Date</td>
		<td class="table_title">Application Date</td>
		<td class="table_title">Delivery Date</td>
		<td class="table_title"><span style="display:inline-block;width: 100px">Last Update Date</span></td>
		<td class="table_title">Staff ID</td>
		<td class="table_title">Recall Order</td>
		<c:if test="true">
		<td class="table_title">Cancel Order</td>
		</c:if>
		<td class="table_subtitle_blue">Force Cancellation Remaining Day</td>
	</tr>
</thead>
<tbody>
	<c:choose>
	<c:when test="${sessionOrderList.pageList == null}">
	</c:when>
	<c:when test="${empty sessionOrderList.pageList}">
		<tr>
			<td colspan="13">No Data Found</td>
		</tr>
	</c:when>
	<c:otherwise>
		<c:forEach items="${sessionOrderList.pageList}" var="orderList">
			<tr>
			<td>
			<sb-util:orderpreview orderId="${orderList.orderId}" shopCd="${orderList.shopCode}"  mobcosbaseurl="<%=scheme %>"/>
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
			<td><my:code id="${orderList.busTxnType}" codeType="ORD_CCS_TYPE"/> 
				/ <sb-util:codelookup codeId="${orderList.brand}" codeType="MOB_BRAND" />
			</td>
				<td>
				<c:if test='${orderList.orderStatus == "99"}'>
					<c:if test='${orderList.salesFalloutFlag == true}'>
						<span style="color:red">
							<my:code id="${orderList.reasonCd}" codeType="" source="ORDER_STATUS"></my:code>
						</span>
					</c:if>
					<c:if test='${orderList.salesFalloutFlag == false}'>
						<my:code id="${orderList.reasonCd}" codeType="" source="ORDER_STATUS"></my:code>
					</c:if>
				</c:if>
				<c:if test='${orderList.orderStatus == "01"}'>
					<my:code id="${orderList.checkPoint}" codeType="" source="ORDER_STATUS"></my:code>
					<c:if test="${fn:startsWith(orderList.orderId, 'CSBOM')}">
						<c:if test='${orderList.checkPoint < 399}'>
							<span style="color:red">(Incomplete)</span>
						</c:if>
					</c:if>
					<c:if test="${fn:startsWith(orderList.orderId, 'CSBSM')}">
						<c:if test='${orderList.checkPoint < 490}'>
							<span style="color:red">(Incomplete)</span>
						</c:if>
					</c:if>
				</c:if>
				<c:if test='${orderList.orderStatus == "02"}'>
					Completed
				</c:if>
				<c:if test='${orderList.orderStatus == "03"}'>
					Cancelling
				</c:if>
				<c:if test='${orderList.orderStatus == "04"}'>
					Cancelled
				</c:if>
			</td>
			<td>${orderList.orderSumCustName}&nbsp;</td>
			<td>${orderList.msisdn}&nbsp;</td>
			<td><a href="#" onclick="ocidStatusSearch(this)">${orderList.ocid}</a></td>
			<td><my:code id="${orderList.bomOrderStatus}" codeType="BOM_MOB_ORD_STATUS" source=""/></td>
			<td>
				<fmt:formatDate pattern="dd/MM/yyyy"
						value="${orderList.actSrvReqDate}" />
			</td>				
			<td>${orderList.errorMessage}&nbsp;</td>
			<td>
				<fmt:formatDate pattern="dd/MM/yyyy"
						value="${orderList.srvReqDate}" />
			</td>
			<td>
				<fmt:formatDate pattern="dd/MM/yyyy"
						value="${orderList.appInDate}" />
			</td>
			<td>
				<fmt:formatDate pattern="dd/MM/yyyy"
						value="${orderList.deliveryDate}" />
				(${orderList.deliveryTimeSlot})
			</td>
			<td>
				<fmt:formatDate pattern="dd/MM/yyyy HH:mm"
						value="${orderList.lastUpdateDate}" />
			</td>
			<td>${orderList.salesCd}&nbsp;</td>
			<td>
				<sb-util:orderrecall orderId="${orderList.orderId}" shopCd="${orderList.shopCode}" 
					checkPoint="${orderList.checkPoint}" orderStatus="${orderList.orderStatus}" mobcosbaseurl="<%=scheme %>" frozenInd="${orderList.bomFrozenInd }"/>
				<%-- <c:if test="${not fn:startsWith(orderList.orderId, 'CSBSM')}">
					<c:if test="${(orderList.orderStatus == '01' && orderList.checkPoint == '490') || orderList.orderStatus == '99' || (orderList.orderStatus == '01' && orderList.checkPoint <= '399')}">
						<input type="button" value="Recall" onClick="window.location='mobccspreview.html?orderId=${orderList.orderId}&status=${orderList.orderStatus}' ">
					</c:if>
				</c:if> --%>
			</td>
			<td>
			<c:if test="${!(orderList.busTxnType eq 'COS' || orderList.busTxnType eq 'BRM' || orderList.busTxnType eq 'TOO1')}">
				<c:if test="${not fn:startsWith(orderList.orderId, 'CSBSM')}">
					<c:if test="${(orderList.orderStatus == '01' && orderList.checkPoint == '490') ||(orderList.orderStatus == '01' && orderList.checkPoint <= '399') ||( orderList.orderStatus =='99' && orderList.allowCancelInd=='Y')}">
					
						<input type="button" value="Cancel" onClick="window.location='<c:url value="mobccscancellation.html">
						<c:param name="orderId" value="${orderList.orderId}"/>	</c:url>'">
						
					</c:if>
				</c:if>
			</c:if>
			<c:if test="${orderList.busTxnType eq 'COS' || orderList.busTxnType eq 'BRM' || orderList.busTxnType eq 'TOO1'}">
			<c:if test="${(orderList.orderStatus == '01' && orderList.checkPoint == '490') ||(orderList.orderStatus == '01' && orderList.checkPoint <= '399') ||( orderList.orderStatus =='99' && orderList.allowCancelInd=='Y')}">
					
			<c:set var="mobcosurl" value="<%=scheme %>"></c:set>
			<sb-util:sso url="${mobcosurl}cancelorder.html?orderId=${orderList.orderId}" var="cosOrderCancelUrl"/>
			<input type="button" value="COS Cancel" onClick="window.location='${cosOrderCancelUrl}'">
						
			</c:if>
			</c:if>
			</td>
			<td>${orderList.forceCancelRemainDays}</td>
		</tr>
		</c:forEach>
	</c:otherwise>
</c:choose>
</tbody>
</table>
<c:if test="${sessionOrderList.pageList != null && not empty sessionOrderList.pageList}">
	<table width="100%" id="pageIndex">
	<tr align="center" class="contenttext">
		<td>
			<c:if test="${!sessionOrderList.firstPage}">
				<a href="#" id="prev" class="contenttext">&lt;</a>
			</c:if>
			<c:forEach begin="1" end="${noOfPages}" var="i">
                <c:choose>
                    <c:when test="${currentPage eq i}">
                        <b>${i}</b>
                    </c:when>
                    <c:otherwise>
                        &nbsp;&nbsp;<a href="#" class="contenttext">${i}</a>&nbsp;&nbsp;
                    </c:otherwise>
                </c:choose>
            </c:forEach>
            <c:if test="${!sessionOrderList.lastPage}">
				<a href="#" id="next" class="contenttext">&gt;</a>
			</c:if>
        </td>
	</tr>
	</table>
</c:if>
</div>