package com.nonfamous.commom.util;

import java.math.BigInteger;
import java.security.Security;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class TokenUtils {
	 private static String Algorithm="DES"; //���� �����㷨,���� DES,DESede,Blowfish
	 public static byte[] key = {-9, -3, 25, 21, 87, -111, -122, -17};
	 static boolean debug = false;
	  
	 static{
	    Security.addProvider(new com.sun.crypto.provider.SunJCE());
	 }

	  //������Կ, ע��˲���ʱ��Ƚϳ�
	 public static byte[] getKey() throws Exception{
	    KeyGenerator keygen = KeyGenerator.getInstance(Algorithm);
	    SecretKey deskey = keygen.generateKey();
	    if (debug)
	      System.out.println("������Կ:"+byte2hex(deskey.getEncoded()));
	    return deskey.getEncoded();
	  }
	  
	  //����
	  public static byte[] encode(byte[] input,byte[] key) throws Exception{
	    SecretKey deskey = new javax.crypto.spec.SecretKeySpec(key,Algorithm);
	    if (debug){
	      System.out.println("����ǰ�Ķ�����:"+byte2hex(input));
	      System.out.println("����ǰ���ַ���:"+new String(input));
	    }
	    Cipher c1 = Cipher.getInstance(Algorithm);
	    c1.init(Cipher.ENCRYPT_MODE,deskey);
	    byte[] cipherByte=c1.doFinal(input);
	    if (debug)
	      System.out.println("���ܺ�Ķ�����:"+byte2hex(cipherByte));
	    return cipherByte;
	  }

	  //����
	  public static byte[] decode(byte[] input,byte[] key) throws Exception{
	    SecretKey deskey = new javax.crypto.spec.SecretKeySpec(key,Algorithm);
	    if (debug)
	      System.out.println("����ǰ����Ϣ:"+byte2hex(input));
	    Cipher c1 = Cipher.getInstance(Algorithm);
	    c1.init(Cipher.DECRYPT_MODE,deskey);
	    byte[] clearByte=c1.doFinal(input);
	    if (debug){
	      System.out.println("���ܺ�Ķ�����:"+byte2hex(clearByte));
	      System.out.println("���ܺ���ַ���:"+(new String(clearByte)));
	    }
	    return clearByte;
	  }

	  //md5()��ϢժҪ, ������
	  public static byte[] md5(byte[] input) throws Exception{
	    java.security.MessageDigest alg=java.security.MessageDigest.getInstance("MD5"); //or "SHA-1"
	    if (debug){
	      System.out.println("ժҪǰ�Ķ�����:"+byte2hex(input));
	      System.out.println("ժҪǰ���ַ���:"+new String(input));
	    }
	    alg.update(input);
	    byte[] digest = alg.digest();
	    if (debug)
	      System.out.println("ժҪ��Ķ�����:"+byte2hex(digest));
	    return digest;
	  }
	  
	  //byte����ת��Ϊ16�����ַ���
	  public static String byte2hex(byte[] data) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < data.length; i++) {
			String temp = Integer.toHexString(((int) data[i]) & 0xFF);
			for(int t = temp.length();t<2;t++)
			{
				sb.append("0");
			}
			sb.append(temp);
		}
		return sb.toString();
	}
	  //16����ת��Ϊbyte����
	  public static byte[] hex2byte(String hexStr){
		  byte[] bts = new byte[hexStr.length() / 2];
		  for (int i = 0,j=0; j < bts.length;j++ ) {
		     bts[j] = (byte) Integer.parseInt(hexStr.substring(i, i+2), 16);
		     i+=2;
		  }
		  return bts;
	  }
	  public static void main(String[] args) throws Exception {
		debug = true;
		System.out.println(System.currentTimeMillis());
		byte[] encodeData = encode("���Լ���".getBytes(), key);
		String hexString = byte2hex(encodeData);

		byte[] hexByte = hex2byte(hexString);

		System.out.println(new String(decode(hexByte, key)));

		// md5("���Լ���".getBytes());
	}
}
