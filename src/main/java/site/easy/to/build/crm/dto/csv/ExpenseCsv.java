package site.easy.to.build.crm.dto.csv;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import site.easy.to.build.crm.entity.Customer;
import site.easy.to.build.crm.entity.Lead;
import site.easy.to.build.crm.entity.Ticket;
import site.easy.to.build.crm.entity.budget.Expense;
import site.easy.to.build.crm.util.csv.exception.CellCSVException;
import site.easy.to.build.crm.util.csv.exception.CsvException;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class ExpenseCsv {
    private String customerEmail;
    private String subjectOrName;
    private String type;
    private String status;
    private double expense;

    private Customer customer;

    public ExpenseCsv(String customerEmail,String subjectOrName,String type,String status,double expense){
        this.customerEmail=customerEmail;
        this.subjectOrName=subjectOrName;
        this.type=type;
        this.status=status;
        this.expense=expense;
    }

    public void setCustomer(List<Customer> customers,int line) throws CellCSVException {
        for (Customer customer:customers){
            if(customer.getEmail().equals(this.customerEmail)){
                this.customer=customer;
                break;
            }
        }
        if(this.customer==null){
            throw new CellCSVException("Customer email :"+this.customerEmail);
        }
    }

    public Expense createExpense(List<Customer> customers,int line) throws CellCSVException {
        Expense expense=new Expense();
        expense.setAmount(this.getExpense());
        this.setCustomer(customers,line);
        expense.setCustomer(customer);
        if(type.equals("lead")){
            expense.setLead(this.createLead(this.customer));
        }
        else{
            expense.setTicket(this.createTicket(this.customer));
        }
        return expense;
    }

    private Ticket createTicket(Customer customer) {
        Ticket ticket=new Ticket();
        ticket.setCustomer(customer);
        ticket.setSubject(this.getSubjectOrName());
        ticket.setStatus(this.getStatus());
        ticket.setPriority("high");
        return ticket;
    }

    private Lead createLead(Customer customer) {
        Lead lead=new Lead();
        lead.setName(this.getSubjectOrName());
        lead.setCustomer(customer);
        lead.setStatus(this.getStatus());
        return lead;
    }
}
