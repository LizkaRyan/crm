package site.easy.to.build.crm.dto;

import lombok.Getter;
import lombok.Setter;
import site.easy.to.build.crm.entity.Lead;
import site.easy.to.build.crm.entity.Ticket;

@Getter
@Setter
public class DepenseCause {
    private Long idDepense;
    private double amount;
    private String cause;
    private String customerName;

    public DepenseCause(Long idDepense, double amount, Ticket ticket, Lead lead,String customerName){
        this.idDepense=idDepense;
        if(ticket!=null){
            this.cause="Ticket: "+ticket.getSubject();
        }
        else{
            this.cause="Lead: "+lead.getName();
        }
        this.customerName=customerName;
        this.amount=amount;
    }
}
