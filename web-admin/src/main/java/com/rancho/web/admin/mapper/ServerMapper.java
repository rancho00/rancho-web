package com.rancho.web.admin.mapper;

import com.rancho.web.admin.domain.server.ServerParam;
import com.rancho.web.db.domain.Server;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

public interface ServerMapper {

    List<Server> getServers(ServerParam serverParam);

    List<Server> getServersByDeployId(@Param("deployId") Integer deployId);

    void addServer(Server server);

    void updateServer(Server server);

    void deleteServer(@Param("ids") Integer[] ids);

    Server getServer(@Param("id")Integer id);
}
