package com.ray.controller;

import cn.hutool.core.date.DateUtil;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.PutObjectRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.net.URL;
import java.util.Date;

/**
 * @author Ray
 * @description
 * @date 2024/09/10
 */
@Api(tags = "文件上传接口管理")
@RequestMapping("admin/file")
@RestController
public class FileController {

    @Value("${oss.endpoint}")
    private String endpoint;

    @Value("${oss.accessKeyId}")
    private String accessKeyId;

    @Value("${oss.accessKeySecret}")
    private String accessKeySecret;

    @Value("${oss.bucketName}")
    private String bucketName;

    /**
     * 文件上传：请求方式只能是post
     * @return
     */
    @ApiOperation("上传单个文件")
    @PostMapping("upload/element")
    public ResponseEntity<String> uploadFileElement(MultipartFile file) {
        // 创建以天为单位的文件夹名称
        String folderName = DateUtil.format(new Date(), "yyyy-MM-dd");
        // 创建新文件的名称，时间戳
        String fileNewName = DateUtil.format(new Date(),"HHmmssSSS");
        // 获取原文件的名称
        String originalFilename = file.getOriginalFilename();
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));

        // 填写Object完整路径，完整路径中不能包含Bucket名称，例如exampledir/exampleobject.txt。
        String objectName = folderName+"/"+fileNewName+suffix;

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint,accessKeyId,accessKeySecret);
        URL url = null;
        try {
            // 创建PutObjectRequest对象。
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, objectName,file.getInputStream());
            // 上传字符串。
            ossClient.putObject(putObjectRequest);
            // 创建上传文件的访问路径
            url = ossClient.generatePresignedUrl(bucketName, objectName, DateUtil.offsetDay(new Date(), 360 * 10));
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
        return ResponseEntity.ok(url.toString());
    }
}
