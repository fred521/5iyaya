package test.com.nonfamous.commom.util;

import java.util.Random;

import com.nonfamous.commom.util.MD5Encrypt;

import junit.framework.TestCase;

public class MD5EncryptTest extends TestCase {

	@Override
	protected void setUp() throws Exception {
		super.setUp();
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testCrypt() {
		String input = "dfafdsda你好个头44af32sdsdsadsd";
		String get1 = MD5Encrypt.encode(input);
		String get2 = MD5Encrypt.encode(input);
		assertEquals(get1, get2);
	}
	
	public void testCryptDiffer(){
		String get1 = MD5Encrypt.encode("dfaliifdda你好个头44aaf32sdsdsadsd");
		String get2 = MD5Encrypt.encode("dfaliifdda你好个头44aaf32sdssadsd");
		assertFalse(get1.equals(get2));
	}
	
	public void testSomeThing(){
		Random r = new Random();
		String psw = "fobidden" + r.nextInt(99);
		String md5 = MD5Encrypt.encode(psw);
		System.out.println(md5);
		System.out.println(MD5Encrypt.encode("dsafdsafdsa"));
		System.out.println(MD5Encrypt.encode("system"));
	}
}
