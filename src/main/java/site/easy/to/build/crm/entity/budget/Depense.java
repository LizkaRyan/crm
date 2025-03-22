package site.easy.to.build.crm.entity.budget;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import site.easy.to.build.crm.entity.Lead;
import site.easy.to.build.crm.entity.Ticket;

@Entity
@NoArgsConstructor
public class Depense {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDepense;

    private double amount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ticket_id")
    private Ticket ticket;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lead_id")
    private Lead lead;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_budget")
    private Budget budget;

    public Depense(double amount,Budget budget,Ticket ticket){
        this.amount=amount;
        this.budget=budget;
        this.ticket=ticket;
    }
}
