
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page import="			
					com.bomwebportal.bean.LookupTableBean,
					com.bomwebportal.dao.ConstantLkupDAO,
					java.util.*
					"
%>
<%
	ConstantLkupDAO constantlkupDao = new ConstantLkupDAO();
	LookupTableBean.getInstance().setBillPeriodList(constantlkupDao.getBillPeriod());
	List<Integer> billPeriod = LookupTableBean.getInstance().getBillPeriodList();
	
	for(int i=0; i<billPeriod.size(); i++){
		System.out.println("billPeriod: " + billPeriod.get(i));	
	}

%>
<html>
<head>

  <link href="js/jquery.autocomplete.css" rel="stylesheet" type="text/css"/>
  <script src="js/jquery-1.4.4.js"></script>
  <script src="js/jquery.autocomplete.js"></script>
  <script>
  $(document).ready(function() {
	    $("input#example").autocomplete("addresslookup.html", {
	        //minChars: 4,
	        minChars: 3,
	        delay: 400,
		selectFirst: false,
	        max: 12,
	        matchSubset: false,
		highlight: false,
	        //extraParams: {random: (function(){return Math.random()})},
	        formatItem: function(row, i, max) {
	           return row;
	        }
	    });

  });
  </script>
  
</head>
<body>
<form>
  Address lookup: <input id="example" size="100"/>
  <br><br>
  Area Desc: <input type="text" id="areaDesc" name="areaDesc"/>	
  Building Name: <input type="text" id="buildingName" name="buildingName" />
  <br><br>
  
</form>
</body>
</html>
