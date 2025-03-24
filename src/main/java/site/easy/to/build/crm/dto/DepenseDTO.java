package site.easy.to.build.crm.dto;

import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;
import site.easy.to.build.crm.customValidations.budget.SumDepense;

@Getter
@Setter
@SumDepense
public class DepenseDTO {
    private Long idBudget;

    @Min(value = 1)
    private double amount;
}
