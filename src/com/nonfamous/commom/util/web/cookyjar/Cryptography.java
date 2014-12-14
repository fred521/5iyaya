/**
 * 
 */
package com.nonfamous.commom.util.web.cookyjar;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author eyeieye
 * 
 */
public class Cryptography {

	private static final Log log = LogFactory.getLog(Cryptography.class);

	private static final String Algorithm = "AES";  //AES

	private static final String Transformation = "AES/ECB/PKCS5Padding";   //AES/ECB/PKCS5Padding

	private static final String Key = "5A82fh-e390V.qw8";    //5A82fh-e390V.qw8

	private Cipher enCipher;

	private Cipher deCipher;

	private static final ThreadLocal<Cryptography> local = new ThreadLocal<Cryptography>();

	private Cryptography() {
		try {
			SecretKeySpec key = new SecretKeySpec(Key.getBytes(), Algorithm);
			enCipher = Cipher.getInstance(Transformation);
			deCipher = Cipher.getInstance(Transformation);
			enCipher.init(Cipher.ENCRYPT_MODE, key);
			deCipher.init(Cipher.DECRYPT_MODE, key);
		} catch (NoSuchAlgorithmException e) {
			if (log.isErrorEnabled()) {
				log.error("no aes Algorithm?", e);
			}
		} catch (NoSuchPaddingException e) {
			if (log.isErrorEnabled()) {
				log.error("no aes padding?", e);
			}
		} catch (InvalidKeyException e) {
			if (log.isErrorEnabled()) {
				log.error("invalid key?", e);
			}
		}
	}

	public static Cryptography getInstance() {
		Cryptography instance = local.get();
		if (instance == null) {
			instance = new Cryptography();
			local.set(instance);
		}
		return instance;
	}

	public static void main(String[] args) {
	    String checkcode = "1234";
	    System.out.println(checkcode.getBytes().length);
	    Cryptography cy = Cryptography.getInstance();
	    String enCC = cy.encrypt(checkcode);
	    System.out.println(enCC);
	    
	    String deCC = cy.dectypt(enCC);
	    System.out.println(deCC);
	    
	}
	
	public String encrypt(String s) {
		if (s == null) {
			throw new NullPointerException("encrypt string can't be null.");
		}
		try {
			byte[] bs = enCipher.doFinal(s.getBytes());
			return new String(Base64.encodeBase64(bs));
		} catch (IllegalBlockSizeException e) {
			if (log.isErrorEnabled()) {
				log.error("encrypt error", e);
			}
		} catch (BadPaddingException e) {
			if (log.isErrorEnabled()) {
				log.error("encrypt error", e);
			}
		}
		return null;
	}

	public String dectypt(String s) {
		if (s == null) {
			throw new NullPointerException("dectypt string can't be null.");
		}
		try {
			byte[] bs = Base64.decodeBase64(s.getBytes());
			return new String(deCipher.doFinal(bs));
		} catch (IllegalBlockSizeException e) {
			if (log.isErrorEnabled()) {
				log.error("dectypt error", e);
			}
		} catch (BadPaddingException e) {
			if (log.isErrorEnabled()) {
				log.error("dectypt error", e);
			}
		}
		return null;
	}
}
