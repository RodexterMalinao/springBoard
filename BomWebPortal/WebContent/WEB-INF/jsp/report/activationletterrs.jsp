<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="pccw-pdf" tagdir="/WEB-INF/tags/pdf"%>
<%@ taglib prefix="pccw-util" tagdir="/WEB-INF/tags/util"%>
<%@ taglib prefix="pccw-ui" tagdir="/WEB-INF/tags/ui"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
<%-- <pccw-pdf:pdf-fontface uri="classpath:fonts/MSJH.ttf"></pccw-pdf:pdf-fontface> --%>
<style type="text/css">
@charset "UTF-8";

#pageHeader {
	display: block;
	text-align: center;
	position: running(header);
}

#header {
font: bold serif;
position: absolute; top: 0; left: 0; 
}


@page {
	size: A4 portrait;
	margin: 0.1in 0.48in 2.0in 0.5in;
	@top-center{
		content:element(header);
	}
}

#footer {
	text-align: right;
	position: running(footer);
}

@page {
	@bottom-right{
		content:element(footer);
	}
}

@font-face {
	font-family: "PCCW-zh-Ming";
	src: url("fonts/PCCW-zh-Ming.ttf");
	-fs-pdf-font-embed: embed;
	-fs-pdf-font-encoding: Identity-H;
}

@font-face {
	font-family: "PCCW-zh-Ming";
	src: url("fonts/PCCW-zh-MingBold.ttf");
	font-weight: bold;
	-fs-pdf-font-embed: embed;
	-fs-pdf-font-encoding: Identity-H;
}

body {
	font-family: "PCCW-zh-Ming", sans-serif;
	font-size: 9pt;
	word-wrap: normal;
	word-break: keep-all;
	overflow-wrap: normal;
	line-break: loose;
}

* {
	font-family: "PCCW-zh-Ming", sans-serif;
	font-size: 9pt;
}

table {
	-fs-table-paginate: paginate;
	-fs-border-spacing-horizontal: 0;
	-fs-border-spacing-vertical: 0;
	border-collapse: collapse;

	border-spacing: 0;
	border-style: none;
	border-width: 0;
	border: 0;
	padding: 0;
	margin: 0;

}

ul {
	list-style-type: square;
}

.underline {
	display: inline-block;
	border: 1px solid black;
}

.clearboth {
	clear: both;
}
</style>
</head>
<body>
	<img src="${companyLogo}" alt=""  height="66"></img>
	<div class="clearboth"></div>
	
	<div style="height: 70px"></div>
	
	<div style="width: 80%; float: left">
		<table style="width: 100%;  margin-left: 70px">
			<colgroup style="width: 70%"></colgroup>
			<colgroup style="width: 30%"></colgroup>
			<tr style="height: 0.25in"><td><div ></div></td></tr>
			<tr style="height: 0.25in"><td>${custName}</td></tr>
			<tr><td>${addrLn1}</td></tr>
			<tr><td>${addrLn2}</td></tr>
			<tr><td>${addrLn3}</td></tr>
			<tr><td>${addrLn4}</td></tr>
			<tr><td>${addrLn5}</td></tr>
			<tr><td></td></tr>
		</table>
	</div>
	<div style="width: 20%; float: right; text-align: right">
		<table style="width: 100%">
			<tr style="height: 0.25in"><td></td></tr>
			<tr style="height: 0.25in"><td></td></tr>
			<tr style="height: 0.25in"><td></td></tr>
			<tr><td>${sendDate}</td></tr>
			<tr style="height: 0.25in"><td><div ></div></td></tr>
			<tr style="height: 0.25in"><td><div ></div></td></tr>
			<tr style="height: 0.25in"><td><div ></div></td></tr>
		</table>
	</div>
	
	<div class="clearboth"></div>
	<div style="height: 90px"></div>
	
	<table style="width: 100%">
		<tr><td>Mobile number: ${msisdn}</td></tr>
		<tr><td>Service application form reference number: ${agreementNum}</td></tr>
	</table>

    <div class="clearboth"></div>
	<br />
	<div style="float: left;font-size: 10pt;">Dear Valued Customer,</div>
	<br />
	<br />
	<div style="float: left; font-size: 10pt;">Thank you for choosing ${brandString} as your mobile service provider. Please complete the address validation process by taking this letter to the ${shopString} where you subscribed 
	(${shopInfo.engDesc}, Address: ${shopInfo.engAddr1}<c:if test="${not empty shopInfo.engAddr2}">, ${shopInfo.engAddr2}</c:if><c:if test="${not empty shopInfo.engAddr3}">, ${shopInfo.engAddr3}</c:if><c:if test="${not empty shopInfo.engAddr4}">, ${shopInfo.engAddr4}</c:if>) within 14 days of ${srdDate}.</div>
	<div class="clearboth"></div>
	<br />
	<div style="float: left; font-size: 10pt;">Your service will terminate automatically if you fail to complete the above-mentioned procedure within the specified  period, so please act as soon as possible. If you have already submitted proof of address, then please ignore this letter.</div>
	<div class="clearboth"></div>
	<br />
	<div style="float: left; font-size: 10pt;">If you have any queries, feel free to contact one of our sales executive.</div>
	<div class="clearboth"></div>
	<br />
	<div style="float: left; font-size: 10pt;">Yours sincerely, </div>
	 <div class="clearboth"></div>
	<div style="float: left;font-size: 10pt;" >CSL Mobile Limited</div>
	<br />
	
	<hr/>
	<br/>
	<div style="float: left; font-size: 10pt;">(To be completed by Customer)</div>
	<div class="clearboth"></div>
	<br/>
	<div style="float: left; font-size: 10pt;">I would like to submit this letter as address proof for application of below service(s):</div>
	<div class="clearboth"></div>
	<br/>
	<div style="float: left; font-size: 10pt;"><img src="images/tickbox.jpg" height="30" /> For CSL Mobile Limited Mobile Service Application</div>
	<div class="clearboth"></div>
	<br/>
	<div style="float: left; font-size: 10pt;"><img src="images/tickbox_empty.jpg" height="30" /> For HKT Payment Limited Tap &amp; Go Card Application</div>
	<div class="clearboth"></div>
	<br/>
	<div style="float: left; font-size: 10pt;">(please‘<img src="images/tick.jpg" height="15" /> ’if applicable)</div>
	<div class="clearboth"></div>
	<br/>
	<div style="height: 45px"></div>
	<table style="width: 100%">
		<tr>
			<td><div style="float: left; font-size: 10pt;">Customer's Signature: <span style="text-decoration: underline;">________________________________</span></div></td>
			<td><div style="float: right; font-size: 10pt;">Date: <span style="text-decoration: underline;">________________</span></div></td>
		</tr>
	</table>
	<div class="clearboth"></div>
	<br/>
	
	<div id="footer"><img src="${companyLogoBottom}" height="100"></img></div>
</body>
</html>



