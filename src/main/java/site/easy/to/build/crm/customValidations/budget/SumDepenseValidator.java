package site.easy.to.build.crm.customValidations.budget;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import site.easy.to.build.crm.dto.ExpenseDTO;
import site.easy.to.build.crm.dto.SumChart;
import site.easy.to.build.crm.entity.budget.Budget;
import site.easy.to.build.crm.service.budget.BudgetService;
import site.easy.to.build.crm.service.budget.ExpenseService;

public class SumDepenseValidator implements ConstraintValidator<SumDepense, ExpenseDTO> {

    private final ExpenseService expenseService;

    private final BudgetService budgetService;

    @Autowired
    public SumDepenseValidator(ExpenseService expenseService, BudgetService budgetService) {
        this.expenseService = expenseService;
        this.budgetService = budgetService;
    }

    @Override
    public boolean isValid(ExpenseDTO depenseDTO, ConstraintValidatorContext constraintValidatorContext) {
        Double sumBudget = budgetService.findSumBudgetCustomer(depenseDTO.getCustomerId());
        Double sumDepense = expenseService.findSumDepenseByCustomerId(depenseDTO.getCustomerId());
        if(sumDepense+depenseDTO.getAmount()>sumBudget){
            return false;
        }
        return true;
    }
}
