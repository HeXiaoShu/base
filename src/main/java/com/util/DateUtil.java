package com.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;
import java.util.Date;

/**
 * @author heyonghao
 * @Description:  常用时间工具类
 * @date 2019/7/411:57
 */
public class DateUtil {

    // df.parse() 转 Date类型
    /**
     * 获取当前时间
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String NowDateAll() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return df.format(new Date());
    }

    /**
     * 获取当前时间,无格式存数字
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String NowDateAllNumber() {
        return NowDateAll().replace("-", "").replace(":", "").replace(" ","");
    }

    /**
     * 获取当前时间,无格式存数字
     * @return yyyy-MM-dd
     */
    public static String NowDateNumber() {
        return NowDateYmd().replace("-", "").replace(" ","");
    }

    /**
     * 获取long型日期
      * @return
     */
    public static Long getLongDay(){
        return Long.parseLong(DateUtil.NowDateNumber());
    }

    /**
     * 获取long型月份
     * @return
     */
    public static Long getLongMonth(){
        return Long.parseLong(DateUtil.NowDateNumber().substring(0,DateUtil.NowDateNumber().length() - 2));
    }

    /**
     * 获取long型年份
     * @return
     */
    public static Long getLongYear(){
        String buyMonth = DateUtil.NowDateNumber().substring(0,DateUtil.NowDateNumber().length() - 2);
        return Long.parseLong(buyMonth.substring(0,buyMonth.length() - 2));
    }

    /**
     * 获取当前时间,无格式存数字
     * @return yyyy-MM-dd
     */
    public static String BeforeOrOldDateNumber(Integer count) {
        return BeforeOrOldDate(count).replace("-", "").replace(" ","");
    }


    /**
     * 获取当前时间
     * @return yyyy-MM-dd
     */
    public static String NowDateYmd() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(new Date());
    }

    /**
     * 获取当前时间
     * @return HH:mm:ss
     */
    public static String NowDateHms() {
        SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
        return df.format(new Date());
    }

    /**
     * 获取当前时间
     * @return xx-年-xx-月-xx-日
     */
    public static String NowDateChinese() {
        StringBuilder sb = new StringBuilder(NowDateYmd());
        StringBuilder replace = sb.replace(4, 5, "年").replace(7, 8, "月").append("日");
        return replace.toString();
    }

    /**
     * 获取当前时间,冒号格式
     * @return HH:mm:ss
     */
    public static String NowDateColon() {
        StringBuilder sb = new StringBuilder(NowDateAll());
        StringBuilder replace = sb.replace(13, 14, ":").replace(16, 17, ":");
        return replace.toString();
    }

    /**
     * 获取当前时间的，前,或，后几天
     * @param num 负数为 前.
     * @return yyyy-MM-dd
     */
    public static String BeforeOrOldDate(Integer num){
        Date date=new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, num);
        date = calendar.getTime();
        SimpleDateFormat format= new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }


    /**
     * 获取当前时间的， 毫秒级时间戳
     * @return 时间戳
     */
    public static Long MilliTimestamp(){
        return System.currentTimeMillis();
    }

    /**
     * 获取当前时间的， 秒级时间戳
     * @return 时间戳
     */
    public static Long SecondTimestamp(){
        return System.currentTimeMillis() / 1000;
    }


    /**
     * 获取本月第一天
     * @return String
     */
    public static String firstDayOfMonth(){
        LocalDateTime date = LocalDateTime.now();
        LocalDateTime firstday = date.with(TemporalAdjusters.firstDayOfMonth());
        return firstday.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    /**
     * 获取本月最后一天
     * @return String
     */
    public static String lastDayOfMonth(){
        LocalDateTime date = LocalDateTime.now();
        LocalDateTime lastDay = date.with(TemporalAdjusters.lastDayOfMonth());
        return lastDay.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    /**
     * 获取两个long型 日期 之间间隔天数
     * @param start 新日期
     * @param end   以前日期
     * @return
     */
    public static long longDaysBetween(Long start,Long end){
        LocalDate startDate =LocalDate.parse(String.valueOf(start), DateTimeFormatter.ofPattern("yyyyMMdd"));
        LocalDate endDate = LocalDate.parse(String.valueOf(end), DateTimeFormatter.ofPattern("yyyyMMdd"));
        return ChronoUnit.DAYS.between(endDate, startDate);
    }

    /**
     * 获取两日期之间间隔的天数
     *
     * @param start 较小的时间
     * @param end  较大的时间
     * @return 相差天数
     */
    public static long daysBetween(Date start, Date end) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate startDate =LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(start), formatter);
        LocalDate endDate = LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(end), formatter);
        return ChronoUnit.DAYS.between(endDate, startDate);
    }

    /**
     * 检查当前时间在某个时间段内
     * @param startHour HH:mm
     * @param endHour   HH:mm
     * @return boolean
     */
    public static boolean isBetween(String startHour,String endHour){
        boolean result=false;
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime startLocal=LocalTime.parse(startHour, dateTimeFormatter);
        LocalTime endLocal=LocalTime.parse(endHour,dateTimeFormatter);
        //如果时间段为隔夜时间
        LocalTime localTime=LocalTime.now();
        if (startLocal.isAfter(endLocal)){
            if (localTime.isAfter(startLocal)){
                System.out.println("当前隔夜时间"+localTime+"开始时间："+startHour+"结束时间："+endHour);
                result=true;
            }
            if (localTime.isBefore(endLocal)){
                System.out.println("当前隔夜时间"+localTime+"开始时间："+startHour+"结束时间："+endHour);
                result=true;
            }
        }
        //如果时间段不是隔夜的
        if (startLocal.isBefore(endLocal)){
            if (localTime.isAfter(startLocal)&&localTime.isBefore(endLocal)){
                System.out.println("当前正常时间"+localTime+"开始时间："+startHour+"结束时间："+endHour);
                result=true;
            }
        }
        return result;
    }

}
