package site.easy.to.build.crm.service.budget;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import site.easy.to.build.crm.entity.budget.TokenApi;
import site.easy.to.build.crm.repository.budget.TokenApiRepo;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class TokenApiService {
    private TokenApiRepo tokenApiRepo;

    public boolean tokenIsValid(String token){
        try{
            TokenApi tokenApi=tokenApiRepo.findTokenApiByToken(token).orElseThrow(()->new RuntimeException("Token not found"));
            if(tokenApi.getDateExpiration().isAfter(LocalDateTime.now())){
                return true;
            }
        }
        catch (RuntimeException ex){
            return false;
        }
        return false;
    }

    public TokenApi save(TokenApi tokenApi){
        return this.tokenApiRepo.save(tokenApi);
    }
}
