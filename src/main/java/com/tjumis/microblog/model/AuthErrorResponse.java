package com.tjumis.microblog.model;

/**
 * Created by yong.h on 15/1/5.
 */
public class AuthErrorResponse {
    private boolean status = false;
    private String info = "用户验证出错，需要重新登陆";

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
