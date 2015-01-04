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
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserDao mUserDao;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public List<User> list() {
        return mUserDao.listUser();
    }

    /**
     * 登陆 GET /users/login?username={username}&password={password}
     * @param username 用户名
     * @param password 密码
     * @return result response
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public Object login(@RequestParam(value = "username") String username, @RequestParam(value = "password") String password) {
        List<User> users = mUserDao.findUserByUsername(username);
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

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    @ResponseBody
    public String logout() {
        return "logout";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public String register() {
        return "registered";
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
