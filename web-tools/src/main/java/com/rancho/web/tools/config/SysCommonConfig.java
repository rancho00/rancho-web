package com.rancho.web.tools.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.List;

/**
 * 系统配置
 */
@Configuration
@PropertySource({"classpath:sys-common.properties"})
@Setter
@Getter
public class SysCommonConfig {

    @Value("${uploadProject}")
    private String uploadProject;

    @Value("${windowsPrefix}")
    private String windowsPrefix;

    @Value("${linuxPrefix}")
    private String linuxPrefix;

    @Value("${imageUploadPrefix}")
    private String imageUploadPrefix;

    @Value("${videoUploadPrefix}")
    private String videoUploadPrefix;

    @Value("${fileUploadPrefix}")
    private String fileUploadPrefix;

    @Value("${imageIncludes}")
    private List imageIncludes;

    @Value("${videoIncludes}")
    private List videoIncludes;

    @Value("${fileIncludes}")
    private List fileIncludes;

    @Value("${folderName}")
    private List folderName;
}
