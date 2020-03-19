package com.rancho.web.api.domain;

import lombok.Data;

import java.util.Date;

@Data
public class UmsMemeber {
    private Integer id;
    private String mobile;
    private String nickname;
    private Date createTime;
}
