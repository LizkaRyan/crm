package site.easy.to.build.crm.service.budget;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import site.easy.to.build.crm.dto.csv.BudgetCustomerExpenseCsv;
import site.easy.to.build.crm.entity.Customer;
import site.easy.to.build.crm.entity.User;
import site.easy.to.build.crm.entity.budget.Budget;
import site.easy.to.build.crm.entity.budget.Expense;
import site.easy.to.build.crm.service.customer.CustomerService;
import site.easy.to.build.crm.util.csv.exception.CellCSVException;
import site.easy.to.build.crm.util.csv.exception.CsvException;

import java.util.List;

@Service
@AllArgsConstructor
public class CsvService {

    private BudgetService budgetService;

    private CustomerService customerService;

    private ExpenseService expenseService;

    public void readAndInsert(BudgetCustomerExpenseCsv budgetCustomerExpenseCsv)throws CsvException {
        if(budgetCustomerExpenseCsv.hasCsvErrors()){
            throw new CsvException(budgetCustomerExpenseCsv.getErrors());
        }
        List<Customer> customers = customerService.createCustomer(budgetCustomerExpenseCsv.getCustomerReader().getData());
        List<Budget> budgets = budgetService.createBudgets(budgetCustomerExpenseCsv.getBudgetReader().getData(),customers);
        List<Expense> expenses = expenseService.createExpense(budgetCustomerExpenseCsv.getExpenseReader().getData(),customers);

        System.out.println("Vita");
    }
}
