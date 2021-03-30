package com.k.community.controller;

import com.k.community.util.CommunityUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @ClassName: TestController
 * @Description: TestController
 * @Author 77166
 * @Date 2021/3/30
 */
@Controller
@RequestMapping("/test")
public class TestController {

    @RequestMapping("/cookie.set")
    @ResponseBody
    public String setCookie(HttpServletResponse response){
        Cookie cookie = new Cookie("code", CommunityUtil.generateUUID());
        cookie.setPath("/community/test");
        cookie.setMaxAge(60*10);
        response.addCookie(cookie);
        return  cookie.getValue();
    }

    @RequestMapping("/cookie.get")
    @ResponseBody
    public String getCookie(@CookieValue("code") String code){
        System.out.println(code);
        return code;
    }

    @RequestMapping("/session.set")
    @ResponseBody
    public String setSession(HttpSession session){
        session.setAttribute("id",1);
        session.setAttribute("name","test");
        return  "setsission";
    }

    @RequestMapping("/session.get")
    @ResponseBody
    public String getSession(HttpSession session){
        Object id =session.getAttribute("id");
        Object name = session.getAttribute("name");
        System.out.println(session.getAttribute("id"));
        System.out.println("id->"+id+",name->"+name);
        return  "getsission";
    }
}
