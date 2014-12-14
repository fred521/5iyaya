package com.nonfamous.commom.util;

import java.math.BigInteger;
import java.security.Security;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class TokenUtils {
	 private static String Algorithm="DES"; //定义 加密算法,可用 DES,DESede,Blowfish
	 public static byte[] key = {-9, -3, 25, 21, 87, -111, -122, -17};
	 static boolean debug = false;
	  
	 static{
	    Security.addProvider(new com.sun.crypto.provider.SunJCE());
	 }

	  //生成密钥, 注意此步骤时间比较长
	 public static byte[] getKey() throws Exception{
	    KeyGenerator keygen = KeyGenerator.getInstance(Algorithm);
	    SecretKey deskey = keygen.generateKey();
	    if (debug)
	      System.out.println("生成密钥:"+byte2hex(deskey.getEncoded()));
	    return deskey.getEncoded();
	  }
	  
	  //加密
	  public static byte[] encode(byte[] input,byte[] key) throws Exception{
	    SecretKey deskey = new javax.crypto.spec.SecretKeySpec(key,Algorithm);
	    if (debug){
	      System.out.println("加密前的二进串:"+byte2hex(input));
	      System.out.println("加密前的字符串:"+new String(input));
	    }
	    Cipher c1 = Cipher.getInstance(Algorithm);
	    c1.init(Cipher.ENCRYPT_MODE,deskey);
	    byte[] cipherByte=c1.doFinal(input);
	    if (debug)
	      System.out.println("加密后的二进串:"+byte2hex(cipherByte));
	    return cipherByte;
	  }

	  //解密
	  public static byte[] decode(byte[] input,byte[] key) throws Exception{
	    SecretKey deskey = new javax.crypto.spec.SecretKeySpec(key,Algorithm);
	    if (debug)
	      System.out.println("解密前的信息:"+byte2hex(input));
	    Cipher c1 = Cipher.getInstance(Algorithm);
	    c1.init(Cipher.DECRYPT_MODE,deskey);
	    byte[] clearByte=c1.doFinal(input);
	    if (debug){
	      System.out.println("解密后的二进串:"+byte2hex(clearByte));
	      System.out.println("解密后的字符串:"+(new String(clearByte)));
	    }
	    return clearByte;
	  }

	  //md5()信息摘要, 不可逆
	  public static byte[] md5(byte[] input) throws Exception{
	    java.security.MessageDigest alg=java.security.MessageDigest.getInstance("MD5"); //or "SHA-1"
	    if (debug){
	      System.out.println("摘要前的二进串:"+byte2hex(input));
	      System.out.println("摘要前的字符串:"+new String(input));
	    }
	    alg.update(input);
	    byte[] digest = alg.digest();
	    if (debug)
	      System.out.println("摘要后的二进串:"+byte2hex(digest));
	    return digest;
	  }
	  
	  //byte数组转换为16进制字符串
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
	  //16进制转换为byte数组
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
		byte[] encodeData = encode("测试加密".getBytes(), key);
		String hexString = byte2hex(encodeData);

		byte[] hexByte = hex2byte(hexString);

		System.out.println(new String(decode(hexByte, key)));

		// md5("测试加密".getBytes());
	}
}
