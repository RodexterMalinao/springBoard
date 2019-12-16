var ltssalesinfo = function () {

	function imsSourceLkup(){
		var sbuid = $("[name='sbuid']").val();
		var mth = $("#imsApplicationMethod").val();
		var sourceCd = $("#sourceCode").val();
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
							if(!sourceCd && 0 === sourceCd.length){
								output.push('<option value=""> -- SELECT --</option>');
							}else{
								output.push('<option value="'+sourceCd+'">'+ sourceCd +'</option>');
							}
							for (var i = 0; i< obj.length; i++) {
								if(obj[i] != sourceCd){
									output.push('<option value="'+ obj[i] +'">'+ obj[i] +'</option>');
								}								
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
		
		if(!isRetail && !isDs){
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
		
		if(!modifySalesInfo){
			$("#staffId").attr("readonly", true);
			$("#salesContact").attr("readonly", true);
		}
		
		$("#clearDateBtn").click(function(event){
			$("#date").val("");
		});
		
		//$("#salesLkupBtn").click(function(event) {
		$("#staffId").change(function(event) {
			var staffId = $("#staffId").val().toUpperCase();
			$("#staffId").val(staffId);
			//var staffId = $("#staffId").val();
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
						$("#salesCenter").val(obj.salesCenter);
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
					 $.unblockUI(); 
				},
				beforeSend : function() {
					 $.blockUI({ message: null }); 
					 $("#errorMsg").val('');
				}
			});
		});
		
		$("#refereeSalesId").change(function(event) {
			var refereeSalesId = $("#refereeSalesId").val().toUpperCase();
			//var refereeSalesId = $("#refereeSalesId").val();
			
			if (refereeSalesId == null || refereeSalesId == '') {
				$("#errorMsg").val('');
				return;
			} 
			
			$("#refereeSalesId").val(refereeSalesId);
			
			var sbuid = $('input[name="sbuid"]').val();
			$.ajax({
				url : "ltssaleslookup.html?sbuid=" + sbuid,
				type : 'POST',
				data : "T=REFEREE&parm=" + refereeSalesId,
				success : function(data){
					var obj = jQuery.parseJSON(data);
					if(obj.state == 1){
						$("#refereeSalesName").val(obj.refereeName);
						$("#refereeSalesCenter").val(obj.refereeSalesCenter);
						$("#refereeSalesTeam").val(obj.refereeSalesTeam);
					}else if(obj.state == 0){
						if(obj.errorMsg != null && obj.errorMsg != ''){
							alert(obj.errorMsg);
						}else{
							alert("Error occur when retrieving data with Referee Sales ID '" + refereeSalesId + "'.");
						}
					}
					$("#errorMsg").val(obj.errorMsg);
				},
				complete : function() {
					 $.unblockUI(); 
				},
				beforeSend : function() {
					 $.blockUI({ message: null }); 
					 $("#errorMsg").val('');
				}
			});
		});
		
		
		
		$("#submit").click(function(event) {
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
			$("#salesinfoform").submit();
		});
		
	    $('form').bind('submit', function() {
	    	$.blockUI({ message: null });
	    	$(this).find(':input').removeAttr('disabled');
	    });
	    
	    $("#imsApplicationMethod").change(function(){
	    	imsSourceLkup();
	    });
	}
	
	return {
		actionPerform : actionPerform
	};
	
}();