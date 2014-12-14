function i18nEditor(formName, textName, maxCount, toolBarIconPath, postAction){	
	window._formName = formName;
	window._textName = textName;
	window._toolBarIconPath = toolBarIconPath;
	window._debug = false;

	window._maxCount = maxCount;
	window._postAction = postAction;

    //����
	window._a_lang = new Array();
    _a_lang['pic'] = 'ͼƬ';
    _a_lang['url'] = '��ַ';
    _a_lang['viewe'] = '��ʾЧ��';
    _a_lang['border'] = '�߿��ϸ';
    _a_lang['align'] = '���뷽ʽ';
    _a_lang['absmiddle'] = '���Ծ���';
    _a_lang['aleft'] = '����';
    _a_lang['aright'] = '����';
    _a_lang['atop'] = '����';
    _a_lang['amiddle'] = '�в�';
    _a_lang['abottom'] = '�ײ�';
    _a_lang['absbottom'] = '���Եײ�';
    _a_lang['baseline'] = '����';
    _a_lang['submit'] = 'ȷ��';
    _a_lang['cancle'] = 'ȡ��';
    _a_lang['hlink'] = '��������';
    _a_lang['other'] = '����ѡ��';
    _a_lang['newwindow'] = '���´��ڴ�';
    _a_lang['ttop'] = '�ı�����';
    
    _a_lang['copy'] = '����';
    _a_lang['cut'] = '����';
    _a_lang['pau'] = 'ճ��';
    _a_lang['del'] = 'ɾ��';
    
    _a_lang['bold'] = '����';
    _a_lang['italic'] = 'б��';
    _a_lang['underline'] = '�»���';
    _a_lang['st'] = '�л���';
    _a_lang['jl'] = '�����';
    _a_lang['jc'] = '���ж���';
    _a_lang['jr'] = '�Ҷ���';
    
    _a_lang['fcolor'] = '������ɫ';
    _a_lang['bcolor'] = '���ֱ�����ɫ';
    _a_lang['ilist'] = '���';
    _a_lang['itlist'] = '��Ŀ����';
    _a_lang['sup'] = '�ϱ�';
    _a_lang['sub'] = '�±�';
    _a_lang['createlink'] = '��������';
    _a_lang['unlink'] = 'ȡ������';
    _a_lang['inserthr'] = '����ˮƽ��';
    _a_lang['insertimg'] = '����/�޸�ͼƬ';
    _a_lang['editsource'] = '�༭Դ�ļ�';
    _a_lang['preview'] = 'Ԥ��';
    _a_lang['usehtml'] = 'ʹ�ñ༭���༭';
    
    _a_lang['font'] = '����';
    _a_lang['simsun'] = '����';
    _a_lang['simhei'] = '����';
    _a_lang['simkai'] = '����';
    _a_lang['fangsong'] = '����';
    _a_lang['lishu'] = '����';
    _a_lang['youyuan'] = '��Բ';
    
    _a_lang['fontsize'] = '�ֺ�';
    _a_lang['fontsize_1'] = 'һ��';
    _a_lang['fontsize_2'] = '����';
    _a_lang['fontsize_3'] = '����';
    _a_lang['fontsize_4'] = '�ĺ�';
    _a_lang['fontsize_5'] = '���';
    _a_lang['fontsize_6'] = '����';
    _a_lang['fontsize_7'] = '�ߺ�';
    
    _a_lang['current'] = '��ǰ';
    _a_lang['word'] = '��';
	_a_lang['char'] = '�ַ�';

    _a_lang['maxword'] = '���';
    
    _a_lang['modify'] = '�޸�';
    _a_lang['insert'] = '����';
}
