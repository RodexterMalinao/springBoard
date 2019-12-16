<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%@ page
	import="com.bomwebportal.bean.LookupTableBean,java.util.*"%>

<%
	List<Integer> billPeriodList = LookupTableBean.getInstance()
			.getBillPeriodList();
	for (int i = 0; i < billPeriodList.size(); i++) {
		System.out.println("Bill Period: " + billPeriodList.get(i));
	}
%>

<c:choose>
	<c:when test="${fn:substring(param, 8, 14) == 'reload'}">
		<h3><font color="blue"> Reloaded Successfully! </font></h3>
	</c:when>
	<c:otherwise>
		<h3> Existing Constant Lookup Table In Memory </h3>
			 <font color="red"><b>Reload?</b></font>
		<a href="<c:url value='reloadconstanttablelkup.html'>
					<c:param name="action" value="reload" />
				 </c:url>">
			 Proceed
		</a>
		<br>
		<br>
	</c:otherwise>
</c:choose>

<b>Issue Bank:&nbsp;</b>
<c:choose>
	<c:when test="${issueBankListSize == -1}">
		<font color="red"><b> NULL </b></font><br>
	</c:when>
	<c:otherwise>
		(Size:&nbsp;${issueBankListSize})<br>
		<%--
		<c:forEach items="${issueBankList}" var="issue" varStatus="row">
			${row.count}:&nbsp;${issue.bankName}&nbsp;(${issue.bankCd})
			<br>
		</c:forEach>
		--%>
	</c:otherwise>
</c:choose>
<br>

<b>Bill Period:</b>
<c:choose>
	<c:when test="${billPeriodListSize == -1}">
		<font color="red"><b> NULL </b></font><br>
	</c:when>
	<c:otherwise>
		(Size:&nbsp;${billPeriodListSize})<br>
		<%--
		${billPeriodList}
		--%>
	</c:otherwise>
</c:choose>
<br>

<b>Address Area:</b>
<c:choose>
	<c:when test="${addressAreaListSize == -1}">
		<font color="red"><b> NULL </b></font><br>
	</c:when>
	<c:otherwise>
		(Size:&nbsp;${addressAreaListSize})<br>
		<%--
		<c:forEach items="${addressAreaList}" var="addrArea" varStatus="row">
			${row.count}:&nbsp;${addrArea.areaDescription}&nbsp;(${addrArea.areaCode})
			<br>
		</c:forEach>
		--%>
	</c:otherwise>
</c:choose>
<br>

<b>Address Category:</b>
<c:choose>
	<c:when test="${addressCategoryListSize == -1}">
		<font color="red"><b> NULL </b></font><br>
	</c:when>
	<c:otherwise>
		(Size:&nbsp;${addressCategoryListSize})<br>
		<%--
		<c:forEach items="${addressCategoryList}" var="addrCate" varStatus="row">
			${row.count}:&nbsp;${addrCate.categoryDesc}&nbsp;(${addrCate.categoryCode})
			<br>
		</c:forEach>
		--%>
	</c:otherwise>
</c:choose>
<br>

<b>Address District:</b>
<c:choose>
	<c:when test="${addressDistrictListSize == -1}">
		<font color="red"><b> NULL </b></font><br>
	</c:when>
	<c:otherwise>
		(Size:&nbsp;${addressDistrictListSize})<br>
		<%--
		<c:forEach items="${addressDistrictList}" var="addrDist" varStatus="row">
			${row.count}:&nbsp;${addrDist.districtDescription}&nbsp;(${addrDist.districtCode})
			<br>
		</c:forEach>
		--%>
	</c:otherwise>
</c:choose>
<br>

<b>Address Section:</b>
<c:choose>
	<c:when test="${addressSectionListSize == -1}">
		<font color="red"><b> NULL </b></font><br>
	</c:when>
	<c:otherwise>
		(Size:&nbsp;${addressSectionListSize})<br>
		<%--
		<c:forEach items="${addressSectionList}" var="addrSect" varStatus="row">
			${row.count}:&nbsp;${addrSect.sectionDescription}&nbsp;(${addrSect.sectionCode})
			<br>
		</c:forEach>
		--%>
	</c:otherwise>
</c:choose>
<br>

<b>Ims Address Area:</b>
<c:choose>
	<c:when test="${imsAddressAreaListSize == -1}">
		<font color="red"><b> NULL </b></font><br>
	</c:when>
	<c:otherwise>
		(Size:&nbsp;${imsAddressAreaListSize})<br>
		<%--
		<c:forEach items="${addressAreaList}" var="addrArea" varStatus="row">
			${row.count}:&nbsp;${addrArea.areaDescription}&nbsp;(${addrArea.areaCode})
			<br>
		</c:forEach>
		--%>
	</c:otherwise>
</c:choose>
<br>

<b>Ims Address District:</b>
<c:choose>
	<c:when test="${imsAddressDistrictListSize == -1}">
		<font color="red"><b> NULL </b></font><br>
	</c:when>
	<c:otherwise>
		(Size:&nbsp;${imsAddressDistrictListSize})<br>
		<%--
		<c:forEach items="${addressDistrictList}" var="addrDist" varStatus="row">
			${row.count}:&nbsp;${addrDist.districtDescription}&nbsp;(${addrDist.districtCode})
			<br>
		</c:forEach>
		--%>
	</c:otherwise>
</c:choose>
<br>

<b>Ims Address Section:</b>
<c:choose>
	<c:when test="${imsAddressSectionListSize == -1}">
		<font color="red"><b> NULL </b></font><br>
	</c:when>
	<c:otherwise>
		(Size:&nbsp;${imsAddressSectionListSize})<br>
		<%--
		<c:forEach items="${addressSectionList}" var="addrSect" varStatus="row">
			${row.count}:&nbsp;${addrSect.sectionDescription}&nbsp;(${addrSect.sectionCode})
			<br>
		</c:forEach>
		--%>
	</c:otherwise>
</c:choose>
<br>

<b>Bank Branch:</b>
<c:choose>
	<c:when test="${bankBranchListSize == -1}">
		<font color="red"><b> NULL </b></font><br>
	</c:when>
	<c:otherwise>
		(Size:&nbsp;${bankBranchListSize})<br>
		<%--
		<c:forEach items="${bankBranchList}" var="bankBranch" varStatus="row">
			${row.count}:&nbsp;${bankBranch.branchName}&nbsp;(${bankBranch.branchCode})
			<br>
		</c:forEach>
		--%>
	</c:otherwise>
</c:choose>
<br>

<b>Credit Card Type:</b>
<c:choose>
	<c:when test="${creditCardTypeListSize == -1}">
		<font color="red"><b> NULL </b></font><br>
	</c:when>
	<c:otherwise>
		(Size:&nbsp;${creditCardTypeListSize})<br>
		<%--
		<c:forEach items="${creditCardTypeList}" var="credit" varStatus="row">
			${row.count}:&nbsp;${credit.bomDesc}&nbsp;(${credit.bomCode})
			<br>
		</c:forEach>
		--%>
	</c:otherwise>
</c:choose>
<br>

<b>Autopay Issue Bank:</b>
<c:choose>
	<c:when test="${autopayIssueBankListSize == -1}">
		<font color="red"><b> NULL </b></font><br>
	</c:when>
	<c:otherwise>
		(Size:&nbsp;${autopayIssueBankListSize})<br>
		<%--
		<c:forEach items="${autopayIssueBankList}" var="autoBank"
			varStatus="row">
			${row.count}:&nbsp;${autoBank.bankName}&nbsp;(${autoBank.bankCd})
			<br>
		</c:forEach>
		--%>
	</c:otherwise>
</c:choose>
<br>

<b>Autopay Bank Branch:</b>
<c:choose>
	<c:when test="${autopayBankBranchListSize == -1}">
		<font color="red"><b> NULL </b></font><br>
	</c:when>
	<c:otherwise>
		(Size:&nbsp;${autopayBankBranchListSize})<br>
		<%--
		<c:forEach items="${autopayBankBranchList}" var="autoBranch"
			varStatus="row">
			${row.count}:&nbsp;${autoBranch.branchName}&nbsp;(${autoBranch.branchCode})
			<br>
		</c:forEach>
		--%>
	</c:otherwise>
</c:choose>
<br>

<b>Address District Map:</b>
<c:choose>
	<c:when test="${tempAddressDistrictMapSize == -1}">
		<font color="red"><b> NULL </b></font><br>
	</c:when>
	<c:otherwise>
		(Size:&nbsp;${tempAddressDistrictMapSize})<br>
		<%--
		<c:forEach items="${tempAddressDistrictMap}" var="addrDistMap"
			varStatus="row">
			${row.count}:&nbsp;${addrDistMap.key}&nbsp;&lt;--&gt;&nbsp;${addrDistMap.value}
			<br>
		</c:forEach>
		--%>
	</c:otherwise>
</c:choose>
<br>

<b>Code Lookup Map:</b>
<c:choose>
	<c:when test="${codeLkupMapSize == -1}">
		<font color="red"><b> NULL </b></font><br>
	</c:when>
	<c:otherwise>
		(Size:&nbsp;${codeLkupMapSize})<br>
		<%--
		<c:forEach items="${codeLkupMap}" var="cdLkupMap" varStatus="row">
			${row.count}:&nbsp;${cdLkupMap.key}&nbsp;&lt;--&gt;&nbsp;${cdLkupMap.value[row.count-1].codeDesc}
			<br>
		</c:forEach>
		--%>
	</c:otherwise>
</c:choose>
<br>

<b>Allow Fallout Channel List:</b>
<c:choose>
	<c:when test="${allowFalloutChannelListSize == -1}">
		<font color="red"><b> NULL </b></font><br>
	</c:when>
	<c:otherwise>
		(Size:&nbsp;${allowFalloutChannelListSize})<br>
		<%--
		<c:forEach items="${allowFalloutChannelList}" var="fallout" varStatus="row">
			${row.count}:&nbsp;${fallout.codeDesc}&nbsp;(${fallout.codeId})
			<br>
		</c:forEach>
		--%>
	</c:otherwise>
</c:choose><br>
<b>Bill Media List:</b>
<c:choose>
	<c:when test="${BillMediaOptionSize == -1}">
		<font color="red"><b> NULL </b></font><br>
	</c:when>
	<c:otherwise>
		(Size:&nbsp;${BillMediaOptionSize})<br>
		<%--
		<c:forEach items="${allowFalloutChannelList}" var="fallout" varStatus="row">
			${row.count}:&nbsp;${fallout.codeDesc}&nbsp;(${fallout.codeId})
			<br>
		</c:forEach>
		--%>
	</c:otherwise>
</c:choose><br>
<b>COS Retail Application Form List:</b>
<c:choose>
	<c:when test="${cosRSAppFormListSize == -1}">
		<font color="red"><b> NULL </b></font><br>
	</c:when>
	<c:otherwise>
		(Size:&nbsp;${cosRSAppFormListSize})<br>
	</c:otherwise>
</c:choose><br>
<b>COS Call Centre Application Form List:</b>
<c:choose>
	<c:when test="${cosCCSAppFormListSize == -1}">
		<font color="red"><b> NULL </b></font><br>
	</c:when>
	<c:otherwise>
		(Size:&nbsp;${cosCCSAppFormListSize})<br>
	</c:otherwise>
</c:choose><br>

<b>MOB SIM Type Brand Map:</b>
<c:choose>
	<c:when test="${mobSimTypeMapSize == -1}">
		<font color="red"><b> NULL </b></font><br>
	</c:when>
	<c:otherwise>
		(Size:&nbsp;${mobSimTypeMapSize})<br>
	</c:otherwise>
</c:choose><br>

<b>Item Func Assgn Mob Map:</b>
<c:choose>
	<c:when test="${itemFuncAssgnMobMapSize == -1}">
		<font color="red"><b> NULL </b></font><br>
	</c:when>
	<c:otherwise>
		(Size:&nbsp;${itemFuncAssgnMobMapSize})<br>
	</c:otherwise>
</c:choose><br>

<b>MOB Item Quota Map:</b>
<c:choose>
	<c:when test="${mobItemQuotaMapSize == -1}">
		<font color="red"><b> NULL </b></font><br>
	</c:when>
	<c:otherwise>
		(Size:&nbsp;${mobItemQuotaMapSize})<br>
	</c:otherwise>
</c:choose><br>