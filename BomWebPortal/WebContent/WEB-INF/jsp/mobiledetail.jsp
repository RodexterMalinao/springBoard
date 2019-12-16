<%@ include file="/WEB-INF/jsp/header.jsp"%>
<%@ include file="loadingpanel.jsp" %>

<script type="text/javascript" src="js/jquery-1.4.4.js"></script>
<script type="text/javascript" src="js/jquery-ui-1.8.10.custom.min.js"></script>
<script type="text/javascript">
	$(function() {

		// Tabs
		$('#tabs').tabs();

		//hover states on the static widgets
		$('#dialog_link, ul#icons li').hover(function() {
			$(this).addClass('ui-state-hover');
		}, function() {
			$(this).removeClass('ui-state-hover');
		});

	});
	
	function compareBasket(selectedBasketId){
		var sURL = "mobccsbasketcomparedetail.html?basketIds="+selectedBasketId;
		var vArguments = new Object();
		var sFeatures = "dialogHeight:768;dialogLeft:0;dialogTop:0;dialogWidth:1024;resizable=yes;scroll=yes;status=no";
		window.showModalDialog(sURL, vArguments, sFeatures);
		
		
	}
</script>

<style type="text/css">
/*demo page css*/
/*body{ font: 62.5% "Trebuchet MS", sans-serif; margin: 50px;}*/
.demoHeaders {
	margin-top: 2em;
}

#dialog_link {
	padding: .4em 1em .4em 20px;
	text-decoration: none;
	position: relative;
}

#dialog_link span.ui-icon {
	margin: 0 5px 0 0;
	position: absolute;
	left: .2em;
	top: 50%;
	margin-top: -8px;
}

ul#icons {
	margin: 0;
	padding: 0;
}

ul#icons li {
	margin: 2px;
	position: relative;
	padding: 4px 0;
	cursor: pointer;
	float: left;
	list-style: none;
}

ul#icons span.ui-icon {
	float: left;
	margin: 0 4px;
}
</style>
<jsp:include page="/WEB-INF/jsp/mobprogressbar.jsp">
	<jsp:param name="currentPageName" value="offerdetail" />
	<jsp:param name="sbuid" value= "${param.sbuid}" />
</jsp:include>

<!-- start content -->
<form name="handset" method="Post" action="mobiledetail.html?sbuid=${param.sbuid}">
	<table width="100%" border="0">
		<tr>
			<!-- select menu delete -->


			<!-- end select menu delete -->

			<td><c:if test='${mobileDetail.modelName != null}'>
					<!--start image & info -->
					<!-- ${mobileDetail.modelName}<br>  -->
					<!-- <div class="imgbox"> -->
					<table width="100%" border="1px" BORDERCOLOR="#7c5db0">
						<tr>
							<td>
								<table width="100%" border="0" align="left" cellspacing="0">
									<tr>
										<td rowspan="3" width="150"><img class="imgleft"
											src="${mobileDetail.modelImagePath}" width="100"
											alt="${mobileDetail.modelName}" /> <br>
										</td>
										<td>${mobileDetail.modelHtml}</td>
									</tr>
									<tr>
										<td><br />
											<p class="contenttext">
												<c:forEach items="${mobileDetail.colorDto}" var="color">
													<input type="radio" name="color" onclick="onColorClick()"
														value="${color.colorId}"
														<c:if test='${color.colorId == selectedColorId}'>checked</c:if>>${color.colorName}<br>
												</c:forEach>

											</p>
										</td>
										<tr>
										<td  class="smalltextgray"><strong>Note: NS price with application date</strong>
									</td>
									</tr>
									</tr>
								</table>
							</td>
						</tr>
					</table>

					<!-- end image & info -->
				</c:if>
			</td>
		</tr>

		<tr>
			<td width="100%" align="left">
				<!-- Tabs -->

				<div id="tabs">
					<ul>
					
						<li><a href="#tabs-1"><span class="contenttext">Hot	Offer</span> </a></li>
						<li><a href="#tabs-2"><span class="contenttext">Dealer Offer</span> </a></li>
						<li><a href="#tabs-3"><span class="contenttext">Targeted Customer</span> </a></li>
						<li><a href="#tabs-4"><span class="contenttext">SIM + STANDALONE</span> </a></li>
						
					</ul>
					<div id="tabs-1">
						<c:forEach items="${basketList}" var="basket">

							<c:if
								test='${(basket.displayTab == null)||(basket.displayTab == "1")}'>
								<div class="imgcaption2">
									<input type="hidden" name="basketInfo"
										value="${basket.basketId}/${basket.belowQuotaInd}/${basket.publicHouseBaksetInd}/${phInd}" />

									<font color="black">
									<a href="#"  onClick='compareBasket(${basket.basketId})'>${basket.sortDesc} </a>
									</font> <br />
									${basket.basketHtml} <br>
									<c:if test="${param.showDetail == 'Y'}">
									<font color="black" font-weight="bold">Brand: ${basket.mipBrand} <br>
									SimType: ${basket.mipSimType} <br> </font>
									</c:if>

									<c:choose>
										<c:when
											test="${(basket.belowQuotaInd == 'Y') && ((basket.publicHouseBaksetInd =='Y' && phInd=='Y') || (basket.publicHouseBaksetInd =='N' || basket.publicHouseBaksetInd ==null ))}">
											<!-- <a	href='vasdetail.html?basket=${basket.basketId}&color=${selectedColorId}&model=${selectedModelId}&brand=${selectedBrandId}'>buy now&gt;</a> -->
												<a href='vasdetail.html?basket=${basket.basketId}&sbuid=${param.sbuid}' onClick="showLoading()">buy now&gt;</a>
										</c:when>
										<c:otherwise>
											<a href='#'>buy now&gt;</a>
											<c:if test='${(basket.belowQuotaInd =="N") }'>
												<font color="red">Quota Exceed</font>
											</c:if>
											<c:if
												test='${(basket.publicHouseBaksetInd =="Y") && (phInd=="N")  }'>
												<font color="red">PH unauthorized</font>
											</c:if>
										</c:otherwise>
									</c:choose>

								</div>

							</c:if>

						</c:forEach>
					</div>

					<div id="tabs-2">
						<c:forEach items="${basketList}" var="basket">
							<c:if
								test='${(basket.displayTab == "2")}'>
								<div class="imgcaption2">
									<input type="hidden" name="basketInfo"
										value="${basket.basketId}/${basket.belowQuotaInd}/${basket.publicHouseBaksetInd}/${phInd}" />

									<font color="black">
									<a href="#"  onClick='compareBasket(${basket.basketId})'>${basket.sortDesc} </a>
									</font> <br />
									${basket.basketHtml} <br>
									<c:if test="${param.showDetail == 'Y'}">
									<font color="black">Brand: ${basket.mipBrand} <br>
									SimType: ${basket.mipSimType} <br> </font>
									</c:if>

									<c:choose>
										<c:when
											test="${(basket.belowQuotaInd == 'Y') && ((basket.publicHouseBaksetInd =='Y' && phInd=='Y') || (basket.publicHouseBaksetInd =='N' || basket.publicHouseBaksetInd ==null ))}">
											<!-- <a	href='vasdetail.html?basket=${basket.basketId}&color=${selectedColorId}&model=${selectedModelId}&brand=${selectedBrandId}'>buy now&gt;</a> -->
												<a href='vasdetail.html?basket=${basket.basketId}&sbuid=${param.sbuid}'>buy now&gt;</a>
										</c:when>
										<c:otherwise>
											<a href='#'>buy now&gt;</a>
											<c:if test='${(basket.belowQuotaInd =="N") }'>
												<font color="red">Quota Exceed</font>
											</c:if>
											<c:if
												test='${(basket.publicHouseBaksetInd =="Y") && (phInd=="N")  }'>
												<font color="red">PH unauthorized</font>
											</c:if>
										</c:otherwise>
									</c:choose>

								</div>

							</c:if>
						</c:forEach>
					</div>


					<div id="tabs-3">
						<c:forEach items="${basketList}" var="basket">
							<c:if
								test='${(basket.displayTab == "3")}'>
								<div class="imgcaption2">
									<input type="hidden" name="basketInfo"
										value="${basket.basketId}/${basket.belowQuotaInd}/${basket.publicHouseBaksetInd}/${phInd}" />

									<font color="black">
									<a href="#"  onClick='compareBasket(${basket.basketId})'>${basket.sortDesc} </a>
									</font> <br />
									${basket.basketHtml} <br>
									<c:if test="${param.showDetail == 'Y'}">
									<font color="black">Brand: ${basket.mipBrand} <br>
									SimType: ${basket.mipSimType} <br> </font>
									</c:if>

									<c:choose>
										<c:when
											test="${(basket.belowQuotaInd == 'Y') && ((basket.publicHouseBaksetInd =='Y' && phInd=='Y') || (basket.publicHouseBaksetInd =='N' || basket.publicHouseBaksetInd ==null ))}">
											<!-- <a	href='vasdetail.html?basket=${basket.basketId}&color=${selectedColorId}&model=${selectedModelId}&brand=${selectedBrandId}'>buy now&gt;</a> -->
												<a href='vasdetail.html?basket=${basket.basketId}&sbuid=${param.sbuid}'>buy now&gt;</a>
										</c:when>
										<c:otherwise>
											<a href='#'>buy now&gt;</a>
											<c:if test='${(basket.belowQuotaInd =="N") }'>
												<font color="red">Quota Exceed</font>
											</c:if>
											<c:if
												test='${(basket.publicHouseBaksetInd =="Y") && (phInd=="N")  }'>
												<font color="red">PH unauthorized</font>
											</c:if>
										</c:otherwise>
									</c:choose>

								</div>

							</c:if>
						</c:forEach>
					</div>
					
					
					<div id="tabs-4">
						<c:forEach items="${basketList}" var="basket">
							<c:if
								test='${(basket.displayTab == "4")}'>
								<div class="imgcaption2">
									<input type="hidden" name="basketInfo"
										value="${basket.basketId}/${basket.belowQuotaInd}/${basket.publicHouseBaksetInd}/${phInd}" />

									<font color="black">
									<a href="#"  onClick='compareBasket(${basket.basketId})'>${basket.sortDesc} </a>
									</font> <br />
									${basket.basketHtml} <br>
									<c:if test="${param.showDetail == 'Y'}">
									<font color="black">Brand: ${basket.mipBrand} <br>
									SimType: ${basket.mipSimType} <br> </font>
									</c:if>

									<c:choose>
										<c:when
											test="${(basket.belowQuotaInd == 'Y') && ((basket.publicHouseBaksetInd =='Y' && phInd=='Y') || (basket.publicHouseBaksetInd =='N' || basket.publicHouseBaksetInd ==null ))}">
											<!-- <a	href='vasdetail.html?basket=${basket.basketId}&color=${selectedColorId}&model=${selectedModelId}&brand=${selectedBrandId}'>buy now&gt;</a> -->
												<a href='vasdetail.html?basket=${basket.basketId}&sbuid=${param.sbuid}'>buy now&gt;</a>
										</c:when>
										<c:otherwise>
											<a href='#'>buy now&gt;</a>
											<c:if test='${(basket.belowQuotaInd =="N") }'>
												<font color="red">Quota Exceed</font>
											</c:if>
											<c:if
												test='${(basket.publicHouseBaksetInd =="Y") && (phInd=="N")  }'>
												<font color="red">PH unauthorized</font>
											</c:if>
										</c:otherwise>
									</c:choose>

								</div>

							</c:if>
						</c:forEach>
					</div>

				</div> <!-- end start 5 box -->
			</td>
		</tr>

	</table>
	<input type="hidden" name="currentView" value="mobiledetail" /> <input
		type="hidden" name="customertier" value="${customerTier}" /> <input
		type="hidden" name="callList" value="${callList}" /> <input
		type="hidden" name="baskettype" value="${basketType}" /> <input
		type="hidden" name="rptype" value="${rpType}" /> <input type="hidden"
		name="brand" value="${selectedBrandId}" /> <input type="hidden"
		name="model" value="${selectedModelId}" /> <input type="hidden"
		name="color" value="${selectedColorId}" />
</form>
<script type="text/javascript">
	function onColorClick() {

		//alert('submit form');
		document.handset.submit();
	}
</script>
<!-- end content -->

<%@ include file="/WEB-INF/jsp/footer.jsp"%>
