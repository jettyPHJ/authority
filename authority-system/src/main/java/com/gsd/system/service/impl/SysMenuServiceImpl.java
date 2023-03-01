package com.gsd.system.service.impl;

import com.gsd.common.constant.UserConstants;
import com.gsd.common.core.domain.entity.SysMenu;
import com.gsd.common.utils.SecurityUtils;
import com.gsd.common.utils.StringUtils;
import com.gsd.system.domain.vo.MetaVo;
import com.gsd.system.domain.vo.RouterVo;
import com.gsd.system.mapper.SysMenuMapper;
import com.gsd.system.service.ISysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SysMenuServiceImpl implements ISysMenuService {
    @Autowired
    private SysMenuMapper menuMapper;

    public Set<String> selectMenuPermsByUserId(Long userId) {
        List<String> perms = menuMapper.selectMenuPermsByUserId(userId);
        Set<String> permsSet = new HashSet<String>();
        for (String perm : perms) {
            if (StringUtils.isNotEmpty(perm)) {
                permsSet.addAll(Arrays.asList(perm.trim().split(",")));
            }
        }
        return permsSet;
    }

    public List<SysMenu> selectMenuTreeByUserId(Long userId) {
        List<SysMenu> menus = null;
        if (SecurityUtils.isAdmin(userId)) {
            menus = menuMapper.selectMenuTreeAll();
        }
        else
        {
            menus = menuMapper.selectMenuTreeByUserId(userId);
        }
        return menus;
    }

    public List<RouterVo> buildMenus(List<SysMenu> menus) {
        List<RouterVo> routers = new LinkedList<RouterVo>();
        for (SysMenu menu : menus) {
            RouterVo router = new RouterVo();
            router.setName(getRouteName(menu));
            router.setPath(getRoutePath(menu));
            router.setComponent(getComponent(menu));
            router.setRedirect("noRedirect");
            router.setAlwaysShow(true);
            router.setMeta(new MetaVo(menu.getMenuName(), menu.getIcon(), StringUtils.equals("1", String.valueOf(menu.getIsCache())), null));
            List<SysMenu> cMenus = menu.getChildren();
            if (UserConstants.TYPE_DIR.equals(menu.getMenuType())) {
                router.setAlwaysShow(true);
                router.setRedirect("noRedirect");
                router.setChildren(buildMenus(cMenus));
            } else {
                router.setAlwaysShow(false);
                router.setChildren(buildMenus(cMenus));
            }
            routers.add(router);
        }
        return routers;
    }


    public String getRouteName(SysMenu menu) {
        return menu.getPath();
    }

    public String getRoutePath(SysMenu menu) {
        return "/" + menu.getPath();
    }

    public String getComponent(SysMenu menu) {
        String component = UserConstants.LAYOUT;
        if (StringUtils.isNotEmpty(menu.getComponent())) {
            component = menu.getComponent();
        }
        return component;
    }

    /**
     * 构建前端所需要树结构
     */
    public List<SysMenu> buildMenuTree(List<SysMenu> menus) {
        List<SysMenu> returnList = new ArrayList<SysMenu>();
        List<Long> tempList = new ArrayList<Long>();
        for (SysMenu dept : menus) {
            tempList.add(dept.getMenuId());
        }
        for (SysMenu menu : menus) {
            // 如果是顶级节点, 遍历该父节点的所有子节点
            if (!tempList.contains(menu.getParentId())) {
                recursionFn(menus, menu);
                returnList.add(menu);
            }
        }
        if (returnList.isEmpty()) {
            returnList = menus;
        }
        return returnList;
    }

    /**
     * 递归列表
     */
    private void recursionFn(List<SysMenu> list, SysMenu t) {
        // 得到子节点列表
        List<SysMenu> childList = getChildList(list, t);
        if (childList.size() > 0) {
            t.setChildren(childList);
            for (SysMenu tChild : childList) {
                if (hasChild(list, tChild)) {
                    recursionFn(list, tChild);
                }
            }
        }
    }

    /**
     * 得到子节点列表
     */
    private List<SysMenu> getChildList(List<SysMenu> list, SysMenu t) {
        List<SysMenu> tlist = new ArrayList<SysMenu>();
        for (SysMenu n : list) {
            if (n.getParentId().longValue() == t.getMenuId().longValue()) {
                tlist.add(n);
            }
        }
        return tlist;
    }

    /**
     * 判断是否有子节点
     */
    private boolean hasChild(List<SysMenu> list, SysMenu t) {
        return getChildList(list, t).size() > 0;
    }

}
