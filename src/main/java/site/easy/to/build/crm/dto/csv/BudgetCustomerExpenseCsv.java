package site.easy.to.build.crm.dto.csv;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
import site.easy.to.build.crm.dto.csv.reader.BudgetCsvReader;
import site.easy.to.build.crm.dto.csv.reader.CustomerCsvReader;
import site.easy.to.build.crm.dto.csv.reader.ExpenseCsvReader;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class BudgetCustomerExpenseCsv {
    private MultipartFile budgetFile;
    private MultipartFile customerFile;
    private MultipartFile expenseFile;
    private String separatorBudget;
    private String separatorCustomer;
    private String separatorExpense;

    @Setter(AccessLevel.PRIVATE)
    private BudgetCsvReader budgetReader;

    @Setter(AccessLevel.PRIVATE)
    private CustomerCsvReader customerReader;

    @Setter(AccessLevel.PRIVATE)
    private ExpenseCsvReader expenseReader;

    @Setter(AccessLevel.PRIVATE)
    private boolean hasBeenRead=false;

    public BudgetCsvReader getBudgetReader(){
        if(budgetReader ==null){
            budgetReader =new BudgetCsvReader(budgetFile,separatorBudget);
        }
        return budgetReader;
    }


    public CustomerCsvReader getCustomerReader(){
        if(customerReader ==null){
            customerReader =new CustomerCsvReader(customerFile,separatorCustomer);
        }
        return customerReader;
    }

    public ExpenseCsvReader getExpenseReader(){
        if(expenseReader ==null){
            expenseReader =new ExpenseCsvReader(expenseFile,separatorExpense);
        }
        return expenseReader;
    }

    public void read(){
        this.getBudgetReader().read();
        this.getCustomerReader().read();
        this.getExpenseReader().read();
        this.hasBeenRead=true;
    }

    public boolean hasCsvErrors(){
        if(!hasBeenRead){
            this.read();
        }
        return this.getBudgetReader().hasError() || this.getCustomerReader().hasError() || this.getCustomerReader().hasError();
    }

    public List<String> getErrors(){
        List<String> value=new ArrayList<>();
        value.addAll(this.getBudgetReader().getErrors());
        value.addAll(this.getCustomerReader().getErrors());
        value.addAll(this.getExpenseReader().getErrors());
        return value;
    }
}
