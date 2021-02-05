package com.rancho.web.admin.mapper;

import com.rancho.web.db.domain.DeployHistory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DeployHistoryMapper {

    void addDeployHistory(DeployHistory deployHistory);

    List<DeployHistory> getDeployHistoryByDeployId(@Param("deployId") Integer deployId);

    DeployHistory getDeployHistory(@Param("id") Integer id);
}
