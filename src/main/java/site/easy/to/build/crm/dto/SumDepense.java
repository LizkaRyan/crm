package site.easy.to.build.crm.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SumDepense {
    Double sum;
    String budgetName;

    public SumDepense(Double sum,String budgetName){
        this.sum=sum;
        this.budgetName=budgetName;
    }

    public SumDepense(){
        this.sum=0d;
    }
}
