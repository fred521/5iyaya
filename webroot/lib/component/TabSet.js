/**
 * @elem.attr[id]
 * @elem.attr[containerId]
 * @elem.attr[switch_event]
 * @elem.attr[css_prefix]
 * @<a>attr[name], if no this attribute, will direct jump to the given href address
 * @<a>attr[href], if href start with #, will perform ajax partially page load
 *                 otherwise, will perform an whole page load
 *                 if the href is an local element's id, will not perform ajax page load, instead will move the local element to the tab set's container
 */ 
window["component.TabSet"]=function( elem ){
	var $elem = jQuery(elem);
    if( !$elem.attr("id") ){
	    alert("the tabset's id can not be null." );
    }
    
    var switch_event = $elem.attr("switch_event") || "click" ;
    var css_prefix= $elem.attr("css_prefix") || "" ;
    
	var containerId = $elem.attr( "containerId" ) || ( "tabset_container_" + Math.random() ).replace( /[.]/g,'' ) ;	
	var hrefs = jQuery( "a" , elem).get() ;
	
	// 1, assign the tds
	jQuery( hrefs ).each( function( i ){
		var $this = jQuery(this);
	    $this.before( '<td></td>' ).wrap( '<td></td>' );
	    if( i == ( hrefs.length - 1 ) ) {
		    $this.after( '<td></td>' )  ;
	    }
	    $this.addClass("tabset_href_link");
	} ) ;	
	$elem.html( 
	    '<table class="tabset ' + css_prefix + 'tabset" cellSpacing=0 ><tr>' + $elem.html() + '</tr>' 
	  + ( $elem.attr( "containerId" ) ? '' : '<tr><td class="' + css_prefix + 'tabset_content" colspan=' + ( 2*hrefs.length+1 ) + ' id=' + containerId + '>' + '</td></tr>' ) 
	  + '</table>' 
	);
	
	var container = document.getElementById(containerId) ;
	
	// 2, function gotoTab
	function gotoTab( holder , activeTab , totalTabs , newUrl ){
		var oldActiveTab = jQuery( holder ).attr( "oldActiveTab" ) ;
		
		if( oldActiveTab != ""+ activeTab ) {
			jQuery( ".tabset_href_link" , holder ).each( function( i ) {
			    var startTab = "" ;
				var tab      = "" ;
			    if( activeTab == i ) {
				    startTab = i==0? "active_start_tab" : "active_tab1"  ;   
				    tab      = "active_tab" ; 
				} else {
				    startTab = i==0? "start_tab" : ( activeTab == ( i -1 ) ? "active_tab2" : "tab") ;
				    tab      = "tab_button"  ;
			    }
			    
			    var $this = jQuery( this ) ;
			    
			    $this
			    // left side's TD
			    .parent()
			        .prev()
			            .attr( "className" , css_prefix + startTab )
			        .end()
			    .end() 
			    // current tab's background td
			    .parent()
			        .attr( "className" , css_prefix + tab )
			    .end() ;
			    
			    // the last tab's end
			    if( i == totalTabs - 1 ){
				    $this.parent().next().attr( "className" ,  css_prefix + (( activeTab == totalTabs - 1 ) ? "active_end_tab" : "end_tab")  ) ;  
			    }	
			    
			    if( i == activeTab ) {
			        jQuery.cookie( "component.TabSet[" + elem.id + "].defaultTab" , $this.attr( "name" ) , {expires:window.cookie_days} ) ;
				}  
			} );
			
		    jQuery( holder ).attr( "oldActiveTab" , activeTab );		
		    
		    newUrl = newUrl.split( '#' )[1] || newUrl ;
		    
		    // safe store local cached dom elements
		    if( container.childNodes.length == 1 && container.childNodes[0].getAttribute( "local" ) ) {
			    jQuery(container.childNodes[0])
			        .prependTo( jQuery( document.body ) )
			        .hide();
		    }
		    
		    jQuery( container ).empty();
		    
		    if( document.getElementById( newUrl ) ) {
			    
			    jQuery( document.getElementById( newUrl ) )
			        .prependTo( jQuery( container ) )
			        .attr( "local" , "true" )
			        .show() ;
			        			    
		    } else {
		    
		        newUrl = absUrl( newUrl );
		        
		        // the content already on the page
		        jQuery( container ).load( newUrl , 
				    { 
				    }			    
				  , function( responseText , status_code , trans ){
					    if( status_code == "success" ) {
					        bindFeatures( container , newUrl );
				        } else {
					        jQuery( holder ).after( '<div class="ajaxError scroll_div" >' + ( responseText || ( newUrl ) ) + '</div>'  ) ;
					    }
				    } 
				) ;
			}
		} 
		
		return true ;
	}
    // 3, transfer all the href address 
	var defaultTab = ( location.href.split( '#' + elem.id + "=" )[1] || "" ).split( "#" )[0] 
	                     || (location.search||'').parseQuery()[elem.id] 
	                         || jQuery.cookie( "component.TabSet[" + elem.id + "].defaultTab" ) ;
	                         
	var defaultHref= "" ;
	
	jQuery( "a" , elem ).each( function( i ){
		var $this = jQuery(this) ;
		if( i == 0 ) {
			defaultHref = $this.attr("href") ;
		}
		
		if( defaultTab == $this.attr("name") ){
		    defaultTab = i ;
		    defaultHref= $this.attr("href") ;	
		}
		if( $this.attr("href").split( '#' )[1] ){
		    $this.bind( switch_event , {} , gotoTab.bind( {} , elem , i , hrefs.length , $this.attr("href") ) );
		}
		$this.bind( "mouseenter" , {} , function( blockTabSetId , curTabName , isAjaxLoad ){
			var params = location.href.parseQuery() ;
			var tabUrl = '' ;
			var seperator= isAjaxLoad ? '#' : '&' ;
			
			jQuery( ".tabset" )
			    .each( function(){
				    var tabsetId = jQuery( this ).parent().attr("id") ;
				    if( blockTabSetId != tabsetId ){
					    tabUrl += seperator + tabsetId + "=" + jQuery.cookie( "component.TabSet[" + tabsetId + "].defaultTab" ) ;
			        } else {
				        tabUrl += seperator + tabsetId + "=" + curTabName ;    
			        }
			        if( params[tabsetId] ) delete params[tabsetId]  ;
				} ) ;
				
			if( !isAjaxLoad ){
				tabUrl = (location.href.split( '?' )[0]).split('#')[0] + '?' + tabUrl.substring(1).replace( /[#]/g , '&' ) ;
				for( var key in params ){
				    tabUrl += params[key] != null ? ( '&' + key + '=' +  params[key] ) : '' ;	
				}
			}
			jQuery(this).attr( "href" , tabUrl ) ;
		}.bind(this, elem.id , $this.attr("name")  , $this.attr("href").indexOf( '#' )>=0 ) );	
		
		
	});
	if( parseInt( defaultTab ) != defaultTab ) defaultTab = 0 ;
	
	gotoTab( elem , defaultTab , hrefs.length ,  defaultHref ) ;
}