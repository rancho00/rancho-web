package com.rancho.web.admin.mapper;

import com.rancho.web.db.domain.Storage;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StorageMapper {

    List<Storage> getStorages();

    void addStorage(Storage file);

    Storage getStorage(@Param("id") Integer id);

    void deleteStorage(@Param("id")Integer id);
}
