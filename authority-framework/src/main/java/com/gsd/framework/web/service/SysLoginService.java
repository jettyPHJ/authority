package com.gsd.framework.web.service;

import com.gsd.common.constant.Constants;
import com.gsd.common.core.domain.model.LoginUser;
import com.gsd.common.core.redis.RedisCache;
import com.gsd.common.exception.ServiceException;
import com.gsd.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class SysLoginService {
    @Resource
    private AuthenticationManager authenticationManager;
    @Autowired
    private RedisCache redisCache;
    @Autowired
    private TokenService tokenService;

    public String login(String username, String password, String code, String uuid) {
        validateCaptcha(username, code, uuid);
        Authentication authenticate = null;
        try {
             authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (Exception e) {
            if (e instanceof BadCredentialsException) {
                throw new ServiceException(e.getMessage());
            } else {
                throw new ServiceException(e.getMessage());
            }
        }
        LoginUser loginUser = (LoginUser)authenticate.getPrincipal();
        return tokenService.creatToken(loginUser);
    }


    public void validateCaptcha(String username, String code, String uuid) {
        String verifyKey = Constants.CAPTCHA_CODE_KEY + StringUtils.nvl(uuid, "");
        String captcha = redisCache.getCacheObject(verifyKey);
        redisCache.deleteObject(verifyKey);
        if (captcha == null) {
            throw new ServiceException("验证码不存在");
        }
        if (!code.equalsIgnoreCase(captcha)) {
            throw new ServiceException("验证码错误");
        }
    }
}
