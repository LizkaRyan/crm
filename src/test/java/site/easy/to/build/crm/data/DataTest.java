package site.easy.to.build.crm.data;

import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import site.easy.to.build.crm.dto.ReservationDTO;
import site.easy.to.build.crm.dto.csv.BudgetCsv;
import site.easy.to.build.crm.dto.csv.BudgetCustomerExpenseCsv;
import site.easy.to.build.crm.dto.csv.CustomerCsv;
import site.easy.to.build.crm.dto.csv.ExpenseCsv;
import site.easy.to.build.crm.dto.csv.reader.BudgetCsvReader;
import site.easy.to.build.crm.dto.csv.reader.CustomerCsvReader;
import site.easy.to.build.crm.dto.csv.reader.ExpenseCsvReader;
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
            MultipartFile multipartFileBudget=getCsvFile("C:\\Users\\ryrab\\Desktop\\Ryan\\Etudes\\S6\\Evaluation\\Saison1\\crm\\src\\main\\resources\\csv\\Feuille-4.csv");
            MultipartFile multipartFileExpense=getCsvFile("C:\\Users\\ryrab\\Desktop\\Ryan\\Etudes\\S6\\Evaluation\\Saison1\\crm\\src\\main\\resources\\csv\\expense.csv");
            MultipartFile multipartFileCustomer=getCsvFile("C:\\Users\\ryrab\\Desktop\\Ryan\\Etudes\\S6\\Evaluation\\Saison1\\crm\\src\\main\\resources\\csv\\Feuille-3.csv");

            BudgetCustomerExpenseCsv budgetCustomerExpenseCsv=new BudgetCustomerExpenseCsv();
            budgetCustomerExpenseCsv.setBudgetFile(multipartFileBudget);
            budgetCustomerExpenseCsv.setSeparatorBudget(",");
            budgetCustomerExpenseCsv.setExpenseFile(multipartFileExpense);
            budgetCustomerExpenseCsv.setSeparatorExpense(";");
            budgetCustomerExpenseCsv.setCustomerFile(multipartFileCustomer);
            budgetCustomerExpenseCsv.setSeparatorCustomer(",");

            budgetCustomerExpenseCsv.read();


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
