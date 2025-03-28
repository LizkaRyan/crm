package site.easy.to.build.crm.dto.csv;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
import site.easy.to.build.crm.entity.Customer;
import site.easy.to.build.crm.entity.budget.Budget;
import site.easy.to.build.crm.util.csv.CSVFile;
import site.easy.to.build.crm.util.csv.ConstraintCSV;
import site.easy.to.build.crm.util.csv.exception.CellCSVException;
import site.easy.to.build.crm.util.csv.exception.CsvException;

import java.io.IOException;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class BudgetCsv {
    private String customerEmail;
    private double budget;

    public Budget createBudget(List<Customer> customers)throws CellCSVException {
        Budget budget=new Budget();
        int i=0;
        for (i=0;i<customers.size();i++){
            if(customers.get(i).getEmail().equals(customerEmail)){
                budget.setCustomer(customers.get(i));
                break;
            }
        }
        if(budget.getCustomer()==null){
            throw new CellCSVException("Customer: "+customerEmail+" not found on line "+(i+1));
        }
        budget.setName("Budget for: "+budget.getCustomer().getName());
        budget.setBudget(this.getBudget());
        return budget;
    }
}
