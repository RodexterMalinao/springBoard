<%@ include file="/WEB-INF/jsp/header.jsp"%>
<jsp:useBean id="now" class="java.util.Date" scope="request" />
<%@ taglib prefix="my" uri="http://www.mobccs.com/mob-taglib"%>
<%@ taglib prefix="sb-util" tagdir="/WEB-INF/tags/util" %>


 

<link rel="stylesheet" href="css/jquery-ui.css">
<script type="text/javascript" src="js/jquery-ui-1.8.16.custom.min.js"></script>
<link type="text/css" href="css/custom-theme/jquery-ui-1.8.9.custom.css" 
    rel="stylesheet" />
<link href="js/jquery.autocomplete.css" rel="stylesheet" type="text/css" />
<script src="js/jquery.autocomplete.js"></script>
<script type="text/javascript" src="js/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="js/jquery.dataTables.sort.plugin.js"></script>
<script type="text/javascript" src="js/jquery.dataTables.colReorderWithResize.plugin.js"></script>
<script type="text/javascript" src="js/jquery.dataTables.inputPagination.js"></script>
<script type="text/javascript" src="js/iepngfix_tilebg.js"></script>
<script type="text/javascript" src="/BomWebPortal/js/jquery.modalDialog.js?v=2015092515243350"></script>
<!-- <link type="text/css" href="css/dataTable/table.jui.css" rel="stylesheet" /> -->
<script src="js/jquery-ui-1.8.9.custom.min.js"></script>
<script src="js/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="js/jquery.blockUI.js"></script>
<link href="css/imsds.css" rel="stylesheet" type="text/css"> 

<script language="javascript">

	function formClear() {
		//alert('formClear function called');
		document.orderSummaryForm.orderIdStr.value = "";
		document.orderSummaryForm.staffId.value = "";
		/*var currentTime = new Date();
		
		var month = currentTime.getMonth() + 1;
		var day = currentTime.getDate();
		var year = currentTime.getFullYear();
		
		document.orderSummaryForm.startDate.value = day + "/" + month + "/" + year;
		document.orderSummaryForm.endDate.value = day + "/" + month + "/" + year;
		 */
		 $('input:radio[name=lob]').filter('[value=ALL]').attr('checked', true);
			$('input:radio[name=orderStatus]').filter('[value=ALL]').attr('checked', true);
			<c:if test='${shopCode == "TTW"}'>
			$('input:radio[name=selectedShopCode]').filter('[value=ALL]').attr(
					'checked', true);
			</c:if>
		$('input:radio[name=dmsInd]').filter('[value=""]').attr('checked', true);
		setCurrentDate('CLEAR');

		refreshStates();

		//$('#dateDatepickerStart').datepicker( "option", "maxDate", currentDate);

	}
	function setCurrentDate( callType) {
		if (callType=='CLEAR'){
		//alert('setCurrentDate function called');
			var currentTime = new Date();
			var month = currentTime.getMonth() + 1;
			var day = currentTime.getDate();
			var year = currentTime.getFullYear();
			
			document.orderSummaryForm.startDate.value = day + "/" + month + "/"	+ year;
			document.orderSummaryForm.endDate.value =  day + "/" + month + "/"	+ year;
			clearBomActivateDates();
		}
		//document.orderSummaryForm.startDate.value = currentTime.format("mm/dd/yyyy");
		//document.orderSummaryForm.endDate.value = currentTime.format("mm/dd/yyyy");
		 $(function() {
			 $.datepicker._gotoToday = function (id) { $(id).datepicker('setDate', new Date()).datepicker('hide').blur(); 
			 $('#dateDatepickerEnd').attr("disabled", false);
			 $('#dateDatepickerEnd').datepicker('setDate', new Date()).datepicker('hide').blur();
			 $('#dateDatepickerEnd').datepicker( "option", "minDate", new Date());
			 };
			 $('#dateDatepickerStart').datepicker({
					changeMonth : true,
					changeYear : true,
					showButtonPanel : true,
					dateFormat : 'dd/mm/yy',//, //'yy' is 4 digit in jquery UI, eg. 1920 
					maxDate: "+0d",yearRange: '2010:+0'
					, onSelect: function(dateText, inst) {
						var futureDate = $(this).datepicker("getDate");
						var toDate = new Date ();
						var futureDateMin = $(this).datepicker("getDate");
						futureDateMin.setDate(futureDate.getDate() - 13);

						//var maxDate = $( ".selector" ).datepicker( "option", "maxDate" );
						//alert(toDate);
						futureDate.setDate(futureDate.getDate() + 13);
						$('#dateDatepickerEnd').attr("disabled", false);
						$('#dateDatepickerEnd').datepicker( "option", "minDate", $(this).datepicker("getDate"));
						if (futureDate < toDate ){
						$('#dateDatepickerEnd').datepicker( "option", "maxDate", futureDate);
						$('#dateDatepickerEnd').datepicker( "setDate", $(this).datepicker("getDate"));
						}else{
							$('#dateDatepickerEnd').datepicker( "option", "maxDate", toDate);
							//$('#dateDatepickerEnd').datepicker( "option", "minDate", futureDateMin);
							$('#dateDatepickerEnd').datepicker( "setDate", $(this).datepicker("getDate"));
						}
						
						
					}
				});
				$('#dateDatepickerEnd').datepicker({
					changeMonth : true,
					changeYear : true,
					showButtonPanel : true,
					dateFormat : 'dd/mm/yy',//, //'yy' is 4 digit in jquery UI, eg. 1920 
					maxDate: "+0d",yearRange: '2010:+0'
				});
				
				
				
				
				 $('#bomStartDate').datepicker({
					 	clearText:'Clear',
						changeMonth : true,
						changeYear : true,
						showButtonPanel : true,
						dateFormat : 'dd/mm/yy',//, //'yy' is 4 digit in jquery UI, eg. 1920 
						maxDate: "+0d",yearRange: '2010:+0'
						, onSelect: function(dateText, inst) {
							var futureDate = $(this).datepicker("getDate");
							var toDate = new Date ();
							var futureDateMin = $(this).datepicker("getDate");
							futureDateMin.setDate(futureDate.getDate() - 6);

							//var maxDate = $( ".selector" ).datepicker( "option", "maxDate" );
							//alert(toDate);
							futureDate.setDate(futureDate.getDate() + 6);
							$('#bomEndDate').attr("disabled", false);
							$('#bomEndDate').datepicker( "option", "minDate", $(this).datepicker("getDate"));
							if (futureDate < toDate ){
							$('#bomEndDate').datepicker( "option", "maxDate", futureDate);
							$('#bomEndDate').datepicker( "setDate", $(this).datepicker("getDate"));
							}else{
								$('#bomEndDate').datepicker( "option", "maxDate", toDate);
								//$('#dateDatepickerEnd').datepicker( "option", "minDate", futureDateMin);
								$('#bomEndDate').datepicker( "setDate", $(this).datepicker("getDate"));
							}
							
							
						}
					});
					$('#bomEndDate').datepicker({
						changeMonth : true,
						changeYear : true,
						showButtonPanel : true,
						dateFormat : 'dd/mm/yy',//, //'yy' is 4 digit in jquery UI, eg. 1920 
						maxDate: "+0d",yearRange: '2010:+0',
						onSelect: function(dateText, inst) {
							if ($('#bomStartDate').val()=='') {
								$('#bomStartDate').datepicker("setDate", $(this).datepicker("getDate"));
							}
						}
					});
					
					//clearBomActivateDates();
		}); 
		
	//	$( "#dateDatepickerStart" ).datepicker( "option", "defaultDate", +5 );
		
		

	}
	
	function clearBomActivateDates() {
		$('#bomStartDate').val('');
		$('#bomEndDate').val('');
	}
	
	function calcDate() {
	    var start = $('#dateDatepickerStart').datepicker('getDate');
	    var end = $('#dateDatepickerEnd').datepicker('getDate');
	    var days = (end - start) / 1000 / 60 / 60 / 24;

	   // alert('date diff'+days);
	    return days;
	    //document.getElementById('total_days').value = days;
	}


	function openDialog(sbuid, ocid){
		openModalDialog("orderamend.html?sbuid="+sbuid+"&orderId="+ocid+"&dialogMode=true", "Order Id :"+ocid, "amendPage");
	}

	$(function() {
		//alert('main function called, set default value');
		var $radios = $('input:radio[name=lob]');
		if ($radios.is(':checked') == false) {
			$radios.filter('[value=ALL]').attr('checked', true);
		}

		var $radios2 = $('input:radio[name=orderStatus]');
		if ($radios2.is(':checked') == false) {
			$radios2.filter('[value=ALL]').attr('checked', true);
		}
		$radios.filter('[value=${lob}]').attr('checked', true);
		$radios2.filter('[value=${orderStatus}]').attr('checked', true);
		
		<c:if test='${shopCode == "TTW" || shopCode == "ALL"}'>
		var $radios3 = $('input:radio[name=selectedShopCode]');
		if ($radios3.is(':checked') == false) {
			$radios3.filter('[value=ALL]').attr('checked', true);
		}
		$radios3.filter('[value=${selectedShopCode}]').attr('checked', true);
		</c:if>
		
		var $dmsInd = $('input:radio[name=dmsInd]');
		$dmsInd.filter('[value="${dmsInd}"]').attr('checked', true);
		
		
		$('input:radio[name=lob]').click(function(e) {
			refreshStates();
		});
		
		setCurrentDate();
		
		refreshStates();
	});

	function refreshStates() {
		var lobRadio = $('input:radio[name=lob]:checked');
		if (lobRadio.val() == 'ALL') {
			$('#serviceNum').attr('disabled', 'disabled');
			$('#serviceNum').val('');
		} else {
			$('#serviceNum').removeAttr('disabled');
		}
		
		if (lobRadio.val() == 'MOB') {
			$("#bomStartDate").removeAttr('disabled');
			$("#bomEndDate").removeAttr('disabled');
		} else {
			$('#bomStartDate').attr('disabled', 'disabled');
			$('#bomEndDate').attr('disabled', 'disabled');
			clearBomActivateDates();
		}
		if (lobRadio.val() == 'IMS') {
			$("#orderType").removeAttr('disabled');
			$("#orderType").removeAttr('disabled');
		} else {
			$('#orderType').attr('disabled', 'disabled');
			$('#orderType').attr('disabled', 'disabled');
			$('#orderType').val('');
		}
	}
	

	function formSubmit() {
		var daysDiff=calcDate();
		//alert(daysDiff);
		if (daysDiff >=14){
			alert("End Date - Start Date must less or equal then 14 days ");
			return;
		}
		//alert($("input[name='selectedShopCode']:checked").val());
		if ($("input[name='selectedShopCode']:checked").val() =='ALL'){
			
			if (document.orderSummaryForm.startDate.value != document.orderSummaryForm.endDate.value){
				alert("Start and End date must be same, when Selected ALL shop");
				//e.preventDefault();
				return false;
			}
		}
		document.orderSummaryForm.submit();

	}
	
	
	function nextPage() {
		$('input[name="action"]').val("next");
		formSubmit();
	}
	function prevPage() {
		$('input[name="action"]').val("prev");
		formSubmit();
	}
	
</script>

<style type="text/css">
.recall_col { padding: 0; text-align: center }
.recall_col a { vertical-align: middle }
</style>
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
<form:form name="orderSummaryForm" method="POST"
	commandName="orderSummary">
	<input type="hidden" name="action" value="search"/>
	<table width="100%" class="tablegrey">
		<tr>
			<td bgcolor="#FFFFFF">
				<table width="100%" border="0" cellspacing="1">
					<tr>
						<td class="table_title">Order Search</td>
					</tr>
				</table>
				<table width="100%" width="100%" border="0" cellspacing="1"
					bgcolor="#FFFFFF">

					
					<tr class="contenttext">
						<td width="100%">
							<table width="100%" border="0" cellspacing="0" bgcolor="#FFFFFF">

								<c:choose>
									<c:when test='${(shopCode == "TTW" || shopCode == "ALL") && allowSearhAllInd=="Y"}'>
										<tr>
											<td width="130px"><b>Shop Code: </b></td>
											<td><input type="radio" name="selectedShopCode"
												value="ALL" /> All 
												<input type="radio"
												name="selectedShopCode" value="TTW" /> TTW</td>

										</tr>
									</c:when>
									<c:otherwise>
										<tr>
											<td width="130px"><b>Shop Code: </b></td>
											<td>${shopCode}

											</td>
										</tr>
									</c:otherwise>
								</c:choose>
								<tr>
									<td width="130px"><b>LOB: </b>
									</td>
									<td><input type="radio" name="lob" value="ALL" /> All <input
										type="radio" name="lob" value="LTS" /> LTS <input
										type="radio" name="lob" value="IMS" /> IMS <input
										type="radio" name="lob" value="MOB" /> MOB</td>

									<td valign="middle" width="50px"><b>AND</b></td>
									<td valign="middle">
										<table bgcolor="#FFFFFF">
											<tr>
												<td><b>Service Number: </b><input id="serviceNum" name="serviceNum"
													maxlength="20" value="${serviceNum }" /></td>
											</tr>
										</table>
									</td>
								</tr>
								<tr>
									<td><b>Order Status : </b>
									</td>
									<td><input type="radio" name="orderStatus" value="ALL" />
										All <input type="radio" name="orderStatus" value="PENDING" />
										Pending <input type="radio" name="orderStatus" value="CANCEL" />
										Cancel</td>

									<td valign="middle" width="50px"><b>OR</b>
									</td>
									<td valign="middle">
										<table bgcolor="#FFFFFF">
											<tr>
												<td><b>Order ID: </b><input name="orderIdStr"
													maxlength="20" value="${orderIdStr}" /></td>
											</tr>
										</table>
									</td>											
								</tr>
								<tr>
									<td><b>Application Date :</b>
									</td>

									<td style="white-space: nowrap;"><input name="startDate" maxlength="10"
										id="dateDatepickerStart" readonly="true" value="${startDate}" />
										to <input name="endDate" maxlength="10" id="dateDatepickerEnd"
										readonly="true" value="${endDate}" />
									</td>
									<td valign="middle" width="50px"><b>AND</b></td>
									<td valign="middle">
										<table bgcolor="#FFFFFF">
											<tr>
												<td style="white-space: nowrap;"><b>BOM Activated Date: </b><input name="bomStartDate" maxlength="10"
										id="bomStartDate" readonly="true" value="${bomStartDate}" />
										to <input name="bomEndDate" maxlength="10" id="bomEndDate"
										readonly="true" value="${bomEndDate}" /></td>
												<td><a href="#" onclick="clearBomActivateDates();return false;">Clear</a></td>
											</tr>
										</table>
									</td>
								</tr>
								<tr>
									<td><b>Salesman Staff ID:</b>
									</td>
									<td><input name="staffId" maxlength="20"
										value="${staffId}" /></td>
								</tr>
							<tr>
								<td><b>DMS Indicator:</b></td>
								<td>
									<input type="radio" name="dmsInd" value="" label="All"/> All
									<input type="radio" name="dmsInd" value="Y" label="Uploaded"/> Uploaded
									<input type="radio" name="dmsInd" value="N" label="Outstanding"/> Outstanding
									<input type="radio" name="dmsInd" value="NA" label="Not Applicable"/> Not Applicable									
								</td>
							</tr>
							<tr>	
								<th style="text-align:left">Order Type:  </th>
								<td><select name = "orderType" id="orderType">
  									<option value="" label="Select..." >Select...</option>
 									<option value="RET" label="Retention" >Retention</option>
 									<option value="TEMP" label="Termination" >Termination</option>
									<option value="PCD-A" label="PCD-ACQ" >PCD-ACQ</option>
									<option value="NTV-A" label="NTV-ACQ" >NTV-ACQ</option>
									<option value="NTVAO" label="NTV-AddOn" >NTV-AddOn</option>
									<option value="NTVUS" label="NTV-Upgrade" >NTV-Upsell</option>
									<option value="NTVRE" label="NTV-Retention" >NTV-Ret</option>
									</select>
								</td>
							</tr>									
							</table>
						</td>
					</tr>
					<tr>
						<td colspan="3"><br> 
						  <a href="#" class="nextbutton"	onClick="javascript:formSubmit();">Search</a> 
							<a href="#"	class="nextbutton" onClick="javascript:formClear();">Clear	</a> 
							<input	type="hidden" name="currentView" value="orderSummary" /> <br>

						</td>
					</tr>
					<tr>
						<td colspan="3">&nbsp;</td>
					</tr>
				</table> <c:if test='${!empty orderList}'>
					<c:if test="${orderList.pageCount > 1 }">
					<center>
					<a href="#" class="nextbutton"	onClick="javascript:prevPage();">Prev</a> Page ${orderList.page + 1 } of ${orderList.pageCount }  <a href="#" class="nextbutton"	onClick="javascript:nextPage();">Next</a>
					</center>
					<br/>
					</c:if>
					<table width="100%" border="1" cellspacing="0" class="contenttext"
						bgcolor="#FFFFFF">

						<tr class="contenttextgreen">
							<td class="table_title">LOB</td>
							<td class="table_title">Order ID</td>
							<td class="table_title">Order Type</td>
							<td class="table_title">OCID</td>
							<td class="table_title">Application Date</td>
							<td class="table_title">Customer Name</td>
							<td class="table_title">Service Number</td>
							<td class="table_title">IMS Login id</td>
							<td class="table_title">BOM Activated Date</td>
							<td class="table_title">Order Status</td>
							<td class="table_title">Error Message</td>
							<td class="table_title">Change SIM Indicator</td>
							<td class="table_title">DMS Indicator</td>
							<td class="table_title">Distribution Mode</td>
							<td class="table_title">Collect Method</td>
							<td class="table_title">Recall Order</td>
						</tr>
							<c:forEach items="${orderList.pageList}" var="order">
								<tr>
									<!-- ---------- LOB ---------------- -->
									<td>${order.orderSumLob}&nbsp;</td>
									
									<!-- ----------- Order ID ---------------- -->
									<td><c:if test='${order.orderSumLob == "MOB"}'>
											<sb-util:orderpreview orderId="${order.orderId}" shopCd="${order.shopCode}"  mobcosbaseurl="<%=scheme %>" />
											<%-- <c:if test="${fn:length(order.orderId) == 11}">
												<c:if test="${fn:startsWith(order.orderId, 'R') || fn:startsWith(order.orderId, 'P')}">
													<a
														href="orderdetail.html?sbuid=${param.sbuid}&orderId=${order.orderId}&action=ENQUIRY"
														title="Order Enquiry for MOB">${order.orderId}&nbsp; </a>
												</c:if>
												<c:if test="${fn:startsWith(order.orderId, 'C')}">
													<a
														href="mobccspreview.html?orderId=${order.orderId}&action=ENQUIRY"
														title="Order Enquiry for MOB">${order.orderId}&nbsp; </a>
												</c:if>
											</c:if>
											<c:if test="${fn:length(order.orderId) != 11}">
												<a
													href="orderdetail.html?sbuid=${param.sbuid}&orderId=${order.orderId}&action=ENQUIRY"
													title="Order Enquiry for MOB">${order.orderId}&nbsp; </a>
											</c:if> --%>
										</c:if> <c:if test='${order.orderSumLob == "LTS"}'>
											<a
												href="ltsorder.html?sbuid=${sessionScope.sbuid}&action=ENQUIRY&orderId=${order.orderId}"
												title="Order Enquiry for LTS">${order.orderId}&nbsp; </a>
										</c:if> <c:if test='${order.orderSumLob == "IMS"}'>
											<c:choose>
											<c:when test = "${order.orderType eq 'NTV-A' || order.orderType eq 'NTVAO' || order.orderType eq 'NTVUS'|| order.orderType eq 'NTVRE'}">
													<a href="${ntvdomain}ntvorderdetail.html?_al=new&orderId=${order.orderId}"
													title="Order Enquiry for IMS">${order.orderId}&nbsp; </a>
											</c:when>
											       <c:when test='${(fn:substring(order.orderId, 5, 6))=="R"||(fn:substring(order.orderId, 5, 6))=="U"}'>		
												<a href="${IMSretentionViewUrl}=${order.orderId}"
													title="Order Enquiry for IMS">${order.orderId}&nbsp; </a>
											       </c:when>
											       <c:when test='${(fn:substring(order.orderId, 5, 6))=="T"}'>				
												<a href="${IMSterminationViewUrl}=${order.orderId}"
													title="Order Enquiry for IMS">${order.orderId}&nbsp; </a>
											       </c:when>
											       <c:otherwise>										
												<a href="imsorderdetail.html?orderId=${order.orderId}"
													title="Order Enquiry for IMS">${order.orderId}&nbsp; </a>
											       </c:otherwise>
											</c:choose>		
										</c:if> <c:if test='${order.orderSumLob == null}'>
										${order.orderId}&nbsp;
									</c:if></td>
								<td>
								<c:choose>
									<c:when test='${order.orderSumLob ne "IMS"}'>
										${order.orderType}&nbsp;
										<c:if test='${order.orderSumLob eq "MOB"}'>
										/ <sb-util:codelookup codeId="${order.brand}" codeType="MOB_BRAND" />
										</c:if>
									</c:when>
									<c:otherwise>
										<c:choose>
											<c:when test="${order.orderType eq 'NTV-A'}">nowTV Acquisition&nbsp;</c:when>
											<c:when test="${order.orderType eq 'NTVAO'}">nowTV AddOn&nbsp;</c:when>
											<c:when test="${order.orderType eq 'NTVUS'}">nowTV UpSell&nbsp;</c:when>
											<c:when test="${order.orderType eq 'NTVRE'}">nowTV Retention&nbsp;</c:when>
											<c:when test="${order.orderType eq 'PCD_T'}">Termination&nbsp;</c:when>
											<c:when test="${order.orderType eq 'PCD_R'}">PCD Retention&nbsp;</c:when>
											<c:otherwise>PCD Acquisition&nbsp;</c:otherwise>
										</c:choose>	
									</c:otherwise>
								</c:choose>
								</td>
									<!-- ---------- OCID ---------------- -->
									<td>${order.ocid}&nbsp;</td>

									<!-- ---------- App Date ---------------- -->
									<td><c:choose>
											<c:when test='${order.orderSumLob == "IMS" }'>
												<fmt:formatDate pattern="dd/MM/yyyy HH:mm"
													value="${order.appInDate}" />
											</c:when>
											<c:otherwise>
												<fmt:formatDate pattern="dd/MM/yyyy HH:mm:ss"
													value="${order.appInDate}" />
											</c:otherwise>
										</c:choose> &nbsp;</td>
									
									<!-- ---------- CUST NAME ---------------- -->
									<td>${order.orderSumCustName}&nbsp;</td>

									<!-- ------------Service Number -------------------- -->
									<td>
									<c:if
										test='${order.orderSumLob == "MOB" && !(empty order.orderSumServiceNum)}'>
										MRT: ${order.orderSumServiceNum}&nbsp;
									</c:if>
									<c:if
										test='${order.orderSumLob == "IMS" && !(empty order.orderSumServiceNum)}'>
										FSA: ${order.orderSumServiceNum}&nbsp;
									</c:if>
									<c:if
										test='${order.orderSumLob == "LTS" && !(empty order.orderSumServiceNum)}'>
										<c:choose>
											<c:when test="${fn:length(order.orderSumServiceNum) == 12}">
												${fn:substring(order.orderSumServiceNum, 4,
													12)}&nbsp;
											</c:when>
											<c:otherwise>
												${order.orderSumServiceNum}&nbsp;
											</c:otherwise>
										</c:choose>
									</c:if>
									<c:if test='${empty order.orderSumServiceNum}'>
										&nbsp;
									</c:if>
									</td>

									<!-- ---------- IMS Login ID ---------------- -->
									<td>
									<c:choose>
										<c:when test="${order.orderType eq 'NTV-A' || order.orderType eq 'NTVAO' || order.orderType eq 'NTVUS' || order.orderType eq 'NTVRE'}">&nbsp;</c:when>
										<c:otherwise>${order.imsLoginId}&nbsp;</c:otherwise>
									</c:choose>	
									</td>

									<!-- ---------- BOM Activated Date ---------------- -->
									<td>
									<c:choose>
											<c:when test='${order.orderSumLob == "MOB" }'>
												<fmt:formatDate pattern="dd/MM/yyyy"
													value="${order.bomCreationDate}" />&nbsp;
											</c:when>
											<c:otherwise>
												<fmt:formatDate pattern="dd/MM/yyyy HH:mm"
													value="${order.bomCreationDate}" />&nbsp;
											</c:otherwise>
										</c:choose>
										</td>

										
									<!-- ---------- Order Status ---------------- -->
									<td>
										<%@ include file="/WEB-INF/jsp/orderstatus.jsp"%>
									</td>

									<!-- ---------- Error Message ---------------- -->
									<td><c:choose>
											<c:when test='${order.orderSumLob == "IMS"}'>N/A</c:when>
											<c:otherwise>${order.errorMessage}</c:otherwise>
										</c:choose> &nbsp;</td>
									<td>${order.changeSimInd}&nbsp;</td>
									<!-- ---------- DMS Indicator ---------------- -->										
									<td><c:choose>
											<c:when test='${order.orderSumLob == "IMS" }'>${order.dmsInd=="Y" ? "Uploaded" : "Outstanding"}</c:when>
											<c:otherwise>${order.collectMethod=="D" ? (order.dmsInd=="Y"?"Uploaded":"Outstanding") : "Not Applicable"}</c:otherwise>
										</c:choose>
									</td>
									
									<!-- ---------- Distribution Mode ---------------- -->									
									<td>${order.disMode=="E" ? "e-Signature" : (order.disMode=="P"?"Paper" : "&nbsp;")}</td>
									
									<!-- ---------- Collect Method ---------------- -->									
									<td>${order.collectMethod=="D" ? "Digital" : (order.collectMethod=="P"?"Paper" : "&nbsp;")}</td>
									
									<!-- ---------- Recall Order ---------------- -->
									<td height="30px" class="recall_col"><c:if
											test='${order.orderSumLob == "MOB"}'>
											<sb-util:orderrecall orderId="${order.orderId}" shopCd="${order.shopCode}" 
												orderStatus="${order.orderStatus}" checkPoint="${order.checkPoint}" todayOrderFlag="${order.todayOrderFlag}" mobcosbaseurl="<%=scheme %>" frozenInd="${order.bomFrozenInd }"/>
											<%-- <c:if test="${fn:length(order.orderId) == 11}">
												<c:if test="${fn:startsWith(order.orderId, 'C')}">
													<c:if
														test="${order.orderStatus == '99' || (order.orderStatus == '01' && order.checkPoint <= '399')}">
														<a class="nextbutton"
															href="mobccspreview.html?orderId=${order.orderId}&status=${order.orderStatus}"
															title="Recall Order ${order.orderId}">Recall&nbsp;</a>
													</c:if>
												</c:if>

												<c:if test="${(fn:startsWith(order.orderId, 'R') || fn:startsWith(order.orderId, 'P')) && order.orderStatus=='INITIAL' && order.todayOrderFlag=='Y' && order.shopCode==salesUserDto.bomShopCd}">
													<a class="nextbutton"	href="orderdetail.html?orderId=${order.orderId}&status=${order.orderStatus}"
														title="Recall Order ${order.orderId}">Recall&nbsp;</a>
														
												</c:if>
											</c:if>
											<c:if
												test="${order.orderStatus=='INITIAL' && order.todayOrderFlag=='Y' && fn:length(order.orderId) != 11 }">
												<a class="nextbutton"
													href="orderdetail.html?sbuid=${param.sbuid}&orderId=${order.orderId}&status=${order.orderStatus}"
													title="Recall Order ${order.orderId}">Recall&nbsp;</a>
											</c:if> --%>
											<input type="hidden" name="orderStatus"
												value="${order.orderStatus}" />
											<input type="hidden" name="todayOrderFlag"
												value="${order.todayOrderFlag}" />
										</c:if> <c:if test='${order.orderSumLob == "IMS"}'>
											<c:choose>
											<c:when test = "${order.orderType eq 'NTV-A' || order.orderType eq 'NTVAO' || order.orderType eq 'NTVUS' || order.orderType eq 'NTVRE'}">
												<c:choose>
														<c:when test="${order.orderStatus=='06'}">
															<a class="nextbutton"href="${ntvdomain}ntvorderdetail.html?_al=new&orderId=${order.orderId}&status=${order.orderStatus}&awaitCash=Y"
																title="Recall Order ${order.orderId}">Recall&nbsp;</a>								
														</c:when>
														<c:when test="${order.orderStatus=='08'}">
															<a class="nextbutton"href="${ntvdomain}ntvorderdetail.html?_al=new&orderId=${order.orderId}&status=${order.orderStatus}&awaitCash=Y"
																title="Recall Order ${order.orderId}">Recall&nbsp;</a>								
														</c:when>
														<c:when test="${order.orderStatus=='11' or order.orderStatus=='15'}">
															<a class="nextbutton"href="${ntvdomain}ntvorderdetail.html?_al=new&orderId=${order.orderId}&status=${order.orderStatus}&awaitSignOff=Y"
																title="Recall Order ${order.orderId}">Recall&nbsp;</a>								
														</c:when>
														<c:when test="${(order.orderStatus=='10' || order.orderStatus=='31' || order.orderStatus=='32')}">
															<a class="nextbutton"href="${ntvdomain}ntvorderdetail.html?_al=new&orderId=${order.orderId}&status=${order.orderStatus}"
																title="Recall Order ${order.orderId}">Recall&nbsp;</a>								
														</c:when>
														<c:when test="${order.orderStatus=='02' || order.orderStatus=='04' || imsOrderList.orderStatus=='92' || imsOrderList.orderStatus=='93'}">
															<a class="nextbutton"title="Amend Order ${order.orderId}" href="orderamend.html?sbuid=${sessionScope.sbuid}&orderId=${order.orderId}" >Amend&nbsp;</a>
														</c:when>							
												</c:choose>											
											</c:when>
											<c:otherwise>
											<c:choose>
											       <c:when test='${(order.orderStatus=="10" || order.orderStatus=="31" || order.orderStatus=="32")&&( (fn:substring(order.orderId, 5, 6))=="R" || (fn:substring(order.orderId, 5, 6))=="U") }'>		
												<a class="nextbutton" href="${IMSretentionRecallUrl}=${order.orderId}"
													title="Recall Order ${order.orderId}">Recall&nbsp; </a>
											       </c:when>
											       <c:when test='${(order.orderStatus=="10" || order.orderStatus=="31" || order.orderStatus=="32")&&(fn:substring(order.orderId, 5, 6))=="T"}'>				
												<a class="nextbutton" href="${IMSterminationRecallUrl}=${order.orderId}"
													title="Recall Order ${order.orderId}">Recall&nbsp; </a>
											       </c:when>
											       <c:when test='${(order.orderStatus=="10" || order.orderStatus=="31" || order.orderStatus=="32")&&(fn:substring(order.orderId, 5, 6))!="T"&&(fn:substring(order.orderId, 5, 6))!="R"&&(fn:substring(order.orderId, 5, 6))!="U"}'>			
												<a class="nextbutton" href="imsorderdetail.html?orderId=${order.orderId}&status=${order.orderStatus}"
													title="Recall Order ${order.orderId}">Recall&nbsp;</a>
											       </c:when>
												 <c:when test="${(order.orderStatus=='02' || order.orderStatus=='04' || imsOrderList.orderStatus=='92' || imsOrderList.orderStatus=='93')}">
<%-- 												<a class="nextbutton" href="orderamend.html?sbuid=${sessionScope.sbuid}&orderId=${order.orderId}" --%>
<%-- 														title="Amend Order ${order.orderId}">Amend</a> --%>
														
											<a class="nextbutton"	href="#"	title="Amend Order ${order.orderId}"
												onclick="openDialog('${sessionScope.sbuid}','${order.orderId}')" >Amend&nbsp;</a>
												</c:when>
											</c:choose>
											</c:otherwise>
											</c:choose>		
										</c:if> <c:if test='${order.orderSumLob == "LTS"}'>
											<!-- Recall Order for status = Suspend(S) or Approval Reject(R)-->
											<c:if
												test="${order.orderStatus=='S' || order.orderStatus=='R'}">
												<a class="nextbutton"
													href="ltsorder.html?sbuid=${sessionScope.sbuid}&action=RECALL&orderId=${order.orderId}"
													title="Recall Order ${order.orderId}">Recall&nbsp;</a>
											</c:if>
										<!-- Amend Order for status = SignOff(D), Full WorkQueue(P),  Forced WQ(F), Extracted(E) and Created Bom Order (B) -->											
										<c:if test="${order.allowAmendOrder && (order.orderStatus=='D' || order.orderStatus=='F' || order.orderStatus=='E' || order.orderStatus=='B' || order.orderStatus=='P')}">
											<a class="nextbutton"
													href="orderamend.html?sbuid=${sessionScope.sbuid}&orderId=${order.orderId}"
													title="Amend Order ${order.orderId}">Amend</a>
											</c:if>
										</c:if> </td>



								</tr>

							</c:forEach>

					</table>
				</c:if> <c:if test='${empty orderList}'>
					<table width="100%" border="1" cellspacing="0" class="contenttext"
						bgcolor="#FFFFFF">
						<tr class="contenttext">
							<td> <span class="contenttext_red">No Record Found.</span></td>
						</tr>
					</table>


				</c:if></td>
		</tr>
	</table>





</form:form>


<!---------------------------------------  end content ------------------------------------------->
<%@ include file="/WEB-INF/jsp/footer.jsp"%>