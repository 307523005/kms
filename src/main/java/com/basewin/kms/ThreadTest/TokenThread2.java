package com.basewin.kms.ThreadTest;

import com.basewin.kms.util.Test;


public class TokenThread2 implements Runnable {
    /*
     * @Resource private WxuserService wxuserService;
     */
    int a = 1;

    boolean test = true;

    @Override
    public void run() {
        System.out.println("线程1111启动");

        while (test) {
            String s= Test.sendGet("http://10.20.10.139:9080/ssl/getsslbks", "serialNumber=b"+a);
            System.out.println(s);
            test=true;
            a++;
            }
        }

    }

