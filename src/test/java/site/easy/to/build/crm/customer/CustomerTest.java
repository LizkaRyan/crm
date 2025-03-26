package site.easy.to.build.crm.customer;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import site.easy.to.build.crm.entity.Customer;
import site.easy.to.build.crm.service.customer.CustomerService;

@SpringBootTest
public class CustomerTest {

    @Autowired
    private CustomerService customerService;

    @Test
    void createJson(){
        Customer customer=customerService.duplicate(43);
        customer.getExpenses();
        customer.getBudgets();
        System.out.println("VITA");
    }

    @Test
    void createCSV(){
        Customer customer=customerService.duplicate(43);
        customer.getCsv();
        System.out.println("VITA");
    }
}
