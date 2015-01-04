package com.tjumis.microblog.model;

/**
 * Created by yong.h on 14/12/31.
 * HTTP Response 类
 */
public class ResultResponse {

    public static final boolean STATUS_OK = true;
    public static final boolean STATUS_FAILED = false;

    private String info;
    private boolean status;

    public ResultResponse(boolean _status, String _info) {
        this.status = _status;
        this.info = _info;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

}
