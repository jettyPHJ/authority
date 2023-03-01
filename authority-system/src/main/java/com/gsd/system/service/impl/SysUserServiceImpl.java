package com.gsd.system.service.impl;


import com.gsd.common.constant.UserConstants;
import com.gsd.common.core.domain.entity.SysUser;
import com.gsd.common.utils.StringUtils;
import com.gsd.system.domain.SysUserRole;
import com.gsd.system.mapper.SysUserMapper;
import com.gsd.system.mapper.SysUserRoleMapper;
import com.gsd.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户信息Service业务层处理
 * 
 * @author ruoyi
 * @date 2022-11-17
 */
@Service
public class SysUserServiceImpl implements ISysUserService
{
    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private SysUserRoleMapper userRoleMapper;

    public List<SysUser> selectSysUserList(SysUser sysUser)
    {
        return sysUserMapper.selectSysUserList(sysUser);
    }

    public SysUser selectUserByUserName(String userName) {
        return sysUserMapper.selectUserByUserName(userName);
    }

    @Override
    public String checkUserNameUnique(SysUser user)
    {
        Long userId = StringUtils.isNull(user.getUserId()) ? -1L : user.getUserId();
        SysUser info = sysUserMapper.checkUserNameUnique(user.getUserName());
        if (StringUtils.isNotNull(info) && info.getUserId().longValue() != userId.longValue())
        {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }
    @Override
    public boolean registerUser(SysUser user)
    {
        return sysUserMapper.insertUser(user) > 0;
    }

    public void insertUserRole(Long userId, Long[] roleIds)
    {
        if (roleIds.length>0)
        {
            // 新增用户与角色管理
            List<SysUserRole> list = new ArrayList<SysUserRole>(roleIds.length);
            for (Long roleId : roleIds)
            {
                SysUserRole ur = new SysUserRole();
                ur.setUserId(userId);
                ur.setRoleId(roleId);
                list.add(ur);
            }
            userRoleMapper.batchUserRole(list);
        }
    }

}
