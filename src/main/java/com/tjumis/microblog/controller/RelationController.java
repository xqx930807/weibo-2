package com.tjumis.microblog.controller;

import com.tjumis.microblog.dao.RelationDao;
import com.tjumis.microblog.dao.UserDao;
import com.tjumis.microblog.model.AuthErrorResponse;
import com.tjumis.microblog.model.ResultResponse;
import com.tjumis.microblog.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Created by yong.h on 15/1/5.
 */
@Controller
public class RelationController {
    @Autowired
    private RelationDao mRelationDao;
    @Autowired
    private UserDao mUserDao;

    /**
     * 获取用户关注用户列表
     * @param uid userid
     * @param token 验证令牌
     * @return 关注用户列表
     */
    @RequestMapping(value = "/users/{uid}/followed", method = RequestMethod.GET)
    @ResponseBody
    public Object getUserFollowedRelations(
            @PathVariable(value = "uid")String uid,
            @RequestParam(value = "token")String token) {
        User user = mUserDao.findUserByUid(uid);
        if (user == null || ! user.checkToken(token)) {
            return new ResponseEntity<Object>(
                    new AuthErrorResponse(),
                    HttpStatus.UNAUTHORIZED);
        }
        return mRelationDao.getUserFollowedRelations(uid);
    }

    /**
     * 获取用户粉丝列表
     * @param uid userid
     * @param token 验证令牌
     * @return 用户粉丝列表
     */
    @RequestMapping(value = "/users/{uid}/fans", method = RequestMethod.GET)
    @ResponseBody
    public Object getUserFans(
            @PathVariable(value = "uid")String uid,
            @RequestParam(value = "token")String token) {
        User user = mUserDao.findUserByUid(uid);
        if (user == null || ! user.checkToken(token)) {
            return new ResponseEntity<Object>(
                    new AuthErrorResponse(),
                    HttpStatus.UNAUTHORIZED);
        }
        return mRelationDao.getUserFans(uid);
    }

    /**
     * 关注或者取消关注用户
     * @param uid userid
     * @param fid 目标用户id
     * @param token 验证令牌
     * @return 操作结果
     */
    @RequestMapping(value = "/users/{uid}/follow/{fid}", method = RequestMethod.POST)
    @ResponseBody
    public Object follow(
            @PathVariable(value = "uid")String uid,
            @PathVariable(value = "fid")String fid,
            @RequestParam(value = "token")String token) {
        User user = mUserDao.findUserByUid(uid);
        if (user == null || ! user.checkToken(token)) {
           return new ResponseEntity<Object>(
                    new AuthErrorResponse(),
                    HttpStatus.UNAUTHORIZED);
        }
        mRelationDao.foOrUnfo(uid, fid);
        return new ResponseEntity<Object>(
                new ResultResponse(ResultResponse.STATUS_OK, "操作成功"),
                HttpStatus.OK);
    }
}
