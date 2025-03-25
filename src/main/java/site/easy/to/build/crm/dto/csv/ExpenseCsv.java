package site.easy.to.build.crm.dto.csv;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ExpenseCsv {
    private String customerEmail;
    private String subjectOrName;
    private String type;
    private String status;
    private double expense;
}
