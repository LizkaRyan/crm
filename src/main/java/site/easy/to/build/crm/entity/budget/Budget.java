package site.easy.to.build.crm.entity.budget;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import site.easy.to.build.crm.entity.Customer;

@Entity
@Getter
@Setter
public class Budget {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idBudget;

    private String name;

    private double budget;

    private double tauxSeuil;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.PERSIST)
    @JoinColumn(name = "customer_id")
    private Customer customer;
}
