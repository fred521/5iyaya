function i18nEditor(formName, textName, maxCount, toolBarIconPath, postAction){	
	window._formName = formName;
	window._textName = textName;
	window._toolBarIconPath = toolBarIconPath;
	window._debug = false;

	window._maxCount = maxCount;
	window._postAction = postAction;

    //语言
	window._a_lang = new Array();
    _a_lang['pic'] = '图片';
    _a_lang['url'] = '地址';
    _a_lang['viewe'] = '显示效果';
    _a_lang['border'] = '边框粗细';
    _a_lang['align'] = '对齐方式';
    _a_lang['absmiddle'] = '绝对居中';
    _a_lang['aleft'] = '居左';
    _a_lang['aright'] = '居右';
    _a_lang['atop'] = '顶部';
    _a_lang['amiddle'] = '中部';
    _a_lang['abottom'] = '底部';
    _a_lang['absbottom'] = '绝对底部';
    _a_lang['baseline'] = '基线';
    _a_lang['submit'] = '确定';
    _a_lang['cancle'] = '取消';
    _a_lang['hlink'] = '超级链接';
    _a_lang['other'] = '其他选项';
    _a_lang['newwindow'] = '在新窗口打开';
    _a_lang['ttop'] = '文本顶部';
    
    _a_lang['copy'] = '复制';
    _a_lang['cut'] = '剪切';
    _a_lang['pau'] = '粘贴';
    _a_lang['del'] = '删除';
    
    _a_lang['bold'] = '粗体';
    _a_lang['italic'] = '斜体';
    _a_lang['underline'] = '下划线';
    _a_lang['st'] = '中划线';
    _a_lang['jl'] = '左对齐';
    _a_lang['jc'] = '居中对齐';
    _a_lang['jr'] = '右对齐';
    
    _a_lang['fcolor'] = '文字颜色';
    _a_lang['bcolor'] = '文字背景颜色';
    _a_lang['ilist'] = '编号';
    _a_lang['itlist'] = '项目符号';
    _a_lang['sup'] = '上标';
    _a_lang['sub'] = '下标';
    _a_lang['createlink'] = '插入链接';
    _a_lang['unlink'] = '取消链接';
    _a_lang['inserthr'] = '插入水平线';
    _a_lang['insertimg'] = '插入/修改图片';
    _a_lang['editsource'] = '编辑源文件';
    _a_lang['preview'] = '预览';
    _a_lang['usehtml'] = '使用编辑器编辑';
    
    _a_lang['font'] = '字体';
    _a_lang['simsun'] = '宋体';
    _a_lang['simhei'] = '黑体';
    _a_lang['simkai'] = '楷体';
    _a_lang['fangsong'] = '仿宋';
    _a_lang['lishu'] = '隶书';
    _a_lang['youyuan'] = '幼圆';
    
    _a_lang['fontsize'] = '字号';
    _a_lang['fontsize_1'] = '一号';
    _a_lang['fontsize_2'] = '二号';
    _a_lang['fontsize_3'] = '三号';
    _a_lang['fontsize_4'] = '四号';
    _a_lang['fontsize_5'] = '五号';
    _a_lang['fontsize_6'] = '六号';
    _a_lang['fontsize_7'] = '七号';
    
    _a_lang['current'] = '当前';
    _a_lang['word'] = '字';
	_a_lang['char'] = '字符';

    _a_lang['maxword'] = '最多';
    
    _a_lang['modify'] = '修改';
    _a_lang['insert'] = '插入';
}
