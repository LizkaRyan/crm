package site.easy.to.build.crm.service.budget;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import site.easy.to.build.crm.service.customer.CustomerService;

@Service
@AllArgsConstructor
public class CsvService {

    private BudgetService budgetService;

    private CustomerService customerService;

    private ExpenseService expenseService;
}
