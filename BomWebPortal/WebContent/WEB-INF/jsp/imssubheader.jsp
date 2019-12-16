<script>

function getCurrent(){
	// check URL has target words
	if(document.getElementById('imsdsaddressinfo').value>0)return 1;
	if(document.getElementById('imsaddressinfo').value>0)return 1;
	if(document.getElementById('imsbasketselect').value>0)return 2;
	if(document.getElementById('imsbasketdetails').value>0)return 3;
	if(document.getElementById('imsnowtv').value>0)return 4;
	if(document.getElementById('imsinstallation').value>0)return 5;
	if(document.getElementById('imspayment').value>0)return 6;
	if(document.getElementById('imsamendpayment').value>0)return 6;
	if(document.getElementById('imsappointment').value>0)return 7;
	if(document.getElementById('imssummary').value>0)return 8;
	if(document.getElementById('imsdone').value>0)return 9;
	return -1;
}

 
function genInnerHtml(index,innerText,actionLink){
	var current = getCurrent();
	if(index == 1 && "${ImsOrder.src}"=="CO" && "${ImsOrder.orderStatus}"=="04") //disable address page  
		return '<div><a><span>'+index+'</span>'+innerText+'</a></div>';
	else if(current==index)//current page 
		return '<div class=\'active-step\'>'+ '<a onclick=\'showLoading();\' href=\''+actionLink+'\'><span>'+index+'</span>'+innerText+'</a></div>';
	else if(9==current) //done page
		return '<div class=\'completed-step\'>'+ '<a onclick=\';\' ><span>'+index+'</span>'+innerText+'</a></div>';
	else if(4==index && current>index && document.getElementById('CanBuyNowTv').value=="no") //nowTV
		return '<div class=\'completed-step\'>'+ '<a onclick=\';\' ><span>'+index+'</span>'+innerText+'</a></div>';
	else if(5==index && ("${ImsOrder.orderStatus}"=="02" || "${ImsOrder.orderStatus}"=="04"))
		return '<div><a><span>'+index+'</span>'+innerText+'</a></div>'; 
	else if(current>index)//completed
		return '<div class=\'completed-step\'>'+ '<a onclick=\'showLoading();\' href=\''+actionLink+'\'><span>'+index+'</span>'+innerText+'</a></div>';
	else //future
		return '<div><a><span>'+index+'</span>'+innerText+'</a></div>';
}

function postload(){
	
	$(document).ready(function(){
	    $('form').attr('autocomplete', 'off');
	});
	
	if ("${ImsOrder.orderStatus}"=="02" || "${ImsOrder.orderStatus}"=="04")
		document.getElementById('imsaddressinfoui').innerHTML = genInnerHtml(1,'<spring:message code="ims.pcdheader.001"/>','imsdsaddressinfo.html');
	else document.getElementById('imsaddressinfoui').innerHTML = genInnerHtml(1,'<spring:message code="ims.pcdheader.001"/>','imsaddressinfo.html');
	document.getElementById('imsbasketselectui').innerHTML = genInnerHtml(2,'<spring:message code="ims.pcdheader.002"/>','imsbasketselect.html');
	document.getElementById('imsbasketdetailsionui').innerHTML =  genInnerHtml(3,'<spring:message code="ims.pcdheader.003"/>','imsbasketdetails.html');
	document.getElementById('imsnowtvui').innerHTML = genInnerHtml(4,'<spring:message code="ims.pcdheader.004"/>','imsnowtv.html');	
	document.getElementById('imsinstallationui').innerHTML = genInnerHtml(5,'<spring:message code="ims.pcdheader.005"/>','imsinstallation.html');
	document.getElementById('imspaymentui').innerHTML = genInnerHtml(6,'<spring:message code="ims.pcdheader.006"/>','imspayment.html');
	document.getElementById('imsappointmentui').innerHTML = genInnerHtml(7,'<spring:message code="ims.pcdheader.007"/>','imsappointment.html');
	document.getElementById('imssummaryui').innerHTML = genInnerHtml(8,'<spring:message code="ims.pcdheader.008"/>','imssummary.html');
	document.getElementById('imsdoneui').innerHTML =  genInnerHtml(9,'<spring:message code="ims.pcdheader.009"/>','imsdone.html');
}

</script>

<input type="hidden" value="<%= request.getRequestURL().indexOf("imsdsaddressinfo")%>" id="imsdsaddressinfo" />
<input type="hidden" value="<%= request.getRequestURL().indexOf("imsaddressinfo")%>" id="imsaddressinfo" />
<input type="hidden" value="<%= request.getRequestURL().indexOf("imsbasketselect")%>" id="imsbasketselect" />
<input type="hidden" value="<%= request.getRequestURL().indexOf("imsbasketdetails")%>" id=imsbasketdetails />
<input type="hidden" value="<%= request.getRequestURL().indexOf("imsnowtv")%>" id="imsnowtv" />
<input type="hidden" value="<%= request.getRequestURL().indexOf("imsinstallation")%>" id="imsinstallation" />
<input type="hidden" value="<%= request.getRequestURL().indexOf("imspayment")%>" id="imspayment" />
<input type="hidden" value="<%= request.getRequestURL().indexOf("imsamendpayment")%>" id="imsamendpayment" />
<input type="hidden" value="<%= request.getRequestURL().indexOf("imsappointment")%>" id="imsappointment" />
<input type="hidden" value="<%= request.getRequestURL().indexOf("imssummary")%>" id="imssummary" />
<input type="hidden" value="<%= request.getRequestURL().indexOf("imsdone")%>" id="imsdone" />
 
<c:choose>
<c:when test='${ImsOrder.nowTvFormType == null}'>
<input type="hidden" value="no" id="CanBuyNowTv" />
</c:when>
<c:when test='${ImsOrder.nowTvFormType == ""}'>
<input type="hidden" value="no" id="CanBuyNowTv" />
</c:when>
<c:otherwise>
<input type="hidden" value="yes" id="CanBuyNowTv" />
</c:otherwise>
</c:choose> 
	
			<table width="100%">
				<tr>
					<td width="1%"></td>
					<td width="99%">
							
					<div class="wizard-steps">
							<div id="imsaddressinfoui"></div>
							<div id="imsbasketselectui"></div>
							<div id="imsbasketdetailsionui"></div> 
							<div id="imsnowtvui"></div>
							<div id="imsinstallationui"></div>
							<div id="imspaymentui"></div>
							<div id="imsappointmentui"></div>
							<div id="imssummaryui"></div>
							<div id="imsdoneui"></div> 
  						</div>		
  					</td>
				</tr>
					
			</table>
 			
 			<script>
 			postload();
 			</script>
