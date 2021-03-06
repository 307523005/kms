package com.basewin.kms.util.sm;

import org.bouncycastle.asn1.*;
import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.crypto.params.ECPrivateKeyParameters;
import org.bouncycastle.crypto.params.ECPublicKeyParameters;
import org.bouncycastle.math.ec.ECPoint;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class SM2Utils {
    //生成随机秘钥对
    public static Map generateKeyPair() {
        SM2 sm2 = SM2.Instance();
        AsymmetricCipherKeyPair key = sm2.ecc_key_pair_generator.generateKeyPair();
        ECPrivateKeyParameters ecpriv = (ECPrivateKeyParameters) key.getPrivate();
        ECPublicKeyParameters ecpub = (ECPublicKeyParameters) key.getPublic();
        BigInteger privateKey = ecpriv.getD();
        ECPoint publicKey = ecpub.getQ();
        Map<String,String> map = new HashMap<String,String>();
        map.put("publicKey", ConversionUtil.byteToHex(publicKey.getEncoded()));
        map.put("privateKey", ConversionUtil.byteToHex(privateKey.toByteArray()));
       // System.out.println("公钥: " + ConversionUtil.byteToHex(publicKey.getEncoded()));
       // System.out.println("私钥: " + ConversionUtil.byteToHex(privateKey.toByteArray()));
        return map;
    }
    public static byte[] encrypt(byte[] publicKey, byte[] data) throws IOException {
        if (publicKey == null || publicKey.length == 0) {
            return null;
        }

        if (data == null || data.length == 0) {
            return null;
        }

        byte[] source = new byte[data.length];
        System.arraycopy(data, 0, source, 0, data.length);
        Cipher cipher = new Cipher();
        SM2 sm2 = SM2.Instance();
        ECPoint userKey = sm2.ecc_curve.decodePoint(publicKey);
        ECPoint c1 = cipher.Init_enc(sm2, userKey);
        cipher.Encrypt(source);
        byte[] c3 = new byte[32];
        cipher.Dofinal(c3);
        DERInteger x = new DERInteger(c1.getX().toBigInteger());
        DERInteger y = new DERInteger(c1.getY().toBigInteger());
        DEROctetString derDig = new DEROctetString(c3);
        DEROctetString derEnc = new DEROctetString(source);
        ASN1EncodableVector v = new ASN1EncodableVector();
        v.add(x);
        v.add(y);
        v.add(derDig);
        v.add(derEnc);
        DERSequence seq = new DERSequence(v);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        DEROutputStream dos = new DEROutputStream(bos);
        dos.writeObject(seq);
        return bos.toByteArray();
    }

    public static byte[] decrypt(byte[] privateKey, byte[] encryptedData) throws IOException {
        if (privateKey == null || privateKey.length == 0) {
            return null;
        }

        if (encryptedData == null || encryptedData.length == 0) {
            return null;
        }
        byte[] enc = new byte[encryptedData.length];
        System.arraycopy(encryptedData, 0, enc, 0, encryptedData.length);
        SM2 sm2 = SM2.Instance();
        BigInteger userD = new BigInteger(1, privateKey);
        ByteArrayInputStream bis = new ByteArrayInputStream(enc);
        ASN1InputStream dis = new ASN1InputStream(bis);
        DERObject derObj = dis.readObject();
        ASN1Sequence asn1 = (ASN1Sequence) derObj;
        DERInteger x = (DERInteger) asn1.getObjectAt(0);
        DERInteger y = (DERInteger) asn1.getObjectAt(1);
        ECPoint c1 = sm2.ecc_curve.createPoint(x.getValue(), y.getValue(), true);
        Cipher cipher = new Cipher();
        cipher.Init_dec(userD, c1);
        DEROctetString data = (DEROctetString) asn1.getObjectAt(3);
        enc = data.getOctets();
        cipher.Decrypt(enc);
        byte[] c3 = new byte[32];
        cipher.Dofinal(c3);
        return enc;
    }

    public static byte[] sign(byte[] userId, byte[] privateKey, byte[] sourceData) throws IOException {
        if (privateKey == null || privateKey.length == 0) {
            return null;
        }
        if (sourceData == null || sourceData.length == 0) {
            return null;
        }
        SM2 sm2 = SM2.Instance();
        BigInteger userD = new BigInteger(privateKey);
       // System.out.println("userD: " + userD.toString(16));
       // System.out.println("");
        ECPoint userKey = sm2.ecc_point_g.multiply(userD);
       // System.out.println("椭圆曲线点X: " + userKey.getX().toBigInteger().toString(16));
       // System.out.println("椭圆曲线点Y: " + userKey.getY().toBigInteger().toString(16));
       // System.out.println("");
        SM3Digest sm3 = new SM3Digest();
        byte[] z = sm2.sm2GetZ(userId, userKey);
        //System.out.println("SM3摘要Z: " + Util.getHexString(z));
        //System.out.println("");
       // System.out.println("M: " + Util.getHexString(sourceData));
       // System.out.println("");
        sm3.update(z, 0, z.length);
        sm3.update(sourceData, 0, sourceData.length);
        byte[] md = new byte[32];
        sm3.doFinal(md, 0);
       // System.out.println("SM3摘要值: " + Util.getHexString(md));
      //  System.out.println("");
        SM2Result sm2Result = new SM2Result();
        sm2.sm2Sign(md, userD, userKey, sm2Result);
      //  System.out.println("r: " + sm2Result.r.toString(16));
      //  System.out.println("s: " + sm2Result.s.toString(16));
       // System.out.println("");
        DERInteger d_r = new DERInteger(sm2Result.r);
        DERInteger d_s = new DERInteger(sm2Result.s);
        ASN1EncodableVector v2 = new ASN1EncodableVector();
        v2.add(d_r);
        v2.add(d_s);
        DERObject sign = new DERSequence(v2);
        byte[] signdata = sign.getDEREncoded();
        return signdata;
    }

    @SuppressWarnings("unchecked")
    public static boolean verifySign(byte[] userId, byte[] publicKey, byte[] sourceData, byte[] signData) throws IOException {
        if (publicKey == null || publicKey.length == 0) {
            return false;
        }

        if (sourceData == null || sourceData.length == 0) {
            return false;
        }

        SM2 sm2 = SM2.Instance();
        ECPoint userKey = sm2.ecc_curve.decodePoint(publicKey);

        SM3Digest sm3 = new SM3Digest();
        byte[] z = sm2.sm2GetZ(userId, userKey);
        sm3.update(z, 0, z.length);
        sm3.update(sourceData, 0, sourceData.length);
        byte[] md = new byte[32];
        sm3.doFinal(md, 0);
        //System.out.println("SM3摘要值: " + Util.getHexString(md));
      //  System.out.println("");

        ByteArrayInputStream bis = new ByteArrayInputStream(signData);
        ASN1InputStream dis = new ASN1InputStream(bis);
        DERObject derObj = dis.readObject();
        Enumeration<DERInteger> e = ((ASN1Sequence) derObj).getObjects();
        BigInteger r = ((DERInteger) e.nextElement()).getValue();
        BigInteger s = ((DERInteger) e.nextElement()).getValue();
        SM2Result sm2Result = new SM2Result();
        sm2Result.r = r;
        sm2Result.s = s;
       // System.out.println("r: " + sm2Result.r.toString(16));
       // System.out.println("s: " + sm2Result.s.toString(16));
       // System.out.println("");
        sm2.sm2Verify(md, userKey, sm2Result.r, sm2Result.s, sm2Result);
        return sm2Result.r.equals(sm2Result.R);
    }

    // 国密规范测试用户ID
    public static final String userId = "basewin";
    // 国密规范正式公钥
    public static final String pubk = "04307A3400F418024B061CB01144E022EE8626D438E2ADC711C016BA4180D25E4E67D4FA350930E4961250D61E1164F630B77AF1857E858D862779427F0EBECD68";
    // 国密规范正式私钥
    public static final String prik = "7E8B5AED7BE3206CF632230F77545E59ACB7654FF17826D703C193299B6BE1FC";

    /**
     * 从获十六进制字符串取加密数据
     *
     * @param data
     * @return
     */
    public static String getSM2encryptString(String data) throws IOException {
        byte[] encrypt = encrypt(ConversionUtil.hexToByte(pubk), data.getBytes());
        return Util.getHexString(encrypt);
    }

    /**
     * 从获解密数据为String
     * @param data
     * @return
     */
    public static String getSM2decryptString(String data) throws IOException {
        byte[] decrypt = decrypt(ConversionUtil.hexToByte(prik), Util.hexStringToBytes(data));
        return new String(decrypt);
    }

    /**
     * 签名
     * @param data
     * @return
     * @throws IOException
     */
    public static String getSM2sign(String data) throws IOException {
        byte[] sign = sign(userId.getBytes(), ConversionUtil.hexToByte(prik), data.getBytes());
        return Util.getHexString(sign);
    }

    /**
     * 验签
     * @param data 源数据
     * @param da 签名
     * @return
     * @throws Exception
     */
    public static boolean getSM2verifySign(String data,String da) throws Exception {
        boolean verifySign = verifySign(userId.getBytes(), ConversionUtil.hexToByte(pubk), data.getBytes(),Util.hexStringToBytes(da));
        return verifySign;
    }

    /*public static void main(String[] args) throws Exception {
        generateKeyPair();

        System.out.println("ID: " + Util.getHexString(userId.getBytes()));
        String plainText = "messa+\"+\"ge digest";
        byte[] sourceData = plainText.getBytes();

        System.out.println("签名: ");
        //*byte[] c = sign(userId.getBytes(), ConversionUtil.hexToByte(prik), sourceData);
        //System.out.println("sign: " + Util.getHexString(c));//*
        String sm2sign = getSM2sign(plainText);
        System.out.println("");


        System.out.println("验签: ");
       // boolean vs = verifySign(userId.getBytes(), ConversionUtil.hexToByte(pubk), sourceData, c);
        boolean vs =getSM2verifySign(plainText,sm2sign);
        System.out.println("验签结果: " + vs);

        System.out.println("加密: ");
       // byte[] cipherText = encrypt(ConversionUtil.hexToByte(pubk), sourceData);
      //  String AA = Util.getHexString(cipherText);
      //  System.out.println(AA);
        String sm2encryptString = getSM2encryptString(plainText);
        System.out.println(""+sm2encryptString);

        System.out.println("解密: ");
        //String sm2decryptString = new String(decrypt(ConversionUtil.hexToByte(prik), Util.hexStringToBytes(AA)));
        String sm2decryptString = getSM2decryptString(sm2encryptString);
        System.out.println(sm2decryptString);

    }*/
}
