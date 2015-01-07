package com.tjumis.microblog.dao;

import com.tjumis.microblog.model.IWeibo;
import com.tjumis.microblog.model.VWeibo;
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

    /**
     * 添加微博
     * @param weibo 新的微博实例
     * @return 新微博id
     */
    public long addWeibo(IWeibo weibo) {
        return (Long) sessionFactory.getCurrentSession().save(weibo);
    }

    /**
     * 删除微博
     * @param id 微博id
     * @param uid 用户id
     * @return 删除结果
     */
    public boolean deleteWeibo(String id, String uid) {
        IWeibo weibo = findWeiboById(id);
        if (weibo.getUid() != Long.valueOf(uid)) {
            return false;
        }
        weibo.setDeletedAt(TimeUtils.format());
        sessionFactory.getCurrentSession().update(weibo);
        return true;
    }

    /**
     * 获取指定用户的微博列表
     * @param uid userid
     * @return 微博列表
     */
    @SuppressWarnings("unchecked")
    public List<Weibo> getUserWeibo(String uid) {
        String sql = "select w.id, w.uid, w.content, w.image, w.created_at, w.deleted_at, u.nickname, u.avatar"
                + " from wb_users u, wb_weibo w "
                + "where w.uid = " + uid
                + " and u.id = w.uid"
                + " and w.deleted_at is NULL";
        return sessionFactory.getCurrentSession().createSQLQuery(sql).addEntity(Weibo.class).list();
    }

    /**
     * 获取用户时间轴微博列表
     * @param ids 好友id
     * @return 微博列表
     */
    public List<Weibo> getUserTimeline(String[] ids) {
        return getUserTimeline(StringUtils.sqlJoin(ids, ","));
    }

    /**
     * 获取用户时间轴微博列表
     * @param ids 好友id
     * @return 微博列表
     */
    public List<Weibo> getUserTimeline(List<Object> ids) {
        return getUserTimeline(StringUtils.sqlJoin(ids, ","));
    }

    /**
     * 获取用户时间轴微博列表
     * @param ids 好友id
     * @return 微博列表
     */
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

    /**
     * 根据指定id找到微博
     * @param id 微博id
     * @return 微博实例
     */
    public IWeibo findWeiboById(String id) {
        Criteria c = sessionFactory.getCurrentSession().createCriteria(IWeibo.class);
        c.add(Restrictions.eq("id", Long.valueOf(id)));
        return (IWeibo) c.list().get(0);
    }

    /**
     * 微博搜索
     * @param query 搜索字段
     * @return 微博列表
     */
    @SuppressWarnings("unchecked")
    public List<Weibo> searchWeibo(String query) {
        String sql = "select w.id, w.uid, w.content, w.image, w.created_at, w.deleted_at, u.nickname, u.avatar"
                + " from wb_users u, wb_weibo w "
                + "where w.content like '%" + query + "%'"
                + " and u.id = w.uid";
        return sessionFactory.getCurrentSession().createSQLQuery(sql).addEntity(Weibo.class).list();
    }
}
