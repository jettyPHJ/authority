package com.gsd.system.mapper;


import com.gsd.common.core.domain.entity.SysUser;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 用户信息Mapper接口
 * 
 * @author ruoyi
 * @date 2022-11-17
 */
@Repository
public interface SysUserMapper 
{
    public List<SysUser> selectSysUserList(SysUser sysUser);
    public SysUser selectUserByUserName(String userName);
    public SysUser checkUserNameUnique(String userName);
    public int insertUser(SysUser user);
}
