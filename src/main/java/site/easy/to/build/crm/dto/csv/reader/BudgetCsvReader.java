package site.easy.to.build.crm.dto.csv.reader;

import org.springframework.web.multipart.MultipartFile;
import site.easy.to.build.crm.dto.csv.BudgetCsv;
import site.easy.to.build.crm.util.csv.CSVFile;
import site.easy.to.build.crm.util.csv.ConstraintCSV;

public class BudgetCsvReader extends CSVFile<BudgetCsv> {
    public BudgetCsvReader(MultipartFile multipartFile, String separation) {
        super(multipartFile, separation);
    }

    @Override
    public void read(){
        this.addConstraint("Budget", ConstraintCSV.DOUBLE_POSITIVE);
        this.readAndTransform(v->
                new BudgetCsv(
                        (String)v.get("customer_email"),
                        (Double)v.get("Budget"))
        );
    }
}
