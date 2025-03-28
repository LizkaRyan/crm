package site.easy.to.build.crm.dto;

import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import site.easy.to.build.crm.customValidations.budget.SumDepense;

@Getter
@Setter
@NoArgsConstructor
public class ExpenseDTO {
    private Integer customerId;

    @Min(value = 1)
    private double amount;

    public ExpenseDTO(Integer customerId){
        this.customerId=customerId;
    }
}
