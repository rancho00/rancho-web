package com.rancho.web.file.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FileVo {
    @ApiModelProperty(value = "baseUrl")
    private String baseUrl;
    @ApiModelProperty(value = "visitUrl")
    private String visitUrl;
}
