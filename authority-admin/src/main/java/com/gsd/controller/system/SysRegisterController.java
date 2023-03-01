package com.gsd.controller.system;

import com.gsd.common.core.domain.AjaxResult;
import com.gsd.common.core.domain.model.RegisterBody;
import com.gsd.common.utils.StringUtils;
import com.gsd.framework.web.service.SysRegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class SysRegisterController
{
    @Autowired
    private SysRegisterService registerService;

    @PostMapping("/register")
    public AjaxResult register(@RequestBody RegisterBody user)
    {
        String msg = registerService.register(user);
        return StringUtils.isEmpty(msg) ? AjaxResult.success() : AjaxResult.error(msg);
    }
}
