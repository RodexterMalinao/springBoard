function retrieveAccountInfo(){
	
			var selected = $("input[name='acctType']:checked").val();
			
			if (selected == 'current') {
				
				loadAccountInfo();}
		}
		function loadAccountInfo() {
			var activeMobileNum = $('#activeMobileNum').val();
			var idDocNum = $('#idDocNum').val();
			
			
			var error = false;
			if (idDocNum == null || idDocNum == "" ) {
				$(".error_pattern").show();
				$(			
				"<p>Please input the document number.</p>")
				.dialog(
						{
							resizable : false,
							height : "200",
							width : "400",
							title : 'Account Information',
							modal : true,
							buttons : {
								"Confirm" : function() {
									$(this)
											.dialog(
													"close");
								}
							}
						});	
				error = true;}
			else if (activeMobileNum == null || activeMobileNum == "" ) {
				$(".error_pattern").show();	
				$(			
				"<p>Please input the Active Mobile No. </p>")
				.dialog(
						{
							resizable : false,
							height : "200",
							width : "400",
							title : 'Account Information',
							modal : true,
							buttons : {
								"Confirm" : function() {
									$(this)
											.dialog(
													"close");
								}
							}
						});	
				error = true;
			}else {
				var regex = /\d{2,}/;
				var regex2 = /^[0-9\*]*$/;
				if (activeMobileNum.match(regex)==null || activeMobileNum.match(regex2)==null ){
					$(			
					"<p>Please input number only. </p>")
					.dialog(
							{
								resizable : false,
								height : "200",
								width : "400",
								title : 'Account Information',
								modal : true,
								buttons : {
									"Confirm" : function() {
										$(this)
												.dialog(
														"close");
									}
								}
							});	
					error = true;
				}
			}
				
			if (error == true){
			
				return false;
				
			}else{
				
				$("#acctInfo").empty();
				
				$.ajax({
					url : 'bomaccountajax.html',
					type : 'POST',
					cache : false,
					dataType : 'JSON',
					data : {
						
						activeMobileNum : $("#activeMobileNum").val(),
							idDocType :  $("#docType").val(),
							idDocNum  :  $("#idDocNum").val(),
							brand      : $("input:radio[name=brand]:checked").val()
				
					},
					
					 error : function() {
						$(			
						"<p>Error searching Account Information! </p>")
						.dialog(
								{
									resizable : false,
									height : "200",
									width : "400",
									title : 'Account Information',
									modal : true,
									buttons : {
										"Confirm" : function() {
											$(this)
													.dialog(
															"close");
										}
									}
								});	
					}, 
					success : function(msg) {
						var result = msg.json;
						
					
						if (typeof result === 'undefined' || result.acctNum == null ) {
							
							$(			
							"<p>No record found. </p>")
							.dialog(
									{
										resizable : false,
										height : "200",
										width : "400",
										title : 'Account Information',
										modal : true,
										buttons : {
											"Confirm" : function() {
												$(this)
														.dialog(
																"close");
											}
										}
									});	
							return;
						}else if (result.isCreditExpiry == "Y"){
							$(			
							"<p>The credit card will be expiried soon. </p>")
							.dialog(
									{
										resizable : false,
										height : "200",
										width : "400",
										title : 'Account Information',
										modal : true,
										buttons : {
											"Confirm" : function() {
												$(this)
														.dialog(
																"close");
											}
										}
									});	
							return;
							
						}
							$("#acctInfo").val(result.acctNum+"-"+result.acctName);	
							$("#acctNum").val(result.acctNum);
							$("#billPeriod").val(result.billPeriod);
							$("#mobCustNum").val(result.mobCustNum);
							$("#acctName").val(result.acctName);	
							$("#bomCustNum").val(result.bomCustNum);
							$("#srvNum").val(result.srvNum);
										}
				});	
			}
		}
		
		function resetCombineAcctInfo() {
			$('#acctNum').val("");
			$('#billPeriod').val("");
			$('#bomCustNum').val("");
			$('#acctName').val("");
			$('#srvNum').val("");
			$('#mobCustNum').val("");
			$('#acctName').val("");
			$('#activeMobileNum').val("");
			$('#acctInfo').val("");	}
		
		function switchcase(accountAction){
			  switch(accountAction) {
	            case 'new':
	            	$('#activeMobileNum').attr('disabled', true);
					$('#acctInfo').attr('readonly', true);
					$('#retrieve').attr('disabled', true);
					$('#activeMobileNum,#acctInfo').val("");
					$("input[name='selectedBillMedia']").attr('disabled', false);
					$("input[name='noBillingAddressFlag']").attr('disabled', false);
	                break;
	            case 'current':
	            	$('#activeMobileNum').attr('disabled', false);
					$('#acctInfo').attr('readonly', true);
					$('#retrieve').attr('disabled', false);
					$('#selectedBillMedia1').attr('disabled', false);
					$("input[name='selectedBillMedia']").attr('disabled', true);
					$("input[name='noBillingAddressFlag']").attr('disabled', true);	
	                break;
	            default:
	            	break;
	        }
		}
		
		