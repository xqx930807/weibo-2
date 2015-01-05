package com.tjumis.microblog.controller;

import com.tjumis.microblog.dao.RelationDao;
import com.tjumis.microblog.dao.UserDao;
import com.tjumis.microblog.model.AuthErrorResponse;
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
}
