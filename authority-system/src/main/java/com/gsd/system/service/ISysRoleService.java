package com.gsd.system.service;

import java.util.Set;

public interface ISysRoleService {
    public Set<String> selectRolePermissionByUserId(Long userId);
}
