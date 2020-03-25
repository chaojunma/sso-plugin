package com.mk.sso.filter;

import javax.servlet.*;
import java.io.IOException;
import com.mk.sso.SsoProperties;
import com.mk.sso.constant.Constants;
import com.mk.sso.util.SsoUtil;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class SignOutFilter implements Filter {

    private SsoProperties properties;

    public SignOutFilter(SsoProperties properties) {
        this.properties = properties;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest)request;
        HttpServletResponse res = (HttpServletResponse)response;
        // 清除session
        SsoUtil.removeSession(req);
        // 清除cookie
        SsoUtil.removeCookie(req, res, Constants.CURRENT_USER);
        // 跳转到登录页
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }


}
