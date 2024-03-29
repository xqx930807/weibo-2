package com.tjumis.microblog.dao;

import com.tjumis.microblog.model.User;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by yong.h on 14/12/31.
 */
@Repository
@Transactional
public class UserDao {
    @Autowired
    private SessionFactory sessionFactory;

    /**
     * 获取所有用户的列表
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<User> listUser() {
        List<User> users = sessionFactory.getCurrentSession().createCriteria(User.class).list();
        sessionFactory.close();
        return users;
    }

    public void updateToken(String email, String token) {
        User user = findUserByEmail(email).get(0);
        user.setToken(token);
        sessionFactory.getCurrentSession().update(user);
        sessionFactory.close();
    }

    public void updateToken(User user) {
        sessionFactory.getCurrentSession().update(user);
        sessionFactory.close();
    }

    public void updateProfile(User user, String query, String content) {
        if (query.equals("location")) {
            user.setLocation(content);
        } else if (query.equals("signature")) {
            user.setSignature(content);
        } else {
            return;
        }
        sessionFactory.getCurrentSession().update(user);
        sessionFactory.close();
    }

    /**
     * 添加一个用户，即注册
     * @param user
     */
    public void addUser(User user) {
        sessionFactory.getCurrentSession().save(user);
        sessionFactory.close();
    }

    /**
     * 根据uid获取用户实例列表
     * @param uid 用户uid
     * @return 用户实例
     */
    public User findUserByUid(String uid) {
        Criteria c = sessionFactory.getCurrentSession().createCriteria(User.class);
        c.add(Restrictions.eq("id", Long.valueOf(uid)));
        sessionFactory.close();
        return (User) c.list().get(0);
    }

    /**
     * 根据用户名获取用户实例列表
     * @param email 用户登陆时使用的登录名
     * @return 用户实例列表
     */
    @SuppressWarnings("unchecked")
    public List<User> findUserByEmail(String email) {
        Criteria c = sessionFactory.getCurrentSession().createCriteria(User.class);
        c.add(Restrictions.eq("email", email));
        sessionFactory.close();
        return  c.list();
    }
}
