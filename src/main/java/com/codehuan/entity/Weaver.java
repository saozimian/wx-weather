package com.codehuan.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 天气
 *
 * @author ZhangHuan created on 2022/8/22
 * @email codehuan@163.com
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Weaver implements Serializable {

    /**
     * 省
     */
    private String province;

    /**
     * 城市名称
     */
    private String city;

    /**
     * 城市编码
     */
    private String adcode;

    /**
     * 天气情况  晴天、雨天
     */
    private String weather;

    /**
     * 温度
     */
    private String temperature;

    /**
     * 风向
     */
    private String winddirection;

    /**
     * 分级
     */
    private String windpower;

    /**
     * 湿度 %
     */
    private String humidity;

    /**
     * 报告时间
     */
    private String reporttime;


}
