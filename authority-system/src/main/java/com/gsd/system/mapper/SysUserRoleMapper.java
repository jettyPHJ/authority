package com.gsd.system.mapper;

import com.gsd.system.domain.SysUserRole;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysUserRoleMapper {
/**
 * 批量新增用户角色信息
 * */
public int batchUserRole(List<SysUserRole> userRoleList);
}
