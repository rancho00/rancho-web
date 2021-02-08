package com.rancho.web.admin.domain.vo;

import lombok.Data;

@Data
public class Threads {
    private long live;
    private long livePeak;
    private long daemon;
    private long totalStarted;
}
