package site.easy.to.build.crm.controller.budget;

import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import site.easy.to.build.crm.dto.DepenseDTO;
import site.easy.to.build.crm.dto.SumChart;
import site.easy.to.build.crm.entity.Customer;
import site.easy.to.build.crm.entity.CustomerLoginInfo;
import site.easy.to.build.crm.entity.Lead;
import site.easy.to.build.crm.entity.Ticket;
import site.easy.to.build.crm.entity.budget.Budget;
import site.easy.to.build.crm.entity.budget.Depense;
import site.easy.to.build.crm.google.model.gmail.Attachment;
import site.easy.to.build.crm.service.budget.BudgetService;
import site.easy.to.build.crm.service.budget.DepenseService;
import site.easy.to.build.crm.service.budget.SeuilBudgetService;
import site.easy.to.build.crm.service.customer.CustomerLoginInfoService;
import site.easy.to.build.crm.service.customer.CustomerService;
import site.easy.to.build.crm.service.lead.LeadService;
import site.easy.to.build.crm.service.ticket.TicketService;
import site.easy.to.build.crm.util.AuthenticationUtils;
import site.easy.to.build.crm.util.FileUtil;

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

    private final SeuilBudgetService seuilBudgetService;
    private final LeadService leadService;
    private final FileUtil fileUtil;

    @GetMapping("/my-depenses")
    public String getDepensesByIdCustomer(Model model, Authentication authentication){
        int customerId = authenticationUtils.getLoggedInUserId(authentication);
        CustomerLoginInfo customerLoginInfo = customerLoginInfoService.findById(customerId);
        Customer customer = customerService.findByEmail(customerLoginInfo.getEmail());
        model.addAttribute("depenses",depenseService.findDepenseByCustomerId(customer.getCustomerId()));
        return "depense/all-depense";
    }

    @GetMapping("/form/ticket")
    public String formTicket(Model model, HttpSession session){
        Ticket ticket=(Ticket)session.getAttribute("ticket");
        if(ticket==null){
            return "error/500";
        }
        model.addAttribute("redirection","/depense/ticket");
        model.addAttribute("depense",new DepenseDTO());
        model.addAttribute("budgets",budgetService.findByCustomerId(ticket.getCustomer().getCustomerId()));
        return "depense/form";
    }

    @GetMapping("/form/lead")
    public String formLead(Model model, HttpSession session){
        Lead lead=(Lead)session.getAttribute("lead");
        if(lead==null){
            return "error/500";
        }
        model.addAttribute("redirection","/depense/lead");
        model.addAttribute("depense",new DepenseDTO());
        model.addAttribute("budgets",budgetService.findByCustomerId(lead.getCustomer().getCustomerId()));
        return "depense/form";
    }

    @PostMapping("/ticket")
    public String insertDepenseAndTicket(HttpSession session, @ModelAttribute DepenseDTO depenseDTO){
        Ticket ticket=(Ticket)session.getAttribute("ticket");
        if(ticket==null || depenseDTO==null){
            return "error/500";
        }
        Budget budget=budgetService.findById(depenseDTO.getIdBudget());
        SumChart sumDepense=depenseService.findSumDepenseOnBudget(depenseDTO.getIdBudget());
        if(budget.getBudget()<sumDepense.getSum()+depenseDTO.getAmount()){
            return "montant invalide";
        }
        if((budget.getBudget()*seuilBudgetService.findActualSeuilBudget().getTauxSeuil())/100<sumDepense.getSum()+depenseDTO.getAmount()){
            session.setAttribute("depense",depenseDTO);
            return "redirect:/depense/alert/ticket";
        }
        ticket=ticketService.save(ticket);
        Depense depense=new Depense(depenseDTO.getAmount(),budget,ticket);
        depenseService.save(depense);
        return "redirect:/depense";
    }

    @PostMapping("/lead")
    public String insertDepenseAndLead(HttpSession session, @ModelAttribute DepenseDTO depenseDTO,Authentication authentication){
        Lead lead=(Lead)session.getAttribute("lead");
        if(lead==null || depenseDTO==null){
            return "error/500";
        }
        Budget budget=budgetService.findById(depenseDTO.getIdBudget());
        SumChart sumDepense=depenseService.findSumDepenseOnBudget(depenseDTO.getIdBudget());
        if(budget.getBudget()<sumDepense.getSum()+depenseDTO.getAmount()){
            return "montant invalide";
        }
        if((budget.getBudget()*seuilBudgetService.findActualSeuilBudget().getTauxSeuil())/100<sumDepense.getSum()+depenseDTO.getAmount()){
            session.setAttribute("depense",depenseDTO);
            return "redirect:/depense/alert/lead";
        }
        List<Attachment> allFiles=(List<Attachment>)session.getAttribute("allFiles");
        String folderId=(String) session.getAttribute("folderId");
        String redirect=(String) session.getAttribute("redirect");

        Lead createdLead = leadService.save(lead);
        fileUtil.saveFiles(allFiles, createdLead);

        if (lead.getGoogleDrive() != null) {
            fileUtil.saveGoogleDriveFiles(authentication, allFiles, folderId, createdLead);
        }

        Depense depense=new Depense(depenseDTO.getAmount(),budgetService.findById(depenseDTO.getIdBudget()),createdLead);
        depenseService.save(depense);

        session.setAttribute("lead",null);
        session.setAttribute("allFiles",null);
        session.setAttribute("folderId",null);
        session.setAttribute("redirect",null);

        return redirect;
    }

    @GetMapping("/confirm/ticket")
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
        return "redirect:/depense";
    }

    @GetMapping("/confirm/lead")
    public String confirmDepenseAndLead(HttpSession session,Authentication authentication){
        Lead lead=(Lead)session.getAttribute("lead");
        List<Attachment> allFiles=(List<Attachment>)session.getAttribute("allFiles");
        String folderId=(String) session.getAttribute("folderId");
        DepenseDTO depenseDTO=(DepenseDTO)session.getAttribute("depense");

        Lead createdLead = leadService.save(lead);
        fileUtil.saveFiles(allFiles, createdLead);

        if (lead.getGoogleDrive() != null) {
            fileUtil.saveGoogleDriveFiles(authentication, allFiles, folderId, createdLead);
        }

        Depense depense=new Depense(depenseDTO.getAmount(),budgetService.findById(depenseDTO.getIdBudget()),createdLead);
        depenseService.save(depense);

        session.setAttribute("lead",null);
        session.setAttribute("allFiles",null);
        session.setAttribute("folderId",null);
        session.setAttribute("redirect",null);

        return "redirect:/depense";
    }

    @GetMapping("/reject/ticket")
    public String reject(HttpSession session){
        session.setAttribute("depense",null);
        session.setAttribute("ticket",null);
        return "redirect:/depense/my-depenses";
    }

    @GetMapping
    public String getAll(Model model){
        model.addAttribute("depenses",depenseService.findAll());
        return "depense/all-depense";
    }

    @GetMapping("/alert/ticket")
    public String alertDepense(Model model){
        model.addAttribute("confirmUrl","/depense/confirm/ticket");
        model.addAttribute("rejectUrl","/depense/reject/ticket");
        return "depense/alert";
    }

    @GetMapping("/alert/lead")
    public String alertLead(Model model){
        model.addAttribute("confirmUrl","/depense/confirm/lead");
        model.addAttribute("rejectUrl","/depense/reject/lead");
        return "depense/alert";
    }
}
