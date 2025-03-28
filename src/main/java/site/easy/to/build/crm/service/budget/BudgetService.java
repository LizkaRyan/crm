package site.easy.to.build.crm.service.budget;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import site.easy.to.build.crm.dto.csv.BudgetCsv;
import site.easy.to.build.crm.entity.Customer;
import site.easy.to.build.crm.entity.budget.Budget;
import site.easy.to.build.crm.repository.budget.BudgetRepository;
import site.easy.to.build.crm.service.customer.CustomerServiceImpl;
import site.easy.to.build.crm.util.csv.exception.CellCSVException;
import site.easy.to.build.crm.util.csv.exception.CsvException;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class BudgetService {

    private final BudgetRepository budgetRepository;

    private final JdbcTemplate jdbcTemplate;

    public Budget save(Budget budget){
        return budgetRepository.save(budget);
    }

    public List<Budget> findAll(){
        return budgetRepository.findAll();
    }

    public List<Budget> findByCustomerId(Integer customerId){
        return budgetRepository.findBudgetByIdCustomer(customerId);
    }

    public Budget findById(Long budgetId){
        return budgetRepository.findById(budgetId).orElseThrow(()->new RuntimeException(budgetId+" budget not found"));
    }

    public Double findSumBudgetCustomer(Integer customerId){
        return budgetRepository.findSumBudgetByIdCustomer(customerId).orElse(0d);
    }

    public Double findSumBudget() {
        return budgetRepository.findSumBudget().orElse(0d);
    }

    public List<Budget> createBudgets(List<BudgetCsv> budgetCsvs, List<Customer> customers)throws CsvException{
        List<String> errors=new ArrayList<>();
        List<Budget> budgets=new ArrayList<>();
        for (BudgetCsv budgetCsv:budgetCsvs){
            try{
                budgets.add(budgetCsv.createBudget(customers));
            } catch (CellCSVException e) {
                errors.add(e.getMessage());
            }
        }
        if(!errors.isEmpty()){
            throw new CsvException(errors);
        }
        return budgets;
    }

    public List<Budget> saveAll(List<Budget> budgets){
        return this.budgetRepository.saveAll(budgets);
    }

    public void insertBatch(List<Budget> budgets){
        String sql = "INSERT INTO budget (name, budget, customer_id) VALUES (?, ?, ?)";
        jdbcTemplate.batchUpdate(sql, budgets, 50, (ps, entity) -> {
            ps.setString(1, entity.getName());
            ps.setDouble(2, entity.getBudget());
            ps.setInt(3, entity.getCustomer().getCustomerId());
        });
    }
}
