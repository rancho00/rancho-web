package com.rancho.web.admin.domain;

import com.rancho.web.db.domain.StorageConfig;
import com.rancho.web.db.domain.Server;
import lombok.Data;

@Data
public class StorageConfigWithServer extends StorageConfig {

    private Server server;
}
