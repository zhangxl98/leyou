package com.leyou.search.service;

import com.leyou.common.utils.BeanHelper;
import com.leyou.dto.SpuDTO;
import com.leyou.search.pojo.Goods;
import org.springframework.stereotype.Service;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author zhangxl98
 * @Date 7/6/19 3:11 PM
 * @OS Ubuntu 18.04 LTS
 * @Device ASRock-Desktop
 * @Version V1.0.0
 * @Description 导入数据业务
 */
@Service
public class IndexService {

    /**
     * 把一个 SPU 转为一个 Goods 对象
     * <pre>createTime:
     * 7/6/19 5:31 PM</pre>
     *
     * @param spuDTO 要转换的 Spu 对象
     * @return 转换好的 Goods 对象
     */
    public Goods buildGoods(SpuDTO spuDTO) {

        return BeanHelper.copyProperties(spuDTO, Goods.class);
    }
}
