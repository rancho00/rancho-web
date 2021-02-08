package com.rancho.web.admin.domain.vo;

import lombok.Data;

@Data
public class Heap {

    private double size;
    private double used;
    private double max;
}
