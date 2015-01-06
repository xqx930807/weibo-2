package com.tjumis.microblog.dao;

import com.tjumis.microblog.model.Comment;
import com.tjumis.microblog.model.IComment;
import com.tjumis.microblog.utils.TimeUtils;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by yong.h on 15/1/6.
 */
@Repository
@Transactional
public class CommentDao {
    @Autowired
    private SessionFactory sessionFactory;

    public long addComment(String wid, String uid, String content, String time) {
        IComment comment = new IComment();
        comment.setUid(Long.valueOf(uid));
        comment.setWid(Long.valueOf(wid));
        comment.setContent(content);
        comment.setCreatedAt(time);
        return (Long) sessionFactory.getCurrentSession().save(comment);
    }

    public void deleteComment(String id) {
        IComment comment = findCommentById(id);
        comment.setDeletedAt(TimeUtils.format());
        sessionFactory.getCurrentSession().update(comment);
    }

    public IComment findCommentById(String id) {
        Criteria c = sessionFactory.getCurrentSession().createCriteria(IComment.class);
        c.add(Restrictions.eq("id", Long.valueOf(id)));
        return (IComment) c.list().get(0);
    }

    @SuppressWarnings("unchecked")
    public List<Comment> getCommentList(String wid) {
        String sql = "select co.id, co.uid, co.wid, co.content, co.created_at, co.deleted_at, u.nickname, u.avatar "
                + "from wb_comments co, wb_users u "
                + "where co.wid = " + wid
                + " and u.id = co.uid"
                + " and co.deleted_at is NULL";
        return sessionFactory.getCurrentSession().createSQLQuery(sql).addEntity(Comment.class).list();
    }
}
