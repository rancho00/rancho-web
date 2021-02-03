package com.rancho.web.admin.mapper;

import com.rancho.web.admin.domain.serve.ServeParam;
import com.rancho.web.admin.domain.server.ServerParam;
import com.rancho.web.db.domain.Serve;
import com.rancho.web.db.domain.Server;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ServeMapper {

    List<Serve> getServes(ServeParam serveParam);

    void addServe(Serve serve);

    void updateServe(Serve serve);

    void deleteServe(@Param("ids") Integer[] ids);
}
