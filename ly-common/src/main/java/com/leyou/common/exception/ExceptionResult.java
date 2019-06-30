package com.leyou.common.exception;

import lombok.Getter;
import org.joda.time.DateTime;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author zhangxl98
 * @Date 6/20/19 8:24 PM
 * @OS Ubuntu 18.04 LTS
 * @Device ASRock-Desktop
 * @Version V1.0.0
 * @Description 异常结果对象
 */
@Getter
public class ExceptionResult {

    /**
     * 异常状态码
     */
    private int status;

    /**
     * 异常信息
     */
    private String message;

    /**
     * 时间戳
     */
    private String timestamp;

    public ExceptionResult(LyException e) {
        this.status = e.getStatus();
        this.message = e.getMessage();
        this.timestamp = DateTime.now().toString("yyyy-MM-dd HH:mm:ss");
    }
}
