package site.easy.to.build.crm.controller.data;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import site.easy.to.build.crm.service.data.DataService;

import java.util.List;

@RestController
@AllArgsConstructor
public class DataRestController {

    private final DataService dataService;


    @GetMapping("/data/init")
    public String initData(){
        return dataService.initData();
    }
}
