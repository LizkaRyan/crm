package site.easy.to.build.crm.config.token;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.HandlerInterceptor;
import site.easy.to.build.crm.service.budget.TokenApiService;

@Component
public class TokenInterceptor implements HandlerInterceptor {

    private final TokenApiService tokenApiService;

    public TokenInterceptor(TokenApiService tokenApiService){
        this.tokenApiService=tokenApiService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("Authorization"); // Ou le nom de ton header

        if (token != null) {
            if(tokenApiService.tokenIsValid(token)){
                return true;
            }
        }

        return false;
    }
}
