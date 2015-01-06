package com.tjumis.microblog.controller;

import com.tjumis.microblog.dao.CommentDao;
import com.tjumis.microblog.dao.UserDao;
import com.tjumis.microblog.dao.WeiboDao;
import com.tjumis.microblog.model.*;
import com.tjumis.microblog.utils.SecurityUtils;
import com.tjumis.microblog.utils.TimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yong.h on 14/12/30.
 */
@Controller
public class UserController {

    @Autowired
    UserDao mUserDao;
    @Autowired
    WeiboDao mWeiboDao;
    @Autowired
    CommentDao mCommentDao;

    @RequestMapping(value = "/users/list", method = RequestMethod.GET)
    @ResponseBody
    public List<User> list() {
        return mUserDao.listUser();
    }

    /**
     * 登陆 GET /users/login?email={email}&password={password}
     * @param email 邮箱
     * @param password 密码
     * @return result response
     */
    @RequestMapping(value = "/users/login", method = RequestMethod.POST)
    @ResponseBody
    public Object login(
            @RequestParam(value = "email") String email,
            @RequestParam(value = "password") String password) {
        List<User> users = mUserDao.findUserByEmail(email);
        if (users.size() == 0) {
            return new ResponseEntity<Object>(
                    new ResultResponse(ResultResponse.STATUS_FAILED, "用户名错误"),
                    HttpStatus.FORBIDDEN);
        } else if (users.size() > 1) {
            return new ResponseEntity<Object>(
                    new ResultResponse(ResultResponse.STATUS_FAILED, "账号出错"),
                    HttpStatus.FORBIDDEN);
        }
        User user = users.get(0);
        if (user.checkPassword(password)) {
            user.setToken(generateToken(user.getEmail()));
            mUserDao.updateToken(user);
            VUser vuser = new VUser(user);
            List<Weibo> tmp = mWeiboDao.getUserWeibo(String.valueOf(user.getId()));
            List<VWeibo> result = new ArrayList<VWeibo>();
            for (Weibo weibo : tmp) {
                VWeibo vWeibo = new VWeibo(weibo);
                vWeibo.setComments(mCommentDao.getCommentList(String.valueOf(vWeibo.getId())));
                result.add(vWeibo);
            }
            vuser.setWeibos(result);
            return new ResponseEntity<Object>(vuser, HttpStatus.OK);
        } else {
            return new ResponseEntity<Object>(
                    new ResultResponse(ResultResponse.STATUS_FAILED, "密码错误"),
                    HttpStatus.FORBIDDEN);
        }
    }

    @RequestMapping(value = "/users/{uid}/logout", method = RequestMethod.POST)
    @ResponseBody
    public Object logout(
            @PathVariable(value = "uid")String uid,
            @RequestParam(value = "token")String token) {
        User user = mUserDao.findUserByUid(uid);
        if (user == null || ! user.checkToken(token)) {
            return new ResponseEntity<Object>(
                    new AuthErrorResponse(),
                    HttpStatus.UNAUTHORIZED);
        }
        user.setToken(generateToken(user.getEmail()));
        mUserDao.updateToken(user);
        return new ResponseEntity<Object>(
                new ResultResponse(ResultResponse.STATUS_OK, "注销成功"),
                HttpStatus.OK);
    }

    @RequestMapping(value = "/users/register", method = RequestMethod.POST)
    @ResponseBody
    public Object register(
            @RequestParam(value = "email")String email,
            @RequestParam(value = "nickname")String nickname,
            @RequestParam(value = "password")String password) {
        if (mUserDao.findUserByEmail(email).size() > 0) {
            return new ResponseEntity<Object>(
                    new ResultResponse(ResultResponse.STATUS_FAILED, "该邮箱已被注册"),
                    HttpStatus.FORBIDDEN);
        }
        User user = new User();
        user.setEmail(email);
        user.setNickname(nickname);
        user.setPassword(SecurityUtils.SHA1(password));
        user.setCreatedAt(TimeUtils.format());
        mUserDao.addUser(user);
        return new ResponseEntity<Object>(
                new ResultResponse(ResultResponse.STATUS_OK, "注册成功"),
                HttpStatus.OK);
    }

    @RequestMapping(value = "/users/register/checkEmail", method = RequestMethod.POST)
    @ResponseBody
    public Object checkEmail(@RequestParam(value = "email")String email) {
        int count = mUserDao.findUserByEmail(email).size();
        return count == 0 ?
                new ResponseEntity<Object>(
                        new ResultResponse(ResultResponse.STATUS_OK, "邮箱未被注册"),
                        HttpStatus.OK)
                :
                new ResponseEntity<Object>(
                        new ResultResponse(ResultResponse.STATUS_FAILED, "该邮箱已被注册"),
                        HttpStatus.FORBIDDEN);
    }

    @RequestMapping("/users/{uid}/user/{tid}")
    public Object getUserInfo(
            @PathVariable(value = "uid")String uid,
            @PathVariable(value = "tid")String tid,
            @RequestParam(value = "token")String token) {
        User user = mUserDao.findUserByUid(uid);
        if (user == null || ! user.checkToken(token)) {
            return new ResponseEntity<Object>(
                    new AuthErrorResponse(),
                    HttpStatus.UNAUTHORIZED);
        }
        User want = mUserDao.findUserByUid(tid);
        VUser vuser = new VUser(want);
        vuser.setToken("");
        List<Weibo> tmp = mWeiboDao.getUserWeibo(uid);
        List<VWeibo> result = new ArrayList<VWeibo>();
        for (Weibo weibo : tmp) {
            VWeibo vWeibo = new VWeibo(weibo);
            vWeibo.setComments(mCommentDao.getCommentList(String.valueOf(vWeibo.getId())));
            result.add(vWeibo);
        }
        vuser.setWeibos(result);
        return new ResponseEntity<Object>(vuser, HttpStatus.OK);
    }

    /**
     * 生成token
     * @param email 邮箱
     * @return token
     */
    private String generateToken(String email) {
        return SecurityUtils.SHA(email + System.currentTimeMillis() + Math.random());
    }
}
