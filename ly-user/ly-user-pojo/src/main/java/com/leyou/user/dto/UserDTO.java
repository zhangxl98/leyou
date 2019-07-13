package com.leyou.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author zhangxl98
 * @Date 7/13/19 8:08 PM
 * @OS Ubuntu 18.04 LTS
 * @Device ASRock-Desktop
 * @Version V1.0.0
 * @Description
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    /**
     * 用户 id
     */
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 用户手机号
     */
    private String phone;

    /**
     * 用户创建时间
     */
    private Long created;
}
