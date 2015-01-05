package com.tjumis.microblog.controller;

import com.tjumis.microblog.dao.RelationDao;
import com.tjumis.microblog.dao.WeiboDao;
import com.tjumis.microblog.model.VWeibo;
import com.tjumis.microblog.model.Weibo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Created by yong.h on 14/12/30.
 */
@Controller
public class WeiboController {
    @Autowired
    private WeiboDao mWeiboDao;
    @Autowired
    private RelationDao mRelationDao;

    @RequestMapping(value = "/users/{uid}/timeline", method = RequestMethod.GET)
    @ResponseBody
    public List<Weibo> getUserTimeline(@PathVariable(value = "uid")String uid) {
        return mWeiboDao.getUserTimeline(mRelationDao.getUserFollowed(uid));
    }

    @RequestMapping(value = "/users/{uid}/weibo", method = RequestMethod.GET)
    @ResponseBody
    public List<Weibo> getUserWeibo(@PathVariable(value = "uid")String uid) {
        return mWeiboDao.getUserWeibo(uid);
    }

    @RequestMapping(value = "/users/{uid}/weibo", method = RequestMethod.POST)
    @ResponseBody
    public void postNewWeibo(
            @PathVariable(value = "uid")String uid,
            @RequestParam(value = "content")String content,
            @RequestParam(value = "image")MultipartFile[] files) {

    }
}
