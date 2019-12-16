/*
 * Autocomplete - jQuery plugin 1.1pre
 *
 * Copyright (c) 2007 Dylan Verheul, Dan G. Switzer, Anjesh Tuladhar, Jörn Zaefferer
 *
 * Dual licensed under the MIT and GPL licenses:
 *   http://www.opensource.org/licenses/mit-license.php
 *   http://www.gnu.org/licenses/gpl.html
 *
 * Revision: $Id: jquery.autocomplete.js 5785 2008-07-12 10:37:33Z joern.zaefferer $
 *
 */

;(function($) {
	
$.fn.extend({
	autocomplete: function(urlOrData, toggle_btn, options) {
		var isUrl = typeof urlOrData == "string";
		options = $.extend({}, $.Autocompleter.defaults, {
			url: isUrl ? urlOrData : null,
			data: isUrl ? null : urlOrData,
			delay: isUrl ? $.Autocompleter.defaults.delay : 10,
			max: options && !options.scroll ? 10 : 150
		}, options);
		// if highlight is set to false, replace it with a do-nothing function
		options.highlight = options.highlight || function(value) { return value; };
		
		// if the formatMatch option is not specified, then use formatItem for backwards compatibility
		options.formatMatch = options.formatMatch || options.formatItem;
		
		return this.each(function() {
			new $.Autocompleter(this, toggle_btn, options);
		});
	},
	result: function(handler) {
		return this.bind("result", handler);
	},
	search: function(handler) {
		return this.trigger("search", [handler]);
	},
	flushCache: function() {
		return this.trigger("flushCache");
	},
	setOptions: function(options){
		return this.trigger("setOptions", [options]);
	},
	unautocomplete: function() {
		return this.trigger("unautocomplete");
	}
});

$.Autocompleter = function(input, toggle_btn, options) {
	var KEY = {
		UP: 38,
		DOWN: 40,
		DEL: 46,
		TAB: 9,
		RETURN: 13,
		ESC: 27,
		COMMA: 188,
		PAGEUP: 33,
		PAGEDOWN: 34,
		BACKSPACE: 8
	};
	// Create $ object for input element
	var $input = $(input).attr("autocomplete", "off").addClass(options.inputClass);
	var $toggle_btn = $(toggle_btn);
	var blurOnSelect = false;
	var timeout;
	var previousValue = "";
	var cache = $.Autocompleter.Cache(options);
	var hasFocus = 0;
	var lastKeyPressCode;
	var config = {
		mouseDownOnSelect: false
	};
	var select = $.Autocompleter.Select(options, input, selectCurrent, config);
	
	var blockSubmit;
	
	/*******************************************************/
	/******************** Custom code **********************/
	var total_cnt;
	var distDescEn;
	var distDescCh;
	var areaDescEn;
	var areaDescCh;
	var sectDescEn;
	var sectDescCh;
	var housingAddrEn;
	var housingAddrCh;
	var lat;
	var lng;
	var markerIdx;
	var btnClick = 0;
	
	/*******************************************************/
	/*******************************************************/
	// prevent form submit in opera when selecting with return key
	$.browser.opera && $(input.form).bind("submit.autocomplete", function() {
		if (blockSubmit) {
			blockSubmit = false;
			return false;
		}
	});
	
	$toggle_btn.mouseup(function(){
			btnClick--;
			$input.focus();
		}
	).mousedown(function(){
		btnClick++;
	}
	).click(function (){
		hasFocus++;
		onChange(0, true);
	});
	
	
	// only opera doesn't trigger keydown multiple times while pressed, others don't work with keypress at all
	$input.bind(($.browser.opera ? "keypress" : "keydown") + ".autocomplete", function(event) {
		// track last key pressed
		lastKeyPressCode = event.keyCode;
		switch(event.keyCode) {
		
			case KEY.UP:
				event.preventDefault();
				if ( select.visible() ) {
					select.prev();
				} else {
					onChange(0, true);
				}
				break;
				
			case KEY.DOWN:
				event.preventDefault();
				if ( select.visible() ) {
					select.next();
				} else {
					onChange(0, true);
				}
				break;
				
			case KEY.PAGEUP:
				event.preventDefault();
				if ( select.visible() ) {
					select.pageUp();
				} else {
					onChange(0, true);
				}
				break;
				
			case KEY.PAGEDOWN:
				event.preventDefault();
				if ( select.visible() ) {
					select.pageDown();
				} else {
					onChange(0, true);
				}
				break;
			
			// matches also semicolon
			case options.multiple && $.trim(options.multipleSeparator) == "," && KEY.COMMA:
			case KEY.TAB:
			case KEY.RETURN:
				if( selectCurrent() ) {
					// stop default to prevent a form submit, Opera needs special handling
					event.preventDefault();
					blockSubmit = true;
					return false;
				}
				break;
				
			case KEY.ESC:
				select.hide();
				break;
				
			default:
				clearTimeout(timeout);
				timeout = setTimeout(onChange, options.delay);
				break;
		}
	}).focus(function(){
		// track whether the field has focus, we shouldn't process any
		// results if the field no longer has focus
		hasFocus++;
	}).blur(function() {
		hasFocus = 0;
		if (!config.mouseDownOnSelect && btnClick==0) {
			hideResults();
		}
	}).click(function() {
		// show select when clicking in a focused field
		blurOnSelect = false;
		if ( hasFocus++ > 1 && !select.visible() ) {
			onChange(0, true);
		}
	}).bind("search", function() {
		// TODO why not just specifying both arguments?
		var fn = (arguments.length > 1) ? arguments[1] : null;
		function findValueCallback(q, data) {
			var result;
			if( data && data.length ) {
				for (var i=0; i < data.length; i++) {
					if( data[i].result.toLowerCase() == q.toLowerCase() ) {
						result = data[i];
						break;
					}
				}
			}
			if( typeof fn == "function" ) fn(result);
			else $input.trigger("result", result && [result.data, result.value]);
		}
		$.each(trimWords($input.val()), function(i, value) {
			request(value, findValueCallback, findValueCallback);
		});
	}).bind("flushCache", function() {
		cache.flush();
	}).bind("setOptions", function() {
		$.extend(options, arguments[1]);
		// if we've updated the data, repopulate
		if ( "data" in arguments[1] )
			cache.populate();
	}).bind("unautocomplete", function() {
		select.unbind();
		$input.unbind();
		$(input.form).unbind(".autocomplete");
	});
	
	
	function selectCurrent() {
		var floorChange = "N";
		
		var selected = select.selected();
		if( !selected )
			return false;
		/*****************************************************/
		var selectedIndex = selected.value;	
		goToMarker(lat[selectedIndex], lng[selectedIndex], markerIdx[selectedIndex]);
		
		/*****************************************************/
//		var v = selected.result;
//		previousValue = v;
//		
//		if ( options.multiple ) {
//			var words = trimWords($input.val());
//			if ( words.length > 1 ) {
//				v = words.slice(0, words.length - 1).join( options.multipleSeparator ) + options.multipleSeparator + v;
//			}
//			v += options.multipleSeparator;
//		}
//		
//		$input.val(v);
		blurOnSelect = true;
		hideResultsNow();
//		$input.trigger("result", [selected.data, selected.value]);
		return true;
	}
	
	function onChange(crap, skipPrevCheck) {
		if( lastKeyPressCode == KEY.DEL ) {
			select.hide();
			return;
		}
		//antony
		// add area, district and section for searching
		blurOnSelect = false;
		var currentValue = $input.val();
//		currentValue = currentValue.trim();
		
		//if ( !skipPrevCheck && currentValue == previousValue )
		//	return;
		
		previousValue = currentValue;
		
		currentValue = lastWord(currentValue);
		if ( currentValue.length >= options.minChars) {
			$input.addClass(options.loadingClass);
			if (!options.matchCase)
				currentValue = currentValue.toLowerCase();
			request(currentValue, receiveData, hideResultsNow);
		} else {
			stopLoading();
			select.hide();
		}
	};
	
	function trimWords(value) {
		if ( !value ) {
			return [""];
		}
		var words = value.split( options.multipleSeparator );
		var result = [];
		$.each(words, function(i, value) {
			if ( $.trim(value) )
				result[i] = $.trim(value);
		});
		return result;
	}
	
	function lastWord(value) {
		if ( !options.multiple )
			return value;
		var words = trimWords(value);
		return words[words.length - 1];
	}
	
	// fills in the input box w/the first match (assumed to be the best match)
	// q: the term entered
	// sValue: the first matching result
	function autoFill(q, sValue){
		// autofill in the complete box w/the first match as long as the user hasn't entered in more data
		// if the last user key pressed was backspace, don't autofill
		if( options.autoFill && (lastWord($input.val()).toLowerCase() == q.toLowerCase()) && lastKeyPressCode != KEY.BACKSPACE ) {
			// fill in the value (keep the case the user has typed)
			$input.val($input.val() + sValue.substring(lastWord(previousValue).length));
			// select the portion of the value not typed by the user (so the next character will erase)
			$.Autocompleter.Selection(input, previousValue.length, previousValue.length + sValue.length);
		}
	};

	function hideResults() {
		clearTimeout(timeout);
		timeout = setTimeout(hideResultsNow, 200);
	};

	function hideResultsNow() {
		var wasVisible = select.visible();
		select.hide();
		clearTimeout(timeout);
		stopLoading();
		if (options.mustMatch) {
			// call search and run callback
			$input.search(
				function (result){
					// if no value found, clear the input box
					if( !result ) {
						if (options.multiple) {
							var words = trimWords($input.val()).slice(0, -1);
							$input.val( words.join(options.multipleSeparator) + (words.length ? options.multipleSeparator : "") );
						}
						else
							$input.val( "" );
					}
				}
			);
		}
		if (wasVisible)
			// position cursor at end of input field
			$.Autocompleter.Selection(input, input.value.length, input.value.length);
		if (blurOnSelect)
			$input.blur();
	};

	function receiveData(q, data) {
//		if ( data && data.length && hasFocus ) {
		if ( data && hasFocus ) {
			stopLoading();
			select.display(data, q, total_cnt);
			if (data.length>0){
				autoFill(q, data[0].value);
			}
			select.show();
		} else {
			hideResultsNow();
//			document.getElementById("quickSearchMsg").innerHTML = "No record matched.";
		}
	};
	
	function request(term, success, failure) {
		if (!options.matchCase)
			term = term.toLowerCase();
		var data = cache.load(term);
		// recieve the cached data
		if (data && data.length) {
			success(term, data);
		// if an AJAX url has been supplied, try loading the data now
		} else if( (typeof options.url == "string") && (options.url.length > 0) ){
			var extraParams = {
				timestamp: +new Date()
			};
			$.each(options.extraParams, function(key, param) {
				extraParams[key] = typeof param == "function" ? param() : param;
			});
			
			$.ajax({
				// try to leverage ajaxQueue plugin to abort previous requests
				mode: "abort",
				// limit abortion to this input
				port: "autocomplete" + input.name,
				dataType: options.dataType,
				url: options.url,
				data: $.extend({
					keyword: encodeURIComponent(lastWord(term)),
					limit: options.max
				}, extraParams),
				success: function(data) {
					try {
						var parsed = options.parse && options.parse(data) || parse(data);
						cache.add(term, parsed);
						success(term, parsed);
					} catch (e) {
						alert("Your session has been timed out, please re-login.");
					}
	
				}
			});
		} else {
			// if we have a failure, we need to empty the list -- this prevents the the [TAB] key from selecting the last successful match
			select.emptyList();
			failure(term);
		}
	};
	
	function parse(data) {
	
		var parsed = [];			
		var obj_json = jQuery.parseJSON(data);
//		var i = 0;
		if (!obj_json[0].totalRecord){
			total_cnt = 0;
			return parsed;
		}else{
			total_cnt = obj_json[0].totalRecord;
			var obj = obj_json[0].addressList;
			distDescEn = new Array();
			distDescCh = new Array();
			areaDescEn = new Array();
			areaDescCh = new Array();
			sectDescEn = new Array();
			sectDescCh = new Array();
			housingAddrEn = new Array();
			housingAddrCh = new Array();
			lat = new Array();
			lng = new Array();
			markerIdx = new Array();
			if (obj_json[0].addressList != "[null]") {
				$(obj).each(function(index){
					address = getAddress(obj[index]);
					parsed[parsed.length] = {
						data: address,
						value: index,
						result: address
					};
					housingAddrEn[index] = obj[index].housingAddrEn;
					housingAddrCh[index] = obj[index].housingAddrCh;
					sectDescEn[index] = obj[index].sectDescEn;
					sectDescCh[index] = obj[index].sectDescCh;
					distDescEn[index] = obj[index].distDescEn;
					distDescCh[index] = obj[index].distDescCh;
					areaDescEn[index] = obj[index].areaDescEn;
					areaDescCh[index] = obj[index].areaDescCh;
					lat[index] = obj[index].lat;
					lng[index] = obj[index].lng;
					markerIdx[index] = obj[index].markerIdx;
					
					
					//alert(">" + areaSearchInput.trim() + "<>" + areaCdSearchInput.trim() + "<");
	//				if ((areaCdSearchInput.trim() == "" || (areaCdSearchInput.trim() != "" && areaCdSearchInput.trim() == obj[index].areaCode)) &&
	//					(districtSearchInput.trim() == "" || (districtSearchInput.trim() != "" && districtSearchInput.trim() == obj[index].districtDesc)) &&	
	//					(sectionSearchInput.trim() == "" || (sectionSearchInput.trim() != "" && sectionSearchInput.trim() == obj[index].sectionDesc))) {
	//						parsed[parsed.length] = {
	//								data: obj[index].address,
	//								value: i,
	//								result: obj[index].address
	//						};
	//						areaDesc[i] = obj[index].areaDesc;
	//						areaCode[i] = obj[index].areaCode;					
	//						buildingName[i] = obj[index].buildingName;					
	//						districtCode[i] = obj[index].districtCode;					
	//						districtDesc[i] = obj[index].districtDesc;					
	//						houseLotNum[i] = obj[index].houseLotNum;					
	//						lotHouseInd[i] = obj[index].lotHouseInd;;					
	//						lotNum[i] = obj[index].lotNum;
	//						sectionCode[i] = obj[index].sectionCode;		
	//						sectionDesc[i] = obj[index].sectionDesc;					
	//						streetCatgCode[i] = obj[index].streetCatgCode;					
	//						streetCatgDesc[i] = obj[index].streetCatgDesc;					
	//						streetName[i] = obj[index].streetName;
	//						serviceBoundaryNum[i] = obj[index].serviceBoundaryNum;
	//						flat[i] = obj[index].flat;
	//						floor[i] = obj[index].floor;
	//						i++;
	//				} else {
	//					//alert("address not match, area input >" + areaSearchInput.trim() + "<, address area >" + obj[index].areaDesc + "<" );
	//				}
				});
				return parsed;
			} else {
				return null;
			}
		}
	};
	
	function getAddress(obj){
		var address="";
		if (lang=="en"){
			if (obj.housingAddrEn && obj.housingAddrEn!="null"){
				address += obj.housingAddrEn +", ";
			}
			if (obj.sectDescEn && obj.sectDescEn!="null"){
				address += obj.sectDescEn +", ";
			}
			if (obj.distDescEn && obj.distDescEn!="null"){
				address += obj.distDescEn +", ";
			}
			if (obj.areaDescEn && obj.areaDescEn!="null"){
				address += obj.areaDescEn +", ";
			}
			address = address.substring(0, address.lastIndexOf(","));
		}else{
			if (obj.areaDescCh && obj.areaDescCh!="null"){
				address += obj.areaDescCh;
			}
			if (obj.distDescCh && obj.distDescCh!="null"){
				address += obj.distDescCh;
			}
			if (obj.sectDescCh && obj.sectDescCh!="null"){
				address += obj.sectDescCh;
			}
			if (obj.housingAddrCh && obj.housingAddrCh!="null"){
				address += obj.housingAddrCh; 
			}
		}
		return address; 
	}

	function stopLoading() {
		
		$input.removeClass(options.loadingClass);
	};

};

$.Autocompleter.defaults = {
	inputClass: "ac_input",
	resultsClass: "ac_results",
	loadingClass: "ac_loading",
	minChars: 3,
	delay: 400,
	matchCase: false,
	matchSubset: true,
	matchContains: false,
	cacheLength: 1,
	max: 100,
	mustMatch: false,
	extraParams: {},
	selectFirst: true,
	formatItem: function(row) { return row[0]; },
	formatMatch: null,
	autoFill: false,
	width: 400,
	multiple: false,
	multipleSeparator: ", ",
	highlight: function(value, term) {
		return value.replace(new RegExp("(?![^&;]+;)(?!<[^<>]*)(" + term.replace(/([\^\$\(\)\[\]\{\}\*\.\+\?\|\\])/gi, "\\$1") + ")(?![^<>]*>)(?![^&;]+;)", "gi"), "<strong>$1</strong>");
	},
	scroll: false,
    scrollHeight: ""
};

$.Autocompleter.Cache = function(options) {

	var data = {};
	var length = 0;
	
	
	function matchSubset(s, sub) {
		if (!options.matchCase) 
			s = s.toLowerCase();
		var i = s.indexOf(sub);
		if (options.matchContains == "word"){
			i = s.toLowerCase().search("\\b" + sub.toLowerCase());
		}
		if (i == -1) return false;
		return i == 0 || options.matchContains;
	};
	
	function add(q, value) {
		if (length > options.cacheLength){
			flush();
		}
		if (!data[q]){ 
			length++;
		}
		data[q] = value;
	}
	
	function populate(){
		if( !options.data ) return false;
		// track the matches
		var stMatchSets = {},
			nullData = 0;

		// no url was specified, we need to adjust the cache length to make sure it fits the local data store
		if( !options.url ) options.cacheLength = 1;
		
		// track all options for minChars = 0
		stMatchSets[""] = [];
		
		// loop through the array and create a lookup structure
		for ( var i = 0, ol = options.data.length; i < ol; i++ ) {
			var rawValue = options.data[i];
			// if rawValue is a string, make an array otherwise just reference the array
			rawValue = (typeof rawValue == "string") ? [rawValue] : rawValue;
			
			var value = options.formatMatch(rawValue, i+1, options.data.length);
			if ( value === false )
				continue;
				
			var firstChar = value.charAt(0).toLowerCase();
			// if no lookup array for this character exists, look it up now
			if( !stMatchSets[firstChar] ) 
				stMatchSets[firstChar] = [];

			// if the match is a string
			var row = {
				value: value,
				data: rawValue,
				result: options.formatResult && options.formatResult(rawValue) || value
			};
			
			// push the current match into the set list
			stMatchSets[firstChar].push(row);

			// keep track of minChars zero items
			if ( nullData++ < options.max ) {
				stMatchSets[""].push(row);
			}
		};

		// add the data items to the cache
		$.each(stMatchSets, function(i, value) {
			// increase the cache size
			options.cacheLength++;
			// add to the cache
			add(i, value);
		});
	}
	
	// populate any existing data
	setTimeout(populate, 25);
	
	function flush(){
		data = {};
		length = 0;
	}
	
	return {
		flush: flush,
		add: add,
		populate: populate,
		load: function(q) {
			if (!options.cacheLength || !length)
				return null;
			/* 
			 * if dealing w/local data and matchContains than we must make sure
			 * to loop through all the data collections looking for matches
			 */
			if( !options.url && options.matchContains ){
				// track all matches
				var csub = [];
				// loop through all the data grids for matches
				for( var k in data ){
					// don't search through the stMatchSets[""] (minChars: 0) cache
					// this prevents duplicates
					if( k.length > 0 ){
						var c = data[k];
						$.each(c, function(i, x) {
							// if we've got a match, add it to the array
							if (matchSubset(x.value, q)) {
								csub.push(x);
							}
						});
					}
				}				
				return csub;
			} else 
			// if the exact item exists, use it
			if (data[q]){
				return data[q];
			} else
			if (options.matchSubset) {
				for (var i = q.length - 1; i >= options.minChars; i--) {
					var c = data[q.substr(0, i)];
					if (c) {
						var csub = [];
						$.each(c, function(i, x) {
							if (matchSubset(x.value, q)) {
								csub[csub.length] = x;
							}
						});
						return csub;
					}
				}
			}
			return null;
		}
	};
};

$.Autocompleter.Select = function (options, input, select, config) {
	var CLASSES = {
		ACTIVE: "ac_over"
	};
	
	var listItems,
		active = -1,
		data,
		term = "",
		needsInit = true,
		element,
		list,
		summary_list;
	
	// Create results
	function init() {
		if (!needsInit)
			return;
		element = $("<div/>")
		.hide()
		.addClass(options.resultsClass)
		.css("position", "absolute")
		.appendTo(document.body);
	
		summary_list = $("<ul/>").appendTo(element)
		.mousedown(function() {
			config.mouseDownOnSelect = true;
		}).mouseup(function() {
			config.mouseDownOnSelect = false;
		});
	
		list = $("<ul/>").appendTo(element).mouseover( function(event) {
			if(target(event).nodeName && target(event).nodeName.toUpperCase() == 'LI') {
	            active = $("li", list).removeClass(CLASSES.ACTIVE).index(target(event));
			    $(target(event)).addClass(CLASSES.ACTIVE);            
	        }
		}).click(function(event) {
			$(target(event)).addClass(CLASSES.ACTIVE);
			select();
			// TODO provide option to avoid setting focus again after selection? useful for cleanup-on-focus
//			input.focus();
			return false;
		}).mousedown(function() {
			config.mouseDownOnSelect = true;
		}).mouseup(function() {
			config.mouseDownOnSelect = false;
		});
		
		if( options.width > 0 )
			element.css("width", options.width);
			
		needsInit = false;
	} 
	
	function target(event) {
		var element = event.target;
		while(element && element.tagName != "LI")
			element = element.parentNode;
		// more fun with IE, sometimes event.target is empty, just ignore it then
		if(!element)
			return [];
		return element;
	}

	function moveSelect(step) {
		listItems.slice(active, active + 1).removeClass(CLASSES.ACTIVE);
		movePosition(step);
        var activeItem = listItems.slice(active, active + 1).addClass(CLASSES.ACTIVE);
        if(options.scroll) {
            var offset = 0;
            listItems.slice(0, active).each(function() {
				offset += this.offsetHeight;
			});
            if((offset + activeItem[0].offsetHeight - list.scrollTop()) > list[0].clientHeight) {
                list.scrollTop(offset + activeItem[0].offsetHeight - list.innerHeight());
            } else if(offset < list.scrollTop()) {
                list.scrollTop(offset);
            }
        }
	};
	
	function movePosition(step) {
		active += step;
		if (active < 0) {
			active = listItems.size() - 1;
		} else if (active >= listItems.size()) {
			active = 0;
		}
	}
	
	function limitNumberOfItems(available) {
		return options.max && options.max < available
			? options.max
			: available;
	}
	
	function fillList() {
		summary_list.empty();
		list.empty();
		var max = limitNumberOfItems(data.length);
		
		$("<li/>").html(options.formatItem("<div style='float:left;'>"+keyword_sel_addr+"</div><div style='float:right;'>"+$(input).val()+" ("+total_cnt+keyword_result_no+")</div>", 1, 2, "", term)).addClass("ac_cnt_even").appendTo(summary_list)[0];
		$("<li/>").html(options.formatItem(keyword_srh_title, 2, 2, "", term)).addClass("ac_cnt_odd").appendTo(summary_list)[0];
//		$.data(cnt_li, "ac_data", total_cnt);
		for (var i=0; i < max; i++) {
			if (!data[i])
				continue;
			var formatted = options.formatItem(data[i].data, i+1, max, data[i].value, term);
			if ( formatted === false )
				continue;
			var li = $("<li/>").html( options.highlight(formatted, term) ).addClass(i%2 == 0 ? "ac_even" : "ac_odd").appendTo(list)[0];
			$.data(li, "ac_data", data[i]);
		}
		listItems = list.find("li");
		if ( options.selectFirst ) {
			listItems.slice(0, 1).addClass(CLASSES.ACTIVE);
			active = 0;
		}
		// apply bgiframe if available
		if ( $.fn.bgiframe ){
			summary_list.bgiframe();
			list.bgiframe();
		}
	}
	
	return {
		display: function(d, q, cnt) {
			init();
			data = d;
			total_cnt = cnt;
			term = q;
			fillList();
		},
		next: function() {
			moveSelect(1);
		},
		prev: function() {
			moveSelect(-1);
		},
		pageUp: function() {
			if (active != 0 && active - 8 < 0) {
				moveSelect( -active );
			} else {
				moveSelect(-8);
			}
		},
		pageDown: function() {
			if (active != listItems.size() - 1 && active + 8 > listItems.size()) {
				moveSelect( listItems.size() - 1 - active );
			} else {
				moveSelect(8);
			}
		},
		hide: function() {
			element && element.hide();
			listItems && listItems.removeClass(CLASSES.ACTIVE);
			active = -1;
		},
		visible : function() {
			return element && element.is(":visible");
		},
		current: function() {
			return this.visible() && (listItems.filter("." + CLASSES.ACTIVE)[0] || options.selectFirst && listItems[0]);
		},
		show: function() {
			var offset = $(input).offset();
			var myWidth =0;
			var myHeight = 0;
			if($.browser.msie && parseInt($.browser.version, 10) == 7){
				myHeight = offset.top + input.offsetHeight;
				myWidth = (offset.left + $(input).outerWidth(false) - options.width + 23);
			}else{
				myHeight = offset.top + input.offsetHeight + 3;
				myWidth = (offset.left + $(input).outerWidth(false) - options.width + 25);
			}
			element.css({
				width: typeof options.width == "string" || options.width > 0 ? options.width : $(input).width(),
				top: myHeight,
//				left: offset.left
//				right: myWidth - (offset.left + $(input).outerWidth() + 25)
				left: myWidth
			}).show();
            if(options.scroll) {
                list.scrollTop(0);
                list.css({
					maxHeight: options.scrollHeight,
					overflow: 'auto'
				});
				
                if($.browser.msie && typeof document.body.style.maxHeight === "undefined") {
					var listHeight = 0;
					listItems.each(function() {
						listHeight += this.offsetHeight;
					});
					var scrollbarsVisible = listHeight > options.scrollHeight;
                    list.css('height', scrollbarsVisible ? options.scrollHeight : listHeight );
					if (!scrollbarsVisible) {
						// IE doesn't recalculate width when scrollbar disappears
						listItems.width( list.width() - parseInt(listItems.css("padding-left")) - parseInt(listItems.css("padding-right")) );
					}
                }
                
            }
		},
		selected: function() {
			var selected = listItems && listItems.filter("." + CLASSES.ACTIVE).removeClass(CLASSES.ACTIVE);
			return selected && selected.length && $.data(selected[0], "ac_data");
		},
		emptyList: function (){
			list && list.empty();
		},
		unbind: function() {
			element && element.remove();
		}
	};
};

$.Autocompleter.Selection = function(field, start, end) {
	if( field.createTextRange ){
		var selRange = field.createTextRange();
		selRange.collapse(true);
		selRange.moveStart("character", start);
		selRange.moveEnd("character", end);
		selRange.select();
	} else if( field.setSelectionRange ){
		field.setSelectionRange(start, end);
	} else {
		if( field.selectionStart ){
			field.selectionStart = start;
			field.selectionEnd = end;
		}
	}
	field.focus();
};

})(jQuery);