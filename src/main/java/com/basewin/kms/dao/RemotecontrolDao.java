package com.basewin.kms.dao;

import com.basewin.kms.entity.RemotecontrolBeen;

import java.util.List;

//@CacheConfig(cacheNames = "RemotecontrolDao")
public interface RemotecontrolDao {

    //@Cacheable(value = "ssl", key = "'getSSLs'.concat(#serialNumber)")
    // @Cacheable(key="'getRemotecontrol'+#p0")
    List<RemotecontrolBeen> getRemotecontrol(RemotecontrolBeen remotecontrolBeen);

    // @CacheEvict(key="'getRemotecontrol'+#p0.getSerialnumber()")
    int updateRemotecontrol(RemotecontrolBeen remotecontrolBeen);

    //@CacheEvict(key="'getRemotecontrol'+#p0.getSerialnumber()")
    int insertRemotecontrol(RemotecontrolBeen RemotecontrolBeen);

    int deleteRemotecontrol(RemotecontrolBeen RemotecontrolBeen);
}
