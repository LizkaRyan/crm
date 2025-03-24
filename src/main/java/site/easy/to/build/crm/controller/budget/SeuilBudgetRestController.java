package site.easy.to.build.crm.controller.budget;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.easy.to.build.crm.dto.ResponseJSON;
import site.easy.to.build.crm.entity.budget.SeuilBudget;
import site.easy.to.build.crm.service.budget.SeuilBudgetService;

@RestController
@AllArgsConstructor
@RequestMapping("/api/seuil")
public class SeuilBudgetRestController {

    private final SeuilBudgetService seuilBudgetService;

    @GetMapping
    public ResponseJSON<SeuilBudget> findActualSeuilBudget(){
        return new ResponseJSON<>(200,"Requête réussie", seuilBudgetService.findActualSeuilBudget());
    }

    @GetMapping("save/{taux}")
    public ResponseJSON<SeuilBudget> saveSeuilBudget(@PathVariable("taux")float taux){
        return new ResponseJSON<>(200,"Requête réussie", seuilBudgetService.save(new SeuilBudget(taux)));
    }
}
