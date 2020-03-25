package com.mk.sso;

import com.mk.sso.filter.AuthFilter;
import com.mk.sso.filter.SignOutFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(prefix = "sso", value = "enable",matchIfMissing = false)
public class SsoAutoConfiguration {

    @Autowired
    private SsoProperties properties;

    @Bean
    public FilterRegistrationBean authFiler(){
        FilterRegistrationBean bean = new FilterRegistrationBean();
        bean.setOrder(1);
        bean.setFilter(new AuthFilter(properties));
        bean.addUrlPatterns("/*");
        return bean;
    }


    @Bean
    public FilterRegistrationBean signOutFilter(){
        FilterRegistrationBean bean = new FilterRegistrationBean();
        bean.setOrder(2);
        bean.setFilter(new SignOutFilter(properties));
        bean.addUrlPatterns("/sso/logout", "/logout");
        return bean;
    }
}
