<%if (!"true".equals(request.getParameter("isAmend"))){%>
</div>
<br/>
<div id="s_line_text"></div>
<br/>
<div id="foot">
  <div id="footer">
<spring:message code="footer.msg" text="" />
<br>
<c:out value="${SbEnv.appEnv} "/>
<c:out value="Version: ${SbEnv.appBuildVersion}"/>
<c:out value="Build: ${SbEnv.appBuildDate} "/>
</div>
</div>
<%} %>
</form>
</body>
</html>