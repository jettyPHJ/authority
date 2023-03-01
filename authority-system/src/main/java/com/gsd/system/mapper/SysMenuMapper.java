package com.gsd.system.mapper;

import com.gsd.common.core.domain.entity.SysMenu;

import java.util.List;

public interface SysMenuMapper {
    public List<String> selectMenuPermsByUserId(Long userId);

    public List<SysMenu> selectMenuTreeAll();
    public List<SysMenu> selectMenuTreeByUserId(Long userId);
}
