package com.codehuan.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author ZhangHuan created on 2022/8/22
 * @email codehuan@163.com
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    /**
     * 用户id
     */
    private List<String> openid;

}
