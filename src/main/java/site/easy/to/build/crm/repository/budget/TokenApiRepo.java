package site.easy.to.build.crm.repository.budget;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import site.easy.to.build.crm.entity.budget.TokenApi;

import java.util.Optional;

public interface TokenApiRepo extends JpaRepository<TokenApi,String> {

    @Query("select t from TokenApi t where t.token = :tokenValue")
    Optional<TokenApi> findTokenApiByToken(@Param("tokenValue")String tokenValue);
}
