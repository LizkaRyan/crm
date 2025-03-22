package site.easy.to.build.crm.service.budget;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import site.easy.to.build.crm.entity.budget.Budget;
import site.easy.to.build.crm.repository.budget.BudgetRepository;
import site.easy.to.build.crm.service.customer.CustomerServiceImpl;

import java.util.List;

@Service
@AllArgsConstructor
public class BudgetService {

    private final BudgetRepository budgetRepository;

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
}
