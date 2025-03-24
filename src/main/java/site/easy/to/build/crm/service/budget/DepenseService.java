package site.easy.to.build.crm.service.budget;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import site.easy.to.build.crm.dto.DepenseCause;
import site.easy.to.build.crm.dto.SumChart;
import site.easy.to.build.crm.dto.SumDepenseCustomer;
import site.easy.to.build.crm.entity.budget.Depense;
import site.easy.to.build.crm.repository.budget.DepenseRepository;
import site.easy.to.build.crm.service.lead.LeadService;
import site.easy.to.build.crm.service.ticket.TicketService;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class DepenseService {

    private final DepenseRepository depenseRepository;

    private final BudgetService budgetService;

    public Depense save(Depense depense){
        return depenseRepository.save(depense);
    }

    public List<Depense> findDepenseByCustomerId(Integer customerId){
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

    public List<Depense> findAll() {
        return depenseRepository.findAll();
    }

    public SumChart findSumDepenseOnBudget(Long idBudget){
        return depenseRepository.findSumDepenseOnBudgetId(idBudget).orElse(new SumChart());
    }

    public Double findSumDepenseByCustomerId(Integer customerId){
        return depenseRepository.findSumDepenseByCustomerId(customerId).orElse(0d);
    }

    public Depense findDepense(Long idBudget){
        return depenseRepository.findById(idBudget).orElseThrow(()->new RuntimeException("Id not recognized"));
    }

    public Depense update(Long idDepense,double amount){
        Depense depense=findDepense(idDepense);
        depense.setAmount(amount);
        return this.depenseRepository.save(depense);
    }

    public void delete(Long idDepense){
        Depense depense=findDepense(idDepense);
        this.depenseRepository.delete(depense);
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
