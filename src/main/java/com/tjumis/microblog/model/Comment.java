package com.tjumis.microblog.model;

import javax.persistence.*;

/**
 * Created by yong.h on 15/1/6.
 */
@Entity
@Table(name = "WB_COMMENTS")
public class Comment {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(name = "uid")
    private long uid;
    @Column(name = "wid")
    private long wid;
    @Column(name = "content")
    private String content;
    @Column(name = "created_at")
    private String createdAt;
    @Column(name = "deleted_at")
    private String deletedAt;

    private String nickname;
    private String avatar;

    public Comment() {

    }

    public Comment(long id, long uid, long wid, String content, String createdAt, String deletedAt, String nickname, String avatar) {
        this.id = id;
        this.uid = uid;
        this.wid = wid;
        this.content = content;
        this.createdAt = createdAt;
        this.deletedAt = deletedAt;
        this.nickname = nickname;
        this.avatar = avatar;
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

    public long getWid() {
        return wid;
    }

    public void setWid(long wid) {
        this.wid = wid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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
