package site.easy.to.build.crm.dto.csv.reader;

import org.springframework.web.multipart.MultipartFile;
import site.easy.to.build.crm.dto.csv.CustomerCsv;
import site.easy.to.build.crm.util.csv.CSVFile;

public class CustomerCsvReader extends CSVFile<CustomerCsv> {
    public CustomerCsvReader(MultipartFile multipartFile, String separation) {
        super(multipartFile, separation);
    }

    @Override
    public void read(){
        this.readAndTransform(v->
                new CustomerCsv(
                        (String)v.get("customer_email"),
                        (String)v.get("customer_name")
                )
        );
    }
}
