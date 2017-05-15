package com.szyooge.controller;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.szyooge.util.HttpUtil;

@Controller
public class HttpController {
	@RequestMapping(value="/httpGet")
	public void httpGet(HttpServletRequest request, HttpServletResponse response){
		Map<String,String> params = new HashMap<String,String>();
		String name = null;
		for (Enumeration<String> e = request.getParameterNames(); e.hasMoreElements();) {
			name = e.nextElement();
			params.put(name, request.getParameter(name));
		}
		String url = params.remove("url");
		try {
			response.getWriter().write(HttpUtil.sendGet(url, params, "UTF-8"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
