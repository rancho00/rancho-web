package com.rancho.web.admin.websocket;

import lombok.Data;
import org.apache.poi.ss.formula.functions.T;

@Data
public class Msg {

    private String type;

    private T data;
}
