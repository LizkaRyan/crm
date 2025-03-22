package site.easy.to.build.crm.controller.budget;

import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import site.easy.to.build.crm.dto.DepenseDTO;
import site.easy.to.build.crm.dto.SumDepense;
import site.easy.to.build.crm.entity.Customer;
import site.easy.to.build.crm.entity.CustomerLoginInfo;
import site.easy.to.build.crm.entity.Ticket;
import site.easy.to.build.crm.entity.budget.Budget;
import site.easy.to.build.crm.entity.budget.Depense;
import site.easy.to.build.crm.service.budget.BudgetService;
import site.easy.to.build.crm.service.budget.DepenseService;
import site.easy.to.build.crm.service.customer.CustomerLoginInfoService;
import site.easy.to.build.crm.service.customer.CustomerService;
import site.easy.to.build.crm.service.ticket.TicketService;
import site.easy.to.build.crm.util.AuthenticationUtils;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/depense")
public class DepenseController {
    private final DepenseService depenseService;

    private final BudgetService budgetService;

    private final CustomerService customerService;

    private final CustomerLoginInfoService customerLoginInfoService;

    private final AuthenticationUtils authenticationUtils;

    private final TicketService ticketService;

    @GetMapping("/my-depenses")
    public String getDepensesByIdCustomer(Model model, Authentication authentication){
        int customerId = authenticationUtils.getLoggedInUserId(authentication);
        CustomerLoginInfo customerLoginInfo = customerLoginInfoService.findById(customerId);
        Customer customer = customerService.findByEmail(customerLoginInfo.getEmail());
        model.addAttribute("depenses",depenseService.findDepenseByCustomerId(customer.getCustomerId()));
        return "depense/all-depense";
    }

    @GetMapping("/form")
    public String form(Model model, HttpSession session){
        Ticket ticket=(Ticket)session.getAttribute("ticket");
        if(ticket==null){
            return "error/500";
        }
        model.addAttribute("budgets",budgetService.findByCustomerId(ticket.getCustomer().getCustomerId()));
        return "depense/all-depense";
    }

    @PostMapping
    public String insertDepenseAndTicket(HttpSession session, @ModelAttribute DepenseDTO depenseDTO){
        Ticket ticket=(Ticket)session.getAttribute("ticket");
        if(ticket==null || depenseDTO==null){
            return "error/500";
        }
        Budget budget=budgetService.findById(depenseDTO.getIdBudget());
        SumDepense sumDepense=depenseService.findSumDepenseOnBudget(depenseDTO.getIdBudget());
        if(budget.getBudget()<sumDepense.getSum()+depenseDTO.getAmount()){
            return "montant invalide";
        }
        if((budget.getBudget()*budget.getTauxSeuil())/100<sumDepense.getSum()+depenseDTO.getAmount()){
            session.setAttribute("depense",depenseDTO);
            return "depense/alert";
        }
        ticket=ticketService.save(ticket);
        Depense depense=new Depense(depenseDTO.getAmount(),budget,ticket);
        depenseService.save(depense);
        return "redirect:/depense/my-depenses";
    }

    @PostMapping("/confirmed")
    public String confirmDepenseAndTicket(HttpSession session){
        DepenseDTO depenseDTO=(DepenseDTO)session.getAttribute("depense");
        Ticket ticket=(Ticket)session.getAttribute("ticket");
        if(ticket==null || depenseDTO==null){
            return "error/500";
        }
        ticket=ticketService.save(ticket);
        Budget budget=budgetService.findById(depenseDTO.getIdBudget());
        Depense depense=new Depense(depenseDTO.getAmount(),budget,ticket);
        depenseService.save(depense);
        return "redirect:/depense/my-depenses";
    }

    @GetMapping
    public String getAll(Model model){
        model.addAttribute("depenses",depenseService.findAll());
        return "depense/all-depense";
    }
}
