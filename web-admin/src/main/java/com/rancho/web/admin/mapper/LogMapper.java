package com.rancho.web.admin.mapper;

import com.rancho.web.db.domain.Log;

import java.util.List;

public interface LogMapper{

    List<Log> getLogs(Log log);

    void addLog(Log log);

    Log getLog(Integer id);
}
