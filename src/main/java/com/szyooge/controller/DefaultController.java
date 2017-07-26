package com.szyooge.controller;

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
}
