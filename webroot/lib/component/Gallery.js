/**
 * @li[@rel] will be the big image's url
 *
 */
window["component.Gallery"]=function( elem ){
    var $elem = jQuery(elem);
        
    var batch_size = parseInt( $elem.attr( 'batch_size' ) || 3 ) ;
    var no_picture = absUrl( $elem.attr( 'no_picture' ) || 'images/gallery/no_picture.gif' ) ;
    var thumbs     = $elem.children() ;
    
    // 1, append no_picture if the picture number less than batch_size
    for( var i=thumbs.length; i<batch_size;i++ ){
	    $elem.append(  '<li class="no_picture" ><img src="' + no_picture + '"/></li>' );    
    }
    
    $elem
    .children()
    .each( function(){
    	var $this = jQuery(this);
		$this.addClass( 'thumb' )
		.hover(
			function() { $this.children(':first-child').fadeTo('fast',1  ); }
			,
			function() { $this.children(':first-child').fadeTo('slow',0.3); }
		);
	} )
	// 1, on thumb clicked
	.click( function(){
		var $this = jQuery(this);	
	    if( !$this.is( '.active' ) ) {
			$elem.children( '.thumb .active' ).removeClass( 'active' );
		    var $img  = jQuery( $this.children()[0] ) ;
		    $elem.children( ".big_picture" ).attr( "src" , $img.attr("rel") || $img.attr("src") );
		    $this.addClass( 'active' ) ;
		    elem.selected_image = $img[0];
	    }
	} )
	// 2, append the nav button
	.parent()
    .append( '<li class="nav right_' + ( thumbs.length > batch_size ? 'on' : 'off'  ) + '" ></li>' )
	.prepend( '<li class="nav left_off" ></li>' ) ;
	
	// 3, append the big picture
	$elem.prepend( '<img class="big_picture" style="cursor:pointer;" src="' + no_picture + '" />' )  ;

    jQuery( '.big_picture' , $elem )
    .click( function(){
	    if( elem.onGalleryImageClicked ) {
		    elem.onGalleryImageClicked( elem.selected_image ) ;   
	    }
	    return false ;
	} ) ;
	
	// 4, hidden the extra images
	thumbs = $elem.children( '.thumb' ) ;
	for( var i=batch_size; i<thumbs.length ; i++ ){
	    jQuery( thumbs[i] ).hide();
	}
	
	// 5, on navigation
	$elem.children( '.nav' ).click( function(){
	    var $this = jQuery(this);
	    if( $this.is( '.left_on' ) || $this.is( '.right_on' ) ){
		    var begin=end=0;
		    for( var i=0; i<thumbs.length;i++){
			    if( jQuery(thumbs[i]).is(':visible') ){
				    begin = i ;
				    end   = begin + batch_size ; 
				    break;   
			    }    
		    }
		    
		    if( $this.is( '.left_on' ) ) {
			    begin = begin <= 0 ? 0 : ( begin - 1 ) ;
			    end   = end <= batch_size ? batch_size : ( end - 1 ) ;   
		    } else {
			    end   = end >= thumbs.length ? thumbs.length : ( end + 1 ) ;  
			    begin = end - batch_size ;
			    begin = begin <= 0 ? 0 : begin ;
			}
			
			var navs = $elem.children( '.nav' ) ;
			
			if( begin > 0 ) {
				jQuery(navs[0]).addClass( 'left_on' ).removeClass( 'left_off' ) ;
		    } else {
			    jQuery(navs[0]).addClass( 'left_off' ).removeClass( 'left_on' ) ;
	        }
	        
		    if( end < thumbs.length ){
			    jQuery(navs[1]).addClass( 'right_on' ).removeClass( 'right_off' ) ;
		    } else {
			    jQuery(navs[1]).addClass( 'right_off' ).removeClass( 'right_on' ) ;
		    }
		    
		    for( var i=0; i<thumbs.length;i++){
			    if( i>= begin && i < end ){
				    jQuery( thumbs[i] ).show();
			    } else {
				    jQuery( thumbs[i] ).hide();
			    }    
		    }
		    
	    } 
	} );
	
	// 6, click the first picture object
	jQuery( $elem.children( '.thumb' )[0]).click() ;
		
}