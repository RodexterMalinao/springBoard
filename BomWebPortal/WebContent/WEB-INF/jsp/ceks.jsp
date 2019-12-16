<% 
	response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
	response.setHeader("Pragma","no-cache"); //HTTP 1.0
	response.setDateHeader ("Expires", 0); //prevents caching at the proxy server

	String v1 = request.getParameter("V1")==null?"":request.getParameter("V1");
	String v2 = request.getParameter("V2")==null?"":request.getParameter("V2");
	String v3 = request.getParameter("V3")==null?"":request.getParameter("V3");
	String v5 = request.getParameter("V5")==null?"":request.getParameter("V5");

%> 
<html>
	<script language="javascript">
		function onLoadHandler(){
				document.ceksForm.submit();
		}
	</script>
	<body onload="onLoadHandler()">
	 <form name="ceksForm" id="ceksForm" method="POST">
	 	<input type="hidden" name="v1" value='<%=v1%>' />
	 	<input type="hidden" name="v2" value='<%=v2%>' />
	 	<input type="hidden" name="v3" value='<%=v3%>' />
	 	<input type="hidden" name="v5" value='<%=v5%>' />
	 </form>
	</body>
</html>