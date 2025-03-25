package site.easy.to.build.crm.dto.csv;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import site.easy.to.build.crm.entity.Customer;
import site.easy.to.build.crm.entity.Lead;
import site.easy.to.build.crm.entity.Ticket;
import site.easy.to.build.crm.entity.budget.Expense;

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

    public void setCustomer(List<Customer> customers){
        for (Customer customer:customers){
            if(customer.getEmail().equals(this.customerEmail)){
                this.customer=customer;
                break;
            }
        }
    }

    public Expense createExpense(List<Customer> customers){
        Expense expense=new Expense();
        expense.setAmount(this.getExpense());
        this.setCustomer(customers);
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
