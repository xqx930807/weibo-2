package com.tjumis.microblog.model;

/**
 * Created by yong.h on 14/12/31.
 * HTTP Response 类
 */
public class ResultResponse {

    public static final int STATUS_OK = 0;
    public static final int STATUS_FAILED = 1;

    private String info;
    private int status;

    public ResultResponse(int _status, String _info) {
        this.status = _status;
        this.info = _info;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

}
