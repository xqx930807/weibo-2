package com.tjumis.microblog.dao;

import com.tjumis.microblog.model.User;
import com.tjumis.microblog.model.VWeibo;
import com.tjumis.microblog.model.Weibo;
import com.tjumis.microblog.utils.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yong.h on 15/1/3.
 */
@Repository
@Transactional
public class WeiboDao {
    @Autowired
    private SessionFactory sessionFactory;

    public List<Weibo> getWeiboList(String[] ids) {
        return getWeiboList(StringUtils.sqlJoin(ids, ","));
    }

    public List<Weibo> getWeiboList(List<Object> ids) {
        return getWeiboList(StringUtils.sqlJoin(ids, ","));
    }

    @SuppressWarnings("unchecked")
    public List<Weibo> getWeiboList(String ids) {
        String sql = "select w.id, w.uid, w.content, w.image, w.created_at, w.deleted_at, u.username, u.avatar"
                + " from wb_users u, wb_weibo w "
                + "where w.uid in ("
                + ids
                + ") and u.id = w.uid";
        return sessionFactory.getCurrentSession().createSQLQuery(sql).addEntity(Weibo.class).list();
    }
}
