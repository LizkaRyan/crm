package site.easy.to.build.crm.service.budget;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import site.easy.to.build.crm.dto.DepenseCause;
import site.easy.to.build.crm.dto.SumChart;
import site.easy.to.build.crm.dto.SumDepenseCustomer;
import site.easy.to.build.crm.dto.csv.ExpenseCsv;
import site.easy.to.build.crm.entity.Customer;
import site.easy.to.build.crm.entity.budget.Expense;
import site.easy.to.build.crm.repository.budget.ExpenseRepository;
import site.easy.to.build.crm.util.csv.exception.CellCSVException;
import site.easy.to.build.crm.util.csv.exception.CsvException;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ExpenseService {

    private final ExpenseRepository expenseRepository;

    private final BudgetService budgetService;

    public Expense save(Expense expense){
        return expenseRepository.save(expense);
    }

    public List<Expense> findDepenseByCustomerId(Integer customerId){
        return expenseRepository.findDepenseByCustomerId(customerId);
    }

    public List<DepenseCause> findDepenseTicket(){
        return expenseRepository.findDepenseTicket();
    }

    public List<DepenseCause> findDepenseLead(){
        return expenseRepository.findDepenseLead();
    }

    public List<DepenseCause> findDepenseCauseByCustomerId(Integer customerId){
        List<DepenseCause> depenseCauses= expenseRepository.findDepenseCauseByCustomerId(customerId);
        return depenseCauses;
    }

    public List<Expense> findAll() {
        return expenseRepository.findAll();
    }

    public SumChart findSumDepense(Long idCustomer){
        return expenseRepository.findSumDepenseByIdCustomer(idCustomer).orElse(new SumChart());
    }

    public Double findSumDepenseByCustomerId(Integer customerId){
        return expenseRepository.findSumDepenseByCustomerId(customerId).orElse(0d);
    }

    public Expense findDepense(Long idBudget){
        return expenseRepository.findById(idBudget).orElseThrow(()->new RuntimeException("Id not recognized"));
    }

    public Expense update(Long idDepense, double amount){
        Expense expense =findDepense(idDepense);
        expense.setAmount(amount);
        return this.expenseRepository.save(expense);
    }

    public void delete(Long idDepense){
        Expense expense =findDepense(idDepense);
        this.expenseRepository.delete(expense);
    }

    public Double findSumDepenseTicket(){
        return this.expenseRepository.findSumTicket().orElse(0d);
    }

    public Double findSumDepenseLead(){
        return this.expenseRepository.findSumLead().orElse(0d);
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
        double depense=this.expenseRepository.findSumDepense().orElse(0d);
        List<SumChart> sumDepenses=new ArrayList<>();
        sumDepenses.add(new SumChart(budget,"Budget"));
        sumDepenses.add(new SumChart(depense,"Depense"));
        return sumDepenses;
    }

    public List<SumDepenseCustomer> findSumDepenseEachCustomer(){
        return this.expenseRepository.findSumDepenseEachCustomer();
    }

    public List<DepenseCause> findDepenseTicketByCustomerId(Integer customerId){
        return this.expenseRepository.findDepenseTicketByCustomerId(customerId);
    }

    public List<DepenseCause> findDepenseLeadByCustomerId(Integer customerId){
        return this.expenseRepository.findDepenseLeadByCustomerId(customerId);
    }

    public List<SumDepenseCustomer> findSumDepenseTicketEachCustomer(){
        return this.expenseRepository.findSumDepenseTicketEachCustomer();
    }

    public List<SumDepenseCustomer> findSumDepenseLeadEachCustomer(){
        return this.expenseRepository.findSumDepenseLeadEachCustomer();
    }

    public List<Expense> createExpense(List<ExpenseCsv> expensesCsv, List<Customer> customers) throws CsvException {
        List<Expense> expenses=new ArrayList<>();
        List<String> errors=new ArrayList<>();
        int i=1;
        for (ExpenseCsv expenseCsv:expensesCsv){
            try{
                expenses.add(expenseCsv.createExpense(customers,i));
            }
            catch (CellCSVException ex){
                errors.add(ex.getMessage());
            }
            i++;
        }
        if(!errors.isEmpty()){
            throw new CsvException(errors);
        }
        return expenses;
    }

    public List<Expense> saveAll(List<Expense> expenses) {
        return expenseRepository.saveAll(expenses);
    }
}
