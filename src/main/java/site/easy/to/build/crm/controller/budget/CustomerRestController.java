package site.easy.to.build.crm.controller.budget;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import site.easy.to.build.crm.dto.ResponseJSON;
import site.easy.to.build.crm.dto.csv.BudgetCustomerExpenseCsv;
import site.easy.to.build.crm.entity.Customer;
import site.easy.to.build.crm.service.budget.CsvService;
import site.easy.to.build.crm.service.customer.CustomerService;
import site.easy.to.build.crm.util.csv.exception.CsvException;

@RestController
@RequestMapping("/api/customer")
@AllArgsConstructor
@CrossOrigin
public class CustomerRestController {

    private final CustomerService customerService;

    private final CsvService csvService;

    @PostMapping("/upload-multiple")
    public String uploadMultipleFiles(@RequestParam("expense") MultipartFile expense,
                                      @RequestParam("budget") MultipartFile budget,
                                      @RequestParam("customer") MultipartFile customer) {
        try {
            BudgetCustomerExpenseCsv budgetCustomerExpenseCsv=new BudgetCustomerExpenseCsv();

            budgetCustomerExpenseCsv.setCustomerFile(customer);
            budgetCustomerExpenseCsv.setSeparatorCustomer(",");

            budgetCustomerExpenseCsv.setExpenseFile(expense);
            budgetCustomerExpenseCsv.setSeparatorExpense(",");

            budgetCustomerExpenseCsv.setBudgetFile(budget);
            budgetCustomerExpenseCsv.setSeparatorExpense(",");

            csvService.readAndInsert(budgetCustomerExpenseCsv);

            return "Tous les fichiers ont été reçus avec succès.";
        } catch (CsvException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/login")
    public Customer test(){
        Customer customer=new Customer();
        customer.setEmail("fmlsdkf");
        customer.setState("sdkjfsdlk");
        return customer;
    }
}
