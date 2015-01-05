package com.tjumis.microblog.dao;

import com.tjumis.microblog.model.Relation;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by yong.h on 15/1/5.
 */
@Repository
@Transactional
public class RelationDao {
    @Autowired
    private SessionFactory sessionFactory;

    @SuppressWarnings("unchecked")
    public List<Relation> getUserFans(String uid) {
        Criteria c = sessionFactory.getCurrentSession().createCriteria(Relation.class);
        c.add(Restrictions.eq("fid", uid));
        sessionFactory.close();
        return c.list();
    }

    @SuppressWarnings("unchecked")
    public List<Object> getUserFollowed(String uid) {
        String sql = "select fid from wb_relations where uid = " + uid;
        List<Object> arr = sessionFactory.getCurrentSession().createSQLQuery(sql).list();
        sessionFactory.close();
        return arr;
    }

    @SuppressWarnings("unchecked")
    public List<Relation> getUserFollowedRelations(String uid) {
        String sql = "select r.id, r.uid, r.fid, r.created_at, u.username, u.avatar  "
                    + "from wb_relations r, wb_users u "
                    + "where u.id in ("
                    + "select fid from wb_relations where uid = " + uid
                    + ") and r.uid = " + uid;
        return sessionFactory.getCurrentSession().createSQLQuery(sql).addEntity(Relation.class).list();
    }
}
