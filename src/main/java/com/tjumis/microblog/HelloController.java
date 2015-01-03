package com.tjumis.microblog;

import com.tjumis.microblog.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    @ResponseBody
    public String test() {
        return "hello";
    }

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    @ResponseBody
    public User model() {
        User user = new User();
        return user;
    }
}