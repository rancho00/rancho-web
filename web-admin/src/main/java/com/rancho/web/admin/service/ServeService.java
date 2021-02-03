package com.rancho.web.admin.service;

import com.rancho.web.admin.domain.serve.ServeCreate;
import com.rancho.web.admin.domain.serve.ServeParam;
import com.rancho.web.admin.domain.serve.ServeUpdate;
import com.rancho.web.common.page.Page;
import com.rancho.web.db.domain.Serve;

import java.util.List;
import java.util.Map;

public interface ServeService {

    List<Serve> getServes(ServeParam serveParam, Page page);

    void addServe(ServeCreate serveCreate);

    void updateServe(Integer id, ServeUpdate serveUpdate);

    void deleteServe(Integer[] ids);

    List<Map<String,Object>> getServeOptions();
}
