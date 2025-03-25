package site.easy.to.build.crm.dto.csv;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
import site.easy.to.build.crm.dto.csv.reader.BudgetCsvReader;
import site.easy.to.build.crm.util.csv.CSVFile;
import site.easy.to.build.crm.util.csv.CollectionCsvFile;

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

    public CollectionCsvFile getCsvFiles(){
        List<CSVFile<?>> csvFiles=new ArrayList<>();
        csvFiles.add(new BudgetCsvReader(budgetFile,separatorBudget));
        csvFiles.add(new BudgetCsvReader(customerFile,separatorCustomer));
        csvFiles.add(new BudgetCsvReader(expenseFile,separatorExpense));
        return new CollectionCsvFile(csvFiles);
    }
}
