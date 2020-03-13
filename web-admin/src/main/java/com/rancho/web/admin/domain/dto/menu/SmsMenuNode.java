package com.rancho.web.admin.domain.dto.menu;

import com.rancho.web.admin.domain.SmsMenu;
import lombok.Data;

import java.util.List;

@Data
public class SmsMenuNode extends SmsMenu {

    private List<SmsMenu> children;
}
