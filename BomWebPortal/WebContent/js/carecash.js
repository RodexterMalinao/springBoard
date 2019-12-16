function careCashTableControl(channelId, workStatus){
	 	 
		  var docType=$('#docType').val();
		  var foreignHelper=!$('#foreignDomesticHelperInd').is(":checked");
		  var studentPlan=!$('#studentPlanSubInd').is(":checked");
		  var noEmailFlag=!$('#noEmailFlag').is(":checked");
		  var dob=$('#dobDatepicker').val();
		  var dobStr=$('#dobStr').val();
		  var emailAddr=$('#emailAddr').val();
		  var careStatus = $('#careStatus').val();
		  var oBiPtStatus=$('#oBiptStatus').val();
 		  var oCareVisitInd=$('#oCareVisitInd').val();
		  var careCashRegisterTimeInd=$('#careCashRegisterTimeInd').val();
		  var today=new Date();
		  if (channelId!=10 && channelId!=11){
			  var dobDateArray=dob.split("/");
			  var dobDate=new Date();
			  var dobDateYear=dobDateArray[2];
			  dobDateYear=parseInt(dobDateYear)+81;
			  var dobDateMonth=dobDateArray[1]-1;
			  var dobDateDay=dobDateArray[0];
			  var dobLength=dob.length;
		  }else{
			  var dobDateArray=dobStr.split("/");
			  var dobDateYear=dobDateArray[2];
			  dobDateYear=parseInt(dobDateYear)+81;
			  var dobDateMonth=dobDateArray[1]-1;
			  var dobDateDay=dobDateArray[0];
			 var dobLength=dobStr.length;
		  }
		  var todayYear=today.getFullYear();
		  var todayMonth=today.getMonth()+1
		  var todayDay=today.getDate();
		  var careCashSubscribedButton=$('#careCashButtonInd').val();
		  
		  var dobDatePlus81=new Date();
		  dobDatePlus81.setFullYear(dobDateYear,dobDateMonth,dobDateDay);
		  if (oBiPtStatus==""||oBiPtStatus=="N"){
			 if (docType=="HKID" && foreignHelper && studentPlan && noEmailFlag && emailAddr.length>0  && dobLength>0 && oCareVisitInd==='true' && careCashRegisterTimeInd==='true'){
				  if (today.getTime()<dobDatePlus81.getTime()){
					  $('#careStatus').val('Y');
				  }else{
					  $('#careStatus').val('N');
				  }
				 }else{
					 $('#careStatus').val('N');
				 }
		  }else{
			  $('#careStatus').val('N');
		  }

		  if ($('#careStatus').val()=='N'){
			$('.iGuardTable').hide();
			$('#customercareDmSupInd').prop('checked', false);
			$('#careOptInd').prop('checked', false);
			$('#careCashUnsubscribed').prop('checked', false);
			$('#careCashNotConsider').prop('checked', false);
		  }
		  if ($('#careStatus').val()=='Y' && careCashOrderSignOffInd==='false'){
			  $('.iGuardTable').show();
			  $('.careCashPersonal').show();
			  $('.careCash').prop('disabled', false);
			  $('#customercareDmSupInd').prop('disabled', false);
			  if (careCashSubscribedButton=='I' || careCashSubscribedButton==''){
				  $('#careOptInd').val('I');
				  $('#careOptInd').prop('checked', true);
			  }
		  	}
		  }