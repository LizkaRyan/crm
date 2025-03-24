package site.easy.to.build.crm.repository.budget;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import site.easy.to.build.crm.entity.budget.Budget;

import java.util.List;
import java.util.Optional;

public interface BudgetRepository extends JpaRepository<Budget,Long> {

    @Query("select b from Budget b where b.customer.customerId = :idCustomer")
    List<Budget> findBudgetByIdCustomer(@Param("idCustomer")Integer idCustomer);

    @Query("select sum(b.budget) from Budget b where b.customer.customerId = :idCustomer")
    Optional<Double> findSumBudgetByIdCustomer(@Param("idCustomer")Integer idCustomer);

    @Query("select sum(b.budget) from Budget b")
    Optional<Double> findSumBudget();
}
