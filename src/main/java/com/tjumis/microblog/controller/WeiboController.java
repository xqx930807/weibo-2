package com.tjumis.microblog.controller;

import com.tjumis.microblog.dao.RelationDao;
import com.tjumis.microblog.dao.WeiboDao;
import com.tjumis.microblog.model.IWeibo;
import com.tjumis.microblog.model.ResultResponse;
import com.tjumis.microblog.model.VWeibo;
import com.tjumis.microblog.model.Weibo;
import com.tjumis.microblog.utils.SecurityUtils;
import com.tjumis.microblog.utils.TimeUtils;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
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
    public Object postNewWeibo(
            @PathVariable(value = "uid")String uid,
            @RequestParam(value = "content")String content,
            @RequestParam(value = "image")MultipartFile file,
            HttpServletRequest request) throws IOException{
        IWeibo weibo = new IWeibo();
        weibo.setUid(Long.valueOf(uid));
        weibo.setCreatedAt(TimeUtils.format());
        weibo.setContent(content);
        if (file != null) {
            if ( ! file.isEmpty()) {
                String[] arr = file.getOriginalFilename().split("\\.");
                String filename = SecurityUtils.SHA(arr[0] + System.currentTimeMillis()) + "." + arr[1];
                String path = request.getSession().getServletContext().getRealPath("/WEB-INF/upload");
                FileUtils.copyInputStreamToFile(file.getInputStream(), new File(path, filename));
                weibo.setImage("/upload/" + filename);
            }
        }
        mWeiboDao.addWeibo(weibo);
        return new ResponseEntity<Object>(
                new ResultResponse(ResultResponse.STATUS_OK, "发布成功"),
                HttpStatus.OK);
    }
}
