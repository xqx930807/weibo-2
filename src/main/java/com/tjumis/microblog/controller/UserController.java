package com.tjumis.microblog.controller;

import com.tjumis.microblog.dao.CommentDao;
import com.tjumis.microblog.dao.RelationDao;
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
    @Autowired
    RelationDao mRelationDao;

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

    /**
     * 注销
     * @param uid userid
     * @param token 验证令牌
     * @return 操作结果
     */
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

    /**
     * 用户注册
     * @param email 邮箱
     * @param nickname 昵称
     * @param password 密码
     * @return 操作结果
     */
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
        user.setPassword(SecurityUtils.md5(password));
        user.setCreatedAt(TimeUtils.format());
        mUserDao.addUser(user);
        return new ResponseEntity<Object>(
                new ResultResponse(ResultResponse.STATUS_OK, "注册成功"),
                HttpStatus.OK);
    }

    /**
     * 验证邮箱
     * @param email 邮箱
     * @return 验证结果
     */
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

    /**
     * 获取用户个人资料接口
     * @param uid userid
     * @param token 验证令牌
     * @return 用户个人资料
     */
    @RequestMapping(value = "/users/{uid}/profile", method = RequestMethod.GET)
    @ResponseBody
    public Object getUserProfile(@PathVariable(value = "uid")String uid,
            @RequestParam(value = "token")String token) {
        User user = mUserDao.findUserByUid(uid);
        if (user == null || ! user.checkToken(token)) {
            return new ResponseEntity<Object>(
                    new AuthErrorResponse(),
                    HttpStatus.UNAUTHORIZED);
        }
        VUser vuser = new VUser(user);
        List<Weibo> tmp = mWeiboDao.getUserWeibo(String.valueOf(user.getId()));
        List<VWeibo> result = new ArrayList<VWeibo>();
        for (Weibo weibo : tmp) {
            VWeibo vWeibo = new VWeibo(weibo);
            vWeibo.setComments(mCommentDao.getCommentList(String.valueOf(vWeibo.getId())));
            result.add(vWeibo);
        }
        vuser.setWeibos(result);
        //List<Re>
        return new ResponseEntity<Object>(vuser, HttpStatus.OK);
    }

    /**
     * 更新个人资料接口
     * @param uid userid
     * @param token 验证令牌
     * @param query 更新字段
     * @param content 更新内容
     * @return 更新结果
     */
    @RequestMapping(value = "/users/{uid}/profile", method = RequestMethod.PUT)
    @ResponseBody
    public Object updateProfile(
            @PathVariable(value = "uid")String uid,
            @RequestParam(value = "token")String token,
            @RequestParam(value = "query")String query,
            @RequestParam(value = "content")String content) {
        User user = mUserDao.findUserByUid(uid);
        if (user == null || ! user.checkToken(token)) {
            return new ResponseEntity<Object>(
                    new AuthErrorResponse(),
                    HttpStatus.UNAUTHORIZED);
        }
        mUserDao.updateProfile(user,query, content);
        return new ResponseEntity<Object>(
                new ResultResponse(ResultResponse.STATUS_OK, "更新成功"),
                HttpStatus.OK);
    }

    /**
     * 获取其他用户信息
     * @param uid userid
     * @param tid 目标用户
     * @param token 验证令牌
     * @return 其他用户信息
     */
    @RequestMapping(value = "/users/{uid}/user/{tid}", method = RequestMethod.GET)
    @ResponseBody
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
        List<Weibo> tmp = mWeiboDao.getUserWeibo(tid);
        List<VWeibo> result = new ArrayList<VWeibo>();
        for (Weibo weibo : tmp) {
            VWeibo vWeibo = new VWeibo(weibo);
            vWeibo.setComments(mCommentDao.getCommentList(String.valueOf(vWeibo.getId())));
            result.add(vWeibo);
        }
        vuser.setWeibos(result);
        vuser.setFan(mRelationDao.isFan(uid, tid));
        vuser.setFollowed(mRelationDao.isFollowed(uid, tid));
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
