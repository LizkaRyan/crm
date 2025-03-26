package site.easy.to.build.crm.data;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import site.easy.to.build.crm.dto.csv.BudgetCustomerExpenseCsv;
import site.easy.to.build.crm.service.budget.CsvService;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@SpringBootTest
public class CsvSave {

    @Autowired
    private CsvService csvService;

    @Test
    public void saveCsv(){
        try {
            MultipartFile multipartFileBudget=getCsvFile("C:\\Users\\ryrab\\Desktop\\Ryan\\Etudes\\S6\\Evaluation\\Saison1\\donnee_eval_mars_2025\\java\\budget.csv");
            MultipartFile multipartFileExpense=getCsvFile("C:\\Users\\ryrab\\Desktop\\Ryan\\Etudes\\S6\\Evaluation\\Saison1\\donnee_eval_mars_2025\\java\\expense.csv");
            MultipartFile multipartFileCustomer=getCsvFile("C:\\Users\\ryrab\\Desktop\\Ryan\\Etudes\\S6\\Evaluation\\Saison1\\donnee_eval_mars_2025\\java\\customer.csv");

            BudgetCustomerExpenseCsv budgetCustomerExpenseCsv=new BudgetCustomerExpenseCsv();
            budgetCustomerExpenseCsv.setBudgetFile(multipartFileBudget);
            budgetCustomerExpenseCsv.setSeparatorBudget(",");
            budgetCustomerExpenseCsv.setExpenseFile(multipartFileExpense);
            budgetCustomerExpenseCsv.setSeparatorExpense(",");
            budgetCustomerExpenseCsv.setCustomerFile(multipartFileCustomer);
            budgetCustomerExpenseCsv.setSeparatorCustomer(",");

            csvService.readAndInsert(budgetCustomerExpenseCsv);

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
