package site.easy.to.build.crm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import site.easy.to.build.crm.util.csv.CSVFile;

@Controller
@RequestMapping("/csv")
public class CSVController {
    @GetMapping("/form")
    public String showImportUsersForm(){
        return "csv/form";
    }
}
