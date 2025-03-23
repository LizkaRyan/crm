package site.easy.to.build.crm.controller.budget;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import site.easy.to.build.crm.dto.DepenseCause;
import site.easy.to.build.crm.dto.ResponseJSON;
import site.easy.to.build.crm.dto.SumDepenseCustomer;
import site.easy.to.build.crm.entity.budget.Depense;
import site.easy.to.build.crm.service.budget.DepenseService;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/depense")
@AllArgsConstructor
public class DepenseRestController {

    private final DepenseService depenseService;

    @GetMapping("/update/{idDepense}/{amount}")
    public ResponseJSON<Depense> update(@PathVariable("idDepense")Long idDepense, @PathVariable("amount")double amount){
        Depense depense=depenseService.update(idDepense,amount);
        return new ResponseJSON<Depense>(200,"Modification réussie",depense);
    }

    @GetMapping("/delete/{idDepense}")
    public ResponseJSON<Depense> delete(@PathVariable("idDepense")Long idDepense){
        depenseService.delete(idDepense);
        return new ResponseJSON<Depense>(200,"Suppression réussie");
    }

    @GetMapping("/statistique")
    public ResponseJSON<HashMap<String, List<SumDepenseCustomer>>> getStat(){
        HashMap<String, List<SumDepenseCustomer>> map=new HashMap<>();
        map.put("totalDepense",depenseService.findSumDepenseEachCustomer());
        map.put("totalTicketDepense",depenseService.findSumDepenseTicketEachCustomer());
        map.put("totalLeadDepense",depenseService.findSumDepenseLeadEachCustomer());
        return new ResponseJSON<>(200,"Requête réussie",map);
    }

    @GetMapping("/ticket/{customerId}")
    public ResponseJSON<List<DepenseCause>> findDepenseTicketByIdCustomer(@PathVariable("customerId")Integer customerId){
        return new ResponseJSON<>(200,"Requête réussie",depenseService.findDepenseTicketByCustomerId(customerId));
    }

    @GetMapping("/lead/{customerId}")
    public ResponseJSON<List<DepenseCause>> findDepenseLeadByIdCustomer(@PathVariable("customerId")Integer customerId){
        return new ResponseJSON<>(200,"Requête réussie",depenseService.findDepenseLeadByCustomerId(customerId));
    }

    @GetMapping("/{customerId}")
    public ResponseJSON<List<DepenseCause>> findDepenseByIdCustomer(@PathVariable("customerId")Integer customerId){
        return new ResponseJSON<>(200,"Requête réussie",depenseService.findDepenseCauseByCustomerId(customerId));
    }
}
