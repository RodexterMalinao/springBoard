var ltsterminatesalesinfo = function () {

	function imsSourceLkup(){
		var sbuid = $("[name='sbuid']").val();
		var mth = $("#imsApplicationMethod").val();
		if(mth != ''){
			$.ajax({
				url : "ltssaleslookup.html?sbuid=" + sbuid,
				type : 'POST',
				data : "T=IMS_SOURCE&parm=" + mth,
				success : function(data){
					var obj = jQuery.parseJSON(data);				
					if(obj != ''){
						if(obj.state == 0){
							if(obj.errorMsg != null && obj.errorMsg != ''){
								alert(obj.errorMsg);
							}
						}else{
							var output = [];
							output.push('<option value=""> -- SELECT --</option>');
							for (var i = 0; i< obj.length; i++) {
								output.push('<option value="'+ obj[i] +'">'+ obj[i] +'</option>');
							}
							$("#imsSource").html(output.join(''));
						}
					}
				},
				complete : function() {
					 $.unblockUI(); 
				},
				beforeSend : function() {
					 $.blockUI({ message: null }); 
				}
			});
		}
		
	}
	
	function actionPerform() {
		//imsSourceLkup();
		
		if("${isOrderSubmitted}" == "true"){
			$("#salesLkupBtn").attr("disabled", true);
		}else{
			$("#salesLkupBtn").attr("disabled", false);
		}
		
//		$("#staffName").attr("disabled", true);
//		$("#salesCode").attr("disabled", true);
//		$("#salesChannel").attr("disabled", true);
//		$("#salesTeam").attr("disabled", true);
		$("#salesContact").attr("readonly", isRetail);
//		$("#date").attr("readonly", true);
//		$("#time").attr("readonly", true);
		
		if(!isRetail){
			$(".ccRow").show();
			$(".retailField").attr("disabled", true).hide();
			$(".ccField").removeAttr("disabled").show();
			
			$("#date").datepicker({
				changeMonth: true,
				changeYear: true,
				showButtonPanel: true,
				dateFormat: 'dd/mm/yy', //'yy' is 4 digit in jquery UI, eg. 1920 
				maxDate: "0", //Y is year, M is month, D is day  
				minDate: "-1M"
			});
			
		}else{
			$(".ccRow").hide();
			$(".retailField").removeAttr("disabled").show();
			$(".ccField").attr("disabled", true).hide();
		}
		if(isCc){
			$(".ccdatetime").show();
		}else{
			$(".ccdatetime").hide();
		}
		
		$("#clearDateBtn").click(function(event){
			$("#date").val("");
		});
		
		//$("#salesLkupBtn").click(function(event) {
		$("#staffId").change(function(event) {
			
			var staffId = $("#staffId").val().toUpperCase();
			$("#staffId").val(staffId);
			var sbuid = $('input[name="sbuid"]').val();
			$.ajax({
				url : "ltssaleslookup.html?sbuid=" + sbuid,
				type : 'POST',
				data : "parm=" + staffId,
				success : function(data){
					var obj = jQuery.parseJSON(data);
					if(obj.state == 1){
						$("#staffName").val(obj.staffName);
						$("#salesCode").val(obj.salesCode);
						$("#boc").val(obj.boc);
					}else if(obj.state == 0){
						if(obj.errorMsg != null && obj.errorMsg != ''){
							alert(obj.errorMsg);
						}else{
							alert("Error occur when retrieving data with Staff ID '" + staffId + "'.");
						}
					}
					$("#errorMsg").val(obj.errorMsg);
				},
				complete : function() {
//					 $.unblockUI(); 
				},
				beforeSend : function() {
//					 $.blockUI({ message: null }); 
//					 $("#errorMsg").val('');
				}
			});
		});
		
		$("#nextBtn").click(function(event) {
			event.preventDefault();
			if($("#errorMsg").val() != ''){
				alert($("#errorMsg").val());
				return;
			}
			$("#staffId").attr("disabled", false);
			$("#staffName").attr("disabled", false);
			$("#salesCode").attr("disabled", false);
			$("#salesChannel").attr("disabled", false);
			$("#salesTeam").attr("disabled", false);
			$("#salesContact").attr("disabled", false);
			$("#terminatesalesinfoform").submit();
		});
		
	    $('form').bind('submit', function() {
//	    	$.blockUI({ message: null });
	    });
	    
	    $("#imsApplicationMethod").change(function(){
	    	imsSourceLkup();
	    });
	}
	
	return {
		actionPerform : actionPerform
	};
	
}();