package site.easy.to.build.crm.service.budget;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.easy.to.build.crm.dto.csv.BudgetCustomerExpenseCsv;
import site.easy.to.build.crm.dto.csv.entity.CustomerUser;
import site.easy.to.build.crm.entity.Customer;
import site.easy.to.build.crm.entity.Role;
import site.easy.to.build.crm.entity.User;
import site.easy.to.build.crm.entity.budget.Budget;
import site.easy.to.build.crm.entity.budget.Expense;
import site.easy.to.build.crm.service.customer.CustomerService;
import site.easy.to.build.crm.service.role.RoleService;
import site.easy.to.build.crm.service.user.UserService;
import site.easy.to.build.crm.util.csv.exception.CsvException;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class CsvService {

    private BudgetService budgetService;

    private CustomerService customerService;

    private ExpenseService expenseService;

    private UserService userService;

    private RoleService roleService;

    @Transactional
    public void readAndInsert(BudgetCustomerExpenseCsv budgetCustomerExpenseCsv)throws CsvException {
        if(budgetCustomerExpenseCsv.hasCsvErrors()){
            throw new CsvException(budgetCustomerExpenseCsv.getErrors());
        }
        Role role=roleService.findById(1).orElseThrow(()->new RuntimeException("Role Id not found"));
        CustomerUser customersUsers = (customerService.createCustomer(budgetCustomerExpenseCsv.getCustomerReader().getData(),role));
        customersUsers.setUsers(userService.saveAll(customersUsers.getUsers()));
        customersUsers.setCustomers(customerService.saveAll(customersUsers.getCustomers()));
        List<Budget> budgets = budgetService.createBudgets(budgetCustomerExpenseCsv.getBudgetReader().getData(), customersUsers.getCustomers());
        budgets=budgetService.saveAll(budgets);
        List<Expense> expenses = expenseService.createExpense(budgetCustomerExpenseCsv.getExpenseReader().getData(),customersUsers.getCustomers());
        expenses=expenseService.saveAll(expenses);

        System.out.println("Vita");
    }

    public static List<User> getUsers(List<Customer> customers){
        List<User> users=new ArrayList<>();
        for (Customer customer:customers){
            users.add(customer.getUser());
        }
        return users;
    }
}
