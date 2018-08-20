package com.basewin.kms.dao;

import com.basewin.kms.entity.SSLBeen;

import java.util.List;

//@CacheConfig(cacheNames = "SSLDao")
public interface SSLDao  {
  //  @Cacheable(key="'getClientbks'+#p0")
    List<SSLBeen> getClientbks(String dsn);
    //@Cacheable(value = "SSLDao" ,key="")
    List<SSLBeen> getALL();
   // @CacheEvict(key="'getkey'+#p0.getSerialnumber()")
    int updates(SSLBeen sslBeen);
    int inserts(SSLBeen sslBeen);

}