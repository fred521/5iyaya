/**
 * http://plugins.learningjquery.com/cluetip/#options
 * @attr[rel]
 * @attr[title]
 * @attr[twidth]
 */
window["component.Tooltip"]=function( elem ){
	var $elem = jQuery(elem);
	$elem.cluetip({
	  width        : ( $elem.attr("twidth")  || 400 ) ,
	  showTitle    : $elem.attr( 'title' )            ,
	  local        : jQuery( $elem.attr('rel') )[0]   ,	  
	  hideLocal    : true       ,
	  topOffset    : 15         ,
      leftOffset   : 15         ,
      hoverClass   : 'highlight',
      cluetipClass : 'default'  ,
      sticky       : true       ,
      closePosition: 'title'    ,
      positionBy   : 'mouse'    ,
      truncate     : 0          ,
      arrows       : true       ,
      mouseOutClose: true       ,
      dropShadow   : true       ,
      closeText    : '<img src="'+absUrl('images/cross.png') + '" alt="close" />' ,
      fx           : {
	      open     : 'fadeIn'    
      }
      ,
      ajaxCache    : false
      ,
      ajaxSettings: {
          type: 'GET'
      }	 
      ,
      onShow       : function( ct , c ){
      /*
	      bindFeatures( jQuery( "#cluetip-outer" )[0] );
	      ct.bind( 'click' , function(){
		      return false ;    
		  });
	      jQuery( document.body ).one( 'click' , function(){
		      jQuery( '#cluetip-close' ).trigger( 'click' ) ;
		  } ); */
      }
    });
}