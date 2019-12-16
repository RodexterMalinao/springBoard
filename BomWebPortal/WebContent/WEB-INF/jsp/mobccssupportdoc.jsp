<%@ include file="/WEB-INF/jsp/header.jsp"%>

<link type="text/css" href="css/custom-theme/jquery-ui-1.8.9.custom.css"
	rel="stylesheet" />

<jsp:include page="/WEB-INF/jsp/mobprogressbar.jsp">
	<jsp:param name="currentPageName" value="mobccssupportdoc" />
</jsp:include>
<%@ include file="loadingpanel.jsp" %>
<script type="text/javascript">
	function authorized() {
		$(".auth_button").hide();
		$('input[name=authorized]').val(true);
		$('input[id="mobCcsSupportDocDTOList[0].mandatory1"]').show();
		$("#dummy01").remove();
				//.attr("disabled", false);
	}

	$(document).ready(function(e) {
		//$("input[name$=mandatory]").filter("input[type=checkbox]")
		//		.attr("disabled", true);
		
		var $dummy = $('input[id="mobCcsSupportDocDTOList[0].mandatory1"]').clone();
		$dummy.attr("id", "dummy01").attr("name", "").attr("disabled", true);
		$('input[id="mobCcsSupportDocDTOList[0].mandatory1"]').after($dummy);
		$dummy.show();
		
		$(".disabledCheckbox").each(function() {
			var $clone = $(this).clone().attr({"id": "", "name": "", "disabled": true}).unbind("click");
			$(this).after($clone);
			$clone.show();
		});
		/* if($('input[name="authorized"]').val() == "true"){
			$(".auth_button").hide();							
		}else{
			$(".auth_button").show();							
		} */
		$(".auth_button").click(function(e) {
			e.preventDefault();
			// http://msdn.microsoft.com/en-us/library/ie/ms536759%28v=vs.85%29.aspx
			var sURL = "mobccsauthorize.html?" + $.param({ "action" : "AU03", "orderId" : "<c:out value="${orderDTO.orderId}" />" });
			var vArguments = self;
			var sFeatures = "dialogHeight:230px;dialogLeft:0;dialogTop:0;dialogWidth:400px;resizable=yes;scroll=yes;status=no";
			window.showModalDialog(sURL, vArguments, sFeatures);
			return false;
		});
		
		$("[id$=faxServerSerialNo]").change(function(e) {
			$(this).parent().parent().find("td:eq(3)").text("Pending");
		});
		
		//ID Document Copy mandatory
		$('input[id="mobCcsSupportDocDTOList[0].mandatory1"]').click(function(e) {
					
			$(this).parent().parent().find("input[name$=receivedByFax]").filter("input[type=checkbox]").attr("disabled", !$(this).is(':checked'));
			
			/* $('input[name="idDocCopyWaived"]').val(!$(this).attr("checked")); */
				
			
			//Requried
			if ($(this).is(':checked')){
				$(this).parent().parent().find("td:eq(3)").text("Pending");
				$('input[id="mobCcsSupportDocDTOList[0].removable1"]').attr("checked", false);
			//Not equired
			}else{
				$('input[name="mobCcsSupportDocDTOList[0].faxServerSerialNo"]').attr("disabled", true);
				$(this).parent().parent().find("td:eq(3)").text("N/A");
				
				if($('input[name="mobCcsSupportDocDTOList[0].recalled"]').val() == false){
					$('input[name="mobCcsSupportDocDTOList[0].receivedByFax"]').attr("checked", false);
					$(this).parent().parent().find("input[name$='faxServerSerialNo']").val(null);
				}else{
					$('input[id="mobCcsSupportDocDTOList[0].removable1"]').attr("checked", true);									
				}																
													
			}								

		});
		
		$("[name$=receivedByFax]").filter("input[type=checkbox]").click(function(e) {
			if ($(this).is(":visible")) {
				$(this).parent().parent().find("input[name$=faxServerSerialNo]").attr("disabled", !$(this).is(':checked'));
				if (!$(this).is(':checked'))$(this).parent().parent().find("input[name$='faxServerSerialNo']").val(null);
			}
		});

		$("form[name=mobCcsSupportDocForm]").submit(function(e) {
			$("input[name$=receivedByFax]:checked").filter("input[type=checkbox]").each(function() {
				var $tr = $(this).parent().parent();
				var faxServerSerialNo = $tr.find("input[name$=faxServerSerialNo]").val();
				var verified = $tr.find(".verifyResult").text();
				if ($.trim(faxServerSerialNo).length == 0 && $.trim(verified) != "Pass") {
					alert("One of the support document needs to received by fax but w/o fax serial input, please aware");
					return false;
				}
			});
		});
		
		$(".continueButton").click(function(e) {
			e.preventDefault();
			$("form[name=mobCcsSupportDocForm]").submit();
			return false;
		});
	});
</script>

<style type="text/css">
.supportDocsForm { background-color: white; border: 2px solid #BEBEBE; padding: 1px }
.supportDocsForm h2 { font-size: 16px; padding: 8px 10px; margin: 0 }
.supportDocsForm .note { font-size: 11px; color: red; font-weight: bold; padding: 0 10px }
.supportDocsDetail { width: 100%; border: 0; border-collapse:collapse }
.supportDocsDetail .table_title { font-size: 13px }
.supportDocsDetail tbody td { padding: 7px 0 }
.disabledCheckbox { display: none }
</style>

<form:form name="mobCcsSupportDocForm" method="POST" commandName="mobCcsSupportDoc">
<div class="supportDocsForm">
<h2 class="table_title">
	Supporting Documents
	<c:if test="true"><a href="#" class="nextbutton3 auth_button">Waive Id Doc Copy Authorize</a></c:if>
</h2>

<div class="note">Note: * May not be accurate when it's Draft or Pre Order</div>

<table class="contenttext supportDocsDetail">
<thead>
	<tr class="contenttextgreen">
		<td class="table_title" style="text-align: left; white-space: nowrap">
			<spring:message code="label.mobccssupportdoc.header"/>
			<br><spring:message code="label.mobccssupportdoc.header.1"/>
		</td>
		<td class="table_title" style="text-align: left; white-space: nowrap">
			<spring:message code="label.mobccssupportdoc.receivedByFax"/>
			<br><spring:message code="label.mobccssupportdoc.receivedByFax.1"/>
		</td>
		<td class="table_title" style="text-align: left; white-space: nowrap">
			<spring:message code="label.mobccssupportdoc.faxServerSerialNo"/>
			<br><spring:message code="label.mobccssupportdoc.faxServerSerialNo.1"/>
		</td>
		<td class="table_title" style="text-align: left; white-space: nowrap">
			<spring:message code="label.mobccssupportdoc.verifyResult"/>
			<br><spring:message code="label.mobccssupportdoc.verifyResult.1"/>
		</td>
		<td class="table_title" style="text-align: left; white-space: nowrap">
			<spring:message code="label.mobccssupportdoc.removable"/>
			<br><spring:message code="label.mobccssupportdoc.removable.1"/>
		</td>
	</tr>							
</thead>
<tbody>
	<c:forEach items="${mobCcsSupportDoc.mobCcsSupportDocDTOList}" var="sdList" varStatus="sdRow">
		<!-- Row 2.2 Data Output -->
		<tr>
			<!-- Column 1 Doc Description -->
			<td>
				<c:choose>
					<c:when test="${sdList.docId == '01'}">
						<form:checkbox path="mobCcsSupportDocDTOList[${sdRow.index}].mandatory" cssStyle="display:none" label="${sdList.docDesc}"/>
					</c:when>
					<c:otherwise>
						<form:checkbox path="mobCcsSupportDocDTOList[${sdRow.index}].mandatory" disabled="true" label="${sdList.docDesc}"/>
					</c:otherwise>
				</c:choose>
			<form:hidden path="mobCcsSupportDocDTOList[${sdRow.index}].recalled" />
			</td>
			<!-- Column 2 Received by Fax -->
			<c:choose>
				<c:when test='${(sdList.verified == "N" || empty sdList.verified) && sdList.mandatory == true}'>
					<td>
					<c:choose>
						<c:when test='${sdList.faxMandatory == true && sdList.onsiteMandatory == false}'>
							<form:checkbox path="mobCcsSupportDocDTOList[${sdRow.index}].receivedByFax" label="Yes" cssClass="disabledCheckbox"/>
						</c:when>
						<c:when test='${sdList.onsiteMandatory == true && sdList.faxMandatory == false}'>
							<form:checkbox path="mobCcsSupportDocDTOList[${sdRow.index}].receivedByFax" label="Yes" cssClass="disabledCheckbox"/>
						</c:when>
						<c:otherwise>
							<form:checkbox path="mobCcsSupportDocDTOList[${sdRow.index}].receivedByFax" label="Yes"/>
						</c:otherwise>
					</c:choose>
					</td>
					<!-- Column 3a Fax Server Serial No -->
					<td>
					<c:choose>
						<c:when test='${sdList.receivedByFax == true}'>
							<form:input path="mobCcsSupportDocDTOList[${sdRow.index}].faxServerSerialNo" maxlength="20" />
						</c:when>
						<c:otherwise>
							<form:input path="mobCcsSupportDocDTOList[${sdRow.index}].faxServerSerialNo" maxlength="20" disabled="true" />
						</c:otherwise>
					</c:choose>
					</td>
				</c:when>
				<c:otherwise>
					<!-- Column 2b Received by Fax -->
					<td>
						<form:checkbox path="mobCcsSupportDocDTOList[${sdRow.index}].receivedByFax" label="Yes" cssClass="disabledCheckbox"/>
					</td>
					<!-- Column 3b Fax Server Serial No -->
					<td>
						<form:input path="mobCcsSupportDocDTOList[${sdRow.index}].faxServerSerialNo" disabled="true" maxlength="20" />
					</td>
				</c:otherwise>
			</c:choose>
			<td class="verifyResult"><c:out value="${sdList.verifyResult}">Pending</c:out></td>
			<td>
				<form:checkbox path="mobCcsSupportDocDTOList[${sdRow.index}].removable" disabled="true" label="Yes"/>
			</td>
		</tr>
	</c:forEach>
</tbody>
</table>

<form:hidden path="authorized" />
<input type="hidden" name="currentView" value="mobccssupportdoc" />

<div class="buttonmenubox_R" id="buttonArea">
	<a href="#" class="nextbutton3 continueButton">continue &gt;</a>
</div>

</div>	
</form:form>
