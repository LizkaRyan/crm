package site.easy.to.build.crm.service.budget;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import site.easy.to.build.crm.dto.SumDepense;
import site.easy.to.build.crm.entity.budget.Budget;
import site.easy.to.build.crm.entity.budget.Depense;
import site.easy.to.build.crm.repository.budget.DepenseRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class DepenseService {
    private final DepenseRepository depenseRepository;

    public Depense save(Depense depense){
        return depenseRepository.save(depense);
    }

    public List<Depense> findDepenseByCustomerId(Integer customerId){
        return depenseRepository.findDepenseByCustomerId(customerId);
    }

    public List<Depense> findAll() {
        return depenseRepository.findAll();
    }

    public SumDepense findSumDepenseOnBudget(Long idBudget){
        return depenseRepository.findSumDepenseOnBudgetId(idBudget).orElse(new SumDepense());
    }
}
