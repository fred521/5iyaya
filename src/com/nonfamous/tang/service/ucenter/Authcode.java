package com.nonfamous.tang.service.ucenter;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.util.Random;

import com.nonfamous.tang.service.ucenter.util.Base64Util;
import com.nonfamous.tang.service.ucenter.util.SecurityTool;

/**
 * ʵ����ucenter�ӿڵļ��ܽ��ܷ���
 * �����㷨��bug,��Ϊ��Ŀ��ֻ�õ����ܲ���,����û�в���ԭ��.
 * ��������˽����㷨��bug��ϣ�����ṩһ�ݸ��ң�лл
 * 
 * @author flash justxu@tom.com
 *
 */

public class Authcode {

	public enum DiscuzAuthcodeMode {
		Encode, Decode
	};

	// / <summary>
	// / ���ַ�����ָ��λ�ý�ȡָ�����ȵ����ַ���
	// / </summary>
	// / <param name="str">ԭ�ַ���</param>
	// / <param name="startIndex">���ַ�������ʼλ��</param>
	// / <param name="length">���ַ����ĳ���</param>
	// / <returns>���ַ���</returns>
	public static String cutString(String str, int startIndex, int length) {
		if (startIndex >= 0) {
			if (length < 0) {
				length = length * -1;
				if (startIndex - length < 0) {
					length = startIndex;
					startIndex = 0;
				} else {
					startIndex = startIndex - length;
				}
			}

			if (startIndex > str.length()) {
				return "";
			}

		} else {
			if (length < 0) {
				return "";
			} else {
				if (length + startIndex > 0) {
					length = length + startIndex;
					startIndex = 0;
				} else {
					return "";
				}
			}
		}

		if (str.length() - startIndex < length) {
			length = str.length() - startIndex;
		}

		return str.substring(startIndex, startIndex + length);
	}

	// / <summary>
	// / ���ַ�����ָ��λ�ÿ�ʼ��ȡ���ַ�����β���˷���
	// / </summary>
	// / <param name="str">ԭ�ַ���</param>
	// / <param name="startIndex">���ַ�������ʼλ��</param>
	// / <returns>���ַ���</returns>
	public static String cutString(String str, int startIndex) {
		return cutString(str, startIndex, str.length());
	}

	// / <summary>
	// / MD5����
	// / </summary>
	// / <param name="str">ԭʼ�ַ���</param>
	// / <returns>MD5���</returns>
	/*
	 * public static String MD5(String str) { byte[] b =
	 * System.Text.Encoding.Default.GetBytes(str); b = new
	 * System.Security.Cryptography.MD5CryptoServiceProvider().ComputeHash(b);
	 * String ret = ""; for (int i = 0; i < b.Length; i++) { ret +=
	 * b[i].ToString("x").PadLeft(2, '0'); } return ret; }
	 */
	public static String MD5(String str) {
		return SecurityTool.getMD5Code(str).toLowerCase();
	}


	// / <summary>
	// / ��������ַ�
	// / </summary>
	// / <param name="lens">����ַ�����</param>
	// / <returns>����ַ�</returns>
	public static String randomString(int lens) {
		char[] CharArray = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'j', 'k',
				'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w',
				'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'J',
				'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V',
				'W', 'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7',
				'8', '9' };
		int clens = CharArray.length;
		String sCode = "";
		Random random = new Random();
		for (int i = 0; i < lens; i++) {
			sCode += CharArray[random.nextInt(clens)];
		}
		return sCode;
	}

	// / <summary>
	// / ʹ�� Discuz authcode �������ַ�������
	// / </summary>
	// / <param name="source">ԭʼ�ַ���</param>
	// / <param name="key">��Կ</param>
	// / <param name="expiry">�����ִ���Чʱ�䣬��λ����</param>
	// / <returns>���ܽ��</returns>
	public static String discuzAuthcodeEncode(String source, String key,
			int expiry) {
		return discuzAuthcode(source, key, DiscuzAuthcodeMode.Encode, expiry);

	}

	// / <summary>
	// / ʹ�� Discuz authcode �������ַ�������
	// / </summary>
	// / <param name="source">ԭʼ�ַ���</param>
	// / <param name="key">��Կ</param>
	// / <returns>���ܽ��</returns>
	public static String encode(String source, String key) {
		return discuzAuthcode(source, key, DiscuzAuthcodeMode.Encode, 0);

	}

	// / <summary>
	// / ʹ�� Discuz authcode �������ַ�������
	// / </summary>
	// / <param name="source">ԭʼ�ַ���</param>
	// / <param name="key">��Կ</param>
	// / <returns>���ܽ��</returns>
	public static String decode(String source, String key) {
		return discuzAuthcode(source, key, DiscuzAuthcodeMode.Decode, 0);

	}

	// / <summary>
	// / ʹ�� ���ε� rc4 ���뷽�����ַ������м��ܻ��߽���
	// / </summary>
	// / <param name="source">ԭʼ�ַ���</param>
	// / <param name="key">��Կ</param>
	// / <param name="operation">���� ���ܻ��ǽ���</param>
	// / <param name="expiry">�����ִ�����ʱ��</param>
	// / <returns>���ܻ��߽��ܺ���ַ���</returns>
	private static String discuzAuthcode(String source, String key,
			DiscuzAuthcodeMode operation, int expiry) {

		if (source == null || key == null) {
			return "";
		}
		int ckey_length = 4;
		String keya, keyb, keyc, cryptkey, result;
		// String timestamp = UnixTimestamp();

		key = MD5(key);
		keya = MD5(cutString(key, 0, 16));
		keyb = MD5(cutString(key, 16, 16));
		keyc = ckey_length > 0 ? (operation == DiscuzAuthcodeMode.Decode ? cutString(
				source, 0, ckey_length)
				: randomString(ckey_length))
				: "";

		cryptkey = keya + MD5(keya + keyc);
		//System.out.println("cryptkey=" + cryptkey);
		if (operation == DiscuzAuthcodeMode.Decode) {
			byte[] temp;
			try {
				temp = Base64Util.getBytesFromBASE64(cutString(source,
						ckey_length));
			} catch (Exception e) {
				try {
					temp = Base64Util.getBytesFromBASE64(cutString(
							source + "=", ckey_length));
				} catch (Exception e1) {
					try {
						temp = Base64Util.getBytesFromBASE64(cutString(source
								+ "==", ckey_length));
					} catch (Exception e2) {
						return "";
					}
				}
			}

			result = new String(RC4(temp, cryptkey));
//			System.out.println("result=" + result);
			if (cutString(result, 10, 16) == cutString(
					MD5(cutString(result, 26) + keyb), 0, 16)) {
				return cutString(result, 26);
			} else {
				return "";
			}
		} else {
			try {
				source = "0000000000" + cutString(MD5(source + keyb), 0, 16)
						+ source;
			} catch (Exception e1) {
				e1.printStackTrace();
			}
//			System.out.println("source=" + source);
			byte[] temp = null;
			try {
				temp = RC4(source.getBytes("utf8"), cryptkey);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
//			System.out.println("result:" + new String(temp));
			return keyc + Base64Util.getBASE64(temp).replaceAll("=", "");

		}

	}

	// / <summary>
	// / RC4 ԭʼ�㷨
	// / </summary>
	// / <param name="input">ԭʼ�ִ�����</param>
	// / <param name="pass">��Կ</param>
	// / <returns>�������ִ�����</returns>
	private static byte[] RC4(byte[] input, String pass) {
		if (input == null || pass == null)
			return null;
		RC4 rc4 = new RC4();
		try {
			try {
				rc4.engineInit(pass.getBytes("UTF8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		}
		return rc4.engineCrypt(input, 0);
	}



	public static void main(String[] args) {
		String source="name=���";

		String results=Authcode.encode(source,	"");
		System.out.println("����:"+results);
		System.out.println("����"+Authcode.decode("",""));

		String pass="";
		//char[] passchar=pass.toCharArray();
		byte[] cc = pass.getBytes(); 
		byte[] passbyte = new byte[256];
		String result="";
		for(int i=0;i<=255;i++){
			int a=(int)cc[i%4]; 
			passbyte[i]=(byte)a;
			result+=a;
			//System.out.print(passbyte[i]);
		}
	}
}
