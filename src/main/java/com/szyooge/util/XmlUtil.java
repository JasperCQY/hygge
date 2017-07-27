package com.szyooge.util;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlUtil {
    private static Logger logger = LoggerFactory.getLogger(XmlUtil.class);
    
    public static String mapToXmlStr(Map<String, String> map, InputStream in) {
        SAXReader reader = new SAXReader();
        Document doc;
        try {
            doc = reader.read(in);
            // 获取根节点
            Element root = doc.getRootElement();
            mapToElement(root, map);
            return doc.asXML();
        } catch (DocumentException e) {
            logger.error("解析xml报错", e);
        }
        return null;
    }
    
    private static void mapToElement(Element els, Map<String, String> map) {
        @SuppressWarnings("rawtypes")
        List items = els.elements();
        if (items == null || items.size() == 0) {
            els.setText("");
            els.addCDATA(map.get(els.getName()));
            return;
        }
        Element el = null;
        for (Object o : items) {
            el = (Element) o;
            mapToElement(el, map);
        }
    }
    
    /**
     * xml字符串转换成Map
     * @author quanyou.chen
     * @date: 2017年7月27日 下午5:14:01
     * @param xmlStr
     * @return
     */
    public static Map<String, String> xmlStrToMap(String xmlStr) {
        Map<String, String> resultMap = new HashMap<String, String>();
        // 解析
        try {
            Document doc = DocumentHelper.parseText(xmlStr); // 将字符串转为XML
            // 获取根节点
            Element root = doc.getRootElement();
            elementToMap(resultMap, root);
        } catch (DocumentException e) {
            logger.error("解析xml报错", e);
        }
        return resultMap;
    }
    
    private static void elementToMap(Map<String, String> map, Element els) {
        @SuppressWarnings("rawtypes")
        List items = els.elements();
        if (items == null || items.size() == 0) {
            map.put(els.getName(), els.getTextTrim());
            return;
        }
        Element el = null;
        for (Object o : items) {
            el = (Element) o;
            elementToMap(map, el);
        }
    }
}
