package com.codehuan.controller;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson2.JSONArray;
import com.codehuan.constant.Constants;
import com.codehuan.entity.Weaver;
import com.codehuan.util.TokenUtil;
import com.codehuan.util.WXUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ZhangHuan created on 2022/8/22
 * @email codehuan@163.com
 */

@Slf4j
public class WeaverController {

    /**
     * 测试
     *
     * @param args
     */
    public static void main(String[] args) {
        //    获取天气信息
        Weaver weaver = null;
        // 用户
        JSONArray users = null;
        try {
            weaver = TokenUtil.getWeaver();
        } catch (Exception e) {
            log.error("errorInfo:{}", e.getMessage());
        }
        //    获取人员信息
        try {
            users = TokenUtil.getFlower();
        } catch (Exception e) {
            log.error("errorInfo:{}", e.getMessage());
        }

        //    计算人员生日，
        int birthday = TokenUtil.getNextBirthday("02-12");

        //    相识多久
        Long days = TokenUtil.getCountDays("2022-03-1");

        // 彩虹屁
        String text;
        try {
            text = TokenUtil.gettext();
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

        // 参数封装
        JSONObject param = new JSONObject();
        param.put("value", weaver.getWeather());
        param.put("color", WXUtil.getColor());

        JSONObject param2 = new JSONObject();
        param2.put("value", weaver.getTemperature() + " ℃");
        param2.put("color", WXUtil.getColor());

        JSONObject param3 = new JSONObject();
        param3.put("value", weaver.getProvince() + weaver.getCity());
        param3.put("color", WXUtil.getColor());

        JSONObject param4 = new JSONObject();
        param4.put("value", weaver.getWinddirection());
        param4.put("color", WXUtil.getColor());

        JSONObject param5 = new JSONObject();
        param5.put("value", days);
        param5.put("color", WXUtil.getColor());

        JSONObject param6 = new JSONObject();
        param6.put("value", birthday);
        param6.put("color", WXUtil.getColor());

        JSONObject param7 = new JSONObject();
        param7.put("value", weaver.getHumidity() + "%");
        param7.put("color", WXUtil.getColor());

        JSONObject param8 = new JSONObject();
        param8.put("value", text);
        param8.put("color", WXUtil.getColor());

        JSONObject param9 = new JSONObject();
        param9.put("value", weaver.getWindpower());
        param9.put("color", WXUtil.getColor());

        Map<String, Object> data = new HashMap<>();
        data.put("weather", param);
        data.put("temperature", param2);
        data.put("cityname", param3);
        data.put("winddirection", param4);
        data.put("love_days", param5);
        data.put("birthday_left", param6);
        data.put("humidity", param7);
        data.put("words", param8);
        data.put("windpower", param9);
        // 发送信息

        WXUtil.sendMsg(users.get(0).toString(), Constants.TEMPLATE, Constants.APP_ID, data);
    }
}
