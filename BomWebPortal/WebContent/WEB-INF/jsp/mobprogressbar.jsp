<%@ taglib prefix="sb-util" tagdir="/WEB-INF/tags/util" %>
<jsp:useBean id="now" class="java.util.Date" scope="request" />
<%@ page import="com.bomwebportal.util.*, com.bomwebportal.bean.LookupTableBean" %>
<script src="js/common.js" language="javascript"></script>
<link type="text/css" href="css/custom-theme/jquery-ui-1.10.4.custom.mob.css" rel="stylesheet" />
<script src="js/jquery-1.10.2.js"></script>
<script src="js/jquery-ui-1.10.3.custom.min.js"></script>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%

	String currentPageName = request.getParameter("currentPageName");

	String orderId = (String) request.getSession().getAttribute("orderIdSession");
	String appMode = (String) request.getSession().getAttribute("appMode");
	String appDate = (String) request.getSession().getAttribute("appDate");
	String orderType = (String) request.getSession().getAttribute("orderType");
	String orderTypeDesc="";
	String channelCd = (String) request.getSession().getAttribute("channelCd");
	String mobuidParameter= (String)request.getParameter("sbuid");
	
	String mobCosUrl = ConfigProperties.getPropertyByEnvironment("mobcos.url.base");

	int currentPageLevel = 0;
	//Map pageMap = new HashMap<String, Integer>();
	if ("DRAF".equals(orderType)){
		currentPageLevel = 1;//set draft only can back to customerProfile page
		
		if ("offerdetail".equals(currentPageName)) {
			currentPageLevel = 2;
		}else if ("mobccsmrt".equals(currentPageName)){
			request.getSession().setAttribute("orderType", "PRE");
			orderType="PRE";
			currentPageLevel = 3;
		}
	}else{
		/***** Common Page *****/
		if ("customerprofile".equals(currentPageName)) {
			currentPageLevel = 1;
		} else if ("offerdetail".equals(currentPageName)) {
			if ("shop".equals(appMode) || "directsales".equals(appMode))
				currentPageLevel = 3; //Retail Mode
			else 
				currentPageLevel = 2; //CCS Mode
		} else if ("depositinfo".equals(currentPageName)) {
			if ("shop".equals(appMode)) {
				currentPageLevel = 7; //Retail Mode
			} else if ("directsales".equals(appMode)) {
				currentPageLevel = 5; //DS Mode
			} else { 
				currentPageLevel = 5; //CCS Mode
			}
		} else
		/***** CCS Mode *****/	
		if ("mobccsmrt".equals(currentPageName)) {
			currentPageLevel = 3;
		} else if ("mobccspayment".equals(currentPageName)) {
			currentPageLevel = 4;
		} else if ("mobccsdelivery".equals(currentPageName)) {
			currentPageLevel = 6;
		} else if ("mobccssupportdoc".equals(currentPageName)) {
			currentPageLevel = 7;
		} else if ("mobccsstaffinfo".equals(currentPageName)) {
			currentPageLevel = 8;
		} else if ("mobccspreview".equals(currentPageName)) {
			currentPageLevel = 9;
		} else if ("mobccssummary".equals(currentPageName)) {
			currentPageLevel = 10;
		} else 
		/***** Retail Mode *****/
		if  ("mobmnp".equals(currentPageName)){
			currentPageLevel = 2;
		} else if ("mobpayment".equals(currentPageName)){
			if ("shop".equals(appMode)) {
				currentPageLevel = 4;
			} else if ("directsales".equals(appMode)){
				currentPageLevel = 5;
			}
		} else if ("mobilesiminfo".equals(currentPageName)){
			if ("shop".equals(appMode)) {
				currentPageLevel = 5;
			} else if ("directsales".equals(appMode)) {
				currentPageLevel = 4;
			}
		} else if ("mobsupportdoc".equals(currentPageName)){
			currentPageLevel = 6;
		} else if ("mobsummary".equals(currentPageName)){
			if ("shop".equals(appMode)) {
				currentPageLevel = 8;
			} else if ("directsales".equals(appMode)) {
				currentPageLevel = 7;
			}
		} else {
			currentPageLevel = -1;
		}
	}

	if (null == orderId || "".equals(orderId)) {
		orderId = "New Order";
	}
	//custQuota.getOrderStatus():DRAFTORDER
	//custQuota.getOrderStatus():PREORDER
	//custQuota.getOrderStatus():PENDORDER
	//custQuota.getOrderStatus():RETAILORDER
	if ("DRAF".equals(orderType)){
		orderTypeDesc ="Draft Order";
	}else if("PRE".equals(orderType)){
		orderTypeDesc ="Pre-Order";
	}else if("PEND".equals(orderType)){
		orderTypeDesc ="Pend Order";
	}else{
		orderTypeDesc="--";
	}
%>

<script type="text/javascript">
function quotaChecking(event) {
	var idDocType = "";
	var idDocNum = "";
	<% if ("customerprofile".equals(currentPageName)) { %>	
		idDocType = $("#docType").val();
		idDocNum = $("#idDocNum").val();
	<% } else { %>
		idDocType = "${customer.idDocType}";
		idDocNum = "${customer.idDocNum}";
	<% } %>

	if (idDocType == null || idDocType == ""
		|| idDocNum == null || idDocNum == "") {
		alert("Please input Document Type and Document Num in Customer's information page.");
        return;
	} else if (idDocType != "HKID" && idDocType != "PASS") {
		alert("No quota handling for BR holder, please note.");
	} else {
		$("#dialog-quota-check").load(
				"mobquotacheck.html?"
						+ $.param({
							idDocType: idDocType,
							idDocNum: idDocNum
						}));
		$("#dialog-quota-check").dialog("open");
		if (event.preventDefault) {
	        event.preventDefault();
	  	} else {
	        event.returnValue = false;
	  	}
	}
}

function clubDetail(event) {
	var idDocType = "";
	var idDocNum = "";
	<% if ("customerprofile".equals(currentPageName)) { %>	
		idDocType = $("#docType").val();
		idDocNum = $("#idDocNum").val();
	<% } else { %>
		idDocType = "${customer.idDocType}";
		idDocNum = "${customer.idDocNum}";
	<% } %>

	if (idDocType == null || idDocType == "" || idDocNum == null || idDocNum == "") {
		alert("Please input Document Type and Document Num in Customer's information page.");
        return;
	} else if (idDocType != "HKID" && idDocType != "PASS") {
		alert("No club member enq for BR holder, please note.");
	} else {
		$("#dialog-club-detail").load(
				<%=mobCosUrl%> +
				"mobclubdetail.html?" + $.param({
					idDocType: idDocType,
					idDocNum: idDocNum,
					SSO_ET: '<sb-util:ssoticket/>'
				})
						, function() { $('#dialog-club-detail :text, #dialog-club-detail select').prop('disabled', true); });
		$("#dialog-club-detail").dialog("open");
		if (event.preventDefault) {
	        event.preventDefault();
	  	} else {
	        event.returnValue = false;
	  	}
	}
}

function approvalLog(event) {

	var orderId = "${orderId}";
	
	if (orderId == null || orderId == "") {
		alert("No Approval Log can be checked.");
        return;
	} else {
		$("#dialog-approval-log").load(
				<%=mobCosUrl%> +
				"mobapprovallog.html?" + $.param({
					orderId: orderId,
					SSO_ET: '<sb-util:ssoticket/>'
				}));
		$("#dialog-approval-log").dialog("open");
		if (event.preventDefault) {
	        event.preventDefault();
	  	} else {
	        event.returnValue = false;
	  	}
	}
}

$(document).ready(function() {
	$("#dialog-quota-check").dialog({
		autoOpen : false,
		height: 400,
		width : 800,
		modal : true,
		title: "Quota Check"
	});
	$("#dialog-club-detail").dialog({
		autoOpen : false,
		height: 350,
		width : 800,
		modal : true
	});
	$("#dialog-approval-log").dialog({
		autoOpen : false,
		height: 400,
		width : 1000,
		modal : true,
		title: "Approval Log"
	});
});
</script>

<c:set var="showQuotaButton" value="true"/>
<% if (!"customerprofile".equals(currentPageName)) { %>
	<c:if test="${customer.idDocType != 'HKID' && customer.idDocType != 'PASS'}">
		<c:set var="showQuotaButton" value="false"/>
	</c:if>
<% } %>

<%
/***** CCS mode flow *****/
if ("mobccs".equals(appMode)) {
	if ("DRAF".equals(orderType) && "mobccspreview".equals(currentPageName)) {
%>
<script type="text/javascript">
$(document).ready(function() {
	$("#progressbar a").click(function(e) {
		var index = $("#progressbar a").index($(this));
		if (index > 0 && index < 7) {
			$("#draftStartPoint").show();
			return false;
		} else {
			$("#draftStartPoint").hide();
		}
	});
});
</script>
<%
	}
%>

<table>
	<tr>
		<td class="contenttextgreen" align="center">MOB Acquisition	</td>
	</tr>
	<tr>
		<td>
			<div id="progressbar">
				<a <%="customerprofile".equals(currentPageName) ? "class=\"progress\""
						: "class=\"progressfuture\""%>
					href=<%=1 > currentPageLevel ? "#" : "customerprofile.html"%>><span><b>1</b>Customer
						Info</span>
				</a> 
				<a <%="offerdetail".equals(currentPageName) ? "class=\"progress\""
						: "class=\"progressfuture\""%>
					href=<%=2 > currentPageLevel ? "#" : "serviceselection.html"%>><span><b>2</b>Offer
						Detail</span> </a> <a
					<%="mobccsmrt".equals(currentPageName) ? "class=\"progress\""
						: "class=\"progressfuture\""%>
					href=<%=3 > currentPageLevel ? "#" : "mobccsmrt.html"%>><span><b>3</b>MRT</span>
				</a> <a
					<%="mobccspayment".equals(currentPageName) ? "class=\"progress\""
						: "class=\"progressfuture\""%>
					href=<%=4 > currentPageLevel ? "#"
						: "mobccspaymentupfront.html"%>><span><b>4</b>Payment
						Info</span> </a> 
				<a
					<%="depositinfo".equals(currentPageName) ? "class=\"progress\""
						: "class=\"progressfuture\""%>
					href=<%=5 > currentPageLevel ? "#" : "depositinfo.html"%>><span><b>5</b>Deposit
						Info</span> </a>
					<a
					<%="mobccsdelivery".equals(currentPageName) ? "class=\"progress\""
						: "class=\"progressfuture\""%>
					href=<%=6 > currentPageLevel ? "#" : "mobccsdelivery.html"%>><span><b>6</b>Delivery
						Info</span> </a> 
						<a
					<%="mobccssupportdoc".equals(currentPageName) ? "class=\"progress\""
						: "class=\"progressfuture\""%>
					href=<%=7 > currentPageLevel ? "#" : "mobccssupportdoc.html"%>><span><b>7</b>Supporting
						Doc</span> </a> <a
					<%="mobccsstaffinfo".equals(currentPageName) ? "class=\"progress\""
						: "class=\"progressfuture\""%>
					href=<%=8 > currentPageLevel ? "#" : "mobccsstaffinfo.html"%>><span><b>8</b>Staff
						Info</span> </a> <a
					<%="mobccspreview".equals(currentPageName) ? "class=\"progress\""
						: "class=\"progressfuture\""%>
					href=<%=9 > currentPageLevel ? "#" : "mobccspreview.html"%>><span><b>9</b>Order Preview</span>
				</a> <a
					<%="mobccssummary".equals(currentPageName) ? "class=\"progress\""
						: "class=\"progressfuture\""%>
					href=<%=10 > currentPageLevel ? "#" : "mobccssummary.html"%>><span><b>10</b>Summary</span>
				</a>
				<br>
				<br/>
				
				<% if (!orderId.equalsIgnoreCase("New Order") && (LookupTableBean.getInstance().getAllowFalloutChannelList()!=null && LookupTableBean.getInstance().getAllowFalloutChannelList().length >0 && Utility.isContainString(LookupTableBean.getInstance().getAllowFalloutChannelList(), channelCd)) ) { %>
					<a href="#" class="nextbutton3" onClick="window.location='mobccsfallout.html?orderId=${orderId}'">Fallout</a> 
				<% }%>
				
			</div>
		</td>

	</tr>
	<tr id="draftStartPoint" style="display:none">
		<td><span class="contenttext_red" style="padding-left:5px">Draft order requires begin from (1) Customer Info</span></td>
	</tr>
	<tr>
		<td class="contenttextgreen" height="30">Order Id : <% out.print(orderId);%> 
		/ Application Date :  <% out.print(appDate);%> 
		/ Order Type : <% out.print(orderTypeDesc);%>
		<c:if test="${showQuotaButton}">
			<a href="#" class="nextbutton3" onClick="quotaChecking(event)" id="quotaCheckButton">Quota Checking</a>
		</c:if>
		
		<a href="#" class="nextbutton3" onClick="clubDetail(event)" id="clubDetailButton">Club Member Enq</a>
		<a href="#" class="nextbutton3" onClick="approvalLog(event);" >Approval Log</a>
		<div style="height:30px ; float:right">
			<c:if test="${brandType eq '1'}">
				<label for="logo"><img style="height:30px;vertical-align:middle" src="images/csl_logo.jpg"/></label>
			</c:if>
			<c:if test="${brandType eq '0'}">
				<label for="logo"><img style="height:30px;vertical-align:middle" src="images/1010_logo.jpg"/></label>
			</c:if>
			<c:choose>
			<c:when test="${simType eq 'C'}">
			<label for="simType">C-SIM</label>
			</c:when>
			<c:when test="${simType eq 'H'}">
			<label for="simType">H-SIM</label>
			</c:when>
			</c:choose>
		</div>
		
		
		
		</td>
	</tr>
</table>
<%
	} else if ("directsales".equals(appMode)) {
	/***** Direct Sales mode flow *****/
%>
<table>
	<tr>
		<td class="contenttextgreen" align="center">MOB Acquisition (Direct Sales)</td>
	</tr>
	<tr>
		<td>
			<div id="progressbar">
				<a	<%="customerprofile".equals(currentPageName) ? "class=\"progress\""
						: "class=\"progressfuture\""%> href=<%=1 > currentPageLevel ? "#" : "customerprofile.html?sbuid="+mobuidParameter%>>
						<span><b>1</b>Customer Info</span> </a> 			
				<a <%="mobmnp".equals(currentPageName) ? "class=\"progress\""
						: "class=\"progressfuture\""%> href=<%=2 > currentPageLevel ? "#" : "mnp.html?sbuid="+mobuidParameter%>>
						<span><b>2</b>MNP/New Phone#</span> </a> 
				<a <%="offerdetail".equals(currentPageName) ? "class=\"progress\""
						: "class=\"progressfuture\""%>	href=<%=3 > currentPageLevel ? "#" : "serviceselection.html?sbuid="+mobuidParameter%>>
						<span><b>3</b>Offer Detail</span> </a> 
				
				<a <%="mobilesiminfo".equals(currentPageName) ? "class=\"progress\""
						: "class=\"progressfuture\""%> href=<%=4 > currentPageLevel ? "#" : "mobilesiminfo.html?sbuid="+mobuidParameter%>>
						<span><b>4</b>Staff,SIM	&amp; HS Info</span> </a> 
				
				<a <%=("mobpayment".equals(currentPageName) || "depositinfo".equals(currentPageName)) ? "class=\"progress\""
						: "class=\"progressfuture\""%> href=<%=5 > currentPageLevel ? "#" : "payment.html?sbuid="+mobuidParameter%>>
						<span><b>5</b>Payment Method</span> </a> 
				
				<a <%="mobsupportdoc".equals(currentPageName) ? "class=\"progress\""
						: "class=\"progressfuture\""%> href=<%=6 > currentPageLevel ? "#" : "supportdoc.html?sbuid="+mobuidParameter%>>
						<span><b>6</b>Support Doc</span>	</a> 
				
				<a <%="mobsummary".equals(currentPageName) ? "class=\"progress\""
						: "class=\"progressfuture\""%> href=<%=7 > currentPageLevel ? "#" : "summary.html?sbuid="+mobuidParameter%>>
						<span><b>7</b>Summary</span>	</a> 
				<a class="progressfuture"><span><b>8</b>Done</span> </a> <br>
			</div></td>

	</tr>
	<tr>
		<td class="contenttextgreen" height="30">Order Id : <%
			out.print(orderId);
		%>
		<c:if test="${showQuotaButton}">
			<a href="#" class="nextbutton3" onClick="quotaChecking(event)" id="quotaCheckButton">Quota Checking</a>
		</c:if>
		
		<a href="#" class="nextbutton3" onClick="clubDetail(event)" id="clubDetailButton">Club Member Enq</a>
		<a href="#" class="nextbutton3" onClick="approvalLog(event);" >Approval Log</a>
		<div style="height:30px ; float:right">
			<c:if test="${brandType eq '1'}">
				<label for="logo"><img style="height:30px;vertical-align:middle" src="images/csl_logo.jpg"/></label>
			</c:if>
			<c:if test="${brandType eq '0'}">
				<label for="logo"><img style="height:30px;vertical-align:middle" src="images/1010_logo.jpg"/></label>
			</c:if>
			<c:choose>
			<c:when test="${simType eq 'C'}">
			<label for="simType">C-SIM</label>
			</c:when>
			<c:when test="${simType eq 'H'}">
			<label for="simType">H-SIM</label>
			</c:when>
			</c:choose>
		</div>
		</td>
	</tr>
</table>


<%
	} else {
	/***** Retail mode flow *****/
%>
<table>
	<tr>
		<td class="contenttextgreen" align="center">MOB Acquisition	</td>
	</tr>
	<tr>
		<td>
			<div id="progressbar">
				<a	<%="customerprofile".equals(currentPageName) ? "class=\"progress\""
						: "class=\"progressfuture\""%> href=<%=1 > currentPageLevel ? "#" : "customerprofile.html?sbuid="+mobuidParameter%>>
						<span><b>1</b>Customer Info</span> </a> 			
				<a <%="mobmnp".equals(currentPageName) ? "class=\"progress\""
						: "class=\"progressfuture\""%> href=<%=2 > currentPageLevel ? "#" : "mnp.html?sbuid="+mobuidParameter%>>
						<span><b>2</b>MNP/New	Phone#</span> </a> 
				<a <%="offerdetail".equals(currentPageName) ? "class=\"progress\""
						: "class=\"progressfuture\""%>	href=<%=3 > currentPageLevel ? "#" : "serviceselection.html?sbuid="+mobuidParameter%>>
						<span><b>3</b>Offer Detail</span> </a> 
				<a <%="mobpayment".equals(currentPageName) ? "class=\"progress\""
						: "class=\"progressfuture\""%> href=<%=4 > currentPageLevel ? "#" : "payment.html?sbuid="+mobuidParameter%>>
						<span><b>4</b>Payment	Method</span> </a> 
				<a <%="mobilesiminfo".equals(currentPageName) ? "class=\"progress\""
						: "class=\"progressfuture\""%> href=<%=5 > currentPageLevel ? "#" : "mobilesiminfo.html?sbuid="+mobuidParameter%>>
						<span><b>5</b>Staff,SIM	&amp; HS Info</span> </a> 
				
				<a <%="mobsupportdoc".equals(currentPageName) ? "class=\"progress\""
						: "class=\"progressfuture\""%> href=<%=6 > currentPageLevel ? "#" : "supportdoc.html?sbuid="+mobuidParameter%>>
						<span><b>6</b>Support Doc</span>	</a> 
				<a <%="depositinfo".equals(currentPageName) ? "class=\"progress\""
						: "class=\"progressfuture\""%> href=<%=7 > currentPageLevel ? "#" : "depositinfo.html?sbuid="+mobuidParameter%>>
						<span><b>7</b>Deposit Info</span>	</a> 				
				<a <%="mobsummary".equals(currentPageName) ? "class=\"progress\""
						: "class=\"progressfuture\""%> href=<%=8 > currentPageLevel ? "#" : "summary.html?sbuid="+mobuidParameter%>>
						<span><b>8</b>Summary</span>	</a> 
				<a class="progressfuture"><span><b>9</b>Done</span> </a> <br>
			</div></td>

	</tr>
	<tr>
		<td class="contenttextgreen" height="30">Order Id : <%
			out.print(orderId);
		%>
		<c:if test="${showQuotaButton}">
			<a href="#" class="nextbutton3" onclick="quotaChecking(event)" id="quotaCheckButton">Quota Checking</a>
		</c:if>
		
		<a href="#" class="nextbutton3" onClick="clubDetail(event)" id="clubDetailButton">Club Member Enq</a>
		<a href="#" class="nextbutton3" onClick="approvalLog(event);" >Approval Log</a>
		<div style="height:30px ; float:right">
			<c:if test="${brandType eq '1'}">
				<label for="logo"><img style="height:30px;vertical-align:middle" src="images/csl_logo.jpg"/></label>
			</c:if>
			<c:if test="${brandType eq '0'}">
				<label for="logo"><img style="height:30px;vertical-align:middle" src="images/1010_logo.jpg"/></label>
			</c:if>
			<c:choose>
			<c:when test="${simType eq 'C'}">
			<label for="simType">C-SIM</label>
			</c:when>
			<c:when test="${simType eq 'H'}">
			<label for="simType">H-SIM</label>
			</c:when>
			</c:choose>
		</div>
		
		</td>
	</tr>
</table>


<%
	}
%>
<div id="dialog-quota-check"></div>
<div id="dialog-club-detail"></div>
<div id="dialog-approval-log"></div>