package com.gsd.system.service;

import com.gsd.common.core.domain.entity.SysUser;

import java.util.List;

/**
 * 用户信息Service接口
 * 
 * @author ruoyi
 * @date 2022-11-17
 */
public interface ISysUserService 
{

    public List<SysUser> selectSysUserList(SysUser sysUser);

    public SysUser selectUserByUserName(String username);
    /**
     * 校验用户名称是否唯一
     *
     * @param user 用户信息
     * @return 结果
     */
    public String checkUserNameUnique(SysUser user);
    public boolean registerUser(SysUser user);
}
