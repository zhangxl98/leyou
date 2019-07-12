package com.leyou.page.service;

import com.leyou.common.enums.ExceptionEnum;
import com.leyou.common.exception.LyException;
import com.leyou.dto.*;
import com.leyou.item.client.ItemClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author zhangxl98
 * @Date 7/11/19 9:37 PM
 * @OS Ubuntu 18.04 LTS
 * @Device ASRock-Desktop
 * @Version V1.0.0
 * @Description
 */
@Service
@Slf4j
public class PageService {

    @Autowired
    private ItemClient itemClient;

    @Autowired
    private SpringTemplateEngine templateEngine;

    @Value("${ly.static.itemDir}")
    private String itemDir;
    @Value("${ly.static.itemTemplate}")
    private String itemTemplate;

    /**
     * 返回商品详情页数据
     * <pre>createTime:
     * 7/11/19 9:39 PM</pre>
     *
     * @param spuId 商品 id
     * @return 商品详情页数据
     */
    public Map<String, Object> loadData(Long spuId) {

        Map<String, Object> result = new HashMap<>(16);

        List<SkuDTO> skuDTOS = itemClient.querySkuListBySpuId(spuId);

        SpuDTO spuDTO = itemClient.querySpuBySpuId(spuId);

        SpuDetailDTO spuDetailDTO = itemClient.querySpuDetailBySpuId(spuId);

        List<CategoryDTO> categoryDTOS = itemClient.queryCategoryListByIds(spuDTO.getCategoryIds());

        List<SpecGroupDTO> specGroupDTOS = itemClient.querySpecListByCategoryId(spuDTO.getCid3());

        BrandDTO brandDTO = itemClient.queryBrandById(spuDTO.getBrandId());

        result.put("skus", skuDTOS);
        result.put("spuName", spuDTO.getName());
        result.put("subTitle", spuDTO.getSubTitle());
        result.put("detail", spuDetailDTO);
        result.put("specs", specGroupDTOS);
        result.put("categories", categoryDTOS);
        result.put("brand", brandDTO);

        return result;
    }

    /**
     * 将生成的静态页面写入本地磁盘
     * <pre>createTime:
     * 7/12/19 4:22 PM</pre>
     *
     * @param id
     */
    public void createItemHtml(Long id) {
        // 上下文，准备模型数据
        Context context = new Context();
        // 调用之前写好的加载数据方法
        context.setVariables(loadData(id));
        // 准备文件路径
        File dir = new File(itemDir);
        if (!dir.exists()) {
            if (!dir.mkdirs()) {
                // 创建失败，抛出异常
                log.error("【静态页服务】创建静态页目录失败，目录地址：{}", dir.getAbsolutePath());
                throw new LyException(ExceptionEnum.DIRECTORY_WRITER_ERROR);
            }
        }
        File filePath = new File(dir, id + ".html");
        // 准备输出流
        try (PrintWriter writer = new PrintWriter(filePath, "UTF-8")) {
            templateEngine.process(itemTemplate, context, writer);
        } catch (IOException e) {
            log.error("【静态页服务】静态页生成失败，商品id：{}", id, e);
            throw new LyException(ExceptionEnum.FILE_WRITER_ERROR);
        }
    }

    /**
     * 删除页面
     * <pre>createTime:
     * 7/12/19 6:29 PM</pre>
     *
     * @param id
     */
    public void deleteItemHtml(Long id) {
        File file = new File(itemDir, id + ".html");
        if (file.exists()) {
            if (!file.delete()) {
                log.error("【静态页服务】静态页删除失败，商品id：{}", id);
                throw new LyException(ExceptionEnum.FILE_WRITER_ERROR);
            }
        }
    }
}
