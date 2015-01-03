package com.tjumis.microblog.dao;

import com.tjumis.microblog.model.User;
import com.tjumis.microblog.model.VWeibo;
import com.tjumis.microblog.model.Weibo;
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

    @SuppressWarnings("unchecked")
    public List<VWeibo> list() {
        List<Weibo> weibos = sessionFactory.getCurrentSession().createCriteria(Weibo.class).list();
        List<VWeibo> result = new ArrayList<VWeibo>();
        for (Weibo weibo : weibos) {
            Criteria c = sessionFactory.getCurrentSession().createCriteria(User.class);
            c.add(Restrictions.eq("id", weibo.getUid()));
            User user = (User) c.uniqueResult();
            VWeibo item = weibo.getVWeibo();
            item.setUsername(user.getUsername());
            item.setAvatar(user.getAvatar());
            result.add(item);
        }
        sessionFactory.close();
        return result;
    }
}
