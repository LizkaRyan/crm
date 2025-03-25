package site.easy.to.build.crm.service.customer;

import org.checkerframework.checker.units.qual.C;
import site.easy.to.build.crm.dto.csv.CustomerCsv;
import site.easy.to.build.crm.entity.Customer;
import site.easy.to.build.crm.entity.User;

import java.util.List;

public interface CustomerService {

    public Customer findByCustomerId(int customerId);

    public List<Customer> findByUserId(int userId);

    public Customer findByEmail(String email);

    public List<Customer> findAll();

    public Customer save(Customer customer);

    public void delete(Customer customer);

    public List<Customer> getRecentCustomers(int userId, int limit);

    long countByUserId(int userId);

    public Integer findCountCustomer();

    List<Customer> createCustomer(List<CustomerCsv> customerCsvs);
}
