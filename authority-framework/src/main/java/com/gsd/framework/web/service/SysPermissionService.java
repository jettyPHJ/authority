package com.gsd.framework.web.service;

import com.gsd.common.core.domain.entity.SysUser;
import com.gsd.system.service.ISysMenuService;
import com.gsd.system.service.ISysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class SysPermissionService {
    @Autowired
    private ISysMenuService menuService;

    @Autowired
    private ISysRoleService roleService;
    public Set<String> getMenuPermission(SysUser user) {
        Set<String> perms = new HashSet<String>();
        if (user.isAdmin()) {
            perms.add("*:*:*");
        } else {
            perms.addAll(menuService.selectMenuPermsByUserId(user.getUserId()));
        }
        return perms;
    }

    public Set<String> getRolePermission(SysUser user){
        Set<String> roles = new HashSet<String>();
        if (user.isAdmin()) {
            roles.add("admin");
        } else {
            roles.addAll(roleService.selectRolePermissionByUserId(user.getUserId()));
        }
        return roles;
    }
}
