package site.easy.to.build.crm.entity.budget;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import site.easy.to.build.crm.entity.Customer;
import site.easy.to.build.crm.entity.Lead;
import site.easy.to.build.crm.entity.Ticket;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Expense {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idExpense;

    private double amount;

    @ManyToOne(fetch = FetchType.LAZY,cascade = {CascadeType.PERSIST,CascadeType.REMOVE,CascadeType.DETACH})
    @JoinColumn(name = "ticket_id")
    private Ticket ticket;

    @ManyToOne(fetch = FetchType.LAZY,cascade = {CascadeType.PERSIST,CascadeType.REMOVE,CascadeType.DETACH})
    @JoinColumn(name = "lead_id")
    private Lead lead;

    @ManyToOne(fetch = FetchType.LAZY,cascade = {CascadeType.REMOVE,CascadeType.DETACH})
    @JoinColumn(name = "customer_id")
    private Customer customer;
    public Expense(double amount, Ticket ticket,Customer customer){
        this.amount=amount;
        this.ticket=ticket;
        this.customer=customer;
    }

    public Expense(double amount, Lead lead,Customer customer){
        this.amount=amount;
        this.lead=lead;
        this.customer=customer;
    }
}
