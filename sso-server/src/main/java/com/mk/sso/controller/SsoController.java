package com.mk.sso.controller;


import java.net.URLDecoder;
import com.mk.sso.entity.User;
import com.mk.sso.util.SsoUtil;
import lombok.extern.slf4j.Slf4j;
import com.mk.sso.SsoProperties;
import com.mk.sso.constant.Constants;
import com.mk.sso.service.UserService;
import com.alibaba.fastjson.JSONObject;
import org.springframework.ui.ModelMap;
import org.apache.commons.lang.StringUtils;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import java.io.UnsupportedEncodingException;



@Slf4j
@Controller
@RequestMapping("/sso")
public class SsoController {

    @Autowired
    private SsoProperties properties;

    @Autowired
    private UserService userService;


    /**
     * 调准登录页
     * @param webapp
     * @param model
     * @return
     */
    @RequestMapping("/loginPage")
    private String loginPage(String webapp, ModelMap model) {
        model.put("webapp", webapp);
        return "/login";
    }


    /**
     * 用户登录
     * @param username
     * @param password
     * @param webapp
     * @param request
     * @param response
     * @param model
     * @return
     */
    @PostMapping("/login")
    public String login(@RequestParam(value = "username", required = true) String username,
                        @RequestParam(value = "password", required = true) String password,
                        @RequestParam("webapp") String webapp,
                        HttpServletRequest request,
                        HttpServletResponse response,
                        ModelMap model) {

        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username", username);
        wrapper.eq("password", password);
        User user = userService.getOne(wrapper);

        if(user != null) {
            // 将用户信息保存到Cookie
            SsoUtil.saveCookie(request, response, properties, Constants.CURRENT_USER, JSONObject.toJSONString(user));
            //重定向到原先访问的页面
             if(StringUtils.isEmpty(webapp)){
                return "redirect:/admin";
             } else {
                 String url = StringUtils.EMPTY;
                 try {
                     url = "redirect:" + URLDecoder.decode(webapp, "UTF-8");
                 } catch (UnsupportedEncodingException e) {
                     log.error(e.getMessage(), e);
                 }
                 return url;
             }
        } else {
            model.put("msg","用户名或密码错误");
            return "/login";
        }
    }


}
