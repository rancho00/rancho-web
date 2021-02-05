package com.rancho.web.admin.domain.dto;

import lombok.Data;

@Data
public class ServerServe {

    private Long id;

    private String name;

    private String ip;

    private Integer port;

    private String account;

    private String password;
}
