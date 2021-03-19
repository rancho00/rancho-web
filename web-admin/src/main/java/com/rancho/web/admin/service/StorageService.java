package com.rancho.web.admin.service;

import com.rancho.web.common.page.Page;
import com.rancho.web.db.domain.Storage;
import com.rancho.web.db.domain.StorageConfig;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface StorageService {

    List<Storage> getStorages(Page page);

    @Transactional
    void addStorage(Storage storage, MultipartFile multipartFile) throws IOException;

    void deleteStorages(Integer[] ids);

    StorageConfig getStorageConfig();

    void updateStorageConfig(StorageConfig storageConfig);
}
