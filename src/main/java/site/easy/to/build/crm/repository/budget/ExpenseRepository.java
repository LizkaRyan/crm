package site.easy.to.build.crm.repository.budget;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import site.easy.to.build.crm.dto.DepenseCause;
import site.easy.to.build.crm.dto.SumChart;
import site.easy.to.build.crm.dto.SumDepenseCustomer;
import site.easy.to.build.crm.entity.budget.Expense;

import java.util.List;
import java.util.Optional;

public interface ExpenseRepository extends JpaRepository<Expense,Long> {

    @Query("select d from Expense d where d.customer.customerId = :idCustomer")
    List<Expense> findDepenseByCustomerId(@Param("idCustomer")Integer idCustomer);

    @Query("select new site.easy.to.build.crm.dto.SumChart(sum(d.amount),d.customer.name) from Expense d where d.customer.customerId = :idCustomer group by d.customer.name,d.customer.customerId")
    Optional<SumChart> findSumDepenseByIdCustomer(@Param("idCustomer")Long idCustomer);

    @Query("""
            select new site.easy.to.build.crm.dto.SumDepenseCustomer(sum(d.amount),d.customer.name,d.customer.customerId)
            from Expense d 
            group by 
            d.customer.name,d.customer.customerId
            """)
    List<SumDepenseCustomer> findSumDepenseEachCustomer();

    @Query("""
            select new site.easy.to.build.crm.dto.SumDepenseCustomer(sum(d.amount),d.customer.name,d.customer.customerId)
            from Expense d
            where
            not d.ticket is null
            group by
            d.customer.name,d.customer.customerId
            """)
    List<SumDepenseCustomer> findSumDepenseTicketEachCustomer();

    @Query("""
            select new site.easy.to.build.crm.dto.SumDepenseCustomer(sum(d.amount),d.customer.name,d.customer.customerId)
            from Expense d
            where
            not d.lead is null
            group by
            d.customer.name,d.customer.customerId
            """)
    List<SumDepenseCustomer> findSumDepenseLeadEachCustomer();

    @Query("""
            select new site.easy.to.build.crm.dto.DepenseCause(d.idExpense,d.amount,t,l,d.customer.name)
            from Expense d
            left join d.lead l
            left join d.ticket t
            where not d.ticket is null and d.customer.customerId = :customerId
            """)
    List<DepenseCause> findDepenseTicketByCustomerId(@Param("customerId")Integer customerId);

    @Query("""
            select new site.easy.to.build.crm.dto.DepenseCause(d.idExpense,d.amount,t,l,d.customer.name)
            from Expense d
            left join d.lead l
            left join d.ticket t
            where not d.ticket is null
            """)
    List<DepenseCause> findDepenseTicket();

    @Query("""
            select new site.easy.to.build.crm.dto.DepenseCause(d.idExpense,d.amount,t,l,d.customer.name)
            from Expense d
            left join d.lead l
            left join d.ticket t
            where not d.lead is null
            """)
    List<DepenseCause> findDepenseLead();

    @Query("""
            select new site.easy.to.build.crm.dto.DepenseCause(d.idExpense,d.amount,t,l,d.customer.name)
            from Expense d
            left join d.lead l
            left join d.ticket t
            where not d.lead is null and d.customer.customerId = :customerId
            """)
    List<DepenseCause> findDepenseLeadByCustomerId(@Param("customerId")Integer customerId);

    @Query("""
            select new site.easy.to.build.crm.dto.DepenseCause(d.idExpense,d.amount,t,l,d.customer.name) 
            from Expense d
            left join d.lead l
            left join d.ticket t
            where d.customer.customerId = :customerId
            """)
    List<DepenseCause> findDepenseCauseByCustomerId(@Param("customerId")Integer customerId);

    @Query("""
            select sum(d.amount)
            from Expense d
            where not d.ticket is null
            """)
    Optional<Double> findSumTicket();

    @Query("""
            select sum(d.amount)
            from Expense d
            where not d.lead is null
            """)
    Optional<Double> findSumLead();

    @Query("select sum(d.amount) from Expense d")
    Optional<Double> findSumDepense();

    @Query("""
            select sum(d.amount) from Expense d
            left join d.ticket t
            left join d.lead l
            where 
            l.customer.customerId = :customerId
            or
            t.customer.customerId = :customerId
            """)
    Optional<Double> findSumDepenseByCustomerId(@Param("customerId") Integer customerId);
}
