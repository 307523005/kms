package com.basewin.kms.controller;

import com.basewin.kms.entity.SMKeyBeen;
import com.basewin.kms.entity.UserTest;
import com.basewin.kms.service.SSLService;
import com.basewin.kms.servlet.Intercept.OenCurrent;
import com.basewin.kms.servlet.Intercept.OenIntercept;
import com.basewin.kms.servlet.Intercept.TwoCurrent;
import com.basewin.kms.servlet.Intercept.TwoIntercept;
import com.basewin.kms.servlet.redis.RedisUtils;
import com.basewin.kms.util.Ret;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.concurrent.TimeUnit;


/**
 * RESTful 风格
 */
@Controller
@RequestMapping("/ssl")
public class SSLController {
    //将你要加入日志的类加进去
    protected static Logger logger = LoggerFactory.getLogger(SSLController.class);
    @Autowired
    private SSLService sSLService;
    @Autowired
    private RedisUtils redisUtils;
    //RedisTemplate redisTemplate;

    @RequestMapping("/getssl/{serialNumber}")
    @ResponseBody
    //@Pathvariable注解绑定它传过来的值到方法的参数上//RESTful 风格//http://10.20.10.86:9080/ssl/getssl/P100AFH00W
    public Ret<Map> getSSL(@PathVariable("serialNumber") String serialNumber) throws Exception {
        //template.opsForValue().set("niuhao", "niuhao123", 5, TimeUnit.MINUTES);
        redisUtils.set("niuhao11", "niuhao11", 10, TimeUnit.SECONDS);
        System.out.println(redisUtils.get("niuhao11"));
        System.out.println("serialNumber--"+serialNumber);
        if (serialNumber != null && !serialNumber.equals("")) {
            Map getssl = sSLService.getSSL(serialNumber);
            if (getssl != null) {
                logger.info("ssl成功");
                return Ret.success(getssl);
            }
        }
        return Ret.error("500", "ssl获取失败");
    }

    @RequestMapping("/getsslbks")
    @ResponseBody
    public void downBks(@RequestParam(required = true) String serialNumber, HttpSession session,
                        HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        //serialNumber = "client";
        if (serialNumber != null && !serialNumber.equals("")) {
            boolean down = sSLService.downBks(serialNumber, session, request, response);
            if (down) {
                logger.info("bks成功");
                // return Ret.success("bks成功");
            }
        }
        //return Ret.error("bks获取失败","bks获取失败");
    }

    @RequestMapping("/getsslcer")
    @ResponseBody
    public void downCer(@RequestParam(required = true) String serialNumber, HttpSession session,
                        HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        serialNumber = "client";
        if (serialNumber != null && !serialNumber.equals("")) {
            boolean down = sSLService.downCer(serialNumber, session, request, response);
            if (down) {
                logger.info("cer成功");
                //   return Ret.success("cer成功");
            }
        }
        //  return Ret.error("cer获取失败","cer获取失败");

    }

    @OenIntercept
    @RequestMapping("/test")
    @ResponseBody
    String test(@OenCurrent SMKeyBeen sMKeyBeen) throws Exception {
        SMKeyBeen niuhao = (SMKeyBeen) redisUtils.get("niuhao");
        System.out.println(niuhao.getAddtime());
        System.out.println(redisUtils.get("niuhao11"));
        //System.out.println("---0-000-"+sMKeyBeen.getId());
        System.out.println("---0-000-" + sMKeyBeen.getId());
        return "111111111";
    }

    @TwoIntercept
    @RequestMapping("/tWO")
    @ResponseBody
    public String tWO(@TwoCurrent SMKeyBeen sMKeyBeen) throws Exception {
        //  SMKeyBeen niuhao = crudRepository.findOne("123456");
        //  System.out.println(niuhao.getAddtime());
        //System.out.println("---0-000-"+sMKeyBeen.getId());
        System.out.println("---0-000-" + sMKeyBeen.getId());
        return "111122";
    }


    @RequestMapping("/test1")
    String test1(ModelMap modelMap )   {
        //Model model
        // ModelMap modelMap
        //modelMap.put("user", user);
        //ModelAndView
        //构建ModelAndView实例，并设置跳转地址
        //ModelAndView view = new ModelAndView("test");
        UserTest user=new UserTest();
        user.setAge(1);
        user.setId(1);
        user.setPassword("12113");
        user.setUserName("钮豪");
        modelMap.put("user", user);
        return "test";
    }
    @RequestMapping("/login")
    String test1(  )   {

        return "/login/login";
    }


}
