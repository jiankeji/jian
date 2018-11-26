package com.jian.portal.reg;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan(basePackages = {"com.jian.core.server.dao"})//注释用它,XML文件配YML
@ComponentScan(basePackages = {"com.jian.core","com.jian.portal"})
public class PortalRegApplication {

	public static void main(String[] args) {
		SpringApplication.run(PortalRegApplication.class, args);
	}
}
