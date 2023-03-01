package com.gsd.controller.common;

import com.google.code.kaptcha.Producer;
import com.gsd.common.constant.Constants;
import com.gsd.common.core.domain.AjaxResult;
import com.gsd.common.core.redis.RedisCache;
import com.gsd.common.utils.uuid.IdUtils;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FastByteArrayOutputStream;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

@RestController
public class CaptchaController {
    @Resource(name = "captchaProducerMath")
    private Producer captchaProducerMath;
    @Autowired
    private RedisCache redisCache;

    @GetMapping("/captchaImage")
    public AjaxResult getCode()  {
        //System.out.println("111111111111111111");
        AjaxResult ajax = AjaxResult.success();
        boolean captchaOnOff = true;
        if(!captchaOnOff){
            return ajax;
        }
        String uuid = IdUtils.simpleUUID();
        String verifyKey = Constants.CAPTCHA_CODE_KEY+uuid;
        String captchaType ="math";
        BufferedImage image = null;
        String capStr = null, code = null;
        if ("math".equals(captchaType)){
            String capText = captchaProducerMath.createText();
            capStr = capText.substring(0, capText.lastIndexOf("@"));
            code = capText.substring(capText.lastIndexOf("@") + 1);
            image = captchaProducerMath.createImage(capStr);
        }
        redisCache.setCacheObject(verifyKey,code,Constants.CAPTCHA_EXPIRATION, TimeUnit.MINUTES);//  \xac\xed\x00\x05t\x002
        FastByteArrayOutputStream os = new FastByteArrayOutputStream();
        try {
            ImageIO.write(image,"jpg",os);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ajax.put("uuid",uuid);
        ajax.put("img", Base64.encode(os.toByteArray()));
        return  ajax;
    }
}
