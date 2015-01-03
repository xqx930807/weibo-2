package com.tjumis.microblog.controller;

import com.tjumis.microblog.dao.WeiboDao;
import com.tjumis.microblog.model.VWeibo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by yong.h on 14/12/30.
 */
@Controller
@RequestMapping("/weibo")
public class WeiboController {
    @Autowired
    private WeiboDao mWeiboDao;

    @RequestMapping(value = "", method = RequestMethod.GET)
    @ResponseBody
    public List<VWeibo> list() {
        return mWeiboDao.list();
    }
}
