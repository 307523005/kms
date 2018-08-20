package com.basewin.kms.dao;

import com.basewin.kms.entity.SMKeyBeen;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

@CacheConfig
public interface SMKeyDao {
    @Cacheable(value = "getkey", key = "#p0")
    List<SMKeyBeen> getkey(String dsn);
    @CacheEvict(value = "getkey",key="+#p0.getSerialnumber()")
    int updates(SMKeyBeen sslBeen);
    @CacheEvict(value = "getkey", allEntries = true)
    int inserts(SMKeyBeen sslBeen);
}
