package com.szyooge.tuling.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.szyooge.constant.CharSet;
import com.szyooge.tuling.Robot;
import com.szyooge.util.HttpUtil;

/**
 * 图灵机器人智能回复
 * @author HugSunshine
 *
 */
@Service
public class RobotImpl implements Robot{
	@Value("${tuling.robot.api.url}")
	private String robotApiUrl;
	
	@Value("${tuling.robot.api.key}")
	private String key;
	
	/**
	 * @param userid 必填。对应的客户ID
     * @param info   必填。发送的消息
     * @param loc    选填。位置消息是需要填此字段
     * @return
     */
	@Override
    public String sendMsg(Map<String,String> params) {
		params.put("key", key);
    	return HttpUtil.sendPost(robotApiUrl, params, CharSet.UTF8);
    }
    /**
     * @param userid 必填。对应的客户ID
     * @param info   必填。发送的消息
     * @param loc    选填。位置消息是需要填此字段
     * @return
     */
	@Override
    public String sendMsg(String... opts) {
    	if(opts.length < 2) {
    		// 出错了
    	}
    	Map<String,String> params = new HashMap<String,String>();
    	params.put("key", key);
    	params.put("userid", opts[0]);
    	params.put("info", opts[1]);
    	if(opts.length >= 3){
    	params.put("loc", opts[2]);
    	}
    	return HttpUtil.sendPost(robotApiUrl, params, CharSet.UTF8);
    }
}
