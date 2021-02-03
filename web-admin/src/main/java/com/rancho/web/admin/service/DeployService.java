package com.rancho.web.admin.service;

import com.rancho.web.admin.domain.dto.deploy.DeployCreate;
import com.rancho.web.admin.domain.dto.deploy.DeployInfo;
import com.rancho.web.admin.domain.dto.deploy.DeployUpdate;
import com.rancho.web.common.page.Page;
import com.rancho.web.db.domain.Deploy;

import java.util.List;

public interface DeployService {

    List<DeployInfo> getDeployInfos(Page page);

    void addDeploy(DeployCreate deployCreate);

    void updateDeploy(Integer id,DeployUpdate deployUpdate);

    void deleteDeploy(Integer[] ids);

    void deploy(String fileSavePath, Integer appId);
}
