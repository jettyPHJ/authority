package com.gsd.system.service;

import com.gsd.common.core.domain.entity.SysMenu;
import com.gsd.system.domain.vo.RouterVo;

import java.util.List;
import java.util.Set;

public interface ISysMenuService {
    public Set<String> selectMenuPermsByUserId(Long userId);
    public List<SysMenu> selectMenuTreeByUserId(Long userId);
    public List<RouterVo> buildMenus(List<SysMenu> menus);
    public List<SysMenu> buildMenuTree(List<SysMenu> menus);
}
