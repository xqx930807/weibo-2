package com.tjumis.microblog.model;

import javax.persistence.*;

/**
 * Created by yong.h on 15/1/5.
 */
@Entity
@Table(name = "WB_RELATIONS")
public class Relation {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(name = "uid")
    private long uid;
    @Column(name = "fid")
    private long fid;
    @Column(name = "created_at")
    private String createdAt;

    private String username;
    private String avatar;

    public Relation() {

    }

    public Relation(long id, long uid, long fid, String createdAt, String username, String avatar) {
        this.id = id;
        this.uid = uid;
        this.fid = fid;
        this.createdAt = createdAt;
        this.username = username;
        this.avatar = avatar;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
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

    public long getFid() {
        return fid;
    }

    public void setFid(long fid) {
        this.fid = fid;
    }
}
