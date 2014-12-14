package com.nonfamous.tang.service.ucenter;

import java.security.InvalidKeyException;

/**
 * RC4算法的java实现,作者不详，感谢原作者
 * 
 * @author not flash
 * 
 */
public class RC4 {

	/** ** Constants *** */
	private static final int STATE_ARRAY_SIZE = 256, BLOCK_SIZE = 1; // Stream
																		// cipher

	/** ** Instance vars *** */
	private byte[] sArray = new byte[STATE_ARRAY_SIZE]; // State array aka
														// "sbox"

	private int x, y; // i, j

	// User Initialization
	public void engineInit(byte[] key) throws InvalidKeyException {
		if (key == null)
			throw new InvalidKeyException("Null key");

		if (key.length < 1 || key.length > 256)
			throw new InvalidKeyException(
					"Invalid key length (req. 1-256 bytes " + key.length + ")");

		rc4Init(key);
	}

	// Encipher with RC4 algorithm
	public byte[] engineCrypt(byte[] in, int inOffset) {
		int outOffset = 0;
		byte[] out = new byte[in.length];

		byte t; // tmp
		for (int i = 0; i < in.length; i++) {
			x = (x + 1) & 0xff;
			y = (sArray[x] + y) & 0xff;
			// swap
			t = sArray[x];
			sArray[x] = sArray[y];
			sArray[y] = t;
			// xor input by keystream
			out[outOffset++] = (byte) (in[inOffset++] ^ sArray[(sArray[x] + sArray[y]) & 0xff]);
		}
		return out;
	}

	// Cipher initialization
	public void rc4Init(byte[] key) {
		// fill state array
		for (int i = 0; i < STATE_ARRAY_SIZE; i++)
			sArray[i] = (byte) i;

		x = 0;
		byte t; // tmp
		for (int i = 0; i < STATE_ARRAY_SIZE; i++) {
			x = ((key[i % key.length] & 0xff) + sArray[i] + x) & 0xff;
			// swap
			t = sArray[i];
			sArray[i] = sArray[x];
			sArray[x] = t;
		}
		x = y = 0;
	}

}