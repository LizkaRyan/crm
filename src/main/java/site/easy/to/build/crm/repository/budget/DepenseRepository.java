package site.easy.to.build.crm.repository.budget;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import site.easy.to.build.crm.dto.SumDepense;
import site.easy.to.build.crm.dto.SumDepenseCustomer;
import site.easy.to.build.crm.entity.budget.Depense;

import java.util.List;
import java.util.Optional;

public interface DepenseRepository extends JpaRepository<Depense,Long> {

    @Query("select d from Depense d where d.budget.customer.customerId = :idCustomer")
    List<Depense> findDepenseByCustomerId(@Param("idCustomer")Integer idCustomer);

    @Query("select new site.easy.to.build.crm.dto.SumDepense(sum(d.amount),d.budget.name) from Depense d where d.budget.idBudget = :idBudget group by d.budget.name,d.budget.idBudget")
    Optional<SumDepense> findSumDepenseOnBudgetId(@Param("idBudget")Long idBudget);

    @Query("""
            select new site.easy.to.build.crm.dto.SumDepenseCustomer(sum(d.amount),d.budget.customer.name,d.budget.customer.customerId) 
            from Depense d 
            group by 
            d.budget.customer.name,d.budget.customer.customerId
            """)
    List<SumDepenseCustomer> findSumDepenseEachCustomer();

    @Query("""
            select new site.easy.to.build.crm.dto.SumDepenseCustomer(sum(d.amount),d.budget.customer.name,d.budget.customer.customerId)
            from Depense d
            where
            not d.ticket is null
            group by
            d.budget.customer.name,d.budget.customer.customerId
            """)
    List<SumDepenseCustomer> findSumDepenseTicketEachCustomer();

    @Query("""
            select new site.easy.to.build.crm.dto.SumDepenseCustomer(sum(d.amount),d.budget.customer.name,d.budget.customer.customerId)
            from Depense d
            where
            not d.lead is null
            group by
            d.budget.customer.name,d.budget.customer.customerId
            """)
    List<SumDepenseCustomer> findSumDepenseLeadEachCustomer();
}
