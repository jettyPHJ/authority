package com.gsd.controller;


import com.gsd.common.core.domain.entity.SysUser;
import com.gsd.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 用户信息Controller
 * 
 * @author ruoyi
 * @date 2022-11-17
 */
@RestController
@RequestMapping("/system/user")
public class SysUserController
{
    @Autowired
    private ISysUserService sysUserService;

    /**
     * 查询用户信息列表
     */
    @GetMapping("/list")
    public List<SysUser> list(SysUser sysUser)
    {
        List<SysUser> list = sysUserService.selectSysUserList(sysUser);
        return list;
    }


}
