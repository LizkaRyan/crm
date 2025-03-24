package site.easy.to.build.crm.controller.budget;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.easy.to.build.crm.dto.ResponseJSON;
import site.easy.to.build.crm.entity.Customer;
import site.easy.to.build.crm.service.budget.DepenseService;
import site.easy.to.build.crm.service.customer.CustomerService;
import site.easy.to.build.crm.service.lead.LeadService;
import site.easy.to.build.crm.service.ticket.TicketService;
import site.easy.to.build.crm.util.POV;

import java.util.HashMap;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class DashboardRestController {

    private final DepenseService depenseService;

    private final TicketService ticketService;

    private final CustomerService customerService;

    private final LeadService leadService;

    @GetMapping("/dashboard")
    public ResponseJSON<HashMap<String, Object>> getStat(){
        HashMap<String, Object> map=new HashMap<>();
        map.put("countTicket",ticketService.findCountTicket());
        map.put("countLead",leadService.findCountLead());
        map.put("countCustomer",customerService.findCountCustomer());
        map.put("totalDepense",depenseService.findSumDepenseEachCustomer());
        map.put("totalTicketDepense",depenseService.findSumDepenseTicketEachCustomer());
        map.put("totalLeadDepense",depenseService.findSumDepenseLeadEachCustomer());
        map.put("depenseSumGroupBy",this.depenseService.findSumDepenseGroupby());
        map.put("sumDepenseBudget",this.depenseService.findSumBudgetAndDepense());
        return new ResponseJSON<>(200,"Requête réussie",map);
    }

    @GetMapping("/customer")
    @JsonView(POV.Public.class)
    public ResponseJSON<List<Customer>> getCustomers(){
        return new ResponseJSON<>(200,"Requête réussie",this.customerService.findAll());
    }
}
