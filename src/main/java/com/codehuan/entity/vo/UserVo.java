package com.codehuan.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 用户Vo
 *
 * @author ZhangHuan created on 2022/8/22
 * @email codehuan@163.com
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserVo implements Serializable {

    /**
     * {
     * "total": 5,
     * "count": 5,
     * "data": {
     * "openid": [
     * "olqaK5sXw4RM4PEaRgi9I0kH4ldE",
     * "olqaK5nbxrC4ehk7Lf9QkSHHbfng",
     * "olqaK5p-v3b6gvFif4XLaAwfg0EE",
     * "olqaK5iLtf1e1O8y50FrAyoVF5G8",
     * "olqaK5haaWE7C8NJ8bclDpKp0Zg8"
     * ]
     * },
     * "next_openid": "olqaK5haaWE7C8NJ8bclDpKp0Zg8"
     * }
     */

    /**
     * 关注该公众账号的总用户数
     */
    private String total;

    /**
     * 拉取的 OPENID 个数，最大值为10000
     */
    private String count;

    /**
     * OPENID的列表
     */
    private String data;

    /**
     * 最后一个用户的OPENID
     */
    private String next_openid;
}
