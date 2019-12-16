<%@ include file="/WEB-INF/jsp/lts/common/ltsheader.jsp"%>
<script type="text/javascript">
	
	$(document).ready(function() {
		$("a#quitBtn").click(function(event) {
			event.preventDefault();
			window.opener=null;
			window.close();
		});
	});
	</script>
<table width="100%" border="0" height="480">
	<tr valign="middle" >
		<td align="center">
		<div style="width: 50%;">
			<div id="orderInfo" class="ui-widget" style="visibility: visible;" >
				<div class="ui-state-highlight ui-corner-all" style="margin-top: 20px; padding: 0 .7em;">
					<p>
						<span class="ui-icon ui-icon-info" style="float: left; margin-right: .3em;"></span>
					</p>
					<div id="info_msg" class="contenttext">
						<b><c:out value="${sessionScope.sessionLtsOrderInfoMsg}"/></b>
						<br><br>
						<c:choose>
							<c:when test="${not empty sessionScope.sessionLtsOrderInfoReutrnUrl}">
								<a href="${sessionScope.sessionLtsOrderInfoReutrnUrl}">Return</a>
								<c:remove var="sessionLtsOrderInfoReutrnUrl" />	
							</c:when>
							<c:otherwise>
								<div class="button">
									<a id="quitBtn" href="#">Quit</a>
								</div>	
							</c:otherwise>
						</c:choose>
					</div>
					<p></p>
				</div>
			</div>
		</div>
		</td>
	</tr>
</table>


<%@ include file="/WEB-INF/jsp/lts/common/ltsfooter.jsp"%>