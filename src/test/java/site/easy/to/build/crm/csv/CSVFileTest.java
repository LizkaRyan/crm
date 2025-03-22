package site.easy.to.build.crm.csv;

import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import site.easy.to.build.crm.dto.Employee;
import site.easy.to.build.crm.util.csv.CSVFile;
import site.easy.to.build.crm.util.csv.ConstraintCSV;

import java.io.File;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public class CSVFileTest {

    @Test
    void importCSV()throws Exception{
        // Chemin du fichier réel
        String filePath = "C:\\Users\\ryrab\\Desktop\\Ryan\\Etudes\\S6\\Evaluation\\Saison1\\crm\\src\\main\\resources\\employee.csv";

        try {
            // Charger le fichier en tant que FileInputStream
            File file = new File(filePath);
            FileInputStream fileInputStream = new FileInputStream(file);

            // Créer un MultipartFile à partir du FileInputStream
            MultipartFile multipartFile = new MockMultipartFile(
                    "file",                             // Nom du fichier
                    file.getName(),                     // Nom du fichier
                    "text/csv",                         // Type MIME
                    fileInputStream                     // Le contenu du fichier
            );

            // Exemple d'utilisation
            CSVFile<Employee> csvFile = new CSVFile<Employee>(multipartFile,",");
            csvFile.addConstraint("id", ConstraintCSV.INTPOSITIVE);
            csvFile.readAndTransform(v->
                new Employee(
                (int)v.get("id"),
                (String)v.get("username"),
                (String)v.get("first_name"),
                (String)v.get("last_name"),
                (String)v.get("password"),
                (String)v.get("provider"))
            );

            System.out.println("VITA");

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
