package com.szyooge.util;

import org.slf4j.MDC;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 诊断日志
 * @ClassName: MDC_LOG
 * @author quanyou.chen
 * @date: 2017年7月27日 下午1:32:33
 * @version  v 1.0
 */
public class MDC_LOG {

    private static SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
    private static final String FIRST_SP = "[";
    private static final String LAST_SP = "]";
    private static final String THREAD_ID = "THREAD_ID";
    /**
     * 添加MDC日志信息
     *
     */
    public static void add(){
        StringBuilder sb = new StringBuilder(FIRST_SP);
        sb.append(sf.format(new Date()));
        sb.append(LAST_SP);
        MDC.put(THREAD_ID,sb.toString());
    }

    /**
     * 添加MDC日志信息
     * @param obj
     */
    public static void add(Object obj){
        StringBuilder sb = new StringBuilder(FIRST_SP);
        sb.append(sf.format(new Date())+Thread.currentThread().hashCode());
        sb.append(obj.hashCode());
        sb.append(LAST_SP);
        MDC.put(THREAD_ID,sb.toString());
    }

    /**
     * 清除MDC日志信息
     *
     */
    public static void remove(){
        MDC.remove(THREAD_ID);
    }
    
    /**
     * 获取MDC ID
     * @author quanyou.chen
     * @date: 2017年6月8日 上午10:07:31
     * @Title: get
     * @return String 返回值
     */
    public static String get(){
        return MDC.get(THREAD_ID);
    }

    /*private void test1(){
        org.jboss.logging.MDC.put(THREAD_ID, FIRST_SP+Thread.currentThread().hashCode()+""+new Date().getTime()+LAST_SP);
        log.info("纯字符串信息的info级别日志test1");
        log.info("纯字符串信息的info级别日志test1");
        org.jboss.logging.MDC.remove(THREAD_ID);
    }

    private void test2(){
        org.jboss.logging.MDC.put(THREAD_ID, FIRST_SP+Thread.currentThread().hashCode()+""+new Date().getTime()+LAST_SP);
        log.info("纯字符串信息的info级别日志test2");
        log.info("纯字符串信息的info级别日志test2");
        test3();
        org.jboss.logging.MDC.remove(THREAD_ID);
    }

    private void test3(){
        log.info("纯字符串信息的info级别日志test3");
        log.info("纯字符串信息的info级别日志test3");
    }

    public static void main(String[] args){
        QueryResultHistorySerialLogController th = new QueryResultHistorySerialLogController();
        th.test1();
        th.test2();
    }*/
}
