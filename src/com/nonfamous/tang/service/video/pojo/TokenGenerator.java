package com.nonfamous.tang.service.video.pojo;

import java.nio.ByteBuffer;
import java.nio.LongBuffer;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import com.nonfamous.commom.util.service.POJOServiceBase;
import com.nonfamous.tang.service.video.ITokenGenerator;

import sun.misc.BASE64Encoder;

public class TokenGenerator extends POJOServiceBase implements ITokenGenerator {

    private int randomNumberBytes = 100;

    private static BASE64Encoder base64Encoder = new BASE64Encoder();
    private static SecureRandom secureRandom = null;

    /* (non-Javadoc)
	 * @see prototype.security.ITokenGenerator#generateToken()
	 */
    public String generateToken() {
        byte[] rnumberBytes = getRandomNumber();

        long time = System.currentTimeMillis();

        byte[] timeBytes = new byte[8];
        LongBuffer lBuffer = ByteBuffer.wrap(timeBytes).asLongBuffer();
        lBuffer.put(0, time);

        // randomNumberBytes + TimeBytes
        byte tokenBytes[] = new byte[rnumberBytes.length + timeBytes.length];
        System.arraycopy(rnumberBytes, 0, tokenBytes, 0, rnumberBytes.length);
        System.arraycopy(timeBytes, 0, tokenBytes, rnumberBytes.length, timeBytes.length);
        return encode(base64Encoder.encode(tokenBytes));
    }

    /**
     * 
     * Creates a SHA1PRNG algorith implementation object and intializes with an unpredictable seed.
     * 
     */
    private byte[] getRandomNumber() {
        try {
            if (secureRandom == null) {

                secureRandom = SecureRandom.getInstance("SHA1PRNG");

                // SecureRandom must produce non-deterministic output
                // and therefore it is required that the seed material
                // be unpredictable. Let's pick a random seed.
                int randomNumber = (int) (Math.random() * 1000);
                long seed = randomNumber + System.currentTimeMillis();
                secureRandom.setSeed(seed);
            }
            byte[] rNum = new byte[randomNumberBytes];
            secureRandom.nextBytes(rNum);
            return rNum;
        } catch (NoSuchAlgorithmException n) {
            return "".getBytes();
        }
    }

    private String encode(String plainVal) {
        if (plainVal == null)
            plainVal = "";
        int length = plainVal.length();
        StringBuffer strBuffer = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int curChar = plainVal.charAt(i);
            if (curChar < 48 || (curChar > 57 && curChar < 65) || (curChar > 90 && curChar < 97) || curChar > 122) {
                String tmp = Integer.toHexString(curChar);
                for (int j = tmp.length(); j < 2; j++)
                    tmp = "0" + tmp;
                strBuffer.append("~" + tmp);
            } else {
                strBuffer.append((char) curChar);
            }
        }
        return strBuffer.toString();
    }

    public int getRandomNumberBytes() {
        return randomNumberBytes;
    }

    public void setRandomNumberBytes(int randomNumberBytes) {
        this.randomNumberBytes = randomNumberBytes;
    }
}