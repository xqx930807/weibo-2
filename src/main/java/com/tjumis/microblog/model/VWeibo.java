package com.tjumis.microblog.model;

import java.util.List;

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
    private String nickname;
    private String avatar;
    private List<Comment> comments;

    public VWeibo(Weibo weibo) {
        this.id = weibo.getId();
        this.uid = weibo.getUid();
        this.content = weibo.getContent();
        this.image = weibo.getImage();
        this.createdAt = weibo.getCreatedAt();
        this.deletedAt = weibo.getDeletedAt();
        this.nickname = weibo.getNickname();
        this.avatar = weibo.getAvatar();
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public List<Comment> getComments() {
        return comments;
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

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
