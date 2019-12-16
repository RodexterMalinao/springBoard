<%@ include file="/WEB-INF/jsp/header.jsp" 
%>
<%@ taglib prefix="sb-util" tagdir="/WEB-INF/tags/util" %>
<%@ page import="org.apache.commons.lang.StringUtils, com.bomwebportal.dto.OrderDTO"
%><%
	//String basketSession = (String)request.getSession().getAttribute("basketSession");
	
	/* if (basketSession == null) 
		basketSession = ""; */
	
	java.util.List customerTierList = (java.util.List) request.getAttribute("customerTierList");
	
	if(customerTierList==null)
		customerTierList = new java.util.ArrayList();
	
	java.util.List basketTypeList = (java.util.List) request.getAttribute("basketTypeList");
	if(basketTypeList==null)
		basketTypeList = new java.util.ArrayList();
	
	java.util.List rpTypeList = (java.util.List) request.getAttribute("ratePlanTypeList");
	if(rpTypeList==null)
		rpTypeList = new java.util.ArrayList();
	
	java.util.List callList = (java.util.List) request.getAttribute("callList");
	if(callList == null)
		callList = new java.util.ArrayList();
	
	String groupSelection = (String) request.getAttribute("groupSelection");
	if(groupSelection == null) {
		groupSelection = "1";
	}
	
	int channelId1 = (Integer) request.getAttribute("channelId");
	
	
%>
<jsp:include page="/WEB-INF/jsp/mobprogressbar.jsp">
	<jsp:param name="currentPageName" value="offerdetail" />
	<jsp:param name="sbuid" value= "${param.sbuid}" />
</jsp:include>

<script type="text/javascript">
var channelId = "<c:out value="${channelId}"/>";
var mnp = { mnp: "<c:out value="${sessionScope.MNP.mnp}"/>", futherMnp: "<c:out value="${sessionScope.MNP.futherMnp}"/>" };
var basket = { customerTierId: "<c:out value="${basket.customerTierId}"/>" };

$(document).ready(function() {
	  refreshRatePlan2();
});

function checkBasketCustomerTierMnp() {
	// only check for retail
	if (channelId != "1") {
		return true;
	}
	// only check for mnp tier basket
	if (basket.customerTierId != "30") {
		return true;
	}
	// mnp only
	if (mnp.mnp == "Y") {
		return true;
	}
	// new number + further mnp
	if (mnp.mnp == "N" && mnp.futherMnp == "true") {
		return true;
	}
	return false;
}

function formRecall() {
	var checkIsWhiteList = "${sessionScope.customer.checkIsWhiteList}";
	var basketCustomerTierId = "${basket.customerTierId}";
	if (checkIsWhiteList == "false" && basketCustomerTierId == "62"){
		alert("Please select another basket!");
		return;
	}
	// requires MNP tierwhen number type = MNP / New Number + MNP
	$(".customerTierCheck").hide();
	//window.location = "vasdetail.html?basket="+"${basketSession}";
	if (!checkBasketCustomerTierMnp()) {
		$(".basketCustomerTierMnp").show();
		return;
	}
	window.location = "vasdetail.html?basket="+"${basket.basketId}"+"&sbuid=${param.sbuid}";;
}
function formChangeRealBasket() {
	//window.location = "vasdetail.html?basket="+"${basketSession}";
	
	window.location = "vasdetail.html?basket="+"${basket.realBasketId}"+"&sbuid=${param.sbuid}";
}

function compareBasket(){
	var sURL = "mobccsbasketcomparedetail.html?basketIds="+"${basket.basketId}";
	var vArguments = new Object();
	var sFeatures = "dialogHeight:768;dialogLeft:0;dialogTop:0;dialogWidth:1024;resizable=yes;scroll=yes;status=no";
	window.showModalDialog(sURL, vArguments, sFeatures);
	
	
}

function formSubmitRs() {
	/*if (document.seviceSelectionForm.customertier.value =="NONE"){
		alert ("Please select Customer Tier");
	}else if (document.seviceSelectionForm.baskettype1.value =="NONE"){
		alert ("Please select Rate Plan Category");
	}else if((document.seviceSelectionForm.baskettype1.value==2 ||document.seviceSelectionForm.baskettype1.value==3) && document.seviceSelectionForm.rptype1.value=="NONE" ){
		alert ("Please select Rate Plan Type");
	}
	
	if (document.seviceSelectionForm.baskettype1.value!='6' && document.seviceSelectionForm.cutoverDateStr.value.length ==0 && document.seviceSelectionForm.serviceReqDateStr.value.length ==0){
		alert ("Please input MNP service request data or cutover date");
		return;
	}else{
		window.location = "modellist.html?customertier="+document.seviceSelectionForm.customertier.value+"&baskettype="+document.seviceSelectionForm.baskettype1.value+"&rptype="+document.seviceSelectionForm.rptype1.value;
	}*/
	$(".customerTierCheck").hide();
	
	// requires MNP tierwhen number type = MNP / New Number + MNP
	if ($("select[name=customertier] option:selected").val() == "30") {
		if (!(mnp.mnp == "Y" || (mnp.mnp == "N" && mnp.futherMnp == "true"))) {
			$(".customerTierMnp").show();
			return;
		}
	}
	<% if (channelId != 10 && channelId != 11) { %> 
	if (document.seviceSelectionForm.baskettype1.value!='6' && document.seviceSelectionForm.cutoverDateStr.value.length ==0 && document.seviceSelectionForm.serviceReqDateStr.value.length ==0){
		alert ("Please input MNP service request data or cutover date");
		return;
	}
	<% } %>
	if (document.seviceSelectionForm.customertier.value =="NONE"){
		alert ("Please select Customer Tier");
	}else if (document.seviceSelectionForm.baskettype1.value =="NONE"){
		alert ("Please select Rate Plan Category");
	}else if((document.seviceSelectionForm.baskettype1.value==2 ||document.seviceSelectionForm.baskettype1.value==3) && document.seviceSelectionForm.rptype1.value=="NONE" ){
		alert ("Please select Rate Plan Type");
	}else if(document.seviceSelectionForm.baskettype1.value==2 ||document.seviceSelectionForm.baskettype1.value==3){ //edit by wilson 20110128
		window.location = "mobiledetail.html?customertier="+document.seviceSelectionForm.customertier.value+"&baskettype="+document.seviceSelectionForm.baskettype1.value+"&rptype="+document.seviceSelectionForm.rptype1.value+"&sbuid=${param.sbuid}";
	}else{
		if (document.seviceSelectionForm.baskettype1.value=='6' ){
			alert ("Please capture customer signature for concierge form in advance, either by digital signature or paper form.");
			
		}
		window.location = "modellist.html?customertier="+document.seviceSelectionForm.customertier.value+"&baskettype="+document.seviceSelectionForm.baskettype1.value+"&rptype="+document.seviceSelectionForm.rptype1.value+"&sbuid=${param.sbuid}";
	}
	
}

function formSubmit(){
	
	len = document.seviceSelectionForm.group.length;
	chosen = "";
	for (i=0;i<len;i++) {
		if (document.seviceSelectionForm.group[i].checked) {
			chosen = document.seviceSelectionForm.group[i].value;
			}
	}
	if (chosen == 1) { //when group1 radio button clicked
		if (document.seviceSelectionForm.customertier.value =="NONE"){
			alert ("Please select Customer Tier");
		}else if (document.seviceSelectionForm.baskettype1.value =="NONE"){
			alert ("Please select Rate Plan Category");
		}else if((document.seviceSelectionForm.baskettype1.value==2 ||document.seviceSelectionForm.baskettype1.value==3) && document.seviceSelectionForm.rptype1.value=="NONE" ){
			alert ("Please select Rate Plan Type");
		}else if(document.seviceSelectionForm.baskettype1.value==2 ||document.seviceSelectionForm.baskettype1.value==3){ //edit by wilson 20110128
			window.location = "mobiledetail.html?customertier="+document.seviceSelectionForm.customertier.value+"&baskettype="+document.seviceSelectionForm.baskettype1.value+"&rptype="+document.seviceSelectionForm.rptype1.value;
		}else{
			window.location = "modellist.html?customertier="+document.seviceSelectionForm.customertier.value+"&baskettype="+document.seviceSelectionForm.baskettype1.value+"&rptype="+document.seviceSelectionForm.rptype1.value;
		}	
	} else { //when group2 radio button clicked
		if (document.seviceSelectionForm.callList.value =="NONE"){
			alert ("Please select Call List");
		}else if (document.seviceSelectionForm.baskettype2.value =="NONE"){
			alert ("Please select Rate Plan Category");
		}else if((document.seviceSelectionForm.baskettype2.value==2 ||document.seviceSelectionForm.baskettype2.value==3) && document.seviceSelectionForm.rptype2.value=="NONE" ){
			alert ("Please select Rate Plan Type");
		}else if(document.seviceSelectionForm.baskettype2.value==2 ||document.seviceSelectionForm.baskettype2.value==3){ 
			window.location = "mobiledetail.html?customertier=NONE&baskettype="+document.seviceSelectionForm.baskettype2.value+"&rptype="+document.seviceSelectionForm.rptype2.value+"&callList="+document.seviceSelectionForm.callList.value+"&callListDesc="+document.getElementById('callListId')[document.getElementById('callListId').selectedIndex].innerHTML;
		}else{
			window.location = "modellist.html?customertier=NONE&baskettype="+document.seviceSelectionForm.baskettype2.value+"&rptype="+document.seviceSelectionForm.rptype2.value+"&callList="+document.seviceSelectionForm.callList.value+"&callListDesc="+document.getElementById('callListId')[document.getElementById('callListId').selectedIndex].innerHTML;
		}	
	}
}

//enable or disable the rate plan select (left side)
function baskettypeChange1(){
	if (document.seviceSelectionForm.baskettype1.value ==2 || document.seviceSelectionForm.baskettype1.value ==3){
		
		document.seviceSelectionForm.rptype1.disabled=false;
	}else{
		document.seviceSelectionForm.rptype1.options[0].selected=true; // set default as 'NONE'
		document.seviceSelectionForm.rptype1.disabled=true;//disable the rate plan select
	}
	
	if(document.seviceSelectionForm.baskettype1.value==3){//set default value 20110128, when type =2(SIM+handset rebate), set rptype=3(VOICE+DATA)
		document.seviceSelectionForm.rptype1.value=3;
	}
}

//enable and disable rate plan drop down selection (right side)
function baskettypeChange2(){
if (document.seviceSelectionForm.baskettype2.value ==2 || document.seviceSelectionForm.baskettype2.value ==3){
		document.seviceSelectionForm.rptype2.disabled=false;
	}else{
		document.seviceSelectionForm.rptype2.options[0].selected=true; 
		document.seviceSelectionForm.rptype2.disabled=true;
	}
	
	if(document.seviceSelectionForm.baskettype2.value==3){
		document.seviceSelectionForm.rptype2.value=3;
	}
}

// enable or disable group choices 
function selectGroup(){
	if ($("input[name=group]").length == 0) {
		// no group in retail mode 
		return;
	}
	var chosen = $("input[name=group]:checked").val();
	if (chosen == "1"){
		//document.seviceSelectionForm.callList.disabled=true;
		//document.seviceSelectionForm.baskettype2.disabled=true;
		//document.seviceSelectionForm.rptype2.disabled=true;
		$("select[name=callList],select[name=baskettype2],select[name=rptype2]").attr("disabled", true);
		
		//document.seviceSelectionForm.customertier.disabled=false;
		//document.seviceSelectionForm.baskettype1.disabled=false;
		$("select[name=customertier],select[name=baskettype1]").attr("disabled", false);
		//if (document.seviceSelectionForm.baskettype1.value ==2 || document.seviceSelectionForm.baskettype1.value ==3) {
		//	document.seviceSelectionForm.rptype1.disabled=false;
		//} else {
		//	document.seviceSelectionForm.rptype1.disabled=true;
		//}
		switch ($("select[name=baskettype1] option:selected").val()) {
		case "2":
		case "3":
			$("select[name=rptype1]").attr("disabled", false);
			break;
		default:
			$("select[name=rptype1]").attr("disabled", true);
		}
		
	} else {
		//document.seviceSelectionForm.customertier.disabled=true;
		//document.seviceSelectionForm.baskettype1.disabled=true;
		//document.seviceSelectionForm.rptype1.disabled=true;
		$("select[name=customertier],select[name=baskettype1],select[name=rptype1]").attr("disabled", true);
		
		//document.seviceSelectionForm.callList.disabled=false;
		//document.seviceSelectionForm.baskettype2.disabled=false;
		$("select[name=callList],select[name=baskettype2]").attr("disabled", false);
		//if (document.seviceSelectionForm.baskettype2.value ==2 || document.seviceSelectionForm.baskettype2.value ==3) {
		//	document.seviceSelectionForm.rptype2.disabled=false;
		//} else {
		//	document.seviceSelectionForm.rptype2.disabled=true;
		//}
		switch ($("select[name=baskettype2] option:selected").val()) {
		case "2":
		case "3":
			$("select[name=rptype2]").attr("disabled", false);
			break;
		default:
			$("select[name=rptype2]").attr("disabled", true);
		}
	}
}

function refreshRatePlan2() {
	$.ajax({
		url : 'serviceselectionlookup.html'
		, data: { jobList: $("select[name=callList] option:selected").val() }
		, type : 'POST'
		, dataType : 'JSON'
		, timeout : 5000
		, error : function() {
			alert('Error loading Rate Plan!');
		}
		, success : function(msg) {
			$("#basketType2Id").empty();
			$.each(eval(msg), function(i, item) {
				if (item.basketTypeId == "${baskettype2Session}") {
					$(
							"<option value='" + item.basketTypeId + "' selected='selected'>"
									+ item.basketType + "</option>")
							.appendTo($("#basketType2Id"));
				} else {
					$(
							"<option value='" + item.basketTypeId + "' >"
									+ item.basketType + "</option>")
							.appendTo($("#basketType2Id"));
				}
			});
		}
	});

}


	function basketCompare() {
		var sURL = "mobccsbasketcompare.html";
		var vArguments = new Object();
		var sFeatures = "dialogHeight:768;dialogLeft:0;dialogTop:0;dialogWidth:1024;resizable=yes;scroll=yes;status=no";
		window.showModalDialog(sURL, vArguments, sFeatures);
	}

</script>

<style type="text/css">
.customerTierCheck { background-color: rgb(255, 240, 248); padding: 1em 2em; font-size: small; font-family: "Helvetica", "Verdana", "Arial", "sans-serif" }
.customerTierCheck ul { margin-top: 0; margin-bottom: 0 }
.customerTierCheck .tier { font-weight: bold; text-decoration: underline }
.customerTierCheck .mnpType { text-decoration: underline }
</style>

<div class="customerTierMnp basketCustomerTierMnp customerTierCheck groupedPcRelationshipList" style="display: none">
	You cannot select "MNP" related basket(s) as inputted mobile number type is not "MNP" or "New Num + MNP". Please correct or select another Customer Tier.
</div>

<%
String orderStatus = "INITIAL";
String ocid = request.getAttribute("order") instanceof OrderDTO ? ((OrderDTO) request.getAttribute("order")).getOcid() : null;
OrderDTO orderDto = (OrderDTO) request.getSession().getAttribute("orderDtoOriginal");
if (orderDto != null) {
	orderStatus = orderDto.getOrderStatus();
	ocid = orderDto.getOcid();
}
%>

<form:form name="seviceSelectionForm" method="POST" commandName="seviceSelection">
<table width="100%"  class="tablegrey" background="images/background2.jpg">
  <tr> 
  	<td> 
	  <table width="100%"  border="0" cellspacing="1" >
	  <tr>
	  	<td>
	  		<font color="RED"><b>${message}</b></font>
	  	</td>
	  </tr>
<% if (channelId == 2) { %>	   	
	   	<c:if test='${not empty order.ocid || order.shopCode =="SBO"}'>
	   	<tr>
	   	<td class="contenttextgreen" >
			<div class="buttonmenubox_L" id="buttonArea">
				<font>Not allow change basket : <a href="mobccsmrt.html" class="nextbutton3">Proceed with MRT page </a>
				</font>
			</div>
			</td>   	 
	   	</tr>
		<tr><td>	
			<!-- package Summary table -->
		<table width="100%" border="1" class="tableGreytext" bgcolor="white">
			<tr>
				<td valign="top" bgcolor="#FFFFFF">
				<table width="100%" border="0" cellspacing="1" class="contenttext">
					<tr>
						<td class="table_title">Package Summary</td>
					</tr>
					<tr>
						<td><!-- image and price table -->
						<table cellspacing="0" class="contenttext" width="100%"> 
							<tr >
								<c:if
									test='${mobileDetail.modelName != null}'>
									<td style="font-family:sans-serif;font-size: 14px" valign="top"><!-- image here -->
									<p class="contenttextgreen" ><img
										src="${mobileDetail.modelImagePath}"
										alt="${mobileDetail.modelName}" width="80" /></p>
									<span class="posttext">${mobileDetail.modelName}</span>
									 <!-- end of image --></td>
								</c:if> <!-- end of image -->

								<td><!-- price table here -->
								<table cellspacing="1" class="contenttext" width="100%" >
									<tr>
										<td class="table_title" ></td>
										<c:if test='${not empty multiSimInfos}'>
											<td class="table_title" align="right" ></td>
										</c:if>
										<td class="table_title" align="right" >Monthly</td>
										<td class="table_title" align="right" >Upfront</td>
									</tr>
									<c:forEach items="${vasHSRPList}" var="vasHSRP">
										<tr>
											<td class="contenttext" width="84%">${vasHSRP.itemHtml}</td>
											<c:if test='${not empty multiSimInfos}'>
											<td class="contenttext" align="right" width="10%">
											<c:forEach items="${multiSimInfos}" var="msi">
											<c:if test='${msi.itemId == vasHSRP.itemId}'>
													<button type="button" 
													onclick="javascript:window.open('multisiminfo.html?vasitem=${msi.itemId}', '_blank', 'toolbar=0,location=0,menubar=0,scrollbars=1,height=768,width=1024')">
													Multi-SIM Options</button>
											</c:if>
											</c:forEach>
											</td></c:if>
											<td class="BGgreen2" align="right" width="3%"><c:if
												test='${vasHSRP.itemRecurrentAmt!=0  }'>
												<fmt:formatNumber value="${vasHSRP.itemRecurrentAmt}"
													pattern="$#,###.####/month" />
											</c:if></td>
											<td class="BGgreen2" align="right" width="3%"><c:if
												test='${vasHSRP.itemOnetimeAmt!=0}'>
												<fmt:formatNumber value="${vasHSRP.itemOnetimeAmt}"
													pattern="$#,###.####" />
											</c:if></td>
										<tr>
									</c:forEach>

								

								</table>

								<!-- end of price table  --></td>
							</tr>
						</table>
						<!-- end of image and price table --></td>
					</tr>
				</table>
				</td>
			</tr>
		</table>
		</td></tr>	
		</c:if>
 <% } %>
			<tr>
	   	
<% if ((channelId != 10 && channelId != 11) || orderStatus.equals("INITIAL") || orderStatus.equals("REJECTED")) { %>
	<c:if test='${ empty order.ocid && order.shopCode !="SBO"}'>
		<c:if test='${not empty basket.basketId}'>
			<c:choose>
			<c:when test='${basket.mipBrand eq brand or basket.mipBrand eq "9"}'>
			<tr>
				<td class="contenttextgreen" >
					<div class="buttonmenubox_L" id="buttonArea">
						<font>Selected Basket : <a href="#"
							onClick="compareBasket();">${basket.description} </a>
						</font>
	
						<c:if test='${channelBasket.channelBasketStatus=="Y" }'>
							<a href="#" class="nextbutton3" onClick="formRecall();">Proceed
								with original basket </a>
						</c:if>
						<c:if
							test='${empty channelBasket || channelBasket.channelBasketStatus=="N" }'>
							<BR>
							<font color="RED"><b> Basket have been obsolete,
									Please choose another basket. </b>
							</font>
						</c:if>
	
					</div>
				</td>
			</tr>
			</c:when>
			<c:otherwise>
			<tr>
				<td class="contenttextgreen" >
					<div class="buttonmenubox_L" id="buttonArea">							
							<font color="RED"><b> Basket is not available for selected brand,
									Please choose another basket. </b>
							</font>
					</div>
				</td>
			</tr>
			</c:otherwise>
			</c:choose>
		</c:if>
	</c:if> 
<% } %> 


			<c:if test='${not empty basket.realBasketId && basket.dummyBasketInd=="Y"}'>
			<tr>
			<td class="contenttextgreen">
				<font color="RED"><b>change to real basket </b></font>
				<a href="#" class="nextbutton3" onClick="formChangeRealBasket();">change to real basket </a>
				<input type="hidden" name="dummyBasketInfo" value='${basket.dummyBasketInd}/${basket.realBasketId}'/>
			</td>
			</tr>
			</c:if>


		<c:if test="${empty order.ocid && order.shopCode !='SBO'}">
			<tr>
				<td class="table_title">	
		 		 <%	if (channelId1 == 1) {%>
		 		 HKT / csl. Shop Basket Selection
		 		 <%} else if (channelId1 == 2){%>
		 		 	Call Center Basket Selection
		 		 <%} else if (channelId1 == 3){%>
		 		 	HKT Premier team Basket Selection
		 		 <%} else if (channelId1 == 10 || channelId1 == 11){%>
		 		 	Road Show/Shop-in-Shop Basket Selection
		 		 <%} else {%>
		 		 	Basket Selection</span>
		 		 <%} %>
		 		 </td>
	 		 </tr>
		</c:if>
			 
	   	</tr>
	  </table>
	  
<%
if (StringUtils.isBlank(ocid) && channelId1 == 2) {
%>
	<c:if test='${ empty order.ocid && order.shopCode !="SBO"}'>
	  <table width="100%" border="0" cellspacing="1" class="contenttext">
        <tr>
        	<td width="25%">
        		<label>
        			<input type="radio" name="group" onclick="selectGroup()" value="1" checked="checked">General
        		</label>
        	</td>
        	<td width="25%">&nbsp;</td>
        	<td width="25%">
        		<label>
					<input type="radio" name="group" onclick="selectGroup()" value="2">Promotion/Call List
        		</label>
        	</td>
        	<td width="25%">&nbsp;</td>
        </tr>
        <tr>
        <td width="25%">
            <div align="right">
            	<b><spring:message code="label.brand"/></b>
            	<span class="contenttext_red"> </span>
            </div>
          </td>
          <td class="contenttext">
          		
				<input value='<sb-util:codelookup codeId="${brand}" codeType="MOB_BRAND" />' readonly></input>
					
			
          </td>
        </tr>
        <tr>
         	<td colspan="2">&nbsp;</td>
        </tr>
        <tr>
          <td width="25%">
            <div align="right">
            	<b><spring:message code="label.customertier"/></b>
            	<span class="contenttext_red">*</span>
            </div>
          </td>
          <td class="contenttext">
          			
					<select name="customertier">
						<option value="NONE">----------Select----------</option>
						<%
          				for(int i=0; i<customerTierList.size(); i++){
          					String[] cList = (String[]) customerTierList.get(i);
          					%>
          						<option value="<%=cList[0]%>"><%=cList[1]%></option>
          					<%
          				}
          				%>					
					 </select>
					
			
          </td>
          <td width="25%">
            <div align="right">
            	<b><spring:message code="label.calllist"/></b>
            	<span class="contenttext_red">*</span>
            </div>
          </td>
          <td class="contenttext">
          			
					<select id="callListId" name="callList" onchange="refreshRatePlan2()" disabled> 
						<option value="NONE">----------Select----------</option>
						<%
          				for(int i=0; i<callList.size(); i++){
          					String[] obj = (String[]) callList.get(i);
          					%>
          						<option value="<%=obj[0]%>"><%=obj[1]%></option>
          					<%
          				}
          				%>				
					 </select>
					
			
          </td>
        </tr>
        <tr>
         	<td colspan="4">&nbsp;</td>
        </tr>
         <tr>
          <td width="25%">
            <div align="right">
	            <b><spring:message code="label.ratePlan.category"/></b>
	            <span class="contenttext_red">*</span>
            </div>
          </td>
          <td class="contenttext">
         
			
			<select name="baskettype1" onchange="baskettypeChange1()" > 
						<option value="NONE">----------Select----------</option>
						<%
          				for(int i=0; i<basketTypeList.size(); i++){
          					String[] cList = (String[]) basketTypeList.get(i);
          					%>
          						<option value="<%=cList[0]%>"><%=cList[1]%></option>
          					<%
          				}
          				%>			
					</select>
					
				
          </td>
           <td width="25%">
            <div align="right">
	            <b><spring:message code="label.ratePlan.category"/></b>
	            <span class="contenttext_red">*</span>
            </div>
          </td>
          <td class="contenttext">
         
			
			<select id="basketType2Id" name="baskettype2" onchange="baskettypeChange2()" disabled> 
						<option value="NONE">----------Select----------</option>
						<%
          				for(int i=0; i<basketTypeList.size(); i++){
          					String[] cList = (String[]) basketTypeList.get(i);
          					%>
          						<option value="<%=cList[0]%>"><%=cList[1]%></option>
          					<%
          				}
          				%>			
					</select>
					
				
          </td>
        </tr>
         <tr>
         	<td colspan="4">&nbsp;</td>
        </tr>
           
         <tr>
          <td width="25%">
            <div align="right">
            	<b><spring:message code="label.ratePlan.type"/></b>
            	<span class="contenttext_red">*</span>
            </div>
          </td>
          <td class="contenttext">
      
			<select name="rptype1" disabled>
						<option value="NONE">----------Select----------</option>
						<%
          				for(int i=0; i<rpTypeList.size(); i++){
          					String[] cList = (String[]) rpTypeList.get(i);
          					%>
          						<option value="<%=cList[0]%>"><%=cList[1]%></option>
          					<%
          				}
          				%>	
					</select>
					
				
          </td>
          <td width="25%">
            <div align="right">
            	<b><spring:message code="label.ratePlan.type"/></b>
            	<span class="contenttext_red">*</span>
            </div>
          </td>
          <td class="contenttext">
      
			<select id="rpTypeId2" name="rptype2" disabled >
						<option value="NONE">----------Select----------</option>
						<%
          				for(int i=0; i<rpTypeList.size(); i++){
          					String[] cList = (String[]) rpTypeList.get(i);
          					%>
          						<option value="<%=cList[0]%>"><%=cList[1]%></option>
          					<%
          				}
          				%>	
					</select>
					
				
          </td>
        </tr>     
      </table>
    </td>
  </tr>
</table>
 <table width="100%" border="0" cellspacing="0">
				<tr>

					<td>
					<div align="left" class="buttonmenubox_L"  >
					
<a href="#" class="nextbutton3" onClick="basketCompare();">Basket Compare</a>

</div>
</td>
<td>
					<div class="buttonmenubox_R" id="buttonArea">
					<a href="#" class="nextbutton3" onClick="formSubmit();">continue &gt;</a></div>
					</td>
				</tr>
	 </table>
</c:if>
    
     <input type="hidden" name="currentView" value="serviceselection"/>
     <input type="hidden" name="cutoverDateStr" value='${sessionCutoverDateStr}'/>
     <input type="hidden" name="serviceReqDateStr" value='${sessionServiceReqDateStr}'/>
<% 
	} else if (StringUtils.isBlank(ocid) && ("INITIAL".equals(orderStatus) || "REJECTED".equals(orderStatus))) {
%>
	<table width="100%"  class="tablegrey" background="images/background2.jpg">
  <tr> 
  	<td> 
	  <table width="100%" border="0" cellspacing="1" class="contenttext">
	  	<tr>
          <td width="50%">
            <div align="right">
            	<b><spring:message code="label.brand"/></b>
            	<span class="contenttext_red"> </span>
            </div>
          </td>
          <td class="contenttext">
          		
				<input value='<sb-util:codelookup codeId="${brand}" codeType="MOB_BRAND" />' readonly></input>
					
			
          </td>
        </tr>
        <tr>
         	<td colspan="2">&nbsp;</td>
        </tr>
        <tr>
          <td width="50%">
            <div align="right">
            	<b><spring:message code="label.customertier"/></b>
            	<span class="contenttext_red">*</span>
            </div>
          </td>
          <td class="contenttext">
          			
					<select name="customertier">
						<option value="NONE">----------Select----------</option>
						<%
          				for(int i=0; i<customerTierList.size(); i++){
          					String[] cList = (String[]) customerTierList.get(i);
          					%>
          						<option value="<%=cList[0]%>"><%=cList[1]%></option>
          					<%
          				}
          				%>					
					 </select>
					
			
          </td>
        </tr>
        <tr>
         	<td colspan="2">&nbsp;</td>
        </tr>
         <tr>
          <td width="50%">
            <div align="right">
            	<b><spring:message code="label.ratePlan.category"/></b>
            	<span class="contenttext_red">*</span>
            </div>
          </td>
          <td class="contenttext">
         
			
			<select name="baskettype1" onchange="baskettypeChange1()" > 
						<option value="NONE">----------Select----------</option>
						<%
          				for(int i=0; i<basketTypeList.size(); i++){
          					String[] cList = (String[]) basketTypeList.get(i);
          					%>
          						<option value="<%=cList[0]%>"><%=cList[1]%></option>
          					<%
          				}
          				%>			
					</select>
					
				
          </td>

        </tr>
         <tr>
         	<td colspan="2">&nbsp;</td>
        </tr>
           
         <tr>
          <td width="25%">
            <div align="right">
            	<b><spring:message code="label.ratePlan.type"/></b>
            	<span class="contenttext_red">*</span>
            </div>
          </td>
          <td class="contenttext">
      
			<select name="rptype1" disabled>
						<option value="NONE">----------Select----------</option>
						<%
          				for(int i=0; i<rpTypeList.size(); i++){
          					String[] cList = (String[]) rpTypeList.get(i);
          					%>
          						<option value="<%=cList[0]%>" ><%=cList[1]%></option>
          					<%
          				}
          				%>	
					</select>
					
				
          </td>
        </tr>     
      </table>
      
    </td>
  </tr>
</table>
 <table width="100%" border="0" cellspacing="0">
				<tr>
				
				<%-- <td>
					<div align="left" class="buttonmenubox_L"  >
					
<a href="#" class="nextbutton3" onClick="javascript:window.open('conciergesign.html?sbuid=${param.sbuid}', '_blank', 'toolbar=0,location=0,menubar=0,scrollbars=1,height=768,width=1024');">Concierge Service Digital Signature</a>
						
	
</div>
</td> --%>

					<td>
					<div class="buttonmenubox_R" id="buttonArea">
					<a href="#" class="nextbutton3" onClick="formSubmitRs();">continue &gt;</a></div>
					</td>
				</tr>
	 </table>
     
     <input type="hidden" name="currentView" value="serviceselection"/>
     <input type="hidden" name="cutoverDateStr" value='${sessionCutoverDateStr}'/>
     <input type="hidden" name="serviceReqDateStr" value='${sessionServiceReqDateStr}'/>
    
   
     

<% } else if (channelId1==10 || channelId1==11) { %>
	<table width="100%"  class="tablegrey" background="images/background2.jpg">
	<tr>
	   	<td class="contenttextgreen" >
			<div class="buttonmenubox_L" id="buttonArea">
				<font>Not allow change basket : <a href="mobilesiminfo.html" class="nextbutton3">Proceed to HS/SIM Info </a>
				</font>
			</div>
			</td>   	 
	   	</tr>
		<tr><td>	
			<!-- package Summary table -->
		<table width="100%" border="1" class="tableGreytext" bgcolor="white">
			<tr>
				<td valign="top" bgcolor="#FFFFFF">
				<table width="100%" border="0" cellspacing="1" class="contenttext">
					<tr>
						<td class="table_title">Package Summary</td>
					</tr>
					<tr>
						<td><!-- image and price table -->
						<table cellspacing="0" class="contenttext" width="100%"> 
							<tr >
								<c:if
									test='${mobileDetail.modelName != null}'>
									<td style="font-family:sans-serif;font-size: 14px" valign="top"><!-- image here -->
									<p class="contenttextgreen" ><img
										src="${mobileDetail.modelImagePath}"
										alt="${mobileDetail.modelName}" width="80" /></p>
									<span class="posttext">${mobileDetail.modelName}</span>
									 <!-- end of image --></td>
								</c:if> <!-- end of image -->

								<td><!-- price table here -->
								<table cellspacing="1" class="contenttext" width="100%" >
									<tr>
										<td class="table_title" ></td>
										<c:if test='${not empty multiSimInfos}'>
											<td class="table_title" align="right" >Multi-SIM Info</td>
										</c:if>
										<td class="table_title" align="right" >Monthly</td>
										<td class="table_title" align="right" >Upfront</td>
									</tr>
									<c:forEach items="${vasHSRPList}" var="vasHSRP">
										<tr>
											<td class="contenttext" width="84%">${vasHSRP.itemHtml}</td>
											<c:if test='${not empty multiSimInfos}'>
											<td class="BGgreen2" align="right" width="10%">
											<c:forEach items="${multiSimInfos}" var="msi">
											<c:if test='${msi.itemId == vasHSRP.itemId}'>
													<button type="button" 
													onclick="javascript:window.open('multisiminfo.html?vasitem=${msi.itemId}', '_blank', 'toolbar=0,location=0,menubar=0,scrollbars=1,height=768,width=1024')">
													Multi-SIM Options</button>
											</c:if>
											</c:forEach>
											</td></c:if>
											<td class="BGgreen2" align="right" width="3%"><c:if
												test='${vasHSRP.itemRecurrentAmt!=0  }'>
												<fmt:formatNumber value="${vasHSRP.itemRecurrentAmt}"
													pattern="$#,###.####/month" />
											</c:if></td>											
											<td class="BGgreen2" align="right" width="3%"><c:if
												test='${vasHSRP.itemOnetimeAmt!=0}'>
												<fmt:formatNumber value="${vasHSRP.itemOnetimeAmt}"
													pattern="$#,###.####" />
											</c:if></td>
										<tr>
									</c:forEach>

								

								</table>

								<!-- end of price table  --></td>
							</tr>
						</table>
						<!-- end of image and price table --></td>
					</tr>
				</table>
				</td>
			</tr>
		</table>
		</td></tr>
	</table>
<%  }  %>
</form:form>
<script language="javascript">
$(document).ready(function() {
	selectGroup();
});
</script>
<!---------------------------------------  end content ------------------------------------------->
<%@ include file="/WEB-INF/jsp/footer.jsp" %>