package com.iqiny.silly.demo.common.sc;

import com.iqiny.silly.core.config.SillyCurrentUserUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

@Component
public class MyCurrentUserUtil implements SillyCurrentUserUtil {

    /**
     * 系统管理员ID
     */
    public static final String ADMIN_KEY = "admin";
    /**
     * 用户信息存放处
     */
    public static final String CURRENT_USER_ATTRIBUTE_NAME = "currentUser";

    @Override
    public boolean isAdmin() {
        return isAdmin(currentUserId());
    }

    @Override
    public boolean isAdmin(String userId) {
        return ADMIN_KEY.equals(userId);
    }

    @Override
    public String currentUserId() {
        HttpServletRequest request = ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getRequest();
        Object currentUser = request.getHeader(CURRENT_USER_ATTRIBUTE_NAME);
        return currentUser == null ? "system" : currentUser.toString();
    }

    @Override
    public String userIdToName(String userId) {
        return userId + "子";
    }

}
