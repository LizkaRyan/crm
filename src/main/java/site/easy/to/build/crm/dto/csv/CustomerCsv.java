package site.easy.to.build.crm.dto.csv;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import site.easy.to.build.crm.entity.Customer;
import site.easy.to.build.crm.entity.User;

@Getter
@Setter
@AllArgsConstructor
public class CustomerCsv {
    private String customerEmail;
    private String customerName;

    public Customer createCustomer(){
        Customer customer=new Customer();
        customer.setEmail(customerEmail);
        customer.setName(customerName);

        User user=new User();
        user.setUsername(customerName);
        user.setEmail(customerEmail);

        customer.setUser(user);
        customer.setCity("Antananarivo");
        customer.setCountry("Madagascar");
        customer.setState("Madagascar");
        customer.setAddress("Lot IVC 103");

        return customer;
    }
}
