package com.nonfamous.tang.service.ucenter;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.util.Random;

import com.nonfamous.tang.service.ucenter.util.Base64Util;
import com.nonfamous.tang.service.ucenter.util.SecurityTool;

/**
 * 实现与ucenter接口的加密解密方法
 * 解密算法有bug,因为项目中只用到加密部分,所以没有查找原因.
 * 如果你解决了解密算法的bug，希望能提供一份给我，谢谢
 * 
 * @author flash justxu@tom.com
 *
 */

public class Authcode {

	public enum DiscuzAuthcodeMode {
		Encode, Decode
	};

	// / <summary>
	// / 从字符串的指定位置截取指定长度的子字符串
	// / </summary>
	// / <param name="str">原字符串</param>
	// / <param name="startIndex">子字符串的起始位置</param>
	// / <param name="length">子字符串的长度</param>
	// / <returns>子字符串</returns>
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
	// / 从字符串的指定位置开始截取到字符串结尾的了符串
	// / </summary>
	// / <param name="str">原字符串</param>
	// / <param name="startIndex">子字符串的起始位置</param>
	// / <returns>子字符串</returns>
	public static String cutString(String str, int startIndex) {
		return cutString(str, startIndex, str.length());
	}

	// / <summary>
	// / MD5函数
	// / </summary>
	// / <param name="str">原始字符串</param>
	// / <returns>MD5结果</returns>
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
	// / 生成随机字符
	// / </summary>
	// / <param name="lens">随机字符长度</param>
	// / <returns>随机字符</returns>
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
	// / 使用 Discuz authcode 方法对字符串加密
	// / </summary>
	// / <param name="source">原始字符串</param>
	// / <param name="key">密钥</param>
	// / <param name="expiry">加密字串有效时间，单位是秒</param>
	// / <returns>加密结果</returns>
	public static String discuzAuthcodeEncode(String source, String key,
			int expiry) {
		return discuzAuthcode(source, key, DiscuzAuthcodeMode.Encode, expiry);

	}

	// / <summary>
	// / 使用 Discuz authcode 方法对字符串加密
	// / </summary>
	// / <param name="source">原始字符串</param>
	// / <param name="key">密钥</param>
	// / <returns>加密结果</returns>
	public static String encode(String source, String key) {
		return discuzAuthcode(source, key, DiscuzAuthcodeMode.Encode, 0);

	}

	// / <summary>
	// / 使用 Discuz authcode 方法对字符串解密
	// / </summary>
	// / <param name="source">原始字符串</param>
	// / <param name="key">密钥</param>
	// / <returns>解密结果</returns>
	public static String decode(String source, String key) {
		return discuzAuthcode(source, key, DiscuzAuthcodeMode.Decode, 0);

	}

	// / <summary>
	// / 使用 变形的 rc4 编码方法对字符串进行加密或者解密
	// / </summary>
	// / <param name="source">原始字符串</param>
	// / <param name="key">密钥</param>
	// / <param name="operation">操作 加密还是解密</param>
	// / <param name="expiry">加密字串过期时间</param>
	// / <returns>加密或者解密后的字符串</returns>
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
	// / RC4 原始算法
	// / </summary>
	// / <param name="input">原始字串数组</param>
	// / <param name="pass">密钥</param>
	// / <returns>处理后的字串数组</returns>
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
		String source="name=你好";

		String results=Authcode.encode(source,	"");
		System.out.println("加密:"+results);
		System.out.println("解密"+Authcode.decode("",""));

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
