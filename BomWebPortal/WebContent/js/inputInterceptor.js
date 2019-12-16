//inputInterceptor.js

var noChinMsg = "Invalid Character. No Chinese and Special Characters are allowed.";
var noSymMsg = "Invalid Character. No Symbols are allowed.";
var chinField = "noChinese";
var symField = "noSymbol";
var ErrorSuffix = "Error']";
var ErrorPreffix = "[class~='";
//var chinErrorField = ".noChineseError";
//var symErrorField = ".noSymbolError";
var regNoChin = /[^\u0000-\u00FF]/;
var regNoSym = /[^a-zA-Z0-9]/;
var record = [];


$(document).ready(function(){

	$("." + chinField).keypress(function(e)
	{
		popWarning(regNoChin, $(this).val(), noChinMsg, $(this).attr("id") + chinField);
		
	})
	.keyup( function(e)
	{
		popWarning(regNoChin, $(this).val(), noChinMsg, $(this).attr("id") + chinField);
		//deleteInput(regNoChin, $(this));
	})
	.blur(function()
	{
		popWarning(regNoChin, $(this).val(), noChinMsg, $(this).attr("id") + chinField);
	});
	/*.keydown(function(e)
	{
		if ( e.keyCode >= 229)
		{
			return false;
		}else
		{
			return true;
		}
	});*/
	
	
	
	
	$("." + symField).keyup(function(e)
	{
		popWarning(regNoSym, $(this).val(), noSymMsg, $(this).attr("id") + symField);
	}).blur(function()
	{
		popWarning(regNoSym, $(this).val(), noSymMsg, $(this).attr("id") + symField);
	});
	
	
});

function deleteChar(i,str)
{
	if ( i == str.length - 1 )
		return str.substring(0,i);
	else
		return str.substring(0,i) + str.substring(i+1);
}

function popWarning(reg,str,msg,msgField)
{
	if ( reg.test( str ) )
	{
		$(ErrorPreffix + msgField + ErrorSuffix).html(msg).show();
	}
	else
	{
		$(ErrorPreffix + msgField + ErrorSuffix).html() == msg ? $(ErrorPreffix + msgField + ErrorSuffix).html(""):"";	
	}
}

function deleteInput(reg, obj)
{
	while ( reg.test( obj.val() ) )
	{
		var i = obj.val().search(reg);
		obj.val( deleteChar(i , obj.val()) );
	}	
}