package site.easy.to.build.crm.dto.csv.entity;

import lombok.Getter;
import site.easy.to.build.crm.entity.Customer;
import site.easy.to.build.crm.entity.User;

import java.util.List;

@Getter
public class CustomerUser {
    List<Customer> customers;
    List<User> users;

    public CustomerUser(List<Customer> customers,List<User> users){
        this.customers=customers;
        this.users=users;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers=customers;
    }

    public void setUsers(List<User> users){
        this.users=users;
        if(customers!=null){
            for (Customer customer:this.getCustomers()){
                customer.setUser(users);
            }
        }
    }
}
