package site.easy.to.build.crm.depense;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import site.easy.to.build.crm.service.budget.DepenseService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@SpringBootTest
public class DepenseTest {

    @Autowired
    private DepenseService depenseService;

    @Test
    public void findSumDepenseByCustomerId(){
        Double sumDepense=this.depenseService.findSumDepenseByCustomerId(47);
        assertNotEquals(0,sumDepense);
    }
}
