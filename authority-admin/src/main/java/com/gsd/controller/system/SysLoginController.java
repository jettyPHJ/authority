package com.gsd.controller.system;

import com.gsd.common.constant.Constants;
import com.gsd.common.core.domain.AjaxResult;
import com.gsd.common.core.domain.entity.SysMenu;
import com.gsd.common.core.domain.entity.SysUser;
import com.gsd.common.core.domain.model.LoginBody;
import com.gsd.common.utils.SecurityUtils;
import com.gsd.common.utils.StringUtils;
import com.gsd.framework.web.service.SysLoginService;
import com.gsd.framework.web.service.SysPermissionService;
import com.gsd.system.service.ISysMenuService;
import com.gsd.system.service.impl.SysUserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Set;

@RestController
public class SysLoginController {
    @Autowired
    private SysLoginService loginService;

    @Autowired
    private SysPermissionService permissionService;

    @Autowired
    private ISysMenuService menuService;

    @Autowired
    private SysUserServiceImpl sysUserServiceImpl;


    @PostMapping("/login")
    public AjaxResult login(@RequestBody LoginBody loginBody) {
        //System.out.println("用户登录");
        AjaxResult ajaxResult = AjaxResult.success();
        String token = loginService.login(loginBody.getUsername(), loginBody.getPassword(), loginBody.getCode(), loginBody.getUuid());
        ajaxResult.put(Constants.TOKEN, token);
        return ajaxResult;
    }


    @GetMapping("/getInfo")
    public AjaxResult getInfo(HttpServletResponse response) {
        //response.addHeader("Access-Control-Allow-Origin", "*");
        //System.out.println("获取信息！");
        SysUser user = SecurityUtils.getLoginUser().getUser();
        Set<String> roles = permissionService.getRolePermission(user);
        //如果没有角色，默认一个
        if(roles.isEmpty()){
            Long[] roleIds={2L};
            sysUserServiceImpl.insertUserRole(user.getUserId(),roleIds);
            roles = permissionService.getRolePermission(user);
        }
        Set<String> permissions = permissionService.getMenuPermission(user);
        AjaxResult ajax = AjaxResult.success();
        ajax.put("user", user);
        ajax.put("roles", roles);
        ajax.put("permissions", permissions);
        return ajax;
    }


    @GetMapping("getRouters")
    public AjaxResult getRouters() {
        Long userId = SecurityUtils.getUserId();
        List<SysMenu> menus = menuService.selectMenuTreeByUserId(userId);
        List<SysMenu> menus2 = menuService.buildMenuTree(menus);
        return AjaxResult.success(menuService.buildMenus(menus2));
    }
}
