package com.basewin.kms;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@ComponentScan(basePackages = {"com.basewin.kms"})//自动扫描组件
@EnableTransactionManagement //如果mybatis中service实现类中加入事务注解，需要此处添加该注解
@MapperScan("com.basewin.kms.dao")//扫描dao层
public class KmsApplication extends SpringBootServletInitializer {
	public static void main(String[] args) {
		SpringApplication.run(KmsApplication.class, args);
	}
}