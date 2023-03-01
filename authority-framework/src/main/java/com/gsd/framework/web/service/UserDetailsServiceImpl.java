package com.gsd.framework.web.service;

import com.gsd.common.core.domain.entity.SysUser;
import com.gsd.common.core.domain.model.LoginUser;
import com.gsd.common.enums.UserStatus;
import com.gsd.common.exception.ServiceException;
import com.gsd.common.utils.StringUtils;
import com.gsd.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private ISysUserService userService;

    @Autowired
    private SysPermissionService sysPermissionService;

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser user = userService.selectUserByUserName(username);
        if (StringUtils.isNull(user)) {
            throw new ServiceException("登录用户" + username + "不存在");
        } else if (UserStatus.DELETED.getCode().equals(user.getDelFlag())) {
            throw new ServiceException("对不起，您的账号" + username + "已被删除");
        } else if (UserStatus.DISABLE.getCode().equals(user.getStatus())) {
            throw new ServiceException("对不起，您的账号" + username + "已被停用");
        }
        return createLoginUser(user);
    }

    public UserDetails createLoginUser(SysUser user) {
        return new LoginUser(user.getUserId(), user, sysPermissionService.getMenuPermission(user));
    }
}
