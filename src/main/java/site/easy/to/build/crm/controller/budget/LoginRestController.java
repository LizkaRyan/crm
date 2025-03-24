package site.easy.to.build.crm.controller.budget;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.easy.to.build.crm.dto.LoginRequest;
import site.easy.to.build.crm.dto.ResponseJSON;
import site.easy.to.build.crm.entity.User;
import site.easy.to.build.crm.service.user.UserServiceImpl;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class LoginRestController {

    private final UserServiceImpl userService;

    private final PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseJSON<Boolean> login(@RequestBody LoginRequest loginRequest){
        List<User> user=userService.findByUsername(loginRequest.getUsername());
        if(user.size()>0){
            if(passwordEncoder.matches(loginRequest.getPassword(), user.get(0).getPassword())){
                return new ResponseJSON<>(200,"Login réussie",true);
            }
        }
        return new ResponseJSON<>(200,"Username not found or password incorrect",false);
    }
}
