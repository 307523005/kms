package com.basewin.kms.util.des;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.security.SecureRandom;
import java.security.spec.KeySpec;

/**
 * niuhao
 * 2018-6-6
 * 类  名  称：AESEncryptTools
 * 类  描  述：DES加密解密算法
 */
public final class DESEncryptTools {

    //加密算是是des
    public static final String ALGORITHM = "DES";
    //转换格式
    private static final String TRANSFORMATION = "DES/ECB/PKCS5Padding";

    //利用8个字节64位的key给src加密
    public static String encrypt(byte[] src, byte[] key) {
        try {
            //加密
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance(ALGORITHM);
            KeySpec keySpec = new DESKeySpec(key);
            SecretKey secretKey = secretKeyFactory.generateSecret(keySpec);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, new SecureRandom());
            byte[] enMsgBytes = cipher.doFinal(src);
            return Base64Util.encode(enMsgBytes);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //利用8个字节64位的key给src解密
    public static byte[] decrypt(String encryptData,byte[] key){
        try {
            //解密
            Cipher deCipher = Cipher.getInstance(TRANSFORMATION);
            SecretKeyFactory deDecretKeyFactory = SecretKeyFactory.getInstance(ALGORITHM);
            KeySpec deKeySpec = new DESKeySpec(key);
            SecretKey deSecretKey = deDecretKeyFactory.generateSecret(deKeySpec);
            deCipher.init(Cipher.DECRYPT_MODE, deSecretKey, new SecureRandom());
            byte[] deMsgBytes = deCipher.doFinal(Base64Util.decode(encryptData.toCharArray()));
            return deMsgBytes;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public static String key = "basewoin147258369";

   /* public static void main(String[] args) throws Exception{
        String msg = "data";    
        System.out.println("加密前："+msg);
        String encryptData = DESEncryptTools.encrypt(msg.getBytes(),key.getBytes());
        System.out.println("加密后："+encryptData);
        byte[] deMsgBytes = DESEncryptTools.decrypt(encryptData,key.getBytes());
        System.out.println("解密后："+new String(deMsgBytes));
    }*/
}