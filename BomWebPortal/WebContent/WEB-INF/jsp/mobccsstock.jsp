<%@ include file="/WEB-INF/jsp/header.jsp"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="sb-util" tagdir="/WEB-INF/tags/util" %>
<jsp:useBean id="now" class="java.util.Date" scope="request" />

<div id="progressbar"><br>
<br>
</div>

<link type="text/css" href="css/custom-theme/jquery-ui-1.8.9.custom.css" rel="stylesheet" />
<script src="js/jquery-ui-1.8.9.custom.min.js"></script>
<%@ page import="
					com.bomwebportal.bean.LookupTableBean,
					com.bomwebportal.mob.ccs.dto.CodeLkupDTO,
					com.bomwebportal.util.ConfigProperties,
					java.util.*
				"
%>
<%
String mobCosUrl = ConfigProperties.getPropertyByEnvironment("mobcos.url.base");
%>
<script type="text/javascript">
function updatePaging(tableClass) {
	var $trs = $("table." + tableClass + " tbody tr");
	var $control = $("div." + tableClass);
	$control.find(".control").show().data("index", 0);
	$control.find(".control .left a").hide();
	if ($trs.length > 10) {
		$control.find(".control .right a").show();
	} else {
		$control.find(".control .right a").hide();
	}
	$control.find(".control .label").text("1 - " + $trs.filter(":visible").length + " / " + $trs.length);
	$control.find(".control .left a").unbind("click").click(function(e) {
		e.preventDefault();
		$trs.hide();
		var start = $control.find(".control").data("index") - 10;
		if (start < 0) {
			start = 0;
		}
		var end = start + 10;
		if (end > $trs.length) {
			end = $trs.length;
		}
		if (start > 0) {
			$control.find(".control .left a").show();
		} else {
			$control.find(".control .left a").hide();
		}
		if (end < $trs.length) {
			$control.find(".control .right a").show();
		} else {
			$control.find(".control .right a").hide();
		}
		$trs.slice(start, end).show().first().find("input[type=radio]").attr("checked", true);
		$control.find(".control .label").text((start + 1) + " - " + (end) + " / " + $trs.length);
		$control.find(".control").data("index", start);
		return false;
	});
	$control.find(".control .right a").unbind("click").click(function(e) {
		e.preventDefault();
		$trs.hide();
		var start = $control.find(".control").data("index") + 10;
		if (start > $trs.length) {
			start = $trs.length;
		}
		var end = start + 10;
		if (end > $trs.length) {
			end = $trs.length;
		}
		if (start > 0) {
			$control.find(".control .left a").show();
		} else {
			$control.find(".control .left a").hide();
		}
		if (end < $trs.length) {
			$control.find(".control .right a").show();
		} else {
			$control.find(".control .right a").hide();
		}
		$trs.slice(start, end).show().first().find("input[type=radio]").attr("checked", true);
		$control.find(".control .label").text((start + 1) + " - " + (end) + " / " + $trs.length);
		$control.find(".control").data("index", start);
		return false;
	});
}
	function formSubmit(actType) {
		$("#actionType").val(actType);
		document.stockInventoryForm.submit();
	}

	function onClear() {
		$("form[name=stockInventoryForm] input").val("");
		$("form[name=stockInventoryForm] select").attr("selectedIndex", 0);
		$("#smsr").empty();
		$("#sisr").empty();
	}
	
	function onClear2() {
		$("#sisr").empty();
	}
$(document).ready(function() {
	$("form[name=stockInventoryForm] input[type=text],form[name=stockInventoryForm] select[name=type]").click(function() {
		var $parent = $(this);
		$("form[name=stockInventoryForm] input[type=text]").not(this).filter(function() {
			return !($parent.attr("name") == "type" && $(this).attr("name") == "model");
		}).val("");
		$("form[name=stockInventoryForm] select[name=type]").not(this).filter(function() {
			return !($parent.attr("name") == "model" && $(this).attr("name") == "type");
		}).attr("selectedIndex", 0);
	});
	updatePaging("tempResultList");
	updatePaging("resultList");
	// switch to selectedNum page
	var $input = $("table.tempResultList input[name=selectedNum]:checked");
	var index = $("table.tempResultList input[name=selectedNum]").index($input);
	if (index != -1) {
		var i = 0;
		while (i < parseInt(index / 10, 10)) {
			$("div.tempResultList .control .right a").click();
			i++;
		}
		$input.attr("checked", true);
	}
});
</script>

<form:form name="stockInventoryForm" method="POST" commandName="stockInventory">
	<%-- ***************************   SEARCHING KEY   ***************************************** --%>
	<form:errors path="type" cssClass="error" />
	<form:errors path="searchItemCode" cssClass="error" />
	<form:errors path="selectedNum" cssClass="error" />
	<form:errors path="status" cssClass="error" />
	<form:errors path="model" cssClass="error" />

	<table class="contenttext" style="width: 100%; background-color: white; border: 2px solid #bebebe">
	<colgroup style="width: 100px"></colgroup>
	<colgroup></colgroup>
	<colgroup style="width: 100px"></colgroup>
	<colgroup></colgroup>
	<tr>
		<td class="table_title" colspan="4" style="font-size: medium">Stock Inventory Maintenance</td>
	</tr>
	<%-- ********************   SEARCHING KEY --- ROW 1 (ID DOC + search)   ***************************** --%>
	<tr>
		<td>
			Type:
		</td>
		<td>
			<form:select path="type">
				<form:option value="NONE" label="Select" />
				<form:options items="${typeList}" itemValue="codeId" itemLabel="codeDesc" />
			</form:select>
		</td>
		<td>
			Model:
		</td>
		<td>
			<form:input path="model" id="stockModelField" cssStyle="width: 300px"/>
		</td>
	</tr>
	<tr>
		<td>
			Item Code:
		</td>
		<td>
			<form:input path="searchItemCode" cssStyle="width: 150px" id="stockItemCodeField" maxlength="7" />
		</td>
	</tr>
	<%-- <tr>
		<td>
			Item Serial:
		</td>
		<td>
			<form:input path="itemSerial" cssStyle="width: 150px" id="stockItemSerialField" maxlength="20" />
		</td>
	</tr>
	<tr>
		<td>
			Order Id:
		</td>
			<td>
				<form:input path="orderId" cssStyle="width: 150px" id="stockOrderIdField" maxlength="12" />
			</td>
	</tr> --%>
	<tr>
		<td colspan="4">
			<div class="buttonmenubox_R">
				<a href="#" class="nextbutton" onClick="javascript:formSubmit('SEARCH1');">Search</a> 
				<a href="#" class="nextbutton" onClick="onClear()">Clear</a>
			</div>
		</td>
	</tr>
	</table>

	<c:if test='${(actionType == "SEARCH1" && (sk == 1 || sk == 2)) || actionType == "SEARCH2"}'>
		<!-- ******************************* TEMP TABLE ************************** -->
		<table id="smsr" width="100%" class="tablegrey">
			<tr>
				<td bgcolor="#FFFFFF" width="100%" colspan="4"><!--content-->
				<table width="100%" border="0" cellspacing="1" rules="none">
					<tr>
						<td class="table_title">Stock Model Search Result</td>
					</tr>
				</table>
				<c:choose>
					<c:when test='${empty tempResultList}'>
						<%-- ***************************   NO RECORD FOUND   ****************************** --%>
						<table width="100%" class="tablegrey">
						<tr>
							<td>No Record Found.</td>
						</tr>
						</table>
					</c:when>
					<c:otherwise>
						<%-- ***************************   TEMP RESULT FOUND   ****************************** --%>

								<table width="100%" border="1" cellspacing="0" class="contenttext tempResultList" bgcolor="#FFFFFF">
								<thead>
									<tr class="contenttextgreen">
										<td class="table_subtitle_blue" width="5%">&nbsp;</td>
										<td class="table_subtitle_blue">Type</td>
										<td class="table_subtitle_blue">Item Code</td>
										<td class="table_subtitle_blue" width="65%">Model</td>
										<td class="table_subtitle_blue">Assign Mode</td>
									</tr>
								</thead>
								<tbody>
										<c:forEach items="${tempResultList}" var="tempResult"
											varStatus="counter">
											<tr<c:if test="${counter.index >= 10}"> style="display:none"</c:if>>
												<td width="3%" align="center"><form:radiobutton
													path="selectedNum" value="${counter.index}" /></td>
												
												<c:choose>
													<c:when test='${tempResult.scItemType == "01"}'>
														<td>&nbsp;HANDSET</td>
													</c:when>
													<c:when test='${tempResult.scItemType == "02"}'>
														<td>&nbsp;SIM</td>
													</c:when>
													<c:when test='${tempResult.scItemType == "03"}'>
														<td>&nbsp;GIFT-PC</td>
													</c:when>
													<c:when test='${tempResult.scItemType == "04"}'>
														<td>&nbsp;TABLET</td>
													</c:when>
													<c:when test='${tempResult.scItemType == "05"}'>
														<td>&nbsp;ANS</td>
													</c:when>
													<c:when test='${tempResult.scItemType == "06"}'>
														<td>&nbsp;GIFT-MISC</td>
													</c:when>
													<c:otherwise>
														<td>&nbsp;${tempResult.scItemType}</td>
													</c:otherwise>
												</c:choose>
												<c:choose>
													<c:when test="${bomsalesuser.areaCd == 'OST'}">
													<td>&nbsp;${tempResult.scItemCode}</td>
													</c:when>
													<c:otherwise>
														<td>&nbsp; <a
															href="<c:url value="mobccsstockmodeldetailsupdate.html">
																		<c:param name="itemCode" value="${tempResult.scItemCode}"/>
																	</c:url>">${tempResult.scItemCode}</a>
														</td>
													</c:otherwise>
												</c:choose>
												<td width="65%">&nbsp;${fn:toUpperCase(tempResult.scItemDesc)}</td>
												<c:choose>
													<c:when test='${tempResult.scAssignMode == "01"}'>
														<td>&nbsp;AUTO</td>
													</c:when>
													<c:when test='${tempResult.scAssignMode == "02"}'>
														<td>&nbsp;MANUAL</td>
													</c:when>
													<c:otherwise>
														<td>&nbsp;${tempResult.scAssignMode}</td>
													</c:otherwise>
												</c:choose>
											</tr>
										</c:forEach>
								</tbody>
								</table>

<c:if test="${not empty tempResultList}">
<div style="text-align: center; padding: 5px 0" class="tempResultList">
	<span class="control">
		<span class="left" style="display: inline-block; width: 10px"><a href="#" style="color: black; display: none">&lt;</a></span>
		<span class="label" style="display: inline-block; width: 120px; text-align: center"></span>
		<span class="right" style="display: inline-block; width: 10px"><a href="#" style="color: black; display: none">&gt;</a></span>
	</span>
</div>
</c:if>

<hr>

					<%-- 	<table width="100%" border="0" cellspacing="0" class="contenttext" bgcolor="#FFFFFF">
						<colgroup style="width: 100px"></colgroup>
						<colgroup></colgroup>
						<tr>
							<td>Stock Pool:</td>
							<td>
								<form:select path="stockPool">
									<form:option value="" label=""/>
									<form:options items="${stockPoolList}" itemValue="parmValue" itemLabel="parmValue"/>
								</form:select>
							</td>
						</tr>
						<tr>
							<td>
								Status:
							</td>
							<td>
								<form:select path="status">
									<form:options items="${statusList}" itemValue="codeId" itemLabel="codeDesc" />
									<form:options items="${statusList}" itemValue="parmValue" itemLabel="parmValue" />
								</form:select></td>
						</tr>
						<tr>
							<td colspan="2">
								<div class="buttonmenubox_R">
									<a href="#" class="nextbutton" onClick="javascript:formSubmit('SEARCH2');">Search</a>
									<a href="#" class="nextbutton" onClick="onClear2()">Clear</a>
								</div>
							</td>
						</tr>
						</table> --%>
					</c:otherwise>
				</c:choose></td>
			</tr>

		</table>
	</c:if>

	<!-- ******************** STOCK ITEM RESULT ************************ -->

	<c:if test='${(actionType == "SEARCH1" && (sk == 3 || sk == 4)) || actionType == "SEARCH2"}'>
		<table id="sisr" width="100%" class="tablegrey">
			<tr>
				<td bgcolor="#FFFFFF"><!--content-->
				<table width="100%" border="0" cellspacing="1" rules="none">
					<tr>
						<td class="table_title">Stock Item Search Result</td>
					</tr>
				</table>

				<c:if test='${empty resultList}'>
					<%-- ***************************   NO RECORD FOUND   ****************************** --%>
					<table width="100%" class="tablegrey">
						<tr>
							<td bgcolor="#FFFFFF"><!--content-->
							<table width="100%" border="0" cellspacing="1" rules="none">
								<tr>
									<td>No Record Found.</td>
								</tr>
							</table>
							</td>
						</tr>
					</table>
				</c:if> 
				<c:if test='${!empty resultList}'>
					<%-- ***************************   RESULT TABLE   ****************************** --%>

							<table width="100%" border="1" cellspacing="0" class="contenttext resultList" bgcolor="#FFFFFF">
							<thead>
								<tr class="contenttextgreen">
									<td class="table_subtitle_blue">Type</td>
									<td class="table_subtitle_blue">Item Code</td>
									<td class="table_subtitle_blue">Model</td>
									<td class="table_subtitle_blue">Serial Number</td>
									<td class="table_subtitle_blue">Stock Pool</td>
									<td class="table_subtitle_blue">Status</td>
									<td class="table_subtitle_blue">Order Id</td>
									<td class="table_subtitle_blue">Location</td>
									<td class="table_subtitle_blue">Stock In Date</td>
									<td class="table_subtitle_blue">Batch No.</td>
									<td class="table_subtitle_blue">Remarks</td>
								</tr>
							</thead>
							<tbody>
									<c:forEach items="${resultList}" var="result" varStatus="status">
										<tr<c:if test="${status.index >= 10}"> style="display:none"</c:if>>
											<td>&nbsp;${result.type}&nbsp;</td>
										
											<td>&nbsp;${result.itemCode}&nbsp;</td>
											
											<td>&nbsp;${result.model}&nbsp;</td>

											<td>
											<c:url value='mobccsstockupdate.html' var="url">
												<c:param name="serialNumber" value ="${result.imei}"></c:param>
											</c:url>
												<a href="${url}">
													&nbsp;${result.imei}&nbsp; 
												</a>
											</td>
											
											<td>
												${result.stockPool}
											</td>
													
											<c:choose>
												<c:when test='${result.status == "02"}'>
													<td>&nbsp;FREE</td>
												</c:when>
												<c:when test='${result.status == "18"}'>
													<td>&nbsp;RESERVE</td>
												</c:when>
												<c:when test='${result.status == "19"}'>
													<td>&nbsp;ASSIGN</td>
												</c:when>
												<c:when test='${result.status == "20"}'>
													<td>&nbsp;SOLD</td>
												</c:when>
												<c:when test='${result.status == "21"}'>
													<td>&nbsp;TRANSFER</td>
												</c:when>
												<c:when test='${result.status == "22"}'>
													<td>&nbsp;RETURN</td>
												</c:when>
												<c:when test='${result.status == "23"}'>
													<td>&nbsp;DOA</td>
												</c:when>
												<c:when test='${result.status == "24"}'>
													<td>&nbsp;DEASSIGN</td>
												</c:when>
												<c:when test='${result.status == "25"}'>
													<td>&nbsp;CANCELLING</td>
												</c:when>
												<c:otherwise>
													<td>&nbsp;${result.status}</td>
												</c:otherwise>
											</c:choose>

											<td>&nbsp;${result.orderId}&nbsp;</td>
											<c:choose>
												<c:when test='${result.location == "01"}'>
													<td>&nbsp;LTF</td>
												</c:when>
												<c:when test='${result.location == "02"}'>
													<td>&nbsp;PLG</td>
												</c:when>
												<c:otherwise>
													<td>&nbsp;${result.location}</td>
												</c:otherwise>
											</c:choose>

											<td>&nbsp; <fmt:formatDate
												pattern="dd/MM/yyyy" value="${result.stockInDate}" />
											&nbsp;</td>

											<td>&nbsp;${fn:toUpperCase(result.batchRef)}&nbsp;</td>
											
											<td>&nbsp;${fn:toUpperCase(result.remarks)}&nbsp;</td>

										</tr>
									</c:forEach>
								</tbody>
							</table>
<c:if test="${not empty resultList}">
<div style="text-align: center; padding: 5px 0" class="resultList">
	<span class="control">
		<span class="left" style="display: inline-block; width: 10px"><a href="#" style="color: black; display: none">&lt;</a></span>
		<span class="label" style="display: inline-block; width: 120px; text-align: center"></span>
		<span class="right" style="display: inline-block; width: 10px"><a href="#" style="color: black; display: none">&gt;</a></span>
	</span>
</div>
</c:if>
				</c:if></td>
			</tr>
		</table>
	</c:if>

	<br>

	<%-- ********************************   BOTTOM BUTTONS   ************************************* --%>
		<table width="100%" border="0" cellspacing="0">
			<tr>
				<td align="center" width="20%">
					<div>
						<a href="mobccsstockmodeldetails.html" class="nextbutton">New Stock Model</a>
					</div>
				</td>
				
				<td align="center" width="20%">
					<div>
						<a href="<%= mobCosUrl %>stockinvccsmaintsearch.html?SSO_ET=<sb-util:ssoticket/>" class="nextbutton">Stock Item Search</a>
					</div>
				</td>
				
				<td align="center" width="20%">
					<div>
						<a href="mobccsstockmanual.html" class="nextbutton">Manual Assign</a>
					</div>
				</td>
				<td align="center" width="20%">
					<div>
						<a href="mobccsstockquantityenquiry.html" class="nextbutton">
							Quantity Enquiry
						</a>
					</div>
				</td>
				<td align="center" width="20%">
					<div>
						<a href="#" class="nextbutton" onClick="javascript:formSubmit('INSTOCK');">In Stock</a>
					</div>
				</td>
			</tr>
		</table>
	<input type="hidden" name="actionType" id="actionType" />

</form:form>


<!---------------------------------------  end content ------------------------------------------->
<%@ include file="/WEB-INF/jsp/footer.jsp"%>