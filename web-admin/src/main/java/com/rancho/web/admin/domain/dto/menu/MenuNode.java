package com.rancho.web.admin.domain.dto.menu;

import com.rancho.web.db.domain.Menu;
import lombok.Data;

import java.util.List;

@Data
public class MenuNode extends Menu {

    private List<Menu> children;
}
