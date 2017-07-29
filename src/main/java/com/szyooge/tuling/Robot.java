package com.szyooge.tuling;

import java.util.Map;

/**
 * 图灵机器人智能回复。<br/>
 * API地址：http://www.tuling123.com/help/h_cent_webapi.jhtml?nav=doc
 * @author HugSunshine
 *
 */
public interface Robot {
	/**
	 * @param userid 必填。对应的客户ID
     * @param info   必填。发送的消息
     * @param loc    选填。位置消息是需要填此字段
     * @return
     */
    public String sendMsg(Map<String,String> params);
    /**
     * @param userid 必填。对应的客户ID
     * @param info   必填。发送的消息
     * @param loc    选填。位置消息是需要填此字段
     * @return
     */
    public String sendMsg(String... opts);
}
