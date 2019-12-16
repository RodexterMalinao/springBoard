<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%@ page import="java.security.*" %>
<%@ page import="org.bouncycastle.jce.provider.*" %>

<%
String reload = request.getParameter("reload");
if ("0".equals(reload)) {
	Security.removeProvider(BouncyCastleProvider.PROVIDER_NAME);
} else if ("1".equals(reload)) {
	Security.removeProvider(BouncyCastleProvider.PROVIDER_NAME);
	BouncyCastleProvider bcp = new BouncyCastleProvider();
	Security.addProvider(bcp);
}

Provider ps[] = Security.getProviders();
pageContext.setAttribute("providers", ps);

%>
<style>
th {
background-color: yellow;
}
</style>
<h1>Security Providers: </h1><br/>
<table border="1">
<tr>
<th>&nbsp;</th>
<th>Name</th>
<th>Provider</th>
<th>Info</th>
<th>ClassLoader</th>
<c:forEach var="p" items="${providers }" varStatus="i">
<tr>
  <td>${i.index }</td>
  <td><%= ((Provider)pageContext.getAttribute("p")).getName() %></td>
  <td>${p }</td>
  <td><code><%= ((Provider)pageContext.getAttribute("p")).getInfo() %></code></td>
  <td><pre><%= ((Provider)pageContext.getAttribute("p")).getClass().getClassLoader() %></pre></td>
</tr>
</c:forEach>
</table>
<br/>
<br/>
<div style="border: solid thin; padding: 10px;">

<a href="?reload=0">Remove BC</a> |  <a href="?reload=1">Reload BC</a> | <a href="?">Refresh</a>

</div>