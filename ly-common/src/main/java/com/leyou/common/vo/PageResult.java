package com.leyou.common.vo;

import lombok.*;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author zhangxl98
 * @Date 6/28/19 7:55 PM
 * @OS Ubuntu 18.04 LTS
 * @Device ASRock-Desktop
 * @Version V1.0.0
 * @Description 封装分页数据
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageResult<T> {

    /**
     * 总条数
     */
    private Long total;

    /**
     * 总页数
     */
    private Integer totalPage;

    /**
     * 当前页数据
     */
    private List<T> items;

    public PageResult(Long total, List<T> items) {
        this.total = total;
        this.items = items;
    }
}
