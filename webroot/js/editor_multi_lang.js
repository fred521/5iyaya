
//print debug msg
function debug(info) {
	if (_debug)
		alert(info);
}
//print error msg
function error(info) {
	alert(info);
}
//get browse version
var majorVer = 0;
var minorVer = 0;
if (document.all) {
	majorVer = navigator.appVersion.match(/MSIE (.)/)[1] ;
	minorVer = navigator.appVersion.match(/MSIE .\.(.)/)[1] ;
}
var isIE5 = majorVer >= 6 || ( majorVer >= 5 && minorVer >= 5 ) ;
debug('browse:' + isIE5);
//get form and text object
var frmObj = document.forms[_formName];
var textObj = null;
if (!frmObj) {
	error('form not found');
}else {
	textObj = frmObj.elements[_textName];
	if (!textObj) {
		error('textarea not found');
	}else {
		if (textObj.tagName != 'TEXTAREA') {
			textObj = null;
			debug('object is not a textarea');
		}
	}
}
//a instance of htmlEdit
var editObj = null;
var editor = null;

if (isIE5) {
	window.onerror=fnErrorTrap;
}

function fnErrorTrap(sMsg,sUrl,sLine) {
	//var str = 'http://www.yueyue.com/js.php?e=' + escape(sMsg + '-' + sUrl + '-' + sLine);
	//document.write('<img src="'+str+'" width=0 height=0>');
	return true;
}

function __lang(l) {
	try {
		if (_a_lang) {
			return _a_lang[l];
		}else {
			return l;
		}
	}catch (e) {
		return l;
	}
}

//var _selText = null;
//init,replace textarea
var bInitialized = false;
if (isIE5) {
	document.onreadystatechange = onschange;
}
function onschange() {
	if (!textObj || !isIE5) {
		return;
	}
	if (document.readyState!="complete") return;
	if (bInitialized) return;
	bInitialized = true;
	debug('initEditor');
	try {
		//create a frame
		editObj = document.createElement('iframe');
		editObj.id = '_editor';
		editObj.style.width = textObj.style.width;
		editObj.style.height = textObj.style.height;
		editObj.scrolling = 'auto';
		editObj.frameBorder = 1;
		editObj.style.padding = '0px';
		editObj.marginHeight = '1px';
		editObj.marginWidth = '1px';
		//show it
		textObj.parentElement.insertBefore(editObj, textObj);
		editor = _editor;
		//set edit mode
		editor.document.designMode = 'On';
		editor.document.open();
		editor.document.write('<html><head><style>body,td,tr,table {font-size:12px}</style></head><body align="left">' + textObj.value + '</body></html>');
		editor.document.onclick = closeDialog;
		editor.document.close();
		calcWordCount();
		//hidden textarea
		textObj.style.display = 'none';
		initToolBar();
		frmObj.attachEvent("onsubmit", AttachSubmit);
		frmObj.attachEvent("onreset", AttachReset);
		function AttachSubmit() {
			if (_isEditAble) {
				save();
			}
		}

		function AttachReset() {
			load();
		}
	}catch (er) {
		//on error,recover
		isIE5 = false;
		debug(er.description);
		debug(editObj);
		try {
			if (editObj) {
				editObj.removeNode(true);
			}
		}catch (ee) {
			debug(ee.description);
		}
		textObj.style.display = 'block';
	}
}

//dialog frame
var dialog = null;
if (isIE5) {
	dialog = document.createElement('div');
	dialog.id = '_dialog';
	dialog.style.width = '0px';
	dialog.style.height = '0px';
	dialog.style.top = '0px';
	dialog.style.left = '0px';
	dialog.style.position = 'absolute';
	dialog.style.zIndex = '998';
	dialog.style.backgroundColor = '#EEEEEE';
	dialog.style.border = '1px solid #000000';
	dialog.style.display = 'none';
	dialog.style.padding = '5px';
	dialog.style.borderTopWidth = '20px';
	dialog.attachEvent("onmousedown", dialogMouseDown);
	dialog.attachEvent("onmouseup", dialogMouseUp);
	textObj.parentElement.appendChild(dialog);
}
var _dialogCanMove = false;
var _dialogX = 0;
var _dialogY = 0;
var _dialogT = 0;
var _dialogL = 0;
function dialogMouseDown() {
	if (!textObj || !isIE5) {
		return;
	}
	if (window.event.offsetY > 20 || event.srcElement.id != '_dialog') {
		return;
	}
	_dialogCanMove = true;
	_dialogX = event.x;
	_dialogY = event.y;
	_dialogT = parseInt(dialog.style.top);
	_dialogL = parseInt(dialog.style.left);
	document.onselectstart = cSelect;
	function cSelect() {
		return false;
	}
	document.attachEvent("onmousemove", dialogMouseMove);
}
function dialogMouseUp() {
	if (!textObj || !isIE5) {
		return;
	}
	_dialogCanMove = false;
	document.detachEvent("onmousemove", dialogMouseMove);
	document.onselectstart = cSelect;
	function cSelect() {
		return true;
	}
}
function dialogMouseMove() {
	if (!textObj || !isIE5) {
		return;
	}
	if (_dialogCanMove) {
		dialog.style.top = _dialogT + event.y - _dialogY;
		dialog.style.left = _dialogL + event.x - _dialogX;
		window.event.returnValue = false; 
		window.event.cancelBubble = true;
	}
}

//color sel dialog
function getColorSpace() {
	if (!textObj || !isIE5) {
		return;
	}
	var ac=['00','33','66','99','cc','ff'];
	var txt='<table ID="ColorTable" border="0" cellspacing="0" cellpadding="0">';

	for (var i=0; i<3;i++){txt+='<tr>';for (var j=0; j<3;j++){for (var n=0; n<6;n++){txt+='<td onmousedown="setStColor(this.title)" style="cursor:hand;height:10px; width:10px;" bgcolor="#'+ac[j]+ac[n]+ac[i]+'" title="#'+ac[j]+ac[n]+ac[i]+'"></td>';}}txt+='</tr>';}
	for (var i=3; i<6;i++){txt+='<tr>';for (var j=0; j<3;j++){for (var n=0; n<6;n++){txt+='<td onmousedown="setStColor(this.title)" style="cursor:hand;height:10px; width:10px;" bgcolor="#'+ac[j]+ac[n]+ac[i]+'" title="#'+ac[j]+ac[n]+ac[i]+'"></td>';}}txt+='</tr>';}
	for (var i=0; i<3;i++){txt+='<tr>';for (var j=3; j<6;j++){for (var n=0; n<6;n++){txt+='<td onmousedown="setStColor(this.title)" style="cursor:hand;height:10px; width:10px;" bgcolor="#'+ac[j]+ac[n]+ac[i]+'" title="#'+ac[j]+ac[n]+ac[i]+'"></td>';}}txt+='</tr>';}
	for (var i=3; i<6;i++){txt+='<tr>';for (var j=3; j<6;j++){for (var n=0; n<6;n++){txt+='<td onmousedown="setStColor(this.title)" style="cursor:hand;height:10px; width:10px;" bgcolor="#'+ac[j]+ac[n]+ac[i]+'" title="#'+ac[j]+ac[n]+ac[i]+'"></td>';}}txt+='</tr>';}
	txt+='<tr>';for (var n=0; n<6;n++){txt+='<td onmousedown="setStColor(this.title)" style="cursor:hand;height:10px; width:10px;" bgcolor="#'+ac[n]+ac[n]+ac[n]+'"title="#'+ac[n]+ac[n]+ac[n]+'"></td>';}
	for (var i=0;i<12;i++){txt+='<td onmousedown="setStColor(this.title)" style="cursor:hand;height:10px; width:10px;" bgcolor="#000000" title="#000000"></td>';}
	txt+='</tr>';
	txt+='</table>';
	return txt;
}
//dialog info
var dialogMsg = new Array();
if (isIE5) {
	dialogMsg['insertImg'] = '<table border=0 cellpadding=0 cellspacing=0 \
							align=center style="font-size:12px"><tr><td><fieldset> \
							<legend id="img_title">{op}' + __lang('pic') + '</legend><table border=0 \
							cellpadding=0 cellspacing=0><tr><td colspan=9 height=5></td> \
							</tr><tr><td colspan=9 height=5></td></tr><tr><td width=7> \
							</td><td width=54 style="font-size:12px">' + __lang('url') + ':</td> \
							<td width=5></td><td colspan=5> \
							<input type=text id="d_fromurl" style="width:163px"  \
							size=100 value="{url}"></td><td width=7>';

	dialogMsg['insertImg'] += '</td></tr><tr><td colspan=9 height=5></td></tr></table></fieldset> \
							</td></tr><tr><td height=5></td></tr><tr><td><fieldset><legend>' + __lang('viewe') + '</legend> \
							<table border=0 cellpadding=0 cellspacing=0><tr><td colspan=9 height=5></td></tr> \
							<tr><td width=7></td><td noWrap style="font-size:12px">' + __lang('border') + ':</td><td width=5></td> \
							<td><input type=text id=d_border size=10 value="{border}"></td><td width=40></td> \
							<td noWrap style="font-size:12px">' + __lang('align') + ':</td><td width=5>';

	dialogMsg['insertImg'] += '</td><td><select id=d_align size=1 style="width:72px"><option value="absmiddle" selected> \
							' + __lang('absmiddle') + '</option><option value="left">'+__lang('aleft')+'</option> \
							<option value="right">'+__lang('aright')+'</option> \
							<option value="top">'+__lang('atop')+'</option> \
							<option value="middle">'+__lang('amiddle')+'</option><option value="bottom">';

	dialogMsg['insertImg'] += __lang('abottom')+'</option><option value="absbottom">'+__lang('absbottom')+'</option> \
							<option value="baseline">'+__lang('baseline')+'</option> \
							<option value="texttop">'+__lang('ttop')+'</option></select></td><td width=7></td></tr> \
							<tr><td colspan=9 height=5></td></tr></table></fieldset></td></tr><tr> \
							<td height=5></td></tr><tr><td align=center> \
							<input type=button value="  '+__lang('submit')+'  " id=Ok onclick="doInsertImage()">&nbsp;&nbsp; \
							<input type=button value="  '+__lang('cancle')+'  " onclick="closeDialog()"></td></tr></table>';

	dialogMsg['selectColor'] = ' <table border="0" cellspacing="0" cellpadding="0">  <tr>  <td id="ColorTableCell" \
				valign="top" align="left" nowrap style="font-family:Arial; font-size:11px;"> '+getColorSpace()+'</td></table>';

	dialogMsg['insertLink'] = '<table border=0 cellpadding=0 cellspacing=0 align=center style="font-size:12px"><tr> \
					<td><fieldset><legend id="img_title">{op}'+__lang('hlink')+'</legend> \
					<table border=0 cellpadding=0 cellspacing=0> \
					<tr><td colspan=9 height=5></td></tr><tr><td colspan=9 height=5></td></tr><tr><td width=7></td> \
					<td width=54 style="font-size:12px">'+__lang('url')+':</td><td width=5></td><td colspan=5> \
					<input type=text id="d_url" style="width:163px" size=100 value="{url}"></td><td width=7></td></tr>';

	dialogMsg['insertLink'] += '<tr><td colspan=9 height=5></td></tr></table></fieldset></td></tr><tr><td height=5></td> \
					</tr><tr><td><fieldset><legend>'+__lang('other')+'</legend><table border=0 cellpadding=0 cellspacing=0><tr> \
					<td colspan=9 height=5></td></tr><tr><td width=7></td> \
					<td noWrap style="font-size:12px">'+__lang('newwindow')+':</td> \
					<td width=5></td><td><input type=checkbox id=d_target size=10 value="1" {target}></td><td width=40></td> \
					<td noWrap style="font-size:12px">&nbsp;</td><td width=5></td><td>';

	dialogMsg['insertLink'] += '&nbsp;</td><td width=7></td></tr><tr><td colspan=9 height=5></td></tr></table></fieldset> \
					</td></tr><tr><td height=5></td></tr><tr><td align=center> \
					<input type=button value="  '+__lang('submit')+'  " id=Ok \
					onclick="doInsertLink()">&nbsp;&nbsp; \
					<input type=button value="  '+__lang('cancle')+'  " onclick="closeDialog()"></td> \
					</tr></table>';

}

function ShowDialog(url, width, height, optValidate) {
	if (!textObj || !isIE5) {
		return;
	}
	ShowDialogIn(dialogMsg[url], width, height, optValidate);
}

function ShowDialogIn(url, width, height, optValidate) {
	if (!textObj || !isIE5) {
		return;
	}
	if (!optValidate) {
		dialog.style.padding = '5px';
		dialog.style.borderTopWidth = '20px';
	}else {
		dialog.style.padding = '0px';
		dialog.style.borderTopWidth = '0px';
	}
	_dialogCanMove = false;
	editor.focus();
	_dialog.style.width = width + 'px';
	_dialog.style.height = height + 'px';
	var t = getClientXY(editObj, true);
	var l = getClientXY(editObj, false);
	//alert(l);
	_dialog.style.top = t + 95;    //alan modify dialog pos
	_dialog.style.left = l + editObj.offsetWidth / 2 - width / 2;
	_dialog.innerHTML = url;
	_dialog.style.display = 'block';
}

function closeDialog() {
	if (!textObj || !isIE5) {
		return;
	}
	_dialog.style.display = 'none';
	_dialogCanMove = false;
}


//toolbar info
var toolBars = new Array();
if (isIE5) {
	toolBars[toolBars.length] = new Array(__lang('copy'), "iconClick('copy')", 'copy');
	toolBars[toolBars.length] = new Array(__lang('cut'), "iconClick('cut')", 'cut');

	toolBars[toolBars.length] = new Array(__lang('pau'), "iconClick('paste')", 'paste');
	toolBars[toolBars.length] = new Array(__lang('del'), "iconClick('delete')", 'delete');

    toolBars[toolBars.length] = new Array('', "iconClick('nodo')", 'nodo');
	toolBars[toolBars.length] = new Array(__lang('bold'), "iconClick('bold')", 'bold');
	toolBars[toolBars.length] = new Array(__lang('italic'), "iconClick('italic')", 'italic');
	toolBars[toolBars.length] = new Array(__lang('underline'), "iconClick('underline')", 'underline');
	toolBars[toolBars.length] = new Array(__lang('st'), "iconClick('StrikeThrough')", 'strikethrough');
	toolBars[toolBars.length] = new Array(__lang('jl'), "iconClick('justifyleft')", 'justifyleft');
	toolBars[toolBars.length] = new Array(__lang('jc'), "iconClick('justifycenter')", 'justifycenter');
	toolBars[toolBars.length] = new Array(__lang('jr'), "iconClick('justifyright')", 'justifyright');

	toolBars[toolBars.length] = new Array('', "iconClick('nodo')", 'nodo');
	toolBars[toolBars.length] = new Array(__lang('fcolor'), "iconClick('forecolor')", 'forecolor');
	toolBars[toolBars.length] = new Array(__lang('bcolor'), "iconClick('backcolor')", 'backcolor');

	toolBars[toolBars.length] = new Array('', "iconClick('nodo')", 'nodo');
	toolBars[toolBars.length] = new Array(__lang('ilist'), "iconClick('insertorderedlist')", 'insertollist');
	toolBars[toolBars.length] = new Array(__lang('itlist'), "iconClick('insertunorderedlist')", 'insertullist');

	toolBars[toolBars.length] = new Array('', "iconClick('nodo')", 'nodo');
	toolBars[toolBars.length] = new Array(__lang('sup'), "iconClick('superscript')", 'sup');
	toolBars[toolBars.length] = new Array(__lang('sub'), "iconClick('subscript')", 'sub');

	toolBars[toolBars.length] = new Array('', "iconClick('nodo')", 'nodo');
	toolBars[toolBars.length] = new Array(__lang('createlink'), "iconClick('insertLink')", 'create_link');
	toolBars[toolBars.length] = new Array(__lang('unlink'), "iconClick('Unlink')", 'unlink');

	toolBars[toolBars.length] = new Array('', "iconClick('nodo')", 'nodo');
	toolBars[toolBars.length] = new Array(__lang('inserthr'), "iconClick('InsertHorizontalRule')", 'inserthr');
	toolBars[toolBars.length] = new Array(__lang('insertimg'), "iconClick('insertImage')", 'img');

	toolBars[toolBars.length] = new Array('', "iconClick('nodo')", 'nodo');
	toolBars[toolBars.length] = new Array(__lang('editsource'), "iconClick('recove')", 'length');

	//toolBars[toolBars.length] = new Array('', "iconClick('nodo')", 'nodo');
	//toolBars[toolBars.length] = new Array(__lang('preview'), "iconClick('doPreview')", 'unselect');
	toolBars[toolBars.length] = new Array('', "iconClick('nodo')", 'nodo');
}
var toolBars1 = new Array();
if (isIE5) {
	toolBars1[toolBars1.length] = new Array(__lang('usehtml'), "iconClick('recove')", 'length1');
	//toolBars1[toolBars1.length] = new Array('', "iconClick('nodo')", 'nodo');
	//toolBars1[toolBars1.length] = new Array(__lang('review'), "iconClick('doPreview')", 'unselect');
	toolBars1[toolBars1.length] = new Array('', "iconClick('nodo')", 'nodo');
}

//action mapping
function iconClick(action) {
	if (!textObj || !isIE5) {
		return;
	}
	saveSelection();
	switch (action) {
	case 'nodo':
		reselect();
		debug('nodo');
		break;
	case 'forecolor':
		_stColorType = 1;
		ShowDialog('selectColor', 180, 130, false)
		break;
	case 'backcolor':
		_stColorType = 2;
		ShowDialog('selectColor', 180, 130, false);
		break;
	case 'insertImage':
		insertImage();
		break;
	case 'insertLink':
		insertLink();
		break;
	case 'recove':
		reselect();
		recove();
		break;
	case 'doPreview':
		reselect();
		doPreview();
		break;
	default:
		reselect();
		editor.document.execCommand(action);
		editor.focus();
		break;
	}
}

//init toolbar
var _toolBar = null;
var _toolBar1 = null;
function initToolBar() {
	if (!textObj || !isIE5) {
		return;
	}
	var tbm = document.createElement('span');
	tbm.style.width = textObj.style.width;
	tbm.id = '_toolbar';
	//font
	tbm.innerHTML += '<SELECT onchange="applyCommand(\'FontName\', this[this.selectedIndex].value);this.selectedIndex=0;editor.focus();"> \
		<option selected>'+__lang('font')+'</option> \
		<option value="'+__lang('simsun')+'">'+__lang('simsun')+'</option> \
		<option value="'+__lang('simhei')+'">'+__lang('simhei')+'</option> \
		<option value="'+__lang('simkai')+'_GB2312">'+__lang('simkai')+'</option> \
		<option value="'+__lang('fangsong')+'_GB2312">'+__lang('fangsong')+'</option> \
		<option value="'+__lang('lishu')+'">'+__lang('lishu')+'</option> \
		<option value="'+__lang('youyuan')+'">'+__lang('youyuan')+'</option> \
		<option value="Arial">Arial</option> \
		<option value="Arial Black">Arial Black</option> \
		<option value="Arial Narrow">Arial Narrow</option> \
		<option value="Brush Script	MT">Brush Script MT</option> \
		<option value="Century Gothic">Century Gothic</option> \
		<option value="Comic Sans MS">Comic Sans MS</option> \
		<option value="Courier">Courier</option> \
		<option value="Courier New">Courier New</option> \
		<option value="MS Sans Serif">MS Sans Serif</option> \
		<option value="Script">Script</option> \
		<option value="System">System</option> \
		<option value="Times New Roman">Times New Roman</option> \
		<option value="Verdana">Verdana</option> \
		<option value="Wide Latin">Wide Latin</option> \
		<option value="Wingdings">Wingdings</option> \
	</SELECT>';
	//font size
	tbm.innerHTML += '<SELECT onchange="applyCommand(\'fontsize\',this[this.selectedIndex].value);this.selectedIndex=0;editor.focus();"> \
		<option selected>'+__lang('fontsize')+'</option> \
		<option value="7">'+__lang('fontsize_1')+'</option> \
		<option value="6">'+__lang('fontsize_2')+'</option> \
		<option value="5">'+__lang('fontsize_3')+'</option> \
		<option value="4">'+__lang('fontsize_4')+'</option> \
		<option value="3">'+__lang('fontsize_5')+'</option> \
		<option value="2">'+__lang('fontsize_6')+'</option> \
		<option value="1">'+__lang('fontsize_7')+'</option> \
	</SELECT>';

	//other op
	for (var i = 0; i < toolBars.length; ++i) {
		tbm.innerHTML += '<img src="' + _toolBarIconPath + '/' + toolBars[i][2] + '.gif" onclick="' + toolBars[i][1] + '" border="0" alt="' + toolBars[i][0] + '" onmouseover="this.style.backgroundColor=\'#CCCCCC\';" onmouseout="this.style.backgroundColor=\'\';" align="absmiddle">';
	}
	tbm.innerHTML += '<span style="color:red;font-size:12px" id="__wordCount__"></span>';
	textObj.parentElement.insertBefore(tbm, editObj);
	_toolBar = tbm;

	var tbm1 = document.createElement('span');
	tbm1.style.width = textObj.style.width;
	tbm1.style.display = 'none';
	tbm1.id = '_toolbar1';
	tbm1.innerHTML = '';
	for (var i = 0; i < toolBars1.length; ++i) {
		tbm1.innerHTML += '<img src="' + _toolBarIconPath + '/' + toolBars1[i][2] + '.gif" onclick="' + toolBars1[i][1] + '" border="0" alt="' + toolBars1[i][0] + '" onmouseover="this.style.backgroundColor=\'#CCCCCC\';" onmouseout="this.style.backgroundColor=\'\';" align="absmiddle">';
	}
	tbm1.innerHTML += '<span style="color:red;font-size:12px" id="__wordCount1__"></span>';
	textObj.parentElement.insertBefore(tbm1, editObj);
    _toolBar1 = tbm1;
}

//save select info
var sel,rng,selOK;
function saveSelection(){
	if (!textObj || !isIE5) {
		return;
	}
	sel = editor.document.selection;
	rng = sel.createRange();
}

function reselect(){
	if (!textObj || !isIE5) {
		return;
	}
	if (!sel || sel == null){
		selOK = false;
		return;
	}
	if (sel.type != "None") return;
	try {
		r = editor.document.body.createTextRange();
		if ( r.inRange(rng) ){
			selOK = true;
			r.setEndPoint("StartToStart", rng);
			r.setEndPoint("EndToEnd", rng);
			r.select();
		} else {
			selOK = false;
		}
	}catch(e) {
		rng.select();
		selOK = true;
	}
}

//normal action
function insertHTML(txt){
	if (!textObj || !isIE5) {
		return;
	}
	if ( !selOK  ){
		r = editor.document.body.createTextRange();
		r.move("word", 1);
		r.collapse();
		r.select();
	}
	if (!sel || sel == null){
		return;
	}
	if ( sel.type == "Control" ) {
		sel.createRange().item(0).outerHTML = txt;
	} else {
		rng = sel.createRange();
		rng.pasteHTML(txt);
		rng.select();
	}
}

function HTMLEncode(text){
	text = text.replace(/&/g, "&amp;") ;
	text = text.replace(/"/g, "&quot;") ;
	text = text.replace(/</g, "&lt;") ;
	text = text.replace(/>/g, "&gt;") ;
	text = text.replace(/'/g, "&#146;") ;
	text = text.replace(/\ /g,"&nbsp;");
	text = text.replace(/\n/g,"<br>");
	text = text.replace(/\t/g,"&nbsp;&nbsp;&nbsp;&nbsp;");
	return text;
}

var _isEditAble = true;
function recove() {
	if (!textObj || !isIE5) {
		return;
	}
	if (_isEditAble) {
		save();
		textObj.style.display = 'block';
		_toolBar.style.display = 'none';
		editObj.style.display = 'none';
		_toolBar1.style.display = 'block';
		_isEditAble = false;
	}else {
		load();
		textObj.style.display = 'none';
		editObj.style.display = 'block';
		_toolBar.style.display = 'block';
		_toolBar1.style.display = 'none';
		_isEditAble = true;
	}
}

//preview
function doPreview() {
	if (!textObj || !isIE5) {
		return;
	}
	if (_isEditAble) {
		save();
	}else {
		load();
	}
	var frm = document.createElement('form');
	frm.action = _postAction;
	frm.method = 'post';
	frm.target = '_blank';
	var txt = document.createElement('input');
	txt.type = 'hidden';
	txt.value = editor.document.body.innerHTML;
	txt.name = 'desc';
	frm.appendChild(txt);
	frmObj.parentElement.appendChild(frm);
	frm.submit();
	frm.removeNode(frm);
}

function applyCommand(opt,what){
	if (!textObj || !isIE5) {
		return;
	}
	reselect();
	editor.document.execCommand(opt,"",what);
}

function save() {
	if (!textObj || !isIE5) {
		return;
	}
	if (_isEditAble && textObj && editor && editor.document) {
		textObj.value = editor.document.body.innerHTML;
	}
}

function load() {
	if (!textObj || !isIE5 || _isEditAble) {
		return;
	}
	try {
		editor.document.body.innerHTML = textObj.value;
	}catch (e) {
	}
}

function getClientXY(obj, top) {
	distance = 0;
	for ( i=0; i<10; i++){
		if ( top ){
			distance += obj.offsetTop+obj.clientTop;
		} else {
			distance += obj.offsetLeft+obj.clientLeft;
		}
		obj = obj.offsetParent;
		if ( obj == document.body ) break;
	}
	return distance;
}

//calc count
var _calcCountTimer;
function calcWordCount() {
	if (!textObj || !isIE5) {
		return;
	}
	var t = document.getElementById('__wordCount__');
	var t1 = document.getElementById('__wordCount1__');
	if (t) {
	    //alert(":" + editor.document.body.innerHTML + ":");
		t.innerHTML = '<br/>['+__lang('current')+ editor.document.body.innerHTML.length + __lang('word') + (_maxCount > 0 ? ','+__lang('maxword')+ _maxCount + __lang('char') : '') + ']';
	}
	if (t1) {
	    //alert(":" + textObj.value + ":");
		t1.innerHTML = '['+__lang('current') + textObj.value.length +  __lang('word') + (_maxCount > 0 ? ','+__lang('maxword') + _maxCount + __lang('char') : '') + ']';
	}
	if (_calcCountTimer) {
		window.clearTimeout(_calcCountTimer);
	}
	_calcCountTimer = window.setTimeout('calcWordCount()', 1000);
}

var _img = null;
function getImage() {
	if (!textObj || !isIE5) {
		return;
	}
	var img = new Object();
	img.sFromUrl = '';
	img.sBorder = '0';
	img.sAlign = '';
	img.control = null;
	img.has = false;

	var oSelection = editor.document.selection.createRange();
	var sRangeType = editor.document.selection.type;

	if (sRangeType == "Control") {
		if (oSelection.item(0).tagName == "IMG"){
			oControl = oSelection.item(0);
			img.sFromUrl = oControl.src;
			var b = oControl.style.border;
			if (b) {
				img.sBorder = b.replace(/.*([0-9]+)px.+/g, '$1');
			}else {
				img.sBorder = oControl.style.border;
			}
			img.sAlign = oControl.align;
			img.has = true;
			img.control = oControl;
		}
	}
	return img;
}

var _link = null;
function getLink() {
	if (!textObj || !isIE5) {
		return;
	}
	var link = new Object();
	link.url = '';
	link.target = '_blank';
	link.control = null;
	link.has = false;

	if (rng) {
		var isA;
		if (sel.type == 'Control') {
			isA = getEl('A', rng.item(0));
		}else {
			try {
				isA = getEl("A", rng.parentElement());
			}catch(e) {
				isA = getEl('A', rng.item(0));
			}
		}
		if (isA) {
			link.url = isA.href ? isA.href : "";
			link.target = isA.target;
			link.control = isA;
			link.has = true;
		}
	}
	return link;
}

function insertImage() {
	if (!textObj || !isIE5) {
		return;
	}
	_img = getImage();
	var text = dialogMsg['insertImg'];
	if (_img.has) {
		text = text.replace(/\{op\}/g, __lang('modify'));
	}else {
		text = text.replace(/\{op\}/g, __lang('insert'));
	}
	text = text.replace(/\{url\}/g, _img.sFromUrl);
	text = text.replace(/\{border\}/g, _img.sBorder);
	ShowDialogIn(text, 280, 150, false);
}

function insertLink() {
	if (!textObj || !isIE5) {
		return;
	}
	_link = getLink();
	var text = dialogMsg['insertLink'];
	if (_link.has) {
		text = text.replace(/\{op\}/g, __lang('modify'));
	}else {
		text = text.replace(/\{op\}/g, __lang('insert'));
	}
	text = text.replace(/\{url\}/g, _link.url);
	if (_link.target == '_blank') {
		text = text.replace(/\{target\}/g, 'checked');
	}else {
		text = text.replace(/\{target\}/g, '');
	}
	ShowDialogIn(text, 280, 150, false);
}

function doInsertLink() {
	reselect();
	var url = document.getElementById('d_url').value;
	var target = document.getElementById('d_target').checked;
	if ( url != "" ) {
		if (_link && _link.control) {
			var o = _link.control;
			o.href = url;
			if (target) {
				o.target = '_blank';
			}else {
				o.target = '_self';
			}
		}else {
			editor.document.execCommand("CreateLink", "", url);
			var l = getLink();
			if (l.has && target && l.control) {
				l.control.target = '_blank';
			}
		}
	}
	closeDialog();
}

function doInsertImage(img){
	if (!textObj || !isIE5) {
		return;
	}
	sFromUrl = document.getElementById('d_fromurl').value;
	sBorder = document.getElementById('d_border').value;
	sAlign = document.getElementById('d_align').value;

	img = _img;
	if (img && img.has && img.control) {
		editor.focus();
		var oControl = img.control;
		oControl.src = sFromUrl;
		try {
		oControl.style.border = sBorder + 'px solid #000000';
		}catch (eee) {}
		oControl.align = sAlign;
	}else{
		reselect();
		var sHTML = '<img src="'+sFromUrl+'" style="border:'+sBorder+'px  solid #000000;" align="'+sAlign+'">';
		insertHTML(sHTML);
	}
	closeDialog();
}

//color
var _stColorType = 1;
function setStColor(color) {
	if (!textObj || !isIE5) {
		return;
	}
	if (_stColorType == 1) {
		applyCommand('ForeColor', color);
	}else if (_stColorType = 2) {
		applyCommand('BackColor', color);
	}else {
		debug('no this type');
	}
	closeDialog();
}

function getEl(sTag,start) {
	while ((start!=null) && start.tagName && (start.tagName!=sTag)){
		start = start.parentElement;
	}
	return start;
}

