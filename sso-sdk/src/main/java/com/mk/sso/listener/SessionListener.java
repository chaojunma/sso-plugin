package com.mk.sso.listener;

import com.mk.sso.constant.Constants;
import com.mk.sso.util.SessionContext;
import lombok.extern.slf4j.Slf4j;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.*;


@Slf4j
@WebListener
public class SessionListener implements HttpSessionListener, HttpSessionAttributeListener {

    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
        HttpSession session = httpSessionEvent.getSession();
        log.info("创建session,seesionId:{}", session.getId());
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        HttpSession session = httpSessionEvent.getSession();
        log.info("销毁session,seesionId:{}", session.getId());
        SessionContext.DelSession(httpSessionEvent.getSession());
    }

    @Override
    public void attributeAdded(HttpSessionBindingEvent event) {
        log.info("session新增属性,key:{},value:{}", event.getName(), event.getValue());
        if(event.getName().equals(Constants.CURRENT_USER)) {
            HttpSession session = event.getSession();
            SessionContext.AddSession(session);
        }
    }

    @Override
    public void attributeRemoved(HttpSessionBindingEvent event) {
        log.info("session删除属性,key:{},value:{}", event.getName(), event.getValue());
        SessionContext.DelSession(event.getSession());
    }

    @Override
    public void attributeReplaced(HttpSessionBindingEvent event) {
        log.info("session替换属性,key:{},value:{}", event.getName(), event.getValue());
        if(event.getName().equals(Constants.CURRENT_USER)) {
            HttpSession session = event.getSession();
            SessionContext.AddSession(session);
        }
    }
}
