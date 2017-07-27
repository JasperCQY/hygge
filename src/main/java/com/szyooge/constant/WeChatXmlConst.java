package com.szyooge.constant;

/**
 * 微信消息xml节点常量
 */
public interface WeChatXmlConst {
    // 微信消息必含的4个属性
    String ToUserName = "ToUserName";
    String FromUserName = "FromUserName";
    String CreateTime = "CreateTime";
    String MsgType = "MsgType";
    
    // ==> 事件
    String Event = "Event";
    String EventKey = "EventKey";
    String MenuId = "MenuId";
    // 取消添加订阅特有
    String Ticket = "Ticket";
    
    // LOCATION事件特有
    String Latitude = "Latitude";
    String Longitude = "Longitude";
    String Precision = "Precision";
    // <== 事件
    
    // ==> 消息
    String MsgId = "MsgId";
    
    // ==> 文本消息
    String Content = "Content";
    // <== 文本消息
    
    // ==> 多媒体消息
    String MediaId = "MediaId";
    
    // ==> 图片消息
    String PicUrl = "PicUrl";
    // <== 图片消息
    
    // ==> 语音消息
    String Format = "Format";
    // <== 语音消息
    
    // ==> 视频消息
    String ThumbMediaId = "ThumbMediaId";
    // <== 视频消息
    
    // ==> 地理位置消息
    String Location_X = "Location_X";
    String Location_Y = "Location_Y";
    String Scale = "Scale";
    String Label = "Label";
    // <== 地理位置消息
    
    // ==> 连接消息
    String Title = "Title";
    String Description = "Description";
    String Url = "Url";
    // <== 连接消息
    // <== 多媒体消息
    // <== 消息
    
    /**
     * 微信消息类型
     * @ClassName: MsgType
     * @author quanyou.chen
     * @date: 2017年7月27日 下午4:14:39
     * @version  v 1.0
     */
    public interface IMsgType {
        /**
         * 事件类消息（含菜单点击）
         */
        String event = "event";
        /**
         * 文本类消息
         */
        String text = "text";
        /**
         * 图片类消息
         */
        String image = "image";
        /**
         * 音频消息
         */
        String voice = "voice";
        /**
         * 视频消息
         */
        String video = "video";
        /**
         * 地理位置消息
         */
        String location = "location";
        /**
         * 连接消息
         */
        String link = "link";
    }
    
    /**
     * 微信事件类型
     * @ClassName: Event
     * @author quanyou.chen
     * @date: 2017年7月27日 下午4:14:50
     * @version  v 1.0
     */
    public interface IEvent {
        /**
         * 点击自定义菜单
         */
        String CLICK = "CLICK";
        /**
         * 点击菜单跳转连接
         */
        String VIEW = "VIEW";
        /**
         * 扫码推事件
         */
        String scancode_push = "scancode_push";
        /**
         * 扫码推事件且弹出“消息接收中”提示框的事件
         */
        String scancode_waitmsg = "scancode_waitmsg";
        /**
         * 弹出系统拍照发图的事件
         */
        String pic_sysphoto = "pic_sysphoto";
        /**
         * 弹出拍照或者相册发图的事件
         */
        String pic_photo_or_album = "pic_photo_or_album";
        /**
         * 弹出微信相册发图器的事件
         */
        String pic_weixin = "pic_weixin";
        /**
         * 弹出地理位置选择器的事件
         */
        String location_select = "location_select";
        
        /**
         * 订阅
         */
        String subscribe = "subscribe";
        /**
         * 取消订阅
         */
        String unsubscribe = "unsubscribe";
        /**
         * 用户已关注时的事件
         */
        String SCAN = "SCAN";
        
        /**
         * 每5秒上报一次地理位置
         */
        String LOCATION = "LOCATION";
    }
}
