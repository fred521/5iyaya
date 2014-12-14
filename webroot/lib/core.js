/**
 * http://visualjquery.com/1.1.2.html
 * 
 * ATTENTION:
 *           1, the html file's encoding(You can set it with CrimsonEditor Document-->Encoding Type-->UTF-8) must be UTF-8
 *
 * window.base is the core.js library's folder path
 * window.root is the core.js library's parent folder path
 * window.runtime option indicate whether it's in runtime
 * window.cookie_days how longer the cookie will be save
 *
 * include:
 *   @param className
 *          it could start with 
 *          1, [css.] e.g.:[css.a.b.c], in this case, the function look for stylesheet file {window.root}/css/a/b/c.css and append to the html's head
 *          2, others, e.g.:[component.a.b.c] in this case, the function look for javascript file {window.base}/component/a/b/c.js and append it to html's head
 * bindFeature
 *   @param className
 *   @param elem_or_callback
 *   this function will check the required class[className] loaded, if not it will load it otherwise, it'll enhance the elem with the required class 
 *   or execute the callback function
 *
 *
 * bindFeatures:
 *   @param pElem
 *   this function will go through the element self and all children elements, for each element check whether each it has css name started with
 *          1, [css.] e.g.:[css.a.b.c], in this case, the function look for stylesheet file {window.root}/css/a/b/c.css and append to the html's head 
 *          2, [component.] e.g.:[component.a.b.c] in this case, the function look for javascript file {window.base}/component/a/b/c.js and append it to html's head and execute
 *                          window["component.a.b.c"]( element )
 *
 * with these functions, we can
 *   1, load css file on fly
 *   2, load javascript library on fly
 *   3, bind features for elements in the html page with harmless way
 *
 */
if( !window.bindFeatures ) ( function( __dependencies__ , __isLibraryLoadedExpression__ , __pre_loaded_classes__ ){
  {	
	// 1, the bind method  
	Function.prototype.bind = function() {
      var __method = this;
      var args     = []  ;
      Array.prototype.push.apply( args, arguments );  
      var object   = args.shift() ;
      return function() {
	    var _args  = [];
	    Array.prototype.push.apply( _args, arguments );  
	    return __method.apply(object, args.concat( _args ) );
      }
    }
    
    Array.prototype.inject=function(memo, iterator) {
	  for( var i=0 ; i<this.length; i++ ){
	    memo = iterator(memo, this[i], i );
	  }  
      return memo;
    }
  
    String.prototype.strip=function(){
	  return this.replace(/^\s+/, '').replace(/\s+$/, '');
    }
  
    String.prototype.parseQuery=function(separator) {
      var match = this.strip().match(/([^?#]*)(#.*)?$/);
      if (!match) return {};

      return match[1].split(separator || '&').inject({}, function(hash, pair) {
        if ((pair = pair.split('='))[0]) {
          var key = decodeURIComponent(pair.shift());
          var value = pair.length > 1 ? pair.join('=') : pair[0];
          if (value != undefined) value = decodeURIComponent(value);

          if (key in hash) {
            if (hash[key].constructor != Array) hash[key] = [hash[key]];
            hash[key].push(value);
          }
          else hash[key] = value;
        }
        return hash;
      });
    }
    
    window.isChildElement=function( childElem , pElemArray ){
		pElemArray = pElemArray || [document.body] ;
		if( !childElem ) return false ;
		
		while( childElem ) {	
			for( var i=0 ; i<pElemArray.length; i++ ){
			    if( pElemArray[i] == childElem ) {
				    return true ;	
			    }
			}
		    childElem = childElem.parentNode ;    
	    }
	    return false ;
	    
	}
		
	window.cumulativeOffset=function(element) {
	    var valueT = 0, valueL = 0;
	    do {
	      valueT += element.offsetTop  || 0;
	      valueL += element.offsetLeft || 0;
	      element = element.offsetParent;
	    } while (element);
	    return [valueL, valueT];
    }
    
    window.assignUniqueId=function( elem ){
	    if( !elem.id ) elem.id = ( "assign_" + Math.random() ).replace( /[.]/g , '' ) ;   
    }
    
    window.atEndOfBox = function ( input_box , mouseClientX ){
		return (  cumulativeOffset(input_box)[0] + parseInt( input_box.width || input_box.style.width )-20 <= mouseClientX )
	}
	
	window.$=function(element) {
	  if (arguments.length > 1) {
	    for (var i = 0, elements = [], length = arguments.length; i < length; i++)
	      elements.push($(arguments[i]));
	    return elements;
	  }
	  if (typeof element == 'string'){
	    element = document.getElementById(element) ;
	  }
	  return element ;
	} 
	
    // 2, initialize the window.base and window.root path variables
    window.cookie_days = 7 ;
    var scripts = document.getElementsByTagName("script") ;
    var rePkg1  = /(core[^\.]*)\.js([\?\.]|$)/i;	  
    for( var i=0 ; i < scripts.length ; i ++ ) {
      if( scripts[i].src != null ) {
    	    var m = scripts[i].src.match(rePkg1);
    	    if(m) {
    		    var str = scripts[i].src.substring(0, m.index) ; 
    			if( str.toLowerCase().indexOf( "http" ) != 0 
    			      && str.toLowerCase().indexOf( "file" ) != 0  
    			        && location.href.lastIndexOf("/") > 0 ){
    				var prefix = location.href.substring(0,location.href.indexOf(location.pathname)) ;    
    				    prefix+= location.pathname ;
    				    prefix = prefix.substring(0,prefix.lastIndexOf("/")+1);					
    				str = prefix + str ;
    			}
    			window.base=str;
    			str = str.substring(0,str.length-1);
    			window.root=str.substring(0,str.lastIndexOf("/")+1);    			
    			window.runtime=scripts[i].getAttribute("runtime") === "true" ;
    			break ;
    		} 
    	}
    }
  }
  
  // 3, class include function
  var __classes__ = [] ;
  window.include=function( fullClassName ) {
	fullClassName = ( fullClassName || "__does__not_exist__" ).replace( /\r\n/g,'').replace(/ /g,'') ;
    if( !__classes__[fullClassName] ) {	
      __classes__[fullClassName] = fullClassName.replace(/[.]/g,'/') ;
      if( fullClassName.indexOf("css.") != 0 ){
	    __classes__[fullClassName] = window.base + __classes__[fullClassName] + ".js" ;  
        xscript = document.createElement('script');
        xscript.src = __classes__[fullClassName] ;
	    document.getElementsByTagName("head")[0].appendChild(xscript);
      } else {
	    __classes__[fullClassName] = window.root + __classes__[fullClassName] + ".css" ;   
	    stylesheet      = document.createElement('link');
	    stylesheet.rel  = "stylesheet" ;
	    stylesheet.type = "text/css"   ;	    
        stylesheet.href = __classes__[fullClassName] ;
	    document.getElementsByTagName("head")[0].appendChild(stylesheet);
      }
    }
    return true;
  } 
  
  // load the required classes files
  for( var i=0 ; i<__pre_loaded_classes__.length;i++){
    include( __pre_loaded_classes__[i] ) ;
  }
  
  // 5, requiredLibaries
  for( var key in __dependencies__ ){
	__isLibraryLoadedExpression__[key] = __isLibraryLoadedExpression__[key] || key ;  
  }
    
  function requiredLibraries( className ){
	return __dependencies__[className]||[] ;  
  }
  
  function requiredLibrariesLoaded( className , notPreDefinedClass ){
    var libraries = requiredLibraries( className ) ;
    
    notPreDefinedClass = ( notPreDefinedClass && libraries.length == 0 ) ;
    if( notPreDefinedClass ) libraries.push( className ) ;
    
    var isLoaded  = true ;
    for( var i=0 ; i<libraries.length; i++){
	  var isLibraryLoadedExpress =  notPreDefinedClass ? className : __isLibraryLoadedExpression__[libraries[i]] ;   
	  if( isLibraryLoadedExpress ){
		if( window[isLibraryLoadedExpress] != undefined ){
		  delete __isLibraryLoadedExpression__[libraries[i]] ; 
	    } else {
		  include( libraries[i] );  
		  isLoaded  = false ;    
		  break ;
	    }  	  
	  } else include( libraries[i] );      
    }   
    if( isLoaded && __dependencies__[className] ) delete __dependencies__[className] ; 
    return isLoaded ; 	  
  }
  
  // 6, bindFeature  
  window.bindFeature=function( className, elem_or_callback ) {
	if( !requiredLibrariesLoaded( className , elem_or_callback.bind ) ){
	  setTimeout( function( ){ bindFeature.apply( {} , [ className, elem_or_callback ] ) ; } , 10 ) ;  
	  return ;  
    }
    if( elem_or_callback.bind ){
	  elem_or_callback();    
    } else if(window[className]){
	  window[className]( elem_or_callback ) ;
    }
  }
  
  window.absUrl = function( url , pUrl ){
	  if( !url ) return url      ;
      pUrl = pUrl || window.root ;
      if( url.match( /^[/].*$/ ) ){
	      url    = window.root + url.substring(1) ;
	  } else if( !url.toLowerCase().match( /^[http].*$|^[file].*$/ ) ){
		  url    = pUrl.match( /(.*[/]).*/ )[1] + url ;
	  }
	  return url ;
  }
  
  // 7, bindFeatures
  window.bindFeatures=function( pElem , currentUrl ){
	  if( window.serverPagesInclude ) serverPagesInclude( pElem , currentUrl ) ;
	  
	  //var start = new Date() ;
	  var executes = [] , elements = [] , child , className ;
	  var children = (pElem || document.body ).getElementsByTagName('*');
      
	  for (var i = 0, length = children.length; i < length; i++) {
        child    = children[i];
        className= child.className || "" ;
        
        if( className.indexOf( "component." ) >= 0 || className.indexOf( "css." ) >= 0 ){
	        var names = className.split( ' ' ) ;
	        className = "" ;
	        
	        for( var j=0; j<names.length; j++ ){
		      if( names[j].indexOf( "component." ) >= 0 || names[j].indexOf( "css." ) >= 0 ){
			      executes.push( [ names[j] , child ] );
			  } else className += names[j] + " " ;   
	        }
	        child.className = className ;
        } 
      }
      
      //if( pElem != document.body ) alert( new Date() - start ) ;
      
      for( var i=0 ; i<executes.length;i++){
	      bindFeature( executes[i][0] , executes[i][1] );
      }
  }  

} )(
  // 1, components and dependent libraries | dependent css stylesheets 
  {

  }
  // 2, expressions which used to verify each component|library loaded, by check window[express] 
  ,
  {
  }
  // 3, pre-loaded classes
  ,
  [
  ]
);