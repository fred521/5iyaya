/**
 * @author frank.liu
 * @version 0.1
 * @desc This is a plugin of jquery, hanlde as cascade-select input.
 */
( function($) {
	$.fn.cascadeSelect = function(o /* options */) {
		if (!o)
			throw new Error("o should not be null!");

		var defaults = {
			key :"id",
			value :"label",
			target :null,
			url :''/* returned valued should be a json string with key & value */,
			type :"get",
			defaultOption :null,
			selectedOption : null,
			isRoot: false
		};

		var settings = $.extend( {}, defaults, o);
		if (!(settings.target instanceof $))
			settings.target = $(settings.target);
		
		$(this).data("settings", settings);
		
		return this.each( function(i) {
			var $self = this;

			var loadData = function(){
				var data = null;
				if(!settings.isRoot){
					if (typeof settings.data == 'string') {
						data = settings.data + '&' + settings.key + '=' + $(this).val();
					} else if (typeof settings.data == 'object') {
						data = settings.data;
						data[settings.key] = $(this).val();
					}
				}
				
				var target = settings.target; 
				target.empty();
				if(settings.defaultOption){
					var o = document.createElement("OPTION");
					target.append(o);
					o.text = settings.defaultOption[settings.value];
					o.value = settings.defaultOption[settings.key];
					//o.selected = "selected";
				}
				
				var result = $(this).data($(this).val() || "rootResponse");
				if(!result){//read from cache if existing
					$.ajax({
						async: false,
						url : settings.url,
						data : data,
						type : (settings.type || 'get'),
						dataType: 'json',
						success : function(res){
							result = res;
						},
	
						error : function(xhr, desc, err){
							result = [];
							alert(desc + "\n" + err);
						}
					});
				}
				$(this).data($(this).val() || "rootResponse", result);
				
				$.each(result, function(i){
					//alert(this[settings.key] + ":" + this[settings.value]);
					var o = document.createElement("OPTION");
					target.append(o);
					o.text = this[settings.value];
					o.value = this[settings.key];
					if(settings.selectedOption && settings.selectedOption[settings.key]
					       && settings.selectedOption[settings.key] == this[settings.key]){
						o.selected = "selected";
					}
				});
				
				setTimeout(function(){
					settings.target
			            .find('option:selected')
			            //.attr('selected', 'selected')
			            .parent('select')
			            .trigger('change');
				}, 10);
			};
			// hand control back to browser for a moment
			//var selector = "option:first";
			if(settings.isRoot){
				loadData();
			}
			else{
				$(this).change(loadData);
			}
		});
		
	};
})(jQuery);
