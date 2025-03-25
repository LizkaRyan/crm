package site.easy.to.build.crm.dto.csv.reader;

import org.springframework.web.multipart.MultipartFile;
import site.easy.to.build.crm.dto.csv.ExpenseCsv;
import site.easy.to.build.crm.util.csv.CSVFile;
import site.easy.to.build.crm.util.csv.ConstraintCSV;

public class ExpenseCsvReader extends CSVFile<ExpenseCsv> {
    public ExpenseCsvReader(MultipartFile multipartFile, String separation) {
        super(multipartFile, separation);
    }

    @Override
    public void read(){
        this.addConstraint("type", ConstraintCSV.TYPE_CONSTRAINT)
                .addConstraint("status",ConstraintCSV.STATUS_CONSTRAINT)
                .addConstraint("expense",ConstraintCSV.DOUBLE_POSITIVE);

        this.readAndTransform(v->
                new ExpenseCsv(
                        (String)v.get("customer_email"),
                        (String)v.get("subject_or_name"),
                        (String)v.get("type"),
                        (String)v.get("status"),
                        (Double)v.get("expense")
                )
        );
    }
}
