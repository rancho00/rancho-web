package com.rancho.web.admin.service;

import com.rancho.web.admin.domain.server.ServerConnect;
import com.rancho.web.admin.domain.server.ServerCreate;
import com.rancho.web.admin.domain.server.ServerUpdate;
import com.rancho.web.admin.domain.server.ServerParam;
import com.rancho.web.common.page.Page;
import com.rancho.web.db.domain.Server;

import java.util.List;
import java.util.Map;

public interface ServerService {

    List<Server> getServers(ServerParam serverParam, Page page);

    void addServer(ServerCreate serverCreate);

    void updateServer(Integer id,ServerUpdate serverUpdate);

    void deleteServer(Integer[] ids);

    boolean connect(ServerConnect serverConnect);

    List<Map<String,Object>> getServerOptions();
}
