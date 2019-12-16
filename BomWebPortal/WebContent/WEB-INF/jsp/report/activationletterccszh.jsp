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
-fs-move-to-flow: "header";
}

#footter{
bottom:0;
}
@page {
	size: A4 portrait;
	margin: 0.1in 0.5in 2.0in 0.5in;
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
		<tr><td>流動電話號碼: ${msisdn}</td></tr>
		<tr><td>流動通訊服務申請書表格編號: ${agreementNum}</td></tr>
	</table>

    <div class="clearboth"></div>
	<br />
	<div style="float: left;font-size: 10pt;">親愛的客戶:</div>
	<br />
	<div style="float: left; font-size: 10pt;">多謝閣下選用${brandString}，為讓閣下可繼續使用我們的優質服務，敬請於服務啟用日期由${srdDate}起計 14 天內, 電郵此確認信至電郵地址 TelesalesSupportCUST@hkcsl.com 或傳真至傳真號碼 2164 3998 為地址確認。</div>
	<div class="clearboth"></div>
	<br />
	<div style="float: left; font-size: 10pt;">如閣下未能於指定時間內辦理手續，有關${serviceString}將被暫停。為免造成不便，敬請從速確認。如已遞交地址證明，則毋須理會此信。  </div>
	<div class="clearboth"></div>
	<div style="float: left; font-size: 10pt;">多謝閣下選用我們的服務，如有任何垂詢，請與我們的營業主任聯絡。 </div>
	<div class="clearboth"></div>
	<br />
	<div style="float: left; font-size: 10pt;">此致 </div>
	 <div class="clearboth"></div>
	<div style="float: left; font-size: 10pt;" >香港移動通訊有限公司</div>
	<br />
	
	<hr/>
	<br/>
	<div style="float: left; font-size: 10pt;">(由客戶填寫)</div>
	<div class="clearboth"></div>
	<br/>
	<div style="float: left; font-size: 10pt;">本人提交此信作為申請以下服務之用途:</div>
	<div class="clearboth"></div>
	<br/>
	<div style="float: left; font-size: 10pt;"><img src="images/tickbox.jpg" /> 申請CSL Mobile Limited 流動通訊服務</div>
	<div class="clearboth"></div>
	<br/>
	<div style="float: left; font-size: 10pt;"><img src="images/tickbox_empty.jpg" /> 申請HKT Payment Limited Tap &amp; Go Card</div>
	<div class="clearboth"></div>
	<br/>
	<div style="float: left; font-size: 10pt;">(請‘<img src="images/tick.jpg" height="15" /> ’以上適用的方格)</div>
	<div class="clearboth"></div>
	<br/>
	<div style="height: 45px"></div>
	<table style="width: 100%">
		<tr>
			<td><div style="float: left; font-size: 10pt;">客戶簽署: <span style="text-decoration: underline;">________________________________</span></div></td>
			<td><div style="float: right; font-size: 10pt;">日期: <span style="text-decoration: underline;">________________</span></div></td>
		</tr>
	</table>
	<div class="clearboth"></div>
	<br/>
	
	<div id="footer"><img src="${companyLogoBottom}" height="100"></img></div>

</body>
</html>



