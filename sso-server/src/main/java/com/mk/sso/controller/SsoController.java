package com.mk.sso.controller;

import javax.servlet.http.Cookie;
import com.mk.sso.constant.Constants;
import com.mk.sso.entity.User;
import com.mk.sso.service.UserService;
import com.mk.sso.util.BASE64Util;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.ModelMap;
import com.alibaba.fastjson.JSONObject;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;


@Slf4j
@Controller
@RequestMapping("/sso")
public class SsoController {

    @Value("${sso.default-webapp}")
    private String defaltWebapp;

    @Autowired
    private UserService userService;


    @RequestMapping("/loginPage")
    private String loginPage(String webapp, ModelMap model) {
        model.put("webapp", webapp);
        return "/login";
    }


    @PostMapping("/login")
    public String login(@RequestParam(value = "username", required = true) String username,
                        @RequestParam(value = "password", required = true) String password,
                        @RequestParam("webapp") String webapp,
                        HttpServletResponse response,
                        ModelMap model) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username", username);
        wrapper.eq("password", password);
        User user = userService.getOne(wrapper);
        if(user != null) {
            Cookie cookie = new Cookie(Constants.CURRENT_USER, BASE64Util.encode(JSONObject.toJSONString(user)));
            cookie.setMaxAge(60 * 3);
            //设置访问路径
            cookie.setPath("/");
            response.addCookie(cookie);
            //重定向到原先访问的页面
            String url = StringUtils.EMPTY;
            try {
                 if(StringUtils.isEmpty(webapp)){
                     webapp = defaltWebapp;
                 }
                 url = "redirect:" + URLDecoder.decode(webapp, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                log.error(e.getMessage(), e);
            }
            return url;
        } else {
            model.put("msg","用户名或密码错误");
            return "/login";
        }

    }


}
