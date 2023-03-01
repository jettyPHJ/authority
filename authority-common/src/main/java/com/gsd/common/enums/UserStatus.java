package com.gsd.common.enums;

public enum UserStatus{
    OK("0","正常"),DISABLE("1","停用"),DELETED("2","删除");
    private String code;
   private String info;
   UserStatus(String code,String info){
       this.code=code;
       this.info=info;
   }

    public String getCode() {
        return code;
    }

    public String getInfo() {
        return info;
    }
}
