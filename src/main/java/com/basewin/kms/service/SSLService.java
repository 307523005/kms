package com.basewin.kms.service;

import com.basewin.kms.entity.SMKeyBeen;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

public interface SSLService {
    Map getSSL(String serialNumber) throws Exception;
    SMKeyBeen getSSLs(String serialNumber) throws Exception;
    /**
     * 文件下载功能
     */

    boolean downBks(String serialNumber, HttpSession session,
                    HttpServletRequest request, HttpServletResponse response)
            throws Exception;
    boolean downCer(String serialNumber, HttpSession session,
                    HttpServletRequest request, HttpServletResponse response)
            throws Exception;
}
