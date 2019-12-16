<%@ include file="/WEB-INF/jsp/header.jsp"%>
<jsp:useBean id="now" class="java.util.Date" scope="request" />
<div id="progressbar"><br>
<br>
</div>

<link type="text/css" href="css/custom-theme/jquery-ui-1.8.9.custom.css"
	rel="stylesheet" />
<script src="js/jquery-1.4.4.js"></script>
<script src="js/jquery-ui-1.8.9.custom.min.js"></script>
<link href="css/imsds.css" rel="stylesheet" type="text/css"> 
<script language="javascript">
$(document).ready(function() {

    <c:if test="${changeShopCode.actionType eq 'UPDATE'}">
    
    var myWindow = window.close();
    
    </c:if>

});

	function changeShopCodeFunction() {
		document.changeShopCodeForm.actionType.value = "UPDATE";
		document.changeShopCodeForm.submit();
	}
</script>
<div id=progress bar>
<br><br>
</div>

<form:form name="changeShopCodeForm" method="POST" commandName="changeShopCode">
<table width="100%"  class="tablegrey">
  <tr> 
  	<td bgcolor="#FFFFFF" > 
	  <table width="100%"  border="0" cellspacing="1" >
	   	<tr>
	   	  <td class="table_title">Change Shop Code</td>
	   	</tr>
	  </table>
 

	  <table width="100%" border="0" cellspacing="1" class="contenttext"	
	      background="images/background2.jpg">
	    <tr>
          <td colspan="2"><span class="error">${exception.customMessage}</span></td>
        </tr>
        
        <tr>
          <td width="25%">
            <div align="right">Shop Code<span class="contenttext_red">*</span></div>
          </td>
          <td class="contenttext">
			<form:select path="shopCd">		
				<form:option value="" label=" ---- Please Select ---- " />
				<form:options items="${shopCdList}" />
			</form:select>
			<form:errors path="shopCd" cssClass="error" />
          </td>
        </tr>

      </table>
    
     <table width="100%" border="0" cellspacing="0">
				<tr>
					<td>
					<div class="buttonmenubox_R" id="buttonArea">
					<a href="#" class="nextbutton" onClick="javascript:changeShopCodeFunction()">Update</a>
					
					</div>
					<div class="buttonmenubox_R" id="buttonArea">
					<a href="welcome.html" class="nextbutton">Quit</a>
					</div>
					</td>
				</tr>
	</table>
    
      <input type="hidden" name="appMode" value="shop"/>
      <input type="hidden" name="actionType"/>
      
    </td>
  </tr>
</table>


</form:form>
  

<!---------------------------------------  end content ------------------------------------------->
<%@ include file="/WEB-INF/jsp/footer.jsp" %>