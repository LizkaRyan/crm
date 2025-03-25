package site.easy.to.build.crm.exception;

import lombok.Getter;
import site.easy.to.build.crm.entity.budget.Expense;

@Getter
public class SeuilDepasseException extends Exception{

    private Expense expense;

    public SeuilDepasseException(Expense expense){
        super("Seuil dépassé");
        this.expense=expense;
    }
}
