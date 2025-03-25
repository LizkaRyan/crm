package site.easy.to.build.crm.data;

import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import site.easy.to.build.crm.dto.ReservationDTO;
import site.easy.to.build.crm.dto.csv.CustomerCsv;
import site.easy.to.build.crm.dto.csv.ExpenseCsv;
import site.easy.to.build.crm.util.csv.CSVFile;
import site.easy.to.build.crm.util.csv.ConstraintCSV;

import java.io.File;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class DataTest {

    @Test
    void importCSV()throws Exception{
        try {
            MultipartFile multipartFile=getCsvFile("C:\\Users\\ryrab\\Desktop\\Ryan\\Etudes\\S6\\Evaluation\\Saison1\\crm\\src\\main\\resources\\test.csv");

            // Exemple d'utilisation
            CSVFile<ReservationDTO> csvFile = new CSVFile<>(multipartFile,";");
            csvFile.addConstraint("duree", ConstraintCSV.INT_POSITIVE)
                    .addConstraint("date",ConstraintCSV.LOCALDATE)
                    .addConstraint("heure_debut",ConstraintCSV.LOCAL_TIME)
                    .addConstraint("option",ConstraintCSV.LIST_FOREIGN);
            csvFile.readAndTransform(v->
                new ReservationDTO(
                (String)v.get("ref"),
                (String)v.get("espace"),
                (String)v.get("client"),
                (LocalDate)v.get("date"),
                (LocalTime)v.get("heure_debut"),
                (int)v.get("duree"),
                (List<String>)v.get("option"))
            );

            System.out.println("VITA");

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void importExpenseCsv(){
        try {
            MultipartFile multipartFile= getCsvFile("C:\\Users\\ryrab\\Desktop\\Ryan\\Etudes\\S6\\Evaluation\\Saison1\\crm\\src\\main\\resources\\expense.csv");
            // Exemple d'utilisation
            CSVFile<ExpenseCsv> csvFile = new CSVFile<>(multipartFile,";");
            csvFile.addConstraint("type", ConstraintCSV.TYPE_CONSTRAINT)
                    .addConstraint("status",ConstraintCSV.STATUS_CONSTRAINT)
                    .addConstraint("expense",ConstraintCSV.DOUBLE_POSITIVE);

            csvFile.readAndTransform(v->
                    new ExpenseCsv(
                            (String)v.get("customer_email"),
                            (String)v.get("subject_or_name"),
                            (String)v.get("type"),
                            (String)v.get("status"),
                            (Double)v.get("expense")
                    )
            );

            System.out.println("VITA");

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void importCustomerCsv(){
        try {
            MultipartFile multipartFile= getCsvFile("C:\\Users\\ryrab\\Desktop\\Ryan\\Etudes\\S6\\Evaluation\\Saison1\\crm\\src\\main\\resources\\Feuille-3.csv");

            // Exemple d'utilisation
            CSVFile<CustomerCsv> csvFile = new CSVFile<>(multipartFile,",");

            csvFile.readAndTransform(v->
                    new CustomerCsv(
                            (String)v.get("customer_email"),
                            (String)v.get("customer_name")
                    )
            );

            System.out.println("VITA");

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private MultipartFile getCsvFile(String filePath) throws IOException {
        File file = new File(filePath);
        FileInputStream fileInputStream = new FileInputStream(file);

        // Créer un MultipartFile à partir du FileInputStream
        return new MockMultipartFile(
                "file",                             // Nom du fichier
                file.getName(),                     // Nom du fichier
                "text/csv",                         // Type MIME
                fileInputStream                     // Le contenu du fichier
        );
    }
}
