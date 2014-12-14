/*
 * jQuery Growl plugin
 * Version 1.0.1 (10/27/2008)
 * @requires jQuery v1.2.3 or later
 *
 * Examples at: http://fragmentedcode.com/jquery-growl
 * Copyright (c) 2008 David Higgins
 * 
 * Special thanks to Daniel Mota for inspiration:
 * http://icebeat.bitacoras.com/mootools/growl/
 */

/*
USAGE:

	$.growl(title, msg);
	$.growl(title, msg, image);
	$.growl(title, msg, image, priority);

THEME/SKIN:

You can override the default look and feel by updating these objects:
$.growl.settings.displayTimeout = 4000;
$.growl.settings.noticeTemplate = ''
  + '<div>'
  + '<div style="float: right; background-image: url(my.growlTheme/normalTop.png); position: relative; width: 259px; height: 16px; margin: 0pt;"></div>'
  + '<div style="float: right; background-image: url(my.growlTheme/normalBackground.png); position: relative; display: block; color: #ffffff; font-family: Arial; font-size: 12px; line-height: 14px; width: 259px; margin: 0pt;">' 
  + '  <img style="margin: 14px; margin-top: 0px; float: left;" src="%image%" />'
  + '  <h3 style="margin: 0pt; margin-left: 77px; padding-bottom: 10px; font-size: 13px;">%title%</h3>'
  + '  <p style="margin: 0pt 14px; margin-left: 77px; font-size: 12px;">%message%</p>'
  + '</div>'
  + '<div style="float: right; background-image: url(my.growlTheme/normalBottom.png); position: relative; width: 259px; height: 16px; margin-bottom: 10px;"></div>'
  + '</div>';
$.growl.settings.noticeCss = {
  position: 'relative'
};

To change the 'dock' look, and position: 

$.growl.settings.dockTemplate = '<div></div>';
$.growl.settings.dockCss = {
    position: 'absolute',
    top: '10px',
    right: '10px',
    width: '300px'
  };
  
The dockCss will allow you to 'dock' the notifications to a specific area
on the page, such as TopRight (the default) or TopLeft, perhaps even in a
smaller area with "overflow: scroll" enabled?
*/
var isIE=null;
var isIE5 = null;
var noticeList = new Array();
(function($) {

$.growl = function(title,message,image,priority) { notify(title,message,image,priority); }
$.growl.version = "1.0.0-b2";

function create(rebuild) {
	var instance = document.getElementById('growlDock');
	if(!instance || rebuild) {
	  instance = $(jQuery.growl.settings.dockTemplate).attr('id', 'growlDock').addClass('growl');
	  if(jQuery.growl.settings.defaultStylesheet) {
	    $('head').append('<link rel="stylesheet" type="text/css" href="' + jQuery.growl.settings.defaultStylesheet + '" />');
	  }
	  
	} else {
	  instance = $(instance);
	}
	
	
	if(jQuery.growl.settings.dockCss.position=='fixed'){
		var majorVer = 0;
		var minorVer = 0;
		if (document.all) {
			majorVer = navigator.appVersion.match(/MSIE (.)/)[1] ;
			minorVer = navigator.appVersion.match(/MSIE .\.(.)/)[1] ;
		}
		isIE5 = majorVer == 6 || ( majorVer >= 5 && minorVer >= 5 ) ;
		isIE = majorVer>=6|| ( majorVer >= 5 && minorVer >= 5 ) ;
		if(isIE5){
			jQuery.growl.settings.dockCss.position='absolute';
			
			var bodyEle = jQuery('body');
			bodyEle.css('height','100%');
			bodyEle.css('overflow-y','auto');
			bodyEle.css('overflow-x','hidden');
			/*
			var bodyEle = jQuery('body');
			bodyEle.css('height','100%');
			bodyEle.css('width','99%');
			bodyEle.css('overflow','hidden');
			bodyEle.css('position','relative');
			var htmlEle = jQuery('html');
			htmlEle.css('height','100%');
			htmlEle.css('overflow','hidden');
			*/
			//alert(jQuery('body').css());
		}
	}
	
	$('body').append(instance.css(jQuery.growl.settings.dockCss));
	return instance;
};
  
function r(text, expr, val) {
	while(expr.test(text)) {
	text = text.replace(expr, val);
	}
	return text;
};
  
function notify(title,message,image,priority) {
	var instance = create();
	var html = jQuery.growl.settings.noticeTemplate;
	if(!isIE){
		message = toBreakWord(25,message);
	}
	if(image){
		if(image=='error'){
			image = "../images/my.growlTheme/error.gif";
		}else if(image=='warn'){
			image = "../images/my.growlTheme/warn.gif";
		}else if(image=='info'){
			image = "../images/my.growlTheme/info.gif";
		}else{
			image = jQuery.growl.settings.defaultImage;
		}
	}
	if(typeof(html) == 'object') html = $(html).html();
	html = r(html, /%message%/, (message?message:''));
	html = r(html, /%title%/, (title?title:''));
	html = r(html, /%image%/, (image?image:jQuery.growl.settings.defaultImage));
	html = r(html, /%priority%/, (priority?priority:'normal'));

	var notice = $(html)
		.hide()
		.css(jQuery.growl.settings.noticeCss)
		.fadeIn(jQuery.growl.settings.notice);;

	$.growl.settings.noticeDisplay(notice);
	while(noticeList.length>0){
		obj = noticeList.shift();
		obj.remove();
	}
	noticeList.push(notice);
	
	
	instance.append(notice);
	$('a[@rel="close"]', notice).click(function() {
		notice.remove();
	});
	if ($.growl.settings.displayTimeout > 0) {
		setTimeout(function(){
			jQuery.growl.settings.noticeRemove(notice, function(){
				notice.remove();
			});
		}, jQuery.growl.settings.displayTimeout);
	}
};
function toBreakWord(intLen,message){
	
	var strContent=message;
	var strTemp="";
	while(strContent.length>intLen){
		strTemp+=strContent.substr(0,intLen)+"<br>";
		strContent=strContent.substr(intLen,strContent.length);
	}
	strTemp+= strContent;
	return strTemp;
}
  
// default settings
$.growl.settings = {
	dockTemplate: '<div></div>',
	dockCss: {
		position: 'fixed',
		top: '0px',
		'margin-top':'0px',
		padding:'0px',
		right: '20px',
		width: '250px',
		zIndex: 50000
	},

		
	noticeTemplate:''
  + '<div>'
  + '<div style="float: right;background:url(../images/my.growlTheme/normalTop.gif); position: relative; width: 259px; height: 16px; margin: 0pt;"></div>'
  + '<div style="float: right; background:url(../images/my.growlTheme/normalBackground.gif);position: relative; display: block; color: #ffffff; font-family: Arial; font-size: 12px; line-height: 14px; width: 259px; margin: 0pt;">' 
  + ' <div sytle="height:22px;line-height:22px;"> <img width="22px" height="22px" style="margin: 14px; margin-top: 0px; float: left;" src="%image%" />'
  + '  <h3 style="float:left;display:inline;margin: 0pt; margin-left: 0px;  font-size: 13px;">%title%</h3>'
  +'<a style="float:right;margin-right: 25px; font-size: 10px; color: #fff; text-align: right;text-decoration:none;font-weight:bold;" href="" title="关闭消息" onclick="return false;" rel="close">X</a>'
  +'</div>'
  + '  <div style="clear:both;margin: 0pt 14px; margin-left: 36px; font-size: 12px;word-wrap: break-word; overflow:hidden;">%message%</div>'
  + '</div>'
  + '<div style="float: right; background: url(../images/my.growlTheme/normalBottom.gif); position: relative; width: 259px; height: 16px;">'
  +'</div>'
  + '</div>',
	noticeCss: {
		opacity: .75,
		backgroundColor: '#333333',
		color: '#ffffff'
	},
	noticeDisplay: function(notice) {
		notice.css({'opacity':'0'}).fadeIn(jQuery.growl.settings.noticeFadeTimeout);
	},
	noticeRemove: function(notice, callback) {
		notice.animate({opacity: '0', height: '0px'}, {duration:jQuery.growl.settings.noticeFadeTimeout, complete: callback});
	},
	noticeFadeTimeout: 'slow',
	displayTimeout: -1,
	defaultImage: '../images/my.growlTheme/info.gif',
	defaultStylesheet: null,
	noticeElement: function(el) {
		$.growl.settings.noticeTemplate = $(el);
	}
};
})(jQuery);