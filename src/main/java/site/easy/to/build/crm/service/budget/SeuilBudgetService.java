package site.easy.to.build.crm.service.budget;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import site.easy.to.build.crm.entity.budget.SeuilBudget;
import site.easy.to.build.crm.repository.budget.SeuilBudgetRepo;

@Service
@AllArgsConstructor
public class SeuilBudgetService {

    private final SeuilBudgetRepo seuilBudgetRepo;

    public SeuilBudget findActualSeuilBudget(){
        return seuilBudgetRepo.findActualSeuilBudget().orElseThrow(()->new RuntimeException("Il n'y a pas encore de seuil dans la base de données"));
    }
}
