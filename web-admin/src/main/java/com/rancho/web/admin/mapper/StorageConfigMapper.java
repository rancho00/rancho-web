package com.rancho.web.admin.mapper;

import com.rancho.web.admin.domain.StorageConfigWithServer;
import com.rancho.web.db.domain.StorageConfig;

public interface StorageConfigMapper {

    StorageConfigWithServer getStorageConfigWithServer();

    StorageConfig getStorageConfig();

    void updateStorageConfig(StorageConfig storageConfig);
}
