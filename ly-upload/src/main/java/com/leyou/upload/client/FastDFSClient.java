package com.leyou.upload.client;

import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.domain.ThumbImageConfig;
import com.github.tobato.fastdfs.service.DefaultFastFileStorageClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author zhangxl98
 * @Date 6/30/19 8:33 PM
 * @OS Ubuntu 18.04 LTS
 * @Device ASRock-Desktop
 * @Version V1.0.0
 * @Description fastDFS 文件上传工具类
 */
@Component
public class FastDFSClient {

    @Autowired
    private DefaultFastFileStorageClient storageClient;

    @Autowired
    private ThumbImageConfig thumbImageConfig;

    /**
     * 文件上传的方法
     * <pre>createTime:
     * 6/30/19 9:16 PM</pre>
     *
     * @param fileContent 文件的内容，字节数组
     * @param fileSize    文件大小
     * @param fileExtName 文件扩展名
     * @return 文件访问路径（带分组）
     */
    public String uploadFile(byte[] fileContent, Long fileSize, String fileExtName) {
        StorePath storePath = storageClient.uploadFile(new ByteArrayInputStream(fileContent), fileSize, fileExtName, null);
        return storePath.getFullPath();
    }
}
