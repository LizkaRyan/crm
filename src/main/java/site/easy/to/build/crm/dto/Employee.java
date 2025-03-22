package site.easy.to.build.crm.dto;

import lombok.AllArgsConstructor;
import lombok.Setter;

@Setter
@AllArgsConstructor
public class Employee {
//    id,username,first_name,last_name,email,password,provider
    private int id;
    private String userName;
    private String firstName;
    private String lastName;
    private String password;
    private String provider;
}
