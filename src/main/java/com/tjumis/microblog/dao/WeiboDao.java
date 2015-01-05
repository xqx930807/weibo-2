package com.tjumis.microblog.dao;

import com.tjumis.microblog.model.Weibo;
import com.tjumis.microblog.utils.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by yong.h on 15/1/3.
 */
@Repository
@Transactional
public class WeiboDao {
    @Autowired
    private SessionFactory sessionFactory;

    public void addWeibo(Weibo weibo) {
        sessionFactory.getCurrentSession().save(weibo);
    }

    public void deleteWeibo(Weibo weibo) {
        sessionFactory.getCurrentSession().update(weibo);
    }

    @SuppressWarnings("unchecked")
    public List<Weibo> getUserWeibo(String uid) {
        String sql = "select w.id, w.uid, w.content, w.image, w.created_at, w.deleted_at, u.username, u.avatar"
                + " from wb_users u, wb_weibo w "
                + "where w.uid = " + uid
                + " and u.id = w.uid";
        return sessionFactory.getCurrentSession().createSQLQuery(sql).addEntity(Weibo.class).list();
    }

    public List<Weibo> getUserTimeline(String[] ids) {
        return getUserTimeline(StringUtils.sqlJoin(ids, ","));
    }

    public List<Weibo> getUserTimeline(List<Object> ids) {
        return getUserTimeline(StringUtils.sqlJoin(ids, ","));
    }

    @SuppressWarnings("unchecked")
    public List<Weibo> getUserTimeline(String ids) {
        String sql = "select w.id, w.uid, w.content, w.image, w.created_at, w.deleted_at, u.username, u.avatar"
                + " from wb_users u, wb_weibo w "
                + "where w.uid in ("
                + ids
                + ") and u.id = w.uid";
        return sessionFactory.getCurrentSession().createSQLQuery(sql).addEntity(Weibo.class).list();
    }
}
