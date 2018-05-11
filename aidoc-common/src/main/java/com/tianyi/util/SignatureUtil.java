package com.tianyi.util;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.*;
import java.util.Map.Entry;

public class SignatureUtil {
    private static final String CHARACTER_ENCODING = "UTF-8";
    final static String ALGORITHM = "HmacSHA256";

    /**
     * 计算签名字符
     * */
    public static String calculateStringToSignV2(Map<String, String> parameters) throws SignatureException, URISyntaxException {

        Map<String, String> sorted = new TreeMap<String, String>();
        sorted.putAll(parameters);
        StringBuilder data = new StringBuilder();
        Iterator<Entry<String, String>> pairs = sorted.entrySet().iterator();

        while (pairs.hasNext()) {
            Entry<String, String> pair = pairs.next();
            if (pair.getValue() != null) {
                data.append( pair.getKey() + "=" + pair.getValue());
            }
            else {
                data.append( pair.getKey() + "=");
            }
            if (pairs.hasNext()) {
                data.append( "&");
            }
        }
        return data.toString();
    }

    /****
     *
     *
     * @param data
     * @param secretKey
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws IllegalStateException
     * @throws UnsupportedEncodingException
     */
    public static String sign(String data, String secretKey) throws NoSuchAlgorithmException, InvalidKeyException, IllegalStateException, UnsupportedEncodingException {

        Mac mac = Mac.getInstance(ALGORITHM);
        mac.init(new SecretKeySpec(secretKey.getBytes(CHARACTER_ENCODING),ALGORITHM));
        byte[] signature = mac.doFinal(data.getBytes(CHARACTER_ENCODING));
        String signatureBase64 = new String(Base64.encodeBase64(signature),CHARACTER_ENCODING);
        return new String(signatureBase64);
    }

    /***
     * 处理base64特殊字符
     * @param rawValue
     * @return
     */
    public static String urlEncode(String rawValue) {
        String value = (rawValue == null) ? "" : rawValue;
        String encoded = null;
        try {
            encoded = URLEncoder.encode(value, CHARACTER_ENCODING)
                    .replace("+", "%20")
                    .replace("*", "%2A")
                    .replace("%7E","~");
        } catch (UnsupportedEncodingException e) {
            System.err.println("Unknown encoding: " + CHARACTER_ENCODING);
            e.printStackTrace();
        }
        return encoded;
    }
}



