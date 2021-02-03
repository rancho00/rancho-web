package com.rancho.web.admin.domain.dto.deploy;

import com.rancho.web.db.domain.Serve;
import com.rancho.web.db.domain.Server;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class DeployInfo {

    @ApiModelProperty(value = "id")
    private Integer id;

    @ApiModelProperty(value = "服务id")
    private Integer serveId;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "服务")
    private Serve serve;

    @ApiModelProperty(value = "服务器")
    private List<Server> servers;
}
