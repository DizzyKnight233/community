package com.k.community.controller;

import com.k.community.entity.DiscussPost;
import com.k.community.entity.Page;
import com.k.community.entity.User;
import com.k.community.service.DiscussPostService;
import com.k.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: HomeController
 * @Description: HomeController
 * @Author 77166
 * @Date 2021/3/26
 */
@Controller
public class HomeController {
    @Autowired
    private DiscussPostService discussPostService;
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String getIndexPage(Model model, Page page) {
        page.setRows(discussPostService.findDiscussPostRows(0));
        page.setPath("/index");

        List<DiscussPost> list = discussPostService.findDiscussPosts(0, page.getOffset(), page.getLimit());
        List<Map<String, Object>> discussPosts = new ArrayList<>();
        if (!list.isEmpty()) {
            for (DiscussPost Post : list) {
                Map<String, Object> map = new HashMap<>();
                map.put("post", Post);
                User user = userService.findUserById(Post.getUserId());
                map.put("user",user);
                discussPosts.add(map);
            }
        }
        model.addAttribute("discussPosts",discussPosts);
        model.addAttribute("page",page);
        return "/index";
    }
}
