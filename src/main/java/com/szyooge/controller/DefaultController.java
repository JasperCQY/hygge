package com.szyooge.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DefaultController extends BaseController {
    /**
     * 跳转到首页
     * @author quanyou.chen
     * @date: 2017年7月26日 下午5:18:07
     * @return
     */
    @RequestMapping("/")
    public String index() {
        return "index";
    }
    /**
     * 跳转到菜单
     * @author quanyou.chen
     * @date: 2017年7月26日 下午5:18:07
     * @return
     */
    @RequestMapping("/menu")
    public String menu() {
        return "menu";
    }
    /**
     * 跳转到内容
     * @author quanyou.chen
     * @date: 2017年7月26日 下午5:18:07
     * @return
     */
    @RequestMapping("/content")
    public String content(HttpServletRequest request) {
        String url = request.getParameter("url");
        return "content/"+url;
    }
}
