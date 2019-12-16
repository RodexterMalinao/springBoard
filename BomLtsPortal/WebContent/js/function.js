// JavaScript Document
	var viewportHeight = $(window).height();


	$(window).resize(function() {
		var viewportHeight = $(window).height(); 
		$("#middle_content").css("min-height",viewportHeight-450+"px");
	}); 
  

	$(window).scroll(function(event) {
	   $("#floating_bar").css("margin-left", -$(document).scrollLeft());
	});
	 	
	$(function() { 
		if("EN"=="ZH") $(".logo").css('top','10px');
		$("#nextview").attr('onclick','formSubmit()');
		$("#middle_content").css("min-height",viewportHeight-450+"px");
		$("#middle_content").css("visibility","visible");
	}); 
	
   /* var temp_lang = "EN";                                     
    if (temp_lang=='ZH'){
     var liveChatUrl = "https://218.102.2.58/chat/index.jsp?c_t=NTK&c_id=Guest&m_n=VIP_COVER_CHK_PCD&l=zh&n_n=Guest ";
     }else{
      var liveChatUrl = "https://218.102.2.58/chat/index.jsp?c_t=NTK&c_id=Guest&m_n=VIP_COVER_CHK_PCD&l=en&n_n=Guest";
       }*/
    
	function rolloverlabel(id){
	}
    
	function rolloutlabel(id){
	}
    
    function hidetv(){
        $("#tvdetail").hide();
    }
    
    function showtv(){
        $("#tvdetail").show();
    }
    
    function showchannel(grp) {	
					var tempScrollTop = $(window).scrollTop();
					$(".HdActivation").attr('disabled',true);
					$('.channel_grp').attr('checked', false);
					$('.HdActivation').attr('checked', false);
					$('#' + grp).attr('checked', true);
					$('.combo').hide();
					$('.' + grp).fadeIn('quick', function () {});
					$('.comboradio').attr('checked',false);
					$(window).scrollTop(tempScrollTop);
					return false; 
			}
            
    function showdetail(id){
        if(id == 1){
            document.getElementById("detailtitle").innerHTML = "PCCW Touch Phone (value: $998)";
            document.getElementById("detailimage").src="images/OnlineRegistration/touch_phone.png";
        }else if(id == 2){
            document.getElementById("detailtitle").innerHTML = "Origo Multi-purpose Convection Roaster (KYR-913B) (value: $998)";
            document.getElementById("detailimage").src="images/OnlineRegistration/roaster.png";
        }else if(id == 3){
            document.getElementById("detailtitle").innerHTML = "Rasonic Compact Induction Cooker   (RIC-GB23(NP) (value: $528)";
            document.getElementById("detailimage").src="images/OnlineRegistration/induction_cooker.png";
        }else if(id == 4){
            document.getElementById("detailtitle").innerHTML = "$200 Wellcome Supermarket Coupon (value: $200)";
            document.getElementById("detailimage").src="images/OnlineRegistration/wellcome.png";
        }else if(id == 5){
            document.getElementById("detailtitle").innerHTML = "DBS Credit Card - $300 Wellcome Coupon  (value: $300)";
            document.getElementById("detailimage").src="images/OnlineRegistration/wellcome.png";
        }
        

        
        for(var i = 1; i <= 5; i++){
            if(i == id){
                $("#itemdetail"+id).show();
            }else{
                $("#itemdetail"+i).hide();
            }
        }
        rolloutlabel();
        $("#detailbox").fadeIn("fast");
        $("#greyout").fadeIn();
        $("#detailcontent").show();
    }
    
    function hidedetail(){
        $("#detailcontent").hide();
        $("#detailbox").fadeOut();
        $("#greyout").fadeOut();
    }
    
    function showcart(){
        $("#cartcontent").show();
        $("#showcartbtn").hide();
        $("#hidecartbtn").show();
    }
    
    function hidecart(){
        $("#cartcontent").hide();
        $("#showcartbtn").show();
        $("#hidecartbtn").hide();
    }
/*
function reg_open_chat(url){

    var reg_live_chat_link = url;

    reg_mywindow = window.open(reg_live_chat_link, "livechat", "height=650, width=800, location=no, scrollbars=no, menubar=no, status=no,toolbar=no");

    //window.open(reg_live_chat_link, "livechat", "height=650, width=800, location=no, scrollbars=no, menubar=no, status=no,toolbar=no");    

    reg_mywindow.moveTo(400, 100);

}*/
			var viewportHeight = $(window).height();

			$(window).resize(function () {
				var viewportHeight = $(window).height();
				$("#middle_content").css("min-height", viewportHeight - 450 + "px");
			});


			$(window).scroll(function (event) {
				$("#floating_bar").css("margin-left", -$(document).scrollLeft());
			});
 
			function formSubmit() {
				var vasselected = [];
				var groupselected =[];
				var hdselected = [];
				var entselected = []; 
				  
				 $("input:checked").each(function() {
					 if($(this).hasClass('vasbox'))vasselected.push($(this).attr('id'));
					 if($(this).hasClass('comboradio'))vasselected.push($(this).attr('id'));
					 if($(this).hasClass('comboradio'))groupselected.push($(this).attr('value'));
					 if($(this).hasClass('HdActivation'))vasselected.push($(this).attr('id'));
					 if($(this).hasClass('ent'))entselected.push($(this).attr('id'));
				});
				vasselected = vasselected.join(',');
				groupselected = groupselected.join(',');
				hdselected = hdselected.join(',');
				entselected = entselected.join(',');
				
				//check if at least one ent pack is picked
				var nowtvfree = $(".NTV_FREE"); 
				if($(nowtvfree).attr('checked')=="checked" && entselected=="" && $(".ent")[0]) {
					//alert("Please choose at least one entertainment pack");
					$('.ent_warn').fadeIn('quick');
					$('html,body').animate({
				        scrollTop: $('.ent_warn').offset().top+30},
				        'slow');
					return;
				}
				
				if($(".adultbox").attr('checked')=="checked") {$("#isAudltAllow").val("Y");}
				else $("#isAudltAllow").val("N");
				
				var vasarray = vasselected.split(",");
				var validAttb="Y";
				for (var i = 0; i < vasarray.length; i++) {
					if(checkMobile(vasarray[i])=="N") validAttb = "N";
				} 
				if (validAttb!="Y") {
					$('html, body').animate({scrollTop:0}, 'slow');
					return;
					}
				
				$("#vasSelected").val(vasselected);
				$("#groupSelected").val(groupselected);
				$("#hdSelected").val(hdselected);
				$("#entSelected").val(entselected);
				
				document.submitForm.submit();
			}

			$(function () {
				$("#nextview").attr('onclick', 'formSubmit()');
				$("#middle_content").css("min-height", viewportHeight - 450 + "px");
				$("#middle_content").css("visibility", "visible");
			});
 
			$(function () {
				$(".ent").change(function () {
					$('.ent_warn').hide();
					$(".NTV_FREE").attr('checked','checked');
				});
				$(".comboradio").change(function () {
					$(".NTV_FREE").attr('checked','checked');
				});
				$(".NTV_FREE").change(function () {
					if($(".NTV_FREE").attr('checked')!="checked"){
						//$(".ntvFreeRadio").attr('checked',false);
						$("input").each(function() {
							$(this).attr('checked',false);
						});
						$(".combo").each(function(){
							$(this).hide();
						});
					}
				}); 
				$(".HdActivation").each(function() { 
					var comboradio = $(this).parents(".combo").find('.comboradio');
					//alert($(comboradio).attr('class'));
				    if(!($(comboradio).is(':checked')))$(this).attr("disabled", true);
				});

				$(".attb_input").each(function() {
				    if($(this).val().length == 0) $(this).attr("disabled", true); 
				});
				
			});

			function show(input) {
				if ($(input).parents(".vas_row").children(".vas_detail").css('display') == 'none') {
					$(input).parents(".vas_row").children(".vas_detail").fadeIn('quick', function () {});
					$(input).parents(".vas_title").children("a").html("-");
				} else {
					$(input).parents(".vas_row").children(".vas_detail").hide();
					$(input).parents(".vas_title").children("a").html("+");
					$('.combo').hide();
					$('.channel_grp').attr('checked', false);
				}
			}

            function showvas(input) {
                    if ($(input).parents(".vas_row").children(".vas_detail").css('display') == 'none') {
                        $(input).parents(".vas_row").children(".vas_detail").fadeIn('quick', function () {});
                        $(input).parents(".vas_title").children("a").html("-");
                    } else {
                        $(input).parents(".vas_row").children(".vas_detail").hide();
                        $(input).parents(".vas_title").children("a").html("+");
                        $('.combo').hide();
                        $('.channel_grp').attr('checked', false);
                    }
            }
                
			function clickradio(input) {
				if ($(input).attr('checked')) {
					$(input).parents(".vas_row").children(".vas_detail").fadeIn('quick', function () {});
					$(input).parents(".vas_title").children("a").html("-"); 
					$(input).parents(".vas_row").children(".vas_detail").find(".attb_input").attr("disabled", false);
				}   
				else {  
					$(input).parents(".vas_row").children(".vas_detail").find(".attb_input").attr("disabled", true);
					$(input).parents(".vas_row").children(".vas_detail").find(".attb_input").val("");
					$(input).parents(".vas_row").children(".vas_detail").find(".attb_warn").css("display", "none");
				}
			}

			function collapse(opt) {
				if (opt == "E") {
					$(".vas_detail").fadeIn('quick', function () {});
					$(".vas_title").children("a").html("-");
				} else if (opt == "C") {
					$(".vas_detail").hide();
					$(".vas_title").children("a").html("+");
				}
			}

			function showchannel(grp) {	
					var tempScrollTop = $(window).scrollTop();
					$(".HdActivation").attr('disabled',true);
					$('.channel_grp').attr('checked', false);
					$('.HdActivation').attr('checked', false);
					$('#' + grp).attr('checked', true);
					$('.combo').hide();
					$('.' + grp).fadeIn('quick', function () {});
					$('.comboradio').attr('checked',false);
					$(window).scrollTop(tempScrollTop);
					return false; 
			}

			function showEntdesc(button) {
				$('.EntPackDesc').hide();
				$(button).parents('.EntPackTitle').next().fadeIn('quick', function () {});
			}


			function activateHD(tag) {
				if ($(tag).is(":checked")) {
					$(tag).parent().find("a").fadeIn('quick', function () {});
				} else {
					$(tag).parent().find("a").hide();
				}
			}

			function _showCombodetail(button) {
				if ($(button).parents('.combotitle').next().css('display') == 'none') {
					$(button).parents('.combotitle').next().fadeIn('quick', function () {});
					$(button).html('-');
					$(button).parents('.combotitle').next().find("span").hide();
				} else {
					$(button).parents('.combotitle').next().hide();
					$(button).html('+');
				}
			}

			function showCombodetail(button) {
				$(button).parents('.combotitle').next().fadeIn('quick', function () {});
				$(button).parents('.combotitle').find("a").html('-');
				$(button).parents('.combotitle').next().find("span").hide();
			}

			function changeChannelDesc(channel) {
				$(channel).parent().find("span").hide();
				$(channel).parent().find("span").fadeIn('quick', function () {});
				$(channel).parent().find("span").html("changed to " + $(channel).html() + " description<br>");
			}
			 
			function changecombo(combo){
				$('.HdActivation').attr('checked', false);
				$('.HdActivation').attr("disabled", true);
				$(combo).parents(".combo").find('.HdActivation').attr("disabled", false);
			}
			
			function isInt(x) 
			 { 
			    var y=parseInt(x); 
			    if (isNaN(y)) return false; 
			    return x==y && x.toString()==y.toString(); 
			 } 
			
			function checkMobile(ItemID){
				
				var valid;
				var i; 
				var mobileNum;
				if(document.getElementById("AttbWarn"+ItemID)!=null){
					document.getElementById("AttbWarn"+ItemID).style.display = "none";
				}
				if(document.getElementById("attbInput"+ItemID)!=null){
					mobileNum = document.getElementById("attbInput"+ItemID).value;
				}
				if(mobileNum!=null){
					if(mobileNum.substring(0, 1)=="9" 
							|| mobileNum.substring(0, 1)=="6" 
							|| mobileNum.substring(0, 1)=="5"){
			    		valid = "Y";
			    		document.getElementById("AttbWarn"+ItemID).style.display = "none";
			    	}else{
			    		valid = "N";
			    		document.getElementById("AttbWarn"+ItemID).style.display = "inline";
			    	}
					
					if(mobileNum.length!= 8){
						valid = "N";
			    		document.getElementById("AttbWarn"+ItemID).style.display = "inline";
			    	}
			    	
			    	if(mobileNum != null || mobileNum!=""){
			    		for(i=0; i<mobileNum.length; i++){
			                if(!isInt(mobileNum.charAt(i))){
			                	valid = "N";
			                	document.getElementById("AttbWarn"+ItemID).style.display = "inline";
			                }
			    		}
			    	}
				}
				return valid;
			}