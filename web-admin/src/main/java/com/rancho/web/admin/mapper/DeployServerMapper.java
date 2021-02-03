package com.rancho.web.admin.mapper;

import com.rancho.web.db.domain.DeployServer;
import org.apache.ibatis.annotations.Param;

public interface DeployServerMapper {

    void addDeployServer(DeployServer deployServer);

    void deleteDeployServerByDeployId(@Param("deployId") Integer deployId);
}
