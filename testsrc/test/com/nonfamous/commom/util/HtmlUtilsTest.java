package test.com.nonfamous.commom.util;

import com.nonfamous.commom.util.HtmlUtils;
import com.nonfamous.commom.util.StringUtils;

import junit.framework.TestCase;

public class HtmlUtilsTest extends TestCase{
	public void testEscapeScriptTag(){
		String s = "<SCRIPT>alert('ddd');</SCRIPT>";
		s = HtmlUtils.escapeScriptTag(s);
		assertTrue(StringUtils.isBlank(s));
		s = "<SCRIPT>\r\nalert('ddd');\r\n</SCRIPT>";
		s = HtmlUtils.escapeScriptTag(s);
		assertTrue(StringUtils.isBlank(s));
		s = "<SCRIPT>\r\nalert('ºÚÒ»ºÅ');\r\n</SCRIPT>";
		s = HtmlUtils.escapeScriptTag(s);
		assertTrue(StringUtils.isBlank(s));
		
		s = "a°¢¶··¢<fontcolor=\"red\"> adfadf  </font> addf<SCRIPT>\r\nalert('ºÚÒ»ºÅ');\r\n</SCRIPT>";
		s = HtmlUtils.escapeScriptTag(s);
		System.out.println(s);
		
	}
	
	public void testTag(){
		String s = "<font color=\"red\" > aa </font> ";
		System.out.println(HtmlUtils.escapeScriptTag(s));
	}
}
