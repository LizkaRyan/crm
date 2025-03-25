package site.easy.to.build.crm.dto.csv;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
import site.easy.to.build.crm.util.csv.CSVFile;
import site.easy.to.build.crm.util.csv.ConstraintCSV;

import java.io.IOException;

@Getter
@Setter
@AllArgsConstructor
public class BudgetCsv {
    private String customerEmail;
    private double budget;
}
