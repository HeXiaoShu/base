package com.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import javax.sql.DataSource;
import java.util.HashMap;

/**
 * @Description  Druid数据源配置  localhost:80/druid
 * @Author Hexiaoshu
 * @Date 2021/4/13
 * @modify
 */
@Configuration
public class DruidConfig {

    @ConfigurationProperties(prefix = "spring.datasource")
    @Bean
    public DataSource druidDataSource(){
        return new DruidDataSource();
    }

    /**
     * 日志监控 ,//禁止谁访问 initParameters.put("name","192.168.11.123");
     * @return ServletRegistrationBean
     */
    @Bean
    public ServletRegistrationBean statViewServlet(){
        ServletRegistrationBean<StatViewServlet> bean = new ServletRegistrationBean<>(new StatViewServlet(),"/druid/*");
        HashMap<String,String> initParameters = new HashMap<>(16);
        //监控页面登录用户
        initParameters.put("loginUsername","xiaoshu");
        initParameters.put("loginPassword","730730");
        //运行谁访问, "" 所有
        initParameters.put("allow","");
        bean.setInitParameters(initParameters);
        return bean;
    }

    /**
     * 过滤配置
     * @return FilterRegistrationBean
     */
    @Bean
    public FilterRegistrationBean filter(){
        FilterRegistrationBean bean = new FilterRegistrationBean();
        bean.setFilter(new WebStatFilter());
        HashMap<String,String> initParameters = new HashMap<>(2);
        //不统计
        initParameters.put("exclusions","*.js,/druid/*");
        bean.setInitParameters(initParameters);
        return bean;
    }



}
