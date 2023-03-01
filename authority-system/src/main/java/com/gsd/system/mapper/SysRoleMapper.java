package com.gsd.system.mapper;

import com.gsd.common.core.domain.entity.SysRole;

import java.util.List;

public interface SysRoleMapper {
    public List<SysRole> selectRolePermissionByUserId(Long userId);
}
