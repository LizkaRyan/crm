package site.easy.to.build.crm.controller.data;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import site.easy.to.build.crm.dto.csv.BudgetCustomerExpenseCsv;

@Controller
@RequestMapping("/csv")
public class DataController {
    @GetMapping("/form")
    public String showImportCsv(){
        return "csv/form";
    }

    @PostMapping
    public String importCsv(@ModelAttribute BudgetCustomerExpenseCsv budgetCustomerExpenseCsv){

    }
}
