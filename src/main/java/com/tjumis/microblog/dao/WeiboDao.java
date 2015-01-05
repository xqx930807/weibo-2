package com.tjumis.microblog.dao;

import com.tjumis.microblog.model.IWeibo;
import com.tjumis.microblog.model.Weibo;
import com.tjumis.microblog.utils.StringUtils;
import com.tjumis.microblog.utils.TimeUtils;
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

    public void addWeibo(IWeibo weibo) {
        sessionFactory.getCurrentSession().save(weibo);
    }

    public void deleteWeibo(String id) {
        IWeibo weibo = findWeiboById(id);
        weibo.setDeletedAt(TimeUtils.format());
        sessionFactory.getCurrentSession().update(weibo);
    }

    @SuppressWarnings("unchecked")
    public List<Weibo> getUserWeibo(String uid) {
        String sql = "select w.id, w.uid, w.content, w.image, w.created_at, w.deleted_at, u.nickname, u.avatar"
                + " from wb_users u, wb_weibo w "
                + "where w.uid = " + uid
                + " and u.id = w.uid"
                + " and w.deleted_at is NULL";
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
        String sql = "select w.id, w.uid, w.content, w.image, w.created_at, w.deleted_at, u.nickname, u.avatar"
                + " from wb_users u, wb_weibo w "
                + "where w.uid in ("
                + ids
                + ") and u.id = w.uid"
                + " and w.deleted_at is NULL";
        return sessionFactory.getCurrentSession().createSQLQuery(sql).addEntity(Weibo.class).list();
    }

    public IWeibo findWeiboById(String id) {
        Criteria c = sessionFactory.getCurrentSession().createCriteria(IWeibo.class);
        c.add(Restrictions.eq("id", Long.valueOf(id)));
        return (IWeibo) c.list().get(0);
    }

    @SuppressWarnings("unchecked")
    public List<Weibo> searchWeibo(String query) {
        String sql = "select w.id, w.uid, w.content, w.image, w.created_at, w.deleted_at, u.nickname, u.avatar"
                + " from wb_users u, wb_weibo w "
                + "where w.content like '%" + query + "%'"
                + " and u.id = w.uid";
        return sessionFactory.getCurrentSession().createSQLQuery(sql).addEntity(Weibo.class).list();
    }
}
