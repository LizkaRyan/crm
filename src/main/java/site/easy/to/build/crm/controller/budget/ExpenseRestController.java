package site.easy.to.build.crm.controller.budget;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import site.easy.to.build.crm.dto.DepenseCause;
import site.easy.to.build.crm.dto.ResponseJSON;
import site.easy.to.build.crm.entity.budget.Expense;
import site.easy.to.build.crm.exception.SeuilDepasseException;
import site.easy.to.build.crm.service.budget.ExpenseService;
import site.easy.to.build.crm.util.POV;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/depense")
@AllArgsConstructor
public class ExpenseRestController {

    private final ExpenseService expenseService;

    @GetMapping("/update/{idDepense}/{amount}")
    @JsonView(POV.Public.class)
    public ResponseJSON<Expense> update(@PathVariable("idDepense") Long idDepense, @PathVariable("amount") double amount, HttpSession session) {
        try {
            Expense expense = expenseService.update(idDepense, amount);
            return new ResponseJSON<Expense>(200, "Modification réussie", expense);
        } catch (SeuilDepasseException ex) {
            return new ResponseJSON<Expense>(400, "Seuil dépassé", ex.getExpense());
        }
    }

    @PostMapping("/confirm/update")
    @JsonView(POV.Public.class)
    public ResponseJSON<Expense> confirm(@RequestBody HashMap<String,Object> objects) {
        Expense expense = expenseService.findDepense(((Integer)objects.get("idDepense")).longValue());
        if(expense!=null){
            expense.setAmount((Double)objects.get("amount"));
            expense=expenseService.save(expense);
        }
        return new ResponseJSON<>(200,"Modification réussie",expense);
    }

    @GetMapping("/delete/{idDepense}")
    @JsonView(POV.Public.class)
    public ResponseJSON<Expense> delete(@PathVariable("idDepense") Long idDepense) {
        expenseService.delete(idDepense);
        return new ResponseJSON<Expense>(200, "Suppression réussie");
    }

    @GetMapping("/ticket/{customerId}")
    public ResponseJSON<List<DepenseCause>> findDepenseTicketByIdCustomer(@PathVariable("customerId") Integer customerId) {
        return new ResponseJSON<>(200, "Requête réussie", expenseService.findDepenseTicketByCustomerId(customerId));
    }

    @GetMapping("/ticket")
    public ResponseJSON<List<DepenseCause>> findDepenseTicket() {
        return new ResponseJSON<>(200, "Requête réussie", expenseService.findDepenseTicket());
    }

    @GetMapping("/lead")
    public ResponseJSON<List<DepenseCause>> findDepenseLead() {
        return new ResponseJSON<>(200, "Requête réussie", expenseService.findDepenseLead());
    }

    @GetMapping("/lead/{customerId}")
    public ResponseJSON<List<DepenseCause>> findDepenseLeadByIdCustomer(@PathVariable("customerId") Integer customerId) {
        return new ResponseJSON<>(200, "Requête réussie", expenseService.findDepenseLeadByCustomerId(customerId));
    }

    @GetMapping("/{customerId}")
    public ResponseJSON<List<DepenseCause>> findDepenseByIdCustomer(@PathVariable("customerId") Integer customerId) {
        return new ResponseJSON<>(200, "Requête réussie", expenseService.findDepenseCauseByCustomerId(customerId));
    }
}
