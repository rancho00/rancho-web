package com.rancho.web.admin.service.impl;

import com.github.pagehelper.PageHelper;
import com.rancho.web.admin.domain.StorageConfigWithServer;
import com.rancho.web.admin.mapper.StorageConfigMapper;
import com.rancho.web.admin.mapper.StorageMapper;
import com.rancho.web.admin.mapper.ServerMapper;
import com.rancho.web.admin.service.StorageService;
import com.rancho.web.admin.util.FileUtil;
import com.rancho.web.admin.util.ScpClientUtil;
import com.rancho.web.common.page.Page;
import com.rancho.web.db.domain.Storage;
import com.rancho.web.db.domain.StorageConfig;
import com.rancho.web.db.domain.Server;
import io.micrometer.core.instrument.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class StorageServiceImpl implements StorageService {

    @Autowired
    private StorageMapper storageMapper;

    @Autowired
    private StorageConfigMapper storageConfigMapper;

    @Value("${spring.servlet.multipart.location}")
    private String path;

    @Override
    public List<Storage> getStorages(Page page) {
        PageHelper.startPage(page.getPageNumber(),page.getPageSize());
        return storageMapper.getStorages();
    }

    @Override
    public void addStorage(Storage file, MultipartFile multipartFile) throws IOException {
        String suffix = FileUtil.getExtensionName(multipartFile.getOriginalFilename());
        String type = FileUtil.getFileType(suffix);
        File deployFile = new File(FileUtil.getTmpDirPath()+"/"+multipartFile.getOriginalFilename());
        multipartFile.transferTo(deployFile);

        StorageConfigWithServer fileConfigWithServer= storageConfigMapper.getStorageConfigWithServer();
        ScpClientUtil scpClientUtil = getScpClientUtil(fileConfigWithServer.getServer());
        scpClientUtil.putFile(FileUtil.getTmpDirPath()+"/"+multipartFile.getOriginalFilename(),fileConfigWithServer.getPath());

        String name = StringUtils.isBlank(file.getName()) ? FileUtil.getFileNameNoEx(multipartFile.getOriginalFilename()) : file.getName();
        file.setName(name);
        file.setRealName(deployFile.getName());
        file.setSuffix(suffix);
        file.setPath(deployFile.getPath());
        file.setType(type);
        file.setSize(FileUtil.getSize(multipartFile.getSize()));
    }

    @Override
    public void deleteStorages(Integer[] ids) {
        for (Integer id : ids) {
            Storage file= storageMapper.getStorage(id);
            FileUtil.del(file.getPath());
            storageMapper.deleteStorage(id);
        }
    }

    @Override
    public StorageConfig getStorageConfig() {
        return storageConfigMapper.getStorageConfig();
    }

    @Override
    public void updateStorageConfig(StorageConfig storageConfig) {
        storageConfigMapper.updateStorageConfig(storageConfig);
    }

    private ScpClientUtil getScpClientUtil(Server server) {
        return ScpClientUtil.getInstance(server.getIp(), server.getPort(), server.getAccount(), server.getPassword());
    }
}
