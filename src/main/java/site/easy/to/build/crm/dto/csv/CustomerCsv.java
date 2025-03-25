package site.easy.to.build.crm.dto.csv;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CustomerCsv {
    private String customerEmail;
    private String customerName;
}
