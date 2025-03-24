package site.easy.to.build.crm.dto;

import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import site.easy.to.build.crm.customValidations.budget.SumDepense;

@Getter
@Setter
@SumDepense
@NoArgsConstructor
public class DepenseDTO {
    private Integer customerId;

    private Long idBudget;

    @Min(value = 1)
    private double amount;

    public DepenseDTO(Integer customerId){
        this.customerId=customerId;
    }
}
