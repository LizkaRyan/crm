package site.easy.to.build.crm.controller.data;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/csv")
public class DataController {
    @GetMapping("/form")
    public String showImportUsersForm(){
        return "csv/form";
    }
}
