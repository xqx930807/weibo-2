package com.tjumis.microblog.model;

import java.util.List;

/**
 * Created by yong.h on 14/12/31.
 * 将密码字段隐藏掉了
 */
public class VUser {
    private long id;
    private String username;
    private String token;
    private String avatar;
    private String nickname;
    private String location;
    private String signature;
    private String createdAt;

    private List<Weibo> weibos;

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public List<Weibo> getWeibos() {
        return this.weibos;
    }

    public void setWeibos(List<Weibo> weibos) {
        this.weibos = weibos;
    }

    public VUser(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.token = user.getToken();
        this.avatar = user.getAvatar();
        this.nickname = user.getNickname();
        this.location = user.getLocation();
        this.signature = user.getSignature();
        this.createdAt = user.getCreatedAt();
    }
}
