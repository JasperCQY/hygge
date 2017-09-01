package com.szyooge.wechat.media.service;

/**
 * 图文素材
 * @ClassName: WeChatMediaService
 * @author quanyou.chen
 * @date: 2017年9月1日 上午11:02:42
 * @version  v 1.0
 */
public interface WeChatMediaService {
    /**
     * 上传素材的url
     * @author quanyou.chen
     * @date: 2017年9月1日 上午11:04:37
     * @param args
     * @return
     */
    String pushUrl(String... args);
    /**
     * 上传素材
     * @author quanyou.chen
     * @date: 2017年9月1日 上午11:05:35
     * @param content
     * @return
     */
    String push(String content);
    /**
     * 获取素材的url
     * @author quanyou.chen
     * @date: 2017年9月1日 上午11:04:37
     * @param args
     * @return
     */
    String pullUrl(String... args);
    /**
     * 获取素材
     * @author quanyou.chen
     * @date: 2017年9月1日 上午11:05:43
     * @param id
     * @return
     */
    String pull(String id);
}
