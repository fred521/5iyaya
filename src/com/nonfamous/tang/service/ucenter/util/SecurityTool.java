package com.nonfamous.tang.service.ucenter.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class SecurityTool {
	/**
	 * ��������ĸ���ĸ
	 * @return
	 */
	public static String getCharAuthCode(){
		int length=4;
		StringBuffer sb = new StringBuffer(length);
		Random r = new Random();
		int i = 0;
		while (i < length) {
			int asc=r.nextInt(122);
			if ((asc > 64&asc<91)||(asc > 96&asc<123)) {
				sb.append((char) asc);
				i++;
			}
		}
		return sb.toString();
	}
	/**
	 * ��������ĸ���ĸ
	 * @return
	 */
	public static String getNumberAuthCode(){
		int length=4;
		StringBuffer sb = new StringBuffer(length);
		Random r = new Random();
		int i = 0;
		while (i < length) {
			int asc=r.nextInt(58);
			if ((asc > 47&asc<58)) {
				sb.append((char) asc);
				i++;
			}
		}
		return sb.toString();
	}
	public static String getMD5Code(String str){
		MessageDigest messageDigest;
		String result=null;
		try {
		messageDigest = MessageDigest.getInstance("MD5");
		messageDigest.update(str.getBytes("UTF8"));
		result=byteHEX(messageDigest.digest());	
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;

	}
	/*byteHEX()��������һ��byte���͵���ת����ʮ�����Ƶ�ASCII��ʾ��
	����Ϊjava�е�byte��toString�޷�ʵ����һ�㣬������û��C�����е�
	  sprintf(outbuf,"%02X",ib)
	*/
	public static String byteHEX(byte[] ib) {
	char[] digit = {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
	StringBuffer s = new StringBuffer(64);
	for(int i=0;i<ib.length;i++){
		char [] ob = new char[2];
		ob[0] = digit[(ib[i] >>> 4) & 0X0F];
		ob[1] = digit[ib[i] & 0X0F];
		s.append(new String(ob));
	}
	return s.toString();
	}
	public static void main(String[] args){
		System.out.println(SecurityTool.getNumberAuthCode());
	}
}
