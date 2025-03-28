package site.easy.to.build.crm.controller.budget;

import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.easy.to.build.crm.dto.LoginRequest;
import site.easy.to.build.crm.dto.ResponseJSON;
import site.easy.to.build.crm.entity.User;
import site.easy.to.build.crm.entity.budget.TokenApi;
import site.easy.to.build.crm.service.budget.TokenApiService;
import site.easy.to.build.crm.service.user.UserService;

import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class LoginRestController {

    private final UserService userService;

    private final PasswordEncoder passwordEncoder;

    private final TokenApiService tokenApiService;

    @PostMapping("/login")
    public ResponseJSON<?> login(@RequestBody LoginRequest loginRequest, HttpSession session){
        List<User> user=userService.findByUsername(loginRequest.getUsername());
        if(user.size()>0){
            if(passwordEncoder.matches(loginRequest.getPassword(), user.get(0).getPassword())){
                TokenApi tokenApi=tokenApiService.save(new TokenApi());
                return new ResponseJSON<>(200,"Login réussie",tokenApi.getToken());
            }
        }
        return new ResponseJSON<>(400,"Username not found or password incorrect",false);
    }
}
