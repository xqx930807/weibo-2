package com.tjumis.microblog.model;

import javax.persistence.*;

/**
 * Created by yong.h on 15/1/5.
 */
@Entity
@Table(name = "WB_WEIBO")
public class IWeibo {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(name = "uid")
    private long uid;
    @Column(name = "content")
    private String content;
    @Column(name = "image")
    private String image;
    @Column(name = "created_at")
    private String createdAt;
    @Column(name = "deleted_at")
    private String deletedAt;

    public IWeibo() {}

    public IWeibo(long id, long uid, String content, String image, String createdAt, String deletedAt) {
        this.id = id;
        this.uid = uid;
        this.content = content;
        this.image = image;
        this.createdAt = createdAt;
        this.deletedAt = deletedAt;
    }

    public String getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(String deletedAt) {
        this.deletedAt = deletedAt;
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

    @Override
    public String toString() {
        return String.format("Weibo: id=%s", this.id);
    }
}

