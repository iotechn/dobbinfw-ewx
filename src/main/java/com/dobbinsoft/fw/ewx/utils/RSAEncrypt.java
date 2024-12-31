package com.dobbinsoft.fw.ewx.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.Security;
import java.security.spec.PKCS8EncodedKeySpec;

@Slf4j
public class RSAEncrypt {

    static {
        // 添加BouncyCastle提供的加密算法支持
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
    }

    public static String decryptRSA(String str, String privateKey) {
        try {

            // 创建RSA cipher实例，使用PKCS1Padding填充方式
            Cipher rsa = Cipher.getInstance("RSA/ECB/PKCS1Padding", "BC");

            // 初始化RSA解密器
            rsa.init(Cipher.DECRYPT_MODE, getPrivateKey(privateKey));

            // Base64解码加密数据
            byte[] encryptedData = Base64.decodeBase64(str);

            // 执行解密操作
            byte[] decryptedBytes = rsa.doFinal(encryptedData);

            // 转换为UTF-8字符串并返回解密后的文本
            return new String(decryptedBytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            log.error("[RSA] RSA/ECB/PKCS1Padding解密 异常", e);
            return null;
        }
    }

    public static PrivateKey getPrivateKey(String privateKeyBase64) throws Exception {
        // 将Base64编码的私钥字符串转换为字节数组
        byte[] privateKeyBytes = Base64.decodeBase64(privateKeyBase64);

        // 使用PKCS8格式解析私钥
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA", "BC");

        // 生成私钥对象
        return keyFactory.generatePrivate(keySpec);
    }

}
