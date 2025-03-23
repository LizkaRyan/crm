package site.easy.to.build.crm.repository.budget;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import site.easy.to.build.crm.entity.budget.SeuilBudget;

import java.util.Optional;

public interface SeuilBudgetRepo extends JpaRepository<SeuilBudget,Long> {
    @Query("select s from SeuilBudget s where s.dateSeuil = (select max(s.dateSeuil) from SeuilBudget s)")
    Optional<SeuilBudget> findActualSeuilBudget();
}
