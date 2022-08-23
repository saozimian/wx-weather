package com.codehuan.util;

import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.codehuan.constant.Constants;
import com.codehuan.entity.Weaver;
import com.codehuan.entity.vo.WeaverVo;
import lombok.extern.slf4j.Slf4j;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 接口工具类
 *
 * @author ZhangHuan created on 2022/8/22
 * @email codehuan@163.com
 */
@Slf4j
public class TokenUtil {

    /**
     * 微信开放平台
     */
    public static final String HOST = "https://api.weixin.qq.com";
    public static final String API_TOKEN = "/cgi-bin/token";
    public static final String API_USER_GET = "/cgi-bin/user/get";

    public static final String GRANT_TYPE = "client_credential";

    public static final String SUCCESS = "1";


    /**
     * 获取 token
     *
     * @return
     * @throws Exception
     */
    public static String getAccessToken() throws Exception {

        if (isExist()) {
            return (String) CacheUtil.CACHE_MAP.get(Constants.TOKEN);
        }

        Map<String, String> map = new HashMap<>();
        map.put("grant_type", GRANT_TYPE);
        map.put("appid", Constants.APP_ID);
        map.put("secret", Constants.API_SECRET);
        String body = HttpRequest
                .get(HOST + API_TOKEN)
                .formStr(map)
                .execute()
                .body();
        JSONObject result = JSON.parseObject(body);
        String access_token = (String) result.get("access_token");
        saveToken(access_token);
        return access_token;
    }

    /**
     * 获取用户openid
     *
     * @return
     * @throws Exception
     */
    public static JSONArray getFlower() throws Exception {

        String accessToken = getAccessToken();
        Map<String, String> map = new HashMap<>();
        map.put("access_token", accessToken);
        String body = HttpRequest
                .get(HOST + API_USER_GET)
                .formStr(map)
                .execute()
                .body();
        JSONObject jsonObject = JSON.parseObject(body);
        JSONArray jsonArray = jsonObject.getJSONObject("data").getJSONArray("openid");
        return jsonArray;
    }

    /**
     * 缓存中是否存在token
     *
     * @return boolean
     */
    private static boolean isExist() {
        if (CacheUtil.checkCacheName(Constants.TOKEN)) {
            return true;
        }
        return false;
    }

    /**
     * 将token存入缓存，有效期为两小时
     *
     * @param token String
     */
    private static void saveToken(String token) {
        CacheUtil.put(Constants.TOKEN, token);
    }

    /**
     * 获取天气信息
     *
     * @return
     * @throws Exception
     */
    public static Weaver getWeaver() throws Exception {
        Map<String, String> map = new HashMap<>();
        map.put("key", Constants.KEY);
        map.put("city", Constants.CITY);
        map.put("extensions", Constants.EXTENSIONS);
        map.put("output", Constants.OUTPUT);

        String body = HttpRequest
                .get(Constants.URL)
                .formStr(map)
                .execute()
                .body();
        WeaverVo weaverVo = JSON.parseObject(body, WeaverVo.class);

        if (!SUCCESS.equals(weaverVo.getStatus())) {
            log.error("请求异常,code={},msg={}", weaverVo.getStatus(), weaverVo.getInfo());
        }

        Weaver weaver = weaverVo.getLives().get(0);
        return weaver;
    }


    /**
     * 计算生日
     *
     * @param birth 生日
     * @return
     */
    public static int getNextBirthday(String birth) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        Date today;
        Date birthday;
        try {
            Calendar calendar = Calendar.getInstance();

            today = calendar.getTime();

            String year = String.valueOf(calendar.get(Calendar.YEAR));

            birthday = dateFormat.parse(year + "-" + birth);

        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        Calendar cToday = Calendar.getInstance();
        Calendar cBirthday = Calendar.getInstance();
        cToday.setTime(today);
        cBirthday.setTime(birthday);

        int days = 0;
        // 已经过了
        if (cBirthday.get(Calendar.DAY_OF_YEAR) < cToday.get(Calendar.DAY_OF_YEAR)) {
            days = cToday.getActualMaximum(Calendar.DAY_OF_YEAR) - cToday.get(Calendar.DAY_OF_YEAR);
            cBirthday.set(Calendar.YEAR, cToday.get(Calendar.YEAR) + 1);
            days += cBirthday.get(Calendar.DAY_OF_YEAR);
        } else {
            days = cBirthday.get(Calendar.DAY_OF_YEAR) - cToday.get(Calendar.DAY_OF_YEAR);
        }
        return days;
    }

    /**
     * 获取两个日期直接的天数
     *
     * @param startDate
     * @return
     */
    public static Long getCountDays(String startDate) {
        Date start;
        Date today = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            start = dateFormat.parse(startDate);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        Long num = (today.getTime() - start.getTime()) / (1000 * 60 * 60 * 24);
        return num;
    }

    public static String gettext() throws UnsupportedEncodingException {
        String url = "https://api.shadiao.pro/chp";
        String body = HttpRequest
                .get(url)
                .execute()
                .body();
        JSONObject jsonObject = JSON.parseObject(body);
        String text = URLDecoder.decode(jsonObject.getJSONObject("data").get("text").toString().replace("%", "%25"), "utf8");
        return text;
    }

    public static void main(String[] args) throws Exception {
        System.out.println(getWeaver());
    }
}
