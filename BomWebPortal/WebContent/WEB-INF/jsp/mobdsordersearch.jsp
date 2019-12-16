<%@ include file="/WEB-INF/jsp/header.jsp"%>
<%@ taglib prefix="my" uri="http://www.mobccs.com/mob-taglib"%>
<%@ taglib prefix="sb-util" tagdir="/WEB-INF/tags/util"%>
<link type="text/css" href="css/custom-theme/jquery-ui-1.8.9.custom.css" rel="stylesheet" />
<link href="js/jquery.autocomplete.css" rel="stylesheet" type="text/css" />
<script src="js/jquery-1.4.4.js"></script>
<script src="js/jquery-ui-1.8.9.custom.min.js"></script>
<script src="js/jquery.autocomplete.js"></script>
<script>

function upfrontPayment(upfrontPaymentUrl){
	$('#upfrontPaymentDialog').dialog({
	        autoOpen: false,
	        modal: true,
	        height: 550,
	        width: 850,
	        title: "Upfront Payment",
	        open: function(ev, ui){ 
	        	$('#upfrontPaymentIframe').attr('src',upfrontPaymentUrl);
	        	  $("#upfrontPaymentIframe").scrollTop(0);

	        }
	    });
	$('#upfrontPaymentDialog').dialog('open');	
	event.preventDefault(); 
}

function formClear() {
	$("#orderSearch #orderId").val("");
	$("#orderSearch #startDate").val("");
	$("#orderSearch #endDate").val("").attr("disabled", true);
	$("#orderSearch #orderStsId option:first").attr("selected", true);
	$("#orderSearch #orderTypeId option:first").attr("selected", true);
	$("#orderSearch input[name=variousDateType]:first").attr("checked", true);
	$("#orderSearch #mrt").val("");
	$("#orderSearch #aoItemCode").val("").attr("disabled", true);
	$("#orderSearch #aoInd").attr("checked", false);
}

function displayError(i) {
	alert($('#ERROR_' + i).html());
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
				currentDate.setDate(currentDate.getDate() + 30);
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

function aoChange() {
	if ($('#aoInd').is(":checked")) {
		$("#orderSearch #aoItemCode").attr("disabled", false);
	} else {
		$("#orderSearch #aoItemCode").val("").attr("disabled", true);
	}
}

$(document).ready(function() {
	if ($('#startDate').datepicker("getDate") == null) {
		$('#endDate').attr("disabled", true);
	} else {
		$('#endDate').attr("disabled", false);
	}
	
	aoChange();
	
	$("#orderSearch").submit(function(e) {
		if ($('#orderStsId').val() != 'ALL' && 
				<c:if test="${sessionScope.bomsalesuser.category == 'ORDER SUPPORT' || sessionScope.bomsalesuser.category == 'SUPERVISOR'}" >
					$('#orderStsId').val() != 'REVIEWING' && 
				</c:if>
				$('#startDate').val() == "") {
			alert('Start Date and End Date can not be blank');
			e.preventDefault();
			return false;
		}
		
		/*if($('#orderTypeId').val() != 'ALL' && $('#orderNatureId').val() == 'ALL')		
		{
			alert('Please choose the nature type');
			e.preventDefault();
			return false;
		}*/
	
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
	
	$('#orderTypeId').change(function(){
		var newOptions = {};
		var $orderNature = $('#orderNatureId');
				
		if ($(this).val() == 'ACT'){
			newOptions = {"ACT": "ACT"};
			
			
		}else if ($(this).val() == 'TOO1'){
			newOptions = {
					"ALL": "ALL",
					"T22": "T22",
					"T2N": "T2N",
					"TN2": "TN2"};
			
		}else if ($(this).val() == 'COS'){
			newOptions = {
					"ALL": "ALL",
					"CRP": "CRP",
					"RET": "RET",
					"UPG": "UPG"};
			
		}else if ($(this).val() == 'BRM'){
			newOptions = {
					"ALL": "ALL",
					"B22":"B22",
					"B2N": "B2N",
					"BN2": "BN2",
					"BNN": "BNN"
			};
			
		}else{
			newOptions = {"ALL": "ALL"
			};
			
		}
		
		$orderNature.empty();
		$.each(newOptions, function(key,value){
			$orderNature.append($("<option></option>").attr("value", value).text(key));
			
		});
		
		
});



});
</script>

<style type="text/css">
.orderSearch {
	background-color: white;
	border: solid 2px #C0C0C0
}

.orderSearchButton {
	text-align: right;
	padding: 0 0.5em 0.5em 0
}

.orderSearchLabel {
	display: inline-block;
	width: 90px
}

.overflowDiv {
	overflow-x: auto;
	overflow-y: hidden;
	display: inline-block;
	width: 100%
}

.orderSearchResult {
	background-color: white
}

.oneOfT {
	background-color: #C8E2B1;
	height: 1.5em;
}

.twoOfT {
	background-color: white;
	height: 1.5em;
}

.threeOfT {
	background-color: #ECECEC;
	height: 1.5em;
}

.threeOfT p {
	width: 350px;
	margin: 0;
	line-height: 1.5em;
	overflow: hidden;
	white-space: nowrap;
	text-overflow: ellipsis;
}
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
		<h2 class="table_title" style="font-size: medium; margin: 0">MOB Order Enquiry</h2>
		<div class="overflowDiv">
			<table width="100%" border="0" cellspacing="1" class="contenttext">
				<tr>
					<td colspan="4"><form:radiobutton path="variousDateType" value="APP" label="Application Date" /> <%-- 			<form:radiobutton path="variousDateType" value="DEL" label="Delivery Date"/> --%> <form:radiobutton path="variousDateType" value="SR" label="SR Date" /></td>
				<tr>
					<td><span class="orderSearchLabel">Start Date:</span></td>
					<td><form:input path="searchStartDate" id="startDate" readonly="true" /></td>
					<td><span class="orderSearchLabel">End Date:</span></td>
					<td><form:input path="searchEndDate" id="endDate" readonly="true" /></td>
				</tr>
				<tr>
					<td><span class="orderSearchLabel">Order ID:</span></td>
					<td><form:input path="searchOrderId" id="orderId" /></td>
					<td><span class="orderSearchLabel">Order Status:</span></td>
					<td><form:select path="searchOrderSts" id="orderStsId">
							<form:options items="${orderStatus}" itemLabel="codeDesc" itemValue="codeDesc" />
						</form:select></td>
				</tr>
				<tr>
					<td><span class="orderSearchLabel">AO Order:</span></td>
					<td><form:checkbox path="searchAoInd" value="Y" id="aoInd" onclick="aoChange()" /></td>
					<td><span class="orderSearchLabel">AO Item:</span></td>
					<td><form:input path="searchAoItemCode" id="aoItemCode" /></td>
				</tr>
				<tr>
					<td><span class="orderSearchLabel">Mobile Num:</span></td>
					<td><form:input path="searchMrt" id="mrt" /></td>
					<td><span class="orderSearchLabel">Staff ID:</span></td>
					<td><c:out value="${orderSearch.searchStaffId}" /> <form:hidden path="searchStaffId" /></td>
				</tr>
				<tr>
					<td><span class="orderSearchLabel">Order Type:</span></td>
					<td><form:select path="searchOrderType" id="orderTypeId">
							<!--<form:options items="${orderStatus}" itemLabel="codeDesc" itemValue="codeDesc" />-->
							<form:option value="ALL">ALL</form:option>
							<form:option value="ACT">ACT</form:option>
							<form:option value="TOO1">TOO1</form:option>
							<form:option value="COS">COS</form:option>
							<form:option value="BRM">BRM</form:option>
						</form:select></td>
					<td><span class="orderSearchLabel">Order Nature:</span></td>
					<td><form:select path="searchOrderNature" id="orderNatureId">
							<!--<form:options items="${orderStatus}" itemLabel="codeDesc" itemValue="codeDesc" />-->
							<form:option value="ALL">ALL</form:option>
						</form:select></td>
				</tr>
			</table>
		</div>
		<div class="orderSearchButton">
			<input type="submit" value="Search" id="searchButton"> <input type="button" value="Clear" onClick="javascript:formClear();">
		</div>
		<div style="clear: both"></div>
	</div>
	<input type="hidden" id="index" name="index" value="" />

</form:form>
<%@ page import="
					com.bomwebportal.bean.LookupTableBean,
					com.bomwebportal.mob.ccs.dto.CodeLkupDTO,
					com.bomwebportal.util.ConfigProperties,
					java.util.*
				"%>
<%
	String scheme = ConfigProperties.getPropertyByEnvironment("mobcos.url.base");
%>
<div class="overflowDiv orderSearchResult" style="margin-top: 5px">
	<table border="1" cellspacing="0" cellpadding="0" class="contenttext" bgcolor="#FFFFFF">
		<thead>
			<tr class="contenttextgreen">
				<td class="table_title" align="left" style="padding-left: 25px; width: 100px">Order ID<br />Order Status<br />Sales ID
				</td>

				<td class="table_title" align="left" style="padding-left: 25px; width: 150px">Service Number (Basket Type)<br />Customer Name<br />Service Date
				</td>

				<td class="table_title" align="left" style="padding-left: 25px; width: 150px">OCID<br />Bom Order Status<br />Actual Service Date
				</td>

				<td class="table_title" align="left" style="padding-left: 25px; width: 150px">Application Date<br />Last Update<br />Error
				</td>
				
				<td class="table_title">Action</td>
			</tr>
		</thead>
		<tbody>
			<c:choose>
				<c:when test="${empty sessionOrderList.pageList}">
					<tr>
						<td colspan="13">No Data Found</td>
					</tr>
				</c:when>
				<c:otherwise>

					<c:forEach items="${sessionOrderList.pageList}" var="orderList" varStatus="orderListStatus">
						<td height="25px" align="center">
							<div class="oneOfT">
								<sb-util:orderpreview orderId="${orderList.orderId}" shopCd="${orderList.shopCode}" mobcosbaseurl="<%=scheme %>"/>

								<%-- <c:if test="${fn:startsWith(orderList.orderId, 'R') || fn:startsWith(orderList.orderId, 'P')}">
						<a href="orderdetail.html?orderId=${orderList.orderId}&action=ENQUIRY"
								title="Order Enquiry for MOB">${orderList.orderId}</a>
					</c:if>
					<c:if test="${fn:startsWith(orderList.orderId, 'C')}">
						<a href="mobccspreview.html?orderId=${orderList.orderId}&action=ENQUIRY"
							title="Order Enquiry for MOB">${orderList.orderId}</a>
					</c:if>
					<c:if test="${fn:startsWith(orderList.orderId, 'D')}">
						<a href="orderdetail.html?orderId=${orderList.orderId}&action=ENQUIRY" target="_blank"
								title="Order Enquiry for MOB">${orderList.orderId}</a>
					</c:if> --%>

							</div>
							<div class="twoOfT">
								<b> <c:if test='${orderList.orderStatus == "INITIAL"}'>Initial</c:if> <c:if test='${orderList.orderStatus == "REVIEWING"}'>Reviewing</c:if> <c:if test='${orderList.orderStatus == "SIGNOFF"}'>Signoff</c:if> <c:if test='${orderList.orderStatus == "SUCCESS"}'>Success</c:if> <c:if test='${orderList.orderStatus == "PARTIAL"}'>Partial Success</c:if> <c:if test='${orderList.orderStatus == "CANCELLED"}'>Cancelled</c:if> <c:if test='${orderList.orderStatus == "REJECTED"}'>Rejected</c:if> <c:if test='${orderList.orderStatus == "FALLOUT"}'>Fallout</c:if> <c:if test='${orderList.orderStatus == "FAILED"}'>Failed</c:if> <c:if test='${orderList.orderStatus == "CANCELLING"}'>Cancelling</c:if> <c:if
										test='${orderList.orderStatus == "QAPROCESS"}'>In QA Process</c:if> <c:if test='${orderList.orderStatus == "PROCESS"}'>Processing</c:if> 
									<c:if test='${orderList.orderStatus == "01" and orderList.checkPoint == "000"}'>Initial</c:if> <c:if test='${orderList.orderStatus == "01" and orderList.checkPoint == "610"}'>Reviewing</c:if> <c:if test='${orderList.orderStatus == "01" and orderList.checkPoint == "620"}'>Rejected</c:if> <c:if test='${orderList.orderStatus == "99" and orderList.checkPoint == "999"}'>Failed</c:if><c:if test='${orderList.orderStatus == "02" }'>Success</c:if> <c:if test='${orderList.orderStatus == "FALLOUT"}'>FALLOUT</c:if><c:if test='${orderList.orderStatus == "QAPROCESS"}'>QAPROCESS</c:if> <c:if test='${orderList.orderStatus == "03"}'>Cancelling</c:if> <c:if test='${orderList.orderStatus == "04"}'>Cancelled</c:if>
								</b>
							</div>
							<div class="threeOfT">${orderList.salesCd}</div>
						</td>
						<td height="25px" align="center" style="width: 250px;">
							<div class="oneOfT">${orderList.msisdn}(${orderList.basketType})</div>
							<div class="twoOfT">
								<b>${orderList.orderSumCustName}</b>
							</div>
							<div class="threeOfT">
								<fmt:formatDate pattern="dd/MM/yyyy" value="${orderList.srvReqDate}" />
							</div>
						</td>
						<td height="25px" align="center">
							<div class="oneOfT">
								<a href="#" onclick="ocidStatusSearch(this)">${orderList.ocid}</a>
							</div>
							<div class="twoOfT">
								<b><my:code id="${orderList.bomOrderStatus}" codeType="BOM_MOB_ORD_STATUS" source="" /></b>
							</div>
							<div class="threeOfT">
								<fmt:formatDate pattern="dd/MM/yyyy" value="${orderList.actSrvReqDate}" />
							</div>
						</td>
						<td height="25px" style="word-break: break-all; width: 350px;" align="center">
							<div class="oneOfT">
								<fmt:formatDate pattern="dd/MM/yyyy" value="${orderList.appInDate}" />
							</div>
							<div class="twoOfT">
								<b><fmt:formatDate pattern="dd/MM/yyyy HH:mm" value="${orderList.lastUpdateDate}" /></b>
							</div>
							<div class="threeOfT">
								<c:if test="${(orderList.orderStatus != 'REJECTED') }">
									<c:if test="${(orderList.errorMessage != null) }">
										<p>
											<a href="javascript:;" id="ERROR_${orderListStatus.index}" onclick="displayError('${orderListStatus.index}');">${orderList.errorMessage}</a>
										</p>
									</c:if>
									<c:if test="${(orderList.errorMessage == null) }">N/A</c:if>
								</c:if>
								<c:if test="${(orderList.orderStatus == 'REJECTED') }">
									<c:if test="${(orderList.rejectReason != null) }">
										<p>
											<a href="javascript:;" id="ERROR_${orderListStatus.index}" onclick="displayError('${orderListStatus.index}');">${orderList.rejectReason}</a>
										</p>
									</c:if>
									<c:if test="${(orderList.rejectReason == null) }">N/A</c:if>
								</c:if>
							</div>
						</td>

						<td>
						
						<c:if test="${(sessionScope.bomsalesuser.category == 'FRONTLINE')}">
								
								<!-- Recall and Cancel Button -->
								<c:if test="${(orderList.orderStatus == 'INITIAL') || (orderList.orderStatus == 'REJECTED') || (orderList.orderStatus == 'FAILED')}">
									<input type="button" value="Recall" onClick="window.location='orderdetail.html?sbuid=${param.sbuid}&orderId=${orderList.orderId}&status=${orderList.orderStatus}'">
									<input type="button" value="Cancel" onClick="window.location='mobdscancelorder.html?sbuid=${param.sbuid}&orderId=${orderList.orderId}'">
								</c:if>
								<c:if test="${(orderList.orderStatus == '01' and orderList.checkPoint == '000')
					|| (orderList.orderStatus == '01' and orderList.checkPoint == '620')
					|| (orderList.orderStatus == '99' and orderList.checkPoint == '999')}">
									<!-- Recall button -->
									<c:set var="mobcosurl" value="<%=scheme%>"></c:set>
									<sb-util:sso url="${mobcosurl }recallorder.html?orderId=${orderList.orderId}&staffId=${bomsalesuser.username}&action=RECALL" var="previewUrl"/>
									<input type="button" value="Recall" onClick="window.location='${previewUrl}'">

									<!-- button for cancel -->
									<sb-util:sso url="${mobcosurl}cancelorder.html?orderId=${orderList.orderId}" var="cancelUrl" />
									<input type="button" value="Cancel" onClick="window.location='${cancelUrl}'">
								</c:if>
							</c:if> 
							
							<c:if test="${(sessionScope.bomsalesuser.category == 'SUPERVISOR') }">
								<!-- Review button -->
								<c:if test="${(orderList.orderStatus == 'REVIEWING')}">
									<input type="button" value="Review" onClick="window.location='orderdetail.html?sbuid=${param.sbuid}&orderId=${orderList.orderId}&action=REVIEW&appInd=N' ">
								</c:if>
								<c:if test="${(orderList.orderStatus == '01' and orderList.checkPoint == '610')}">
									<c:set var="mobcosurl" value="<%=scheme%>"></c:set>
									<sb-util:sso url="${mobcosurl}review.html?orderId=${orderList.orderId}&staffId=${bomsalesuser.username}&action=REVIEW" var="supportReviewUrl" />
									<input type="button" value="Review" onClick="window.location='${supportReviewUrl}'">
								</c:if>
							</c:if> 
							
							<c:if test="${(sessionScope.bomsalesuser.category == 'ORDER SUPPORT') }">
								<!-- Cancel button -->
								<c:if test="${orderList.orderStatus == 'INITIAL' || orderList.orderStatus == 'FAILED' || orderList.orderStatus == 'REVIEWING'
								|| orderList.orderStatus == 'REJECTED' || (orderList.orderType eq 'ACT' && orderList.orderStatus eq 'SUCCESS' && orderList.bomOrderStatus ne '02')}">
									<input type="button" value="Cancel" onClick="window.location='mobdscancelorder.html?sbuid=${param.sbuid}&orderId=${orderList.orderId}'">
								</c:if>
								<c:if test="${(orderList.orderStatus == '01' and orderList.checkPoint == '000') || (orderList.orderStatus == '99' and orderList.checkPoint == '999')
								 || (orderList.orderStatus == '01' and orderList.checkPoint == '610') || (orderList.orderStatus == '01' and orderList.checkPoint == '620') 
								 || (orderList.orderStatus eq '02' && orderList.bomOrderStatus ne '02')}">
									<c:set var="mobcosurl" value="<%=scheme%>"></c:set>
									<sb-util:sso url="${mobcosurl }cancelorder.html?orderId=${orderList.orderId}" var="cancelUrl" />
									<input type="button" value="Cancel" onClick="window.location='${cancelUrl}'">
								</c:if>
								
								<!--Review button -->	
								<c:if test ="${orderList.orderStatus == 'REVIEWING'}">
								 	<input type="button" value="Review" onClick="window.location='orderdetail.html?sbuid=${param.sbuid}&orderId=${orderList.orderId}&action=REVIEW&appInd=N' ">
								</c:if>
								<c:if test ="${orderList.orderStatus == '01' and orderList.checkPoint == '610'}">
									<c:set var="mobcosurl" value="<%=scheme %>"></c:set>
									<sb-util:sso url="${mobcosurl}review.html?orderId=${orderList.orderId}&staffId=${bomsalesuser.username}&action=REVIEW" var="supportReviewUrl"/>
									<input type="button" value="Review" onClick="window.location='${supportReviewUrl}'">
								</c:if>
								
								<!-- Amend Button -->
								<c:if test="${orderList.bomOrderStatus ne '02'}">
									<c:if test="${orderList.orderStatus eq 'REVIEWING' || (orderList.orderType eq 'ACT' && orderList.orderStatus eq 'SUCCESS')}">
										<input type="button" value="Amend" onClick="window.location='orderdetail.html?sbuid=${param.sbuid}&orderId=${orderList.orderId}&status=${orderList.orderStatus}&action=AMEND' ">
									</c:if>
									<c:if test="${(orderList.orderStatus eq '01' && orderList.checkPoint eq '610') || (orderList.orderStatus eq '02')}">
										<c:set var="mobcosurl" value="<%=scheme %>"></c:set>
										<sb-util:sso url="${mobcosurl }dsamendorder.html?orderId=${orderList.orderId}" var="amendUrl" />
										<input type="button" value="Amend" onClick="window.location='${amendUrl}'">	
									</c:if>
								</c:if>
								<c:if test="${orderList.bomOrderStatus eq '02'}">
									<c:set var="mobcosurl" value="<%=scheme %>"></c:set>
									<sb-util:sso url="${mobcosurl }dsamendorder.html?orderId=${orderList.orderId}" var="amendUrl" />
									<input type="button" value="Amend" onClick="window.location='${amendUrl}'">	
								</c:if>
							</c:if>
							<c:if test="${(sessionScope.bomsalesuser.category == 'WAREHOUSE SUPPORT') }">
								<c:if test="${orderList.orderStatus eq 'SUCCESS' || orderList.orderStatus eq '02'}"> <!--for success order -->
									<c:set var="mobcosurl" value="<%=scheme %>"></c:set>
									<sb-util:sso url="${mobcosurl }dsamendorder.html?orderId=${orderList.orderId}" var="amendUrl" />
									<input type="button" value="Amend" onClick="window.location='${amendUrl}'">	
								</c:if>
							</c:if>
							</td>
						</tr>
					</c:forEach>
				</c:otherwise>
			</c:choose>
		</tbody>
	</table>
	<c:if test="${sessionOrderList.pageList != null && not empty sessionOrderList.pageList}">
		<table width="100%" id="pageIndex">
			<tr align="center" class="contenttext">
				<td><c:if test="${!sessionOrderList.firstPage}">
						<a href="#" id="prev" class="contenttext">&lt;</a>
					</c:if> <c:forEach begin="1" end="${noOfPages}" var="i">
						<c:choose>
							<c:when test="${currentPage eq i}">
								<b>${i}</b>
							</c:when>
							<c:otherwise>
                        &nbsp;&nbsp;<a href="#" class="contenttext">${i}</a>&nbsp;&nbsp;
                    </c:otherwise>
						</c:choose>
					</c:forEach> <c:if test="${!sessionOrderList.lastPage}">
						<a href="#" id="next" class="contenttext">&gt;</a>
					</c:if></td>
			</tr>
		</table>
	</c:if>
</div>
<div id="upfrontPaymentDialog" class="dialog" style="display:none;"> 
	<iframe id="upfrontPaymentIframe" height="100%" width="100%" frameBorder="0"></iframe> 
</div>
<%@ include file="/WEB-INF/jsp/footer.jsp"%>