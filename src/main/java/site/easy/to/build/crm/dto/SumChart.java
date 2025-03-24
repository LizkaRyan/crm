package site.easy.to.build.crm.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SumChart {
    Double sum;
    String budgetName;

    public SumChart(Double sum, String budgetName){
        this.sum=sum;
        this.budgetName=budgetName;
    }

    public SumChart(){
        this.sum=0d;
    }
}
