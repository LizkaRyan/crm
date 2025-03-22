package site.easy.to.build.crm.controller.budget;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import site.easy.to.build.crm.entity.Customer;
import site.easy.to.build.crm.entity.CustomerLoginInfo;
import site.easy.to.build.crm.entity.budget.Budget;
import site.easy.to.build.crm.service.budget.BudgetService;
import site.easy.to.build.crm.service.customer.CustomerLoginInfoService;
import site.easy.to.build.crm.service.customer.CustomerService;
import org.springframework.security.core.Authentication;
import site.easy.to.build.crm.util.AuthenticationUtils;

@Controller
@AllArgsConstructor
@RequestMapping("/budget")
public class BudgetController {

    private final BudgetService budgetService;

    private final CustomerService customerService;

    private final CustomerLoginInfoService customerLoginInfoService;

    private final AuthenticationUtils authenticationUtils;

    @GetMapping
    public String list(Model model){
        model.addAttribute("budgets",budgetService.findAll());
        return "budget/all-budgets";
    }

    @GetMapping("/my-budgets")
    public String list(Model model,Authentication authentication){
        int customerId = authenticationUtils.getLoggedInUserId(authentication);
        CustomerLoginInfo customerLoginInfo = customerLoginInfoService.findById(customerId);
        Customer customer = customerService.findByEmail(customerLoginInfo.getEmail());
        model.addAttribute("budgets",budgetService.findByCustomerId(customer.getCustomerId()));
        return "budget/all-budgets";
    }

    @PostMapping
    public String insert(@ModelAttribute Budget budget){
        budgetService.save(budget);
        return "redirect:/budget/form";
    }

    @GetMapping("/form")
    public String form(Model model){
        model.addAttribute("customers",customerService.findAll());
        model.addAttribute("budget",new Budget());
        return "budget/form";
    }
}
