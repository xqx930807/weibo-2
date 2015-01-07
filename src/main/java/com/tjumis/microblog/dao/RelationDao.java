package com.tjumis.microblog.dao;

import com.tjumis.microblog.model.IRelation;
import com.tjumis.microblog.model.Relation;
import com.tjumis.microblog.utils.TimeUtils;
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
        String sql = "select r.id, r.uid, r.fid, r.created_at, u.nickname, u.avatar  "
                + "from wb_relations r, wb_users u "
                + "where u.id in ("
                + "select uid from wb_relations where fid = " + uid
                + ") and r.fid = " + uid;
        return sessionFactory.getCurrentSession().createSQLQuery(sql).addEntity(Relation.class).list();
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
        String sql = "select r.id, r.uid, r.fid, r.created_at, u.nickname, u.avatar  "
                    + "from wb_relations r, wb_users u "
                    + "where u.id in ("
                    + "select fid from wb_relations where uid = " + uid
                    + ") and r.uid = " + uid;
        return sessionFactory.getCurrentSession().createSQLQuery(sql).addEntity(Relation.class).list();
    }

    public boolean isFan(String uid, String fid) {
        Criteria c = sessionFactory.getCurrentSession().createCriteria(IRelation.class);
        c.add(Restrictions.eq("fid", Long.valueOf(uid)));
        c.add(Restrictions.eq("uid", Long.valueOf(fid)));
        return c.list().size() != 0;
    }

    public boolean isFollowed(String uid, String fid) {
        Criteria c = sessionFactory.getCurrentSession().createCriteria(IRelation.class);
        c.add(Restrictions.eq("uid", Long.valueOf(uid)));
        c.add(Restrictions.eq("fid", Long.valueOf(fid)));
        return c.list().size() != 0;
    }

    public void foOrUnfo(String uid, String fid) {
        Criteria c = sessionFactory.getCurrentSession().createCriteria(IRelation.class);
        c.add(Restrictions.eq("uid", Long.valueOf(uid)));
        c.add(Restrictions.eq("fid", Long.valueOf(fid)));
        List arr = c.list();
        if (arr.size() == 0) {
            IRelation relation = new IRelation();
            relation.setUid(Long.valueOf(uid));
            relation.setFid(Long.valueOf(fid));
            relation.setCreatedAt(TimeUtils.format());
            sessionFactory.getCurrentSession().save(relation);
        } else {
            IRelation relation = (IRelation) arr.get(0);
            sessionFactory.getCurrentSession().delete(relation);
        }
    }
}
