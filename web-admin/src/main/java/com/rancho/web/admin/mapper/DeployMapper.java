package com.rancho.web.admin.mapper;

import com.rancho.web.admin.domain.dto.deploy.DeployInfo;
import com.rancho.web.db.domain.Deploy;
import com.rancho.web.db.domain.Server;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DeployMapper {

    List<DeployInfo> getDeployInfos();

    void addDeploy(Deploy deploy);

    void updateDeploy(Deploy deploy);

    void deleteDeploy(@Param("ids") Integer[] ids);

    DeployInfo getDeployInfo(@Param("id") Integer id);
}
