package com.tjumis.microblog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by yong.h on 15/1/3.
 */
@Controller
public class RootController {
    @RequestMapping(value = "/api", method = RequestMethod.GET)
    @ResponseBody
    public String root() {
        return "index";
    }
}
