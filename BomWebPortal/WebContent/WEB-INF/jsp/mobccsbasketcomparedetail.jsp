<%@ include file="/WEB-INF/jsp/header.jsp"%>
<style type="text/css">
.recordRow { background-color: white; overflow: auto; width: 100% }
.recordCol { float: left; overflow: hidden }
.recordCol1 { width: 99% }
.recordCol2 { width: 49% }
.recordCol3 { width: 33% }
.mobileDetail { }
.basketDisplayTitle { background-color: #E8FFE8 }
.vasHSRPList { }

.itemInfo { float: left; width: 90%; margin-left: 10px }
.itemAmount { float: right; width: 100px; text-align: right }
</style>

<script type="text/javascript">
$(document).ready(function() {
	$(".recordCol").addClass("recordCol" + $(".recordRow:first > .recordCol").length);
	
	// hide right top link
	$("#header table:first td:gt(0)").remove();
	$("#header table:last").remove();
	
	// add close button
	$("#closeButton").click(function(e) {
		window.close();
	});
});
</script>

<!-- basket  -->
<div class="recordRow mobileDetail">
<c:forEach items="${records}" var="record">
	<c:set var="basket" value="${record.value.basket}" />
	<div class="recordCol">
			<table class="BGgreen2" align="left">
		
			<tr><td align="left">Basket ID:</td><td align="left">${basket.basketId}</td></tr>
				<tr><td align="left">Customer Tier Desc:</td><td align="left">${basket.	customerTierDesc}</td></tr>
				<tr><td align="left">Recurrent Amt:</td><td align="left">${basket.recurrentAmt}</td></tr>
				<tr><td align="left">Contract Period:</td><td align="left">${basket.contractPeriod}</td></tr>
				<tr><td align="left">Upfront Amt:</td><td align="left">${basket.upfrontAmt}</td></tr>
				<tr><td align="left">Basket OfferType Desc:</td><td align="left">${basket.basketOfferTypeDesc}</td></tr>
				<tr><td align="left">Rate Plan TypeDesc:</td><td align="left">${basket.rpTypeDesc}</td></tr>
				<tr><td align="left">Rate Plan Desc:</td><td align="left">${basket.	ratePlanDesc}</td></tr>
				<tr><td align="left">Brand Desc:</td><td align="left">${basket.brandDesc}</td></tr>
				<tr><td align="left">Model Desc:</td><td align="left">${basket.modelDesc}</td></tr>
				<tr><td align="left">Color Desc:</td><td align="left">${basket.colorDesc}</td></tr>
				<tr><td align="left">Offer Type Desc:</td><td align="left">${basket.offerTypeDesc}</td></tr>
				<tr><td align="left">Sim Type Desc:</td><td align="left">${basket.simTypeDesc	}</td></tr>
				<tr><td align="left">Basket Type Desc:</td><td align="left">${basket.basketTypeDesc}</td></tr>

			</table>

	&nbsp;
	</div>
</c:forEach>
</div>

<!-- mobileDetail -->
<div class="recordRow mobileDetail">
<c:forEach items="${records}" var="record">
	<c:set var="mobileDetail" value="${record.value.mobileDetail}" />
	<div class="recordCol">
	<c:if test="${not empty mobileDetail.modelImagePath}">
	<img src="${mobileDetail.modelImagePath}" width="150">
	</c:if>
	&nbsp;
	</div>
</c:forEach>
</div>
<div style="clear:both"></div>

<!-- basketDisplayTitle -->
<div class="recordRow basketDisplayTitle">
<c:forEach items="${records}" var="record">
	<c:set var="basketDisplayTitle" value="${record.value.basketDisplayTitle}" />
	<div class="recordCol">
	<span class="basket_title">${record.key}</span>
	<div style="clear:both"></div>
	${basketDisplayTitle}
	&nbsp;
	</div>
</c:forEach>
</div>
<div style="clear:both"></div>

<!-- vasHSRPList -->
<div class="recordRow vasHSRPList">
<c:forEach items="${records}" var="record">
	<div class="recordCol">
	<c:set var="vasHSRPList" value="${record.value.vasHSRPList}" />
	<c:forEach items="${vasHSRPList}" var="vas">
	<div>
		
		<div class="itemInfo">
		<c:if test="${vas.itemRecurrentAmt != 0}">
		<div class="itemAmount">
			<fmt:formatNumber value="${vas.itemRecurrentAmt}" pattern="$#,###.####/month" />
		</div>
		</c:if>
		${vas.itemHtml}
		</div>
		
	</div>
	<div style="clear:both"></div>
	</c:forEach>
	&nbsp;
	</div>
</c:forEach>
</div>
<div style="clear:both"></div>


<div style="clear:both"></div>

<div style="float:right;padding:10px 0">
<input type="button" id="closeButton" value="Close">
</div>
<%@ include file="/WEB-INF/jsp/footer.jsp"%>