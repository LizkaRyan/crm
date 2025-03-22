package site.easy.to.build.crm.data;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import site.easy.to.build.crm.service.data.DataService;

@SpringBootTest
public class InitData {

    @Autowired
    private DataService dataService;

    @Test
    void contextLoads() {
        dataService.initData();
    }
}
