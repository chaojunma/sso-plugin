package com.mk.sso.util;

import com.mk.sso.SsoProperties;
import com.mk.sso.constant.Constants;
import com.mk.sso.entity.User;
import org.apache.commons.lang.StringUtils;
import javax.servlet.http.Cookie;
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
        HttpSession session = request.getSession();
        if(session ==  null){
            return null;
        }
        return (User)session.getAttribute(Constants.CURRENT_USER);
    }


    /**
     * 清空session
     * @param request
     */
    public static void removeSession(HttpServletRequest request){
        HttpSession session = request.getSession();
        session.invalidate();
    }


    /**
     * 设置cookie
     * @param request
     * @param response
     * @param properties
     * @param key
     * @param value
     */
    public static void saveCookie (HttpServletRequest request, HttpServletResponse response,
                        SsoProperties properties, String key, String value){
        Cookie cookie = new Cookie(key, BASE64Util.encode(value));
        cookie.setMaxAge(properties.getCookieMaxAge());
        //设置访问路径
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
    }


    /**
     * 通过key获取cookie信息
     * @param request
     * @param key
     * @return
     */
    public static String getCookieVal(HttpServletRequest request, String key){
        String value = StringUtils.EMPTY;
        Cookie[] cookies = request.getCookies();
        if(cookies != null){
            for (Cookie cookie : cookies) {
                if (key.equals(cookie.getName())) {
                    value = BASE64Util.decode(cookie.getValue());
                }
            }
        }
        return  value;
    }


    /**
     * 删除cookie
     * @param request
     * @param response
     * @param key
     */
    public static void removeCookie(HttpServletRequest request, HttpServletResponse response, String key) {
        Cookie[] cookies = request.getCookies();
        if(cookies != null){
            for (Cookie cookie : cookies) {
                if (key.equals(cookie.getName())) {
                    cookie.setMaxAge(0);
                    cookie.setPath("/");
                    response.addCookie(cookie);
                }
            }
        }
    }



}
