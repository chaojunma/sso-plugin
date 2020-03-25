package com.mk.sso.filter;

import javax.servlet.*;
import java.io.IOException;
import com.alibaba.fastjson.JSONObject;
import com.mk.sso.SsoProperties;
import com.mk.sso.constant.Constants;
import com.mk.sso.entity.User;
import com.mk.sso.util.SsoUtil;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

public class AuthFilter implements Filter {

    private SsoProperties properties;

    public AuthFilter(SsoProperties properties) {
        this.properties = properties;
    }

    private static final PathMatcher PATH_MATCHER = new AntPathMatcher();


    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest)request;
        HttpServletResponse res = (HttpServletResponse)response;

        //访问路径
        String url = req.getRequestURI();
        // 不需要验证
        if(isAllow(url)) {
            chain.doFilter(request, response);
            return;
        }

        // 判断是否已经登录
        if(isLogined(req, res)) {
            chain.doFilter(request, response);
            return;
        }

        SsoUtil.redirectLogin(req,res,properties);
    }

    public void destroy() {

    }

    /**
     * 判断用户是否登录
     * @param request
     * @param response
     * @return
     */
    public boolean isLogined(HttpServletRequest request, HttpServletResponse response){
        // 默认未登录
        boolean isLogined = false;

        // 通过key获取cookie信息
        String userJson = SsoUtil.getCookieVal(request, Constants.CURRENT_USER);
        User currentUser = JSONObject.parseObject(userJson, User.class);

        if(!isLogined && currentUser != null) {
            // 标记为已登录
            isLogined = true;
            SsoUtil.setCurrentUser(request, currentUser);
        }

        return isLogined;
    }


    /**
     * 判断请求路径是否放行
     * @param path
     * @return
     */
    public boolean isAllow(String path) {

        // 获取Resource白名单
        String[] ignoreResources = properties.getIgnoreResources();

        for (String ignoreResource :ignoreResources) {
            if (path.endsWith(ignoreResource)) {
                return true;
            }
        }

        if (!path.startsWith("/")) {
            path = "/" + path;
        }
        // 获取URL白名单
        String[] ignoredPaths = properties.getIgnoreUrls();
        for (String ignoredPath : ignoredPaths) {
            if (PATH_MATCHER.match(ignoredPath, path)) {
                return true;
            }
        }

        return false;
    }
}
