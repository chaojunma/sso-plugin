package com.mk.sso.util;

import com.mk.sso.constant.Constants;
import com.mk.sso.entity.User;

import java.util.Map;
import javax.servlet.http.HttpSession;
import java.util.concurrent.ConcurrentHashMap;

public class SessionContext {

    private static final Map<String, HttpSession> HTTP_SESSIONS  = new ConcurrentHashMap<>();

    /**
     * 新增session
     * @param session
     */
    public static void AddSession(HttpSession session) {
        if (session != null) {
            User user = (User) session.getAttribute(Constants.CURRENT_USER);
            HTTP_SESSIONS.put(session.getId(), session);
        }
    }

    /**
     * 删除session
     * @param session
     */
    public static void DelSession(HttpSession session) {
        if (session != null) {
            HTTP_SESSIONS.remove(session.getId());
        }
    }

    /**
     * 获取session
     * @param session_id
     * @return
     */
    public static HttpSession getSession(String session_id) {
        if (session_id == null)
            return null;
        return HTTP_SESSIONS.get(session_id);
    }

}
