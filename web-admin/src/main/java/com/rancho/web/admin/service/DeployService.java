package com.rancho.web.admin.service;

import com.rancho.web.admin.domain.dto.deploy.DeployCreate;
import com.rancho.web.admin.domain.dto.deploy.DeployInfo;
import com.rancho.web.admin.domain.dto.deploy.DeployUpdate;
import com.rancho.web.common.page.Page;
import com.rancho.web.db.domain.DeployHistory;

import java.io.IOException;
import java.util.List;

public interface DeployService {

    List<DeployInfo> getDeployInfos(Page page);

    List<DeployHistory> getDeployHistoryByDeployId(Integer deployId,Page page);

    void addDeploy(DeployCreate deployCreate);

    DeployInfo getDeployInfo(Integer id);

    void updateDeploy(Integer id,DeployUpdate deployUpdate);

    void deleteDeploy(Integer[] ids);

    void deploy(Integer id,String fileSavePath) throws InterruptedException, IOException;

    void restore(Integer historyId) throws InterruptedException, IOException;

    void serverStatus(Integer id) throws InterruptedException, IOException;

    void startServer(Integer id) throws InterruptedException, IOException;

    void stopServer(Integer id) throws InterruptedException, IOException;
}
