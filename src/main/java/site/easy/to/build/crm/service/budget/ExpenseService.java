package site.easy.to.build.crm.service.budget;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import site.easy.to.build.crm.dto.DepenseCause;
import site.easy.to.build.crm.dto.SumChart;
import site.easy.to.build.crm.dto.SumDepenseCustomer;
import site.easy.to.build.crm.entity.budget.Expense;
import site.easy.to.build.crm.repository.budget.ExpenseRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ExpenseService {

    private final ExpenseRepository depenseRepository;

    private final BudgetService budgetService;

    public Expense save(Expense expense){
        return depenseRepository.save(expense);
    }

    public List<Expense> findDepenseByCustomerId(Integer customerId){
        return depenseRepository.findDepenseByCustomerId(customerId);
    }

    public List<DepenseCause> findDepenseTicket(){
        return depenseRepository.findDepenseTicket();
    }

    public List<DepenseCause> findDepenseLead(){
        return depenseRepository.findDepenseLead();
    }

    public List<DepenseCause> findDepenseCauseByCustomerId(Integer customerId){
        List<DepenseCause> depenseCauses=depenseRepository.findDepenseCauseByCustomerId(customerId);
        return depenseCauses;
    }

    public List<Expense> findAll() {
        return depenseRepository.findAll();
    }

    public SumChart findSumDepenseOnBudget(Long idBudget){
        return depenseRepository.findSumDepenseOnBudgetId(idBudget).orElse(new SumChart());
    }

    public Double findSumDepenseByCustomerId(Integer customerId){
        return depenseRepository.findSumDepenseByCustomerId(customerId).orElse(0d);
    }

    public Expense findDepense(Long idBudget){
        return depenseRepository.findById(idBudget).orElseThrow(()->new RuntimeException("Id not recognized"));
    }

    public Expense update(Long idDepense, double amount){
        Expense expense =findDepense(idDepense);
        expense.setAmount(amount);
        return this.depenseRepository.save(expense);
    }

    public void delete(Long idDepense){
        Expense expense =findDepense(idDepense);
        this.depenseRepository.delete(expense);
    }

    public Double findSumDepenseTicket(){
        return this.depenseRepository.findSumTicket().orElse(0d);
    }

    public Double findSumDepenseLead(){
        return this.depenseRepository.findSumLead().orElse(0d);
    }

    public List<SumChart> findSumDepenseGroupby(){
        double ticket=findSumDepenseTicket();
        double lead=findSumDepenseLead();
        List<SumChart> sumDepenses=new ArrayList<>();
        sumDepenses.add(new SumChart(ticket,"Ticket"));
        sumDepenses.add(new SumChart(lead,"Lead"));
        return sumDepenses;
    }

    public List<SumChart> findSumBudgetAndDepense(){
        double budget=this.budgetService.findSumBudget();
        double depense=this.depenseRepository.findSumDepense().orElse(0d);
        List<SumChart> sumDepenses=new ArrayList<>();
        sumDepenses.add(new SumChart(budget,"Budget"));
        sumDepenses.add(new SumChart(depense,"Depense"));
        return sumDepenses;
    }

    public List<SumDepenseCustomer> findSumDepenseEachCustomer(){
        return this.depenseRepository.findSumDepenseEachCustomer();
    }

    public List<DepenseCause> findDepenseTicketByCustomerId(Integer customerId){
        return this.depenseRepository.findDepenseTicketByCustomerId(customerId);
    }

    public List<DepenseCause> findDepenseLeadByCustomerId(Integer customerId){
        return this.depenseRepository.findDepenseLeadByCustomerId(customerId);
    }

    public List<SumDepenseCustomer> findSumDepenseTicketEachCustomer(){
        return this.depenseRepository.findSumDepenseTicketEachCustomer();
    }

    public List<SumDepenseCustomer> findSumDepenseLeadEachCustomer(){
        return this.depenseRepository.findSumDepenseLeadEachCustomer();
    }
}
