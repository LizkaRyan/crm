package site.easy.to.build.crm.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SumDepenseCustomer {
    private Double sum;
    private String customerName;
    private Integer customerId;

    public SumDepenseCustomer(Double sum,String customerName,Integer customerId){
        this.sum=sum;
        this.customerName=customerName;
        this.customerId=customerId;
    }
}
