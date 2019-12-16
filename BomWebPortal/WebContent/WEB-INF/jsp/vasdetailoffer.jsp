<%@ include file="/WEB-INF/jsp/header.jsp" %>




<script language="Javascript">
function OnClickContinue(){
		document.handset.submit();
	}

function formSubmit(){

	document.vasDetailForm.submit();
}
</script> 

<form:form method="POST" name="vasDetailForm" commandName="vasDetail">

<!-- 
${pageMessage}
 -->	
<!-- start   -->
<form:errors path="itemHtml" cssClass="error" />
	<table width="100%" border="1">
  	<tr>
			<c:if test='${mobileDetail.modelName != null}'> 
        <td rowspan="2" valign="top">
        <img src="${mobileDetail.modelImagePath}" width="150"> 
          
        </td>
        </c:if>
	  	<td>
	  	<table width="100%" border="1" bgcolor="#FFFFFF">
		  	<tr><td colspan="2">${basketDisplayTitle}</td></tr>
		  	<tr>
		  	<td width="20">&nbsp;</td>
		  	<td>
				<table width="100%" border="1">
					<c:forEach items="${vasHSRPList}" var="vas">			  
				
								  
					<tr>
						<td width="85%" valign="top">item id: ${vas.itemId}
						<BR>${vas.itemHtml}
						<BR>
									<table width="100%" border="1" cellpadding="0" cellspacing="0">
										<tr>
											<td>Offer Id</td>
											<td>Offer Type</td>
											<td>PCM Offer</td>
											<td>Product Id</td>
											<td>Product Type</td>
											<td>PCM Product</td>
											<td>Feature Display</td>
											<td>POS item Code</td>

										</tr>

										<c:forEach items="${vas.productList}" var="offer">
											<tr>
												<td>${offer.offerId}</td>
												<td>${offer.offerType}</td>
												<td>${offer.pcmOffer}</td>
												<td>${offer.prodId}</td>
												<td>${offer.prodType}</td>
												<td>${offer.pcmProduct}</td>
												<td>${offer.featureDisplay}</td>
												<td>${offer.posItemCode}</td>
								
											</tr>

										</c:forEach>

									</table>
									<hr>

									</td>
						
						<td width="15%" valign="top">
						
						 <c:if test='${vas.itemRecurrentAmt!=0  }'>
							<fmt:formatNumber value="${vas.itemRecurrentAmt}" pattern="$#,###.####/month" />
						 </c:if>
						</td>
					</tr>
				
					</c:forEach>		
				</table>
		  	</td>
		  	</tr>
		  	<tr><td colspan="2">
		  	
				<!-- some non display item offer list -->
				<table width="100%" border="1" cellpadding="0" cellspacing="0">
				<tr>
				<td>none display item</td>
				</tr>
										<tr>
										<td>Item Id</td>
											<td>Offer Id</td>
											<td>Offer Type</td>
											<td>PCM Offer</td>
											<td>Product Id</td>
											<td>Product Type</td>
											<td>PCM Product</td>
											<td>Feature Display</td>
											<td>POS item Code</td>

										</tr>

										<c:forEach items="${noneDisplayItem.productList}" var="offer">
											<tr>
											<td>${offer.itemId}</td>
												<td>${offer.offerId}</td>
												<td>${offer.offerType}</td>
												<td>${offer.pcmOffer}</td>
												<td>${offer.prodId}</td>
												<td>${offer.prodType}</td>
												<td>${offer.pcmProduct}</td>
												<td>${offer.featureDisplay}</td>
												<td>${offer.posItemCode}</td>
								
											</tr>

										</c:forEach>

									</table>


</td></tr>
        </table>
  <tr>
    <td>
	
    <table width="100%"  class="tablegrey" border="0">
      <tr > 
       <td  bgcolor="#FFFFFF" > 
        <!--content vas-->
         <div>      
		<table width="100%" border="1" cellspacing="0" class="tableGreytext" bgcolor="white">
		     <tr > 
                    <td colspan="3" class="table_title">Additional VAS<BR>
                    <form:errors path="vasitem" cssClass="error" />
                    </td>
             </tr>
            
             <tr >
             <td>
      
          <table width="100%" border="1" cellspacing="1">  
          	    <c:forEach items="${vasDetailList}" var="vas">  
			  		<tr>           	
			          	<td valign="top"  width="10%">
							<!-- c:if test='${vas.itemType == "VAS"}' -->
							
								<INPUT TYPE=CHECKBOX name="vasitem" value="${vas.itemId}" <c:if test='${vas.itemMdoInd == "D"}'>checked="yes"</c:if> ><BR>
							<!-- /c:if --> 
						</td>
						<td valign="top" width="75%">
						item id: ${vas.itemId}
						<BR>
							${vas.itemHtml}
							<BR>
							<table width="100%" border="1" cellpadding="0" cellspacing="0">
										<tr>
											<td>Offer Id</td>
											<td>Offer Type</td>
											<td>PCM Offer</td>
											<td>Product Id</td>
											<td>Product Type</td>
											<td>PCM Product</td>
											<td>Feature Display</td>
											<td>POS item Code</td>

										</tr>

										<c:forEach items="${vas.productList}" var="offer">
											<tr>
												<td>${offer.offerId}</td>
												<td>${offer.offerType}</td>
												<td>${offer.pcmOffer}</td>
												<td>${offer.prodId}</td>
												<td>${offer.prodType}</td>
												<td>${offer.pcmProduct}</td>
												<td>${offer.featureDisplay}</td>
												<td>${offer.posItemCode}</td>
								
											</tr>

										</c:forEach>

									</table>
							
						</td>
						<td align="right" valign="top" class="BGgreen2" width="15%">
						<!--  
							<c:if test='${vas.itemRecurrentAmt>0}'> 
							$${vas.itemRecurrentAmt}/month </c:if>
							-->
							
							<c:if test='${vas.itemRecurrentAmt!=0  }'>
							<fmt:formatNumber value="${vas.itemRecurrentAmt}" pattern="$#,###.####/month" />
						 </c:if>
						</td>
					</tr>             
				 </c:forEach>	
			</table>
		</td>
		</tr>
		</table>
			
			<input type="hidden" name="brand" value="${selectedBrandId}"/>
			<input type="hidden" name="model" value="${selectedModelId}"/>
			
			<input type="hidden" name="basket" value="${selectedBasketId}"/>
		</div> 

       <!-- end content vas -->
	
</td></tr></table></td>

  </tr>
</table>
<!-- end   -->
<!-- button -->
<table width="100%" border="0" cellspacing="0">
<tr>
	<td>
	<div class="buttonmenubox_R" id="buttonArea"> 
	
	<!-- <a href="#" class="nextbutton" onClick="javascript:formSubmit();">continue ></a>  -->
	  
	</div>
	</td>
</tr>
</table>
<!-- end of button -->
	<input type="hidden" name="currentView" value="vasdetail"/>
</form:form>

	

<!-- -------------------------------------  end content ----------------------------------------- -->


<%@ include file="/WEB-INF/jsp/footer.jsp" %>