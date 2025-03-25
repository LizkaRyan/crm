package site.easy.to.build.crm.entity.budget;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import site.easy.to.build.crm.entity.Lead;
import site.easy.to.build.crm.entity.Ticket;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Expense {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDepense;

    private double amount;

    @ManyToOne(fetch = FetchType.LAZY,cascade = {CascadeType.REMOVE,CascadeType.DETACH})
    @JoinColumn(name = "ticket_id")
    private Ticket ticket;

    @ManyToOne(fetch = FetchType.LAZY,cascade = {CascadeType.REMOVE,CascadeType.DETACH})
    @JoinColumn(name = "lead_id")
    private Lead lead;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_budget")
    private Budget budget;

    public Expense(double amount, Budget budget, Ticket ticket){
        this.amount=amount;
        this.budget=budget;
        this.ticket=ticket;
    }

    public Expense(double amount, Budget budget, Lead lead){
        this.amount=amount;
        this.budget=budget;
        this.lead=lead;
    }
}
