jQuery.extend( jQuery.fn.dataTableExt.oSort, {
    "dd-mm-yyyy-pre": function ( a ) {
    	var x;
        if ($.trim(a) != '') {
            var frDatea = $.trim(a).split(' ');
            var frDatea2 = frDatea[0].split('/');
			if (frDatea.length <= 1) {
				x = (frDatea2[2] + frDatea2[1] + frDatea2[0] + '000000') * 1;
			} else {
	            var frTimea = frDatea[1].split(':');
				if (frTimea.length > 2) {
					x = (frDatea2[2] + frDatea2[1] + frDatea2[0] + frTimea[0] + frTimea[1] + frTimea[2]) * 1;
				} else {
					x = (frDatea2[2] + frDatea2[1] + frDatea2[0] + frTimea[0] + frTimea[1] + '00') * 1;
				}
			}
		} else {
			x = 10000000000000; // = l'an 1000 ...
		}

		return x;
	},

	"dd-mm-yyyy-asc" : function(a, b) {
		return a - b;
	},

	"dd-mm-yyyy-desc" : function(a, b) {
		return b - a;
	}
});
