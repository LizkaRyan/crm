package site.easy.to.build.crm.entity.budget;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class SeuilBudget {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idSeuilBudget;

    private double tauxSeuil;

    private LocalDateTime dateSeuil;

    public SeuilBudget(double tauxSeuil){
        this.tauxSeuil=tauxSeuil;
        this.dateSeuil=LocalDateTime.now();
    }
}
