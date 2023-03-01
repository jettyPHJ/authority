package com.gsd.framework.security.handler;

import com.alibaba.druid.support.json.JSONUtils;
import com.gsd.common.constant.HttpStatus;
import com.gsd.common.core.domain.AjaxResult;
import com.gsd.common.core.domain.model.LoginUser;
import com.gsd.common.utils.ServletUtils;
import com.gsd.common.utils.StringUtils;
import com.gsd.framework.web.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
public class LogoutSuccessHandlerImpl implements LogoutSuccessHandler {
    @Autowired
    private TokenService tokenService;
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        LoginUser loginUser = tokenService.getLoginUser(request);
        if(StringUtils.isNotNull(loginUser)){
            String username = loginUser.getUsername();
            tokenService.delLoginUser(loginUser.getToken());
        }
        ServletUtils.renderString(response, JSONUtils.toJSONString(AjaxResult.success(HttpStatus.SUCCESS,"退出成功")));


    }
}
