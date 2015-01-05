package com.tjumis.microblog.controller;

import com.tjumis.microblog.dao.RelationDao;
import com.tjumis.microblog.model.Relation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by yong.h on 15/1/5.
 */
@Controller
public class RelationController {
    @Autowired
    private RelationDao mRelationDao;

    @RequestMapping(value = "/users/{uid}/relations", method = RequestMethod.GET)
    @ResponseBody
    public List<Relation> getUserFollowedRelations(@PathVariable(value = "uid")String uid) {
        return mRelationDao.getUserFollowedRelations(uid);
    }
}
