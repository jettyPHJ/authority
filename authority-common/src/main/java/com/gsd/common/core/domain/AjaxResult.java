package com.gsd.common.core.domain;

import com.gsd.common.constant.HttpStatus;
import com.gsd.common.utils.StringUtils;

import java.util.HashMap;

public class AjaxResult extends HashMap<String,Object> {
    public  static final String CODE_TAG = "code";
    public  static final String MSG_TAG = "msg";
    public  static final String DATA_TAG = "data";

    public AjaxResult(int code, String msg, Object data)
    {
        super.put(CODE_TAG, code);
        super.put(MSG_TAG, msg);
        if (StringUtils.isNotNull(data))
        {
            super.put(DATA_TAG, data);
        }
    }
    public  static  AjaxResult success(){
        return  AjaxResult.success("操作成功");
    }
    public static AjaxResult success(String msg)
    {
        return AjaxResult.success(msg, null);
    }
    public static AjaxResult success(Object data)
    {
        return AjaxResult.success("操作成功", data);
    }
    public static AjaxResult success(int code,String msg){
        return new AjaxResult(code,msg,null);
    }
    public static AjaxResult success(String msg, Object data)
    {
        return new AjaxResult(HttpStatus.SUCCESS, msg, data);
    }


    /**
     * 返回错误消息
     *
     * @return
     */
    public static AjaxResult error()
    {
        return AjaxResult.error("操作失败");
    }

    /**
     * 返回错误消息
     *
     * @param msg 返回内容
     * @return 警告消息
     */
    public static AjaxResult error(String msg)
    {
        return AjaxResult.error(msg, null);
    }

    /**
     * 返回错误消息
     *
     * @param msg 返回内容
     * @param data 数据对象
     * @return 警告消息
     */
    public static AjaxResult error(String msg, Object data)
    {
        return new AjaxResult(HttpStatus.ERROR, msg, data);
    }

    /**
     * 返回错误消息
     *
     * @param code 状态码
     * @param msg 返回内容
     * @return 警告消息
     */
    public static AjaxResult error(int code, String msg)
    {
        return new AjaxResult(code, msg, null);
    }



}
