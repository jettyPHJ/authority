package com.gsd.common.utils;

import com.gsd.common.constant.HttpStatus;
import com.gsd.common.core.domain.model.LoginUser;
import com.gsd.common.exception.ServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;


@Component
public class SecurityUtils {
    public static LoginUser getLoginUser() {
        try {
            return (LoginUser) getAuthentication().getPrincipal();
        } catch (Exception e) {
            throw new ServiceException(HttpStatus.UNAUTHORIZED, "获取用户信息异常");
        }
    }
    public static  Long getUserId(){
        return getLoginUser().getUserId();
    }
    public static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public static boolean isAdmin(Long userId){
        return userId !=null && userId == 1L;
    }

    public static String encryptPassword(String password)
    {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }

}
