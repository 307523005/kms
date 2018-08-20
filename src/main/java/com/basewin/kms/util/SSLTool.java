package com.basewin.kms.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.*;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SSLTool {
    //密码
    private static final Log log = LogFactory.getLog(SSLTool.class);
    private static final String serverPass = ParametersUtil.SERVERPASS;
    //private static String courseFile = "E:\\basewin/";
    private static final String courseFile = ParametersUtil.PROJECPATH;
    //private static final String pass = "123456";
    private static final String OPENSSL = ParametersUtil.OPENSSLPATH;
   /* public static void main(String[] args) throws InterruptedException,
            IOException, KeyStoreException, NoSuchAlgorithmException,
            CertificateException {
        String name = "niuhao";//
        if (zssfycz(name + "client")) {
            boolean delVal = deleteCert(name);// 删除证书
        }
        datelod(name);//删除原有证书文件夹
        createCert(name, pass);// 创建证书
        //boolean val = updateCertPass(name, pass);// 更新证书密码
        // boolean upVal = updateServerPass(pass, pass);// 更新信任库密码
        // System.out.println(val);
    }*/

    public static synchronized boolean SSL(String name) {
        if (zssfycz(name)) {
            deleteCert(name);// 删除证书
        }
        datelod(name);//删除原有证书文件夹
        boolean createCert = createCert(name, serverPass);// 创建证书
        if (createCert) {
            return true;
        }
        return false;
    }

    /**
     * 创建客户端证书
     *
     * @param alias clientPass file
     * @Return boolean
     */
    public static boolean createCert(String alias, String clientPass) {
        boolean validate = validate(alias);
        if (!validate) {
            // System.out.println("ca和server证书文件不存在！");
            return validate;
        }
        // 执行命令目录
        File file = new File(courseFile + "/ssl");
        // 客户端信息
        String jksName = alias + ".jks";
        String bksName = alias + ".bks";
        String csrName = alias + ".csr";
        String pemName = alias + ".pem";
        String cerName = alias + ".cer";
        //String fileName = alias;
        // 生成客户端的私钥
        String str1 = "keytool -genkeypair -v -alias " + alias + " -keyalg RSA -sigalg SHA1withRSA -keystore client/" + alias + "/" + bksName + " -storetype BKS -dname CN=localhost -provider org.bouncycastle.jce.provider.BouncyCastleProvider -providerpath ./bcprov-jdk15on-1.58.jar -storepass 123456 -keypass 123456 -noprompt";
        // 生成csr文件,证书请求文件
        String str2 = "keytool -certreq -alias " + alias + " -keyalg RSA -file client/" + alias + "/" + csrName + "  -keystore  client/" + alias + "/" + bksName + " -storetype BKS  -provider org.bouncycastle.jce.provider.BouncyCastleProvider -providerpath ./bcprov-jdk15on-1.58.jar -storepass 123456 -keypass 123456";
        // 生成pem格式证书,用CA根证书来签名服务器端的证书请求文件
        //C:/OpenSSL-Win64/bin/openssl.exe
        String str3 = OPENSSL+" x509 -req -in client/" + alias + "/" + csrName + " -out client/" + alias + "/" + pemName + " -CA ca/ca.crt -CAkey ca/ca.key -CAcreateserial -days 7";
        //4、把CA签名过的文本文件证书server.pem转化为cer二进制文件
        String str4 = OPENSSL+" x509 -in client/" + alias + "/" + pemName + " -inform pem -outform der -out client/" + alias + "/" + cerName;
        String str5 = "keytool -import -keystore client/" + alias + "/" + bksName + " -storepass 123456 -keypass 123456 -alias bctcserver -file server/server.cer -storetype BKS  -provider org.bouncycastle.jce.provider.BouncyCastleProvider -providerpath ./bcprov-jdk15on-1.58.jar -noprompt";
        // 导入信任库6、将客户端证书导入到服务端信任列表中erver/server.keystore
        String str6 = "keytool -import -v -alias " + alias + " -keystore server/server.keystore -storepass 123456 -file client/" + alias + "/" + cerName + " -noprompt";
        Process process1 = null;
        Process process2 = null;
        Process process3 = null;
        Process process4 = null;
        Process process5 = null;
        Process process6 = null;
        int exit1;
        int exit2;
        int exit3;
        int exit4;
        int exit5;
        int exit6;
        try {
            process1 = Runtime.getRuntime().exec(str1, null, file);
            exit1 = process1.waitFor();
            log.info("exit1>>>>>>" + exit1);
            if (exit1 == 0) {
                process2 = Runtime.getRuntime().exec(str2, null, file);
                exit2 = process2.waitFor();
                log.info("exit2>>>>>>" + exit2);
                if (exit2 == 0) {
                    process3 = Runtime.getRuntime().exec(str3, null, file);
                    exit3 = process3.waitFor();
                    log.info("exit3>>>>>>" + exit3);
                    if (exit3 == 0) {
                        process4 = Runtime.getRuntime().exec(str4, null, file);
                        exit4 = process4.waitFor();
                        //导入证书库
                        log.info("exit4>>>>>>" + exit4);
                        if (exit4 == 0) {
                            process5 = Runtime.getRuntime().exec(str5, null,
                                    file);
                            exit5 = process5.waitFor();
                            log.info("exit5>>>>>>" + exit5);
                            if (exit5 == 0) {
                                process6 = Runtime.getRuntime().exec(str6, null, file);
                                exit6 = process6.waitFor();
                                log.info("exit6>>>>>>" + exit6);
                                if (exit6 == 0) {
                                    log.info("证书生成成功。");
                                    log.info(alias + "证书生成成功。");
                                    // 写文件
                                    // writeSerNum(lias, clientPass, courseFile);
                                    return true;
                                } else {
                                      log.error("执行命令：" + str6 + " 出错。");
                                }
                            } else {
                                log.error("执行命令：" + str5 + " 出错。");
                            }
                        } else {
                             log.error("执行命令：" + str4 + " 出错。");
                        }
                    } else {
                        log.error("执行命令：" + str3 + " 出错。");
                    }
                } else {
                    log.error("执行命令：" + str2 + " 出错。");
                }
            } else {
                log.error("执行命令：" + str1 + " 出错。");
            }
        } catch (IOException e) {
            log.error("createCert方法：" + e);
            e.printStackTrace();
        } catch (InterruptedException e) {
            log.error("createCert方法：" + e);
            e.printStackTrace();
        } finally {
            // 杀死子进程
            if (process1 != null) {
                process1.destroy();
                process1 = null;
                if (process2 != null) {
                    process2.destroy();
                    process2 = null;
                    if (process3 != null) {
                        process3.destroy();
                        process3 = null;
                        if (process4 != null) {
                            process4.destroy();
                            process4 = null;
                            if (process5 != null) {
                                process5.destroy();
                                process5 = null;
                                if (process6 != null) {
                                    process6.destroy();
                                    process6 = null;
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * 删除证书条目
     *
     * @param name 证书别名
     * @Return boolean
     */
    public static boolean deleteCert(String name) {
        boolean validate = validate(name);
        if (!validate) {
            log.info("ca和server证书文件不存在！");
            return validate;
        }
        File directory = new File("");
        //String courseFile = directory.getAbsolutePath();
        // 执行命令目录
        File file = new File(courseFile + "/ssl");
        String str = "keytool -delete -alias " + name +" -keystore server/server.keystore -storepass " + serverPass
                + "";
        log.info("str删除" + str);
        int val = 1;
        Process process = null;
        try {
            process = Runtime.getRuntime().exec(str, null, file);
            val = process.waitFor();
        } catch (IOException e) {
            log.error("deleteCert方法：" + e);
            e.printStackTrace();
        } catch (InterruptedException e) {
            log.error("deleteCert方法：" + e);
            e.printStackTrace();
        } finally {
            process.destroy();
            process = null;
        }
        if (val == 1) {
            log.error("删除证书条目时，执行命令：" + str + " 出错");
        }
        // 0代表删除成功，1代表删除失败
        return val == 0 ? true : false;
    }

    /**
     * 更新证书条目密码
     *
     * @param alias newPass 证书别名 新密码
     * @Return boolean
     */
    public static boolean updateCertPass(String alias, String newPass) {
        // 执行命令目录
        File file = new File(courseFile + "/ssl");
        String keyName = alias + "-key.pem";
        String certName = alias + "-cert.pem";
        String fileName = alias + ".p12";
        if (!new File(courseFile + "/ssl/client/" + keyName + "").exists()
                || !new File(courseFile + "/ssl/client/" + certName + "")
                .exists()) {
            log.error("更新证书密码时，" + keyName + "或" + certName + "文件不存在");
            log.info(keyName + "或" + certName + "文件不存在");
            return false;
        }
        String str = "openssl pkcs12 -export -clcerts -in client/" + certName
                + " -inkey client/" + keyName + " -out client/" + fileName
                + " -passout pass:" + newPass + "";
        int val = 1;
        Process process = null;
        try {
            process = Runtime.getRuntime().exec(str, null, file);
            val = process.waitFor();
        } catch (IOException e) {
            log.error("updateCertPass方法：" + e);
            e.printStackTrace();
        } catch (InterruptedException e) {
            log.error("updateCertPass方法：" + e);
            e.printStackTrace();
        } finally {
            process.destroy();
            process = null;
        }
        if (val == 1) {
            log.error("更新证书密码时，执行命令：" + str + " 出错");
        }
        // 0代表删除成功，1代表删除失败
        return val == 0 ? true : false;

    }

    /**
     * 更新信任库密码
     *
     * @param oldPass newPass file 原密码 新密码
     * @Return val 0代表更新成功，1代表更新失败
     */
    public static boolean updateServerPass(String oldPass, String newPass) {
       /* boolean validate = validate(name);
        if (!validate) {
            System.out.println("ca和server证书文件不存在！");
            return validate;
        }*/
        // 执行命令目录
        File file = new File(courseFile + "/ssl");
        String str = "keytool -storepasswd -keystore server/servertrust.jks -storepass "
                + oldPass + " -new " + newPass + "";
        int val = 1;
        Process process = null;
        try {
            process = Runtime.getRuntime().exec(str, null, file);
            val = process.waitFor();
        } catch (IOException e) {
            log.error("updateServerPass方法：" + e);
            e.printStackTrace();
        } catch (InterruptedException e) {
            log.error("updateServerPass方法：" + e);
            e.printStackTrace();
        } finally {
            process.destroy();
            process = null;
        }
        // 0代表删除成功，1代表删除失败
        return val == 0 ? true : false;
    }

    /**
     * 记录证书系列号
     *
     * @param alias file 证书别名 文件目录
     */
    private static void writeSerNum(String alias, String pass, String path) {
        // 信任库文件servertrust.jks
        String storePath = path + "/ssl/server/servertrust.jks";
        // 记录证书系列号的文件
        File txtFile = new File(path + "/ssl/客户端证书系列信息.txt");

        FileInputStream in = null;
        FileWriter writer = null;
        KeyStore ks;
        try {
            in = new FileInputStream(storePath);
            ks = KeyStore.getInstance("JKS");
            ks.load(in, serverPass.toCharArray());
            X509Certificate c = (X509Certificate) ks.getCertificate(alias);
            // alias为条目的别名
            String serialNumber = c.getSerialNumber().toString(16);
            Date today = new Date();
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String str = "生成时间  : " + sf.format(today) + "\r\n" + "证书名称  : "
                    + alias + "\r\n" + "安装密码  : " + pass + "\r\n" + "证书系列号: "
                    + serialNumber + "\r\n\r\n\r\n";
            if (!txtFile.exists()) {
                boolean createNewFile = txtFile.createNewFile();
                if (!createNewFile) {
                    log.error("生成客户端证书系列信息文件失败,请手动记录证书信息：" + str);
                    throw new Exception();
                }
            }
            writer = new FileWriter(txtFile, true);
            // if(txtFile.length() == 0){
            // writer.write("       生成时间     " + "  用户名称    " + "  用户密码     " +
            // "   证书系列号 \n ");
            // writer.flush();
            // }

            writer.write(str);
            writer.flush();
        } catch (NoSuchAlgorithmException e) {
            log.error("writeSerNum方法：" + e);
            e.printStackTrace();
        } catch (KeyStoreException e) {
            log.error("writeSerNum方法：" + e);
            e.printStackTrace();
        } catch (CertificateException e) {
            log.error("writeSerNum方法：" + e);
            e.printStackTrace();
        } catch (IOException e) {
            log.error("writeSerNum方法：" + e);
            e.printStackTrace();
        } catch (Exception e) {
            log.error("生成客户端证书系列信息文件失败,请手动记录证书信息：" + e);
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 删除原来的客户端证书文件夹
     *
     * @param
     * @return
     */
    public static boolean datelod(String name) {
        String sPath = courseFile + "/ssl/client/" + name;
        File file = new File(sPath);
        if (!file.exists()) {  // 不存在返回 false
            return true;
        } else {
            // 判断是否为文件
            if (file.isFile()) {  // 为文件时调用删除文件方法
                return deleteFile(sPath);
            } else {  // 为目录时调用删除目录方法
                return deleteDirectory(sPath);
            }
        }
    }


    /**
     * 删除单个文件
     *
     * @param sPath 被删除文件path
     * @return 删除成功返回true，否则返回false
     */
    public static boolean deleteFile(String sPath) {
        boolean flag = false;
        File file = new File(sPath);
        // 路径为文件且不为空则进行删除
        if (file.isFile() && file.exists()) {
            file.delete();
            flag = true;
        }
        return flag;
    }

    /**
     * 删除目录以及目录下的文件
     *
     * @param sPath 被删除目录的路径
     * @return 目录删除成功返回true，否则返回false
     */
    public static boolean deleteDirectory(String sPath) {
        //如果sPath不以文件分隔符结尾，自动添加文件分隔符
        if (!sPath.endsWith(File.separator)) {
            sPath = sPath + File.separator;
        }
        File dirFile = new File(sPath);
        //如果dir对应的文件不存在，或者不是一个目录，则退出
        if (!dirFile.exists() || !dirFile.isDirectory()) {
            return false;
        }
        boolean flag = true;
        //删除文件夹下的所有文件(包括子目录)
        File[] files = dirFile.listFiles();
        for (int i = 0; i < files.length; i++) {
            //删除子文件
            if (files[i].isFile()) {
                flag = deleteFile(files[i].getAbsolutePath());
                if (!flag) break;
            } //删除子目录
            else {
                flag = deleteDirectory(files[i].getAbsolutePath());
                if (!flag) break;
            }
        }
        if (!flag) return false;
        //删除当前目录
        if (dirFile.delete()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 验证ssl 所需ca和server文件是否存在
     *
     * @return
     */
    public static boolean validate(String name) {
        boolean val = false;
        File file = new File(courseFile + "/ssl/client/" + name);
        if (!file.exists()) {
            boolean mkdirs = file.mkdirs();
            if (!mkdirs) {
                log.error("创建" + file.getAbsolutePath() + "文件夹失败");
                return false;
            }
        }
        if (new File(courseFile + "/ssl/ca/ca.key").exists()
                && new File(courseFile + "/ssl/ca/ca.crt").exists()
                && new File(courseFile + "/ssl/ca/ca.pem").exists()
                && new File(courseFile + "/ssl/server/server.cer").exists()
                && new File(courseFile + "/ssl/server/server.keystore")
                .exists()) {
            val = true;
        }
        if (!val) {
            log.error("ca和server证书相关文件不存在！");
        }
        return val;
    }

    //判断证书是否已经存在
    public static boolean zssfycz(String alias) {
        String storePath = courseFile + "/ssl/server/server.keystore";
        boolean val = false;
        try {
            FileInputStream in = new FileInputStream(storePath);
            KeyStore ks = KeyStore.getInstance("JKS");
            ks.load(in, serverPass.toCharArray());
            val = ks.containsAlias(alias);
            if (val) log.info("证书已经存在");
        } catch (Exception e) {
            log.error("zssfycz方法：" + e);
            e.printStackTrace();
        }// 检验条目是否在密钥库中，存在返回true
        // Process process = null;
        // try {
        // File directory = new File("");
        // String courseFile = directory.getAbsolutePath();
        // //执行命令目录
        // File file = new File(courseFile+"/ssl");
        // //判断信任库中是否存在该证书
        // String str =
        // "keytool -list -v -alias "+alias+" -keystore server/servertrust.jks -storepass 123456";
        // //1代表信任库中没有导入客户端证书
        // process = Runtime.getRuntime().exec(str, null, file);
        // int exit = process.waitFor();
        // if(exit == 1){
        // return false;
        // }
        // } catch (IOException e) {
        // log.error("zssfycz方法："+e);
        // e.printStackTrace();
        // } catch (InterruptedException e) {
        // log.error("zssfycz方法："+e);
        // e.printStackTrace();
        // }
        return val;
    }

    //实现cmd 输入信息交互
    private static int handleImport(Process process, String... params) throws IOException {
        InputStream inputStream = process.getInputStream();
        OutputStreamWriter outputStream = new OutputStreamWriter(process
                .getOutputStream());
        if (params != null && params.length > 0) {
            for (int i = 0; i < params.length; i++) {
                outputStream.write(params[i]);
                outputStream.flush();
            }
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                inputStream));
        String line = null;
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }
        inputStream = process.getErrorStream();
        int val = process.exitValue();
        inputStream.close();
        inputStream = null;
        return val;
    }

}
