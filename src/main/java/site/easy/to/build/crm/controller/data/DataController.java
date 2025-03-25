package site.easy.to.build.crm.controller.data;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import site.easy.to.build.crm.dto.csv.BudgetCustomerExpenseCsv;
import site.easy.to.build.crm.service.budget.CsvService;
import site.easy.to.build.crm.util.csv.exception.CsvException;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/csv")
public class DataController {

    private CsvService csvService;

    @GetMapping("/form")
    public String showImportCsv(){
        return "csv/form";
    }

    @PostMapping
    public String importCsv(@ModelAttribute BudgetCustomerExpenseCsv budgetCustomerExpenseCsv, Model model){
        try{
            csvService.readAndInsert(budgetCustomerExpenseCsv);
        }
        catch (CsvException csvException){
            List<String> errors=csvException.getErrors();
            model.addAttribute("errors",errors);
            return "error/message-errors";
        }
        return "redirect:/csv/form";
    }
}
