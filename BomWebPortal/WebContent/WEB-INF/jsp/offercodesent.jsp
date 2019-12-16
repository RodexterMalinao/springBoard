<%
response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
response.setHeader("Pragma","no-cache"); //HTTP 1.0
response.setDateHeader ("Expires", 0); //prevents caching at the proxy server
%><%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" 
%><%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"
%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"
%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"
%><%@ taglib prefix="spring" uri="http://www.springframework.org/tags"
%><%@taglib prefix="form" uri="http://www.springframework.org/tags/form" 
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width"/>
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="expires" content="0" />
<title>mobile number</title>

<link type="text/css" href="css/custom-theme/jquery-ui-1.10.4.custom.min.css" rel="stylesheet" />

<script src="js/jquery-1.10.2.js"></script>
<script src="js/jquery-ui-1.10.3.custom.min.js"></script>
<script>
$(document).ready(function() {
  $('#btnClose').click(function(e) {
    e.preventDefault();
    window.close();
  });
});
</script>

<style>
html {
  height:100%;
}
body {
  min-height:100%;
  background: rgba(235,234,230,1);
background: -moz-linear-gradient(top, rgba(235,234,230,1) 0%, rgba(247,247,244,1) 100%);
background: -webkit-gradient(left top, left bottom, color-stop(0%, rgba(235,234,230,1)), color-stop(100%, rgba(247,247,244,1)));
background: -webkit-linear-gradient(top, rgba(235,234,230,1) 0%, rgba(247,247,244,1) 100%);
background: -o-linear-gradient(top, rgba(235,234,230,1) 0%, rgba(247,247,244,1) 100%);
background: -ms-linear-gradient(top, rgba(235,234,230,1) 0%, rgba(247,247,244,1) 100%);
background: linear-gradient(to bottom, rgba(235,234,230,1) 0%, rgba(247,247,244,1) 100%);
filter: progid:DXImageTransform.Microsoft.gradient( startColorstr='#ebeae6', endColorstr='#f7f7f4', GradientType=0 );
  font-family: "Helvetica Neue", Helvetica, Arial, sans-serif;
  padding-top: 20px;
}
.container {
  width: 406px;
  max-width: 406px;
  margin: 0 auto;
}
#signup {
  padding: 0px 25px 25px;
  background: #fff;
  box-shadow: 0px 0px 0px 5px rgba( 255,255,255,0.4 ),  0px 4px 20px rgba( 0,0,0,0.66 );
  -moz-border-radius: 5px;
  -webkit-border-radius: 5px;
  border-radius: 5px;
  display: table;
  position: static;
}
#signup .header {
  margin-bottom: 20px;
}
#signup .header h3 {
  color: #333333;
  font-size: 16px;
  font-weight: bold;
  margin-bottom: 5px;
}
#signup .header img {
  border:0;
  margin:14px 0;
}
#signup .inputs p.error {
  color: #72350c;
  font-size: 14px;
  font-weight: 700;
  text-align: center;
  /*visibility:hidden;*/
}
#signup .sep {
  height: 1px;
  background: #e8e8e8;
  width: 100%;
  margin: 0px ;
}
#signup .inputs {
  margin-top: 25px;
}
#signup .inputs label {
  color: #8f8f8f;
  font-size: 12px;
  font-weight: 300;
  letter-spacing: 1px;
  margin-bottom: 7px;
  display: block;
}
 input::-webkit-input-placeholder {
 color:    #b5b5b5;
}
 input:-moz-placeholder {
 color:    #b5b5b5;
}
#signup .inputs input[type=tel] {
  background: #f5f5f5;
  font-size: 1.2rem;
  -moz-border-radius: 3px;
  -webkit-border-radius: 3px;
  border-radius: 3px;
  border: none;
  padding: 13px 10px;
  width: 330px;
  margin-bottom: 20px;
  box-shadow: inset 0px 2px 3px rgba( 0,0,0,0.1 );
  clear: both;
}
#signup .inputs label.terms {
  float: left;
  font-size: 14px;
  font-style: italic;
}
#signup .inputs #btnClose {
  width: 100%;
  margin-top: 20px;
  padding: 15px 0;
  color: #fff;
  font-size: 18px;
  font-weight: 800;
  letter-spacing: 1px;
  text-align: center;
  text-decoration: none;
  background: rgba(168,163,155,1);
background: -moz-linear-gradient(top, rgba(168,163,155,1) 0%, rgba(77,70,59,1) 51%, rgba(66,59,49,1) 100%);
background: -webkit-gradient(left top, left bottom, color-stop(0%, rgba(168,163,155,1)), color-stop(51%, rgba(77,70,59,1)), color-stop(100%, rgba(66,59,49,1)));
background: -webkit-linear-gradient(top, rgba(168,163,155,1) 0%, rgba(77,70,59,1) 51%, rgba(66,59,49,1) 100%);
background: -o-linear-gradient(top, rgba(168,163,155,1) 0%, rgba(77,70,59,1) 51%, rgba(66,59,49,1) 100%);
background: -ms-linear-gradient(top, rgba(168,163,155,1) 0%, rgba(77,70,59,1) 51%, rgba(66,59,49,1) 100%);
background: linear-gradient(to bottom, rgba(168,163,155,1) 0%, rgba(77,70,59,1) 51%, rgba(66,59,49,1) 100%);
filter: progid:DXImageTransform.Microsoft.gradient( startColorstr='#a8a39b', endColorstr='#423b31', GradientType=0 );
  display: table;
  position: static;
  clear: both;
}
</style>
</head>

<body>
<div class="container">
  <form id="signup">
    <div class="header" style="text-align:center; vertical-align:top;"><img src="http://1010.hkcsl.com/images/home.2012/BrandLogo2011_eng.png" alt="1O1O" width="113" height="61" style="margin-bottom:40px;" />
    <img src="http://www.hkcsl.com/r/cms/pccw/default/tc/images/csl_logo.jpg" alt="csl" width="130" height="110" />
    </div>
    <div class="sep"></div>
    <div class="header" style="width: 330px">
      <h3>
        <spring:message code="label.offercodereq.offer_code_has_been_sent" />
      </h3>
    </div>
    <div class="inputs">
      <button id="btnClose" ><spring:message code="label.offercodereq.close" /></button>
      <c:set var="tc_en" value="${pageContext.response.locale == 'en' ? 'en' : 'tc'}"/>
      <p align="right"><img src="http://www.hkcsl.com/r/cms/pccw/default/${tc_en}/images/footer_right_logo.jpg" /></p>
    </div>
  </form>
</div>
</body>
</html>
