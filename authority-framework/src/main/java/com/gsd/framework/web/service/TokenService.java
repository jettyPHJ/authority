package com.gsd.framework.web.service;

import com.gsd.common.constant.Constants;
import com.gsd.common.core.domain.model.LoginUser;
import com.gsd.common.core.redis.RedisCache;
import com.gsd.common.utils.StringUtils;
import com.gsd.common.utils.uuid.IdUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
public class TokenService {
    @Value("${token.secret}")
    private String secret;
    @Value("${token.expireTime}")
    private int expireTime;
    @Value("${token.header}")
    private String header;

    @Autowired
    private RedisCache redisCache;

    public LoginUser getLoginUser(HttpServletRequest request){
         String token = getToken(request);
        if (StringUtils.isNotEmpty(token)) {
            Claims claims = parseToken(token);
            String uuid = (String) claims.get(Constants.LOGIN_USER_KEY);
            String userKey = getTokenKey(uuid);
            return redisCache.getCacheObject(userKey);
        }
        return null;
    }

    public void delLoginUser(String token){
        if(StringUtils.isNotEmpty(token)){
            String userKey = getTokenKey(token);
            redisCache.deleteObject(userKey);
        }
    }

    public String creatToken(LoginUser loginUser) {
        String token = IdUtils.fastUUID();
        loginUser.setToken(token);
        refreshToken(loginUser);
        Map<String, Object> claims = new HashMap<String, Object>();
        claims.put(Constants.LOGIN_USER_KEY, token);
        return creatToken(claims);
    }

    private String creatToken(Map<String, Object> claims) {
        return Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS512, secret).compact();
    }

    private void refreshToken(LoginUser loginUser){
        String userKey = getTokenKey(loginUser.getToken());
        redisCache.setCacheObject(userKey,loginUser,expireTime, TimeUnit.MINUTES);
    }

    private String getTokenKey(String uuid){
        return Constants.LOGIN_TOKEN_KEY+uuid;
    }

    private String getToken(HttpServletRequest request) {
        String token = request.getHeader(this.header);
        if (StringUtils.isNotEmpty(token) && token.startsWith(Constants.TOKEN_PREFIX)) {
            token = token.replace(Constants.TOKEN_PREFIX, "");
        }
        return token;
    }

    private Claims parseToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }
}
