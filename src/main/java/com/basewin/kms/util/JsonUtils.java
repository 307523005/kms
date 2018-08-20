package com.basewin.kms.util;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonSyntaxException;
import org.json.JSONArray;
import org.json.JSONException;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by niuhao
 * time : 17:07
 * date : 2018/8/26
 */

public class JsonUtils {

    private JsonUtils() {
        throw new AssertionError();
    }

    /**
     * 将字符串解析成列表
     *
     * @param JsonStr
     * @param classOfT
     * @return
     */
    public static <T> List<T> toList(String JsonStr, Class<T> classOfT) {
        JSONArray array = null;
        List<T> tList = new LinkedList<>();
        try {
            array = new JSONArray(JsonStr);
            for (int i = 0; i < array.length(); i++) {
                String jsonStr = array.get(i).toString();
                T t = new Gson().fromJson(jsonStr, classOfT);
                tList.add(t);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return tList;

    }

    /**
     * 将字符串解析成对象
     *
     * @param JsonStr
     * @param classOfT
     * @return 解析出错返回null
     */
    public static <T> T toObj(String JsonStr, Class<T> classOfT) {
        try {
            return new Gson().fromJson(JsonStr, classOfT);
        } catch (JsonSyntaxException e) {
            return null;
        }
    }

    /**
     * 将对象编码成Json字符串
     *
     * @param object
     * @return
     */
    public static String toJson(Object object) {
        return new Gson().toJson(object);
    }

    /**
     * 将List编码成JsonArray字符串
     *
     * @param list
     * @return
     */
    public static String toJsonArray(List<String> list) {
        JsonArray jsonArray = new JsonArray();
        for (String s : list) {
            jsonArray.add(s);
        }
        return jsonArray.toString();
    }

    /**
     * 顺序按照首字母排序，对象转json
     *
     * @param object
     * @return
     */
    public static String toJsonString(Object object) {
        Object model = object;

        StringBuffer str = new StringBuffer("{");

        int fieldCount = 0;
        Field[] field = model.getClass().getDeclaredFields(); // 获取实体类的所有属性，返回Field数组
        for (int j = 0; j < field.length; j++) { // 遍历所有属性
            String name = field[j].getName(); // 获取属性的名字
            name = name.substring(0, 1).toUpperCase() + name.substring(1);
            String type = field[j].getGenericType().toString(); // 获取属性的类型

            Method m = null;
            try {
                m = model.getClass().getMethod("get" + name);
                Object vale = null;
                try {
                    vale = m.invoke(model);
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
                if (vale != null) {

                    fieldCount++;
                    if (type.equals("class java.lang.String") ||
                            type.equals("class java.util.Date")) {

                        if (fieldCount > 1) {
                            str.append(",");
                        }
                        str.append(name + ":\"" + vale + "\"");
                    } else if (type.equals("class java.lang.Integer") ||
                            type.equals("class java.lang.Short") ||
                            type.equals("class java.lang.Long") ||
                            type.equals("class java.lang.Float") ||
                            type.equals("class java.lang.Double") ||
                            type.equals("class java.lang.Boolean") ||
                            type.equals("long") ||
                            type.equals("int") ||
                            type.equals("float") ||
                            type.equals("double")) {
                        if (fieldCount > 1) {
                            str.append(",");
                        }
                        str.append(name + ":" + vale);
                    }
                }
            } catch (SecurityException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }

        }
        str.append("}");
        return str.toString();
    }
}
