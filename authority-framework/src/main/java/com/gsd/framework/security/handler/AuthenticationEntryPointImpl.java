package com.gsd.framework.security.handler;

import com.alibaba.druid.support.json.JSONUtils;
import com.gsd.common.constant.HttpStatus;
import com.gsd.common.core.domain.AjaxResult;
import com.gsd.common.utils.ServletUtils;
import com.gsd.common.utils.StringUtils;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        int code = HttpStatus.UNAUTHORIZED;
        String msg = "认证失败";
        ServletUtils.renderString(response, JSONUtils.toJSONString(AjaxResult.error(code,msg)));
    }
}
