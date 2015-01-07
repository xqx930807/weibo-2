package com.tjumis.microblog.controller;

import com.tjumis.microblog.dao.CommentDao;
import com.tjumis.microblog.dao.RelationDao;
import com.tjumis.microblog.dao.UserDao;
import com.tjumis.microblog.dao.WeiboDao;
import com.tjumis.microblog.model.*;
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
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
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
    @Autowired
    private UserDao mUserDao;
    @Autowired
    private CommentDao mCommentDao;

    /**
     * 获取用户时间轴微博
     * @param uid userid
     * @param token 验证令牌
     * @return 微博列表
     */
    @RequestMapping(value = "/users/{uid}/timeline", method = RequestMethod.GET)
    @ResponseBody
    public Object getUserTimeline(
            @PathVariable(value = "uid")String uid,
            @RequestParam(value = "token")String token) {
        User user = mUserDao.findUserByUid(uid);
        if (user == null || ! user.checkToken(token)) {
            return new ResponseEntity<Object>(
                    new AuthErrorResponse(),
                    HttpStatus.UNAUTHORIZED);
        }
        List<Weibo> tmp = mWeiboDao.getUserTimeline(mRelationDao.getUserFollowed(uid));
        List<VWeibo> result = new ArrayList<VWeibo>();
        for (Weibo weibo : tmp) {
            VWeibo vWeibo = new VWeibo(weibo);
            vWeibo.setComments(mCommentDao.getCommentList(String.valueOf(vWeibo.getId())));
            result.add(vWeibo);
        }
        return result;
    }

    /**
     * 获取指定用户的微博
     * @param uid userid
     * @param token 验证令牌
     * @return 微博列表
     */
    @RequestMapping(value = "/users/{uid}/weibo", method = RequestMethod.GET)
    @ResponseBody
    public Object getUserWeibo(
            @PathVariable(value = "uid")String uid,
            @RequestParam(value = "token")String token) {
        User user = mUserDao.findUserByUid(uid);
        if (user == null || ! user.checkToken(token)) {
            return new ResponseEntity<Object>(
                    new AuthErrorResponse(),
                    HttpStatus.UNAUTHORIZED);
        }
        List<Weibo> tmp = mWeiboDao.getUserWeibo(uid);
        List<VWeibo> result = new ArrayList<VWeibo>();
        for (Weibo weibo : tmp) {
            VWeibo vWeibo = new VWeibo(weibo);
            vWeibo.setComments(mCommentDao.getCommentList(String.valueOf(vWeibo.getId())));
            result.add(vWeibo);
        }
        return result;
    }

    /**
     * 发微博
     * @param uid userid
     * @param token 验证令牌
     * @param content 微博内容
     * @param file 上传图片
     * @param request 请求实例
     * @return 新微博对应的id
     * @throws IOException
     */
    @RequestMapping(value = "/users/{uid}/weibo", method = RequestMethod.POST)
    @ResponseBody
    public Object postNewWeibo(
            @PathVariable(value = "uid")String uid,
            @RequestParam(value = "token")String token,
            @RequestParam(value = "content")String content,
            @RequestParam(value = "image", required=false)MultipartFile file,
            HttpServletRequest request) throws IOException{
        User user = mUserDao.findUserByUid(uid);
        if (user == null || ! user.checkToken(token)) {
            return new ResponseEntity<Object>(
                    new AuthErrorResponse(),
                    HttpStatus.UNAUTHORIZED);
        }
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
        long wid = mWeiboDao.addWeibo(weibo);
        return new ResponseEntity<Object>(
                new ResultResponse(ResultResponse.STATUS_OK, String.valueOf(wid)),
                HttpStatus.OK);
    }

    /**
     * 删除微博
     * @param uid userid
     * @param id 指定微博id
     * @param token 验证令牌
     * @return 删除结果反馈
     */
    @RequestMapping(value = "/users/{uid}/weibo/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public Object deleteWeibo(
            @PathVariable(value = "uid")String uid,
            @PathVariable(value = "id")String id,
            @RequestParam(value = "token")String token) {
        User user = mUserDao.findUserByUid(uid);
        if (user == null || ! user.checkToken(token)) {
            return new ResponseEntity<Object>(
                    new AuthErrorResponse(),
                    HttpStatus.UNAUTHORIZED);
        }
        boolean result = mWeiboDao.deleteWeibo(id, uid);
        return result ? new ResponseEntity<Object>(
                new ResultResponse(ResultResponse.STATUS_OK, "删除成功"),
                HttpStatus.OK)
                :
                new ResponseEntity<Object>(
                        new ResultResponse(ResultResponse.STATUS_FAILED, "只能删除自己的微博"),
                        HttpStatus.FORBIDDEN);
    }

    /**
     * 微博搜索
     * @param uid userid
     * @param token 验证令牌
     * @param query 搜索
     * @return 微博列表
     */
    @RequestMapping(value = "/users/{uid}/weibo/search", method = RequestMethod.GET)
    @ResponseBody
    public Object search(
            @PathVariable(value = "uid")String uid,
            @RequestParam(value = "token")String token,
            @RequestParam(value = "query")String query) {
        User user = mUserDao.findUserByUid(uid);
        if (user == null || ! user.checkToken(token)) {
            return new ResponseEntity<Object>(
                    new AuthErrorResponse(),
                    HttpStatus.UNAUTHORIZED);
        }
        List<Weibo> weibos;
        List<VWeibo> result = new ArrayList<VWeibo>();
        if (query != null && !query.equals("")) {
            weibos = mWeiboDao.searchWeibo(query);
            for (Weibo weibo : weibos) {
                VWeibo vWeibo = new VWeibo(weibo);
                vWeibo.setComments(mCommentDao.getCommentList(String.valueOf(vWeibo.getId())));
                result.add(vWeibo);
            }
        }
        return new ResponseEntity<Object>(result, HttpStatus.OK);
    }

    /**
     * 评论微博
     * @param uid userid
     * @param wid 指定微博id
     * @param content 评论内容
     * @param token 验证令牌
     * @return 新评论的id与创建时间
     */
    @RequestMapping(value = "/users/{uid}/weibo/{wid}/comment", method = RequestMethod.POST)
    @ResponseBody
    public Object comment(
            @PathVariable(value = "uid")String uid,
            @PathVariable(value = "wid")String wid,
            @RequestParam(value = "content")String content,
            @RequestParam(value = "token")String token) {
        User user = mUserDao.findUserByUid(uid);
        if (user == null || ! user.checkToken(token)) {
            return new ResponseEntity<Object>(
                    new AuthErrorResponse(),
                    HttpStatus.UNAUTHORIZED);
        }
        String time = TimeUtils.format();
        long id = mCommentDao.addComment(wid, uid, content, time);
        return new ResponseEntity<Object>(
                new ResultResponse(ResultResponse.STATUS_OK, id + ">" + time),
                HttpStatus.OK);
    }

    /**
     * 删除评论
     * @param uid userid
     * @param wid 指定微博id
     * @param cid 指定评论id
     * @param token 验证令牌
     * @return 删除结果
     */
    @RequestMapping(value = "/users/{uid}/weibo/{wid}/comment/{cid}", method = RequestMethod.DELETE)
    @ResponseBody
    public Object deleteComment(
            @PathVariable(value = "uid")String uid,
            @PathVariable(value = "wid")String wid,
            @PathVariable(value = "cid")String cid,
            @RequestParam(value = "token")String token) {
        User user = mUserDao.findUserByUid(uid);
        if (user == null || ! user.checkToken(token)) {
            return new ResponseEntity<Object>(
                    new AuthErrorResponse(),
                    HttpStatus.UNAUTHORIZED);
        }
        boolean result = mCommentDao.deleteComment(cid, uid);
        return result ?
                new ResponseEntity<Object>(
                        new ResultResponse(ResultResponse.STATUS_OK, "删除成功"),
                        HttpStatus.OK)
                :
                new ResponseEntity<Object>(
                        new ResultResponse(ResultResponse.STATUS_OK, "只能删除自己发的评论"),
                        HttpStatus.FORBIDDEN);
    }
}
