package com.mk.sso.util;

import com.mk.sso.SsoProperties;
import com.mk.sso.constant.Constants;
import com.mk.sso.entity.User;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URLEncoder;

public class SsoUtil {

    /**
     * 跳转到登录页
     * @param request
     * @param response
     * @param ssoProperties
     * @throws IOException
     */
    public static void redirectLogin(HttpServletRequest request,
                                     HttpServletResponse response,
                                     SsoProperties ssoProperties) throws IOException {
        //访问路径
        String url = request.getRequestURL().toString();
        String ssoServer = ssoProperties.getSsoServer();

        if(!ssoServer.endsWith("/")) {
            ssoServer += "/";
        }

        url = ssoServer + "sso/loginPage?webapp=" + URLEncoder.encode(url, "UTF-8");

        response.sendRedirect(url);
    }


    /**
     * 将当前用户存储到session作用域
     * @param request
     * @param currentUser
     */
    public static void setCurrentUser(HttpServletRequest request, User currentUser){
        HttpSession session = request.getSession();
        if(session.getAttribute(Constants.CURRENT_USER) == null) {
            session.setAttribute(Constants.CURRENT_USER, currentUser);
        }
    }


    /**
     * 从session作用域获取当前用户
     * @param request
     * @return
     */
    public static User getCurrentUser(HttpServletRequest request){
        return (User) request.getSession().getAttribute(Constants.CURRENT_USER);
    }
}
