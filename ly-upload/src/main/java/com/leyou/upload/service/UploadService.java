package com.leyou.upload.service;

import com.leyou.common.enums.ExceptionEnum;
import com.leyou.common.exception.LyException;
import com.leyou.upload.client.FastDFSClient;
import com.leyou.upload.config.FileServerUrlProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author zhangxl98
 * @Date 6/30/19 9:35 PM
 * @OS Ubuntu 18.04 LTS
 * @Device ASRock-Desktop
 * @Version V1.0.0
 * @Description 文件上传业务层
 */
@Service
@EnableConfigurationProperties(FileServerUrlProperties.class)
@Slf4j
public class UploadService {

    @Autowired
    private FastDFSClient fastDFSClient;

    @Autowired
    private FileServerUrlProperties properties;

    /**
     * 图片上传
     * <pre>createTime:
     * 6/30/19 9:41 PM</pre>
     *
     * @param file 要上传的图片
     * @return 返回图片路径
     */
    public String uploadImage(MultipartFile file) {

        // 获取文件扩展名
        String originalFilename = file.getOriginalFilename();
        String fileExtName = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);

        try {
            // 上传图片
            String path = fastDFSClient.uploadFile(file.getBytes(), file.getSize(), fileExtName);
            // 拼接成完整的图片路径
            log.info("Image : {}", properties.getUrl() + path);
            return properties.getUrl() + path;
        } catch (IOException e) {
            // 上传失败，抛出异常
            throw new LyException(ExceptionEnum.FILE_UPLOAD_ERROR);
        }
    }
}
