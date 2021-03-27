package com.k.community.service;

import com.k.community.dao.DiscussPostMapper;
import com.k.community.entity.DiscussPost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName: DiscussPostService
 * @Description: DiscussPostService
 * @Author 77166
 * @Date 2021/3/26
 */
@Service
public class DiscussPostService {
    private final DiscussPostMapper discussPostMapper;
    @Autowired
    public DiscussPostService(DiscussPostMapper discussPostMapper) {
        this.discussPostMapper = discussPostMapper;
    }

    public List<DiscussPost> findDiscussPosts(int userid,int offset,int limit){
        return discussPostMapper.selectDiscussPosts(userid, offset, limit);
    }

    public int findDiscussPostRows(int userId){
        return discussPostMapper.selectDiscussPostRows(userId);
    }


}
