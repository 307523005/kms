package com.basewin.kms.util;

import java.util.Random;
import java.util.UUID;


/**
 * @ClassName: RoundUtil 
 * @Description: 随机数生成工具类
 * @author niuhao
 */
public class RoundUtil {

/**
* @Title: getUUID32 
* @Description: 获取UUID 获取一个32位的随机数，并且不会重复
* @param @return 设定文件 
* @return String 返回类型 
* @throws
*/
public static String getUUID32() {
String uuid = UUID.randomUUID().toString(); 
//8+4+4+4+12
uuid = uuid.substring(0,8)+uuid.substring(9,13)+uuid.substring(14,18)+uuid.substring(19,23)+uuid.substring(24,36); 
        return uuid;
}
/**
* @Title: getUUID28 
* @Description: 获取UUID 获取一个32位的随机数，并且不会重复
* @param @return 设定文件 
* @return String 返回类型 
* @throws
*/
public static String getUUID28() {
String uuid = UUID.randomUUID().toString(); 
//8+4+4+4+8
uuid = uuid.substring(0,8)+uuid.substring(9,13)+uuid.substring(14,18)+uuid.substring(19,23)+uuid.substring(24,32); 
        return uuid;
}
/**
* @Title: getUUID16
* @Description: 获取UUID 获取一个32位的随机数，并且不会重复
* @param @return 设定文件 
* @return String 返回类型 
* @throws
*/
public static String getUUID16() {
String uuid = UUID.randomUUID().toString(); 
//8+4+4
uuid = uuid.substring(0,8)+uuid.substring(9,13)+uuid.substring(14,18); 
        return uuid;
}
/**
* @Title: getUUID12
* @Description: 获取UUID 获取一个12位的随机数，并且不会重复
* @param @return 设定文件 
* @return String 返回类型 
* @throws
*/
public static String getUUID12() {
String uuid = UUID.randomUUID().toString(); 
//8+4
uuid = uuid.substring(0,8)+uuid.substring(9,13); 
        return uuid;
}
/**
* @Title: getCode 
* @Description: 随机生成4位验证码
* @param @return 设定文件 
* @return String 返回类型 
* @throws
*/
public static String getCode() {
int i = (int) (Math.random() * 9000 + 1000);
return i + "";
}

/** 
 * 生成特定位数的随机数字 
 * @param length 
 * @return 
 */  
public static String getRandomNum(int length) {  
    String val = "";  
    Random random = new Random();  
    for (int i = 0; i < length; i++) {  
        val += String.valueOf(random.nextInt(10));  
    }  
    return val;  
}

    /**
     * 生成时间戳 16位
     * @param
     * @return
     */
    public static String getTimeNum16() {
    //    String  time= new SimpleDateFormat("yyMMddHHmmssSSSS").format(new Date());
        String  time= String.valueOf(System.currentTimeMillis());
        return time;
    }

}