package com.codehuan.util;

import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.codehuan.constant.Constants;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * 推送WX服务器
 *
 * @author ZhangHuan created on 2022/8/22
 * @email codehuan@163.com
 */
@Slf4j
public class WXUtil {


    public static String getTemplateId() {
        String accessToken;

        try {
            accessToken = TokenUtil.getAccessToken();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return "";
    }

    /**
     * 发送模板消息
     *
     * @param userId
     * @param templateId
     * @param appid
     */
    public static void sendMsg(String userId, String templateId, String appid, Map<String, Object> param) {
        String accessToken;

        try {
            accessToken = TokenUtil.getAccessToken();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        Map<String, Object> map = new HashMap<>();
        map.put("touser", userId);
        map.put("template_id", templateId);
        map.put("appid", Constants.APP_ID);
        map.put("data", param);
        System.out.println(JSON.toJSONString(map));
        String body = HttpRequest
                .post(Constants.WX_HOST + Constants.API_TEMPLATE_SEND + "?access_token=" + accessToken)
                .body(JSON.toJSONString(map))
                .execute()
                .body();
        JSONObject result = JSON.parseObject(body);
        if (!"0".equals(result.get("errcode"))) {
            log.error("errmsg:{}", result.get("errmsg"));
        }
        log.info("result:{}", "result");
    }

    public static String getColor() {
        //红色
        String red;
        //绿色
        String green;
        //蓝色
        String blue;
        //生成随机对象
        Random random = new Random();
        //生成红色颜色代码
        red = Integer.toHexString(random.nextInt(256)).toUpperCase();
        //生成绿色颜色代码
        green = Integer.toHexString(random.nextInt(256)).toUpperCase();
        //生成蓝色颜色代码
        blue = Integer.toHexString(random.nextInt(256)).toUpperCase();

        //判断红色代码的位数
        red = red.length() == 1 ? "0" + red : red;
        //判断绿色代码的位数
        green = green.length() == 1 ? "0" + green : green;
        //判断蓝色代码的位数
        blue = blue.length() == 1 ? "0" + blue : blue;
        //生成十六进制颜色值
        String color = "#" + red + green + blue;
        return color;
    }
}
