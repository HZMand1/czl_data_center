package cn.paohe;

import cn.paohe.base.config.RedisConfig;
import cn.paohe.base.datasource.mybatis.DynamicDataSourceRegister;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @Copyright (c) by paohe information technology Co., Ltd.
 * @All right reserved.
 * @Project: config_center
 * @File: Application.java
 * @Description: 配置中心启动类
 * @Author: yuanzhenhui
 * @Date: 2020/07/01
 */
@Import(DynamicDataSourceRegister.class)
@MapperScan({"cn.paohe.*.dao", "cn.paohe.base.dict.dao", "cn.paohe.dao"})
@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootConfiguration
@ComponentScan(excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {RedisConfig.class})
})
public class ConfigApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(ConfigApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(ConfigApplication.class, args);
    }

}
