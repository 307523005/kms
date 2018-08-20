package com.basewin.kms.service.impl;

import com.basewin.kms.dao.SMKeyDao;
import com.basewin.kms.dao.SSLDao;
import com.basewin.kms.entity.SMKeyBeen;
import com.basewin.kms.entity.SSLBeen;
import com.basewin.kms.service.SSLService;
import com.basewin.kms.util.ParametersUtil;
import com.basewin.kms.util.sm.SM2Utils;
import com.basewin.kms.util.FileDownUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Service
@Transactional//配置事务处理
public class SSLServiceImpl implements SSLService {
    protected static Logger logger = LoggerFactory.getLogger(SSLServiceImpl.class);
    @Autowired
    SSLDao ssldao;
    @Autowired
    SMKeyDao smKeyDao;

    @Override
    public Map getSSL(String serialNumber) throws Exception {
        //根据设备号生成
        //boolean ssl = SSLTool.SSL(serialNumber);
        boolean ssl=true;
        if (ssl) {
            String clientpath = ParametersUtil.CLIENTPATH;
            String bksPath = clientpath + serialNumber + "/" + serialNumber + ".bks";///usr/local/springboot
            String cerPath = clientpath + serialNumber + "/" + serialNumber + ".cer";
            List<SSLBeen> clientbks = ssldao.getClientbks(serialNumber);
            SSLBeen sSLBeen = new SSLBeen();
            sSLBeen.setBkspath(bksPath);
            sSLBeen.setCerpath(cerPath);
            sSLBeen.setSerialnumber(serialNumber);
            int ret = 0;
            logger.info(String.valueOf(clientbks.size()));
            if (clientbks.size() == 1) {
               // ret = ssldao.updates(sSLBeen);
                ret=1;
            } else {
                ret = ssldao.inserts(sSLBeen);
            }
            if (ret > 0) {
                Map mapkey = SM2Utils.generateKeyPair();
                //String s = JSONUtils.toJSONString(mapkey);
             /*   String publicKey =  mapkey.get("publicKey").toString();
                String privateKey =  mapkey.get("privateKey").toString();*/
                SMKeyBeen smKeyBeen = new SMKeyBeen();
                smKeyBeen.setSerialnumber(serialNumber);
                smKeyBeen.setPrik(mapkey.get("privateKey").toString());
                smKeyBeen.setPubk(mapkey.get("publicKey").toString());
                List<SMKeyBeen> getkey = smKeyDao.getkey(serialNumber);
                 logger.info(String.valueOf(getkey.size()));
                int up = 0;
                if (getkey.size() > 0) {
                    up = smKeyDao.updates(smKeyBeen);
                } else {
                    up = smKeyDao.inserts(smKeyBeen);
                }
                //if (up > 0) return JSONUtils.toJSONString(mapkey);
                if (up > 0) return mapkey;
            }
        }
        return null;
    }


    @Override
    //@Cacheable(value = "ssl", key = "'getSSLs'.concat(#serialNumber)")
    public SMKeyBeen getSSLs(String serialNumber) throws Exception {
        return smKeyDao.getkey(serialNumber).get(0);
    }

    /**
     * 文件下载功能
     */
    @Override
    public boolean downBks(String serialNumber, HttpSession session,
                           HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String clientpath = ParametersUtil.CLIENTPATH;
        String bksPath = clientpath + serialNumber + "/" + serialNumber + ".bks";///usr/local/springboot
        String cerPath = clientpath + serialNumber + "/" + serialNumber + ".cer";
        SSLBeen sSLBeen = new SSLBeen();
        sSLBeen.setBkspath(bksPath);
        sSLBeen.setCerpath(cerPath);
        sSLBeen.setSerialnumber(serialNumber);
        ssldao.updates(sSLBeen);


        //String bksPath = ParametersUtil.CLIENTPATH + serialNumber + "/" + serialNumber + ".bks";///usr/local/springboot
        return FileDownUtil.downFile(request, response, bksPath, serialNumber + ".bks");
    }

    /**
     * 文件下载功能
     */
    @Override
    public boolean downCer(String serialNumber, HttpSession session,
                           HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        String cerPath = ParametersUtil.CLIENTPATH + serialNumber + "/" + serialNumber + ".cer";
        return FileDownUtil.downFile(request, response, cerPath, serialNumber + ".cer");
        /*long begin = System.currentTimeMillis();
        List<SSLBeen> clientbks = ssldao.getClientbks(serialNumber);

        long ing = System.currentTimeMillis();

         logger.info("第一次请求时间：" + (ing - begin) + "ms");

        SSLBeen sSLBeen = new SSLBeen();
        sSLBeen.setBkspath(bksPath);
        sSLBeen.setCerpath(cerPath);
        sSLBeen.setSerialnumber(serialNumber);
        int ret = 0;
        if (clientbks.size() == 1) {
            ret = ssldao.updates(sSLBeen);
        } else {
            ret = ssldao.inserts(sSLBeen);
        }
        return true;*/

    }
}
