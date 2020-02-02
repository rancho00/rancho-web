package com.rancho.web.admin.domain.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminPasswordDto {
    private Integer id;
    private String username;
    private String nickname;
    private String password;
    private Integer status;
}
