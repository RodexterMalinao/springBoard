


 





<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<script type="text/javascript">
function closeMe()
{
	self.close();
}
</script>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<meta http-equiv="Cache-Control" content="no-cache" />
<title>CEKS</title>
<link href="css/ceks.css" rel="stylesheet" type="text/css" media="screen" />
</head>
<body >
<div align=center style="position: absolute; top: 0%; width: 95%; ">
<div align=left style="width:420px;"> <!-- Logo Only -->
<table style="width:100%;">
	<tr>
		<td align=left style="width:50%;">
			<img src="images/logo_chi.jpg" alt="PCCW" />
		</td>
		<td align=right style="width:50%;">

		</td>
	</tr>

	<tr>
		<td class="style1" colspan="2" style="width: 100%;">
		This transaction may be protected by Verified by VISA or MasterCard SecureCode services; in which case you will have to go through the Verified by VISA or MasterCard SecureCode verification procedures in order to complete this transaction.<br />此交易或需經 VISA 驗證或 MasterCard SecureCode 核實，在這情況下，閣下須通過 VISA 驗證或 MasterCard SecureCode 核實程序方可完成此交易。
		</td>
	</tr>
	<tr>
		<td colspan="2" style="width: 410px;">
			<img src="images/vbv.jpg" align="right">
		</td>
	</tr>

</table>
</div> <!-- End of Logo -->
	
<div> <!-- Content  -->
<form>
<!--onsubmit="if (this.getAttribute('submitted')) {alert('In progress, please wait ...'); return false;} this.setAttribute('submitted', 'true');"-->
<fieldset class="box" style="width:420px;">

<legend>Payment/付款</legend>

<table class="lbl" style="width: 400px;" cellspacing="0"; cellpadding="0">
	<tr>
		<td align=left style="width: 35%;" >
		Service<br/>服務
		</td>
		<td align=left style="width: 65%;" >
		<input class="inp" style="width: 80%;" type="text" 
	               name='MRCH'
	               value='eye'
	               READONLY
		/>
		</td>
	</tr>

	<tr>
		<td align=left style="width: 35%;" >
		Charging Description<br/>收費事項
		</td>
		<td align=left style="width: 65%;" >
		<input class="inp" style="width: 80%;" type="text" 
	               name='CHG_ITEM'
	               value='Service fee'
	               READONLY
		/>
		</td>
	</tr>

	<tr>
		<td align=left style="width: 35%;" >
		Credit Card Number<br/>信用卡卡號
		</td>
		<td align=left style="width: 65%;" >
		<input class="inp" style="width: 80%;" type="text"
	               name='PAN'
	               value=''
	                
	               autocomplete="off"
		/>
		<input type='hidden'
	               name='WSQ'
	               value='1'
	    />
		</td>
	</tr>
	<tr>
		<td align=left style="width: 35%;" >
		</td>
		<td align=left style="width: 65%;" >
			(Please omit space. E.g.: 4201888855558888)<br />
			(請勿輸入空格. 例子: 4201888855558888)
		</td>
	</tr>
	<tr>
		<td align=left style="width: 35%;" >
		Expiry Date<br/>有效日期
		</td>
		<td align=left style="width: 65%;" >
		<input class="inp" style="width: 15%;" type="text"
	               name='EXDA_MM'
	               value=''
	               
		/>
		&nbsp;/&nbsp;
		<input class="inp" style="width: 15%;" type="text"
	               name='EXDA_YY'
	               value=''
	               
		/>
	<tr>
		<td align=left style="width: 35%;" >
		</td>
		<td align=left style="width: 65%;" >
			(MM/YY)
		</td>
	</tr>
	

	<tr>
		<td align=left style="width: 35%;" >
		Amount<br/>款項 (HK$)
		</td>
		<td align=left style="width: 65%;" >
		<input class="inp" style="width: 80%;" type="text" 
	               name='AMOUNT'
	               value='398'
	               READONLY
		/>
		</td>
	</tr>

	<tr>
		<td align=left>
		</td>
		<td align=left>
        <button onClick="window.parent.location='regfinish.html'" class="lbl" style="width:85px; height:25px;">Submit/遞交</button>
		<!--<input class="lbl" type="submit" style="width:80px; height:25px;"
				   name='Submit'
	               value='Submit/遞交'
	               
		/>-->
		
		<input class="lbl" type="submit" style="width:90px; height:25px;"
				   name='Confirm'
	               value='Confirm/確認'
	               DISABLED
		/>
		

		<input class="lbl" type="button" style="width:75px; height:25px;" onclick="location.href='https://218.102.2.69/OnlineSales/ceksPC.jsp?V3=7e730a0674d92c554864dddb2ac5b9209dd36e5391bdaaea2a6324e707446418&V5='"
				   name='Back'
	               value='Back/返回'
	               
		/>

		</td>
	</tr>
	<tr style="height: 18px;">
		<td class="style1" align=left colspan=2>
		<span>Please input credit card information and press &#034;Submit&#034; to complete the transaction / 請填上信用咭資料及按 &#039;遞交&#039; 以完成此交易</span>
		</td>
	</tr>
</table>
</fieldset>
<table cellspacing="0"; cellpadding="0">
	<tr>
		<td class="style1" style="width: 410px;">
Information that you provide will be encrypted, using 128bit SSL encryption technology, during its transmission.  Collection, use and storage of any personal data is subject to PCCW's Privacy Policy Statement posted at http://www.pccw.com<br />閣下所提供的資料會以 128 位元 SSL 加密技術傳送。任何個人資料的收集、使用及儲存乃根據電訊盈科私隱政策聲明，詳情見 http://www.pccw.com。
		</td>
	</tr>
	<tr>
		<td style="width: 410px;">
			<img src="images/verisign.gif" height="41" width="74" align="right">
		</td>
	</tr>
	<tr>
		<td class="style2" style="width: 410px;">
Credit card authentication or payment process may take 1-2 minutes.  Please be patient and do not click “Back” or “Refresh” button on your browser once the process starts.<br />信用卡核實或付款程序需時一至兩分鐘。請耐心等候，切勿在瀏覽器上按"上一頁"或"重新整理"。
		</td>
	</tr>
</table>
	
</form>
</div> <!--  End of Content -->

</div>

<script language="JavaScript">

	var tokenName = 'OWASP_CSRFTOKEN';
	var tokenValue = 'TQ5T0MC6Bhgg4HuyzlVniubfyfQHvrnCD6QFM5NVduUe';

	function updateAnchors()
	{
		updateTag('a','href');
	}
	
	function updateLinks()
	{
		updateTag('link', 'href');
	}
	
	function updateAreas()
	{
		updateTag('area', 'href');
	}
	
	function updateFrames()
	{
		updateTag('frame', 'src');
	}
	
	function updateIFrames()
	{
		updateTag('iframe', 'src');
	}
	
	function updateStyles()
	{
		updateTag('style', 'src');
	}
	
	function updateScripts()
	{
		updateTag('script', 'src');
	}
	
	function updateImages()
	{
		updateTag('img', 'src');
	}
	
	function updateForms()
	{
		var forms = document.getElementsByTagName('form');
		
		for(i=0; i<forms.length; i++)
		{
			var html = forms[i].innerHTML;
			
			html += '<input type=hidden name=' + tokenName + ' value=' + tokenValue + ' />';
			
			//alert('new html: ' + html);
			
			forms[i].innerHTML = html;
		}
	}
	
	function updateTag(name,attr)
	{
		var links = document.getElementsByTagName(name);
		
		//alert('length: ' + links.length);
		
		for(i=0; i<links.length; i++)
		{
			var src = links[i].getAttribute(attr);
			
			if(src != null && src != '')
			{
				//alert('found ' + src + '!');
			
				if(isHttpLink(src))
				{
					var index = src.indexOf('?');
				
					if(index != -1)
					{
						src = src + '&' + tokenName + '=' + tokenValue;
					}
					else
					{
						src = src + '?' + tokenName + '=' + tokenValue;
					}
					
					//alert('new src ' + src);
					
					links[i].setAttribute(attr, src);
				}
			}
		}
	}
	
	function isHttpLink(src)
	{
		var result = 0;
		
		if(src.substring(0, 4) == 'http' || src.substring(0, 1) == '/' || src.indexOf(':') == -1)
		{
			result = 1;
		}
		
		return result;
	}
	
	updateAnchors();
	updateLinks();
	updateAreas();
	updateFrames();
	updateIFrames();
	updateStyles();
	updateScripts();
	updateImages();
	updateForms();
</script>

</body>
</html>