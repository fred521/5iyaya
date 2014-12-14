package com.nonfamous.commom.util.alipay;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;

public class Payment {

    public static String CreateUrl(String paygateway, String service, String sign_type, String out_trade_no,
                                   String input_charset, String partner, String key, String seller_email,
                                   String subject, String price, String quantity, String show_url, String payment_type,
                                   String logistics_type, String logistics_fee, String logistics_payment,
                                   String return_url, String notify_url) {

        Map<String,String> params = new HashMap<String,String>();
        params.put("service", service);
        params.put("out_trade_no", out_trade_no);
        params.put("show_url", show_url);
        params.put("quantity", quantity);
        params.put("partner", partner);
        params.put("payment_type", payment_type);
        // params.put("discount", discount);
        // params.put("body", body);
        // params.put("notify_url", notify_url);
        params.put("price", price);
        params.put("return_url", return_url);
        params.put("seller_email", seller_email);
        params.put("logistics_type", logistics_type);
        params.put("logistics_fee", logistics_fee);
        params.put("logistics_payment", logistics_payment);
        params.put("subject", subject);
        params.put("_input_charset", input_charset);

        String prestr = "";

        prestr = prestr + key;
        // System.out.println("prestr=" + prestr);

        String sign = com.nonfamous.commom.util.alipay.Md5Encrypt.md5(getContent(params, key));

        String parameter = "";
        parameter = parameter + paygateway;

        List keys = new ArrayList(params.keySet());
        for (int i = 0; i < keys.size(); i++) {
            String value = (String) params.get(keys.get(i));
            if (value == null || value.trim().length() == 0) {
                continue;
            }
            try {
                parameter = parameter + keys.get(i) + "=" + URLEncoder.encode(value, input_charset) + "&";
            } catch (UnsupportedEncodingException e) {

                e.printStackTrace();
            }
        }

        parameter = parameter + "sign=" + sign + "&sign_type=" + sign_type;

        return parameter;

    }

    private static String getContent(Map params, String privateKey) {
        List keys = new ArrayList(params.keySet());
        Collections.sort(keys);

        String prestr = "";

        boolean first = true;
        for (int i = 0; i < keys.size(); i++) {
            String key = (String) keys.get(i);
            String value = (String) params.get(key);
            if (value == null || value.trim().length() == 0) {
                continue;
            }
            if (first) {
                prestr = prestr + key + "=" + value;
                first = false;
            } else {
                prestr = prestr + "&" + key + "=" + value;
            }
        }
        return prestr + privateKey;
    }
}
