if ( typeof Object.create !== 'function' ) {
	Object.create = function( obj ) {
		function F() {};
		F.prototype = obj;
		return new F();
	};
}

(function($, undefined) {
	var AddressSelector = {
		init : function(options, elem) {
			var self = this;
			self.elem = elem;
			self.$elem = $(elem);
			self.settings = $.extend( {}, $.fn.address.defaults, self.settings, options);

		},
		getOption : function(n) {
			var self = this;
			return self.settings[n];
		},
		setOption : function(n, v) {
			var self = this;
			self.settings[n] = v;
		},
		fetch: function(id) {
			var self = this;

			return $.ajax({
				url : self.settings.url
				, data: { T: self.settings.type, ID: id, SECTIONALL:self.settings.sectionall }
				, type : 'get'
				, dataType : 'json'
				, timeout : 5000
				, error : self.settings.onError
			});
		},
		loadAddress : function (id, selected) {
			var self = this;

			return self.fetch(id).done(function(result) {
				self.$elem.empty();
				var $emptyoption = $("<option/>");
				self.$elem.append($emptyoption);
				$.each(eval(result), function(i, item) {
					if (item.id == 'ZZZZ') {
						$emptyoption.text(item.name).val(item.id);
						$emptyoption.data('item', item);
						if (item.id == selected) {
							$emptyoption.attr("selected", true)
						}
					} else {
						var $option = $("<option/>").text(item.name).val(item.id);
						//$.data($option.get(0), "item", item);
						$option.data('item', item);
						if (item.id == selected) {
							$option.attr("selected", true)
						}
						self.$elem.append($option);
					} 
				});
				//ele.trigger("change");
				if ( typeof self.settings.onLoaded === 'function' ) {
					self.settings.onLoaded(self.elem, arguments);
				}

			});
		},
		selectedItem : function () {
			var self = this;
			var $selected = self.$elem.find("option:selected");
			return $selected ? $selected.data("item") : undefined ;
		},
		empty : function () {
			var self = this;
			self.$elem.empty();
		}
	};

	$.fn.address = function(options) {
		var address = $(this).data('AddressSelector');
		/*
		if (typeof address != "AddressSelector") {
			address = Object.create(AddressSelector);
		}
		*/
		if (!address) {
			address = Object.create(AddressSelector);
			$(this).data("AddressSelector", address);
		}
		address.init(options, this);
		
		//$.data(this.get(0), "AddressSelector", address);
		return address;
		
	};




	//var defaultOptionTemplate = function() 

	$.fn.address.defaults = {
		url: "addressdistrictlookup.html",
		type: 'AREA',
		//optionTemplate: 
		onError: function() {
			alert("Error loading data");
		},
		onLoaded: null

	};

	//////////////////////////////////////////////////////////////////////////////////////////
	
	
	var AddressGroup = {
			init: function(elem, areaId, districtId, sectionId) {
				
				var self = this;
				var $elem = $(elem);
				var $area = $elem.find("select.address-area");
				var $district = $elem.find("select.address-district");
				var $section = $elem.find("select.address-section");
				var addrArea = $area.address();
				var addrDist = $district.address({type:'DISTRICT'});
				var addrSect = $section.address({type:'SECTION'});
				
				var $areaDesc = $elem.find("input.address-area-desc");
				var $distDesc = $elem.find("input.address-district-desc");
				var $sectDesc = $elem.find("input.address-section-desc");
				
				self.addrArea = addrArea;
				self.addrDist = addrDist;
				self.addrSect = addrSect;
				self.$areaDesc = $areaDesc;
				self.$distDesc = $distDesc;
				self.$sectDesc = $sectDesc;
				
				$area.change(function() {
					var areaid = $(this).val();
					addrDist.loadAddress(areaid);
					addrSect.empty();
					var item = addrArea.selectedItem();
					$areaDesc && $areaDesc.val(item ? item.name : '');
					$distDesc && $distDesc.val('');
					$sectDesc && $sectDesc.val('');

				});
				$district.change(function() {
					var districtid = $(this).val();
					addrSect.loadAddress(districtid);
					
					var item = addrDist.selectedItem();
					$distDesc && $distDesc.val(item ? item.name : '');
					$sectDesc && $sectDesc.val('');
				});
				$section.change(function() {
					var item = addrDist.selectedItem();
					$sectDesc && $sectDesc.val(item ? item.name : '');
				});
				

				
				self.reloadAll(areaId, districtId, sectionId);
				/*
				addrArea.loadAddress('', areaId);
				addrDist.loadAddress(areaId, districtId);
				addrSect.loadAddress(districtId, sectionId);
				*/
			},
			reloadAll : function(areaId, districtId, sectionId) {
				var self = this;
				self.addrArea.loadAddress('', areaId);
				self.addrDist.loadAddress(areaId, districtId);
				self.addrSect.loadAddress(districtId, sectionId);
			},
			updateAllDesc : function() {
				var self = this;
				var selectedArea = self.addrArea.selectedItem();
				var selectedDist = self.addrDist.selectedItem();
				var selectedSect = self.addrSect.selectedItem();
				self.$areaDesc && self.$areaDesc.val(selectedArea ? selectedArea.name : '');
				self.$distDesc && self.$distDesc.val(selectedDist ? selectedDist.name : '');
				self.$sectDesc && self.$sectDesc.val(selectedSect ? selectedSect.name : '');
			}
	};

	$.fn.addressgroup = function(areaId, districtId, sectionId) {
		var addressgroup = $(this).data('AddressGroup');
		if (!addressgroup) {
			addressgroup = Object.create(AddressGroup);
			//console.log(typeof addressgroup);
			$(this).data("AddressGroup", addressgroup);
			addressgroup.init(this, areaId, districtId, sectionId);
		}
		
		return addressgroup;
		/*
		var $self = this;
		var $area = this.find("select.address-area");
		var $district = this.find("select.address-district");
		var $section = this.find("select.address-section");
		var addrArea = $area.address();
		var addrDist = $district.address({type:'DISTRICT'});
		var addrSect = $section.address({type:'SECTION'});
		
		var $areaDesc = this.find("input.address-area-desc");
		var $distDesc = this.find("input.address-district-desc");
		var $sectDesc = this.find("input.address-section-desc");
		
		//console.log($areaDesc);
		$area.change(function() {
			var areaid = $(this).val();
			addrDist.loadAddress(areaid);
			addrSect.empty();

			$areaDesc && $areaDesc.val(addrArea.selectedItem().name);
			$distDesc && $distDesc.val('');
			$sectDesc && $sectDesc.val('');

		});
		$district.change(function() {
			var districtid = $(this).val();
			addrSect.loadAddress(districtid);
			$distDesc && $distDesc.val(addrDist.selectedItem().name);
			$sectDesc && $sectDesc.val('');
		});
		$section.change(function() {
			$sectDesc && $sectDesc.val(addrSect.selectedItem().name);
		});
		
		addrArea.loadAddress('', areaId);
		addrDist.loadAddress(areaId, districtId);
		addrSect.loadAddress(districtId, sectionId);
		
*/
	};

})(jQuery);