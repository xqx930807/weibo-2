package com.tjumis.microblog.model;

/**
 * Created by yong.h on 15/1/3.
 */
public class VWeibo {
    private long id;
    private long uid;
    private String content;
    private String image;
    private String createdAt;
    private String deletedAt;
    private String username;
    private String avatar;

    public VWeibo(Weibo weibo) {
        this.id = weibo.getId();
        this.uid = weibo.getUid();
        this.content = weibo.getContent();
        this.image = weibo.getImage();
        this.createdAt = weibo.getCreatedAt();
        this.deletedAt = weibo.getDeletedAt();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(String deletedAt) {
        this.deletedAt = deletedAt;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
