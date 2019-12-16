<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<script type="text/javascript">

$("#request").click(function(){
	
	
	
	if (<%=request.getAttribute("twoRequestsAlready")%>) {
		
		
		//alert("two requests already");
		//$("<font color='#FF0000'>Two requests have been made already!</font>").appendTo($("#warningMsg"));
		
		$("#warningMsg").html("<font color='#FF0000'>Two requests have been made already!</font>");
		
		return false;
		
	};
	
	
	
});

function toSpecialMRTRequest(requestId){
	
	location.href = "./mobccsspecialmrtrequest.html?" + $.param({
		
		requestId:requestId,
		from:'rsv'
		
	});
	

}


</script>

<form:form name="specialMrtReserveForm" method="POST" commandName="specialMRTReserve" action="/BomWebPortal/mobccsspecialmrtreserve.html">


<div id="warningMsg"></div>


<!-- Reserve Special MRT -->
<table width="100%" class="tablegrey">
	<tr>
		<td bgcolor="#FFFFFF" width="100%" colspan="4">
		<table width="100%" border="0" cellspacing="1" rules="none">
			<tr>
				<td class="table_title">Reserve Special MRT</td>
			</tr>	
		</table>
		<c:choose>
			<c:when test = "${empty reserveSpecialMRTList}" >
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
			</c:when>
		
		<c:otherwise>
		<table width="100%" class="contenttext">
							<tr>
								<td>
								<div style="overflow:scroll; height:230px;">
								<table width="100%" border="1" cellspacing="0" class="contenttext"
									bgcolor="#FFFFFF">
		
									<tr class="contenttextgreen">
		
										<td class="table_subtitle_blue" width="15%">Request Number</td>
										<td class="table_subtitle_blue" width="15%">Request Date</td>
										<td class="table_subtitle_blue" width="12.5%">Customer Name</td>
										<td class="table_subtitle_blue" width="12.5%">MRT Pattern</td>
										<td class="table_subtitle_blue" width="12.5%">Result Status</td>
										<td class="table_subtitle_blue" width="10%">Assigned MRT</td>
										<td class="table_subtitle_blue" width="7.5%">MRT Grade</td>
										<td class="table_subtitle_blue" width="15%">Valid Date till</td>
		
										<c:forEach items="${reserveSpecialMRTList}" var="rSMRT" >
											<tr>
												<td width="15%">
													<a href="javascript:toSpecialMRTRequest('${fn:toUpperCase(rSMRT.requestId)}')" >
													&nbsp;${fn:toUpperCase(rSMRT.requestId)}
													</a>
												</td>
												<!--  
												<td><fmt:formatDate pattern="dd/MM/yyyy HH:mm:ss" value="${rSMRT.requestDate}"/></td> -->
												<td width="15%">
													&nbsp;${fn:toUpperCase(rSMRT.requestDate)}
												</td>
												
												<td width="12.5%">
													&nbsp;${fn:toUpperCase(rSMRT.lastName)} ${fn:toUpperCase(rSMRT.firstName)}
												</td>
												
												<td width="12.5%">
													&nbsp;${fn:toUpperCase(rSMRT.msisdnPattern)}
												</td>
												
												<td width="12.5%">
													&nbsp;${fn:toUpperCase(rSMRT.approvalResult)}
												</td>
												<td width="10%">
													&nbsp;${fn:toUpperCase(rSMRT.msisdn)}
												</td>
												<td width="7.5%">
													&nbsp;${fn:toUpperCase(rSMRT.msisdnlvl)}
												</td>
												<td width="15%">
													&nbsp;${fn:toUpperCase(rSMRT.validDateTill)}
												</td>
												
											</tr>
										</c:forEach>
									</tr>
								</table>
								</div>
								</td>
							</tr>
			</table>
		</c:otherwise>
		
		</c:choose>
		
		<table width="100%" border="0" cellspacing="0">
			<tr height="25">
				
				<td width="100%" align="right">
				<div><a href="<c:url value='/mobccsspecialmrtrequest.html'><c:param name="requestId" value=""/><c:param name="from" value="rsv"/></c:url>" class="nextbutton" id="request" >Request</a></div>
				</td>
					
			</tr>
		</table>		
		
		
		
		</td>
	</tr>

</table>
</form:form>

