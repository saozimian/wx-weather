package com.codehuan.entity.vo;

import com.codehuan.entity.Weaver;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author ZhangHuan created on 2022/8/22
 * @email codehuan@163.com
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WeaverVo {
    /**
     * {
     * "status": "1",
     * "count": "1",
     * "info": "OK",
     * "infocode": "10000",
     * "lives": [
     * {
     * "province": "广东",
     * "city": "深圳市",
     * "adcode": "440300",
     * "weather": "晴",
     * "temperature": "29",
     * "winddirection": "西南",
     * "windpower": "≤3",
     * "humidity": "82",
     * "reporttime": "2022-08-22 20:30:16"
     * }
     * ]
     * }
     */

    private String status;
    private String count;
    private String info;
    private String infocode;
    private List<Weaver> lives;

}
