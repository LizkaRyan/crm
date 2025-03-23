package site.easy.to.build.crm.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SumDepenseCustomer {
    private Double sum;
    private String customerName;
    private Integer customerId;
}
