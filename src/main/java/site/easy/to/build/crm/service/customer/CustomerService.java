package site.easy.to.build.crm.service.customer;

import site.easy.to.build.crm.dto.csv.CustomerCsv;
import site.easy.to.build.crm.dto.csv.entity.CustomerUser;
import site.easy.to.build.crm.entity.Customer;
import site.easy.to.build.crm.entity.Role;

import java.util.List;

public interface CustomerService {

    public Customer findByCustomerId(int customerId);

    public List<Customer> findByUserId(int userId);

    public Customer findByEmail(String email);

    public List<Customer> findAll();

    public Customer save(Customer customer);


    public List<Customer> saveAll(List<Customer> customers);

    public void delete(Customer customer);

    public List<Customer> getRecentCustomers(int userId, int limit);

    long countByUserId(int userId);

    public Integer findCountCustomer();

    CustomerUser createCustomer(List<CustomerCsv> customerCsvs, Role role);
}
