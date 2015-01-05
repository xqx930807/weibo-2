package com.tjumis.microblog.controller;

import com.tjumis.microblog.dao.UserDao;
import com.tjumis.microblog.model.ResultResponse;
import com.tjumis.microblog.model.User;
import com.tjumis.microblog.model.VUser;
import com.tjumis.microblog.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by yong.h on 14/12/30.
 */
@Controller
public class UserController {

    @Autowired
    UserDao mUserDao;

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
    public Object login(@RequestParam(value = "email") String email, @RequestParam(value = "password") String password) {
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
            user.setToken(generateToken(user.getUsername()));
            mUserDao.updateToken(user);
            return new ResponseEntity<Object>(new VUser(user), HttpStatus.OK);
        } else {
            return new ResponseEntity<Object>(
                    new ResultResponse(ResultResponse.STATUS_FAILED, "密码错误"),
                    HttpStatus.FORBIDDEN);
        }
    }

    @RequestMapping(value = "/users/logout", method = RequestMethod.POST)
    @ResponseBody
    public String logout(@RequestParam(value = "username")String username) {
        mUserDao.updateToken(username, generateToken(username));
        return "logout";
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

    /**
     * 生成token
     * @param username 用户名
     * @return token
     */
    private String generateToken(String username) {
        return SecurityUtils.SHA(username + System.currentTimeMillis() + Math.random());
    }
}
