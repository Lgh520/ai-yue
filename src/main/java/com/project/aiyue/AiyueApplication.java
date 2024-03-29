package com.project.aiyue;

import com.project.aiyue.utils.WxPayUtil;
import com.wechat.pay.java.core.Config;
import com.wechat.pay.java.core.RSAAutoCertificateConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@ComponentScan("com.project.aiyue.*")
@MapperScan("com.project.aiyue.dao")
@EnableSwagger2
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class AiyueApplication {

    @RequestMapping
    public static void main(String[] args) {
        SpringApplication.run(AiyueApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplateBuilder().build();
    }

    @Bean
    public Config getWxConfig() {
        return new RSAAutoCertificateConfig.Builder()
                .merchantId(WxPayUtil.merchantId)
                .privateKeyFromPath(WxPayUtil.privateKeyPath)
                .merchantSerialNumber(WxPayUtil.merchantSerialNumber)
                .apiV3Key(WxPayUtil.apiV3key)
                .build();
    }
}
