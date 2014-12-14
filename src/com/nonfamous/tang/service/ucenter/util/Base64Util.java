package com.nonfamous.tang.service.ucenter.util;


import sun.misc.*;

///////////////////////////////////////////

public class Base64Util {

  // �� s ���� BASE64 ����
  public static String getBASE64(String s) {
    if (s == null) {
      return null;
    }
    return (new sun.misc.BASE64Encoder()).encode(s.getBytes());
  }

// �� BASE64 ������ַ��� s ���н���
  public static String getFromBASE64(String s) {
    if (s == null) {
      return null;
    }
    BASE64Decoder decoder = new BASE64Decoder();
    try {
      byte[] b = decoder.decodeBuffer(s);
      return new String(b);
    }
    catch (Exception e) {
      return null;
    }
  }

  public static String getBASE64(byte[] b) {
    if (b == null) {
      return null;
    }
    return (new sun.misc.BASE64Encoder()).encode(b);
  }

// �� BASE64 ������ַ��� s ���н���
  public static byte[] getBytesFromBASE64(String s) {
    if (s == null) {
      return null;
    }
    BASE64Decoder decoder = new BASE64Decoder();
    try {
      return decoder.decodeBuffer(s);
    }
    catch (Exception e) {
      return null;
    }
  }

  /**
   * ѹ�����ݲ�����BASE64����
   * @param value byte[] ��ѹ������
   * @return String ѹ��������������
   */
  public static String zipAndEncode64(byte[] value) {
    byte[] buffer = new byte[value.length];
    java.util.zip.Deflater compresser = new java.util.zip.Deflater();
    compresser.setInput(value);
    compresser.finish();
    int compressedDataLength = compresser.deflate(buffer);
    byte[] newValues = new byte[compressedDataLength];
    System.arraycopy(buffer, 0, newValues, 0, compressedDataLength);
    return Base64Util.getBASE64(newValues);
  }

  /**
   * ����BASE64���벢��ѹ������
   * @param value String ����ѹ����BASE64������ַ���
   * @return byte[] ���벢��ѹ��������
   */
  public static byte[] decode64AndUnzip(String value) {
    if (value == null) {
      return null;
    }
    byte[] dat = Base64Util.getBytesFromBASE64(value);
    byte[] result = new byte[0];
    try {
      java.io.ByteArrayInputStream bais = new java.io.ByteArrayInputStream(dat,
          0, dat.length);
      java.util.zip.InflaterInputStream ifis = new java.util.zip.
          InflaterInputStream(bais);
      byte[] block = new byte[1024];
      java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
      while (true) {
        int count = ifis.read(block, 0, block.length);
        if (count > 0) {
          baos.write(block, 0, count);
        }
        else {
          break;
        }
      }
      result = baos.toByteArray();
    }
    catch (Exception e) {
      System.err.println("��ѹ�����ݳ���" + e.getMessage());
    }
    return result;
  }

}
