package site.easy.to.build.crm.service.customer;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import site.easy.to.build.crm.dto.csv.CustomerCsv;
import site.easy.to.build.crm.dto.csv.entity.CustomerUser;
import site.easy.to.build.crm.entity.Role;
import site.easy.to.build.crm.entity.User;
import site.easy.to.build.crm.repository.CustomerRepository;
import site.easy.to.build.crm.entity.Customer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    private final JdbcTemplate jdbcTemplate;

    @Override
    public Customer findByCustomerId(int customerId) {
        return customerRepository.findByCustomerId(customerId);
    }

    @Override
    public Customer findByEmail(String email) {
        return customerRepository.findByEmail(email);
    }

    @Override
    public List<Customer> findByUserId(int userId) {
        return customerRepository.findByUserId(userId);
    }

    @Override
    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    @Override
    public Customer save(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public void delete(Customer customer) {
        customerRepository.delete(customer);
    }

    @Override
    public List<Customer> getRecentCustomers(int userId, int limit) {
        Pageable pageable = PageRequest.of(0, limit);
        return customerRepository.findByUserIdOrderByCreatedAtDesc(userId, pageable);
    }

    @Override
    public long countByUserId(int userId) {
        return customerRepository.countByUserId(userId);
    }

    @Override
    public Integer findCountCustomer() {
        return this.customerRepository.findCountCustomer().orElse(0);
    }

    @Override
    public CustomerUser createCustomer(List<CustomerCsv> customerCsvs, Role role) {
        List<Customer> customers=new ArrayList<>();
        List<User> users=new ArrayList<>();
        for (CustomerCsv customerCsv:customerCsvs){
            Customer customer=customerCsv.createCustomer();

            User user=customer.getUser();
            user.setRoles(Arrays.asList(role));
            user.setStatus("active");

            customers.add(customer);
            users.add(user);
        }
        return new CustomerUser(customers,users);
    }

    public List<Customer> saveAll(List<Customer> customers){
        insertBatch(customers);
        return this.customerRepository.findAll();
    }

    public void insertBatch(List<Customer> customers){
        String sql = "INSERT INTO customer (name, address, city, state, country, user_id, email) VALUES (?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.batchUpdate(sql, customers, 50, (ps, entity) -> {
            ps.setString(1, entity.getName());
            ps.setString(2, entity.getAddress());
            ps.setString(3, entity.getCity());
            ps.setString(4, entity.getState());
            ps.setString(5, entity.getCountry());
            ps.setInt(6, entity.getUser().getId());
            ps.setString(7,entity.getEmail());
        });
    }
}
