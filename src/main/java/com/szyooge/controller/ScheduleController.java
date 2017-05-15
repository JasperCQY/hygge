package com.szyooge.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ScheduleController {
	@RequestMapping(value="/index")
	public String getAccessToken(){
		return "index";
	}
}
