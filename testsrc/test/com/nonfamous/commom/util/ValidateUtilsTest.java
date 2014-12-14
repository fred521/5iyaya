package test.com.nonfamous.commom.util;

import com.nonfamous.commom.util.ValidateUtils;

import junit.framework.TestCase;


public class ValidateUtilsTest extends TestCase{
	
	public void testNotNull(){
		Object obj = null;
		try{
			ValidateUtils.notNull(obj);
			assertFalse(true);
		}
		catch(NullPointerException e){
			assertNull(obj);
			assertEquals("object should not be null.", e.getMessage());
		}
	}
	
	public void testNotNullWithMessage(){
		Object obj = null;
		try{
			ValidateUtils.notNull(obj, "obj");
			assertFalse(true);
		}
		catch(NullPointerException e){
			assertNull(obj);
			assertEquals("obj should not be null.", e.getMessage());
		}
	}
	
	public void testNotBlank(){
		String s = "";
		try{
			ValidateUtils.notBlank(s);
			assertFalse(true);
		}
		catch(IllegalArgumentException e){
			assertEquals("string should not be blank.", e.getMessage());
		}
	}
	
	public void testNotBlankWithMessage(){
		String s = "";
		try{
			ValidateUtils.notBlank(s, "s");
			assertFalse(true);
		}
		catch(IllegalArgumentException e){
			assertEquals("s should not be blank.", e.getMessage());
		}
	}
	
}
