package com.leyou.upload.service;

import com.aliyun.oss.OSS;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.model.MatchMode;
import com.aliyun.oss.model.PolicyConditions;
import com.leyou.common.enums.ExceptionEnum;
import com.leyou.common.exception.LyException;
import com.leyou.upload.client.FastDFSClient;
import com.leyou.upload.config.FileServerUrlProperties;
import com.leyou.upload.config.OSSProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;

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

    /**
     * 支持的文件类型
     */
    private static final List<String> SUFFIXES = Arrays.asList("image/png", "image/jpeg", "image/bmp");

    @Autowired
    private FastDFSClient fastDFSClient;

    @Autowired
    private FileServerUrlProperties fastDFSUrlProperties;

    @Autowired
    private OSSProperties ossProperties;

    @Autowired
    private OSS ossClient;

    /**
     * 图片上传
     * <pre>createTime:
     * 6/30/19 9:41 PM</pre>
     *
     * @param file 要上传的图片
     * @return 返回图片路径
     */
    public String uploadImage(MultipartFile file) {

        // 验证上传的文件类型
        if (!SUFFIXES.contains(file.getContentType())) {
            throw new LyException(ExceptionEnum.INVALID_FILE_TYPE);
        }

        // 验证文件内容
        BufferedImage read;
        try {
            read = ImageIO.read(file.getInputStream());
        } catch (IOException e) {
            throw new LyException(ExceptionEnum.INVALID_FILE_TYPE);
        }
        if (null == read) {
            throw new LyException(ExceptionEnum.INVALID_FILE_TYPE);
        }

        // 获取文件扩展名
        String originalFilename = file.getOriginalFilename();
        String fileExtName = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);

        try {
            // 上传图片
            String path = fastDFSClient.uploadFile(file.getBytes(), file.getSize(), fileExtName);
            // 拼接成完整的图片路径
            log.info("Image : {}", fastDFSUrlProperties.getUrl() + path);
            return fastDFSUrlProperties.getUrl() + path;
        } catch (IOException e) {
            // 上传失败，抛出异常
            throw new LyException(ExceptionEnum.FILE_UPLOAD_ERROR);
        }
    }

    /**
     * 利用 OSS 上传图片
     * <pre>createTime:
     * 7/1/19 11:08 AM</pre>
     *
     * @return
     */
    public Map<String, Object> getSignature() {

        try {
            long expireEndTime = System.currentTimeMillis() + ossProperties.getExpireTime() * 1000;

            Date expiration = new Date(expireEndTime);
            PolicyConditions policyConds = new PolicyConditions();
            policyConds.addConditionItem(PolicyConditions.COND_CONTENT_LENGTH_RANGE, 0, ossProperties.getMaxFileSize());
            policyConds.addConditionItem(MatchMode.StartWith, PolicyConditions.COND_KEY, ossProperties.getDir());

            String postPolicy = ossClient.generatePostPolicy(expiration, policyConds);
            byte[] binaryData = postPolicy.getBytes("utf-8");
            String encodedPolicy = BinaryUtil.toBase64String(binaryData);
            String postSignature = ossClient.calculatePostSignature(postPolicy);

            Map<String, Object> respMap = new LinkedHashMap<>();

            respMap.put("accessId", ossProperties.getAccessKeyId());
            respMap.put("dir", ossProperties.getDir());
            respMap.put("host", ossProperties.getHost());
            respMap.put("expire", expireEndTime);
            respMap.put("policy", encodedPolicy);
            respMap.put("signature", postSignature);

            return respMap;
        } catch (Exception e) {
            throw new LyException(ExceptionEnum.FILE_UPLOAD_ERROR);
        }
    }
}
